package net.grid.vampiresdelight.common.core;

import de.teamlapen.werewolves.util.WUtils;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.util.OptRegistryObject;
import net.grid.vampiresdelight.common.world.item.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.common.FoodValues;
import vectorwing.farmersdelight.common.item.ConsumableItem;

import static vectorwing.farmersdelight.common.registry.ModItems.*;
import static net.grid.vampiresdelight.common.util.VDIntegrationUtils.WEREWOLVES;

public class VDItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, VampiresDelight.MODID);

    public static final OptRegistryObject<SilverKnifeItem> SILVER_KNIFE = OptRegistryObject.register(ITEMS, WEREWOLVES, "silver_knife", () -> new SilverKnifeItem(WUtils.SILVER_ITEM_TIER, 0.5F, -2.0F, basicItem()));
    public static final RegistryObject<AlchemicalCocktailItem> ALCHEMICAL_COCKTAIL = ITEMS.register("alchemical_cocktail", () -> new AlchemicalCocktailItem(basicItem()));
    public static final RegistryObject<ItemNameBlockItem> ORCHID_SEEDS = ITEMS.register("orchid_seeds", () -> new ItemNameBlockItem(VDBlocks.VAMPIRE_ORCHID_CROP.get(), basicItem()));
    public static final RegistryObject<Item> ORCHID_PETALS = ITEMS.register("orchid_petals", () -> new Item(basicItem()));

    public static final RegistryObject<HunterConsumableItem> ROASTED_GARLIC = ITEMS.register("roasted_garlic", () -> new HunterConsumableItem(foodItem(VDFoodValues.ROASTED_GARLIC)).orientation(FoodOrientation.HUNTER));
    public static final RegistryObject<FactionDrinkableItem> DAISY_TEA = ITEMS.register("daisy_tea", () -> new FactionDrinkableItem(drinkItem()).feature(VDFoodFeatures.DAISY_TEA).customTooltip());
    public static final RegistryObject<VampireDrinkableItem> BLOOD_SYRUP = ITEMS.register("blood_syrup", () -> new VampireDrinkableItem(drinkItem().food(VDFoodValues.NASTY)).orientation(FoodOrientation.VAMPIRE).vampireFood(VDFoodValues.BLOOD_SYRUP));
    public static final RegistryObject<OrchidTeaItem> ORCHID_TEA = ITEMS.register("orchid_tea", () -> new OrchidTeaItem(drinkItem().food(VDFoodValues.ORCHID_TEA_HUMAN)).orientation(FoodOrientation.VAMPIRE).feature(VDFoodFeatures.ORCHID_TEA).vampireFood(VDFoodValues.ORCHID_TEA_VAMPIRE).hunterFood(VDFoodValues.ORCHID_TEA_IMMUNE));
    public static final RegistryObject<ConsumableItem> SUGARED_BERRIES = ITEMS.register("sugared_berries", () -> new ConsumableItem(foodItem(VDFoodValues.SUGARED_BERRIES)));
    public static final RegistryObject<VampireConsumableItem> HEART_PIECES = ITEMS.register("heart_pieces", () -> new VampireConsumableItem(foodItem(VDFoodValues.NASTY)).orientation(FoodOrientation.VAMPIRE).vampireFood(VDFoodValues.HEART_PIECES));
    public static final RegistryObject<VampireConsumableItem> HUMAN_EYE = ITEMS.register("human_eye", () -> new VampireConsumableItem(foodItem(VDFoodValues.NASTY)).orientation(FoodOrientation.VAMPIRE).vampireFood(VDFoodValues.HUMAN_EYE));
    public static final RegistryObject<FactionConsumableItem> HARDTACK = ITEMS.register("hardtack", () -> new FactionConsumableItem(foodItem(VDFoodValues.HARDTACK_HUMAN)).hunterFood(VDFoodValues.HARDTACK_HUNTER));
    public static final RegistryObject<Item> RICE_DOUGH = ITEMS.register("rice_dough", () -> new Item(foodItem(VDFoodValues.RICE_DOUGH)));
    public static final RegistryObject<Item> RICE_BREAD = ITEMS.register("rice_bread", () -> new Item(foodItem(VDFoodValues.RICE_BREAD)));
    public static final RegistryObject<VampireConsumableItem> BLOOD_DOUGH = ITEMS.register("blood_dough", () -> new VampireConsumableItem(foodItem(VDFoodValues.NASTY_BLOOD_DOUGH)).orientation(FoodOrientation.VAMPIRE).vampireFood(VDFoodValues.BLOOD_DOUGH).hideFoodEffectTooltip());
    public static final RegistryObject<VampireConsumableItem> BLOOD_BAGEL = ITEMS.register("blood_bagel", () -> new VampireConsumableItem(foodItem(VDFoodValues.NASTY)).orientation(FoodOrientation.VAMPIRE).vampireFood(VDFoodValues.BLOOD_BAGEL));
    public static final RegistryObject<FactionConsumableItem> RAW_BAT = ITEMS.register("raw_bat", () -> new FactionConsumableItem(foodItem(VDFoodValues.RAW_BAT)).orientation(FoodOrientation.VAMPIRE_TOLERANT).vampireFood(VDFoodValues.RAW_BAT).hideFoodEffectTooltip());
    public static final RegistryObject<FactionConsumableItem> RAW_BAT_CHOPS = ITEMS.register("raw_bat_chops", () -> new FactionConsumableItem(foodItem(VDFoodValues.RAW_BAT_CHOPS)).orientation(FoodOrientation.VAMPIRE_TOLERANT).vampireFood(VDFoodValues.RAW_BAT_CHOPS).hideFoodEffectTooltip());
    public static final RegistryObject<FactionConsumableItem> GRILLED_BAT = ITEMS.register("grilled_bat", () -> new FactionConsumableItem(foodItem(VDFoodValues.GRILLED_BAT_HUMAN)).orientation(FoodOrientation.VAMPIRE_TOLERANT).vampireFood(VDFoodValues.GRILLED_BAT_VAMPIRE).hideFoodEffectTooltip());
    public static final RegistryObject<FactionConsumableItem> GRILLED_BAT_CHOPS = ITEMS.register("grilled_bat_chops", () -> new FactionConsumableItem(foodItem(VDFoodValues.GRILLED_BAT_CHOPS_HUMAN)).orientation(FoodOrientation.VAMPIRE_TOLERANT).vampireFood(VDFoodValues.GRILLED_BAT_CHOPS_VAMPIRE).hideFoodEffectTooltip());
    public static final RegistryObject<VampireConsumableItem> ORCHID_CAKE_SLICE = ITEMS.register("orchid_cake_slice", () -> new VampireConsumableItem(foodItem(VDFoodValues.NASTY_BLINDNESS)).orientation(FoodOrientation.VAMPIRE).vampireFood(VDFoodValues.ORCHID_CAKE_SLICE));
    public static final RegistryObject<VampireConsumableItem> BLOOD_PIE_SLICE = ITEMS.register("blood_pie_slice", () -> new VampireConsumableItem(foodItem(VDFoodValues.NASTY)).orientation(FoodOrientation.VAMPIRE).vampireFood(VDFoodValues.BLOOD_PIE_SLICE));

    public static final RegistryObject<FactionDrinkableItem> DANDELION_BEER_MUG = ITEMS.register("dandelion_beer_mug", () -> new FactionDrinkableItem(drinkItem().food(VDFoodValues.DANDELION_BEER_MUG)));
    public static final RegistryObject<PourableBottleItem> DANDELION_BEER_BOTTLE = ITEMS.register("dandelion_beer_bottle", () -> new PourableBottleItem(basicItem(), VDBlocks.DANDELION_BEER_BOTTLE_PLACED.get(), DANDELION_BEER_MUG.get(), Items.GLASS_BOTTLE, 3));
    public static final RegistryObject<VampireDrinkableItem> BLOOD_WINE_GLASS = ITEMS.register("blood_wine_glass", () -> new VampireDrinkableItem(drinkItem().food(VDFoodValues.BLOOD_WINE_GLASS_HUMAN)).orientation(FoodOrientation.VAMPIRE).vampireFood(VDFoodValues.BLOOD_WINE_GLASS_VAMPIRE));
    public static final RegistryObject<PourableBottleItem> BLOOD_WINE_BOTTLE = ITEMS.register("blood_wine_bottle", () -> new PourableBottleItem(basicItem(), VDBlocks.BLOOD_WINE_BOTTLE_PLACED.get(), BLOOD_WINE_GLASS.get(), Items.GLASS_BOTTLE, 3));
    public static final RegistryObject<VampireDrinkableItem> MULLED_WINE_GLASS = ITEMS.register("mulled_wine_glass", () -> new VampireDrinkableItem(drinkItem().food(VDFoodValues.MULLED_WINE_GLASS_HUMAN)).orientation(FoodOrientation.VAMPIRE).vampireFood(VDFoodValues.MULLED_WINE_GLASS_VAMPIRE));

    public static final RegistryObject<FactionConsumableItem> PURE_SORBET = ITEMS.register("pure_sorbet", () -> new FactionConsumableItem(foodItem(VDFoodValues.PURE_SORBET)).orientation(FoodOrientation.UNIVERSAL).vampireFood(VDFoodValues.PURE_SORBET).feature(VDFoodFeatures.ICE_CREAM));
    public static final RegistryObject<VampireConsumableItem> ORCHID_COOKIE = ITEMS.register("orchid_cookie", () -> new VampireConsumableItem(foodItem(VDFoodValues.NASTY_BLINDNESS)).orientation(FoodOrientation.VAMPIRE).vampireFood(VDFoodValues.ORCHID_COOKIE));
    public static final RegistryObject<VampireConsumableItem> ORCHID_ECLAIR = ITEMS.register("orchid_eclair", () -> new VampireConsumableItem(foodItem(VDFoodValues.NASTY_BLINDNESS)).orientation(FoodOrientation.VAMPIRE).vampireFood(VDFoodValues.ORCHID_ECLAIR));
    public static final RegistryObject<VampireConsumableItem> ORCHID_ICE_CREAM = ITEMS.register("orchid_ice_cream", () -> new VampireConsumableItem(bowlFoodItem(VDFoodValues.NASTY_BLINDNESS)).orientation(FoodOrientation.VAMPIRE).vampireFood(VDFoodValues.ORCHID_ICE_CREAM).feature(VDFoodFeatures.ICE_CREAM));
    public static final RegistryObject<VampireConsumableItem> TRICOLOR_DANGO = ITEMS.register("tricolor_dango", () -> new VampireConsumableItem(foodItem(VDFoodValues.NASTY)).orientation(FoodOrientation.VAMPIRE).vampireFood(VDFoodValues.TRICOLOR_DANGO));
    public static final RegistryObject<VampireConsumableItem> CURSED_CUPCAKE = ITEMS.register("cursed_cupcake", () -> new VampireConsumableItem(foodItem(VDFoodValues.NASTY)).orientation(FoodOrientation.VAMPIRE).vampireFood(VDFoodValues.CURSED_CUPCAKE).feature(VDFoodFeatures.CURSED_CUPCAKE).customTooltip());
    public static final RegistryObject<VampireConsumableItem> DARK_ICE_CREAM = ITEMS.register("dark_ice_cream", () -> new VampireConsumableItem(foodItem(VDFoodValues.NASTY_DARKNESS)).orientation(FoodOrientation.VAMPIRE).vampireFood(VDFoodValues.DARK_ICE_CREAM).feature(VDFoodFeatures.ICE_CREAM));
    public static final RegistryObject<HunterConsumableItem> SNOW_WHITE_ICE_CREAM = ITEMS.register("snow_white_ice_cream", () -> new HunterConsumableItem(foodItem(VDFoodValues.SNOW_WHITE_ICE_CREAM)).feature(VDFoodFeatures.ICE_CREAM));
    public static final OptRegistryObject<WerewolfConsumableItem> WOLF_BERRY_COOKIE = OptRegistryObject.register(ITEMS, WEREWOLVES, "wolf_berry_cookie", () -> new WerewolfConsumableItem(foodItem(VDFoodValues.NASTY_POISON)).orientation(FoodOrientation.WEREWOLF).werewolfFood(FoodValues.COOKIES));
    public static final OptRegistryObject<WerewolfConsumableItem> WOLF_BERRY_ICE_CREAM = OptRegistryObject.register(ITEMS, WEREWOLVES, "wolf_berry_ice_cream", () -> new WerewolfConsumableItem(bowlFoodItem(VDFoodValues.NASTY_POISON)).orientation(FoodOrientation.WEREWOLF).werewolfFood(VDFoodValues.WOLF_BERRY_ICE_CREAM).feature(VDFoodFeatures.ICE_CREAM));

    public static final RegistryObject<HunterConsumableItem> FISH_BURGER = ITEMS.register("fish_burger", () -> new HunterConsumableItem(foodItem(VDFoodValues.FISH_BURGER)).orientation(FoodOrientation.HUNTER).containsGarlic());
    public static final RegistryObject<VampireConsumableItem> BLOOD_SAUSAGE = ITEMS.register("blood_sausage", () -> new VampireConsumableItem(foodItem(VDFoodValues.NASTY)).orientation(FoodOrientation.VAMPIRE).vampireFood(VDFoodValues.BLOOD_SAUSAGE));
    public static final RegistryObject<VampireConsumableItem> BLOOD_HOT_DOG = ITEMS.register("blood_hot_dog", () -> new VampireConsumableItem(foodItem(VDFoodValues.NASTY)).orientation(FoodOrientation.VAMPIRE).vampireFood(VDFoodValues.BLOOD_HOT_DOG));
    public static final RegistryObject<VampireConsumableItem> EYES_ON_STICK = ITEMS.register("eyes_on_stick", () -> new VampireConsumableItem(foodItem(VDFoodValues.NASTY)).orientation(FoodOrientation.VAMPIRE).vampireFood(VDFoodValues.EYES_ON_STICK));
    public static final RegistryObject<VampireConsumableItem> EYE_CROISSANT = ITEMS.register("eye_croissant", () -> new VampireConsumableItem(foodItem(VDFoodValues.NASTY)).orientation(FoodOrientation.VAMPIRE).vampireFood(VDFoodValues.EYE_CROISSANT));
    public static final RegistryObject<VampireConsumableItem> BAGEL_SANDWICH = ITEMS.register("bagel_sandwich", () -> new VampireConsumableItem(foodItem(VDFoodValues.NASTY)).orientation(FoodOrientation.VAMPIRE).vampireFood(VDFoodValues.BAGEL_SANDWICH));
    public static final RegistryObject<FactionConsumableItem> BAT_TACO = ITEMS.register("bat_taco", () -> new FactionConsumableItem(foodItem(VDFoodValues.BAT_TACO_HUMAN)).orientation(FoodOrientation.VAMPIRE_TOLERANT).vampireFood(VDFoodValues.BAT_TACO));

    public static final RegistryObject<VampireConsumableItem> ORCHID_CREAM_SOUP = ITEMS.register("orchid_cream_soup", () -> new VampireConsumableItem(bowlFoodItem(VDFoodValues.NASTY_BLINDNESS)).orientation(FoodOrientation.VAMPIRE).vampireFood(VDFoodValues.ORCHID_CREAM_SOUP));
    public static final RegistryObject<VampireConsumableItem> BLACK_MUSHROOM_SOUP = ITEMS.register("black_mushroom_soup", () -> new VampireConsumableItem(bowlFoodItem(VDFoodValues.NASTY_DARKNESS)).orientation(FoodOrientation.VAMPIRE).vampireFood(VDFoodValues.BLACK_MUSHROOM_SOUP));
    public static final RegistryObject<HunterConsumableItem> GARLIC_SOUP = ITEMS.register("garlic_soup", () -> new HunterConsumableItem(bowlFoodItem(VDFoodValues.GARLIC_SOUP)).orientation(FoodOrientation.HUNTER).containsGarlic());
    public static final RegistryObject<HunterConsumableItem> BORSCHT = ITEMS.register("borscht", () -> new HunterConsumableItem(bowlFoodItem(VDFoodValues.BORSCHT)).orientation(FoodOrientation.HUNTER).containsGarlic());

    public static final RegistryObject<VampireConsumableItem> ORCHID_CURRY = ITEMS.register("orchid_curry", () -> new VampireConsumableItem(bowlFoodItem(VDFoodValues.NASTY_BLINDNESS)).orientation(FoodOrientation.VAMPIRE).vampireFood(VDFoodValues.ORCHID_CURRY));
    public static final RegistryObject<VampireConsumableItem> BLACK_MUSHROOM_NOODLES = ITEMS.register("black_mushroom_noodles", () -> new VampireConsumableItem(bowlFoodItem(VDFoodValues.NASTY_DARKNESS)).orientation(FoodOrientation.VAMPIRE).vampireFood(VDFoodValues.BLACK_MUSHROOM_NOODLES));

    public static final RegistryObject<VampireConsumableItem> WEIRD_JELLY = ITEMS.register("weird_jelly", () -> new VampireConsumableItem(bowlFoodItem(VDFoodValues.NASTY)).orientation(FoodOrientation.VAMPIRE).vampireFood(VDFoodValues.WEIRD_JELLY));

    public static final RegistryObject<BlockItem> DARK_STONE_STOVE = registerBlockItem(VDBlocks.DARK_STONE_STOVE);
    public static final RegistryObject<BlockItem> GARLIC_CRATE = registerBlockItem(VDBlocks.GARLIC_CRATE);
    public static final RegistryObject<BlockItem> ORCHID_BAG = registerBlockItem(VDBlocks.ORCHID_BAG);
    public static final RegistryObject<BlockItem> DARK_SPRUCE_CABINET = registerBlockItem(VDBlocks.DARK_SPRUCE_CABINET);
    public static final RegistryObject<BlockItem> CURSED_SPRUCE_CABINET = registerBlockItem(VDBlocks.CURSED_SPRUCE_CABINET);
    public static final OptRegistryObject<BlockItem> JACARANDA_CABINET = registerBlockItem(VDBlocks.JACARANDA_CABINET);
    public static final OptRegistryObject<BlockItem> MAGIC_CABINET = registerBlockItem(VDBlocks.MAGIC_CABINET);
    public static final RegistryObject<BlockItem> OAK_WINE_SHELF = registerBlockItem(VDBlocks.OAK_WINE_SHELF);
    public static final RegistryObject<BlockItem> SPRUCE_WINE_SHELF = registerBlockItem(VDBlocks.SPRUCE_WINE_SHELF);
    public static final RegistryObject<BlockItem> BIRCH_WINE_SHELF = registerBlockItem(VDBlocks.BIRCH_WINE_SHELF);
    public static final RegistryObject<BlockItem> JUNGLE_WINE_SHELF = registerBlockItem(VDBlocks.JUNGLE_WINE_SHELF);
    public static final RegistryObject<BlockItem> ACACIA_WINE_SHELF = registerBlockItem(VDBlocks.ACACIA_WINE_SHELF);
    public static final RegistryObject<BlockItem> DARK_OAK_WINE_SHELF = registerBlockItem(VDBlocks.DARK_OAK_WINE_SHELF);
    public static final RegistryObject<BlockItem> MANGROVE_WINE_SHELF = registerBlockItem(VDBlocks.MANGROVE_WINE_SHELF);
    public static final RegistryObject<BlockItem> CHERRY_WINE_SHELF = registerBlockItem(VDBlocks.CHERRY_WINE_SHELF);
    public static final RegistryObject<BlockItem> BAMBOO_WINE_SHELF = registerBlockItem(VDBlocks.BAMBOO_WINE_SHELF);
    public static final RegistryObject<BlockItem> CRIMSON_WINE_SHELF = registerBlockItem(VDBlocks.CRIMSON_WINE_SHELF);
    public static final RegistryObject<BlockItem> WARPED_WINE_SHELF = registerBlockItem(VDBlocks.WARPED_WINE_SHELF);
    public static final RegistryObject<BlockItem> DARK_SPRUCE_WINE_SHELF = registerBlockItem(VDBlocks.DARK_SPRUCE_WINE_SHELF);
    public static final RegistryObject<BlockItem> CURSED_SPRUCE_WINE_SHELF = registerBlockItem(VDBlocks.CURSED_SPRUCE_WINE_SHELF);
    public static final OptRegistryObject<BlockItem> JACARANDA_WINE_SHELF = registerBlockItem(VDBlocks.JACARANDA_WINE_SHELF);
    public static final OptRegistryObject<BlockItem> MAGIC_WINE_SHELF = registerBlockItem(VDBlocks.MAGIC_WINE_SHELF);
    public static final RegistryObject<BlockItem> WHITE_BAR_STOOL = registerBlockItem(VDBlocks.WHITE_BAR_STOOL);
    public static final RegistryObject<BlockItem> ORANGE_BAR_STOOL = registerBlockItem(VDBlocks.ORANGE_BAR_STOOL);
    public static final RegistryObject<BlockItem> MAGENTA_BAR_STOOL = registerBlockItem(VDBlocks.MAGENTA_BAR_STOOL);
    public static final RegistryObject<BlockItem> LIGHT_BLUE_BAR_STOOL = registerBlockItem(VDBlocks.LIGHT_BLUE_BAR_STOOL);
    public static final RegistryObject<BlockItem> YELLOW_BAR_STOOL = registerBlockItem(VDBlocks.YELLOW_BAR_STOOL);
    public static final RegistryObject<BlockItem> LIME_BAR_STOOL = registerBlockItem(VDBlocks.LIME_BAR_STOOL);
    public static final RegistryObject<BlockItem> PINK_BAR_STOOL = registerBlockItem(VDBlocks.PINK_BAR_STOOL);
    public static final RegistryObject<BlockItem> GRAY_BAR_STOOL = registerBlockItem(VDBlocks.GRAY_BAR_STOOL);
    public static final RegistryObject<BlockItem> LIGHT_GRAY_BAR_STOOL = registerBlockItem(VDBlocks.LIGHT_GRAY_BAR_STOOL);
    public static final RegistryObject<BlockItem> CYAN_BAR_STOOL = registerBlockItem(VDBlocks.CYAN_BAR_STOOL);
    public static final RegistryObject<BlockItem> PURPLE_BAR_STOOL = registerBlockItem(VDBlocks.PURPLE_BAR_STOOL);
    public static final RegistryObject<BlockItem> BLUE_BAR_STOOL = registerBlockItem(VDBlocks.BLUE_BAR_STOOL);
    public static final RegistryObject<BlockItem> BROWN_BAR_STOOL = registerBlockItem(VDBlocks.BROWN_BAR_STOOL);
    public static final RegistryObject<BlockItem> GREEN_BAR_STOOL = registerBlockItem(VDBlocks.GREEN_BAR_STOOL);
    public static final RegistryObject<BlockItem> RED_BAR_STOOL = registerBlockItem(VDBlocks.RED_BAR_STOOL);
    public static final RegistryObject<BlockItem> BLACK_BAR_STOOL = registerBlockItem(VDBlocks.BLACK_BAR_STOOL);
    public static final RegistryObject<BlockItem> SPIRIT_LANTERN = registerBlockItem(VDBlocks.SPIRIT_LANTERN);
    public static final RegistryObject<BlockItem> CURSED_FARMLAND = registerBlockItem(VDBlocks.CURSED_FARMLAND);
    public static final RegistryObject<BlockItem> BLOODY_SOIL = registerBlockItem(VDBlocks.BLOODY_SOIL);
    public static final RegistryObject<BlockItem> BLOODY_SOIL_FARMLAND = registerBlockItem(VDBlocks.BLOODY_SOIL_FARMLAND);
    public static final RegistryObject<BlockItem> BLACK_MUSHROOM_BLOCK = registerBlockItem(VDBlocks.BLACK_MUSHROOM_BLOCK);
    public static final RegistryObject<BlockItem> BLACK_MUSHROOM_STEM = registerBlockItem(VDBlocks.BLACK_MUSHROOM_STEM);
    public static final RegistryObject<BlockItem> BLACK_MUSHROOM = registerBlockItem(VDBlocks.BLACK_MUSHROOM);
    public static final RegistryObject<BlockItem> ORCHID_CAKE = registerBlockItem(VDBlocks.ORCHID_CAKE);
    public static final RegistryObject<BlockItem> BLOOD_PIE = registerBlockItem(VDBlocks.BLOOD_PIE);
    public static final RegistryObject<BlockItem> WILD_GARLIC = registerBlockItem(VDBlocks.WILD_GARLIC);
    public static final RegistryObject<BlockItem> WEIRD_JELLY_BLOCK = registerBlockItem(VDBlocks.WEIRD_JELLY_BLOCK);

    public static <T extends Block> RegistryObject<BlockItem> registerBlockItem(RegistryObject<T> block) {
        return registerBlockItem(block, basicItem());
    }

    public static <T extends Block> OptRegistryObject<BlockItem> registerBlockItem(OptRegistryObject<T> block) {
        return registerBlockItem(block, basicItem());
    }

    @SuppressWarnings("ConstantConditions")
    public static <T extends Block> RegistryObject<BlockItem> registerBlockItem(RegistryObject<T> block, Item.Properties properties) {
        return ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), properties));
    }

    public static <T extends Block> OptRegistryObject<BlockItem> registerBlockItem(OptRegistryObject<T> block, Item.Properties properties) {
        return block.id().map(id -> OptRegistryObject.register(ITEMS, block.modId(), id.getPath(), () -> new BlockItem(block.getOrThrow(), properties))).orElseGet(() -> OptRegistryObject.empty(block.modId()));
    }

    public static void registerCompostables() {
        addCompostable(ORCHID_SEEDS, 0.3F);
        addCompostable(ORCHID_PETALS, 0.3F);
        addCompostable(WILD_GARLIC, 0.65F);
        addCompostable(BLACK_MUSHROOM, 0.65F);
        addCompostable(ROASTED_GARLIC, 0.85F);
        addCompostable(SUGARED_BERRIES, 0.85F);
        addCompostable(RICE_BREAD, 0.85F);
        addCompostable(BLOOD_BAGEL, 0.85F);
        addCompostable(ORCHID_CAKE_SLICE, 0.85F);
        addCompostable(ORCHID_COOKIE, 0.85F);
        addCompostable(WOLF_BERRY_COOKIE, 0.85F);
        addCompostable(BLACK_MUSHROOM_BLOCK, 0.85F);
        addCompostable(BLACK_MUSHROOM_STEM, 0.85F);
        addCompostable(HARDTACK, 1.0F);
        addCompostable(ORCHID_CAKE, 1.0F);
    }

    public static void addCompostable(RegistryObject<? extends Item> item, float chance) {
        ComposterBlock.COMPOSTABLES.put(item.get(), chance);
    }

    public static void addCompostable(OptRegistryObject<? extends Item> optItem, float chance) {
        optItem.ifPresent(item -> ComposterBlock.COMPOSTABLES.put(item, chance));
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}