package net.grid.vampiresdelight.data.recipe;

import de.teamlapen.vampirism.core.ModBlocks;
import de.teamlapen.vampirism.core.ModItems;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.grid.vampiresdelight.data.builder.VDCookingPotRecipeBuilder;
import net.minecraft.data.recipes.FinishedRecipe;
import vectorwing.farmersdelight.client.recipebook.CookingPotRecipeBookTab;
import vectorwing.farmersdelight.common.tag.ForgeTags;

import java.util.function.Consumer;

public class VDCookingRecipes {
    public static final int FAST_COOKING = 100;      // 5 seconds
    public static final int NORMAL_COOKING = 200;    // 10 seconds
    public static final int SLOW_COOKING = 400;      // 20 seconds

    public static final float SMALL_EXP = 0.35F;
    public static final float MEDIUM_EXP = 1.0F;
    public static final float LARGE_EXP = 2.0F;

    public static void register(Consumer<FinishedRecipe> consumer) {
        cookMiscellaneous(consumer);
        cookMeals(consumer);
    }

    private static void cookMiscellaneous(Consumer<FinishedRecipe> consumer) {
        VDCookingPotRecipeBuilder.cookingPotRecipe(VDItems.ORCHID_TEA.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(ForgeTags.MILK)
                .addIngredient(ModBlocks.VAMPIRE_ORCHID.get())
                .addIngredient(ModBlocks.VAMPIRE_ORCHID.get())
                .unlockedByAnyIngredient(ModBlocks.VAMPIRE_ORCHID.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.DRINKS)
                .build(consumer);
    }

    private static void cookMeals(Consumer<FinishedRecipe> consumer) {
        VDCookingPotRecipeBuilder.cookingPotRecipe(VDItems.GARLIC_SOUP.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                .addIngredient(ForgeTags.RAW_CHICKEN)
                .addIngredient(VDItems.GRILLED_GARLIC.get())
                .addIngredient(ForgeTags.VEGETABLES)
                .unlockedByAnyIngredient(VDItems.GRILLED_GARLIC.get(), ModItems.ITEM_GARLIC.get())
                .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                .build(consumer);
    }
}
