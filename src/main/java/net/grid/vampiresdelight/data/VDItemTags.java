package net.grid.vampiresdelight.data;

import de.teamlapen.vampirism.core.ModItems;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.grid.vampiresdelight.common.tag.VDCompatibilityTags;
import net.grid.vampiresdelight.common.tag.VDForgeTags;
import net.grid.vampiresdelight.common.tag.VDTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.tag.*;

import java.util.concurrent.CompletableFuture;

public class VDItemTags extends ItemTagsProvider {
    public VDItemTags(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, CompletableFuture<TagsProvider.TagLookup<Block>> blockTagProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, blockTagProvider, VampiresDelight.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.registerModTags();
        this.registerVampirismTags();
        this.registerFarmersDelightTags();
        this.registerForgeTags();
        this.registerCompatibilityTags();
    }

    private void registerModTags() {
        tag(VDTags.VAMPIRE_FOOD)
                .addTag(de.teamlapen.vampirism.core.ModTags.Items.HEART)
                .add(VDItems.BLOOD_PIE.get())
                .add(VDItems.BLOOD_PIE_SLICE.get())
                .add(VDItems.CURSED_CUPCAKE.get())
                .add(VDItems.EYE_TOAST.get())
                .add(VDItems.BAGEL_SANDWICH.get())
                .add(VDItems.ORCHID_TEA.get())
                .add(VDItems.WEIRD_JELLY.get())
                .add(VDItems.WEIRD_JELLY_BLOCK.get())
                .add(VDItems.BLOOD_DOUGH.get())
                .add(VDItems.BLOOD_BAGEL.get())
                .add(VDItems.HUMAN_EYE.get())
                .add(VDItems.BLOOD_WINE_BOTTLE.get())
                .add(VDItems.WINE_GLASS.get())
                .add(VDItems.TRICOLOR_DANGO.get());
        tag(VDTags.HUNTER_FOOD)
                .add(ModItems.GARLIC_BREAD.get())
                .add(VDItems.BORSCHT.get())
                .add(VDItems.GARLIC_SOUP.get())
                .add(VDItems.GRILLED_GARLIC.get())
                .add(VDItems.HARDTACK.get());
    }

    private void registerVampirismTags() {
        tag(de.teamlapen.vampirism.core.ModTags.Items.HEART)
                .add(VDItems.HEART_PIECES.get());
    }

    private void registerFarmersDelightTags() {
        tag(ModTags.WOODEN_CABINETS)
                .add(VDItems.DARK_SPRUCE_CABINET.get())
                .add(VDItems.CURSED_SPRUCE_CABINET.get());
    }

    @SuppressWarnings("unchecked")
    private void registerForgeTags() {
        tag(ForgeTags.DOUGH).add(VDItems.RICE_DOUGH.get());
        tag(VDForgeTags.DOUGH_RICE).add(VDItems.RICE_DOUGH.get());

        tag(ForgeTags.VEGETABLES).addTags(VDForgeTags.VEGETABLES_GARLIC);
        tag(VDForgeTags.VEGETABLES_GARLIC).add(ModItems.ITEM_GARLIC.get());
    }

    public void registerCompatibilityTags() {
        tag(VDCompatibilityTags.CREATE_UPRIGHT_ON_BELT)
                .add(VDItems.ORCHID_TEA.get())
                .add(VDItems.WINE_GLASS.get())
                .add(VDItems.BLOOD_PIE.get());

        tag(VDCompatibilityTags.SERENE_SEASONS_AUTUMN_CROPS)
                .add(ModItems.ITEM_GARLIC.get());
        tag(VDCompatibilityTags.SERENE_SEASONS_SUMMER_CROPS)
                .add(ModItems.ITEM_GARLIC.get());
    }
}
