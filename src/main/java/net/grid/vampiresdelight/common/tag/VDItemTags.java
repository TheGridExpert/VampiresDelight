package net.grid.vampiresdelight.common.tag;

import net.grid.vampiresdelight.VampiresDelight;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class VDItemTags {

    // Food items that vampires should be able to consume. Empty by default, but can be used by other mods.
    public static final TagKey<Item> BLOOD_FOOD = tag("blood_food");

    // Bottle items that can be placed on Wine Shelves.
    public static final TagKey<Item> WINE_SHELF_BOTTLES = tag("wine_shelf_bottles");
    public static final TagKey<Item> BEER_BOTTLES = tag("beer_bottles");
    public static final TagKey<Item> WINE_BOTTLES = tag("wine_bottles");

    public static final TagKey<Item> BLOOD_SYRUP_INGREDIENTS = tag("blood_syrup_ingredients");

    private static TagKey<Item> tag(String name) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, name));
    }
}
