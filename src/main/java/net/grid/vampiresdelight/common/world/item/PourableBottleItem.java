package net.grid.vampiresdelight.common.world.item;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.client.extension.PourableBottleItemExtension;
import net.grid.vampiresdelight.common.core.VDAdvancementTriggers;
import net.grid.vampiresdelight.common.core.VDSounds;
import net.grid.vampiresdelight.common.util.VDTooltipUtils;
import net.grid.vampiresdelight.common.world.block.PlacedPourableBottleBlock;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

/**
 * Credits to the Create mod for mechanic
 * <a href="https://github.com/Creators-of-Create/Create">...</a>
 */
public class PourableBottleItem extends Item implements ICustomUseItem {

    private static final String TAG_SERVING_HELD = "ServingHeld";

    private final PlacedPourableBottleBlock placedBottleBlock;
    private final Item serving;
    private final Item servingContainer;
    private final int servings;

    public PourableBottleItem(Properties properties, PlacedPourableBottleBlock placedBottleBlock, Item serving, Item servingContainer, int servings) {
        super(properties.durability(servings).setNoRepair());
        this.placedBottleBlock = placedBottleBlock;
        this.serving = serving;
        this.servingContainer = servingContainer;
        this.servings = servings;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
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
            BlockPos targetPos = blockPos.relative(hitResult.getDirection());
            BlockState targetState = level.getBlockState(targetPos);
            BlockPlaceContext placeContext = new BlockPlaceContext(level, player, pouringHand, pouringBottle, hitResult);
            BlockState bottleBlockToPlace = placedBottleBlock.getStateForPlacement(placeContext);

            if (targetState.canBeReplaced(placeContext) && bottleBlockToPlace != null) {
                level.setBlock(targetPos, bottleBlockToPlace, Block.UPDATE_ALL);

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

                return InteractionResultHolder.sidedSuccess(pouringBottle, level.isClientSide);
            }
        }

        if (!getServingHeld(pouringBottle).isEmpty()) {
            player.startUsingItem(pouringHand);
            return InteractionResultHolder.pass(pouringBottle);
        }

        if (glassBottle.getItem() == servingContainer) {
            ItemStack itemUsed = glassBottle.copy();
            ItemStack toPour = itemUsed.split(1);
            player.startUsingItem(pouringHand);
            setServingHeld(pouringBottle, toPour);
            player.setItemInHand(offHand, itemUsed);
            return InteractionResultHolder.sidedSuccess(pouringBottle, level.isClientSide);
        }

        Vec3 povHit = hitResult.getLocation();
        AABB aabb = new AABB(povHit, povHit).inflate(1f);
        ItemEntity pickUp = null;

        for (ItemEntity itemEntity : level.getEntitiesOfClass(ItemEntity.class, aabb)) {
            if (!itemEntity.isAlive() && itemEntity.position().distanceTo(player.position()) > 3 && !itemEntity.isAlive() && itemEntity.getItem().getItem() != servingContainer) {
                continue;
            }
            pickUp = itemEntity;
            break;
        }

        if (pickUp != null) {
            ItemStack pickedItem = pickUp.getItem().copy();
            ItemStack toPour = pickedItem.split(1);
            player.startUsingItem(pouringHand);

            if (!level.isClientSide) {
                setServingHeld(pouringBottle, toPour);
                if (pickedItem.isEmpty()) pickUp.discard();
                else pickUp.setItem(pickedItem);
            }

            return InteractionResultHolder.sidedSuccess(pouringBottle, level.isClientSide);
        }

        return InteractionResultHolder.fail(pouringBottle);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (!(entity instanceof Player player))
            return stack;

        if (!getServingHeld(stack).isEmpty()) {
            if (player instanceof FakePlayer) {
                player.drop(new ItemStack(serving), false, false);
            } else {
                player.getInventory().placeItemBackInInventory(new ItemStack(serving));
            }
            clearServingHeld(stack);
            if (player instanceof ServerPlayer serverPlayer) {
                VDAdvancementTriggers.DRINK_POURED.trigger(serverPlayer, stack);
            }
            entity.playSound(VDSounds.POURING_FINISH.get(), 1.2F, 1.0F);
            stack.setDamageValue(stack.getDamageValue() + 1);
            if (stack.getDamageValue() >= stack.getMaxDamage()) stack = new ItemStack(servingContainer);
        }

        return stack;
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int durationTime) {
        if (!(entity instanceof Player player))
            return;

        ItemStack heldServing = getServingHeld(stack);
        if (!heldServing.isEmpty()) {
            player.getInventory().placeItemBackInInventory(heldServing);
            clearServingHeld(stack);

            entity.playSound(VDSounds.POURING_FINISH.get(), 1.2F, 1.0F);
        }
    }

    @Override
    public boolean hasCustomUseEffects() {
        return true;
    }

    @Override
    public boolean triggerUseEffects(ItemStack stack, LivingEntity entity, int amount) {
        if ((entity.getTicksUsingItem() - 6) % 7 == 0) {
            entity.playSound(VDSounds.POURING_SHORT.get(), 1.2F, entity.getRandom().nextFloat() * 0.2F + 0.9F + ((float) entity.getTicksUsingItem() / 128));
        }

        return true;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
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
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        int remainingServings = stack.getMaxDamage() - stack.getDamageValue();
        String itemPath = ForgeRegistries.ITEMS.getKey(this).getPath();
        MutableComponent textTooltip = Component.translatable("tooltip." + VampiresDelight.MODID + "." + itemPath + (remainingServings == 1 ? ".single" : ".multiple"), remainingServings);
        tooltipComponents.add(textTooltip.withStyle(ChatFormatting.GRAY));
        VDTooltipUtils.addShiftTooltip("tooltip." + VampiresDelight.MODID + ".pourable_drink", tooltipComponents);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new PourableBottleItemExtension());
    }

    private static ItemStack getServingHeld(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag == null || !tag.contains(TAG_SERVING_HELD, CompoundTag.TAG_COMPOUND)) {
            return ItemStack.EMPTY;
        }
        return ItemStack.of(tag.getCompound(TAG_SERVING_HELD));
    }

    private static void setServingHeld(ItemStack stack, ItemStack serving) {
        if (serving.isEmpty()) {
            clearServingHeld(stack);
            return;
        }
        CompoundTag servedTag = new CompoundTag();
        serving.save(servedTag);
        stack.getOrCreateTag().put(TAG_SERVING_HELD, servedTag);
    }

    private static void clearServingHeld(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag != null) {
            tag.remove(TAG_SERVING_HELD);
            if (tag.isEmpty()) stack.setTag(null);
        }
    }
}