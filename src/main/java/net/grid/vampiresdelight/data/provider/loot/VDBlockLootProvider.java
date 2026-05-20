package net.grid.vampiresdelight.data.provider.loot;

import de.teamlapen.vampirism.core.ModBlocks;
import de.teamlapen.vampirism.core.ModItems;
import de.teamlapen.vampirism.mixin.VanillaBlockLootAccessor;
import net.grid.vampiresdelight.common.core.VDBlocks;
import net.grid.vampiresdelight.common.core.VDItems;
import net.grid.vampiresdelight.common.world.block.BarStoolBlock;
import net.grid.vampiresdelight.common.world.block.FactionCandleCakeBlock;
import net.grid.vampiresdelight.common.world.block.OrchidCropBlock;
import net.grid.vampiresdelight.common.world.block.WineShelfBlock;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.ApplyExplosionDecay;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.InvertedLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.common.loot.CanToolPerformAction;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.common.block.FeastBlock;

public class VDBlockLootProvider extends BlockLootSubProvider {

    public VDBlockLootProvider() {
        super(VanillaBlockLootAccessor.getEXPLOSION_RESISTANT(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        dropSelf(VDBlocks.DARK_STONE_STOVE.get());
        dropSelf(VDBlocks.GARLIC_CRATE.get());
        dropSelf(VDBlocks.ORCHID_BAG.get());
        dropSelf(VDBlocks.SPIRIT_LANTERN.get());
        dropNamedContainer(VDBlocks.DARK_SPRUCE_CABINET.get());
        dropNamedContainer(VDBlocks.CURSED_SPRUCE_CABINET.get());
        dropNamedContainer(VDBlocks.JACARANDA_CABINET.getOrThrow());
        dropNamedContainer(VDBlocks.MAGIC_CABINET.getOrThrow());
        WineShelfBlock.getAllShelfBlocks().forEach(this::dropSelf);
        BarStoolBlock.getBarStoolBlocks().forEach(this::dropSelf);
        wildCropNoSeeds(VDBlocks.WILD_GARLIC.get(), ModItems.ITEM_GARLIC.get());
        add(VDBlocks.ORCHID_CAKE.get(), noDrop());
        FactionCandleCakeBlock.getAllCandleCakes().forEach(block -> add(block, createCandleCakeDrops((block).getCandleBlock())));
        add(VDBlocks.BLOOD_PIE.get(), noDrop());
        dropOther(VDBlocks.CURSED_FARMLAND.get(), ModBlocks.CURSED_EARTH.get());
        dropSelf(VDBlocks.BLOODY_SOIL.get());
        dropOther(VDBlocks.BLOODY_SOIL_FARMLAND.get(), VDBlocks.BLOODY_SOIL.get());
        dropSelf(VDBlocks.BLACK_MUSHROOM.get());
        dropPottedContents(VDBlocks.POTTED_BLACK_MUSHROOM.get());
        add(VDBlocks.BLACK_MUSHROOM_BLOCK.get(), block -> createMushroomBlockDrop(block, VDItems.BLACK_MUSHROOM.get()));
        dropWhenSilkTouch(VDBlocks.BLACK_MUSHROOM_STEM.get());
        add(VDBlocks.VAMPIRE_ORCHID_CROP.get(), block -> createOrchidDrops(block, VDItems.ORCHID_PETALS.get(), VDItems.ORCHID_SEEDS.get(), LootItemBlockStatePropertyCondition.hasBlockStateProperties(VDBlocks.VAMPIRE_ORCHID_CROP.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(OrchidCropBlock.AGE, OrchidCropBlock.MAX_AGE))));
        dropSelf(VDBlocks.DANDELION_BEER_BOTTLE_PLACED.get());
        dropSelf(VDBlocks.BLOOD_WINE_BOTTLE_PLACED.get());
        dropFeastBlock(VDBlocks.WEIRD_JELLY_BLOCK.get(), 4);
    }

    protected void dropNamedContainer(Block block) {
        add(block, this::createNameableBlockEntityTable);
    }

    protected void wildCropNoSeeds(Block wildCrop, Item crop) {
        add(wildCrop, block -> LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(AlternativesEntry.alternatives(LootItem.lootTableItem(block).when(CanToolPerformAction.canToolPerformAction(ToolActions.SHEARS_HARVEST)), LootItem.lootTableItem(crop).apply(ApplyExplosionDecay.explosionDecay()).apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE, 2))))));
    }

    protected LootTable.Builder createOrchidDrops(Block cropBlock, Item grownCropItem, Item seedsItem, LootItemCondition.Builder dropGrownCropCondition) {
        return applyExplosionDecay(cropBlock, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .when(dropGrownCropCondition)
                        .add(LootItem.lootTableItem(grownCropItem)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))))
                .withPool(LootPool.lootPool()
                        .when(dropGrownCropCondition)
                        .add(LootItem.lootTableItem(seedsItem)))
                .withPool(LootPool.lootPool()
                        .when(dropGrownCropCondition)
                        .when(LootItemRandomChanceCondition.randomChance(0.5F))
                        .add(LootItem.lootTableItem(seedsItem)))
                .withPool(LootPool.lootPool()
                        .when(dropGrownCropCondition)
                        .when(LootItemRandomChanceCondition.randomChance(0.3F))
                        .add(LootItem.lootTableItem(ModBlocks.CURSED_ROOTS.get())))
                .withPool(LootPool.lootPool()
                        .when(InvertedLootItemCondition.invert(dropGrownCropCondition))
                        .add(LootItem.lootTableItem(seedsItem))));
    }

    protected void dropFeastBlock(Block block, int servings) {
        add(block, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(block))
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(FeastBlock.SERVINGS, servings))))
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(Items.BOWL))
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(FeastBlock.SERVINGS, servings)).invert())));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return VDBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).toList();
    }
}
