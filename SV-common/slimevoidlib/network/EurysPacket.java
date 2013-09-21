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

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.network.packet.Packet250CustomPayload;

/**
 * Packet Information Base
 * 
 * @author Eurymachus
 * 
 */
public abstract class EurysPacket {
	/**
	 * Used to separate packets into a different send queue.
	 */
	public boolean	isChunkDataPacket	= false;

	/**
	 * The channel for the packet
	 */
	private String	channel;

	/**
	 * Sets the packet channel
	 * 
	 * @param channel
	 *            the channel to set
	 */
	public void setChannel(String channel) {
		this.channel = channel;
	}

	/**
	 * Writes data to the packet
	 * 
	 * @param data
	 *            the outputstream to write to
	 * 
	 * @throws IOException
	 *             if data is corrupt/null
	 */
	public abstract void writeData(DataOutputStream data) throws IOException;

	/**
	 * Reads data from the packet
	 * 
	 * @param data
	 *            the inputstream to read from
	 * 
	 * @throws IOException
	 *             if data is corrupt/null
	 */
	public abstract void readData(DataInputStream data) throws IOException;

	/**
	 * The packet ID usually listed with PacketIds.class
	 * 
	 * @return the Packet ID for this packet instance
	 */
	public abstract int getID();

	/**
	 * Gets a readable output for this packet instance
	 * 
	 * @param full
	 *            should return the full packet text
	 * 
	 * @return toString()
	 */
	public String toString(boolean full) {
		return toString();
	}

	/**
	 * Gets a readable output for this packet instance
	 */
	@Override
	public String toString() {
		return getID() + " " + getClass().getSimpleName();
	}

	/**
	 * Retrieves the Custom Packet and Payload data as Packet250CustomPayload
	 */
	public Packet250CustomPayload getPacket() {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		DataOutputStream data = new DataOutputStream(bytes);
		try {
			data.writeByte(getID());
			writeData(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = this.channel;
		packet.data = bytes.toByteArray();
		packet.length = packet.data.length;
		packet.isChunkDataPacket = this.isChunkDataPacket;
		return packet;
	}
}
