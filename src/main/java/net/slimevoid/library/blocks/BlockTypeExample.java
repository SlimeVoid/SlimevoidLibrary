package net.slimevoid.library.blocks;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.slimevoid.library.tileentity.TileEntityBase;

public enum BlockTypeExample implements IBlockEnumType {
	
	BLOCK1(0, "name1", TileEntity.class);
	
	int meta;
	String name;
	Class<? extends TileEntityBase> tile;
	
	BlockTypeExample(int meta, String name, Class tile) {
		this.meta = meta;
		this.name = name;
		this.setTileData(tile);
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setTileData(Class<? extends TileEntityBase> tileEntityClass) {
		this.tile = tileEntityClass;
		GameRegistry.registerTileEntity(tileEntityClass, this.getName());
	}

	@Override
	public int getMeta() {
		return meta;
	}

	@Override
	public Class<? extends TileEntityBase> getTileEntityClass() {
		return tile;
	}

	@Override
	public TileEntity createTileEntity() {
		try {
			return tile.newInstance();
		} catch (Exception e) {
			return null;
		}
	}

}
