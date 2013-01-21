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

import slimevoid.lib.ICommonProxy;
import slimevoid.lib.util.SlimevoidHelper;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(
		modid = "SlimevoidLib",
		name = "Slimevoid Library",
		version = "2.0.1.1")
@NetworkMod(
		clientSideRequired = true,
		serverSideRequired = false)
public class SlimevoidLib {
	@SidedProxy(
			clientSide="slimevoid.lib.proxy.SV_ClientProxy",
			serverSide="slimevoid.lib.proxy.SV_CommonProxy")
	public static ICommonProxy proxy;
	
	@PreInit
	public static void SlimevoidLibPreInit(FMLPreInitializationEvent event) {
		SlimevoidHelper.init();
	}
}
