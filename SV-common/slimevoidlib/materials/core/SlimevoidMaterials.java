package slimevoidlib.materials.core;

import slimevoidlib.materials.lib.ConfigurationLib;
import slimevoidlib.materials.lib.CoreLib;
import slimevoidlib.materials.lib.ReInitMatsCommand;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.ServerStarting;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(
		modid = CoreLib.MOD_ID,
		name = CoreLib.MOD_NAME,
		version = CoreLib.MOD_VERSION,
		dependencies = CoreLib.MOD_DEPENDENCIES)
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class SlimevoidMaterials {

	@PreInit
	public static void MaterialsPreInit(FMLPreInitializationEvent event) {
		ConfigurationLib.CommonConfig(event.getSuggestedConfigurationFile());
	}

	@PostInit
	public static void MaterialsPostInit(FMLPostInitializationEvent event) {
		ConfigurationLib.reInitMaterials();
	}
	
	@ServerStarting
	public void registerCommand(FMLServerStartingEvent event) {
		//1.6.2 Only command
		//event.registerServerCommand(new ReInitMatsCommand());
	}
}
