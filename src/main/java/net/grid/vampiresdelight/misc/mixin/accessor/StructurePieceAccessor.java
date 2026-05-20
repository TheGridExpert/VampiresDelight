package net.grid.vampiresdelight.misc.mixin.accessor;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(StructurePiece.class)
public interface StructurePieceAccessor {

    @Invoker("placeBlock")
    void vampiresdelight$placeBlock(WorldGenLevel level, BlockState state, int x, int y, int z, BoundingBox box);

    @Invoker("getWorldPos")
    BlockPos.MutableBlockPos vampiresdelight$getWorldPos(int x, int y, int z);
}