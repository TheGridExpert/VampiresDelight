package net.grid.vampiresdelight.common.world.item;

import de.teamlapen.vampirism.VampirismMod;
import de.teamlapen.vampirism.util.Helper;
import net.grid.vampiresdelight.common.util.VDEntityUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class OrchidTeaItem extends VampireDrinkableItem {

    public OrchidTeaItem(Properties properties) {
        super(properties);
    }

    @Override
    @Nullable
    public FoodProperties getFoodProperties(ItemStack stack, @Nullable LivingEntity entity) {
        LivingEntity holder = entity != null ? entity : VampirismMod.proxy.getClientPlayer();

        if (Helper.isVampire(holder)) {
            return getVampireFood();
        }
        if (holder instanceof Player player && VDEntityUtils.canBeInfectedFromItem(player)) {
            return super.getFoodProperties(stack, entity);
        }
        return getHunterFood() != null ? getHunterFood() : super.getFoodProperties(stack, entity);
    }

    @Override
    public boolean hasCustomTooltip(ItemStack stack, @Nullable Player player) {
        return player != null && VDEntityUtils.canBeInfectedFromItem(player);
    }

    @Override
    public boolean hasFoodEffectTooltip(ItemStack stack, @Nullable Player player) {
        return player == null || !VDEntityUtils.canBeInfectedFromItem(player);
    }

    @Override
    public boolean hasFactionTooltip(ItemStack stack, Player player) {
        return !VDEntityUtils.canBeInfectedFromItem(player);
    }
}