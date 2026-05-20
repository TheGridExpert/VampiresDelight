package net.grid.vampiresdelight.misc.mixin;

import de.teamlapen.vampirism.VampirismMod;
import de.teamlapen.vampirism.client.renderer.RenderHandler;
import net.grid.vampiresdelight.common.core.VDEffects;
import net.grid.vampiresdelight.common.core.VDItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.ViewportEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(RenderHandler.class)
public class RenderHandlerMixin {

    @ModifyVariable(method = "onRenderFog(Lnet/minecraftforge/client/event/ViewportEvent$RenderFog;)V", at = @At(value = "STORE", ordinal = 1), remap = false, name = "f")
    private float vampiresdelight$modifyFogDistanceMultiplier(float fogDistanceMultiplier, ViewportEvent.RenderFog event) {
        Player player = VampirismMod.proxy.getClientPlayer();
        if (player == null) return fogDistanceMultiplier;

        float addition = 0;
        if (player.hasEffect(VDEffects.FOG_VISION.get())) {
            addition += 3.0F;
        }

        for (ItemStack stack : player.getHandSlots()) {
            if (stack.is(VDItems.SPIRIT_LANTERN.get())) {
                addition += 0.4F;
                break;
            }
        }

        return fogDistanceMultiplier + addition;
    }
}