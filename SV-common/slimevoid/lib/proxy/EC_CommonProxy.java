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
package slimevoid.lib.proxy;

import slimevoid.lib.ICommonProxy;
import slimevoid.lib.IPacketHandling;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.Player;

public class EC_CommonProxy implements ICommonProxy {

	@Override
	public void registerRenderInformation() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMinecraftDir() {
		return ".";
	}

	@Override
	public int getBlockTextureFromMetadata(int meta) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int meta) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IPacketHandling getPacketHandler() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void registerTileEntitySpecialRenderer(Class<? extends TileEntity> clazz) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayTileEntityGui(EntityPlayer entityplayer, TileEntity tileentity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public World getWorld() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public World getWorld(NetHandler handler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EntityPlayer getPlayer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void login(NetHandler handler, INetworkManager manager, Packet1Login login) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerTickHandler() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preInit() {
		// TODO Auto-generated method stub
		
	}

}
