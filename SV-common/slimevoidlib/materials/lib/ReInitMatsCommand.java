package slimevoidlib.materials.lib;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

public class ReInitMatsCommand extends CommandBase {

	@Override
	public String getCommandName() {
		return "reloadslopes";
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		return "/reloadslopes";
	}

	@Override
	public void processCommand(ICommandSender icommandsender, String[] astring) {
		ConfigurationLib.getBaseBlockList(true);
		ConfigurationLib.reInitMaterials();
		if (FMLCommonHandler.instance().getSide() == Side.SERVER) {
			PacketLib.sendAllMaterialList();
		}
	}

}
