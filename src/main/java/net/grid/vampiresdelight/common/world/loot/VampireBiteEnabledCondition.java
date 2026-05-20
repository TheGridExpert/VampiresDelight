package net.grid.vampiresdelight.common.world.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.grid.vampiresdelight.common.config.VDCommonConfig;
import net.grid.vampiresdelight.common.core.VDLootConditions;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.Serializer;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public class VampireBiteEnabledCondition implements LootItemCondition {

    public static final VampireBiteEnabledCondition INSTANCE = new VampireBiteEnabledCondition();

    private VampireBiteEnabledCondition() {
    }

    public static LootItemCondition.Builder builder() {
        return () -> INSTANCE;
    }

    @Override
    public LootItemConditionType getType() {
        return VDLootConditions.VAMPIRE_BITE_ENABLED.get();
    }

    @Override
    public boolean test(LootContext context) {
        return VDCommonConfig.ENABLE_VAMPIRE_BITE.get();
    }

    public static class VDSerializer implements Serializer<VampireBiteEnabledCondition> {

        @Override
        public void serialize(JsonObject json, VampireBiteEnabledCondition condition, JsonSerializationContext context) {
        }

        @Override
        public VampireBiteEnabledCondition deserialize(JsonObject json, JsonDeserializationContext context) {
            return INSTANCE;
        }
    }
}