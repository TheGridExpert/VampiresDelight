package net.grid.vampiresdelight.common.registry;

import de.teamlapen.vampirism.api.VampirismAPI;
import de.teamlapen.vampirism.api.items.ExtendedPotionMix;
import de.teamlapen.vampirism.core.ModBlocks;
import de.teamlapen.vampirism.effects.VampirismPotion.HunterPotion;
import net.grid.vampiresdelight.VampiresDelight;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.common.util.NonNullSupplier;

import java.util.function.Supplier;

public class VDPotions {
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(Registries.POTION, VampiresDelight.MODID);

    //Hunter
    public static final RegistryObject<HunterPotion> FOG_VISION = POTIONS.register("fog_vision",
            () -> new HunterPotion(null, new MobEffectInstance(VDEffects.FOG_VISION.get(), 3600)));
    public static final RegistryObject<HunterPotion> LONG_FOG_VISION = POTIONS.register("long_fog_vision",
            () -> new HunterPotion("fog_vision", new MobEffectInstance(VDEffects.FOG_VISION.get(), 9600)));
    public static final RegistryObject<HunterPotion> STRONG_FOG_VISION = POTIONS.register("strong_fog_vision",
            () -> new HunterPotion("fog_vision", new MobEffectInstance(VDEffects.FOG_VISION.get(), 800, 1)));
    public static final RegistryObject<HunterPotion> VERY_LONG_FOG_VISION = POTIONS.register("very_long_fog_vision",
            () -> new HunterPotion("fog_vision", new MobEffectInstance(VDEffects.FOG_VISION.get(), 96000)));
    public static final RegistryObject<HunterPotion> LONG_STRONG_FOG_VISION = POTIONS.register("long_strong_fog_vision",
            () -> new HunterPotion("fog_vision", new MobEffectInstance(VDEffects.FOG_VISION.get(), 2400, 1)));

    public static void registerPotionMixes() {
        master(FOG_VISION, () -> Ingredient.of(ModBlocks.VAMPIRE_ORCHID.get()), 16, 8);
        durable(FOG_VISION, LONG_FOG_VISION);
        strong(FOG_VISION, STRONG_FOG_VISION);
        veryDurable(LONG_FOG_VISION, VERY_LONG_FOG_VISION);
        veryStrong(VERY_LONG_FOG_VISION, LONG_STRONG_FOG_VISION);
    }

    private static void durable(Supplier<? extends Potion> in, Supplier<? extends Potion> out) {
        VampirismAPI.extendedBrewingRecipeRegistry().addMix(new ExtendedPotionMix.Builder(in, out).ingredient(() -> Ingredient.of(Items.REDSTONE), 1).blood().build());
    }

    private static void strong(Supplier<? extends Potion> in, Supplier<? extends Potion> out) {
        VampirismAPI.extendedBrewingRecipeRegistry().addMix(new ExtendedPotionMix.Builder(in, out).ingredient(() -> Ingredient.of(Items.GLOWSTONE_DUST), 1).blood().build());
    }

    private static void veryDurable(Supplier<? extends Potion> in, Supplier<? extends Potion> out) {
        VampirismAPI.extendedBrewingRecipeRegistry().addMix(new ExtendedPotionMix.Builder(in, out).ingredient(() -> Ingredient.of(Items.REDSTONE_BLOCK), 32, 16).blood().durable().build());
    }

    private static void veryStrong(Supplier<? extends Potion> in, Supplier<? extends Potion> out) {
        VampirismAPI.extendedBrewingRecipeRegistry().addMix(new ExtendedPotionMix.Builder(in, out).ingredient(() -> Ingredient.of(Items.GLOWSTONE), 64, 32).blood().concentrated().build());
    }

    private static void master(Supplier<? extends Potion> out, NonNullSupplier<Ingredient> in, int count, int countReduced) {
        VampirismAPI.extendedBrewingRecipeRegistry().addMix(new ExtendedPotionMix.Builder(() -> Potions.AWKWARD, out).master().ingredient(in, count, countReduced).blood().build());
    }
}
