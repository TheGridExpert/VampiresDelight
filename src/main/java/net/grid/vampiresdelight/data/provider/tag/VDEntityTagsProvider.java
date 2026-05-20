package net.grid.vampiresdelight.data.provider.tag;

import de.teamlapen.vampirism.core.ModEntities;
import de.teamlapen.vampirism.core.ModTags;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.tag.VDEntityTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class VDEntityTagsProvider extends EntityTypeTagsProvider {

    public VDEntityTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, VampiresDelight.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(VDEntityTags.UNHOLY_SPIRITS).add(EntityType.PHANTOM, ModEntities.GHOST.get());
        tag(VDEntityTags.DROPS_HUMAN_EYE)
                .addTag(ModTags.Entities.HUNTER)
                .addTag(EntityTypeTags.RAIDERS)
                .add(EntityType.VILLAGER)
                .add(EntityType.WANDERING_TRADER);
    }
}
