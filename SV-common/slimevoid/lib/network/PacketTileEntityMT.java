/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version. This program is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details. You should have received a copy of the GNU
 * Lesser General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>
 */
package slimevoid.lib.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import slimevoid.lib.tileentity.TileEntityMT;


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
