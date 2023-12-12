package net.grid.vampiresdelight.common.block.entity;

import com.google.common.collect.Lists;
import de.teamlapen.vampirism.core.ModBlocks;
import de.teamlapen.vampirism.core.ModItems;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.block.BrewingBarrelBlock;
import net.grid.vampiresdelight.common.block.entity.container.BrewingBarrelMenu;
import net.grid.vampiresdelight.common.block.entity.inventory.BrewingBarrelItemHandler;
import net.grid.vampiresdelight.common.crafting.BrewingBarrelRecipe;
import net.grid.vampiresdelight.common.registry.VDBlockEntityTypes;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.grid.vampiresdelight.common.registry.VDRecipeTypes;
import net.grid.vampiresdelight.common.tag.VDTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeHolder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.block.entity.SyncedBlockEntity;
import vectorwing.farmersdelight.common.mixin.accessor.RecipeManagerAccessor;
import vectorwing.farmersdelight.common.tag.ModTags;
import vectorwing.farmersdelight.common.utility.ItemUtils;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

public class BrewingBarrelBlockEntity extends SyncedBlockEntity implements MenuProvider, RecipeHolder {
    private ItemStackHandler createHandler() {
        return new ItemStackHandler(INVENTORY_SIZE)
        {
            @Override
            protected void onContentsChanged(int slot) {
                if (slot >= 0 && slot < MEAL_DISPLAY_SLOT) {
                    checkNewRecipe = true;
                }
                inventoryChanged();
            }
        };
    }

    public static final int MEAL_DISPLAY_SLOT = 4;
    public static final int CONTAINER_SLOT = 5;
    public static final int OUTPUT_SLOT = 6;
    public static final int INVENTORY_SIZE = OUTPUT_SLOT + 1;

    public static final Map<Item, Item> INGREDIENT_REMAINDER_OVERRIDES = Map.ofEntries(
            Map.entry(ModItems.BLOOD_BOTTLE.get(), Items.GLASS_BOTTLE),
            Map.entry(ModItems.VAMPIRE_BLOOD_BOTTLE.get(), Items.GLASS_BOTTLE),
            Map.entry(ModItems.PURE_BLOOD_0.get(), Items.GLASS_BOTTLE),
            Map.entry(ModItems.PURE_BLOOD_1.get(), Items.GLASS_BOTTLE),
            Map.entry(ModItems.PURE_BLOOD_2.get(), Items.GLASS_BOTTLE),
            Map.entry(ModItems.PURE_BLOOD_3.get(), Items.GLASS_BOTTLE),
            Map.entry(ModItems.PURE_BLOOD_4.get(), Items.GLASS_BOTTLE),

            Map.entry(Items.POWDER_SNOW_BUCKET, Items.BUCKET),
            Map.entry(Items.AXOLOTL_BUCKET, Items.BUCKET),
            Map.entry(Items.COD_BUCKET, Items.BUCKET),
            Map.entry(Items.PUFFERFISH_BUCKET, Items.BUCKET),
            Map.entry(Items.SALMON_BUCKET, Items.BUCKET),
            Map.entry(Items.TROPICAL_FISH_BUCKET, Items.BUCKET),
            Map.entry(Items.SUSPICIOUS_STEW, Items.BOWL),
            Map.entry(Items.MUSHROOM_STEW, Items.BOWL),
            Map.entry(Items.RABBIT_STEW, Items.BOWL),
            Map.entry(Items.BEETROOT_SOUP, Items.BOWL),
            Map.entry(Items.POTION, Items.GLASS_BOTTLE),
            Map.entry(Items.SPLASH_POTION, Items.GLASS_BOTTLE),
            Map.entry(Items.LINGERING_POTION, Items.GLASS_BOTTLE),
            Map.entry(Items.EXPERIENCE_BOTTLE, Items.GLASS_BOTTLE)
    );

