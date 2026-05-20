package net.grid.vampiresdelight.common.core;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class VDRegistryManager {

    public static void setupRegistries(IEventBus eventBus) {
        VDEntityTypes.register(eventBus);
        VDFluids.register(eventBus);
        VDParticles.register(eventBus);
        VDEffects.register(eventBus);
        VDPotions.register(eventBus);
        VDOils.register(eventBus);
        VDItems.register(eventBus);
        VDBlocks.register(eventBus);
        VDBlockEntities.register(eventBus);
        VDEnchantments.register(eventBus);
        VDFeatures.register(eventBus);
        VDCreativeTabs.register(eventBus);
        VDSounds.register(eventBus);
        VDLootConditions.register(eventBus);
    }

    public static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(VDBlockEntities::addToExistingBlockEntities);
        event.enqueueWork(VDItems::registerCompostables);
        event.enqueueWork(VDPotions::registerPotionMixes);
        event.enqueueWork(VDAdvancementTriggers::register);
    }
}
