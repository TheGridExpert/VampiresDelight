package net.grid.vampiresdelight.common.tag;

import net.grid.vampiresdelight.VampiresDelight;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class VDBiomeTags {

    public static final TagKey<Biome> WITHOUT_WILD_GARLIC = tag("without_wild_garlic");

    private static TagKey<Biome> tag(String name) {
        return TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, name));
    }
}
