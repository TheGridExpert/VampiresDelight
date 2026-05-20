package net.grid.vampiresdelight.common.core;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.util.OptRegistryObject;
import net.grid.vampiresdelight.common.world.block.*;
import net.grid.vampiresdelight.common.world.item.PourableBottleItem;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.common.block.CabinetBlock;
import vectorwing.farmersdelight.common.block.StoveBlock;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import static vectorwing.farmersdelight.common.registry.ModBlocks.*;
import static de.teamlapen.vampirism.core.ModBlocks.*;
import static de.teamlapen.werewolves.core.ModBlocks.*;
import static net.grid.vampiresdelight.common.util.VDIntegrationUtils.WEREWOLVES;

public class VDBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, VampiresDelight.MODID);

    public static final RegistryObject<StoveBlock> DARK_STONE_STOVE = BLOCKS.register("dark_stone_stove", () -> new StoveBlock(copyProperties(STOVE).mapColor(MapColor.DEEPSLATE)));

    public static final RegistryObject<Block> GARLIC_CRATE = BLOCKS.register("garlic_crate", () -> new Block(copyProperties(TOMATO_CRATE)));
    public static final RegistryObject<Block> ORCHID_BAG = BLOCKS.register("orchid_bag", () -> new Block(copyProperties(RICE_BAG).mapColor(MapColor.COLOR_MAGENTA)));

    public static final RegistryObject<CabinetBlock> DARK_SPRUCE_CABINET = BLOCKS.register("dark_spruce_cabinet", () -> new CabinetBlock(copyProperties(OAK_CABINET).mapColor(MapColor.COLOR_GRAY)));
    public static final RegistryObject<CabinetBlock> CURSED_SPRUCE_CABINET = BLOCKS.register("cursed_spruce_cabinet", () -> new CabinetBlock(copyProperties(OAK_CABINET).mapColor(MapColor.CRIMSON_HYPHAE)));
    public static final OptRegistryObject<CabinetBlock> JACARANDA_CABINET = OptRegistryObject.register(BLOCKS, WEREWOLVES, "jacaranda_cabinet", () -> new CabinetBlock(copyProperties(OAK_CABINET).mapColor(MapColor.COLOR_PINK)));
    public static final OptRegistryObject<CabinetBlock> MAGIC_CABINET = OptRegistryObject.register(BLOCKS, WEREWOLVES, "magic_cabinet", () -> new CabinetBlock(copyProperties(OAK_CABINET).mapColor(MapColor.COLOR_LIGHT_BLUE)));

    public static final RegistryObject<WineShelfBlock> OAK_WINE_SHELF = BLOCKS.register("oak_wine_shelf", () -> new WineShelfBlock(copyProperties(Blocks.OAK_PLANKS)));
    public static final RegistryObject<WineShelfBlock> SPRUCE_WINE_SHELF = BLOCKS.register("spruce_wine_shelf", () -> new WineShelfBlock(copyProperties(Blocks.SPRUCE_PLANKS)));
    public static final RegistryObject<WineShelfBlock> BIRCH_WINE_SHELF = BLOCKS.register("birch_wine_shelf", () -> new WineShelfBlock(copyProperties(Blocks.BIRCH_PLANKS)));
    public static final RegistryObject<WineShelfBlock> JUNGLE_WINE_SHELF = BLOCKS.register("jungle_wine_shelf", () -> new WineShelfBlock(copyProperties(Blocks.JUNGLE_PLANKS)));
    public static final RegistryObject<WineShelfBlock> ACACIA_WINE_SHELF = BLOCKS.register("acacia_wine_shelf", () -> new WineShelfBlock(copyProperties(Blocks.ACACIA_PLANKS)));
    public static final RegistryObject<WineShelfBlock> DARK_OAK_WINE_SHELF = BLOCKS.register("dark_oak_wine_shelf", () -> new WineShelfBlock(copyProperties(Blocks.DARK_OAK_PLANKS)));
    public static final RegistryObject<WineShelfBlock> MANGROVE_WINE_SHELF = BLOCKS.register("mangrove_wine_shelf", () -> new WineShelfBlock(copyProperties(Blocks.MANGROVE_PLANKS)));
    public static final RegistryObject<WineShelfBlock> CHERRY_WINE_SHELF = BLOCKS.register("cherry_wine_shelf", () -> new WineShelfBlock(copyProperties(Blocks.CHERRY_PLANKS)));
    public static final RegistryObject<WineShelfBlock> BAMBOO_WINE_SHELF = BLOCKS.register("bamboo_wine_shelf", () -> new WineShelfBlock(copyProperties(Blocks.BAMBOO_PLANKS)));
    public static final RegistryObject<WineShelfBlock> CRIMSON_WINE_SHELF = BLOCKS.register("crimson_wine_shelf", () -> new WineShelfBlock(copyProperties(Blocks.CRIMSON_PLANKS)));
    public static final RegistryObject<WineShelfBlock> WARPED_WINE_SHELF = BLOCKS.register("warped_wine_shelf", () -> new WineShelfBlock(copyProperties(Blocks.WARPED_PLANKS)));
    public static final RegistryObject<WineShelfBlock> DARK_SPRUCE_WINE_SHELF = BLOCKS.register("dark_spruce_wine_shelf", () -> new WineShelfBlock(copyProperties(DARK_SPRUCE_PLANKS)));
    public static final RegistryObject<WineShelfBlock> CURSED_SPRUCE_WINE_SHELF = BLOCKS.register("cursed_spruce_wine_shelf", () -> new WineShelfBlock(copyProperties(CURSED_SPRUCE_PLANKS)));
    public static final OptRegistryObject<WineShelfBlock> JACARANDA_WINE_SHELF = OptRegistryObject.register(BLOCKS, WEREWOLVES, "jacaranda_wine_shelf", () -> new WineShelfBlock(copyProperties(JACARANDA_PLANKS)));
    public static final OptRegistryObject<WineShelfBlock> MAGIC_WINE_SHELF = OptRegistryObject.register(BLOCKS, WEREWOLVES, "magic_wine_shelf", () -> new WineShelfBlock(copyProperties(MAGIC_PLANKS)));

    public static final RegistryObject<BarStoolBlock> WHITE_BAR_STOOL = registerBarStool(DyeColor.WHITE);
    public static final RegistryObject<BarStoolBlock> ORANGE_BAR_STOOL = registerBarStool(DyeColor.ORANGE);
    public static final RegistryObject<BarStoolBlock> MAGENTA_BAR_STOOL = registerBarStool(DyeColor.MAGENTA);
    public static final RegistryObject<BarStoolBlock> LIGHT_BLUE_BAR_STOOL = registerBarStool(DyeColor.LIGHT_BLUE);
    public static final RegistryObject<BarStoolBlock> YELLOW_BAR_STOOL = registerBarStool(DyeColor.YELLOW);
    public static final RegistryObject<BarStoolBlock> LIME_BAR_STOOL = registerBarStool(DyeColor.LIME);
    public static final RegistryObject<BarStoolBlock> PINK_BAR_STOOL = registerBarStool(DyeColor.PINK);
    public static final RegistryObject<BarStoolBlock> GRAY_BAR_STOOL = registerBarStool(DyeColor.GRAY);
    public static final RegistryObject<BarStoolBlock> LIGHT_GRAY_BAR_STOOL = registerBarStool(DyeColor.LIGHT_GRAY);
    public static final RegistryObject<BarStoolBlock> CYAN_BAR_STOOL = registerBarStool(DyeColor.CYAN);
    public static final RegistryObject<BarStoolBlock> PURPLE_BAR_STOOL = registerBarStool(DyeColor.PURPLE);
    public static final RegistryObject<BarStoolBlock> BLUE_BAR_STOOL = registerBarStool(DyeColor.BLUE);
    public static final RegistryObject<BarStoolBlock> BROWN_BAR_STOOL = registerBarStool(DyeColor.BROWN);
    public static final RegistryObject<BarStoolBlock> GREEN_BAR_STOOL = registerBarStool(DyeColor.GREEN);
    public static final RegistryObject<BarStoolBlock> RED_BAR_STOOL = registerBarStool(DyeColor.RED);
    public static final RegistryObject<BarStoolBlock> BLACK_BAR_STOOL = registerBarStool(DyeColor.BLACK);

    public static final RegistryObject<SpiritLanternBlock> SPIRIT_LANTERN = BLOCKS.register("spirit_lantern", () -> new SpiritLanternBlock(copyProperties(Blocks.LANTERN).mapColor(MapColor.GOLD).lightLevel(state -> 12)));

    public static final RegistryObject<CursedFarmlandBlock> CURSED_FARMLAND = BLOCKS.register("cursed_farmland", () -> new CursedFarmlandBlock(copyProperties(Blocks.FARMLAND).mapColor(MapColor.TERRACOTTA_BROWN).strength(0.5F, 2.0F).sound(SoundType.GRAVEL)));
    public static final RegistryObject<BloodySoilBlock> BLOODY_SOIL = BLOCKS.register("bloody_soil", () -> new BloodySoilBlock(copyProperties(RICH_SOIL).strength(0.5f, 2.0f).sound(SoundType.GRAVEL).mapColor(MapColor.TERRACOTTA_RED).randomTicks()));
    public static final RegistryObject<BloodySoilFarmlandBlock> BLOODY_SOIL_FARMLAND = BLOCKS.register("bloody_soil_farmland", () -> new BloodySoilFarmlandBlock(copyProperties(CURSED_FARMLAND.get()).mapColor(MapColor.TERRACOTTA_RED)));
    public static final RegistryObject<HugeMushroomBlock> BLACK_MUSHROOM_BLOCK = BLOCKS.register("black_mushroom_block", () -> new HugeMushroomBlock(copyProperties(Blocks.RED_MUSHROOM_BLOCK).mapColor(MapColor.TERRACOTTA_BLACK)));
    public static final RegistryObject<HugeMushroomBlock> BLACK_MUSHROOM_STEM  = BLOCKS.register("black_mushroom_stem", () -> new HugeMushroomBlock(copyProperties(Blocks.MUSHROOM_STEM).mapColor(MapColor.TERRACOTTA_GRAY)));
    public static final RegistryObject<BlackMushroomBlock> BLACK_MUSHROOM = BLOCKS.register("black_mushroom", () -> new BlackMushroomBlock(copyProperties(Blocks.BROWN_MUSHROOM).mapColor(MapColor.TERRACOTTA_BLACK).sound(SoundType.FUNGUS), VDConfiguredFeatures.HUGE_BLACK_MUSHROOM));
    public static final RegistryObject<FlowerPotBlock> POTTED_BLACK_MUSHROOM = registerPotted("potted_black_mushroom", BLACK_MUSHROOM);

    public static final RegistryObject<FactionCakeBlock> ORCHID_CAKE = registerCakeAndCandles("orchid_cake", "orchid_candle_cake", properties -> properties.mapColor(MapColor.TERRACOTTA_MAGENTA), VDItems.ORCHID_CAKE_SLICE::get);
    public static final RegistryObject<VampirePieBlock> BLOOD_PIE = BLOCKS.register("blood_pie", () -> new VampirePieBlock(copyProperties(Blocks.CAKE), VDItems.BLOOD_PIE_SLICE::get));

    public static final RegistryObject<WildGarlicBlock> WILD_GARLIC = BLOCKS.register("wild_garlic", () -> new WildGarlicBlock(MobEffects.BLINDNESS, 8, copyProperties(Blocks.TALL_GRASS)));

    public static final RegistryObject<OrchidCropBlock> VAMPIRE_ORCHID_CROP = BLOCKS.register("vampire_orchid_crop", () -> new OrchidCropBlock(copyProperties(Blocks.WHEAT).mapColor(MapColor.TERRACOTTA_MAGENTA)));

    public static final RegistryObject<PlacedPourableBottleBlock> DANDELION_BEER_BOTTLE_PLACED = registerPlacedPourableBottle("dandelion_beer_bottle_placed", VDItems.DANDELION_BEER_BOTTLE, MapColor.TERRACOTTA_BROWN, PlacedPourableBottleBlock.SHAPE_1);
    public static final RegistryObject<PlacedPourableBottleBlock> BLOOD_WINE_BOTTLE_PLACED = registerPlacedPourableBottle("blood_wine_bottle_placed", VDItems.BLOOD_WINE_BOTTLE, MapColor.TERRACOTTA_PURPLE, PlacedPourableBottleBlock.SHAPE_1);

    public static final RegistryObject<WeirdJellyBlock> WEIRD_JELLY_BLOCK = BLOCKS.register("weird_jelly_block", () -> new WeirdJellyBlock(copyProperties(Blocks.CAKE).sound(SoundType.SLIME_BLOCK).noOcclusion().mapColor(MapColor.TERRACOTTA_MAGENTA), VDItems.WEIRD_JELLY::get, true));

    private static BlockBehaviour.Properties basicProperties() {
        return BlockBehaviour.Properties.of();
    }

    private static BlockBehaviour.Properties copyProperties(BlockBehaviour block) {
        return BlockBehaviour.Properties.copy(block);
    }

    private static BlockBehaviour.Properties copyProperties(Supplier<Block> block) {
        return copyProperties(block.get());
    }

    private static RegistryObject<BarStoolBlock> registerBarStool(DyeColor color) {
        return BLOCKS.register(color.getName() + "_bar_stool", () -> new BarStoolBlock(basicProperties().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava(), color));
    }

    private static RegistryObject<FlowerPotBlock> registerPotted(String name, RegistryObject<? extends Block> flower) {
        RegistryObject<FlowerPotBlock> block = BLOCKS.register(name, () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, flower, copyProperties(Blocks.POTTED_POPPY)));
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Objects.requireNonNull(flower.getId()), block);
        return block;
    }

    private static RegistryObject<FactionCakeBlock> registerCakeAndCandles(String cakeName, String candleCakeName, Function<BlockBehaviour.Properties, BlockBehaviour.Properties> extraProperties, Supplier<Item> cakeSlice) {
        BlockBehaviour.Properties properties = extraProperties.apply(copyProperties(Blocks.CAKE));
        RegistryObject<FactionCakeBlock> cakeBlock = BLOCKS.register(cakeName, () -> new FactionCakeBlock(properties, cakeSlice));
        FactionCandleCakeBlock.CANDLE_SUFFIXES.forEach((candleBlock, suffix) ->
            BLOCKS.register(suffix + candleCakeName, () -> new FactionCandleCakeBlock(properties, cakeBlock, candleBlock))
        );
        return cakeBlock;
    }

    private static RegistryObject<PlacedPourableBottleBlock> registerPlacedPourableBottle(String name, RegistryObject<PourableBottleItem> bottleItem, MapColor mapColor, VoxelShape shape) {
        return BLOCKS.register( name, () -> new PlacedPourableBottleBlock(copyProperties(Blocks.GLASS).mapColor(mapColor).instabreak().noOcclusion().pushReaction(PushReaction.DESTROY), bottleItem, shape));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
