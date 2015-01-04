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

import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public class EntityHelper {
	
	/**
	 * get a floored BlockPos using x, y, z coordinates
	 * @param x
	 * @param y
	 * @param z
	 * @return floored BlockPos
	 */
	public static BlockPos getFlooredPosition(double x, double y, double z) {
		return new BlockPos(MathHelper.floor_double(x), MathHelper.floor_double(y), MathHelper.floor_double(z));
	}
}
