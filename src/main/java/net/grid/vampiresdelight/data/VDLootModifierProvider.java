package net.grid.vampiresdelight.data;

import de.teamlapen.vampirism.core.ModLootTables;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.loot.modifier.VDAddItemModifier;
import net.grid.vampiresdelight.common.loot.modifier.VDAddLootTableModifier;
import net.grid.vampiresdelight.common.loot.modifier.VDPastrySlicingModifier;
import net.grid.vampiresdelight.common.registry.VDBlocks;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.grid.vampiresdelight.common.registry.VDLootTables;
import net.grid.vampiresdelight.common.tag.VDTags;
import net.minecraft.advancements.critereon.EntityEquipmentPredicate;
import net.minecraft.advancements.critereon.EntityFlagsPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;
import vectorwing.farmersdelight.common.tag.ModTags;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unused")
public class VDLootModifierProvider extends GlobalLootModifierProvider {
    public VDLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, VampiresDelight.MODID);
    }

    @Override
    protected void start() {
        this.add("add_loot_crypt", addChestLoot(ModLootTables.CHEST_CRYPT.location(), VDLootTables.VD_CHEST_CRYPT));
        this.add("add_loot_hunter_outpost_tent", addChestLoot(ModLootTables.CHEST_HUNTER_OUTPOST_TENT.location(), VDLootTables.VD_CHEST_HUNTER_OUTPOST_TENT));
        this.add("add_loot_hunter_outpost_tower_food", addChestLoot(ModLootTables.CHEST_HUNTER_OUTPOST_TOWER_FOOD.location(), VDLootTables.VD_CHEST_HUNTER_OUTPOST_TOWER_FOOD));
        this.add("add_loot_hunter_outpost_tower_special", addChestLoot(ModLootTables.CHEST_HUNTER_OUTPOST_TOWER_SPECIAL.location(), VDLootTables.VD_CHEST_HUNTER_OUTPOST_TOWER_SPECIAL));
        this.add("add_loot_vampire_altar", addChestLoot(ModLootTables.CHEST_VAMPIRE_ALTAR.location(), VDLootTables.VD_CHEST_ALTAR));
        this.add("add_loot_vampire_dungeon", addChestLoot(ModLootTables.CHEST_VAMPIRE_DUNGEON.location(), VDLootTables.VD_CHEST_VAMPIRE_DUNGEON));
        this.add("add_loot_vampire_hut", addChestLoot(ModLootTables.CHEST_VAMPIRE_HUT.location(), VDLootTables.VD_CHEST_VAMPIRE_HUT));

        this.add("add_mob_loot_bat", addMobLoot(VDItems.RAW_BAT.get(), false, EntityType.BAT));
        this.add("add_mob_loot_cooked_bat", addMobLoot(VDItems.COOKED_BAT.get(), true, EntityType.BAT));

        this.add("scavenging_eyes_from_humans", addScavengeMobChanceLoot(VDItems.HUMAN_EYE.get(), VDTags.DROP_HUMAN_EYE));

        this.add("slicing_blood_pie", pastrySlicing(VDItems.BLOOD_PIE_SLICE.get(), VDBlocks.BLOOD_PIE.get()));
        this.add("slicing_orchid_cake", pastrySlicing(VDItems.ORCHID_CAKE_SLICE.get(), VDBlocks.ORCHID_CAKE.get()));
    }

    private VDAddLootTableModifier addChestLoot(ResourceLocation basePool, ResourceKey<LootTable> insertedPool) {
        return new VDAddLootTableModifier(new LootItemCondition[]{LootTableIdCondition.builder(basePool).build()}, insertedPool);
    }

    private VDAddItemModifier addMobLoot(Item item, boolean onFire, EntityType<?>... entity) {
        LootItemCondition.Builder[] condition = new LootItemCondition.Builder[entity.length];

        for (int i = 0; i < entity.length; i++) {
            condition[i] = LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS,
                    EntityPredicate.Builder.entity().of(entity[i]).build());
        }

        return new VDAddItemModifier(new LootItemCondition[]{
                LootItemEntityPropertyCondition.hasProperties(
                        LootContext.EntityTarget.THIS,
                        EntityPredicate.Builder.entity().flags(EntityFlagsPredicate.Builder.flags().setOnFire(onFire)).build()).build(),
                AnyOfCondition.anyOf(condition).build()
        }, item, 1);
    }

    private VDAddItemModifier addScavengeMobLoot(Item item, EntityType<?>... entity) {
        LootItemCondition.Builder[] condition = new LootItemCondition.Builder[entity.length];

        for (int i = 0; i < entity.length; i++) {
            condition[i] = LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS,
                    EntityPredicate.Builder.entity().of(entity[i]).build());
        }

        return new VDAddItemModifier(new LootItemCondition[]{
                LootItemEntityPropertyCondition.hasProperties(
                        LootContext.EntityTarget.ATTACKER,
                        EntityPredicate.Builder.entity().equipment(EntityEquipmentPredicate.Builder.equipment().mainhand(ItemPredicate.Builder.item().of(ModTags.Items.KNIVES)).build()).build()).build(),
                AnyOfCondition.anyOf(condition).build()
        }, item, 1);
    }

    private VDAddItemModifier addScavengeMobLoot(Item item, TagKey<EntityType<?>> entities) {
        LootItemCondition.Builder condition = LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS,
                EntityPredicate.Builder.entity().of(entities).build());

        return new VDAddItemModifier(new LootItemCondition[]{
                LootItemEntityPropertyCondition.hasProperties(
                        LootContext.EntityTarget.ATTACKER,
                        EntityPredicate.Builder.entity().equipment(EntityEquipmentPredicate.Builder.equipment().mainhand(ItemPredicate.Builder.item().of(ModTags.Items.KNIVES)).build()).build()).build(),
                condition.build()
        }, item, 1);
    }

    private VDAddItemModifier addScavengeMobChanceLoot(Item item, TagKey<EntityType<?>> entities) {
        LootItemCondition.Builder conditionTarget = LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS,
                EntityPredicate.Builder.entity().of(entities).build());
        LootItemCondition.Builder conditionLooting = LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(this.registries, 0.6f, 0.2f);

        return new VDAddItemModifier(new LootItemCondition[]{
                LootItemEntityPropertyCondition.hasProperties(
                        LootContext.EntityTarget.ATTACKER,
                        EntityPredicate.Builder.entity().equipment(EntityEquipmentPredicate.Builder.equipment().mainhand(ItemPredicate.Builder.item().of(ModTags.Items.KNIVES)).build()).build()).build(),
                conditionTarget.build(), conditionLooting.build()
        }, item, 1);
    }

    private VDPastrySlicingModifier pastrySlicing(Item receivedItem, Block... slicedBlock) {
        LootItemCondition.Builder[] condition = new LootItemCondition.Builder[slicedBlock.length];

        for (int i = 0; i < slicedBlock.length; i++) {
            condition[i] = LootItemBlockStatePropertyCondition.hasBlockStateProperties(slicedBlock[i]);
        }
        return new VDPastrySlicingModifier(new LootItemCondition[]{
                MatchTool.toolMatches(ItemPredicate.Builder.item().of(ModTags.Items.KNIVES)).build(),
                AnyOfCondition.anyOf(condition).build()
        }, receivedItem);
    }
}
