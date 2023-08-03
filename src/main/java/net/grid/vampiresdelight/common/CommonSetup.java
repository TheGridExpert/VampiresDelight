package net.grid.vampiresdelight.common;

import net.grid.vampiresdelight.common.registry.ModItems;
import net.grid.vampiresdelight.common.world.WildGarlicGeneration;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class CommonSetup {
    public static void init(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            registerCompostableItems();
            WildGarlicGeneration.registerWildGarlicGeneration();
        });
    }
    public static void registerCompostableItems() {
        ComposterBlock.COMPOSTABLES.put(de.teamlapen.vampirism.core.ModItems.ITEM_GARLIC.get(), 0.65F);
        ComposterBlock.COMPOSTABLES.put(ModItems.WILD_GARLIC.get(), 0.65F);
    }
}
