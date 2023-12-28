package net.grid.vampiresdelight.common.item;

import de.teamlapen.vampirism.VampirismMod;
import de.teamlapen.vampirism.api.VReference;
import de.teamlapen.vampirism.api.entity.factions.IFaction;
import de.teamlapen.vampirism.api.entity.vampire.IVampire;
import de.teamlapen.vampirism.api.items.IFactionExclusiveItem;
import de.teamlapen.vampirism.entity.player.vampire.VampirePlayer;
import de.teamlapen.vampirism.util.Helper;
import net.grid.vampiresdelight.common.utility.VDHelper;
import net.grid.vampiresdelight.common.utility.VDTextUtils;
import net.grid.vampiresdelight.common.utility.VDTooltipUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.Configuration;
import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.Objects;

public class VampireConsumableItem extends Item implements IFactionExclusiveItem {
    private final FoodProperties vampireFood;
    private final boolean hasFoodEffectTooltip;
    private final boolean hasHumanFoodEffectTooltip;
    private final boolean hasCustomTooltip;

    public VampireConsumableItem(FoodProperties vampireFood, @NotNull FoodProperties humanFood) {
        super(new Properties().food(humanFood));
        this.vampireFood = vampireFood;
        this.hasFoodEffectTooltip = false;
        this.hasHumanFoodEffectTooltip = false;
        this.hasCustomTooltip = false;
    }

    public VampireConsumableItem(FoodProperties vampireFood, @NotNull FoodProperties humanFood, boolean hasFoodEffectTooltip) {
        super(new Properties().food(humanFood));
        this.vampireFood = vampireFood;
        this.hasFoodEffectTooltip = hasFoodEffectTooltip;
        this.hasHumanFoodEffectTooltip = false;
        this.hasCustomTooltip = false;
    }

    public VampireConsumableItem(FoodProperties vampireFood, @NotNull FoodProperties humanFood, boolean hasFoodEffectTooltip, boolean hasCustomTooltip) {
        super(new Properties().food(humanFood));
        this.vampireFood = vampireFood;
        this.hasFoodEffectTooltip = hasFoodEffectTooltip;
        this.hasHumanFoodEffectTooltip = false;
        this.hasCustomTooltip = hasCustomTooltip;
    }

    public VampireConsumableItem(FoodProperties vampireFood, @NotNull FoodProperties humanFood, boolean hasFoodEffectTooltip, boolean hasCustomTooltip, boolean hasHumanFoodEffectTooltip) {
        super(new Properties().food(humanFood));
        this.vampireFood = vampireFood;
        this.hasFoodEffectTooltip = hasFoodEffectTooltip;
        this.hasHumanFoodEffectTooltip = hasHumanFoodEffectTooltip;
        this.hasCustomTooltip = hasCustomTooltip;
    }

    public VampireConsumableItem(FoodProperties vampireFood, @NotNull FoodProperties humanFood, Item craftRemainder, boolean hasFoodEffectTooltip) {
        super(new Properties().food(humanFood).craftRemainder(craftRemainder).stacksTo(16));
        this.vampireFood = vampireFood;
        this.hasFoodEffectTooltip = hasFoodEffectTooltip;
        this.hasHumanFoodEffectTooltip = false;
        this.hasCustomTooltip = false;
    }

    @Nullable
    @Override
    public IFaction<?> getExclusiveFaction(@NotNull ItemStack stack) {
        return VReference.VAMPIRE_FACTION;
    }

    @NotNull
    @Override
    public ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level worldIn, @NotNull LivingEntity entityLiving) {
        if (!worldIn.isClientSide) {
            this.affectConsumer(stack, worldIn, entityLiving);
        }

        if (entityLiving instanceof Player player) {
            // Don't shrink stack before retrieving food
            VampirePlayer.getOpt(player).ifPresent(v -> v.drinkBlood(vampireFood.getNutrition(), vampireFood.getSaturationModifier()));
        }

        if (entityLiving instanceof IVampire) {
            ((IVampire) entityLiving).drinkBlood(vampireFood.getNutrition(), vampireFood.getSaturationModifier());
        } else {
            // We don't use entityLiving.eat because it applies human food effect to vampires
            VDHelper.feedEntity(worldIn, stack, entityLiving);  // Applies human food effects only to humans
        }
        if (entityLiving instanceof Player player && !player.isCreative() || !(entityLiving instanceof Player)) {
            stack.shrink(1);
        }

        worldIn.playSound(null, entityLiving.getX(), entityLiving.getY(), entityLiving.getZ(), SoundEvents.PLAYER_BURP, SoundSource.PLAYERS, 0.5F, worldIn.random.nextFloat() * 0.1F + 0.9F);

        if (Helper.isVampire(entityLiving)) {
            VDHelper.addFoodEffects(vampireFood, worldIn, entityLiving);
        }

        if (!stack.isEdible()) {
            Player player = entityLiving instanceof Player ? (Player) entityLiving : null;
            if (player instanceof ServerPlayer) {
                CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer) player, stack);
            }
        }

        return stack;
    }

    public FoodProperties getVampireFood() {
        return vampireFood;
    }

    /**
     * Override this to apply changes to the consumer (e.g. curing effects).
     */
    public void affectConsumer(ItemStack stack, Level level, LivingEntity consumer) {
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag isAdvanced) {
        Player player = VampirismMod.proxy.getClientPlayer();
        assert player != null;

        if (Configuration.FOOD_EFFECT_TOOLTIP.get()) {
            if (this.hasCustomTooltip) {
                MutableComponent textEmpty = VDTextUtils.getTranslation("tooltip." + this);
                tooltip.add(textEmpty.withStyle(ChatFormatting.BLUE));
            }
            if (this.hasFoodEffectTooltip) {
                FoodProperties foodProperties = Helper.isVampire(player) ? vampireFood : Objects.requireNonNull(stack.getFoodProperties(player));
                if (!foodProperties.getEffects().isEmpty()) {
                    if (Helper.isVampire(player))
                        VDTextUtils.addFoodEffectTooltip(vampireFood, tooltip, 1.0F);
                    else if (hasHumanFoodEffectTooltip) {
                        VDTextUtils.addFoodEffectTooltip(Objects.requireNonNull(stack.getFoodProperties(player)), tooltip, 1.0F);
                    }
                }
            }
        }

        VDTooltipUtils.addFactionFoodToolTips(tooltip, player, VReference.VAMPIRE_FACTION);
    }
}
