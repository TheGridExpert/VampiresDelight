package net.grid.vampiresdelight.common.registry;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.block.*;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CandleCakeBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.common.block.*;

import java.util.function.Supplier;
import java.util.function.ToIntFunction;

public class VDBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, VampiresDelight.MODID);

    private static ToIntFunction<BlockState> litBlockEmission(int lightValue) {
        return (state) -> state.getValue(BlockStateProperties.LIT) ? lightValue : 0;
    }

    // Workstations
    public static final RegistryObject<Block> DARK_STONE_STOVE = BLOCKS.register("dark_stone_stove",
            () -> new DarkStoneStoveBlock(BlockBehaviour.Properties.of().mapColor(MapColor.DEEPSLATE).requiresCorrectToolForDrops().strength(2f, 10f).sound(SoundType.STONE).lightLevel(litBlockEmission(13))));
    public static final RegistryObject<Block> BREWING_BARREL = BLOCKS.register("brewing_barrel",
            () -> new BrewingBarrelBlock(Block.Properties.copy(Blocks.BARREL)));

    // Crop Storage
    public static final RegistryObject<Block> GARLIC_CRATE = BLOCKS.register("garlic_crate",
            () -> new Block(Block.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> ORCHID_BAG = BLOCKS.register("orchid_bag",
            () -> new Block(Block.Properties.copy(Blocks.WHITE_WOOL)));

    // Building
    public static final RegistryObject<Block> SPIRIT_LANTERN = BLOCKS.register("spirit_lantern",
            () -> new SpiritLanternBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).forceSolidOn()
                    .requiresCorrectToolForDrops().strength(3.5F).sound(SoundType.LANTERN)
                    .lightLevel((state) -> 12).noOcclusion().pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> DARK_SPRUCE_CABINET = BLOCKS.register("dark_spruce_cabinet",
            () -> new CabinetBlock(Block.Properties.copy(Blocks.BARREL)));
    public static final RegistryObject<Block> CURSED_SPRUCE_CABINET = BLOCKS.register("cursed_spruce_cabinet",
            () -> new CabinetBlock(Block.Properties.copy(Blocks.BARREL)));
    public static final RegistryObject<Block> OAK_WINE_SHELF = BLOCKS.register("oak_wine_shelf",
            () -> new WineShelfBlock(Block.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> SPRUCE_WINE_SHELF = BLOCKS.register("spruce_wine_shelf",
            () -> new WineShelfBlock(Block.Properties.copy(Blocks.SPRUCE_PLANKS)));
    public static final RegistryObject<Block> BIRCH_WINE_SHELF = BLOCKS.register("birch_wine_shelf",
            () -> new WineShelfBlock(Block.Properties.copy(Blocks.BIRCH_PLANKS)));
    public static final RegistryObject<Block> JUNGLE_WINE_SHELF = BLOCKS.register("jungle_wine_shelf",
            () -> new WineShelfBlock(Block.Properties.copy(Blocks.JUNGLE_PLANKS)));
    public static final RegistryObject<Block> ACACIA_WINE_SHELF = BLOCKS.register("acacia_wine_shelf",
            () -> new WineShelfBlock(Block.Properties.copy(Blocks.ACACIA_PLANKS)));
    public static final RegistryObject<Block> DARK_OAK_WINE_SHELF = BLOCKS.register("dark_oak_wine_shelf",
            () -> new WineShelfBlock(Block.Properties.copy(Blocks.DARK_OAK_PLANKS)));
    public static final RegistryObject<Block> MANGROVE_WINE_SHELF = BLOCKS.register("mangrove_wine_shelf",
            () -> new WineShelfBlock(Block.Properties.copy(Blocks.MANGROVE_PLANKS)));
    public static final RegistryObject<Block> CHERRY_WINE_SHELF = BLOCKS.register("cherry_wine_shelf",
            () -> new WineShelfBlock(Block.Properties.copy(Blocks.CHERRY_PLANKS)));
    public static final RegistryObject<Block> BAMBOO_WINE_SHELF = BLOCKS.register("bamboo_wine_shelf",
            () -> new WineShelfBlock(Block.Properties.copy(Blocks.BAMBOO_PLANKS)));
    public static final RegistryObject<Block> CRIMSON_WINE_SHELF = BLOCKS.register("crimson_wine_shelf",
            () -> new WineShelfBlock(Block.Properties.copy(Blocks.CRIMSON_PLANKS)));
    public static final RegistryObject<Block> WARPED_WINE_SHELF = BLOCKS.register("warped_wine_shelf",
            () -> new WineShelfBlock(Block.Properties.copy(Blocks.WARPED_PLANKS)));
    public static final RegistryObject<Block> CURSED_SPRUCE_WINE_SHELF = BLOCKS.register("cursed_spruce_wine_shelf",
            () -> new WineShelfBlock(Block.Properties.of().ignitedByLava().mapColor(MapColor.CRIMSON_HYPHAE).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> DARK_SPRUCE_WINE_SHELF = BLOCKS.register("dark_spruce_wine_shelf",
            () -> new WineShelfBlock(Block.Properties.of().ignitedByLava().mapColor(MapColor.COLOR_GRAY).strength(2.0F, 3.0F).sound(SoundType.WOOD)));

    // Farming
    public static final RegistryObject<Block> CURSED_FARMLAND = BLOCKS.register("cursed_farmland",
            () -> new CursedFarmlandBlock(Block.Properties.copy(Blocks.FARMLAND).strength(0.5f, 2.0f).sound(SoundType.GRAVEL).mapColor(MapColor.TERRACOTTA_BROWN)));

    // Pastries
    public static final RegistryObject<Block> BLOOD_PIE = BLOCKS.register("blood_pie",
            () -> new BloodPieBlock(Block.Properties.copy(Blocks.CAKE), VDItems.BLOOD_PIE_SLICE));

    // Wild Crops
    public static final RegistryObject<Block> WILD_GARLIC = BLOCKS.register("wild_garlic",
            () -> new WildCropBlock(MobEffects.BLINDNESS, 8, Block.Properties.copy(Blocks.TALL_GRASS)));

    // Crops
    public static final RegistryObject<Block> VAMPIRE_ORCHID_CROP = BLOCKS.register("vampire_orchid_crop",
            () -> new VampireOrchidCropBlock(Block.Properties.copy(Blocks.WHEAT).mapColor(MapColor.TERRACOTTA_MAGENTA).instabreak().noCollission().sound(SoundType.CROP)));

    // Feasts
    public static final RegistryObject<Block> WEIRD_JELLY_BLOCK = BLOCKS.register("weird_jelly_block",
            () -> new WeirdJellyBlock(Block.Properties.copy(Blocks.SLIME_BLOCK), VDItems.WEIRD_JELLY, true));

    // Cakes
    public static final RegistryObject<Block> ORCHID_CAKE = BLOCKS.register("orchid_cake",
            () -> new ConsumableCakeBlock(Block.Properties.copy(Blocks.CAKE).mapColor(MapColor.TERRACOTTA_PURPLE), VDItems.BLOOD_PIE_SLICE));
    public static final RegistryObject<Block> ORCHID_CANDLE_CAKE = BLOCKS.register("orchid_candle_cake",
            () -> candleCakeBlock(Blocks.CANDLE, ORCHID_CAKE, MapColor.TERRACOTTA_PURPLE));
    public static final RegistryObject<Block> WHITE_ORCHID_CANDLE_CAKE = BLOCKS.register("white_orchid_candle_cake",
            () -> candleCakeBlock(Blocks.WHITE_CANDLE, ORCHID_CAKE, MapColor.TERRACOTTA_PURPLE));
    public static final RegistryObject<Block> ORANGE_ORCHID_CANDLE_CAKE = BLOCKS.register("orange_orchid_candle_cake",
            () -> candleCakeBlock(Blocks.ORANGE_CANDLE, ORCHID_CAKE, MapColor.TERRACOTTA_PURPLE));
    public static final RegistryObject<Block> MAGENTA_ORCHID_CANDLE_CAKE = BLOCKS.register("magenta_orchid_candle_cake",
            () -> candleCakeBlock(Blocks.MAGENTA_CANDLE, ORCHID_CAKE, MapColor.TERRACOTTA_PURPLE));
    public static final RegistryObject<Block> LIGHT_BLUE_ORCHID_CANDLE_CAKE = BLOCKS.register("light_blue_orchid_candle_cake",
            () -> candleCakeBlock(Blocks.LIGHT_BLUE_CANDLE, ORCHID_CAKE, MapColor.TERRACOTTA_PURPLE));
    public static final RegistryObject<Block> YELLOW_ORCHID_CANDLE_CAKE = BLOCKS.register("yellow_orchid_candle_cake",
            () -> candleCakeBlock(Blocks.YELLOW_CANDLE, ORCHID_CAKE, MapColor.TERRACOTTA_PURPLE));
    public static final RegistryObject<Block> LIME_ORCHID_CANDLE_CAKE = BLOCKS.register("lime_orchid_candle_cake",
            () -> candleCakeBlock(Blocks.LIME_CANDLE, ORCHID_CAKE, MapColor.TERRACOTTA_PURPLE));
    public static final RegistryObject<Block> PINK_ORCHID_CANDLE_CAKE = BLOCKS.register("pink_orchid_candle_cake",
            () -> candleCakeBlock(Blocks.PINK_CANDLE, ORCHID_CAKE, MapColor.TERRACOTTA_PURPLE));
    public static final RegistryObject<Block> GRAY_ORCHID_CANDLE_CAKE = BLOCKS.register("gray_orchid_candle_cake",
            () -> candleCakeBlock(Blocks.GRAY_CANDLE, ORCHID_CAKE, MapColor.TERRACOTTA_PURPLE));
    public static final RegistryObject<Block> LIGHT_GRAY_ORCHID_CANDLE_CAKE = BLOCKS.register("light_gray_orchid_candle_cake",
            () -> candleCakeBlock(Blocks.LIGHT_GRAY_CANDLE, ORCHID_CAKE, MapColor.TERRACOTTA_PURPLE));
    public static final RegistryObject<Block> CYAN_ORCHID_CANDLE_CAKE = BLOCKS.register("cyan_orchid_candle_cake",
            () -> candleCakeBlock(Blocks.CYAN_CANDLE, ORCHID_CAKE, MapColor.TERRACOTTA_PURPLE));
    public static final RegistryObject<Block> PURPLE_ORCHID_CANDLE_CAKE = BLOCKS.register("purple_orchid_candle_cake",
            () -> candleCakeBlock(Blocks.PURPLE_CANDLE, ORCHID_CAKE, MapColor.TERRACOTTA_PURPLE));
    public static final RegistryObject<Block> BLUE_ORCHID_CANDLE_CAKE = BLOCKS.register("blue_orchid_candle_cake",
            () -> candleCakeBlock(Blocks.BLUE_CANDLE, ORCHID_CAKE, MapColor.TERRACOTTA_PURPLE));
    public static final RegistryObject<Block> BROWN_ORCHID_CANDLE_CAKE = BLOCKS.register("brown_orchid_candle_cake",
            () -> candleCakeBlock(Blocks.BROWN_CANDLE, ORCHID_CAKE, MapColor.TERRACOTTA_PURPLE));
    public static final RegistryObject<Block> GREEN_ORCHID_CANDLE_CAKE = BLOCKS.register("green_orchid_candle_cake",
            () -> candleCakeBlock(Blocks.GREEN_CANDLE, ORCHID_CAKE, MapColor.TERRACOTTA_PURPLE));
    public static final RegistryObject<Block> RED_ORCHID_CANDLE_CAKE = BLOCKS.register("red_orchid_candle_cake",
            () -> candleCakeBlock(Blocks.RED_CANDLE, ORCHID_CAKE, MapColor.TERRACOTTA_PURPLE));
    public static final RegistryObject<Block> BLACK_ORCHID_CANDLE_CAKE = BLOCKS.register("black_orchid_candle_cake",
            () -> candleCakeBlock(Blocks.BLACK_CANDLE, ORCHID_CAKE, MapColor.TERRACOTTA_PURPLE));

    public static ConsumableCandleCakeBlock candleCakeBlock(Block candleBlock, Supplier<Block> cakeBlock, MapColor mapColor) {
        return new ConsumableCandleCakeBlock(Block.Properties.copy(Blocks.CAKE)
                .mapColor(mapColor).lightLevel(litBlockEmission(3)), cakeBlock, candleBlock);
    }
}
