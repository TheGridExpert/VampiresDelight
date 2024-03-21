package net.grid.vampiresdelight.common.event;

import de.teamlapen.vampirism.core.ModBlocks;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.block.ConsumableCakeBlock;
import net.grid.vampiresdelight.common.registry.VDBlocks;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.grid.vampiresdelight.common.tag.VDTags;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import vectorwing.farmersdelight.common.tag.ModTags;
import vectorwing.farmersdelight.common.utility.ItemUtils;

@Mod.EventBusSubscriber(modid = VampiresDelight.MODID)
public class PlayerInteractEventHandler {
    @SubscribeEvent
    public static void onCursedEarthClickedWithHoe(BlockEvent.BlockToolModificationEvent event) {
        if (event.isCanceled() || ! event.getToolAction().equals(ToolActions.HOE_TILL))
            return;

        LevelAccessor world = event.getContext().getLevel();
        BlockPos pos = event.getPos();
        BlockState cursedBlock = world.getBlockState(event.getContext().getClickedPos());
        BlockState newBlock = VDBlocks.CURSED_FARMLAND.get().defaultBlockState();

        if (event.getToolAction() == ToolActions.HOE_TILL && (cursedBlock.is(ModBlocks.CURSED_EARTH.get()) || cursedBlock.is(ModBlocks.CURSED_GRASS.get()))) {
            if (newBlock.canSurvive(world, pos)) {
                event.setFinalState(VDBlocks.CURSED_FARMLAND.get().defaultBlockState());
            }
        }
    }

    @SubscribeEvent
    public static void onCakeInteraction(PlayerInteractEvent.RightClickBlock event) {
        ItemStack toolStack = event.getEntity().getItemInHand(event.getHand());

        if (!toolStack.is(ModTags.KNIVES)) {
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
