package net.grid.vampiresdelight.common.event;

import de.teamlapen.vampirism.core.ModItems;
import net.grid.vampiresdelight.VampiresDelight;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static de.teamlapen.vampirism.items.BloodBottleFluidHandler.MULTIPLIER;

@Mod.EventBusSubscriber(modid = VampiresDelight.MODID)
public class CraftingEventHandler {
    @SubscribeEvent
    public static void onCraftingWithBlood(PlayerEvent.ItemCraftedEvent event) {
        if (!event.getCrafting().isEmpty() && !event.getInventory().isEmpty() && !event.getEntity().getCommandSenderWorld().isClientSide) {
            Container container = event.getInventory();

            for (int i = 0; i < container.getContainerSize(); i++) {
                ItemStack stack = container.getItem(i);

                if (!stack.isEmpty()) {
                    if (stack.getItem() == ModItems.BLOOD_BOTTLE.get()) {
                        if (stack.getDamageValue() * MULTIPLIER < 900) {
                            //event.setCanceled(true);
                            //event.setResult(Event.Result.DENY);
                            //event.setResult(null);
                        }
                    }
                }
            }
        }
    }
}
