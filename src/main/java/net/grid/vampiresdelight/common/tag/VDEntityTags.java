package net.grid.vampiresdelight.common.tag;

import net.grid.vampiresdelight.VampiresDelight;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class VDEntityTags {

    public static final TagKey<EntityType<?>> UNHOLY_SPIRITS = tag("unholy_spirits");
    public static final TagKey<EntityType<?>> DROPS_HUMAN_EYE = tag("drops_human_eye");

    private static TagKey<EntityType<?>> tag(String name) {
        return TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, name));
    }
}
