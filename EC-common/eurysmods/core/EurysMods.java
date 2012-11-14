package eurysmods.core;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;

@Mod(modid = "EurysCore", name = "Eurys Mod Core", version = "1.3.2.0")
@NetworkMod(
		clientSideRequired = true,
		serverSideRequired = false,
		versionBounds = "[1.3.2]")
public class EurysMods {
}
