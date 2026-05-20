package net.grid.vampiresdelight.common.core;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.util.OptRegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static net.grid.vampiresdelight.common.core.VDItems.*;

public class VDCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, VampiresDelight.MODID);

    public static final RegistryObject<CreativeModeTab> VAMPIRES_DELIGHT = CREATIVE_TABS.register(VampiresDelight.MODID, () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.vampiresdelight"))
            .icon(VDBlocks.DARK_STONE_STOVE.get().asItem()::getDefaultInstance)
            .displayItems((params, output) -> {
                add(DARK_STONE_STOVE, output);
                add(GARLIC_CRATE, output);
                add(ORCHID_BAG, output);
                add(DARK_SPRUCE_CABINET, output);
                add(CURSED_SPRUCE_CABINET, output);
                add(JACARANDA_CABINET, output);
                add(MAGIC_CABINET, output);

                add(OAK_WINE_SHELF, output);
                add(SPRUCE_WINE_SHELF, output);
                add(BIRCH_WINE_SHELF, output);
                add(JUNGLE_WINE_SHELF, output);
                add(ACACIA_WINE_SHELF, output);
                add(DARK_OAK_WINE_SHELF, output);
                add(MANGROVE_WINE_SHELF, output);
                add(CHERRY_WINE_SHELF, output);
                add(BAMBOO_WINE_SHELF, output);
                add(CRIMSON_WINE_SHELF, output);
                add(WARPED_WINE_SHELF, output);
                add(DARK_SPRUCE_WINE_SHELF, output);
                add(CURSED_SPRUCE_WINE_SHELF, output);
                add(JACARANDA_WINE_SHELF, output);
                add(MAGIC_WINE_SHELF, output);

                add(WHITE_BAR_STOOL, output);
                add(LIGHT_GRAY_BAR_STOOL, output);
                add(GRAY_BAR_STOOL, output);
                add(BLACK_BAR_STOOL, output);
                add(BROWN_BAR_STOOL, output);
                add(RED_BAR_STOOL, output);
                add(ORANGE_BAR_STOOL, output);
                add(YELLOW_BAR_STOOL, output);
                add(LIME_BAR_STOOL, output);
                add(GREEN_BAR_STOOL, output);
                add(CYAN_BAR_STOOL, output);
                add(LIGHT_BLUE_BAR_STOOL, output);
                add(BLUE_BAR_STOOL, output);
                add(PURPLE_BAR_STOOL, output);
                add(MAGENTA_BAR_STOOL, output);
                add(PINK_BAR_STOOL, output);

                add(SPIRIT_LANTERN, output);

                add(CURSED_FARMLAND, output);
                add(BLOODY_SOIL, output);
                add(BLOODY_SOIL_FARMLAND, output);
                add(BLACK_MUSHROOM_BLOCK, output);
                add(BLACK_MUSHROOM_STEM, output);
                add(BLACK_MUSHROOM, output);

                add(WILD_GARLIC, output);
                add(ORCHID_SEEDS, output);
                add(ORCHID_PETALS, output);

                add(SILVER_KNIFE, output);
                add(ALCHEMICAL_COCKTAIL, output);

                add(ROASTED_GARLIC, output);
                add(SUGARED_BERRIES, output);

                add(DAISY_TEA, output);
                add(BLOOD_SYRUP, output);
                add(ORCHID_TEA, output);
                add(DANDELION_BEER_BOTTLE, output);
                add(DANDELION_BEER_MUG, output);
                add(BLOOD_WINE_BOTTLE, output);
                add(BLOOD_WINE_GLASS, output);
                add(MULLED_WINE_GLASS, output);

                add(HARDTACK, output);
                add(RICE_DOUGH, output);
                add(RICE_BREAD, output);
                add(BLOOD_DOUGH, output);
                add(BLOOD_BAGEL, output);
                add(HEART_PIECES, output);
                add(HUMAN_EYE, output);
                add(RAW_BAT, output);
                add(RAW_BAT_CHOPS, output);
                add(GRILLED_BAT, output);
                add(GRILLED_BAT_CHOPS, output);

                add(ORCHID_CAKE, output);
                add(ORCHID_CAKE_SLICE, output);
                add(BLOOD_PIE, output);
                add(BLOOD_PIE_SLICE, output);

                add(PURE_SORBET, output);
                add(ORCHID_COOKIE, output);
                add(ORCHID_ECLAIR, output);
                add(ORCHID_ICE_CREAM, output);
                add(TRICOLOR_DANGO, output);
                add(CURSED_CUPCAKE, output);
                add(DARK_ICE_CREAM, output);
                add(SNOW_WHITE_ICE_CREAM, output);
                add(WOLF_BERRY_COOKIE, output);
                add(WOLF_BERRY_ICE_CREAM, output);

                add(FISH_BURGER, output);
                add(BLOOD_SAUSAGE, output);
                add(BLOOD_HOT_DOG, output);
                add(EYES_ON_STICK, output);
                add(EYE_CROISSANT, output);
                add(BAGEL_SANDWICH, output);
                add(BAT_TACO, output);

                add(ORCHID_CREAM_SOUP, output);
                add(BLACK_MUSHROOM_SOUP, output);
                add(GARLIC_SOUP, output);
                add(BORSCHT, output);

                add(ORCHID_CURRY, output);
                add(BLACK_MUSHROOM_NOODLES, output);

                add(WEIRD_JELLY_BLOCK, output);
                add(WEIRD_JELLY, output);
            })
            .build()
    );

    public static void add(RegistryObject<? extends Item> item, CreativeModeTab.Output output) {
        output.accept(item.get());
    }

    public static void add(OptRegistryObject<? extends Item> item, CreativeModeTab.Output output) {
        item.ifPresent(output::accept);
    }

    

    public static void register(IEventBus eventBus) {
        CREATIVE_TABS.register(eventBus);
    }
}