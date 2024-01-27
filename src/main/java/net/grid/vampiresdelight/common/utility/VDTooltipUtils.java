package net.grid.vampiresdelight.common.utility;

import de.teamlapen.vampirism.api.VReference;
import de.teamlapen.vampirism.api.entity.factions.IPlayableFaction;
import de.teamlapen.vampirism.util.Helper;
import net.grid.vampiresdelight.common.VDConfiguration;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class VDTooltipUtils {
    public static void addFactionFoodToolTips(@NotNull List<Component> tooltip, @Nullable Player player, IPlayableFaction<?> foodFaction) {
        assert player != null;

        if (!Helper.isVampire(player) && !VDConfiguration.HUNTER_TOOLTIPS_FOR_EVERYONE.get())
            return;

        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("text.vampirism.faction_specifics").withStyle(ChatFormatting.GRAY));
        ChatFormatting color = ChatFormatting.GRAY;

        if (foodFaction != null) {
            if (Objects.equals(foodFaction, VReference.VAMPIRE_FACTION)) {
                if (Helper.isVampire(player)) {
                    color = ChatFormatting.DARK_GREEN;
                } else {
                    color = ChatFormatting.DARK_RED;
                }
            } else {
                if (!Helper.isVampire(player)) {
                    color = ChatFormatting.DARK_GREEN;
                } else {
                    color = ChatFormatting.DARK_RED;
                }
            }

            tooltip.add(Component.translatable(" ").append(foodFaction.getName()).withStyle(color));
        }
    }
}
