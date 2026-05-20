package net.grid.vampiresdelight.common.core;

import de.teamlapen.vampirism.items.VampirismVampireSwordItem;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.world.enchantment.VampireBiteEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class VDEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, VampiresDelight.MODID);

    public static final EnchantmentCategory VAMPIRE_SWORD = EnchantmentCategory.create("vampire_sword", item -> item instanceof VampirismVampireSwordItem);

    public static final RegistryObject<Enchantment> VAMPIRE_BITE = ENCHANTMENTS.register("vampire_bite", () -> new VampireBiteEnchantment(Enchantment.Rarity.VERY_RARE));

    public static void register(IEventBus eventBus) {
        ENCHANTMENTS.register(eventBus);
    }
}
