package net.grid.vampiresdelight.common.tag;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import vectorwing.farmersdelight.common.tag.CommonTags;

public class VDCommonTags {

    private static TagKey<Block> blockTag(String path) {
        return BlockTags.create(ResourceLocation.fromNamespaceAndPath("forge", path));
    }

    private static TagKey<Item> itemTag(String path) {
        return ItemTags.create(ResourceLocation.fromNamespaceAndPath("forge", path));
    }

    public static class Blocks extends CommonTags.Blocks {

        public static final TagKey<Block> STORAGE_BLOCKS_GARLIC = blockTag("storage_blocks/garlic");
    }

    public static class Items extends CommonTags.Items {

        public static final TagKey<Item> BREAD_RICE = itemTag("bread/rice");
        public static final TagKey<Item> BREAD_BLOOD = itemTag("bread/blood");
        public static final TagKey<Item> DOUGH_RICE = itemTag("dough/rice");
        public static final TagKey<Item> DOUGH_BLOOD = itemTag("dough/blood");
        public static final TagKey<Item> RAW_BAT = itemTag("raw_bat");
        public static final TagKey<Item> COOKED_BAT = itemTag("cooked_bat");
        public static final TagKey<Item> STORAGE_BLOCKS_GARLIC = itemTag("storage_blocks/garlic");
        public static final TagKey<Item> VEGETABLES_GARLIC = itemTag("vegetables/garlic");
    }
}
