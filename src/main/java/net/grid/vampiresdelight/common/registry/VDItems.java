package net.grid.vampiresdelight.common.registry;

import de.teamlapen.vampirism.core.ModEffects;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.VDFoodValues;
import net.grid.vampiresdelight.common.item.*;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.FarmersDelight;
import vectorwing.farmersdelight.common.item.FuelBlockItem;
import vectorwing.farmersdelight.common.item.PopsicleItem;
import vectorwing.farmersdelight.common.item.RottenTomatoItem;
import vectorwing.farmersdelight.common.registry.ModBlocks;

public class VDItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, VampiresDelight.MODID);
    // Helper methods
    public static Item.Properties basicItem() {
        return new Item.Properties().tab(FarmersDelight.CREATIVE_TAB);
    }
    public static Item.Properties foodItem(FoodProperties food) {
        return new Item.Properties().food(food).tab(FarmersDelight.CREATIVE_TAB);
    }
    public static Item.Properties bowlFoodItem(FoodProperties food) {
        return new Item.Properties().food(food).craftRemainder(Items.BOWL).stacksTo(16).tab(FarmersDelight.CREATIVE_TAB);
    }
    public static Item.Properties drinkItem() {
        return new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16).tab(FarmersDelight.CREATIVE_TAB);
    }


    // The neutral items go first, then the vampire and hunter ones

    // Blocks
    public static final RegistryObject<Item> GARLIC_CRATE = ITEMS.register("garlic_crate",
            () -> new BlockItem(VDBlocks.GARLIC_CRATE.get(), basicItem()));
    public static final RegistryObject<Item> ORCHID_BAG = ITEMS.register("orchid_bag",
            () -> new BlockItem(VDBlocks.ORCHID_BAG.get(), basicItem()));
    public static final RegistryObject<Item> DARK_SPRUCE_CABINET = ITEMS.register("dark_spruce_cabinet",
            () -> new FuelBlockItem(VDBlocks.DARK_SPRUCE_CABINET.get(), basicItem(), 300));
    public static final RegistryObject<Item> CURSED_SPRUCE_CABINET = ITEMS.register("cursed_spruce_cabinet",
            () -> new FuelBlockItem(VDBlocks.CURSED_SPRUCE_CABINET.get(), basicItem(), 300));
    public static final RegistryObject<Item> WILD_GARLIC = ITEMS.register("wild_garlic",
            () -> new BlockItem(VDBlocks.WILD_GARLIC.get(), basicItem()));



    // Tools and Non-Food Stuff
    public static final RegistryObject<Item> ALCHEMICAL_COCKTAIL = ITEMS.register("alchemical_cocktail",
            () -> new AlchemicalCocktailItem(new Item.Properties().stacksTo(8).tab(FarmersDelight.CREATIVE_TAB)));

    // Crops
    public static final RegistryObject<Item> ORCHID_SEEDS = ITEMS.register("orchid_seeds",
            () -> new ItemNameBlockItem(VDBlocks.VAMPIRE_ORCHID_CROP.get(), basicItem()));

    // Foodstuffs
    public static final RegistryObject<Item> GRILLED_GARLIC = ITEMS.register("grilled_garlic",
            () -> new HunterConsumableItem(foodItem(VDFoodValues.GRILLED_GARLIC).tab(FarmersDelight.CREATIVE_TAB)));
    public static final RegistryObject<Item> ORCHID_TEA = ITEMS.register("orchid_tea",
            () -> new OrchidTeaItem(drinkItem()));
    public static final RegistryObject<Item> ORCHID_PETALS = ITEMS.register("orchid_petals", () -> new Item(basicItem()));
    public static final RegistryObject<Item> HEART_PIECES = ITEMS.register("heart_pieces",
            () -> new VampireConsumableItem(VDFoodValues.HEART_PIECES, VDFoodValues.NASTY));
    public static final RegistryObject<Item> HUMAN_EYE = ITEMS.register("human_eye",
            () -> new VampireConsumableItem(VDFoodValues.HUMAN_EYE, VDFoodValues.NASTY));
    public static final RegistryObject<Item> BLOOD_DOUGH = ITEMS.register("blood_dough",
            () -> new VampireConsumableItem(VDFoodValues.BLOOD_DOUGH, VDFoodValues.NASTY_BLOOD_DOUGH,
                    new MobEffectInstance(MobEffects.HUNGER, 300, 0), false));
    public static final RegistryObject<Item> BLOOD_BAGEL = ITEMS.register("blood_bagel",
            () -> new VampireConsumableItem(VDFoodValues.BLOOD_BAGEL, VDFoodValues.NASTY));

    // Sweets
    public static final RegistryObject<Item> PURE_SORBET = ITEMS.register("pure_sorbet",
            () -> new PopsicleItem(foodItem(VDFoodValues.PURE_SORBET)));
    public static final RegistryObject<Item> CURSED_CUPCAKE = ITEMS.register("cursed_cupcake",
            () -> new CursedCupcakeItem(VDFoodValues.CURSED_CUPCAKE, VDFoodValues.NASTY));
    public static final RegistryObject<Item> BLOOD_PIE = ITEMS.register("blood_pie",
            () -> new BlockItem(VDBlocks.BLOOD_PIE.get(), basicItem()));
    public static final RegistryObject<Item> BLOOD_PIE_SLICE = ITEMS.register("blood_pie_slice",
            () -> new VampireConsumableItem(VDFoodValues.BLOOD_PIE_SLICE, VDFoodValues.NASTY,
                    new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600, 0)));

    // Basic Meals
    public static final RegistryObject<Item> EYE_TOAST = ITEMS.register("eye_toast",
            () -> new VampireConsumableItem(VDFoodValues.EYE_TOAST, VDFoodValues.NASTY));
    public static final RegistryObject<Item> BAGEL_SANDWICH = ITEMS.register("bagel_sandwich",
            () -> new VampireConsumableItem(VDFoodValues.BAGEL_SANDWICH, VDFoodValues.NASTY));
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
            () -> new VampireConsumableItem(VDFoodValues.WEIRD_JELLY, VDFoodValues.NASTY, Items.BOWL,
                    new MobEffectInstance(ModEffects.SUNSCREEN.get(), 200, 1), true));
}