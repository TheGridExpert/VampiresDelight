package net.grid.vampiresdelight.common.mixin;

import de.teamlapen.vampirism.client.renderer.RenderHandler;
import de.teamlapen.vampirism.config.VampirismConfig;
import de.teamlapen.vampirism.entity.player.vampire.VampirePlayer;
import de.teamlapen.vampirism.util.Helper;
import net.grid.vampiresdelight.common.registry.VDEffects;
import net.minecraft.client.Minecraft;
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
        assert Minecraft.getInstance().player != null;
        VampirePlayer vampire = VampirePlayer.getOpt(Minecraft.getInstance().player).resolve().orElse(null);
        if (Minecraft.getInstance().player.tickCount % 10 == 0) {
            if ((VampirismConfig.CLIENT.renderVampireForestFog.get() ||
                    VampirismConfig.SERVER.enforceRenderForestFog.get()) &&
                    (Helper.isEntityInArtificalVampireFogArea(Minecraft.getInstance().player) ||
                            Helper.isEntityInVampireBiome(Minecraft.getInstance().player))) {
                vampireBiomeFogDistanceMultiplier += vampire != null && Minecraft.getInstance().player.hasEffect(VDEffects.FOG_VISION.get()) ? 1 : 0;
            }
        }
    }
}