    private final ItemStackHandler inventory;
    private final LazyOptional<IItemHandler> inputHandler;
    private final LazyOptional<IItemHandler> outputHandler;

    private int brewTime;
    private int brewTimeTotal;
    private ItemStack mealContainerStack;

    protected final ContainerData brewingBarrelData;
    private final Object2IntOpenHashMap<ResourceLocation> usedRecipeTracker;

    private ResourceLocation lastRecipeID;
    private boolean checkNewRecipe;

    public BrewingBarrelBlockEntity(BlockPos pos, BlockState state) {
        super(VDBlockEntityTypes.BREWING_BARREL.get(), pos, state);
        this.inventory = createHandler();
        this.inputHandler = LazyOptional.of(() -> new BrewingBarrelItemHandler(inventory, Direction.UP));
        this.outputHandler = LazyOptional.of(() -> new BrewingBarrelItemHandler(inventory, Direction.DOWN));
        this.mealContainerStack = ItemStack.EMPTY;
        this.brewingBarrelData = createIntArray();
        this.usedRecipeTracker = new Object2IntOpenHashMap<>();
        this.checkNewRecipe = true;
    }

    public static ItemStack getMealFromItem(ItemStack brewingBarrelStack) {
        if (!brewingBarrelStack.is(VDItems.BREWING_BARREL.get())) {
            return ItemStack.EMPTY;
        }

        CompoundTag compound = brewingBarrelStack.getTagElement("BlockEntityTag");
        if (compound != null) {
            CompoundTag inventoryTag = compound.getCompound("Inventory");
            if (inventoryTag.contains("Items", 7)) {
                ItemStackHandler handler = new ItemStackHandler();
                handler.deserializeNBT(inventoryTag);
                return handler.getStackInSlot(4);
            }
        }

        return ItemStack.EMPTY;
    }

    public static void takeServingFromItem(ItemStack brewingBarrelStack) {
        if (!brewingBarrelStack.is(VDItems.BREWING_BARREL.get())) {
            return;
        }

        CompoundTag compound = brewingBarrelStack.getTagElement("BlockEntityTag");
        if (compound != null) {
            CompoundTag inventoryTag = compound.getCompound("Inventory");
            if (inventoryTag.contains("Items", 7)) {
                ItemStackHandler handler = new ItemStackHandler();
                handler.deserializeNBT(inventoryTag);
                ItemStack newMealStack = handler.getStackInSlot(4);
                newMealStack.shrink(1);
                compound.remove("Inventory");
                compound.put("Inventory", handler.serializeNBT());
            }
        }
    }

    public static ItemStack getContainerFromItem(ItemStack brewingBarrelStack) {
        if (!brewingBarrelStack.is(VDItems.BREWING_BARREL.get())) {
            return ItemStack.EMPTY;
        }

        CompoundTag compound = brewingBarrelStack.getTagElement("BlockEntityTag");
        if (compound != null) {
            return ItemStack.of(compound.getCompound("Container"));
        }

        return ItemStack.EMPTY;
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        inventory.deserializeNBT(compound.getCompound("Inventory"));
        brewTime = compound.getInt("BrewTime");
        brewTimeTotal = compound.getInt("BrewTimeTotal");
        mealContainerStack = ItemStack.of(compound.getCompound("Container"));
        CompoundTag compoundRecipes = compound.getCompound("RecipesUsed");
        for (String key : compoundRecipes.getAllKeys()) {
            usedRecipeTracker.put(new ResourceLocation(key), compoundRecipes.getInt(key));
        }
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        compound.putInt("BrewTime", brewTime);
        compound.putInt("BrewTimeTotal", brewTimeTotal);
        compound.put("Container", mealContainerStack.serializeNBT());
        compound.put("Inventory", inventory.serializeNBT());
        CompoundTag compoundRecipes = new CompoundTag();
        usedRecipeTracker.forEach((recipeId, craftedAmount) -> compoundRecipes.putInt(recipeId.toString(), craftedAmount));
        compound.put("RecipesUsed", compoundRecipes);
    }

