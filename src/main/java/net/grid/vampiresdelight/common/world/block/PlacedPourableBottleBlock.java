package net.grid.vampiresdelight.common.world.block;

import net.grid.vampiresdelight.common.core.VDSounds;
import net.grid.vampiresdelight.common.world.item.PourableBottleItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

@SuppressWarnings("deprecation")
public class PlacedPourableBottleBlock extends Block implements SimpleWaterloggedBlock {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final IntegerProperty SERVINGS = IntegerProperty.create("servings", 0, 64);

    public static final VoxelShape SHAPE_1 = Stream.of(
            Block.box(5.5, 0, 5.5, 10.5, 11, 10.5),
            Block.box(7, 11, 7, 9, 16, 9)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).orElseThrow();

    private final Supplier<PourableBottleItem> bottleItem;
    private final VoxelShape shape;

    public PlacedPourableBottleBlock(Properties properties, Supplier<PourableBottleItem> bottleItem, VoxelShape shape) {
        super(properties);
        this.bottleItem = bottleItem;
        this.shape = shape;
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false).setValue(SERVINGS, 0));
    }

    @Override
    public String getDescriptionId() {
        return bottleItem.get().getDescriptionId();
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);

        if (stack.getItem() instanceof PourableBottleItem) {
            level.setBlock(pos, state.setValue(SERVINGS, stack.getDamageValue()), 2);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!level.isClientSide && player.isCrouching()) {
            ItemStack itemStack = getBottleStack(state);
            if (player.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) {
                player.setItemInHand(InteractionHand.MAIN_HAND, itemStack);
                level.playSound(null, pos, state.getBlock().getSoundType(state, level, pos, player).getPlaceSound(), SoundSource.BLOCKS, 1.0f, 1.0f);
                level.removeBlock(pos, false);

                return InteractionResult.SUCCESS;
            }
        }

        return super.use(state, level, pos, player, hand, hitResult);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        return List.of(getBottleStack(state));
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
        return getBottleStack(state);
    }

    public ItemStack getBottleStack(BlockState state) {
        ItemStack itemStack = new ItemStack(bottleItem.get());
        itemStack.setDamageValue(state.getValue(SERVINGS));
        return itemStack;
    }

    @Override
    public void onProjectileHit(Level level, BlockState state, BlockHitResult hit, Projectile projectile) {
        BlockPos blockPos = hit.getBlockPos();
        if (!level.isClientSide && projectile.mayInteract(level, blockPos)) {
            level.playSound(null, blockPos, VDSounds.BOTTLE_BREAKS.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
            level.destroyBlock(blockPos, true);
        }
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType pathComputationType) {
        return false;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(WATERLOGGED, SERVINGS);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return shape;
    }
}