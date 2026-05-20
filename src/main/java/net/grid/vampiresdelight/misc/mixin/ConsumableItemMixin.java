package net.grid.vampiresdelight.misc.mixin;

import de.teamlapen.vampirism.VampirismMod;
import net.grid.vampiresdelight.common.util.VDEntityUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vectorwing.farmersdelight.common.item.ConsumableItem;

import javax.annotation.Nullable;
import java.util.List;

@Mixin(ConsumableItem.class)
public class ConsumableItemMixin {

    @Redirect(
            method = "finishUsingItem",
            at = @At(
                    value = "INVOKE",
                    target = "Lvectorwing/farmersdelight/common/item/ConsumableItem;affectConsumer(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;)V",
                    remap = false
            )
    )
    private void vampiresdelight$wrapAffectConsumer(ConsumableItem instance, ItemStack stack, Level level, LivingEntity consumer) {
        if (consumer instanceof Player player && !VDEntityUtils.canConsumeHumanFood(player)) {
            return;
        }
        instance.affectConsumer(stack, level, consumer);
    }

    @Inject(method = "appendHoverText", at = @At("HEAD"), cancellable = true)
    private void vampiresdelight$cancelAppendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag, CallbackInfo ci) {
        Player player = VampirismMod.proxy.getClientPlayer();
        if (player != null && !VDEntityUtils.canConsumeHumanFood(player)) {
            ci.cancel();
        }
    }
}