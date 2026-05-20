package net.grid.vampiresdelight.common.world.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import net.grid.vampiresdelight.VampiresDelight;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.AbstractCandleBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CakeBlock;
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
import vectorwing.farmersdelight.common.registry.ModSounds;
import vectorwing.farmersdelight.common.utility.ItemUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@SuppressWarnings("deprecation")
public class FactionCandleCakeBlock extends AbstractCandleBlock {

    public static final Map<Block, String> CANDLE_SUFFIXES = Map.ofEntries(
            Map.entry(Blocks.CANDLE, ""),
            Map.entry(Blocks.WHITE_CANDLE, "white_"),
            Map.entry(Blocks.ORANGE_CANDLE, "orange_"),
            Map.entry(Blocks.MAGENTA_CANDLE, "magenta_"),
            Map.entry(Blocks.LIGHT_BLUE_CANDLE, "light_blue_"),
            Map.entry(Blocks.YELLOW_CANDLE, "yellow_"),
            Map.entry(Blocks.LIME_CANDLE, "lime_"),
            Map.entry(Blocks.PINK_CANDLE, "pink_"),
            Map.entry(Blocks.GRAY_CANDLE, "gray_"),
            Map.entry(Blocks.LIGHT_GRAY_CANDLE, "light_gray_"),
            Map.entry(Blocks.CYAN_CANDLE, "cyan_"),
            Map.entry(Blocks.PURPLE_CANDLE, "purple_"),
            Map.entry(Blocks.BLUE_CANDLE, "blue_"),
            Map.entry(Blocks.BROWN_CANDLE, "brown_"),
            Map.entry(Blocks.GREEN_CANDLE, "green_"),
            Map.entry(Blocks.RED_CANDLE, "red_"),
            Map.entry(Blocks.BLACK_CANDLE, "black_")
    );

    public static final BooleanProperty LIT = AbstractCandleBlock.LIT;

    protected static final VoxelShape CAKE_SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D);
    protected static final VoxelShape CANDLE_SHAPE = Block.box(7.0D, 8.0D, 7.0D, 9.0D, 14.0D, 9.0D);
    protected static final VoxelShape SHAPE = Shapes.or(CAKE_SHAPE, CANDLE_SHAPE);

    private static final Map<Pair<Block, FactionCakeBlock>, FactionCandleCakeBlock> BY_CANDLE_AND_CAKE = Maps.newHashMap();

    private static final Iterable<Vec3> PARTICLE_OFFSETS = ImmutableList.of(new Vec3(0.5D, 1.0D, 0.5D));

    private final Supplier<FactionCakeBlock> cakeBlock;
    private final Block candleBlock;

    public FactionCandleCakeBlock(Properties properties, Supplier<FactionCakeBlock> cakeBlock, Block candleBlock) {
        super(properties);
        this.cakeBlock = cakeBlock;
        this.candleBlock = candleBlock;
        this.registerDefaultState(this.stateDefinition.any().setValue(LIT, Boolean.FALSE));
        BY_CANDLE_AND_CAKE.put(Pair.of(candleBlock, cakeBlock.get()), this);
    }

    @Override
    protected Iterable<Vec3> getParticleOffsets(BlockState state) {
        return PARTICLE_OFFSETS;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand);

        if (ItemUtils.isKnife(stack)) {
            return cutSlice(level, pos, state, player, stack);
        }

        if (stack.is(Items.FLINT_AND_STEEL) || stack.is(Items.FIRE_CHARGE)) {
            return InteractionResult.PASS;
        }

        if (candleHit(hit) && stack.isEmpty() && state.getValue(LIT)) {
            extinguish(player, state, level, pos);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        FactionCakeBlock cake = cakeBlock.get();
        InteractionResult result = cake.consumeBite(level, pos, cake.defaultBlockState(), player);
        if (result.consumesAction()) {
            dropResources(state, level, pos);
        }
        return result;
    }

    public InteractionResult cutSlice(Level level, BlockPos pos, BlockState state, Player player, ItemStack knife) {
        if (level.isClientSide) return InteractionResult.SUCCESS;

        FactionCakeBlock cake = cakeBlock.get();
        level.setBlock(pos, cake.defaultBlockState().setValue(CakeBlock.BITES, 1), 3);
        popResource(level, pos, new ItemStack(candleBlock));
        ItemUtils.spawnItemEntity(level, cake.getCakeSlice(), pos.getX(), pos.getY() + 0.2, pos.getZ() + 0.5, -0.05, 0, 0);

        level.playSound(null, pos, ModSounds.BLOCK_FOOD_SLICE.get(), SoundSource.PLAYERS, 0.8F, 0.8F);
        player.awardStat(Stats.ITEM_USED.get(knife.getItem()));

        return InteractionResult.SUCCESS;
    }

    private static boolean candleHit(BlockHitResult hit) {
        return hit.getLocation().y - (double) hit.getBlockPos().getY() > 0.5D;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
        return new ItemStack(cakeBlock.get());
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        return direction == Direction.DOWN && !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.below()).isSolid();
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        return CakeBlock.FULL_CAKE_SIGNAL;
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type) {
        return false;
    }

    public static BlockState byCandle(Block candleBlock, FactionCakeBlock cakeBlock) {
        return BY_CANDLE_AND_CAKE.get(Pair.of(candleBlock, cakeBlock)).defaultBlockState();
    }

    public static boolean hasCandle(Block candleBlock, FactionCakeBlock cakeBlock) {
        return BY_CANDLE_AND_CAKE.get(Pair.of(candleBlock, cakeBlock)) != null;
    }

    public Block getCandleBlock() {
        return candleBlock;
    }

    public Supplier<FactionCakeBlock> getCakeBlock() {
        return cakeBlock;
    }

    public static List<FactionCandleCakeBlock> getAllCandleCakes() {
        return ForgeRegistries.BLOCKS.getValues().stream().filter(block -> {
            ResourceLocation location = ForgeRegistries.BLOCKS.getKey(block);
            return location != null && location.getNamespace().equals(VampiresDelight.MODID) && block instanceof FactionCandleCakeBlock;
        }).map(block -> (FactionCandleCakeBlock) block).collect(Collectors.toList());
    }

    public static Map<FactionCakeBlock, List<FactionCandleCakeBlock>> getCandleCakesByCake() {
        return getAllCandleCakes().stream().collect(Collectors.groupingBy(block -> block.getCakeBlock().get()));
    }
}