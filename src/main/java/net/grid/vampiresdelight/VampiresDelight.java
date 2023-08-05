package net.grid.vampiresdelight;

import com.mojang.logging.LogUtils;
import net.grid.vampiresdelight.common.CommonSetup;
import net.grid.vampiresdelight.common.Configuration;
import net.grid.vampiresdelight.common.event.PlayerEvents;
import net.grid.vampiresdelight.common.registry.VDBlocks;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(VampiresDelight.MODID)
public class VampiresDelight {
    public static final String MODID = "vampiresdelight";
    private static final Logger LOGGER = LogUtils.getLogger();
    public VampiresDelight() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        eventBus.addListener(CommonSetup::init);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Configuration.COMMON_CONFIG);

        VDItems.register(eventBus);
        VDBlocks.register(eventBus);

        MinecraftForge.EVENT_BUS.register(new PlayerEvents());

        MinecraftForge.EVENT_BUS.register(this);
    }
}
