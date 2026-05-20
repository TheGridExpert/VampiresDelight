package net.grid.vampiresdelight.misc.mixin;

import de.teamlapen.vampirism.VampirismMod;
import de.teamlapen.vampirism.util.Helper;
import net.grid.vampiresdelight.common.util.VDEntityUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vectorwing.farmersdelight.common.item.ConsumableItem;
import vectorwing.farmersdelight.common.utility.TextUtils;

import java.util.List;

@Mixin(TextUtils.class)
public class TextUtilsMixin {

    @Inject(method = "addFoodEffectTooltip(Lnet/minecraft/world/item/ItemStack;Ljava/util/List;F)V", at = @At(value = "HEAD"), cancellable = true, remap = false)
    private static void vampiresdelight$addFoodEffectTooltip(ItemStack stack, List<Component> lores, float durationFactor, CallbackInfo ci) {
        Player player = VampirismMod.proxy.getClientPlayer();
        if (player != null && Helper.isVampire(player) && stack.getItem() instanceof ConsumableItem && !VDEntityUtils.canVampireConsume(stack)) {
            ci.cancel();
        }
    }

    @ModifyArg(
            method = "addFoodEffectTooltip(Lnet/minecraft/world/item/ItemStack;Ljava/util/List;F)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item;getFoodProperties(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/LivingEntity;)Lnet/minecraft/world/food/FoodProperties;"),
            index = 1,
            remap = false
    )
    private static LivingEntity vampiresdelight$injectClientPlayer(LivingEntity entity) {
        Player player = VampirismMod.proxy.getClientPlayer();
        return player != null ? player : entity;
    }
}