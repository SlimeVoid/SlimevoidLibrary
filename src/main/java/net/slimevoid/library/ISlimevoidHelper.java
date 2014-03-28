/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version. This program is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details. You should have received a copy of the GNU
 * Lesser General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>
 */
package net.slimevoid.library;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public interface ISlimevoidHelper {

    /**
     * Gets the block from location
     * 
     * @param world
     *            the world of the target
     * @param x
     *            the xCoord
     * @param y
     *            the yCoord
     * @param z
     *            the zCoord
     * @return the block
     */
    public Block getBlock(World world, int x, int y, int z);

    /**
     * Gets the tile entity for the block
     * 
     * @param world
     *            the world of the target
     * @param x
     *            the xCoord
     * @param y
     *            the yCoord
     * @param z
     *            the zCoord
     * @return a tile entity
     */
    public TileEntity getBlockTileEntity(IBlockAccess world, int x, int y, int z);

    /**
     * Checks if the target exists within the worldObj of the target
     * 
     * @param world
     *            the world of the target
     * @param x
     *            the xCoord
     * @param y
     *            the yCoord
     * @param z
     *            the zCoord
     * @return if block exists
     */
    public boolean targetExists(World world, int x, int y, int z);

    /**
     * 
     * Helps get the usable by player for Containers
     * 
     * @param world
     *            NOT player.worldObj world of the object e.g.
     *            tileentity.worldObj
     * @param player
     *            the player attempting to use the block
     * @param xCoord
     *            the xCoord of the object
     * @param yCoord
     *            the yCoord of the object
     * @param zCoord
     *            the zCoord of the object
     * @param xDiff
     *            the x differential (0.5D)
     * @param yDiff
     *            the y differential (0.5D)
     * @param zDiff
     *            the z differential (0.5D)
     * @param distance
     *            the distance from object (64.0D)
     * 
     * @return whether the Player can use the object
     */
    public boolean isUseableByPlayer(World world, EntityPlayer player, int xCoord, int yCoord, int zCoord, double xDiff, double yDiff, double zDiff, double distance);

    /**
     * Overridden to return a readable name string
     * 
     * @return the name of the helper
     */
    public String getHelperName();

    /**
     * Overridden to determine whether or not the entity is on a ladder
     * 
     * @param world
     * @param x
     * @param y
     * @param z
     * @param entity
     * 
     * @return true of false
     */
    public boolean isLadder(IBlockAccess world, int x, int y, int z, EntityLivingBase entity);
}
