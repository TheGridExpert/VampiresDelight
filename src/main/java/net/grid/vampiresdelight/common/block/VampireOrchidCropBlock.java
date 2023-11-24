package net.grid.vampiresdelight.common.block;

import de.teamlapen.vampirism.api.VReference;
import de.teamlapen.vampirism.core.ModBlocks;
import net.grid.vampiresdelight.common.registry.VDBlocks;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.grid.vampiresdelight.common.util.VDHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.PlantType;
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
        if (!worldIn.isAreaLoaded(pos, 1)) return;
        if (worldIn.getRawBrightness(pos, 0) >= 9) {
            int i = this.getAge(blockState);
            if (i < this.getMaxAge()) {
                float f = getGrowthSpeed(this, worldIn, pos);
                if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, blockState, randomSource.nextInt((int)(25.0F / f) + 1) == 0)) {
                    worldIn.setBlock(pos, this.getStateForAge(i + 1), 2);
                    net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, blockState);
                }
            }
        }
    }

    protected static float getGrowthSpeed(Block block, BlockGetter blockGetter, BlockPos pos) {
        float speed = 1.0F;
        BlockPos blockpos = pos.below();

        for(int i = -1; i <= 1; ++i) {
            for(int j = -1; j <= 1; ++j) {
                float speedBoost = 0.0F;
                BlockState blockstate = blockGetter.getBlockState(blockpos.offset(i, 0, j));
                if (blockstate.canSustainPlant(blockGetter, blockpos.offset(i, 0, j), net.minecraft.core.Direction.UP, (net.minecraftforge.common.IPlantable) block)) {
                    speedBoost = 1.0F;
                    if (blockstate.isFertile(blockGetter, pos.offset(i, 0, j))) {
                        speedBoost = 3.0F;
                    }
                }

                if (i != 0 || j != 0) {
                    speedBoost /= 4.0F;
                }

                speed += speedBoost;
            }
        }

        BlockPos north = pos.north();
        BlockPos south = pos.south();
        BlockPos west = pos.west();
        BlockPos east = pos.east();
        boolean matchesEastWestRow = blockGetter.getBlockState(west).is(block) || blockGetter.getBlockState(east).is(block);
        boolean matchesNorthSouthRow = blockGetter.getBlockState(north).is(block) || blockGetter.getBlockState(south).is(block);
        if (matchesEastWestRow && matchesNorthSouthRow) {
            speed /= 2.0F;
        } else {
            boolean matchesDiagonalRows = blockGetter.getBlockState(west.north()).is(block) || blockGetter.getBlockState(east.north()).is(block) || blockGetter.getBlockState(east.south()).is(block) || blockGetter.getBlockState(west.south()).is(block);
            if (matchesDiagonalRows) {
                speed /= 2.0F;
            }
        }

        speed /= 2.5F;

        return speed;
    }

    @Override
    protected boolean mayPlaceOn(BlockState blockState, BlockGetter block, BlockPos pos) {
        return blockState.is(VDBlocks.CURSED_FARMLAND.get()) && !blockState.is(ModBlocks.CURSED_EARTH.get());
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
    public PlantType getPlantType(BlockGetter world, BlockPos pos) {
        return VDHelper.CURSED_PLANT_TYPE;
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
