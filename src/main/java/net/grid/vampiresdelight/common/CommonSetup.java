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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import vectorwing.farmersdelight.common.block.entity.CookingPotBlockEntity;

public class CommonSetup {
    public static void init(final FMLCommonSetupEvent event) {
        registerModIntegrations();
        event.enqueueWork(() -> {
            VDStats.registerModStats();
            registerDispenserBehaviors();
            //registerIngredientRemainderOverrides();
            registerCompostableItems();
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

    public static void registerIngredientRemainderOverrides() {
        CookingPotBlockEntity.INGREDIENT_REMAINDER_OVERRIDES.put(ModItems.BLOOD_BOTTLE.get(), Items.GLASS_BOTTLE);
    }

    public static void registerCompostableItems() {
        // 30% chance
        ComposterBlock.COMPOSTABLES.put(VDItems.ORCHID_SEEDS.get(), 0.3F);

        // 50% chance
        ComposterBlock.COMPOSTABLES.put(VDItems.ORCHID_PETALS.get(), 0.3F);

        // 65% chance
        ComposterBlock.COMPOSTABLES.put(VDItems.WILD_GARLIC.get(), 0.65F);

        // 85% chance
        ComposterBlock.COMPOSTABLES.put(VDItems.BLOOD_PIE_SLICE.get(), 0.85F);
        ComposterBlock.COMPOSTABLES.put(VDItems.ORCHID_COOKIE.get(), 0.85F);

        // 100% chance
        ComposterBlock.COMPOSTABLES.put(VDItems.BLOOD_PIE.get(), 1.0F);
        ComposterBlock.COMPOSTABLES.put(VDItems.WEIRD_JELLY_BLOCK.get(), 1.0F);
    }

    public static void registerModIntegrations() {
        if (ModLoad.CREATE.isLoaded()) {
            VDPotatoProjectileTypes.register();
        }
    }
}
