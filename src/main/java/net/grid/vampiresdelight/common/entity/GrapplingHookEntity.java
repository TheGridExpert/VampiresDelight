package net.grid.vampiresdelight.common.entity;

import net.grid.vampiresdelight.common.registry.VDEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;

import java.util.Optional;
import java.util.UUID;

public class GrapplingHookEntity extends Entity {

    private static final EntityDataAccessor<Optional<UUID>> USER_UUID = SynchedEntityData.defineId(GrapplingHookEntity.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<Direction> DIRECTION = SynchedEntityData.defineId(GrapplingHookEntity.class, EntityDataSerializers.DIRECTION);
    private static final EntityDataAccessor<Optional<BlockPos>> TARGET_POS = SynchedEntityData.defineId(GrapplingHookEntity.class, EntityDataSerializers.OPTIONAL_BLOCK_POS);
    private static final EntityDataAccessor<Boolean> RECALLING = SynchedEntityData.defineId(GrapplingHookEntity.class, EntityDataSerializers.BOOLEAN);
    private int recallingTicks = 0;

    public GrapplingHookEntity(EntityType entityType, Level level) {
        super(entityType, level);
    }

    public GrapplingHookEntity(Level level, LivingEntity user, boolean rightHand) {
        this(VDEntities.GRAPPLING_HOOK.get(), level);
        this.setUserUuid(user.getUUID());
        float rot = user.yHeadRot + (rightHand ? 60 : -60);

        double xOffset = -user.getBbWidth() * 0.5D * Math.sin(rot * (Math.PI / 180.0));
        double yOffset = user.getEyeY() - 0.2;
        double zOffset = user.getBbWidth() * 0.5D * Math.cos(rot * (Math.PI / 180.0));

        this.setPos(user.getX() + xOffset, yOffset, user.getZ() + zOffset);
    }

    public GrapplingHookEntity(PlayMessages.SpawnEntity spawnEntity, Level level) {
        this(VDEntities.GRAPPLING_HOOK.get(), level);
    }

    public UUID getUserUuid() {
        return this.entityData.get(USER_UUID).orElse(null);
    }

    public void setUserUuid(UUID uuid) {
        this.entityData.set(USER_UUID, Optional.ofNullable(uuid));
    }

    public Entity getUser() {
        UUID uuid = getUserUuid();
        if (!level.isClientSide) {
            return (uuid != null) ? ((ServerLevel) level).getEntity(uuid) : null;
        } else {
            return level.getPlayerByUUID(getUserUuid());
        }
    }

    public Direction getAttachmentFacing() {
        return this.entityData.get(DIRECTION);
    }

    public void setAttachmentFacing(Direction direction){
        this.entityData.set(DIRECTION, direction);
    }

    public BlockPos getTargetPos() {
        return this.entityData.get(TARGET_POS).orElse(null);
    }

    public void setTargetPos(BlockPos pos) {
        this.entityData.set(TARGET_POS, Optional.ofNullable(pos));
    }

    public void setRecalling(boolean withdrawing){
        this.entityData.set(RECALLING, withdrawing);
    }

    public boolean isRecalling(){
        return this.entityData.get(RECALLING);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(USER_UUID, Optional.empty());
        this.entityData.define(DIRECTION, Direction.DOWN);
        this.entityData.define(TARGET_POS, Optional.empty());
        this.entityData.define(RECALLING, false);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
        if (this.getUserUuid() != null) {
            compoundTag.putUUID("user_uuid", this.getUserUuid());
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
        if (compoundTag.hasUUID("user_uuid")) {
            this.setUserUuid(compoundTag.getUUID("user_uuid"));
        }
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected void onImpact(HitResult result) {
        if (!level.isClientSide && result.getType() == HitResult.Type.BLOCK && getTargetPos() == null) {
            setDeltaMovement(Vec3.ZERO);
            BlockHitResult blockHitResult = (BlockHitResult) result;
            this.setTargetPos(blockHitResult.getBlockPos());
            this.setAttachmentFacing(blockHitResult.getDirection());
        }
    }

    protected float wrapAngle(float currentAngle, float targetAngle, float maxShift) {
        float delta = Mth.wrapDegrees(targetAngle - currentAngle);
        float clampedDifference = Mth.clamp(delta, -maxShift, maxShift);
        float result = Mth.wrapDegrees(currentAngle + clampedDifference);

        return result;
    }

    protected static float interAngle(float startAngle, float targetAngle) {
        float angleDifference = targetAngle - startAngle;
        angleDifference = (angleDifference + 180.0F) % 360.0F - 180.0F;
        return Mth.lerp(0.2F, startAngle, startAngle + angleDifference);
    }

    public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
        Vec3 vector3d = (new Vec3(x, y, z)).normalize().add(this.random.nextGaussian() * (double) 0.0075F * (double) inaccuracy, this.random.nextGaussian() * (double) 0.0075F * (double) inaccuracy, this.random.nextGaussian() * (double) 0.0075F * (double) inaccuracy).scale(velocity);
        this.setDeltaMovement(vector3d);

        float horizontalRot = (float) Math.toDegrees(Math.atan2(vector3d.x, vector3d.z)) + 180;
        float verticalRot = (float) Math.toDegrees(Math.atan2(vector3d.y, Math.sqrt(vector3d.x * vector3d.x + vector3d.z * vector3d.z)));

        this.setYRot(Mth.wrapDegrees(horizontalRot));
        this.setXRot(verticalRot);
        this.yRotO = this.getYRot();
        this.xRotO = verticalRot;
    }

    public void tick() {
        this.xRotO = this.getXRot();
        this.yRotO = this.getYRot();
        Entity entity = this.getUser();
        if(!level.isClientSide && !entity.isAlive() || entity == null) {
            this.discard();
        }

        /**
        if(!level.isClientSide){
            if(entity == null || !entity.isAlive()){
                this.discard();
            }else if (entity.isShiftKeyDown()) {
                this.setRecalling(true);
            }
        }
         */


        if(this.isRecalling() && entity != null){
            super.tick();
            recallingTicks++;
            this.setTargetPos(null);
            Vec3 withDrawTo = entity.getEyePosition().add(0, -0.2F, 0);
            if(withDrawTo.distanceTo(this.position()) > 1.2F && recallingTicks < 200){
                Vec3 move = new Vec3(withDrawTo.x - this.getX(), withDrawTo.y - this.getY(), withDrawTo.z - this.getZ());
                Vec3 vector3d = move.normalize().scale(1.2D);
                this.setDeltaMovement(vector3d.scale(0.99));
                double d0 = this.getX() + vector3d.x;
                double d1 = this.getY() + vector3d.y;
                double d2 = this.getZ() + vector3d.z;
                float f = Mth.sqrt((float) (move.x * move.x + move.z * move.z));
                if(!level.isClientSide){
                    this.setYRot(Mth.wrapDegrees((float) (-Mth.atan2(move.x, move.z) * (double) (180F / (float) Math.PI))) - 180);
                    this.setXRot((float) (Mth.atan2(move.y, f) * (double) (180F / (float) Math.PI)));
                    this.yRotO = this.getYRot();
                    this.xRotO = this.getXRot();
                }
                this.setPos(d0, d1, d2);
            }else{
                this.discard();
            }
        }

        else if (this.level.isClientSide || this.level.hasChunkAt(this.blockPosition())) {
            if(this.getTargetPos() == null){
                super.tick();
                Vec3 vector3d = this.getDeltaMovement();
                HitResult raytraceresult = ProjectileUtil.getHitResult(this, newentity -> false);
                if (raytraceresult != null && raytraceresult.getType() != HitResult.Type.MISS) {
                    this.onImpact(raytraceresult);
                }
                this.checkInsideBlocks();
                double d0 = this.getX() + vector3d.x;
                double d1 = this.getY() + vector3d.y;
                double d2 = this.getZ() + vector3d.z;
                this.setDeltaMovement(vector3d.scale(0.99));
                if (this.level.getBlockStates(this.getBoundingBox()).noneMatch(BlockBehaviour.BlockStateBase::isAir) && !this.isInWater()) {
                    this.setDeltaMovement(Vec3.ZERO);

                } else {
                    this.setPos(d0, d1, d2);
                }
                if (!this.isNoGravity()) {
                    this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.1F, 0.0D));
                }
            }else{
                BlockState state = this.level.getBlockState(this.getTargetPos());
                Vec3 vec3 = new Vec3(this.getTargetPos().getX() + 0.5F, this.getTargetPos().getY() + 0.5F, this.getTargetPos().getZ() + 0.5F);
                Vec3 offset = new Vec3(this.getAttachmentFacing().getStepX() * 0.55F, this.getAttachmentFacing().getStepY() * 0.55F, this.getAttachmentFacing().getStepZ() * 0.55F);
                this.setPos(vec3.add(offset));
                float targetX = this.getXRot();
                float targetY = this.getYRot();
                switch (this.getAttachmentFacing()){
                    case UP:
                        targetX = 0;
                        break;
                    case DOWN:
                        targetX = 180;
                        break;
                    case NORTH:
                        targetX = -90;
                        targetY = 0;
                        break;
                    case EAST:
                        targetX = -90;
                        targetY = 90;
                        break;
                    case SOUTH:
                        targetX = -90;
                        targetY = 180;
                        break;
                    case WEST:
                        targetX = -90;
                        targetY = -90;
                        break;
                }
                this.setXRot(targetX);
                this.setYRot(targetY);
                if(entity != null && entity.distanceTo(this) > 2){
                    float entitySwing = 1.0F;
                    if(entity instanceof LivingEntity living){
                        float detract = living.xxa * living.xxa + living.yya * living.yya + living.zza * living.zza;
                        entitySwing -= Math.min(1.0F, Math.sqrt(detract) * 0.333F);
                    }
                    Vec3 move = new Vec3(this.getX() - entity.getX(), this.getY() - (double)entity.getEyeHeight() / 2.0D - entity.getY(), this.getZ() - entity.getZ());
                    entity.setDeltaMovement(entity.getDeltaMovement().add(move.normalize().scale(0.2D * entitySwing)));
                    if(!entity.isOnGround()){
                        entity.fallDistance = 0.0F;
                    }
                }
                if(state.isAir()){
                    this.setRecalling(true);
                }
            }
        } else {
            discard();
        }

    }
}
