package net.grid.vampiresdelight;

import net.grid.vampiresdelight.client.ClientSetup;
import net.grid.vampiresdelight.common.CommonSetup;
import net.grid.vampiresdelight.common.Configuration;
import net.grid.vampiresdelight.common.event.PlayerEvents;
import net.grid.vampiresdelight.common.item.VampireCloakItem;
import net.grid.vampiresdelight.common.registry.VDBlockEntityTypes;
import net.grid.vampiresdelight.common.registry.VDBlocks;
import net.grid.vampiresdelight.common.registry.VDEnchantments;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(VampiresDelight.MODID)
public class VampiresDelight {
    public static final String MODID = "vampiresdelight";
    public static final Logger LOGGER = LogManager.getLogger();
    public VampiresDelight() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        eventBus.addListener(CommonSetup::init);
        eventBus.addListener(ClientSetup::init);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Configuration.COMMON_CONFIG);

        VDItems.ITEMS.register(eventBus);
        VDEnchantments.ENCHANTMENTS.register(eventBus);
        VDBlocks.BLOCKS.register(eventBus);
        VDBlockEntityTypes.TILES.register(eventBus);

        MinecraftForge.EVENT_BUS.register(new PlayerEvents());

        MinecraftForge.EVENT_BUS.register(this);
    }
}
