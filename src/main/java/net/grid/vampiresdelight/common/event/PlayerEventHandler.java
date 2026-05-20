package net.grid.vampiresdelight.common.event;

import de.teamlapen.vampirism.core.ModItems;
import de.teamlapen.vampirism.items.VampirismItemBloodFoodItem;
import de.teamlapen.vampirism.util.Helper;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.core.VDAdvancementTriggers;
import net.grid.vampiresdelight.common.util.VDIntegrationUtils;
import net.grid.vampiresdelight.common.world.item.FactionConsumableItem;
import net.grid.vampiresdelight.common.world.item.VampireConsumableItem;
import net.grid.vampiresdelight.common.world.item.WerewolfConsumableItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(modid = VampiresDelight.MODID)
public class PlayerEventHandler {

    @SubscribeEvent
    public static void checkDisgustingFood(LivingEntityUseItemEvent.@NotNull Finish event) {
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity.getCommandSenderWorld().isClientSide) return;

        ItemStack itemStack = event.getItem();
        Item item = itemStack.getItem();

        if (Helper.isVampire(livingEntity)) {
            if (item instanceof FactionConsumableItem factionConsumableItem && factionConsumableItem.hasGarlic()) {
                disgustingFoodConsumed(livingEntity);
            }

            if (item == ModItems.GARLIC_BREAD.get()) {
                disgustingFoodConsumed(livingEntity);
            }
        } else if (item instanceof VampireConsumableItem || item instanceof VampirismItemBloodFoodItem) {
            disgustingFoodConsumed(livingEntity);
        }

        if (!VDIntegrationUtils.isWerewolf(livingEntity) && item instanceof WerewolfConsumableItem) {
            disgustingFoodConsumed(livingEntity);
        }
    }

    private static void disgustingFoodConsumed(LivingEntity livingEntity) {
        if (livingEntity instanceof ServerPlayer serverPlayer) {
            VDAdvancementTriggers.DISGUSTING_FOOD_CONSUMED.trigger(serverPlayer);
        }
    }
}
