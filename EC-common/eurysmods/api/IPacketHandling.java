package eurysmods.api;

import eurysmods.network.packets.core.PacketTileEntity;
import eurysmods.network.packets.core.PacketUpdate;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;

public interface IPacketHandling {
	public void handleTileEntityPacket(PacketTileEntity packet, EntityPlayer entityplayer, World world);

	public void handleGuiPacket(PacketUpdate packet, EntityPlayer entityplayer, World world);

	public void handlePacket(PacketUpdate packet, EntityPlayer entityplayer, World world);
}