package net.grid.vampiresdelight.common.world.block;

import de.teamlapen.vampirism.api.VampirismAPI;
import de.teamlapen.vampirism.core.ModBlocks;
import de.teamlapen.vampirism.core.ModTags;
import de.teamlapen.vampirism.util.Helper;
import net.grid.vampiresdelight.common.core.VDBlocks;
import net.grid.vampiresdelight.common.core.VDItems;
import net.grid.vampiresdelight.common.core.VDPlantTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.PlantType;

public class OrchidCropBlock extends CropBlock {

    public static final int MAX_AGE = 5;

    public static final IntegerProperty AGE = BlockStateProperties.AGE_5;

    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[] {
            Block.box(6.0D, 0.0D, 7.0D, 10.0D, 5.0D, 9.0D),
            Block.box(6.0D, 0.0D, 7.0D, 10.0D, 5.0D, 9.0D),
            Block.box(6.0D, 0.0D, 6.0D, 10.0D, 6.0D, 10.0D),
            Block.box(6.0D, 0.0D, 6.0D, 10.0D, 6.0D, 10.0D),
            Block.box(5.0D, 0.0D, 5.0D, 11.0D, 8.0D, 11.0D),
            Block.box(5.0D, 0.0D, 5.0D, 11.0D, 10.0D, 11.0D)
    };

    public OrchidCropBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE_BY_AGE[this.getAge(pState)];
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(VDBlocks.CURSED_FARMLAND.get()) || state.is(VDBlocks.BLOODY_SOIL_FARMLAND.get());
    }

    @Override
    public String getDescriptionId() {
        return ModBlocks.VAMPIRE_ORCHID.get().getDescriptionId();
    }

    @Override
    protected IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!level.isAreaLoaded(pos, 1)) return;
        if (level.getRawBrightness(pos, 0) >= 9) {
            int age = getAge(state);
            if (age < getMaxAge()) {
                float speed = getGrowthSpeed(this, level, pos) * getGrowthSpeedMultiplier(level, pos);
                if (ForgeHooks.onCropsGrowPre(level, pos, state, random.nextInt((int) (25.0F / speed) + 1) == 0)) {
                    level.setBlock(pos, getStateForAge(age + 1), Block.UPDATE_CLIENTS);
                    ForgeHooks.onCropsGrowPost(level, pos, state);
                }
            }
        }
    }

    private static float getGrowthSpeedMultiplier(ServerLevel level, BlockPos pos) {
        if (VampirismAPI.getVampirismWorld(level).resolve().map(world -> world.isInsideArtificialVampireFogArea(pos)).orElse(false) || level.getBiome(pos).is(ModTags.Biomes.IS_VAMPIRE_BIOME)) {
            return 1.35f;
        }
        if (!Helper.isDay(level) || !level.canSeeSky(pos.above())) {
            return 1.2f;
        }
        return 0.6f;
    }

    @Override
    public PlantType getPlantType(BlockGetter level, BlockPos pos) {
        return VDPlantTypes.CURSED;
    }

    @Override
    public BlockState getPlant(BlockGetter world, BlockPos pos) {
        return VDBlocks.VAMPIRE_ORCHID_CROP.get().defaultBlockState();
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return VDItems.ORCHID_SEEDS.get();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }
}
