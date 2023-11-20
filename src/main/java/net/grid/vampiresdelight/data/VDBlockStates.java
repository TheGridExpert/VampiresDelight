package net.grid.vampiresdelight.data;

import de.teamlapen.vampirism.blocks.GarlicBlock;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.block.VampireOrchidCropBlock;
import net.grid.vampiresdelight.common.registry.VDBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.world.level.block.Block;
import vectorwing.farmersdelight.common.block.BuddingTomatoBlock;
import vectorwing.farmersdelight.common.block.CabinetBlock;
import vectorwing.farmersdelight.common.block.FeastBlock;
import vectorwing.farmersdelight.common.registry.ModBlocks;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class VDBlockStates extends BlockStateProvider {
    private static final int DEFAULT_ANGLE_OFFSET = 180;
    public VDBlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, VampiresDelight.MODID, exFileHelper);
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
}