    private CompoundTag writeItems(CompoundTag compound) {
        super.saveAdditional(compound);
        compound.put("Container", mealContainerStack.serializeNBT());
        compound.put("Inventory", inventory.serializeNBT());
        return compound;
    }

    public CompoundTag writeMeal(CompoundTag compound) {
        if (getMeal().isEmpty()) return compound;

        ItemStackHandler drops = new ItemStackHandler(INVENTORY_SIZE);
        for (int i = 0; i < INVENTORY_SIZE; ++i) {
            drops.setStackInSlot(i, i == MEAL_DISPLAY_SLOT ? inventory.getStackInSlot(i) : ItemStack.EMPTY);
        }
        compound.put("Container", mealContainerStack.serializeNBT());
        compound.put("Inventory", drops.serializeNBT());
        return compound;
    }

    public static void brewingTick(Level level, BlockPos pos, BlockState state, BrewingBarrelBlockEntity brewingBarrelBlockEntity) {
        boolean didInventoryChange = false;
        Optional<BrewingBarrelRecipe> recipe = brewingBarrelBlockEntity.getMatchingRecipe(new RecipeWrapper(brewingBarrelBlockEntity.inventory));

        if (brewingBarrelBlockEntity.hasInput()) {
            if (recipe.isPresent() && brewingBarrelBlockEntity.canBrew(recipe.get())) {
                didInventoryChange = brewingBarrelBlockEntity.processCooking(recipe.get(), brewingBarrelBlockEntity);
            } else {
                brewingBarrelBlockEntity.brewTime = 0;
            }
        } else if (brewingBarrelBlockEntity.brewTime > 0) {
            brewingBarrelBlockEntity.brewTime = Mth.clamp(brewingBarrelBlockEntity.brewTime - 2, 0, brewingBarrelBlockEntity.brewTimeTotal);
        }

        ItemStack mealStack = brewingBarrelBlockEntity.getMeal();
        if (!mealStack.isEmpty()) {
            if (!brewingBarrelBlockEntity.doesMealHaveContainer(mealStack)) {
                brewingBarrelBlockEntity.moveMealToOutput();
                didInventoryChange = true;
            } else if (!brewingBarrelBlockEntity.inventory.getStackInSlot(CONTAINER_SLOT).isEmpty()) {
                brewingBarrelBlockEntity.useStoredContainersOnMeal();
                didInventoryChange = true;
            }
        }

        if (didInventoryChange) {
            brewingBarrelBlockEntity.inventoryChanged();
        }
    }

    public static boolean isTemperatureModerate(Level level, BlockPos pos) {
        if (isTemperatureCold(level, pos) && isTemperatureHot(level, pos)) return true;

        return !isTemperatureHot(level, pos) && !isTemperatureCold(level, pos);
    }

    public static boolean isTemperatureHot(Level level, BlockPos pos) {
        BlockState stateBelow = level.getBlockState(pos.below());

        if (level.getBiome(pos).is(Tags.Biomes.IS_HOT)) return true;

        for (int dx = -2; dx < 3; dx++) {
            for (int dy = -2; dy < 3; dy++) {
                for (int dz = -2; dz < 3; dz++) {
                    BlockState nearestState = level.getBlockState(pos.offset(dx, dy, dz));
                    if (nearestState.is(ModTags.HEAT_SOURCES)) {
                        if (nearestState.hasProperty(BlockStateProperties.LIT))
                            return nearestState.getValue(BlockStateProperties.LIT);
                        return true;
                    }
                }
            }
        }

        if (stateBelow.is(ModTags.HEAT_CONDUCTORS)) {
            BlockState stateFurtherBelow = level.getBlockState(pos.below(2));
            if (stateFurtherBelow.is(ModTags.HEAT_SOURCES)) {
                if (stateFurtherBelow.hasProperty(BlockStateProperties.LIT))
                    return stateFurtherBelow.getValue(BlockStateProperties.LIT);
                return true;
            }
        }

        return false;
    }

