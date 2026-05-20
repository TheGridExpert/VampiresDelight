package net.grid.vampiresdelight.common.world.block;

import de.teamlapen.vampirism.api.VReference;
import net.grid.vampiresdelight.common.tag.VDBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.PlantType;

public class BlackMushroomBlock extends MushroomBlock {

    private static final VoxelShape SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 8.0D, 11.0D);

    public BlackMushroomBlock(Properties properties, ResourceKey<ConfiguredFeature<?, ?>> feature) {
        super(properties, feature);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos belowPos = pos.below();
        BlockState belowState = level.getBlockState(belowPos);

        if (belowState.is(VDBlockTags.BLACK_MUSHROOM_GROW_BLOCK)) {
            return true;
        } else {
            return level.getRawBrightness(pos, 0) < 13 && belowState.canSustainPlant(level, belowPos, Direction.UP, this);
        }
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public PlantType getPlantType(BlockGetter level, BlockPos pos) {
        return VReference.VAMPIRE_PLANT_TYPE;
    }
}
