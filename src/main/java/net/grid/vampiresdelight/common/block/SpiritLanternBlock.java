package net.grid.vampiresdelight.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class SpiritLanternBlock extends LanternBlock {
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
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState pState, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return pState.getValue(HANGING) ? SHAPE_HANGING : SHAPE;
    }
}
