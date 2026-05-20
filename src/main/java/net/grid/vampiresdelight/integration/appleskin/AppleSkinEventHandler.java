package net.grid.vampiresdelight.integration.appleskin;

import de.teamlapen.vampirism.VampirismMod;
import de.teamlapen.vampirism.items.VampirismItemBloodFoodItem;
import de.teamlapen.vampirism.util.Helper;
import de.teamlapen.werewolves.items.LiverItem;
import net.grid.vampiresdelight.common.config.VDClientConfig;
import net.grid.vampiresdelight.common.tag.VDItemTags;
import net.grid.vampiresdelight.common.util.VDIntegrationUtils;
import net.grid.vampiresdelight.common.world.item.FactionConsumableItem;
import net.grid.vampiresdelight.common.world.item.VampireConsumableItem;
import net.grid.vampiresdelight.misc.mixin.accessor.VampirismItemBloodFoodItemAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import squeek.appleskin.api.event.FoodValuesEvent;
import squeek.appleskin.api.event.TooltipOverlayEvent;
import squeek.appleskin.api.food.FoodValues;

@OnlyIn(Dist.CLIENT)
public class AppleSkinEventHandler {

    public static void init() {
        MinecraftForge.EVENT_BUS.register(new AppleSkinEventHandler());
    }

    @SubscribeEvent
    public void onPreTooltipEvent(TooltipOverlayEvent.Pre event) {
        Player player = VampirismMod.proxy.getClientPlayer();
        if (player == null) return;

        ItemStack stack = event.itemStack;

        if (Helper.isVampire(player) && VDClientConfig.HIDE_APPLE_SKIN_HUMAN_FOOD_TOOLTIPS_FOR_VAMPIRES.get() && !isVampireEdible(stack)) {
            event.setCanceled(true);
        } else if (VDIntegrationUtils.isWerewolf(player) && VDClientConfig.HIDE_APPLE_SKIN_HUMAN_FOOD_TOOLTIPS_FOR_WEREWOLVES.get() && !VDIntegrationUtils.canWerewolfEatFood(player, stack)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onFoodValuesEvent(FoodValuesEvent event) {
        if (!VDClientConfig.CORRECT_APPLE_SKIN_TOOLTIPS.get()) return;

        Player player = event.player;
        Item item = event.itemStack.getItem();

        if (item instanceof VampirismItemBloodFoodItem bloodFoodItem && Helper.isVampire(player)) {
            FoodProperties vampireFood = ((VampirismItemBloodFoodItemAccessor) bloodFoodItem).getVampireFood();
            if (vampireFood != null) {
                FoodValues values = makeFoodValues(vampireFood);
                event.defaultFoodValues = values;
                event.modifiedFoodValues = values;
            }
        }
    }

    private static boolean isVampireEdible(ItemStack stack) {
        Item item = stack.getItem();
        if (item instanceof VampireConsumableItem || item instanceof VampirismItemBloodFoodItem) return true;
        if (VDIntegrationUtils.isModPresent(VDIntegrationUtils.WEREWOLVES) && item instanceof LiverItem) return true;
        if (stack.is(VDItemTags.BLOOD_FOOD)) return true;
        return item instanceof FactionConsumableItem factionItem && factionItem.getVampireFood() != null;
    }

    private static FoodValues makeFoodValues(FoodProperties foodProperties) {
        return new FoodValues(foodProperties.getNutrition(), foodProperties.getSaturationModifier());
    }
}