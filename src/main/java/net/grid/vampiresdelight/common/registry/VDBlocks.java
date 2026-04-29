package net.grid.vampiresdelight.common.registry;

import com.mojang.datafixers.util.Pair;
import de.teamlapen.lib.lib.util.UtilLib;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.block.*;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import vectorwing.farmersdelight.common.block.*;

import java.util.List;
import java.util.function.ToIntFunction;

public class VDBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(VampiresDelight.MODID);

    public static ToIntFunction<BlockState> litBlockEmission(int lightValue) {
        return (state) -> state.getValue(BlockStateProperties.LIT) ? lightValue : 0;
    }

    // Workstations
    public static final DeferredBlock<StoveBlock> DARK_STONE_STOVE = BLOCKS.register("dark_stone_stove",
            () -> new StoveBlock(Block.Properties.of().mapColor(MapColor.DEEPSLATE).requiresCorrectToolForDrops().strength(2f, 10f).sound(SoundType.STONE).lightLevel(litBlockEmission(13))));

    // Crop Storage
    public static final DeferredBlock<Block> GARLIC_CRATE = BLOCKS.register("garlic_crate",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));

    public static final DeferredBlock<Block> ORCHID_BAG = BLOCKS.register("orchid_bag",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.WHITE_WOOL)));

    // Building
    public static final DeferredBlock<SpiritLanternBlock> SPIRIT_LANTERN = BLOCKS.register("spirit_lantern",
            () -> new SpiritLanternBlock(Block.Properties.of().mapColor(MapColor.METAL).forceSolidOn()
                    .requiresCorrectToolForDrops().strength(3.5F).sound(SoundType.LANTERN)
                    .lightLevel((state) -> 12).noOcclusion().pushReaction(PushReaction.DESTROY)));

    // Cabinets
    public static final DeferredBlock<CabinetBlock> DARK_SPRUCE_CABINET = BLOCKS.register("dark_spruce_cabinet",
            () -> new CabinetBlock(Block.Properties.ofFullCopy(Blocks.BARREL)));

    public static final DeferredBlock<CabinetBlock> CURSED_SPRUCE_CABINET = BLOCKS.register("cursed_spruce_cabinet",
            () -> new CabinetBlock(Block.Properties.ofFullCopy(Blocks.BARREL)));

    public static final DeferredBlock<CabinetBlock> JACARANDA_CABINET = BLOCKS.register("jacaranda_cabinet",
            () -> new CabinetBlock(Block.Properties.ofFullCopy(Blocks.BARREL)));

    public static final DeferredBlock<CabinetBlock> MAGIC_CABINET = BLOCKS.register("magic_cabinet",
            () -> new CabinetBlock(Block.Properties.ofFullCopy(Blocks.BARREL)));

    // Wine Shelves
    public static final DeferredBlock<WineShelfBlock> OAK_WINE_SHELF = BLOCKS.register("oak_wine_shelf",
            () -> new WineShelfBlock(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS)));

    public static final DeferredBlock<WineShelfBlock> SPRUCE_WINE_SHELF = BLOCKS.register("spruce_wine_shelf",
            () -> new WineShelfBlock(Block.Properties.ofFullCopy(Blocks.SPRUCE_PLANKS)));

    public static final DeferredBlock<WineShelfBlock> BIRCH_WINE_SHELF = BLOCKS.register("birch_wine_shelf",
            () -> new WineShelfBlock(Block.Properties.ofFullCopy(Blocks.BIRCH_PLANKS)));

    public static final DeferredBlock<WineShelfBlock> JUNGLE_WINE_SHELF = BLOCKS.register("jungle_wine_shelf",
            () -> new WineShelfBlock(Block.Properties.ofFullCopy(Blocks.JUNGLE_PLANKS)));

    public static final DeferredBlock<WineShelfBlock> ACACIA_WINE_SHELF = BLOCKS.register("acacia_wine_shelf",
            () -> new WineShelfBlock(Block.Properties.ofFullCopy(Blocks.ACACIA_PLANKS)));

    public static final DeferredBlock<WineShelfBlock> DARK_OAK_WINE_SHELF = BLOCKS.register("dark_oak_wine_shelf",
            () -> new WineShelfBlock(Block.Properties.ofFullCopy(Blocks.DARK_OAK_PLANKS)));

    public static final DeferredBlock<WineShelfBlock> MANGROVE_WINE_SHELF = BLOCKS.register("mangrove_wine_shelf",
            () -> new WineShelfBlock(Block.Properties.ofFullCopy(Blocks.MANGROVE_PLANKS)));

    public static final DeferredBlock<WineShelfBlock> CHERRY_WINE_SHELF = BLOCKS.register("cherry_wine_shelf",
            () -> new WineShelfBlock(Block.Properties.ofFullCopy(Blocks.CHERRY_PLANKS)));

    public static final DeferredBlock<WineShelfBlock> BAMBOO_WINE_SHELF = BLOCKS.register("bamboo_wine_shelf",
            () -> new WineShelfBlock(Block.Properties.ofFullCopy(Blocks.BAMBOO_PLANKS)));

    public static final DeferredBlock<WineShelfBlock> CRIMSON_WINE_SHELF = BLOCKS.register("crimson_wine_shelf",
            () -> new WineShelfBlock(Block.Properties.ofFullCopy(Blocks.CRIMSON_PLANKS)));

    public static final DeferredBlock<WineShelfBlock> WARPED_WINE_SHELF = BLOCKS.register("warped_wine_shelf",
            () -> new WineShelfBlock(Block.Properties.ofFullCopy(Blocks.WARPED_PLANKS)));

    public static final DeferredBlock<WineShelfBlock> CURSED_SPRUCE_WINE_SHELF = BLOCKS.register("cursed_spruce_wine_shelf",
            () -> new WineShelfBlock(Block.Properties.of().ignitedByLava().mapColor(MapColor.CRIMSON_HYPHAE).strength(2.0F, 3.0F).sound(SoundType.WOOD)));

    public static final DeferredBlock<WineShelfBlock> DARK_SPRUCE_WINE_SHELF = BLOCKS.register("dark_spruce_wine_shelf",
            () -> new WineShelfBlock(Block.Properties.of().ignitedByLava().mapColor(MapColor.COLOR_GRAY).strength(2.0F, 3.0F).sound(SoundType.WOOD)));

    public static final DeferredBlock<WineShelfBlock> JACARANDA_WINE_SHELF = BLOCKS.register("jacaranda_wine_shelf",
            () -> new WineShelfBlock(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS)));

    public static final DeferredBlock<WineShelfBlock> MAGIC_WINE_SHELF = BLOCKS.register("magic_wine_shelf",
            () -> new WineShelfBlock(Block.Properties.ofFullCopy(Blocks.OAK_PLANKS)));

    // Bar Stools
    public static final DeferredBlock<BarStoolBlock> WHITE_BAR_STOOL = BLOCKS.register("white_bar_stool",
            () -> new BarStoolBlock(DyeColor.WHITE));

    public static final DeferredBlock<BarStoolBlock> ORANGE_BAR_STOOL = BLOCKS.register("orange_bar_stool",
            () -> new BarStoolBlock(DyeColor.ORANGE));

    public static final DeferredBlock<BarStoolBlock> MAGENTA_BAR_STOOL = BLOCKS.register("magenta_bar_stool",
            () -> new BarStoolBlock(DyeColor.MAGENTA));

    public static final DeferredBlock<BarStoolBlock> LIGHT_BLUE_BAR_STOOL = BLOCKS.register("light_blue_bar_stool",
            () -> new BarStoolBlock(DyeColor.LIGHT_BLUE));

    public static final DeferredBlock<BarStoolBlock> YELLOW_BAR_STOOL = BLOCKS.register("yellow_bar_stool",
            () -> new BarStoolBlock(DyeColor.YELLOW));

    public static final DeferredBlock<BarStoolBlock> LIME_BAR_STOOL = BLOCKS.register("lime_bar_stool",
            () -> new BarStoolBlock(DyeColor.LIME));

    public static final DeferredBlock<BarStoolBlock> PINK_BAR_STOOL = BLOCKS.register("pink_bar_stool",
            () -> new BarStoolBlock(DyeColor.PINK));

    public static final DeferredBlock<BarStoolBlock> GRAY_BAR_STOOL = BLOCKS.register("gray_bar_stool",
            () -> new BarStoolBlock(DyeColor.GRAY));

    public static final DeferredBlock<BarStoolBlock> LIGHT_GRAY_BAR_STOOL = BLOCKS.register("light_gray_bar_stool",
            () -> new BarStoolBlock(DyeColor.LIGHT_GRAY));

    public static final DeferredBlock<BarStoolBlock> CYAN_BAR_STOOL = BLOCKS.register("cyan_bar_stool",
            () -> new BarStoolBlock(DyeColor.CYAN));

    public static final DeferredBlock<BarStoolBlock> PURPLE_BAR_STOOL = BLOCKS.register("purple_bar_stool",
            () -> new BarStoolBlock(DyeColor.PURPLE));

    public static final DeferredBlock<BarStoolBlock> BLUE_BAR_STOOL = BLOCKS.register("blue_bar_stool",
            () -> new BarStoolBlock(DyeColor.BLUE));

    public static final DeferredBlock<BarStoolBlock> BROWN_BAR_STOOL = BLOCKS.register("brown_bar_stool",
            () -> new BarStoolBlock(DyeColor.BROWN));

    public static final DeferredBlock<BarStoolBlock> GREEN_BAR_STOOL = BLOCKS.register("green_bar_stool",
            () -> new BarStoolBlock(DyeColor.GREEN));

    public static final DeferredBlock<BarStoolBlock> RED_BAR_STOOL = BLOCKS.register("red_bar_stool",
            () -> new BarStoolBlock(DyeColor.RED));

    public static final DeferredBlock<BarStoolBlock> BLACK_BAR_STOOL = BLOCKS.register("black_bar_stool",
            () -> new BarStoolBlock(DyeColor.BLACK));

    // Farming
    public static final DeferredBlock<CursedFarmlandBlock> CURSED_FARMLAND = BLOCKS.register("cursed_farmland",
            () -> new CursedFarmlandBlock(Block.Properties.ofFullCopy(Blocks.FARMLAND).strength(0.6f, 2.0f).sound(SoundType.GRAVEL).mapColor(MapColor.TERRACOTTA_BROWN)));

    public static final DeferredBlock<BloodySoilBlock> BLOODY_SOIL = BLOCKS.register("bloody_soil",
            () -> new BloodySoilBlock(Block.Properties.ofFullCopy(Blocks.DIRT).strength(0.5f, 2.0f).sound(SoundType.GRAVEL).mapColor(MapColor.TERRACOTTA_RED).randomTicks()));

    public static final DeferredBlock<BloodySoilFarmlandBlock> BLOODY_SOIL_FARMLAND = BLOCKS.register("bloody_soil_farmland",
            () -> new BloodySoilFarmlandBlock(Block.Properties.ofFullCopy(Blocks.FARMLAND).strength(0.5f, 2.0f).sound(SoundType.GRAVEL).mapColor(MapColor.TERRACOTTA_RED)));

    public static final DeferredBlock<HugeMushroomBlock> BLACK_MUSHROOM_BLOCK = BLOCKS.register("black_mushroom_block",
            () -> new HugeMushroomBlock(Block.Properties.ofFullCopy(Blocks.RED_MUSHROOM_BLOCK).mapColor(MapColor.TERRACOTTA_BLACK)));

    public static final DeferredBlock<HugeMushroomBlock> BLACK_MUSHROOM_STEM  = BLOCKS.register("black_mushroom_stem",
            () -> new HugeMushroomBlock(Block.Properties.ofFullCopy(Blocks.MUSHROOM_STEM).mapColor(MapColor.TERRACOTTA_GRAY)));

    public static final DeferredBlock<BlackMushroomBlock> BLACK_MUSHROOM = BLOCKS.register("black_mushroom",
            () -> new BlackMushroomBlock(Block.Properties.ofFullCopy(Blocks.RED_MUSHROOM).mapColor(MapColor.TERRACOTTA_BLACK).sound(SoundType.FUNGUS)));

    public static final DeferredBlock<FlowerPotBlock> POTTED_BLACK_MUSHROOM = BLOCKS.register("potted_black_mushroom",
            () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, BLACK_MUSHROOM, Block.Properties.of().noCollission().isViewBlocking(UtilLib::never).pushReaction(PushReaction.DESTROY).instabreak()));

    // Pastries
    public static final DeferredBlock<VampirePieBlock> BLOOD_PIE = BLOCKS.register("blood_pie",
            () -> new VampirePieBlock(Block.Properties.ofFullCopy(Blocks.CAKE), VDItems.BLOOD_PIE_SLICE::get));

    // Wild Crops
    public static final DeferredBlock<WildGarlicBlock> WILD_GARLIC = BLOCKS.register("wild_garlic",
            () -> new WildGarlicBlock(MobEffects.BLINDNESS, 8, Block.Properties.ofFullCopy(Blocks.TALL_GRASS)));

    // Crops
    public static final DeferredBlock<OrchidCropBlock> VAMPIRE_ORCHID_CROP = BLOCKS.register("vampire_orchid_crop",
            () -> new OrchidCropBlock(Block.Properties.ofFullCopy(Blocks.WHEAT).mapColor(MapColor.TERRACOTTA_MAGENTA).instabreak().noCollission().sound(SoundType.CROP)));

    // Placed Drinks
    public static final DeferredBlock<PlacedPourableBottleBlock> DANDELION_BEER_BOTTLE_PLACED = BLOCKS.register("dandelion_beer_bottle_placed",
            () -> new PlacedPourableBottleBlock(MapColor.TERRACOTTA_BROWN, VDItems.DANDELION_BEER_BOTTLE, PlacedPourableBottleBlock.SHAPE_1));

    public static final DeferredBlock<PlacedPourableBottleBlock> BLOOD_WINE_BOTTLE_PLACED = BLOCKS.register("blood_wine_bottle_placed",
            () -> new PlacedPourableBottleBlock(MapColor.TERRACOTTA_PURPLE, VDItems.BLOOD_WINE_BOTTLE, PlacedPourableBottleBlock.SHAPE_1));

    // Feasts
    public static final DeferredBlock<WeirdJellyBlock> WEIRD_JELLY_BLOCK = BLOCKS.register("weird_jelly_block",
            () -> new WeirdJellyBlock(Block.Properties.ofFullCopy(Blocks.CAKE).sound(SoundType.SLIME_BLOCK).noOcclusion().mapColor(MapColor.TERRACOTTA_MAGENTA), VDItems.WEIRD_JELLY::get, true));

    // Cakes
    public static final DeferredBlock<ConsumableCakeBlock> ORCHID_CAKE = BLOCKS.register("orchid_cake",
            () -> new ConsumableCakeBlock(Block.Properties.ofFullCopy(Blocks.CAKE).mapColor(MapColor.TERRACOTTA_PURPLE), VDItems.ORCHID_CAKE_SLICE::get));

    static {
        List<Pair<Block, String>> candlesAndColors = List.of(Pair.of(Blocks.CANDLE, ""), Pair.of(Blocks.WHITE_CANDLE, "white_"), Pair.of(Blocks.ORANGE_CANDLE, "orange_"), Pair.of(Blocks.MAGENTA_CANDLE, "magenta_"), Pair.of(Blocks.LIGHT_BLUE_CANDLE, "light_blue_"), Pair.of(Blocks.YELLOW_CANDLE, "yellow_"), Pair.of(Blocks.LIME_CANDLE, "lime_"), Pair.of(Blocks.PINK_CANDLE, "pink_"), Pair.of(Blocks.GRAY_CANDLE, "gray_"), Pair.of(Blocks.LIGHT_GRAY_CANDLE, "light_gray_"), Pair.of(Blocks.CYAN_CANDLE, "cyan_"), Pair.of(Blocks.PURPLE_CANDLE, "purple_"), Pair.of(Blocks.BLUE_CANDLE, "blue_"), Pair.of(Blocks.BROWN_CANDLE, "brown_"), Pair.of(Blocks.GREEN_CANDLE, "green_"), Pair.of(Blocks.RED_CANDLE, "red_"), Pair.of(Blocks.BLACK_CANDLE, "black_"));

        for (Pair<Block, String> color : candlesAndColors) {
            BLOCKS.register(color.getSecond() + "orchid_candle_cake",
                    () -> candleCakeBlock(color.getFirst(), ORCHID_CAKE.get(), MapColor.TERRACOTTA_PURPLE));
        }
    }

    public static ConsumableCandleCakeBlock candleCakeBlock(Block candleBlock, ConsumableCakeBlock cakeBlock, MapColor mapColor) {
        return new ConsumableCandleCakeBlock(Block.Properties.ofFullCopy(Blocks.CAKE)
                .mapColor(mapColor).lightLevel(litBlockEmission(3)), cakeBlock, candleBlock);
    }
}
