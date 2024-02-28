package net.grid.vampiresdelight.common.registry;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import net.grid.vampiresdelight.VampiresDelight;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class VDLootTables {
    private static final Set<ResourceLocation> LOOT_TABLES = Sets.newHashSet();

    public static final ResourceLocation CHEST_LOST_CARRIAGE = register("chests/lost_carriage");

    static @NotNull ResourceLocation register(@NotNull String resourceName) {
        return register(new ResourceLocation(VampiresDelight.MODID, resourceName));
    }

    static @NotNull ResourceLocation register(@NotNull ResourceLocation resourceLocation) {
        LOOT_TABLES.add(resourceLocation);
        return resourceLocation;
    }

    public static @NotNull Set<ResourceLocation> getLootTables() {
        return ImmutableSet.copyOf(LOOT_TABLES);
    }
}
