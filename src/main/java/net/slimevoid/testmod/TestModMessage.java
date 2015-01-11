package net.slimevoid.testmod;

import net.slimevoid.library.network.PacketUpdate;

public class TestModMessage extends PacketUpdate {
	
	public TestModMessage() {
		super();
		this.setChannel("SlimevoidTest");
	}

}
