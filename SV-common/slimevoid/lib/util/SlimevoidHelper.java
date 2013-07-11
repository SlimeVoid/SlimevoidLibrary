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
package slimevoid.lib.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import slimevoid.lib.ISlimevoidHelper;
import slimevoid.lib.core.SlimevoidCore;

public class SlimevoidHelper {
	
	private static boolean initialized = false;
	private static List<ISlimevoidHelper> helperClasses;
	
	public static void init() {
		if (!initialized) {
			helperClasses = new ArrayList<ISlimevoidHelper>();
			initialized = true;
		}
	}
	
	public static void registerHelper(ISlimevoidHelper newHelper) {
		if (!helperClasses.contains(newHelper)) {
			helperClasses.add(newHelper);
		} else {
			SlimevoidCore.console("Slimevoid Lib", "Attempted to register helper Object " + newHelper.getHelperName() + " that was already registered.");
		}
	}

	public static int getBlockId(World world, int x, int y, int z) {
		for (ISlimevoidHelper helper : helperClasses) {
			int id = helper.getBlockId(world, x, y, z);
			if (id > 0)
				return id;
		}
		return world.getBlockId(x, y, z);
	}

	public static TileEntity getBlockTileEntity(World world, int x, int y, int z) {
		for (ISlimevoidHelper helper : helperClasses) {
			TileEntity tileentity = helper.getBlockTileEntity(world, x, y, z);
			if (tileentity != null) {
				return tileentity;
			}
		}
		return world.getBlockTileEntity(x, y, z);
	}

	public static boolean targetExists(World world, int x, int y, int z) {
		for (ISlimevoidHelper helper : helperClasses) {
			boolean exists = helper.targetExists(world, x, y, z);
			if (exists)
				return true;
		}
		return world.blockExists(x, y, z);
	}

	public static boolean isUseableByPlayer(World world, EntityPlayer player,
			int xCoord, int yCoord, int zCoord, double xDiff, double yDiff,
			double zDiff, double distance) {
		for (ISlimevoidHelper helper : helperClasses) {
			boolean isUseable = helper.isUseableByPlayer(world, player, xCoord, yCoord, zCoord, xDiff, yDiff, zDiff, distance);
			if (isUseable)
				return true;
		}
		return player.getDistanceSq(xCoord + xDiff, yCoord + yDiff, zCoord + zDiff) <= distance;
	}
	
	public static boolean isLadder(World world, int x, int y, int z, EntityLivingBase entity) {
		for (ISlimevoidHelper helper : helperClasses) {
			boolean isLadder = helper.isLadder(world, x, y, z, entity);
			if (isLadder)
				return true;
		}
		int blockID = world.getBlockId(x, y, z);
		return blockID > 0 ? Block.blocksList[blockID].isLadder(world, x, y, z, entity) : false;
	}
}
