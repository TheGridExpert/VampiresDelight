package net.grid.vampiresdelight.common.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WineGlassItem extends VampireDrinkableItem {
    public WineGlassItem(Properties properties) {
        super(properties);
    }

    @Override
    public void affectVampire(ItemStack stack, Level level, LivingEntity consumer) {
        consumer.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200, 1));
        consumer.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 80));
    }

    @Override
    public void affectHuman(ItemStack stack, Level level, LivingEntity consumer) {
        consumer.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 400));
        consumer.addEffect(new MobEffectInstance(MobEffects.HUNGER, 200));
    }

    @Override
    public void tooltipVampire(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag isAdvanced, List<MobEffectInstance> effects) {
        effects.add(new MobEffectInstance(MobEffects.REGENERATION, 200, 1));
        effects.add(new MobEffectInstance(MobEffects.CONFUSION, 80));
        PotionUtils.addPotionTooltip(effects, tooltip, 1.0F);
    }

    @Override
    public void tooltipHuman(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag isAdvanced, List<MobEffectInstance> effects) {
        effects.add(new MobEffectInstance(MobEffects.CONFUSION, 400));
        effects.add(new MobEffectInstance(MobEffects.HUNGER, 200));
        PotionUtils.addPotionTooltip(effects, tooltip, 1.0F);
    }
}