    public static boolean isWet(Level level, BlockPos pos) {
        for (int dx = -2; dx < 3; dx++) {
            for (int dy = -2; dy < 3; dy++) {
                for (int dz = -2; dz < 3; dz++) {
                    BlockState nearestState = level.getBlockState(pos.offset(dx, dy, dz));
                    if (nearestState.getBlock() == Blocks.WATER) return true;
                }
            }
        }
        return level.getBlockState(pos).getValue(BlockStateProperties.WATERLOGGED);
    }

    public static boolean isTemperatureCold(Level level, BlockPos pos) {
        BlockState stateBelow = level.getBlockState(pos.below());

        if (stateBelow.is(VDTags.COOLERS)) return true;
        if (level.getBiome(pos).is(Tags.Biomes.IS_COLD)) return true;

        return isWet(level, pos);
    }

    private Optional<BrewingBarrelRecipe> getMatchingRecipe(RecipeWrapper inventoryWrapper) {
        if (level == null) return Optional.empty();

        if (lastRecipeID != null) {
            Recipe<RecipeWrapper> recipe = ((RecipeManagerAccessor) level.getRecipeManager())
                    .getRecipeMap(VDRecipeTypes.FERMENTING.get())
                    .get(lastRecipeID);
            if (recipe instanceof BrewingBarrelRecipe) {
                if (recipe.matches(inventoryWrapper, level)) {
                    return Optional.of((BrewingBarrelRecipe) recipe);
                }
                if (recipe.getResultItem().sameItem(getMeal())) {
                    return Optional.empty();
                }
            }
        }

        if (checkNewRecipe) {
            Optional<BrewingBarrelRecipe> recipe = level.getRecipeManager().getRecipeFor(VDRecipeTypes.FERMENTING.get(), inventoryWrapper, level);
            if (recipe.isPresent()) {
                ResourceLocation newRecipeID = recipe.get().getId();
                if (lastRecipeID != null && !lastRecipeID.equals(newRecipeID)) {
                    brewTime = 0;
                }
                lastRecipeID = newRecipeID;
                return recipe;
            }
        }

        checkNewRecipe = false;
        return Optional.empty();
    }

    public ItemStack getContainer() {
        ItemStack mealStack = getMeal();
        if (!mealStack.isEmpty() && !mealContainerStack.isEmpty()) {
            return mealContainerStack;
        } else {
            return mealStack.getCraftingRemainingItem();
        }
    }

    private boolean hasInput() {
        for (int i = 0; i < MEAL_DISPLAY_SLOT; ++i) {
            if (!inventory.getStackInSlot(i).isEmpty()) return true;
        }
        return false;
    }

    protected boolean canBrew(BrewingBarrelRecipe recipe) {
        if (hasInput()) {
            ItemStack resultStack = recipe.getResultItem();
            if (resultStack.isEmpty()) {
                return false;
            } else {
                ItemStack storedMealStack = inventory.getStackInSlot(MEAL_DISPLAY_SLOT);
                if (storedMealStack.isEmpty()) {
                    return true;
                } else if (!storedMealStack.sameItem(resultStack)) {
                    return false;
                } else if (storedMealStack.getCount() + resultStack.getCount() <= inventory.getSlotLimit(MEAL_DISPLAY_SLOT)) {
                    return true;
                } else {
                    return storedMealStack.getCount() + resultStack.getCount() <= resultStack.getMaxStackSize();
                }
            }
        } else {
            return false;
        }
    }

