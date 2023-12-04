package net.grid.vampiresdelight.common.registry;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.block.entity.container.BrewingBarrelMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class VDMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, VampiresDelight.MODID);

    public static final RegistryObject<MenuType<BrewingBarrelMenu>> BREWING_BARREL = MENU_TYPES
            .register("brewing_barrel", () -> IForgeMenuType.create(BrewingBarrelMenu::new));
}
