package net.grid.vampiresdelight.common.effect;

import de.teamlapen.vampirism.util.Helper;
import net.grid.vampiresdelight.common.registry.VDEffects;
import net.grid.vampiresdelight.common.registry.VDSounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.FastColor;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.List;

public class BlessingEffect extends MobEffect {

    public BlessingEffect() {
        super(MobEffectCategory.NEUTRAL, FastColor.ARGB32.color(100, 255, 223, 136));
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (!Helper.isVampire(entity)) {
            resistPhantoms(entity.getLevel(), entity.getX(), entity.getY(), entity.getZ());
        } else {
            entity.removeEffect(VDEffects.BLESSING.get());
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    public static void resistPhantoms(final LevelAccessor world, final double x, final double y, final double z) {
        Vec3 center = new Vec3(x, y, z);
        List<LivingEntity> unsortedEntityFound = world.getEntitiesOfClass(LivingEntity.class, new AABB(center, center).inflate(6.0), e -> true);
        List<LivingEntity> sortedEntitiesFound = unsortedEntityFound.stream().sorted(Comparator.comparingDouble(t -> t.distanceToSqr(center))).toList();

        for (LivingEntity entity : sortedEntitiesFound) {
            double entityX = entity.getX();
            double entityY = entity.getY();
            double entityZ = entity.getZ();

            if (entity instanceof Phantom) {
                if (!entity.getEntityData().isEmpty()) {
                    entity.remove(Entity.RemovalReason.DISCARDED);

                    Level worldLevel = (Level) world;
                    worldLevel.playSound(null, entityX, entityY, entityZ, VDSounds.TRIANGLE.get(), SoundSource.NEUTRAL, 0.4f, worldLevel.random.nextFloat() * 0.5F);

                    generateBurningParticles(entityX, entityY, entityZ, 6, worldLevel);
                }
            }
        }
    }

    public static void generateBurningParticles(double entityX, double entityY, double entityZ, int amount, Level worldLevel) {
        for (int i = 0; i < amount; i++) {
            double randomDoubleWorld = worldLevel.random.nextDouble();
            worldLevel.addParticle(ParticleTypes.FLAME, entityX + randomDoubleWorld, entityY + randomDoubleWorld, entityZ + randomDoubleWorld,
                     (Math.random() * (0.3 + 0.3)) - 0.3, (Math.random() * (0.3 + 0.3)) - 0.3, (Math.random() * (0.3 + 0.3)) - 0.3);
        }
    }
}
