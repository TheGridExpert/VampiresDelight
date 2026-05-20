package net.grid.vampiresdelight.common.core;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.world.fluid.DrinkFluidType;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class VDFluids {
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, VampiresDelight.MODID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, VampiresDelight.MODID);

    public static final RegistryObject<FluidType> DAISY_TEA_TYPE = FLUID_TYPES.register("daisy_tea_type", () -> new DrinkFluidType(0xffffb03b));
    public static final RegistryObject<FlowingFluid> DAISY_TEA = FLUIDS.register("daisy_tea", () -> new ForgeFlowingFluid.Source(VDFluids.DAISY_TEA_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_DAISY_TEA = FLUIDS.register("flowing_daisy_tea", () -> new ForgeFlowingFluid.Flowing(VDFluids.DAISY_TEA_PROPERTIES));
    public static final ForgeFlowingFluid.Properties DAISY_TEA_PROPERTIES = new ForgeFlowingFluid.Properties(DAISY_TEA_TYPE, DAISY_TEA, FLOWING_DAISY_TEA);

    public static final RegistryObject<FluidType> ORCHID_TEA_TYPE = FLUID_TYPES.register("orchid_tea_type", () -> new DrinkFluidType(0xffc46aeb));
    public static final RegistryObject<FlowingFluid> ORCHID_TEA = FLUIDS.register("orchid_tea", () -> new ForgeFlowingFluid.Source(VDFluids.ORCHID_TEA_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_ORCHID_TEA = FLUIDS.register("flowing_orchid_tea", () -> new ForgeFlowingFluid.Flowing(VDFluids.ORCHID_TEA_PROPERTIES));
    public static final ForgeFlowingFluid.Properties ORCHID_TEA_PROPERTIES = new ForgeFlowingFluid.Properties(ORCHID_TEA_TYPE, ORCHID_TEA, FLOWING_ORCHID_TEA);



    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
        FLUIDS.register(eventBus);
    }
}
