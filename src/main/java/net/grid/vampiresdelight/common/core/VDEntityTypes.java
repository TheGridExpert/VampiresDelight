package net.grid.vampiresdelight.common.core;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.world.entity.AlchemicalCocktailEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class VDEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, VampiresDelight.MODID);

    public static final RegistryObject<EntityType<AlchemicalCocktailEntity>> ALCHEMICAL_COCKTAIL = registerEntity("alchemical_cocktail", EntityType.Builder.<AlchemicalCocktailEntity>of(AlchemicalCocktailEntity::new, MobCategory.MISC)
            .sized(0.5F, 0.5F)
            .clientTrackingRange(4)
            .updateInterval(10)
    );

    public static <T extends Entity> RegistryObject<EntityType<T>> registerEntity(String id, EntityType.Builder<T> builder) {
        return ENTITIES.register(id, () -> builder.build(id));
    }

    public static void register(IEventBus eventBus) {
        ENTITIES.register(eventBus);
    }
}