    private boolean processCooking(BrewingBarrelRecipe recipe, BrewingBarrelBlockEntity brewingBarrel) {
        if (level == null) return false;

        ++brewTime;
        brewTimeTotal = recipe.getBrewTime();
        if (brewTime < brewTimeTotal) {
            return false;
        }

        brewTime = 0;
        mealContainerStack = recipe.getOutputContainer();
        ItemStack resultStack = recipe.getResultItem();
        ItemStack storedMealStack = inventory.getStackInSlot(MEAL_DISPLAY_SLOT);
        if (storedMealStack.isEmpty()) {
            inventory.setStackInSlot(MEAL_DISPLAY_SLOT, resultStack.copy());
        } else if (storedMealStack.sameItem(resultStack)) {
            storedMealStack.grow(resultStack.getCount());
        }
        brewingBarrel.setRecipeUsed(recipe);

        for (int i = 0; i < MEAL_DISPLAY_SLOT; ++i) {
            ItemStack slotStack = inventory.getStackInSlot(i);
            if (slotStack.hasCraftingRemainingItem()) {
                ejectIngredientRemainder(slotStack.getCraftingRemainingItem());
            } else if (INGREDIENT_REMAINDER_OVERRIDES.containsKey(slotStack.getItem())) {
                ejectIngredientRemainder(INGREDIENT_REMAINDER_OVERRIDES.get(slotStack.getItem()).getDefaultInstance());
            }
            if (!slotStack.isEmpty())
                slotStack.shrink(1);
        }
        return true;
    }

    protected void ejectIngredientRemainder(ItemStack remainderStack) {
        Direction direction = Direction.UP;
        double x = worldPosition.getX() + 0.5 + (direction.getStepX() * 0.25);
        double y = worldPosition.getY() + 0.7;
        double z = worldPosition.getZ() + 0.5 + (direction.getStepZ() * 0.25);
        ItemUtils.spawnItemEntity(level, remainderStack, x, y, z,
                direction.getStepX() * 0.08F, 0.25F, direction.getStepZ() * 0.08F);
    }

    @Override
    public void setRecipeUsed(@Nullable Recipe<?> recipe) {
        if (recipe != null) {
            ResourceLocation recipeID = recipe.getId();
            usedRecipeTracker.addTo(recipeID, 1);
        }
    }

    @Nullable
    @Override
    public Recipe<?> getRecipeUsed() {
        return null;
    }

    @Override
    public void awardUsedRecipes(Player player) {
        List<Recipe<?>> usedRecipes = getUsedRecipesAndPopExperience(player.level, player.position());
        player.awardRecipes(usedRecipes);
        usedRecipeTracker.clear();
    }

    public List<Recipe<?>> getUsedRecipesAndPopExperience(Level level, Vec3 pos) {
        List<Recipe<?>> list = Lists.newArrayList();

        for (Object2IntMap.Entry<ResourceLocation> entry : usedRecipeTracker.object2IntEntrySet()) {
            level.getRecipeManager().byKey(entry.getKey()).ifPresent((recipe) -> {
                list.add(recipe);
                splitAndSpawnExperience((ServerLevel) level, pos, entry.getIntValue(), ((BrewingBarrelRecipe) recipe).getExperience());
            });
        }

        return list;
    }

