package net.grid.vampiresdelight.common.effect;

import net.minecraft.util.FastColor;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class IntoxicationEffect extends MobEffect {
    protected IntoxicationEffect() {
        super(MobEffectCategory.HARMFUL, FastColor.ARGB32.color(100, 255, 223, 136));
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}