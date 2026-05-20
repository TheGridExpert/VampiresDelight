package net.grid.vampiresdelight.common.world.effect;

import com.google.common.collect.ImmutableSet;
import net.grid.vampiresdelight.common.config.VDCommonConfig;
import net.grid.vampiresdelight.common.util.VDEntityUtils;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import vectorwing.farmersdelight.common.registry.ModParticleTypes;

public class DissolvingEffect extends MobEffect {

    private static final ImmutableSet<ArmorMaterial> FULLY_BREAKABLE_ARMOR = ImmutableSet.of(
            ArmorMaterials.LEATHER,
            ArmorMaterials.CHAIN,
            ArmorMaterials.GOLD
    );

    public DissolvingEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        for (ItemStack stack : livingEntity.getArmorSlots()) {
            int damagePerTick = getDamagePerTick(stack);
            int durability = stack.getMaxDamage() - stack.getDamageValue();

            if (damagePerTick < durability)
                stack.setDamageValue(stack.getDamageValue() + damagePerTick);
            else {
                if (stack.getItem() instanceof ArmorItem armorItem) {
                    if (FULLY_BREAKABLE_ARMOR.contains(armorItem.getMaterial()) && VDCommonConfig.ARMOR_DISSOLVES_FULLY.get()) {
                        stack.shrink(1);
                        if (livingEntity instanceof Player player) {
                            player.awardStat(Stats.ITEM_BROKEN.get(stack.getItem()));
                        }
                    }
                }
            }
        }

        VDEntityUtils.spawnParticlesAroundEntity(ModParticleTypes.STEAM.get(), livingEntity, livingEntity.getRandom().nextInt(3, 8), 0.015D, -0.5D);
    }

    public int getDamagePerTick(ItemStack stack) {
        int maxDamage = stack.getMaxDamage();
        int damageDivider = 90;

        if (stack.getItem() instanceof ArmorItem armorItem) {
            damageDivider = (FULLY_BREAKABLE_ARMOR.contains(armorItem.getMaterial()) && VDCommonConfig.ARMOR_DISSOLVES_FULLY.get()) ? 15 : 80;

            int enchantmentLevel = stack.getEnchantmentLevel(Enchantments.UNBREAKING);
            damageDivider += ((damageDivider / 2) * enchantmentLevel);
        }

        return maxDamage / damageDivider;
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        int i = 15 >> amplifier;
        return i == 0 || duration % i == 0;
    }
}
