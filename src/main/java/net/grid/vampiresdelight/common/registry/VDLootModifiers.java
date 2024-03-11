package net.grid.vampiresdelight.common.registry;

import com.mojang.serialization.Codec;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.loot.modifier.VDAddItemModifier;
import net.grid.vampiresdelight.common.loot.modifier.VDAddLootTableModifier;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class VDLootModifiers {
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, VampiresDelight.MODID);

    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> ADD_ITEM = LOOT_MODIFIERS.register("add_item", VDAddItemModifier.CODEC);
    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> ADD_LOOT_TABLE = LOOT_MODIFIERS.register("add_loot_table", VDAddLootTableModifier.CODEC);
}
