package slimevoidlib.materials.network.packet;

import net.minecraft.world.World;
import slimevoidlib.materials.lib.CoreLib;
import slimevoidlib.network.PacketIds;
import slimevoidlib.network.PacketPayload;
import slimevoidlib.network.PacketUpdate;

public class PacketMaterialList extends PacketUpdate {

	public PacketMaterialList() {
		super(PacketIds.LOGIN);
		this.setChannel(CoreLib.MOD_CHANNEL);
	}

	public PacketMaterialList(String[] materialList) {
		super(PacketIds.LOGIN, new PacketPayload(0, 0, materialList.length, 0));
		this.setChannel(CoreLib.MOD_CHANNEL);
		this.setMaterialList(materialList);
	}

	private void setMaterialList(String[] materialList) {
		for (int i = 0; i < materialList.length; i++) {
			this.payload.setStringPayload(i, materialList[i]);
		}
	}

	public String[] getMaterialList() {
		String[] list = new String[this.payload.getStringSize()];
		for (int i = 0; i < list.length; i++) {
			list[i] = this.payload.getStringPayload(i);
		}
		return list;
	}

	@Override
	public boolean targetExists(World world) {
		// TODO Auto-generated method stub
		return false;
	}

}
