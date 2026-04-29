package net.grid.vampiresdelight.data.tag;

import de.teamlapen.vampirism.core.ModBlocks;
import de.teamlapen.vampirism.core.ModItems;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.block.BarStoolBlock;
import net.grid.vampiresdelight.common.block.ConsumableCandleCakeBlock;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.grid.vampiresdelight.common.tag.VDCompatibilityTags;
import net.grid.vampiresdelight.common.tag.VDCommonTags;
import net.grid.vampiresdelight.common.tag.VDTags;
import net.grid.vampiresdelight.common.utility.VDIntegrationUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.tag.ModTags;

import java.util.concurrent.CompletableFuture;

public class VDItemTagProvider extends ItemTagsProvider {
    public VDItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, VampiresDelight.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        this.registerModTags();
        this.registerVampirismTags();
        this.registerFarmersDelightTags();
        this.registerCommonTags();
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
                .add(VDItems.ROASTED_GARLIC.get())
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

        tag(VDTags.VAMPIRE_BITE_ENCHANTABLE)
                .add(ModItems.HEART_SEEKER_NORMAL.get())
                .add(ModItems.HEART_SEEKER_ENHANCED.get())
                .add(ModItems.HEART_SEEKER_ULTIMATE.get())
                .add(ModItems.HEART_STRIKER_NORMAL.get())
                .add(ModItems.HEART_STRIKER_ENHANCED.get())
                .add(ModItems.HEART_STRIKER_ULTIMATE.get());

        tag(VDTags.WINE_SHELF_BOTTLES)
                .addTag(VDTags.BEER_BOTTLES)
                .addTag(VDTags.WINE_BOTTLES);

        tag(VDTags.BEER_BOTTLES)
                .add(VDItems.DANDELION_BEER_BOTTLE.get());

