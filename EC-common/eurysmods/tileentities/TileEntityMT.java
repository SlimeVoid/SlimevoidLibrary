package eurysmods.tileentities;

import eurysmods.api.ITileEntityMT;
import eurysmods.network.packets.core.PacketTileEntityMT;
import eurysmods.network.packets.core.PacketUpdate;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.Packet;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public abstract class TileEntityMT extends TileEntity implements ITileEntityMT {

	private int textureValue;

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setInteger("textureValue", textureValue);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		textureValue = nbttagcompound.getInteger("textureValue");
	}

	@Override
	public void setTextureValue(int texture) {
		this.textureValue = texture;
	}

	@Override
	public int getTextureValue() {
		return this.textureValue;
	}

	@Override
	public Packet getDescriptionPacket() {
		return getUpdatePacket();
	}

	public abstract Packet getUpdatePacket();

	@Override
	public void handleUpdatePacket(World world, PacketUpdate packet) {
		this.setTextureValue(((PacketTileEntityMT) packet).getTextureValue());
		this.onInventoryChanged();
		world.markBlockNeedsUpdate(
				packet.xPosition,
				packet.yPosition,
				packet.zPosition);
	}
}
