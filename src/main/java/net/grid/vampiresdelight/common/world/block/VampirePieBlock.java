package net.grid.vampiresdelight.common.world.block;

import de.teamlapen.vampirism.util.Helper;
import net.grid.vampiresdelight.common.util.VDEntityUtils;
import net.grid.vampiresdelight.common.world.item.FactionConsumableItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import vectorwing.farmersdelight.common.block.PieBlock;

import java.util.function.Supplier;

public class VampirePieBlock extends PieBlock {

    public VampirePieBlock(Properties properties, Supplier<Item> pieSlice) {
        super(properties, pieSlice);
    }

    @Override
    protected InteractionResult consumeBite(Level level, BlockPos pos, BlockState state, Player player) {
        ItemStack sliceStack = getPieSliceItem();
        if (!(sliceStack.getItem() instanceof FactionConsumableItem sliceItem)) {
            return super.consumeBite(level, pos, state, player);
        }

        boolean isVampire = Helper.isVampire(player);
        if (isVampire && sliceItem.getVampireFood() == null) return InteractionResult.PASS;
        if (!isVampire && !player.canEat(false)) return InteractionResult.PASS;

        FoodProperties foodProperties = sliceStack.getFoodProperties(player);
        if (foodProperties == null) return InteractionResult.PASS;

        VDEntityUtils.consumeBloodFood(sliceStack.copy(), level, player);

        int bites = state.getValue(BITES);
        if (bites < getMaxBites() - 1) {
            level.setBlock(pos, state.setValue(BITES, bites + 1), 3);
        } else {
            level.removeBlock(pos, false);
        }

        level.playSound(null, pos, SoundEvents.GENERIC_EAT, SoundSource.PLAYERS, 0.8F, 0.8F);
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, state), pos.getX() + 0.5, pos.getY() + 0.3, pos.getZ() + 0.5, 3, 0.1, 0.1, 0.1, 0.001);
        }

        return InteractionResult.SUCCESS;
    }
}