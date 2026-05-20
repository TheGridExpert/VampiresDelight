package net.grid.vampiresdelight.common.world.entity;

import de.teamlapen.vampirism.core.ModBlocks;
import net.grid.vampiresdelight.common.config.VDCommonConfig;
import net.grid.vampiresdelight.common.core.VDEntityTypes;
import net.grid.vampiresdelight.common.core.VDItems;
import net.grid.vampiresdelight.common.core.VDSounds;
import net.grid.vampiresdelight.common.world.item.AlchemicalCocktailItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.event.level.BlockEvent;
import org.jetbrains.annotations.Nullable;

public class AlchemicalCocktailEntity extends ThrowableItemProjectile {

    public AlchemicalCocktailEntity(EntityType<AlchemicalCocktailEntity> type, Level level) {
        super(type, level);
    }

    public AlchemicalCocktailEntity(Level level, LivingEntity entity) {
        super(VDEntityTypes.ALCHEMICAL_COCKTAIL.get(), entity, level);
    }

    public AlchemicalCocktailEntity(Level level, double x, double y, double z) {
        super(VDEntityTypes.ALCHEMICAL_COCKTAIL.get(), x, y, z, level);
    }

    @Override
    protected Item getDefaultItem() {
        return VDItems.ALCHEMICAL_COCKTAIL.get();
    }

    @Override
    protected Component getTypeName() {
        return getDefaultItem().getDescription();
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);

        Entity entity = result.getEntity();
        entity.hurt(damageSources().thrown(this, getOwner()), 0);
        entity.setSecondsOnFire(16);
        setOnFire(result);
        playSmashSound(this, isMetalPipe());
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);

        if (!level().isClientSide) {
            level().broadcastEntityEvent(this, (byte) 3);
            setOnFire(result);
            playSmashSound(this, isMetalPipe());
            discard();
        }
    }

    public void setOnFire(HitResult result) {
        setOnFire(BlockPos.containing(result.getLocation()), AlchemicalCocktailItem.getSplashRadius(), level(), getOwner());
    }

    public static void setOnFire(BlockPos blockPos, double radius, Level level, @Nullable Entity owner) {
        if (VDCommonConfig.ALCHEMICAL_COCKTAIL_BURNS_GROUND.get() && !level.isClientSide) {
            for (int dx = (int) -Math.ceil(radius); dx <= radius; dx++) {
                for (int dz = (int) -Math.ceil(radius); dz <= radius; dz++) {
                    double distance = Math.sqrt(dx * dx + dz * dz);
                    if (distance > radius) continue;

                    for (int dy = -2; dy < 2; dy++) {
                        BlockPos pos = blockPos.offset(dx, dy, dz);
                        BlockState blockState = level.getBlockState(pos);
                        BlockState blockStateBelow = level.getBlockState(pos.below());

                        double probability = (radius - distance) / radius;

                        if (blockState.canBeReplaced() && isProperBlockBelow(blockStateBelow, pos.below(), level) && level.random.nextDouble() < probability) {
                            if (owner != null) {
                                BlockEvent.EntityPlaceEvent placeEvent = new BlockEvent.EntityPlaceEvent(BlockSnapshot.create(level.dimension(), level, pos), blockStateBelow, owner);
                                MinecraftForge.EVENT_BUS.post(placeEvent);
                                if (placeEvent.isCanceled()) continue;
                            }
                            level.setBlockAndUpdate(pos, ModBlocks.ALCHEMICAL_FIRE.get().defaultBlockState());
                        }
                    }
                }
            }

        }
    }

    private static boolean isProperBlockBelow(BlockState blockStateBelow, BlockPos posBelow, Level level) {
        Block blockBelow = blockStateBelow.getBlock();
        return blockStateBelow.isFaceSturdy(level, posBelow, Direction.UP) || blockStateBelow.is(BlockTags.LEAVES) || blockBelow instanceof StairBlock;
    }

    public static void playSmashSound(Entity entity, boolean isMetalPipe) {
        if (isMetalPipe) {
            entity.playSound(VDSounds.METAL_PIPE.get(), 2.0F, entity.random.nextFloat() * 0.1F + 1.0F);
        } else {
            entity.playSound(VDSounds.BOTTLE_BREAKS.get(), 1.0F, (entity.random.nextFloat() - entity.random.nextFloat()) * 0.2F + 1.0F);
        }
    }

    private boolean isMetalPipe() {
        return AlchemicalCocktailItem.isMetalPipe(getItem());
    }

    @Override
    protected float getGravity() {
        return 0.1f;
    }
}
