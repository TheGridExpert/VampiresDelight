package net.grid.vampiresdelight.common.registry;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.crafting.BarrelPouringRecipe;
import net.grid.vampiresdelight.common.crafting.BrewingBarrelRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class VDRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, VampiresDelight.MODID);

    public static final RegistryObject<RecipeSerializer<?>> FERMENTING = RECIPE_SERIALIZERS.register("fermenting", BrewingBarrelRecipe.Serializer::new);

    public static final RegistryObject<SimpleRecipeSerializer<?>> BARREL_POURING =
            RECIPE_SERIALIZERS.register("barrel_pouring", () -> new SimpleRecipeSerializer<>(BarrelPouringRecipe::new));

    public static final RegistryObject<RecipeSerializer> BLOOD_SHAPELESS_RECIPE = RECIPE_SERIALIZERS.register("crafting_blood_shapeless", ShapelessRecipe.Serializer::new);
}
