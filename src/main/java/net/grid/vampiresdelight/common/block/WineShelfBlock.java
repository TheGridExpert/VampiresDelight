package net.grid.vampiresdelight.common.block;

import de.teamlapen.lib.lib.util.UtilLib;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class WineShelfBlock extends Block {
    public static final BooleanProperty HAS_UPPER_SUPPORT = BooleanProperty.create("has_upper_support");

    public static @NotNull VoxelShape makeShape(boolean has_support){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.or(shape, Shapes.box(0, 0.5, 0, 1, 0.625, 1));
        shape = Shapes.or(shape, Shapes.box(0, 0, 0, 1, 0.125, 1));
        shape = Shapes.or(shape, Shapes.box(0.4375, 0.125, 0.875, 0.5625, 0.5, 1));
        if (has_support) {
            shape = Shapes.or(shape, Shapes.box(0.4375, 0.625, 0.875, 0.5625, 1, 1));
        }

        return shape;
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        VoxelShape shape;

        switch (state.getValue(HorizontalDirectionalBlock.FACING)) {
            case EAST -> shape = UtilLib.rotateShape(makeShape(state.getValue(HAS_UPPER_SUPPORT)), UtilLib.RotationAmount.NINETY);
            case SOUTH -> shape = UtilLib.rotateShape(makeShape(state.getValue(HAS_UPPER_SUPPORT)), UtilLib.RotationAmount.HUNDRED_EIGHTY);
            case WEST -> shape = UtilLib.rotateShape(makeShape(state.getValue(HAS_UPPER_SUPPORT)), UtilLib.RotationAmount.TWO_HUNDRED_SEVENTY);
            default -> shape = makeShape(state.getValue(HAS_UPPER_SUPPORT));
        }

        return shape;
    }

    public WineShelfBlock(Properties properties) {
        super(properties);

        BlockState blockstate = this.stateDefinition.any()
                .setValue(HorizontalDirectionalBlock.FACING, Direction.NORTH)
                .setValue(HAS_UPPER_SUPPORT, false);

        this.registerDefaultState(blockstate);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockGetter world = context.getLevel();
        BlockPos posAbove = context.getClickedPos().above();
        return this.defaultBlockState()
                .setValue(HorizontalDirectionalBlock.FACING, context.getHorizontalDirection().getOpposite())
                .setValue(HAS_UPPER_SUPPORT, world.getBlockState(posAbove).getBlock() instanceof WineShelfBlock);
    }

    @Override
    public @NotNull BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos blockPos, BlockPos facingPos) {
        return super.updateShape(state.setValue(HAS_UPPER_SUPPORT, level.getBlockState(blockPos.above()).getBlock() instanceof WineShelfBlock), facing, facingState, level, blockPos, facingPos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(HorizontalDirectionalBlock.FACING, HAS_UPPER_SUPPORT);
    }
}
