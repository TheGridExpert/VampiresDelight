package net.grid.vampiresdelight.common;

import net.grid.vampiresdelight.common.registry.VDItems;
import net.grid.vampiresdelight.common.world.WildGarlicGeneration;
import net.grid.vampiresdelight.integration.ModLoad;
import net.grid.vampiresdelight.integration.create.VDPotatoProjectileTypes;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.Optional;

public class CommonSetup {
    public static void init(final FMLCommonSetupEvent event) {
        registerModIntegrations();
        event.enqueueWork(() -> {
            registerCompostableItems();
            WildGarlicGeneration.registerWildGarlicGeneration();
        });
    }

    public static void registerCompostableItems() {
        ComposterBlock.COMPOSTABLES.put(de.teamlapen.vampirism.core.ModItems.ITEM_GARLIC.get(), 0.65F);
        ComposterBlock.COMPOSTABLES.put(VDItems.WILD_GARLIC.get(), 0.65F);
    }

    public static void registerModIntegrations() {
        if (ModLoad.CREATE.isLoaded()) {
            VDPotatoProjectileTypes.register();
        }
    }
}
