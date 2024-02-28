package net.grid.vampiresdelight.common.registry;

import de.teamlapen.vampirism.mixin.StructuresAccessor;
import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.tag.VDTags;
import net.grid.vampiresdelight.common.world.structure.LostCarriagePieces;
import net.grid.vampiresdelight.common.world.structure.LostCarriageStructure;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.*;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class VDStructures {
    public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES = DeferredRegister.create(Registries.STRUCTURE_TYPE, VampiresDelight.MODID);
    public static final DeferredRegister<StructurePieceType> STRUCTURE_PIECES = DeferredRegister.create(Registries.STRUCTURE_PIECE, VampiresDelight.MODID);
    public static final DeferredRegister<StructureProcessorType<?>> STRUCTURE_PROCESSOR_TYPES = DeferredRegister.create(Registries.STRUCTURE_PROCESSOR, VampiresDelight.MODID);

    public static final RegistryObject<StructurePieceType> LOST_CARRIAGE_PIECE = STRUCTURE_PIECES.register("lost_carriage", () -> (StructurePieceType.StructureTemplateType) LostCarriagePieces.LostCarriagePiece::new);
    public static final RegistryObject<StructureType<LostCarriageStructure>> LOST_CARRIAGE_TYPE = STRUCTURE_TYPES.register("lost_carriage", () -> () -> LostCarriageStructure.CODEC);
    public static final ResourceKey<Structure> LOST_CARRIAGE = ResourceKey.create(Registries.STRUCTURE, new ResourceLocation(VampiresDelight.MODID, "lost_carriage"));
    public static final ResourceKey<StructureSet> LOST_CARRIAGE_SET = createStructureSetKey("lost_carriage");

    private static ResourceKey<StructureSet> createStructureSetKey(String name) {
        return ResourceKey.create(Registries.STRUCTURE_SET, new ResourceLocation(VampiresDelight.MODID, name));
    }

    public static void createStructureSets(BootstapContext<StructureSet> context) {
        HolderGetter<Structure> structureLookup = context.lookup(Registries.STRUCTURE);
        context.register(LOST_CARRIAGE_SET, new StructureSet(structureLookup.getOrThrow(LOST_CARRIAGE), new RandomSpreadStructurePlacement(28, 15, RandomSpreadType.LINEAR, 481920014)));
    }

    public static void createStructures(BootstapContext<Structure> context) {
        HolderGetter<Biome> lookup = context.lookup(Registries.BIOME);
        context.register(LOST_CARRIAGE, new LostCarriageStructure(StructuresAccessor.structure(lookup.getOrThrow(VDTags.HAS_LOST_CARRIAGE), TerrainAdjustment.BEARD_THIN)));
    }
}
