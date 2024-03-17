package net.grid.vampiresdelight.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.grid.vampiresdelight.common.block.DarkStoneStoveBlock;
import net.grid.vampiresdelight.common.block.entity.DarkStoneStoveBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.items.ItemStackHandler;

public class DarkStoneStoveRenderer implements BlockEntityRenderer<DarkStoneStoveBlockEntity> {
    public DarkStoneStoveRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(DarkStoneStoveBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        Direction direction = pBlockEntity.getBlockState().getValue(DarkStoneStoveBlock.FACING).getOpposite();

        ItemStackHandler inventory = pBlockEntity.getInventory();
        int posLong = (int) pBlockEntity.getBlockPos().asLong();

        for (int i = 0; i < inventory.getSlots(); ++i) {
            ItemStack stoveStack = inventory.getStackInSlot(i);
            if (!stoveStack.isEmpty()) {
                pPoseStack.pushPose();

                // Center item above the stove
                pPoseStack.translate(0.5D, 1.02D, 0.5D);

                // Rotate item to face the stove's front side
                float f = -direction.toYRot();
                pPoseStack.mulPose(Axis.YP.rotationDegrees(f));

                // Rotate item flat on the stove. Use X and Y from now on
                pPoseStack.mulPose(Axis.XP.rotationDegrees(90.0F));

                // Neatly align items according to their index
                Vec2 itemOffset = pBlockEntity.getStoveItemOffset(i);
                pPoseStack.translate(itemOffset.x, itemOffset.y, 0.0D);

                // Resize the items
                pPoseStack.scale(0.375F, 0.375F, 0.375F);

                if (pBlockEntity.getLevel() != null)
                    Minecraft.getInstance().getItemRenderer().renderStatic(stoveStack, ItemDisplayContext.FIXED, LevelRenderer.getLightColor(pBlockEntity.getLevel(), pBlockEntity.getBlockPos().above()), pPackedOverlay, pPoseStack, pBuffer, pBlockEntity.getLevel(), posLong + i);
                pPoseStack.popPose();
            }
        }
    }
}
