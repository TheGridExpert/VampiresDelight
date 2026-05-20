package net.grid.vampiresdelight.integration.jei.category;

import de.teamlapen.vampirism.core.ModTags;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.core.VDItems;
import net.grid.vampiresdelight.integration.jei.VDJEIRecipeTypes;
import net.grid.vampiresdelight.integration.jei.resource.VDJEISpillingBloodRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

public class VDJEISpillingBloodRecipeCategory implements IRecipeCategory<VDJEISpillingBloodRecipe> {

    private final Component title;
    private final IDrawable background;
    private final IDrawable icon;

    public VDJEISpillingBloodRecipeCategory(IGuiHelper helper) {
        this.title = Component.translatable("gui." + VampiresDelight.MODID + ".jei.spilling_blood");

        ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "textures/gui/jei/spilling_blood.png");
        background = helper.createDrawable(texture, 0, 0, 117, 57);

        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(VDItems.BLOODY_SOIL.get()));
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, VDJEISpillingBloodRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 23, 5)
                .addIngredients(Ingredient.of(ModTags.Items.PURE_BLOOD))
                .setSlotName("PureBlood");
        builder.addSlot(RecipeIngredientRole.INPUT, 23, 36)
                .addItemStack(recipe.richSoilBlock())
                .setSlotName("RichSoil");
        builder.addSlot(RecipeIngredientRole.OUTPUT, 80, 19)
                .addItemStack(recipe.bloodySoilBlock())
                .setSlotName("BloodySoil");
    }

    @Override
    public RecipeType<VDJEISpillingBloodRecipe> getRecipeType() {
        return VDJEIRecipeTypes.SPILLING_BLOOD;
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
    public @Nullable IDrawable getIcon() {
        return this.icon;
    }
}