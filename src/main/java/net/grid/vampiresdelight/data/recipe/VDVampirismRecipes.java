package net.grid.vampiresdelight.data.recipe;

import de.teamlapen.vampirism.core.ModItems;
import de.teamlapen.vampirism.data.recipebuilder.ShapelessWeaponTableRecipeBuilder;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Items;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Consumer;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class VDVampirismRecipes {
    public static void register(Consumer<FinishedRecipe> consumer) {
        recipesWeaponTable(consumer);
    }

    private static void recipesWeaponTable(Consumer<FinishedRecipe> consumer) {
        ShapelessWeaponTableRecipeBuilder.shapelessWeaponTable(VDItems.ALCHEMICAL_COCKTAIL.get())
                .lava(1)
                .requires(Items.GLASS_BOTTLE)
                .requires(ModItems.ITEM_ALCHEMICAL_FIRE.get(), 2)
                .unlockedBy("has_alchemical_fire", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.ITEM_ALCHEMICAL_FIRE.get()))
                .save(consumer);

    }
}
