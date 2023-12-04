package net.grid.vampiresdelight.common.registry;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.block.*;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.common.block.*;

public class VDBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, VampiresDelight.MODID);

    // Workstations
    public static final RegistryObject<Block> BREWING_BARREL = BLOCKS.register("brewing_barrel",
            () -> new BrewingBarrelBlock(Block.Properties.copy(Blocks.BARREL)));

    // Crop Storage
    public static final RegistryObject<Block> GARLIC_CRATE = BLOCKS.register("garlic_crate",
            () -> new Block(Block.Properties.of(Material.WOOD).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> ORCHID_BAG = BLOCKS.register("orchid_bag",
            () -> new Block(Block.Properties.copy(Blocks.WHITE_WOOL)));

    // Building
    public static final RegistryObject<Block> DARK_SPRUCE_CABINET = BLOCKS.register("dark_spruce_cabinet",
            () -> new CabinetBlock(Block.Properties.copy(Blocks.BARREL)));
    public static final RegistryObject<Block> CURSED_SPRUCE_CABINET = BLOCKS.register("cursed_spruce_cabinet",
            () -> new CabinetBlock(Block.Properties.copy(Blocks.BARREL)));

    // Farming
    public static final RegistryObject<Block> CURSED_FARMLAND = BLOCKS.register("cursed_farmland",
            () -> new CursedFarmlandBlock(Block.Properties.of(Material.DIRT, MaterialColor.TERRACOTTA_BROWN).strength(0.5f, 2.0f).sound(SoundType.GRAVEL)));

    // Pastries
    public static final RegistryObject<Block> BLOOD_PIE = BLOCKS.register("blood_pie",
            () -> new BloodPieBlock(Block.Properties.copy(Blocks.CAKE), VDItems.BLOOD_PIE_SLICE));

    // Wild Crops
    public static final RegistryObject<Block> WILD_GARLIC = BLOCKS.register("wild_garlic",
            () -> new WildCropBlock(MobEffects.BLINDNESS, 8, Block.Properties.copy(Blocks.TALL_GRASS)));

    // Crops
    public static final RegistryObject<Block> VAMPIRE_ORCHID_CROP = BLOCKS.register("vampire_orchid_crop",
            () -> new VampireOrchidCropBlock(Block.Properties.of(Material.PLANT).color(MaterialColor.TERRACOTTA_MAGENTA).instabreak().noCollission().sound(SoundType.CROP)));

    // Feasts
    public static final RegistryObject<Block> WEIRD_JELLY_BLOCK = BLOCKS.register("weird_jelly_block",
            () -> new WeirdJellyBlock(Block.Properties.copy(Blocks.SLIME_BLOCK), VDItems.WEIRD_JELLY, true));
}
