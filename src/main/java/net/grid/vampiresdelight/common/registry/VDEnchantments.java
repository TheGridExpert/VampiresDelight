package net.grid.vampiresdelight.common.registry;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.item.enchantment.VampireBiteEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class VDEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, VampiresDelight.MODID);

    public static final RegistryObject<Enchantment> VAMPIRE_BITE = ENCHANTMENTS.register("vampire_bite",
            () -> new VampireBiteEnchantment(Enchantment.Rarity.RARE));
}
