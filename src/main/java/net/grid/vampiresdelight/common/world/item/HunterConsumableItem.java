package net.grid.vampiresdelight.common.world.item;

import de.teamlapen.vampirism.api.VReference;
import de.teamlapen.vampirism.util.Helper;
import net.grid.vampiresdelight.common.util.VDEntityUtils;
import net.grid.vampiresdelight.common.util.VDTooltipUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.List;

public class HunterConsumableItem extends FactionConsumableItem {

    public HunterConsumableItem(Properties properties) {
        super(properties);
        this.containsGarlic();
    }

    @Override
    public boolean hasAnyFoodTooltip(ItemStack stack, @Nullable Player player) {
        return player != null && VDEntityUtils.canConsumeHumanFood(player, stack) && super.hasAnyFoodTooltip(stack, player);
    }

    @Override
    public boolean hasFactionTooltip(ItemStack stack, Player player) {
        return Helper.isVampire(player);
    }

    @Override
    public void addFactionFoodTooltip(List<Component> tooltip, Player player) {
        VDTooltipUtils.addFactionFoodTooltip(tooltip, player, VReference.HUNTER_FACTION);
    }
}