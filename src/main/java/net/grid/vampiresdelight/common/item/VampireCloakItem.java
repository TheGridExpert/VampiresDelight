package net.grid.vampiresdelight.common.item;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import de.teamlapen.vampirism.items.VampireClothingItem;
import net.grid.vampiresdelight.VampiresDelight;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

@Mod.EventBusSubscriber(modid = VampiresDelight.MODID)
public class VampireCloakItem extends ArmorItem {
    protected final DyeColor color;
    protected final boolean isInDefaultTab;
    public VampireCloakItem(DyeColor color, boolean isInCreativeTab) {
        super(VampireClothingItem.VAMPIRE_CLOTH, EquipmentSlot.CHEST, new Properties().defaultDurability(ArmorMaterials.IRON.getDurabilityForSlot(EquipmentSlot.CHEST)));
        this.color = color;
        this.isInDefaultTab = isInCreativeTab;
    }

    @Override
    public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> stack) {
        if (!isInDefaultTab && tab != CreativeModeTab.TAB_SEARCH) {
            return;
        }
        super.fillItemCategory(tab, stack);
    }

    public DyeColor getColor() {
        return color;
    }

    //private static final Set rendered = Collections.newSetFromMap(new WeakHashMap());

    public boolean isCapeEquipped(Player player) {
        ItemStack chestSlot = player.getItemBySlot(EquipmentSlot.CHEST);
        return !chestSlot.isEmpty() && chestSlot.getItem() == this;
    }

    public boolean isCape(ItemStack stack) {
        return stack.getItem() == this;
    }
    /**
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void onRenderPlayer(RenderPlayerEvent.Post event) {
        Player player = event.getEntity();
        String uuid = player.getUUID().toString();
        VampireCloakItem cloakItem = this;

        if (player instanceof AbstractClientPlayer playerCl && cloakItem.isCapeEquipped(player)) {
            Map<MinecraftProfileTexture.Type, ResourceLocation> textures = (Map<MinecraftProfileTexture.Type, ResourceLocation>) playerCl.getCloakTextureLocation();
            ResourceLocation path = new ResourceLocation(VampiresDelight.MODID, "textures/models/armor/vampire_cloak/vampire_cloak_model_" + this.getColor().getId() + ".png");
            textures.put(MinecraftProfileTexture.Type.CAPE, path);
            rendered.add(uuid);
        }
    }

    /**
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void onCapeChange(LivingEquipmentChangeEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            ItemStack oldCape = event.getFrom();
            ItemStack newCape = event.getTo();
            VampireCloakItem cloakItem = this;

            if (cloakItem.isCape(oldCape) && !cloakItem.isCape(newCape)) {

            }
        }
    }
    */



    /**
     ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
     if (!chest.isEmpty() && chest.getItem() instanceof VampireCloakItem cloakItem) {
     if (player instanceof AbstractClientPlayer playerCl && playerCl.isCapeLoaded()) {
     ResourceLocation location = new ResourceLocation(VampiresDelight.MODID, "textures/models/armor/vampire_cloak/" + this.getColor().getId() + ".png");
     }
     }
     */
}
