package net.grid.vampiresdelight.common.world.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

/**
 * Credits to the Create mod
 * <a href="https://github.com/Creators-of-Create/Create">...</a>
 */
public interface ICustomUseItem {

    boolean hasCustomUseEffects();

    boolean triggerUseEffects(ItemStack stack, LivingEntity entity, int amount);
}