package net.grid.vampiresdelight.common.event;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.config.VDCommonConfig;
import net.grid.vampiresdelight.common.core.VDEnchantments;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = VampiresDelight.MODID)
public class EnchantmentEventHandler {

    @SubscribeEvent
    public static void onVampireBite(LivingHurtEvent event) {
        Entity entity = event.getSource().getEntity();
        if (entity != null && entity.level().isClientSide) return;

        if (entity instanceof Player player && VDCommonConfig.ENABLE_VAMPIRE_BITE.get()) {
            int enchantmentLevel = player.getMainHandItem().getEnchantmentLevel(VDEnchantments.VAMPIRE_BITE.get());
            if (enchantmentLevel > 0) {
                healFromDamage(player, enchantmentLevel, event.getAmount());
            }
        }
    }

    public static void healFromDamage(LivingEntity user, int level, float damage) {
        RandomSource randomSource = user.getRandom();
        int chance = switch (level) {
            case 2 -> VDCommonConfig.VAMPIRE_BITE_HEALING_CHANCE_2.get();
            case 3 -> VDCommonConfig.VAMPIRE_BITE_HEALING_CHANCE_3.get();
            default -> VDCommonConfig.VAMPIRE_BITE_HEALING_CHANCE_1.get();
        };
        int maxHealingValue = (int) (VDCommonConfig.VAMPIRE_BITE_MAX_HEALING_VALUE.get() * 2);

        if (user instanceof Player player && randomSource.nextInt(100) <= chance) {
            float healAmount = (float) Math.ceil((double) level / 30 * damage);

            if (!user.getCommandSenderWorld().isClientSide) {
                player.heal(Math.min(healAmount, maxHealingValue));
            }
        }
    }
}
