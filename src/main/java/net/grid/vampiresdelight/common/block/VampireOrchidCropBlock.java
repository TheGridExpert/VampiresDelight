package net.grid.vampiresdelight.common.block;

import de.teamlapen.vampirism.core.ModBlocks;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class VampireOrchidCropBlock extends CropBlock {
    public VampireOrchidCropBlock(Properties properties) {
        super(properties);
    }

    public static final int MAX_AGE = 2;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_1;
    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
            Block.box(5.0D, 0.0D, 5.0D, 11.0D, 6.0D, 11.0D),
            Block.box(5.0D,0.0D, 5.0D, 11.0D, 10.0D, 11.00)};
    private static final int BONEMEAL_INCREASE = 1;

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    public @NotNull BlockState getStateForAge(int age) {
        return age == 2 ? ModBlocks.VAMPIRE_ORCHID.get().defaultBlockState() : super.getStateForAge(age);
    }

    @Override
    public void randomTick(BlockState blockState, ServerLevel worldIn, BlockPos pos, RandomSource randomSource) {
        if (randomSource.nextInt(2) == 0) {
            super.randomTick(blockState, worldIn, pos, randomSource);
        }
    }

    @Override
    protected boolean mayPlaceOn(BlockState blockState, BlockGetter block, BlockPos pos) {
        return blockState.is(ModBlocks.CURSED_EARTH.get());
    }

    @Override
    protected @NotNull ItemLike getBaseSeedId() {
        return VDItems.ORCHID_SEEDS.get();
    }

    @Override
    public @NotNull IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateDefinition) {
        stateDefinition.add(AGE);
    }

    @Override
    protected int getBonemealAgeIncrease(Level worldIn) {
        return BONEMEAL_INCREASE;
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
        return SHAPE_BY_AGE[this.getAge(blockState)];
    }
}
