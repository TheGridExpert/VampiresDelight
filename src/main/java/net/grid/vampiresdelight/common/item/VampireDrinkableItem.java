package net.grid.vampiresdelight.common.item;

import de.teamlapen.vampirism.VampirismMod;
import de.teamlapen.vampirism.api.VReference;
import de.teamlapen.vampirism.api.entity.factions.IFaction;
import de.teamlapen.vampirism.api.items.IFactionExclusiveItem;
import de.teamlapen.vampirism.util.Helper;
import net.grid.vampiresdelight.common.util.VDTooltipUtils;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.item.DrinkableItem;

import java.util.List;

public class VampireDrinkableItem extends DrinkableItem implements IFactionExclusiveItem {
    private final boolean hasSpecialHunterImpact;

    public VampireDrinkableItem(Item.Properties properties) {
        super(properties);
        this.hasSpecialHunterImpact = false;
    }

    public VampireDrinkableItem(Item.Properties properties, boolean hasSpecialHumanImpact) {
        super(properties, false, false);
        this.hasSpecialHunterImpact = hasSpecialHumanImpact;
    }

    @Nullable
    @Override
    public IFaction<?> getExclusiveFaction(@NotNull ItemStack stack) {
        return VReference.VAMPIRE_FACTION;
    }

    /**
     * Do not override it, only in specific cases
     */
    @Override
    public void affectConsumer(ItemStack stack, Level level, LivingEntity consumer) {
        if (Helper.isVampire(consumer)) {
            affectVampire(stack, level, consumer);
        } else {
            if (hasSpecialHunterImpact && Helper.isHunter(consumer)) {
                affectHunter(stack, level, consumer);
            } else {
                affectHuman(stack, level, consumer);
            }
        }
    }

    /**
     * Override those three to apply changes or effects to the consumer.
     * affectHunter works if hasSpecialHunterImpact is true, use it if you need hunters to be affected differently than humans.
     */
    public void affectVampire(ItemStack stack, Level level, LivingEntity consumer) {
    }

    public void affectHuman(ItemStack stack, Level level, LivingEntity consumer) {
    }

    public void affectHunter(ItemStack stack, Level level, LivingEntity consumer) {
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag isAdvanced) {
        VDTooltipUtils.addFactionFoodToolTips(tooltip, VampirismMod.proxy.getClientPlayer(), VReference.VAMPIRE_FACTION);
    }
}
