package net.slimevoid.library.util.xml;

import java.util.Iterator;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class XMLVariables {

    public static void registerDefaultXMLVariables() {
        Iterator<Block> blocks = Block.blockRegistry.iterator();
        while (blocks.hasNext()) {
            Block block = blocks.next();
            if (block != null) {
                XMLLoader.addXmlVariable("$" + block.getUnlocalizedName(),
                                         Block.getIdFromBlock(block));
            }
        }
        Iterator<Item> items = Item.itemRegistry.iterator();
        while (items.hasNext()) {
            Item item = items.next();
            if (item != null && !(item instanceof ItemBlock)) {
                XMLLoader.addXmlVariable("$" + item.getUnlocalizedName(),
                                         Item.getIdFromItem(item));

            }
        }
    }
}
