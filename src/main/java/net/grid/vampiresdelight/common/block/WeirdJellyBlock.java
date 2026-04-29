package net.grid.vampiresdelight.common.block;

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

// Jelly is 85% transparent
public class WeirdJellyBlock extends RotatedFeastBlock {

    public static VoxelShape[] JELLY_SHAPES = {
            Block.box(3, 1, 8, 8, 8, 13),
            Block.box(3, 1, 8, 13, 8, 13),
            Shapes.join(Block.box(3, 1, 8, 8, 8, 13), Block.box(8, 1, 3, 13, 8, 13), BooleanOp.OR),
            Block.box(3, 1, 3, 13, 8, 13)
    };
    protected static final VoxelShape PLATE_SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 1.0D, 14.0D);

    public WeirdJellyBlock(Properties properties, Supplier<Item> servingItem, boolean hasLeftovers) {
        super(properties, servingItem, hasLeftovers, JELLY_SHAPES, PLATE_SHAPE);
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fl) {
        if (entity.isSuppressingBounce()) {
            super.fallOn(level, state, pos, entity, fl);
        } else {
            entity.causeFallDamage(fl, 0.0F, entity.damageSources().fall());
        }

    }

    @Override
    public void updateEntityAfterFallOn(BlockGetter blockGetter, Entity entity) {
        if (entity.isSuppressingBounce()) {
            super.updateEntityAfterFallOn(blockGetter, entity);
        } else {
            this.bounceUp(entity);
        }
    }

    private void bounceUp(Entity entity) {
        Vec3 vec3 = entity.getDeltaMovement();
        if (vec3.y < 0.0D) {
            double d0 = entity instanceof LivingEntity ? 1.0D : 0.8D;
            entity.setDeltaMovement(vec3.x, -vec3.y * d0, vec3.z);
        }
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        double d0 = Math.abs(entity.getDeltaMovement().y);
        if (d0 < 0.1D && !entity.isSteppingCarefully()) {
            double d1 = 0.4D + d0 * 0.2D;
            entity.setDeltaMovement(entity.getDeltaMovement().multiply(d1, 1.0D, d1));
        }

        super.stepOn(level, pos, state, entity);
    }
}
