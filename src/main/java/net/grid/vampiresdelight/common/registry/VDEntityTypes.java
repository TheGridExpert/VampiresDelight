package net.grid.vampiresdelight.common.registry;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.entity.AlchemicalCocktailEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class VDEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, VampiresDelight.MODID);

    public static final RegistryObject<EntityType<AlchemicalCocktailEntity>> ALCHEMICAL_COCKTAIL = ENTITIES.register("alchemical_cocktail", () -> (
            EntityType.Builder.<AlchemicalCocktailEntity>of(AlchemicalCocktailEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build("alchemical_cocktail")));

}
