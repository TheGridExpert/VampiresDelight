package net.grid.vampiresdelight.data;

import de.teamlapen.vampirism.core.ModItems;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.grid.vampiresdelight.common.tag.VDCompatibilityTags;
import net.grid.vampiresdelight.common.tag.VDForgeTags;
import net.grid.vampiresdelight.common.tag.VDTags;
import net.grid.vampiresdelight.common.utility.VDIntegrationUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
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
        this.registerMinecraftTags();
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
                .add(VDItems.BLOOD_WINE_GLASS.get())
                .add(VDItems.TRICOLOR_DANGO.get())
                .add(VDItems.ORCHID_COOKIE.get())
                .add(VDItems.BLOOD_SYRUP.get())
                .add(VDItems.ORCHID_ECLAIR.get())
                .add(VDItems.ORCHID_ICE_CREAM.get())
                .add(VDItems.MULLED_WINE_GLASS.get())
                .add(VDItems.DARK_ICE_CREAM.get())
                .add(VDItems.EYES_ON_STICK.get())
                .add(VDItems.BLOOD_SAUSAGE.get())
                .add(VDItems.BLOOD_HOT_DOG.get())
                .add(VDItems.ORCHID_CAKE.get())
                .add(VDItems.ORCHID_CAKE_SLICE.get())
                .add(VDItems.BLACK_MUSHROOM_SOUP.get())
                .add(VDItems.ORCHID_CURRY.get())
                .add(VDItems.ORCHID_CREAM_SOUP.get())
                .add(VDItems.BLACK_MUSHROOM_NOODLES.get());
        tag(VDTags.HUNTER_FOOD)
                .add(ModItems.GARLIC_BREAD.get())
                .add(VDItems.BORSCHT.get())
                .add(VDItems.GARLIC_SOUP.get())
                .add(VDItems.GRILLED_GARLIC.get())
                .add(VDItems.HARDTACK.get())
                .add(VDItems.FISH_BURGER.get())
                .add(VDItems.SNOW_WHITE_ICE_CREAM.get());
        tag(VDTags.WEREWOLF_ONLY_FOOD)
                .add(VDItems.WOLF_BERRY_COOKIE.get())
                .add(VDItems.WOLF_BERRY_ICE_CREAM.get())
                .addOptional(VDIntegrationUtils.WOLF_BERRIES);
        tag(VDTags.NOT_ROTTEN_FOOD)
                .add(VDItems.DANDELION_BEER_MUG.get())
                .add(VDItems.COOKED_BAT.get())
                .add(VDItems.COOKED_BAT_CHOPS.get())
                .add(VDItems.BAT_TACO.get());
        tag(VDTags.WINE_SHELF_BOTTLES)
                .addTag(VDTags.BEER_BOTTLES)
                .addTag(VDTags.WINE_BOTTLES);
        tag(VDTags.BEER_BOTTLES)
                .add(VDItems.DANDELION_BEER_BOTTLE.get());
        tag(VDTags.WINE_BOTTLES)
                .add(VDItems.BLOOD_WINE_BOTTLE.get());
        tag(VDTags.BLOOD_FOOD);
    }

    private void registerVampirismTags() {
        tag(de.teamlapen.vampirism.core.ModTags.Items.HEART)
                .add(VDItems.HEART_PIECES.get());
    }

    private void registerFarmersDelightTags() {
        tag(ModTags.KNIVES)
                .add(VDItems.SILVER_KNIFE.get());

        tag(ModTags.WOODEN_CABINETS)
                .add(VDItems.DARK_SPRUCE_CABINET.get())
                .add(VDItems.CURSED_SPRUCE_CABINET.get())
                .add(VDItems.JACARANDA_CABINET.get())
                .add(VDItems.MAGIC_CABINET.get());
    }

    @SuppressWarnings("unchecked")
    private void registerForgeTags() {
        tag(ForgeTags.BREAD)
                .addTag((VDForgeTags.BREAD_RICE));
        tag(VDForgeTags.BREAD_RICE)
                .add(VDItems.RICE_BREAD.get());

        tag(ForgeTags.DOUGH)
                .add(VDItems.RICE_DOUGH.get());
        tag(VDForgeTags.DOUGH_RICE)
                .add(VDItems.RICE_DOUGH.get());

        tag(VDForgeTags.COOKED_BAT).add(VDItems.COOKED_BAT.get(), VDItems.COOKED_BAT_CHOPS.get());
        tag(VDForgeTags.RAW_BAT).add(VDItems.RAW_BAT.get(), VDItems.RAW_BAT_CHOPS.get());

        tag(ForgeTags.VEGETABLES).addTags(VDForgeTags.VEGETABLES_GARLIC);
        tag(VDForgeTags.VEGETABLES_GARLIC).add(ModItems.ITEM_GARLIC.get());

        tag(ForgeTags.TOOLS_KNIVES)
                .add(VDItems.SILVER_KNIFE.get());
    }

    public void registerMinecraftTags() {
        tag(ItemTags.BOOKSHELF_BOOKS)
                .add(ModItems.VAMPIRE_BOOK.get())
                .addOptional(new ResourceLocation("guideapi_vp", "vampirism-guidebook"));
    }

    public void registerCompatibilityTags() {
        tag(VDCompatibilityTags.SILVER_TOOL)
                .add(VDItems.SILVER_KNIFE.get());
        tag(VDCompatibilityTags.WEREWOLF_FOOD)
                .addTag(VDTags.WEREWOLF_ONLY_FOOD)
                .add(VDItems.PURE_SORBET.get());

        tag(CompatibilityTags.CREATE_UPRIGHT_ON_BELT)
                .add(VDItems.DAISY_TEA.get())
                .add(VDItems.ORCHID_TEA.get())
                .add(VDItems.MULLED_WINE_GLASS.get())
                .add(VDItems.BLOOD_WINE_GLASS.get())
                .add(VDItems.BLOOD_PIE.get())
                .add(VDItems.ORCHID_CAKE.get());

        tag(CompatibilityTags.SERENE_SEASONS_AUTUMN_CROPS)
                .add(ModItems.ITEM_GARLIC.get());
        tag(CompatibilityTags.SERENE_SEASONS_SUMMER_CROPS)
                .add(ModItems.ITEM_GARLIC.get());
    }
}
