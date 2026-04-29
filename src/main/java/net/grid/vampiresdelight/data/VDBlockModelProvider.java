package net.grid.vampiresdelight.data;

import de.teamlapen.vampirism.REFERENCE;
import de.teamlapen.vampirism.core.ModBlocks;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.block.BarStoolBlock;
import net.grid.vampiresdelight.common.registry.VDBlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.client.model.generators.BlockModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import vectorwing.farmersdelight.FarmersDelight;

import java.util.Objects;

import static net.grid.vampiresdelight.common.utility.VDNameUtils.blockName;

public class VDBlockModelProvider extends BlockModelProvider {
    public VDBlockModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, VampiresDelight.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // Pies
        //pieBlock(VDBlocks.BLOOD_PIE.get());

        // Wine shelves
        wineShelfBlock(VDBlocks.OAK_WINE_SHELF.get(), Blocks.OAK_PLANKS);
        wineShelfBlock(VDBlocks.SPRUCE_WINE_SHELF.get(), Blocks.SPRUCE_PLANKS);
        wineShelfBlock(VDBlocks.BIRCH_WINE_SHELF.get(), Blocks. BIRCH_PLANKS);
        wineShelfBlock(VDBlocks.JUNGLE_WINE_SHELF.get(), Blocks.JUNGLE_PLANKS);
        wineShelfBlock(VDBlocks.ACACIA_WINE_SHELF.get(), Blocks.ACACIA_PLANKS);
        wineShelfBlock(VDBlocks.DARK_OAK_WINE_SHELF.get(), Blocks.DARK_OAK_PLANKS);
        wineShelfBlock(VDBlocks.MANGROVE_WINE_SHELF.get(), Blocks.MANGROVE_PLANKS);
        wineShelfBlock(VDBlocks.CHERRY_WINE_SHELF.get(), Blocks.CHERRY_PLANKS);
        wineShelfBlock(VDBlocks.BAMBOO_WINE_SHELF.get(), Blocks.BAMBOO_PLANKS);
        wineShelfBlock(VDBlocks.CRIMSON_WINE_SHELF.get(), Blocks.CRIMSON_PLANKS);
        wineShelfBlock(VDBlocks.WARPED_WINE_SHELF.get(), Blocks.WARPED_PLANKS);
        wineShelfBlock(VDBlocks.CURSED_SPRUCE_WINE_SHELF.get(), ModBlocks.CURSED_SPRUCE_PLANKS.get(), REFERENCE.MODID);
        wineShelfBlock(VDBlocks.DARK_SPRUCE_WINE_SHELF.get(), ModBlocks.DARK_SPRUCE_PLANKS.get(), REFERENCE.MODID);
        // Werewolves wine shelves have to be made manually

        // Bar Stools
        BarStoolBlock.getBarStoolBlocks().forEach(this::barStoolBlock);

        // Farmlands
        farmlandBlock(VDBlocks.CURSED_FARMLAND.get(), ModBlocks.CURSED_EARTH.get(), false, true);
        farmlandBlock(VDBlocks.BLOODY_SOIL_FARMLAND.get(), VDBlocks.BLOODY_SOIL.get(), true, false);

