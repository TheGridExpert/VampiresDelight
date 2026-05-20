package net.grid.vampiresdelight.common.world.item;

import net.grid.vampiresdelight.common.config.VDCommonConfig;
import net.grid.vampiresdelight.common.core.VDItems;
import net.grid.vampiresdelight.common.core.VDSounds;
import net.grid.vampiresdelight.common.world.entity.AlchemicalCocktailEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AlchemicalCocktailItem extends Item {

    public AlchemicalCocktailItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack heldStack = player.getItemInHand(hand);

        level.playSound(null, player.getX(), player.getY(), player.getZ(), VDSounds.ALCHEMICAL_COCKTAIL_THROW.get(), SoundSource.NEUTRAL, 0.5F, 0.4F / (level.random.nextFloat() * 0.4F + 0.8F));
        if (!level.isClientSide) {
            AlchemicalCocktailEntity projectile = new AlchemicalCocktailEntity(level, player);
            projectile.setItem(heldStack);
            projectile.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
            level.addFreshEntity(projectile);
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        if (!player.getAbilities().instabuild) {
            heldStack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(heldStack, level.isClientSide());
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return VDCommonConfig.ALCHEMICAL_COCKTAIL_STACK_SIZE.get();
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        AlchemicalCocktailEntity.playSmashSound(target, isMetalPipe(stack));
        if (!isMetalPipe(stack)) {
            target.setSecondsOnFire(16);
            AlchemicalCocktailEntity.setOnFire(target.blockPosition(), getSplashRadius(), target.level(), attacker);
            if (!(attacker instanceof Player player && player.isCreative())) stack.shrink(1);

            return true;
        }

        return false;
    }

    public static boolean isMetalPipe(ItemStack stack) {
        return stack.getHoverName().toString().toLowerCase().replace(" ", "").contains("metal pipe") && stack.getItem() == VDItems.ALCHEMICAL_COCKTAIL.get();
    }

    public static double getSplashRadius() {
        return VDCommonConfig.ALCHEMICAL_COCKTAIL_SPLASH_RADIUS.get();
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        if (!VDCommonConfig.ALCHEMICAL_COCKTAIL_BURNS_GROUND.get()) {
            tooltipComponents.add(Component.translatable("tooltip.vampiresdelight.alchemical_fire.burning_disabled").withStyle(ChatFormatting.GRAY));
        }
    }
}
