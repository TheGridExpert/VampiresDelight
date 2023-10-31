package net.grid.vampiresdelight.common.crafting.crafting_table;

import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.level.Level;

public class BloodBottleShapelessRecipe extends ShapelessRecipe {
    final NonNullList<Ingredient> ingredients;
    private final boolean isSimple;

    public BloodBottleShapelessRecipe(ResourceLocation resourceLocation, String group, ItemStack output, NonNullList<Ingredient> inputs) {
        super(resourceLocation, group, output, inputs);
        this.ingredients = inputs;
        this.isSimple = inputs.stream().allMatch(Ingredient::isSimple);
    }

    @Override
    public boolean matches(CraftingContainer container, Level worldIn) {
        StackedContents stackedcontents = new StackedContents();
        java.util.List<ItemStack> inputs = new java.util.ArrayList<>();
        int i = 0;

        for(int j = 0; j < container.getContainerSize(); ++j) {
            ItemStack itemstack = container.getItem(j);
            if (!itemstack.isEmpty()) {
                ++i;
                if (isSimple)
                    stackedcontents.accountStack(itemstack, 1);
                else inputs.add(itemstack);
            }
        }

        return i == this.ingredients.size() && (isSimple ? stackedcontents.canCraft(this, (IntList)null) : net.minecraftforge.common.util.RecipeMatcher.findMatches(inputs,  this.ingredients) != null);
    }
}
