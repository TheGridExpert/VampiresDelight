package net.grid.vampiresdelight.common.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class BloodWineBottleBlock extends Block implements SimpleWaterloggedBlock {
    public static final int MIN_BOTTLES = 1;
    public static final int MAX_BOTTLES = 4;
    public static final IntegerProperty BOTTLES = IntegerProperty.create("bottles", 1, 4);

    public BloodWineBottleBlock(Block.Properties properties) {
        super(properties);
    }
}
