package net.grid.vampiresdelight.common.block;

import de.teamlapen.vampirism.api.VReference;
import de.teamlapen.vampirism.blocks.VampirismFlowerBlock;
import de.teamlapen.vampirism.core.ModBlocks;
import de.teamlapen.vampirism.core.ModItems;
import de.teamlapen.vampirism.items.HolyWaterBottleItem;
import net.grid.vampiresdelight.common.registry.VDBlocks;
import net.grid.vampiresdelight.common.util.VDHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.PlantType;
import org.jetbrains.annotations.NotNull;

public class CursedFarmlandBlock extends FarmBlock {
    public CursedFarmlandBlock(Properties properties) {
        super(properties);
    }

    public static void turnToCursedEarth(BlockState blockState, Level worldIn, BlockPos pos) {
        worldIn.setBlockAndUpdate(pos, pushEntitiesUp(blockState, de.teamlapen.vampirism.core.ModBlocks.CURSED_EARTH.get().defaultBlockState(), worldIn, pos));
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState aboveState = level.getBlockState(pos.above());
        return super.canSurvive(state, level, pos) || aboveState.getBlock() instanceof StemGrownBlock;
    }

    @Override
    public boolean isFertile(BlockState state, BlockGetter world, BlockPos pos) {
        if (state.is(VDBlocks.CURSED_FARMLAND.get()))
            return state.getValue(CursedFarmlandBlock.MOISTURE) > 0;

        return false;
    }

    @Override
    public void randomTick(BlockState blockState, ServerLevel worldIn, BlockPos pos, RandomSource randomSource) {
        int i = blockState.getValue(MOISTURE);
        if (!isNearWater(worldIn, pos) && !worldIn.isRainingAt(pos.above())) {
            if (i > 0) {
                worldIn.setBlock(pos, blockState.setValue(MOISTURE, i - 1), 2);
            } else if (!isUnderCrops(worldIn, pos)) {
                turnToCursedEarth(blockState, worldIn, pos);
            }
        } else if (i < 7) {
            worldIn.setBlock(pos, blockState.setValue(MOISTURE, 7), 2);
        }
    }

    private static boolean isUnderCrops(BlockGetter blockGetter, BlockPos pos) {
        BlockState plant = blockGetter.getBlockState(pos.above());
        BlockState state = blockGetter.getBlockState(pos);
        return plant.getBlock() instanceof net.minecraftforge.common.IPlantable && state.canSustainPlant(blockGetter, pos, Direction.UP, (net.minecraftforge.common.IPlantable)plant.getBlock());
    }

    private static boolean isNearWater(LevelReader levelReader, BlockPos pos) {
        BlockState state = levelReader.getBlockState(pos);
        for(BlockPos blockpos : BlockPos.betweenClosed(pos.offset(-4, 0, -4), pos.offset(4, 1, 4))) {
            if (state.canBeHydrated(levelReader, pos, levelReader.getFluidState(blockpos), blockpos)) {
                return true;
            }
        }

        return net.minecraftforge.common.FarmlandWaterManager.hasBlockWaterTicket(levelReader, pos);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
        if (!state.canSurvive(level, pos)) {
            turnToCursedEarth(state, level, pos);
        }
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, @NotNull Level worldIn, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand handIn, @NotNull BlockHitResult hit) {
        ItemStack heldItemStack = player.getItemInHand(handIn);
        Item heldItem = heldItemStack.getItem();
        if (heldItem instanceof HolyWaterBottleItem) {
            int uses = heldItem == ModItems.HOLY_WATER_BOTTLE_ULTIMATE.get() ? 100 : (heldItem == ModItems.HOLY_WATER_BOTTLE_ENHANCED.get() ? 50 : 25);
            if (player.getRandom().nextInt(uses) == 0) {
                heldItemStack.setCount(heldItemStack.getCount() - 1);
            }
            worldIn.setBlockAndUpdate(pos, Blocks.FARMLAND.defaultBlockState());
            return InteractionResult.SUCCESS;
        }
        return super.use(state, worldIn, pos, player, handIn, hit);
    }

    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, net.minecraftforge.common.IPlantable plantable) {
        net.minecraftforge.common.PlantType plantType = plantable.getPlantType(world, pos.relative(facing));
        BlockState block = plantable.getPlant(world, pos);
        return (plantable instanceof BushBlock && plantType != PlantType.CROP) || (plantType == VReference.VAMPIRE_PLANT_TYPE || plantType == VDHelper.CURSED_PLANT_TYPE || block.getBlock() instanceof VampirismFlowerBlock);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return !this.defaultBlockState().canSurvive(context.getLevel(), context.getClickedPos()) ? ModBlocks.CURSED_EARTH.get().defaultBlockState() : super.getStateForPlacement(context);
    }

    @Override
    public void fallOn(Level worldIn, @NotNull BlockState blockState, @NotNull BlockPos pos, @NotNull Entity entity, float damage) {
        if (!worldIn.isClientSide && net.minecraftforge.common.ForgeHooks.onFarmlandTrample(worldIn, pos, ModBlocks.CURSED_EARTH.get().defaultBlockState(), damage, entity)) {
            turnToCursedEarth(blockState, worldIn, pos);
        }
        //Super turns block to default dirt, so it mustn't be called here
        entity.causeFallDamage(damage, 1.0F, entity.damageSources().fall());
    }
}
