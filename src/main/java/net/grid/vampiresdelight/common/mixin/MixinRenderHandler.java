package net.grid.vampiresdelight.common.mixin;

import de.teamlapen.vampirism.client.renderer.RenderHandler;
import de.teamlapen.vampirism.config.VampirismConfig;
import de.teamlapen.vampirism.util.Helper;
import net.grid.vampiresdelight.common.utility.VDRenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderHandler.class)
public class MixinRenderHandler {
    @Shadow
    private float vampireBiomeFogDistanceMultiplier = 1;

    @Inject(at = @At("TAIL"), method = "onClientTick(Lnet/minecraftforge/event/TickEvent$ClientTickEvent;)V", remap = false)
    public void onFogClientTick(TickEvent.@NotNull ClientTickEvent event, CallbackInfo ci) {
        Player player = Minecraft.getInstance().player;
        assert player != null;
        if (player.tickCount % 10 == 0) {
            if ((VampirismConfig.CLIENT.renderVampireForestFog.get() || VampirismConfig.SERVER.enforceRenderForestFog.get()) &&
                    (Helper.isEntityInArtificalVampireFogArea(player) || Helper.isEntityInVampireBiome(player))) {
                vampireBiomeFogDistanceMultiplier += VDRenderUtils.getFogDistanceMultiplier(player);
            }
        }
    }
}
