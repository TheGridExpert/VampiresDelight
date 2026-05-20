package net.grid.vampiresdelight.common.util;

import net.grid.vampiresdelight.misc.mixin.accessor.StructurePieceAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraftforge.items.ItemStackHandler;
import vectorwing.farmersdelight.FarmersDelight;
import vectorwing.farmersdelight.common.block.entity.CookingPotBlockEntity;
import vectorwing.farmersdelight.common.crafting.CookingPotRecipe;

import java.util.List;

public final class VDHunterCampHelper {

    private static final List<ResourceLocation> HUNTER_CAMP_RECIPES = List.of(
            ResourceLocation.fromNamespaceAndPath(FarmersDelight.MODID, "cooking/beef_stew"),
            ResourceLocation.fromNamespaceAndPath(FarmersDelight.MODID, "cooking/onion_soup"),
            ResourceLocation.fromNamespaceAndPath(FarmersDelight.MODID, "cooking/vegetable_soup"),
            ResourceLocation.fromNamespaceAndPath(FarmersDelight.MODID, "cooking/chicken_soup")
    );

    private static final int INPUT_SLOT_COUNT = 6;
    private static final int CONTAINER_SLOT = 7;

    private VDHunterCampHelper() {
    }

    public static void populateCookingPot(StructurePiece piece, WorldGenLevel level, RandomSource random, int localX, int localY, int localZ, BoundingBox box) {
        BlockPos worldPos = ((StructurePieceAccessor) piece).vampiresdelight$getWorldPos(localX, localY, localZ);
        if (!box.isInside(worldPos)) return;
        if (level.getLevel().getServer() == null) return;

        if (!(level.getBlockEntity(worldPos) instanceof CookingPotBlockEntity cookingPot)) return;

        RecipeManager recipeManager = level.getLevel().getServer().getRecipeManager();
        ResourceLocation recipeId = HUNTER_CAMP_RECIPES.get(random.nextInt(HUNTER_CAMP_RECIPES.size()));
        Recipe<?> recipe = recipeManager.byKey(recipeId).orElse(null);
        if (!(recipe instanceof CookingPotRecipe cookingPotRecipe)) return;

        int servings = 1 + random.nextInt(4);
        ItemStackHandler inventory = cookingPot.getInventory();

        int slot = 0;
        for (Ingredient ingredient : cookingPotRecipe.getIngredients()) {
            if (slot >= INPUT_SLOT_COUNT) break;
            ItemStack[] matches = ingredient.getItems();
            if (matches.length == 0) {
                slot++;
                continue;
            }
            ItemStack stack = matches[random.nextInt(matches.length)].copy();
            stack.setCount(Math.min(servings, stack.getMaxStackSize()));
            inventory.setStackInSlot(slot, stack);
            slot++;
        }

        ItemStack bowls = new ItemStack(Items.BOWL);
        bowls.setCount(Math.min(servings + random.nextInt(1, 3), bowls.getMaxStackSize()));
        inventory.setStackInSlot(CONTAINER_SLOT, bowls);

        cookingPot.setChanged();
    }
}