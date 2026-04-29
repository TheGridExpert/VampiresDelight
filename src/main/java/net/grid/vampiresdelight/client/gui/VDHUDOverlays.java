package net.grid.vampiresdelight.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import de.teamlapen.vampirism.api.client.VIngameOverlays;
import de.teamlapen.vampirism.api.entity.player.vampire.IBloodStats;
import de.teamlapen.vampirism.entity.player.vampire.VampirePlayer;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.utility.VDHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import vectorwing.farmersdelight.client.gui.HUDOverlays;
import vectorwing.farmersdelight.common.Configuration;
import vectorwing.farmersdelight.common.registry.ModEffects;

import java.util.Random;

public class VDHUDOverlays {
    public static int bloodIconsOffset;
    private static final ResourceLocation VD_ICONS_TEXTURE = ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "textures/gui/icons.png");

    public static void register(RegisterGuiLayersEvent event) {
        event.registerBelow(
                VIngameOverlays.BLOOD_BAR_ID,
                ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "blood_offset"),
                (guiGraphics, deltaTracker) -> bloodIconsOffset = Minecraft.getInstance().gui.rightHeight);
        event.registerAbove(VIngameOverlays.BLOOD_BAR_ID, NourishmentBloodOverlay.ID, new NourishmentBloodOverlay());
    }

    public static class NourishmentBloodOverlay extends HUDOverlays.BaseOverlay {
        public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "blood_nourishment");

        @Override
        public void render(Minecraft minecraft, Player player, GuiGraphics guiGraphics, int left, int right, int top, int guiTicks) {
            IBloodStats bloodStats = VampirePlayer.get(player).getBloodStats();

            boolean isPlayerHealingWithSaturation =
                    player.level().getGameRules().getBoolean(GameRules.RULE_NATURAL_REGENERATION)
                            && player.isHurt()
                            && bloodStats.getBloodLevel() >= 18;

            if (player.getEffect(ModEffects.NOURISHMENT) != null) {
                drawNourishmentBloodOverlay(bloodStats, VampirePlayer.get(player).getBloodSaturation(), minecraft, guiGraphics, right, top - bloodIconsOffset, isPlayerHealingWithSaturation);
            }
        }

        @Override
        public boolean shouldRenderOverlay(Minecraft minecraft, Player player, GuiGraphics guiGraphics, int guiTicks) {
            if (!super.shouldRenderOverlay(minecraft, player, guiGraphics, guiTicks))
                return false;

            return Configuration.ENABLE_NOURISHMENT_HUNGER_OVERLAY.get() && VDHelper.isVampire(player);
        }
    }

    public static void drawNourishmentBloodOverlay(IBloodStats bloodStats, float saturation, Minecraft minecraft, GuiGraphics graphics, int right, int top, boolean naturalHealing) {
        int blood = bloodStats.getBloodLevel();
        int maxBlood = bloodStats.getMaxBlood();
        int blood2 = blood - 20;
        int maxBlood2 = maxBlood - 20;

        int ticks = minecraft.gui.getGuiTicks();
        Random rand = new Random();
        rand.setSeed(ticks * 312871L);

        RenderSystem.enableBlend();

        for (int j = 0; j < 10; ++j) {
            int idx = j * 2 + 1;
            int x = right - j * 8 - 9;
            int y = top;

            if (saturation <= 0.0F && ticks % (blood * 3 + 1) == 0) {
                y = top + (rand.nextInt(3) - 1);
            }

            int naturalHealingOffset = naturalHealing ? 18 : 0;

            // Background texture
            graphics.blit(VD_ICONS_TEXTURE, x, y, 0, idx <= maxBlood2 ? 9 : 0, 9, 9);

            // Gilded hunger icons
            if (idx < blood) {
                graphics.blit(VD_ICONS_TEXTURE, x, y, 9 + naturalHealingOffset, idx < blood2 ? 9 : 0, 9, 9);
                if (idx == blood2) {
                    graphics.blit(VD_ICONS_TEXTURE, x, y, 18 + naturalHealingOffset, 9, 9, 9);
                }
            } else if (idx == blood) {
                graphics.blit(VD_ICONS_TEXTURE, x, y, 18 + naturalHealingOffset, 0, 9, 9);
            }
        }

        RenderSystem.disableBlend();
    }
}
