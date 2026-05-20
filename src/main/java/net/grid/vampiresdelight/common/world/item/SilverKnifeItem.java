package net.grid.vampiresdelight.common.world.item;

import de.teamlapen.werewolves.api.items.ISilverItem;
import net.minecraft.world.item.Tier;
import vectorwing.farmersdelight.common.item.KnifeItem;

public class SilverKnifeItem extends KnifeItem implements ISilverItem {

    public SilverKnifeItem(Tier tier, float attackDamage, float attackSpeed, Properties properties) {
        super(tier, attackDamage, attackSpeed, properties);
    }
}
