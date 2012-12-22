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
package eurysmods.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.Player;

public interface ICommonProxy extends IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z);

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z);

	/**
	 * Should be called prior to any other configuration
	 */
	public void preInit();
	
	/**
	 * Register Sided Tick handlers
	 */
	public void registerTickHandler();

	/**
	 * Register render information (Client only)
	 */
	public void registerRenderInformation();

	/**
	 * Retrieve Sided Minecraft directory
	 * 
	 * @return miencraft path as String
	 */
	public String getMinecraftDir();

	/**
	 * Fascade to call block texture from side and metadata
	 * 
	 * @param side the side of the block
	 * @param meta the metadata
	 * 
	 * @return an index in the texture file
	 */
	public int getBlockTextureFromSideAndMetadata(int side, int meta);

	/**
	 * Fascade to call block texture from meta
	 * 
	 * @param meta the metadata
	 * 
	 * @return an index in the texture file
	 */
	public int getBlockTextureFromMetadata(int meta);

	/**
	 * Handles sided packetdata
	 * 
	 * @param manager
	 * @param packet
	 * @param player
	 */
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player);

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
	 * Display a tileentity GUI
	 * 
	 * @param entityplayer the player to display for
	 * @param tileentity the tileentity associated with the GUI
	 */
	public void displayTileEntityGui(EntityPlayer entityplayer, TileEntity tileentity);
	
	/**
	 * Fetches the current Minecraft world object.
	 * 
	 * @return Minecraft world object.
	 */
	public World getWorld();
	
	/**
	 * Fetches the current Minecraft world object relating to the NetHandler.
	 * 
	 * handler the NetHandler (Server or Client)
	 * 
	 * @return Minecraft world object.
	 */
	public World getWorld(NetHandler handler);

	/**
	 * Fetches the current player.
	 * 
	 * @return Player
	 */
	public EntityPlayer getPlayer();

	/**
	 * Called on player/client login
	 * 
	 * @param handler
	 * @param manager
	 * @param login
	 */
	public void login(NetHandler handler, INetworkManager manager, Packet1Login login);
}
