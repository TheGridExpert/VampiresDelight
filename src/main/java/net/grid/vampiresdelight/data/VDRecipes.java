package net.grid.vampiresdelight.data;

import net.grid.vampiresdelight.data.recipe.VDCookingRecipes;
import net.grid.vampiresdelight.data.recipe.VDCraftingRecipes;
import net.grid.vampiresdelight.data.recipe.VDSmeltingRecipes;
import net.grid.vampiresdelight.data.recipe.VDVampirismRecipes;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Consumer;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class VDRecipes extends RecipeProvider {
    public VDRecipes(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        VDCraftingRecipes.register(consumer);
        VDSmeltingRecipes.register(consumer);
        VDCookingRecipes.register(consumer);
        VDVampirismRecipes.register(consumer);
    }
}
