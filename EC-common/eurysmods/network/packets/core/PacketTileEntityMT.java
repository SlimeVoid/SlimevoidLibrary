package eurysmods.network.packets.core;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import eurysmods.tileentities.TileEntityMT;








public class PacketTileEntityMT extends PacketTileEntity {
	private int textureValue;

	public PacketTileEntityMT() {
		super();
	}

	public PacketTileEntityMT(String channel) {
		super();
		this.setChannel(channel);
	}

	public PacketTileEntityMT(String channel, TileEntityMT tileentitymt) {
		this(channel);
		this.setTextureValue(tileentitymt.getTextureValue());
		this.setPosition(
				tileentitymt.xCoord,
				tileentitymt.yCoord,
				tileentitymt.zCoord,
				0);
		this.isChunkDataPacket = true;
	}

	public int getTextureValue() {
		return this.textureValue;
	}

	public void setTextureValue(int textureValue) {
		this.textureValue = textureValue;
	}

	@Override
	public void writeData(DataOutputStream data) throws IOException {
		super.writeData(data);
		data.writeInt(this.textureValue);
	}

	@Override
	public void readData(DataInputStream data) throws IOException {
		super.readData(data);
		this.textureValue = data.readInt();
	}
}
