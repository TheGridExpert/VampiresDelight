package net.grid.vampiresdelight.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.block.entity.container.BrewingBarrelMenu;
import net.grid.vampiresdelight.common.util.VDTextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
@ParametersAreNonnullByDefault
public class BrewingBarrelScreen extends AbstractContainerScreen<BrewingBarrelMenu> {
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(VampiresDelight.MODID, "textures/gui/brewing_barrel.png");
    private static final Rectangle PROGRESS_BUBBLES = new Rectangle(75, 27, 0, 13);

    public BrewingBarrelScreen(BrewingBarrelMenu screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    public void render(PoseStack ms, final int mouseX, final int mouseY, float partialTicks) {
        this.renderBackground(ms);
        this.renderBg(ms, partialTicks, mouseX, mouseY);
        this.renderMealDisplayTooltip(ms, mouseX, mouseY);
        super.render(ms, mouseX, mouseY, partialTicks);
    }

    protected void renderMealDisplayTooltip(PoseStack ms, int mouseX, int mouseY) {
        if (this.minecraft != null && this.minecraft.player != null && this.menu.getCarried().isEmpty() && this.hoveredSlot != null && this.hoveredSlot.hasItem()) {
            if (this.hoveredSlot.index == 4) {
                List<Component> tooltip = new ArrayList<>();

                ItemStack mealStack = this.hoveredSlot.getItem();
                tooltip.add(((MutableComponent) mealStack.getItem().getDescription()).withStyle(mealStack.getRarity().color));

                ItemStack containerStack = this.menu.blockEntity.getContainer();
                String container = !containerStack.isEmpty() ? containerStack.getItem().getDescription().getString() : "";

                tooltip.add(VDTextUtils.getTranslation("container.brewing_barrel.poured_into", container).withStyle(ChatFormatting.GRAY));

                this.renderComponentTooltip(ms, tooltip, mouseX, mouseY);
            } else {
                this.renderTooltip(ms, this.hoveredSlot.getItem(), mouseX, mouseY);
            }
        }
    }

    @Override
    protected void renderLabels(PoseStack ms, int mouseX, int mouseY) {
        super.renderLabels(ms, mouseX, mouseY);
        this.font.draw(ms, this.playerInventoryTitle, 8.0f, (float) (this.imageHeight - 96 + 2), 4210752);
    }

    @Override
    protected void renderBg(PoseStack ms, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);
        this.blit(ms, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

        // Render progress arrow
        int l = this.menu.getBrewProgressionScaled();
        this.blit(ms, this.leftPos + PROGRESS_BUBBLES.x, this.topPos + PROGRESS_BUBBLES.y, 176, 0, l + 1, PROGRESS_BUBBLES.height);
    }
}
