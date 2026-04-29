package net.grid.vampiresdelight.data.builder;

import net.grid.vampiresdelight.VampiresDelight;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import vectorwing.farmersdelight.data.builder.CuttingBoardRecipeBuilder;

public class VDCuttingBoardRecipeBuilder extends CuttingBoardRecipeBuilder {

    public VDCuttingBoardRecipeBuilder(Ingredient ingredient, Ingredient tool, ItemLike mainResult, int count, float chance) {
        super(ingredient, tool, mainResult, count, chance);
        this.setNamespace(VampiresDelight.MODID);
    }
}
