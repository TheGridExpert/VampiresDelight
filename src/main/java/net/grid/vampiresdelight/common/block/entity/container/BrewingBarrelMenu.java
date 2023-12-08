package net.grid.vampiresdelight.common.block.entity.container;

import com.mojang.datafixers.util.Pair;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.block.entity.BrewingBarrelBlockEntity;
import net.grid.vampiresdelight.common.registry.VDBlocks;
import net.grid.vampiresdelight.common.registry.VDMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

public class BrewingBarrelMenu extends AbstractContainerMenu {
    public static final ResourceLocation EMPTY_CONTAINER_SLOT_BOTTLE = new ResourceLocation(VampiresDelight.MODID, "item/empty_container_slot_bottle");

    public final BrewingBarrelBlockEntity blockEntity;
    public final ItemStackHandler inventory;
    private final ContainerData brewingBarrelData;
    private final ContainerLevelAccess canInteractWithCallable;
    protected final Level level;
    public BrewingBarrelMenu(final int windowId, final Inventory playerInventory, final BrewingBarrelBlockEntity blockEntity, ContainerData brewingBarrelDataIn) {
        super(VDMenuTypes.BREWING_BARREL.get(), windowId);
        this.blockEntity = blockEntity;
        this.inventory = blockEntity.getInventory();
        this.brewingBarrelData = brewingBarrelDataIn;
        this.level = playerInventory.player.level;
        this.canInteractWithCallable = ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos());

        // Ingredient Slots - 2 Rows x 2 Columns
        int startX = 8;
        int startY = 18;
        int inputStartX = 33;
        int inputStartY = 19;
        int borderSlotSize = 18;
        for (int row = 0; row < 2; ++row) {
            for (int column = 0; column < 2; ++column) {
                this.addSlot(new SlotItemHandler(inventory, (row * 2) + column,
                        inputStartX + (column * borderSlotSize),
                        inputStartY + (row * borderSlotSize)));
            }
        }

        // Meal Display
        this.addSlot(new BrewingBarrelMenu.BrewingBarrelMealSlot(inventory, 4, 115, 26));

        // Bowl Input
        this.addSlot(new SlotItemHandler(inventory, 5, 82, 56)
        {
            @OnlyIn(Dist.CLIENT)
            public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                return Pair.of(InventoryMenu.BLOCK_ATLAS, EMPTY_CONTAINER_SLOT_BOTTLE);
            }
        });

        // Bowl Output
        this.addSlot(new BrewingBarrelMenu.BrewingBarrelResultSlot(playerInventory.player, blockEntity, inventory, 6, 115, 56));

        // Main Player Inventory
        int startPlayerInvY = startY * 4 + 12;
        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 9; ++column) {
                this.addSlot(new Slot(playerInventory, 7 + (row * 9) + column, startX + (column * borderSlotSize),
                        startPlayerInvY + (row * borderSlotSize)));
            }
        }

        // Hotbar
        for (int column = 0; column < 9; ++column) {
            this.addSlot(new Slot(playerInventory, column, startX + (column * borderSlotSize), 142));
        }

        this.addDataSlots(brewingBarrelDataIn);
    }

    private static BrewingBarrelBlockEntity getTileEntity(final Inventory playerInventory, final FriendlyByteBuf data) {
        Objects.requireNonNull(playerInventory, "playerInventory cannot be null");
        Objects.requireNonNull(data, "data cannot be null");
        final BlockEntity tileAtPos = playerInventory.player.level.getBlockEntity(data.readBlockPos());
        if (tileAtPos instanceof BrewingBarrelBlockEntity) {
            return (BrewingBarrelBlockEntity) tileAtPos;
        }
        throw new IllegalStateException("Tile entity is not correct! " + tileAtPos);
    }

    public BrewingBarrelMenu(final int windowId, final Inventory playerInventory, final FriendlyByteBuf data) {
        this(windowId, playerInventory, getTileEntity(playerInventory, data), new SimpleContainerData(4));
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return stillValid(canInteractWithCallable, playerIn, VDBlocks.BREWING_BARREL.get());
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        int indexMealDisplay = 4;
        int indexContainerInput = 5;
        int indexOutput = 6;
        int startPlayerInv = indexOutput + 1;
        int endPlayerInv = startPlayerInv + 36;

        ItemStack slotStackCopy = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack slotStack = slot.getItem();
            slotStackCopy = slotStack.copy();
            if (index == indexOutput) {
                if (!this.moveItemStackTo(slotStack, startPlayerInv, endPlayerInv, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (index > indexOutput) {
                boolean isValidContainer = slotStack.is(Items.GLASS_BOTTLE) || slotStack.is(blockEntity.getContainer().getItem());
                if (isValidContainer && !this.moveItemStackTo(slotStack, indexContainerInput, indexContainerInput + 1, false)) {
                    return ItemStack.EMPTY;
                } else if (!this.moveItemStackTo(slotStack, 0, indexMealDisplay, false)) {
                    return ItemStack.EMPTY;
                } else if (!this.moveItemStackTo(slotStack, indexContainerInput, indexOutput, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(slotStack, startPlayerInv, endPlayerInv, false)) {
                return ItemStack.EMPTY;
            }

            if (slotStack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (slotStack.getCount() == slotStackCopy.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, slotStack);
        }
        return slotStackCopy;
    }

    @OnlyIn(Dist.CLIENT)
    public int getBrewProgressionScaled() {
        int i = this.brewingBarrelData.get(0);
        int j = this.brewingBarrelData.get(1);
        return j != 0 && i != 0 ? i * 33 / j : 0;
    }

    @ParametersAreNonnullByDefault
    public static class BrewingBarrelMealSlot extends SlotItemHandler {
        public BrewingBarrelMealSlot(IItemHandler inventoryIn, int index, int xPosition, int yPosition) {
            super(inventoryIn, index, xPosition, yPosition);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return false;
        }

        @Override
        public boolean mayPickup(Player playerIn) {
            return false;
        }
    }

    @ParametersAreNonnullByDefault
    public static class BrewingBarrelResultSlot extends SlotItemHandler {
        public final BrewingBarrelBlockEntity tileEntity;
        private final Player player;
        private int removeCount;

        public BrewingBarrelResultSlot(Player player, BrewingBarrelBlockEntity tile, IItemHandler inventoryIn, int index, int xPosition, int yPosition) {
            super(inventoryIn, index, xPosition, yPosition);
            this.tileEntity = tile;
            this.player = player;
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return false;
        }

        @Override
        @Nonnull
        public ItemStack remove(int amount) {
            if (this.hasItem()) {
                this.removeCount += Math.min(amount, this.getItem().getCount());
            }

            return super.remove(amount);
        }

        @Override
        public void onTake(Player thePlayer, ItemStack stack) {
            this.checkTakeAchievements(stack);
            super.onTake(thePlayer, stack);
        }

        @Override
        protected void onQuickCraft(ItemStack stack, int amount) {
            this.removeCount += amount;
            this.checkTakeAchievements(stack);
        }

        @Override
        protected void checkTakeAchievements(ItemStack stack) {
            stack.onCraftedBy(this.player.level, this.player, this.removeCount);

            if (!this.player.level.isClientSide) {
                tileEntity.awardUsedRecipes(this.player);
            }

            this.removeCount = 0;
        }
    }
}
