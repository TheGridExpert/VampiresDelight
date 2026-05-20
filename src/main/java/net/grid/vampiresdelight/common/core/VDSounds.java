package net.grid.vampiresdelight.common.core;

import net.grid.vampiresdelight.VampiresDelight;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class VDSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, VampiresDelight.MODID);

    public static final RegistryObject<SoundEvent> BLOOD_POURED = registerRangeSound("block.blood_poured");
    public static final RegistryObject<SoundEvent> BOTTLE_BREAKS = registerRangeSound("block.bottle_breaks");
    public static final RegistryObject<SoundEvent> METAL_PIPE = registerRangeSound("metal_pipe");
    public static final RegistryObject<SoundEvent> TRIANGLE = registerRangeSound("triangle");
    public static final RegistryObject<SoundEvent> POURING_FULL = registerRangeSound("item.pouring_full");
    public static final RegistryObject<SoundEvent> POURING_SHORT = registerRangeSound("item.pouring_short");
    public static final RegistryObject<SoundEvent> POURING_FINISH = registerRangeSound("item.pouring_finish");
    public static final RegistryObject<SoundEvent> ALCHEMICAL_COCKTAIL_THROW = registerRangeSound("entity.alchemical_cocktail.throw");

    public static RegistryObject<SoundEvent> registerRangeSound(String id) {
        return SOUNDS.register(id, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, id)));
    }

    public static void register(IEventBus eventBus) {
        SOUNDS.register(eventBus);
    }
}
