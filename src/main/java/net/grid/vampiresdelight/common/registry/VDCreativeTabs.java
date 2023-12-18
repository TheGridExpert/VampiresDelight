package net.grid.vampiresdelight.common.registry;

import net.grid.vampiresdelight.VampiresDelight;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import vectorwing.farmersdelight.common.registry.ModCreativeTabs;

@Mod.EventBusSubscriber(modid = VampiresDelight.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class VDCreativeTabs {
    @SubscribeEvent
    public static void addItemsToCreativeTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTab() != ModCreativeTabs.TAB_FARMERS_DELIGHT.get()) return;
        // Blocks
        event.accept(VDItems.BREWING_BARREL);
        event.accept(VDItems.GARLIC_CRATE);
        event.accept(VDItems.ORCHID_BAG);
        event.accept(VDItems.DARK_SPRUCE_CABINET);
        event.accept(VDItems.CURSED_SPRUCE_CABINET);
        event.accept(VDItems.CURSED_FARMLAND);
        event.accept(VDItems.WILD_GARLIC);

        // Tools/Non-food
        event.accept(VDItems.ALCHEMICAL_COCKTAIL);

        // Farming
        event.accept(VDItems.ORCHID_SEEDS);

        // Foodstuffs
        event.accept(VDItems.GRILLED_GARLIC);
        event.accept(VDItems.ORCHID_TEA);
        event.accept(VDItems.ORCHID_PETALS);
        event.accept(VDItems.HEART_PIECES);
        event.accept(VDItems.HUMAN_EYE);
        event.accept(VDItems.BLOOD_DOUGH);
        event.accept(VDItems.BLOOD_BAGEL);
        event.accept(VDItems.BLOOD_WINE_BOTTLE);
        event.accept(VDItems.WINE_GLASS);
        event.accept(VDItems.BLOOD_PIE);
        event.accept(VDItems.BLOOD_PIE_SLICE);

        // Sweets
        event.accept(VDItems.PURE_SORBET);
        event.accept(VDItems.CURSED_CUPCAKE);

        // Basic Meals
        event.accept(VDItems.EYE_TOAST);
        event.accept(VDItems.BAGEL_SANDWICH);
        event.accept(VDItems.HARDTACK);

        // Soups and Stews
        event.accept(VDItems.GARLIC_SOUP);
        event.accept(VDItems.BORSCHT);

        // Feasts
        event.accept(VDItems.WEIRD_JELLY_BLOCK);
        event.accept(VDItems.WEIRD_JELLY);
    }
}
