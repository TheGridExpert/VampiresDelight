package net.grid.vampiresdelight.data.provider;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.core.VDParticles;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ParticleDescriptionProvider;

public class VDParticleDescriptionProvider extends ParticleDescriptionProvider {

    protected VDParticleDescriptionProvider(PackOutput output, ExistingFileHelper fileHelper) {
        super(output, fileHelper);
    }

    @Override
    protected void addDescriptions() {
        spriteSet(VDParticles.DISPEL.get(), ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "dispel"), 5, false);
    }
}
