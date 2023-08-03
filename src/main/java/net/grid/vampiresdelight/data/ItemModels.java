package net.grid.vampiresdelight.data;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.registry.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.FarmersDelight;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static vectorwing.farmersdelight.data.ItemModels.MUG;

public class ItemModels extends ItemModelProvider {

    public static final String GENERATED = "item/generated";


    public ItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, VampiresDelight.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        defaultItem(ModItems.CURSED_CUPCAKE);
    }

    private ItemModelBuilder defaultItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(VampiresDelight.MODID, "item/" + item.getId().getPath()));
    }
    private String itemName(Item item) {
        return ForgeRegistries.ITEMS.getKey(item).getPath();
    }
}
