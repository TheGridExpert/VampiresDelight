package net.grid.vampiresdelight.common.world.block;

import com.mojang.datafixers.util.Pair;
import de.teamlapen.lib.lib.util.UtilLib;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.world.blockentity.WineShelfBlockEntity;
import net.grid.vampiresdelight.common.tag.VDItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stats;
import net.minecraft.tags.TagKey;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("deprecation")
public class WineShelfBlock extends BaseEntityBlock {

    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty HAS_UPPER_SUPPORT = BooleanProperty.create("has_upper_support");

    public static final EnumProperty<Slot> WINE_SHELF_SLOT_0 = EnumProperty.create("slot_0", Slot.class);
    public static final EnumProperty<Slot> WINE_SHELF_SLOT_1 = EnumProperty.create("slot_1", Slot.class);
    public static final EnumProperty<Slot> WINE_SHELF_SLOT_2 = EnumProperty.create("slot_2", Slot.class);
    public static final EnumProperty<Slot> WINE_SHELF_SLOT_3 = EnumProperty.create("slot_3", Slot.class);
    public static final List<EnumProperty<Slot>> SLOT_CONTENTS = List.of(WINE_SHELF_SLOT_0, WINE_SHELF_SLOT_1, WINE_SHELF_SLOT_2, WINE_SHELF_SLOT_3);

    public static final Map<EnumProperty<Slot>, String> SLOT_NAMES = Map.of(
            WINE_SHELF_SLOT_0, "_slot_top_left",
            WINE_SHELF_SLOT_1, "_slot_top_right",
            WINE_SHELF_SLOT_2, "_slot_bottom_left",
            WINE_SHELF_SLOT_3, "_slot_bottom_right"
    );

    private static final Map<Pair<Direction, Boolean>, VoxelShape> SHAPES = makeShapes();

    public WineShelfBlock(Properties properties) {
        super(properties.strength(1.5F));
        BlockState blockstate = this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(HAS_UPPER_SUPPORT, false);
        for (EnumProperty<Slot> enumProperty : SLOT_CONTENTS) {
            blockstate = blockstate.setValue(enumProperty, Slot.EMPTY);
        }
        this.registerDefaultState(blockstate);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPES.get(Pair.of(state.getValue(FACING), state.getValue(HAS_UPPER_SUPPORT)));
    }

    public static Map<Pair<Direction, Boolean>, VoxelShape> makeShapes() {
        Map<Pair<Direction, Boolean>, VoxelShape> map = new HashMap<>();
        for (Direction direction : List.of(Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST)) {
            for (boolean hasSupport : List.of(false, true)) {
                UtilLib.RotationAmount rotation = switch (direction) {
                    case SOUTH -> UtilLib.RotationAmount.HUNDRED_EIGHTY;
                    case WEST -> UtilLib.RotationAmount.TWO_HUNDRED_SEVENTY;
                    case EAST -> UtilLib.RotationAmount.NINETY;
                    default -> null;
                };
                VoxelShape shape = rotation == null ? makeShape(hasSupport) : UtilLib.rotateShape(makeShape(hasSupport), rotation);
                map.put(Pair.of(direction, hasSupport), shape);
            }
        }
        return map;
    }

