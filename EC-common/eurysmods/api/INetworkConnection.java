package eurysmods.api;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.NetHandler;
import net.minecraft.src.NetLoginHandler;
import net.minecraft.src.INetworkManager;
import net.minecraft.src.Packet1Login;
import net.minecraft.src.Packet250CustomPayload;
import cpw.mods.fml.common.network.IConnectionHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public interface INetworkConnection extends IConnectionHandler, IPacketHandler {

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
