package net.grid.vampiresdelight.common.utility;

import com.mojang.datafixers.util.Pair;
import de.teamlapen.vampirism.api.VReference;
import de.teamlapen.vampirism.api.VampirismAPI;
import de.teamlapen.vampirism.util.Helper;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.common.PlantType;

import java.util.Objects;

public class VDHelper {
    // Plant type for plants that grow on cursed soil
    public static final PlantType CURSED_PLANT_TYPE = PlantType.get("vampiresdelight_cursed");

    // Applies food effect to living entity from food properties
    public static void addFoodEffects(FoodProperties foodProperties, Level level, LivingEntity livingEntity) {
        for (Pair<MobEffectInstance, Float> pair : foodProperties.getEffects()) {
            if (!level.isClientSide && pair.getFirst() != null && level.random.nextFloat() < pair.getSecond()) {
                livingEntity.addEffect(new MobEffectInstance(pair.getFirst()));
            }
        }
    }

    // LivingEntity.eat, but with no stack.shrink
    public static void feedEntity(Level level, ItemStack stack, LivingEntity livingEntity) {
        if (stack.isEdible()) {
            level.playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), livingEntity.getEatingSound(stack), SoundSource.NEUTRAL, 1.0F, 1.0F + (level.random.nextFloat() - level.random.nextFloat()) * 0.4F);
            if (!Helper.isVampire(livingEntity)) addFoodEffects(Objects.requireNonNull(stack.getFoodProperties(livingEntity)), level, livingEntity);
            livingEntity.gameEvent(GameEvent.EAT);
        }
    }
    public static void feedEntity(Level level, ItemStack stack, FoodProperties foodProperties, LivingEntity livingEntity) {
        if (stack.isEdible()) {
            level.playSound(null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), livingEntity.getEatingSound(stack), SoundSource.NEUTRAL, 1.0F, 1.0F + (level.random.nextFloat() - level.random.nextFloat()) * 0.4F);
            if (!Helper.isVampire(livingEntity)) addFoodEffects(foodProperties, level, livingEntity);
            livingEntity.gameEvent(GameEvent.EAT);
        }
    }

    public static boolean isHuman(Entity entity) {
        return !Helper.isVampire(entity) && !Helper.isHunter(entity);
    }

    public static boolean isHuman(Player player) {
        return !Helper.isVampire(player) && !Helper.isHunter(player);
    }
}
