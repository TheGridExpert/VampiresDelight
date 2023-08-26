package net.grid.vampiresdelight.common.event;

import com.google.common.collect.ImmutableMap;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.registry.VDBlocks;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.MissingMappingsEvent;
import net.minecraft.world.item.Item;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = VampiresDelight.MODID)
public class MappingEvents {
    public static ResourceLocation mapping(String name) {
        return new ResourceLocation(VampiresDelight.MODID, name);
    }

    @SubscribeEvent
    public static void blockRemapping(MissingMappingsEvent event) {
        List<MissingMappingsEvent.Mapping<Block>> mappings = event.getMappings(ForgeRegistries.Keys.BLOCKS, VampiresDelight.MODID);
        Map<ResourceLocation, Supplier<Block>> blockRemapping = (new ImmutableMap.Builder<ResourceLocation, Supplier<Block>>())
                .put(mapping("dark_spruce_pantry"), VDBlocks.DARK_SPRUCE_CABINET)
                .put(mapping("cursed_spruce_pantry"), VDBlocks.CURSED_SPRUCE_CABINET)
                .build();

        for (MissingMappingsEvent.Mapping<Block> mapping : mappings) {
            Supplier<Block> blockSupplier = blockRemapping.get(mapping.getKey());
            if (blockSupplier != null) {
                Block block = blockSupplier.get();
                if (ForgeRegistries.BLOCKS.getKey(block) != null) {
                    mapping.remap(block);
                    VampiresDelight.LOGGER.warn("Remapping block '{}' to '{}'...", mapping.getKey().toString(), ForgeRegistries.BLOCKS.getKey(block).toString());
                }
            }
        }
    }

    @SubscribeEvent
    public static void itemRemapping(MissingMappingsEvent event) {
        List<MissingMappingsEvent.Mapping<Item>> mappings = event.getMappings(ForgeRegistries.Keys.ITEMS, VampiresDelight.MODID);
        Map<ResourceLocation, Supplier<Item>> itemRemapping = (new ImmutableMap.Builder<ResourceLocation, Supplier<Item>>())
                .put(mapping("dark_spruce_pantry"), VDItems.DARK_SPRUCE_CABINET)
                .put(mapping("cursed_spruce_pantry"), VDItems.CURSED_SPRUCE_CABINET)
                .build();

        for (MissingMappingsEvent.Mapping<Item> mapping : mappings) {
            Supplier<Item> itemSupplier = itemRemapping.get(mapping.getKey());

            if (itemSupplier != null) {
                Item item = itemSupplier.get();
                if (item != null && ForgeRegistries.ITEMS.getKey(item) != null) {
                    mapping.remap(item);
                    VampiresDelight.LOGGER.warn("Remapping item '{}' to '{}'...", mapping.getKey().toString(), ForgeRegistries.ITEMS.getKey(item).toString());
                }
            }
        }
    }
}
