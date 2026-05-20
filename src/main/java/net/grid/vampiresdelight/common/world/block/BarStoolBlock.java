package net.grid.vampiresdelight.common.world.block;

import de.teamlapen.vampirism.sit.SitEntity;
import de.teamlapen.vampirism.sit.SitUtil;
import net.grid.vampiresdelight.VampiresDelight;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraftforge.common.ForgeMod;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings("deprecation")
public class BarStoolBlock extends Block implements SimpleWaterloggedBlock {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private static final VoxelShape SHAPE = Shapes.join(Block.box(4, 0, 4, 12, 13, 12), Block.box(3, 13, 3, 13, 16, 13), BooleanOp.OR);

    private final DyeColor color;

    public BarStoolBlock(Properties properties, DyeColor color) {
        super(properties);
        this.color = color;
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false));
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        BlockState blockAbove = level.getBlockState(pos.above());
        if (isClickedOnSeat(pos, hit) && (blockAbove.isAir() || blockAbove.is(BlockTags.BUTTONS) || blockAbove.is(BlockTags.TRAPDOORS) || blockAbove.is(Blocks.END_ROD))) {
            startSitting(player, level, pos, 0.75);
            return InteractionResult.SUCCESS;
        }

        return super.use(state, level, pos, player, hand, hit);
    }

    private static boolean isClickedOnSeat(BlockPos pos, BlockHitResult hit) {
        return hit.getLocation().y() >= pos.getY() + 0.8125;
    }

    public static void startSitting(Player player, Level level, BlockPos pos, double offset) {
        if (!level.isClientSide && !SitUtil.isPlayerSitting(player) && !player.isShiftKeyDown()) {
            if (isPlayerInRange(player, pos) && !SitUtil.isOccupied(level, pos)) {
                SitEntity sit = SitEntity.newEntity(level, pos, offset, player.position());

                if (SitUtil.addSitEntity(level, pos, sit)) {
                    level.addFreshEntity(sit);
                    player.startRiding(sit);
                }
            }
        }
    }

    private static boolean isPlayerInRange(Player player, BlockPos pos) {
        Vec3 playerPos = player.position();
        Vec3 blockPos = new Vec3(pos.getX(), pos.getY(), pos.getZ());
        AttributeInstance blockReach = player.getAttribute(ForgeMod.BLOCK_REACH.get());
        double blockReachDistance = (blockReach == null) ? 4.5D : blockReach.getValue();

        blockPos = blockPos.add(0.5D, 0.5D, 0.5D);

        AABB range = new AABB(blockPos.x() + blockReachDistance, blockPos.y() + blockReachDistance, blockPos.z() + blockReachDistance, blockPos.x() - blockReachDistance, blockPos.y() - blockReachDistance, blockPos.z() - blockReachDistance);

        playerPos = playerPos.add(0.5D, 0.5D, 0.5D);
        return range.minX <= playerPos.x() && range.minY <= playerPos.y() && range.minZ <= playerPos.z() && range.maxX >= playerPos.x() && range.maxY >= playerPos.y() && range.maxZ >= playerPos.z();
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        super.onRemove(state, level, pos, newState, movedByPiston);
        SitEntity entity = SitUtil.getSitEntity(level, pos);
        if (entity != null) {
            entity.discard();
        }
    }

    @Override
    public void updateEntityAfterFallOn(BlockGetter level, Entity entity) {
        super.updateEntityAfterFallOn(level, entity);
        if (entity.isSuppressingBounce()) {
            super.updateEntityAfterFallOn(level, entity);
        } else {
            Vec3 vec3 = entity.getDeltaMovement();
            if (vec3.y < 0.0D) {
                double d0 = entity instanceof LivingEntity ? 1.0D : 0.8D;
                entity.setDeltaMovement(vec3.x, -vec3.y * (double)0.66F * d0, vec3.z);
            }
        }
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        return SitUtil.isOccupied(level, pos) ? 15 : 0;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type) {
        return false;
    }

    @Override
    public @Nullable BlockPathTypes getAdjacentBlockPathType(BlockState state, BlockGetter level, BlockPos pos, @Nullable Mob mob, BlockPathTypes originalType) {
        return BlockPathTypes.FENCE;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    public DyeColor getColor() {
        return color;
    }

    public static Iterable<Block> getBarStoolBlocks() {
        return ForgeRegistries.BLOCKS.getEntries().stream()
                .filter(entry -> VampiresDelight.MODID.equals(entry.getKey().location().getNamespace()) && entry.getValue() instanceof BarStoolBlock)
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }
}
