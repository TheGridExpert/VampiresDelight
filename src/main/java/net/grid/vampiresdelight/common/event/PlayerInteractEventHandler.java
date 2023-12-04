package net.grid.vampiresdelight.common.event;

import de.teamlapen.vampirism.core.ModBlocks;
import net.grid.vampiresdelight.common.registry.VDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PlayerInteractEventHandler {
    @SubscribeEvent
    public void onCursedEarthClickedWithHoe(BlockEvent.BlockToolModificationEvent event) {
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
}
