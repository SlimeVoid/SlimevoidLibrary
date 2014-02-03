package slimevoidlib.util.xml;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class XMLVariables {

    public static void registerDefaultXMLVariables() {
        for (Block block : Block.blocksList) {
            if (block != null) {
                XMLLoader.addXmlVariable("$" + block.getUnlocalizedName(),
                                         block.blockID);
            }
        }
        for (Item item : Item.itemsList) {
            if (item != null && !(item instanceof ItemBlock)) {
                XMLLoader.addXmlVariable("$" + item.getUnlocalizedName(),
                                         item.itemID);

            }
        }
    }
}
