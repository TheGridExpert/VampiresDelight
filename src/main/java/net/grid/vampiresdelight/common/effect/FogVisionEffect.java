package net.grid.vampiresdelight.common.effect;

import net.minecraft.util.FastColor;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class FogVisionEffect extends MobEffect {
    public FogVisionEffect() {
        super(MobEffectCategory.BENEFICIAL, FastColor.ARGB32.color(100, 135, 105, 150));
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
