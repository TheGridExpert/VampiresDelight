package net.grid.vampiresdelight.common.registry;

import net.grid.vampiresdelight.VampiresDelight;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = VampiresDelight.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class VDCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, VampiresDelight.MODID);

    public static final RegistryObject<CreativeModeTab> TAB_VAMPIRES_DELIGHT = CREATIVE_TABS.register(VampiresDelight.MODID,
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.vampiresdelight"))
                    .icon(() -> new ItemStack(VDBlocks.DARK_STONE_STOVE.get()))
                    .displayItems((displayParams, output) -> {
                        // Blocks
                        output.accept(VDItems.DARK_STONE_STOVE.get());
                        //output.accept(VDItems.BREWING_BARREL.get());
                        output.accept(VDItems.GARLIC_CRATE.get());
                        output.accept(VDItems.ORCHID_BAG.get());
                        output.accept(VDItems.DARK_SPRUCE_CABINET.get());
                        output.accept(VDItems.CURSED_SPRUCE_CABINET.get());
                        output.accept(VDItems.CURSED_FARMLAND.get());
                        output.accept(VDItems.OAK_WINE_SHELF.get());
                        output.accept(VDItems.SPRUCE_WINE_SHELF.get());
                        output.accept(VDItems.BIRCH_WINE_SHELF.get());
                        output.accept(VDItems.JUNGLE_WINE_SHELF.get());
                        output.accept(VDItems.ACACIA_WINE_SHELF.get());
                        output.accept(VDItems.DARK_OAK_WINE_SHELF.get());
                        output.accept(VDItems.MANGROVE_WINE_SHELF.get());
                        output.accept(VDItems.CHERRY_WINE_SHELF.get());
                        output.accept(VDItems.BAMBOO_WINE_SHELF.get());
                        output.accept(VDItems.CRIMSON_WINE_SHELF.get());
                        output.accept(VDItems.WARPED_WINE_SHELF.get());
                        output.accept(VDItems.CURSED_SPRUCE_WINE_SHELF.get());
                        output.accept(VDItems.DARK_SPRUCE_WINE_SHELF.get());
                        output.accept(VDItems.SPIRIT_LANTERN.get());

                        // Farming
                        output.accept(VDItems.WILD_GARLIC.get());
                        output.accept(VDItems.ORCHID_SEEDS.get());

                        // Foodstuffs
                        output.accept(VDItems.GRILLED_GARLIC.get());
                        output.accept(VDItems.BLOOD_SYRUP.get());
                        output.accept(VDItems.ORCHID_TEA.get());
                        output.accept(VDItems.ORCHID_PETALS.get());
                        output.accept(VDItems.SUGARED_BERRIES.get());
                        output.accept(VDItems.RICE_DOUGH.get());
                        output.accept(VDItems.RICE_BREAD.get());
                        output.accept(VDItems.BLOOD_DOUGH.get());
                        output.accept(VDItems.BLOOD_BAGEL.get());
                        output.accept(VDItems.HEART_PIECES.get());
                        output.accept(VDItems.HUMAN_EYE.get());
                        output.accept(VDItems.RAW_BAT.get());
                        output.accept(VDItems.RAW_BAT_CHOPS.get());
                        output.accept(VDItems.COOKED_BAT.get());
                        output.accept(VDItems.COOKED_BAT_CHOPS.get());
                        output.accept(VDItems.BLOOD_WINE_BOTTLE.get());
                        output.accept(VDItems.WINE_GLASS.get());
                        output.accept(VDItems.MULLED_WINE_GLASS.get());
                        output.accept(VDItems.BLOOD_PIE.get());
                        output.accept(VDItems.BLOOD_PIE_SLICE.get());

                        // Sweets
                        output.accept(VDItems.PURE_SORBET.get());
                        output.accept(VDItems.ORCHID_COOKIE.get());
                        output.accept(VDItems.ORCHID_ECLAIR.get());
                        output.accept(VDItems.ORCHID_ICE_CREAM.get());
                        output.accept(VDItems.TRICOLOR_DANGO.get());
                        output.accept(VDItems.CURSED_CUPCAKE.get());
                        output.accept(VDItems.DARK_ICE_CREAM.get());
                        output.accept(VDItems.SNOW_WHITE_ICE_CREAM.get());

                        // Basic Meals
                        output.accept(VDItems.BLOOD_SAUSAGE.get());
                        output.accept(VDItems.BLOOD_HOT_DOG.get());
                        output.accept(VDItems.EYES_ON_STICK.get());
                        output.accept(VDItems.EYE_CROISSANT.get());
                        output.accept(VDItems.BAGEL_SANDWICH.get());
                        output.accept(VDItems.FISH_BURGER.get());
                        output.accept(VDItems.HARDTACK.get());

                        // Soups and Stews
                        output.accept(VDItems.GARLIC_SOUP.get());
                        output.accept(VDItems.BORSCHT.get());

                        // Feasts
                        output.accept(VDItems.ORCHID_CAKE.get());
                        output.accept(VDItems.ORCHID_CAKE_SLICE.get());
                        output.accept(VDItems.WEIRD_JELLY_BLOCK.get());
                        output.accept(VDItems.WEIRD_JELLY.get());
                    })
                    .build());

    @SubscribeEvent
    public static void addItemsToCreativeTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.COMBAT)
            event.accept(VDItems.ALCHEMICAL_COCKTAIL);
    }
}
