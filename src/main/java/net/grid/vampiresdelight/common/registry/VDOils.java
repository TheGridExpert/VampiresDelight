package net.grid.vampiresdelight.common.registry;

import de.teamlapen.vampirism.api.VampirismRegistries;
import de.teamlapen.vampirism.api.items.oil.IOil;
import de.teamlapen.vampirism.items.oil.EffectWeaponOil;
import net.grid.vampiresdelight.VampiresDelight;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class VDOils {
    public static final DeferredRegister<IOil> OILS = DeferredRegister.create(VampirismRegistries.OILS_ID, VampiresDelight.MODID);

    public static final RegistryObject<EffectWeaponOil> FOG_VISION = OILS.register("fog_vision",
            () -> new EffectWeaponOil(VDEffects.FOG_VISION.get(), 200, 20));
}
