package net.grid.vampiresdelight.common.advancement;

import com.google.gson.JsonObject;
import net.grid.vampiresdelight.VampiresDelight;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

public class DrinkPouredTrigger extends SimpleCriterionTrigger<DrinkPouredTrigger.TriggerInstance> {

    private static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(VampiresDelight.MODID, "drink_poured");

    @Override
    public @NotNull ResourceLocation getId() {
        return ID;
    }

    @Override
    protected @NotNull TriggerInstance createInstance(JsonObject json, @NotNull ContextAwarePredicate player, @NotNull DeserializationContext context) {
        ItemPredicate item = ItemPredicate.fromJson(json.get("item"));
        return new TriggerInstance(player, item);
    }

    public void trigger(ServerPlayer player, ItemStack stack) {
        this.trigger(player, (instance) -> instance.matches(stack));
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        private final ItemPredicate item;

        public TriggerInstance(ContextAwarePredicate player, ItemPredicate item) {
            super(ID, player);
            this.item = item;
        }

        public static TriggerInstance pouredDrinkBottle(ItemLike item) {
            return pouredDrinkBottle(ItemPredicate.Builder.item().of(item.asItem()).build());
        }

        public static TriggerInstance pouredDrinkBottle(ItemPredicate item) {
            return new TriggerInstance(ContextAwarePredicate.ANY, item);
        }

        public boolean matches(ItemStack stack) {
            return this.item.matches(stack);
        }

        @Override
        public @NotNull JsonObject serializeToJson(@NotNull SerializationContext context) {
            JsonObject json = super.serializeToJson(context);
            json.add("item", this.item.serializeToJson());
            return json;
        }
    }
}