package net.grid.vampiresdelight.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;

public class DispelParticle extends TextureSheetParticle {

    private final SpriteSet sprites;

    protected DispelParticle(ClientLevel level, double pX, double pY, double pZ, double xSpeed, double ySpeed, double zSpeed, SpriteSet spriteSet) {
        super(level, pX, pY, pZ, 0.0D, 0.0D, 0.0D);
        this.sprites = spriteSet;
        this.friction = 0.96F;
        this.gravity = -0.1F;
        this.speedUpWhenYMotionIsBlocked = true;
        this.xd = xSpeed;
        this.yd = this.yd * 0.9 + ySpeed;
        this.zd = zSpeed;
        this.quadSize *= 1.125F;
        this.lifetime = (int) (12.0F / Mth.randomBetween(this.random, 0.5F, 1.0F));
        this.setSpriteFromAge(spriteSet);
        this.hasPhysics = true;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    protected int getLightColor(float partialTick) {
        return 240;
    }

    @Override
    public void tick() {
        super.tick();
        setSpriteFromAge(sprites);
    }

    @Override
    public float getQuadSize(float scaleFactor) {
        return quadSize * Mth.clamp(((float) age + scaleFactor) / (float) lifetime * 32.0F, 0.0F, 1.0F);
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {

        private final SpriteSet spriteSet;

        public Provider(SpriteSet sprite) {
            this.spriteSet = sprite;
        }

        @Override
        public @Nullable Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new DispelParticle(clientLevel, x, y, z, xSpeed, ySpeed, zSpeed, spriteSet);
        }
    }
}
