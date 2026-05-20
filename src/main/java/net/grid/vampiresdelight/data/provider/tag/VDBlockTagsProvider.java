package net.grid.vampiresdelight.data.provider.tag;

import de.teamlapen.vampirism.core.ModBlocks;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.core.VDBlocks;
import net.grid.vampiresdelight.common.tag.VDBlockTags;
import net.grid.vampiresdelight.common.tag.VDCommonTags;
import net.grid.vampiresdelight.common.world.block.FactionCandleCakeBlock;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.tag.CompatibilityTags;
import vectorwing.farmersdelight.common.tag.ModTags;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unchecked")
public class VDBlockTagsProvider extends BlockTagsProvider {

    public VDBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, VampiresDelight.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // Vampire's Delight
        tag(VDBlockTags.BLOODY_SOIL).add(VDBlocks.BLOODY_SOIL.get(), VDBlocks.BLOODY_SOIL_FARMLAND.get());
        tag(VDBlockTags.BLACK_MUSHROOM_GROW_BLOCK).addTags(de.teamlapen.vampirism.core.ModTags.Blocks.CURSED_EARTH, VDBlockTags.BLOODY_SOIL);

        tag(VDBlockTags.WINE_SHELVES_WOODEN)
                .add(VDBlocks.OAK_WINE_SHELF.get())
                .add(VDBlocks.SPRUCE_WINE_SHELF.get())
                .add(VDBlocks.BIRCH_WINE_SHELF.get())
                .add(VDBlocks.JUNGLE_WINE_SHELF.get())
                .add(VDBlocks.ACACIA_WINE_SHELF.get())
                .add(VDBlocks.DARK_OAK_WINE_SHELF.get())
                .add(VDBlocks.MANGROVE_WINE_SHELF.get())
                .add(VDBlocks.CHERRY_WINE_SHELF.get())
                .add(VDBlocks.BAMBOO_WINE_SHELF.get())
                .add(VDBlocks.CRIMSON_WINE_SHELF.get())
                .add(VDBlocks.WARPED_WINE_SHELF.get())
                .add(VDBlocks.DARK_SPRUCE_WINE_SHELF.get())
                .add(VDBlocks.CURSED_SPRUCE_WINE_SHELF.get())
                .add(VDBlocks.JACARANDA_WINE_SHELF.getOrThrow())
                .add(VDBlocks.MAGIC_WINE_SHELF.getOrThrow());
        tag(VDBlockTags.WINE_SHELVES).addTag(VDBlockTags.WINE_SHELVES_WOODEN);

        // Vanilla
        tag(BlockTags.MINEABLE_WITH_AXE)
                .addTag(VDBlockTags.WINE_SHELVES_WOODEN)
                .add(VDBlocks.DARK_SPRUCE_CABINET.get())
                .add(VDBlocks.CURSED_SPRUCE_CABINET.get())
                .add(VDBlocks.JACARANDA_CABINET.getOrThrow())
                .add(VDBlocks.MAGIC_CABINET.getOrThrow())
                .add(VDBlocks.GARLIC_CRATE.get())
                .add(VDBlocks.WHITE_BAR_STOOL.get())
                .add(VDBlocks.ORANGE_BAR_STOOL.get())
                .add(VDBlocks.MAGENTA_BAR_STOOL.get())
                .add(VDBlocks.LIGHT_BLUE_BAR_STOOL.get())
                .add(VDBlocks.YELLOW_BAR_STOOL.get())
                .add(VDBlocks.LIME_BAR_STOOL.get())
                .add(VDBlocks.PINK_BAR_STOOL.get())
                .add(VDBlocks.GRAY_BAR_STOOL.get())
                .add(VDBlocks.LIGHT_GRAY_BAR_STOOL.get())
                .add(VDBlocks.CYAN_BAR_STOOL.get())
                .add(VDBlocks.PURPLE_BAR_STOOL.get())
                .add(VDBlocks.BLUE_BAR_STOOL.get())
                .add(VDBlocks.BROWN_BAR_STOOL.get())
                .add(VDBlocks.GREEN_BAR_STOOL.get())
                .add(VDBlocks.RED_BAR_STOOL.get())
                .add(VDBlocks.BLACK_BAR_STOOL.get())
                .add(VDBlocks.BLACK_MUSHROOM_BLOCK.get())
                .add(VDBlocks.BLACK_MUSHROOM_STEM.get());

        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(VDBlocks.DARK_STONE_STOVE.get())
                .add(VDBlocks.SPIRIT_LANTERN.get());

