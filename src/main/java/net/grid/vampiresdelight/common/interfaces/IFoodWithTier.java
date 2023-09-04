package net.grid.vampiresdelight.common.interfaces;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.List;

// IItemWithTier but made for food items
public interface IFoodWithTier extends ItemLike {
    @OnlyIn(Dist.CLIENT)
    default void addTierInformation(@NotNull List<Component> tooltip) {
        TIER t = getVampirismTier();
        if (t != TIER.NORMAL) {
            ChatFormatting format = t == TIER.APPETIZING ? ChatFormatting.YELLOW : ChatFormatting.RED;
            tooltip.add(Component.translatable("vampiresdelight.food_tier." + t.getSerializedName().toLowerCase()).withStyle(format));
        }
    }

    TIER getVampirismTier();
    enum TIER implements StringRepresentable {
        DISGUSTING("disgusting"), NORMAL("normal"), APPETIZING("appetizing");

        private final String name;

        TIER(String name) {
            this.name = name;
        }

        public @NotNull String getName() {
            return this.getSerializedName();
        }

        @NotNull
        @Override
        public String getSerializedName() {
            return name;
        }
    }
}
