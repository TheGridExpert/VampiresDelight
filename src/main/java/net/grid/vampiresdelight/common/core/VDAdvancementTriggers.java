package net.grid.vampiresdelight.common.core;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.advancement.DrinkPouredTrigger;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.resources.ResourceLocation;

public class VDAdvancementTriggers {

    public static final DrinkPouredTrigger DRINK_POURED = new DrinkPouredTrigger();
    public static final PlayerTrigger DISGUSTING_FOOD_CONSUMED = new PlayerTrigger(ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "disgusting_food_consumed"));
    public static final PlayerTrigger RICH_SOIL_BLOODIED = new PlayerTrigger(ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "rich_soil_bloodied"));

    public static void register() {
        CriteriaTriggers.register(DRINK_POURED);
        CriteriaTriggers.register(DISGUSTING_FOOD_CONSUMED);
        CriteriaTriggers.register(RICH_SOIL_BLOODIED);
    }

    public static PlayerTrigger.TriggerInstance disgustingFoodConsumed() {
        return new PlayerTrigger.TriggerInstance(DISGUSTING_FOOD_CONSUMED.getId(), ContextAwarePredicate.ANY);
    }

    public static PlayerTrigger.TriggerInstance richSoilBloodied() {
        return new PlayerTrigger.TriggerInstance(RICH_SOIL_BLOODIED.getId(), ContextAwarePredicate.ANY);
    }
}