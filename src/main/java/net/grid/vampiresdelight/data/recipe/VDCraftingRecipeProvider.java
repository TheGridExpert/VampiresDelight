package net.grid.vampiresdelight.data.recipe;

import de.teamlapen.vampirism.core.ModBlocks;
import de.teamlapen.vampirism.core.ModItems;
import de.teamlapen.vampirism.core.ModTags;
import net.grid.vampiresdelight.common.crafting.WeaveLettersRecipe;
import net.grid.vampiresdelight.common.registry.*;
import net.grid.vampiresdelight.common.tag.VDCommonTags;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.utility.VDHelper;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;

import java.util.List;
import java.util.stream.Stream;

import static net.grid.vampiresdelight.common.utility.VDNameUtils.blockName;
import static net.grid.vampiresdelight.common.utility.VDNameUtils.itemName;

public class VDCraftingRecipeProvider {
    public static void register(RecipeOutput output) {
        recipesBlocks(output);
        recipesMaterials(output);
        recipesFoodstuffs(output);
        recipesPouring(output);
        recipesFoodBlocks(output);
        recipesCraftedMeals(output);
        SpecialRecipeBuilder.special(WeaveLettersRecipe::new).save(output, WeaveLettersRecipe.ID);
    }

    private static void recipesBlocks(RecipeOutput output) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, VDBlocks.DARK_STONE_STOVE.get())
                .pattern("iii")
                .pattern("d d")
                .pattern("dCd")
                .define('i', Tags.Items.INGOTS_IRON)
                .define('d', ModBlocks.DARK_STONE_BRICKS.get())
                .define('C', Blocks.CAMPFIRE)
                .unlockedBy(hasBlock(ModBlocks.DARK_STONE_BRICKS.get()), has(ModBlocks.DARK_STONE_BRICKS.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, VDBlocks.DARK_SPRUCE_CABINET.get())
                .pattern("___")
                .pattern("D D")
                .pattern("___")
                .define('_', ModBlocks.DARK_SPRUCE_SLAB.get())
                .define('D', ModBlocks.DARK_SPRUCE_TRAPDOOR.get())
                .unlockedBy(hasBlock(ModBlocks.DARK_SPRUCE_TRAPDOOR.get()), has(ModBlocks.DARK_SPRUCE_TRAPDOOR.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, VDBlocks.CURSED_SPRUCE_CABINET.get())
                .pattern("___")
                .pattern("D D")
                .pattern("___")
                .define('_', ModBlocks.CURSED_SPRUCE_SLAB.get())
                .define('D', ModBlocks.CURSED_SPRUCE_TRAPDOOR.get())
                .unlockedBy(hasBlock(ModBlocks.CURSED_SPRUCE_TRAPDOOR.get()), has(ModBlocks.CURSED_SPRUCE_TRAPDOOR.get()))
                .save(output);

        wineShelfRecipe(VDBlocks.OAK_WINE_SHELF.get(), Blocks.OAK_SLAB, Blocks.OAK_PLANKS, output);
        wineShelfRecipe(VDBlocks.SPRUCE_WINE_SHELF.get(), Blocks.SPRUCE_SLAB, Blocks.SPRUCE_PLANKS, output);
        wineShelfRecipe(VDBlocks.BIRCH_WINE_SHELF.get(), Blocks.BIRCH_SLAB, Blocks.BIRCH_PLANKS, output);
        wineShelfRecipe(VDBlocks.JUNGLE_WINE_SHELF.get(), Blocks.JUNGLE_SLAB, Blocks.JUNGLE_PLANKS, output);
        wineShelfRecipe(VDBlocks.ACACIA_WINE_SHELF.get(), Blocks.ACACIA_SLAB, Blocks.ACACIA_PLANKS, output);
        wineShelfRecipe(VDBlocks.DARK_OAK_WINE_SHELF.get(), Blocks.DARK_OAK_SLAB, Blocks.DARK_OAK_PLANKS, output);
        wineShelfRecipe(VDBlocks.MANGROVE_WINE_SHELF.get(), Blocks.MANGROVE_SLAB, Blocks.MANGROVE_PLANKS, output);
        wineShelfRecipe(VDBlocks.CHERRY_WINE_SHELF.get(), Blocks.CHERRY_SLAB, Blocks.CHERRY_PLANKS, output);
        wineShelfRecipe(VDBlocks.BAMBOO_WINE_SHELF.get(), Blocks.BAMBOO_SLAB, Blocks.BAMBOO_PLANKS, output);
        wineShelfRecipe(VDBlocks.CRIMSON_WINE_SHELF.get(), Blocks.CRIMSON_SLAB, Blocks.CRIMSON_PLANKS, output);
        wineShelfRecipe(VDBlocks.WARPED_WINE_SHELF.get(), Blocks.WARPED_SLAB, Blocks.WARPED_PLANKS, output);
        wineShelfRecipe(VDBlocks.CURSED_SPRUCE_WINE_SHELF.get(), ModBlocks.CURSED_SPRUCE_SLAB.get(), ModBlocks.CURSED_SPRUCE_PLANKS.get(), output);
        wineShelfRecipe(VDBlocks.DARK_SPRUCE_WINE_SHELF.get(), ModBlocks.DARK_SPRUCE_SLAB.get(), ModBlocks.DARK_SPRUCE_PLANKS.get(), output);
        // Werewolves wine shelves have to be made manually

        barStoolRecipe(VDBlocks.WHITE_BAR_STOOL.get(), Blocks.WHITE_WOOL, output);
        barStoolRecipe(VDBlocks.ORANGE_BAR_STOOL.get(), Blocks.ORANGE_WOOL, output);
        barStoolRecipe(VDBlocks.MAGENTA_BAR_STOOL.get(), Blocks.MAGENTA_WOOL, output);
        barStoolRecipe(VDBlocks.LIGHT_BLUE_BAR_STOOL.get(), Blocks.LIGHT_BLUE_WOOL, output);
        barStoolRecipe(VDBlocks.YELLOW_BAR_STOOL.get(), Blocks.YELLOW_WOOL, output);
        barStoolRecipe(VDBlocks.LIME_BAR_STOOL.get(), Blocks.LIME_WOOL, output);
        barStoolRecipe(VDBlocks.PINK_BAR_STOOL.get(), Blocks.PINK_WOOL, output);
        barStoolRecipe(VDBlocks.GRAY_BAR_STOOL.get(), Blocks.GRAY_WOOL, output);
        barStoolRecipe(VDBlocks.LIGHT_GRAY_BAR_STOOL.get(), Blocks.LIGHT_GRAY_WOOL, output);
        barStoolRecipe(VDBlocks.CYAN_BAR_STOOL.get(), Blocks.CYAN_WOOL, output);
        barStoolRecipe(VDBlocks.PURPLE_BAR_STOOL.get(), Blocks.PURPLE_WOOL, output);
        barStoolRecipe(VDBlocks.BLUE_BAR_STOOL.get(), Blocks.BLUE_WOOL, output);
        barStoolRecipe(VDBlocks.BROWN_BAR_STOOL.get(), Blocks.BROWN_WOOL, output);
        barStoolRecipe(VDBlocks.GREEN_BAR_STOOL.get(), Blocks.GREEN_WOOL, output);
        barStoolRecipe(VDBlocks.RED_BAR_STOOL.get(), Blocks.RED_WOOL, output);
        barStoolRecipe(VDBlocks.BLACK_BAR_STOOL.get(), Blocks.BLACK_WOOL, output);

        colorBlockWithDye(List.of(VDItems.BLACK_BAR_STOOL.get(), VDItems.BLUE_BAR_STOOL.get(), VDItems.BROWN_BAR_STOOL.get(), VDItems.CYAN_BAR_STOOL.get(), VDItems.GRAY_BAR_STOOL.get(), VDItems.GREEN_BAR_STOOL.get(), VDItems.LIGHT_BLUE_BAR_STOOL.get(), VDItems.LIGHT_GRAY_BAR_STOOL.get(), VDItems.LIME_BAR_STOOL.get(), VDItems.MAGENTA_BAR_STOOL.get(), VDItems.ORANGE_BAR_STOOL.get(), VDItems.PINK_BAR_STOOL.get(), VDItems.PURPLE_BAR_STOOL.get(), VDItems.RED_BAR_STOOL.get(), VDItems.YELLOW_BAR_STOOL.get(), VDItems.WHITE_BAR_STOOL.get()), output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, VDItems.GARLIC_CRATE.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ModBlocks.GARLIC.asItem())
                .unlockedBy(hasItem(ModBlocks.GARLIC.asItem()), has(ModBlocks.GARLIC.asItem()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, VDItems.ORCHID_BAG.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', VDItems.ORCHID_PETALS.get())
                .unlockedBy(hasItem(VDItems.ORCHID_PETALS.get()), has(VDItems.ORCHID_PETALS.get()))
                .save(output);
    }

    private static void recipesMaterials(RecipeOutput output) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.PAPER, 1)
                .requires(VDItems.WEATHERED_LETTER)
                .unlockedBy(hasItem(VDItems.WEATHERED_LETTER), has(VDItems.WEATHERED_LETTER))
                .save(output, ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "paper_from_weathered_letter"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ModBlocks.GARLIC.asItem(), 9)
                .requires(VDItems.GARLIC_CRATE.get())
                .unlockedBy(hasItem(VDItems.GARLIC_CRATE.get()), has(VDItems.GARLIC_CRATE.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "garlic_from_crate"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.ORCHID_PETALS.get(), 9)
                .requires(VDItems.ORCHID_BAG.get())
                .unlockedBy(hasItem(VDItems.ORCHID_BAG.get()), has(VDItems.ORCHID_BAG.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "orchid_petals_from_bag"));
    }

    private static void recipesFoodstuffs(RecipeOutput output) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.BLOOD_SYRUP.get(), 2)
                .requires(Items.GLASS_BOTTLE)
                .requires(VDHelper.FULL_BLOOD_BOTTLE)
                .requires(Ingredient.of(Items.APPLE, Items.SWEET_BERRIES, Items.GLOW_BERRIES, ModBlocks.CURSED_ROOTS.asItem(), ModBlocks.DARK_SPRUCE_LEAVES.asItem(), ModBlocks.DARK_SPRUCE_SAPLING.asItem(), ModBlocks.CURSED_SPRUCE_SAPLING.asItem()))
                .unlockedBy(hasItem(ModItems.BLOOD_BOTTLE.get()), has(ModItems.BLOOD_BOTTLE.get()))
                .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.RICE_DOUGH.get(), 3)
                .requires(Tags.Items.BUCKETS_WATER)
                .requires(VDCommonTags.Items.CROPS_RICE)
                .requires(VDCommonTags.Items.CROPS_RICE)
                .requires(VDCommonTags.Items.CROPS_RICE)
                .unlockedBy("has_rice", has(VDCommonTags.Items.CROPS_RICE))
                .save(output, ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "rice_dough_from_water"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.RICE_DOUGH.get(), 3)
                .requires(Tags.Items.EGGS)
                .requires(VDCommonTags.Items.CROPS_RICE)
                .requires(VDCommonTags.Items.CROPS_RICE)
                .requires(VDCommonTags.Items.CROPS_RICE)
                .unlockedBy("has_rice", has(VDCommonTags.Items.CROPS_RICE))
                .save(output, ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "rice_dough_from_eggs"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.BLOOD_DOUGH.get(), 1)
                .requires(VDItems.BLOOD_SYRUP.get())
                .requires(VDCommonTags.FOODS_DOUGH_RICE)
                .unlockedBy(hasItem(VDItems.BLOOD_SYRUP.get()), has(VDItems.BLOOD_SYRUP.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, VDItems.PURE_SORBET.get(), 1)
                .pattern(" sa")
                .pattern("ips")
                .pattern("ti ")
                .define('p', ModTags.Items.PURE_BLOOD)
                .define('s', Items.SWEET_BERRIES)
                .define('a', Items.APPLE)
                .define('i', Items.ICE)
                .define('t', Tags.Items.RODS_WOODEN)
                .unlockedBy("has_pure_blood", has(ModTags.Items.PURE_BLOOD))
                .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.SUGARED_BERRIES.get(), 1)
                .requires(Items.SWEET_BERRIES)
                .requires(Items.SUGAR)
                .unlockedBy(hasItem(Items.SWEET_BERRIES), has(Items.SWEET_BERRIES))
                .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.ORCHID_COOKIE.get(), 8)
                .requires(VDItems.ORCHID_PETALS.get())
                .requires(Tags.Items.CROPS_WHEAT)
                .requires(Tags.Items.CROPS_WHEAT)
                .unlockedBy(hasItem(VDItems.ORCHID_PETALS.get()), has(VDItems.ORCHID_PETALS.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "orchid_cookie_from_wheat"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.ORCHID_COOKIE.get(), 8)
                .requires(VDItems.ORCHID_PETALS.get())
                .requires(VDCommonTags.Items.CROPS_RICE)
                .requires(VDCommonTags.Items.CROPS_RICE)
                .unlockedBy(hasItem(VDItems.ORCHID_PETALS.get()), has(VDItems.ORCHID_PETALS.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "orchid_cookie_from_rice"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.CURSED_CUPCAKE.get())
                .requires(VDItems.BLOOD_BAGEL.get())
                .requires(Tags.Items.DRINKS_MILK)
                .requires(Items.SUGAR)
                .unlockedBy(hasItem(VDItems.BLOOD_BAGEL.get()), has(VDItems.BLOOD_BAGEL.get()))
                .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.ORCHID_ECLAIR.get(), 1)
                .requires(VDItems.ORCHID_PETALS.get())
                .requires(Tags.Items.FOODS_BREAD)
                .requires(Items.SWEET_BERRIES)
                .unlockedBy(hasItem(VDItems.ORCHID_PETALS.get()), has(VDItems.ORCHID_PETALS.get()))
                .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.ORCHID_ICE_CREAM.get(), 1)
                .requires(VDItems.ORCHID_PETALS.get())
                .requires(VDItems.ORCHID_PETALS.get())
                .requires(Tags.Items.DRINKS_MILK)
                .requires(Items.ICE)
                .requires(Items.SUGAR)
                .requires(Items.BOWL)
                .unlockedBy(hasItem(VDItems.ORCHID_PETALS.get()), has(VDItems.ORCHID_PETALS.get()))
                .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.SNOW_WHITE_ICE_CREAM.get(), 1)
                .requires(ModTags.Items.HOLY_WATER)
                .requires(Tags.Items.DRINKS_MILK)
                .requires(Items.COCOA_BEANS)
                .requires(Items.ICE)
                .requires(Items.SUGAR)
                .unlockedBy("has_holy_water", has(ModTags.Items.HOLY_WATER))
                .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.DARK_ICE_CREAM.get(), 1)
                .requires(VDItems.BLOOD_SYRUP.get())
                .requires(VDCommonTags.Items.CROPS_GRAIN)
                .requires(VDCommonTags.Items.CROPS_GRAIN)
                .requires(Items.ICE)
                .requires(Items.ICE)
                .requires(Ingredient.fromValues(Stream.of(
                        new Ingredient.ItemValue(new ItemStack(Items.INK_SAC)),
                        new Ingredient.ItemValue(new ItemStack(ModBlocks.CURSED_SPRUCE_SAPLING.get())),
                        new Ingredient.ItemValue(new ItemStack(ModBlocks.DARK_SPRUCE_SAPLING.get())),
                        new Ingredient.ItemValue(new ItemStack(ModBlocks.DARK_SPRUCE_LEAVES.get()))
                )))
                .unlockedBy(hasItem(VDItems.BLOOD_SYRUP.get()), has(VDItems.BLOOD_SYRUP.get()))
                .save(output);
    }

    private static void recipesPouring(RecipeOutput output) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.DANDELION_BEER_MUG.get(), 1)
                .requires(VDItems.DANDELION_BEER_BOTTLE.get())
                .requires(Items.GLASS_BOTTLE)
                .unlockedBy(hasItem(VDItems.DANDELION_BEER_BOTTLE.get()), has(VDItems.DANDELION_BEER_BOTTLE.get()))
                .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.BLOOD_WINE_GLASS.get(), 1)
                .requires(VDItems.BLOOD_WINE_BOTTLE.get())
                .requires(Items.GLASS_BOTTLE)
                .unlockedBy(hasItem(VDItems.BLOOD_WINE_BOTTLE.get()), has(VDItems.BLOOD_WINE_BOTTLE.get()))
                .save(output);
    }

    private static void recipesFoodBlocks(RecipeOutput output) {
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, VDItems.BLOOD_PIE.get(), 1)
                .pattern("sss")
                .pattern("wxw")
                .pattern("bOb")
                .define('w', Items.NETHER_WART)
                .define('b', VDItems.BLOOD_SYRUP.get())
                .define('x', Items.SUGAR)
                .define('s', Items.SWEET_BERRIES)
                .define('O', vectorwing.farmersdelight.common.registry.ModItems.PIE_CRUST.get())
                .unlockedBy(hasItem(VDItems.BLOOD_SYRUP.get()), has(VDItems.BLOOD_SYRUP.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, VDItems.BLOOD_PIE.get(), 1)
                .pattern("##")
                .pattern("##")
                .define('#', VDItems.BLOOD_PIE_SLICE.get())
                .unlockedBy(hasItem(VDItems.BLOOD_PIE_SLICE.get()), has(VDItems.BLOOD_PIE_SLICE.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "blood_pie_from_slices"));

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, VDItems.ORCHID_CAKE.get(), 1)
                .pattern("mmm")
                .pattern("oso")
                .pattern("www")
                .define('m', Tags.Items.DRINKS_MILK)
                .define('o', VDItems.ORCHID_PETALS.get())
                .define('s', Items.SUGAR)
                .define('w', Tags.Items.CROPS_WHEAT)
                .unlockedBy(hasItem(VDItems.ORCHID_PETALS.get()), has(VDItems.ORCHID_PETALS.get()))
                .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.ORCHID_CAKE.get(), 1)
                .requires(VDItems.ORCHID_CAKE_SLICE.get())
                .requires(VDItems.ORCHID_CAKE_SLICE.get())
                .requires(VDItems.ORCHID_CAKE_SLICE.get())
                .requires(VDItems.ORCHID_CAKE_SLICE.get())
                .requires(VDItems.ORCHID_CAKE_SLICE.get())
                .requires(VDItems.ORCHID_CAKE_SLICE.get())
                .requires(VDItems.ORCHID_CAKE_SLICE.get())
                .unlockedBy(hasItem(VDItems.ORCHID_CAKE_SLICE.get()), has(VDItems.ORCHID_CAKE_SLICE.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "orchid_cake_from_slices"));
    }

    private static void recipesCraftedMeals(RecipeOutput output) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.FISH_BURGER.get())
                .requires(VDCommonTags.FOODS_BREADS_RICE)
                .requires(Tags.Items.FOODS_COOKED_FISH)
                .requires(VDCommonTags.Items.FOODS_LEAFY_GREEN)
                .requires(VDCommonTags.CROPS_GARLIC)
                .unlockedBy("has_garlic", has(VDCommonTags.CROPS_GARLIC))
                .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.BLOOD_SAUSAGE.get())
                .requires(VDItems.BLOOD_SYRUP.get())
                .requires(VDCommonTags.Items.CROPS_ONION)
                .requires(Tags.Items.FOODS_COOKED_MEAT)
                .unlockedBy(hasItem(VDItems.BLOOD_SYRUP.get()), has(VDItems.BLOOD_SYRUP.get()))
                .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.BLOOD_HOT_DOG.get())
                .requires(VDItems.BLOOD_SAUSAGE.get())
                .requires(Tags.Items.FOODS_BREAD)
                .unlockedBy(hasItem(VDItems.BLOOD_SAUSAGE.get()), has(VDItems.BLOOD_SAUSAGE.get()))
                .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.BAGEL_SANDWICH.get())
                .requires(VDItems.BLOOD_BAGEL.get())
                .requires(VDCommonTags.Items.FOODS_COOKED_BACON)
                .requires(vectorwing.farmersdelight.common.registry.ModItems.FRIED_EGG.get())
                .unlockedBy(hasItem(VDItems.BLOOD_BAGEL.get()), has(VDItems.BLOOD_BAGEL.get()))
                .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.EYES_ON_STICK.get())
                .requires(VDItems.HUMAN_EYE.get())
                .requires(VDItems.HUMAN_EYE.get())
                .requires(VDCommonTags.Items.CROPS_TOMATO)
                .requires(Ingredient.fromValues(Stream.of(
                        new Ingredient.ItemValue(new ItemStack(Items.BROWN_MUSHROOM)),
                        new Ingredient.ItemValue(new ItemStack(Items.RED_MUSHROOM))
                )))
                .requires(Tags.Items.RODS_WOODEN)
                .unlockedBy(hasItem(VDItems.HUMAN_EYE.get()), has(VDItems.HUMAN_EYE.get()))
                .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.EYE_CROISSANT.get())
                .requires(Tags.Items.FOODS_BREAD)
                .requires(VDItems.HUMAN_EYE.get())
                .requires(VDItems.HUMAN_EYE.get())
                .requires(VDCommonTags.Items.FOODS_LEAFY_GREEN)
                .requires(VDCommonTags.Items.CROPS_TOMATO)
                .unlockedBy(hasItem(VDItems.HUMAN_EYE.get()), has(VDItems.HUMAN_EYE.get()))
                .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.BAT_TACO.get())
                .requires(Tags.Items.FOODS_BREAD)
                .requires(VDCommonTags.FOODS_COOKED_BAT)
                .requires(VDCommonTags.Items.FOODS_LEAFY_GREEN)
                .requires(VDCommonTags.Items.CROPS_TOMATO)
                .unlockedBy("has_cooked_bat", has(VDCommonTags.FOODS_COOKED_BAT))
                .save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.HARDTACK.get())
                .requires(Tags.Items.CROPS_WHEAT)
                .requires(Tags.Items.CROPS_WHEAT)
                .requires(Tags.Items.CROPS_WHEAT)
                .requires(Tags.Items.CROPS_WHEAT)
                .unlockedBy(hasItem(Items.WHEAT), has(Tags.Items.CROPS_WHEAT))
                .save(output);
    }

    private static void wineShelfRecipe(Block wineShelfBlock, Block slabBlock, Block planksBlock, RecipeOutput output) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, wineShelfBlock)
                .pattern("SSS")
                .pattern(" P ")
                .pattern("SSS")
                .define('S', slabBlock)
                .define('P', planksBlock)
                .unlockedBy(hasBlock(slabBlock), has(slabBlock))
                .save(output);
    }

    private static void barStoolRecipe(Block barStoolBlock, Block woolBlock, RecipeOutput output) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, barStoolBlock)
                .pattern("W")
                .pattern("F")
                .pattern("F")
                .define('F', ItemTags.WOODEN_FENCES)
                .define('W', woolBlock)
                .unlockedBy(hasBlock(woolBlock), has(woolBlock))
                .save(output);
    }

    private static void colorBlockWithDye(List<Item> pDyeableItems, RecipeOutput output) {
        for (int i = 0; i < DYES.size(); ++i) {
            Item dyeItem = DYES.get(i);
            Item dyeableItem = pDyeableItems.get(i);
            ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, dyeableItem)
                    .requires(dyeItem)
                    .requires(Ingredient.of(pDyeableItems.stream().filter(item -> !item.equals(dyeableItem)).map(ItemStack::new)))
                    .unlockedBy("has_needed_dye", has(dyeItem))
                    .save(output, ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "dye_" + itemName(dyeableItem)));
        }
    }

    public static final List<Item> DYES = List.of(Items.BLACK_DYE, Items.BLUE_DYE, Items.BROWN_DYE, Items.CYAN_DYE, Items.GRAY_DYE, Items.GREEN_DYE, Items.LIGHT_BLUE_DYE, Items.LIGHT_GRAY_DYE, Items.LIME_DYE, Items.MAGENTA_DYE, Items.ORANGE_DYE, Items.PINK_DYE, Items.PURPLE_DYE, Items.RED_DYE, Items.YELLOW_DYE, Items.WHITE_DYE);

    private static Criterion<?> has(ItemLike itemLike) {
        return InventoryChangeTrigger.TriggerInstance.hasItems(itemLike);
    }

    private static Criterion<?> has(TagKey<Item> tag) {
        return InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(tag).build());
    }

    private static String hasItem(ItemLike itemLike) {
        return "has_" + itemName(itemLike);
    }

    private static String hasBlock(Block block) {
        return "has_" + blockName(block);
    }
}
