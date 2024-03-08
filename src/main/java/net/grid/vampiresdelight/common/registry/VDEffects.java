package net.grid.vampiresdelight.common.registry;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.effect.ClothesDissolvingEffect;
import net.grid.vampiresdelight.common.effect.FogVisionEffect;
import net.grid.vampiresdelight.common.effect.BlessingEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class VDEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, VampiresDelight.MODID);

    public static final RegistryObject<MobEffect> BLESSING = EFFECTS.register("blessing", BlessingEffect::new);
    public static final RegistryObject<MobEffect> CLOTHES_DISSOLVING = EFFECTS.register("clothes_dissolving", ClothesDissolvingEffect::new);
    public static final RegistryObject<MobEffect> FOG_VISION = EFFECTS.register("fog_vision", FogVisionEffect::new);
}
