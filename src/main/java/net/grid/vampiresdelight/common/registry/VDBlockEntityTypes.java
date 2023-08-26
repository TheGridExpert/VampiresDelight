package net.grid.vampiresdelight.common.registry;

import net.grid.vampiresdelight.VampiresDelight;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.common.block.entity.CabinetBlockEntity;

public class VDBlockEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, VampiresDelight.MODID);

    public static final RegistryObject<BlockEntityType<CabinetBlockEntity>> PANTRY = TILES.register("pantry",
            () -> BlockEntityType.Builder.of(CabinetBlockEntity::new,
                    VDBlocks.DARK_SPRUCE_CABINET.get(), VDBlocks.CURSED_SPRUCE_CABINET.get())
                    .build(null));
    public static final RegistryObject<BlockEntityType<CabinetBlockEntity>> CABINET = TILES.register("cabinet",
            () -> BlockEntityType.Builder.of(CabinetBlockEntity::new,
                            VDBlocks.DARK_SPRUCE_CABINET.get(), VDBlocks.CURSED_SPRUCE_CABINET.get())
                    .build(null));
}
