package net.grid.vampiresdelight.common.world.blockentity;

import com.mojang.logging.LogUtils;
import net.grid.vampiresdelight.common.core.VDBlockEntities;
import net.grid.vampiresdelight.common.tag.VDItemTags;
import net.grid.vampiresdelight.common.world.block.WineShelfBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.slf4j.Logger;

import java.util.Objects;

public class WineShelfBlockEntity extends BlockEntity implements Container {
    
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final String KEY_LAST_SLOT = "LastInteractedSlot";
    
    private final NonNullList<ItemStack> items = NonNullList.withSize(4, ItemStack.EMPTY);
    private int lastInteractedSlot = -1;

    public WineShelfBlockEntity(BlockPos pos, BlockState blockState) {
        super(VDBlockEntities.WINE_SHELF.get(), pos, blockState);
    }

    private void updateState(int slot) {
        if (slot >= 0 && slot < 4) {
            lastInteractedSlot = slot;
            BlockState state = getBlockState();

            for (int i = 0; i < WineShelfBlock.SLOT_CONTENTS.size(); ++i) {
                EnumProperty<WineShelfBlock.Slot> enumProperty = WineShelfBlock.SLOT_CONTENTS.get(i);
                state = state.setValue(enumProperty, WineShelfBlock.getSlotTypeForStack(getItem(i)));
            }

            Objects.requireNonNull(level).setBlock(worldPosition, state, Block.UPDATE_ALL);
        } else {
            LOGGER.error("Expected slot 0-3, got {}", slot);
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        items.clear();
        ContainerHelper.loadAllItems(tag, items);
        lastInteractedSlot = tag.getInt(KEY_LAST_SLOT);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        ContainerHelper.saveAllItems(tag, items, true);
        tag.putInt(KEY_LAST_SLOT, lastInteractedSlot);
    }

    @Override
    public void clearContent() {
        items.clear();
    }

    @Override
    public int getContainerSize() {
        return 4;
    }

    @Override
    public boolean isEmpty() {
        return items.stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    public ItemStack getItem(int slot) {
        return items.get(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        ItemStack itemstack = Objects.requireNonNullElse(items.get(slot), ItemStack.EMPTY);
        items.set(slot, ItemStack.EMPTY);
        if (!itemstack.isEmpty()) {
            updateState(slot);
        }

        return itemstack;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return removeItem(slot, 1);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        if (stack.is(VDItemTags.WINE_SHELF_BOTTLES)) {
            items.set(slot, stack);
            updateState(slot);
        }
    }

    @Override
    public boolean canTakeItem(Container target, int slot, ItemStack stack) {
        return target.hasAnyMatching(matchStack -> matchStack.isEmpty() || ItemStack.isSameItemSameTags(stack, matchStack) && matchStack.getCount() + stack.getCount() <= target.getMaxStackSize());
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        return stack.is(VDItemTags.WINE_SHELF_BOTTLES) && getItem(index).isEmpty();
    }

    public int getLastInteractedSlot() {
        return lastInteractedSlot;
    }
}
