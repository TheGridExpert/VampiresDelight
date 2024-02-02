package net.grid.vampiresdelight.data.recipe;

import de.teamlapen.vampirism.core.ModBlocks;
import de.teamlapen.vampirism.core.ModItems;
import de.teamlapen.vampirism.core.ModTags;
import de.teamlapen.vampirism.data.recipebuilder.ShapelessWeaponTableRecipeBuilder;
import net.grid.vampiresdelight.common.registry.VDBlocks;
import net.grid.vampiresdelight.common.registry.VDRecipeSerializers;
import net.grid.vampiresdelight.common.tag.VDForgeTags;
import net.grid.vampiresdelight.data.builder.VDCuttingBoardRecipeBuilder;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import vectorwing.farmersdelight.common.tag.ForgeTags;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Consumer;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class VDCraftingRecipes {
    public static void register(Consumer<FinishedRecipe> consumer) {
        // Crafting
        recipesBlocks(consumer);
        recipesMaterials(consumer);
        recipesFoodstuffs(consumer);
        recipesPouring(consumer);
        recipesFoodBlocks(consumer);
        recipesCraftedMeals(consumer);
        // Cutting
        cuttingAnimalItems(consumer);
        cuttingFoods(consumer);
        cuttingFlowers(consumer);
        // Vampirism
        recipesWeaponTable(consumer);
        // Other
        SpecialRecipeBuilder.special(VDRecipeSerializers.BARREL_POURING.get()).save(consumer, "barrel_pouring");
    }

    // Crafting
    private static void recipesBlocks(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, VDBlocks.DARK_SPRUCE_CABINET.get())
                .pattern("___")
                .pattern("D D")
                .pattern("___")
                .define('_', ModBlocks.DARK_SPRUCE_SLAB.get())
                .define('D', ModBlocks.DARK_SPRUCE_TRAPDOOR.get())
                .unlockedBy("has_dark_spruce_trapdoor", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.DARK_SPRUCE_TRAPDOOR.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, VDBlocks.CURSED_SPRUCE_CABINET.get())
                .pattern("___")
                .pattern("D D")
                .pattern("___")
                .define('_', ModBlocks.CURSED_SPRUCE_SLAB.get())
                .define('D', ModBlocks.CURSED_SPRUCE_TRAPDOOR.get())
                .unlockedBy("has_cursed_spruce_trapdoor", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.CURSED_SPRUCE_TRAPDOOR.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, VDItems.GARLIC_CRATE.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', de.teamlapen.vampirism.core.ModItems.ITEM_GARLIC.get())
                .unlockedBy("has_garlic", InventoryChangeTrigger.TriggerInstance.hasItems(de.teamlapen.vampirism.core.ModItems.ITEM_GARLIC.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, VDItems.ORCHID_BAG.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', VDItems.ORCHID_PETALS.get())
                .unlockedBy("has_orchid_petals", InventoryChangeTrigger.TriggerInstance.hasItems(VDItems.ORCHID_PETALS.get()))
                .save(consumer);
    }

    private static void recipesMaterials(Consumer<FinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, de.teamlapen.vampirism.core.ModItems.ITEM_GARLIC.get(), 9)
                .requires(VDItems.GARLIC_CRATE.get())
                .unlockedBy("has_garlic_crate", InventoryChangeTrigger.TriggerInstance.hasItems(VDItems.GARLIC_CRATE.get()))
                .save(consumer, new ResourceLocation(VampiresDelight.MODID, "garlic_from_crate"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.ORCHID_PETALS.get(), 9)
                .requires(VDItems.ORCHID_BAG.get())
                .unlockedBy("has_orchid_bag", InventoryChangeTrigger.TriggerInstance.hasItems(VDItems.ORCHID_BAG.get()))
                .save(consumer, new ResourceLocation(VampiresDelight.MODID, "orchid_petals_from_bag"));
    }

    private static void recipesFoodstuffs(Consumer<FinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.RICE_DOUGH.get(), 3)
                .requires(Items.WATER_BUCKET)
                .requires(ForgeTags.CROPS_RICE)
                .requires(ForgeTags.CROPS_RICE)
                .requires(ForgeTags.CROPS_RICE)
                .unlockedBy("has_rice", InventoryChangeTrigger.TriggerInstance.hasItems(vectorwing.farmersdelight.common.registry.ModItems.RICE.get()))
                .save(consumer, new ResourceLocation(VampiresDelight.MODID, "rice_dough_from_water"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.RICE_DOUGH.get(), 3)
                .requires(ForgeTags.EGGS)
                .requires(ForgeTags.CROPS_RICE)
                .requires(ForgeTags.CROPS_RICE)
                .requires(ForgeTags.CROPS_RICE)
                .unlockedBy("has_rice", InventoryChangeTrigger.TriggerInstance.hasItems(vectorwing.farmersdelight.common.registry.ModItems.RICE.get()))
                .save(consumer, new ResourceLocation(VampiresDelight.MODID, "rice_dough_from_eggs"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.BLOOD_DOUGH.get(), 1)
                .requires(ModItems.BLOOD_BOTTLE.get())
                .requires(VDItems.RICE_DOUGH.get())
                .unlockedBy("has_blood_bottle", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.BLOOD_BOTTLE.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, VDItems.PURE_SORBET.get(), 1)
                .pattern(" sa")
                .pattern("ips")
                .pattern("ti ")
                .define('p', ModTags.Items.PURE_BLOOD)
                .define('s', Items.SWEET_BERRIES)
                .define('a', Items.APPLE)
                .define('i', Items.ICE)
                .define('t', Items.STICK)
                .unlockedBy("has_pure_blood", InventoryChangeTrigger.TriggerInstance.hasItems(Items.ICE))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.ORCHID_COOKIE.get(), 8)
                .requires(VDItems.ORCHID_PETALS.get())
                .requires(Items.WHEAT)
                .requires(Items.WHEAT)
                .unlockedBy("has_orchid_petals", InventoryChangeTrigger.TriggerInstance.hasItems(VDItems.ORCHID_PETALS.get()))
                .save(consumer, new ResourceLocation(VampiresDelight.MODID, "orchid_cookie_from_wheat"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.ORCHID_COOKIE.get(), 8)
                .requires(VDItems.ORCHID_PETALS.get())
                .requires(ForgeTags.CROPS_RICE)
                .requires(ForgeTags.CROPS_RICE)
                .unlockedBy("has_orchid_petals", InventoryChangeTrigger.TriggerInstance.hasItems(VDItems.ORCHID_PETALS.get()))
                .save(consumer, new ResourceLocation(VampiresDelight.MODID, "orchid_cookie_from_rice"));
    }

    private static void recipesPouring(Consumer<FinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.WINE_GLASS.get(), 1)
                .requires(VDItems.BLOOD_WINE_BOTTLE.get())
                .requires(Items.GLASS_BOTTLE)
                .unlockedBy("has_blood_wine_bottle", InventoryChangeTrigger.TriggerInstance.hasItems(VDItems.BLOOD_WINE_BOTTLE.get()))
                .save(consumer);
    }

    private static void recipesFoodBlocks(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, VDItems.BLOOD_PIE.get(), 1)
                .pattern("www")
                .pattern("xxx")
                .pattern("bOb")
                .define('w', Items.NETHER_WART)
                .define('b', ModItems.BLOOD_BOTTLE.get())
                .define('x', Items.SUGAR)
                .define('O', vectorwing.farmersdelight.common.registry.ModItems.PIE_CRUST.get())
                .unlockedBy("has_pie_crust", InventoryChangeTrigger.TriggerInstance.hasItems(vectorwing.farmersdelight.common.registry.ModItems.PIE_CRUST.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, VDItems.BLOOD_PIE.get(), 1)
                .pattern("##")
                .pattern("##")
                .define('#', VDItems.BLOOD_PIE_SLICE.get())
                .unlockedBy("has_blood_pie_slice", InventoryChangeTrigger.TriggerInstance.hasItems(VDItems.BLOOD_PIE_SLICE.get()))
                .save(consumer, new ResourceLocation(VampiresDelight.MODID, "blood_pie_from_slices"));
    }

    private static void recipesCraftedMeals(Consumer<FinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.FISH_BURGER.get())
                .requires(VDForgeTags.BREAD_RICE)
                .requires(ForgeTags.COOKED_FISHES)
                .requires(ForgeTags.SALAD_INGREDIENTS)
                .requires(VDForgeTags.VEGETABLES_GARLIC)
                .unlockedBy("has_garlic", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.ITEM_GARLIC.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.BAGEL_SANDWICH.get())
                .requires(VDItems.BLOOD_BAGEL.get())
                .requires(vectorwing.farmersdelight.common.registry.ModItems.SMOKED_HAM.get())
                .requires(vectorwing.farmersdelight.common.registry.ModItems.FRIED_EGG.get())
                .unlockedBy("has_blood_bagel", InventoryChangeTrigger.TriggerInstance.hasItems(VDItems.BLOOD_BAGEL.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.EYE_TOAST.get())
                .requires(ForgeTags.BREAD)
                .requires(VDItems.HUMAN_EYE.get())
                .requires(ForgeTags.CROPS_TOMATO)
                .requires(ModItems.BLOOD_BOTTLE.get())
                .unlockedBy("has_human_eye", InventoryChangeTrigger.TriggerInstance.hasItems(VDItems.HUMAN_EYE.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.HARDTACK.get())
                .requires(Items.WHEAT)
                .requires(Items.WHEAT)
                .requires(Items.WHEAT)
                .requires(Items.WHEAT)
                .unlockedBy("has_wheat", InventoryChangeTrigger.TriggerInstance.hasItems(Items.WHEAT))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.CURSED_CUPCAKE.get())
                .requires(VDItems.BLOOD_BAGEL.get())
                .requires(ForgeTags.MILK)
                .requires(Items.SUGAR)
                .unlockedBy("has_blood_bagel", InventoryChangeTrigger.TriggerInstance.hasItems(VDItems.BLOOD_BAGEL.get()))
                .save(consumer);
    }

    // Cutting
    private static void cuttingAnimalItems(Consumer<FinishedRecipe> consumer) {
        VDCuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModItems.HUMAN_HEART.get()), Ingredient.of(ForgeTags.TOOLS_KNIVES), VDItems.HEART_PIECES.get(), 2)
                .build(consumer);
    }
    private static void cuttingFoods(Consumer<FinishedRecipe> consumer) {
        VDCuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(VDItems.BLOOD_PIE.get()), Ingredient.of(ForgeTags.TOOLS_KNIVES), VDItems.BLOOD_PIE_SLICE.get(), 4)
                .build(consumer);
    }
    private static void cuttingFlowers(Consumer<FinishedRecipe> consumer) {
        VDCuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(VDItems.WILD_GARLIC.get()), Ingredient.of(ForgeTags.TOOLS_KNIVES), ModItems.ITEM_GARLIC.get(), 1)
                .addResult(Items.LIGHT_GRAY_DYE, 2)
                .addResultWithChance(Items.GREEN_DYE, 0.25F)
                .build(consumer);
        VDCuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModBlocks.VAMPIRE_ORCHID.get()), Ingredient.of(ForgeTags.TOOLS_KNIVES), VDItems.ORCHID_PETALS.get(), 2)
                .addResultWithChance(VDItems.ORCHID_SEEDS.get(), 0.7F, 2)
                .addResultWithChance(ModBlocks.CURSED_ROOTS.get(), 0.30F, 1)
                .build(consumer);
    }

    // Vampirism
    private static void recipesWeaponTable(Consumer<FinishedRecipe> consumer) {
        ShapelessWeaponTableRecipeBuilder.shapelessWeaponTable(RecipeCategory.COMBAT, VDItems.ALCHEMICAL_COCKTAIL.get())
                .lava(1)
                .requires(Items.GLASS_BOTTLE)
                .requires(ModItems.ITEM_ALCHEMICAL_FIRE.get(), 2)
                .unlockedBy("has_alchemical_fire", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.ITEM_ALCHEMICAL_FIRE.get()))
                .save(consumer);

    }
}
