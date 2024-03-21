package net.grid.vampiresdelight.data;

import com.google.common.collect.Sets;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.registry.VDItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;
import java.util.stream.Collectors;

import static vectorwing.farmersdelight.data.ItemModels.takeAll;

public class VDItemModels extends ItemModelProvider {

    public static final String GENERATED = "item/generated";
    public static final String HANDHELD = "item/handheld";

    public VDItemModels(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, VampiresDelight.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        Set<Item> items = ForgeRegistries.ITEMS.getValues().stream().filter(i -> VampiresDelight.MODID.equals(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(i)).getNamespace()))
                .collect(Collectors.toSet());

        // Items that use its own model in models/item (mostly because runData cannot locate farmersdelight:item/mug)
        Set<Item> specialItems = Sets.newHashSet(
                VDItems.ORCHID_TEA.get(),
                VDItems.WINE_GLASS.get(),
                VDItems.MULLED_WINE_GLASS.get(),
                VDItems.SPIRIT_LANTERN.get());
        takeAll(items, specialItems.toArray(new Item[0])).forEach(items::remove);

        // Blocks with special item sprites
        Set<Item> spriteBlockItems = Sets.newHashSet(
                VDItems.BLOOD_PIE.get(),
                VDItems.ORCHID_SEEDS.get(),
                VDItems.WEIRD_JELLY_BLOCK.get(),
                VDItems.ORCHID_CAKE.get()
        );
        takeAll(items, spriteBlockItems.toArray(new Item[0])).forEach(item -> withExistingParent(itemName(item), GENERATED).texture("layer0", resourceItem(itemName(item))));

        Set<Item> flatBlockItems = Sets.newHashSet(
                VDItems.WILD_GARLIC.get());
        takeAll(items, flatBlockItems.toArray(new Item[0])).forEach(item -> itemGeneratedModel(item, resourceBlock(itemName(item))));

        // Blocks whose items look alike
        takeAll(items, i -> i instanceof BlockItem).forEach(item -> blockBasedModel(item, ""));

        // Handheld items
        Set<Item> handheldItems = Sets.newHashSet(
                VDItems.TRICOLOR_DANGO.get(),
                VDItems.EYES_ON_STICK.get()
        );
        takeAll(items, handheldItems.toArray(new Item[0])).forEach(item -> itemHandheldModel(item, resourceItem(itemName(item))));

        // Generated items
        items.forEach(item -> itemGeneratedModel(item, resourceItem(itemName(item))));

    }

    public void blockBasedModel(Item item, String suffix) {
        withExistingParent(itemName(item), resourceBlock(itemName(item) + suffix));
    }

    public void itemHandheldModel(Item item, ResourceLocation texture) {
        withExistingParent(itemName(item), HANDHELD).texture("layer0", texture);
    }

    public void itemGeneratedModel(Item item, ResourceLocation texture) {
        withExistingParent(itemName(item), GENERATED).texture("layer0", texture);
    }

    private String itemName(Item item) {
        return Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)).getPath();
    }

    public ResourceLocation resourceBlock(String path) {
        return new ResourceLocation(VampiresDelight.MODID, "block/" + path);
    }

    public ResourceLocation resourceItem(String path) {
        return new ResourceLocation(VampiresDelight.MODID, "item/" + path);
    }
}
