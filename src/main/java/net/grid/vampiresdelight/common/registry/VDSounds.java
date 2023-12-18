package net.grid.vampiresdelight.common.registry;

import net.grid.vampiresdelight.VampiresDelight;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class VDSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, VampiresDelight.MODID);

    // World
    public static final RegistryObject<SoundEvent> TRIANGLE = SOUNDS.register("world.triangle",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(VampiresDelight.MODID, "world.triangle")));
    // https://pixabay.com/sound-effects/triangle-29016/
}
