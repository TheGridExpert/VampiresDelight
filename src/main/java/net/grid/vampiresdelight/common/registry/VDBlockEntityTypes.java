package net.grid.vampiresdelight.common.registry;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.block.entity.WineShelfBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import vectorwing.farmersdelight.common.registry.ModBlockEntityTypes;

import java.util.function.Supplier;

@EventBusSubscriber(modid = VampiresDelight.MODID, bus = EventBusSubscriber.Bus.MOD)
public class VDBlockEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, VampiresDelight.MODID);

    public static final Supplier<BlockEntityType<WineShelfBlockEntity>> WINE_SHELF = BLOCK_ENTITIES.register("wine_shelf",
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
                            VDBlocks.CURSED_SPRUCE_WINE_SHELF.get(),
                            VDBlocks.DARK_SPRUCE_WINE_SHELF.get(),
                            VDBlocks.JACARANDA_WINE_SHELF.get(),
                            VDBlocks.MAGIC_WINE_SHELF.get())
                    .build(null));

    @SubscribeEvent
    public static void addCabinetsBlockEntities(BlockEntityTypeAddBlocksEvent event) {
        event.modify(ModBlockEntityTypes.CABINET.get(),
                VDBlocks.CURSED_SPRUCE_CABINET.get(),
                VDBlocks.DARK_SPRUCE_CABINET.get(),
                VDBlocks.JACARANDA_CABINET.get(),
                VDBlocks.MAGIC_CABINET.get()
        );
        event.modify(ModBlockEntityTypes.STOVE.get(),
                VDBlocks.DARK_STONE_STOVE.get());
    }
}
