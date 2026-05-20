package net.grid.vampiresdelight.misc.mixin;

import de.teamlapen.vampirism.core.ModItems;
import net.grid.vampiresdelight.common.config.VDCommonConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vectorwing.farmersdelight.FarmersDelight;

@Mixin(Enchantment.class)
public class EnchantmentMixin {

    @Inject(at = @At("RETURN"), method = "canEnchant", cancellable = true)
    public void canBackstabbingBeApplied(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (!VDCommonConfig.BACKSTABBING_CAN_BE_APPLIED_TO_HUNTER_WEAPON.get()) return;
        Enchantment backstabbing = ForgeRegistries.ENCHANTMENTS.getValue(ResourceLocation.fromNamespaceAndPath(FarmersDelight.MODID, "backstabbing"));
        if (backstabbing == null) return;

        Enchantment self = (Enchantment) (Object) this;
        if (self != backstabbing) return;

        if (stack.is(ModItems.HUNTER_AXE_NORMAL.get()) || stack.is(ModItems.HUNTER_AXE_ENHANCED.get()) || stack.is(ModItems.HUNTER_AXE_ULTIMATE.get()) || stack.is(ModItems.STAKE.get())) {
            cir.setReturnValue(true);
        }
    }
}
