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

    // Nasty
    public static final FoodProperties NASTY = (new FoodProperties.Builder())
            .nutrition(1).saturationMod(0.1f)
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, FLEETING_DURATION), 1.0F).build();
    public static final FoodProperties NASTY_BLINDNESS = (new FoodProperties.Builder())
            .nutrition(1).saturationMod(0.1f)
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, FLEETING_DURATION), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.BLINDNESS, 200), 1.0F).build();
    public static final FoodProperties NASTY_DARKNESS = (new FoodProperties.Builder())
            .nutrition(1).saturationMod(0.1f)
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, FLEETING_DURATION), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.DARKNESS, 200), 1.0F).build();
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
    public static final FoodProperties BLOOD_SYRUP = (new FoodProperties.Builder())
            .nutrition(5).alwaysEat().build();
    public static final FoodProperties MULLED_WINE_GLASS_VAMPIRE = (new FoodProperties.Builder()).alwaysEat()
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 200, 1), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 1200), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 300), 1.0F).build();
    public static final FoodProperties MULLED_WINE_GLASS_HUMAN = (new FoodProperties.Builder()).alwaysEat()
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 400), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.HUNGER, 200), 1.0F).build();

    // Small foods
    public static final FoodProperties GRILLED_GARLIC = (new FoodProperties.Builder())
            .nutrition(3).saturationMod(0.2f).build();
    public static final FoodProperties RICE_DOUGH = (new FoodProperties.Builder())
            .nutrition(2).saturationMod(0.3f).effect(() -> new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.3F).build();
    public static final FoodProperties BLOOD_DOUGH = (new FoodProperties.Builder())
            .nutrition(5).saturationMod(0.3f)
            .effect(() -> new MobEffectInstance(MobEffects.HUNGER, BRIEF_DURATION), 0.3F).build();
    public static final FoodProperties HEART_PIECES = (new FoodProperties.Builder())
            .nutrition(10).saturationMod(0.8f).meat().fast().build();
    public static final FoodProperties HUMAN_EYE = (new FoodProperties.Builder())
            .nutrition(2).saturationMod(0.1f).meat().build();
    public static final FoodProperties RAW_BAT = (new FoodProperties.Builder())
            .nutrition(2).saturationMod(0.3f).meat().fast()
            .effect(() -> new MobEffectInstance(MobEffects.WITHER, 600), 0.4F).build();
    public static final FoodProperties RAW_BAT_CHOPS = (new FoodProperties.Builder())
            .nutrition(1).saturationMod(0.3f).meat().fast()
            .effect(() -> new MobEffectInstance(MobEffects.HUNGER, 600), 0.4F).build();
    public static final FoodProperties GRILLED_BAT_HUMAN = (new FoodProperties.Builder())
            .nutrition(6).saturationMod(0.5f).meat()
            .effect(() -> new MobEffectInstance(MobEffects.WITHER, 400), 0.1F).build();
    public static final FoodProperties GRILLED_BAT_VAMPIRE = (new FoodProperties.Builder())
            .nutrition(6).saturationMod(0.5f).meat().build();
    public static final FoodProperties GRILLED_BAT_CHOPS_HUMAN = (new FoodProperties.Builder())
            .nutrition(3).saturationMod(0.2f).meat().fast()
            .effect(() -> new MobEffectInstance(MobEffects.HUNGER, 400), 0.1F).build();
    public static final FoodProperties GRILLED_BAT_CHOPS_VAMPIRE = (new FoodProperties.Builder())
            .nutrition(3).saturationMod(0.2f).meat().fast().build();

    // Sweets and desserts
    public static final FoodProperties ORCHID_COOKIE = (new FoodProperties.Builder())
            .nutrition(2).saturationMod(0.4f).fast().build();
    public static final FoodProperties ORCHID_ECLAIR = (new FoodProperties.Builder())
            .nutrition(9).saturationMod(0.7f).fast().build();
    public static final FoodProperties ORCHID_ICE_CREAM = (new FoodProperties.Builder())
            .nutrition(7).saturationMod(0.5f).fast()
            .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, SHORT_DURATION), 1.0F).build();
    public static final FoodProperties SUGARED_BERRIES = (new FoodProperties.Builder())
            .nutrition(3).saturationMod(0.3f).build();
    public static final FoodProperties CURSED_CUPCAKE = (new FoodProperties.Builder())
            .nutrition(11).saturationMod(0.8f).fast().alwaysEat().build();
    public static final FoodProperties BLOOD_PIE_SLICE = (new FoodProperties.Builder())
            .nutrition(7).saturationMod(0.6f).fast()
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, BRIEF_DURATION), 1.0F).build();
    public static final FoodProperties PURE_SORBET = (new FoodProperties.Builder())
            .nutrition(16).saturationMod(0.8f).fast().alwaysEat()
            .effect(() -> new MobEffectInstance(VDEffects.FOG_VISION.get(), BRIEF_DURATION), 1.0F).build();
    public static final FoodProperties TRICOLOR_DANGO = (new FoodProperties.Builder())
            .nutrition(13).saturationMod(1.4f).fast().build();
    public static final FoodProperties DARK_ICE_CREAM = (new FoodProperties.Builder())
            .nutrition(10).saturationMod(0.8f).fast()
            .effect(() -> new MobEffectInstance(MobEffects.JUMP, BRIEF_DURATION), 1.0F).build();
    public static final FoodProperties ORCHID_CAKE_SLICE = (new FoodProperties.Builder())
            .nutrition(3).saturationMod(0.2f).fast()
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, FLEETING_DURATION), 1.0F).build();
    public static final FoodProperties SNOW_WHITE_ICE_CREAM = (new FoodProperties.Builder())
            .nutrition(8).saturationMod(0.6f).fast()
            .effect(() -> new MobEffectInstance(VDEffects.BLESSING.get(), MEDIUM_DURATION), 1.0F).build();

    // Handheld Foods
    public static final FoodProperties RICE_BREAD = (new FoodProperties.Builder())
            .nutrition(5).saturationMod(0.6F).build();
    public static final FoodProperties FISH_BURGER = (new FoodProperties.Builder())
            .nutrition(13).saturationMod(1.1f).build();
    public static final FoodProperties BLOOD_SAUSAGE = (new FoodProperties.Builder())
            .nutrition(12).saturationMod(1.0f).build();
    public static final FoodProperties BLOOD_HOT_DOG = (new FoodProperties.Builder())
            .nutrition(16).saturationMod(1.4f).build();
    public static final FoodProperties BLOOD_BAGEL = (new FoodProperties.Builder())
            .nutrition(9).saturationMod(0.8f).build();
    public static final FoodProperties BAGEL_SANDWICH = (new FoodProperties.Builder())
            .nutrition(16).saturationMod(1.4f).build();
    public static final FoodProperties EYES_ON_STICK = (new FoodProperties.Builder())
            .nutrition(8).saturationMod(0.7f).build();
    public static final FoodProperties EYE_CROISSANT = (new FoodProperties.Builder())
            .nutrition(11).saturationMod(1.0f).build();
    public static final FoodProperties HARDTACK = (new FoodProperties.Builder())
            .nutrition(6).saturationMod(0.9f).build();

    // Bowl Foods
    public static final FoodProperties GARLIC_SOUP = (new FoodProperties.Builder())
            .nutrition(12).saturationMod(0.6f)
            .effect(() -> new MobEffectInstance(ModEffects.COMFORT.get(), MEDIUM_DURATION), 1.0F).build();
    public static final FoodProperties BORSCHT = (new FoodProperties.Builder())
            .nutrition(15).saturationMod(0.9f)
            .effect(() -> new MobEffectInstance(ModEffects.COMFORT.get(), MEDIUM_DURATION), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, SHORT_DURATION), 1.0F).build();

    // Plated Foods


    // Feast Portions
    public static final FoodProperties WEIRD_JELLY = (new FoodProperties.Builder())
            .nutrition(7).saturationMod(0.8f).fast().alwaysEat()
            .effect(() -> new MobEffectInstance(de.teamlapen.vampirism.core.ModEffects.SUNSCREEN.get(), BRIEF_DURATION), 1.0F).build();
}
