package net.slimevoid.library.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.ByteBufUtils;

/**
 * Packet Information for Reading/Writing packet data
 * 
 * packetId The ID of the packet used to identify which packet handler to use
 * payload The payload to be delivered with the packet xPosition The value x for
 * the current packet yPosition The value y for the current packet zPosition The
 * value z for the current packet side The value side for the current packet
 * (Used for blocks and activation) hitX The hitX for the current packet (Used
 * for blocks and activation) hitY The hitY for the current packet (Used for
 * blocks and activation) hitZ the hitZ for the current packet
 * 
 * @author Eurymachus
 * 
 */
public abstract class PacketUpdate extends EurysPacket {
    public PacketPayload payload;

    public int           xPosition;
    public int           yPosition;
    public int           zPosition;
    public int           side;

    public float         hitX;
    public float         hitY;
    public float         hitZ;

    public String        command;

    public PacketUpdate(int packetId) {
        this.setPacketId(packetId);
    }

    public PacketUpdate(int packetId, PacketPayload payload) {
        this.setPacketId(packetId);
        this.payload = payload;
    }

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

    public String getCommand() {
        return !this.command.isEmpty() ? this.command : "";
    }

    public void setCommand(String command) {
        this.command = command;
    }

    @Override
    public void writeData(ChannelHandlerContext ctx, ByteBuf data) {
        data.writeByte(this.getPacketId());

        ByteBufUtils.writeUTF8String(data,
                                     this.getCommand());

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
            ByteBufUtils.writeUTF8String(data,
                                         this.payload.getStringPayload(i));
        for (int i = 0; i < this.payload.getBoolSize(); i++)
            data.writeBoolean(this.payload.getBoolPayload(i));
        for (int i = 0; i < this.payload.getDoubleSize(); i++)
            data.writeDouble(this.payload.getDoublePayload(i));
    }

    @Override
    public void readData(ChannelHandlerContext ctx, ByteBuf data) {
        // this.setPacketId(data.readByte());

        this.setCommand(ByteBufUtils.readUTF8String(data));

        this.setPosition(data.readInt(),
                         data.readInt(),
                         data.readInt(),
                         data.readInt());
        this.setHitVectors(data.readFloat(),
                           data.readFloat(),
                           data.readFloat());

        int intSize = data.readInt();
        int floatSize = data.readInt();
        int stringSize = data.readInt();
        int boolSize = data.readInt();
        int doubleSize = data.readInt();

        this.payload = new PacketPayload(intSize, floatSize, stringSize, boolSize, doubleSize);

        for (int i = 0; i < this.payload.getIntSize(); i++)
            this.payload.setIntPayload(i,
                                       data.readInt());
        for (int i = 0; i < this.payload.getFloatSize(); i++)
            this.payload.setFloatPayload(i,
                                         data.readFloat());
        for (int i = 0; i < this.payload.getStringSize(); i++)
            this.payload.setStringPayload(i,
                                          ByteBufUtils.readUTF8String(data));
        for (int i = 0; i < this.payload.getBoolSize(); i++)
            this.payload.setBoolPayload(i,
                                        data.readBoolean());
        for (int i = 0; i < this.payload.getDoubleSize(); i++)
            this.payload.setDoublePayload(i,
                                          data.readDouble());
    }

    public boolean targetExists(World world) {
        return false;
    }
}
