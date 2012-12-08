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

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.INetworkManager;
import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet1Login;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.Player;

public interface ICommonProxy extends IGuiHandler {

	public void registerRenderInformation();

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z);

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z);

	public String getMinecraftDir();

	public int getBlockTextureFromMetadata(int meta);

	public int getBlockTextureFromSideAndMetadata(int side, int meta);

	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player);

	public IPacketHandling getPacketHandler();

	void registerTileEntitySpecialRenderer(Class<? extends TileEntity> clazz);

	public void displayTileEntityGui(EntityPlayer entityplayer, TileEntity tileentity);
	
	/**
	 * Fetches the current minecraft world object.
	 * 
	 * @return Minecraft world object.
	 */
	public World getWorld();
	
	/**
	 * Fetches the current minecraft world object relating to the NetHandler.
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
	public void registerTickHandler();
}
