package net.grid.vampiresdelight.common;

import net.grid.vampiresdelight.common.registry.VDEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import vectorwing.farmersdelight.common.FoodValues;
import vectorwing.farmersdelight.common.registry.ModEffects;

public class VDFoodValues {
    // Vampires
    public static final FoodProperties NASTY = (new FoodProperties.Builder())
            .nutrition(1).saturationMod(0.1f).build();
    public static final FoodProperties BLOOD_DOUGH = (new FoodProperties.Builder())
            .nutrition(2).saturationMod(0.3f).build();
    public static final FoodProperties NASTY_BLOOD_DOUGH = (new FoodProperties.Builder())
            .nutrition(1).saturationMod(0.1f)
            .effect(() -> new MobEffectInstance(MobEffects.HUNGER, 400, 0), 0.5F).build();
    public static final FoodProperties HEART_PIECES = (new FoodProperties.Builder())
            .nutrition(10).saturationMod(0.8f).meat().fast().build();
    public static final FoodProperties HUMAN_EYE = (new FoodProperties.Builder())
            .nutrition(2).saturationMod(0.1f).meat().build();
    public static final FoodProperties CURSED_CUPCAKE = (new FoodProperties.Builder())
            .nutrition(4).saturationMod(0.4f).fast().alwaysEat().build();
    public static final FoodProperties BLOOD_BAGEL = (new FoodProperties.Builder())
            .nutrition(6).saturationMod(0.7f).build();
    public static final FoodProperties BLOOD_PIE_SLICE = (new FoodProperties.Builder())
            .nutrition(5).saturationMod(0.6f).fast().build();
    public static final FoodProperties EYE_TOAST = (new FoodProperties.Builder())
            .nutrition(7).saturationMod(0.8f).build();
    public static final FoodProperties BAGEL_SANDWICH = (new FoodProperties.Builder())
            .nutrition(14).saturationMod(1.2f).build();
    public static final FoodProperties WEIRD_JELLY = (new FoodProperties.Builder())
            .nutrition(8).saturationMod(0.8f).fast().alwaysEat().build();

    // Hunters
    public static final FoodProperties GRILLED_GARLIC = (new FoodProperties.Builder())
            .nutrition(3).saturationMod(0.2f).build();
    public static final FoodProperties HARDTACK = (new FoodProperties.Builder())
            .nutrition(6).saturationMod(0.8f).build();
    public static final FoodProperties HARDTACK_WITH_JAM = (new FoodProperties.Builder())
            .nutrition(8).saturationMod(0.9f)
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 150, 0), 1.0F).build();
    public static final FoodProperties GARLIC_SOUP = (new FoodProperties.Builder())
            .nutrition(12).saturationMod(0.6f)
            .effect(() -> new MobEffectInstance(ModEffects.COMFORT.get(), FoodValues.MEDIUM_DURATION, 0), 1.0F).build();
    public static final FoodProperties BORSCHT = (new FoodProperties.Builder())
            .nutrition(14).saturationMod(0.9f)
            .effect(() -> new MobEffectInstance(ModEffects.COMFORT.get(), FoodValues.MEDIUM_DURATION, 0), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, FoodValues.SHORT_DURATION, 0), 1.0F).build();

    // Neutral
    public static final FoodProperties SUGARED_BERRIES = (new FoodProperties.Builder())
            .nutrition(3).saturationMod(0.3f).build();
    public static final FoodProperties PURE_SORBET = (new FoodProperties.Builder())
            .nutrition(4).saturationMod(0.2f).fast().alwaysEat()
            .effect(() -> new MobEffectInstance(VDEffects.FOG_VISION.get(), FoodValues.MEDIUM_DURATION, 0), 1.0F).build();
}
