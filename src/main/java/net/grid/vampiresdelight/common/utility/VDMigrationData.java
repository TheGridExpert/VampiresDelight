package net.grid.vampiresdelight.common.utility;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.MissingMappingsEvent;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(modid = VampiresDelight.MODID)
public class VDMigrationData {
    @SubscribeEvent
    public static void onMissingMappings(@NotNull MissingMappingsEvent event) {
        event.getAllMappings(ForgeRegistries.Keys.ITEMS).forEach(VDMigrationData::fixItems);
    }

    public static void fixItems(@NotNull MissingMappingsEvent.Mapping<Item> mapping) {
        switch (mapping.getKey().toString()) {
            case "vampiresdelight:eye_toast" -> mapping.remap(VDItems.EYE_CROISSANT.get());
        }
    }
}
