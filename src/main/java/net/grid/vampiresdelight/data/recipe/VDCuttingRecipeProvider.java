package net.grid.vampiresdelight.data.recipe;

import de.teamlapen.vampirism.blocks.LogBlock;
import de.teamlapen.vampirism.core.ModBlocks;
import de.teamlapen.vampirism.core.ModItems;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.grid.vampiresdelight.data.builder.VDCuttingBoardRecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.state.properties.WoodType;
import vectorwing.farmersdelight.data.builder.CuttingBoardRecipeBuilder;
import vectorwing.farmersdelight.data.recipe.CuttingRecipes;

public class VDCuttingRecipeProvider {

    public static void register(RecipeOutput output) {
        // Knife
        cuttingAnimalItems(output);
        cuttingFoods(output);
        cuttingFlowers(output);

        // Pickaxe
        salvagingMinerals(output);

        // Axe
        strippingWood(output);
        salvagingWoodenFurniture(output);

        // Shears
        salvagingUsingShears(output);
    }

    private static void cuttingAnimalItems(RecipeOutput output) {
        VDCuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModItems.HUMAN_HEART.get()), CuttingRecipes.KNIVES, VDItems.HEART_PIECES.get(), 2)
                .save(output);
        VDCuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(VDItems.RAW_BAT.get()), CuttingRecipes.KNIVES, VDItems.RAW_BAT_CHOPS.get(), 2)
                .save(output);
    }

    private static void cuttingFoods(RecipeOutput output) {
        VDCuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(VDItems.BLOOD_PIE.get()), CuttingRecipes.KNIVES, VDItems.BLOOD_PIE_SLICE.get(), 4)
                .save(output);
        VDCuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(VDItems.ORCHID_CAKE.get()), CuttingRecipes.KNIVES, VDItems.ORCHID_CAKE_SLICE.get(), 7)
                .save(output);
    }

    private static void cuttingFlowers(RecipeOutput output) {
        VDCuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(VDItems.WILD_GARLIC.get()), CuttingRecipes.KNIVES, ModBlocks.GARLIC.get(), 1)
                .addResult(Items.LIGHT_GRAY_DYE, 2)
                .addResultWithChance(Items.GREEN_DYE, 0.25F)
                .save(output);
        VDCuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModBlocks.VAMPIRE_ORCHID.get()), CuttingRecipes.KNIVES, VDItems.ORCHID_PETALS.get(), 2)
                .addResultWithChance(VDItems.ORCHID_SEEDS.get(), 0.7F, 2)
                .addResultWithChance(ModBlocks.CURSED_ROOTS.get(), 0.30F, 1)
                .save(output);
    }

    private static void salvagingMinerals(RecipeOutput output) {
        VDCuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModBlocks.DARK_STONE.get()), CuttingRecipes.PICKAXES, ModBlocks.COBBLED_DARK_STONE.get(), 1)
                .salvaging()
                .save(output);
    }

    private static void strippingWood(RecipeOutput output) {
        stripLogForBark(output, ModBlocks.CURSED_SPRUCE_LOG.get(), ModBlocks.STRIPPED_CURSED_SPRUCE_LOG.get());
        stripLogForBark(output, ModBlocks.DARK_SPRUCE_LOG.get(), ModBlocks.STRIPPED_DARK_SPRUCE_LOG.get());
    }

    private static void salvagingWoodenFurniture(RecipeOutput output) {
        salvagePlankFromFurniture(output, LogBlock.CURSED_SPRUCE, ModBlocks.CURSED_SPRUCE_PLANKS.get(), ModBlocks.CURSED_SPRUCE_DOOR.get(), ModBlocks.CURSED_SPRUCE_TRAPDOOR.get(), ModBlocks.CURSED_SPRUCE_SIGN.get(), ModBlocks.CURSED_SPRUCE_HANGING_SIGN.get());
        salvagePlankFromFurniture(output, LogBlock.DARK_SPRUCE, ModBlocks.DARK_SPRUCE_PLANKS.get(), ModBlocks.DARK_SPRUCE_DOOR.get(), ModBlocks.DARK_SPRUCE_TRAPDOOR.get(), ModBlocks.DARK_SPRUCE_SIGN.get(), ModBlocks.DARK_SPRUCE_HANGING_SIGN.get());
    }

    private static void salvagingUsingShears(RecipeOutput output) {
        VDCuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModItems.ARMOR_OF_SWIFTNESS_HEAD_NORMAL.get(), ModItems.ARMOR_OF_SWIFTNESS_CHEST_NORMAL.get(), ModItems.ARMOR_OF_SWIFTNESS_LEGS_NORMAL.get(), ModItems.ARMOR_OF_SWIFTNESS_FEET_NORMAL.get()), CuttingRecipes.SHEARS, Items.LEATHER, 1)
                .save(output, salvagingRecipe("armor_of_swiftness_normal"));
        VDCuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModItems.ARMOR_OF_SWIFTNESS_HEAD_ENHANCED.get(), ModItems.ARMOR_OF_SWIFTNESS_CHEST_ENHANCED.get(), ModItems.ARMOR_OF_SWIFTNESS_LEGS_ENHANCED.get(), ModItems.ARMOR_OF_SWIFTNESS_FEET_ENHANCED.get()), CuttingRecipes.SHEARS, Items.LEATHER, 2)
                .addResultWithChance(Items.GOLD_INGOT, 0.65f, 2)
                .save(output, salvagingRecipe("armor_of_swiftness_enchanced"));
        VDCuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModItems.ARMOR_OF_SWIFTNESS_HEAD_ULTIMATE.get(), ModItems.ARMOR_OF_SWIFTNESS_CHEST_ULTIMATE.get(), ModItems.ARMOR_OF_SWIFTNESS_LEGS_ULTIMATE.get(), ModItems.ARMOR_OF_SWIFTNESS_FEET_ULTIMATE.get()), CuttingRecipes.SHEARS, Items.LEATHER, 2)
                .addResultWithChance(Items.DIAMOND, 0.4f)
                .save(output, salvagingRecipe("armor_of_swiftness_ultimate"));
    }

    private static void stripLogForBark(RecipeOutput output, ItemLike log, ItemLike strippedLog) {
        VDCuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(log), CuttingRecipes.AXES_STRIP, strippedLog)
                .addResult(vectorwing.farmersdelight.common.registry.ModItems.TREE_BARK.get())
                .addSound(SoundEvents.AXE_STRIP)
                .save(output);
    }

    private static void salvagePlankFromFurniture(RecipeOutput output, WoodType woodType, ItemLike plank, ItemLike... furniture) {
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(furniture), CuttingRecipes.AXES, plank, 1, 0.75F)
                .save(output, salvagingRecipe(woodType.name() + "_furniture"));
    }

    private static ResourceLocation salvagingRecipe(String name) {
        if (name.contains(":")) {
            name = name.substring(name.lastIndexOf(':') + 1);
        }
        return ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "salvaging/" + name);
    }
}
