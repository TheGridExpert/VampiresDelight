package net.grid.vampiresdelight.data.provider;

import de.teamlapen.vampirism.core.ModBlocks;
import de.teamlapen.vampirism.core.ModItems;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.advancement.DrinkPouredTrigger;
import net.grid.vampiresdelight.common.core.VDAdvancementTriggers;
import net.grid.vampiresdelight.common.core.VDBlocks;
import net.grid.vampiresdelight.common.core.VDItems;
import net.grid.vampiresdelight.common.tag.VDBlockTags;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.ItemUsedOnLocationTrigger;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class VDAdvancementProvider extends ForgeAdvancementProvider {

    public VDAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, registries, existingFileHelper, List.of(new Generator()));
    }

    public static class Generator implements ForgeAdvancementProvider.AdvancementGenerator {

        @Override
        public void generate(HolderLookup.Provider registries, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {
            Advancement vampiresDelight = Advancement.Builder.advancement()
                    .display(VDItems.DARK_STONE_STOVE.get(),
                            getTranslation("root"),
                            getTranslation("root.desc"),
                            ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "textures/block/dark_stone_small_bricks.png"),
                            FrameType.TASK, false, false, false)
                    .addCriterion("seeds", InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[]{}))
                    .save(saver, getNameId("main/root"));

            // Eating Branch
            getAdvancement(vampiresDelight, ModItems.HUMAN_HEART.get(), "consume_disgusting_food", FrameType.TASK, true, false)
                    .addCriterion("disgusting_food", VDAdvancementTriggers.disgustingFoodConsumed())
                    .save(saver, getNameId("main/consume_disgusting_food"));

            // Human Branch
            Advancement iKnewYouElves = getAdvancement(vampiresDelight, VDItems.DANDELION_BEER_MUG.get(), "pour_dandelion_beer", FrameType.TASK, true, false)
                    .addCriterion("pour_dandelion_beer", DrinkPouredTrigger.TriggerInstance.pouredDrinkBottle(VDItems.DANDELION_BEER_BOTTLE.get()))
                    .save(saver, getNameId("main/pour_dandelion_beer"));

            getAdvancement(iKnewYouElves, VDItems.SPRUCE_WINE_SHELF.get(), "place_dandelion_beer_bottle_on_shelf", FrameType.TASK, false, false)
                    .addCriterion("wine_shelf", ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(
                            LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(VDBlockTags.WINE_SHELVES).build()),
                            ItemPredicate.Builder.item().of(VDItems.DANDELION_BEER_BOTTLE.get())))
                    .save(saver, getNameId("main/place_dandelion_beer_bottle_on_shelf"));

            // Hunter Branch
            getAdvancement(vampiresDelight, VDItems.WILD_GARLIC.get(), "get_garlic", FrameType.TASK, false, false)
                    .addCriterion("garlic", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.ITEM_GARLIC.get()))
                    .save(saver, getNameId("main/get_garlic"));

            // Vampire Branch
            Advancement yourBestFriend = getAdvancement(vampiresDelight, ModBlocks.VAMPIRE_ORCHID.get().asItem(), "get_vampire_orchid", FrameType.TASK, false, false)
                    .addCriterion("vampire_orchid", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.VAMPIRE_ORCHID.get()))
                    .save(saver, getNameId("main/get_vampire_orchid"));

            Advancement bothBeautyAndHealth = getAdvancement(yourBestFriend, VDItems.ORCHID_PETALS.get(), "plant_vampire_orchid_crop", FrameType.TASK, false, false)
                    .addCriterion("vampire_orchid", ItemUsedOnLocationTrigger.TriggerInstance.placedBlock(VDBlocks.VAMPIRE_ORCHID_CROP.get()))
                    .save(saver, getNameId("main/plant_vampire_orchid_crop"));

            getAdvancement(bothBeautyAndHealth, VDItems.ORCHID_TEA.get(), "get_orchid_tea", FrameType.TASK, false, false)
                    .addCriterion("orchid_tea", InventoryChangeTrigger.TriggerInstance.hasItems(VDItems.ORCHID_TEA.get()))
                    .save(saver, getNameId("main/get_orchid_tea"));

            getAdvancement(bothBeautyAndHealth, VDItems.BLOODY_SOIL.get(), "bloody_rich_soil", FrameType.TASK, false, false)
                    .addCriterion("bloody_soil", VDAdvancementTriggers.richSoilBloodied())
                    .save(saver, getNameId("main/bloody_rich_soil"));

            Advancement bloodWineTastesTheSameAsIRemember = getAdvancement(yourBestFriend, VDItems.BLOOD_WINE_GLASS.get(), "pour_blood_wine", FrameType.TASK, true, false)
                    .addCriterion("pour_blood_wine", DrinkPouredTrigger.TriggerInstance.pouredDrinkBottle(VDItems.BLOOD_WINE_BOTTLE.get()))
                    .save(saver, getNameId("main/pour_blood_wine"));

            getAdvancement(bloodWineTastesTheSameAsIRemember, VDItems.DARK_SPRUCE_WINE_SHELF.get(), "place_blood_wine_bottle_on_shelf", FrameType.TASK, false, false)
                    .addCriterion("wine_shelf", ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(
                            LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(VDBlockTags.WINE_SHELVES).build()),
                            ItemPredicate.Builder.item().of(VDItems.BLOOD_WINE_BOTTLE.get())))
                    .save(saver, getNameId("main/place_blood_wine_bottle_on_shelf"));

            getAdvancement(yourBestFriend, VDItems.HUMAN_EYE.get(), "get_human_eye", FrameType.TASK, true, false)
                    .addCriterion("human_eye", InventoryChangeTrigger.TriggerInstance.hasItems(VDItems.HUMAN_EYE.get()))
                    .save(saver, getNameId("main/get_human_eye"));
        }

        protected static Advancement.Builder getAdvancement(Advancement parent, ItemLike display, String name, FrameType frame, boolean announceToChat, boolean hidden) {
            return Advancement.Builder.advancement().parent(parent).display(display,
                    getTranslation(name),
                    getTranslation(name + ".desc"),
                    null, frame, true, announceToChat, hidden);
        }

        private static Component getTranslation(String key) {
            return Component.translatable("advancement." + VampiresDelight.MODID + "." + key);
        }

        private static String getNameId(String id) {
            return VampiresDelight.MODID + ":" + id;
        }
    }
}