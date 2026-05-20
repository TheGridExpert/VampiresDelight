package net.grid.vampiresdelight.common.core;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.world.effect.DissolvingEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class VDEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, VampiresDelight.MODID);

    public static final RegistryObject<MobEffect> FOG_VISION = EFFECTS.register("fog_vision", () -> new MobEffect(MobEffectCategory.BENEFICIAL, 0x876996));
    public static final RegistryObject<MobEffect> CONSECRATION = EFFECTS.register("consecration", () -> new MobEffect(MobEffectCategory.BENEFICIAL, 0xf2be5c));
    public static final RegistryObject<DissolvingEffect> DISSOLVING = EFFECTS.register("dissolving", () -> new DissolvingEffect(MobEffectCategory.HARMFUL, 0xceb180));



    public static void register(IEventBus eventBus) {
        EFFECTS.register(eventBus);
    }
}
