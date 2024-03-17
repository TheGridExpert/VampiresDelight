package net.grid.vampiresdelight.common.item.enchantment;

import de.teamlapen.vampirism.items.VampirismVampireSwordItem;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.registry.VDEnchantments;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

public class VampireBiteEnchantment extends Enchantment {
    public VampireBiteEnchantment(Rarity rarity) {
        super(rarity, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    public static void healFromDamage(LivingEntity user, int level, float damage) {
        RandomSource randomSource = user.getRandom();
        if (randomSource.nextInt(5) < 3 && user instanceof Player player) {
            player.heal((float) level / 20 * damage);
        }
    }

    @Mod.EventBusSubscriber(modid = VampiresDelight.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class VampireBiteEvent {
        @SubscribeEvent
        public static void onVampireBite(LivingHurtEvent event) {
            if (event.getSource().getEntity() instanceof Player player) {
                ItemStack weapon = player.getMainHandItem();
                int enchantmentLevel = EnchantmentHelper.getItemEnchantmentLevel(VDEnchantments.VAMPIRE_BITE.get(), weapon);
                Level level = event.getEntity().getCommandSenderWorld();
                if (!level.isClientSide) {
                    healFromDamage(player, enchantmentLevel, event.getAmount());
                }
            }
        }
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getMinCost(int enchantmentLevel) {
        return 15 + (enchantmentLevel - 1) * 12;
    }

    @Override
    public int getMaxCost(int enchantmentLevel) {
        return super.getMinCost(enchantmentLevel) + 50;
    }

    @Override
    public boolean canApplyAtEnchantingTable(@NotNull ItemStack stack) {
        return stack.getItem() instanceof VampirismVampireSwordItem;
    }

    @Override
    public boolean isTreasureOnly() {
        return true;
    }
}
