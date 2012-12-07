package eurysmods.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.INetworkManager;
import net.minecraft.src.NetHandler;
import net.minecraft.src.Packet1Login;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import cpw.mods.fml.common.network.Player;
import eurysmods.api.ICommonProxy;
import eurysmods.api.IPacketHandling;

public class EC_ClientProxy extends EC_CommonProxy {

	@Override
	public String getMinecraftDir() {
		return Minecraft.getMinecraftDir().getPath();
	}
}
