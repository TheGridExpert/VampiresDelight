package net.grid.vampiresdelight.common.item;

import de.teamlapen.vampirism.VampirismMod;
import de.teamlapen.vampirism.api.VReference;
import de.teamlapen.vampirism.api.entity.factions.IFaction;
import de.teamlapen.vampirism.api.entity.vampire.IVampire;
import de.teamlapen.vampirism.api.items.IFactionExclusiveItem;
import de.teamlapen.vampirism.entity.player.vampire.VampirePlayer;
import de.teamlapen.vampirism.util.Helper;
import net.grid.vampiresdelight.common.util.VDTextUtils;
import net.grid.vampiresdelight.common.util.VDTooltipUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.FarmersDelight;
import vectorwing.farmersdelight.common.Configuration;
import vectorwing.farmersdelight.common.utility.TextUtils;
import net.minecraft.network.chat.Component;

import java.util.List;

public class VampireConsumableItem extends Item implements IFactionExclusiveItem {
    private final FoodProperties vampireFood;
    private final boolean hasFoodEffectTooltip;
    private final boolean hasCustomTooltip;
    private final MobEffectInstance mobEffectInstance;
    private final Item craftRemainder;

    public VampireConsumableItem(FoodProperties vampireFood, @NotNull FoodProperties humanFood) {
        super(new Properties().food(humanFood));
        this.vampireFood = vampireFood;
        this.craftRemainder = null;
        this.mobEffectInstance = null;
        this.hasFoodEffectTooltip = false;
        this.hasCustomTooltip = false;
    }

    public VampireConsumableItem(FoodProperties vampireFood, @NotNull FoodProperties humanFood, MobEffectInstance mobEffectInstance) {
        super(new Properties().food(humanFood));
        this.vampireFood = vampireFood;
        this.craftRemainder = null;
        this.mobEffectInstance = mobEffectInstance;
        this.hasFoodEffectTooltip = false;
        this.hasCustomTooltip = false;
    }

    public VampireConsumableItem(FoodProperties vampireFood, @NotNull FoodProperties humanFood, Item craftRemainder, MobEffectInstance mobEffectInstance, boolean hasFoodEffectTooltip) {
        super(new Properties().food(humanFood).craftRemainder(craftRemainder).stacksTo(16));
        this.vampireFood = vampireFood;
        this.craftRemainder = craftRemainder;
        this.mobEffectInstance = mobEffectInstance;
        this.hasFoodEffectTooltip = hasFoodEffectTooltip;
        this.hasCustomTooltip = false;
    }

    public VampireConsumableItem(FoodProperties vampireFood, @NotNull FoodProperties humanFood, MobEffectInstance mobEffectInstance, boolean hasFoodEffectTooltip) {
        super(new Properties().food(humanFood));
        this.vampireFood = vampireFood;
        this.craftRemainder = null;
        this.mobEffectInstance = mobEffectInstance;
        this.hasFoodEffectTooltip = hasFoodEffectTooltip;
        this.hasCustomTooltip = false;
    }

    public VampireConsumableItem(FoodProperties vampireFood, @NotNull FoodProperties humanFood, MobEffectInstance mobEffectInstance, boolean hasFoodEffectTooltip, boolean hasCustomTooltip) {
        super(new Properties().food(humanFood));
        this.vampireFood = vampireFood;
        this.craftRemainder = null;
        this.mobEffectInstance = mobEffectInstance;
        this.hasFoodEffectTooltip = hasFoodEffectTooltip;
        this.hasCustomTooltip = hasCustomTooltip;
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
            assert stack.getItem().getFoodProperties() != null; //Don't shrink stack before retrieving food
            VampirePlayer.getOpt(player).ifPresent(v -> v.drinkBlood(vampireFood.getNutrition(), vampireFood.getSaturationModifier()));
        }

        if (entityLiving instanceof IVampire) {
            ((IVampire) entityLiving).drinkBlood(vampireFood.getNutrition(), vampireFood.getSaturationModifier());
            stack.shrink(1);
        } else {
            entityLiving.eat(worldIn, stack); //Shrinks stack and applies human food effects
        }

        worldIn.playSound(null, entityLiving.getX(), entityLiving.getY(), entityLiving.getZ(), SoundEvents.PLAYER_BURP, SoundSource.PLAYERS, 0.5F, worldIn.random.nextFloat() * 0.1F + 0.9F);

        if (!Helper.isVampire(entityLiving)) {
            entityLiving.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 20 * 20));
        } else {
            if (mobEffectInstance != null) entityLiving.addEffect(mobEffectInstance);
        }

        if (!stack.isEdible()) {
            Player player = entityLiving instanceof Player ? (Player) entityLiving : null;
            if (player instanceof ServerPlayer) {
                CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer) player, stack);
            }
        }

        return stack;
    }

    /**
     * Override this to apply changes to the consumer (e.g. curing effects).
     */
    public void affectConsumer(ItemStack stack, Level level, LivingEntity consumer) {
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag isAdvanced) {
        if (Configuration.FOOD_EFFECT_TOOLTIP.get()) {
            if (this.hasCustomTooltip) {
                MutableComponent textEmpty = VDTextUtils.getTranslation("tooltip." + this);
                tooltip.add(textEmpty.withStyle(ChatFormatting.BLUE));
            }
            if (this.hasFoodEffectTooltip) {
                VDTextUtils.addFoodEffectTooltip(stack, tooltip, 1.0F);
            }
        }
        VDTooltipUtils.addFactionFoodToolTips(tooltip, VampirismMod.proxy.getClientPlayer(), VReference.VAMPIRE_FACTION);
    }
}
