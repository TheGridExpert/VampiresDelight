package net.grid.vampiresdelight.misc.mixin;

import net.grid.vampiresdelight.common.core.VDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BiConsumer;

@Mixin(TrunkPlacer.class)
public class TrunkPlacerMixin {

    /**
     * Copy of {@link vectorwing.farmersdelight.common.mixin.KeepRichSoilTreeMixin}.
     * Due to how Trees generate, this mixin is needed to prevent Rich Soil from becoming Podzol under a Giant Spruce Tree growth.
     */
    @Inject(at = @At(value = "HEAD"), method = "setDirtAt", cancellable = true)
    private static void cancelSetDirtIfRichSoil(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, RandomSource random, BlockPos pos, TreeConfiguration config, CallbackInfo ci) {
        if (level.isStateAtPosition(pos, state -> state.is(VDBlocks.BLOODY_SOIL.get()))) {
            ci.cancel();
        }
    }
}
