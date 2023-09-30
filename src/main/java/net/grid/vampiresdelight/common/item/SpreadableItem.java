package net.grid.vampiresdelight.common.item;

import net.grid.vampiresdelight.common.crafting.SpreadingRecipe;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.chat.LoggedChatMessage;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class SpreadableItem extends Item {
    public SpreadableItem(Properties properties) {
        super(properties.defaultDurability(12));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);

        if (itemstack.getOrCreateTag().contains("Spreading")) {
            playerIn.startUsingItem(handIn);
            return new InteractionResultHolder<>(InteractionResult.PASS, itemstack);
        }

        InteractionHand leftHand = handIn == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
        ItemStack itemInLeftHand = playerIn.getItemInHand(leftHand);
        /*
        if (SpreadingRecipe.canPolish(worldIn, itemInLeftHand)) {

            ItemStack item = itemInLeftHand.copy();
            ItemStack target = item.split(1);
            playerIn.startUsingItem(handIn);
            itemstack.getOrCreateTag().put("Spreading", target.serializeNBT());
            playerIn.setItemInHand(leftHand, item);

            return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
        }
        */
        return null;
    }

    @Override
    public void onDestroyed(ItemEntity itemEntity) {
        Level level = itemEntity.level;
        if (level.isClientSide) {
            return;
        }
        Player player = Minecraft.getInstance().player;
        ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(Items.GLASS_BOTTLE));
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 16;
    }
}
