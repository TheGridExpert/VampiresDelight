package net.grid.vampiresdelight.common.item;

import net.grid.vampiresdelight.common.block.PlacedPourableBottleBlock;
import net.grid.vampiresdelight.common.registry.VDAdvancementTriggers;
import net.grid.vampiresdelight.common.registry.VDDataComponents;
import net.grid.vampiresdelight.common.registry.VDSounds;
import net.grid.vampiresdelight.common.utility.VDNameUtils;
import net.grid.vampiresdelight.common.utility.VDTextUtils;
import net.grid.vampiresdelight.common.utility.VDTooltipUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.util.FakePlayer;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.item.component.ItemStackWrapper;

import java.util.List;

/**
 * Credits to the Create mod for mechanic
 * <a href="https://github.com/Creators-of-Create/Create">...</a>
 */
public class PourableBottleItem extends Item implements ICustomUseItem {

    private final PlacedPourableBottleBlock placedBottleBlock;
    private final Item serving;
    private final Item servingContainer;
    private final int servings;

    public PourableBottleItem(Properties properties, PlacedPourableBottleBlock placedBottleBlock, Item serving, Item servingContainer, int servings) {
        super(properties.durability(servings).stacksTo(1).setNoRepair());
        this.placedBottleBlock = placedBottleBlock;
        this.serving = serving;
        this.servingContainer = servingContainer;
        this.servings = servings;
    }

    @Override
    public boolean isEnchantable(@NotNull ItemStack stack) {
        return false;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand pouringHand) {
        BlockHitResult hitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.NONE);
        InteractionHand offHand = pouringHand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
        ItemStack pouringBottle = player.getItemInHand(pouringHand);
        ItemStack glassBottle = player.getItemInHand(offHand);

        if (hitResult.getType() == HitResult.Type.BLOCK && player.isCrouching()) {
            BlockPos blockPos = hitResult.getBlockPos();
            BlockPos targetPos = player.isCrouching() ? blockPos.relative(hitResult.getDirection()) : blockPos;
            BlockState targetState = level.getBlockState(targetPos);
            BlockState bottleBlockToPlace = placedBottleBlock.getStateForPlacement(new BlockPlaceContext(level, player, pouringHand, pouringBottle, hitResult));

            if (targetState.canBeReplaced() && bottleBlockToPlace != null) {
                level.setBlock(targetPos, bottleBlockToPlace, 3);

                bottleBlockToPlace.getBlock().setPlacedBy(level, targetPos, bottleBlockToPlace, player, pouringBottle);
                if (player instanceof ServerPlayer serverPlayer) {
                    CriteriaTriggers.PLACED_BLOCK.trigger(serverPlayer, targetPos, pouringBottle);
                }
                SoundType soundtype = bottleBlockToPlace.getSoundType(level, targetPos, player);
                level.playSound(player, targetPos, soundtype.getPlaceSound(), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                level.gameEvent(GameEvent.BLOCK_PLACE, targetPos, GameEvent.Context.of(player, bottleBlockToPlace));

                if (!player.getAbilities().instabuild) {
                    pouringBottle.shrink(1);
                }

                return InteractionResultHolder.success(pouringBottle);
            }
        }

        if (!pouringBottle.getOrDefault(VDDataComponents.SERVING_HELD, ItemStackWrapper.EMPTY).getStack().isEmpty()) {
            player.startUsingItem(pouringHand);
            return InteractionResultHolder.pass(pouringBottle);
        }

        if (glassBottle.getItem() == servingContainer) {
            ItemStack itemUsed = glassBottle.copy();
            ItemStack toPour = itemUsed.split(1);
            player.startUsingItem(pouringHand);
            pouringBottle.set(VDDataComponents.SERVING_HELD, new ItemStackWrapper(toPour));
            player.setItemInHand(offHand, itemUsed);
            return InteractionResultHolder.success(pouringBottle);
        }

        Vec3 POVHit = hitResult.getLocation();
        AABB aabb = new AABB(POVHit, POVHit).inflate(1f);
        ItemEntity pickUp = null;

        for (ItemEntity itemEntity : level.getEntitiesOfClass(ItemEntity.class, aabb)) {
            if (!itemEntity.isAlive() && itemEntity.position().distanceTo(player.position()) > 3 && !itemEntity.isAlive() &&
                    itemEntity.getItem().getItem() != servingContainer)
                continue;
            pickUp = itemEntity;
            break;
        }

        if (pickUp != null) {
            ItemStack pickedItem = pickUp.getItem().copy();
            ItemStack toPour = pickedItem.split(1);
            player.startUsingItem(pouringHand);

            if (!level.isClientSide) {
                pouringBottle.set(VDDataComponents.SERVING_HELD, new ItemStackWrapper(toPour));
                if (pickedItem.isEmpty()) pickUp.discard();
                else pickUp.setItem(pickedItem);
            }

            return InteractionResultHolder.success(pouringBottle);
        }

        return InteractionResultHolder.fail(pouringBottle);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity entity) {
        if (!(entity instanceof Player player))
            return itemStack;

        ItemStackWrapper storedStack = itemStack.getOrDefault(VDDataComponents.SERVING_HELD, ItemStackWrapper.EMPTY);
        if (!storedStack.getStack().isEmpty()) {
            if (player instanceof FakePlayer) {
                player.drop(new ItemStack(serving), false, false);
            } else {
                player.getInventory().placeItemBackInInventory(new ItemStack(serving));
            }
            itemStack.remove(VDDataComponents.SERVING_HELD);
            if (player instanceof ServerPlayer serverPlayer) {
                VDAdvancementTriggers.DRINK_POURED.get().trigger(serverPlayer, itemStack);
            }
            entity.playSound(VDSounds.POURING_FINISH.get(), 1.2F, 1.0F);
            itemStack.setDamageValue(itemStack.getDamageValue() + 1);
            if (itemStack.getDamageValue() >= itemStack.getMaxDamage()) itemStack = new ItemStack(servingContainer);
        }

        return itemStack;
    }

