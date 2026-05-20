package net.grid.vampiresdelight.client;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.client.gui.NourishmentBloodOverlay;
import net.grid.vampiresdelight.client.particle.DispelParticle;
import net.grid.vampiresdelight.client.renderer.WineShelfRenderer;
import net.grid.vampiresdelight.common.core.VDBlockEntities;
import net.grid.vampiresdelight.common.core.VDEntityTypes;
import net.grid.vampiresdelight.common.core.VDItems;
import net.grid.vampiresdelight.common.core.VDParticles;
import net.grid.vampiresdelight.common.util.VDIntegrationUtils;
import net.grid.vampiresdelight.common.world.block.WineShelfBlock;
import net.grid.vampiresdelight.common.world.item.AlchemicalCocktailItem;
import net.grid.vampiresdelight.integration.appleskin.AppleSkinEventHandler;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = VampiresDelight.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class VDClientEventHandler {

    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event) {
        NourishmentBloodOverlay.init();

        if (VDIntegrationUtils.isModPresent(VDIntegrationUtils.APPLESKIN)) {
            AppleSkinEventHandler.init();
        }

        event.enqueueWork(VDClientEventHandler::registerItemProperties);
    }

    private static void registerItemProperties() {
        ItemProperties.register(
                VDItems.ALCHEMICAL_COCKTAIL.get(),
                ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "metal_pipe"),
                (stack, level, entity, seed) -> stack.hasCustomHoverName() && AlchemicalCocktailItem.isMetalPipe(stack) ? 1.0F : 0.0F
        );
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(VDEntityTypes.ALCHEMICAL_COCKTAIL.get(), ThrownItemRenderer::new);
        event.registerBlockEntityRenderer(VDBlockEntities.WINE_SHELF.get(), WineShelfRenderer::new);
    }

    @SubscribeEvent
    public static void registerAdditionalModels(ModelEvent.RegisterAdditional event) {
        for (String suffix : WineShelfBlock.SLOT_NAMES.values()) {
            for (WineShelfBlock.Slot slot : WineShelfBlock.Slot.values()) {
                ResourceLocation model = slot.getModel();
                if (model != null) {
                    event.register(model.withSuffix(suffix));
                }
            }
        }
    }

    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(VDParticles.DISPEL.get(), DispelParticle.Provider::new);
    }
}