package net.grid.vampiresdelight.common.event;

import de.teamlapen.vampirism.api.EnumStrength;
import de.teamlapen.vampirism.core.ModItems;
import de.teamlapen.vampirism.items.VampirismItemBloodFoodItem;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.VDConfiguration;
import net.grid.vampiresdelight.common.item.FactionalConsumableItem;
import net.grid.vampiresdelight.common.item.VampireConsumableItem;
import net.grid.vampiresdelight.common.item.WerewolfConsumableItem;
import net.grid.vampiresdelight.common.registry.VDAdvancementTriggers;
import net.grid.vampiresdelight.common.registry.VDDataComponents;
import net.grid.vampiresdelight.common.registry.VDStats;
import net.grid.vampiresdelight.common.tag.VDTags;
import net.grid.vampiresdelight.common.utility.VDEntityUtils;
import net.grid.vampiresdelight.common.utility.VDHelper;
import net.grid.vampiresdelight.common.utility.VDIntegrationUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.item.ConsumableItem;

@SuppressWarnings("unused")
@EventBusSubscriber(modid = VampiresDelight.MODID)
public class PlayerEventHandler {
    @SubscribeEvent
    public static void checkDisgustingFood(LivingEntityUseItemEvent.@NotNull Finish event) {
        ItemStack itemStack = event.getItem();
        Item item = itemStack.getItem();
        LivingEntity livingEntity = event.getEntity();

        if (!livingEntity.getCommandSenderWorld().isClientSide) {
            if (VDHelper.isVampire(livingEntity)) {
                if (item instanceof FactionalConsumableItem factionConsumableItem) {
                    if (factionConsumableItem.hasGarlic()) {
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
                if (item instanceof WerewolfConsumableItem && itemStack.is(VDTags.WEREWOLF_ONLY_FOOD) || VDHelper.isSame(item, VDIntegrationUtils.WOLF_BERRIES)) {
                    disgustingFoodConsumed(livingEntity);
                }
            }
        }
    }

    private static void disgustingFoodConsumed(LivingEntity livingEntity) {
        if (livingEntity instanceof Player player) {
            player.awardStat(VDStats.DISGUSTING_FOOD_CONSUMED.get());
            VDAdvancementTriggers.DISGUSTING_FOOD_CONSUMED.get().trigger((ServerPlayer) player);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onFoodEaten(final MobEffectEvent.Applicable event) {
        LivingEntity consumer = event.getEntity();
        ItemStack itemInHand = consumer.getItemInHand(consumer.getUsedItemHand());
        Item item = itemInHand.getItem();
        if (VDHelper.isVampire(consumer) && item instanceof ConsumableItem && !itemInHand.has(VDDataComponents.VAMPIRE_FOOD) && !VDConfiguration.ENABLE_FD_FOOD_EFFECTS_DESPITE_FACTION.get()) {
            event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
        }
    }
}
