package net.grid.vampiresdelight.common.mixin;

import de.teamlapen.vampirism.VampirismMod;
import net.grid.vampiresdelight.common.VDConfiguration;
import net.grid.vampiresdelight.common.utility.VDEntityUtils;
import net.grid.vampiresdelight.common.utility.VDIntegrationUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vectorwing.farmersdelight.common.item.ConsumableItem;
import vectorwing.farmersdelight.common.item.MilkBottleItem;
import vectorwing.farmersdelight.common.item.PopsicleItem;

import java.util.List;

@Mixin(ConsumableItem.class)
public class MixinConsumableItem {
    @Shadow
    public void affectConsumer(ItemStack stack, Level level, LivingEntity consumer) {}

    @Redirect(method = "finishUsingItem", at = @At(value = "INVOKE", target = "Lvectorwing/farmersdelight/common/item/ConsumableItem;affectConsumer(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;)V"))
    private void checkIfCanAffectConsumer(ConsumableItem instance, ItemStack stack, Level level, LivingEntity consumer) {
        if (VDEntityUtils.canConsumeHumanFood(consumer) || stack.getItem() instanceof MilkBottleItem || stack.getItem() instanceof PopsicleItem || VDConfiguration.ENABLE_FD_FOOD_EFFECTS_DESPITE_FACTION.get()) {
            this.affectConsumer(stack, level, consumer);
        } else if (consumer instanceof Player player && VDIntegrationUtils.isWerewolf(player)) {
            player.displayClientMessage(Component.translatable("text.werewolves.taste_not_right"), true);
        }
    }

    @Inject(method = "appendHoverText(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/Item$TooltipContext;Ljava/util/List;Lnet/minecraft/world/item/TooltipFlag;)V", at = @At("HEAD"), cancellable = true)
    public void hideFoodTooltips(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag isAdvanced, CallbackInfo ci) {
        if (VDConfiguration.ENABLE_FD_FOOD_EFFECTS_DESPITE_FACTION.get()) return;

        Player player = VampirismMod.proxy.getClientPlayer();
        if (player != null && !VDEntityUtils.canConsumeHumanFood(player) && !(stack.getItem() instanceof MilkBottleItem)) {
            ci.cancel();
        }
    }
}
