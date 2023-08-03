package net.grid.vampiresdelight.common.registry;

import de.teamlapen.vampirism.items.VampirismItemBloodFoodItem;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.FoodValues;
import net.grid.vampiresdelight.common.item.CursedCupcakeItem;
import net.grid.vampiresdelight.common.item.HunterConsumableItem;
import net.grid.vampiresdelight.common.item.OrchidTeaItem;
import net.grid.vampiresdelight.common.item.VampireConsumableItem;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.FarmersDelight;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, VampiresDelight.MODID);
    // Helper methods
    public static Item.Properties basicItem() {
        return new Item.Properties().tab(FarmersDelight.CREATIVE_TAB);
    }
    public static Item.Properties wildCropItem() {
        return new Item.Properties();
    }
    public static Item.Properties foodItem(FoodProperties food) {
        return new Item.Properties().food(food).tab(FarmersDelight.CREATIVE_TAB);
    }
    public static Item.Properties bowlFoodItem(FoodProperties food) {
        return new Item.Properties().food(food).craftRemainder(Items.BOWL).stacksTo(16).tab(FarmersDelight.CREATIVE_TAB);
    }
    public static Item.Properties drinkItem() {
        return new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16).tab(FarmersDelight.CREATIVE_TAB);
    }


    // The neutral items go first, then the vampire and hunter ones

    // Blocks
    public static final RegistryObject<Item> GARLIC_CRATE = ITEMS.register("garlic_crate",
            () -> new BlockItem(ModBlocks.GARLIC_CRATE.get(), basicItem()));
    public static final RegistryObject<Item> WILD_GARLIC = ITEMS.register("wild_garlic",
            () -> new BlockItem(ModBlocks.WILD_GARLIC.get(), wildCropItem()));

    // Foodstuffs
    public static final RegistryObject<Item> GRILLED_GARLIC = ITEMS.register("grilled_garlic",
            () -> new HunterConsumableItem(foodItem(FoodValues.GRILLED_GARLIC).tab(FarmersDelight.CREATIVE_TAB)));
    public static final RegistryObject<Item> ORCHID_TEA = ITEMS.register("orchid_tea",
            () -> new OrchidTeaItem(drinkItem()));

    // Sweets
    public static final RegistryObject<Item> CURSED_CUPCAKE = ITEMS.register("cursed_cupcake",
            () -> new CursedCupcakeItem((new FoodProperties.Builder()).nutrition(4).saturationMod(0.4f).build(),
                    new FoodProperties.Builder().nutrition(1).saturationMod(0.2f).build()));

    // Soups and Stews
    public static final RegistryObject<Item> GARLIC_SOUP = ITEMS.register("garlic_soup",
            () -> new HunterConsumableItem(bowlFoodItem(FoodValues.GARLIC_SOUP), true));



    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
