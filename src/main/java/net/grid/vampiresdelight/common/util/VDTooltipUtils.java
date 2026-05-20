package net.grid.vampiresdelight.common.util;

import de.teamlapen.vampirism.api.VReference;
import de.teamlapen.vampirism.api.entity.factions.IPlayableFaction;
import de.teamlapen.vampirism.util.Helper;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.config.VDClientConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class VDTooltipUtils {

    public static void addFormattedTooltip(String key, List<Component> tooltip, ChatFormatting style) {
        tooltip.add(Component.translatable(key).withStyle(style));
    }

    public static void addShiftTooltip(String key, List<Component> tooltip) {
        if (Screen.hasShiftDown()) {
            tooltip.add(Component.translatable(key).withStyle(ChatFormatting.GRAY));
        } else {
            tooltip.add(Component.translatable("tooltip." + VampiresDelight.MODID + ".hold_shift_for_info", Component.translatable("tooltip." + VampiresDelight.MODID + ".shift").withStyle(ChatFormatting.GRAY)).withStyle(ChatFormatting.DARK_GRAY));
        }
    }

    public static void addFactionFoodTooltip(List<Component> tooltip, @Nullable Player player, IPlayableFaction<?> foodFaction) {
        if (!VDClientConfig.FACTION_TOOLTIPS.get()) return;

        boolean isVampire = player != null && Helper.isVampire(player);

        ChatFormatting color = isVampire ? Objects.equals(foodFaction, VReference.VAMPIRE_FACTION) ? ChatFormatting.DARK_GREEN : ChatFormatting.DARK_RED : Objects.equals(foodFaction, VReference.VAMPIRE_FACTION) ? ChatFormatting.DARK_RED : ChatFormatting.DARK_GREEN;

        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("tooltip.vampiresdelight.for_faction", foodFaction.getName().copy().withStyle(color)).withStyle(ChatFormatting.GRAY));
    }

    public static void addWerewolfFactionFoodTooltip(List<Component> tooltip, Player player) {
        if (!VDClientConfig.FACTION_TOOLTIPS.get()) return;

        ChatFormatting color = VDIntegrationUtils.isWerewolf(player) ? ChatFormatting.DARK_GREEN : ChatFormatting.DARK_RED;

        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("tooltip.vampiresdelight.for_faction", Component.translatable("text.werewolves.werewolf").withStyle(color)).withStyle(ChatFormatting.GRAY));
    }
}