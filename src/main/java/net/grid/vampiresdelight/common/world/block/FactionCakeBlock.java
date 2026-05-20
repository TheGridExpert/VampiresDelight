package net.grid.vampiresdelight.common.world.block;

import de.teamlapen.vampirism.util.Helper;
import net.grid.vampiresdelight.common.util.VDEntityUtils;
import net.grid.vampiresdelight.common.world.item.FactionConsumableItem;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import vectorwing.farmersdelight.common.registry.ModSounds;
import vectorwing.farmersdelight.common.utility.ItemUtils;

import java.util.function.Supplier;

public class FactionCakeBlock extends CakeBlock {

    private final Supplier<Item> cakeSlice;

    public FactionCakeBlock(Properties properties, Supplier<Item> cakeSlice) {
        super(properties);
        this.cakeSlice = cakeSlice;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand);
        Item item = stack.getItem();

        if (ItemUtils.isKnife(stack)) {
            return cutSlice(level, pos, state, player, stack);
        }

        if (stack.is(ItemTags.CANDLES) && state.getValue(BITES) == 0) {
            Block candle = Block.byItem(item);
            if (candle instanceof CandleBlock && FactionCandleCakeBlock.hasCandle(candle, this)) {
                if (!player.isCreative()) {
                    stack.shrink(1);
                }
                level.playSound(null, pos, SoundEvents.CAKE_ADD_CANDLE, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.setBlockAndUpdate(pos, FactionCandleCakeBlock.byCandle(candle, this));
                level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
                player.awardStat(Stats.ITEM_USED.get(item));
                return InteractionResult.SUCCESS;
            }
        }

        if (level.isClientSide) {
            if (consumeBite(level, pos, state, player).consumesAction()) {
                return InteractionResult.SUCCESS;
            }
            if (stack.isEmpty()) {
                return InteractionResult.CONSUME;
            }
        }

        return consumeBite(level, pos, state, player);
    }

    public InteractionResult consumeBite(Level level, BlockPos pos, BlockState state, Player player) {
        ItemStack sliceStack = getCakeSlice();

        if (!(sliceStack.getItem() instanceof FactionConsumableItem sliceItem)) {
            return eat(level, pos, state, player);
        }

        boolean isVampire = Helper.isVampire(player);
        if (isVampire && sliceItem.getVampireFood() == null) return InteractionResult.PASS;
        if (!isVampire && !player.canEat(false)) return InteractionResult.PASS;

        FoodProperties foodProperties = sliceStack.getFoodProperties(player);
        if (foodProperties == null) return InteractionResult.PASS;

        player.awardStat(Stats.EAT_CAKE_SLICE);
        VDEntityUtils.consumeBloodFood(sliceStack.copy(), level, player);

        int bites = state.getValue(BITES);
        level.gameEvent(player, GameEvent.EAT, pos);
        if (bites < MAX_BITES - 1) {
            level.setBlock(pos, state.setValue(BITES, bites + 1), 3);
        } else {
            level.removeBlock(pos, false);
            level.gameEvent(player, GameEvent.BLOCK_DESTROY, pos);
        }

        return InteractionResult.SUCCESS;
    }

    public InteractionResult cutSlice(Level level, BlockPos pos, BlockState state, Player player, ItemStack knife) {
        if (level.isClientSide) return InteractionResult.SUCCESS;

        int bites = state.getValue(BITES);
        ItemUtils.spawnItemEntity(level, getCakeSlice(), pos.getX() + (bites * 0.1), pos.getY() + 0.2, pos.getZ() + 0.5, -0.05, 0, 0);

        if (bites < MAX_BITES - 1) {
            level.setBlock(pos, state.setValue(BITES, bites + 1), 3);
        } else {
            level.removeBlock(pos, false);
            level.gameEvent(player, GameEvent.BLOCK_DESTROY, pos);
        }

        level.playSound(null, pos, ModSounds.BLOCK_FOOD_SLICE.get(), SoundSource.PLAYERS, 0.8F, 0.8F);
        player.awardStat(Stats.ITEM_USED.get(knife.getItem()));

        return InteractionResult.SUCCESS;
    }

    public ItemStack getCakeSlice() {
        return new ItemStack(cakeSlice.get());
    }
}