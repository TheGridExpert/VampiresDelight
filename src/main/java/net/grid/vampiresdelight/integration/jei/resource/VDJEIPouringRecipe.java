package net.grid.vampiresdelight.integration.jei.resource;

import net.minecraft.world.item.ItemStack;

public class VDJEIPouringRecipe {
    private final ItemStack tool;
    private final ItemStack result;
    private final ItemStack servingContainer;

    public VDJEIPouringRecipe(ItemStack tool, ItemStack result, ItemStack servingContainer) {
        this.tool = tool;
        this.result = result;
        this.servingContainer = servingContainer;
    }

    public ItemStack getTool() {
        return tool;
    }

    public ItemStack getResult() {
        return result;
    }

    public ItemStack getServingContainer() {
        return servingContainer;
    }
}
