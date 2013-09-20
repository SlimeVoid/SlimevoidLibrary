package slimevoidlib.materials.network.packet.executor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import slimevoidlib.IPacketExecutor;
import slimevoidlib.materials.lib.ConfigurationLib;
import slimevoidlib.materials.network.packet.PacketMaterialList;
import slimevoidlib.network.PacketUpdate;

public class PacketMaterialListExecutor implements IPacketExecutor {

	@Override
	public void execute(PacketUpdate packet, World world, EntityPlayer entityplayer) {
		if (packet instanceof PacketMaterialList) {
			PacketMaterialList packetList = (PacketMaterialList) packet;
			ConfigurationLib.setBaseBlockList(packetList.getMaterialList());
		}
	}

}
