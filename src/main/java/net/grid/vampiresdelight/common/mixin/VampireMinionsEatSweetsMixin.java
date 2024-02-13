package net.grid.vampiresdelight.common.mixin;

import de.teamlapen.vampirism.entity.minion.VampireMinionEntity;
import net.grid.vampiresdelight.common.tag.VDTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VampireMinionEntity.class)
public abstract class VampireMinionsEatSweetsMixin {
    @Shadow
    public abstract @NotNull LivingEntity getRepresentingEntity();

    @Inject(at = @At(value = "HEAD"), method = "canConsume(Lnet/minecraft/world/item/ItemStack;)Z", remap = false, cancellable = true)
    public void isSweet(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        boolean fullHealth = this.getRepresentingEntity().getHealth() == this.getRepresentingEntity().getMaxHealth();
        if (stack.is(VDTags.MINION_VAMPIRE_FOOD) && !fullHealth) cir.setReturnValue(true);
    }
}
