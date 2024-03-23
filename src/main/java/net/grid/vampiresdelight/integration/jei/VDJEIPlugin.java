package net.grid.vampiresdelight.integration.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.grid.vampiresdelight.common.utility.VDTextUtils;
import net.grid.vampiresdelight.integration.jei.category.VDJEIPouringRecipeCategory;
import net.grid.vampiresdelight.integration.jei.resource.VDJEIPouringRecipe;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;

@JeiPlugin
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@SuppressWarnings("unused")
public class VDJEIPlugin implements IModPlugin {
    private static final ResourceLocation ID = new ResourceLocation(VampiresDelight.MODID, "jei_plugin");

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new VDJEIPouringRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        VDJEIPouringRecipe[] recipes = new VDJEIPouringRecipe[] {
                new VDJEIPouringRecipe(new ItemStack(VDItems.BLOOD_WINE_BOTTLE.get()), new ItemStack(VDItems.WINE_GLASS.get()), new ItemStack(Items.GLASS_BOTTLE))
        };
        registration.addRecipes(VDJEIRecipeTypes.POURING, new ArrayList<>(Arrays.asList(recipes)));

        registration.addIngredientInfo(new ItemStack(VDItems.HUMAN_EYE.get()), VanillaTypes.ITEM_STACK, VDTextUtils.getTranslation("jei.info.human_eye"));
        registration.addIngredientInfo(new ItemStack(VDItems.WILD_GARLIC.get()), VanillaTypes.ITEM_STACK, VDTextUtils.getTranslation("jei.info.wild_garlic"));
    }

    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }
}
