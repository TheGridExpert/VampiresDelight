package net.grid.vampiresdelight.common;

import net.minecraftforge.common.ForgeConfigSpec;

public class Configuration {
    public static ForgeConfigSpec COMMON_CONFIG;
    public static ForgeConfigSpec CLIENT_CONFIG;

    //COMMON
    public static final String CATEGORY_WORLD = "world";
    public static ForgeConfigSpec.IntValue CHANCE_WILD_GARLIC;

    // CLIENT
    public static final String CATEGORY_CLIENT = "client";
    public static ForgeConfigSpec.BooleanValue COLORED_TOOLTIPS;
    public static ForgeConfigSpec.BooleanValue SPECIAL_APPLE_SKIN_TOOLTIP;


    static {
        // COMMON
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

        COMMON_BUILDER.comment("World generation").push(CATEGORY_WORLD);

        COMMON_BUILDER.comment("Wild Garlic generation").push("wild_garlic");
        CHANCE_WILD_GARLIC = COMMON_BUILDER.comment("Chance of generating clusters. Smaller value = more frequent.")
                .defineInRange("chance", 200, 0, Integer.MAX_VALUE);

        COMMON_BUILDER.pop();
        COMMON_CONFIG = COMMON_BUILDER.build();


        // CLIENT
        ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

        CLIENT_BUILDER.comment("Client settings").push(CATEGORY_CLIENT);

        COLORED_TOOLTIPS = CLIENT_BUILDER.comment("Should the mod change the color of tooltips?")
                .define("coloredTooltips", true);
        SPECIAL_APPLE_SKIN_TOOLTIP = CLIENT_BUILDER.comment("Hides AppleSkin food tooltips if you're a Vampire. You'll be shown blood values instead")
                .define("specialAppleSkinTooltip", true);

        CLIENT_BUILDER.pop();
        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }
}
