package slimevoidlib.items;

import java.util.ArrayList;
import java.util.HashMap;

import slimevoidlib.blocks.BlockBase;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBlockBase extends ItemBlock {
	
	protected HashMap<Integer, String> itemBlockNames;
	protected ArrayList<Integer> validItemBlocks;

	public ItemBlockBase(int itemId) {
		super(itemId);
		itemBlockNames = new HashMap<Integer, String>();
		validItemBlocks = new ArrayList<Integer>();
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int damage) {
		return damage;
	}
	
	public void setMetaName(int damage, String name) {
		itemBlockNames.put(Integer.valueOf(damage), name);
		validItemBlocks.add(Integer.valueOf(damage));
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		String itemName = (String) itemBlockNames.get(Integer.valueOf(itemstack.getItemDamage()));
		if (itemName == null) {
			throw new IndexOutOfBoundsException();
		} else {
			return itemName;
		}
	}
	
	@Override
	public boolean placeBlockAt(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
		if (!world.setBlock(x, y, z, this.itemID, metadata, 0x3)) {
			return false;
		}
		if (world.getBlockId(x, y, z) == this.itemID) {
			BlockBase blockBase = (BlockBase) Block.blocksList[this.itemID];
			if (blockBase != null) {
				blockBase.onBlockPlaced(world, x, y, z, side, entityplayer, itemstack);
			}
		}
		return true;
	}
}
