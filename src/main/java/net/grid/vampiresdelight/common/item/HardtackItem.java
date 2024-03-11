package net.grid.vampiresdelight.common.item;

import de.teamlapen.vampirism.core.ModEffects;
import de.teamlapen.vampirism.util.Helper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.item.ConsumableItem;

public class HardtackItem extends ConsumableItem {
    public HardtackItem(Properties properties) {
        super(properties, false);
    }

    @Override
    public void affectConsumer(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity consumer) {
        if (Helper.isHunter(consumer)) {
            consumer.addEffect(new MobEffectInstance(ModEffects.SATURATION.get(), 200));
        }
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack) {
        return 48;
    }
}
