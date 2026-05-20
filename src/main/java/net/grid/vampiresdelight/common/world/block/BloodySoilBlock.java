package net.grid.vampiresdelight.common.world.block;

import net.grid.vampiresdelight.common.config.VDCommonConfig;
import net.grid.vampiresdelight.common.core.VDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import vectorwing.farmersdelight.common.block.RichSoilBlock;
import vectorwing.farmersdelight.common.tag.ModTags;

import javax.annotation.Nullable;

@SuppressWarnings("deprecation")
public class BloodySoilBlock extends Block {

    public BloodySoilBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        BlockPos abovePos = pos.above();
        BlockState aboveState = level.getBlockState(abovePos);
        if (!convertMushroomToColony(aboveState, abovePos, level)) {
            tryBoostingPlantsAboveAndBelow(level, pos, random);
        }
    }

    public static void tryBoostingPlantsAboveAndBelow(ServerLevel level, BlockPos pos, RandomSource random) {
        if (VDCommonConfig.BLOODY_SOIL_BOOST_CHANCE.get() != 0.0 && random.nextFloat() <= VDCommonConfig.BLOODY_SOIL_BOOST_CHANCE.get()) {
            BlockPos abovePos = pos.above();
            BlockState aboveState = level.getBlockState(abovePos);
            if (aboveState.is(ModTags.Blocks.PLANTED_FROM_BELOW) || !RichSoilBlock.boostPlant(aboveState, abovePos, level)) {
                BlockPos belowPos = pos.below();
                BlockState belowState = level.getBlockState(belowPos);
                if (belowState.is(ModTags.Blocks.PLANTED_FROM_BELOW)) {
                    RichSoilBlock.boostPlant(belowState, belowPos, level);
                }
            }
        }
    }

    public boolean convertMushroomToColony(BlockState targetState, BlockPos targetPos, ServerLevel level) {
        return false;
    }

    @Nullable
    @Override
    public BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate) {
        return toolAction.equals(ToolActions.HOE_TILL) && context.getLevel().getBlockState(context.getClickedPos().above()).isAir() ? VDBlocks.BLOODY_SOIL_FARMLAND.get().defaultBlockState() : null;
    }

    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter level, BlockPos pos, Direction facing, IPlantable plantable) {
        PlantType plantType = plantable.getPlantType(level, pos.relative(facing));
        return plantType != PlantType.CROP && plantType != PlantType.NETHER && plantType != PlantType.WATER;
    }
}