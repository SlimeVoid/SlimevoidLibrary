package net.slimevoid.library.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public abstract class PacketGuiEvent extends PacketUpdate {

    private int guiID;

    public PacketGuiEvent() {
        super(PacketIds.GUI);
    }

    public void setGuiID(int guiID) {
        this.guiID = guiID;
    }

    public int getGuiID() {
        return this.guiID;
    }

    @Override
    public void writeData(ChannelHandlerContext ctx, ByteBuf data) {
        super.writeData(ctx,
                        data);
        data.writeInt(this.guiID);
    }

    @Override
    public void readData(ChannelHandlerContext ctx, ByteBuf data) {
        super.readData(ctx,
                       data);
        this.guiID = data.readInt();
    }

}
