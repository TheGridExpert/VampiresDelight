package net.grid.vampiresdelight.common.world.item;

import net.grid.vampiresdelight.common.util.VDEntityUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class FactionDrinkableItem extends FactionConsumableItem {

    public FactionDrinkableItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 32;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return useDrink(level, player, hand);
    }

    @Override
    public boolean hasAnyFoodTooltip(ItemStack stack, @Nullable Player player) {
        return player != null && VDEntityUtils.canConsumeHumanFood(player, stack);
    }

    public static InteractionResultHolder<ItemStack> useDrink(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.isEdible()) {
            if (player.canEat(stack.getFoodProperties(player).canAlwaysEat())) {
                player.startUsingItem(hand);
                return InteractionResultHolder.consume(stack);
            }
            return InteractionResultHolder.fail(stack);
        }
        return ItemUtils.startUsingInstantly(level, player, hand);
    }
}