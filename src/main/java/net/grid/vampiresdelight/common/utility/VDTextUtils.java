package net.grid.vampiresdelight.common.utility;


import net.grid.vampiresdelight.VampiresDelight;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.ai.attributes.Attribute;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.List;

public class VDTextUtils {
    /**
     * Syntactic sugar for custom translation keys. Always prefixed with the mod's ID in lang files (e.g. vampiresdelight.your.key.here).
     */
    public static MutableComponent getTranslation(String key, Object... args) {
        return Component.translatable(VampiresDelight.MODID + "." + key, args);
    }

    /**
     * An alternate version of TextUtils.addFoodEffectTooltip, that uses FoodProperties instead of ItemStack.
     */
    @OnlyIn(Dist.CLIENT)
    public static void addFoodEffectTooltip(@NotNull FoodProperties foodStats, List<Component> lores, float durationFactor) {
        List<Pair<MobEffectInstance, Float>> effectList = foodStats.getEffects();
        List<Pair<Attribute, AttributeModifier>> attributeList = Lists.newArrayList();
        if (!effectList.isEmpty()) {
            for (Pair<MobEffectInstance, Float> effectPair : effectList) {
                MobEffectInstance instance = effectPair.getFirst();
                MutableComponent iformattabletextcomponent = Component.translatable(instance.getDescriptionId());
                MobEffect effect = instance.getEffect();
                Map<Attribute, AttributeModifier> attributeMap = effect.getAttributeModifiers();
                if (!attributeMap.isEmpty()) {
                    for (Map.Entry<Attribute, AttributeModifier> entry : attributeMap.entrySet()) {
                        AttributeModifier rawModifier = entry.getValue();
                        AttributeModifier modifier = new AttributeModifier(rawModifier.getName(), effect.getAttributeModifierValue(instance.getAmplifier(), rawModifier), rawModifier.getOperation());
                        attributeList.add(new Pair<>(entry.getKey(), modifier));
                    }
                }

                if (instance.getAmplifier() > 0) {
                    iformattabletextcomponent = Component.translatable("potion.withAmplifier", iformattabletextcomponent, Component.translatable("potion.potency." + instance.getAmplifier()));
                }

                if (instance.getDuration() > 20) {
                    iformattabletextcomponent = Component.translatable("potion.withDuration", iformattabletextcomponent, MobEffectUtil.formatDuration(instance, durationFactor));
                }

                lores.add(iformattabletextcomponent.withStyle(effect.getCategory().getTooltipFormatting()));
            }
        }
    }
}