        tag(VDTags.WINE_BOTTLES)
                .add(VDItems.BLOOD_WINE_BOTTLE.get());
    }

    private void registerVampirismTags() {
        tag(de.teamlapen.vampirism.core.ModTags.Items.HEART)
                .add(VDItems.HEART_PIECES.get());
    }

    private void registerFarmersDelightTags() {
        tag(ModTags.Items.KNIFE_ENCHANTABLE)
                .add(ModItems.HUNTER_AXE_NORMAL.get())
                .add(ModItems.HUNTER_AXE_ENHANCED.get())
                .add(ModItems.HUNTER_AXE_ULTIMATE.get())
                .add(ModItems.STAKE.get());

        tag(ModTags.Items.KNIVES)
                .add(VDItems.SILVER_KNIFE.get());

        tag(ModTags.Items.CABINETS_WOODEN)
                .add(VDItems.DARK_SPRUCE_CABINET.get())
                .add(VDItems.CURSED_SPRUCE_CABINET.get())
                .add(VDItems.JACARANDA_CABINET.get())
                .add(VDItems.MAGIC_CABINET.get());

        tag(ModTags.Items.SNACKS)
                .add(VDItems.BAGEL_SANDWICH.get())
                .add(VDItems.BLOOD_HOT_DOG.get())
                .add(VDItems.BLOOD_SAUSAGE.get())
                .add(VDItems.EYE_CROISSANT.get())
                .add(VDItems.EYES_ON_STICK.get())
                .add(VDItems.FISH_BURGER.get())
                .add(VDItems.BAT_TACO.get())
                .add(VDItems.BLOOD_BAGEL.get());

        tag(ModTags.Items.MEALS)
                .add(VDItems.ORCHID_CREAM_SOUP.get())
                .add(VDItems.BLACK_MUSHROOM_SOUP.get())
                .add(VDItems.GARLIC_SOUP.get())
                .add(VDItems.BORSCHT.get())
                .add(VDItems.ORCHID_CURRY.get())
                .add(VDItems.BLACK_MUSHROOM_NOODLES.get());

        tag(ModTags.Items.SWEETS)
                .add(VDItems.ORCHID_COOKIE.get())
                .add(VDItems.ORCHID_CAKE_SLICE.get())
                .add(VDItems.BLOOD_PIE_SLICE.get())
                .add(VDItems.ORCHID_ECLAIR.get())
                .add(VDItems.ORCHID_ICE_CREAM.get())
                .add(VDItems.DARK_ICE_CREAM.get())
                .add(VDItems.SNOW_WHITE_ICE_CREAM.get())
                .add(VDItems.WOLF_BERRY_ICE_CREAM.get())
                .add(VDItems.TRICOLOR_DANGO.get())
                .add(VDItems.PURE_SORBET.get())
                .add(VDItems.CURSED_CUPCAKE.get());

        tag(ModTags.Items.DRINKS)
                .add(VDItems.ORCHID_TEA.get())
                .add(VDItems.DANDELION_BEER_MUG.get())
                .add(VDItems.BLOOD_WINE_GLASS.get())
                .add(VDItems.MULLED_WINE_GLASS.get());

        tag(ModTags.Items.FEASTS)
                .add(VDItems.WEIRD_JELLY_BLOCK.get());

        tag(Tags.Items.FOODS_SOUP)
                .add(VDItems.ORCHID_CREAM_SOUP.get())
                .add(VDItems.BLACK_MUSHROOM_SOUP.get())
                .add(VDItems.GARLIC_SOUP.get())
                .add(VDItems.BORSCHT.get());

        tag(Tags.Items.FOODS_PIE)
                .add(VDItems.BLOOD_PIE_SLICE.get());
    }

    private void registerCommonTags() {
        BarStoolBlock.getBarStoolBlocks().forEach(block -> {
            tag(Tags.Items.DYED).add(block.asItem());

            if (block instanceof BarStoolBlock barStoolBlock) {
                tag(barStoolBlock.getColor().getDyedTag()).add(block.asItem());
            }
        });

        tag(Tags.Items.CROPS)
                .addTag(VDCommonTags.CROPS_GARLIC);
        tag(VDCommonTags.CROPS_GARLIC)
                .add(ModBlocks.GARLIC.asItem());

        tag(Tags.Items.SEEDS)
                .add(VDItems.ORCHID_SEEDS.get());

        tag(Tags.Items.MUSHROOMS)
                .add(VDItems.BLACK_MUSHROOM.get());

        tag(Tags.Items.FOODS_VEGETABLE)
                .add(ModBlocks.GARLIC.asItem());
        tag(VDCommonTags.FOODS_GARLIC)
                .add(ModBlocks.GARLIC.asItem());

        // Blood bread should not be in FOODS_BREAD because it's vampire food
        tag(Tags.Items.FOODS_BREAD)
                .addTag(VDCommonTags.FOODS_BREADS_RICE);
        tag(VDCommonTags.FOODS_BREADS_RICE)
                .add(VDItems.RICE_BREAD.get());
        tag(VDCommonTags.FOODS_BREADS_BLOOD)
                .add(VDItems.BLOOD_BAGEL.get());

        tag(Tags.Items.FOODS_COOKIE)
                .add(VDItems.ORCHID_COOKIE.get())
                .add(VDItems.WOLF_BERRY_COOKIE.get());

        // Blood dough should not be in FOODS_DOUGH as it's vampire food
        tag(VDCommonTags.Items.FOODS_DOUGH)
                .addTag(VDCommonTags.FOODS_DOUGH_RICE);
        tag(VDCommonTags.FOODS_DOUGH_RICE)
                .add(VDItems.RICE_DOUGH.get());
        tag(VDCommonTags.FOODS_DOUGH_BLOOD)
                .add(VDItems.BLOOD_BAGEL.get());

        tag(VDCommonTags.FOODS_RAW_BAT)
                .add(VDItems.RAW_BAT.get())
                .add(VDItems.RAW_BAT_CHOPS.get());
        tag(VDCommonTags.FOODS_COOKED_BAT)
                .add(VDItems.COOKED_BAT.get())
                .add(VDItems.COOKED_BAT_CHOPS.get());
        tag(Tags.Items.FOODS_RAW_MEAT)
                .addTag(VDCommonTags.FOODS_RAW_BAT);
        tag(Tags.Items.FOODS_COOKED_MEAT)
                .addTag(VDCommonTags.FOODS_COOKED_BAT);
        tag(ItemTags.MEAT)
                .addTag(Tags.Items.FOODS_RAW_MEAT)
                .addTag(VDCommonTags.FOODS_COOKED_BAT);

        tag(Tags.Items.FOODS_FOOD_POISONING)
                .addTag(VDCommonTags.FOODS_RAW_BAT);

        ConsumableCandleCakeBlock.getAllCandleCakes().forEach(block ->
                tag(Tags.Items.FOODS_EDIBLE_WHEN_PLACED).add(block.asItem()));

        tag(Tags.Items.FOODS_EDIBLE_WHEN_PLACED)
                .add(VDItems.BLOOD_PIE.get())
                .add(VDItems.WEIRD_JELLY_BLOCK.get())
                .add(VDItems.ORCHID_CAKE.get());

        tag(VDCommonTags.Items.TOOLS_KNIFE)
                .add(VDItems.SILVER_KNIFE.get());
    }

    public void registerMinecraftTags() {
        tag(ItemTags.BOOKSHELF_BOOKS)
                .add(ModItems.VAMPIRE_BOOK.get())
                .addOptional(ResourceLocation.fromNamespaceAndPath("guideapi_vp", "vampirism-guidebook"));
    }

    public void registerCompatibilityTags() {
        tag(VDCompatibilityTags.SILVER_TOOL)
                .add(VDItems.SILVER_KNIFE.get());
        tag(VDCompatibilityTags.WEREWOLF_FOOD)
                .addTag(VDTags.WEREWOLF_ONLY_FOOD)
                .add(VDItems.PURE_SORBET.get());

        tag(VDCompatibilityTags.CREATE_UPRIGHT_ON_BELT)
                .add(VDItems.DANDELION_BEER_MUG.get())
                .add(VDItems.DAISY_TEA.get())
                .add(VDItems.ORCHID_TEA.get())
                .add(VDItems.MULLED_WINE_GLASS.get())
                .add(VDItems.BLOOD_WINE_GLASS.get())
                .add(VDItems.BLOOD_PIE.get())
                .add(VDItems.ORCHID_CAKE.get());

        tag(VDCompatibilityTags.SERENE_SEASONS_AUTUMN_CROPS).add(
                ModBlocks.GARLIC.asItem());
        tag(VDCompatibilityTags.SERENE_SEASONS_SPRING_CROPS).add(
                VDItems.ORCHID_SEEDS.get());
        tag(VDCompatibilityTags.SERENE_SEASONS_SUMMER_CROPS).add(
                ModBlocks.GARLIC.asItem(),
                VDItems.ORCHID_SEEDS.get());

        tag(VDCompatibilityTags.TINKERS_CONSTRUCT_SEEDS)
                .add(VDItems.ORCHID_SEEDS.get());
    }
}
