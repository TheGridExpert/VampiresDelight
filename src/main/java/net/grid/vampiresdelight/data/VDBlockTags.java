package net.grid.vampiresdelight.data;

import de.teamlapen.vampirism.core.ModBlocks;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.registry.VDBlocks;
import net.grid.vampiresdelight.common.tag.VDCompatibilityTags;
import net.grid.vampiresdelight.common.tag.VDTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.tag.ModTags;

import java.util.concurrent.CompletableFuture;

public class VDBlockTags extends BlockTagsProvider {
    public VDBlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, VampiresDelight.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.registerModTags();
        this.registerBlockMineables();
        this.registerMinecraftTags();
        this.registerFarmersDelightTags();
        this.registerCompatibilityTags();
    }

    private void registerModTags() {
        tag(VDTags.COOLERS).addTag(BlockTags.ICE).add(
                Blocks.WATER
        );
    }

    protected void registerBlockMineables() {
        tag(BlockTags.MINEABLE_WITH_AXE).add(
                VDBlocks.GARLIC_CRATE.get(),
                VDBlocks.DARK_SPRUCE_CABINET.get(),
                VDBlocks.CURSED_SPRUCE_CABINET.get(),
                VDBlocks.BREWING_BARREL.get());
        tag(ModTags.MINEABLE_WITH_KNIFE).add(
                VDBlocks.BLOOD_PIE.get(),
                VDBlocks.WEIRD_JELLY_BLOCK.get());
        tag(BlockTags.MINEABLE_WITH_SHOVEL).add(
                VDBlocks.CURSED_FARMLAND.get());
    }

    protected void registerMinecraftTags() {
        tag(net.minecraft.tags.BlockTags.CROPS).add(
                ModBlocks.GARLIC.get());
        tag(net.minecraft.tags.BlockTags.SMALL_FLOWERS).add(
                VDBlocks.WILD_GARLIC.get());
    }

    private void registerFarmersDelightTags() {
        tag(ModTags.WILD_CROPS).add(
                VDBlocks.WILD_GARLIC.get());
        tag(ModTags.STRAW_BLOCKS).add(
                VDBlocks.ORCHID_BAG.get());
    }

    private void registerCompatibilityTags() {
        tag(VDCompatibilityTags.SERENE_SEASONS_AUTUMN_CROPS_BLOCK).add(
                ModBlocks.GARLIC.get());
        tag(VDCompatibilityTags.SERENE_SEASONS_SUMMER_CROPS_BLOCK).add(
                ModBlocks.GARLIC.get());
    }
}
