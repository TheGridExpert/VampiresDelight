package net.grid.vampiresdelight.misc.mixin.accessor;

import de.teamlapen.vampirism.entity.player.vampire.BloodStats;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BloodStats.class)
public interface BloodStatsAccessor {

    @Accessor(value = "bloodSaturationLevel", remap = false)
    float getBloodSaturation();

    @Accessor(value = "bloodExhaustionLevel", remap = false)
    void setBloodExhaustion(float exhaustion);
}
