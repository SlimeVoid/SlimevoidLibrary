package net.slimevoid.library.blocks;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IStringSerializable;
import net.slimevoid.library.tileentity.TileEntityBase;

public interface IEnumBlockType extends IStringSerializable {

    /**
     * Here you will want to set the local class and register the tileentity mapping
     *
     * @param tileEntityClass
     */
    public void setTileData(Class<? extends TileEntityBase> tileEntityClass);

    /**
     * This will be the value for which the IBlockState will return the TileEntity
     *
     * @return metadata value for TileEntity
     */
    public int getMeta();

    /**
     * Retrieves the Tile Entity class
     *
     * @return
     */
    public Class<? extends TileEntityBase> getTileEntityClass();

    /**
     * Instantiates the Tile class data
     *
     * @return
     */
    public TileEntity createTileEntity();

}
