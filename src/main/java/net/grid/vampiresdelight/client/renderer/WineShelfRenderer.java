package net.grid.vampiresdelight.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.grid.vampiresdelight.common.world.block.WineShelfBlock;
import net.grid.vampiresdelight.common.world.blockentity.WineShelfBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.RenderTypeHelper;
import net.minecraftforge.client.model.data.ModelData;

public class WineShelfRenderer implements BlockEntityRenderer<WineShelfBlockEntity> {

    public WineShelfRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(WineShelfBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        Level level = blockEntity.getLevel();
        if (level == null) return;

        BlockState state = blockEntity.getBlockState();
        Direction direction = state.getValue(WineShelfBlock.FACING);

        WineShelfBlock.SLOT_NAMES.forEach((property, suffix) -> {
            WineShelfBlock.Slot slot = state.getValue(property);
            if (slot == WineShelfBlock.Slot.EMPTY) return;

            ResourceLocation location = slot.getModel();
            if (location == null) return;

            BakedModel model = Minecraft.getInstance().getModelManager().getModel(location.withSuffix(suffix));
            ModelData modelData = model.getModelData(level, blockEntity.getBlockPos(), state, ModelData.EMPTY);

            poseStack.pushPose();
            poseStack.rotateAround(Axis.YP.rotationDegrees(-(direction.toYRot() + 180)), 0.5F, 0.5F, 0.5F);

            for (RenderType renderType : model.getRenderTypes(state, RandomSource.create(42), modelData)) {
                Minecraft.getInstance().getBlockRenderer().getModelRenderer().renderModel(poseStack.last(), buffer.getBuffer(RenderTypeHelper.getEntityRenderType(renderType, false)), state, model, 1.0F, 1.0F, 1.0F, packedLight, packedOverlay, modelData, renderType);
            }

            poseStack.popPose();
        });
    }
}
