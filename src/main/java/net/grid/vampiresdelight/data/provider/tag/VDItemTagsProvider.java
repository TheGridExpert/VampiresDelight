package net.grid.vampiresdelight.data.provider.tag;

import de.teamlapen.vampirism.core.ModBlocks;
import de.teamlapen.vampirism.core.ModItems;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.core.VDItems;
import net.grid.vampiresdelight.common.tag.VDCommonTags;
import net.grid.vampiresdelight.common.tag.VDItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.tag.CompatibilityTags;
import vectorwing.farmersdelight.common.tag.ModTags;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unchecked")
public class VDItemTagsProvider extends ItemTagsProvider {

    public VDItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, VampiresDelight.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // Vampire's Delight
        tag(VDItemTags.BLOOD_FOOD);

        tag(VDItemTags.BEER_BOTTLES).add(VDItems.DANDELION_BEER_BOTTLE.get());
        tag(VDItemTags.WINE_BOTTLES).add(VDItems.BLOOD_WINE_BOTTLE.get());
        tag(VDItemTags.WINE_SHELF_BOTTLES).addTags(VDItemTags.BEER_BOTTLES, VDItemTags.WINE_BOTTLES);
        
        tag(VDItemTags.BLOOD_SYRUP_INGREDIENTS)
                .add(Items.INK_SAC)
                .add(ModBlocks.CURSED_SPRUCE_SAPLING.get().asItem())
                .add(ModBlocks.DARK_SPRUCE_SAPLING.get().asItem())
                .add(ModBlocks.DARK_SPRUCE_LEAVES.get().asItem());

        // Vanilla
        tag(net.minecraft.tags.ItemTags.BOOKSHELF_BOOKS).add(ModItems.VAMPIRE_BOOK.get());

        // Farmer's Delight
        tag(ModTags.Items.KNIVES).addOptional(VDItems.SILVER_KNIFE.idOrThrow());

        tag(ModTags.Items.CABINETS_WOODEN)
                .add(VDItems.DARK_SPRUCE_CABINET.get())
                .add(VDItems.CURSED_SPRUCE_CABINET.get())
                .addOptional(VDItems.JACARANDA_CABINET.idOrThrow())
                .addOptional(VDItems.MAGIC_CABINET.idOrThrow());

        tag(ModTags.Items.MEALS)
                .add(VDItems.ORCHID_CREAM_SOUP.get())
                .add(VDItems.BLACK_MUSHROOM_SOUP.get())
                .add(VDItems.GARLIC_SOUP.get())
                .add(VDItems.BORSCHT.get())
                .add(VDItems.ORCHID_CURRY.get())
                .add(VDItems.BLACK_MUSHROOM_NOODLES.get());

        tag(ModTags.Items.DRINKS)
                .add(VDItems.DAISY_TEA.get())
                .add(VDItems.BLOOD_SYRUP.get())
                .add(VDItems.ORCHID_TEA.get())
                .add(VDItems.DANDELION_BEER_MUG.get())
                .add(VDItems.BLOOD_WINE_GLASS.get())
                .add(VDItems.MULLED_WINE_GLASS.get());

        tag(ModTags.Items.SNACKS)
                .add(VDItems.BAGEL_SANDWICH.get())
                .add(VDItems.BLOOD_HOT_DOG.get())
                .add(VDItems.BLOOD_SAUSAGE.get())
                .add(VDItems.EYE_CROISSANT.get())
                .add(VDItems.EYES_ON_STICK.get())
                .add(VDItems.FISH_BURGER.get())
                .add(VDItems.BAT_TACO.get());