        tag(BlockTags.MINEABLE_WITH_HOE)
                .add(VDBlocks.ORCHID_BAG.get());

        tag(BlockTags.MINEABLE_WITH_SHOVEL)
                .add(VDBlocks.CURSED_FARMLAND.get())
                .add(VDBlocks.BLOODY_SOIL.get())
                .add(VDBlocks.BLOODY_SOIL_FARMLAND.get());

        tag(BlockTags.FLOWER_POTS).add(VDBlocks.POTTED_BLACK_MUSHROOM.get());
        tag(BlockTags.CROPS).add(VDBlocks.VAMPIRE_ORCHID_CROP.get(), ModBlocks.GARLIC.get());
        tag(BlockTags.SMALL_FLOWERS).add(VDBlocks.WILD_GARLIC.get());
        tag(BlockTags.DIRT).add(VDBlocks.BLOODY_SOIL.get());
        tag(BlockTags.BAMBOO_PLANTABLE_ON).add(VDBlocks.BLOODY_SOIL.get());
        tag(BlockTags.MUSHROOM_GROW_BLOCK).add(VDBlocks.BLOODY_SOIL.get());
        tag(BlockTags.MAINTAINS_FARMLAND)
                .add(VDBlocks.CURSED_FARMLAND.get())
                .add(VDBlocks.BLOODY_SOIL_FARMLAND.get());

        VDBlocks.BLOCKS.getEntries().stream()
                .filter(block -> block.get() instanceof FactionCandleCakeBlock)
                .forEach(block -> tag(BlockTags.CANDLE_CAKES).add(block.get()));

        // Farmer's Delight
        tag(ModTags.Blocks.CABINETS_WOODEN)
                .add(VDBlocks.DARK_SPRUCE_CABINET.get())
                .add(VDBlocks.CURSED_SPRUCE_CABINET.get())
                .add(VDBlocks.JACARANDA_CABINET.getOrThrow())
                .add(VDBlocks.MAGIC_CABINET.getOrThrow());

        tag(ModTags.Blocks.HEAT_SOURCES).add(VDBlocks.DARK_STONE_STOVE.get());
        tag(ModTags.Blocks.TRAY_HEAT_SOURCES).add(ModBlocks.FIRE_PLACE.get());

        tag(ModTags.Blocks.MINEABLE_WITH_KNIFE).add(VDBlocks.ORCHID_CAKE.get(), VDBlocks.WEIRD_JELLY_BLOCK.get());

        tag(ModTags.Blocks.UNAFFECTED_BY_RICH_SOIL)
                .add(VDBlocks.BLACK_MUSHROOM.get())
                .add(VDBlocks.VAMPIRE_ORCHID_CROP.get());

        tag(ModTags.Blocks.WILD_CROPS).add(VDBlocks.WILD_GARLIC.get());

        tag(ModTags.Blocks.STRAW_BLOCKS).add(VDBlocks.ORCHID_BAG.get());

        tag(ModTags.Blocks.COMPOST_ACTIVATORS)
                .add(VDBlocks.BLACK_MUSHROOM.get())
                .add(VDBlocks.BLOODY_SOIL.get())
                .add(VDBlocks.BLOODY_SOIL_FARMLAND.get());

        tag(ModTags.Blocks.MUSHROOM_COLONY_GROWABLE_ON).add(VDBlocks.BLOODY_SOIL.get());

        tag(ModTags.Blocks.FEASTS).add(VDBlocks.WEIRD_JELLY_BLOCK.get());

        tag(ModTags.Blocks.PIES).add(VDBlocks.BLOOD_PIE.get());

        // Common
        tag(VDCommonTags.Blocks.STORAGE_BLOCKS_GARLIC).add(VDBlocks.GARLIC_CRATE.get());

        // Compatibility
        tag(CompatibilityTags.CREATE_PASSIVE_BOILER_HEATERS).add(VDBlocks.DARK_STONE_STOVE.get());
        tag(CompatibilityTags.SERENE_SEASONS_AUTUMN_CROPS_BLOCK).add(ModBlocks.GARLIC.get());
        tag(CompatibilityTags.SERENE_SEASONS_SUMMER_CROPS_BLOCK).add(ModBlocks.GARLIC.get());
    }
}
