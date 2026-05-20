package net.grid.vampiresdelight.common.world.item;

import de.teamlapen.werewolves.api.items.ISilverItem;
import de.teamlapen.werewolves.util.WUtils;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import vectorwing.farmersdelight.common.item.KnifeItem;

public class SilverKnifeItem extends KnifeItem implements ISilverItem {

    public SilverKnifeItem(Tier tier, float attackDamage, float attackSpeed, Properties properties) {
        super(tier, attackDamage, attackSpeed, properties);
    }

    // Registration was moved here in order not to mess with the game when Werewolves is missing.
    public static Item create(Properties properties) {
        return new SilverKnifeItem(WUtils.SILVER_ITEM_TIER, 0.5F, -2.0F, properties);
    }
}