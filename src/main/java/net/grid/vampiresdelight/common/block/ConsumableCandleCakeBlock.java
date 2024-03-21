package net.grid.vampiresdelight.common.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import net.grid.vampiresdelight.VampiresDelight;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Credits to the Neapolitan mod
 */
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ConsumableCandleCakeBlock extends AbstractCandleBlock {
    public static final BooleanProperty LIT = AbstractCandleBlock.LIT;
    protected static final VoxelShape CAKE_SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D);
    protected static final VoxelShape CANDLE_SHAPE = Block.box(7.0D, 8.0D, 7.0D, 9.0D, 14.0D, 9.0D);
    protected static final VoxelShape SHAPE = Shapes.or(CAKE_SHAPE, CANDLE_SHAPE);
    private static final Map<Pair<Block, ConsumableCakeBlock>, ConsumableCandleCakeBlock> BY_CANDLE_AND_CAKE = Maps.newHashMap();
    private static final Iterable<Vec3> PARTICLE_OFFSETS = ImmutableList.of(new Vec3(0.5D, 1.0D, 0.5D));

    private final Supplier<Block> cakeBlock;
    private final Block candleBlock;

    public ConsumableCandleCakeBlock(Properties properties, Supplier<Block> cakeBlock, Block candleBlock) {
        super(properties);
        this.cakeBlock = cakeBlock;
        this.candleBlock = candleBlock;
        this.registerDefaultState(this.stateDefinition.any().setValue(LIT, Boolean.FALSE));
        BY_CANDLE_AND_CAKE.put(Pair.of(candleBlock, (ConsumableCakeBlock) cakeBlock.get()), this);
    }

    @Override
    protected Iterable<Vec3> getParticleOffsets(BlockState pState) {
        return PARTICLE_OFFSETS;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (!itemstack.is(Items.FLINT_AND_STEEL) && !itemstack.is(Items.FIRE_CHARGE) && cakeBlock instanceof ConsumableCakeBlock consumableCakeBlock) {
            if (candleHit(pHit) && pPlayer.getItemInHand(pHand).isEmpty() && pState.getValue(LIT)) {
                extinguish(pPlayer, pState, pLevel, pPos);
                return InteractionResult.sidedSuccess(pLevel.isClientSide);
            } else {
                InteractionResult interactionresult = consumableCakeBlock.consumeBite(pLevel, pPos, consumableCakeBlock.defaultBlockState(), pPlayer);
                if (interactionresult.consumesAction()) {
                    dropResources(pState, pLevel, pPos);
                }

                return interactionresult;
            }
        } else {
            return InteractionResult.PASS;
        }
    }

    private static boolean candleHit(BlockHitResult pHit) {
        return pHit.getLocation().y - (double)pHit.getBlockPos().getY() > 0.5D;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(LIT);
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter pLevel, BlockPos pPos, BlockState pState) {
        return new ItemStack(cakeBlock.get());
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pPos, BlockPos pNeighborPos) {
        return pDirection == Direction.DOWN && !pState.canSurvive(pLevel, pPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pDirection, pNeighborState, pLevel, pPos, pNeighborPos);
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return pLevel.getBlockState(pPos.below()).isSolid();
    }

    @Override
    public int getAnalogOutputSignal(BlockState pState, Level pLevel, BlockPos pPos) {
        return ConsumableCakeBlock.FULL_CAKE_SIGNAL;
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState pState) {
        return true;
    }

    @Override
    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return false;
    }

    public static BlockState byCandle(Block candleBlock, ConsumableCakeBlock cakeBlock) {
        return BY_CANDLE_AND_CAKE.get(Pair.of(candleBlock, cakeBlock)).defaultBlockState();
    }

    public static boolean hasCandle(Block candleBlock, ConsumableCakeBlock cakeBlock) {
        return BY_CANDLE_AND_CAKE.get(Pair.of(candleBlock, cakeBlock)) != null;
    }

    public Block getCandleBlock() {
        return candleBlock;
    }

    public Supplier<Block> getCakeBlock() {
        return cakeBlock;
    }

    public static Iterable<Block> getAllCandleCakes() {
        return ForgeRegistries.BLOCKS.getValues().stream().filter(block -> ForgeRegistries.BLOCKS.getKey(block) != null && VampiresDelight.MODID.equals(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getNamespace()) && block instanceof ConsumableCandleCakeBlock).collect(Collectors.toList());
    }
}