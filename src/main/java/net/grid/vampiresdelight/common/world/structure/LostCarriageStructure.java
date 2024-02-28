package net.grid.vampiresdelight.common.world.structure;

import com.mojang.serialization.Codec;
import de.teamlapen.vampirism.world.gen.structure.StructureEx;
import net.grid.vampiresdelight.common.registry.VDStructures;
import net.minecraft.world.level.levelgen.structure.StructureType;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class LostCarriageStructure extends StructureEx {

    public static final Codec<LostCarriageStructure> CODEC = simpleCodec(LostCarriageStructure::new);

    public LostCarriageStructure(StructureSettings pSettings) {
        super(pSettings);
    }

    @Override
    protected @NotNull Optional<GenerationStub> findGenerationPoint(@NotNull GenerationContext pContext) {
        return onSurface(pContext, (builder, pos) -> LostCarriagePieces.addPieces(pContext.structureTemplateManager(), builder, pContext.random(), pos));
    }

    @Override
    public @NotNull StructureType<?> type() {
        return VDStructures.LOST_CARRIAGE_TYPE.get();
    }
}
