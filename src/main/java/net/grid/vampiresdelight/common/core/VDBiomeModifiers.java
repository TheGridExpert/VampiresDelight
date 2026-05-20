package net.grid.vampiresdelight.common.core;

import de.teamlapen.vampirism.core.ModTags;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.tag.VDBiomeTags;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.ForgeRegistries;
import vectorwing.farmersdelight.common.world.modifier.AddFeaturesByFilterBiomeModifier;

import java.util.Optional;

public class VDBiomeModifiers {

    public static final ResourceKey<BiomeModifier> WILD_GARLIC = createKey("wild_garlic");
    public static final ResourceKey<BiomeModifier> PATCH_BLACK_MUSHROOM = createKey("patch_black_mushroom");

    public static void create(BootstapContext<BiomeModifier> context) {
        HolderGetter<Biome> biomeLookup = context.lookup(Registries.BIOME);
        HolderGetter<PlacedFeature> placedFeatureLookup = context.lookup(Registries.PLACED_FEATURE);
        context.register(WILD_GARLIC, new AddFeaturesByFilterBiomeModifier(biomeLookup.getOrThrow(BiomeTags.IS_OVERWORLD), Optional.of(biomeLookup.getOrThrow(VDBiomeTags.WITHOUT_WILD_GARLIC)), Optional.of(0.4F), Optional.of(0.9F), HolderSet.direct(placedFeatureLookup.getOrThrow(VDPlacedFeatures.PATCH_WILD_GARLIC)), GenerationStep.Decoration.VEGETAL_DECORATION));
        context.register(PATCH_BLACK_MUSHROOM, new AddFeaturesByFilterBiomeModifier(biomeLookup.getOrThrow(ModTags.Biomes.IS_VAMPIRE_BIOME), Optional.empty(), Optional.empty(), Optional.empty(), HolderSet.direct(placedFeatureLookup.getOrThrow(VDPlacedFeatures.PATCH_BLACK_MUSHROOM)), GenerationStep.Decoration.VEGETAL_DECORATION));
    }

    private static ResourceKey<BiomeModifier> createKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, name));
    }
}
