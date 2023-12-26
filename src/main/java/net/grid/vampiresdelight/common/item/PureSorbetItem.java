package net.grid.vampiresdelight.common.item;

import de.teamlapen.vampirism.api.entity.vampire.IVampire;
import de.teamlapen.vampirism.entity.player.vampire.VampirePlayer;
import net.grid.vampiresdelight.common.VDFoodValues;
import net.grid.vampiresdelight.common.registry.VDEffects;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.Configuration;
import vectorwing.farmersdelight.common.item.PopsicleItem;

import java.util.ArrayList;
import java.util.List;

public class PureSorbetItem extends PopsicleItem {
    private final MobEffectInstance mobEffectInstance;

    /**
    * Pure Sorbet is supposed to give the same saturation to both vampires and humans, that's why it has its own class
     */

    public PureSorbetItem() {
        super(new Properties().food(VDFoodValues.PURE_SORBET));
        this.mobEffectInstance = new MobEffectInstance(VDEffects.FOG_VISION.get(), 3600);
    }

    @NotNull
    @Override
    public ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level worldIn, @NotNull LivingEntity entityLiving) {
        if (!worldIn.isClientSide) {
            this.affectConsumer(stack, worldIn, entityLiving);
        }

        if (entityLiving instanceof Player player) {
            //Don't shrink stack before retrieving food
            VampirePlayer.getOpt(player).ifPresent(v -> v.drinkBlood(stack.getFoodProperties(entityLiving).getNutrition(), stack.getFoodProperties(entityLiving).getSaturationModifier()));
        }

        if (entityLiving instanceof IVampire) {
            ((IVampire) entityLiving).drinkBlood(stack.getFoodProperties(entityLiving).getNutrition(), stack.getFoodProperties(entityLiving).getSaturationModifier());
            stack.shrink(1);
        } else {
            entityLiving.eat(worldIn, stack); //Shrinks stack and applies human food effects
        }

        worldIn.playSound(null, entityLiving.getX(), entityLiving.getY(), entityLiving.getZ(), SoundEvents.PLAYER_BURP, SoundSource.PLAYERS, 0.5F, worldIn.random.nextFloat() * 0.1F + 0.9F);

        entityLiving.addEffect(mobEffectInstance);

        if (!stack.isEdible()) {
            Player player = entityLiving instanceof Player ? (Player) entityLiving : null;
            if (player instanceof ServerPlayer) {
                CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer) player, stack);
            }
        }

        return stack;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag isAdvanced) {
        if (Configuration.FOOD_EFFECT_TOOLTIP.get()) {
            List<MobEffectInstance> effects = new ArrayList<>();
            effects.add(mobEffectInstance);
            PotionUtils.addPotionTooltip(effects, tooltip, 1.0F);
        }
    }
}
