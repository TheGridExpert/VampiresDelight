package net.grid.vampiresdelight.common.item;

import de.teamlapen.vampirism.api.entity.vampire.IVampire;
import de.teamlapen.vampirism.entity.player.vampire.VampirePlayer;
import de.teamlapen.vampirism.util.Helper;
import net.grid.vampiresdelight.common.utility.VDHelper;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class VampireDrinkableItem extends VampireConsumableItem {
    private final FoodProperties vampireFood;
    private final FoodProperties hunterFood;

    public VampireDrinkableItem(FoodProperties vampireFood, @NotNull FoodProperties humanFood) {
        super(vampireFood, humanFood, true, false, false);
        this.vampireFood = vampireFood;
        this.hunterFood = null;
    }

    public VampireDrinkableItem(FoodProperties vampireFood, FoodProperties hunterFood, @NotNull FoodProperties humanFood) {
        super(vampireFood, humanFood);
        this.vampireFood = vampireFood;
        this.hunterFood = hunterFood;
    }

    public FoodProperties getHunterFood() {
        return hunterFood;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 32;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
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
            VDHelper.feedEntity(worldIn, stack, Helper.isHunter(entityLiving) && hunterFood != null ? hunterFood : stack.getFoodProperties(entityLiving), entityLiving);  // Applies human food effects only to humans
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

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack heldStack = player.getItemInHand(hand);
        if (heldStack.isEdible()) {
            if (player.canEat(heldStack.getFoodProperties(player).canAlwaysEat())) {
                player.startUsingItem(hand);
                return InteractionResultHolder.consume(heldStack);
            } else {
                return InteractionResultHolder.fail(heldStack);
            }
        }
        return ItemUtils.startUsingInstantly(level, player, hand);
    }
}
