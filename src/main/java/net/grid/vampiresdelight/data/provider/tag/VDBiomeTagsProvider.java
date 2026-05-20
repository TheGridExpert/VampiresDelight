package net.grid.vampiresdelight.data.provider.tag;

import de.teamlapen.vampirism.core.ModTags;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.tag.VDBiomeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class VDBiomeTagsProvider extends BiomeTagsProvider {

    public VDBiomeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, VampiresDelight.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(VDBiomeTags.WITHOUT_WILD_GARLIC).addTag(ModTags.Biomes.IS_VAMPIRE_BIOME).add(Biomes.LUSH_CAVES, Biomes.MUSHROOM_FIELDS);
    }
}
