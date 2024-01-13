package net.grid.vampiresdelight.common.block;

import de.teamlapen.lib.lib.util.UtilLib;
import net.grid.vampiresdelight.common.block.entity.WineShelfBlockEntity;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.stats.Stats;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("deprecation")
public class WineShelfBlock extends BaseEntityBlock {
    public static final BooleanProperty HAS_UPPER_SUPPORT = BooleanProperty.create("has_upper_support");
    public static final BooleanProperty WINE_SHELF_SLOT_0_OCCUPIED = BooleanProperty.create("slot_0_occupied");
    public static final BooleanProperty WINE_SHELF_SLOT_1_OCCUPIED = BooleanProperty.create("slot_1_occupied");
    public static final BooleanProperty WINE_SHELF_SLOT_2_OCCUPIED = BooleanProperty.create("slot_2_occupied");
    public static final BooleanProperty WINE_SHELF_SLOT_3_OCCUPIED = BooleanProperty.create("slot_3_occupied");
    public static final List<BooleanProperty> SLOT_OCCUPIED_PROPERTIES = List.of(WINE_SHELF_SLOT_0_OCCUPIED, WINE_SHELF_SLOT_1_OCCUPIED, WINE_SHELF_SLOT_2_OCCUPIED, WINE_SHELF_SLOT_3_OCCUPIED);

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        VoxelShape shape;

        switch (state.getValue(HorizontalDirectionalBlock.FACING)) {
            case EAST -> shape = UtilLib.rotateShape(makeShape(state.getValue(HAS_UPPER_SUPPORT), state), UtilLib.RotationAmount.NINETY);
            case SOUTH -> shape = UtilLib.rotateShape(makeShape(state.getValue(HAS_UPPER_SUPPORT), state), UtilLib.RotationAmount.HUNDRED_EIGHTY);
            case WEST -> shape = UtilLib.rotateShape(makeShape(state.getValue(HAS_UPPER_SUPPORT), state), UtilLib.RotationAmount.TWO_HUNDRED_SEVENTY);
            default -> shape = makeShape(state.getValue(HAS_UPPER_SUPPORT), state);
        }

