package slimevoidlib.materials.lib;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.NetHandler;
import slimevoidlib.materials.network.handler.PacketMaterialHandler;
import slimevoidlib.materials.network.packet.PacketMaterialList;
import slimevoidlib.materials.network.packet.executor.PacketMaterialListExecutor;
import slimevoidlib.network.PacketIds;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PacketLib {

	public static void sendMaterialList(Player player, NetHandler netHandler, INetworkManager manager) {
		PacketMaterialList packet = new PacketMaterialList(
				ConfigurationLib.getBaseBlockList(false));
		System.out.println("Sending Mats List");
		packet.setCommand(CommandLib.SEND_MATERIALS);
		PacketDispatcher.sendPacketToPlayer(packet.getPacket(), player);
	}

	public static void sendAllMaterialList() {
		PacketMaterialList packet = new PacketMaterialList(
				ConfigurationLib.getBaseBlockList(false));
		System.out.println("Sending updated Mats List");
		packet.setCommand(CommandLib.SEND_MATERIALS);
		PacketDispatcher.sendPacketToAllPlayers(packet.getPacket());
	}

	@SideOnly(Side.CLIENT)
	public static void registerClientPacketHandlers() {
		PacketMaterialHandler materialHandler = new PacketMaterialHandler();
		materialHandler
				.registerPacketHandler(	CommandLib.SEND_MATERIALS,
										new PacketMaterialListExecutor());

		ClientPacketHandler.registerPacketHandler(	PacketIds.LOGIN,
													materialHandler);
	}

	public static void registerPacketHandlers() {
	}

}
