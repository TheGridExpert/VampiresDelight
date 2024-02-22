package net.grid.vampiresdelight.data.recipe;

import de.teamlapen.vampirism.core.ModItems;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.function.Consumer;

public class VDSmeltingRecipes {
    public static void register(Consumer<FinishedRecipe> consumer) {
        multipleSmeltingRecipes("grilled_garlic", ModItems.ITEM_GARLIC.get(), VDItems.GRILLED_GARLIC.get(),
                0.35F, true, true, true, consumer);
        multipleSmeltingRecipes("rice_bread", VDItems.RICE_DOUGH.get(), VDItems.RICE_BREAD.get(),
                0.35F, true, true, false, consumer);
        multipleSmeltingRecipes("blood_bagel", VDItems.BLOOD_DOUGH.get(), VDItems.BLOOD_BAGEL.get(),
                0.35F, true, true, false, consumer);
    }

    private static void multipleSmeltingRecipes(String name, ItemLike ingredient, ItemLike result, float experience, boolean hasSmeltingRecipe, boolean hasSmokingRecipe, boolean hasCampfireRecipe, Consumer<FinishedRecipe> consumer) {
        String namePrefix = new ResourceLocation(VampiresDelight.MODID, name).toString();
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
}
