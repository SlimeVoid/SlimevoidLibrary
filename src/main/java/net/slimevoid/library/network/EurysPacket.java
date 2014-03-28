package net.slimevoid.library.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * Packet Information Base
 * 
 * @author Eurymachus
 * 
 */
public abstract class EurysPacket {
    private int packetId;

    /**
     * The packet ID usually listed with PacketIds.class
     * 
     * @return the Packet ID for this packet instance
     */
    public int getPacketId() {
        return this.packetId;
    }

    public void setPacketId(int packetId) {
        this.packetId = packetId;
    }

    /**
     * The channel for the packet
     */
    private String channel;

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
     * @param ctx    channel context
     * @param buffer the buffer to encode into
     */
    public abstract void writeData(ChannelHandlerContext ctx, ByteBuf buffer);

    /**
     * Decode the packet data from the ByteBuf stream. Complex data sets may need specific data handlers (See @link{cpw.mods.fml.common.network.ByteBuffUtils})
     *
     * @param ctx    channel context
     * @param buffer the buffer to decode from
     */
    public abstract void readData(ChannelHandlerContext ctx, ByteBuf buffer);

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
        return getPacketId() + " " + getClass().getSimpleName();
    }
}
