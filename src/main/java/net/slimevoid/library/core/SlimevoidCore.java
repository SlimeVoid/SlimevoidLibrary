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
package net.slimevoid.library.core;

import net.slimevoid.library.data.LoggerSlimevoidLib;
import cpw.mods.fml.common.FMLCommonHandler;

public class SlimevoidCore {
    public static void console(String modName, String s, int type) {
        switch (type) {
        case 0:
            FMLCommonHandler.instance().getFMLLogger().info("[" + modName
                                                            + "] " + s);
            LoggerSlimevoidLib.getInstance(modName).write(false,
                                                          s,
                                                          LoggerSlimevoidLib.LogLevel.DEBUG);
            break;
        case 1:
            FMLCommonHandler.instance().getFMLLogger().warn("[" + modName
                                                            + "] " + s);
            LoggerSlimevoidLib.getInstance(modName).write(false,
                                                          s,
                                                          LoggerSlimevoidLib.LogLevel.WARNING);
            break;
        case 2:
            FMLCommonHandler.instance().getFMLLogger().fatal("[" + modName
                                                             + "] " + s);
            LoggerSlimevoidLib.getInstance(modName).write(false,
                                                          s,
                                                          LoggerSlimevoidLib.LogLevel.ERROR);
            break;
        default:
            FMLCommonHandler.instance().getFMLLogger().info("[" + modName
                                                            + "] " + s);
            LoggerSlimevoidLib.getInstance(modName).write(false,
                                                          s,
                                                          LoggerSlimevoidLib.LogLevel.INFO);
            break;
        }
    }

    public static void console(String modName, String s) {
        console(modName,
                s,
                0);
    }
}
