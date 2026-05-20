package net.grid.vampiresdelight.common.world.item;

import net.grid.vampiresdelight.common.util.VDIntegrationUtils;
import net.grid.vampiresdelight.common.util.VDTooltipUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.List;

public class WerewolfConsumableItem extends FactionConsumableItem {

    public WerewolfConsumableItem(Properties properties) {
        super(properties);
        this.factionTooltip();
    }

    @Override
    public boolean hasFoodEffectTooltip(ItemStack stack, @Nullable Player player) {
        return player != null && VDIntegrationUtils.isWerewolf(player) && super.hasFoodEffectTooltip(stack, player);
    }

    @Override
    public void addFactionFoodTooltip(List<Component> tooltip, Player player) {
        VDTooltipUtils.addWerewolfFactionFoodTooltip(tooltip, player);
    }
}