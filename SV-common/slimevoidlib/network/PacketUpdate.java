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
package slimevoidlib.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import slimevoidlib.nbt.NBTHelper;

/**
 * Packet Information for Reading/Writing packet data
 * 
 * packetId The ID of the packet used to identify which packet handler to use
 * payload The payload to be delivered with the packet xPosition The value x for
 * the current packet yPosition The value y for the current packet zPosition The
 * value z for the current packet side The value side for the current packet
 * (Used for blocks and activation) hitX The hitX for the current packet
 * (Used for blocks and activation) hitY The hitY for the current packet
 * (Used for blocks and activation) hitZ the hitZ for the current packet
 * 
 * @author Eurymachus
 * 
 */
public abstract class PacketUpdate extends EurysPacket {
	private int packetId;

	public PacketPayload payload;

	public int xPosition;
	public int yPosition;
	public int zPosition;
	public int side;

	public float hitX;
	public float hitY;
	public float hitZ;
	
	public String command;

	/**
	 * Set the position x, y, z and side if applicable
	 * 
	 * @param x
	 *            The x position
	 * @param y
	 *            The y position
	 * @param z
	 *            The z position
	 * @param side
	 *            The side (if applicable)
	 */
	public void setPosition(int x, int y, int z, int side) {
		this.xPosition = x;
		this.yPosition = y;
		this.zPosition = z;
		this.side = side;
	}

	/**
	 * Set the selected vector positions (if applicable)
	 * 
	 * @param hitX
	 *            The selected vector on x
	 * @param hitY
	 *            The selected vector on y
	 * @param hitZ
	 *            The selected vector on z
	 */
	public void setHitVectors(float hitX, float hitY, float hitZ) {
		this.hitX = hitX;
		this.hitY = hitY;
		this.hitZ = hitZ;
	}

	public PacketUpdate(int packetId, PacketPayload payload) {
		this(packetId);
		this.payload = payload;
	}

	public PacketUpdate(int packetId) {
		this.packetId = packetId;
		this.isChunkDataPacket = true;
	}

	/**
	 * Writes a String to the DataOutputStream
	 */
	protected static void writeString(String par0Str,
			DataOutputStream par1DataOutputStream) throws IOException {
		NBTHelper.writeString(par0Str, par1DataOutputStream);
	}

	/**
	 * Reads a string from a packet
	 */
	protected static String readString(DataInputStream par0DataInputStream,
			int par1) throws IOException {
		return NBTHelper.readString(par0DataInputStream, par1);
	}

	/**
	 * Writes a compressed NBTTagCompound to the OutputStream
	 */
	protected static void writeNBTTagCompound(
			NBTTagCompound nbttagcompound,
			DataOutputStream data) throws IOException {
		NBTHelper.writeNBTTagCompound(nbttagcompound, data);
	}

	/**
	 * Reads a compressed NBTTagCompound from the InputStream
	 */
	protected static NBTTagCompound readNBTTagCompound(
			DataInputStream data) throws IOException {
		return NBTHelper.readNBTTagCompound(data);
	}

	@Override
	public void writeData(DataOutputStream data) throws IOException {
		if (this.command == null || this.command.isEmpty() || this.command.equals("")) {
			data.writeUTF("sv");
		} else {
			data.writeUTF(this.getCommand());
		}

		data.writeInt(this.xPosition);
		data.writeInt(this.yPosition);
		data.writeInt(this.zPosition);

		// Checks if the side, vecX, vecY and vecZ values have been set defaults
		// to 0 if not
		data.writeInt(Integer.valueOf(this.side) != null ? this.side : 0);
		data.writeFloat(Float.valueOf(this.hitX) != null ? this.hitX : 0.0F);
		data.writeFloat(Float.valueOf(this.hitY) != null ? this.hitY : 0.0F);
		data.writeFloat(Float.valueOf(this.hitZ) != null ? this.hitZ : 0.0F);

		// No payload means no additional data
		if (this.payload == null) {
			data.writeInt(0);
			data.writeInt(0);
			data.writeInt(0);
			data.writeInt(0);
			data.writeInt(0);
			return;
		}

		// Continue writing payload information
		data.writeInt(this.payload.getIntSize());
		data.writeInt(this.payload.getFloatSize());
		data.writeInt(this.payload.getStringSize());
		data.writeInt(this.payload.getBoolSize());
		data.writeInt(this.payload.getDoubleSize());

		for (int i = 0; i < this.payload.getIntSize(); i++)
			data.writeInt(this.payload.getIntPayload(i));
		for (int i = 0; i < this.payload.getFloatSize(); i++)
			data.writeFloat(this.payload.getFloatPayload(i));
		for (int i = 0; i < this.payload.getStringSize(); i++)
			data.writeUTF(this.payload.getStringPayload(i));
		for (int i = 0; i < this.payload.getBoolSize(); i++)
			data.writeBoolean(this.payload.getBoolPayload(i));
		for (int i = 0; i < this.payload.getDoubleSize(); i++)
			data.writeDouble(this.payload.getDoublePayload(i));
	}

	@Override
	public void readData(DataInputStream data) throws IOException {
		this.setCommand(data.readUTF());

		this.setPosition(data.readInt(), data.readInt(), data.readInt(),
				data.readInt());
		this.setHitVectors(data.readFloat(), data.readFloat(), data.readFloat());

		int intSize = data.readInt();
		int floatSize = data.readInt();
		int stringSize = data.readInt();
		int boolSize = data.readInt();
		int doubleSize = data.readInt();

		this.payload = new PacketPayload(intSize, floatSize, stringSize,
				boolSize, doubleSize);

		for (int i = 0; i < this.payload.getIntSize(); i++)
			this.payload.setIntPayload(i, data.readInt());
		for (int i = 0; i < this.payload.getFloatSize(); i++)
			this.payload.setFloatPayload(i, data.readFloat());
		for (int i = 0; i < this.payload.getStringSize(); i++)
			this.payload.setStringPayload(i, data.readUTF());
		for (int i = 0; i < this.payload.getBoolSize(); i++)
			this.payload.setBoolPayload(i, data.readBoolean());
		for (int i = 0; i < this.payload.getDoubleSize(); i++)
			this.payload.setDoublePayload(i, data.readDouble());
	}

	/**
	 * Used to check that a target of (usually x, y, z) exists
	 * 
	 * @param world
	 *            The World in which to check for target
	 * 
	 * @return true or false
	 */
	public abstract boolean targetExists(World world);
	
	public String getCommand() {
		return !this.command.isEmpty() ? this.command : "";
	}
	
	public void setCommand(String command) {
		this.command = command;
	}

	@Override
	public int getID() {
		return this.packetId;
	}
}