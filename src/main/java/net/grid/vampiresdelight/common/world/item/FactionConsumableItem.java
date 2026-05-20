package net.grid.vampiresdelight.common.world.item;

import de.teamlapen.vampirism.VampirismMod;
import de.teamlapen.vampirism.api.EnumStrength;
import de.teamlapen.vampirism.core.ModEffects;
import de.teamlapen.vampirism.util.Helper;
import net.grid.vampiresdelight.common.core.VDFoodValues;
import net.grid.vampiresdelight.common.util.VDEntityUtils;
import net.grid.vampiresdelight.common.util.VDIntegrationUtils;
import net.grid.vampiresdelight.common.util.VDTooltipUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import vectorwing.farmersdelight.common.Configuration;
import vectorwing.farmersdelight.common.utility.TextUtils;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings({"unused", "unchecked"})
public class FactionConsumableItem extends Item {

    private @Nullable FoodProperties vampireFood = null;
    private @Nullable FoodProperties hunterFood = null;
    private @Nullable FoodProperties werewolfFood = null;

    private FoodOrientation orientation = FoodOrientation.NONE;
    private @Nullable Consumer<LivingEntity> features = null;
    private boolean hasFoodEffectTooltip = true;
    private boolean hasCustomTooltip = false;
    private boolean hasFactionTooltip = false;
    private boolean hasGarlic = false;

    public FactionConsumableItem(Properties properties) {
        super(properties);
    }

    public <SELF extends FactionConsumableItem> SELF vampireFood(FoodProperties food) {
        this.vampireFood = food;
        return (SELF) this;
    }

    public <SELF extends FactionConsumableItem> SELF hunterFood(FoodProperties food) {
        this.hunterFood = food;
        return (SELF) this;
    }

    public <SELF extends FactionConsumableItem> SELF werewolfFood(FoodProperties food) {
        this.werewolfFood = food;
        return (SELF) this;
    }

    public <SELF extends FactionConsumableItem> SELF orientation(FoodOrientation orientation) {
        this.orientation = orientation;
        return (SELF) this;
    }

    public <SELF extends FactionConsumableItem> SELF feature(Consumer<LivingEntity> feature) {
        this.features = feature;
        return (SELF) this;
    }

    public <SELF extends FactionConsumableItem> SELF hideFoodEffectTooltip() {
        this.hasFoodEffectTooltip = false;
        return (SELF) this;
    }

    public <SELF extends FactionConsumableItem> SELF customTooltip() {
        this.hasCustomTooltip = true;
        return (SELF) this;
    }

    public <SELF extends FactionConsumableItem> SELF factionTooltip() {
        this.hasFactionTooltip = true;
        return (SELF) this;
    }

    public <SELF extends FactionConsumableItem> SELF containsGarlic() {
        this.hasGarlic = true;
        return (SELF) this;
    }

    @Override
    @Nullable
    public FoodProperties getFoodProperties(ItemStack stack, @Nullable LivingEntity entity) {
        LivingEntity holder = entity != null ? entity : VampirismMod.proxy.getClientPlayer();
        if (Helper.isVampire(holder)) {
            return getVampireFood() != null ? getVampireFood() : VDFoodValues.EMPTY;
        }
        if (Helper.isHunter(holder) && getHunterFood() != null) {
            return getHunterFood();
        }
        if (VDIntegrationUtils.isWerewolf(holder) && getWerewolfFood() != null) {
            return getWerewolfFood();
        }
        return super.getFoodProperties(stack, entity);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity consumer) {
        if (!level.isClientSide) {
            if (hasGarlic) {
                consumer.removeEffect(ModEffects.SANGUINARE.get());
                VDEntityUtils.affectVampireEntityWithGarlic(consumer, EnumStrength.MEDIUM);
            }

            if (features != null) {
                features.accept(consumer);
            }

            affectConsumer(stack, level, consumer);
        }

        ItemStack containerStack = stack.getCraftingRemainingItem();

        if (!(Helper.isVampire(consumer) && getVampireFood() == null) && stack.getFoodProperties(consumer) != null) {
            VDEntityUtils.consumeBloodFood(stack, level, consumer);
        } else if (consumer instanceof Player player) {
            if (player instanceof ServerPlayer serverPlayer) {
                CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);
            }
            player.awardStat(Stats.ITEM_USED.get(this));
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
        }

        if (stack.isEmpty()) {
            return containerStack;
        }
        if (consumer instanceof Player player && !player.getAbilities().instabuild) {
            if (!player.getInventory().add(containerStack)) {
                player.drop(containerStack, false);
            }
        }
        return stack;
    }

    public void affectConsumer(ItemStack stack, Level level, LivingEntity consumer) {
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        Player player = VampirismMod.proxy.getClientPlayer();

        if (Configuration.ENABLE_FOOD_EFFECT_TOOLTIP.get() && hasAnyFoodTooltip(stack, player)) {
            if (hasCustomTooltip(stack, player)) {
                String key = stack.getItem().getDescriptionId().replaceFirst("^item\\.", "tooltip.");
                VDTooltipUtils.addFormattedTooltip(key, tooltip, ChatFormatting.BLUE);
            }
            if (hasFoodEffectTooltip(stack, player)) {
                TextUtils.addFoodEffectTooltip(stack, tooltip, 1.0F);
            }
        }

        if (player != null && hasFactionTooltip(stack, player)) {
            addFactionFoodTooltip(tooltip, player);
        }
    }

    public boolean hasCustomTooltip(ItemStack stack, @Nullable Player player) {
        return hasCustomTooltip;
    }

    public boolean hasFoodEffectTooltip(ItemStack stack, @Nullable Player player) {
        if (player != null && Helper.isVampire(player)) {
            return hasFoodEffectTooltip && vampireFood != null;
        }
        return hasFoodEffectTooltip;
    }

    public boolean hasAnyFoodTooltip(ItemStack stack, @Nullable Player player) {
        return hasCustomTooltip(stack, player) || hasFoodEffectTooltip(stack, player);
    }

    public boolean hasFactionTooltip(ItemStack stack, Player player) {
        return hasFactionTooltip;
    }

    public void addFactionFoodTooltip(List<Component> tooltip, Player player) {
    }

    @Nullable
    public FoodProperties getVampireFood() {
        return vampireFood;
    }

    @Nullable
    public FoodProperties getHunterFood() {
        return hunterFood;
    }

    @Nullable
    public FoodProperties getWerewolfFood() {
        return werewolfFood;
    }

    public FoodOrientation getOrientation() {
        return orientation;
    }

    public boolean hasGarlic() {
        return hasGarlic;
    }
}