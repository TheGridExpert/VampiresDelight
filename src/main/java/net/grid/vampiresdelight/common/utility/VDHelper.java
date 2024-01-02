package net.grid.vampiresdelight.common.utility;

import com.mojang.datafixers.util.Pair;
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

public class VDHelper {
    // Plant type for plants that grow on cursed soil
    public static final PlantType CURSED_PLANT_TYPE = PlantType.get("vampiresdelight_cursed");

    // Applies food effect to living entity from food properties
    public static void addFoodEffects(FoodProperties foodProperties, Level level, LivingEntity entity) {
        for (Pair<MobEffectInstance, Float> pair : foodProperties.getEffects()) {
            if (!level.isClientSide && pair.getFirst() != null && level.random.nextFloat() < pair.getSecond()) {
                entity.addEffect(new MobEffectInstance(pair.getFirst()));
            }
        }
    }

    public static ItemStack eatFood(Level level, LivingEntity entity, ItemStack foodItem, FoodProperties foodProperties) {
        if (foodItem.isEdible()) {
            level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), entity.getEatingSound(foodItem), SoundSource.NEUTRAL, 1.0F, 1.0F + (level.random.nextFloat() - level.random.nextFloat()) * 0.4F);
            addFoodEffects(foodProperties, level, entity);
            if (!(entity instanceof Player) || !((Player) entity).getAbilities().instabuild) {
                foodItem.shrink(1);
            }

            entity.gameEvent(GameEvent.EAT);
        }

        return foodItem;
    }

    public static boolean isHuman(Entity entity) {
        return !Helper.isVampire(entity) && !Helper.isHunter(entity);
    }

    public static boolean isHuman(Player player) {
        return !Helper.isVampire(player) && !Helper.isHunter(player);
    }
}
