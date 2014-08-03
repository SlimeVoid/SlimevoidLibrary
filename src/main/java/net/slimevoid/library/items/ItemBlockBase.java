package net.slimevoid.library.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.FMLLog;

public class ItemBlockBase extends ItemBlock {

    protected HashMap<Integer, String> itemBlockNames;
    private ArrayList<Integer>       validItemBlocks;

    public ItemBlockBase(Block block) {
        super(block);
        this.itemBlockNames = new HashMap<Integer, String>();
        this.setValidItemBlocks(new ArrayList<Integer>());
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    public void setMetaName(int damage, String name) {
        this.itemBlockNames.put(Integer.valueOf(damage),
                                name);
        this.getValidItemBlocks().add(Integer.valueOf(damage));
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack) {
        String itemName = (String) this.itemBlockNames.get(Integer.valueOf(itemstack.getItemDamage()));
        if (itemName == null) {
            FMLLog.severe("No Item Exists for this Item Damage");
            itemName = "item.null";
        }
        return itemName;
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i < this.getValidItemBlocks().size(); i++) {
            list.add(new ItemStack(item, 1, i));
        }
    }

    public ArrayList<Integer> getValidItemBlocks() {
        return validItemBlocks;
    }

    public void setValidItemBlocks(ArrayList<Integer> validItemBlocks) {
        this.validItemBlocks = validItemBlocks;
    }
}
