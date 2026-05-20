package net.grid.vampiresdelight.integration.jei;

import mezz.jei.api.recipe.RecipeType;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.integration.jei.resource.VDJEIPouringRecipe;
import net.grid.vampiresdelight.integration.jei.resource.VDJEISpillingBloodRecipe;

public class VDJEIRecipeTypes {

    public static final RecipeType<VDJEIPouringRecipe> POURING = RecipeType.create(VampiresDelight.MODID, "pouring", VDJEIPouringRecipe.class);
    public static final RecipeType<VDJEISpillingBloodRecipe> SPILLING_BLOOD = RecipeType.create(VampiresDelight.MODID, "spilling_blood", VDJEISpillingBloodRecipe.class);
}