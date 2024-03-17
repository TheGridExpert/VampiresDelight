package net.grid.vampiresdelight.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class SpiritLanternBlock extends LanternBlock {
    public static final DirectionProperty HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;
    protected static final VoxelShape SHAPE = Shapes.or(
            Block.box(5.0D, 0.0D, 5.0D, 11.0D, 2.0D, 11.0D),
            Block.box(5.0D, 5.0D, 5.0D, 11.0D, 7.0D, 11.0D),
            Block.box(6.0D, 2.0D, 6.0D, 10.0D, 8.0D, 10.0D));
    protected static final VoxelShape SHAPE_HANGING = Shapes.or(
            Block.box(5.0D, 2.0D, 5.0D, 11.0D, 4.0D, 11.0D),
            Block.box(5.0D, 7.0D, 5.0D, 11.0D, 9.0D, 11.0D),
            Block.box(6.0D, 4.0D, 6.0D, 10.0D, 10.0D, 10.0D));

    public SpiritLanternBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(HANGING, false)
                .setValue(WATERLOGGED, false)
                .setValue(HORIZONTAL_FACING, Direction.NORTH));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());

        for(Direction direction : pContext.getNearestLookingDirections()) {
            if (direction.getAxis() == Direction.Axis.Y) {
                BlockState blockstate = this.defaultBlockState()
                        .setValue(HANGING, direction == Direction.UP)
                        .setValue(HORIZONTAL_FACING, pContext.getHorizontalDirection());
                if (blockstate.canSurvive(pContext.getLevel(), pContext.getClickedPos())) {
                    return blockstate.setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
                }
            }
        }

        return null;
    }

    @Override
    public @NotNull BlockState updateShape(BlockState pState, @NotNull Direction pDirection, @NotNull BlockState pNeighborState, @NotNull LevelAccessor pLevel, @NotNull BlockPos pPos, @NotNull BlockPos pNeighborPos) {
        if (pState.getValue(WATERLOGGED)) {
            pLevel.scheduleTick(pPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }

        return getConnectedDirection(pState).getOpposite() == pDirection
                && !pState.canSurvive(pLevel, pPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pDirection, pNeighborState, pLevel, pPos, pNeighborPos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(HORIZONTAL_FACING);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState pState, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return pState.getValue(HANGING) ? SHAPE_HANGING : SHAPE;
    }
}
