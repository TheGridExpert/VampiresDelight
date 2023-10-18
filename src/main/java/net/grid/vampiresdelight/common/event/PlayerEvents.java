package net.grid.vampiresdelight.common.event;

import de.teamlapen.vampirism.api.EnumStrength;
import de.teamlapen.vampirism.api.entity.vampire.IVampire;
import de.teamlapen.vampirism.entity.player.vampire.VampirePlayer;
import de.teamlapen.vampirism.items.VampirismItemBloodFoodItem;
import de.teamlapen.vampirism.util.DamageHandler;
import de.teamlapen.vampirism.util.Helper;
import net.grid.vampiresdelight.common.item.HunterConsumableItem;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraft.world.entity.player.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

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
}
