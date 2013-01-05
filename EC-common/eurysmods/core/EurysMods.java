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
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.network.NetworkMod;
import eurysmods.api.ICommonProxy;

@Mod(
		modid = "EurysCore",
		name = "Eurys Mod Core",
		version = "2.0.0.5")
@NetworkMod(
		clientSideRequired = true,
		serverSideRequired = false)
public class EurysMods {
	@SidedProxy(
			clientSide="eurysmods.proxy.EC_ClientProxy",
			serverSide="eurysmods.proxy.EC_CommonProxy")
	public static ICommonProxy proxy;
}
