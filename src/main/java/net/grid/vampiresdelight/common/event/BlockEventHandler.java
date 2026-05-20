package net.grid.vampiresdelight.common.event;

import de.teamlapen.vampirism.core.ModTags;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.core.VDAdvancementTriggers;
import net.grid.vampiresdelight.common.core.VDBlocks;
import net.grid.vampiresdelight.common.core.VDSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import vectorwing.farmersdelight.common.registry.ModBlocks;

import java.util.Map;

@Mod.EventBusSubscriber(modid = VampiresDelight.MODID)
public class BlockEventHandler {

    @SubscribeEvent
    public static void onCursedEarthTilled(BlockEvent.BlockToolModificationEvent event) {
        if (!event.getToolAction().equals(ToolActions.HOE_TILL)) return;

        Level level = event.getContext().getLevel();
        BlockState arableBlock = level.getBlockState(event.getContext().getClickedPos());

        if (arableBlock.is(ModTags.Blocks.CURSED_EARTH)) {
            event.setFinalState(VDBlocks.CURSED_FARMLAND.get().defaultBlockState());
        }
    }

    @SubscribeEvent
    public static void onRichSoilBloodied(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        Block block = level.getBlockState(pos).getBlock();
        ItemStack stack = event.getItemStack();

        if (level instanceof ServerLevel serverLevel && stack.is(ModTags.Items.PURE_BLOOD)) {
            Map<Block, Block> blocksMap = Map.of(
                    ModBlocks.RICH_SOIL.get(), VDBlocks.BLOODY_SOIL.get(),
                    ModBlocks.RICH_SOIL_FARMLAND.get(), VDBlocks.BLOODY_SOIL_FARMLAND.get()
            );

            Block bloodyBlock = blocksMap.get(block);
            if (bloodyBlock != null) {
                Player player = event.getEntity();
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
                BlockState bloodyState = bloodyBlock.defaultBlockState();
                serverLevel.setBlockAndUpdate(pos, bloodyState);
                serverLevel.playSound(null, pos, VDSounds.BLOOD_POURED.get(), SoundSource.BLOCKS, 5.0F, 1.0F);
                serverLevel.sendParticles(
                        new BlockParticleOption(ParticleTypes.BLOCK, bloodyState),
                        pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                        20, 0.3, 0.3, 0.3, 0.15
                );
                if (player instanceof ServerPlayer serverPlayer) {
                    VDAdvancementTriggers.RICH_SOIL_BLOODIED.trigger(serverPlayer);
                }
                event.setCancellationResult(InteractionResult.sidedSuccess(false));
                event.setCanceled(true);
            }
        }
    }
}
