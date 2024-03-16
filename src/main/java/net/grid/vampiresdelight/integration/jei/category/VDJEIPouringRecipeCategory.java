package net.grid.vampiresdelight.integration.jei.category;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.grid.vampiresdelight.common.utility.VDTextUtils;
import net.grid.vampiresdelight.integration.jei.VDJEIRecipeTypes;
import net.grid.vampiresdelight.integration.jei.resource.VDJEIPouringRecipe;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class VDJEIPouringRecipeCategory implements IRecipeCategory<VDJEIPouringRecipe> {
    private final Component title;
    private final IDrawable background;
    private final IDrawable icon;

    public VDJEIPouringRecipeCategory(IGuiHelper helper) {
        title = VDTextUtils.getTranslation("jei.pouring");

        ResourceLocation texture = new ResourceLocation(VampiresDelight.MODID, "textures/gui/jei/pouring.png");
        background = helper.createDrawable(texture, 0, 0, 117, 57);

        icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(VDItems.WINE_GLASS.get()));
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, VDJEIPouringRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 19, 12)
                .addItemStack(recipe.getTool())
                .setSlotName("Bottle");
        builder.addSlot(RecipeIngredientRole.INPUT, 83, 12)
                .addItemStack(recipe.getServingContainer())
                .setSlotName("ServingContainer");
        builder.addSlot(RecipeIngredientRole.OUTPUT, 52, 31)
                .addItemStack(recipe.getResult())
                .setSlotName("Drink");
    }

    @Override
    public RecipeType<VDJEIPouringRecipe> getRecipeType() {
        return VDJEIRecipeTypes.POURING;
    }

    @Override
    public Component getTitle() {
        return this.title;
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }
}
