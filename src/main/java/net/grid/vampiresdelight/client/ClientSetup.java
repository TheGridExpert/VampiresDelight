package net.grid.vampiresdelight.client;

import net.grid.vampiresdelight.client.gui.BrewingBarrelScreen;
import net.grid.vampiresdelight.common.registry.VDMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientSetup {
    public static void init(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> MenuScreens.register(VDMenuTypes.BREWING_BARREL.get(), BrewingBarrelScreen::new));
    }
}
