package net.slimevoid.testmod;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.slimevoid.library.util.helpers.PacketHelper;

//@Mod(
//        modid = "SlimevoidTest",
//        name = "SlimevoidTestMod",
//        version = "1.8")
public class TestMod {
	
	@EventHandler
	public void TestModPreInit(FMLPreInitializationEvent event) {
		PacketHelper.registerHandler();
		PacketHelper.registerServerExecutor(TestModMessageHandler.class, TestModMessage.class, 0);
		GameRegistry.registerBlock(new TestModBlock(), "slimevoid.TestBlock");
	}
}