    private static void splitAndSpawnExperience(ServerLevel level, Vec3 pos, int craftedAmount, float experience) {
        int expTotal = Mth.floor((float) craftedAmount * experience);
        float expFraction = Mth.frac((float) craftedAmount * experience);
        if (expFraction != 0.0F && Math.random() < (double) expFraction) {
            ++expTotal;
        }

        ExperienceOrb.award(level, pos, expTotal);
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    public ItemStack getMeal() {
        return inventory.getStackInSlot(MEAL_DISPLAY_SLOT);
    }

    public NonNullList<ItemStack> getDroppableInventory() {
        NonNullList<ItemStack> drops = NonNullList.create();
        for (int i = 0; i < INVENTORY_SIZE; ++i) {
            if (i != MEAL_DISPLAY_SLOT) {
                drops.add(inventory.getStackInSlot(i));
            }
        }
        return drops;
    }

    private void moveMealToOutput() {
        ItemStack mealStack = inventory.getStackInSlot(MEAL_DISPLAY_SLOT);
        ItemStack outputStack = inventory.getStackInSlot(OUTPUT_SLOT);
        int mealCount = Math.min(mealStack.getCount(), mealStack.getMaxStackSize() - outputStack.getCount());
        if (outputStack.isEmpty()) {
            inventory.setStackInSlot(OUTPUT_SLOT, mealStack.split(mealCount));
        } else if (outputStack.getItem() == mealStack.getItem()) {
            mealStack.shrink(mealCount);
            outputStack.grow(mealCount);
        }
    }

    private void useStoredContainersOnMeal() {
        ItemStack mealStack = inventory.getStackInSlot(MEAL_DISPLAY_SLOT);
        ItemStack containerInputStack = inventory.getStackInSlot(CONTAINER_SLOT);
        ItemStack outputStack = inventory.getStackInSlot(OUTPUT_SLOT);

        if (isContainerValid(containerInputStack) && outputStack.getCount() < outputStack.getMaxStackSize()) {
            int smallerStackCount = Math.min(mealStack.getCount(), containerInputStack.getCount());
            int mealCount = Math.min(smallerStackCount, mealStack.getMaxStackSize() - outputStack.getCount());
            if (outputStack.isEmpty()) {
                containerInputStack.shrink(mealCount);
                inventory.setStackInSlot(OUTPUT_SLOT, mealStack.split(mealCount));
            } else if (outputStack.getItem() == mealStack.getItem()) {
                mealStack.shrink(mealCount);
                containerInputStack.shrink(mealCount);
                outputStack.grow(mealCount);
            }
        }
    }

    public ItemStack useHeldItemOnMeal(ItemStack container) {
        if (isContainerValid(container) && !getMeal().isEmpty()) {
            container.shrink(1);
            return getMeal().split(1);
        }
        return ItemStack.EMPTY;
    }

    private boolean doesMealHaveContainer(ItemStack meal) {
        return !mealContainerStack.isEmpty() || meal.hasCraftingRemainingItem();
    }

    public boolean isContainerValid(ItemStack containerItem) {
        if (containerItem.isEmpty()) return false;
        if (!mealContainerStack.isEmpty()) {
            return mealContainerStack.sameItem(containerItem);
        } else {
            return getMeal().getCraftingRemainingItem().sameItem(containerItem);
        }
    }

    public static void animationTick(Level level, BlockPos pos, BlockState state, BrewingBarrelBlockEntity blockEntity) {
        Optional<BrewingBarrelRecipe> recipe = blockEntity.getMatchingRecipe(new RecipeWrapper(blockEntity.inventory));
        if (recipe.isPresent() && blockEntity.canBrew(recipe.get())) {
            state.setValue(BrewingBarrelBlock.BREWING, true);
        } else state.setValue(BrewingBarrelBlock.BREWING, false);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable(VampiresDelight.MODID + ".container.brewing_barrel");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory player, Player entity) {
        return new BrewingBarrelMenu(id, player, this, brewingBarrelData);
    }

    @Override
    @Nonnull
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if (cap.equals(ForgeCapabilities.ITEM_HANDLER)) {
            if (side == null || side.equals(Direction.UP)) {
                return inputHandler.cast();
            } else {
                return outputHandler.cast();
            }
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        inputHandler.invalidate();
        outputHandler.invalidate();
    }

    @Override
    public CompoundTag getUpdateTag() {
        return writeItems(new CompoundTag());
    }

    private ContainerData createIntArray() {
        return new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> BrewingBarrelBlockEntity.this.brewTime;
                    case 1 -> BrewingBarrelBlockEntity.this.brewTimeTotal;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> BrewingBarrelBlockEntity.this.brewTime = value;
                    case 1 -> BrewingBarrelBlockEntity.this.brewTimeTotal = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }
}
