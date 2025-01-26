package net.grid.vampiresdelight.common.registry;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.item.WeatheredLetterItem;
import net.grid.vampiresdelight.common.utility.VDIntegrationUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class VDCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, VampiresDelight.MODID);

    public static final Supplier<CreativeModeTab> TAB_VAMPIRES_DELIGHT = CREATIVE_TABS.register(VampiresDelight.MODID,
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.vampiresdelight"))
                    .icon(() -> new ItemStack(VDBlocks.DARK_STONE_STOVE.get()))
                    .displayItems((displayParams, output) -> {
                        // Blocks
                        add(VDItems.DARK_STONE_STOVE.get(), output);
                        add(VDItems.GARLIC_CRATE.get(), output);
                        add(VDItems.ORCHID_BAG.get(), output);
                        add(VDItems.DARK_SPRUCE_CABINET.get(), output);
                        add(VDItems.CURSED_SPRUCE_CABINET.get(), output);
                        add(VDItems.JACARANDA_CABINET.get(), VDIntegrationUtils.WEREWOLVES, output);
                        add(VDItems.MAGIC_CABINET.get(), VDIntegrationUtils.WEREWOLVES, output);

                        add(VDItems.OAK_WINE_SHELF.get(), output);
                        add(VDItems.SPRUCE_WINE_SHELF.get(), output);
                        add(VDItems.BIRCH_WINE_SHELF.get(), output);
                        add(VDItems.JUNGLE_WINE_SHELF.get(), output);
                        add(VDItems.ACACIA_WINE_SHELF.get(), output);
                        add(VDItems.DARK_OAK_WINE_SHELF.get(), output);
                        add(VDItems.MANGROVE_WINE_SHELF.get(), output);
                        add(VDItems.CHERRY_WINE_SHELF.get(), output);
                        add(VDItems.BAMBOO_WINE_SHELF.get(), output);
                        add(VDItems.CRIMSON_WINE_SHELF.get(), output);
                        add(VDItems.WARPED_WINE_SHELF.get(), output);
                        add(VDItems.CURSED_SPRUCE_WINE_SHELF.get(), output);
                        add(VDItems.DARK_SPRUCE_WINE_SHELF.get(), output);
                        add(VDItems.JACARANDA_WINE_SHELF.get(), VDIntegrationUtils.WEREWOLVES, output);
                        add(VDItems.MAGIC_WINE_SHELF.get(), VDIntegrationUtils.WEREWOLVES, output);

                        add(VDItems.WHITE_BAR_STOOL.get(), output);
                        add(VDItems.LIGHT_GRAY_BAR_STOOL.get(), output);
                        add(VDItems.GRAY_BAR_STOOL.get(), output);
                        add(VDItems.BLACK_BAR_STOOL.get(), output);
                        add(VDItems.BROWN_BAR_STOOL.get(), output);
                        add(VDItems.RED_BAR_STOOL.get(), output);
                        add(VDItems.ORANGE_BAR_STOOL.get(), output);
                        add(VDItems.YELLOW_BAR_STOOL.get(), output);
                        add(VDItems.LIME_BAR_STOOL.get(), output);
                        add(VDItems.GREEN_BAR_STOOL.get(), output);
                        add(VDItems.CYAN_BAR_STOOL.get(), output);
                        add(VDItems.LIGHT_BLUE_BAR_STOOL.get(), output);
                        add(VDItems.BLUE_BAR_STOOL.get(), output);
                        add(VDItems.PURPLE_BAR_STOOL.get(), output);
                        add(VDItems.MAGENTA_BAR_STOOL.get(), output);
                        add(VDItems.PINK_BAR_STOOL.get(), output);

                        add(VDItems.CURSED_FARMLAND.get(), output);
                        add(VDItems.BLOODY_SOIL.get(), output);
                        add(VDItems.BLOODY_SOIL_FARMLAND.get(), output);
                        add(VDItems.SPIRIT_LANTERN.get(), output);

                        // Tools
                        add(VDItems.SILVER_KNIFE.get(), VDIntegrationUtils.WEREWOLVES, output);
                        add(VDItems.ALCHEMICAL_COCKTAIL.get(), output);
                        WeatheredLetterItem.generateCreativeTab(displayParams, output);

                        // Farming
                        add(VDItems.BLACK_MUSHROOM_BLOCK.get(), output);
                        add(VDItems.BLACK_MUSHROOM_STEM.get(), output);
                        add(VDItems.BLACK_MUSHROOM.get(), output);
                        add(VDItems.WILD_GARLIC.get(), output);
                        add(VDItems.ORCHID_SEEDS.get(), output);

                        // Foodstuffs
                        add(VDItems.ROASTED_GARLIC.get(), output);
                        add(VDItems.DANDELION_BEER_BOTTLE.get(), output);
                        add(VDItems.DANDELION_BEER_MUG.get(), output);
                        add(VDItems.DAISY_TEA.get(), output);
                        add(VDItems.BLOOD_SYRUP.get(), output);
                        add(VDItems.BLOOD_WINE_BOTTLE.get(), output);
                        add(VDItems.BLOOD_WINE_GLASS.get(), output);
                        add(VDItems.MULLED_WINE_GLASS.get(), output);
                        add(VDItems.ORCHID_TEA.get(), output);
                        add(VDItems.ORCHID_PETALS.get(), output);
                        add(VDItems.SUGARED_BERRIES.get(), output);
                        add(VDItems.RICE_DOUGH.get(), output);
                        add(VDItems.RICE_BREAD.get(), output);
                        add(VDItems.BLOOD_DOUGH.get(), output);
                        add(VDItems.BLOOD_BAGEL.get(), output);
                        add(VDItems.HEART_PIECES.get(), output);
                        add(VDItems.HUMAN_EYE.get(), output);
                        add(VDItems.RAW_BAT.get(), output);
                        add(VDItems.RAW_BAT_CHOPS.get(), output);
                        add(VDItems.COOKED_BAT.get(), output);
                        add(VDItems.COOKED_BAT_CHOPS.get(), output);
                        add(VDItems.BLOOD_PIE.get(), output);
                        add(VDItems.BLOOD_PIE_SLICE.get(), output);

                        // Sweets
                        add(VDItems.PURE_SORBET.get(), output);
                        add(VDItems.ORCHID_COOKIE.get(), output);
                        add(VDItems.ORCHID_ECLAIR.get(), output);
                        add(VDItems.ORCHID_ICE_CREAM.get(), output);
                        add(VDItems.TRICOLOR_DANGO.get(), output);
                        add(VDItems.CURSED_CUPCAKE.get(), output);
                        add(VDItems.DARK_ICE_CREAM.get(), output);
                        add(VDItems.SNOW_WHITE_ICE_CREAM.get(), output);
                        add(VDItems.WOLF_BERRY_COOKIE.get(), VDIntegrationUtils.WEREWOLVES, output);
                        add(VDItems.WOLF_BERRY_ICE_CREAM.get(), VDIntegrationUtils.WEREWOLVES, output);

                        // Basic Meals
                        add(VDItems.BLOOD_SAUSAGE.get(), output);
                        add(VDItems.BLOOD_HOT_DOG.get(), output);
                        add(VDItems.EYES_ON_STICK.get(), output);
                        add(VDItems.EYE_CROISSANT.get(), output);
                        add(VDItems.BAGEL_SANDWICH.get(), output);
                        add(VDItems.BAT_TACO.get(), output);
                        add(VDItems.FISH_BURGER.get(), output);
                        add(VDItems.HARDTACK.get(), output);

                        // Soups and Stews
                        add(VDItems.ORCHID_CREAM_SOUP.get(), output);
                        add(VDItems.BLACK_MUSHROOM_SOUP.get(), output);
                        add(VDItems.GARLIC_SOUP.get(), output);
                        add(VDItems.BORSCHT.get(), output);

                        // Plated Foods
                        add(VDItems.ORCHID_CURRY.get(), output);
                        add(VDItems.BLACK_MUSHROOM_NOODLES.get(), output);

                        // Feasts
                        add(VDItems.ORCHID_CAKE.get(), output);
                        add(VDItems.ORCHID_CAKE_SLICE.get(), output);
                        add(VDItems.WEIRD_JELLY_BLOCK.get(), output);
                        add(VDItems.WEIRD_JELLY.get(), output);
                    })
                    .build());

    private static void add(Item item, String requiredMod, CreativeModeTab.Output output) {
        if (VDIntegrationUtils.isModPresent(requiredMod))
            add(item, output);
    }

    private static void add(Item item, CreativeModeTab.Output output) {
        output.accept(item);
    }
}
