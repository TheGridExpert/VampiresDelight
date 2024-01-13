package net.grid.vampiresdelight.common.block.entity;

import com.mojang.logging.LogUtils;
import net.grid.vampiresdelight.common.block.WineShelfBlock;
import net.grid.vampiresdelight.common.registry.VDBlockEntityTypes;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.Objects;
import java.util.function.Predicate;

public class WineShelfBlockEntity extends BlockEntity implements Container {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final NonNullList<ItemStack> items = NonNullList.withSize(4, ItemStack.EMPTY);
    private int lastInteractedSlot = -1;

    public WineShelfBlockEntity(BlockPos pos, BlockState blockState) {
        super(VDBlockEntityTypes.WINE_SHELF.get(), pos, blockState);
    }

    private void updateState(int slot) {
        if (slot >= 0 && slot < 4) {
            this.lastInteractedSlot = slot;
            BlockState blockstate = this.getBlockState();

            for(int i = 0; i < WineShelfBlock.SLOT_OCCUPIED_PROPERTIES.size(); ++i) {
                boolean flag = !this.getItem(i).isEmpty();
                BooleanProperty booleanproperty = WineShelfBlock.SLOT_OCCUPIED_PROPERTIES.get(i);
                blockstate = blockstate.setValue(booleanproperty, flag);
            }

            Objects.requireNonNull(this.level).setBlock(this.worldPosition, blockstate, 3);
        } else {
            LOGGER.error("Expected slot 0-3, got {}", slot);
        }
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        this.items.clear();
        ContainerHelper.loadAllItems(tag, this.items);
        this.lastInteractedSlot = tag.getInt("last_interacted_slot");
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        ContainerHelper.saveAllItems(tag, this.items, true);
        tag.putInt("last_interacted_slot", this.lastInteractedSlot);
    }

    public int count() {
        return (int)this.items.stream().filter(Predicate.not(ItemStack::isEmpty)).count();
    }

    @Override
    public void clearContent() {
        this.items.clear();
    }

    @Override
    public int getContainerSize() {
        return 4;
    }

    @Override
    public boolean isEmpty() {
        return this.items.stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    public @NotNull ItemStack getItem(int slot) {
        return this.items.get(slot);
    }

    @Override
    public @NotNull ItemStack removeItem(int slot, int amount) {
        ItemStack itemstack = Objects.requireNonNullElse(this.items.get(slot), ItemStack.EMPTY);
        this.items.set(slot, ItemStack.EMPTY);
        if (!itemstack.isEmpty()) {
            this.updateState(slot);
        }

        return itemstack;
    }

    @Override
    public @NotNull ItemStack removeItemNoUpdate(int slot) {
        return this.removeItem(slot, 1);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        if (stack.is(VDItems.BLOOD_WINE_BOTTLE.get())) {
            this.items.set(slot, stack);
            this.updateState(slot);
        }
    }

    @Override
    public boolean canTakeItem(Container target, int index, @NotNull ItemStack stack) {
        return target.hasAnyMatching((matchStack) -> {
            if (matchStack.isEmpty()) {
                return true;
            } else {
                return ItemStack.isSameItemSameTags(stack, matchStack) && matchStack.getCount() + stack.getCount() <= Math.min(matchStack.getMaxStackSize(), target.getMaxStackSize());
            }
        });
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        return stack.is(VDItems.BLOOD_WINE_BOTTLE.get()) && this.getItem(index).isEmpty();
    }

    public int getLastInteractedSlot() {
        return this.lastInteractedSlot;
    }

    private net.minecraftforge.common.util.LazyOptional<?> itemHandler = net.minecraftforge.common.util.LazyOptional.of(this::createUnSidedHandler);

    protected net.minecraftforge.items.IItemHandler createUnSidedHandler() {
        return new net.minecraftforge.items.wrapper.InvWrapper(this);
    }

    @Override
    public <T> net.minecraftforge.common.util.@NotNull LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.@NotNull Capability<T> cap, @org.jetbrains.annotations.Nullable net.minecraft.core.Direction side) {
        if (!this.remove && cap == net.minecraftforge.common.capabilities.ForgeCapabilities.ITEM_HANDLER)
            return itemHandler.cast();
        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        itemHandler.invalidate();
    }

    @Override
    public void reviveCaps() {
        super.reviveCaps();
        itemHandler = net.minecraftforge.common.util.LazyOptional.of(this::createUnSidedHandler);
    }
}
