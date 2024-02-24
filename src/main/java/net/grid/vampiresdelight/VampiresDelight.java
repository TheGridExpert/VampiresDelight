package net.grid.vampiresdelight;

import net.grid.vampiresdelight.client.ClientSetup;
import net.grid.vampiresdelight.common.CommonSetup;
import net.grid.vampiresdelight.common.VDConfiguration;
import net.grid.vampiresdelight.common.event.PlayerEventHandler;
import net.grid.vampiresdelight.common.event.PlayerInteractEventHandler;
import net.grid.vampiresdelight.common.registry.*;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(VampiresDelight.MODID)
public class VampiresDelight {
    public static final String MODID = "vampiresdelight";
    public static final Logger LOGGER = LogManager.getLogger();
    public static final RecipeBookType RECIPE_TYPE_FERMENTING = RecipeBookType.create("FERMENTING");
    public VampiresDelight() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        eventBus.addListener(CommonSetup::init);
        eventBus.addListener(ClientSetup::init);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, VDConfiguration.COMMON_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, VDConfiguration.CLIENT_CONFIG);

        VDItems.ITEMS.register(eventBus);
        VDPotions.POTIONS.register(eventBus);
        VDOils.OILS.register(eventBus);
        VDEnchantments.ENCHANTMENTS.register(eventBus);
        VDBlocks.BLOCKS.register(eventBus);
        VDEffects.EFFECTS.register(eventBus);
        VDSounds.SOUNDS.register(eventBus);
        VDEntityTypes.ENTITIES.register(eventBus);
        VDRecipeSerializers.RECIPE_SERIALIZERS.register(eventBus);
        VDRecipeTypes.RECIPE_TYPES.register(eventBus);
        VDBlockEntityTypes.TILES.register(eventBus);
        VDMenuTypes.MENU_TYPES.register(eventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }
}
