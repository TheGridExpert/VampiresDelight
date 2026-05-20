package net.grid.vampiresdelight.misc.mixin;

import de.teamlapen.vampirism.util.Helper;
import net.grid.vampiresdelight.common.util.VDEntityUtils;
import net.grid.vampiresdelight.common.world.item.ICustomUseItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vectorwing.farmersdelight.common.item.ConsumableItem;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow public abstract ItemStack getUseItem();

    @Inject(method = "addEatEffect", at = @At("HEAD"), cancellable = true)
    private void vampiresdelight$addEatEffect(ItemStack food, Level level, LivingEntity livingEntity, CallbackInfo ci) {
        if (Helper.isVampire(livingEntity) && food.getItem() instanceof ConsumableItem && !VDEntityUtils.canVampireConsume(food)) {
            ci.cancel();
        }
    }

    @Inject(method = "shouldTriggerItemUseEffects()Z", at = @At("HEAD"), cancellable = true)
    private void vampiresdelight$onShouldTriggerUseEffects(CallbackInfoReturnable<Boolean> cir) {
        Item usedItem = getUseItem().getItem();
        if (usedItem instanceof ICustomUseItem customUseItem) {
            cir.setReturnValue(customUseItem.hasCustomUseEffects());
        }
    }

    @Inject(
            method = "triggerItemUseEffects(Lnet/minecraft/world/item/ItemStack;I)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getUseAnimation()Lnet/minecraft/world/item/UseAnim;", ordinal = 0),
            cancellable = true
    )
    private void vampiresdelight$onTriggerUseEffects(ItemStack stack, int amount, CallbackInfo ci) {
        Item usedItem = stack.getItem();
        if (usedItem instanceof ICustomUseItem customUseItem) {
            if (customUseItem.triggerUseEffects(stack, (LivingEntity) (Object) this, amount)) {
                ci.cancel();
            }
        }
    }
}
