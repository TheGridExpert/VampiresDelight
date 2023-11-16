package net.grid.vampiresdelight.common.mixin;

import de.teamlapen.vampirism.core.ModItems;
import de.teamlapen.vampirism.items.VampirismSwordItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vectorwing.farmersdelight.common.registry.ModEnchantments;

@Mixin(VampirismSwordItem.class)
public class MixinVampirismSwordItem {
    @Inject(at = @At("RETURN"), method = "canApplyAtEnchantingTable(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/enchantment/Enchantment;)Z", remap = false, cancellable = true)
    public void canBackstabbingBeApplied(ItemStack stack, Enchantment enchantment, CallbackInfoReturnable<Boolean> cir) {
        if ((enchantment == ModEnchantments.BACKSTABBING.get())
                && (stack.is(ModItems.HUNTER_AXE_NORMAL.get())
                || stack.is(ModItems.HUNTER_AXE_ENHANCED.get())
                || stack.is(ModItems.HUNTER_AXE_ULTIMATE.get())
                || stack.is(ModItems.STAKE.get()))) {
            cir.setReturnValue(true);
        }
    }
}
