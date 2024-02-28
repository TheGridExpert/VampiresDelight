package net.grid.vampiresdelight.common.world.structure;

import net.grid.vampiresdelight.VampiresDelight;
import net.grid.vampiresdelight.common.registry.VDLootTables;
import net.grid.vampiresdelight.common.registry.VDStructures;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.jetbrains.annotations.NotNull;

public class LostCarriagePieces {
    private static final ResourceLocation LOCATION_1 = new ResourceLocation(VampiresDelight.MODID, "lost_carriage_1");
    private static final ResourceLocation LOCATION_2 = new ResourceLocation(VampiresDelight.MODID, "lost_carriage_2");
    private static final ResourceLocation LOCATION_3 = new ResourceLocation(VampiresDelight.MODID, "lost_carriage_3");
    private static final ResourceLocation LOCATION_4 = new ResourceLocation(VampiresDelight.MODID, "lost_carriage_4");

    public static void addPieces(StructureTemplateManager structureTemplateManager, StructurePieceAccessor pieceAccessor, RandomSource random, BlockPos pos)  {
        ResourceLocation location;
        location = switch (random.nextInt(4)) {
            case 0 -> LOCATION_1;
            case 1 -> LOCATION_2;
            case 2 -> LOCATION_3;
            default -> LOCATION_4;
        };
        pieceAccessor.addPiece(new LostCarriagePiece(structureTemplateManager, location, pos));
    }

    public static class LostCarriagePiece extends TemplateStructurePiece {

        public LostCarriagePiece(StructureTemplateManager pStructureTemplateManager, ResourceLocation pLocation, BlockPos pPos) {
            super(VDStructures.LOST_CARRIAGE_PIECE.get(), 0, pStructureTemplateManager, pLocation, pLocation.toString(), makeSettings(), pPos);
        }

        public LostCarriagePiece(StructureTemplateManager pStructureTemplateManager, CompoundTag pTag) {
            super(VDStructures.LOST_CARRIAGE_PIECE.get(), pTag, pStructureTemplateManager, (id) -> makeSettings());
        }

        @Override
        protected void handleDataMarker(@NotNull String pName, @NotNull BlockPos pPos, @NotNull ServerLevelAccessor pLevel, @NotNull RandomSource pRandom, @NotNull BoundingBox pBox) {
            if (pName.equals("chest")) {
                pLevel.setBlock(pPos, Blocks.AIR.defaultBlockState(), 3);
                BlockEntity blockEntity = pLevel.getBlockEntity(pPos.below());
                if (blockEntity instanceof ChestBlockEntity chest) {
                    chest.setLootTable(VDLootTables.CHEST_LOST_CARRIAGE, pRandom.nextLong());
                }
            }
        }

        private static @NotNull StructurePlaceSettings makeSettings() {
            return (new StructurePlaceSettings()).setRotation(Rotation.NONE).setMirror(Mirror.NONE).addProcessor(BlockIgnoreProcessor.STRUCTURE_AND_AIR);
        }
    }
}
