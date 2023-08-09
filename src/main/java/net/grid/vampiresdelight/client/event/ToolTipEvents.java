package net.grid.vampiresdelight.client.event;

import de.teamlapen.vampirism.VampirismMod;
import de.teamlapen.vampirism.items.VampirismItemBloodFoodItem;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.util.VDHelper;
import net.grid.vampiresdelight.common.util.VDTextUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;
import java.util.List;

@Mod.EventBusSubscriber(modid = VampiresDelight.MODID, value = Dist.CLIENT)
public class ToolTipEvents {
    @SubscribeEvent
    public static void onTooltipColorEvent(RenderTooltipEvent.Color event) {
        ItemStack stack = event.getItemStack();
        Color borderStartColor = null;
        Color borderEndColor = null;

        if (VDHelper.isVampireFood(stack)) {
            borderStartColor = new Color(124, 40, 124);
            borderEndColor = new Color(50, 0, 50);
        } else if (VDHelper.isHunterFood(stack)) {
            borderStartColor = new Color(65, 65, 220);
            borderEndColor = new Color(30, 30, 70);
        }

        if(borderStartColor != null){
            event.setBorderStart(borderStartColor.getRGB());
        }
        if(borderEndColor != null){
            event.setBorderEnd(borderEndColor.getRGB());
        }
    }

    @SubscribeEvent
    public static void addTooltipToVampirismBloodFood(ItemTooltipEvent event) {
        Item food = event.getItemStack().getItem();
        List<Component> tooltip = event.getToolTip();
        if (food instanceof VampirismItemBloodFoodItem) {
            VDTextUtils.addFactionFoodToolTips(tooltip, VampirismMod.proxy.getClientPlayer(), "VAMPIRE");
        }
    }
}
