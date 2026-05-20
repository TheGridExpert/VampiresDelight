package net.grid.vampiresdelight.data.provider;

import net.grid.vampiresdelight.data.provider.tag.VDBiomeTagsProvider;
import net.grid.vampiresdelight.data.provider.tag.VDBlockTagsProvider;
import net.grid.vampiresdelight.data.provider.tag.VDEntityTagsProvider;
import net.grid.vampiresdelight.data.provider.tag.VDItemTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class VDTagsProvider {

    public static void register(DataGenerator generator, PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        VDBlockTagsProvider blockTagsProvider = new VDBlockTagsProvider(output, lookupProvider, existingFileHelper);
        generator.addProvider(true, blockTagsProvider);
        generator.addProvider(true, new VDItemTagsProvider(output, lookupProvider, blockTagsProvider.contentsGetter(), existingFileHelper));
        generator.addProvider(true, new VDEntityTagsProvider(output, lookupProvider, existingFileHelper));
        generator.addProvider(true, new VDBiomeTagsProvider(output, lookupProvider, existingFileHelper));
    }
}
