package net.grid.vampiresdelight.common.crafting;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.simibubi.create.AllRecipeTypes;
import net.grid.vampiresdelight.common.registry.VDRecipeSerializers;
import net.grid.vampiresdelight.common.registry.VDRecipeTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.crafting.CuttingBoardRecipe;

import java.util.Objects;
import java.util.Spliterator;

public class SpreadingRecipe implements Recipe<RecipeWrapper> {
    public final ResourceLocation id;
    private final String group;
    public final Ingredient input;
    private final ItemStack result;
    public final Ingredient tool;
    public SpreadingRecipe(ResourceLocation id, String group, Ingredient input, Ingredient tool, ItemStack result) {
        this.id = id;
        this.group = group;
        this.input = input;
        this.tool = tool;
        this.result = result;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public boolean matches(RecipeWrapper p_44002_, Level p_44003_) {
        if (p_44002_.isEmpty())
            return false;
        return input.test(p_44002_.getItem(0));
    }

    @Override
    public ItemStack assemble(RecipeWrapper inv) {
        return this.result.copy();
    }

    @Override
    public ItemStack getResultItem() {
        return result;
    }

    protected int getMaxInputCount() {
        return 1;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= this.getMaxInputCount();
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    public Ingredient getTool() {
        return this.tool;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return VDRecipeSerializers.SPREADING.get();
    }

    @Override
    public RecipeType<?> getType() {
        return VDRecipeTypes.SPREADING.get();
    }

    public static class Serializer implements RecipeSerializer<SpreadingRecipe> {

        @Override
        public SpreadingRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            final String groupIn = GsonHelper.getAsString(json, "group", "");
            Ingredient ingredient = Ingredient.fromJson(json.get("ingredient"));
            final JsonObject toolObject = GsonHelper.getAsJsonObject(json, "tool");
            final Ingredient toolIn = Ingredient.fromJson(toolObject);

            if (ingredient.isEmpty()) {
                throw new JsonParseException("No ingredients for spreading recipe");
            } else if (toolIn.isEmpty()) {
                throw new JsonParseException("No tool for spreading recipe");
            } else {
                ItemStack result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
                return new SpreadingRecipe(recipeId, groupIn, ingredient, toolIn, result);
            }
        }

        @Override
        public @Nullable SpreadingRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            String groupIn = buffer.readUtf(32767);
            Ingredient toolIn = Ingredient.fromNetwork(buffer);
            Ingredient ingredient = Ingredient.fromNetwork(buffer);
            ItemStack result = buffer.readItem();

            return new SpreadingRecipe(recipeId, groupIn, ingredient, toolIn, result);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, SpreadingRecipe recipe) {
            buffer.writeUtf(recipe.group);
            buffer.writeItem(recipe.result);
            recipe.input.toNetwork(buffer);
            recipe.tool.toNetwork(buffer);
        }
    }
}