        tag(ModTags.Items.SWEETS)
                .add(VDItems.SUGARED_BERRIES.get())
                .add(VDItems.ORCHID_CAKE_SLICE.get())
                .add(VDItems.BLOOD_PIE_SLICE.get())
                .add(VDItems.PURE_SORBET.get())
                .add(VDItems.ORCHID_COOKIE.get())
                .add(VDItems.ORCHID_ECLAIR.get())
                .add(VDItems.ORCHID_ICE_CREAM.get())
                .add(VDItems.TRICOLOR_DANGO.get())
                .add(VDItems.CURSED_CUPCAKE.get())
                .add(VDItems.DARK_ICE_CREAM.get())
                .add(VDItems.SNOW_WHITE_ICE_CREAM.get())
                .addOptional(VDItems.WOLF_BERRY_COOKIE.idOrThrow())
                .addOptional(VDItems.WOLF_BERRY_ICE_CREAM.idOrThrow());

        tag(ModTags.Items.PIES).add(VDItems.BLOOD_PIE.get());

        tag(ModTags.Items.FEASTS).add(VDItems.WEIRD_JELLY_BLOCK.get());

        // Vampirism
        tag(de.teamlapen.vampirism.core.ModTags.Items.HEART).add(VDItems.HEART_PIECES.get());

        // Werewolves
        tag(de.teamlapen.werewolves.core.ModTags.Items.WEREWOLF_FOOD)
                .addOptional(VDItems.WOLF_BERRY_COOKIE.idOrThrow())
                .addOptional(VDItems.WOLF_BERRY_ICE_CREAM.idOrThrow());

        tag(de.teamlapen.werewolves.core.ModTags.Items.SILVER_TOOL).addOptional(VDItems.SILVER_KNIFE.idOrThrow());

        tag(de.teamlapen.werewolves.core.ModTags.Items.RAW_MEAT)
                .add(VDItems.RAW_BAT.get())
                .add(VDItems.RAW_BAT_CHOPS.get())
                .add(VDItems.HEART_PIECES.get());
        tag(de.teamlapen.werewolves.core.ModTags.Items.COOKED_MEAT)
                .add(VDItems.GRILLED_BAT.get())
                .add(VDItems.GRILLED_BAT_CHOPS.get());

        // Common
        tag(Tags.Items.MUSHROOMS).add(VDItems.BLACK_MUSHROOM.get());
        tag(Tags.Items.SEEDS).add(VDItems.ORCHID_SEEDS.get());
        tag(VDCommonTags.Items.SEEDS).add(VDItems.ORCHID_SEEDS.get());
        tag(VDCommonTags.Items.BREAD).addTags(VDCommonTags.Items.BREAD_RICE);
        tag(VDCommonTags.Items.BREAD_RICE).add(VDItems.RICE_BREAD.get());
        tag(VDCommonTags.Items.BREAD_BLOOD).add(VDItems.BLOOD_BAGEL.get());
        tag(VDCommonTags.Items.DOUGH).addTags(VDCommonTags.Items.DOUGH_RICE);
        tag(VDCommonTags.Items.DOUGH_RICE).add(VDItems.RICE_DOUGH.get());
        tag(VDCommonTags.Items.DOUGH_BLOOD).add(VDItems.BLOOD_DOUGH.get());
        tag(VDCommonTags.Items.RAW_BAT).add(VDItems.RAW_BAT.get()).add(VDItems.RAW_BAT_CHOPS.get());
        tag(VDCommonTags.Items.COOKED_BAT).add(VDItems.GRILLED_BAT.get()).add(VDItems.GRILLED_BAT_CHOPS.get());
        tag(VDCommonTags.Items.STORAGE_BLOCKS_GARLIC).add(VDItems.GARLIC_CRATE.get());
        tag(VDCommonTags.Items.VEGETABLES_GARLIC).add(ModItems.ITEM_GARLIC.get());

        // Compatibility
        tag(CompatibilityTags.SERENE_SEASONS_AUTUMN_CROPS).add(ModItems.ITEM_GARLIC.get());
        tag(CompatibilityTags.SERENE_SEASONS_SUMMER_CROPS).add(ModItems.ITEM_GARLIC.get());
    }
}
