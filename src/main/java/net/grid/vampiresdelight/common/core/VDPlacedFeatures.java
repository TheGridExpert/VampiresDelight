package net.grid.vampiresdelight.common.core;

import net.grid.vampiresdelight.VampiresDelight;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import vectorwing.farmersdelight.common.world.filter.BiomeTagFilter;

import java.util.List;

public class VDPlacedFeatures {

    public static final ResourceKey<PlacedFeature> PATCH_WILD_GARLIC = createKey("patch_wild_garlic");
    public static final ResourceKey<PlacedFeature> PATCH_BLACK_MUSHROOM = createKey("patch_black_mushroom");

    public static void create(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> placedFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
        context.register(PATCH_WILD_GARLIC, new PlacedFeature(placedFeatures.getOrThrow(VDConfiguredFeatures.PATCH_WILD_GARLIC), List.of(RarityFilter.onAverageOnceEvery(120), InSquarePlacement.spread(), HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING), BiomeFilter.biome(), BiomeTagFilter.biomeIsInTag(BiomeTags.IS_OVERWORLD))));
        context.register(PATCH_BLACK_MUSHROOM, new PlacedFeature(placedFeatures.getOrThrow(VDConfiguredFeatures.PATCH_BLACK_MUSHROOM), List.of(RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome())));
    }

    private static ResourceKey<PlacedFeature> createKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, name));
    }
}
