package net.grid.vampiresdelight.common.crafting;

import net.grid.vampiresdelight.common.block.entity.BrewingBarrelBlockEntity;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.grid.vampiresdelight.common.registry.VDRecipeSerializers;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class BarrelPouringRecipe extends CustomRecipe {
    public BarrelPouringRecipe(ResourceLocation id) {
        super(id);
    }

    @Override
    public boolean matches(CraftingContainer container, Level level) {
        ItemStack brewingBarrelStack = ItemStack.EMPTY;
        ItemStack containerStack = ItemStack.EMPTY;
        ItemStack secondStack = ItemStack.EMPTY;

        for (int index = 0; index < container.getContainerSize(); ++index) {
            ItemStack selectedStack = container.getItem(index);
            if (!selectedStack.isEmpty()) {
                if (brewingBarrelStack.isEmpty()) {
                    ItemStack mealStack = BrewingBarrelBlockEntity.getMealFromItem(selectedStack);
                    if (!mealStack.isEmpty()) {
                        brewingBarrelStack = selectedStack;
                        containerStack = BrewingBarrelBlockEntity.getContainerFromItem(selectedStack);
                        continue;
                    }
                }
                if (secondStack.isEmpty()) {
                    secondStack = selectedStack;
                } else {
                    return false;
                }
            }
        }

        return !brewingBarrelStack.isEmpty() && !secondStack.isEmpty() && secondStack.is(containerStack.getItem());
    }

    @Override
    public ItemStack assemble(CraftingContainer container) {
        for (int i = 0; i < container.getContainerSize(); ++i) {
            ItemStack selectedStack = container.getItem(i);
            if (!selectedStack.isEmpty() && selectedStack.is(VDItems.BREWING_BARREL.get())) {
                ItemStack resultStack = BrewingBarrelBlockEntity.getMealFromItem(selectedStack).copy();
                resultStack.setCount(1);
                return resultStack;
            }
        }

        return ItemStack.EMPTY;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingContainer container) {
        NonNullList<ItemStack> remainders = NonNullList.withSize(container.getContainerSize(), ItemStack.EMPTY);

        for (int i = 0; i < remainders.size(); ++i) {
            ItemStack selectedStack = container.getItem(i);
            if (selectedStack.hasCraftingRemainingItem()) {
                remainders.set(i, selectedStack.getCraftingRemainingItem());
            } else if (selectedStack.is(VDItems.BREWING_BARREL.get())) {
                BrewingBarrelBlockEntity.takeServingFromItem(selectedStack);
                ItemStack newBrewingBarrelStack = selectedStack.copy();
                newBrewingBarrelStack.setCount(1);
                remainders.set(i, newBrewingBarrelStack);
                break;
            }
        }

        return remainders;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width >= 2 && height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return VDRecipeSerializers.BARREL_POURING.get();
    }
}
