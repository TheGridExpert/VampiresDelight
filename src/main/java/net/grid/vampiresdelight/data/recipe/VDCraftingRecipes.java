package net.grid.vampiresdelight.data.recipe;

import de.teamlapen.vampirism.core.ModBlocks;
import de.teamlapen.vampirism.core.ModItems;
import de.teamlapen.vampirism.core.ModTags;
import de.teamlapen.vampirism.data.recipebuilder.AlchemyTableRecipeBuilder;
import de.teamlapen.vampirism.data.recipebuilder.ShapelessWeaponTableRecipeBuilder;
import de.teamlapen.vampirism.util.NBTIngredient;
import net.grid.vampiresdelight.common.registry.*;
import net.grid.vampiresdelight.common.tag.VDForgeTags;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.grid.vampiresdelight.VampiresDelight;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;
import vectorwing.farmersdelight.common.tag.ForgeTags;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.data.builder.CuttingBoardRecipeBuilder;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;
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
        recipesAlchemyTable(consumer);
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
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, VDBlocks.OAK_WINE_SHELF.get())
                .pattern("SSS")
                .pattern(" P ")
                .pattern("SSS")
                .define('S', Blocks.OAK_SLAB)
                .define('P', Blocks.OAK_PLANKS)
                .unlockedBy("has_oak_slab", InventoryChangeTrigger.TriggerInstance.hasItems(Blocks.OAK_SLAB))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, VDBlocks.SPRUCE_WINE_SHELF.get())
                .pattern("SSS")
                .pattern(" P ")
                .pattern("SSS")
                .define('S', Blocks.SPRUCE_SLAB)
                .define('P', Blocks.SPRUCE_PLANKS)
                .unlockedBy("has_spruce_slab", InventoryChangeTrigger.TriggerInstance.hasItems(Blocks.SPRUCE_SLAB))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, VDBlocks.BIRCH_WINE_SHELF.get())
                .pattern("SSS")
                .pattern(" P ")
                .pattern("SSS")
                .define('S', Blocks.BIRCH_SLAB)
                .define('P', Blocks.BIRCH_PLANKS)
                .unlockedBy("has_birch_slab", InventoryChangeTrigger.TriggerInstance.hasItems(Blocks.BIRCH_SLAB))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, VDBlocks.JUNGLE_WINE_SHELF.get())
                .pattern("SSS")
                .pattern(" P ")
                .pattern("SSS")
                .define('S', Blocks.JUNGLE_SLAB)
                .define('P', Blocks.JUNGLE_PLANKS)
                .unlockedBy("has_jungle_slab", InventoryChangeTrigger.TriggerInstance.hasItems(Blocks.JUNGLE_SLAB))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, VDBlocks.ACACIA_WINE_SHELF.get())
                .pattern("SSS")
                .pattern(" P ")
                .pattern("SSS")
                .define('S', Blocks.ACACIA_SLAB)
                .define('P', Blocks.ACACIA_PLANKS)
                .unlockedBy("has_acacia_slab", InventoryChangeTrigger.TriggerInstance.hasItems(Blocks.ACACIA_SLAB))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, VDBlocks.DARK_OAK_WINE_SHELF.get())
                .pattern("SSS")
                .pattern(" P ")
                .pattern("SSS")
                .define('S', Blocks.DARK_OAK_SLAB)
                .define('P', Blocks.DARK_OAK_PLANKS)
                .unlockedBy("has_dark_oak_slab", InventoryChangeTrigger.TriggerInstance.hasItems(Blocks.DARK_OAK_SLAB))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, VDBlocks.MANGROVE_WINE_SHELF.get())
                .pattern("SSS")
                .pattern(" P ")
                .pattern("SSS")
                .define('S', Blocks.MANGROVE_SLAB)
                .define('P', Blocks.MANGROVE_PLANKS)
                .unlockedBy("has_mangrove_slab", InventoryChangeTrigger.TriggerInstance.hasItems(Blocks.MANGROVE_SLAB))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, VDBlocks.CHERRY_WINE_SHELF.get())
                .pattern("SSS")
                .pattern(" P ")
                .pattern("SSS")
                .define('S', Blocks.CHERRY_SLAB)
                .define('P', Blocks.CHERRY_PLANKS)
                .unlockedBy("has_cherry_slab", InventoryChangeTrigger.TriggerInstance.hasItems(Blocks.CHERRY_SLAB))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, VDBlocks.BAMBOO_WINE_SHELF.get())
                .pattern("SSS")
                .pattern(" P ")
                .pattern("SSS")
                .define('S', Blocks.BAMBOO_SLAB)
                .define('P', Blocks.BAMBOO_PLANKS)
                .unlockedBy("has_bamboo_slab", InventoryChangeTrigger.TriggerInstance.hasItems(Blocks.BAMBOO_SLAB))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, VDBlocks.CRIMSON_WINE_SHELF.get())
                .pattern("SSS")
                .pattern(" P ")
                .pattern("SSS")
                .define('S', Blocks.CRIMSON_SLAB)
                .define('P', Blocks.CRIMSON_PLANKS)
                .unlockedBy("has_crimson_slab", InventoryChangeTrigger.TriggerInstance.hasItems(Blocks.CRIMSON_SLAB))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, VDBlocks.WARPED_WINE_SHELF.get())
                .pattern("SSS")
                .pattern(" P ")
                .pattern("SSS")
                .define('S', Blocks.WARPED_SLAB)
                .define('P', Blocks.WARPED_PLANKS)
                .unlockedBy("has_warped_slab", InventoryChangeTrigger.TriggerInstance.hasItems(Blocks.WARPED_SLAB))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, VDBlocks.CURSED_SPRUCE_WINE_SHELF.get())
                .pattern("SSS")
                .pattern(" P ")
                .pattern("SSS")
                .define('S', ModBlocks.CURSED_SPRUCE_SLAB.get())
                .define('P', ModBlocks.CURSED_SPRUCE_PLANKS.get())
                .unlockedBy("has_cursed_spruce_slab", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.CURSED_SPRUCE_SLAB.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, VDBlocks.DARK_SPRUCE_WINE_SHELF.get())
                .pattern("SSS")
                .pattern(" P ")
                .pattern("SSS")
                .define('S', ModBlocks.DARK_SPRUCE_SLAB.get())
                .define('P', ModBlocks.DARK_SPRUCE_PLANKS.get())
                .unlockedBy("has_dark_spruce_slab", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.DARK_SPRUCE_SLAB.get()))
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
                .requires(VDItems.BLOOD_SYRUP.get())
                .requires(VDItems.RICE_DOUGH.get())
                .unlockedBy("has_blood_syrup", InventoryChangeTrigger.TriggerInstance.hasItems(VDItems.BLOOD_SYRUP.get()))
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
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.SUGARED_BERRIES.get(), 1)
                .requires(Items.SWEET_BERRIES)
                .requires(Items.SUGAR)
                .unlockedBy("has_sweet_berries", InventoryChangeTrigger.TriggerInstance.hasItems(Items.SWEET_BERRIES))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.ORCHID_ECLAIR.get(), 1)
                .requires(VDItems.ORCHID_PETALS.get())
                .requires(ForgeTags.BREAD)
                .requires(Items.SWEET_BERRIES)
                .unlockedBy("has_orchid_petals", InventoryChangeTrigger.TriggerInstance.hasItems(VDItems.ORCHID_PETALS.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.ORCHID_ICE_CREAM.get(), 1)
                .requires(VDItems.ORCHID_PETALS.get())
                .requires(VDItems.ORCHID_PETALS.get())
                .requires(ForgeTags.MILK)
                .requires(Items.ICE)
                .requires(Items.SUGAR)
                .requires(Items.BOWL)
                .unlockedBy("has_orchid_petals", InventoryChangeTrigger.TriggerInstance.hasItems(VDItems.ORCHID_PETALS.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.SNOW_WHITE_ICE_CREAM.get(), 1)
                .requires(Items.COCOA_BEANS)
                .requires(ForgeTags.MILK)
                .requires(ModTags.Items.HOLY_WATER)
                .requires(Items.SUGAR)
                .requires(Items.BOWL)
                .unlockedBy("has_holy_water", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.HOLY_WATER_BOTTLE_NORMAL.get(), ModItems.HOLY_WATER_BOTTLE_ENHANCED.get(), ModItems.HOLY_WATER_BOTTLE_ULTIMATE.get()))
                .save(consumer);
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
                .define('b', VDItems.BLOOD_SYRUP.get())
                .define('x', Items.SUGAR)
                .define('O', vectorwing.farmersdelight.common.registry.ModItems.PIE_CRUST.get())
                .unlockedBy("has_blood_syrup", InventoryChangeTrigger.TriggerInstance.hasItems(VDItems.BLOOD_SYRUP.get()))
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
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, VDItems.EYE_CROISSANT.get())
                .requires(ForgeTags.BREAD)
                .requires(VDItems.HUMAN_EYE.get())
                .requires(VDItems.HUMAN_EYE.get())
                .requires(ForgeTags.SALAD_INGREDIENTS)
                .requires(ForgeTags.CROPS_TOMATO)
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
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModItems.HUMAN_HEART.get()), Ingredient.of(ForgeTags.TOOLS_KNIVES), VDItems.HEART_PIECES.get(), 2)
                .build(consumer, itemLocationCutting(ModItems.HUMAN_HEART.get()));
    }

    private static void cuttingFoods(Consumer<FinishedRecipe> consumer) {
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(VDItems.BLOOD_PIE.get()), Ingredient.of(ForgeTags.TOOLS_KNIVES), VDItems.BLOOD_PIE_SLICE.get(), 4)
                .build(consumer, itemLocationCutting(VDItems.BLOOD_PIE.get()));
    }

    private static void cuttingFlowers(Consumer<FinishedRecipe> consumer) {
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(VDItems.WILD_GARLIC.get()), Ingredient.of(ForgeTags.TOOLS_KNIVES), ModItems.ITEM_GARLIC.get(), 1)
                .addResult(Items.LIGHT_GRAY_DYE, 2)
                .addResultWithChance(Items.GREEN_DYE, 0.25F)
                .build(consumer, itemLocationCutting(VDItems.WILD_GARLIC.get()));
        CuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModBlocks.VAMPIRE_ORCHID.get()), Ingredient.of(ForgeTags.TOOLS_KNIVES), VDItems.ORCHID_PETALS.get(), 2)
                .addResultWithChance(VDItems.ORCHID_SEEDS.get(), 0.7F, 2)
                .addResultWithChance(ModBlocks.CURSED_ROOTS.get(), 0.30F, 1)
                .build(consumer, blockLocationCutting(ModBlocks.VAMPIRE_ORCHID.get()));
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

    private static void recipesAlchemyTable(Consumer<FinishedRecipe> consumer) {
        AlchemyTableRecipeBuilder
                .builder(VDOils.FOG_VISION)
                .bloodOilIngredient()
                .input(potion(VDPotions.FOG_VISION.get(), VDPotions.STRONG_FOG_VISION.get(), VDPotions.LONG_FOG_VISION.get()))
                .build(consumer, new ResourceLocation(VampiresDelight.MODID, "fog_vision_oil"));
    }

    private static @NotNull Ingredient potion(Potion @NotNull ... potion) {
        return new NBTIngredient(Arrays.stream(potion).map(p -> PotionUtils.setPotion(new ItemStack(Items.POTION, 1), p)).toArray(ItemStack[]::new));
    }

    public static ResourceLocation itemLocationCutting(Item item) {
        return new ResourceLocation(VampiresDelight.MODID + ":cutting/" + ForgeRegistries.ITEMS.getKey(item).getPath());
    }
    public static ResourceLocation blockLocationCutting(Block block) {
        return new ResourceLocation(VampiresDelight.MODID + ":cutting/" + ForgeRegistries.BLOCKS.getKey(block).getPath());
    }
}
