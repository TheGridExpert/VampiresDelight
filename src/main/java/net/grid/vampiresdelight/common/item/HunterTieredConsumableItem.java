package net.grid.vampiresdelight.common.item;

import net.grid.vampiresdelight.common.interfaces.IFoodWithTier;
import net.minecraft.world.item.Item;

public class HunterTieredConsumableItem implements IFoodWithTier {

    private final TIER tier;

    public HunterTieredConsumableItem(TIER tier) {
        this.tier = tier;
    }

    @Override
    public TIER getVampirismTier() {
        return null;
    }

    @Override
    public Item asItem() {
        return null;
    }
}
