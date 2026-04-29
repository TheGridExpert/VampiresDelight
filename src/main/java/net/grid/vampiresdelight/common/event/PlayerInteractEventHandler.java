package net.grid.vampiresdelight.common.event;

import de.teamlapen.vampirism.core.ModBlocks;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.block.ConsumableCakeBlock;
import net.grid.vampiresdelight.common.registry.VDBlocks;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.grid.vampiresdelight.common.tag.VDTags;
import net.grid.vampiresdelight.common.utility.VDTextUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.entity.player.UseItemOnBlockEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import vectorwing.farmersdelight.common.tag.ModTags;
import vectorwing.farmersdelight.common.utility.ItemUtils;

import java.util.Map;

@SuppressWarnings("unused")
@EventBusSubscriber(modid = VampiresDelight.MODID)
public class PlayerInteractEventHandler {
    @SubscribeEvent
    public static void onCursedEarthClickedWithHoe(BlockEvent.BlockToolModificationEvent event) {
        if (event.isCanceled() || ! event.getItemAbility().equals(ItemAbilities.HOE_TILL))
            return;

        LevelAccessor world = event.getContext().getLevel();
        BlockPos pos = event.getPos();
        BlockState cursedBlock = world.getBlockState(event.getContext().getClickedPos());
        BlockState newBlock = VDBlocks.CURSED_FARMLAND.get().defaultBlockState();

        if (event.getItemAbility() == ItemAbilities.HOE_TILL && (cursedBlock.is(ModBlocks.CURSED_EARTH.get()) || cursedBlock.is(ModBlocks.CURSED_GRASS.get()))) {
            if (newBlock.canSurvive(world, pos)) {
                event.setFinalState(VDBlocks.CURSED_FARMLAND.get().defaultBlockState());
            }
        }
    }

    @SubscribeEvent
    public static void onRichSoilClickedWithPureBlood(UseItemOnBlockEvent event) {
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        BlockState blockState = level.getBlockState(pos);
        Block block = blockState.getBlock();
        ItemStack itemStack = event.getItemStack();

        if (!level.isClientSide() && event.getUsePhase() == UseItemOnBlockEvent.UsePhase.ITEM_AFTER_BLOCK) {
            if (itemStack.is(de.teamlapen.vampirism.core.ModTags.Items.PURE_BLOOD)) {
                Map<Block, Block> blocksMap = Map.of(
                        vectorwing.farmersdelight.common.registry.ModBlocks.RICH_SOIL.get(), VDBlocks.BLOODY_SOIL.get(),
                        vectorwing.farmersdelight.common.registry.ModBlocks.RICH_SOIL_FARMLAND.get(), VDBlocks.BLOODY_SOIL_FARMLAND.get()
                );

                Block bloodyBlock = blocksMap.get(block);
                if (bloodyBlock != null) {
                    itemStack.shrink(1);
                    level.setBlockAndUpdate(pos, bloodyBlock.defaultBlockState());
                    level.playSound(null, pos, SoundEvents.SCULK_BLOCK_SPREAD, SoundSource.BLOCKS, 3.0F, 1.0F);
                    event.setCancellationResult(ItemInteractionResult.sidedSuccess(level.isClientSide()));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onWrongPlantSoilClicked(PlayerInteractEvent.RightClickBlock event) {
        Item item = event.getEntity().getItemInHand(event.getHand()).getItem();
        Block block = event.getLevel().getBlockState(event.getPos()).getBlock();

        if (item == VDItems.ORCHID_SEEDS.get() && (block == Blocks.FARMLAND || block == vectorwing.farmersdelight.common.registry.ModBlocks.RICH_SOIL_FARMLAND.get())) {
            event.getEntity().displayClientMessage(VDTextUtils.getTranslation("text.planted_on_vampire_soil"), true);
        }
    }

    @SubscribeEvent
    public static void onCakeInteraction(PlayerInteractEvent.RightClickBlock event) {
        ItemStack toolStack = event.getEntity().getItemInHand(event.getHand());

        if (!toolStack.is(ModTags.Items.KNIVES)) {
            return;
        }

        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        BlockState state = event.getLevel().getBlockState(pos);
        Block block = state.getBlock();

        if (state.is(VDTags.DROPS_ORCHID_CAKE_SLICE)) {
            level.setBlock(pos, VDBlocks.ORCHID_CAKE.get().defaultBlockState().setValue(ConsumableCakeBlock.BITES, 1), 3);
            Block.dropResources(state, level, pos);
            ItemUtils.spawnItemEntity(level, new ItemStack(VDItems.ORCHID_CAKE_SLICE.get()),
                    pos.getX(), pos.getY() + 0.2, pos.getZ() + 0.5,
                    -0.05, 0, 0);
            level.playSound(null, pos, SoundEvents.WOOL_BREAK, SoundSource.PLAYERS, 0.8F, 0.8F);

            event.setCancellationResult(InteractionResult.SUCCESS);
            event.setCanceled(true);
        }

        if (block == VDBlocks.ORCHID_CAKE.get()) {
            int bites = state.getValue(ConsumableCakeBlock.BITES);
            if (bites < 6) {
                level.setBlock(pos, state.setValue(ConsumableCakeBlock.BITES, bites + 1), 3);
            } else {
                level.removeBlock(pos, false);
            }
            ItemUtils.spawnItemEntity(level, new ItemStack(VDItems.ORCHID_CAKE_SLICE.get()),
                    pos.getX() + (bites * 0.1), pos.getY() + 0.2, pos.getZ() + 0.5,
                    -0.05, 0, 0);
            level.playSound(null, pos, SoundEvents.WOOL_BREAK, SoundSource.PLAYERS, 0.8F, 0.8F);

            event.setCancellationResult(InteractionResult.SUCCESS);
            event.setCanceled(true);
        }
    }
}
