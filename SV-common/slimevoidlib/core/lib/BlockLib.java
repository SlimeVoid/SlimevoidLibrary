package slimevoidlib.core.lib;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import slimevoidlib.util.SlimevoidHelper;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

public class BlockLib {

	public static void notifyBlock(World world, int x, int y, int z, int blockID) {
		Block block = Block.blocksList[world.getBlockId(x, y, z)];
		if (block != null) {
			block.onNeighborBlockChange(world, x, y, z, blockID);
		}
	}

	public static void updateIndirectNeighbors(World world, int x, int y, int z, int blockID) {
		if (world.isRemote || FMLCommonHandler.instance().getSide() == Side.CLIENT)
			return;
		for (int inDirX = -3; inDirX <= 3; inDirX++) {
			for (int inDirY = -3; inDirY <= 3; inDirY++) {
				for (int inDirZ = -3; inDirZ <= 3; inDirZ++) {
					int updateDirection = inDirX >= 0 ? inDirX : -inDirX;
					updateDirection += inDirY >= 0 ? inDirY : -inDirY;
					updateDirection += inDirZ >= 0 ? inDirZ : -inDirZ;
					if (updateDirection <= 3) {
						notifyBlock(world, x + inDirX, y + inDirY, z + inDirZ, blockID);
					}
				}

			}

		}

	}

	public static void markBlockDirty(World world, int x, int y, int z) {
		if (world.blockExists(x, y, z)) {
			world.getChunkFromBlockCoords(x, z).setChunkModified();
		}
	}

	public static Object getTileEntity(IBlockAccess world, int x, int y, int z, Class tileEntityClass) {
		TileEntity tileentity = SlimevoidHelper.getBlockTileEntity(world, x, y, z);
		if (!tileEntityClass.isInstance(tileentity)) {
			return null;
		} else {
			return tileentity;
		}
	}
}