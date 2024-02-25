package net.grid.vampiresdelight.common.utility;

import net.grid.vampiresdelight.common.registry.VDEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

import java.util.Objects;

public class VDRenderUtils {
    public static int getFogDistanceMultiplier(Player player) {
        assert Minecraft.getInstance().player != null;
        int fogDistanceMultiplier = Minecraft.getInstance().player.hasEffect(VDEffects.FOG_VISION.get())
                ? Objects.requireNonNull(player.getEffect(VDEffects.FOG_VISION.get())).getAmplifier() + 1 : 0;

        return fogDistanceMultiplier;
    }
}