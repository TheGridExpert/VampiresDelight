package net.grid.vampiresdelight.common;

import de.teamlapen.vampirism.core.ModItems;
import net.grid.vampiresdelight.common.entity.AlchemicalCocktailEntity;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.grid.vampiresdelight.common.registry.VDStats;
import net.grid.vampiresdelight.integration.ModLoad;
import net.grid.vampiresdelight.integration.create.VDPotatoProjectileTypes;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import vectorwing.farmersdelight.common.block.entity.CookingPotBlockEntity;

import java.util.Map;

public class CommonSetup {
    public static void init(final FMLCommonSetupEvent event) {
        registerModIntegrations();
        event.enqueueWork(() -> {
            VDStats.registerModStats();
            registerDispenserBehaviors();
            registerCompostableItems();
            //registerCookingPotRemainders();
        });
    }

    public static void registerDispenserBehaviors() {
        DispenserBlock.registerBehavior(VDItems.ALCHEMICAL_COCKTAIL.get(), new AbstractProjectileDispenseBehavior()
        {
            @Override
            protected Projectile getProjectile(Level pLevel, Position pPosition, ItemStack pStack) {
                return new AlchemicalCocktailEntity(pLevel, pPosition.x(), pPosition.y(), pPosition.z());
            }
        });
    }

    public static void registerCompostableItems() {
        // 65% chance
        ComposterBlock.COMPOSTABLES.put(VDItems.WILD_GARLIC.get(), 0.65F);

        // 85% chance
        ComposterBlock.COMPOSTABLES.put(VDItems.BLOOD_PIE_SLICE.get(), 0.85F);

        // 100% chance
        ComposterBlock.COMPOSTABLES.put(VDItems.BLOOD_PIE.get(), 1.0F);
    }

    /*
    public static void registerCookingPotRemainders() {
        Map<Item, Item> INGREDIENT_REMAINDER_OVERRIDES = Map.ofEntries(
                Map.entry(ModItems.BLOOD_BOTTLE.get(), Items.GLASS_BOTTLE),
                Map.entry(ModItems.VAMPIRE_BLOOD_BOTTLE.get(), Items.GLASS_BOTTLE),
                Map.entry(ModItems.PURE_BLOOD_0.get(), Items.GLASS_BOTTLE),
                Map.entry(ModItems.PURE_BLOOD_1.get(), Items.GLASS_BOTTLE),
                Map.entry(ModItems.PURE_BLOOD_2.get(), Items.GLASS_BOTTLE),
                Map.entry(ModItems.PURE_BLOOD_3.get(), Items.GLASS_BOTTLE),
                Map.entry(ModItems.PURE_BLOOD_4.get(), Items.GLASS_BOTTLE));

        CookingPotBlockEntity.INGREDIENT_REMAINDER_OVERRIDES.putAll(INGREDIENT_REMAINDER_OVERRIDES);
    }
     */

    public static void registerModIntegrations() {
        if (ModLoad.CREATE.isLoaded()) {
            VDPotatoProjectileTypes.register();
        }
    }
}
