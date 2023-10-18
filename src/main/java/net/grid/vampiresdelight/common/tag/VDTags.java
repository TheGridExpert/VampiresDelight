package net.grid.vampiresdelight.common.tag;

import net.grid.vampiresdelight.VampiresDelight;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class VDTags {
    // Items
    public static final TagKey<Item> VAMPIRE_FOOD = modItemTag("vampire_food");
    public static final TagKey<Item> HUNTER_FOOD = modItemTag("hunter_food");


    private static TagKey<Item> modItemTag(String path) {
        return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(VampiresDelight.MODID, path));
    }
}
