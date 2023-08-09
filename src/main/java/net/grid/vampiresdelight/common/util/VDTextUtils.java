package net.grid.vampiresdelight.common.util;

import de.teamlapen.vampirism.api.VReference;
import de.teamlapen.vampirism.api.entity.factions.IFaction;
import de.teamlapen.vampirism.util.Helper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class VDTextUtils {
    public static void addFactionFoodToolTips(@NotNull List<Component> tooltip, @Nullable Player player, String foodFaction) {
        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("text.vampirism.faction_specifics").withStyle(ChatFormatting.GRAY));
        ChatFormatting color = ChatFormatting.GRAY;
        IFaction<?> faction = getExclusiveFaction(foodFaction);

        if (faction != null) {
            if (player != null) {
                if (Objects.equals(foodFaction, "VAMPIRE")) {
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
                tooltip.add(Component.translatable(" ").append(faction.getName()).withStyle(color));
            }
        }
    }

    @Nullable
    public static IFaction<?> getExclusiveFaction(String foodFaction) {
        if (Objects.equals(foodFaction, "VAMPIRE")) {
            return VReference.VAMPIRE_FACTION;
        } else {
            return VReference.HUNTER_FACTION;
        }
    }
}
