package net.grid.vampiresdelight.common.block;

import de.teamlapen.vampirism.api.entity.vampire.IVampire;
import de.teamlapen.vampirism.entity.player.vampire.VampirePlayer;
import de.teamlapen.vampirism.entity.vampire.DrinkBloodContext;
import de.teamlapen.vampirism.util.Helper;
import net.grid.vampiresdelight.common.VDFoodValues;
import net.grid.vampiresdelight.common.utility.VDHelper;
import net.minecraft.MethodsReturnNonnullByDefault;
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

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Supplier;

/**
 * Credits to the Neapolitan mod
 */
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ConsumableCakeBlock extends CakeBlock {
    private final Supplier<Item> cakeSlice;

    public ConsumableCakeBlock(Properties properties, Supplier<Item> cakeSlice) {
        super(properties);
        this.cakeSlice = cakeSlice;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack itemstack = player.getItemInHand(hand);
        Item item = itemstack.getItem();
        if (itemstack.is(ItemTags.CANDLES) && state.getValue(BITES) == 0) {
            Block block = Block.byItem(item);
            if (block instanceof CandleBlock && ConsumableCandleCakeBlock.hasCandle(block, this)) {
                if (!player.isCreative()) {
                    itemstack.shrink(1);
                }

                level.playSound(null, pos, SoundEvents.CAKE_ADD_CANDLE, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.setBlockAndUpdate(pos, ConsumableCandleCakeBlock.byCandle(block, this));
                level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
                player.awardStat(Stats.ITEM_USED.get(item));
                return InteractionResult.SUCCESS;
            }
        }

        if (level.isClientSide) {
            if (consumeBite(level, pos, state, player).consumesAction()) {
                return InteractionResult.SUCCESS;
            }

            if (itemstack.isEmpty()) {
                return InteractionResult.CONSUME;
            }
        }

        return consumeBite(level, pos, state, player);
    }

    public InteractionResult consumeBite(Level level, BlockPos pos, BlockState state, Player player) {
        if (!player.canEat(false)) {
            return InteractionResult.PASS;
        } else {
            player.awardStat(Stats.EAT_CAKE_SLICE);

            ItemStack sliceStack = this.getCakeSlice();
            FoodProperties vampireFood = VDFoodValues.BLOOD_PIE_SLICE;

            // Don't shrink stack before retrieving food
            VampirePlayer.getOpt(player).ifPresent(v -> v.drinkBlood(vampireFood.getNutrition(), vampireFood.getSaturationModifier(), new DrinkBloodContext(sliceStack)));

            if (player instanceof IVampire) {
                ((IVampire) player).drinkBlood(vampireFood.getNutrition(), vampireFood.getSaturationModifier(), new DrinkBloodContext(sliceStack));
            } else if (!Helper.isVampire(player))
                player.eat(level, sliceStack);

            if (Helper.isVampire(player)) {
                VDHelper.addFoodEffects(vampireFood, level, player);
            }

            int i = state.getValue(BITES);
            level.gameEvent(player, GameEvent.EAT, pos);
            if (i < 6) {
                level.setBlock(pos, state.setValue(BITES, i + 1), 3);
            } else {
                level.removeBlock(pos, false);
                level.gameEvent(player, GameEvent.BLOCK_DESTROY, pos);
            }

            return InteractionResult.SUCCESS;
        }
    }

    public ItemStack getCakeSlice() {
        return new ItemStack(this.cakeSlice.get());
    }
}
