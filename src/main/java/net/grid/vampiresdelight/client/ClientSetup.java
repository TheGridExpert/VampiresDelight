package net.grid.vampiresdelight.client;

import net.grid.vampiresdelight.common.registry.VDBlocks;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientSetup {
    public static void init(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            //registerRenderLayers();
            registerModIntegrations();
        });
    }
    /*
    public static void registerRenderLayers() {
        ItemBlockRenderTypes.setRenderLayer(VDBlocks.WEIRD_JELLY_BLOCK.get(), RenderType.translucent());
    }
     */
    public static void registerModIntegrations() {
        /**
        if (ModList.get().isLoaded("appleskin")) {
            MinecraftForge.EVENT_BUS.register(new AppleSkinEventHandler());
        }
         */
    }
}
