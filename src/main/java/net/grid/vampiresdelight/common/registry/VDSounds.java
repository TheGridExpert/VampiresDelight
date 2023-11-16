package net.grid.vampiresdelight.common.registry;

import net.grid.vampiresdelight.VampiresDelight;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class VDSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, VampiresDelight.MODID);

    // Placeholder
    public static final RegistryObject<SoundEvent> METAL_PIPE = SOUNDS.register("placeholder.metal_pipe",
            () -> new SoundEvent(new ResourceLocation(VampiresDelight.MODID, "placeholder.metal_pipe")));

    // World
    public static final RegistryObject<SoundEvent> TRIANGLE = SOUNDS.register("world.triangle",
            () -> new SoundEvent(new ResourceLocation(VampiresDelight.MODID, "world.triangle")));
    // https://pixabay.com/sound-effects/triangle-29016/
}
