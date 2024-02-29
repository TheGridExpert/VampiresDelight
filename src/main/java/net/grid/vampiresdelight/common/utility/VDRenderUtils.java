package net.grid.vampiresdelight.common.utility;

import net.grid.vampiresdelight.common.registry.VDEffects;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;

import java.util.Objects;

public class VDRenderUtils {
    public static float getFogDistanceMultiplier(Player player) {
        assert Minecraft.getInstance().player != null;
        float fogDistanceMultiplier = Minecraft.getInstance().player.hasEffect(VDEffects.FOG_VISION.get())
                ? Objects.requireNonNull(player.getEffect(VDEffects.FOG_VISION.get())).getAmplifier() + 1 : 0;

        if (player.getItemInHand(InteractionHand.MAIN_HAND).getItem() == VDItems.SPIRIT_LANTERN.get() ||
                player.getItemInHand(InteractionHand.OFF_HAND).getItem() == VDItems.SPIRIT_LANTERN.get())
            fogDistanceMultiplier += 0.4f;

        return fogDistanceMultiplier;
    }
}
