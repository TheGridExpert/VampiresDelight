package net.grid.vampiresdelight.misc.mixin;

import de.teamlapen.vampirism.entity.player.vampire.VampirePlayer;
import de.teamlapen.vampirism.util.Helper;
import net.grid.vampiresdelight.misc.mixin.accessor.BloodStatsAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vectorwing.farmersdelight.common.effect.NourishmentEffect;

@Mixin(NourishmentEffect.class)
public class NourishmentEffectMixin {

    @Inject(method = "applyEffectTick(Lnet/minecraft/world/entity/LivingEntity;I)V", at = @At("TAIL"))
    private void vampiresdelight$applyForVampires(LivingEntity entity, int amplifier, CallbackInfo ci) {
        if (entity.level().isClientSide()) return;
        if (entity instanceof Player player && Helper.isVampire(player)) {
            VampirePlayer.getOpt(player).ifPresent(vampire ->
                    ((BloodStatsAccessor) vampire.getBloodStats()).setBloodExhaustion(0.0F)
            );
        }
    }
}