package net.grid.vampiresdelight.data;

import net.grid.vampiresdelight.data.recipe.VDCookingRecipes;
import net.grid.vampiresdelight.data.recipe.VDCraftingRecipes;
import net.grid.vampiresdelight.data.recipe.VDSmeltingRecipes;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Consumer;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class VDRecipes extends RecipeProvider {
    public VDRecipes(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        VDCraftingRecipes.register(consumer);
        VDSmeltingRecipes.register(consumer);
        VDCookingRecipes.register(consumer);
    }
}
