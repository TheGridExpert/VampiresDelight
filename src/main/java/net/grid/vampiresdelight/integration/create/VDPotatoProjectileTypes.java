package net.grid.vampiresdelight.integration.create;
/*
import com.simibubi.create.Create;
import com.simibubi.create.content.equipment.potatoCannon.PotatoCannonProjectileType;
import de.teamlapen.vampirism.api.EnumStrength;
import de.teamlapen.vampirism.api.entity.vampire.IVampire;
import de.teamlapen.vampirism.core.ModItems;
import de.teamlapen.vampirism.entity.player.vampire.VampirePlayer;
import de.teamlapen.vampirism.util.DamageHandler;
import de.teamlapen.vampirism.util.Helper;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.EntityHitResult;

import java.util.function.Predicate;

public class VDPotatoProjectileTypes {
    public static final PotatoCannonProjectileType
            GARLIC = create("garlic").damage(4)
                .reloadTicks(15)
                .velocity(1.4f)
                .renderTumbling()
                .soundPitch(1.2f)
                .onEntityHit(garlicEffect(true))
                .registerAndAssign(ModItems.ITEM_GARLIC.get()),
            GRILLED_GARLIC = create("grilled_garlic").damage(3)
                    .reloadTicks(15)
                    .velocity(1.4f)
                    .renderTumbling()
                    .soundPitch(1.0f)
                    .sticky()
                    .onEntityHit(garlicEffect(true))
                    .registerAndAssign(VDItems.GRILLED_GARLIC.get());

    private static PotatoCannonProjectileType.Builder create(String name) {
        return new PotatoCannonProjectileType.Builder(Create.asResource(name));
    }

    private static Predicate<EntityHitResult> garlicEffect(boolean recoverable) {
        return ray -> {
            Entity entity = ray.getEntity();
            if (entity.level().isClientSide)
                return true;

            if (entity instanceof LivingEntity || Helper.isVampire(entity) || !entity.getCommandSenderWorld().isClientSide) {
                if (entity instanceof IVampire) {
                    DamageHandler.affectVampireGarlicDirect((IVampire) entity, EnumStrength.MEDIUM);
                } else if (entity instanceof Player) {
                    VampirePlayer.getOpt((Player) entity).ifPresent(vampire -> DamageHandler.affectVampireGarlicDirect(vampire, EnumStrength.MEDIUM));
                }
            }
            return !recoverable;
        };
    }

        public static void register() {
    }
}
*/