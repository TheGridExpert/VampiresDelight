package net.grid.vampiresdelight.client.recipebook;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.crafting.BrewingBarrelRecipe;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.grid.vampiresdelight.common.registry.VDRecipeTypes;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.client.event.RegisterRecipeBookCategoriesEvent;
import vectorwing.farmersdelight.common.registry.ModItems;
import java.util.function.Supplier;

public class VDRecipeCategories {
    public static final Supplier<RecipeBookCategories> FERMENTING_SEARCH = Suppliers.memoize(() -> RecipeBookCategories.create("FERMENTING_SEARCH", new ItemStack(Items.COMPASS)));
    public static final Supplier<RecipeBookCategories> FERMENTING_DRINKS = Suppliers.memoize(() -> RecipeBookCategories.create("FERMENTING_DRINKS", new ItemStack(VDItems.BLOOD_WINE_BOTTLE.get())));
    public static final Supplier<RecipeBookCategories> FERMENTING_MISC = Suppliers.memoize(() -> RecipeBookCategories.create("FERMENTING_MISC", new ItemStack(ModItems.DUMPLINGS.get()), new ItemStack(ModItems.TOMATO_SAUCE.get())));

    public static void init(RegisterRecipeBookCategoriesEvent event) {
        event.registerBookCategories(VampiresDelight.RECIPE_TYPE_FERMENTING, ImmutableList.of(FERMENTING_SEARCH.get(), FERMENTING_DRINKS.get(), FERMENTING_MISC.get()));
        event.registerAggregateCategory(FERMENTING_SEARCH.get(), ImmutableList.of(FERMENTING_DRINKS.get(), FERMENTING_MISC.get()));
        event.registerRecipeCategoryFinder(VDRecipeTypes.FERMENTING.get(), recipe ->
        {
            if (recipe instanceof BrewingBarrelRecipe fermentingRecipe) {
                BrewingBarrelRecipeBookTab tab = fermentingRecipe.getRecipeBookTab();
                if (tab != null) {
                    return switch (tab) {
                        case DRINKS -> FERMENTING_DRINKS.get();
                        case MISC -> FERMENTING_MISC.get();
                    };
                }
            }
            return FERMENTING_DRINKS.get();
        });
    }
}
