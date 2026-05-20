package net.grid.vampiresdelight.common.world.features;

import com.mojang.serialization.Codec;
import net.grid.vampiresdelight.common.core.VDBlocks;
import net.grid.vampiresdelight.common.tag.VDBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.function.BiPredicate;

public class HugeBlackMushroomFeature extends Feature<NoneFeatureConfiguration> {

    protected static final BiPredicate<WorldGenLevel, BlockPos> replace = ((worldGenLevel, blockPos) -> TreeFeature.isAirOrLeaves(worldGenLevel, blockPos) || worldGenLevel.getBlockState(blockPos).getBlock() instanceof BushBlock);
    protected static final BiPredicate<WorldGenLevel, BlockPos> placeOn = ((worldGenLevel, blockPos) -> worldGenLevel.getBlockState(blockPos).is(VDBlockTags.BLACK_MUSHROOM_GROW_BLOCK));

    public HugeBlackMushroomFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel worldLevel = context.level();
        RandomSource randomSource = context.random();
        BlockPos originPos = context.origin();

        BlockState mushroomStem = VDBlocks.BLACK_MUSHROOM_STEM.get().defaultBlockState();
        BlockState mushroomCap = VDBlocks.BLACK_MUSHROOM_BLOCK.get().defaultBlockState().setValue(HugeMushroomBlock.DOWN, false);

        int stemHeight = 2 + randomSource.nextInt(3);
        int lowestPartHeight = randomSource.nextInt(2);
        int middlePartHeight = 1 + randomSource.nextInt(2);
        int topPartHeight = 2 + randomSource.nextInt(2);

        while (originPos.getY() >= worldLevel.getMinBuildHeight() + 1 && replace.test(worldLevel, originPos)) {
            originPos = originPos.below();
        }

        // Abandoned if the mushroom can't be placed on this block or there isn't enough space
        if (!(placeOn.test(worldLevel, originPos) || this.checkSpace(worldLevel, originPos.above()))) {
            return false;
        }

        BlockPos pos = originPos.above();

        for (int height = 0; height < stemHeight; height++) {
            this.setBlock(worldLevel, pos.above(height), mushroomStem);
        }

        BlockPos highestPos = pos.offset(0, stemHeight, 0);

        // Makes curvy cap part
        BlockState capNoNorth = mushroomCap.setValue(HugeMushroomBlock.NORTH, false);
        BlockState capNoSouth = mushroomCap.setValue(HugeMushroomBlock.SOUTH, false);
        BlockState capNoEast = mushroomCap.setValue(HugeMushroomBlock.EAST, false);
        BlockState capNoWest = mushroomCap.setValue(HugeMushroomBlock.WEST, false);
        // South
        this.setBlock(worldLevel, highestPos.offset(-1, -1, 2), capNoNorth);
        this.setBlock(worldLevel, highestPos.offset(0, -1, 2), capNoNorth);
        this.setBlock(worldLevel, highestPos.offset(1, -1, 2), capNoNorth);
        this.setBlock(worldLevel, highestPos.offset(0, 0, 2), capNoNorth);
        // East
        this.setBlock(worldLevel, highestPos.offset(2, -1, -1), capNoWest);
        this.setBlock(worldLevel, highestPos.offset(2, -1, 0), capNoWest);
        this.setBlock(worldLevel, highestPos.offset(2, -1, 1), capNoWest);
        this.setBlock(worldLevel, highestPos.offset(2, 0, 0), capNoWest);
        // North
        this.setBlock(worldLevel, highestPos.offset(-1, -1, -2), capNoSouth);
        this.setBlock(worldLevel, highestPos.offset(0, -1, -2), capNoSouth);
        this.setBlock(worldLevel, highestPos.offset(1, -1, -2), capNoSouth);
        this.setBlock(worldLevel, highestPos.offset(0, 0, -2), capNoSouth);
        // West
        this.setBlock(worldLevel, highestPos.offset(-2, -1, -1), capNoEast);
        this.setBlock(worldLevel, highestPos.offset(-2, -1, 0), capNoEast);
        this.setBlock(worldLevel, highestPos.offset(-2, -1, 1), capNoEast);
        this.setBlock(worldLevel, highestPos.offset(-2, 0, 0), capNoEast);

        // Makes the lowest cap part
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                for (int height = 0; height <= lowestPartHeight; height++) {
                    this.setBlock(worldLevel, highestPos.offset(x, height, z), mushroomCap);
                }
            }
        }
        highestPos = highestPos.above(lowestPartHeight);

        // Makes the middle cap part
        for (int height = 1; height <= middlePartHeight + 1; height++) {
            this.setBlock(worldLevel, highestPos.offset(0, height, 0), mushroomCap);
            this.setBlock(worldLevel, highestPos.offset(1, height, 0), mushroomCap);
            this.setBlock(worldLevel, highestPos.offset(-1, height, 0), mushroomCap);
            this.setBlock(worldLevel, highestPos.offset(0, height, 1), mushroomCap);
            this.setBlock(worldLevel, highestPos.offset(0, height, -1), mushroomCap);
        }
        highestPos = highestPos.above(middlePartHeight);

        // Makes the top cap part
        for (int height = 1; height <= topPartHeight; height++) {
            this.setBlock(worldLevel, highestPos.above(height), mushroomCap);
        }

        return true;
    }

    public void setBlock(WorldGenLevel level, BlockPos pos, BlockState state) {
        if (replace.test(level, pos)) {
            super.setBlock(level, pos, state);
        }
    }

    public boolean checkSpace(WorldGenLevel level, BlockPos pos) {
        for (int y = 0; y <= 13; y++) {
            for (int x = -2; x <= 2; x++) {
                for (int z = -2; z <= 2; z++) {
                    BlockPos check = pos.offset(x, y, z);
                    if (check.getY() >= 255 || !replace.test(level, check)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
