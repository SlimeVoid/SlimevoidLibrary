package net.slimevoid.library.network;

import io.netty.buffer.ByteBuf;

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
    public void writeData(ByteBuf data) {
        super.writeData(data);
        data.writeInt(this.guiID);
    }

    @Override
    public void readData(ByteBuf data) {
        super.readData(data);
        this.guiID = data.readInt();
    }

}
