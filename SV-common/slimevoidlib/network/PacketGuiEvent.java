package slimevoidlib.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class PacketGuiEvent extends PacketUpdate {

	private int	guiID;

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
	public void writeData(DataOutputStream data) throws IOException {
		super.writeData(data);
		data.writeInt(this.guiID);
	}

	@Override
	public void readData(DataInputStream data) throws IOException {
		super.readData(data);
		this.guiID = data.readInt();
	}

}
