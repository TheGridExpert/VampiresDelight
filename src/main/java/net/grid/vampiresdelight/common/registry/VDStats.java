package net.grid.vampiresdelight.common.registry;

import net.grid.vampiresdelight.VampiresDelight;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.Stats;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class VDStats {
    private static final Map<ResourceLocation, StatFormatter> ALL_STATS = new HashMap<>();

    public static final ResourceLocation gross_food_eaten = add( "gross_food_eaten");

    public static void registerModStats() {
        ALL_STATS.forEach(VDStats::register);
    }

    private static ResourceLocation add(String name) {
        return add(name, StatFormatter.DEFAULT);
    }

    private static ResourceLocation add(String name, @SuppressWarnings("SameParameterValue") StatFormatter formatter) {
        return add(new ResourceLocation(VampiresDelight.MODID, name), formatter);
    }

    private static ResourceLocation add(ResourceLocation id) {
        return add(id, StatFormatter.DEFAULT);
    }

    private static ResourceLocation add(ResourceLocation id, @SuppressWarnings("SameParameterValue") StatFormatter formatter) {
        ALL_STATS.put(id, formatter);
        return id;
    }

    private static void register(@NotNull ResourceLocation id, StatFormatter formatter) {
        Registry.register(BuiltInRegistries.CUSTOM_STAT, id, id);
        Stats.CUSTOM.get(id, formatter);
    }
}
