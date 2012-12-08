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
package eurysmods.core;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import eurysmods.api.ICommonProxy;

@Mod(modid = "EurysCore", name = "Eurys Mod Core", version = "1.3.2.0")
@NetworkMod(
		clientSideRequired = true,
		serverSideRequired = false,
		versionBounds = "[1.3.2]")
public class EurysMods {
	@SidedProxy(
			clientSide="eurysmods.proxy.EC_ClientProxy",
			serverSide="eurysmods.proxy.EC_CommonProxy")
	public static ICommonProxy proxy;
}
