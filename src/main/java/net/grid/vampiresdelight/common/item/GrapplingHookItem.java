package net.grid.vampiresdelight.common.item;

import net.grid.vampiresdelight.common.entity.GrapplingHookEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

public class GrapplingHookItem extends Item {
    public GrapplingHookItem(Properties properties) {
        super(properties);
    }

    public static float getPowerForTime(int p_40662_) {
        float f = (float)p_40662_ / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
    }

    public int getUseDuration(ItemStack p_40680_) {
        return 72000;
    }
    public UseAnim getUseAnimation(ItemStack p_40678_) {
        return UseAnim.SPEAR;
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        return InteractionResultHolder.pass(itemstack);
    }

    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int i) {
        entity.gameEvent(GameEvent.ITEM_INTERACT_FINISH);
        if (!level.isClientSide) {

            boolean left = (entity.getUsedItemHand() == InteractionHand.OFF_HAND && entity.getMainArm() == HumanoidArm.RIGHT)
                    || (entity.getUsedItemHand() == InteractionHand.MAIN_HAND && entity.getMainArm() == HumanoidArm.LEFT);

            int power = this.getUseDuration(stack) - i;
            GrapplingHookEntity hook = new GrapplingHookEntity(level, entity, !left);

            Vec3 vector3d = entity.getViewVector(1.0F);
            hook.shoot((double) vector3d.x(), (double) vector3d.y(), (double) vector3d.z(), getPowerForTime(power) * 3, 1);
            hook.setXRot(entity.getXRot());
            hook.setYRot(entity.getYRot());
            level.addFreshEntity(hook);
            stack.hurtAndBreak(1, entity, (playerIn) -> {
                entity.broadcastBreakEvent(playerIn.getUsedItemHand());
            });
        }
    }
}
