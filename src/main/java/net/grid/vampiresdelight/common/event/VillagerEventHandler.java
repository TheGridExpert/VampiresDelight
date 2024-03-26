package net.grid.vampiresdelight.common.event;

import de.teamlapen.vampirism.core.ModBlocks;
import de.teamlapen.vampirism.core.ModItems;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.VDConfiguration;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.BasicItemListing;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@Mod.EventBusSubscriber(modid = VampiresDelight.MODID)
@ParametersAreNonnullByDefault
public class VillagerEventHandler {
    @SubscribeEvent
    public static void onVillagerTrades(VillagerTradesEvent event) {
        if (!VDConfiguration.FARMERS_BUY_GARLIC.get()) return;

        Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
        VillagerProfession profession = event.getType();
        ResourceLocation professionKey = ForgeRegistries.VILLAGER_PROFESSIONS.getKey(profession);
        if (professionKey == null) return;
        if (professionKey.getPath().equals("farmer")) {
            trades.get(1).add(emeraldForItemsTrade(ModItems.ITEM_GARLIC.get(), 24, 16, 4));
        }
    }

    @SubscribeEvent
    public static void onWandererTrades(WandererTradesEvent event) {
        if (VDConfiguration.WANDERING_TRADER_SELLS_VAMPIRISM_ITEMS.get()) {
            List<VillagerTrades.ItemListing> trades = event.getGenericTrades();
            trades.add(itemForEmeraldTrade(ModBlocks.VAMPIRE_ORCHID.get(), 3, 1, 12));
            trades.add(itemForEmeraldTrade(VDItems.ORCHID_SEEDS.get(), 2, 1, 12));
            trades.add(itemForEmeraldTrade(VDItems.ORCHID_PETALS.get(), 2, 3, 12));
            trades.add(itemForEmeraldTrade(ModItems.ITEM_GARLIC.get(), 1, 1, 12));
            trades.add(itemForEmeraldTrade(ModBlocks.CURSED_EARTH.get(), 2, 1, 12));
            trades.add(itemForEmeraldTrade(ModBlocks.CURSED_ROOTS.get(), 1, 1, 12));
        }
    }

    public static BasicItemListing emeraldForItemsTrade(ItemLike item, int count, int maxTrades, int xp) {
        return new BasicItemListing(new ItemStack(item, count), new ItemStack(Items.EMERALD), maxTrades, xp, 0.05F);
    }

    public static BasicItemListing itemForEmeraldTrade(ItemLike item, int emeralds,int maxTrades, int xp) {
        return new BasicItemListing(emeralds, new ItemStack(item), maxTrades, xp, 0.05F);
    }
}
