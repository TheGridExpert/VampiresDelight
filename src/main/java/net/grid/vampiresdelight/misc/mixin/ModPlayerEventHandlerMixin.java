package net.grid.vampiresdelight.misc.mixin;


import de.teamlapen.vampirism.entity.player.ModPlayerEventHandler;
import de.teamlapen.vampirism.items.GarlicBreadItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ModPlayerEventHandler.class)
public class ModPlayerEventHandlerMixin {

    @Inject(at = @At("HEAD"), method = "checkItemUsePerm(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/player/Player;)Z", remap = false, cancellable = true)
    private void checkGarlicBreadUse(ItemStack stack, Player player, CallbackInfoReturnable<Boolean> cir) {
        if (stack.getItem() instanceof GarlicBreadItem) {
            cir.setReturnValue(true);
        }
    }
}
