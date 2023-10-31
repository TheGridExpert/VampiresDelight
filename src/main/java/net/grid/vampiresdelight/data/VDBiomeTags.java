package net.grid.vampiresdelight.data;

import net.grid.vampiresdelight.common.tag.VDTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class VDBiomeTags extends BiomeTagsProvider {
    public VDBiomeTags(DataGenerator generatorIn, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(generatorIn, modId, existingFileHelper);
    }

    @Override
    protected void addTags() {
        registerModTags();
    }

    private void registerModTags() {
        tag(VDTags.HAS_LOST_WAGON)
                .add(Biomes.TAIGA)
                .add(Biomes.SWAMP)
                .add(Biomes.PLAINS)
                .add(Biomes.MEADOW)
                .add(Biomes.DARK_FOREST)
                .add(Biomes.SUNFLOWER_PLAINS)
                .add(Biomes.FOREST)
                .add(Biomes.FLOWER_FOREST)
                .add(Biomes.BIRCH_FOREST)
                .add(Biomes.OLD_GROWTH_BIRCH_FOREST)
                .add(Biomes.OLD_GROWTH_PINE_TAIGA)
                .add(Biomes.OLD_GROWTH_SPRUCE_TAIGA)
                .replace(false);
    }
}
