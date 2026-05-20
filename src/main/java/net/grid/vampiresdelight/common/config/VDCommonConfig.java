package net.grid.vampiresdelight.common.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class VDCommonConfig {

    public static final ForgeConfigSpec SPEC;

    public static final String CATEGORY_VILLAGE = "village";
    public static final ForgeConfigSpec.BooleanValue FARMERS_BUY_GARLIC;
    public static final ForgeConfigSpec.BooleanValue WANDERING_TRADER_SELLS_VAMPIRISM_ITEMS;

    public static final String CATEGORY_BLOCKS = "blocks";
    public static final ForgeConfigSpec.DoubleValue BLOODY_SOIL_BOOST_CHANCE;

    public static final String CATEGORY_ITEMS = "items";
    public static final ForgeConfigSpec.BooleanValue ALCHEMICAL_COCKTAIL_BURNS_GROUND;
    public static final ForgeConfigSpec.DoubleValue ALCHEMICAL_COCKTAIL_SPLASH_RADIUS;
    public static final ForgeConfigSpec.IntValue ALCHEMICAL_COCKTAIL_STACK_SIZE;

    public static final String CATEGORY_EFFECTS = "effects";
    public static final ForgeConfigSpec.BooleanValue REPLACE_WEIRD_JELLY_SUNSCREEN_WITH_JUMP_BOOST;
    public static final ForgeConfigSpec.BooleanValue BAT_MEAT_WITHERS_HUMANS;
    public static final ForgeConfigSpec.BooleanValue BLESSING_HELPS_AGAINST_GHOSTS;
    public static final ForgeConfigSpec.BooleanValue ARMOR_DISSOLVES_FULLY;

    public static final String CATEGORY_ENCHANTMENTS = "enchantments";
    public static final ForgeConfigSpec.BooleanValue BACKSTABBING_CAN_BE_APPLIED_TO_HUNTER_WEAPON;
    public static final ForgeConfigSpec.DoubleValue VAMPIRE_BITE_MAX_HEALING_VALUE;
    public static final ForgeConfigSpec.IntValue VAMPIRE_BITE_HEALING_CHANCE_1;
    public static final ForgeConfigSpec.IntValue VAMPIRE_BITE_HEALING_CHANCE_2;
    public static final ForgeConfigSpec.IntValue VAMPIRE_BITE_HEALING_CHANCE_3;
    public static final ForgeConfigSpec.BooleanValue ENABLE_VAMPIRE_BITE;

    public static final String CATEGORY_WORLD = "world";
    public static final ForgeConfigSpec.BooleanValue GENERATE_COOKING_POT_IN_HUNTER_CAMP;
    public static final ForgeConfigSpec.BooleanValue GENERATE_COOKING_POT_NEAR_HUNTER_CAMP;
    public static final ForgeConfigSpec.IntValue COOKING_POT_IN_HUNTER_CAMP_CHANCE;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.push(CATEGORY_VILLAGE);
        FARMERS_BUY_GARLIC = builder
                .comment("Should Farmers buy garlic? (May reduce chances of other trades appearing)")
                .define("farmersBuyGarlic", true);
        WANDERING_TRADER_SELLS_VAMPIRISM_ITEMS = builder
                .comment("Should the Wandering Trader sell some of vampirism's and this mod's items? (Including seeds and some blocks)")
                .define("wanderingTraderSellsVampirismItems", true);
        builder.pop();

        builder.push(CATEGORY_BLOCKS);
        BLOODY_SOIL_BOOST_CHANCE = builder
                .comment("How often (in percentage) should Bloody Soil succeed in boosting a plant's growth at each random tick? Set it to 0.0 to disable this.")
                .defineInRange("bloodySoilBoostChance", 0.2, 0.0, 1.0);
        builder.pop();

        builder.push(CATEGORY_ITEMS);
        ALCHEMICAL_COCKTAIL_BURNS_GROUND = builder
                .comment("Should the Alchemical Cocktail burn ground when thrown? Recommended to set this to \"false\" on servers with claims.")
                .define("alchemicalCocktailBurnsGround", true);
        ALCHEMICAL_COCKTAIL_SPLASH_RADIUS = builder
                .comment("What should be the radius of Alchemical Cocktail fire splash?")
                .defineInRange("alchemicalCocktailSplashRadius", 3.5, 1.0, 99.0);
        ALCHEMICAL_COCKTAIL_STACK_SIZE = builder
                .comment("What should be the maximum stack size of the Alchemical Cocktail?")
                .defineInRange("alchemicalCocktailStackSize", 8, 1, 99);
        builder.pop();

        builder.push(CATEGORY_EFFECTS);
        REPLACE_WEIRD_JELLY_SUNSCREEN_WITH_JUMP_BOOST = builder
                .comment("Should the Weird Jelly Sunscreen effect be replaced with Jump Boost?")
                .define("replaceWeirdJellySunscreenWithJumpBoost", false);
        BAT_MEAT_WITHERS_HUMANS = builder
                .comment("Should the bat meat and food made of it have chance of giving Wither effect when eaten by humans? Otherwise, it would poison the player and not wither.")
                .define("batMeatWithersHumans", true);
        BLESSING_HELPS_AGAINST_GHOSTS = builder
                .comment("Should the blessing effect banish ghosts just like phantoms?")
                .define("blessingHelpsAgainstGhosts", true);
        ARMOR_DISSOLVES_FULLY = builder
                .comment("Should \"weak\" armor such as leather and chain dissolve fully because of Clothes Dissolving effect?")
                .define("armorDissolvesFully", true);
        builder.pop();

        builder.push(CATEGORY_ENCHANTMENTS);
        BACKSTABBING_CAN_BE_APPLIED_TO_HUNTER_WEAPON = builder
                .comment("Should it be possible to apply Backstabbing enchantment to some hunter axes and stakes?")
                .define("backstabbingCanBeAppliedToHunterWeapon", true);
        VAMPIRE_BITE_MAX_HEALING_VALUE = builder
                .comment("What should be the maximum amount of hearts that Vampire Bite enchantment should heal?")
                .defineInRange("vampireBiteMaxHealingValue", 1.5, 0.5, 10.0);
        VAMPIRE_BITE_HEALING_CHANCE_1 = builder
                .comment("With what chance should Vampire Bite enchantment regenerate health?")
                .defineInRange("vampireBiteChanceLevel1", 25, 1, 100);
        VAMPIRE_BITE_HEALING_CHANCE_2 = builder
                .defineInRange("vampireBiteChanceLevel2", 30, 1, 100);
        VAMPIRE_BITE_HEALING_CHANCE_3 = builder
                .defineInRange("vampireBiteChanceLevel3", 35, 1, 100);
        ENABLE_VAMPIRE_BITE = builder
                .comment("Should Vampire Bite enchantment be enabled?")
                .comment("If disabled, it won't be removed from the game, but it'll be impossible to apply it to tools and it won't heal the player.")
                .define("enableVampireBite", true);
        builder.pop();

        builder.push(CATEGORY_WORLD);
        GENERATE_COOKING_POT_IN_HUNTER_CAMP = builder
                .comment("Should a Cooking Pot on a Campfire/Fire Place be generated in hunter camps?")
                .define("generateCookingPotInHunterCamp", true);
        GENERATE_COOKING_POT_NEAR_HUNTER_CAMP = builder
                .comment("Should a Cooking Pot be generated on the campfire near hunter camps? By default it's generated on the Campfire/Fire Place in the middle of it.")
                .define("generateCookingPotNearHunterCamp", false);
        COOKING_POT_IN_HUNTER_CAMP_CHANCE = builder
                .comment("With what chance should a Cooking Pot on a Campfire/Fire Place be generated in hunter camps?")
                .defineInRange("cookingPotInHunterCampSpawnChance", 40, 1, 100);
        builder.pop();

        SPEC = builder.build();
    }
}
