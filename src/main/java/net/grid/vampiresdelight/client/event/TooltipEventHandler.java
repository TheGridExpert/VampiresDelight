package net.grid.vampiresdelight.client.event;

import de.teamlapen.vampirism.VampirismMod;
import de.teamlapen.vampirism.items.BloodBottleItem;
import de.teamlapen.vampirism.items.GarlicBreadItem;
import de.teamlapen.vampirism.items.VampirismItemBloodFoodItem;
import de.teamlapen.vampirism.util.Helper;
import de.teamlapen.vampirism.util.OilUtils;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.config.VDClientConfig;
import net.grid.vampiresdelight.common.core.VDItems;
import net.grid.vampiresdelight.common.core.VDOils;
import net.grid.vampiresdelight.common.util.VDIntegrationUtils;
import net.grid.vampiresdelight.common.util.VDTooltipUtils;
import net.grid.vampiresdelight.common.world.item.FactionConsumableItem;
import net.grid.vampiresdelight.common.world.item.FoodOrientation;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.List;

import static net.grid.vampiresdelight.common.core.VDPotions.*;

@Mod.EventBusSubscriber(modid = VampiresDelight.MODID, value = Dist.CLIENT)
public class TooltipEventHandler {

    @SubscribeEvent
    public static void registerShiftTooltips(ItemTooltipEvent event) {
        List<Component> tooltip = event.getToolTip();
        ItemStack stack = event.getItemStack();

        if (stack.is(VDItems.ORCHID_SEEDS.get())) {
            VDTooltipUtils.addShiftTooltip("tooltip.vampiresdelight.orchid_seeds", tooltip);
        } else if (stack.is(VDItems.SPIRIT_LANTERN.get())) {
            VDTooltipUtils.addShiftTooltip("tooltip.vampiresdelight.spirit_lantern", tooltip);
        }

        if (VDClientConfig.SHOW_DISSOLVING_POTION_WARNING.get()) {
            Potion potion = PotionUtils.getPotion(stack);
            if (List.of(
                    DISSOLVING.get(),
                    LONG_DISSOLVING.get(),
                    STRONG_DISSOLVING.get(),
                    VERY_STRONG_DISSOLVING.get(),
                    VERY_LONG_DISSOLVING.get(),
                    LONG_STRONG_DISSOLVING.get()
            ).contains(potion) || OilUtils.getOil(stack) == VDOils.DISSOLVING.get()) {
                tooltip.add(Component.translatable("tooltip.vampiresdelight.creative_only_potion").withStyle(ChatFormatting.DARK_PURPLE));
            }
        }
    }

    @SubscribeEvent
    public static void onTooltipColorEvent(RenderTooltipEvent.Color event) {
        ItemStack stack = event.getItemStack();
        Item item = stack.getItem();
        Player player = VampirismMod.proxy.getClientPlayer();

        if (!VDClientConfig.COLORED_TOOLTIPS.get() || stack.isEmpty()) {
            return;
        }

        if (!isItemFrom(item, VampiresDelight.MODID) && !VDClientConfig.COLORED_TOOLTIPS_FOR_VAMPIRISM_ITEMS.get()) {
            return;
        }

        if (hasVampireMargin(item, player)) {
            setBorderColors(VDClientConfig.VAMPIRE_FOOD_TOOLTIP_START_COLOR, VDClientConfig.VAMPIRE_FOOD_TOOLTIP_END_COLOR, event);
        } else if (hasHunterMargin(item, player)) {
            setBorderColors(VDClientConfig.HUNTER_FOOD_TOOLTIP_START_COLOR, VDClientConfig.HUNTER_FOOD_TOOLTIP_END_COLOR, event);
        } else if (hasWerewolfMargin(item, player)) {
            setBorderColors(VDClientConfig.WEREWOLF_FOOD_TOOLTIP_START_COLOR, VDClientConfig.WEREWOLF_FOOD_TOOLTIP_END_COLOR, event);
        }
    }

    private static void setBorderColors(ForgeConfigSpec.ConfigValue<String> startColorConfigValue, ForgeConfigSpec.ConfigValue<String> endColorConfigValue, RenderTooltipEvent.Color event) {
        Color startColor, endColor;

        try {
            startColor = Color.decode(startColorConfigValue.get());
        } catch (NumberFormatException e) {
            startColor = Color.decode(startColorConfigValue.getDefault());
        }
        try {
            endColor = Color.decode(endColorConfigValue.get());
        } catch (NumberFormatException e) {
            endColor = Color.decode(endColorConfigValue.getDefault());
        }

        event.setBorderStart(startColor.getRGB());
        event.setBorderEnd(endColor.getRGB());
    }

    private static boolean hasVampireMargin(Item item, Player player) {
        if (item instanceof BloodBottleItem || item instanceof VampirismItemBloodFoodItem) {
            return true;
        }
        return item instanceof FactionConsumableItem factionItem && (factionItem.getOrientation() == FoodOrientation.VAMPIRE || player != null && Helper.isVampire(player) && (factionItem.getOrientation() == FoodOrientation.VAMPIRE_TOLERANT || factionItem.getOrientation() == FoodOrientation.UNIVERSAL));
    }

    private static boolean hasHunterMargin(Item item, Player player) {
        return (item instanceof GarlicBreadItem || item instanceof FactionConsumableItem factionItem && factionItem.getOrientation() == FoodOrientation.HUNTER) && player != null && Helper.isVampire(player) || VDClientConfig.HUNTER_TOOLTIPS_FOR_EVERYONE.get();
    }

    private static boolean hasWerewolfMargin(Item item, Player player) {
        return item instanceof FactionConsumableItem factionItem && (factionItem.getOrientation() == FoodOrientation.WEREWOLF || player != null && VDIntegrationUtils.isWerewolf(player) && (factionItem.getOrientation() == FoodOrientation.WEREWOLF_TOLERANT || factionItem.getOrientation() == FoodOrientation.UNIVERSAL));
    }

    private static boolean isItemFrom(@Nullable Item item, String modId) {
        if (item == null) return false;
        ResourceLocation key = ForgeRegistries.ITEMS.getKey(item);
        return key != null && key.getNamespace().equals(modId);
    }
}