package net.grid.vampiresdelight.common.crafting.crafting_table;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import de.teamlapen.vampirism.api.items.IWeaponTableRecipe;
import de.teamlapen.vampirism.core.ModItems;
import de.teamlapen.vampirism.core.ModRecipes;
import de.teamlapen.vampirism.fluids.BloodHelper;
import de.teamlapen.vampirism.items.BloodBottleItem;
import de.teamlapen.vampirism.util.Helper;
import it.unimi.dsi.fastutil.ints.IntList;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.registry.VDRecipeSerializers;
import net.grid.vampiresdelight.common.registry.VDRecipeTypes;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class BloodShapelessRecipe implements CraftingRecipe {
    static int MAX_WIDTH = 3;
    static int MAX_HEIGHT = 3;
    private final @NotNull ResourceLocation id;
    final @NotNull String group;
    final @NotNull ItemStack result;
    final @NotNull NonNullList<Ingredient> ingredients;
    private final boolean isSimple;

    public BloodShapelessRecipe(ResourceLocation id, String group, ItemStack result, NonNullList<Ingredient> ingredients) {
        this.id = id;
        this.group = group;
        this.result = result;
        this.ingredients = ingredients;
        this.isSimple = ingredients.stream().allMatch(Ingredient::isSimple);
    }

    @NotNull
    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @NotNull
    @Override
    public RecipeSerializer<?> getSerializer() {
        return VDRecipeSerializers.BLOOD_SHAPELESS_RECIPE.get();
    }

    @NotNull
    @Override
    public RecipeType<?> getType() {
        return VDRecipeTypes.BLOOD_CRAFTING_TYPE.get();
    }

    @Override
    public CraftingBookCategory category() {
        return null;
    }

    @NotNull
    @Override
    public String getGroup() {
        return this.group;
    }

    @NotNull
    @Override
    public ItemStack getResultItem(RegistryAccess access) {
        return this.result;
    }

    @NotNull
    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.ingredients;
    }

    @Override
    public boolean matches(CraftingContainer container, Level worldIn) {
        StackedContents stackedcontents = new StackedContents();
        java.util.List<ItemStack> inputs = new java.util.ArrayList<>();
        int i = 0;
        boolean isBloodBottleFilled = false;

        for(int j = 0; j < container.getContainerSize(); ++j) {
            ItemStack itemstack = container.getItem(j);
            if (!itemstack.isEmpty()) {
                ++i;
                if (isSimple)
                    stackedcontents.accountStack(itemstack, 1);
                else inputs.add(itemstack);
                if (itemstack.getItem() instanceof BloodBottleItem) {
                    if (BloodHelper.getBlood(itemstack) != 9) isBloodBottleFilled = false;
                    //if (itemstack.getDamageValue() != 1) isBloodBottleFilled = false;
                }
            }
        }

        return i == this.ingredients.size() && isBloodBottleFilled && (isSimple ? stackedcontents.canCraft(this, (IntList)null) : net.minecraftforge.common.util.RecipeMatcher.findMatches(inputs,  this.ingredients) != null);
    }

    @NotNull
    @Override
    public ItemStack assemble(CraftingContainer container, RegistryAccess access) {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int p_44252_, int p_44253_) {
        return p_44252_ * p_44253_ >= this.ingredients.size();
    }

    public static class Serializer implements RecipeSerializer<BloodShapelessRecipe> {
        private static final ResourceLocation NAME = new ResourceLocation(VampiresDelight.MODID, "crafting_shapeless_blood");
        @NotNull
        @Override
        public BloodShapelessRecipe fromJson(ResourceLocation location, JsonObject jsonObject) {
            String s = GsonHelper.getAsString(jsonObject, "group", "");
            NonNullList<Ingredient> nonnulllist = itemsFromJson(GsonHelper.getAsJsonArray(jsonObject, "ingredients"));
            if (nonnulllist.isEmpty()) {
                throw new JsonParseException("No ingredients for blood shapeless recipe");
            } else if (nonnulllist.size() > MAX_WIDTH * MAX_HEIGHT) {
                throw new JsonParseException("Too many ingredients for blood shapeless recipe. The maximum is " + (MAX_WIDTH * MAX_HEIGHT));
            } else {
                ItemStack itemstack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(jsonObject, "result"));
                return new BloodShapelessRecipe(location, s, itemstack, nonnulllist);
            }
        }

        private static NonNullList<Ingredient> itemsFromJson(JsonArray array) {
            NonNullList<Ingredient> nonnulllist = NonNullList.create();

            for (int i = 0; i < array.size(); ++i) {
                Ingredient ingredient = Ingredient.fromJson(array.get(i));
                if (true || !ingredient.isEmpty()) { // FORGE: Skip checking if an ingredient is empty during shapeless recipe deserialization to prevent complex ingredients from caching tags too early. Can not be done using a config value due to sync issues.
                    nonnulllist.add(ingredient);
                }
            }

            return nonnulllist;
        }

        @Override
        public BloodShapelessRecipe fromNetwork(ResourceLocation location, FriendlyByteBuf buf) {
            String s = buf.readUtf();
            int i = buf.readVarInt();
            NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i, Ingredient.EMPTY);

            for(int j = 0; j < nonnulllist.size(); ++j) {
                nonnulllist.set(j, Ingredient.fromNetwork(buf));
            }

            ItemStack itemstack = buf.readItem();
            return new BloodShapelessRecipe(location, s, itemstack, nonnulllist);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, BloodShapelessRecipe recipe) {
            buf.writeUtf(recipe.group);
            buf.writeVarInt(recipe.ingredients.size());

            for(Ingredient ingredient : recipe.ingredients) {
                ingredient.toNetwork(buf);
            }

            buf.writeItem(recipe.result);
        }
    }
}
