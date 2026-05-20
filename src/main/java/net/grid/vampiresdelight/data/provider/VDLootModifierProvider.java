package net.grid.vampiresdelight.data.provider;

import de.teamlapen.vampirism.core.ModLootTables;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.core.VDBlocks;
import net.grid.vampiresdelight.common.core.VDItems;
import net.grid.vampiresdelight.common.core.VDLootTables;
import net.grid.vampiresdelight.common.tag.VDEntityTags;
import net.grid.vampiresdelight.common.world.block.FactionCandleCakeBlock;
import net.minecraft.advancements.critereon.EntityEquipmentPredicate;
import net.minecraft.advancements.critereon.EntityFlagsPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;
import net.minecraftforge.registries.ForgeRegistries;
import vectorwing.farmersdelight.common.loot.modifier.AddItemModifier;
import vectorwing.farmersdelight.common.loot.modifier.AddLootTableModifier;
import vectorwing.farmersdelight.common.loot.modifier.PastrySlicingModifier;
import vectorwing.farmersdelight.common.tag.ModTags;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VDLootModifierProvider extends GlobalLootModifierProvider {

    public VDLootModifierProvider(PackOutput output) {
        super(output, VampiresDelight.MODID);
    }

    @Override
    protected void start() {
        injectLoot(ModLootTables.CHEST_VAMPIRE_DUNGEON, VDLootTables.VD_CHEST_VAMPIRE_DUNGEON);
        injectLoot(ModLootTables.CHEST_VAMPIRE_HUT, VDLootTables.VD_CHEST_VAMPIRE_HUT);
        injectLoot(ModLootTables.CHEST_VAMPIRE_ALTAR, VDLootTables.VD_CHEST_ALTAR);
        injectLoot(ModLootTables.CHEST_CRYPT, VDLootTables.VD_CHEST_CRYPT);
        injectLoot(ModLootTables.CHEST_HUNTER_OUTPOST_TENT, VDLootTables.VD_CHEST_HUNTER_OUTPOST_TENT);
        injectLoot(ModLootTables.CHEST_HUNTER_OUTPOST_TOWER_FOOD, VDLootTables.VD_CHEST_HUNTER_OUTPOST_TOWER_FOOD);
        injectLoot(ModLootTables.CHEST_HUNTER_OUTPOST_TOWER_SPECIAL, VDLootTables.VD_CHEST_HUNTER_OUTPOST_TOWER_SPECIAL);

        add("add_mob_loot_bat", addItemOnPlayerKill(VDItems.RAW_BAT.get(), false, EntityType.BAT));
        add("add_mob_loot_grilled_bat", addItemOnPlayerKill(VDItems.GRILLED_BAT.get(), true, EntityType.BAT));

        add("scavenging_eyes_from_humans", addItemOnKnifeKill(VDItems.HUMAN_EYE.get(), 0.75F, VDEntityTags.DROPS_HUMAN_EYE));

        add("slicing_orchid_cake", pastrySlicing(VDItems.ORCHID_CAKE_SLICE.get(), VDBlocks.ORCHID_CAKE.get()));
        add("slicing_blood_pie", pastrySlicing(VDItems.BLOOD_PIE_SLICE.get(), VDBlocks.BLOOD_PIE.get()));

        FactionCandleCakeBlock.getCandleCakesByCake().forEach((cake, candleCakes) -> {
            String cakeName = Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(cake)).getPath();
            add("slicing_" + cakeName + "_with_candle", candleCakeSlicing(cake.getCakeSlice().getItem(), candleCakes));
        });
    }

    private void injectLoot(ResourceLocation targetPool, ResourceLocation injectPool) {
        add("add_loot_" + targetPool.getPath().substring(targetPool.getPath().lastIndexOf("/") + 1), addNewLootPool(targetPool, injectPool));
    }

    private AddLootTableModifier addNewLootPool(ResourceLocation targetPool, ResourceLocation injectPool) {
        return new AddLootTableModifier(new LootItemCondition[] {LootTableIdCondition.builder(targetPool).build()}, injectPool);
    }

    private AddItemModifier addItemOnPlayerKill(Item item, Boolean onFire, EntityType<?>... entity) {
        return addItemOnPlayerKill(item, onFire, 1.0F, entity);
    }

    private AddItemModifier addItemOnPlayerKill(Item item, Boolean onFire, float chance, EntityType<?>... entity) {
        LootItemCondition.Builder[] entityConditions = new LootItemCondition.Builder[entity.length];
        for (int i = 0; i < entity.length; i++) {
            entityConditions[i] = LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS,
                    EntityPredicate.Builder.entity().of(entity[i]).build());
        }
        List<LootItemCondition> conditions = new ArrayList<>();
        conditions.add(entityConditions.length > 1 ? AnyOfCondition.anyOf(entityConditions).build() : entityConditions[0].build());
        conditions.add(LootItemKilledByPlayerCondition.killedByPlayer().build());

        if (onFire != null) {
            conditions.add(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().flags(EntityFlagsPredicate.Builder.flags().setOnFire(onFire).build())).build());
        }

        if (chance < 1.0F) {
            conditions.add(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(chance, 0.01F).build());
        }

        return new AddItemModifier(conditions.toArray(LootItemCondition[]::new), item, 1);
    }

    private AddItemModifier addItemOnKnifeKill(Item item, float chance, TagKey<EntityType<?>> entity) {
        LootItemCondition.Builder entityCondition = LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS,
                EntityPredicate.Builder.entity().of(entity).build());

        List<LootItemCondition> conditions = new ArrayList<>();
        conditions.add(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.KILLER, EntityPredicate.Builder.entity().equipment(EntityEquipmentPredicate.Builder.equipment().mainhand(ItemPredicate.Builder.item().of(ModTags.Items.KNIVES).build()).build()).build()).build());
        conditions.add(entityCondition.build());

        if (chance < 1.0F) {
            conditions.add(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(chance, 0.1F).build());
        }

        return new AddItemModifier(conditions.toArray(LootItemCondition[]::new), item, 1);
    }

    private AddItemModifier candleCakeSlicing(Item sliceItem, List<FactionCandleCakeBlock> candleCakes) {
        LootItemCondition.Builder[] conditions = new LootItemCondition.Builder[candleCakes.size()];
        for (int i = 0; i < candleCakes.size(); i++) {
            conditions[i] = LootItemBlockStatePropertyCondition.hasBlockStateProperties(candleCakes.get(i));
        }
        return new AddItemModifier(new LootItemCondition[]{
                MatchTool.toolMatches(ItemPredicate.Builder.item().of(ModTags.Items.KNIVES)).build(),
                AnyOfCondition.anyOf(conditions).build()
        }, sliceItem, 7);
    }

    private PastrySlicingModifier pastrySlicing(Item receivedItem, Block slicedBlock) {
        return new PastrySlicingModifier(new LootItemCondition[] {
                MatchTool.toolMatches(ItemPredicate.Builder.item().of(ModTags.Items.KNIVES)).build(),
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(slicedBlock).build()
        }, receivedItem);
    }
}