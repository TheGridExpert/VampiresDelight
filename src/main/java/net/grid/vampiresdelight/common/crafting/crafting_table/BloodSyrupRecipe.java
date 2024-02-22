package net.grid.vampiresdelight.common.crafting.crafting_table;

import de.teamlapen.vampirism.core.ModItems;
import de.teamlapen.vampirism.util.OilUtils;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.grid.vampiresdelight.common.registry.VDRecipeSerializers;
import net.grid.vampiresdelight.common.tag.VDTags;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class BloodSyrupRecipe extends CustomRecipe {

    public BloodSyrupRecipe(ResourceLocation id, CraftingBookCategory category) {
        super(id, category);
    }

    @Override
    public boolean matches(CraftingContainer inventory, @NotNull Level level) {
        ItemStack bloodBottle = null;
        ItemStack secondaryIngredient = null;
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() == ModItems.BLOOD_BOTTLE.get()) {
                    if (bloodBottle == null) {
                        bloodBottle = stack;
                    }
                } else if(stack.is(VDTags.BLOOD_SYRUP_INGREDIENTS)) {
                    if (secondaryIngredient != null) return false;
                    secondaryIngredient = stack;
                } else {
                    return false;
                }
            }
        }
        return bloodBottle != null && secondaryIngredient != null;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull CraftingContainer container, @NotNull RegistryAccess registryAccess) {
        return new ItemStack(VDItems.BLOOD_SYRUP.get());
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return VDRecipeSerializers.BLOOD_SYRUP.get();
    }
}
