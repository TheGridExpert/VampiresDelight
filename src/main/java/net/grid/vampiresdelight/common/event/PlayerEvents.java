package net.grid.vampiresdelight.common.event;

import de.teamlapen.vampirism.api.EnumStrength;
import de.teamlapen.vampirism.api.entity.vampire.IVampire;
import de.teamlapen.vampirism.entity.player.vampire.VampirePlayer;
import de.teamlapen.vampirism.items.VampirismItemBloodFoodItem;
import de.teamlapen.vampirism.util.DamageHandler;
import de.teamlapen.vampirism.util.Helper;
import net.grid.vampiresdelight.common.item.HunterConsumableItem;
import net.grid.vampiresdelight.common.registry.VDEffects;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraft.world.entity.player.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.item.ConsumableItem;
import vectorwing.farmersdelight.common.registry.ModEffects;

public class PlayerEvents {
    private final static Logger LOGGER = LogManager.getLogger(PlayerEvents.class);

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onItemUse(LivingEntityUseItemEvent.@NotNull Finish event) {
        if (Helper.isVampire(event.getEntity())) {
            if (!event.getEntity().getCommandSenderWorld().isClientSide) {
                if (event.getItem().getItem() instanceof HunterConsumableItem) {
                    if (event.getEntity() instanceof IVampire) {
                        DamageHandler.affectVampireGarlicDirect((IVampire) event.getEntity(), EnumStrength.MEDIUM);
                    } else if (event.getEntity() instanceof Player) {
                        VampirePlayer.getOpt((Player) event.getEntity()).ifPresent(vampire -> DamageHandler.affectVampireGarlicDirect(vampire, EnumStrength.MEDIUM));
                    }
                }
                if (event.getItem().getItem() instanceof VampirismItemBloodFoodItem) {
                    if (!(event.getEntity() instanceof IVampire)) {
                        RandomSource randomSource = event.getEntity().getRandom();
                        if (randomSource.nextInt(2) < 1)
                            event.getEntity().addEffect(new MobEffectInstance(MobEffects.HUNGER, 400, 0));
                    }
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onFoodEaten(final MobEffectEvent.Applicable event) {
        LivingEntity consumer = event.getEntity();
        InteractionHand hand = consumer.getUsedItemHand();
        ItemStack itemInHand = consumer.getItemInHand(hand);
        if (Helper.isVampire(consumer) && itemInHand.getItem() instanceof ConsumableItem) {
            if (event.getEffectInstance().getEffect() == ModEffects.COMFORT.get() ||
                    event.getEffectInstance().getEffect() == ModEffects.NOURISHMENT.get() ||
                    event.getEffectInstance().getEffect() == VDEffects.FOG_VISION.get()) {
                event.setResult(Event.Result.DENY);
            }
        }
    }
}
