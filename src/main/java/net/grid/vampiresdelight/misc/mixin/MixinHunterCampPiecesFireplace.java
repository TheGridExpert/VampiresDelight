package net.grid.vampiresdelight.misc.mixin;

import de.teamlapen.vampirism.world.gen.structure.huntercamp.HunterCampPieces;
import net.grid.vampiresdelight.common.config.VDCommonConfig;
import net.grid.vampiresdelight.common.util.VDHunterCampHelper;
import net.grid.vampiresdelight.misc.mixin.accessor.StructurePieceAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vectorwing.farmersdelight.common.block.CookingPotBlock;
import vectorwing.farmersdelight.common.block.state.CookingPotSupport;
import vectorwing.farmersdelight.common.registry.ModBlocks;

@Mixin(HunterCampPieces.Fireplace.class)
public class MixinHunterCampPiecesFireplace {

    @Inject(at = @At("TAIL"), method = "postProcess(Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/world/level/StructureManager;Lnet/minecraft/world/level/chunk/ChunkGenerator;Lnet/minecraft/util/RandomSource;Lnet/minecraft/world/level/levelgen/structure/BoundingBox;Lnet/minecraft/world/level/ChunkPos;Lnet/minecraft/core/BlockPos;)V")
    public void placeCookingPot(WorldGenLevel worldIn, StructureManager structureManager, ChunkGenerator chunkGenerator, RandomSource random, BoundingBox structureBoundingBoxIn, ChunkPos chunkPos, BlockPos blockPos, CallbackInfo ci) {
        if (random.nextInt(100) <= VDCommonConfig.COOKING_POT_IN_HUNTER_CAMP_CHANCE.get() && VDCommonConfig.GENERATE_COOKING_POT_IN_HUNTER_CAMP.get() && !VDCommonConfig.GENERATE_COOKING_POT_NEAR_HUNTER_CAMP.get()) {
            ((StructurePieceAccessor) this).vampiresdelight$placeBlock(worldIn, ModBlocks.COOKING_POT.get().defaultBlockState().setValue(CookingPotBlock.SUPPORT, CookingPotSupport.TRAY), 1, 1, 1, structureBoundingBoxIn);
            VDHunterCampHelper.populateCookingPot((StructurePiece) (Object) this, worldIn, random, 1, 1, 1, structureBoundingBoxIn);
        }
    }
}