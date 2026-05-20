package net.grid.vampiresdelight.client.extension;

import com.mojang.blaze3d.vertex.PoseStack;
import net.grid.vampiresdelight.VampiresDelight;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;

public class PourableBottleItemExtension implements IClientItemExtensions {

    public static final HumanoidModel.ArmPose POURING_POSE = HumanoidModel.ArmPose.create(VampiresDelight.MODID + ":pouring", true, PourableBottleItemExtension::makePose);

    private static void makePose(HumanoidModel<?> model, LivingEntity entity, HumanoidArm arm) {
        boolean isRightArmPouring = arm == HumanoidArm.RIGHT;
        int multiplier = isRightArmPouring ? -1 : 1;

        ModelPart pouringHand = isRightArmPouring ? model.rightArm : model.leftArm;
        ModelPart glassHand = isRightArmPouring ? model.leftArm : model.rightArm;
        ModelPart head = model.head;

        pouringHand.xRot = (float) Math.toRadians(-135) + head.xRot / 2f;
        pouringHand.yRot = (float) Math.toRadians(45) * multiplier + head.yRot / 2.5f;

        glassHand.xRot = (float) Math.toRadians(-50) + head.xRot / 2f;
        glassHand.yRot = (float) Math.toRadians(25) * -multiplier + head.yRot / 2.5f;
    }

    @Nullable
    @Override
    public HumanoidModel.ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
        return !itemStack.isEmpty() && entityLiving.getUsedItemHand() == hand && entityLiving.getUseItemRemainingTicks() > 0 ? POURING_POSE : HumanoidModel.ArmPose.EMPTY;
    }

    @Override
    public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm, ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess) {
        if (player.isUsingItem() && player.getUseItemRemainingTicks() > 0) {
            boolean isRightArmPouring = arm == HumanoidArm.RIGHT;
            int multiplier = isRightArmPouring ? -1 : 1;

            poseStack.translate(0.25f * multiplier, 0.4f, 0.0f);
        }

        return false;
    }
}
