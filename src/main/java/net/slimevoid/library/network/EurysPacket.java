package net.slimevoid.library.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

/**
 * Packet Information Base
 *
 * @author Eurymachus
 */
public abstract class EurysPacket implements IMessage {
    /**
     * The ID of the packet
     */
    private int packetId;

    /**
     * Set packet ID
     *
     * @param packetId
     */
    public void setPacketId(int packetId) {
        this.packetId = packetId;
    }

    /**
     * Retrieve the packet ID
     *
     * @return the packetID
     */
    public int getPacketId() {
        return this.packetId;
    }

    /**
     * The channel for the packet
     */
    private String channel;

    /**
     * Sets the packet channel
     *
     * @param channel the channel to set
     */
    public void setChannel(String channel) {
        this.channel = channel;
    }

    /**
     * Retrieves the channel for this packet
     *
     * @return channel
     */
    public String getChannel() {
        return this.channel;
    }

    /**
     * Encode the packet data into the ByteBuf stream. Complex data sets may need specific data handlers (See @link{cpw.mods.fml.common.network.ByteBuffUtils})
     *
     * @param buffer the buffer to encode into
     */
    public abstract void writeData(ByteBuf buffer);

    @Override
    public void toBytes(ByteBuf buffer) {
        this.writeData(buffer);
    }

    /**
     * Decode the packet data from the ByteBuf stream. Complex data sets may need specific data handlers (See @link{cpw.mods.fml.common.network.ByteBuffUtils})
     *
     * @param buffer the buffer to decode from
     */
    public abstract void readData(ByteBuf buffer);

    @Override
    public void fromBytes(ByteBuf buffer) {
        this.readData(buffer);
    }

    /**
     * Gets a readable output for this packet instance
     *
     * @param full should return the full packet text
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
        return this.getClass().getSimpleName();
    }
}
