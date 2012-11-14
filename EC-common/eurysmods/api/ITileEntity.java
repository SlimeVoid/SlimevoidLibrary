package eurysmods.api;

import eurysmods.network.packets.core.PacketUpdate;
import net.minecraft.src.World;

public interface ITileEntity {
	public void handleUpdatePacket(World world, PacketUpdate packet);
}
