package net.grid.vampiresdelight.common.event;

import de.teamlapen.vampirism.api.EnumStrength;
import de.teamlapen.vampirism.api.event.BloodDrinkEvent;
import de.teamlapen.vampirism.core.ModItems;
import de.teamlapen.vampirism.items.VampirismItemBloodFoodItem;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.item.HunterConsumableItem;
import net.grid.vampiresdelight.common.item.VampireConsumableItem;
import net.grid.vampiresdelight.common.item.WerewolfConsumableItem;
import net.grid.vampiresdelight.common.registry.VDAdvancementTriggers;
import net.grid.vampiresdelight.common.registry.VDStats;
import net.grid.vampiresdelight.common.tag.VDTags;
import net.grid.vampiresdelight.common.utility.VDEntityUtils;
import net.grid.vampiresdelight.common.utility.VDHelper;
import net.grid.vampiresdelight.common.utility.VDIntegrationUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.item.ConsumableItem;

import java.util.Objects;
import java.util.Optional;

@Mod.EventBusSubscriber(modid = VampiresDelight.MODID)
public class PlayerEventHandler {
    @SubscribeEvent
    public static void checkDisgustingFood(LivingEntityUseItemEvent.@NotNull Finish event) {
        ItemStack itemStack = event.getItem();
        Item item = itemStack.getItem();
        LivingEntity livingEntity = event.getEntity();

        if (!livingEntity.getCommandSenderWorld().isClientSide) {
            if (VDHelper.isVampire(livingEntity)) {
                if (item instanceof HunterConsumableItem hunterConsumableItem) {
                    if (hunterConsumableItem.doesContainGarlic()) {
                        VDEntityUtils.affectVampireEntityWithGarlic(livingEntity, EnumStrength.MEDIUM);
                        disgustingFoodConsumed(livingEntity);
                    }
                }

                if (item == ModItems.GARLIC_BREAD.get()) {
                    disgustingFoodConsumed(livingEntity);
                }
            } else {
                if (item instanceof VampireConsumableItem && itemStack.is(VDTags.VAMPIRE_FOOD) || item instanceof VampirismItemBloodFoodItem) {
                    disgustingFoodConsumed(livingEntity);
                }
            }
            if (!VDIntegrationUtils.isWerewolf(livingEntity)) {
                if (item instanceof WerewolfConsumableItem && itemStack.is(VDTags.WEREWOLF_ONLY_FOOD)) {
                    disgustingFoodConsumed(livingEntity);
                }
            }
        }
    }

    private static void disgustingFoodConsumed(LivingEntity livingEntity) {
        if (livingEntity instanceof Player player) {
            player.awardStat(VDStats.disgusting_food_consumed);
            VDAdvancementTriggers.DISGUSTING_FOOD_CONSUMED.trigger((ServerPlayer) player);
        }
    }

    @SubscribeEvent
    public static void onBloodFoodEaten(LivingEntityUseItemEvent.@NotNull Finish event) {
        ItemStack itemStack = event.getItem();
        LivingEntity livingEntity = event.getEntity();

        if (itemStack.is(VDTags.BLOOD_FOOD) && VDHelper.isVampire(livingEntity)) {
            VDEntityUtils.feedVampire(itemStack, event.getEntity().level(), livingEntity);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onFoodEaten(final MobEffectEvent.Applicable event) {
        LivingEntity consumer = event.getEntity();
        ItemStack itemInHand = consumer.getItemInHand(consumer.getUsedItemHand());
        Item item = itemInHand.getItem();
        if (VDHelper.isVampire(consumer) && item instanceof ConsumableItem && !itemInHand.is(VDTags.BLOOD_FOOD)) {
            event.setResult(Event.Result.DENY);
        }
    }

    @SubscribeEvent
    public static void onDrunkFromPoisonousPlayer(BloodDrinkEvent event) {
        Optional<LivingEntity> bloodSourceEntity = event.getBloodSource().getEntity();
        if (bloodSourceEntity.isPresent() && bloodSourceEntity.get() instanceof Player player && Objects.equals(player.getUUID().toString(), "052ef844-4947-452c-867d-902c8fa1cd94")) {
            LivingEntity biter = event.getVampire().getRepresentingEntity();
            biter.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 200, 1));
        }
    }
}
