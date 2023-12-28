package net.grid.vampiresdelight.common.item;

import de.teamlapen.vampirism.VampirismMod;
import de.teamlapen.vampirism.api.VReference;
import de.teamlapen.vampirism.config.VampirismConfig;
import de.teamlapen.vampirism.effects.SanguinareEffect;
import de.teamlapen.vampirism.util.Helper;
import net.grid.vampiresdelight.common.utility.VDHelper;
import net.grid.vampiresdelight.common.utility.VDTextUtils;
import net.grid.vampiresdelight.common.utility.VDTooltipUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.Configuration;

import java.util.List;

public class OrchidTeaItem extends VampireDrinkableItem {
    private final FoodProperties defaultFood;

    public OrchidTeaItem(FoodProperties vampireFood, FoodProperties hunterFood) {
        super(vampireFood, hunterFood, new FoodProperties.Builder().build());
        this.defaultFood = hunterFood;
    }

    @Override
    public void affectConsumer(ItemStack stack, Level level, LivingEntity consumer) {
        if (VDHelper.isHuman(consumer)) {
            if (Helper.canBecomeVampire((Player) consumer)) {
                if (!VampirismConfig.SERVER.disableFangInfection.get()) {
                    SanguinareEffect.addRandom(consumer, true);
                    consumer.addEffect(new MobEffectInstance(MobEffects.POISON, 60));
                }
            } else {
                // This line is used for werewolves, or other addon fractions
                VDHelper.addFoodEffects(defaultFood, level, consumer);
            }
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag isAdvanced) {
        Player player = VampirismMod.proxy.getClientPlayer();
        assert player != null;

        if (Configuration.FOOD_EFFECT_TOOLTIP.get()) {
            if (Helper.canBecomeVampire(player)) {
                MutableComponent textEmpty = VDTextUtils.getTranslation("tooltip." + this);
                tooltip.add(textEmpty.withStyle(ChatFormatting.BLUE));
            } else {
                FoodProperties foodProperties;
                if (!VDHelper.isHuman(player))
                    foodProperties = Helper.isVampire(player) ? this.getVampireFood() : this.getHunterFood();
                else {
                    foodProperties = defaultFood;
                }

                if (!foodProperties.getEffects().isEmpty()) {
                    VDTextUtils.addFoodEffectTooltip(foodProperties, tooltip, 1.0F);
                }
            }
        }

        VDTooltipUtils.addFactionFoodToolTips(tooltip, player, VReference.VAMPIRE_FACTION);
    }
}
