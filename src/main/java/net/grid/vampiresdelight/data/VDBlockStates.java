package net.grid.vampiresdelight.data;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.block.ConsumableCakeBlock;
import net.grid.vampiresdelight.common.block.ConsumableCandleCakeBlock;
import net.grid.vampiresdelight.common.block.DarkStoneStoveBlock;
import net.grid.vampiresdelight.common.block.VampireOrchidCropBlock;
import net.grid.vampiresdelight.common.registry.VDBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.world.level.block.Block;
import vectorwing.farmersdelight.common.block.*;

import java.util.function.Function;

public class VDBlockStates extends BlockStateProvider {
    private static final int DEFAULT_ANGLE_OFFSET = 180;
    public VDBlockStates(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, VampiresDelight.MODID, existingFileHelper);
    }

    private String blockName(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block).getPath();
    }


    public ResourceLocation resourceBlock(String path) {
        return new ResourceLocation(VampiresDelight.MODID, "block/" + path);
    }

    public ModelFile existingModel(String path) {
        return new ModelFile.ExistingModelFile(resourceBlock(path), models().existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        this.cabinetBlock(VDBlocks.DARK_SPRUCE_CABINET.get(), "dark_spruce");
        this.cabinetBlock(VDBlocks.CURSED_SPRUCE_CABINET.get(), "cursed_spruce");

        this.wildCropBlock(VDBlocks.WILD_GARLIC.get());

        this.horizontalBlock(VDBlocks.DARK_STONE_STOVE.get(), state -> {
            String name = blockName(VDBlocks.DARK_STONE_STOVE.get());
            String suffix = state.getValue(DarkStoneStoveBlock.LIT) ? "_on" : "";

            return models().orientableWithBottom(name + suffix,
                    resourceBlock(name + "_side"),
                    resourceBlock(name + "_front" + suffix),
                    resourceBlock(name + "_bottom"),
                    resourceBlock(name + "_top" + suffix));
        });

        getVariantBuilder(VDBlocks.VAMPIRE_ORCHID_CROP.get())
                .partialState().with(VampireOrchidCropBlock.AGE, 0).modelForState().modelFile(models().getExistingFile(modLoc("vampire_orchid_crop_stage0"))).addModel()
                .partialState().with(VampireOrchidCropBlock.AGE, 1).modelForState().modelFile(models().getExistingFile(modLoc("vampire_orchid_crop_stage1"))).addModel();

        this.feastBlock((FeastBlock) VDBlocks.WEIRD_JELLY_BLOCK.get());

        String orchidBag = blockName(VDBlocks.ORCHID_BAG.get());
        this.simpleBlock(VDBlocks.ORCHID_BAG.get(), models().withExistingParent(orchidBag, "cube")
                .texture("particle", resourceBlock(orchidBag + "_top"))
                .texture("down", resourceBlock(orchidBag + "_bottom"))
                .texture("up", resourceBlock(orchidBag + "_top"))
                .texture("north", resourceBlock(orchidBag + "_side_tied"))
                .texture("south", resourceBlock(orchidBag + "_side_tied"))
                .texture("east", resourceBlock(orchidBag + "_side"))
                .texture("west", resourceBlock(orchidBag + "_side")));

        ConsumableCandleCakeBlock.getAllCandleCakes().forEach(block -> this.candleCakeBlock((ConsumableCandleCakeBlock) block));
        this.cakeBlock((ConsumableCakeBlock) VDBlocks.ORCHID_CAKE.get());
    }

    public void wildCropBlock(Block block) {
        this.wildCropBlock(block, false);
    }

    public void wildCropBlock(Block block, boolean isBushCrop) {
        if (isBushCrop) {
            this.simpleBlock(block, models().singleTexture(blockName(block), resourceBlock("bush_crop"), "crop", resourceBlock(blockName(block))).renderType("cutout"));
        } else {
            this.simpleBlock(block, models().cross(blockName(block), resourceBlock(blockName(block))).renderType("cutout"));
        }
    }

    public void cabinetBlock(Block block, String woodType) {
        this.horizontalBlock(block, state -> {
            String suffix = state.getValue(CabinetBlock.OPEN) ? "_open" : "";
            return models().orientable(blockName(block) + suffix,
                    resourceBlock(woodType + "_cabinet_side"),
                    resourceBlock(woodType + "_cabinet_front" + suffix),
                    resourceBlock(woodType + "_cabinet_top"));
        });
    }

    public void feastBlock(FeastBlock block) {
        getVariantBuilder(block)
                .forAllStates(state -> {
                    IntegerProperty servingsProperty = block.getServingsProperty();
                    int servings = state.getValue(servingsProperty);

                    String suffix = "_stage" + (block.getMaxServings() - servings);

                    if (servings == 0) {
                        suffix = block.hasLeftovers ? "_leftover" : "_stage" + (servingsProperty.getPossibleValues().toArray().length - 2);
                    }

                    return ConfiguredModel.builder()
                            .modelFile(existingModel(blockName(block) + suffix))
                            .rotationY(((int) state.getValue(FeastBlock.FACING).toYRot() + DEFAULT_ANGLE_OFFSET) % 360)
                            .build();
                });
    }

    private void cakeBlock(ConsumableCakeBlock block) {
        getVariantBuilder(block)
                .forAllStates(state -> {
                            int bites = state.getValue(ConsumableCakeBlock.BITES);
                            String suffix = bites > 0 ? "_slice" + bites : "";
                            return ConfiguredModel.builder()
                                    .modelFile(existingModel(blockName(block) + suffix))
                                    .build();
                        }
                );
    }

    // Credits to the Neapolitan mod for candle cake code generation.
    public void candleCakeBlock(ConsumableCandleCakeBlock block) {
        Block candle = block.getCandleBlock();
        Block cake = block.getCakeBlock().get();

        ModelFile candleCake = models().withExistingParent(blockName(block), "block/template_cake_with_candle")
                .texture("candle", blockTexture(candle))
                .texture("bottom", withSuffix(blockTexture(cake), "_bottom"))
                .texture("side", withSuffix(blockTexture(cake), "_side"))
                .texture("top", withSuffix(blockTexture(cake), "_top"))
                .texture("particle", withSuffix(blockTexture(cake), "_side"));

        ModelFile candleCakeLit = models().withExistingParent(blockName(block) + "_lit", "block/template_cake_with_candle")
                .texture("candle", withSuffix(blockTexture(candle), "_lit"))
                .texture("bottom", withSuffix(blockTexture(cake), "_bottom"))
                .texture("side", withSuffix(blockTexture(cake), "_side"))
                .texture("top", withSuffix(blockTexture(cake), "_top"))
                .texture("particle", withSuffix(blockTexture(cake), "_side"));

        Function<BlockState, ModelFile> function = blockState -> blockState.getValue(BlockStateProperties.LIT) ? candleCakeLit : candleCake;

        this.getVariantBuilder(block).forAllStates(state -> ConfiguredModel.builder().modelFile(function.apply(state)).build());
    }

    private ResourceLocation withSuffix(ResourceLocation resourceLocation, String suffix) {
        return new ResourceLocation(resourceLocation.getNamespace(), resourceLocation.getPath() + suffix);
    }
}
