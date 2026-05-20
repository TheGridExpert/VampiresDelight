package net.grid.vampiresdelight;

import net.grid.vampiresdelight.common.config.VDClientConfig;
import net.grid.vampiresdelight.common.config.VDCommonConfig;
import net.grid.vampiresdelight.common.core.VDRegistryManager;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(VampiresDelight.MODID)
public class VampiresDelight {

    public static final String MODID = "vampiresdelight";

    public VampiresDelight(FMLJavaModLoadingContext context) {
        IEventBus eventBus = context.getModEventBus();

        context.registerConfig(ModConfig.Type.COMMON, VDCommonConfig.SPEC);
        context.registerConfig(ModConfig.Type.CLIENT, VDClientConfig.SPEC);

        VDRegistryManager.setupRegistries(eventBus);

        eventBus.addListener(VDRegistryManager::commonSetup);

        ForgeMod.enableMilkFluid();

        MinecraftForge.EVENT_BUS.register(this);
    }
}
