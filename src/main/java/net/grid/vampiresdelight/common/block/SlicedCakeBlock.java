package net.grid.vampiresdelight.common.block;

import com.mojang.datafixers.util.Pair;
import de.teamlapen.vampirism.core.ModEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import vectorwing.farmersdelight.common.tag.ModTags;

import java.util.Objects;
import java.util.function.Supplier;

// Credit: Collector's Reap (Brnbrd)
public class SlicedCakeBlock  extends CakeBlock {
    public final Supplier<Item> slice;

    public SlicedCakeBlock(Properties properties, Supplier<Item> slice) {
        super(properties);
        this.slice = slice;
    }

    public Item getSliceItem() {
        return slice.get();
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack heldStack = player.getItemInHand(hand);
        if (heldStack.is(ItemTags.CANDLES) && state.getValue(BITES) == 0) {
            Block block = Block.byItem(heldStack.getItem());
            if (block instanceof CandleBlock candle) {
                if (!player.isCreative()) {
                    heldStack.shrink(1);
                }
                level.playSound(null, pos, SoundEvents.CAKE_ADD_CANDLE, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.setBlockAndUpdate(pos, SlicedCandleCakeBlock.byCakeCandle(this, candle));
                level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
                player.awardStat(Stats.ITEM_USED.get(heldStack.getItem()));
                return InteractionResult.SUCCESS;
            }
        }
        if (level.isClientSide) {
            if (heldStack.is(ModTags.KNIVES)) {
                return cutSlice(level, pos, state, player);
            }

            if (this.consumeBite(level, pos, state, player) == InteractionResult.SUCCESS) {
                return InteractionResult.SUCCESS;
            }

            if (heldStack.isEmpty()) {
                return InteractionResult.CONSUME;
            }
        }

        if (heldStack.is(ModTags.KNIVES)) {
            return cutSlice(level, pos, state, player);
        }
        return this.consumeBite(level, pos, state, player);
    }

    /**
     * Eats a slice from the pie, feeding the player.
     */
    protected InteractionResult consumeBite(Level level, BlockPos pos, BlockState state, Player playerIn) {
        if (!playerIn.canEat(false)) {
            return InteractionResult.PASS;
        }
        Item slice = this.getSliceItem();
        ItemStack sliceStack = slice.getDefaultInstance();

        playerIn.getFoodData().eat(sliceStack.getItem(), sliceStack);
        this.affectConsumer(sliceStack, level, playerIn);
        level.gameEvent(playerIn, GameEvent.BLOCK_CHANGE, pos);

        int bites = state.getValue(BITES);
        if (bites < 6) {
            level.setBlock(pos, state.setValue(BITES, bites + 1), 3);
        } else {
            level.removeBlock(pos, false);
            level.gameEvent(playerIn, GameEvent.BLOCK_DESTROY, pos);
        }
        level.playSound(null, pos, SoundEvents.GENERIC_EAT, SoundSource.PLAYERS, 0.8F, 0.8F);
        return InteractionResult.SUCCESS;
    }

    /**
     * Cuts off a bite and drops a slice item, without feeding the player.
     */
    protected InteractionResult cutSlice(Level level, BlockPos pos, BlockState state, Player player) {
        int bites = state.getValue(BITES);
        if (bites < 6) {
            level.setBlock(pos, state.setValue(BITES, bites + 1), 3);
        } else {
            level.removeBlock(pos, false);
        }

        Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), this.getSliceItem().getDefaultInstance());
        level.playSound(null, pos, SoundEvents.WOOL_BREAK, SoundSource.PLAYERS, 0.8F, 0.8F);
        return InteractionResult.SUCCESS;
    }

    public static void affectConsumer(ItemStack stack, Level level, Player consumer) {
        Item item = stack.getItem();
        if (item.isEdible() && stack.getFoodProperties(consumer) != null) {
            for(Pair<MobEffectInstance, Float> pair : Objects.requireNonNull(stack.getFoodProperties(consumer)).getEffects()) {
                if (!level.isClientSide && pair.getFirst() != null && level.random.nextFloat() < pair.getSecond()) {
                    consumer.addEffect(new MobEffectInstance(pair.getFirst()));
                }
            }
        }
    }
}
