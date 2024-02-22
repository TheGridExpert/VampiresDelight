package net.grid.vampiresdelight.common.item;

import de.teamlapen.vampirism.api.entity.vampire.IVampire;
import de.teamlapen.vampirism.entity.player.vampire.VampirePlayer;
import de.teamlapen.vampirism.entity.vampire.DrinkBloodContext;
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

    public VampireDrinkableItem(Properties properties, FoodProperties vampireFood) {
        super(properties, vampireFood, true, false, false);
        this.vampireFood = vampireFood;
        this.hunterFood = null;
    }

    public VampireDrinkableItem(Properties properties, FoodProperties vampireFood, FoodProperties hunterFood) {
        super(properties, vampireFood);
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
    public ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity consumer) {
        if (!level.isClientSide) {
            this.affectConsumer(stack, level, consumer);
        }

        if (consumer instanceof Player player) {
            // Don't shrink stack before retrieving food
            VampirePlayer.getOpt(player).ifPresent(v -> v.drinkBlood(vampireFood.getNutrition(), vampireFood.getSaturationModifier(), new DrinkBloodContext(stack)));
        }
        if (consumer instanceof IVampire) {
            ((IVampire) consumer).drinkBlood(vampireFood.getNutrition(), vampireFood.getSaturationModifier(), new DrinkBloodContext(stack));
        } else if (!Helper.isVampire(consumer))
            //consumer.eat(level, stack);
            VDHelper.eatFood(level, consumer, stack, Helper.isHunter(consumer) && hunterFood != null ? hunterFood : stack.getFoodProperties(consumer));

        if (consumer instanceof Player player && !player.isCreative() || !(consumer instanceof Player)) {
            stack.shrink(1);
        }

        level.playSound(null, consumer.getX(), consumer.getY(), consumer.getZ(), SoundEvents.PLAYER_BURP, SoundSource.PLAYERS, 0.5F, level.random.nextFloat() * 0.1F + 0.9F);

        if (Helper.isVampire(consumer)) {
            VDHelper.addFoodEffects(vampireFood, level, consumer);
        }

        if (!stack.isEdible()) {
            Player player = consumer instanceof Player ? (Player) consumer : null;
            if (player instanceof ServerPlayer) {
                CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer) player, stack);
            }
        }

        ItemStack containerStack = stack.getCraftingRemainingItem();

        if (stack.isEmpty()) {
            return containerStack;
        } else {
            if (consumer instanceof Player player && !((Player) consumer).getAbilities().instabuild) {
                if (!player.getInventory().add(containerStack)) {
                    player.drop(containerStack, false);
                }
            }
            return stack;
        }
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
