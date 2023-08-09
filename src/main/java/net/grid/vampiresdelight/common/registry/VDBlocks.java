package net.grid.vampiresdelight.common.registry;

import net.grid.vampiresdelight.VampiresDelight;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.common.block.CabinetBlock;
import vectorwing.farmersdelight.common.block.WildCropBlock;

public class VDBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, VampiresDelight.MODID);


    // Neutral
    public static final RegistryObject<Block> GARLIC_CRATE = BLOCKS.register("garlic_crate",
            () -> new Block(Block.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> WILD_GARLIC = BLOCKS.register("wild_garlic",
            () -> new WildCropBlock(MobEffects.BLINDNESS, 8, Block.Properties.copy(Blocks.TALL_GRASS)));

    // Vampire
    public static final RegistryObject<Block> CURSED_SPRUCE_CABINET = BLOCKS.register("cursed_spruce_cabinet",
            () -> new CabinetBlock(Block.Properties.copy(Blocks.BARREL)));



    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
