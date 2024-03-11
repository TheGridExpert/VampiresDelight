package net.grid.vampiresdelight.data.loot;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.registry.VDEnchantments;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.grid.vampiresdelight.common.registry.VDLootTables;
import net.grid.vampiresdelight.common.registry.VDPotions;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
import net.minecraft.world.level.storage.loot.functions.EnchantWithLevelsFunction;
import net.minecraft.world.level.storage.loot.functions.SetPotionFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.registry.ModItems;

import java.util.function.BiConsumer;

public class VDChestLootTables implements LootTableSubProvider {
    @Override
    public void generate(@NotNull BiConsumer<ResourceLocation, LootTable.Builder> output) {
        lootModifiers(output);
        lootChests(output);
    }

    public void lootChests(BiConsumer<ResourceLocation, LootTable.Builder> output) {
        output.accept(VDLootTables.CHEST_LOST_CARRIAGE, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(9, 25))
                        .add(LootItem.lootTableItem(Items.BREAD).setWeight(10))
                        .add(LootItem.lootTableItem(de.teamlapen.vampirism.core.ModItems.GARLIC_BREAD.get()).setWeight(10))
                        .add(LootItem.lootTableItem(de.teamlapen.vampirism.core.ModItems.ITEM_GARLIC.get()).setWeight(10))
                        .add(LootItem.lootTableItem(Items.COBWEB).setWeight(20))
                        .add(LootItem.lootTableItem(Items.WHEAT).setWeight(15))
                        .add(LootItem.lootTableItem(Items.POTATO).setWeight(10))
                        .add(LootItem.lootTableItem(Items.CARROT).setWeight(10))
                        .add(LootItem.lootTableItem(Items.APPLE).setWeight(10))
                        .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(5))
                        .add(LootItem.lootTableItem(Items.EMERALD).setWeight(5))
                )
        );
    }

    public void lootModifiers(BiConsumer<ResourceLocation, LootTable.Builder> output) {
        output.accept(new ResourceLocation(VampiresDelight.MODID, "chests/vd_vampire_dungeon"), LootTable.lootTable()
                .withPool(vampiresBiteBookLoot(3, 8))
                .withPool(clothesDissolvingLoot())
        );
        output.accept(new ResourceLocation(VampiresDelight.MODID, "chests/vd_vampire_hut"), LootTable.lootTable()
                .withPool(vampiresBiteBookLoot(3, 8))
                .withPool(clothesDissolvingLoot())
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(5, 8))
                        .add(LootItem.lootTableItem(VDItems.ORCHID_COOKIE.get()).setWeight(10))
                        .add(LootItem.lootTableItem(VDItems.ORCHID_TEA.get()).setWeight(3))
                        .add(LootItem.lootTableItem(VDItems.BAGEL_SANDWICH.get()).setWeight(2))
                        .add(LootItem.lootTableItem(VDItems.HUMAN_EYE.get()).setWeight(5))
                        .add(LootItem.lootTableItem(VDItems.BLOOD_PIE_SLICE.get()).setWeight(3))
                )
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(ModItems.IRON_KNIFE.get()).setWeight(4)
                                .apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(10.0F, 35.0F)).allowTreasure()))
                        .add(EmptyLootItem.emptyItem().setWeight(8))
                )
        );
        output.accept(new ResourceLocation(VampiresDelight.MODID, "chests/vd_altar"), LootTable.lootTable()
                .withPool(vampiresBiteBookLoot(6, 4))
                .withPool(clothesDissolvingLoot())
        );
        output.accept(new ResourceLocation(VampiresDelight.MODID, "chests/vd_crypt"), LootTable.lootTable()
                .withPool(vampiresBiteBookLoot(3, 4))
                .withPool(clothesDissolvingLoot())
        );
        output.accept(new ResourceLocation(VampiresDelight.MODID, "chests/vd_hunter_outpost_tent"), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(5, 8))
                        .add(LootItem.lootTableItem(VDItems.HARDTACK.get()).setWeight(5))
                        .add(EmptyLootItem.emptyItem().setWeight(4))
                )
        );
        output.accept(new ResourceLocation(VampiresDelight.MODID, "chests/vd_hunter_outpost_tower_food"), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(5, 8))
                        .add(LootItem.lootTableItem(VDItems.HARDTACK.get()).setWeight(5))
                        .add(EmptyLootItem.emptyItem().setWeight(4))
                )
        );
        output.accept(new ResourceLocation(VampiresDelight.MODID, "chests/vd_hunter_outpost_tower_special"), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(UniformGenerator.between(0, 3))
                        .add(LootItem.lootTableItem(VDItems.ALCHEMICAL_COCKTAIL.get()).setWeight(5))
                        .add(EmptyLootItem.emptyItem().setWeight(4))
                )
        );
    }

    public static LootPool.Builder vampiresBiteBookLoot(int bookWeight, int emptyWeight) {
        return LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(Items.BOOK).setWeight(bookWeight)
                        .apply((new EnchantRandomlyFunction.Builder()).withEnchantment(VDEnchantments.VAMPIRE_BITE.get())))
                .add(EmptyLootItem.emptyItem().setWeight(emptyWeight));
    }

    public static LootPool.Builder clothesDissolvingLoot() {
        return LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(Items.SPLASH_POTION).setWeight(1)
                        .apply(SetPotionFunction.setPotion(VDPotions.CLOTHES_DISSOLVING.get())))
                .add(EmptyLootItem.emptyItem().setWeight(49));
    }
}
