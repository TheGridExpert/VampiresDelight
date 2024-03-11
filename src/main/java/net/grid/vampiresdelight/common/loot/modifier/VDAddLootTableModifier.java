package net.grid.vampiresdelight.common.loot.modifier;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.grid.vampiresdelight.common.VDConfiguration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

import static net.minecraft.world.level.storage.loot.LootTable.createStackSplitter;

/**
 * Credits to Commoble for this implementation!
 * VDAddLootTableModifier is Farmer's Delight's AddLootTableModifier adapted for Vampire's Delight
 */
public class VDAddLootTableModifier extends LootModifier {
    public static final Supplier<Codec<VDAddLootTableModifier>> CODEC = Suppliers.memoize(() ->
            RecordCodecBuilder.create(inst -> codecStart(inst)
                    .and(ResourceLocation.CODEC.fieldOf("lootTable").forGetter((m) -> m.lootTable))
                    .apply(inst, VDAddLootTableModifier::new)));

    private final ResourceLocation lootTable;

    protected VDAddLootTableModifier(LootItemCondition[] conditionsIn, ResourceLocation lootTable) {
        super(conditionsIn);
        this.lootTable = lootTable;
    }

    @Nonnull
    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        if (VDConfiguration.GENERATE_VD_CHEST_LOOT.get()) {
            LootTable extraTable = context.getResolver().getLootTable(this.lootTable);
            extraTable.getRandomItemsRaw(context, createStackSplitter(context.getLevel(), generatedLoot::add));
        }
        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
