package net.grid.vampiresdelight.common.item;

import de.teamlapen.vampirism.VampirismMod;
import de.teamlapen.vampirism.config.VampirismConfig;
import de.teamlapen.vampirism.effects.SanguinareEffect;
import de.teamlapen.vampirism.util.Helper;
import net.grid.vampiresdelight.common.util.VDTextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.Configuration;

import java.util.ArrayList;
import java.util.List;

public class OrchidTeaItem extends VampireDrinkableItem {
    public OrchidTeaItem(Properties properties) {
        super(properties, true);
    }

    @Override
    public void affectHuman(ItemStack stack, Level level, LivingEntity consumer) {
        if (Helper.canBecomeVampire((Player) consumer)) {
            if (!VampirismConfig.SERVER.disableFangInfection.get()) {
                SanguinareEffect.addRandom(consumer, true);
                consumer.addEffect(new MobEffectInstance(MobEffects.POISON, 60));
            }
        }
    }

    @Override
    public void affectHunter(ItemStack stack, Level level, LivingEntity consumer) {
        consumer.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 400));
        consumer.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 150));
    }

    @Override
    public void affectVampire(ItemStack stack, Level level, LivingEntity consumer) {
        consumer.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 440));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag isAdvanced) {
        Player player = VampirismMod.proxy.getClientPlayer();
        List<MobEffectInstance> effects = new ArrayList<>();

        if (Configuration.FOOD_EFFECT_TOOLTIP.get() && player != null) {
            if (Helper.isVampire(player)) {
                effects.add(new MobEffectInstance(MobEffects.REGENERATION, 440));
                PotionUtils.addPotionTooltip(effects, tooltip, 1.0F);
            } else {
                if (Helper.isHunter(player)) {
                    effects.add(new MobEffectInstance(MobEffects.CONFUSION, 400));
                    effects.add(new MobEffectInstance(MobEffects.BLINDNESS, 150));
                    PotionUtils.addPotionTooltip(effects, tooltip, 1.0F);
                } else {
                    MutableComponent textEmpty = VDTextUtils.getTranslation("tooltip." + this);
                    tooltip.add(textEmpty.withStyle(ChatFormatting.BLUE));
                }
            }
        }

        super.appendHoverText(stack, level, tooltip, isAdvanced);
    }
}
