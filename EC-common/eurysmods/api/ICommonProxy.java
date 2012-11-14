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
