package slimevoidlib.util.xml;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class XMLVariables {

	public static void registerDefaultXMLVariables() {
		for (Block block : Block.blocksList) {
			if (block != null) {
				XMLLoader.addXmlVariable(	"$" + block.getUnlocalizedName(),
											block.blockID);
			}
		}
		for (Item item : Item.itemsList) {
			if (item != null) {
				XMLLoader.addXmlVariable(	"$" + item.getUnlocalizedName(),
											item.itemID);

			}
		}
	}
}
