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
package net.slimevoid.library.util.helpers;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.slimevoid.library.ISlimevoidHelper;
import net.slimevoid.library.core.SlimevoidCore;

public class SlimevoidHelper {

    private static boolean                initialized = false;
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
            SlimevoidCore.console("Slimevoid Lib",
                                  "Attempted to register helper Object "
                                          + newHelper.getHelperName()
                                          + " that was already registered.");
        }
    }

    public static Block getBlock(World world, int x, int y, int z) {
        for (ISlimevoidHelper helper : helperClasses) {
            Block id = helper.getBlock(world,
                                       x,
                                       y,
                                       z);
            if (id != null) return id;
        }
        return world.getBlock(x,
                              y,
                              z);
    }

    public static TileEntity getBlockTileEntity(IBlockAccess world, int x, int y, int z) {
        for (ISlimevoidHelper helper : helperClasses) {
            TileEntity tileentity = helper.getBlockTileEntity(world,
                                                              x,
                                                              y,
                                                              z);
            if (tileentity != null) {
                return tileentity;
            }
        }
        return world.getTileEntity(x,
                                   y,
                                   z);
    }

    public static boolean targetExists(World world, int x, int y, int z) {
        for (ISlimevoidHelper helper : helperClasses) {
            boolean exists = helper.targetExists(world,
                                                 x,
                                                 y,
                                                 z);
            if (exists) return true;
        }
        return world.blockExists(x,
                                 y,
                                 z);
    }

    public static boolean isUseableByPlayer(World world, EntityPlayer player, int xCoord, int yCoord, int zCoord, double xDiff, double yDiff, double zDiff, double distance) {
        for (ISlimevoidHelper helper : helperClasses) {
            boolean isUseable = helper.isUseableByPlayer(world,
                                                         player,
                                                         xCoord,
                                                         yCoord,
                                                         zCoord,
                                                         xDiff,
                                                         yDiff,
                                                         zDiff,
                                                         distance);
            if (isUseable) return true;
        }
        return player.getDistanceSq(xCoord + xDiff,
                                    yCoord + yDiff,
                                    zCoord + zDiff) <= distance;
    }

    public static boolean isLadder(IBlockAccess world, int x, int y, int z, EntityLivingBase entity) {
        for (ISlimevoidHelper helper : helperClasses) {
            boolean isLadder = helper.isLadder(world,
                                               x,
                                               y,
                                               z,
                                               entity);
            if (isLadder) return true;
        }
        return false;
    }

    /**
     * Used to trace where a method is being called from
     * 
     * @param _class
     *            the Class to compare against
     * @param traceValue
     *            the value of the trace level, (DO NOT preempt the stack trace
     *            + 1 simply use the trace from where you call this method)
     * 
     * @return if the trace matches
     */
    public static boolean isReflectedClass(Class _class, int traceValue) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        boolean flag = stackTraceElements[traceValue + 1].getClassName().equals(_class.getName());
        return flag;
    }

    public static boolean isReflectedMethod(String methodName, int traceValue) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        boolean flag = stackTraceElements[traceValue + 1].getMethodName().equals(methodName);
        return flag;
    }
}
