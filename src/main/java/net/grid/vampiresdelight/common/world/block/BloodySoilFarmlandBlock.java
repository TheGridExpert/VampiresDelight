package net.grid.vampiresdelight.common.world.block;

import net.grid.vampiresdelight.common.core.VDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StemGrownBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.common.FarmlandWaterManager;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

import javax.annotation.Nullable;

public class BloodySoilFarmlandBlock extends CursedFarmlandBlock {

    public BloodySoilFarmlandBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int moisture = state.getValue(MOISTURE);
        if (!isNearWater(level, pos) && !level.isRainingAt(pos.above())) {
            if (moisture > 0) {
                level.setBlock(pos, state.setValue(MOISTURE, moisture - 1), 2);
            }
        } else if (moisture < 7) {
            level.setBlock(pos, state.setValue(MOISTURE, 7), 2);
        } else if (moisture == 7) {
            BloodySoilBlock.tryBoostingPlantsAboveAndBelow(level, pos, random);
        }
    }

    private static boolean isNearWater(LevelReader level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);

        for (BlockPos nearbyPos : BlockPos.betweenClosed(pos.offset(-4, 0, -4), pos.offset(4, 1, 4))) {
            if (state.canBeHydrated(level, pos, level.getFluidState(nearbyPos), nearbyPos)) {
                return true;
            }
        }

        return FarmlandWaterManager.hasBlockWaterTicket(level, pos);
    }

    public static void turnIntoBloodySoil(@Nullable Entity entity, BlockState state, Level level, BlockPos pos) {
        level.setBlockAndUpdate(pos, pushEntitiesUp(state, VDBlocks.BLOODY_SOIL.get().defaultBlockState(), level, pos));
        level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(entity, state));
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState above = level.getBlockState(pos.above());
        return super.canSurvive(state, level, pos) || above.getBlock() instanceof StemGrownBlock;
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!state.canSurvive(level, pos)) {
            turnIntoBloodySoil(null, state, level, pos);
        }
    }

    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter level, BlockPos pos, Direction facing, IPlantable plantable) {
        PlantType plantType = plantable.getPlantType(level, pos.relative(facing));
        Block plantBlock = plantable.getPlant(level, pos).getBlock();
        return CursedFarmlandBlock.isVampireOrNonCrop(plantBlock, plantable, plantType);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return !this.defaultBlockState().canSurvive(context.getLevel(), context.getClickedPos()) ? VDBlocks.BLOODY_SOIL.get().defaultBlockState() : super.getStateForPlacement(context);
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        entity.causeFallDamage(fallDistance, 1.0F, entity.damageSources().fall());
    }
}