    @Override
    public void releaseUsing(ItemStack itemStack, Level level, LivingEntity entity, int durationTime) {
        if (!(entity instanceof Player player))
            return;

        ItemStackWrapper storedStack = itemStack.getOrDefault(VDDataComponents.SERVING_HELD, ItemStackWrapper.EMPTY);
        if (!storedStack.getStack().isEmpty()) {
            player.getInventory().placeItemBackInInventory(storedStack.getStack());
            itemStack.remove(VDDataComponents.SERVING_HELD);

            entity.playSound(VDSounds.POURING_FINISH.get(), 1.2F, 1.0F);
        }
    }

    @Override
    public boolean hasCustomUseEffects() {
        return true;
    }

    @Override
    public boolean triggerUseEffects(ItemStack stack, LivingEntity entity, int amount) {
        //if (entity.getTicksUsingItem() % 3 == 0) {
            //VDEntityUtils.spawnParticlesOnItemEntityHolding(new DrinkSplashOptions(new Color(112, 18, 27).getRGB()), entity);
        //}

        if ((entity.getTicksUsingItem() - 6) % 7 == 0) {
            entity.playSound(VDSounds.POURING_SHORT.get(), 1.2F, entity.getRandom().nextFloat() * 0.2F + 0.9F + ((float) entity.getTicksUsingItem() / 128));
        }

        return true;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 32;
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack stack) {
        ItemStack copyStack = stack.copy();
        copyStack.setDamageValue(copyStack.getDamageValue() + 1);
        if (copyStack.getDamageValue() >= this.servings) return new ItemStack(servingContainer);
        return copyStack;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        int servings = stack.getMaxDamage() - stack.getDamageValue();
        MutableComponent textTooltip = VDTextUtils.getTranslation("tooltip." + VDNameUtils.itemName(this) + (servings == 1 ? ".single_serving" : ".multiple_servings"), servings);
        tooltipComponents.add(textTooltip.withStyle(ChatFormatting.GRAY));
        VDTooltipUtils.addShiftTooltip("tooltip.pourable_drink_item", tooltipComponents);
    }

    @Override
    public @NotNull UseAnim getUseAnimation(ItemStack itemStack) {
        return UseAnim.DRINK;
    }

    /*
    @EventBusSubscriber(modid = VampiresDelight.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.GAME)
    public static class ContainerRender {
        @SubscribeEvent
        static void onRenderHandEvent(RenderHandEvent event) {
            Player player = Minecraft.getInstance().player;
            if (player == null) {
                return;
            }

            // In this case it's done when arm with nothing it in is rendered while other arm has pouring bottle, so it's reverted
            InteractionHand offHand = event.getHand();
            InteractionHand pouringHand = offHand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
            ItemStack glassBottle = player.getItemInHand(pouringHand);
            ItemStack pouringBottle = player.getItemInHand(offHand);

            // Render bottle if it's empty and players pours into nothing
            if (glassBottle.isEmpty() && !pouringBottle.isEmpty() && pouringBottle.getItem() instanceof PourableBottleItem pourableBottleItem) {
                // Serving container in which drink is poured. So it's usually glass bottle. That's what we are going to render
                Item servingContainer = pourableBottleItem.servingContainer;
                ItemDisplayContext displayContext = player.getMainArm() == HumanoidArm.RIGHT && offHand == InteractionHand.MAIN_HAND ? ItemDisplayContext.THIRD_PERSON_RIGHT_HAND : ItemDisplayContext.THIRD_PERSON_LEFT_HAND;

                Minecraft.getInstance().getEntityRenderDispatcher().getItemInHandRenderer().renderItem(player, new ItemStack(servingContainer), displayContext, player.getMainArm() == HumanoidArm.LEFT, event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight());
            }
        }
    }
     */
}
