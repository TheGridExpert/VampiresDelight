package net.grid.vampiresdelight.data;

import de.teamlapen.vampirism.core.ModBlocks;
import net.grid.vampiresdelight.common.registry.VDBlocks;
import net.grid.vampiresdelight.common.tag.VDCompatibilityTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.tag.ModTags;

public class VDBlockTags extends BlockTagsProvider {
    public VDBlockTags(DataGenerator generatorIn, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(generatorIn, modId, existingFileHelper);
    }

    @Override
    protected void addTags() {
        this.registerBlockMineables();
        this.registerMinecraftTags();
        this.registerFarmersDelightTags();
        this.registerCompatibilityTags();
    }

    protected void registerBlockMineables() {
        tag(net.minecraft.tags.BlockTags.MINEABLE_WITH_AXE).add(
                VDBlocks.GARLIC_CRATE.get(),
                VDBlocks.DARK_SPRUCE_CABINET.get(),
                VDBlocks.CURSED_SPRUCE_CABINET.get());
        tag(ModTags.MINEABLE_WITH_KNIFE).add(
                VDBlocks.BLOOD_PIE.get(),
                VDBlocks.WEIRD_JELLY_BLOCK.get());
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
    }

    private void registerCompatibilityTags() {
        tag(VDCompatibilityTags.SERENE_SEASONS_AUTUMN_CROPS_BLOCK).add(
                ModBlocks.GARLIC.get());
        tag(VDCompatibilityTags.SERENE_SEASONS_SUMMER_CROPS_BLOCK).add(
                ModBlocks.GARLIC.get());
    }
}
