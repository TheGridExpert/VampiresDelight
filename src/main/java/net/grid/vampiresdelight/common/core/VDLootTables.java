package net.grid.vampiresdelight.common.core;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import net.grid.vampiresdelight.VampiresDelight;
import net.minecraft.resources.ResourceLocation;

import java.util.Set;

public class VDLootTables {
    private static final Set<ResourceLocation> LOOT_TABLES = Sets.newHashSet();

    public static final ResourceLocation CHEST_COOKING_SPOT = register("chests/cooking_spot");

    public static final ResourceLocation VD_CHEST_VAMPIRE_DUNGEON = register("chests/vd_vampire_dungeon");
    public static final ResourceLocation VD_CHEST_VAMPIRE_HUT = register("chests/vd_vampire_hut");
    public static final ResourceLocation VD_CHEST_ALTAR = register("chests/vd_altar");
    public static final ResourceLocation VD_CHEST_CRYPT = register("chests/vd_crypt");
    public static final ResourceLocation VD_CHEST_HUNTER_OUTPOST_TENT = register("chests/vd_hunter_outpost_tent");
    public static final ResourceLocation VD_CHEST_HUNTER_OUTPOST_TOWER_FOOD = register("chests/vd_hunter_outpost_tower_food");
    public static final ResourceLocation VD_CHEST_HUNTER_OUTPOST_TOWER_SPECIAL = register("chests/vd_hunter_outpost_tower_special");

    private static ResourceLocation register(String resourceName) {
        return register(ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, resourceName));
    }

    private static ResourceLocation register(ResourceLocation resourceLocation) {
        LOOT_TABLES.add(resourceLocation);
        return resourceLocation;
    }

    public static Set<ResourceLocation> getLootTables() {
        return ImmutableSet.copyOf(LOOT_TABLES);
    }
}