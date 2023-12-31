package net.grid.vampiresdelight.client.event;

import de.teamlapen.vampirism.VampirismMod;
import de.teamlapen.vampirism.api.VReference;
import de.teamlapen.vampirism.items.BloodBottleItem;
import de.teamlapen.vampirism.items.GarlicBreadItem;
import de.teamlapen.vampirism.items.VampirismItemBloodFoodItem;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.tag.VDTags;
import net.grid.vampiresdelight.common.utility.VDTooltipUtils;
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

        if (stack.is(VDTags.VAMPIRE_FOOD)) {
            borderStartColor = new Color(124, 40, 124);
            borderEndColor = new Color(50, 0, 70);
        } else if (stack.is(VDTags.HUNTER_FOOD)) {
            borderStartColor = new Color(65, 65, 220);
            borderEndColor = new Color(30, 30, 90);
        }

        if(borderStartColor != null){
            event.setBorderStart(borderStartColor.getRGB());
        }
        if(borderEndColor != null){
            event.setBorderEnd(borderEndColor.getRGB());
        }
    }

    @SubscribeEvent
    public static void addTooltipToVampirismFood(ItemTooltipEvent event) {
        Item food = event.getItemStack().getItem();
        List<Component> tooltip = event.getToolTip();
        if (food instanceof VampirismItemBloodFoodItem || food instanceof BloodBottleItem) {
            VDTooltipUtils.addFactionFoodToolTips(tooltip, VampirismMod.proxy.getClientPlayer(), VReference.VAMPIRE_FACTION);
        }
        if (food instanceof GarlicBreadItem) {
            VDTooltipUtils.addFactionFoodToolTips(tooltip, VampirismMod.proxy.getClientPlayer(), VReference.HUNTER_FACTION);
        }
    }
}
