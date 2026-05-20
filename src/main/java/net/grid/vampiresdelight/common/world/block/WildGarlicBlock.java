package net.grid.vampiresdelight.common.world.block;

import de.teamlapen.vampirism.api.EnumStrength;
import de.teamlapen.vampirism.api.entity.vampire.IVampire;
import de.teamlapen.vampirism.util.DamageHandler;
import de.teamlapen.vampirism.util.Helper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import vectorwing.farmersdelight.common.block.WildCropBlock;

@SuppressWarnings("deprecation")
public class WildGarlicBlock extends WildCropBlock {

    public WildGarlicBlock(MobEffect suspiciousStewEffect, int effectDuration, Properties properties) {
        super(suspiciousStewEffect, effectDuration, properties);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity livingEntity && Helper.isVampire(livingEntity)) {
            DamageHandler.affectVampireGarlicDirect((IVampire) entity, EnumStrength.WEAK);
        }

        super.entityInside(state, level, pos, entity);
    }
}
