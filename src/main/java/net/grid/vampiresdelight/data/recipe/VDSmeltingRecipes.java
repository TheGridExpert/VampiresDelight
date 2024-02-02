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
        foodSmeltingRecipes("grilled_garlic", ModItems.ITEM_GARLIC.get(), VDItems.GRILLED_GARLIC.get(), 0.35F, consumer);

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(VDItems.RICE_DOUGH.get()), RecipeCategory.FOOD,
                        VDItems.RICE_BREAD.get(), 0.35F, 200)
                .unlockedBy("has_rice_dough", InventoryChangeTrigger.TriggerInstance.hasItems(VDItems.RICE_DOUGH.get()))
                .save(consumer, new ResourceLocation(VampiresDelight.MODID, "rice_bread") + "_from_smelting");
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(VDItems.RICE_DOUGH.get()), RecipeCategory.FOOD,
                        VDItems.RICE_BREAD.get(), 0.35F, 100)
                .unlockedBy("has_rice_dough", InventoryChangeTrigger.TriggerInstance.hasItems(VDItems.RICE_DOUGH.get()))
                .save(consumer, new ResourceLocation(VampiresDelight.MODID, "rice_bread") + "_from_smoking");

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(VDItems.BLOOD_DOUGH.get()), RecipeCategory.FOOD,
                        VDItems.BLOOD_BAGEL.get(), 0.35F, 200)
                .unlockedBy("has_blood_dough", InventoryChangeTrigger.TriggerInstance.hasItems(VDItems.BLOOD_DOUGH.get()))
                .save(consumer, new ResourceLocation(VampiresDelight.MODID, "blood_bagel") + "_from_smelting");
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(VDItems.BLOOD_DOUGH.get()), RecipeCategory.FOOD,
                        VDItems.BLOOD_BAGEL.get(), 0.35F, 100)
                .unlockedBy("has_blood_dough", InventoryChangeTrigger.TriggerInstance.hasItems(VDItems.BLOOD_DOUGH.get()))
                .save(consumer, new ResourceLocation(VampiresDelight.MODID, "blood_bagel") + "_from_smoking");
    }

    private static void foodSmeltingRecipes(String name, ItemLike ingredient, ItemLike result, float experience, Consumer<FinishedRecipe> consumer) {
        String namePrefix = new ResourceLocation(VampiresDelight.MODID, name).toString();
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ingredient), RecipeCategory.FOOD, result, experience, 200)
                .unlockedBy(name, InventoryChangeTrigger.TriggerInstance.hasItems(ingredient))
                .save(consumer);
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(ingredient), RecipeCategory.FOOD, result, experience, 600)
                .unlockedBy(name, InventoryChangeTrigger.TriggerInstance.hasItems(ingredient))
                .save(consumer, namePrefix + "_from_campfire_cooking");
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(ingredient), RecipeCategory.FOOD, result, experience, 100)
                .unlockedBy(name, InventoryChangeTrigger.TriggerInstance.hasItems(ingredient))
                .save(consumer, namePrefix + "_from_smoking");
    }
}
