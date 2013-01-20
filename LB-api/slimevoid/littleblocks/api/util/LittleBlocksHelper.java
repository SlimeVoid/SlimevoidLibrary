package slimevoid.littleblocks.api.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import slimevoid.lib.ICommonProxy;
import slimevoid.littleblocks.api.ILBCommonProxy;
import slimevoid.littleblocks.api.ILittleBlocks;

public class LittleBlocksHelper {
	
	private static boolean initialized = false;
	private static ICommonProxy proxy;
	private static int size;
	
	/**
	 * Initialized the Helper
	 * 
	 * @param littleProxy the LB Proxy
	 * @param littleBlocksSize the size of LB
	 */
	public static void init(ICommonProxy littleProxy, int littleBlocksSize) {
		if (!initialized) {
			proxy = littleProxy;
			size = littleBlocksSize;
			initialized = true;
		}
	}

	/**
	 * Gets the block ID for the block whether in the little world or real world
	 * 
	 * @param world the world of the target
	 * @param x the xCoord
	 * @param y the yCoord
	 * @param z the zCoord
	 * @return the block Id
	 */
	public static int getBlockId(World world, int x, int y,
			int z) {
		if (world != null) {
			return getWorld(world, x, y, z).getBlockId(x, y, z);
		}
		return 0;
	}

	/**
	 * Gets the tile entity for the block whether in the little world or real world
	 * 
	 * @param world the world of the target
	 * @param x the xCoord
	 * @param y the yCoord
	 * @param z the zCoord
	 * @return a tile entity
	 */
	public static TileEntity getBlockTileEntity(World world, int x, int y, int z) {
		if (world != null) {
			return getWorld(world, x, y, z).getBlockTileEntity(x, y, z);	
		}
		return null; 
	}
	
	/**
	 * Checks if the target exists within the worldObj of the target
	 * 
	 * @param world the world of the target
	 * @param x the xCoord
	 * @param y the yCoord
	 * @param z the zCoord
	 * @return if block exists
	 */
	public static boolean targetExists(World world, int x, int y, int z) {
		if (world != null) {
			return getWorld(world, x, y, z).blockExists(x, y, z);
		}
		return false;
	}

	/**
	 * If the worldObj of our target is LittleWorld return the LittleWorld for use
	 * 
	 * @param world the world of the target
	 * @param x the xCoord
	 * @param y the yCoord
	 * @param z the zCoord
	 * @return the world or the little world 
	 */
	private static World getWorld(World world, int x, int y, int z) {
		if (isLittleBlock(world, x, y, z)) {
			return (World)((ILBCommonProxy)proxy).getLittleWorld(world, false);
		}
		return world;
	}

	/**
	 * Ascertains whether or not the coordinates of an object are that of the LittleWorld
	 * 
	 * @param world the world of the target
	 * @param x the xCoord
	 * @param y the yCoord
	 * @param z the zCoord
	 * @return whether we're a little block or not
	 */
	private static boolean isLittleBlock(World world, int x, int y, int z) {
		if (world.getBlockTileEntity(x >> 3, y >> 3, z >> 3) instanceof ILittleBlocks) {
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * Helps get the useable by player for Containers within LB Blocks
	 * 
	 * @param world NOT player.worldObj world of the object e.g. tileenitty.worldObj
	 * @param player the player attempting to use the block
	 * @param xCoord the xCoord of the object
	 * @param yCoord the yCoord of the object
	 * @param zCoord the zCoord of the object
	 * @param xDiff the x differential (0.5D)
	 * @param yDiff the y differential (0.5D)
	 * @param zDiff the z differential (0.5D)
	 * @param distance the distance from object (64.0D)
	 * 
	 * @return whether the Player can use the object
	 */
	public static boolean isUseableByPlayer(
			World world, 
			EntityPlayer player,
			int xCoord,
			int yCoord,
			int zCoord,
			double xDiff, 
			double yDiff, 
			double zDiff, 
			double distance) {
		if (isLittleBlock(world, xCoord, yCoord, zCoord)) {
			return player.getDistanceSq((double)(xCoord * size) + xDiff, (double)(yCoord * size) + yDiff, (double)(zCoord * size) + zDiff) <= distance;
		}
		return player.getDistanceSq((double)xCoord + xDiff, (double)yCoord + yDiff, (double)zCoord + zDiff) <= distance;
	}
}
