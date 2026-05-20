package net.grid.vampiresdelight.common.core;

import de.teamlapen.vampirism.api.VampirismAPI;
import de.teamlapen.vampirism.api.items.ExtendedPotionMix;
import de.teamlapen.vampirism.core.ModBlocks;
import de.teamlapen.vampirism.core.ModTags;
import de.teamlapen.vampirism.effects.VampirismPotion.HunterPotion;
import net.grid.vampiresdelight.VampiresDelight;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.util.NonNullSupplier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class VDPotions {
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, VampiresDelight.MODID);

    public static final RegistryObject<HunterPotion> FOG_VISION = POTIONS.register("fog_vision", () -> new HunterPotion(null, new MobEffectInstance(VDEffects.FOG_VISION.get(), 3600)));
    public static final RegistryObject<HunterPotion> LONG_FOG_VISION = POTIONS.register("long_fog_vision", () -> new HunterPotion("fog_vision", new MobEffectInstance(VDEffects.FOG_VISION.get(), 9600)));
    public static final RegistryObject<HunterPotion> VERY_LONG_FOG_VISION = POTIONS.register("very_long_fog_vision", () -> new HunterPotion("fog_vision", new MobEffectInstance(VDEffects.FOG_VISION.get(), 96000)));
    public static final RegistryObject<HunterPotion> CONSECRATION = POTIONS.register("consecration", () -> new HunterPotion(null, new MobEffectInstance(VDEffects.CONSECRATION.get(), 3600)));
    public static final RegistryObject<HunterPotion> LONG_CONSECRATION = POTIONS.register("long_consecration", () -> new HunterPotion("consecration", new MobEffectInstance(VDEffects.CONSECRATION.get(), 9600)));
    public static final RegistryObject<HunterPotion> VERY_LONG_CONSECRATION = POTIONS.register("very_long_consecration", () -> new HunterPotion("consecration", new MobEffectInstance(VDEffects.CONSECRATION.get(), 96000)));
    public static final RegistryObject<Potion> DISSOLVING = POTIONS.register("dissolving", () -> new Potion(new MobEffectInstance(VDEffects.DISSOLVING.get(), 3600)));
    public static final RegistryObject<Potion> LONG_DISSOLVING = POTIONS.register("long_dissolving", () -> new Potion("dissolving", new MobEffectInstance(VDEffects.DISSOLVING.get(), 9600)));
    public static final RegistryObject<Potion> STRONG_DISSOLVING = POTIONS.register("strong_dissolving", () -> new Potion("dissolving", new MobEffectInstance(VDEffects.DISSOLVING.get(), 1800, 1)));
    public static final RegistryObject<HunterPotion> VERY_STRONG_DISSOLVING = POTIONS.register("very_strong_dissolving", () -> new HunterPotion("dissolving", new MobEffectInstance(VDEffects.DISSOLVING.get(), 600, 2)));
    public static final RegistryObject<HunterPotion> VERY_LONG_DISSOLVING = POTIONS.register("very_long_dissolving", () -> new HunterPotion("dissolving", new MobEffectInstance(VDEffects.DISSOLVING.get(), 96000)));
    public static final RegistryObject<HunterPotion> LONG_STRONG_DISSOLVING = POTIONS.register("long_strong_dissolving", () -> new HunterPotion("dissolving", new MobEffectInstance(VDEffects.DISSOLVING.get(), 4800, 1)));

    public static void register(IEventBus eventBus) {
        POTIONS.register(eventBus);
    }

    public static void registerPotionMixes() {
        master(FOG_VISION, () -> Ingredient.of(ModTags.Items.HOLY_WATER), 8, 4);
        durable(FOG_VISION, LONG_FOG_VISION);
        veryDurable(LONG_FOG_VISION, VERY_LONG_FOG_VISION);

        master(CONSECRATION, () -> Ingredient.of(VDItems.ORCHID_PETALS.get(), ModBlocks.VAMPIRE_ORCHID.get()), 16, 8);
        durable(CONSECRATION, LONG_CONSECRATION);
        veryDurable(LONG_CONSECRATION, VERY_LONG_CONSECRATION);

        durable(DISSOLVING, LONG_DISSOLVING);
        strong(DISSOLVING, STRONG_DISSOLVING);
        veryDurable(LONG_DISSOLVING, VERY_LONG_DISSOLVING);
        veryStrong(STRONG_DISSOLVING, VERY_STRONG_DISSOLVING);
        veryDurable(VERY_STRONG_DISSOLVING, LONG_STRONG_DISSOLVING);
        veryStrong(VERY_LONG_DISSOLVING, LONG_STRONG_DISSOLVING);
    }

    private static void durable(Supplier<? extends Potion> in, Supplier<? extends Potion> out) {
        VampirismAPI.extendedBrewingRecipeRegistry().addMix((new ExtendedPotionMix.Builder(in, out)).ingredient(() -> Ingredient.of(Items.REDSTONE), 1).blood().build());
    }

    private static void strong(Supplier<? extends Potion> in, Supplier<? extends Potion> out) {
        VampirismAPI.extendedBrewingRecipeRegistry().addMix((new ExtendedPotionMix.Builder(in, out)).ingredient(() -> Ingredient.of(Items.GLOWSTONE_DUST), 1).blood().build());
    }

    private static void veryDurable(Supplier<? extends Potion> in, Supplier<? extends Potion> out) {
        VampirismAPI.extendedBrewingRecipeRegistry().addMix((new ExtendedPotionMix.Builder(in, out)).ingredient(() -> Ingredient.of(Items.REDSTONE_BLOCK), 32, 16).blood().durable().build());
    }

    private static void veryStrong(Supplier<? extends Potion> in, Supplier<? extends Potion> out) {
        VampirismAPI.extendedBrewingRecipeRegistry().addMix((new ExtendedPotionMix.Builder(in, out)).ingredient(() -> Ingredient.of(Items.GLOWSTONE), 64, 32).blood().concentrated().build());
    }

    private static void master(Supplier<? extends Potion> out, NonNullSupplier<Ingredient> in, int count, int countReduced) {
        VampirismAPI.extendedBrewingRecipeRegistry().addMix((new ExtendedPotionMix.Builder(() -> Potions.AWKWARD, out)).master().ingredient(in, count, countReduced).blood().build());
    }
}
