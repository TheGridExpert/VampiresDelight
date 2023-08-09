package net.grid.vampiresdelight.data.recipe;

import de.teamlapen.vampirism.core.ModBlocks;
import de.teamlapen.vampirism.core.ModItems;
import net.grid.vampiresdelight.common.registry.VDBlocks;
import net.grid.vampiresdelight.data.builder.VDCuttingBoardRecipeBuilder;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
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
        // Cutting
        cuttingFlowers(consumer);
    }

    // Crafting
    private static void recipesBlocks(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(VDBlocks.CURSED_SPRUCE_CABINET.get())
                .pattern("___")
                .pattern("D D")
                .pattern("___")
                .define('_', ModBlocks.CURSED_SPRUCE_SLAB.get())
                .define('D', ModBlocks.CURSED_SPRUCE_TRAPDOOR.get())
                .unlockedBy("has_cursed_spruce_trapdoor", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.CURSED_SPRUCE_TRAPDOOR.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(VDItems.GARLIC_CRATE.get(), 1)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', de.teamlapen.vampirism.core.ModItems.ITEM_GARLIC.get())
                .unlockedBy("has_garlic", InventoryChangeTrigger.TriggerInstance.hasItems(de.teamlapen.vampirism.core.ModItems.ITEM_GARLIC.get()))
                .save(consumer);
    }

    private static void recipesMaterials(Consumer<FinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapeless(de.teamlapen.vampirism.core.ModItems.ITEM_GARLIC.get(), 9)
                .requires(VDItems.GARLIC_CRATE.get())
                .unlockedBy("has_garlic_crate", InventoryChangeTrigger.TriggerInstance.hasItems(VDItems.GARLIC_CRATE.get()))
                .save(consumer, new ResourceLocation(VampiresDelight.MODID, "garlic_from_crate"));
    }

    // Cutting
    private static void cuttingFlowers(Consumer<FinishedRecipe> consumer) {
        VDCuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(VDItems.WILD_GARLIC.get()), Ingredient.of(ForgeTags.TOOLS_KNIVES), ModItems.ITEM_GARLIC.get(), 1)
                .addResult(Items.LIGHT_GRAY_DYE, 2)
                .addResultWithChance(Items.GREEN_DYE, 0.25F)
                .build(consumer);
        VDCuttingBoardRecipeBuilder.cuttingRecipe(Ingredient.of(ModBlocks.VAMPIRE_ORCHID.get()), Ingredient.of(ForgeTags.TOOLS_KNIVES), Items.MAGENTA_DYE, 2)
                .addResultWithChance(ModBlocks.CURSED_ROOTS.get(), 0.75F, 1)
                .build(consumer);
    }
}
