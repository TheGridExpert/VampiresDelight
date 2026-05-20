package net.grid.vampiresdelight.common.event;

import de.teamlapen.vampirism.api.event.PlayerFactionEvent;
import de.teamlapen.vampirism.util.Helper;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.core.VDEffects;
import net.grid.vampiresdelight.common.util.VDEntityUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = VampiresDelight.MODID)
public class EntityEventHandler {

    @SubscribeEvent
    public static void onMobEffectApplied(MobEffectEvent.Applicable event) {
        LivingEntity entity = event.getEntity();
        Level level = entity.level();

        if (level.isClientSide || event.getEffectInstance().getEffect() != VDEffects.CONSECRATION.get()) return;
        if (Helper.isVampire(entity)) {
            event.setResult(Event.Result.DENY);
            return;
        }

        double radius = 24.0;
        level.getEntitiesOfClass(Mob.class, entity.getBoundingBox().inflate(radius), mob -> mob.getTarget() == entity).forEach(VDEntityUtils::tryBanishUnholySpirit);
    }

    @SubscribeEvent
    public static void onFactionLevelChanged(PlayerFactionEvent.FactionLevelChanged event) {
        Player player = event.getPlayer().getPlayer();

        if (Helper.isVampire(player) && player.hasEffect(VDEffects.CONSECRATION.get())) {
            player.removeEffect(VDEffects.CONSECRATION.get());
        }
    }
}