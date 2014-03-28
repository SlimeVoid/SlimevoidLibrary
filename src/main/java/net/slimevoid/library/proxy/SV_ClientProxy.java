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
package net.slimevoid.library.proxy;

import cpw.mods.fml.client.FMLClientHandler;

public class SV_ClientProxy extends SV_CommonProxy {

    @Override
    public String getMinecraftDir() {
        return FMLClientHandler.instance().getClient().mcDataDir.toString();
    }

    @Override
    public void preInit() {
        super.preInit();
    }
}
