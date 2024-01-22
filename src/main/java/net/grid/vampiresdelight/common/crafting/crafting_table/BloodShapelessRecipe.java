package net.grid.vampiresdelight.common.crafting.crafting_table;

import com.google.gson.JsonObject;
import de.teamlapen.vampirism.core.ModItems;
import net.grid.vampiresdelight.common.registry.VDRecipeSerializers;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static de.teamlapen.vampirism.items.BloodBottleFluidHandler.MULTIPLIER;

public class BloodShapelessRecipe extends ShapelessRecipe {
    public BloodShapelessRecipe(ShapelessRecipe shapelessRecipe) {
        super(shapelessRecipe.getId(), shapelessRecipe.getGroup(), shapelessRecipe.category(),
                shapelessRecipe.getResultItem(RegistryAccess.EMPTY), shapelessRecipe.getIngredients());
    }

    @Override
    public boolean matches(@NotNull CraftingContainer inv, @NotNull Level level) {
        java.util.List<ItemStack> ingredients = new java.util.ArrayList<>();
        boolean areAllBottlesFull = true;

        for(int j = 0; j < inv.getContainerSize(); ++j) {
            ItemStack itemstack = inv.getItem(j);
            if (!itemstack.isEmpty()) {
                ingredients.add(itemstack);

                if (itemstack.getItem() == ModItems.BLOOD_BOTTLE.get() &&
                        itemstack.getDamageValue() * MULTIPLIER < 900) {
                    areAllBottlesFull = false;
                }
            }
        }

        return super.matches(inv, level) && areAllBottlesFull;
    }

    @NotNull
    @Override
    public RecipeSerializer<?> getSerializer() {
        return VDRecipeSerializers.BLOOD_SHAPELESS_RECIPE.get();
    }

    public static class Serializer implements RecipeSerializer<BloodShapelessRecipe> {

        @Override
        @NotNull
        public BloodShapelessRecipe fromJson(@NotNull ResourceLocation recipeId, @NotNull JsonObject serializedRecipe) {
            return new BloodShapelessRecipe(SHAPELESS_RECIPE.fromJson(recipeId, serializedRecipe));
        }

        @Override
        @NotNull
        public BloodShapelessRecipe fromNetwork(@NotNull ResourceLocation recipeId, @NotNull FriendlyByteBuf buffer) {
            return new BloodShapelessRecipe(Objects.requireNonNull(SHAPELESS_RECIPE.fromNetwork(recipeId, buffer)));
        }

        @Override
        public void toNetwork(@NotNull FriendlyByteBuf buffer, @NotNull BloodShapelessRecipe recipe) {
            SHAPELESS_RECIPE.toNetwork(buffer, recipe);
        }
    }
}
