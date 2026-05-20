package net.grid.vampiresdelight.common.tag;

import net.grid.vampiresdelight.VampiresDelight;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class VDBlockTags {

    public static final TagKey<Block> BLOODY_SOIL = tag("bloody_soil");
    public static final TagKey<Block> BLACK_MUSHROOM_GROW_BLOCK = tag("black_mushroom_grow_block");

    // All Wine Shelves (for advancements).
    public static final TagKey<Block> WINE_SHELVES_WOODEN = tag("wine_shelves_wooden");
    public static final TagKey<Block> WINE_SHELVES = tag("wine_shelves");

    private static TagKey<Block> tag(String name) {
        return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, name));
    }
}
