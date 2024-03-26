package net.grid.vampiresdelight.data;

import de.teamlapen.vampirism.core.ModBlocks;
import de.teamlapen.vampirism.core.ModItems;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.registry.VDAdvancements;
import net.grid.vampiresdelight.common.registry.VDBlocks;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.grid.vampiresdelight.common.registry.VDPotions;
import net.grid.vampiresdelight.common.utility.VDTextUtils;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class VDAdvancementProvider extends ForgeAdvancementProvider {
    public VDAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, registries, existingFileHelper, List.of(new VDAdvancementGenerator()));
    }

    public static class VDAdvancementGenerator implements AdvancementGenerator {
        @Override
        public void generate(HolderLookup.Provider registries, Consumer<Advancement> consumer, ExistingFileHelper existingFileHelper) {
            Advancement vampiresDelight = Advancement.Builder.advancement()
                    .display(VDItems.DARK_STONE_STOVE.get(),
                            VDTextUtils.getTranslation("advancement.root"),
                            VDTextUtils.getTranslation("advancement.root.desc"),
                            new ResourceLocation("vampirism:textures/block/dark_stone_tiles.png"),
                            FrameType.TASK, false, false, false)
                    .addCriterion("seeds", InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[]{}))
                    .save(consumer, getNameId("main/root"));

            // Eating Branch
            Advancement disgusting = getAdvancement(vampiresDelight, ModItems.HUMAN_HEART.get(), "consume_disgusting_food", FrameType.TASK, true, true, false)
                    .addCriterion("disgusting_food", new PlayerTrigger.TriggerInstance(VDAdvancements.DISGUSTING_FOOD_CONSUMED.getId(), ContextAwarePredicate.ANY))
                    .save(consumer, getNameId("main/consume_disgusting_food"));

            // Exploring Branch
            Advancement theBestPresentForAMan = getAdvancement(vampiresDelight, Items.SPLASH_POTION, "get_clothes_dissolving_potion", FrameType.CHALLENGE, true, true, true)
                    .addCriterion("clothes_dissolving_potion", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ItemPredicate.Builder.item().of(Items.POTION).isPotion(VDPotions.CLOTHES_DISSOLVING.get()).build()))
                    .addCriterion("clothes_dissolving_splash_potion", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ItemPredicate.Builder.item().of(Items.SPLASH_POTION).isPotion(VDPotions.CLOTHES_DISSOLVING.get()).build()))
                    .addCriterion("clothes_dissolving_lingering_potion", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ItemPredicate.Builder.item().of(Items.LINGERING_POTION).isPotion(VDPotions.CLOTHES_DISSOLVING.get()).build()))
                    .requirements(RequirementsStrategy.OR)
                    .save(consumer, getNameId("main/get_clothes_dissolving_potion"));

            // Vampire Branch
            Advancement yourBestFriend = getAdvancement(vampiresDelight, ModBlocks.VAMPIRE_ORCHID.get().asItem(), "get_vampire_orchid", FrameType.TASK, true, false, false)
                    .addCriterion("vampire_orchid", InventoryChangeTrigger.TriggerInstance.hasItems(ModBlocks.VAMPIRE_ORCHID.get()))
                    .save(consumer, getNameId("main/get_vampire_orchid"));

            Advancement bothBeautyAndHealth = getAdvancement(yourBestFriend, VDItems.ORCHID_PETALS.get(), "plant_vampire_orchid_crop", FrameType.TASK, true, false, false)
                    .addCriterion("vampire_orchid", ItemUsedOnLocationTrigger.TriggerInstance.placedBlock(VDBlocks.VAMPIRE_ORCHID_CROP.get()))
                    .save(consumer, getNameId("main/plant_vampire_orchid_crop"));

            Advancement bloodWineTastesTheSameAsIRemember = getAdvancement(yourBestFriend, VDItems.BLOOD_WINE_BOTTLE.get(), "get_blood_wine_bottle", FrameType.TASK, true, true, false)
                    .addCriterion("blood_wine_bottle", InventoryChangeTrigger.TriggerInstance.hasItems(VDItems.BLOOD_WINE_BOTTLE.get()))
                    .save(consumer, getNameId("main/get_blood_wine_bottle"));

            Advancement winesServed = getAdvancement(yourBestFriend, VDItems.WINE_GLASS.get(), "pour_blood_wine", FrameType.TASK, true, false, false)
                    .addCriterion("pour_blood_wine", new PlayerTrigger.TriggerInstance(VDAdvancements.BLOOD_WINE_POURED.getId(), ContextAwarePredicate.ANY))
                    .save(consumer, getNameId("main/pour_blood_wine"));

            Advancement funnyCutsOfChildren = getAdvancement(yourBestFriend, VDItems.HUMAN_EYE.get(), "get_human_eye", FrameType.TASK, true, true, false)
                    .addCriterion("human_eye", InventoryChangeTrigger.TriggerInstance.hasItems(VDItems.HUMAN_EYE.get()))
                    .save(consumer, getNameId("main/get_human_eye"));

            // Hunter Branch
            Advancement messiahOfHumanity = getAdvancement(vampiresDelight, VDItems.WILD_GARLIC.get(), "get_garlic", FrameType.TASK, true, false, false)
                    .addCriterion("garlic", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.ITEM_GARLIC.get()))
                    .save(consumer, getNameId("main/get_garlic"));
        }

        protected static Advancement.Builder getAdvancement(Advancement parent, ItemLike display, String name, FrameType frame, boolean showToast, boolean announceToChat, boolean hidden) {
            return Advancement.Builder.advancement().parent(parent).display(display,
                    VDTextUtils.getTranslation("advancement." + name),
                    VDTextUtils.getTranslation("advancement." + name + ".desc"),
                    null, frame, showToast, announceToChat, hidden);
        }

        private String getNameId(String id) {
            return VampiresDelight.MODID + ":" + id;
        }
    }
}