    public static VoxelShape makeShape(boolean hasSupport) {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.or(shape, Shapes.box(0, 0.5, 0, 1, 0.625, 1));
        shape = Shapes.or(shape, Shapes.box(0, 0, 0, 1, 0.125, 1));
        shape = Shapes.or(shape, Shapes.box(0.4375, 0.125, 0.875, 0.5625, 0.5, 1));

        if (hasSupport) {
            shape = Shapes.or(shape, Shapes.box(0.4375, 0.625, 0.875, 0.5625, 1, 1));
        }

        return shape;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos posAbove = context.getClickedPos().above();
        BlockState stateAbove = context.getLevel().getBlockState(posAbove);
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(HAS_UPPER_SUPPORT, stateAbove.getBlock() instanceof WineShelfBlock);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos blockPos, BlockPos facingPos) {
        return super.updateShape(state.setValue(HAS_UPPER_SUPPORT, level.getBlockState(blockPos.above()).getBlock() instanceof WineShelfBlock), facing, facingState, level, blockPos, facingPos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, HAS_UPPER_SUPPORT);
        SLOT_CONTENTS.forEach(builder::add);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (!(blockEntity instanceof WineShelfBlockEntity wineshelfblockentity)) {
            return InteractionResult.PASS;
        }

        ItemStack stack = player.getItemInHand(hand);
        OptionalInt optionalint = getHitSlot(hitResult, state);

        if (stack.is(VDItemTags.WINE_SHELF_BOTTLES)) {
            if (optionalint.isEmpty()) {
                return InteractionResult.PASS;
            }
            if (state.getValue(SLOT_CONTENTS.get(optionalint.getAsInt())) != Slot.EMPTY) {
                return InteractionResult.PASS;
            }
            addBottle(level, pos, player, wineshelfblockentity, stack, optionalint.getAsInt());
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        if (optionalint.isEmpty()) {
            return InteractionResult.PASS;
        }
        if (state.getValue(SLOT_CONTENTS.get(optionalint.getAsInt())) == Slot.EMPTY) {
            return InteractionResult.CONSUME;
        }
        removeBottle(level, pos, player, wineshelfblockentity, optionalint.getAsInt());

        return InteractionResult.sidedSuccess(level.isClientSide);
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

            return switch (direction) {
                case NORTH -> Optional.of(new Vec2((float) (1.0D - d0), (float) d1));
                case SOUTH -> Optional.of(new Vec2((float) d0, (float) d1));
                case WEST -> Optional.of(new Vec2((float) d2, (float) d1));
                case EAST -> Optional.of(new Vec2((float) (1.0D - d2), (float) d1));
                case DOWN, UP -> Optional.empty();
            };
        }
    }

    private OptionalInt getHitSlot(BlockHitResult hitResult, BlockState state) {
        return getRelativeHitCoordinatesForBlockFace(hitResult, state.getValue(FACING)).map(vec2 -> {
            int i = vec2.y >= 0.5F ? 0 : 1;
            int j = getSection(vec2.x);
            return OptionalInt.of(j + i * 2);
        }).orElseGet(OptionalInt::empty);
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
            blockEntity.setItem(slot, bottleStack.split(1));
            if (player.isCreative()) {
                bottleStack.grow(1);
            }

            level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
        }
    }

    private static void removeBottle(Level level, BlockPos pos, Player player, WineShelfBlockEntity blockEntity, int slot) {
        if (!level.isClientSide) {
            ItemStack itemstack = blockEntity.removeItem(slot, 1);
            if (!player.getInventory().add(itemstack)) {
                player.drop(itemstack, false);
            }

            level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new WineShelfBlockEntity(pos, state);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (!state.is(newState.getBlock())) {
            BlockEntity blockentity = level.getBlockEntity(pos);
            if (blockentity instanceof WineShelfBlockEntity wineshelfblockentity) {
                if (!wineshelfblockentity.isEmpty()) {
                    for (int i = 0; i < 4; ++i) {
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

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
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

    public static Slot getSlotTypeForStack(ItemStack stack) {
        for (Slot slot : Slot.values()) {
            TagKey<Item> tag = slot.tag;
            if (tag == null) continue;
            if (stack.is(tag)) {
                return slot;
            }
        }
        return stack.is(VDItemTags.WINE_SHELF_BOTTLES) ? Slot.WINE_BOTTLE : Slot.EMPTY;
    }

    public enum Slot implements StringRepresentable {
        EMPTY("empty", null, null),
        BEER_BOTTLE("beer_bottle", VDItemTags.BEER_BOTTLES, ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "block/dandelion_beer_bottle")),
        WINE_BOTTLE("wine_bottle", VDItemTags.WINE_BOTTLES, ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "block/blood_wine_bottle"));

        private final String name;
        private final @Nullable TagKey<Item> tag;
        private final @Nullable ResourceLocation model;

        Slot(String name, @Nullable TagKey<Item> tag, @Nullable ResourceLocation model) {
            this.name = name;
            this.tag = tag;
            this.model = model;
        }

        public @Nullable TagKey<Item> getTag() {
            return tag;
        }

        public @Nullable ResourceLocation getModel() {
            return model;
        }

        @Override
        public String getSerializedName() {
            return name;
        }
    }

    public static Iterable<Block> getAllShelfBlocks() {
        return ForgeRegistries.BLOCKS.getEntries().stream()
                .filter(entry -> VampiresDelight.MODID.equals(entry.getKey().location().getNamespace()) && entry.getValue() instanceof WineShelfBlock)
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }
}