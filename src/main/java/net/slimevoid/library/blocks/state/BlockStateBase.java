package net.slimevoid.library.blocks.state;

import net.minecraft.block.state.BlockState.StateImplementation;
import net.minecraft.block.state.IBlockState;

public class BlockStateBase extends StateImplementation {

    public BlockStateBase(IBlockState stateIn) {
        super(stateIn.getBlock(), stateIn.getProperties());
    }
}