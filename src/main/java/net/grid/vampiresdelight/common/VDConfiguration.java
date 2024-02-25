package net.grid.vampiresdelight.common;

import net.minecraftforge.common.ForgeConfigSpec;

public class VDConfiguration {
    public static ForgeConfigSpec COMMON_CONFIG;
    public static ForgeConfigSpec CLIENT_CONFIG;

    //COMMON
    public static final String CATEGORY_SETTINGS = "settings";
    public static ForgeConfigSpec.BooleanValue FARMERS_BUY_GARLIC;
    public static ForgeConfigSpec.BooleanValue WANDERING_TRADER_SELLS_VAMPIRISM_ITEMS;

    public static final String CATEGORY_RECIPE_BOOK = "recipe_book";
    public static ForgeConfigSpec.BooleanValue ENABLE_RECIPE_BOOK_BREWING_BARREL;

    // CLIENT
    public static final String CATEGORY_CLIENT = "client";
    public static ForgeConfigSpec.BooleanValue COLORED_TOOLTIPS;
    public static ForgeConfigSpec.BooleanValue HUNTER_TOOLTIPS_FOR_EVERYONE;
    public static ForgeConfigSpec.BooleanValue SPECIAL_APPLE_SKIN_TOOLTIP;


    static {
        // COMMON
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

        COMMON_BUILDER.comment("Game settings").push(CATEGORY_SETTINGS);
        FARMERS_BUY_GARLIC = COMMON_BUILDER.comment("Should Farmers buy garlic? (May reduce chances of other trades appearing)")
                .define("farmersBuyGarlic", true);
        WANDERING_TRADER_SELLS_VAMPIRISM_ITEMS = COMMON_BUILDER.comment("Should the Wandering Trader sell some of vampirism's and this mod's items? (Including seeds and some blocks)")
                .define("wanderingTraderSellsVampirismItems", true);

        COMMON_BUILDER.comment("Recipe book").push(CATEGORY_RECIPE_BOOK);
        ENABLE_RECIPE_BOOK_BREWING_BARREL = COMMON_BUILDER.comment("Should the Brewing Barrel have a Recipe Book available on its interface?")
                .define("enableRecipeBookBrewingBarrel", true);

        COMMON_BUILDER.pop();
        COMMON_CONFIG = COMMON_BUILDER.build();


        // CLIENT
        ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

        CLIENT_BUILDER.comment("Client settings").push(CATEGORY_CLIENT);

        COLORED_TOOLTIPS = CLIENT_BUILDER.comment("Should the mod change the color of tooltips?")
                .define("coloredTooltips", true);
        HUNTER_TOOLTIPS_FOR_EVERYONE = CLIENT_BUILDER.comment("Hunter food tooltips and color be shown for all fractions? (Only shown to vampires by default)")
                .define("hunterTooltipsForEveryone", false);
        SPECIAL_APPLE_SKIN_TOOLTIP = CLIENT_BUILDER.comment("Hides AppleSkin food tooltips if you're a Vampire. You'll be shown blood values instead")
                .define("specialAppleSkinTooltip", true);

        CLIENT_BUILDER.pop();
        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }
}
