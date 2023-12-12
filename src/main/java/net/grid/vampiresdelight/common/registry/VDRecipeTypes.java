package net.grid.vampiresdelight.common.registry;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.crafting.BrewingBarrelRecipe;
import net.grid.vampiresdelight.common.crafting.crafting_table.BloodShapelessRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class VDRecipeTypes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, VampiresDelight.MODID);

    public static final RegistryObject<RecipeType<BrewingBarrelRecipe>> FERMENTING = RECIPE_TYPES.register("fermenting", () -> registerRecipeType("fermenting"));
    public static final RegistryObject<RecipeType<BloodShapelessRecipe>> BLOOD_CRAFTING_TYPE = RECIPE_TYPES.register("crafting_blood_shapeless", () -> registerRecipeType("crafting_blood_shapeless"));

    public static <T extends Recipe<?>> RecipeType<T> registerRecipeType(final String identifier) {
        return new RecipeType<>()
        {
            public String toString() {
                return VampiresDelight.MODID + ":" + identifier;
            }
        };
    }
}
