package net.slimevoid.library.blocks;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IStringSerializable;
import net.slimevoid.library.tileentity.TileEntityBase;

public interface IBlockEnumType extends IStringSerializable {
	
	public void setTileData(Class<? extends TileEntityBase> tileEntityClass);
	
	public int getMeta();

	public Class<? extends TileEntityBase> getTileEntityClass();
	
	public TileEntity createTileEntity();
	
}
