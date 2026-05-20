package net.grid.vampiresdelight.common.core;

import net.grid.vampiresdelight.VampiresDelight;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import vectorwing.farmersdelight.common.registry.ModBiomeFeatures;
import vectorwing.farmersdelight.common.world.configuration.WildCropConfiguration;

import java.util.List;

public class VDConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_WILD_GARLIC = createKey("patch_wild_garlic");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_BLACK_MUSHROOM = createKey("patch_black_mushroom");
    public static final ResourceKey<ConfiguredFeature<?, ?>> HUGE_BLACK_MUSHROOM = createKey("huge_black_mushroom");

    public static void create(BootstapContext<ConfiguredFeature<?, ?>> context) {
        context.register(PATCH_WILD_GARLIC, new ConfiguredFeature<>(ModBiomeFeatures.WILD_CROP.get(), new WildCropConfiguration(64, 7, 4, createPlacedFeature(VDBlocks.WILD_GARLIC.get(), BlockTags.DIRT), createPlacedFeature(Blocks.TALL_GRASS, BlockTags.DIRT), createFloorFeature(Blocks.COARSE_DIRT, BlockTags.DIRT))));
        context.register(PATCH_BLACK_MUSHROOM, new ConfiguredFeature<>(Feature.RANDOM_PATCH, FeatureUtils.simpleRandomPatchConfiguration(10, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(VDBlocks.BLACK_MUSHROOM.get()))))));
        context.register(HUGE_BLACK_MUSHROOM, new ConfiguredFeature<>(VDFeatures.HUGE_BLACK_MUSHROOM.get(), NoneFeatureConfiguration.INSTANCE));
    }

    private static Holder<PlacedFeature> createPlacedFeature(Block blockToPlace, TagKey<Block> blockToPlaceOn) {
        return Holder.direct(new PlacedFeature(Holder.direct(new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(blockToPlace)))), List.of(BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, BlockPredicate.matchesTag(new Vec3i(0, -1, 0), blockToPlaceOn))))));
    }

    private static Holder<PlacedFeature> createFloorFeature(Block blockToPlace, TagKey<Block> blockToPlaceIn) {
        return Holder.direct(new PlacedFeature(Holder.direct(new ConfiguredFeature<>(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(blockToPlace)))), List.of(BlockPredicateFilter.forPredicate(BlockPredicate.allOf(BlockPredicate.replaceable(new Vec3i(0, 1, 0)), BlockPredicate.matchesTag(blockToPlaceIn))))));
    }

    private static ResourceKey<ConfiguredFeature<?, ?>> createKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, name));
    }
}
