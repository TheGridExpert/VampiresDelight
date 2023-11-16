package net.grid.vampiresdelight.common.registry;

import net.grid.vampiresdelight.VampiresDelight;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.Stats;
import org.jetbrains.annotations.NotNull;

public class VDStats {
    public static final ResourceLocation gross_food_eaten = new ResourceLocation(VampiresDelight.MODID, "gross_food_eaten");

    public static void registerModStats() {
        register(gross_food_eaten);
    }

    private static void register(@NotNull ResourceLocation id) {
        Registry.register(Registry.CUSTOM_STAT, id, id);
        Stats.CUSTOM.get(id, StatFormatter.DEFAULT);
    }
}
