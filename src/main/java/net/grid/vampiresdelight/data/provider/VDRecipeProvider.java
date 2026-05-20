package net.grid.vampiresdelight.data.provider;

import de.teamlapen.vampirism.core.ModBlocks;
import de.teamlapen.vampirism.core.ModItems;
import de.teamlapen.vampirism.core.ModTags;
import de.teamlapen.vampirism.data.recipebuilder.AlchemyTableRecipeBuilder;
import de.teamlapen.vampirism.data.recipebuilder.ShapedWeaponTableRecipeBuilder;
import de.teamlapen.vampirism.data.recipebuilder.ShapelessWeaponTableRecipeBuilder;
import de.teamlapen.vampirism.util.NBTIngredient;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.core.VDBlocks;
import net.grid.vampiresdelight.common.core.VDItems;
import net.grid.vampiresdelight.common.core.VDOils;
import net.grid.vampiresdelight.common.core.VDPotions;
import net.grid.vampiresdelight.common.tag.VDCommonTags;
import net.grid.vampiresdelight.common.tag.VDItemTags;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.common.crafting.StrictNBTIngredient;
import net.minecraftforge.registries.ForgeRegistries;
import vectorwing.farmersdelight.client.recipebook.CookingPotRecipeBookTab;
import vectorwing.farmersdelight.common.crafting.ingredient.ToolActionIngredient;
import vectorwing.farmersdelight.data.builder.CookingPotRecipeBuilder;
import vectorwing.farmersdelight.data.builder.CuttingBoardRecipeBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class VDRecipeProvider extends RecipeProvider {

    public static final int FAST_COOKING = 100;      // 5 seconds
    public static final int NORMAL_COOKING = 200;    // 10 seconds
    public static final int SLOW_FERMENTING = 1200;  // 60 seconds

    public static final float SMALL_EXP = 0.35F;
    public static final float MEDIUM_EXP = 1.0F;
    public static final float LARGE_EXP = 2.0F;

    private static final TagKey<Item> IRON_INGOT = Tags.Items.INGOTS_IRON;
    private static final TagKey<Item> GOLD_NUGGET = Tags.Items.NUGGETS_GOLD;
    private static final TagKey<Item> SILVER_INGOT = de.teamlapen.werewolves.core.ModTags.Items.SILVER_INGOT;
    private static final TagKey<Item> SHEARS = Tags.Items.SHEARS;
    private static final TagKey<Item> WOODEN_FENCES = ItemTags.WOODEN_FENCES;
    private static final TagKey<Item> MILK = VDCommonTags.Items.MILK;
    private static final TagKey<Item> BREAD = VDCommonTags.Items.BREAD;
    private static final TagKey<Item> RICE_BREAD = VDCommonTags.Items.BREAD_RICE;
    private static final TagKey<Item> RICE_DOUGH = VDCommonTags.Items.DOUGH_RICE;
    private static final TagKey<Item> PASTA = VDCommonTags.Items.PASTA;
    private static final TagKey<Item> GRAIN = VDCommonTags.Items.GRAIN;
    private static final TagKey<Item> GRAIN_WHEAT = VDCommonTags.Items.GRAIN_WHEAT;
    private static final TagKey<Item> CROPS_RICE = VDCommonTags.Items.CROPS_RICE;
    private static final TagKey<Item> CROPS_TOMATO = VDCommonTags.Items.CROPS_TOMATO;
    private static final TagKey<Item> EGGS = VDCommonTags.Items.EGGS;
    private static final TagKey<Item> SALAD_INGREDIENTS = VDCommonTags.Items.SALAD_INGREDIENTS;
    private static final TagKey<Item> VEGETABLES = VDCommonTags.Items.VEGETABLES;
    private static final TagKey<Item> VEGETABLES_ONION = VDCommonTags.Items.VEGETABLES_ONION;
    private static final TagKey<Item> VEGETABLES_BEETROOT = VDCommonTags.Items.VEGETABLES_BEETROOT;
    private static final TagKey<Item> VEGETABLES_POTATO = VDCommonTags.Items.VEGETABLES_POTATO;
    private static final TagKey<Item> VEGETABLES_CARROT = VDCommonTags.Items.VEGETABLES_CARROT;
    private static final TagKey<Item> VEGETABLES_GARLIC = VDCommonTags.Items.VEGETABLES_GARLIC;
    private static final TagKey<Item> RAW_CHICKEN = VDCommonTags.Items.RAW_CHICKEN;
    private static final TagKey<Item> RAW_BEEF = VDCommonTags.Items.RAW_BEEF;
    private static final TagKey<Item> RAW_PORK = VDCommonTags.Items.RAW_PORK;
    private static final TagKey<Item> RAW_MUTTON = VDCommonTags.Items.RAW_MUTTON;
    private static final TagKey<Item> COOKED_BEEF = VDCommonTags.Items.COOKED_BEEF;
    private static final TagKey<Item> COOKED_CHICKEN = VDCommonTags.Items.COOKED_CHICKEN;
    private static final TagKey<Item> COOKED_MUTTON = VDCommonTags.Items.COOKED_MUTTON;
    private static final TagKey<Item> COOKED_PORK = VDCommonTags.Items.COOKED_PORK;
    private static final TagKey<Item> COOKED_BACON = VDCommonTags.Items.COOKED_BACON;
    private static final TagKey<Item> COOKED_FISHES = VDCommonTags.Items.COOKED_FISHES;
    private static final TagKey<Item> GRILLED_BAT = VDCommonTags.Items.COOKED_BAT;

    private static final TagKey<Item> PURE_BLOOD = ModTags.Items.PURE_BLOOD;
    private static final TagKey<Item> HOLY_WATER = ModTags.Items.HOLY_WATER;

    private static final Ingredient KNIVES = Ingredient.of(VDCommonTags.Items.TOOLS_KNIVES);
    private static final Ingredient SHEARS_TOOL = Ingredient.of(SHEARS);
    private static final Ingredient PICKAXES = new ToolActionIngredient(ToolActions.PICKAXE_DIG);
    private static final Ingredient AXES_DIG = new ToolActionIngredient(ToolActions.AXE_DIG);
    private static final Ingredient AXES_STRIP = new ToolActionIngredient(ToolActions.AXE_STRIP);

    public static final List<Item> DYES = List.of(
            Items.BLACK_DYE, Items.BLUE_DYE, Items.BROWN_DYE, Items.CYAN_DYE,
            Items.GRAY_DYE, Items.GREEN_DYE, Items.LIGHT_BLUE_DYE, Items.LIGHT_GRAY_DYE,
            Items.LIME_DYE, Items.MAGENTA_DYE, Items.ORANGE_DYE, Items.PINK_DYE,
            Items.PURPLE_DYE, Items.RED_DYE, Items.YELLOW_DYE, Items.WHITE_DYE);

    public VDRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        // Crafting Table
        recipesBlocks(consumer);
        recipesTools(consumer);
        recipesMaterials(consumer);
        recipesFoodstuffs(consumer);
        recipesPouring(consumer);
        recipesFoodBlocks(consumer);
        recipesCraftedMeals(consumer);

        // Cooking Pot
        cookMiscellaneous(consumer);
        cookMeals(consumer);
        fermentingAlternatives(consumer);

        // Cutting Board
        cuttingAnimalItems(consumer);
        cuttingFoods(consumer);
        cuttingFlowers(consumer);
        salvagingMinerals(consumer);
        strippingWood(consumer);
        salvagingWoodenFurniture(consumer);
        salvagingUsingShears(consumer);

        // Furnace / Smoker / Campfire
        smeltingRecipes(consumer);

        // Vampirism
        recipesWeaponTable(consumer);
        recipesAlchemyTable(consumer);
    }

    private static void recipesBlocks(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, VDBlocks.DARK_STONE_STOVE.get())
                .pattern("III")
                .pattern("D D")
                .pattern("DCD")
                .define('I', IRON_INGOT)
                .define('D', ModBlocks.DARK_STONE_BRICKS.get())
                .define('C', Blocks.CAMPFIRE)
                .unlockedBy(hasBlock(ModBlocks.DARK_STONE_BRICKS.get()), has(ModBlocks.DARK_STONE_BRICKS.get()))
                .unlockedBy(hasBlock(Blocks.CAMPFIRE), has(Blocks.CAMPFIRE))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, VDBlocks.DARK_SPRUCE_CABINET.get())
                .pattern("___")
                .pattern("D D")
                .pattern("___")
                .define('_', ModBlocks.DARK_SPRUCE_SLAB.get())
                .define('D', ModBlocks.DARK_SPRUCE_TRAPDOOR.get())
                .unlockedBy(hasBlock(ModBlocks.DARK_SPRUCE_TRAPDOOR.get()), has(ModBlocks.DARK_SPRUCE_TRAPDOOR.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, VDBlocks.CURSED_SPRUCE_CABINET.get())
                .pattern("___")
                .pattern("D D")
                .pattern("___")
                .define('_', ModBlocks.CURSED_SPRUCE_SLAB.get())
                .define('D', ModBlocks.CURSED_SPRUCE_TRAPDOOR.get())
                .unlockedBy(hasBlock(ModBlocks.CURSED_SPRUCE_TRAPDOOR.get()), has(ModBlocks.CURSED_SPRUCE_TRAPDOOR.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, VDBlocks.JACARANDA_CABINET.getOrThrow())
                .pattern("___")
                .pattern("D D")
                .pattern("___")
                .define('_', de.teamlapen.werewolves.core.ModBlocks.JACARANDA_SLAB.get())
                .define('D', de.teamlapen.werewolves.core.ModBlocks.JACARANDA_TRAPDOOR.get())
                .unlockedBy(hasBlock(de.teamlapen.werewolves.core.ModBlocks.JACARANDA_TRAPDOOR.get()), has(de.teamlapen.werewolves.core.ModBlocks.JACARANDA_TRAPDOOR.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, VDBlocks.MAGIC_CABINET.getOrThrow())
                .pattern("___")
                .pattern("D D")
                .pattern("___")
                .define('_', de.teamlapen.werewolves.core.ModBlocks.MAGIC_SLAB.get())
                .define('D', de.teamlapen.werewolves.core.ModBlocks.MAGIC_TRAPDOOR.get())
                .unlockedBy(hasBlock(de.teamlapen.werewolves.core.ModBlocks.MAGIC_TRAPDOOR.get()), has(de.teamlapen.werewolves.core.ModBlocks.MAGIC_TRAPDOOR.get()))
                .save(consumer);

        wineShelfRecipe(VDBlocks.OAK_WINE_SHELF.get(), Blocks.OAK_SLAB, Blocks.OAK_PLANKS, consumer);
        wineShelfRecipe(VDBlocks.SPRUCE_WINE_SHELF.get(), Blocks.SPRUCE_SLAB, Blocks.SPRUCE_PLANKS, consumer);
        wineShelfRecipe(VDBlocks.BIRCH_WINE_SHELF.get(), Blocks.BIRCH_SLAB, Blocks.BIRCH_PLANKS, consumer);
        wineShelfRecipe(VDBlocks.JUNGLE_WINE_SHELF.get(), Blocks.JUNGLE_SLAB, Blocks.JUNGLE_PLANKS, consumer);
        wineShelfRecipe(VDBlocks.ACACIA_WINE_SHELF.get(), Blocks.ACACIA_SLAB, Blocks.ACACIA_PLANKS, consumer);
        wineShelfRecipe(VDBlocks.DARK_OAK_WINE_SHELF.get(), Blocks.DARK_OAK_SLAB, Blocks.DARK_OAK_PLANKS, consumer);
        wineShelfRecipe(VDBlocks.MANGROVE_WINE_SHELF.get(), Blocks.MANGROVE_SLAB, Blocks.MANGROVE_PLANKS, consumer);
        wineShelfRecipe(VDBlocks.CHERRY_WINE_SHELF.get(), Blocks.CHERRY_SLAB, Blocks.CHERRY_PLANKS, consumer);
        wineShelfRecipe(VDBlocks.BAMBOO_WINE_SHELF.get(), Blocks.BAMBOO_SLAB, Blocks.BAMBOO_PLANKS, consumer);
        wineShelfRecipe(VDBlocks.CRIMSON_WINE_SHELF.get(), Blocks.CRIMSON_SLAB, Blocks.CRIMSON_PLANKS, consumer);
        wineShelfRecipe(VDBlocks.WARPED_WINE_SHELF.get(), Blocks.WARPED_SLAB, Blocks.WARPED_PLANKS, consumer);
        wineShelfRecipe(VDBlocks.CURSED_SPRUCE_WINE_SHELF.get(), ModBlocks.CURSED_SPRUCE_SLAB.get(), ModBlocks.CURSED_SPRUCE_PLANKS.get(), consumer);
        wineShelfRecipe(VDBlocks.DARK_SPRUCE_WINE_SHELF.get(), ModBlocks.DARK_SPRUCE_SLAB.get(), ModBlocks.DARK_SPRUCE_PLANKS.get(), consumer);
        wineShelfRecipe(VDBlocks.JACARANDA_WINE_SHELF.getOrThrow(), de.teamlapen.werewolves.core.ModBlocks.JACARANDA_SLAB.get(), de.teamlapen.werewolves.core.ModBlocks.JACARANDA_PLANKS.get(), consumer);
        wineShelfRecipe(VDBlocks.MAGIC_WINE_SHELF.getOrThrow(), de.teamlapen.werewolves.core.ModBlocks.MAGIC_SLAB.get(), de.teamlapen.werewolves.core.ModBlocks.MAGIC_PLANKS.get(), consumer);

        barStoolRecipe(VDBlocks.WHITE_BAR_STOOL.get(), Blocks.WHITE_WOOL, consumer);
        barStoolRecipe(VDBlocks.ORANGE_BAR_STOOL.get(), Blocks.ORANGE_WOOL, consumer);
        barStoolRecipe(VDBlocks.MAGENTA_BAR_STOOL.get(), Blocks.MAGENTA_WOOL, consumer);
        barStoolRecipe(VDBlocks.LIGHT_BLUE_BAR_STOOL.get(), Blocks.LIGHT_BLUE_WOOL, consumer);
        barStoolRecipe(VDBlocks.YELLOW_BAR_STOOL.get(), Blocks.YELLOW_WOOL, consumer);
        barStoolRecipe(VDBlocks.LIME_BAR_STOOL.get(), Blocks.LIME_WOOL, consumer);
        barStoolRecipe(VDBlocks.PINK_BAR_STOOL.get(), Blocks.PINK_WOOL, consumer);
        barStoolRecipe(VDBlocks.GRAY_BAR_STOOL.get(), Blocks.GRAY_WOOL, consumer);
        barStoolRecipe(VDBlocks.LIGHT_GRAY_BAR_STOOL.get(), Blocks.LIGHT_GRAY_WOOL, consumer);
        barStoolRecipe(VDBlocks.CYAN_BAR_STOOL.get(), Blocks.CYAN_WOOL, consumer);
        barStoolRecipe(VDBlocks.PURPLE_BAR_STOOL.get(), Blocks.PURPLE_WOOL, consumer);
        barStoolRecipe(VDBlocks.BLUE_BAR_STOOL.get(), Blocks.BLUE_WOOL, consumer);
        barStoolRecipe(VDBlocks.BROWN_BAR_STOOL.get(), Blocks.BROWN_WOOL, consumer);
        barStoolRecipe(VDBlocks.GREEN_BAR_STOOL.get(), Blocks.GREEN_WOOL, consumer);
        barStoolRecipe(VDBlocks.RED_BAR_STOOL.get(), Blocks.RED_WOOL, consumer);
        barStoolRecipe(VDBlocks.BLACK_BAR_STOOL.get(), Blocks.BLACK_WOOL, consumer);

        colorBlockWithDye(List.of(
                VDItems.BLACK_BAR_STOOL.get(), VDItems.BLUE_BAR_STOOL.get(), VDItems.BROWN_BAR_STOOL.get(), VDItems.CYAN_BAR_STOOL.get(),
                VDItems.GRAY_BAR_STOOL.get(), VDItems.GREEN_BAR_STOOL.get(), VDItems.LIGHT_BLUE_BAR_STOOL.get(), VDItems.LIGHT_GRAY_BAR_STOOL.get(),
                VDItems.LIME_BAR_STOOL.get(), VDItems.MAGENTA_BAR_STOOL.get(), VDItems.ORANGE_BAR_STOOL.get(), VDItems.PINK_BAR_STOOL.get(),
                VDItems.PURPLE_BAR_STOOL.get(), VDItems.RED_BAR_STOOL.get(), VDItems.YELLOW_BAR_STOOL.get(), VDItems.WHITE_BAR_STOOL.get()
        ), consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, VDItems.GARLIC_CRATE.get(), 1)
                .pattern("GGG")
                .pattern("GGG")
                .pattern("GGG")
                .define('G', ModItems.ITEM_GARLIC.get())
                .unlockedBy("has_garlic", has(ModItems.ITEM_GARLIC.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, VDItems.ORCHID_BAG.get(), 1)
                .pattern("OOO")
                .pattern("OOO")
                .pattern("OOO")
                .define('O', VDItems.ORCHID_PETALS.get())
                .unlockedBy(hasItem(VDItems.ORCHID_PETALS.get()), has(VDItems.ORCHID_PETALS.get()))
                .save(consumer);
    }

    private static void recipesTools(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, VDItems.SILVER_KNIFE.getOrThrow())
                .pattern("M")
                .pattern("S")
                .define('M', SILVER_INGOT)
                .define('S', Items.STICK)
                .unlockedBy("has_silver_ingot", has(SILVER_INGOT))
                .save(consumer);
    }

    private static void recipesMaterials(Consumer<FinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModItems.ITEM_GARLIC.get(), 9)
                .requires(VDItems.GARLIC_CRATE.get())
                .unlockedBy(hasItem(VDItems.GARLIC_CRATE.get()), has(VDItems.GARLIC_CRATE.get()))
                .save(consumer, ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "garlic_from_crate"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.ORCHID_PETALS.get(), 9)
                .requires(VDItems.ORCHID_BAG.get())
                .unlockedBy(hasItem(VDItems.ORCHID_BAG.get()), has(VDItems.ORCHID_BAG.get()))
                .save(consumer, ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "orchid_petals_from_bag"));
    }

    private static void recipesFoodstuffs(Consumer<FinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.RICE_DOUGH.get(), 3)
                .requires(Items.WATER_BUCKET)
                .requires(CROPS_RICE)
                .requires(CROPS_RICE)
                .requires(CROPS_RICE)
                .unlockedBy("has_rice", has(CROPS_RICE))
                .save(consumer, ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "rice_dough_from_water"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.RICE_DOUGH.get(), 3)
                .requires(EGGS)
                .requires(CROPS_RICE)
                .requires(CROPS_RICE)
                .requires(CROPS_RICE)
                .unlockedBy("has_rice", has(CROPS_RICE))
                .save(consumer, ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "rice_dough_from_eggs"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.BLOOD_SYRUP.get(), 2)
                .requires(Items.GLASS_BOTTLE)
                .requires(StrictNBTIngredient.of(bloodBottleWithDamage(9)))
                .requires(Ingredient.fromValues(Stream.of(
                        new Ingredient.ItemValue(new ItemStack(Items.APPLE)),
                        new Ingredient.ItemValue(new ItemStack(Items.SWEET_BERRIES)),
                        new Ingredient.ItemValue(new ItemStack(Items.GLOW_BERRIES)),
                        new Ingredient.ItemValue(new ItemStack(ModBlocks.CURSED_ROOTS.get())),
                        new Ingredient.ItemValue(new ItemStack(ModBlocks.DARK_SPRUCE_LEAVES.get())),
                        new Ingredient.ItemValue(new ItemStack(ModBlocks.DARK_SPRUCE_SAPLING.get())),
                        new Ingredient.ItemValue(new ItemStack(ModBlocks.CURSED_SPRUCE_SAPLING.get()))
                )))
                .unlockedBy("has_blood_bottle", has(ModItems.BLOOD_BOTTLE.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.BLOOD_DOUGH.get(), 1)
                .requires(VDItems.BLOOD_SYRUP.get())
                .requires(VDItems.RICE_DOUGH.get())
                .unlockedBy(hasItem(VDItems.BLOOD_SYRUP.get()), has(VDItems.BLOOD_SYRUP.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, VDItems.PURE_SORBET.get(), 1)
                .pattern(" SA")
                .pattern("IPS")
                .pattern("TI ")
                .define('P', PURE_BLOOD)
                .define('S', Items.SWEET_BERRIES)
                .define('A', Items.APPLE)
                .define('I', Items.ICE)
                .define('T', Items.STICK)
                .unlockedBy("has_pure_blood", has(PURE_BLOOD))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.SUGARED_BERRIES.get(), 1)
                .requires(Items.SWEET_BERRIES)
                .requires(Items.SUGAR)
                .unlockedBy(hasItem(Items.SWEET_BERRIES), has(Items.SWEET_BERRIES))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.ORCHID_COOKIE.get(), 8)
                .requires(VDItems.ORCHID_PETALS.get())
                .requires(Items.WHEAT)
                .requires(Items.WHEAT)
                .unlockedBy(hasItem(VDItems.ORCHID_PETALS.get()), has(VDItems.ORCHID_PETALS.get()))
                .save(consumer, ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "orchid_cookie_from_wheat"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.ORCHID_COOKIE.get(), 8)
                .requires(VDItems.ORCHID_PETALS.get())
                .requires(CROPS_RICE)
                .requires(CROPS_RICE)
                .unlockedBy(hasItem(VDItems.ORCHID_PETALS.get()), has(VDItems.ORCHID_PETALS.get()))
                .save(consumer, ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "orchid_cookie_from_rice"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.CURSED_CUPCAKE.get())
                .requires(VDItems.BLOOD_BAGEL.get())
                .requires(MILK)
                .requires(Items.SUGAR)
                .unlockedBy(hasItem(VDItems.BLOOD_BAGEL.get()), has(VDItems.BLOOD_BAGEL.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.ORCHID_ECLAIR.get(), 1)
                .requires(VDItems.ORCHID_PETALS.get())
                .requires(BREAD)
                .requires(Items.SWEET_BERRIES)
                .unlockedBy(hasItem(VDItems.ORCHID_PETALS.get()), has(VDItems.ORCHID_PETALS.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.ORCHID_ICE_CREAM.get(), 1)
                .requires(VDItems.ORCHID_PETALS.get())
                .requires(VDItems.ORCHID_PETALS.get())
                .requires(MILK)
                .requires(Items.ICE)
                .requires(Items.SUGAR)
                .requires(Items.BOWL)
                .unlockedBy(hasItem(VDItems.ORCHID_PETALS.get()), has(VDItems.ORCHID_PETALS.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.SNOW_WHITE_ICE_CREAM.get(), 1)
                .requires(HOLY_WATER)
                .requires(MILK)
                .requires(Items.COCOA_BEANS)
                .requires(Items.ICE)
                .requires(Items.SUGAR)
                .unlockedBy("has_holy_water", has(HOLY_WATER))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.DARK_ICE_CREAM.get(), 1)
                .requires(VDItems.BLOOD_SYRUP.get())
                .requires(GRAIN)
                .requires(GRAIN)
                .requires(Items.ICE)
                .requires(Items.ICE)
                .requires(VDItemTags.BLOOD_SYRUP_INGREDIENTS)
                .unlockedBy(hasItem(VDItems.BLOOD_SYRUP.get()), has(VDItems.BLOOD_SYRUP.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.WOLF_BERRY_COOKIE.getOrThrow(), 8)
                .requires(de.teamlapen.werewolves.core.ModItems.WOLF_BERRIES.get())
                .requires(CROPS_RICE)
                .requires(CROPS_RICE)
                .unlockedBy(hasItem(de.teamlapen.werewolves.core.ModItems.WOLF_BERRIES.get()), has(de.teamlapen.werewolves.core.ModItems.WOLF_BERRIES.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.WOLF_BERRY_ICE_CREAM.getOrThrow(), 1)
                .requires(de.teamlapen.werewolves.core.ModItems.WOLF_BERRIES.get())
                .requires(de.teamlapen.werewolves.core.ModItems.WOLF_BERRIES.get())
                .requires(MILK)
                .requires(Items.ICE)
                .requires(Items.SUGAR)
                .requires(Items.BOWL)
                .unlockedBy(hasItem(de.teamlapen.werewolves.core.ModItems.WOLF_BERRIES.get()), has(de.teamlapen.werewolves.core.ModItems.WOLF_BERRIES.get()))
                .save(consumer);
    }

    private static void recipesPouring(Consumer<FinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.DANDELION_BEER_MUG.get(), 1)
                .requires(VDItems.DANDELION_BEER_BOTTLE.get())
                .requires(Items.GLASS_BOTTLE)
                .unlockedBy(hasItem(VDItems.DANDELION_BEER_BOTTLE.get()), has(VDItems.DANDELION_BEER_BOTTLE.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.BLOOD_WINE_GLASS.get(), 1)
                .requires(VDItems.BLOOD_WINE_BOTTLE.get())
                .requires(Items.GLASS_BOTTLE)
                .unlockedBy(hasItem(VDItems.BLOOD_WINE_BOTTLE.get()), has(VDItems.BLOOD_WINE_BOTTLE.get()))
                .save(consumer);
    }

    private static void recipesFoodBlocks(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, VDItems.BLOOD_PIE.get(), 1)
                .pattern("SSS")
                .pattern("WGW")
                .pattern("BCB")
                .define('W', Items.NETHER_WART)
                .define('B', VDItems.BLOOD_SYRUP.get())
                .define('G', Items.SUGAR)
                .define('S', Items.SWEET_BERRIES)
                .define('C', vectorwing.farmersdelight.common.registry.ModItems.PIE_CRUST.get())
                .unlockedBy(hasItem(VDItems.BLOOD_SYRUP.get()), has(VDItems.BLOOD_SYRUP.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, VDItems.BLOOD_PIE.get(), 1)
                .pattern("SS")
                .pattern("SS")
                .define('S', VDItems.BLOOD_PIE_SLICE.get())
                .unlockedBy(hasItem(VDItems.BLOOD_PIE_SLICE.get()), has(VDItems.BLOOD_PIE_SLICE.get()))
                .save(consumer, ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "blood_pie_from_slices"));
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, VDItems.ORCHID_CAKE.get(), 1)
                .pattern("MMM")
                .pattern("OSO")
                .pattern("WWW")
                .define('M', MILK)
                .define('O', VDItems.ORCHID_PETALS.get())
                .define('S', Items.SUGAR)
                .define('W', GRAIN_WHEAT)
                .unlockedBy(hasItem(VDItems.ORCHID_PETALS.get()), has(VDItems.ORCHID_PETALS.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.ORCHID_CAKE.get(), 1)
                .requires(VDItems.ORCHID_CAKE_SLICE.get(), 7)
                .unlockedBy(hasItem(VDItems.ORCHID_CAKE_SLICE.get()), has(VDItems.ORCHID_CAKE_SLICE.get()))
                .save(consumer, ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "orchid_cake_from_slices"));
    }

    private static void recipesCraftedMeals(Consumer<FinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.FISH_BURGER.get())
                .requires(RICE_BREAD)
                .requires(COOKED_FISHES)
                .requires(SALAD_INGREDIENTS)
                .requires(VEGETABLES_GARLIC)
                .unlockedBy("has_garlic", has(VEGETABLES_GARLIC))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.BLOOD_SAUSAGE.get())
                .requires(VDItems.BLOOD_SYRUP.get())
                .requires(VEGETABLES_ONION)
                .requires(Ingredient.fromValues(Stream.of(
                        new Ingredient.TagValue(COOKED_BEEF),
                        new Ingredient.TagValue(COOKED_CHICKEN),
                        new Ingredient.TagValue(COOKED_MUTTON),
                        new Ingredient.TagValue(COOKED_PORK)
                )))
                .unlockedBy(hasItem(VDItems.BLOOD_SYRUP.get()), has(VDItems.BLOOD_SYRUP.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.BLOOD_HOT_DOG.get())
                .requires(VDItems.BLOOD_SAUSAGE.get())
                .requires(BREAD)
                .unlockedBy(hasItem(VDItems.BLOOD_SAUSAGE.get()), has(VDItems.BLOOD_SAUSAGE.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.BAGEL_SANDWICH.get())
                .requires(VDItems.BLOOD_BAGEL.get())
                .requires(COOKED_BACON)
                .requires(vectorwing.farmersdelight.common.registry.ModItems.FRIED_EGG.get())
                .unlockedBy(hasItem(VDItems.BLOOD_BAGEL.get()), has(VDItems.BLOOD_BAGEL.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.EYES_ON_STICK.get())
                .requires(VDItems.HUMAN_EYE.get())
                .requires(VDItems.HUMAN_EYE.get())
                .requires(CROPS_TOMATO)
                .requires(Ingredient.fromValues(Stream.of(
                        new Ingredient.ItemValue(new ItemStack(Items.BROWN_MUSHROOM)),
                        new Ingredient.ItemValue(new ItemStack(Items.RED_MUSHROOM))
                )))
                .requires(Items.STICK)
                .unlockedBy(hasItem(VDItems.HUMAN_EYE.get()), has(VDItems.HUMAN_EYE.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.EYE_CROISSANT.get())
                .requires(BREAD)
                .requires(VDItems.HUMAN_EYE.get())
                .requires(VDItems.HUMAN_EYE.get())
                .requires(SALAD_INGREDIENTS)
                .requires(CROPS_TOMATO)
                .unlockedBy(hasItem(VDItems.HUMAN_EYE.get()), has(VDItems.HUMAN_EYE.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.BAT_TACO.get())
                .requires(BREAD)
                .requires(GRILLED_BAT)
                .requires(SALAD_INGREDIENTS)
                .requires(CROPS_TOMATO)
                .unlockedBy("has_grilled_bat", has(GRILLED_BAT))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.HARDTACK.get())
                .requires(Items.WHEAT)
                .requires(Items.WHEAT)
                .requires(Items.WHEAT)
                .requires(Items.WHEAT)
                .unlockedBy(hasItem(Items.WHEAT), has(Items.WHEAT))
                .save(consumer);
    }

    private static void cookMiscellaneous(Consumer<FinishedRecipe> consumer) {
        CookingPotRecipeBuilder.cookingPotRecipe(VDItems.DAISY_TEA.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(Items.OXEYE_DAISY)
                .addIngredient(Items.OXEYE_DAISY)
                .unlockedByItems("has_oxeye_daisy", Items.OXEYE_DAISY)
                .setRecipeBookTab(CookingPotRecipeBookTab.DRINKS)
                .save(consumer, itemLocationCooking(VDItems.DAISY_TEA.get()));
        CookingPotRecipeBuilder.cookingPotRecipe(VDItems.ORCHID_TEA.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(MILK)
                .addIngredient(VDItems.ORCHID_PETALS.get())
                .addIngredient(VDItems.ORCHID_PETALS.get())
                .unlockedByAnyIngredient(VDItems.ORCHID_PETALS.get(), ModBlocks.VAMPIRE_ORCHID.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.DRINKS)
                .save(consumer, itemLocationCooking(VDItems.ORCHID_TEA.get()));
        CookingPotRecipeBuilder.cookingPotRecipe(VDItems.WEIRD_JELLY_BLOCK.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(Items.BONE)
                .addIngredient(Items.SLIME_BALL)
                .addIngredient(Items.SLIME_BALL)
                .addIngredient(Items.SWEET_BERRIES)
                .addIngredient(Items.SWEET_BERRIES)
                .addIngredient(PURE_BLOOD)
                .unlockedByAnyIngredient(ModItems.PURE_BLOOD_0.get(), ModItems.PURE_BLOOD_1.get(), ModItems.PURE_BLOOD_2.get(), ModItems.PURE_BLOOD_3.get(), ModItems.PURE_BLOOD_4.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .save(consumer, itemLocationCooking(VDItems.WEIRD_JELLY_BLOCK.get()));
        CookingPotRecipeBuilder.cookingPotRecipe(VDItems.TRICOLOR_DANGO.get(), 1, NORMAL_COOKING, MEDIUM_EXP, Items.STICK)
                .addIngredient(RICE_DOUGH)
                .addIngredient(VDItems.BLOOD_SYRUP.get())
                .addIngredient(VDItems.ORCHID_PETALS.get())
                .addIngredient(Items.SUGAR)
                .unlockedByAnyIngredient(VDItems.ORCHID_PETALS.get(), VDItems.BLOOD_SYRUP.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MISC)
                .save(consumer, itemLocationCooking(VDItems.TRICOLOR_DANGO.get()));
        CookingPotRecipeBuilder.cookingPotRecipe(VDItems.MULLED_WINE_GLASS.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(VDItems.BLOOD_WINE_GLASS.get())
                .addIngredient(Ingredient.fromValues(Stream.of(
                        new Ingredient.ItemValue(new ItemStack(ModBlocks.CURSED_ROOTS.get())),
                        new Ingredient.ItemValue(new ItemStack(ModBlocks.CURSED_SPRUCE_SAPLING.get())),
                        new Ingredient.ItemValue(new ItemStack(ModBlocks.DARK_SPRUCE_SAPLING.get())),
                        new Ingredient.ItemValue(new ItemStack(ModBlocks.DARK_SPRUCE_LEAVES.get()))
                )))
                .addIngredient(Items.SUGAR)
                .unlockedByAnyIngredient(VDItems.BLOOD_WINE_GLASS.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.DRINKS)
                .save(consumer, itemLocationCooking(VDItems.MULLED_WINE_GLASS.get()));
    }

    private static void cookMeals(Consumer<FinishedRecipe> consumer) {
        CookingPotRecipeBuilder.cookingPotRecipe(VDItems.ORCHID_CREAM_SOUP.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(VDItems.ORCHID_PETALS.get())
                .addIngredient(SALAD_INGREDIENTS)
                .addIngredient(VEGETABLES_ONION)
                .addIngredient(Items.POTATO)
                .addIngredient(MILK)
                .unlockedByAnyIngredient(VDItems.ORCHID_PETALS.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .save(consumer, itemLocationCooking(VDItems.ORCHID_CREAM_SOUP.get()));
        CookingPotRecipeBuilder.cookingPotRecipe(VDItems.BLACK_MUSHROOM_SOUP.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(VDItems.BLACK_MUSHROOM.get())
                .addIngredient(Items.POTATO)
                .addIngredient(Items.CARROT)
                .addIngredient(VEGETABLES_ONION)
                .unlockedByAnyIngredient(VDItems.BLACK_MUSHROOM.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .save(consumer, itemLocationCooking(VDItems.BLACK_MUSHROOM_SOUP.get()));
        CookingPotRecipeBuilder.cookingPotRecipe(VDItems.ORCHID_CURRY.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(VDItems.ORCHID_PETALS.get())
                .addIngredient(VDItems.ORCHID_PETALS.get())
                .addIngredient(Items.POTATO)
                .addIngredient(VEGETABLES_ONION)
                .addIngredient(Ingredient.fromValues(Stream.of(
                        new Ingredient.TagValue(RAW_MUTTON),
                        new Ingredient.TagValue(RAW_BEEF)
                )))
                .unlockedByAnyIngredient(VDItems.ORCHID_PETALS.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .save(consumer, itemLocationCooking(VDItems.ORCHID_CURRY.get()));
        CookingPotRecipeBuilder.cookingPotRecipe(VDItems.BLACK_MUSHROOM_NOODLES.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(VDItems.BLACK_MUSHROOM.get())
                .addIngredient(VDItems.BLACK_MUSHROOM.get())
                .addIngredient(PASTA)
                .addIngredient(VEGETABLES_ONION)
                .addIngredient(MILK)
                .unlockedByAnyIngredient(VDItems.BLACK_MUSHROOM.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .save(consumer, itemLocationCooking(VDItems.BLACK_MUSHROOM_NOODLES.get()));
        CookingPotRecipeBuilder.cookingPotRecipe(VDItems.GARLIC_SOUP.get(), 1, NORMAL_COOKING, MEDIUM_EXP, Items.BREAD)
                .addIngredient(RAW_CHICKEN)
                .addIngredient(VEGETABLES_GARLIC)
                .addIngredient(VEGETABLES)
                .unlockedByAnyIngredient(VDItems.ROASTED_GARLIC.get(), ModItems.ITEM_GARLIC.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .save(consumer, itemLocationCooking(VDItems.GARLIC_SOUP.get()));
        CookingPotRecipeBuilder.cookingPotRecipe(VDItems.BORSCHT.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(Ingredient.fromValues(Stream.of(
                        new Ingredient.TagValue(RAW_PORK),
                        new Ingredient.ItemValue(new ItemStack(Items.BEEF)),
                        new Ingredient.TagValue(RAW_CHICKEN)
                )))
                .addIngredient(Ingredient.fromValues(Stream.of(
                        new Ingredient.TagValue(VEGETABLES_POTATO),
                        new Ingredient.TagValue(VEGETABLES_CARROT)
                )))
                .addIngredient(VEGETABLES_BEETROOT)
                .addIngredient(VEGETABLES_GARLIC)
                .unlockedByAnyIngredient(VDItems.ROASTED_GARLIC.get(), ModItems.ITEM_GARLIC.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .save(consumer, itemLocationCooking(VDItems.BORSCHT.get()));
    }

    private static void fermentingAlternatives(Consumer<FinishedRecipe> consumer) {
        CookingPotRecipeBuilder.cookingPotRecipe(VDItems.DANDELION_BEER_BOTTLE.get(), 1, SLOW_FERMENTING, LARGE_EXP, Items.GLASS_BOTTLE)
                .addIngredient(Items.WHEAT)
                .addIngredient(Items.WHEAT)
                .addIngredient(Items.WHEAT)
                .addIngredient(Items.DANDELION)
                .addIngredient(Items.SUGAR)
                .unlockedByAnyIngredient(Items.WHEAT, Items.DANDELION)
                .setRecipeBookTab(CookingPotRecipeBookTab.DRINKS)
                .save(consumer, itemLocationCooking(VDItems.DANDELION_BEER_BOTTLE.get()));
        CookingPotRecipeBuilder.cookingPotRecipe(VDItems.BLOOD_WINE_BOTTLE.get(), 1, SLOW_FERMENTING, LARGE_EXP, Items.GLASS_BOTTLE)
                .addIngredient(VDItems.BLOOD_SYRUP.get())
                .addIngredient(VDItems.BLOOD_SYRUP.get())
                .addIngredient(VDItems.BLOOD_SYRUP.get())
                .addIngredient(Items.SWEET_BERRIES)
                .addIngredient(Items.SUGAR)
                .unlockedByAnyIngredient(VDItems.BLOOD_SYRUP.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.DRINKS)
                .save(consumer, itemLocationCooking(VDItems.BLOOD_WINE_BOTTLE.get()));
    }

    private static void cuttingAnimalItems(Consumer<FinishedRecipe> consumer) {
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModItems.HUMAN_HEART.get()), KNIVES, VDItems.HEART_PIECES.get(), 2)
                .save(consumer, itemLocationCutting(ModItems.HUMAN_HEART.get()));
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(VDItems.RAW_BAT.get()), KNIVES, VDItems.RAW_BAT_CHOPS.get(), 2)
                .save(consumer, itemLocationCutting(VDItems.RAW_BAT.get()));
    }

    private static void cuttingFoods(Consumer<FinishedRecipe> consumer) {
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(VDItems.BLOOD_PIE.get()), KNIVES, VDItems.BLOOD_PIE_SLICE.get(), 4)
                .save(consumer, itemLocationCutting(VDItems.BLOOD_PIE.get()));
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(VDItems.ORCHID_CAKE.get()), KNIVES, VDItems.ORCHID_CAKE_SLICE.get(), 7)
                .save(consumer, itemLocationCutting(VDItems.ORCHID_CAKE.get()));
    }

    private static void cuttingFlowers(Consumer<FinishedRecipe> consumer) {
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(VDItems.WILD_GARLIC.get()), KNIVES, ModItems.ITEM_GARLIC.get(), 1)
                .addResult(Items.LIGHT_GRAY_DYE, 2)
                .addResultWithChance(Items.GREEN_DYE, 0.25F)
                .save(consumer, itemLocationCutting(VDItems.WILD_GARLIC.get()));
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModBlocks.VAMPIRE_ORCHID.get()), KNIVES, VDItems.ORCHID_SEEDS.get(), 1)
                .addResultWithChance(VDItems.ORCHID_PETALS.get(), 0.6F, 1)
                .addResultWithChance(ModBlocks.CURSED_ROOTS.get(), 0.35F, 1)
                .save(consumer, blockLocationCutting(ModBlocks.VAMPIRE_ORCHID.get()));
    }

    private static void salvagingMinerals(Consumer<FinishedRecipe> consumer) {
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModBlocks.DARK_STONE.get()), PICKAXES, ModBlocks.COBBLED_DARK_STONE.get(), 1)
                .save(consumer, blockLocationCutting(ModBlocks.DARK_STONE.get()));
    }

    private static void strippingWood(Consumer<FinishedRecipe> consumer) {
        stripLogForBark(consumer, ModBlocks.CURSED_SPRUCE_LOG.get(), ModBlocks.STRIPPED_CURSED_SPRUCE_LOG.get());
        stripLogForBark(consumer, ModBlocks.DARK_SPRUCE_LOG.get(), ModBlocks.STRIPPED_DARK_SPRUCE_LOG.get());
    }

    private static void salvagingWoodenFurniture(Consumer<FinishedRecipe> consumer) {
        salvagePlankFromFurniture(consumer, ModBlocks.CURSED_SPRUCE_PLANKS.get(), ModBlocks.CURSED_SPRUCE_DOOR.get(), ModBlocks.CURSED_SPRUCE_TRAPDOOR.get(), ModBlocks.CURSED_SPRUCE_SIGN.get(), ModBlocks.CURSED_SPRUCE_HANGING_SIGN.get());
        salvagePlankFromFurniture(consumer, ModBlocks.DARK_SPRUCE_PLANKS.get(), ModBlocks.DARK_SPRUCE_DOOR.get(), ModBlocks.DARK_SPRUCE_TRAPDOOR.get(), ModBlocks.DARK_SPRUCE_SIGN.get(), ModBlocks.DARK_SPRUCE_HANGING_SIGN.get());
    }

    private static void salvagingUsingShears(Consumer<FinishedRecipe> consumer) {
        salvageLeatherArmor(consumer, 1,
                ModItems.ARMOR_OF_SWIFTNESS_HEAD_NORMAL.get(),
                ModItems.ARMOR_OF_SWIFTNESS_CHEST_NORMAL.get(),
                ModItems.ARMOR_OF_SWIFTNESS_LEGS_NORMAL.get(),
                ModItems.ARMOR_OF_SWIFTNESS_FEET_NORMAL.get()
        );
        salvageLeatherArmor(consumer, 2,
                ModItems.ARMOR_OF_SWIFTNESS_HEAD_ENHANCED.get(),
                ModItems.ARMOR_OF_SWIFTNESS_CHEST_ENHANCED.get(),
                ModItems.ARMOR_OF_SWIFTNESS_LEGS_ENHANCED.get(),
                ModItems.ARMOR_OF_SWIFTNESS_FEET_ENHANCED.get(),

                ModItems.ARMOR_OF_SWIFTNESS_HEAD_ULTIMATE.get(),
                ModItems.ARMOR_OF_SWIFTNESS_CHEST_ULTIMATE.get(),
                ModItems.ARMOR_OF_SWIFTNESS_LEGS_ULTIMATE.get(),
                ModItems.ARMOR_OF_SWIFTNESS_FEET_ULTIMATE.get()
        );
    }

    private static void smeltingRecipes(Consumer<FinishedRecipe> consumer) {
        multipleSmeltingRecipes("roasted_garlic", ModItems.ITEM_GARLIC.get(), VDItems.ROASTED_GARLIC.get(),
                SMALL_EXP, true, true, true, consumer);
        multipleSmeltingRecipes("grilled_bat", VDItems.RAW_BAT.get(), VDItems.GRILLED_BAT.get(),
                SMALL_EXP, true, true, true, consumer);
        multipleSmeltingRecipes("grilled_bat_chops", VDItems.RAW_BAT_CHOPS.get(), VDItems.GRILLED_BAT_CHOPS.get(),
                SMALL_EXP, true, true, true, consumer);
        multipleSmeltingRecipes("rice_bread", VDItems.RICE_DOUGH.get(), VDItems.RICE_BREAD.get(),
                SMALL_EXP, true, true, false, consumer);
        multipleSmeltingRecipes("blood_bagel", VDItems.BLOOD_DOUGH.get(), VDItems.BLOOD_BAGEL.get(),
                SMALL_EXP, true, true, false, consumer);

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(VDItems.SILVER_KNIFE.getOrThrow()), RecipeCategory.MISC, de.teamlapen.werewolves.core.ModItems.SILVER_NUGGET.get(), 0.1F, NORMAL_COOKING)
                .unlockedBy(hasItem(VDItems.SILVER_KNIFE.getOrThrow()), InventoryChangeTrigger.TriggerInstance.hasItems(VDItems.SILVER_KNIFE.getOrThrow()))
                .save(consumer, ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "silver_nugget_from_smelting_knife"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(VDItems.SILVER_KNIFE.getOrThrow()), RecipeCategory.MISC, de.teamlapen.werewolves.core.ModItems.SILVER_NUGGET.get(), 0.1F, FAST_COOKING)
                .unlockedBy(hasItem(VDItems.SILVER_KNIFE.getOrThrow()), InventoryChangeTrigger.TriggerInstance.hasItems(VDItems.SILVER_KNIFE.getOrThrow()))
                .save(consumer, ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "silver_nugget_from_blasting_knife"));
    }

    private static void recipesWeaponTable(Consumer<FinishedRecipe> consumer) {
        ShapelessWeaponTableRecipeBuilder.shapelessWeaponTable(RecipeCategory.COMBAT, VDItems.ALCHEMICAL_COCKTAIL.get())
                .requires(Items.GLASS_BOTTLE)
                .requires(ModItems.ITEM_ALCHEMICAL_FIRE.get(), 2)
                .unlockedBy("has_alchemical_fire", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.ITEM_ALCHEMICAL_FIRE.get()))
                .save(consumer);
        ShapedWeaponTableRecipeBuilder.shapedWeaponTable(RecipeCategory.COMBAT, VDBlocks.SPIRIT_LANTERN.get())
                .pattern(" GG ")
                .pattern("GSSG")
                .pattern("GSSG")
                .pattern(" GG ")
                .define('G', GOLD_NUGGET)
                .define('S', ModItems.SOUL_ORB_VAMPIRE.get())
                .unlockedBy("has_vampire_soul", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.SOUL_ORB_VAMPIRE.get()))
                .save(consumer);
    }

    private static void recipesAlchemyTable(Consumer<FinishedRecipe> consumer) {
        AlchemyTableRecipeBuilder
                .builder(VDOils.FOG_VISION)
                .bloodOilIngredient()
                .input(potion(VDPotions.FOG_VISION.get(), VDPotions.LONG_FOG_VISION.get()))
                .build(consumer, ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "fog_vision_oil"));
        AlchemyTableRecipeBuilder
                .builder(VDOils.CONSECRATION)
                .bloodOilIngredient()
                .input(potion(VDPotions.CONSECRATION.get(), VDPotions.LONG_CONSECRATION.get()))
                .build(consumer, ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "consecration_oil"));
        AlchemyTableRecipeBuilder
                .builder(VDOils.DISSOLVING)
                .bloodOilIngredient()
                .input(potion(VDPotions.DISSOLVING.get(), VDPotions.LONG_DISSOLVING.get(), VDPotions.STRONG_DISSOLVING.get()))
                .build(consumer, ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "dissolving_oil"));
    }

    private static void wineShelfRecipe(Block wineShelfBlock, Block slabBlock, Block planksBlock, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, wineShelfBlock)
                .pattern("SSS")
                .pattern(" P ")
                .pattern("SSS")
                .define('S', slabBlock)
                .define('P', planksBlock)
                .unlockedBy(hasBlock(slabBlock), has(slabBlock))
                .save(consumer);
    }

    private static void barStoolRecipe(Block barStoolBlock, Block woolBlock, Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, barStoolBlock)
                .pattern("W")
                .pattern("F")
                .pattern("F")
                .define('F', WOODEN_FENCES)
                .define('W', woolBlock)
                .unlockedBy(hasBlock(woolBlock), has(woolBlock))
                .save(consumer);
    }

    private static void colorBlockWithDye(List<Item> dyeableItems, Consumer<FinishedRecipe> consumer) {
        for (int i = 0; i < DYES.size(); ++i) {
            Item dyeItem = DYES.get(i);
            Item dyeableItem = dyeableItems.get(i);
            ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, dyeableItem)
                    .requires(dyeItem)
                    .requires(Ingredient.of(dyeableItems.stream().filter(item -> !item.equals(dyeableItem)).map(ItemStack::new)))
                    .unlockedBy("has_needed_dye", has(dyeItem))
                    .save(consumer, ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "dye_" + itemName(dyeableItem)));
        }
    }

    private static void multipleSmeltingRecipes(String name, ItemLike ingredient, ItemLike result, float experience, boolean hasSmeltingRecipe, boolean hasSmokingRecipe, boolean hasCampfireRecipe, Consumer<FinishedRecipe> consumer) {
        String namePrefix = ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, name).toString();
        if (hasSmeltingRecipe) SimpleCookingRecipeBuilder.smelting(Ingredient.of(ingredient), RecipeCategory.FOOD, result, experience, 200)
                .unlockedBy(name, InventoryChangeTrigger.TriggerInstance.hasItems(ingredient))
                .save(consumer);
        if (hasCampfireRecipe) SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(ingredient), RecipeCategory.FOOD, result, experience, 600)
                .unlockedBy(name, InventoryChangeTrigger.TriggerInstance.hasItems(ingredient))
                .save(consumer, namePrefix + "_from_campfire_cooking");
        if (hasSmokingRecipe) SimpleCookingRecipeBuilder.smoking(Ingredient.of(ingredient), RecipeCategory.FOOD, result, experience, 100)
                .unlockedBy(name, InventoryChangeTrigger.TriggerInstance.hasItems(ingredient))
                .save(consumer, namePrefix + "_from_smoking");
    }

    private static void stripLogForBark(Consumer<FinishedRecipe> consumer, Block log, Block strippedLog) {
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(log), AXES_STRIP, strippedLog)
                .addResult(vectorwing.farmersdelight.common.registry.ModItems.TREE_BARK.get())
                .addSound(Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getKey(SoundEvents.AXE_STRIP)).toString())
                .save(consumer, blockLocationCutting(log));
    }

    private static void salvagePlankFromFurniture(Consumer<FinishedRecipe> consumer, Block plank, Block door, Block trapdoor, Block sign, Block hangingSign) {
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(door), AXES_DIG, plank)
                .save(consumer, blockLocationCutting(door));
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(trapdoor), AXES_DIG, plank)
                .save(consumer, blockLocationCutting(trapdoor));
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(sign), AXES_DIG, plank)
                .save(consumer, blockLocationCutting(sign));
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(hangingSign), AXES_DIG, plank)
                .save(consumer, blockLocationCutting(hangingSign));
    }

    private static void salvageLeatherArmor(Consumer<FinishedRecipe> consumer, int leatherAmount, Item... items) {
        for (Item item : items) {
            CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(item), SHEARS_TOOL, Items.LEATHER, leatherAmount)
                    .save(consumer, itemLocationCutting(item));
        }
    }

    private static Ingredient potion(Potion... potion) {
        return new NBTIngredient(Arrays.stream(potion).map(p -> PotionUtils.setPotion(new ItemStack(Items.POTION, 1), p)).toArray(ItemStack[]::new));
    }

    private static ResourceLocation itemLocationCooking(Item item) {
        return ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "cooking/" + Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)).getPath());
    }

    private static ResourceLocation itemLocationCutting(Item item) {
        return ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "cutting/" + Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)).getPath());
    }

    private static ResourceLocation blockLocationCutting(Block block) {
        return ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "cutting/" + Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath());
    }

    private static String itemName(Item item) {
        return Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)).getPath();
    }

    private static String blockName(Block block) {
        return Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath();
    }

    private static ItemStack bloodBottleWithDamage(int damage) {
        ItemStack stack = new ItemStack(ModItems.BLOOD_BOTTLE.get());
        stack.setDamageValue(damage);
        return stack;
    }

    private static String hasItem(Item item) {
        return "has_" + itemName(item);
    }

    private static String hasBlock(Block block) {
        return "has_" + blockName(block);
    }
}