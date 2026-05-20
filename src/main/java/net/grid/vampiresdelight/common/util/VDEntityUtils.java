package net.grid.vampiresdelight.common.util;

import com.mojang.datafixers.util.Pair;
import de.teamlapen.vampirism.api.EnumStrength;
import de.teamlapen.vampirism.api.VReference;
import de.teamlapen.vampirism.api.entity.vampire.IVampire;
import de.teamlapen.vampirism.config.VampirismConfig;
import de.teamlapen.vampirism.core.ModEntities;
import de.teamlapen.vampirism.effects.SanguinareEffect;
import de.teamlapen.vampirism.entity.player.vampire.VampirePlayer;
import de.teamlapen.vampirism.entity.vampire.DrinkBloodContext;
import de.teamlapen.vampirism.items.VampirismItemBloodFoodItem;
import de.teamlapen.vampirism.util.DamageHandler;
import de.teamlapen.vampirism.util.Helper;
import net.grid.vampiresdelight.common.config.VDCommonConfig;
import net.grid.vampiresdelight.common.core.VDParticles;
import net.grid.vampiresdelight.common.core.VDSounds;
import net.grid.vampiresdelight.common.tag.VDEntityTags;
import net.grid.vampiresdelight.common.tag.VDItemTags;
import net.grid.vampiresdelight.common.world.item.FactionConsumableItem;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.function.Supplier;

public class VDEntityUtils {

    private static final float DEG_TO_RAD = (float) Math.PI / 180F;

    public static void addFoodEffects(FoodProperties foodProperties, Level level, LivingEntity entity) {
        for (Pair<MobEffectInstance, Float> pair : foodProperties.getEffects()) {
            if (!level.isClientSide && pair.getFirst() != null && level.random.nextFloat() < pair.getSecond()) {
                entity.addEffect(new MobEffectInstance(pair.getFirst()));
            }
        }
    }

    public static boolean canVampireConsume(ItemStack stack) {
        Item item = stack.getItem();
        return item instanceof VampirismItemBloodFoodItem || item instanceof FactionConsumableItem || stack.is(VDItemTags.BLOOD_FOOD);
    }

    public static void consumeBloodFood(ItemStack stack, Level level, LivingEntity consumer) {
        FoodProperties foodProperties = stack.getFoodProperties(consumer);
        if (foodProperties == null) return;

        if (Helper.isVampire(consumer)) {
            feedVampire(stack, level, consumer, foodProperties);
            addFoodEffects(foodProperties, level, consumer);

            if (consumer instanceof Player player) {
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
            } else {
                stack.shrink(1);
            }
        } else {
            consumer.eat(level, stack);
        }
    }

    private static void feedVampire(ItemStack stack, Level level, LivingEntity consumer, FoodProperties foodProperties) {
        if (consumer instanceof Player player) {
            VampirePlayer.get(player).drinkBlood(foodProperties.getNutrition(), foodProperties.getSaturationModifier(), false, new DrinkBloodContext(stack));
        } else if (consumer instanceof IVampire vampire) {
            vampire.drinkBlood(foodProperties.getNutrition(), foodProperties.getSaturationModifier(), false, new DrinkBloodContext(stack));
        }
        level.playSound(null, consumer.getX(), consumer.getY(), consumer.getZ(), SoundEvents.PLAYER_BURP, SoundSource.PLAYERS, 0.5F, consumer.getRandom().nextFloat() * 0.1F + 0.9F);
    }

    public static void cureEffect(Supplier<? extends MobEffect> mobEffect, LivingEntity entity) {
        MobEffect effect = mobEffect.get();
        if (effect != null && entity.hasEffect(effect)) {
            entity.removeEffect(effect);
        }
    }

    public static void cureEffects(LivingEntity entity, List<MobEffect> effects) {
        for (MobEffect effect : effects) {
            if (entity.hasEffect(effect)) {
                entity.removeEffect(effect);
            }
        }
    }

    public static boolean canConsumeHumanFood(LivingEntity consumer) {
        if (Helper.isVampire(consumer)) {
            return false;
        }
        if (VDIntegrationUtils.isWerewolf(consumer)) {
            return consumer instanceof Player player && VDIntegrationUtils.isWerewolfVegetarian(player);
        }
        return true;
    }

    public static boolean canConsumeHumanFood(LivingEntity consumer, ItemStack stack) {
        if (Helper.isVampire(consumer)) {
            return false;
        }
        if (VDIntegrationUtils.isWerewolf(consumer)) {
            return VDIntegrationUtils.canWerewolfEatFood(consumer, stack);
        }
        return true;
    }

