package net.grid.vampiresdelight.integration.jei;

import mezz.jei.api.recipe.RecipeType;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.integration.jei.resource.VDJEIPouringRecipe;

public class VDJEIRecipeTypes {
    public static final RecipeType<VDJEIPouringRecipe> POURING = RecipeType.create(VampiresDelight.MODID, "pouring", VDJEIPouringRecipe.class);
}
