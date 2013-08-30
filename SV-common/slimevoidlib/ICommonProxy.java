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
package slimevoidlib;

import java.io.File;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.Player;

public interface ICommonProxy extends IGuiHandler, INetworkConnection {
	
	/**
	 * Should be called prior to any other configuration
	 */
	public void preInit();
	
	/**
	 * Retrieves the Minecraft directory
	 * 
	 * @return the Path
	 */
	public String getMinecraftDir();
	
	/**
	 * Registers sided Configuration
	 */
	public void registerConfigurationProperties(File configFile);

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z);

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z);
	
	/**
	 * Register Sided Tick handlers
	 */
	public void registerTickHandler();

	/**
	 * Register render information (Client only)
	 */
	public void registerRenderInformation();

	/**
	 * Retrieves the sided packet handler
	 * 
	 * @return Packet Handler
	 */
	public IPacketHandling getPacketHandler();

	/**
	 * Register tileentity renderer (Client only)
	 * 
	 * @param clazz the TileEntity class to register for
	 */
	public void registerTileEntitySpecialRenderer(Class<? extends TileEntity> clazz);

	/**
	 * Checks if we're looking at the Client side session.
	 * 
	 * @return True or false.
	 */
	public boolean isClient(World world);
	
	/*
	 * Network Connection Area
	 */

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player);

	@Override
	public void playerLoggedIn(Player player, NetHandler netHandler, INetworkManager manager);

	@Override
	public String connectionReceived(NetLoginHandler netHandler, INetworkManager manager);

	@Override
	public void connectionOpened(NetHandler netClientHandler, String server, int port, INetworkManager manager);

	@Override
	public void connectionOpened(NetHandler netClientHandler, MinecraftServer server, INetworkManager manager);

	@Override
	public void connectionClosed(INetworkManager manager);

	@Override
	public void clientLoggedIn(NetHandler clientHandler, INetworkManager manager, Packet1Login login);
}