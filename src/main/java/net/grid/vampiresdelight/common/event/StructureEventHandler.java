package net.grid.vampiresdelight.common.event;

import de.teamlapen.vampirism.REFERENCE;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.misc.mixin.accessor.StructureTemplatePoolAccessor;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = VampiresDelight.MODID)
public class StructureEventHandler {

    @SubscribeEvent
    public static void addStructures(ServerAboutToStartEvent event) {
        RegistryAccess registryAccess = event.getServer().registryAccess();

        addCookingSpotToOutpost(registryAccess);
        addTentsToOutpost(registryAccess);
    }

    private static void addCookingSpotToOutpost(RegistryAccess registryAccess) {
        ResourceLocation alchemyPool = ResourceLocation.fromNamespaceAndPath(REFERENCE.MODID, "hunter_outpost/alchemy");

        registryAccess.registry(Registries.TEMPLATE_POOL).flatMap(pools -> pools.getOptional(alchemyPool)).ifPresent(structurePool -> {
            StructurePoolElement piece = SinglePoolElement.single(VampiresDelight.MODID + ":cooking_spot").apply(StructureTemplatePool.Projection.RIGID);
            ((StructureTemplatePoolAccessor) structurePool).getTemplates().add(piece);
        });
    }

    private static void addTentsToOutpost(RegistryAccess registryAccess) {
        ResourceLocation tentsPool = ResourceLocation.fromNamespaceAndPath(REFERENCE.MODID, "hunter_outpost/tents");

        registryAccess.registry(Registries.TEMPLATE_POOL).flatMap(pools -> pools.getOptional(tentsPool)).ifPresent(structurePool -> {
            for (int i = 1; i <= 3; i++) {
                StructurePoolElement piece = SinglePoolElement.single(VampiresDelight.MODID + ":tents/tent_vd_" + i).apply(StructureTemplatePool.Projection.RIGID);
                ((StructureTemplatePoolAccessor) structurePool).getTemplates().add(piece);
            }
        });
    }
}
