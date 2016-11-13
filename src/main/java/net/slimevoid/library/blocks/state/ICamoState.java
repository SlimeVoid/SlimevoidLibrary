package net.slimevoid.library.blocks.state;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;

/**
 * Created by Greg on 26/03/15.
 */
public interface ICamoState {
    IBlockState getStateForSide(EnumFacing side);

    void setStateForSide(EnumFacing side, IBlockState stateIn);
}
