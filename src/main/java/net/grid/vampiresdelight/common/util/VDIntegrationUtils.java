package net.grid.vampiresdelight.common.util;

import de.teamlapen.vampirism.api.VampirismAPI;
import de.teamlapen.vampirism.api.VampirismRegistries;
import de.teamlapen.vampirism.api.entity.factions.IFactionPlayerHandler;
import de.teamlapen.vampirism.api.entity.player.skills.ISkill;
import de.teamlapen.werewolves.items.LiverItem;
import de.teamlapen.werewolves.util.Helper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.Nullable;

public class VDIntegrationUtils {

    public static final String WEREWOLVES = "werewolves";
    public static final String APPLESKIN = "appleskin";

    public static boolean isModPresent(String modId) {
        return ModList.get().isLoaded(modId);
    }

    public static boolean isWerewolf(Entity entity) {
        return isModPresent(WEREWOLVES) && Werewolves.isWerewolf(entity);
    }

    public static boolean isWerewolf(Player player) {
        return isModPresent(WEREWOLVES) && Werewolves.isWerewolf(player);
    }

    public static boolean isWerewolfVegetarian(Player player) {
        if (!isModPresent(WEREWOLVES)) return false;
        return hasSkill(player, ResourceLocation.fromNamespaceAndPath(WEREWOLVES, "not_meat"));
    }

    public static boolean isMeat(@Nullable LivingEntity entity, ItemStack stack) {
        if (isModPresent(WEREWOLVES)) {
            return Werewolves.isMeat(entity, stack);
        }
        FoodProperties food = stack.getFoodProperties(entity);
        return food != null && food.isMeat();
    }

    public static boolean canWerewolfEatFood(LivingEntity entity, ItemStack stack) {
        return !isModPresent(WEREWOLVES) || Werewolves.canEat(entity, stack);
    }

    public static boolean isLiver(Item item) {
        return isModPresent(WEREWOLVES) && Werewolves.isLiver(item);
    }

    private static boolean hasSkill(Player player, ResourceLocation skillId) {
        LazyOptional<IFactionPlayerHandler> playerHandler = player.isAlive() ? VampirismAPI.getFactionPlayerHandler(player) : LazyOptional.empty();
        ISkill<?> requiredSkill = VampirismRegistries.SKILLS.get().getValue(skillId);
        if (requiredSkill == null) return false;
        return playerHandler.map(IFactionPlayerHandler::getCurrentFactionPlayer).flatMap(p -> p.map(d -> d.getSkillHandler().isSkillEnabled(requiredSkill))).orElse(false);
    }

    /**
     * Direct delegates to Werewolves' {@code Helper}, isolated in a nested class so the
     * Werewolves classpath is only resolved when one of these methods is actually invoked
     * (always behind an {@link #isModPresent(String)} check at the call site).
     */
    private static final class Werewolves {

        static boolean isWerewolf(Entity entity) {
            return Helper.isWerewolf(entity);
        }

        static boolean isWerewolf(Player player) {
            return Helper.isWerewolf(player);
        }

        static boolean isMeat(@Nullable LivingEntity entity, ItemStack stack) {
            return Helper.isMeat(entity, stack);
        }

        static boolean canEat(LivingEntity entity, ItemStack stack) {
            return Helper.canEat(entity, stack);
        }

        static boolean isLiver(Item item) {
            return item instanceof LiverItem;
        }
    }
}