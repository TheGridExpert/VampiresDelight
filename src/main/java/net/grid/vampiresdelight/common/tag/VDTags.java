package net.grid.vampiresdelight.common.tag;

import net.grid.vampiresdelight.VampiresDelight;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import vectorwing.farmersdelight.FarmersDelight;

public class VDTags {
    // Vampire and Hunter food.
    public static final TagKey<Item> VAMPIRE_FOOD = modItemTag("vampire_food");
    public static final TagKey<Item> HUNTER_FOOD = modItemTag("hunter_food");

    // Cold blocks brewing barrel won't work on.
    public static final TagKey<Block> COOLERS = modBlockTag("coolers");

    // Biomes lost wagon spawns in.
    public static final TagKey<Biome> HAS_LOST_WAGON = modBiomeTag("has_structure/lost_wagon");


    private static TagKey<Item> modItemTag(String path) {
        return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(VampiresDelight.MODID, path));
    }

    private static TagKey<Block> modBlockTag(String path) {
        return TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(VampiresDelight.MODID, path));
    }

    private static TagKey<Biome> modBiomeTag(String path) {
        return TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(VampiresDelight.MODID, path));
    }
}
