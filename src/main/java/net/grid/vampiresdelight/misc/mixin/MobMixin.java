package net.grid.vampiresdelight.misc.mixin;

import net.grid.vampiresdelight.common.core.VDEffects;
import net.grid.vampiresdelight.common.util.VDEntityUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mob.class)
public class MobMixin {

    @Inject(method = "setTarget", at = @At("HEAD"))
    private void vampiresdelight$banishUnholySpiritsOnTarget(LivingEntity target, CallbackInfo ci) {
        Mob mob = (Mob) (Object) this;

        if (target == null || mob.level().isClientSide) return;
        if (!target.hasEffect(VDEffects.CONSECRATION.get())) return;

        VDEntityUtils.tryBanishUnholySpirit(mob);
    }
}