package net.grid.vampiresdelight.data;

import de.teamlapen.vampirism.core.ModBlocks;
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
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.tag.*;

import java.util.concurrent.CompletableFuture;

public class VDItemTags extends ItemTagsProvider {
    public VDItemTags(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, CompletableFuture<TagsProvider.TagLookup<Block>> blockTagProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, blockTagProvider, VampiresDelight.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        this.registerModTags();
        this.registerVampirismTags();
        this.registerFarmersDelightTags();
        this.registerForgeTags();
        this.registerCompatibilityTags();
    }

    private void registerModTags() {
        tag(VDTags.VAMPIRE_FOOD)
                .addTag(de.teamlapen.vampirism.core.ModTags.Items.HEART)
                .add(ModItems.BLOOD_BOTTLE.get())
                .add(VDItems.BLOOD_PIE.get())
                .add(VDItems.BLOOD_PIE_SLICE.get())
                .add(VDItems.CURSED_CUPCAKE.get())
                .add(VDItems.EYE_CROISSANT.get())
                .add(VDItems.BAGEL_SANDWICH.get())
                .add(VDItems.ORCHID_TEA.get())
                .add(VDItems.WEIRD_JELLY.get())
                .add(VDItems.WEIRD_JELLY_BLOCK.get())
                .add(VDItems.BLOOD_DOUGH.get())
                .add(VDItems.BLOOD_BAGEL.get())
                .add(VDItems.HUMAN_EYE.get())
                .add(VDItems.BLOOD_WINE_BOTTLE.get())
                .add(VDItems.WINE_GLASS.get())
                .add(VDItems.TRICOLOR_DANGO.get())
                .add(VDItems.ORCHID_COOKIE.get())
                .add(VDItems.BLOOD_SYRUP.get())
                .add(VDItems.ORCHID_ECLAIR.get())
                .add(VDItems.ORCHID_ICE_CREAM.get())
                .add(VDItems.MULLED_WINE_GLASS.get())
                .add(VDItems.DARK_ICE_CREAM.get());
        tag(VDTags.HUNTER_FOOD)
                .add(ModItems.GARLIC_BREAD.get())
                .add(VDItems.BORSCHT.get())
                .add(VDItems.GARLIC_SOUP.get())
                .add(VDItems.GRILLED_GARLIC.get())
                .add(VDItems.HARDTACK.get())
                .add(VDItems.FISH_BURGER.get())
                .add(VDItems.SNOW_WHITE_ICE_CREAM.get());
        tag(VDTags.MINION_VAMPIRE_FOOD)
                .add(VDItems.ORCHID_COOKIE.get());
        tag(VDTags.BLOOD_SYRUP_INGREDIENTS)
                .add(Items.APPLE)
                .add(Items.SWEET_BERRIES)
                .add(Items.GLOW_BERRIES)
                .add(ModBlocks.CURSED_ROOTS.get().asItem())
                .add(ModBlocks.DARK_SPRUCE_LEAVES.get().asItem())
                .add(ModBlocks.DARK_SPRUCE_SAPLING.get().asItem())
                .add(ModBlocks.CURSED_SPRUCE_SAPLING.get().asItem());
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
        tag(ForgeTags.BREAD).addTag((VDForgeTags.BREAD_RICE));
        tag(VDForgeTags.BREAD_RICE).add(VDItems.RICE_BREAD.get());

        tag(ForgeTags.DOUGH).add(VDItems.RICE_DOUGH.get());
        tag(VDForgeTags.DOUGH_RICE).add(VDItems.RICE_DOUGH.get());

        tag(VDForgeTags.COOKED_BAT).add(VDItems.COOKED_BAT.get(), VDItems.COOKED_BAT_CHOPS.get());
        tag(VDForgeTags.RAW_BAT).add(VDItems.RAW_BAT.get(), VDItems.RAW_BAT_CHOPS.get());

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
