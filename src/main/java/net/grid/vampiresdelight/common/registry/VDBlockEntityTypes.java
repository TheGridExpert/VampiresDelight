package net.grid.vampiresdelight.common.registry;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.block.entity.BrewingBarrelBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class VDBlockEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, VampiresDelight.MODID);

    public static final RegistryObject<BlockEntityType<BrewingBarrelBlockEntity>> BREWING_BARREL = TILES.register("brewing_barrel",
            () -> BlockEntityType.Builder.of(BrewingBarrelBlockEntity::new, VDBlocks.BREWING_BARREL.get()).build(null));
}
