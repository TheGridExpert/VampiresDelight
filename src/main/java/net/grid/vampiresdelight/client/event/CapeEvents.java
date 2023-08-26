package net.grid.vampiresdelight.client.event;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.item.VampireCloakItem;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

@Mod.EventBusSubscriber(modid = VampiresDelight.MODID, value = Dist.CLIENT)
public class CapeEvents {
    /**
    private static final Set rendered = Collections.newSetFromMap(new WeakHashMap());

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void onRenderPlayer(RenderPlayerEvent.Post event) {
        Player player = event.getEntity();
        String uuid = player.getUUID().toString();
        ItemStack chest = event.getEntity().getItemBySlot(EquipmentSlot.CHEST);
        Item cloakItem = chest.getItem();
        if (cloakItem instanceof VampireCloakItem ) {
            if (player instanceof AbstractClientPlayer playerCl && !chest.isEmpty()) {
                Map<MinecraftProfileTexture.Type, ResourceLocation> textures = (Map<MinecraftProfileTexture.Type, ResourceLocation>) playerCl.getCloakTextureLocation();
                ResourceLocation path = new ResourceLocation(VampiresDelight.MODID, "textures/models/armor/vampire_cloak/vampire_cloak_model_" + ((VampireCloakItem) cloakItem).getColor().getId() + ".png");
                textures.put(MinecraftProfileTexture.Type.CAPE, path);
                rendered.add(uuid);
            }
        }
    }
    */
}
