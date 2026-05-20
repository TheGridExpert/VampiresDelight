package net.grid.vampiresdelight.data.provider;

import de.teamlapen.lib.lib.data.BaseItemModelGenerator;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.core.VDBlocks;
import net.grid.vampiresdelight.common.core.VDItems;
import net.grid.vampiresdelight.common.world.block.BarStoolBlock;
import net.grid.vampiresdelight.common.world.block.WineShelfBlock;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import vectorwing.farmersdelight.data.ItemModels;

import java.util.stream.Stream;

public class VDItemModelProvider extends BaseItemModelGenerator {

    public VDItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, VampiresDelight.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        Stream.of(
                VDItems.SPIRIT_LANTERN,
                VDItems.ORCHID_SEEDS,
                VDItems.ORCHID_PETALS,
                VDItems.ROASTED_GARLIC,
                VDItems.SUGARED_BERRIES,
                VDItems.HEART_PIECES,
                VDItems.HUMAN_EYE,
                VDItems.RICE_DOUGH,
                VDItems.RICE_BREAD,
                VDItems.BLOOD_DOUGH,
                VDItems.BLOOD_BAGEL,
                VDItems.RAW_BAT,
                VDItems.RAW_BAT_CHOPS,
                VDItems.GRILLED_BAT,
                VDItems.GRILLED_BAT_CHOPS,
                VDItems.ORCHID_CAKE,
                VDItems.ORCHID_CAKE_SLICE,
                VDItems.BLOOD_PIE,
                VDItems.BLOOD_PIE_SLICE,
                VDItems.DANDELION_BEER_BOTTLE,
                VDItems.BLOOD_WINE_BOTTLE,
                VDItems.PURE_SORBET,
                VDItems.ORCHID_COOKIE,
                VDItems.ORCHID_ECLAIR,
                VDItems.ORCHID_ICE_CREAM,
                VDItems.CURSED_CUPCAKE,
                VDItems.DARK_ICE_CREAM,
                VDItems.SNOW_WHITE_ICE_CREAM,
                VDItems.WOLF_BERRY_COOKIE.registryObjectOrThrow(),
                VDItems.WOLF_BERRY_ICE_CREAM.registryObjectOrThrow(),
                VDItems.FISH_BURGER,
                VDItems.BLOOD_SAUSAGE,
                VDItems.BLOOD_HOT_DOG,
                VDItems.EYE_CROISSANT,
                VDItems.BAGEL_SANDWICH,
                VDItems.BAT_TACO,
                VDItems.HARDTACK,
                VDItems.ORCHID_CREAM_SOUP,
                VDItems.BLACK_MUSHROOM_SOUP,
                VDItems.GARLIC_SOUP,
                VDItems.BORSCHT,
                VDItems.ORCHID_CURRY,
                VDItems.BLACK_MUSHROOM_NOODLES,
                VDItems.WEIRD_JELLY_BLOCK,
                VDItems.WEIRD_JELLY
        ).forEach(item -> basicItem(item.get()));

        Stream.of(
                VDItems.DAISY_TEA,
                VDItems.BLOOD_SYRUP,
                VDItems.ORCHID_TEA,
                VDItems.DANDELION_BEER_MUG,
                VDItems.BLOOD_WINE_GLASS,
                VDItems.MULLED_WINE_GLASS
        ).forEach(item -> withExistingParent(item.get(), ItemModels.MUG).texture("layer0", ModelLocationUtils.getModelLocation(item.get())));

        Stream.of(
                VDBlocks.DARK_STONE_STOVE,
                VDBlocks.GARLIC_CRATE,
                VDBlocks.ORCHID_BAG,
                VDBlocks.DARK_SPRUCE_CABINET,
                VDBlocks.CURSED_SPRUCE_CABINET,
                VDBlocks.JACARANDA_CABINET.registryObjectOrThrow(),
                VDBlocks.MAGIC_CABINET.registryObjectOrThrow(),
                VDBlocks.CURSED_FARMLAND,
                VDBlocks.BLOODY_SOIL,
                VDBlocks.BLOODY_SOIL_FARMLAND
        ).forEach(block -> block(block.get()));
        WineShelfBlock.getAllShelfBlocks().forEach(this::block);
        BarStoolBlock.getBarStoolBlocks().forEach(this::block);

        Stream.of(
                VDItems.BLACK_MUSHROOM,
                VDItems.WILD_GARLIC
        ).forEach(item -> item(item.get(), resourceBlock(itemName(item.get()))));

        Stream.of(
                VDBlocks.BLACK_MUSHROOM_BLOCK,
                VDBlocks.BLACK_MUSHROOM_STEM
        ).forEach(block -> block(block.get(), itemName(block.get().asItem()) + "_inventory"));

        Stream.of(
                VDItems.SILVER_KNIFE.registryObjectOrThrow(),
                VDItems.ALCHEMICAL_COCKTAIL,
                VDItems.TRICOLOR_DANGO,
                VDItems.EYES_ON_STICK
        ).forEach(item -> withExistingParent(item.get(), ResourceLocation.withDefaultNamespace("item/handheld")).texture("layer0", ModelLocationUtils.getModelLocation(item.get())));
    }

    private static String itemName(Item item) {
        return ForgeRegistries.ITEMS.getKey(item).getPath();
    }

    private static ResourceLocation resourceBlock(String path) {
        return ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "block/" + path);
    }
}
