package net.grid.vampiresdelight.common.world.item;

import de.teamlapen.vampirism.api.VReference;
import de.teamlapen.vampirism.util.Helper;
import net.grid.vampiresdelight.common.util.VDTooltipUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.List;

public class VampireConsumableItem extends FactionConsumableItem {

    public VampireConsumableItem(Properties properties) {
        super(properties);
        this.factionTooltip();
    }

    @Override
    public boolean hasFoodEffectTooltip(ItemStack stack, @Nullable Player player) {
        return player != null && Helper.isVampire(player) && super.hasFoodEffectTooltip(stack, player);
    }

    @Override
    public void addFactionFoodTooltip(List<Component> tooltip, Player player) {
        VDTooltipUtils.addFactionFoodTooltip(tooltip, player, VReference.VAMPIRE_FACTION);
    }
}