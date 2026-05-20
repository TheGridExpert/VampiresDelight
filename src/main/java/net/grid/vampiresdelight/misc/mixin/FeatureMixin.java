package net.grid.vampiresdelight.misc.mixin;

import net.grid.vampiresdelight.common.core.VDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.Feature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Feature.class)
public class FeatureMixin {

    /**
     * Copy of {@link vectorwing.farmersdelight.common.mixin.KeepRichSoilGiantTreeMixin}.
     * Due to how Trees generate, this mixin is needed to prevent Rich Soil from becoming Podzol under a Giant Spruce Tree growth.
     */
    @Inject(at = @At(value = "HEAD"), method = "isGrassOrDirt", cancellable = true)
    private static void keepRichSoil(LevelSimulatedReader level, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (level.isStateAtPosition(pos, state -> state.is(VDBlocks.BLOODY_SOIL.get()))) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}
