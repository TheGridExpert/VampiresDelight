package net.grid.vampiresdelight.common.util;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.core.VDEffects;
import net.grid.vampiresdelight.common.core.VDItems;
import net.grid.vampiresdelight.common.core.VDPotions;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.MissingMappingsEvent;


@Mod.EventBusSubscriber(modid = VampiresDelight.MODID)
public class VDMigrationData {

    @SubscribeEvent
    public static void onMissingMappings(MissingMappingsEvent event) {
        event.getAllMappings(ForgeRegistries.Keys.ITEMS).forEach(VDMigrationData::fixItems);
        event.getAllMappings(ForgeRegistries.Keys.MOB_EFFECTS).forEach(VDMigrationData::fixEffects);
        event.getAllMappings(ForgeRegistries.Keys.POTIONS).forEach(VDMigrationData::fixPotions);
    }

    public static void fixItems(MissingMappingsEvent.Mapping<Item> mapping) {
        switch (mapping.getKey().toString()) {
            case "vampiresdelight:eye_toast":
                mapping.remap(VDItems.EYE_CROISSANT.get());
                break;
            case "vampiresdelight:wine_glass":
                mapping.remap(VDItems.BLOOD_WINE_GLASS.get());
                break;
            case "vampiresdelight:grilled_garlic":
                mapping.remap(VDItems.ROASTED_GARLIC.get());
                break;
            case "vampiresdelight:cooked_bat":
                mapping.remap(VDItems.GRILLED_BAT.get());
                break;
            case "vampiresdelight:cooked_bat_chops":
                mapping.remap(VDItems.GRILLED_BAT_CHOPS.get());
                break;
        }
    }

    public static void fixEffects(MissingMappingsEvent.Mapping<MobEffect> mapping) {
        switch (mapping.getKey().toString()) {
            case "vampiresdelight:blessing":
                mapping.remap(VDEffects.CONSECRATION.get());
                break;
            case "vampiresdelight:clothes_dissolving":
                mapping.remap(VDEffects.DISSOLVING.get());
                break;
        }
    }

    public static void fixPotions(MissingMappingsEvent.Mapping<Potion> mapping) {
        switch (mapping.getKey().toString()) {
            case "vampiresdelight:blessing":
            case "vampiresdelight:strong_blessing":
                mapping.remap(VDPotions.CONSECRATION.get());
                break;
            case "vampiresdelight:long_blessing":
            case "vampiresdelight:long_strong_blessing":
                mapping.remap(VDPotions.LONG_CONSECRATION.get());
                break;
            case "vampiresdelight:very_long_blessing":
                mapping.remap(VDPotions.VERY_LONG_CONSECRATION.get());
                break;
            case "vampiresdelight:clothes_dissolving":
                mapping.remap(VDPotions.DISSOLVING.get());
                break;
            case "vampiresdelight:strong_fog_vision":
                mapping.remap(VDPotions.FOG_VISION.get());
                break;
            case "vampiresdelight:long_strong_fog_vision":
                mapping.remap(VDPotions.LONG_FOG_VISION.get());
                break;
        }
    }
}
