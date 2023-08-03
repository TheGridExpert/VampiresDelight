package net.grid.vampiresdelight.common;

import net.minecraftforge.common.ForgeConfigSpec;

public class Configuration {
    public static ForgeConfigSpec COMMON_CONFIG;

    //COMMON
    public static final String CATEGORY_WORLD = "world";
    public static ForgeConfigSpec.IntValue CHANCE_WILD_GARLIC;

    static {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

        COMMON_BUILDER.comment("World generation").push(CATEGORY_WORLD);

        COMMON_BUILDER.comment("Wild Garlic generation").push("wild_garlic");
        CHANCE_WILD_GARLIC = COMMON_BUILDER.comment("Chance of generating clusters. Smaller value = more frequent.")
                .defineInRange("chance", 200, 0, Integer.MAX_VALUE);
        COMMON_BUILDER.pop();

        COMMON_CONFIG = COMMON_BUILDER.build();
    }
}
