package eurysmods.api;

import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public interface IContainer {

	public TileEntity createNewTileEntity(World world);

	public TileEntity createTileEntity(World world, int meta);

	public void onBlockAdded(World world, int x, int y, int z);
}
