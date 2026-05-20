package net.grid.vampiresdelight.common.core;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.world.blockentity.WineShelfBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.common.registry.ModBlockEntityTypes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VDBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, VampiresDelight.MODID);

    public static final RegistryObject<BlockEntityType<WineShelfBlockEntity>> WINE_SHELF = BLOCK_ENTITY_TYPES.register("wine_shelf",
            () -> BlockEntityType.Builder.of(WineShelfBlockEntity::new,
                    VDBlocks.OAK_WINE_SHELF.get(),
                    VDBlocks.SPRUCE_WINE_SHELF.get(),
                    VDBlocks.BIRCH_WINE_SHELF.get(),
                    VDBlocks.JUNGLE_WINE_SHELF.get(),
                    VDBlocks.ACACIA_WINE_SHELF.get(),
                    VDBlocks.DARK_OAK_WINE_SHELF.get(),
                    VDBlocks.MANGROVE_WINE_SHELF.get(),
                    VDBlocks.CHERRY_WINE_SHELF.get(),
                    VDBlocks.BAMBOO_WINE_SHELF.get(),
                    VDBlocks.CRIMSON_WINE_SHELF.get(),
                    VDBlocks.WARPED_WINE_SHELF.get(),
                    VDBlocks.DARK_SPRUCE_WINE_SHELF.get(),
                    VDBlocks.CURSED_SPRUCE_WINE_SHELF.get())
                    .build(null));

    public static void addToExistingBlockEntities() {
        addValidBlocks(ModBlockEntityTypes.STOVE.get(),
                VDBlocks.DARK_STONE_STOVE.get());

        List<Block> cabinets = new ArrayList<>(List.of(
                VDBlocks.DARK_SPRUCE_CABINET.get(),
                VDBlocks.CURSED_SPRUCE_CABINET.get()
        ));
        VDBlocks.JACARANDA_CABINET.ifPresent(cabinets::add);
        VDBlocks.MAGIC_CABINET.ifPresent(cabinets::add);
        addValidBlocks(ModBlockEntityTypes.CABINET.get(), cabinets);

        List<Block> wineShelves = new ArrayList<>();
        VDBlocks.JACARANDA_WINE_SHELF.ifPresent(wineShelves::add);
        VDBlocks.MAGIC_WINE_SHELF.ifPresent(wineShelves::add);
        addValidBlocks(WINE_SHELF.get(), wineShelves);
    }

    private static void addValidBlocks(BlockEntityType<?> type, Block... blocks) {
        addValidBlocks(type, Arrays.asList(blocks));
    }

    private static void addValidBlocks(BlockEntityType<?> type, Collection<? extends Block> blocks) {
        if (blocks.isEmpty()) return;
        Set<Block> updated = new HashSet<>(type.validBlocks);
        updated.addAll(blocks);
        type.validBlocks = updated;
    }

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITY_TYPES.register(eventBus);
    }
}