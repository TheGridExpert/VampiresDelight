package net.grid.vampiresdelight.data.provider;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.core.VDBiomeModifiers;
import net.grid.vampiresdelight.common.core.VDConfiguredFeatures;
import net.grid.vampiresdelight.common.core.VDLootTables;
import net.grid.vampiresdelight.common.core.VDPlacedFeatures;
import net.grid.vampiresdelight.data.provider.loot.VDBlockLootProvider;
import net.grid.vampiresdelight.data.provider.loot.VDChestLootProvider;
import net.minecraft.DetectedVersion;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = VampiresDelight.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class VDDataProvider {

    @SubscribeEvent
    public static void generateData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        RegistrySetBuilder registryBuilder = new RegistrySetBuilder()
                .add(Registries.CONFIGURED_FEATURE, VDConfiguredFeatures::create)
                .add(Registries.PLACED_FEATURE, VDPlacedFeatures::create)
                .add(ForgeRegistries.Keys.BIOME_MODIFIERS, VDBiomeModifiers::create);

        VDTagsProvider.register(generator, output, lookupProvider, existingFileHelper);
        generator.addProvider(true, new LootTableProvider(output, VDLootTables.getLootTables(), List.of(
                new LootTableProvider.SubProviderEntry(VDBlockLootProvider::new, LootContextParamSets.BLOCK),
                new LootTableProvider.SubProviderEntry(VDChestLootProvider::new, LootContextParamSets.CHEST)
        )));
        generator.addProvider(true, new VDRecipeProvider(output));
        generator.addProvider(true, new VDItemModelProvider(output, existingFileHelper));
        generator.addProvider(true, new VDBlockStateProvider(output, existingFileHelper));
        generator.addProvider(true, new VDLootModifierProvider(output));
        generator.addProvider(true, new VDAdvancementProvider(output, lookupProvider, existingFileHelper));
        generator.addProvider(true, new VDSoundDefinitionProvider(output, existingFileHelper));
        generator.addProvider(true, new VDParticleDescriptionProvider(output, existingFileHelper));
        generator.addProvider(true, new PackMetadataGenerator(output).add(PackMetadataSection.TYPE, new PackMetadataSection(Component.literal("Vampire's Delight resources"), DetectedVersion.BUILT_IN.getPackVersion(PackType.SERVER_DATA))));
        generator.addProvider(true, new DatapackBuiltinEntriesProvider(output, lookupProvider, registryBuilder, Set.of(VampiresDelight.MODID)));
    }
}
