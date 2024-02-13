package net.grid.vampiresdelight.common.mixin.accessor;

import de.teamlapen.vampirism.entity.player.vampire.BloodStats;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BloodStats.class)
public interface BloodStatsAccessor {
    @Accessor("bloodSaturationLevel")
    float getBloodSaturation();
}
