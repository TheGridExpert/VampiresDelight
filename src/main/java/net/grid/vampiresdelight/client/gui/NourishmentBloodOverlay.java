package net.grid.vampiresdelight.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import de.teamlapen.vampirism.entity.player.vampire.VampirePlayer;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.mixin.accessor.BloodStatsAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.GuiOverlayManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import vectorwing.farmersdelight.common.Configuration;
import vectorwing.farmersdelight.common.registry.ModEffects;

import java.util.Random;

public class NourishmentBloodOverlay {
    public static int bloodIconsOffset;
    private static final ResourceLocation ICONS_TEXTURE = new ResourceLocation(VampiresDelight.MODID, "textures/gui/icons.png");

    public static void init() {
        MinecraftForge.EVENT_BUS.register(new NourishmentBloodOverlay());
    }

    static ResourceLocation BLOOD_BAR_ID = new ResourceLocation("vampirism", "blood_bar");

    @SubscribeEvent
    public void onRenderGuiOverlayPost(RenderGuiOverlayEvent.Post event) {
        if (event.getOverlay() == GuiOverlayManager.findOverlay(BLOOD_BAR_ID)) {
            Minecraft mc = Minecraft.getInstance();
            ForgeGui gui = (ForgeGui) mc.gui;
            boolean isMounted = mc.player != null && mc.player.getVehicle() instanceof LivingEntity;
            if (!isMounted && !mc.options.hideGui && gui.shouldDrawSurvivalElements()) {
                renderNourishmentOverlay(gui, event.getGuiGraphics());
            }
        }
    }

    public static void renderNourishmentOverlay(ForgeGui gui, GuiGraphics graphics) {
        if (!Configuration.NOURISHED_HUNGER_OVERLAY.get()) {
            return;
        }

        bloodIconsOffset = gui.rightHeight;
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;

        if (player == null) {
            return;
        }

        VampirePlayer.getOpt(player).map(VampirePlayer::getBloodStats).ifPresent(stats -> {
            int top = minecraft.getWindow().getGuiScaledHeight() - bloodIconsOffset + 10;
            int left = minecraft.getWindow().getGuiScaledWidth() / 2 + 91;

            int blood = stats.getBloodLevel();
            int maxBlood = stats.getMaxBlood();
            int blood2 = blood - 20;
            int maxBlood2 = maxBlood - 20;
            float saturation = ((BloodStatsAccessor) stats).getBloodSaturation();

            boolean isPlayerHealingWithSaturation =
                    player.level().getGameRules().getBoolean(GameRules.RULE_NATURAL_REGENERATION)
                            && player.isHurt()
                            && blood >= 18;

            if (player.getEffect(ModEffects.NOURISHMENT.get()) != null && !Float.isNaN(saturation)) {

                int ticks = minecraft.gui.getGuiTicks();
                Random rand = new Random();
                rand.setSeed(ticks * 312871L);

                RenderSystem.enableBlend();

                for (int j = 0; j < 10; ++j) {
                    int idx = j * 2 + 1;
                    int x = left - j * 8 - 9;
                    int y = top;

                    if (saturation <= 0.0F && ticks % (blood * 3 + 1) == 0) {
                        y = top + (rand.nextInt(3) - 1);
                    }

                    int naturalHealingOffset = isPlayerHealingWithSaturation ? 18 : 0;

                    // Background texture
                    graphics.blit(ICONS_TEXTURE, x, y, 0, idx <= maxBlood2 ? 9 : 0, 9, 9);

                    // Gilded hunger icons
                    if (idx < blood) {
                        graphics.blit(ICONS_TEXTURE, x, top, 9 + naturalHealingOffset, idx < blood2 ? 9 : 0, 9, 9);
                        if (idx == blood2) {
                            graphics.blit(ICONS_TEXTURE, x, top, 18 + naturalHealingOffset, 9, 9, 9);
                        }
                    } else if (idx == blood) {
                        graphics.blit(ICONS_TEXTURE, x, top, 18 + naturalHealingOffset, 0, 9, 9);
                    }
                }

                RenderSystem.disableBlend();
            }
        });
    }
}
