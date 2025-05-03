package net.grid.vampiresdelight.common.constant;

import de.teamlapen.vampirism.core.ModEffects;
import net.grid.vampiresdelight.common.VDConfiguration;
import net.grid.vampiresdelight.common.misc.VDFoodPropertiesBuilder;
import net.grid.vampiresdelight.common.registry.VDEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

import java.util.function.Supplier;

import static vectorwing.farmersdelight.common.FoodValues.comfort;
import static vectorwing.farmersdelight.common.FoodValues.nourishment;

public class VDFoodValues {
    public static final int MOMENT_DURATION = 200;    // 10 seconds
    public static final int FLEETING_DURATION = 400;    // 20 seconds
    public static final int BRIEF_DURATION = 600;    // 30 seconds
    public static final int SHORT_DURATION = 1200;    // 1 minute
    public static final int MEDIUM_DURATION = 3600;    // 3 minutes
    public static final int LONG_DURATION = 6000;    // 5 minutes

    // Nasty
    public static final FoodProperties NASTY = new FoodProperties.Builder()
            .nutrition(1).saturationModifier(0.1f)
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, FLEETING_DURATION), 1.0F).build();
    
    
    public static final FoodProperties NASTY_BLINDNESS = new FoodProperties.Builder()
            .nutrition(1).saturationModifier(0.1f)
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, FLEETING_DURATION), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.BLINDNESS, MOMENT_DURATION), 1.0F).build();
    
    
    public static final FoodProperties NASTY_DARKNESS = new FoodProperties.Builder()
            .nutrition(1).saturationModifier(0.1f)
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, FLEETING_DURATION), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.DARKNESS, MOMENT_DURATION), 1.0F).build();
    
    public static final FoodProperties NASTY_BLOOD_DOUGH = new FoodProperties.Builder()
            .nutrition(1).saturationModifier(0.1f)
            .effect(() -> new MobEffectInstance(MobEffects.HUNGER, BRIEF_DURATION), 0.5F)
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, FLEETING_DURATION), 1.0F).build();
    
    public static final FoodProperties NASTY_POISON = new FoodProperties.Builder()
            .nutrition(1).saturationModifier(0.1f)
            .effect(() -> new MobEffectInstance(MobEffects.POISON, 60), 1.0F).build();
    

    // Drinks
    public static final FoodProperties DANDELION_BEER_MUG = new FoodProperties.Builder().alwaysEdible()
            .nutrition(4).saturationModifier(0.3f)
            .effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, MEDIUM_DURATION), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 200), 1.0F).build();
    
    public static final FoodProperties BLOOD_WINE_GLASS_VAMPIRE = new FoodProperties.Builder().alwaysEdible()
            .nutrition(7).saturationModifier(0.6f)
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 300), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 300), 1.0F).build();
    
    public static final FoodProperties BLOOD_WINE_GLASS_HUMAN = new FoodProperties.Builder().alwaysEdible()
            .nutrition(1).saturationModifier(0.1f)
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 400), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.HUNGER, MOMENT_DURATION), 1.0F).build();
    
    public static final FoodProperties ORCHID_TEA_HUMAN = new FoodProperties.Builder().alwaysEdible()
            .effect(() -> new MobEffectInstance(MobEffects.POISON, 60), 1.0F).build();
    
    public static final FoodProperties ORCHID_TEA_VAMPIRE = new FoodProperties.Builder().alwaysEdible()
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, MOMENT_DURATION), 1.0F).build();
    
    public static final FoodProperties ORCHID_TEA_IMMUNE = new FoodProperties.Builder().alwaysEdible()
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, FLEETING_DURATION), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.BLINDNESS, MOMENT_DURATION), 1.0F).build();
    
    public static final FoodProperties BLOOD_SYRUP = new FoodProperties.Builder()
            .nutrition(9).alwaysEdible().build();
    
    public static final FoodProperties MULLED_WINE_GLASS_VAMPIRE = new FoodProperties.Builder().alwaysEdible()
            .nutrition(8).saturationModifier(0.7f)
            .effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, MEDIUM_DURATION), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 200), 1.0F).build();
    
    public static final FoodProperties MULLED_WINE_GLASS_HUMAN = new FoodProperties.Builder().alwaysEdible()
            .nutrition(1).saturationModifier(0.1f)
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 400), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.HUNGER, MOMENT_DURATION), 1.0F).build();
    

    // Small foods
    public static final FoodProperties ROASTED_GARLIC = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.2f).build();
    
    public static final FoodProperties RICE_DOUGH = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(0.3f).effect(() -> new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.3F).build();
    
    public static final FoodProperties BLOOD_DOUGH = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.3f)
            .effect(() -> new MobEffectInstance(MobEffects.HUNGER, BRIEF_DURATION), 0.3F).build();
    
    public static final FoodProperties HEART_PIECES = new FoodProperties.Builder()
            .nutrition(10).saturationModifier(0.8f).fast().build();
    
    public static final FoodProperties HUMAN_EYE = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(0.1f).build();
    
    public static final FoodProperties RAW_BAT = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(0.3f)    
            .effect(batMeatWitherEffect(FLEETING_DURATION), 0.4F).build();
    
    public static final FoodProperties RAW_BAT_CHOPS = new FoodProperties.Builder()
            .nutrition(1).saturationModifier(0.3f).fast()
            .effect(batMeatWitherEffect(FLEETING_DURATION), 0.2F).build();
    
    public static final FoodProperties GRILLED_BAT_HUMAN = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.5f)    
            .effect(batMeatWitherEffect(FLEETING_DURATION), 0.2F).build();
    
    public static final FoodProperties GRILLED_BAT_VAMPIRE = new FoodProperties.Builder()
            .nutrition(6).saturationModifier(0.5f).build();
    
    public static final FoodProperties GRILLED_BAT_CHOPS_HUMAN = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.2f).fast()
            .effect(() -> new MobEffectInstance(MobEffects.HUNGER, 400), 0.1F)
            .effect(batMeatWitherEffect(FLEETING_DURATION), 0.1F).build();
    
    public static final FoodProperties GRILLED_BAT_CHOPS_VAMPIRE = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.2f).fast().build();
    

    // Sweets and desserts
    public static final FoodProperties ORCHID_COOKIE = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(0.4f).fast().build();
    
    public static final FoodProperties ORCHID_ECLAIR = new FoodProperties.Builder()
            .nutrition(8).saturationModifier(0.7f).fast().build();
    
    public static final FoodProperties ORCHID_ICE_CREAM = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.5f).fast()
            .effect(() -> new MobEffectInstance(ModEffects.FIRE_PROTECTION, SHORT_DURATION, 1), 1.0F).build();
    
    public static final FoodProperties SUGARED_BERRIES = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.3f).build();
    
    public static final FoodProperties CURSED_CUPCAKE = new FoodProperties.Builder()
            .nutrition(10).saturationModifier(0.8f).fast().alwaysEdible()
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, MOMENT_DURATION), 1.0F).build();
    
    public static final FoodProperties BLOOD_PIE_SLICE = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.6f).fast()
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, BRIEF_DURATION), 1.0F).build();
    
    public static final FoodProperties PURE_SORBET = new FoodProperties.Builder()
            .nutrition(8).saturationModifier(0.8f).fast().alwaysEdible()
            .effect(() -> new MobEffectInstance(VDEffects.FOG_VISION, MEDIUM_DURATION), 1.0F).build();
    
    public static final FoodProperties TRICOLOR_DANGO = new FoodProperties.Builder()
            .nutrition(12).saturationModifier(1.4f).fast().build();
    
    public static final FoodProperties DARK_ICE_CREAM = new FoodProperties.Builder()
            .nutrition(9).saturationModifier(0.8f).fast().alwaysEdible()
            .effect(() -> new MobEffectInstance(MobEffects.JUMP, SHORT_DURATION), 1.0F).build();
    
    public static final FoodProperties ORCHID_CAKE_SLICE = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.2f).fast().alwaysEdible()
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, FLEETING_DURATION), 1.0F).build();
    
    public static final FoodProperties SNOW_WHITE_ICE_CREAM = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.5f).fast().alwaysEdible()
            .effect(() -> new MobEffectInstance(VDEffects.BLESSING, MEDIUM_DURATION), 1.0F).build();
    
    public static final FoodProperties WOLF_BERRY_ICE_CREAM = new FoodProperties.Builder()
            .nutrition(8).saturationModifier(0.5f).fast().alwaysEdible()
            .effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 2400), 1.0F).build();
    

    // Handheld Foods
    public static final FoodProperties RICE_BREAD = new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.6F).build();
    
    public static final FoodProperties FISH_BURGER = new FoodProperties.Builder()
            .nutrition(12).saturationModifier(0.8f).build();
    
    public static final FoodProperties BLOOD_SAUSAGE = new FoodProperties.Builder()
            .nutrition(10).saturationModifier(0.8f).build();
    
    public static final FoodProperties BLOOD_HOT_DOG = new FoodProperties.Builder()
            .nutrition(14).saturationModifier(0.8f).build();
    
    public static final FoodProperties BLOOD_BAGEL = new FoodProperties.Builder()
            .nutrition(9).saturationModifier(0.7f).build();
    
    public static final FoodProperties BAGEL_SANDWICH = new FoodProperties.Builder()
            .nutrition(14).saturationModifier(0.8f).build();
    
    public static final FoodProperties EYES_ON_STICK = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.6f).build();
    
    public static final FoodProperties EYE_CROISSANT = new FoodProperties.Builder()
            .nutrition(10).saturationModifier(0.6f).build();
    
    public static final FoodProperties BAT_TACO = new FoodProperties.Builder()
            .nutrition(9).saturationModifier(0.8f).build();
    
    public static final FoodProperties BAT_TACO_HUMAN = new FoodProperties.Builder()
            .nutrition(8).saturationModifier(0.5f)
            .effect(batMeatWitherEffect(FLEETING_DURATION), 0.2F).build();
    
    public static final FoodProperties HARDTACK_HUMAN = new VDFoodPropertiesBuilder()
            .nutrition(6).saturationModifier(0.9f).eatSeconds(2.4f).build();
    
    public static final FoodProperties HARDTACK_HUNTER = new FoodProperties.Builder()
            .nutrition(8).saturationModifier(1.0f).build();
    

    // Bowl Foods
    public static final FoodProperties ORCHID_CREAM_SOUP = new FoodProperties.Builder()
            .nutrition(14).saturationModifier(0.75f)
            .effect(() -> comfort(LONG_DURATION), 1.0F).build();
    
    public static final FoodProperties BLACK_MUSHROOM_SOUP = new FoodProperties.Builder()
            .nutrition(12).saturationModifier(0.7f)
            .effect(() -> comfort(MEDIUM_DURATION), 1.0F).build();
    
    public static final FoodProperties GARLIC_SOUP = new FoodProperties.Builder()
            .nutrition(14).saturationModifier(0.75f)
            .effect(() -> comfort(MEDIUM_DURATION), 1.0F).build();
    
    public static final FoodProperties BORSCHT = new FoodProperties.Builder()
            .nutrition(16).saturationModifier(0.75f)
            .effect(() -> comfort(MEDIUM_DURATION), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 1800), 1.0F).build();
    

    // Plated Foods
    public static final FoodProperties ORCHID_CURRY = new FoodProperties.Builder()
            .nutrition(14).saturationModifier(0.75f)
            .effect(() -> nourishment(MEDIUM_DURATION), 1.0F).build();
    
    public static final FoodProperties BLACK_MUSHROOM_NOODLES = new FoodProperties.Builder()
            .nutrition(14).saturationModifier(0.75f)
            .effect(() -> nourishment(LONG_DURATION), 1.0F).build();
    

    // Feast Portions
    public static final FoodProperties WEIRD_JELLY = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.8f).fast().alwaysEdible()
            .effect(() -> {
                if (VDConfiguration.REPLACE_WEIRD_JELLY_SUNSCREEN_WITH_JUMPBOOST.get()) {
                    return new MobEffectInstance(MobEffects.JUMP, SHORT_DURATION);
                } else {
                    return new MobEffectInstance(de.teamlapen.vampirism.core.ModEffects.SUNSCREEN, SHORT_DURATION);
                }
            }, 1.0F).build();

    public static Supplier<MobEffectInstance> batMeatWitherEffect(int duration) {
        return () -> new MobEffectInstance(VDConfiguration.BAT_MEAT_WITHERS_HUMANS.get() ? MobEffects.WITHER : MobEffects.POISON, duration);
    }
}
