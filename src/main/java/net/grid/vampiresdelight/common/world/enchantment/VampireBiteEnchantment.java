package net.grid.vampiresdelight.common.world.enchantment;

import net.grid.vampiresdelight.common.config.VDCommonConfig;
import net.grid.vampiresdelight.common.core.VDEnchantments;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

public class VampireBiteEnchantment extends Enchantment {

    public VampireBiteEnchantment(Rarity rarity) {
        super(rarity, VDEnchantments.VAMPIRE_SWORD, new EquipmentSlot[] {EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getMinCost(int level) {
        return level * 30;
    }

    @Override
    public int getMaxCost(int level) {
        return getMinCost(level) + 50;
    }

    @Override
    public boolean isTreasureOnly() {
        return true;
    }

    @Override
    public boolean isTradeable() {
        return false;
    }

    @Override
    public boolean isDiscoverable() {
        return false;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return !VDCommonConfig.ENABLE_VAMPIRE_BITE.get() && super.canEnchant(stack);
    }
}
