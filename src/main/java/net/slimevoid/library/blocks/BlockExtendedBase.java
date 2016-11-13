package net.slimevoid.library.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

/**
 * Created by Greg on 22/03/15.
 */
public abstract class BlockExtendedBase extends BlockSimpleBase {

    public BlockExtendedBase(Material material) {
        super(material);
    }

    @Override
    protected void setActualDefaultState() {
    }

    @Override
    protected BlockState createBlockState() {
        return new ExtendedBlockState(this, this.getPropertyList(), this.getUnlistedPropertyList());
    }

    @Override
    protected IBlockState getInitialState(IBlockState state) {
        return this.getInitialExtendedState(state);
    }

    protected abstract IExtendedBlockState getInitialExtendedState(IBlockState state);

    protected abstract IUnlistedProperty[] getUnlistedPropertyList();

    @Override
    protected IProperty[] getPropertyList() {
        return new IProperty[0];
    }
}
