package net.grid.vampiresdelight.common.mixin;

import de.teamlapen.vampirism.core.ModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vectorwing.farmersdelight.common.item.enchantment.BackstabbingEnchantment;

@Mixin(Enchantment.class)
public class MixinEnchantment {
    @Inject(at = @At("RETURN"), method = "canEnchant", cancellable = true)
    public void canBackstabbingBeApplied(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if ((Enchantment) (Object) this instanceof BackstabbingEnchantment)
            if (stack.is(ModItems.HUNTER_AXE_NORMAL.get())
                    || stack.is(ModItems.HUNTER_AXE_ENHANCED.get())
                    || stack.is(ModItems.HUNTER_AXE_ULTIMATE.get())
                    || stack.is(ModItems.STAKE.get())) {
                cir.setReturnValue(true);
            }
    }
}
