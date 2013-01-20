package slimevoid.lib;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public interface ISlimevoidHelper {

	/**
	 * Gets the block ID for the block
	 * 
	 * @param world the world of the target
	 * @param x the xCoord
	 * @param y the yCoord
	 * @param z the zCoord
	 * @return the block Id
	 */
	public int getBlockId(World world, int x, int y,	int z);

	/**
	 * Gets the tile entity for the block
	 * 
	 * @param world the world of the target
	 * @param x the xCoord
	 * @param y the yCoord
	 * @param z the zCoord
	 * @return a tile entity
	 */
	public TileEntity getBlockTileEntity(World world, int x, int y, int z);
	
	/**
	 * Checks if the target exists within the worldObj of the target
	 * 
	 * @param world the world of the target
	 * @param x the xCoord
	 * @param y the yCoord
	 * @param z the zCoord
	 * @return if block exists
	 */
	public boolean targetExists(World world, int x, int y, int z);
	
	/**
	 * 
	 * Helps get the useable by player for Containers
	 * 
	 * @param world NOT player.worldObj world of the object e.g. tileentity.worldObj
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
	public boolean isUseableByPlayer(
			World world, 
			EntityPlayer player,
			int xCoord,
			int yCoord,
			int zCoord,
			double xDiff, 
			double yDiff, 
			double zDiff, 
			double distance);

	/**
	 * Overriden to return a readable name string
	 * 
	 * @return the name of the helper
	 */
	public String getHelperName();
}