        return shape;
    }

    public WineShelfBlock(Properties properties) {
        super(properties);

        BlockState blockstate = this.stateDefinition.any()
                .setValue(HorizontalDirectionalBlock.FACING, Direction.NORTH)
                .setValue(HAS_UPPER_SUPPORT, false);

        for (BooleanProperty booleanproperty : SLOT_OCCUPIED_PROPERTIES) {
            blockstate = blockstate.setValue(booleanproperty, Boolean.valueOf(false));
        }

        this.registerDefaultState(blockstate);
    }

    @Override
    public @NotNull RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockGetter world = context.getLevel();
        BlockPos posAbove = context.getClickedPos().above();
        return this.defaultBlockState()
                .setValue(HorizontalDirectionalBlock.FACING, context.getHorizontalDirection().getOpposite())
                .setValue(HAS_UPPER_SUPPORT, world.getBlockState(posAbove).getBlock() instanceof WineShelfBlock);
    }

    @Override
    public @NotNull BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos blockPos, BlockPos facingPos) {
        return super.updateShape(state.setValue(HAS_UPPER_SUPPORT, level.getBlockState(blockPos.above()).getBlock() instanceof WineShelfBlock), facing, facingState, level, blockPos, facingPos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(HorizontalDirectionalBlock.FACING, HAS_UPPER_SUPPORT);
        SLOT_OCCUPIED_PROPERTIES.forEach(builder::add);
    }

    @Override
    public InteractionResult use(BlockState state, Level pLevel, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        BlockEntity blockEntity = pLevel.getBlockEntity(pos);
        if (blockEntity instanceof WineShelfBlockEntity wineshelfblockentity) {
            Optional<Vec2> optional = getRelativeHitCoordinatesForBlockFace(hitResult, state.getValue(HorizontalDirectionalBlock.FACING));
            if (optional.isEmpty()) {
                return InteractionResult.PASS;
            } else {
                int i = getHitSlot(optional.get());
                if (state.getValue(SLOT_OCCUPIED_PROPERTIES.get(i))) {
                    removeBottle(pLevel, pos, player, wineshelfblockentity, i);
                    return InteractionResult.sidedSuccess(pLevel.isClientSide);
                } else {
                    ItemStack itemstack = player.getItemInHand(hand);
                    if (itemstack.is(VDItems.BLOOD_WINE_BOTTLE.get())) {
                        addBottle(pLevel, pos, player, wineshelfblockentity, itemstack, i);
                        return InteractionResult.sidedSuccess(pLevel.isClientSide);
                    } else {
                        return InteractionResult.CONSUME;
                    }
                }
            }
        } else {
            return InteractionResult.PASS;
        }
    }

    private static Optional<Vec2> getRelativeHitCoordinatesForBlockFace(BlockHitResult hitResult, Direction blocFace) {
        Direction direction = hitResult.getDirection();
        if (blocFace != direction) {
            return Optional.empty();
        } else {
            BlockPos blockpos = hitResult.getBlockPos().relative(direction);
            Vec3 vec3 = hitResult.getLocation().subtract(blockpos.getX(), blockpos.getY(), blockpos.getZ());
            double d0 = vec3.x();
            double d1 = vec3.y();
            double d2 = vec3.z();
            Optional optional;
            switch (direction) {
                case NORTH:
                    optional = Optional.of(new Vec2((float) (1.0D - d0), (float) d1));
                    break;
                case SOUTH:
                    optional = Optional.of(new Vec2((float) d0, (float) d1));
                    break;
                case WEST:
                    optional = Optional.of(new Vec2((float) d2, (float) d1));
                    break;
                case EAST:
                    optional = Optional.of(new Vec2((float) (1.0D - d2), (float) d1));
                    break;
                case DOWN:
                case UP:
                    optional = Optional.empty();
                    break;
                default:
                    throw new IncompatibleClassChangeError();
            }

            return optional;
        }
    }

    private static int getHitSlot(Vec2 hitPos) {
        int i = hitPos.y >= 0.5F ? 0 : 1;
        int j = getSection(hitPos.x);
        return j + i * 2;
    }

    private static int getSection(float pX) {
        float f = 0.5F;
        if (pX < f) {
            return 0;
        } else {
            return 1;
        }
    }

    private static void addBottle(Level level, BlockPos pos, Player player, WineShelfBlockEntity blockEntity, ItemStack bottleStack, int slot) {
        if (!level.isClientSide) {
            player.awardStat(Stats.ITEM_USED.get(bottleStack.getItem()));
            //SoundEvent soundevent = pBookStack.is(Items.ENCHANTED_BOOK) ? SoundEvents.CHISELED_BOOKSHELF_INSERT_ENCHANTED : SoundEvents.CHISELED_BOOKSHELF_INSERT;
            blockEntity.setItem(slot, bottleStack.split(1));
            //pLevel.playSound((Player)null, pPos, soundevent, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (player.isCreative()) {
                bottleStack.grow(1);
            }

            level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
        }
    }

    private static void removeBottle(Level level, BlockPos pos, Player player, WineShelfBlockEntity blockEntity, int slot) {
        if (!level.isClientSide) {
            ItemStack itemstack = blockEntity.removeItem(slot, 1);
            //SoundEvent soundevent = itemstack.is(Items.ENCHANTED_BOOK) ? SoundEvents.CHISELED_BOOKSHELF_PICKUP_ENCHANTED : SoundEvents.CHISELED_BOOKSHELF_PICKUP;
            //level.playSound((Player)null, pos, soundevent, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (!player.getInventory().add(itemstack)) {
                player.drop(itemstack, false);
            }

            level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new WineShelfBlockEntity(pos, state);
    }

    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (!state.is(newState.getBlock())) {
            BlockEntity blockentity = level.getBlockEntity(pos);
            if (blockentity instanceof WineShelfBlockEntity wineshelfblockentity) {
                if (!wineshelfblockentity.isEmpty()) {
                    for(int i = 0; i < 4; ++i) {
                        ItemStack itemstack = wineshelfblockentity.getItem(i);
                        if (!itemstack.isEmpty()) {
                            Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), itemstack);
                        }
                    }

                    wineshelfblockentity.clearContent();
                    level.updateNeighbourForOutputSignal(pos, this);
                }
            }

            super.onRemove(state, level, pos, newState, movedByPiston);
        }
    }

    public static @NotNull VoxelShape makeShape(boolean has_support, BlockState state) {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.or(shape, Shapes.box(0, 0.5, 0, 1, 0.625, 1));
        shape = Shapes.or(shape, Shapes.box(0, 0, 0, 1, 0.125, 1));
        shape = Shapes.or(shape, Shapes.box(0.4375, 0.125, 0.875, 0.5625, 0.5, 1));

        if (has_support) {
            shape = Shapes.or(shape, Shapes.box(0.4375, 0.625, 0.875, 0.5625, 1, 1));
        }

        if (state.getValue(SLOT_OCCUPIED_PROPERTIES.get(0))) {
            shape = Shapes.or(shape, Shapes.box(0.59375, 0.625, 0.375, 0.90625, 0.9375, 1));
            shape = Shapes.or(shape, Shapes.box(0.6875, 0.71875, 0.125, 0.8125, 0.84375, 0.375));
            shape = Shapes.or(shape, Shapes.box(0.65625, 0.6875, 0.0625, 0.84375, 0.875, 0.125));
            shape = Shapes.or(shape, Shapes.box(0.6875, 0.71875, 0, 0.8125, 0.84375, 0.0625));
        }
        if (state.getValue(SLOT_OCCUPIED_PROPERTIES.get(1))) {
            shape = Shapes.or(shape, Shapes.box(0.09375, 0.625, 0.375, 0.40625, 0.9375, 1));
            shape = Shapes.or(shape, Shapes.box(0.1875, 0.71875, 0.125, 0.3125, 0.84375, 0.375));
            shape = Shapes.or(shape, Shapes.box(0.15625, 0.6875, 0.0625, 0.34375, 0.875, 0.125));
            shape = Shapes.or(shape, Shapes.box(0.1875, 0.71875, 0, 0.3125, 0.84375, 0.0625));
        }
        if (state.getValue(SLOT_OCCUPIED_PROPERTIES.get(2))) {
            shape = Shapes.or(shape, Shapes.box(0.59375, 0.125, 0.375, 0.90625, 0.4375, 1));
            shape = Shapes.or(shape, Shapes.box(0.6875, 0.21875, 0.125, 0.8125, 0.34375, 0.375));
            shape = Shapes.or(shape, Shapes.box(0.65625, 0.1875, 0.0625, 0.84375, 0.375, 0.125));
            shape = Shapes.or(shape, Shapes.box(0.6875, 0.21875, 0, 0.8125, 0.34375, 0.0625));
        }
        if (state.getValue(SLOT_OCCUPIED_PROPERTIES.get(3))) {
            shape = Shapes.or(shape, Shapes.box(0.09375, 0.125, 0.375, 0.40625, 0.4375, 1));
            shape = Shapes.or(shape, Shapes.box(0.1875, 0.21875, 0.125, 0.3125, 0.34375, 0.375));
            shape = Shapes.or(shape, Shapes.box(0.15625, 0.1875, 0.0625, 0.34375, 0.375, 0.125));
            shape = Shapes.or(shape, Shapes.box(0.1875, 0.21875, 0, 0.3125, 0.34375, 0.0625));
        }

        return shape;
    }

    @Override
    public @NotNull BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(HorizontalDirectionalBlock.FACING, rotation.rotate(state.getValue(HorizontalDirectionalBlock.FACING)));
    }

    @Override
    public @NotNull BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(HorizontalDirectionalBlock.FACING)));
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        if (level.isClientSide()) {
            return 0;
        } else {
            BlockEntity blockentity = level.getBlockEntity(pos);
            if (blockentity instanceof WineShelfBlockEntity wineshelfblockentity) {
                return wineshelfblockentity.getLastInteractedSlot() + 1;
            } else {
                return 0;
            }
        }
    }
}
