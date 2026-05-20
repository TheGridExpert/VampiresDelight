package net.grid.vampiresdelight.common.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class VDClientConfig {

    public static final ForgeConfigSpec SPEC;

    public static final String CATEGORY_TOOLTIPS = "tooltips";
    public static final ForgeConfigSpec.BooleanValue COLORED_TOOLTIPS;
    public static final ForgeConfigSpec.BooleanValue COLORED_TOOLTIPS_FOR_VAMPIRISM_ITEMS;
    public static final ForgeConfigSpec.BooleanValue FACTION_TOOLTIPS;
    public static final ForgeConfigSpec.BooleanValue HUNTER_TOOLTIPS_FOR_EVERYONE;
    public static final ForgeConfigSpec.BooleanValue SHOW_DISSOLVING_POTION_WARNING;

    public static final String CATEGORY_TOOLTIP_COLORS = "tooltip_colors";
    public static final ForgeConfigSpec.ConfigValue<String> VAMPIRE_FOOD_TOOLTIP_START_COLOR;
    public static final ForgeConfigSpec.ConfigValue<String> VAMPIRE_FOOD_TOOLTIP_END_COLOR;
    public static final ForgeConfigSpec.ConfigValue<String> HUNTER_FOOD_TOOLTIP_START_COLOR;
    public static final ForgeConfigSpec.ConfigValue<String> HUNTER_FOOD_TOOLTIP_END_COLOR;
    public static final ForgeConfigSpec.ConfigValue<String> WEREWOLF_FOOD_TOOLTIP_START_COLOR;
    public static final ForgeConfigSpec.ConfigValue<String> WEREWOLF_FOOD_TOOLTIP_END_COLOR;

    public static final String CATEGORY_APPLESKIN = "appleskin";
    public static final ForgeConfigSpec.BooleanValue CORRECT_APPLE_SKIN_TOOLTIPS;
    public static final ForgeConfigSpec.BooleanValue HIDE_APPLE_SKIN_HUMAN_FOOD_TOOLTIPS_FOR_VAMPIRES;
    public static final ForgeConfigSpec.BooleanValue HIDE_APPLE_SKIN_HUMAN_FOOD_TOOLTIPS_FOR_WEREWOLVES;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.push(CATEGORY_TOOLTIPS);
        COLORED_TOOLTIPS = builder
                .comment("Should the mod change the color of tooltips?")
                .define("coloredTooltips", true);
        COLORED_TOOLTIPS_FOR_VAMPIRISM_ITEMS = builder
                .comment("Should the mod change the color of tooltips of items from Vampirism (Garlic Bread, Human Heart, ect.)?")
                .define("coloredTooltipsForVampirismItems", true);
        FACTION_TOOLTIPS = builder
                .comment("Should the mod add tooltips that show which faction the item belongs to?")
                .define("factionTooltips", true);
        HUNTER_TOOLTIPS_FOR_EVERYONE = builder
                .comment("Should hunter food tooltips and tooltip's color be shown to all fractions? (Only shown to vampires by default)")
                .define("hunterTooltipsForEveryone", false);

        builder.push(CATEGORY_TOOLTIP_COLORS);
        VAMPIRE_FOOD_TOOLTIP_START_COLOR = builder
                .comment("What color (hex) should be used for vampire food tooltips as the start color? (The shade it starts with)")
                .define("vampireFoodTooltipStartColor", "#7c287c");
        VAMPIRE_FOOD_TOOLTIP_END_COLOR = builder
                .comment("What color (hex) should be used for vampire food tooltips as the end color? (The shade it ends with)")
                .define("vampireFoodTooltipEndColor", "#320046");
        HUNTER_FOOD_TOOLTIP_START_COLOR = builder
                .comment("What color (hex) should be used for hunter food tooltips as the start color? (The shade it starts with)")
                .define("hunterFoodTooltipStartColor", "#4141dc");
        HUNTER_FOOD_TOOLTIP_END_COLOR = builder
                .comment("What color (hex) should be used for hunter food tooltips as the end color? (The shade it ends with)")
                .define("hunterFoodTooltipEndColor", "#1e1e5a");
        WEREWOLF_FOOD_TOOLTIP_START_COLOR = builder
                .comment("What color (hex) should be used for werewolf food tooltips as the start color? (Werewolves mod only) (The shade it starts with)")
                .define("werewolfFoodTooltipStartColor", "#fa8500");
        WEREWOLF_FOOD_TOOLTIP_END_COLOR = builder
                .comment("What color (hex) should be used for werewolf food tooltips as the end color? (Werewolves mod only) (The shade it ends with)")
                .define("werewolfFoodTooltipEndColor", "#732c00");
        builder.pop();

        SHOW_DISSOLVING_POTION_WARNING = builder
                .comment("Should potions of dissolving have a tooltip showing they're not intended for survival? Can be disabled in case you want to use those in a modpack or a pata pack where they are obtainable.")
                .define("showDissolvingPotionWarning", true);

        builder.pop();

        builder.push(CATEGORY_APPLESKIN);
        CORRECT_APPLE_SKIN_TOOLTIPS = builder
                .comment("Should AppleSkin tooltips' food values be fixed depending on player's race? (In case the player is a vampire, it'll show blood values for vampire food)")
                .define("correctAppleSkinTooltips", true);
        HIDE_APPLE_SKIN_HUMAN_FOOD_TOOLTIPS_FOR_VAMPIRES = builder
                .comment("Should AppleSkin tooltips be hidden for human food if the player is a vampire?")
                .define("hideAppleSkinHumanFoodTooltipsForVampires", true);
        HIDE_APPLE_SKIN_HUMAN_FOOD_TOOLTIPS_FOR_WEREWOLVES = builder
                .comment("Should AppleSkin tooltips be hidden if the player is a werewolf for food they can't eat?")
                .define("hideAppleSkinHumanFoodTooltipsForWerewolves", true);

        builder.pop();
        SPEC = builder.build();
    }
}