        // Huge black mushroom blocks
        hugeBlackMushroomBlock(VDBlocks.BLACK_MUSHROOM_BLOCK.get());
        hugeBlackMushroomBlock(VDBlocks.BLACK_MUSHROOM_STEM.get());
        hugeBlackMushroomBlockInside();
    }

    private void pieBlock(Block pieBlock) {
        String pieBlockName = blockName(pieBlock);

        pieBlockPart(pieBlockName, "");
        pieBlockPart(pieBlockName, "_slice1");
        pieBlockPart(pieBlockName, "_slice2");
        pieBlockPart(pieBlockName, "_slice3");
    }

    private void pieBlockPart(String pieBlockName, String suffix) {
        ResourceLocation topTexture = resourceBlock(pieBlockName + "_top");
        ResourceLocation innerTexture = resourceBlock(pieBlockName + "_inner");

        if (Objects.equals(suffix, ""))
            withExistingParent(pieBlockName + suffix, "farmersdelight:block/pie" + suffix)
                    .texture("particle", topTexture)
                    .texture("bottom", ResourceLocation.fromNamespaceAndPath(FarmersDelight.MODID, "block/pie_bottom"))
                    .texture("side", ResourceLocation.fromNamespaceAndPath(FarmersDelight.MODID, "block/pie_side"))
                    .texture("top", topTexture);
        else
            withExistingParent(pieBlockName + suffix, "farmersdelight:block/pie" + suffix)
                    .texture("particle", topTexture)
                    .texture("bottom", ResourceLocation.fromNamespaceAndPath(FarmersDelight.MODID, "block/pie_bottom"))
                    .texture("inner", innerTexture)
                    .texture("side", ResourceLocation.fromNamespaceAndPath(FarmersDelight.MODID, "block/pie_side"))
                    .texture("top", topTexture);
    }

    private void wineShelfBlock(Block shelfBlock, Block woodType) {
        wineShelfBlock(shelfBlock, woodType, "minecraft");
    }

    private void wineShelfBlock(Block shelfBlock, Block woodType, String nameSpace) {
        ResourceLocation shelfTexture = resourceBlock(blockName(shelfBlock));
        ResourceLocation woodTypeTexture = ResourceLocation.fromNamespaceAndPath(nameSpace, "block/" + blockName(woodType));
        String name = blockName(shelfBlock);

        // Shelf body
        withExistingParent(name, "vampiresdelight:block/template_wine_shelf")
                .texture("particle", woodTypeTexture)
                .texture("body", shelfTexture);
        // Shelf support
        withExistingParent(name + "_support", "vampiresdelight:block/template_wine_shelf_support")
                .texture("particle", woodTypeTexture)
                .texture("body", shelfTexture);
    }

    private void barStoolBlock(Block barStool) {
        ResourceLocation seatTexture = resourceBlock(blockName(barStool) + "_seat");
        String name = blockName(barStool);

        withExistingParent(name, "vampiresdelight:block/template_bar_stool")
                .texture("seat", seatTexture);
    }

    private void farmlandBlock(Block farmlandBlock, Block soilBlock, boolean needsSpecialSide, boolean vampirismSoilBlock) {
        String name = blockName(farmlandBlock);
        String soilBlockModId = vampirismSoilBlock ? REFERENCE.MODID : VampiresDelight.MODID;

        withExistingParent(name, "farmersdelight:block/template_farmland_custom")
                .texture("top", resourceBlock(blockName(farmlandBlock)))
                .texture("bottom", resourceBlock(soilBlockModId, blockName(soilBlock)))
                .texture("side", resourceBlock(soilBlockModId, blockName(soilBlock)));

        withExistingParent(name + "_moist", "farmersdelight:block/template_farmland_custom")
                .texture("top", resourceBlock(blockName(farmlandBlock) + "_moist"))
                .texture("bottom", resourceBlock(soilBlockModId, blockName(soilBlock)))
                .texture("side", resourceBlock(soilBlockModId, needsSpecialSide ? blockName(farmlandBlock) + "_moist_side" : blockName(soilBlock)));
    }

    private void hugeBlackMushroomBlock(String name, ResourceLocation texture, boolean needsInventoryVersion) {
        getBuilder(name)
                .texture("texture", texture)
                .texture("particle", texture)
                .element().from(0, 0, 0).to(16, 16, 0).face(Direction.NORTH).texture("#texture").cullface(Direction.NORTH);
        if (needsInventoryVersion)
            cubeAll(name + "_inventory", texture);
    }

    private void hugeBlackMushroomBlock(Block block) {
        hugeBlackMushroomBlock(blockName(block), resourceBlock(blockName(block)), true);
    }

    private void hugeBlackMushroomBlockInside() {
        hugeBlackMushroomBlock("black_mushroom_block_inside", resourceBlock("black_mushroom_block_inside"), false);
    }

    public ResourceLocation resourceBlock(String path) {
        return ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "block/" + path);
    }

    public ResourceLocation resourceBlock(String namespace, String path) {
        return ResourceLocation.fromNamespaceAndPath(namespace, "block/" + path);
    }
}
