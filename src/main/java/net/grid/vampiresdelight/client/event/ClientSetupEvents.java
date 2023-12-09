package net.grid.vampiresdelight.client.event;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.registry.VDEntityTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

@Mod.EventBusSubscriber(modid = VampiresDelight.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetupEvents {
    public static final ResourceLocation EMPTY_CONTAINER_SLOT_BOTTLE = new ResourceLocation(VampiresDelight.MODID, "item/empty_container_slot_bottle");

    @SubscribeEvent
    public static void onEntityRendererRegister(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(VDEntityTypes.ALCHEMICAL_COCKTAIL.get(), ThrownItemRenderer::new);
    }

    @SubscribeEvent
    public static void onStitchEvent(TextureStitchEvent.Pre event) {
        event.addSprite(EMPTY_CONTAINER_SLOT_BOTTLE);
    }
}
