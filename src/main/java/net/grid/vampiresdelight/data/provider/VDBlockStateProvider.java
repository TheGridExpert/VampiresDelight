package net.grid.vampiresdelight.data.provider;

import de.teamlapen.vampirism.core.ModBlocks;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.core.VDBlocks;
import net.grid.vampiresdelight.common.world.block.BarStoolBlock;
import net.grid.vampiresdelight.common.world.block.FactionCakeBlock;
import net.grid.vampiresdelight.common.world.block.FactionCandleCakeBlock;
import net.grid.vampiresdelight.common.world.block.OrchidCropBlock;
import net.grid.vampiresdelight.common.world.block.WineShelfBlock;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import vectorwing.farmersdelight.FarmersDelight;
import vectorwing.farmersdelight.common.block.*;

import javax.annotation.Nullable;

public class VDBlockStateProvider extends BlockStateProvider {

    public VDBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, VampiresDelight.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        stoveBlock(VDBlocks.DARK_STONE_STOVE.get());
        crateBlock(VDBlocks.GARLIC_CRATE.get(), "garlic");
        bagBlock(VDBlocks.ORCHID_BAG.get());
        cabinetBlock(VDBlocks.DARK_SPRUCE_CABINET.get(), "dark_spruce");
        cabinetBlock(VDBlocks.CURSED_SPRUCE_CABINET.get(), "cursed_spruce");
        cabinetBlock(VDBlocks.JACARANDA_CABINET.getOrThrow(), "jacaranda");
        cabinetBlock(VDBlocks.MAGIC_CABINET.getOrThrow(), "magic");
        WineShelfBlock.getAllShelfBlocks().forEach(this::wineShelfBlock);
        BarStoolBlock.getBarStoolBlocks().forEach(this::barStoolBlock);
        spiritLanternBlock(VDBlocks.SPIRIT_LANTERN.get());
        farmlandBlock(VDBlocks.CURSED_FARMLAND.get(), ModBlocks.CURSED_EARTH.get());
        simpleBlock(VDBlocks.BLOODY_SOIL.get(), models().cubeAll(blockName(VDBlocks.BLOODY_SOIL.get()), resourceVDBlock(blockName(VDBlocks.BLOODY_SOIL.get()))));
        farmlandBlock(VDBlocks.BLOODY_SOIL_FARMLAND.get(), VDBlocks.BLOODY_SOIL.get());
        hugeMushroomBlock(VDBlocks.BLACK_MUSHROOM_BLOCK.get());
        hugeMushroomBlock(VDBlocks.BLACK_MUSHROOM_STEM.get());
        crossBlock(VDBlocks.BLACK_MUSHROOM.get());
        pottedFlowerBlock(VDBlocks.POTTED_BLACK_MUSHROOM.get(), VDBlocks.BLACK_MUSHROOM.get());
        cakeBlock(VDBlocks.ORCHID_CAKE.get());
        FactionCandleCakeBlock.getAllCandleCakes().forEach(this::candleCakeBlock);
        pieBlock(VDBlocks.BLOOD_PIE.get());
        crossBlock(VDBlocks.WILD_GARLIC.get());
        customStageBlock(VDBlocks.VAMPIRE_ORCHID_CROP.get(), resourceFDBlock("template_crop_cross"), "cross", OrchidCropBlock.AGE, Arrays.asList(0, 0, 1, 1, 2, 3));
        placedPourableBottle(VDBlocks.DANDELION_BEER_BOTTLE_PLACED.get(), "dandelion_beer_bottle");
        placedPourableBottle(VDBlocks.BLOOD_WINE_BOTTLE_PLACED.get(), "blood_wine_bottle");
        feastBlock(VDBlocks.WEIRD_JELLY_BLOCK.get());
    }

    public void stoveBlock(Block block) {
        horizontalBlock(block, state -> {
            String name = blockName(block);
            String suffix = state.getValue(StoveBlock.LIT) ? "_on" : "";
            return models().orientableWithBottom(name + suffix, resourceVDBlock(name + "_side"), resourceVDBlock(name + "_front" + suffix), this.resourceVDBlock(name + "_bottom"), this.resourceVDBlock(name + "_top" + suffix));
        });
    }

    public void crateBlock(Block block, String cropName) {
        simpleBlock(block, models().cubeBottomTop(blockName(block), resourceVDBlock(cropName + "_crate_side"), resourceFDBlock("crate_bottom"), resourceVDBlock(cropName + "_crate_top")));
    }

    public void bagBlock(Block block) {
        String name = blockName(block);
        simpleBlock(block, models().withExistingParent(name, "cube")
                .texture("particle", resourceVDBlock(name + "_top"))
                .texture("down", resourceVDBlock(name + "_bottom"))
                .texture("up", resourceVDBlock(name + "_top"))
                .texture("north", resourceVDBlock(name + "_side_tied"))
                .texture("south", resourceVDBlock(name + "_side_tied"))
                .texture("east", resourceVDBlock(name + "_side"))
                .texture("west", resourceVDBlock(name + "_side"))
        );
    }

    public void cabinetBlock(Block block, String woodType) {
        this.horizontalBlock(block, state -> {
            String suffix = state.getValue(CabinetBlock.OPEN) ? "_open" : "";
            return models().orientable(blockName(block) + suffix, resourceVDBlock(woodType + "_cabinet_side"), resourceVDBlock(woodType + "_cabinet_front" + suffix), resourceVDBlock(woodType + "_cabinet_top"));
        });
    }

    public void wineShelfBlock(Block block) {
        String name = blockName(block);
        ModelFile baseModel = models().withExistingParent(name, resourceVDBlock("template_wine_shelf")).texture("shelf", resourceVDBlock(name));
        ModelFile supportModel = models().withExistingParent(name + "_support", resourceVDBlock("template_wine_shelf_support")).texture("shelf", resourceVDBlock(name));

        MultiPartBlockStateBuilder builder = getMultipartBuilder(block);
        for (Direction direction : new Direction[] {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST}) {
            int rotation = (direction.get2DDataValue() + 2) % 4 * 90;

            builder.part().modelFile(baseModel).rotationY(rotation).addModel()
                    .condition(WineShelfBlock.FACING, direction).end();
            builder.part().modelFile(supportModel).rotationY(rotation).addModel()
                    .condition(WineShelfBlock.FACING, direction)
                    .condition(WineShelfBlock.HAS_UPPER_SUPPORT, true).end();
        }
    }

    public void barStoolBlock(Block block) {
        String name = blockName(block);
        simpleBlock(block, models().withExistingParent(name, resourceVDBlock("template_bar_stool"))
                .texture("seat", resourceVDBlock(name + "_seat"))
        );
    }

    public void spiritLanternBlock(Block block) {
        String name = blockName(block);
        ModelFile standing = models().getExistingFile(resourceVDBlock(name));
        ModelFile hanging = models().getExistingFile(resourceVDBlock(name + "_hanging"));

        MultiPartBlockStateBuilder builder = getMultipartBuilder(block);
        for (Direction direction : new Direction[] {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST}) {
            int yRot = switch (direction) {
                case EAST -> 90;
                case SOUTH -> 180;
                case WEST -> 270;
                default -> 0;
            };
            builder.part().modelFile(standing).rotationY(yRot).addModel()
                    .condition(BlockStateProperties.HANGING, false)
                    .condition(BlockStateProperties.HORIZONTAL_FACING, direction).end();
            builder.part().modelFile(hanging).rotationY(yRot).addModel()
                    .condition(BlockStateProperties.HANGING, true)
                    .condition(BlockStateProperties.HORIZONTAL_FACING, direction).end();
        }
    }

    public void farmlandBlock(Block farmlandBlock, Block dirtBlock) {
        this.getVariantBuilder(farmlandBlock).forAllStates((state) -> {
            boolean moist = state.getValue(RichSoilFarmlandBlock.MOISTURE) == 7;
            String suffix = moist ? "_moist" : "";
            String farmlandName = blockName(farmlandBlock);
            ResourceLocation dirtLoc = resourceBlock(blockLoc(dirtBlock));
            return ConfiguredModel.builder().modelFile(models()
                    .withExistingParent(farmlandName + suffix, resourceFDBlock("template_farmland_custom"))
                    .texture("bottom", dirtLoc)
                    .texture("side", moist ? resourceVDBlock(farmlandName + suffix + "_side") : dirtLoc)
                    .texture("top", resourceVDBlock(farmlandName + suffix))
            ).build();
        });
    }

    public void crossBlock(Block block) {
        String name = blockName(block);
        simpleBlock(block, models().cross(name, resourceVDBlock(name)).renderType("cutout"));
    }

    public void pottedFlowerBlock(Block block, Block plantBlock) {
        simpleBlock(block, models().withExistingParent(blockName(block), resourceMCBlock("flower_pot_cross")).texture("plant", resourceVDBlock(blockName(plantBlock))).renderType("cutout"));
    }

    public void cakeBlock(Block block) {
        String name = blockName(block);
        getVariantBuilder(block).forAllStates(state -> {
            int bites = state.getValue(FactionCakeBlock.BITES);
            String suffix = bites > 0 ? "_slice" + bites : "";
            BlockModelBuilder model = models().withExistingParent(name + suffix, resourceMCBlock("cake" + suffix))
                    .texture("particle", resourceVDBlock(name) + "_side")
                    .texture("bottom", resourceVDBlock(name) + "_bottom")
                    .texture("top", resourceVDBlock(name) + "_top")
                    .texture("side", resourceVDBlock(name) + "_side");
            if (bites > 0) {
                model = model.texture("inside", resourceVDBlock(name) + "_inner");
            }
            return ConfiguredModel.builder().modelFile(model).build();
        });
    }

    public void candleCakeBlock(FactionCandleCakeBlock block) {
        Block candle = block.getCandleBlock();
        Block cake = block.getCakeBlock().get();

        ModelFile candleCake = models().withExistingParent(blockName(block), "block/template_cake_with_candle")
                .texture("candle", blockTexture(candle))
                .texture("bottom", blockTexture(cake).withSuffix("_bottom"))
                .texture("side", blockTexture(cake).withSuffix("_side"))
                .texture("top", blockTexture(cake).withSuffix("_top"))
                .texture("particle", blockTexture(cake).withSuffix("_side"));

        ModelFile candleCakeLit = models().withExistingParent(blockName(block) + "_lit", "block/template_cake_with_candle")
                .texture("candle", blockTexture(candle).withSuffix("_lit"))
                .texture("bottom", blockTexture(cake).withSuffix("_bottom"))
                .texture("side", blockTexture(cake).withSuffix("_side"))
                .texture("top", blockTexture(cake).withSuffix("_top"))
                .texture("particle", blockTexture(cake).withSuffix("_side"));

        getVariantBuilder(block).forAllStates(state -> ConfiguredModel.builder().modelFile(state.getValue(BlockStateProperties.LIT) ? candleCakeLit : candleCake).build());
    }

    public void pieBlock(Block block) {
        getVariantBuilder(block).forAllStates(state -> {
            int bites = state.getValue(PieBlock.BITES);
            return ConfiguredModel.builder().modelFile(bites > 0 ? modelPieSlice(blockName(block), bites) : modelPie(blockName(block))).rotationY(((int) state.getValue(PieBlock.FACING).toYRot() + 180) % 360).build();
        });
    }

    private ModelFile modelPie(String baseName) {
        return models().withExistingParent(baseName, resourceFDBlock("template_pie"))
                .texture("bottom", resourceFDBlock("pie_bottom"))
                .texture("side", resourceFDBlock("pie_side"))
                .texture("top", resourceVDBlock(baseName + "_top"));
    }

    private ModelFile modelPieSlice(String baseName, int bites) {
        return models().withExistingParent(baseName + "_slice" + bites, resourceFDBlock("template_pie_slice" + bites))
                .texture("bottom", resourceFDBlock("pie_bottom"))
                .texture("side", resourceFDBlock("pie_side"))
                .texture("inner", resourceVDBlock(baseName + "_inner"))
                .texture("top", resourceVDBlock(baseName + "_top"));
    }

    public void hugeMushroomBlock(Block block) {
        String name = blockName(block);
        ModelFile face = models().withExistingParent(name, resourceMCBlock("template_single_face")).texture("texture", resourceVDBlock(name));
        ModelFile inside = models().withExistingParent("black_mushroom_block_inside", resourceMCBlock("template_single_face")).texture("texture", resourceVDBlock("black_mushroom_block_inside"));
        models().cubeAll(name + "_inventory", resourceVDBlock(name));

        for (boolean visible : new boolean[]{true, false}) {
            for (Map.Entry<Direction, BooleanProperty> entry : PipeBlock.PROPERTY_BY_DIRECTION.entrySet()) {
                int xRot = 0, yRot = 0;
                switch (entry.getKey()) {
                    case EAST -> yRot = 90;
                    case SOUTH -> yRot = 180;
                    case WEST -> yRot = 270;
                    case UP -> xRot = 270;
                    case DOWN -> xRot = 90;
                    default -> {}
                }
                getMultipartBuilder(block).part()
                        .modelFile(visible ? face : inside).rotationX(xRot).rotationY(yRot).uvLock(visible).addModel()
                        .condition(entry.getValue(), visible).end();
            }
        }
    }

    public void customStageBlock(Block block, @Nullable ResourceLocation parent, String textureKey, IntegerProperty ageProperty, List<Integer> suffixes, Property<?>... ignored) {
        getVariantBuilder(block).forAllStatesExcept(state -> {
            int age = state.getValue(ageProperty);
            String stageName = blockName(block) + "_stage";
            stageName = stageName + (suffixes.isEmpty() ? age : suffixes.get(Math.min(suffixes.size(), age)));
            return parent == null ? ConfiguredModel.builder().modelFile(models().cross(stageName, resourceVDBlock(stageName)).renderType("cutout")).build() : ConfiguredModel.builder().modelFile(models().singleTexture(stageName, parent, textureKey, resourceVDBlock(stageName)).renderType("cutout")).build();
        }, ignored);
    }

    public void placedPourableBottle(Block block, String name) {
        simpleBlock(block, models().withExistingParent(name, resourceVDBlock("template_bottle_placed"))
                .texture("bottle", resourceVDBlock(name))
                .texture("particle", resourceVDBlock(name))
        );
    }

    public void feastBlock(FeastBlock block) {
        getVariantBuilder(block).forAllStates((state) -> {
            IntegerProperty property = block.getServingsProperty();
            int servings = state.getValue(property);
            String suffix = "_stage" + (block.getMaxServings() - servings);
            if (servings == 0) {
                suffix = block.hasLeftovers ? "_leftovers" : "_stage" + (property.getPossibleValues().toArray().length - 2);
            }

            String name = blockName(block);
            return ConfiguredModel.builder().modelFile(models().getExistingFile(resourceVDBlock(name + suffix))).rotationY(((int) state.getValue(FeastBlock.FACING).toYRot() + 180) % 360).build();
        });
    }

    private ResourceLocation blockLoc(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block);
    }

    private String blockName(Block block) {
        return Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath();
    }

    public ResourceLocation resourceBlock(ResourceLocation location) {
        return location.withPrefix("block/");
    }

    public ResourceLocation resourceMCBlock(String path) {
        return ResourceLocation.withDefaultNamespace("block/" + path);
    }

    public ResourceLocation resourceFDBlock(String path) {
        return ResourceLocation.fromNamespaceAndPath(FarmersDelight.MODID, "block/" + path);
    }

    public ResourceLocation resourceVDBlock(String path) {
        return ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "block/" + path);
    }
}
