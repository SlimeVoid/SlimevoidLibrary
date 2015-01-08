package net.slimevoid.library;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IStringSerializable;
import net.slimevoid.library.tileentity.TileEntityBase;

public interface IEnumBlockType extends IStringSerializable {
	
	public void setTileData(Class<? extends TileEntityBase> tileEntityClass);
	
	public int getMeta();

	public Class<? extends TileEntityBase> getTileEntityClass();
	
	public TileEntity createTileEntity();
	
}
