package net.grid.vampiresdelight.common.utility;


import com.mojang.datafixers.util.Pair;
import net.grid.vampiresdelight.VampiresDelight;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeI18n;

import java.util.List;

public class VDTextUtils {
    public static MutableComponent getTranslation(String key, Object... args) {
        return Component.translatable(VampiresDelight.MODID + "." + key, args);
    }

    public static String getStraightTranslation(String key) {
        return ForgeI18n.getPattern(VampiresDelight.MODID + "." + key);
    }

    public static void addFoodEffectTooltip(ItemStack item, LivingEntity consumer, List<Component> tooltip) {
        FoodProperties foodProperties = item.getItem().getFoodProperties(item, consumer);
        if (foodProperties == null) return;

        List<Pair<MobEffectInstance, Float>> effectList = foodProperties.getEffects();

        for (Pair<MobEffectInstance, Float> effectPair : effectList) {
            MobEffectInstance instance = effectPair.getFirst();
            if (instance != null) {
                MutableComponent iformattabletextcomponent = Component.translatable(instance.getDescriptionId());
                MobEffect effect = instance.getEffect();

                if (instance.getAmplifier() > 0) {
                    iformattabletextcomponent = Component.translatable("potion.withAmplifier", iformattabletextcomponent, Component.translatable("potion.potency." + instance.getAmplifier()));
                }

                if (instance.getDuration() > 20) {
                    iformattabletextcomponent = Component.translatable("potion.withDuration", iformattabletextcomponent, MobEffectUtil.formatDuration(instance, 1.0F));
                }

                tooltip.add(iformattabletextcomponent.withStyle(effect.getCategory().getTooltipFormatting()));
            }
        }
    }
}
