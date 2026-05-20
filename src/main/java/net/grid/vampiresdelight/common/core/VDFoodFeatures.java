package net.grid.vampiresdelight.common.core;

import de.teamlapen.vampirism.core.ModEffects;
import de.teamlapen.vampirism.util.Helper;
import net.grid.vampiresdelight.common.util.VDEntityUtils;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;
import java.util.function.Consumer;

public class VDFoodFeatures {

    public static final Consumer<LivingEntity> DAISY_TEA = entity -> {
        if (VDEntityUtils.canConsumeHumanFood(entity)) {
            VDEntityUtils.cureEffects(entity, List.of(
                    MobEffects.MOVEMENT_SLOWDOWN,
                    MobEffects.WEAKNESS,
                    MobEffects.BLINDNESS,
                    MobEffects.CONFUSION
            ));
        }
    };

    public static final Consumer<LivingEntity> ICE_CREAM = Entity::clearFire;

    public static final Consumer<LivingEntity> CURSED_CUPCAKE = entity -> {
        if (Helper.isVampire(entity)) {
            VDEntityUtils.cureEffect(ModEffects.GARLIC, entity);
        }
    };

    public static final Consumer<LivingEntity> ORCHID_TEA = VDEntityUtils::tryInfectWithoutPoisoning;
}