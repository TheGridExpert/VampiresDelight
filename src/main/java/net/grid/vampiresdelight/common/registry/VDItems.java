package net.grid.vampiresdelight.common.registry;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.VDFoodValues;
import net.grid.vampiresdelight.common.item.*;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.common.item.ConsumableItem;
import vectorwing.farmersdelight.common.item.FuelBlockItem;

public class VDItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, VampiresDelight.MODID);
    // Helper methods
    public static Item.Properties basicItem() {
        return new Item.Properties();
    }
    public static Item.Properties foodItem(FoodProperties food) {
        return new Item.Properties().food(food);
    }
    public static Item.Properties bowlFoodItem(FoodProperties food) {
        return new Item.Properties().food(food).craftRemainder(Items.BOWL).stacksTo(16);
    }
    public static Item.Properties drinkItem() {
        return new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16);
    }
    public static Item.Properties drinkItem(FoodProperties food) {
        return new Item.Properties().food(food).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16);
    }


    // The neutral items go first, then the vampire and hunter ones

    // Blocks
    public static final RegistryObject<Item> DARK_STONE_STOVE = ITEMS.register("dark_stone_stove",
            () -> new BlockItem(VDBlocks.DARK_STONE_STOVE.get(), basicItem()));
    public static final RegistryObject<Item> BREWING_BARREL = ITEMS.register("brewing_barrel",
            () -> new BlockItem(VDBlocks.BREWING_BARREL.get(), basicItem()));
    public static final RegistryObject<Item> GARLIC_CRATE = ITEMS.register("garlic_crate",
            () -> new BlockItem(VDBlocks.GARLIC_CRATE.get(), basicItem()));
    public static final RegistryObject<Item> ORCHID_BAG = ITEMS.register("orchid_bag",
            () -> new BlockItem(VDBlocks.ORCHID_BAG.get(), basicItem()));
    public static final RegistryObject<Item> SPIRIT_LANTERN = ITEMS.register("spirit_lantern",
            () -> new BlockItem(VDBlocks.SPIRIT_LANTERN.get(), basicItem()));
    public static final RegistryObject<Item> DARK_SPRUCE_CABINET = ITEMS.register("dark_spruce_cabinet",
            () -> new FuelBlockItem(VDBlocks.DARK_SPRUCE_CABINET.get(), basicItem(), 300));
    public static final RegistryObject<Item> CURSED_SPRUCE_CABINET = ITEMS.register("cursed_spruce_cabinet",
            () -> new FuelBlockItem(VDBlocks.CURSED_SPRUCE_CABINET.get(), basicItem(), 300));
    public static final RegistryObject<Item> OAK_WINE_SHELF = ITEMS.register("oak_wine_shelf",
            () -> new FuelBlockItem(VDBlocks.OAK_WINE_SHELF.get(), basicItem(), 300));
    public static final RegistryObject<Item> SPRUCE_WINE_SHELF = ITEMS.register("spruce_wine_shelf",
            () -> new FuelBlockItem(VDBlocks.SPRUCE_WINE_SHELF.get(), basicItem(), 300));
    public static final RegistryObject<Item> BIRCH_WINE_SHELF = ITEMS.register("birch_wine_shelf",
            () -> new FuelBlockItem(VDBlocks.BIRCH_WINE_SHELF.get(), basicItem(), 300));
    public static final RegistryObject<Item> JUNGLE_WINE_SHELF = ITEMS.register("jungle_wine_shelf",
            () -> new FuelBlockItem(VDBlocks.JUNGLE_WINE_SHELF.get(), basicItem(), 300));
    public static final RegistryObject<Item> ACACIA_WINE_SHELF = ITEMS.register("acacia_wine_shelf",
            () -> new FuelBlockItem(VDBlocks.ACACIA_WINE_SHELF.get(), basicItem(), 300));
    public static final RegistryObject<Item> DARK_OAK_WINE_SHELF = ITEMS.register("dark_oak_wine_shelf",
            () -> new FuelBlockItem(VDBlocks.DARK_OAK_WINE_SHELF.get(), basicItem(), 300));
    public static final RegistryObject<Item> MANGROVE_WINE_SHELF = ITEMS.register("mangrove_wine_shelf",
            () -> new FuelBlockItem(VDBlocks.MANGROVE_WINE_SHELF.get(), basicItem(), 300));
    public static final RegistryObject<Item> CHERRY_WINE_SHELF = ITEMS.register("cherry_wine_shelf",
            () -> new FuelBlockItem(VDBlocks.CHERRY_WINE_SHELF.get(), basicItem(), 300));
    public static final RegistryObject<Item> BAMBOO_WINE_SHELF = ITEMS.register("bamboo_wine_shelf",
            () -> new FuelBlockItem(VDBlocks.BAMBOO_WINE_SHELF.get(), basicItem(), 300));
    public static final RegistryObject<Item> CRIMSON_WINE_SHELF = ITEMS.register("crimson_wine_shelf",
            () -> new BlockItem(VDBlocks.CRIMSON_WINE_SHELF.get(), basicItem()));
    public static final RegistryObject<Item> WARPED_WINE_SHELF = ITEMS.register("warped_wine_shelf",
            () -> new BlockItem(VDBlocks.WARPED_WINE_SHELF.get(), basicItem()));
    public static final RegistryObject<Item> CURSED_SPRUCE_WINE_SHELF = ITEMS.register("cursed_spruce_wine_shelf",
            () -> new FuelBlockItem(VDBlocks.CURSED_SPRUCE_WINE_SHELF.get(), basicItem(), 300));
    public static final RegistryObject<Item> DARK_SPRUCE_WINE_SHELF = ITEMS.register("dark_spruce_wine_shelf",
            () -> new FuelBlockItem(VDBlocks.DARK_SPRUCE_WINE_SHELF.get(), basicItem(), 300));
    public static final RegistryObject<Item> WILD_GARLIC = ITEMS.register("wild_garlic",
            () -> new BlockItem(VDBlocks.WILD_GARLIC.get(), basicItem()));



    // Tools/Non-food
    public static final RegistryObject<Item> ALCHEMICAL_COCKTAIL = ITEMS.register("alchemical_cocktail",
            () -> new AlchemicalCocktailItem(basicItem().stacksTo(8)));

    // Farming
    public static final RegistryObject<Item> CURSED_FARMLAND = ITEMS.register("cursed_farmland",
            () -> new BlockItem(VDBlocks.CURSED_FARMLAND.get(), new Item.Properties()));
    public static final RegistryObject<Item> ORCHID_SEEDS = ITEMS.register("orchid_seeds",
            () -> new ItemNameBlockItem(VDBlocks.VAMPIRE_ORCHID_CROP.get(), basicItem()));

    // Foodstuffs
    public static final RegistryObject<Item> GRILLED_GARLIC = ITEMS.register("grilled_garlic",
            () -> new HunterConsumableItem(foodItem(VDFoodValues.GRILLED_GARLIC)));
    public static final RegistryObject<Item> BLOOD_SYRUP = ITEMS.register("blood_syrup",
            () -> new VampireDrinkableItem(drinkItem(VDFoodValues.NASTY), VDFoodValues.BLOOD_SYRUP));
    public static final RegistryObject<Item> ORCHID_TEA = ITEMS.register("orchid_tea",
            () -> new OrchidTeaItem(drinkItem(), VDFoodValues.ORCHID_TEA_VAMPIRE, VDFoodValues.ORCHID_TEA_HUNTER));
    public static final RegistryObject<Item> ORCHID_PETALS = ITEMS.register("orchid_petals",
            () -> new Item(basicItem()));
    public static final RegistryObject<Item> SUGARED_BERRIES = ITEMS.register("sugared_berries",
            () -> new ConsumableItem(foodItem(VDFoodValues.SUGARED_BERRIES)));
    public static final RegistryObject<Item> HEART_PIECES = ITEMS.register("heart_pieces",
            () -> new VampireConsumableItem(foodItem(VDFoodValues.NASTY), VDFoodValues.HEART_PIECES));
    public static final RegistryObject<Item> HUMAN_EYE = ITEMS.register("human_eye",
            () -> new VampireConsumableItem(foodItem(VDFoodValues.NASTY), VDFoodValues.HUMAN_EYE));
    public static final RegistryObject<Item> RICE_DOUGH = ITEMS.register("rice_dough",
            () -> new Item(foodItem(VDFoodValues.RICE_DOUGH)));
    public static final RegistryObject<Item> RICE_BREAD = ITEMS.register("rice_bread",
            () -> new Item(foodItem(VDFoodValues.RICE_BREAD)));
    public static final RegistryObject<Item> BLOOD_DOUGH = ITEMS.register("blood_dough",
            () -> new VampireConsumableItem(foodItem(VDFoodValues.NASTY_BLOOD_DOUGH), VDFoodValues.BLOOD_DOUGH, false));
    public static final RegistryObject<Item> BLOOD_BAGEL = ITEMS.register("blood_bagel",
            () -> new VampireConsumableItem(foodItem(VDFoodValues.NASTY), VDFoodValues.BLOOD_BAGEL));
    public static final RegistryObject<Item> RAW_BAT = ITEMS.register("raw_bat",
            () -> new VampireConsumableItem(foodItem(VDFoodValues.RAW_BAT), VDFoodValues.RAW_BAT));
    public static final RegistryObject<Item> RAW_BAT_CHOPS = ITEMS.register("raw_bat_chops",
            () -> new VampireConsumableItem(foodItem(VDFoodValues.RAW_BAT_CHOPS), VDFoodValues.RAW_BAT_CHOPS));
    public static final RegistryObject<Item> COOKED_BAT = ITEMS.register("cooked_bat",
            () -> new VampireConsumableItem(foodItem(VDFoodValues.GRILLED_BAT_HUMAN), VDFoodValues.GRILLED_BAT_VAMPIRE));
    public static final RegistryObject<Item> COOKED_BAT_CHOPS = ITEMS.register("cooked_bat_chops",
            () -> new VampireConsumableItem(foodItem(VDFoodValues.GRILLED_BAT_CHOPS_HUMAN), VDFoodValues.GRILLED_BAT_CHOPS_VAMPIRE));
    public static final RegistryObject<Item> BLOOD_WINE_BOTTLE = ITEMS.register("blood_wine_bottle",
            () -> new BloodWineBottleItem(basicItem()));
    public static final RegistryObject<Item> WINE_GLASS = ITEMS.register("wine_glass",
            () -> new VampireDrinkableItem(drinkItem(VDFoodValues.WINE_GLASS_HUMAN), VDFoodValues.WINE_GLASS_VAMPIRE));
    public static final RegistryObject<Item> MULLED_WINE_GLASS = ITEMS.register("mulled_wine_glass",
            () -> new VampireDrinkableItem(drinkItem(VDFoodValues.MULLED_WINE_GLASS_HUMAN), VDFoodValues.MULLED_WINE_GLASS_VAMPIRE));
    public static final RegistryObject<Item> BLOOD_PIE = ITEMS.register("blood_pie",
            () -> new BlockItem(VDBlocks.BLOOD_PIE.get(), basicItem()));
    public static final RegistryObject<Item> BLOOD_PIE_SLICE = ITEMS.register("blood_pie_slice",
            () -> new VampireConsumableItem(foodItem(VDFoodValues.NASTY), VDFoodValues.BLOOD_PIE_SLICE));

    // Sweets
    public static final RegistryObject<Item> PURE_SORBET = ITEMS.register("pure_sorbet",
            () -> new PureSorbetItem(foodItem(VDFoodValues.PURE_SORBET)));
    public static final RegistryObject<Item> ORCHID_COOKIE = ITEMS.register("orchid_cookie",
            () -> new VampireConsumableItem(foodItem(VDFoodValues.NASTY_BLINDNESS), VDFoodValues.ORCHID_COOKIE));
    public static final RegistryObject<Item> ORCHID_ECLAIR = ITEMS.register("orchid_eclair",
            () -> new VampireConsumableItem(foodItem(VDFoodValues.NASTY_BLINDNESS), VDFoodValues.ORCHID_ECLAIR));
    public static final RegistryObject<Item> ORCHID_ICE_CREAM = ITEMS.register("orchid_ice_cream",
            () -> new VampireConsumableItem(bowlFoodItem(VDFoodValues.NASTY_BLINDNESS), VDFoodValues.ORCHID_ICE_CREAM));
    public static final RegistryObject<Item> TRICOLOR_DANGO = ITEMS.register("tricolor_dango",
            () -> new VampireConsumableItem(foodItem(VDFoodValues.NASTY), VDFoodValues.TRICOLOR_DANGO));
    public static final RegistryObject<Item> CURSED_CUPCAKE = ITEMS.register("cursed_cupcake",
            () -> new CursedCupcakeItem(foodItem(VDFoodValues.NASTY), VDFoodValues.CURSED_CUPCAKE));
    public static final RegistryObject<Item> DARK_ICE_CREAM = ITEMS.register("dark_ice_cream",
            () -> new VampireConsumableItem(foodItem(VDFoodValues.NASTY_DARKNESS), VDFoodValues.DARK_ICE_CREAM));
    public static final RegistryObject<Item> ORCHID_CAKE = ITEMS.register("orchid_cake",
            () -> new BlockItem(VDBlocks.ORCHID_CAKE.get(), basicItem()));
    public static final RegistryObject<Item> ORCHID_CAKE_SLICE = ITEMS.register("orchid_cake_slice",
            () -> new VampireConsumableItem(foodItem(VDFoodValues.NASTY_BLINDNESS), VDFoodValues.ORCHID_CAKE_SLICE));
    public static final RegistryObject<Item> SNOW_WHITE_ICE_CREAM = ITEMS.register("snow_white_ice_cream",
            () -> new HunterConsumableItem(foodItem(VDFoodValues.SNOW_WHITE_ICE_CREAM)));

    // Basic Meals
    public static final RegistryObject<Item> FISH_BURGER = ITEMS.register("fish_burger",
            () -> new HunterConsumableItem(foodItem(VDFoodValues.FISH_BURGER)));
    public static final RegistryObject<Item> BLOOD_SAUSAGE = ITEMS.register("blood_sausage",
            () -> new VampireConsumableItem(foodItem(VDFoodValues.NASTY), VDFoodValues.BLOOD_SAUSAGE));
    public static final RegistryObject<Item> BLOOD_HOT_DOG = ITEMS.register("blood_hot_dog",
            () -> new VampireConsumableItem(foodItem(VDFoodValues.NASTY), VDFoodValues.BLOOD_HOT_DOG));
    public static final RegistryObject<Item> EYES_ON_STICK = ITEMS.register("eyes_on_stick",
            () -> new VampireConsumableItem(foodItem(VDFoodValues.NASTY), VDFoodValues.EYES_ON_STICK));
    public static final RegistryObject<Item> EYE_CROISSANT = ITEMS.register("eye_croissant",
            () -> new VampireConsumableItem(foodItem(VDFoodValues.NASTY), VDFoodValues.EYE_CROISSANT));
    public static final RegistryObject<Item> BAGEL_SANDWICH = ITEMS.register("bagel_sandwich",
            () -> new VampireConsumableItem(foodItem(VDFoodValues.NASTY), VDFoodValues.BAGEL_SANDWICH));
    public static final RegistryObject<Item> HARDTACK = ITEMS.register("hardtack",
            () -> new HardtackItem(bowlFoodItem(VDFoodValues.HARDTACK)));

    // Soups and Stews
    public static final RegistryObject<Item> GARLIC_SOUP = ITEMS.register("garlic_soup",
            () -> new HunterConsumableItem(bowlFoodItem(VDFoodValues.GARLIC_SOUP), true));
    public static final RegistryObject<Item> BORSCHT = ITEMS.register("borscht",
            () -> new HunterConsumableItem(bowlFoodItem(VDFoodValues.BORSCHT), true));

    // Feasts
    public static final RegistryObject<Item> WEIRD_JELLY_BLOCK = ITEMS.register("weird_jelly_block",
            () -> new BlockItem(VDBlocks.WEIRD_JELLY_BLOCK.get(), basicItem().stacksTo(1)));
    public static final RegistryObject<Item> WEIRD_JELLY = ITEMS.register("weird_jelly",
            () -> new VampireConsumableItem(bowlFoodItem(VDFoodValues.NASTY), VDFoodValues.WEIRD_JELLY, true));
}