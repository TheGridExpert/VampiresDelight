package net.grid.vampiresdelight.client.renders;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.grid.vampiresdelight.common.entity.GrapplingHookEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class GrapplingHookEntityRenderer extends EntityRenderer<GrapplingHookEntity> {
    private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("vampiresdelight:textures/entity/grappling_hook.png");

    public GrapplingHookEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    public void render(GrapplingHookEntity entity, float partialTicks, float p_113841_, PoseStack matrixStack, MultiBufferSource bufferSource, int packedLightIn) {
        matrixStack.pushPose();

        // Apply Y rotation
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(partialTicks, entity.yRotO, entity.getYRot()) - 90.0F));

        // Apply Z rotation
        matrixStack.mulPose(Vector3f.ZP.rotationDegrees(Mth.lerp(partialTicks, entity.xRotO, entity.getXRot())));

        // Define constants
        float[] vertexData = {
                -7, -2, -2, 0.0F, 0.15625F, -1, 0, 0,
                -7, -2, 2, 0.15625F, 0.15625F, -1, 0, 0,
                -7, 2, 2, 0.15625F, 0.3125F, -1, 0, 0,
                -7, 2, -2, 0.0F, 0.3125F, -1, 0, 0,
                -7, 2, -2, 0.0F, 0.15625F, 1, 0, 0,
                -7, 2, 2, 0.15625F, 0.15625F, 1, 0, 0,
                -7, -2, 2, 0.15625F, 0.3125F, 1, 0, 0,
                -7, -2, -2, 0.0F, 0.3125F, 1, 0, 0,
        };

        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entityCutout(getTextureLocation(entity)));
        PoseStack.Pose pose = matrixStack.last();
        Matrix4f matrix4f = pose.pose();
        Matrix3f matrix3f = pose.normal();

        // Render vertices
        for (int i = 0; i < vertexData.length; i += 8) {
            vertex(matrix4f, matrix3f, vertexConsumer, vertexData[i], vertexData[i + 1], vertexData[i + 2],
                    vertexData[i + 3], vertexData[i + 4], (int) vertexData[i + 5], 0, 0, packedLightIn);
        }

        matrixStack.popPose();
        super.render(entity, partialTicks, p_113841_, matrixStack, bufferSource, packedLightIn);
    }

    private void vertex(Matrix4f matrix4f, Matrix3f matrix3f, VertexConsumer vertexConsumer, float x, float y, float z,
                        float u, float v, int normalX, int normalY, int normalZ, int overlayCoords) {
        vertexConsumer.vertex(matrix4f, x, y, z)
                .color(255, 255, 255, 255)
                .uv(u, v)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(overlayCoords)
                .normal(matrix3f, normalX, normalY, normalZ)
                .endVertex();
    }

    @Override
    public ResourceLocation getTextureLocation(GrapplingHookEntity entity) {
        return TEXTURE_LOCATION;
    }
}
