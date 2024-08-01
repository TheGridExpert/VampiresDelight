package net.grid.vampiresdelight.client.event;

import de.teamlapen.vampirism.VampirismMod;
import de.teamlapen.vampirism.api.VReference;
import de.teamlapen.vampirism.items.BloodBottleItem;
import de.teamlapen.vampirism.items.GarlicBreadItem;
import de.teamlapen.vampirism.items.VampirismItemBloodFoodItem;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.VDConfiguration;
import net.grid.vampiresdelight.common.tag.VDTags;
import net.grid.vampiresdelight.common.utility.VDHelper;
import net.grid.vampiresdelight.common.utility.VDIntegrationUtils;
import net.grid.vampiresdelight.common.utility.VDTooltipUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
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
        Player player = VampirismMod.proxy.getClientPlayer();

        if (!VDConfiguration.COLORED_TOOLTIPS.get())
            return;

        ItemStack stack = event.getItemStack();
        Item item = stack.getItem();

        List<? extends Integer> vampireStartColors = VDConfiguration.VAMPIRE_FOOD_TOOLTIP_START_COLOR.get();
        Color vampireStartColor = new Color(vampireStartColors.get(0), vampireStartColors.get(1), vampireStartColors.get(2));
        List<? extends Integer> vampireEndColors = VDConfiguration.VAMPIRE_FOOD_TOOLTIP_END_COLOR.get();
        Color vampireEndColor = new Color(vampireEndColors.get(0), vampireEndColors.get(1), vampireEndColors.get(2));

        List<? extends Integer> hunterStartColors = VDConfiguration.HUNTER_FOOD_TOOLTIP_START_COLOR.get();
        Color hunterStartColor = new Color(hunterStartColors.get(0), hunterStartColors.get(1), hunterStartColors.get(2));
        List<? extends Integer> hunterEndColors = VDConfiguration.HUNTER_FOOD_TOOLTIP_END_COLOR.get();
        Color hunterEndColor = new Color(hunterEndColors.get(0), hunterEndColors.get(1), hunterEndColors.get(2));

        List<? extends Integer> werewolfStartColors = VDConfiguration.WEREWOLF_FOOD_TOOLTIP_START_COLOR.get();
        Color werewolfStartColor = new Color(werewolfStartColors.get(0), werewolfStartColors.get(1), werewolfStartColors.get(2));
        List<? extends Integer> werewolfEndColors = VDConfiguration.WEREWOLF_FOOD_TOOLTIP_END_COLOR.get();
        Color werewolfEndColor = new Color(werewolfEndColors.get(0), werewolfEndColors.get(1), werewolfEndColors.get(2));

        if (stack.is(VDTags.VAMPIRE_FOOD)) {
            setBorderColors(vampireStartColor, vampireEndColor, event);
        } else if (stack.is(VDTags.HUNTER_FOOD) && player != null && VDHelper.isVampire(player)) {
            setBorderColors(hunterStartColor, hunterEndColor, event);
        } else if (stack.is(VDTags.WEREWOLF_ONLY_FOOD)) {
            setBorderColors(werewolfStartColor, werewolfEndColor, event);
        }
    }

    public static void setBorderColors(Color borderStartColor, Color borderEndColor, RenderTooltipEvent.Color event) {
        event.setBorderStart(borderStartColor.getRGB());
        event.setBorderEnd(borderEndColor.getRGB());
    }

    @SubscribeEvent
    public static void addVDTooltips(ItemTooltipEvent event) {
        ItemStack itemStack = event.getItemStack();
        Item item = itemStack.getItem();
        List<Component> tooltip = event.getToolTip();
        Player player = VampirismMod.proxy.getClientPlayer();

        if (player != null) {
            if (item instanceof VampirismItemBloodFoodItem || item instanceof BloodBottleItem) {
                VDTooltipUtils.addFactionFoodToolTips(tooltip, player, VReference.VAMPIRE_FACTION);
            } else if (item instanceof GarlicBreadItem) {
                VDTooltipUtils.addFactionFoodToolTips(tooltip, player, VReference.HUNTER_FACTION);
            } else if (VDHelper.isSame(item, VDIntegrationUtils.WOLF_BERRIES)) {
                VDTooltipUtils.addWerewolfFactionFoodToolTips(tooltip, player);
            }

            //tooltip.add(Component.literal("Color Test 1").withStyle(Style.EMPTY.withColor(new Color(66, 33, 133).getRGB()).withObfuscated(true)));
            //tooltip.add(Component.literal("Color Test 2").withStyle(Style.EMPTY.withColor(new Color(90, 90, 200).getRGB()).withUnderlined(true)));
        }
    }
}
