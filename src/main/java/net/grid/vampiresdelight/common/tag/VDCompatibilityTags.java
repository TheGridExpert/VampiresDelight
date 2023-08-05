package net.grid.vampiresdelight.common.tag;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class VDCompatibilityTags {
    // Create
    public static final String CREATE = "create";
    public static final TagKey<Item> CREATE_UPRIGHT_ON_BELT = externalItemTag(CREATE, "upright_on_belt");

    // Serene Seasons
    public static final String SERENE_SEASONS = "sereneseasons";
    public static final TagKey<Block> SERENE_SEASONS_AUTUMN_CROPS_BLOCK = externalBlockTag(SERENE_SEASONS, "autumn_crops");
    public static final TagKey<Block> SERENE_SEASONS_SUMMER_CROPS_BLOCK = externalBlockTag(SERENE_SEASONS, "summer_crops");
    public static final TagKey<Item> SERENE_SEASONS_AUTUMN_CROPS = externalItemTag(SERENE_SEASONS, "autumn_crops");
    public static final TagKey<Item> SERENE_SEASONS_SUMMER_CROPS = externalItemTag(SERENE_SEASONS, "summer_crops");




    private static TagKey<Item> externalItemTag(String modId, String path) {
        return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(modId, path));
    }

    private static TagKey<Block> externalBlockTag(String modId, String path) {
        return TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(modId, path));
    }
}
