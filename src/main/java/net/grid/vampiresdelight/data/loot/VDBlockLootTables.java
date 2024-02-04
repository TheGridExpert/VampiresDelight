package net.grid.vampiresdelight.data.loot;

import de.teamlapen.vampirism.core.ModBlocks;
import net.grid.vampiresdelight.common.registry.VDBlocks;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class VDBlockLootTables extends BlockLootSubProvider {
    private final Set<Block> generatedLootTables = new HashSet<>();

    public VDBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        dropSelf(VDBlocks.BREWING_BARREL.get());

        dropSelf(VDBlocks.GARLIC_CRATE.get());
        dropSelf(VDBlocks.ORCHID_BAG.get());

        dropNamedContainer(VDBlocks.DARK_SPRUCE_CABINET.get());
        dropNamedContainer(VDBlocks.CURSED_SPRUCE_CABINET.get());

        dropSelf(VDBlocks.OAK_WINE_SHELF.get());
        dropSelf(VDBlocks.SPRUCE_WINE_SHELF.get());
        dropSelf(VDBlocks.BIRCH_WINE_SHELF.get());
        dropSelf(VDBlocks.JUNGLE_WINE_SHELF.get());
        dropSelf(VDBlocks.ACACIA_WINE_SHELF.get());
        dropSelf(VDBlocks.DARK_OAK_WINE_SHELF.get());
        dropSelf(VDBlocks.MANGROVE_WINE_SHELF.get());
        dropSelf(VDBlocks.CHERRY_WINE_SHELF.get());
        dropSelf(VDBlocks.BAMBOO_WINE_SHELF.get());
        dropSelf(VDBlocks.CRIMSON_WINE_SHELF.get());
        dropSelf(VDBlocks.WARPED_WINE_SHELF.get());
        dropSelf(VDBlocks.CURSED_SPRUCE_WINE_SHELF.get());
        dropSelf(VDBlocks.DARK_SPRUCE_WINE_SHELF.get());

        dropOther(VDBlocks.CURSED_FARMLAND.get(), ModBlocks.CURSED_EARTH.get());
    }

    protected void dropNamedContainer(Block block) {
        add(block, this::createNameableBlockEntityTable);
    }

    @Override
    protected void add(@NotNull Block block, LootTable.@NotNull Builder builder) {
        this.generatedLootTables.add(block);
        this.map.put(block.getLootTable(), builder);
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return generatedLootTables;
    }
}
