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
package slimevoid.lib.core;

import slimevoid.lib.data.Logger;
import slimevoid.lib.data.LoggerSlimevoidLib;
import net.minecraft.src.ModLoader;


public class SlimevoidCore {
	public static void console(String modName, String s, int type) {
		switch (type) {
		case 0:
			ModLoader.getLogger().info("[" + modName + "] " + s);
			LoggerSlimevoidLib
			.getInstance(
					Logger.filterClassName(BlockRemover.class.toString())
			).write(
					false,
					"[" + modName + "] " + s,
					LoggerEurysCore.LogLevel.DEBUG
			);
			break;
		case 1:
			ModLoader.getLogger().warning("[" + modName + "] " + s);
			LoggerSlimevoidLib
			.getInstance(
					Logger.filterClassName(BlockRemover.class.toString())
			).write(
					false,
					"[" + modName + "] " + s,
					LoggerEurysCore.LogLevel.WARNING
			);
			break;
		case 2:
			ModLoader.getLogger().severe("[" + modName + "] " + s);
			LoggerSlimevoidLib
			.getInstance(
					Logger.filterClassName(BlockRemover.class.toString())
			).write(
					false,
					"[" + modName + "] " + s,
					LoggerEurysCore.LogLevel.ERROR
			);
			break;
		default:
			ModLoader.getLogger().info("[" + modName + "] " + s);
			LoggerSlimevoidLib
			.getInstance(
					Logger.filterClassName(BlockRemover.class.toString())
			).write(
					false,
					"[" + modName + "] " + s,
					LoggerEurysCore.LogLevel.INFO
			);
			break;
		}
	}

	public static void console(String modName, String s) {
		console(modName, s, 0);
	}
}
