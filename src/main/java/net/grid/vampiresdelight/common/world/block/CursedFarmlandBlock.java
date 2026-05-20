package net.grid.vampiresdelight.common.world.block;

import de.teamlapen.vampirism.api.VReference;
import de.teamlapen.vampirism.blocks.VampirismFlowerBlock;
import de.teamlapen.vampirism.core.ModBlocks;
import de.teamlapen.vampirism.core.ModItems;
import de.teamlapen.vampirism.items.HolyWaterBottleItem;
import net.grid.vampiresdelight.common.core.VDPlantTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.FarmlandWaterManager;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

@SuppressWarnings("deprecation")
public class CursedFarmlandBlock extends FarmBlock {

    public CursedFarmlandBlock(Properties properties) {
        super(properties);
    }

    public static void turnIntoCursedEarth(BlockState blockState, Level worldIn, BlockPos pos) {
        worldIn.setBlockAndUpdate(pos, pushEntitiesUp(blockState, ModBlocks.CURSED_EARTH.get().defaultBlockState(), worldIn, pos));
    }

    @Override
    public boolean isFertile(BlockState state, BlockGetter level, BlockPos pos) {
        return state.getValue(CursedFarmlandBlock.MOISTURE) > 0;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int moisture = state.getValue(MOISTURE);
        if (!isNearWater(level, pos) && !level.isRainingAt(pos.above())) {
            if (moisture > 0) {
                level.setBlock(pos, state.setValue(MOISTURE, moisture - 1), 2);
            } else if (!isUnderCrops(level, pos)) {
                turnIntoCursedEarth(state, level, pos);
            }
        } else if (moisture < 7) {
            level.setBlock(pos, state.setValue(MOISTURE, 7), 2);
        }
    }

    private static boolean isUnderCrops(BlockGetter level, BlockPos pos) {
        BlockState plant = level.getBlockState(pos.above());
        BlockState state = level.getBlockState(pos);
        return plant.getBlock() instanceof IPlantable plantable && state.canSustainPlant(level, pos, Direction.UP, plantable);
    }

    private static boolean isNearWater(LevelReader level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        for (BlockPos blockpos : BlockPos.betweenClosed(pos.offset(-4, 0, -4), pos.offset(4, 1, 4))) {
            if (state.canBeHydrated(level, pos, level.getFluidState(blockpos), blockpos)) {
                return true;
            }
        }

        return FarmlandWaterManager.hasBlockWaterTicket(level, pos);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!state.canSurvive(level, pos)) {
            turnIntoCursedEarth(state, level, pos);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack heldItemStack = player.getItemInHand(hand);
        if (heldItemStack.getItem() instanceof HolyWaterBottleItem holyWaterItem) {
            int uses = holyWaterItem == ModItems.HOLY_WATER_BOTTLE_ULTIMATE.get() ? 100 : (holyWaterItem == ModItems.HOLY_WATER_BOTTLE_ENHANCED.get() ? 50 : 25);
            if (player.getRandom().nextInt(uses) == 0) {
                heldItemStack.setCount(heldItemStack.getCount() - 1);
            }
            level.setBlockAndUpdate(pos, Blocks.FARMLAND.defaultBlockState());
            return InteractionResult.SUCCESS;
        }

        return super.use(state, level, pos, player, hand, hit);
    }

    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter level, BlockPos pos, Direction facing, IPlantable plantable) {
        PlantType plantType = plantable.getPlantType(level, pos.relative(facing));
        Block plantBlock = plantable.getPlant(level, pos).getBlock();
        return plantType == PlantType.CROP || isVampireOrNonCrop(plantBlock, plantable, plantType);
    }

    public static boolean isVampireOrNonCrop(Block state, IPlantable plantable, PlantType plantType) {
        return plantable instanceof BushBlock && plantType != PlantType.CROP
                || plantType == VReference.VAMPIRE_PLANT_TYPE
                || plantType == VDPlantTypes.CURSED
                || state instanceof VampirismFlowerBlock;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return !this.defaultBlockState().canSurvive(context.getLevel(), context.getClickedPos()) ? ModBlocks.CURSED_EARTH.get().defaultBlockState() : super.getStateForPlacement(context);
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (!level.isClientSide && net.minecraftforge.common.ForgeHooks.onFarmlandTrample(level, pos, ModBlocks.CURSED_EARTH.get().defaultBlockState(), fallDistance, entity)) {
            turnIntoCursedEarth(state, level, pos);
        }
        entity.causeFallDamage(fallDistance, 1.0F, entity.damageSources().fall());
    }
}