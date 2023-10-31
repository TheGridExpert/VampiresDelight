package net.grid.vampiresdelight.data;

import net.grid.vampiresdelight.VampiresDelight;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = VampiresDelight.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class VDDataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();

        VDBlockTags blockTags = new VDBlockTags(generator, VampiresDelight.MODID, helper);
        generator.addProvider(event.includeServer(), blockTags);
        generator.addProvider(event.includeServer(), new VDRecipes(generator));
        generator.addProvider(event.includeServer(), new VDItemTags(generator, blockTags, VampiresDelight.MODID, helper));
        generator.addProvider(event.includeServer(), new VDBiomeTags(generator, VampiresDelight.MODID, helper));

        VDBlockStates blockStates = new VDBlockStates(generator, helper);
        generator.addProvider(event.includeClient(), blockStates);
        generator.addProvider(event.includeClient(), new VDItemModels(generator, blockStates.models().existingFileHelper));
    }
}
