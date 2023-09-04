package net.grid.vampiresdelight.common;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import vectorwing.farmersdelight.common.FoodValues;
import vectorwing.farmersdelight.common.registry.ModEffects;

public class VDFoodValues {
    // Vampires
    public static final FoodProperties HEART_PIECES = (new FoodProperties.Builder())
            .nutrition(10).saturationMod(0.8f).meat().fast().build();
    public static final FoodProperties NASTY = (new FoodProperties.Builder())
            .nutrition(1).saturationMod(0.1f).build();
    public static final FoodProperties CURSED_CUPCAKE = (new FoodProperties.Builder())
            .nutrition(4).saturationMod(0.4f).build();
    public static final FoodProperties BLOOD_PIE_SLICE = (new FoodProperties.Builder())
            .nutrition(5).saturationMod(0.6f).fast().build();
    public static final FoodProperties HEARTY_PATTY = (new FoodProperties.Builder())
            .nutrition(15).saturationMod(1.2f).build();
    public static final FoodProperties WEIRD_JELLY = (new FoodProperties.Builder())
            .nutrition(8).saturationMod(0.8f).build();

    // Hunters
    public static final FoodProperties GRILLED_GARLIC = (new FoodProperties.Builder())
            .nutrition(3).saturationMod(0.2f).build();
    public static final FoodProperties GARLIC_SOUP = (new FoodProperties.Builder())
            .nutrition(12).saturationMod(0.6f)
            .effect(() -> new MobEffectInstance(ModEffects.COMFORT.get(), FoodValues.MEDIUM_DURATION, 0), 1.0F).build();
    public static final FoodProperties BORSCHT = (new FoodProperties.Builder())
            .nutrition(14).saturationMod(0.9f)
            .effect(() -> new MobEffectInstance(ModEffects.COMFORT.get(), FoodValues.MEDIUM_DURATION, 0), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, FoodValues.SHORT_DURATION, 0), 1.0F).build();
}
