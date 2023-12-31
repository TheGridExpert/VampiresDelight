package net.grid.vampiresdelight.common;

import net.grid.vampiresdelight.common.registry.VDEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import vectorwing.farmersdelight.common.registry.ModEffects;

public class VDFoodValues {
    public static final int FLEETING_DURATION = 400;    // 20 seconds
    public static final int BRIEF_DURATION = 600;    // 30 seconds
    public static final int SHORT_DURATION = 1200;    // 1 minute
    public static final int MEDIUM_DURATION = 3600;    // 3 minutes
    public static final int LONG_DURATION = 6000;    // 5 minutes

    // Gross
    public static final FoodProperties NASTY = (new FoodProperties.Builder())
            .nutrition(1).saturationMod(0.1f)
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, FLEETING_DURATION), 1.0F).build();
    public static final FoodProperties NASTY_BLOOD_DOUGH = (new FoodProperties.Builder())
            .nutrition(1).saturationMod(0.1f)
            .effect(() -> new MobEffectInstance(MobEffects.HUNGER, BRIEF_DURATION), 0.5F)
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, FLEETING_DURATION), 1.0F).build();

    // Drinks
    public static final FoodProperties WINE_GLASS_VAMPIRE = (new FoodProperties.Builder()).alwaysEat()
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 200, 1), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 300), 1.0F).build();
    public static final FoodProperties WINE_GLASS_HUMAN = (new FoodProperties.Builder()).alwaysEat()
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 400), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.HUNGER, 200), 1.0F).build();
    public static final FoodProperties ORCHID_TEA_VAMPIRE = (new FoodProperties.Builder()).alwaysEat()
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 400), 1.0F).build();
    public static final FoodProperties ORCHID_TEA_HUNTER = (new FoodProperties.Builder()).alwaysEat()
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 400), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.BLINDNESS, 200), 1.0F).build();

    // Small foods
    public static final FoodProperties GRILLED_GARLIC = (new FoodProperties.Builder())
            .nutrition(3).saturationMod(0.2f).build();
    public static final FoodProperties BLOOD_DOUGH = (new FoodProperties.Builder())
            .nutrition(2).saturationMod(0.3f)
            .effect(() -> new MobEffectInstance(MobEffects.HUNGER, BRIEF_DURATION), 0.3F).build();
    public static final FoodProperties HEART_PIECES = (new FoodProperties.Builder())
            .nutrition(10).saturationMod(0.8f).meat().fast().build();
    public static final FoodProperties HUMAN_EYE = (new FoodProperties.Builder())
            .nutrition(2).saturationMod(0.1f).meat().build();

    // Sweets and desserts
    public static final FoodProperties SUGARED_BERRIES = (new FoodProperties.Builder())
            .nutrition(3).saturationMod(0.3f).build();
    public static final FoodProperties CURSED_CUPCAKE = (new FoodProperties.Builder())
            .nutrition(4).saturationMod(0.4f).fast().alwaysEat().build();
    public static final FoodProperties BLOOD_PIE_SLICE = (new FoodProperties.Builder())
            .nutrition(4).saturationMod(0.4f).fast()
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, BRIEF_DURATION), 1.0F).build();
    public static final FoodProperties PURE_SORBET = (new FoodProperties.Builder())
            .nutrition(4).saturationMod(0.2f).fast().alwaysEat()
            .effect(() -> new MobEffectInstance(VDEffects.FOG_VISION.get(), MEDIUM_DURATION), 1.0F).build();
    public static final FoodProperties TRICOLOR_DANGO = (new FoodProperties.Builder())
            .nutrition(7).saturationMod(0.7f).fast().build();

    // Handheld Foods
    public static final FoodProperties BLOOD_BAGEL = (new FoodProperties.Builder())
            .nutrition(6).saturationMod(0.7f).build();
    public static final FoodProperties BAGEL_SANDWICH = (new FoodProperties.Builder())
            .nutrition(13).saturationMod(1.2f).build();
    public static final FoodProperties EYE_TOAST = (new FoodProperties.Builder())
            .nutrition(8).saturationMod(0.8f).build();
    public static final FoodProperties HARDTACK = (new FoodProperties.Builder())
            .nutrition(6).saturationMod(0.9f).build();

    // Bowl Foods
    public static final FoodProperties GARLIC_SOUP = (new FoodProperties.Builder())
            .nutrition(12).saturationMod(0.6f)
            .effect(() -> new MobEffectInstance(ModEffects.COMFORT.get(), MEDIUM_DURATION), 1.0F).build();
    public static final FoodProperties BORSCHT = (new FoodProperties.Builder())
            .nutrition(14).saturationMod(0.9f)
            .effect(() -> new MobEffectInstance(ModEffects.COMFORT.get(), MEDIUM_DURATION), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, SHORT_DURATION), 1.0F).build();

    // Plated Foods


    // Feast Portions
    public static final FoodProperties WEIRD_JELLY = (new FoodProperties.Builder())
            .nutrition(8).saturationMod(0.8f).fast().alwaysEat()
            .effect(() -> new MobEffectInstance(de.teamlapen.vampirism.core.ModEffects.SUNSCREEN.get(), 300), 1.0F).build();
}
