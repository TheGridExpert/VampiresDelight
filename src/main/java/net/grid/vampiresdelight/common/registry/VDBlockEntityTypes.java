package net.grid.vampiresdelight.common.registry;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.block.entity.BrewingBarrelBlockEntity;
import net.grid.vampiresdelight.common.block.entity.WineShelfBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class VDBlockEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, VampiresDelight.MODID);

    public static final RegistryObject<BlockEntityType<BrewingBarrelBlockEntity>> BREWING_BARREL = TILES.register("brewing_barrel",
            () -> BlockEntityType.Builder.of(BrewingBarrelBlockEntity::new, VDBlocks.BREWING_BARREL.get()).build(null));
    public static final RegistryObject<BlockEntityType<WineShelfBlockEntity>> WINE_SHELF = TILES.register("wine_shelf",
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
                            VDBlocks.DARK_SPRUCE_WINE_SHELF.get())
                    .build(null));
}
