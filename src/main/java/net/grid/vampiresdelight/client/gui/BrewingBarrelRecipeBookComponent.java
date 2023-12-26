package net.grid.vampiresdelight.client.gui;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.crafting.BrewingBarrelRecipe;
import net.grid.vampiresdelight.common.utility.VDTextUtils;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.network.chat.Component;

import javax.annotation.Nonnull;
import java.util.List;

public class BrewingBarrelRecipeBookComponent extends RecipeBookComponent {
    protected static final ResourceLocation RECIPE_BOOK_BUTTONS = new ResourceLocation(VampiresDelight.MODID, "textures/gui/recipe_book_buttons.png");

    @Override
    protected void initFilterButtonTextures() {
        this.filterButton.initTextureValues(0, 0, 28, 18, RECIPE_BOOK_BUTTONS);
    }

    public void hide() {
        this.setVisible(false);
    }

    @Override
    @Nonnull
    protected Component getRecipeFilterName() {
        return VDTextUtils.getTranslation("container.recipe_book.fermentable");
    }

    @Override
    public void setupGhostRecipe(Recipe<?> recipe, List<Slot> slots) {
        ItemStack resultStack = recipe.getResultItem(this.minecraft.level.registryAccess());
        this.ghostRecipe.setRecipe(recipe);
        if (slots.get(4).getItem().isEmpty()) {
            this.ghostRecipe.addIngredient(Ingredient.of(resultStack), (slots.get(4)).x, (slots.get(4)).y);
        }

        if (recipe instanceof BrewingBarrelRecipe brewingRecipe) {
            ItemStack containerStack = brewingRecipe.getOutputContainer();
            if (!containerStack.isEmpty()) {
                this.ghostRecipe.addIngredient(Ingredient.of(containerStack), (slots.get(7)).x, (slots.get(7)).y);
            }
        }

        this.placeRecipe(this.menu.getGridWidth(), this.menu.getGridHeight(), this.menu.getResultSlotIndex(), recipe, recipe.getIngredients().iterator(), 0);
    }
}
