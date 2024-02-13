package net.grid.vampiresdelight.common.tag;

import net.grid.vampiresdelight.VampiresDelight;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class VDTags {
    // Vampire and Hunter food.
    public static final TagKey<Item> VAMPIRE_FOOD = modItemTag("vampire_food");
    public static final TagKey<Item> HUNTER_FOOD = modItemTag("hunter_food");
    // Vampire and Hunter food that can be fed to minions.
    public static final TagKey<Item> MINION_VAMPIRE_FOOD = modItemTag("minion_vampire_food");

    // Cold blocks brewing barrel won't work on.
    public static final TagKey<Block> COOLERS = modBlockTag("coolers");

    // Biomes lost wagon spawns in.
    public static final TagKey<Biome> HAS_LOST_WAGON = modBiomeTag("has_structure/lost_wagon");


    private static TagKey<Item> modItemTag(String path) {
        return ItemTags.create(new ResourceLocation(VampiresDelight.MODID, path));
    }

    private static TagKey<Block> modBlockTag(String path) {
        return BlockTags.create(new ResourceLocation(VampiresDelight.MODID, path));
    }

    private static TagKey<EntityType<?>> modEntityTag(String path) {
        return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(VampiresDelight.MODID, path));
    }

    private static TagKey<Biome> modBiomeTag(String path) {
        return TagKey.create(Registries.BIOME, new ResourceLocation(VampiresDelight.MODID, path));
    }
}
