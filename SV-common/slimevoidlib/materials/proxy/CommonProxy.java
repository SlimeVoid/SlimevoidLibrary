package slimevoidlib.materials.proxy;

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
import cpw.mods.fml.common.network.Player;
import slimevoidlib.ICommonProxy;
import slimevoidlib.IPacketHandling;
import slimevoidlib.materials.lib.PacketLib;
import slimevoidlib.materials.network.CommonPacketHandler;

public class CommonProxy implements ICommonProxy {

	@Override
	public void preInit() {
		CommonPacketHandler.init();
	}

	@Override
	public String getMinecraftDir() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void registerConfigurationProperties(File configFile) {
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
	public void registerTickHandler() {
		// TODO Auto-generated method stub

	}

	@Override
	public void registerRenderInformation() {
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
	public boolean isClient(World world) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		// TODO Auto-generated method stub

	}

	@Override
	public void playerLoggedIn(Player player, NetHandler netHandler, INetworkManager manager) {
		PacketLib.sendMaterialList(player, netHandler, manager);
	}

	@Override
	public String connectionReceived(NetLoginHandler netHandler, INetworkManager manager) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void connectionOpened(NetHandler netClientHandler, String server, int port, INetworkManager manager) {
		// TODO Auto-generated method stub

	}

	@Override
	public void connectionOpened(NetHandler netClientHandler, MinecraftServer server, INetworkManager manager) {
		// TODO Auto-generated method stub

	}

	@Override
	public void connectionClosed(INetworkManager manager) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clientLoggedIn(NetHandler clientHandler, INetworkManager manager, Packet1Login login) {
		// TODO Auto-generated method stub

	}

}
