package net.grid.vampiresdelight.common.world.fluid;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;

import java.util.function.Consumer;

/**
 * This is made after Farmer's Respite's TeaFluidType for compatibility.
 */
public class DrinkFluidType extends FluidType {

    public static final ResourceLocation FLUID_STILL_TEXTURE = ResourceLocation.withDefaultNamespace("block/water_still");
    public static final ResourceLocation FLUID_FLOWING_TEXTURE = ResourceLocation.withDefaultNamespace("block/water_flow");

    private final int tintColor;

    public DrinkFluidType(int tintColor) {
        super(Properties.create().sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY).sound(SoundActions.FLUID_VAPORIZE, SoundEvents.FIRE_EXTINGUISH));
        this.tintColor = tintColor;
    }

    @Override
    public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
        consumer.accept(new IClientFluidTypeExtensions() {

            public ResourceLocation getStillTexture() {
                return FLUID_STILL_TEXTURE;
            }

            public ResourceLocation getFlowingTexture() {
                return FLUID_FLOWING_TEXTURE;
            }

            public int getTintColor() {
                return tintColor;
            }
        });
    }
}
