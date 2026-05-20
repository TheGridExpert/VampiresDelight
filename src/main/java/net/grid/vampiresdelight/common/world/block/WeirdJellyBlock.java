package net.grid.vampiresdelight.common.world.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import vectorwing.farmersdelight.common.block.RotatedFeastBlock;

import java.util.function.Supplier;

public class WeirdJellyBlock extends RotatedFeastBlock {

    public static VoxelShape[] JELLY_SHAPES = {
            Block.box(3, 1, 8, 8, 8, 13),
            Block.box(3, 1, 8, 13, 8, 13),
            Shapes.join(Block.box(3, 1, 8, 8, 8, 13), Block.box(8, 1, 3, 13, 8, 13), BooleanOp.OR),
            Block.box(3, 1, 3, 13, 8, 13)
    };
    public static final VoxelShape PLATE_SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 1.0D, 14.0D);

    public WeirdJellyBlock(Properties properties, Supplier<Item> servingItem, boolean hasLeftovers) {
        super(properties, servingItem, hasLeftovers, JELLY_SHAPES, PLATE_SHAPE);
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (entity.isSuppressingBounce()) {
            super.fallOn(level, state, pos, entity, fallDistance);
        } else {
            entity.causeFallDamage(fallDistance, 0.0F, entity.damageSources().fall());
        }
    }

    @Override
    public void updateEntityAfterFallOn(BlockGetter level, Entity entity) {
        if (entity.isSuppressingBounce()) {
            super.updateEntityAfterFallOn(level, entity);
        } else {
            this.bounceUp(entity);
        }
    }

    private void bounceUp(Entity entity) {
        Vec3 motion = entity.getDeltaMovement();
        if (motion.y < 0.0D) {
            double bounceFactor = entity instanceof LivingEntity ? 1.0D : 0.8D;
            entity.setDeltaMovement(motion.x, -motion.y * bounceFactor, motion.z);
        }
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        double verticalSpeed = Math.abs(entity.getDeltaMovement().y);
        if (verticalSpeed < 0.1D && !entity.isSteppingCarefully()) {
            double slowdownFactor = 0.4D + verticalSpeed * 0.2D;
            entity.setDeltaMovement(entity.getDeltaMovement().multiply(slowdownFactor, 1.0D, slowdownFactor));
        }

        super.stepOn(level, pos, state, entity);
    }
}
