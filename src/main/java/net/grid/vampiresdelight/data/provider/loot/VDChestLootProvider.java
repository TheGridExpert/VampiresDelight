package net.grid.vampiresdelight.data.provider.loot;

import net.grid.vampiresdelight.common.core.VDEnchantments;
import net.grid.vampiresdelight.common.core.VDItems;
import net.grid.vampiresdelight.common.core.VDLootTables;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
import net.minecraft.world.level.storage.loot.functions.EnchantWithLevelsFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import vectorwing.farmersdelight.common.registry.ModItems;

import java.util.function.BiConsumer;

public class VDChestLootProvider implements LootTableSubProvider {

    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        consumer.accept(VDLootTables.CHEST_COOKING_SPOT, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(10, 20))
                        .add(LootItem.lootTableItem(Items.BREAD).setWeight(15))
                        .add(LootItem.lootTableItem(Items.CARROT).setWeight(10))
                        .add(LootItem.lootTableItem(Items.POTATO).setWeight(15))
                        .add(LootItem.lootTableItem(ModItems.CABBAGE.get()).setWeight(10))
                        .add(LootItem.lootTableItem(ModItems.RICE.get()).setWeight(10))
                        .add(LootItem.lootTableItem(Items.CHARCOAL).setWeight(15))
                )
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.IRON_KNIFE.get()).setWeight(5)
                                .apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(10.0F, 35.0F))))
                        .add(EmptyLootItem.emptyItem().setWeight(5))
                )
        );

        consumer.accept(VDLootTables.VD_CHEST_VAMPIRE_DUNGEON, LootTable.lootTable()
                .withPool(vampiresBiteBookLoot(1, 2))
        );
        consumer.accept(VDLootTables.VD_CHEST_VAMPIRE_HUT, LootTable.lootTable()
                .withPool(vampiresBiteBookLoot(1, 3))
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(5, 8))
                        .add(LootItem.lootTableItem(VDItems.ORCHID_COOKIE.get()).setWeight(10))
                        .add(LootItem.lootTableItem(VDItems.ORCHID_TEA.get()).setWeight(3))
                        .add(LootItem.lootTableItem(VDItems.BAGEL_SANDWICH.get()).setWeight(2))
                        .add(LootItem.lootTableItem(VDItems.HUMAN_EYE.get()).setWeight(5))
                        .add(LootItem.lootTableItem(VDItems.BLOOD_PIE_SLICE.get()).setWeight(3))
                )
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.IRON_KNIFE.get()).setWeight(4)
                                .apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(10.0F, 35.0F))))
                        .add(EmptyLootItem.emptyItem().setWeight(8))
                )
        );
        consumer.accept(VDLootTables.VD_CHEST_ALTAR, LootTable.lootTable()
                .withPool(vampiresBiteBookLoot(1, 2))
        );
        consumer.accept(VDLootTables.VD_CHEST_CRYPT, LootTable.lootTable()
                .withPool(vampiresBiteBookLoot(1, 4))
        );
        consumer.accept(VDLootTables.VD_CHEST_HUNTER_OUTPOST_TENT, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(4, 6))
                        .add(LootItem.lootTableItem(VDItems.HARDTACK.get()).setWeight(5))
                        .add(EmptyLootItem.emptyItem().setWeight(8))
                )
        );
        consumer.accept(VDLootTables.VD_CHEST_HUNTER_OUTPOST_TOWER_FOOD, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(4, 6))
                        .add(LootItem.lootTableItem(VDItems.HARDTACK.get()).setWeight(5))
                        .add(EmptyLootItem.emptyItem().setWeight(8))
                )
        );
        consumer.accept(VDLootTables.VD_CHEST_HUNTER_OUTPOST_TOWER_SPECIAL, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0, 3))
                        .add(LootItem.lootTableItem(VDItems.ALCHEMICAL_COCKTAIL.get()).setWeight(5))
                        .add(EmptyLootItem.emptyItem().setWeight(4))
                )
        );
    }

    public LootPool.Builder vampiresBiteBookLoot(int bookWeight, int emptyWeight) {
        return LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(Items.BOOK).setWeight(bookWeight)
                        .apply(EnchantRandomlyFunction.randomEnchantment().withEnchantment(VDEnchantments.VAMPIRE_BITE.get())))
                .add(EmptyLootItem.emptyItem().setWeight(emptyWeight));
    }
}