package net.grid.vampiresdelight.common.block;

import de.teamlapen.vampirism.api.VReference;
import de.teamlapen.vampirism.api.entity.factions.IFaction;
import de.teamlapen.vampirism.api.entity.player.vampire.IVampirePlayer;
import de.teamlapen.vampirism.api.items.IFactionExclusiveItem;
import de.teamlapen.vampirism.entity.player.vampire.VampirePlayer;
import de.teamlapen.vampirism.entity.vampire.DrinkBloodContext;
import de.teamlapen.vampirism.util.Helper;
import net.grid.vampiresdelight.common.VDFoodValues;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.block.PieBlock;

import java.util.function.Supplier;

public class BloodPieBlock extends PieBlock implements IFactionExclusiveItem {
    public BloodPieBlock(Properties properties, Supplier<Item> pieSlice) {
        super(properties, pieSlice);
    }

    @Override
    protected InteractionResult consumeBite(Level level, BlockPos pos, BlockState state, Player playerIn) {
        if (!playerIn.canEat(false)) {
            return InteractionResult.PASS;
        } else {
            ItemStack sliceStack = this.getPieSliceItem();
            FoodProperties vampireFood = VDFoodValues.BLOOD_PIE_SLICE;

            VampirePlayer.getOpt(playerIn).ifPresent(v -> v.drinkBlood(vampireFood.getNutrition(), vampireFood.getSaturationModifier(), new DrinkBloodContext(sliceStack)));

            if (playerIn instanceof IVampirePlayer) {
                ((IVampirePlayer) playerIn).drinkBlood(vampireFood.getNutrition(), vampireFood.getSaturationModifier(), new DrinkBloodContext(sliceStack));
            } else {
                playerIn.eat(level, sliceStack); //Shrinks stack and applies human food effects
            }

            playerIn.getFoodData().eat(sliceStack.getItem(), sliceStack);
            if (!Helper.isVampire(playerIn)) {
                playerIn.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 20 * 20));
            } else {
                playerIn.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600, 0));
            }

            if (!sliceStack.isEdible()) {
                if (playerIn instanceof ServerPlayer) {
                    CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer) playerIn, sliceStack);
                }
            }

            int bites = state.getValue(BITES);
            if (bites < getMaxBites() - 1) {
                level.setBlock(pos, state.setValue(BITES, bites + 1), 3);
            } else {
                level.removeBlock(pos, false);
            }
            level.playSound(null, pos, SoundEvents.GENERIC_EAT, SoundSource.PLAYERS, 0.8F, 0.8F);
            return InteractionResult.SUCCESS;
        }
    }

    @Override
    public @Nullable IFaction<?> getExclusiveFaction(@NotNull ItemStack stack) {
        return VReference.VAMPIRE_FACTION;
    }
}
