package net.grid.vampiresdelight.common.event;

import de.teamlapen.vampirism.mixin.LootTableAccessor;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.config.VDCommonConfig;
import net.grid.vampiresdelight.common.core.VDLootTables;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = VampiresDelight.MODID)
public class LootEventHandler {

    @SubscribeEvent
    public static void onLootTableLoad(LootTableLoadEvent event) {
        if (VDCommonConfig.ENABLE_VAMPIRE_BITE.get()) return;

        ResourceLocation name = event.getName();
        if (name.equals(VDLootTables.VD_CHEST_VAMPIRE_DUNGEON) || name.equals(VDLootTables.VD_CHEST_ALTAR) || name.equals(VDLootTables.VD_CHEST_CRYPT)) {
            event.setTable(LootTable.EMPTY);
        } else if (name.equals(VDLootTables.VD_CHEST_VAMPIRE_HUT)) {
            ((LootTableAccessor) event.getTable()).getPools().remove(0);
        }
    }
}