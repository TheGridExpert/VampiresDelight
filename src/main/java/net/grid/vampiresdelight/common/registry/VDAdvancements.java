package net.grid.vampiresdelight.common.registry;

import net.grid.vampiresdelight.VampiresDelight;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.resources.ResourceLocation;

public class VDAdvancements {
    public static final PlayerTrigger DISGUSTING_FOOD_CONSUMED = new PlayerTrigger(new ResourceLocation(VampiresDelight.MODID, "disgusting_food_consumed"));
    public static final PlayerTrigger BLOOD_WINE_POURED = new PlayerTrigger(new ResourceLocation(VampiresDelight.MODID, "blood_wine_poured"));

    public static void register() {
        CriteriaTriggers.register(DISGUSTING_FOOD_CONSUMED);
        CriteriaTriggers.register(BLOOD_WINE_POURED);
    }
}