    public static void affectVampireEntityWithGarlic(LivingEntity entity, EnumStrength strength) {
        if (entity instanceof Player player && Helper.isVampire(entity)) {
            VReference.VAMPIRE_FACTION.getPlayerCapability(player).ifPresent(vamp -> DamageHandler.affectVampireGarlicDirect(vamp, strength));
        } else if (entity instanceof IVampire vampire) {
            DamageHandler.affectVampireGarlicDirect(vampire, strength);
        }
    }

    public static boolean canBeInfectedFromItem(Player player) {
        return Helper.canBecomeVampire(player) && !VampirismConfig.SERVER.disableFangInfection.get();
    }

    // Infects consumer if it's a player that can become a vampire.
    public static void tryInfectWithoutPoisoning(LivingEntity consumer) {
        if (consumer instanceof Player player && canBeInfectedFromItem(player)) {
            SanguinareEffect.addRandom(consumer, true);
        }
    }

    public static boolean tryBanishUnholySpirit(Mob mob) {
        EntityType<?> type = mob.getType();
        if (!type.is(VDEntityTags.UNHOLY_SPIRITS)) return false;
        if (type == ModEntities.GHOST.get() && !VDCommonConfig.BLESSING_HELPS_AGAINST_GHOSTS.get()) return false;

        mob.level().playSound(null, mob.getX(), mob.getY(), mob.getZ(), VDSounds.TRIANGLE.get(), SoundSource.HOSTILE, 0.4f, mob.getRandom().nextFloat() * 0.5F);
        VDEntityUtils.spawnDispelParticlesAroundEntity(VDParticles.DISPEL.get(), mob, type == ModEntities.GHOST.get() ? mob.getRandom().nextInt(4, 10) : mob.getRandom().nextInt(18, 25), type == ModEntities.GHOST.get() ? 0.4 : 0.6);
        mob.remove(Entity.RemovalReason.DISCARDED);
        return true;
    }

    public static void spawnParticlesAroundEntity(ParticleOptions particle, LivingEntity livingEntity, int amount, double speedMultiplier, double yOffset) {
        Level level = livingEntity.level();
        RandomSource random = livingEntity.getRandom();
        for (int i = 0; i < amount; i++) {
            double dx = random.nextGaussian() * speedMultiplier;
            double dy = random.nextGaussian() * speedMultiplier;
            double dz = random.nextGaussian() * speedMultiplier;
            level.addParticle(particle, livingEntity.getRandomX(1.0D), livingEntity.getRandomY() + yOffset, livingEntity.getRandomZ(1.0D), dx, dy, dz);
        }
    }

    public static void spawnParticlesAroundEntity(ParticleOptions particle, LivingEntity livingEntity, int amount) {
        spawnParticlesAroundEntity(particle, livingEntity, amount, 0.02D, 0.5D);
    }

    public static void spawnDispelParticlesAroundEntity(ParticleOptions particle, LivingEntity livingEntity, int amount, double radius) {
        Level level = livingEntity.level();
        double centerX = livingEntity.getX();
        double centerY = livingEntity.getY() + livingEntity.getBoundingBox().getYsize() / 2 + 0.2;
        double centerZ = livingEntity.getZ();
        double angleStep = 2 * Math.PI / amount;

        for (int i = 0; i < amount; i++) {
            double angle = angleStep * i;
            double x = centerX + radius * Math.cos(angle);
            double z = centerZ + radius * Math.sin(angle);
            if (level instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(particle, x, centerY, z, 1, 0, 0, 0, 0);
            } else {
                level.addParticle(particle, x, centerY, z, 0, 0, 0);
            }
        }
    }

    // LivingEntity.spawnItemParticlesAroundEntity but with configurable particle type
    public static void spawnParticlesOnItemEntityHolding(ParticleOptions particleOptions, LivingEntity livingEntity) {
        Level level = livingEntity.level();
        RandomSource random = livingEntity.getRandom();
        float xRot = -livingEntity.getXRot() * DEG_TO_RAD;
        float yRot = -livingEntity.getYRot() * DEG_TO_RAD;

        Vec3 velocity = new Vec3((random.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D).xRot(xRot).yRot(yRot);
        double offsetY = -random.nextFloat() * 0.6D - 0.3D;
        Vec3 position = new Vec3((random.nextFloat() - 0.5D) * 0.3D, offsetY, 0.6D).xRot(xRot).yRot(yRot).add(livingEntity.getX(), livingEntity.getEyeY(), livingEntity.getZ());

        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(particleOptions, position.x, position.y, position.z, 1, velocity.x, velocity.y + 0.05D, velocity.z, 0.0D);
        } else {
            level.addParticle(particleOptions, position.x, position.y, position.z, velocity.x, velocity.y + 0.05D, velocity.z);
        }
    }
}