package slimevoidlib.util.helpers;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemHelper {

	public static void dropItem(World world, int x, int y, int z, ItemStack itemstack) {
		if (world.isRemote) {
			return;
		} else {
			double d = 0.69999999999999996D;
			double xx = (double) world.rand.nextFloat() * d + (1.0D - d) * 0.5D;
			double yy = (double) world.rand.nextFloat() * d + (1.0D - d) * 0.5D;
			double zz = (double) world.rand.nextFloat() * d + (1.0D - d) * 0.5D;
			EntityItem item = new EntityItem(world, (double) x + xx, (double) y
																		+ yy, (double) z
																				+ zz, itemstack);
			item.age = 10;
			world.spawnEntityInWorld(item);
			return;
		}
	}

	public static String correctName(String name) {
		return Character.toUpperCase(name.charAt(0)) + name.substring(1);
	}

	public static String itemstackArrayToIntegers(Object[] input) {
		String concat = "";
		for (int i = 0; i < input.length; i++) {
			String itemstack = null;
			if (input[i] instanceof ItemStack) {
				itemstack = itemstackToInteger((ItemStack) input[i]);
			}
			if (itemstack != null) {
				concat += itemstack;
				concat += i < input.length - 1 ? " : " : "";
			}
		}
		return concat;
	}

	public static String itemstackToInteger(ItemStack itemstack) {
		return itemstack != null ? itemstack.itemID + " | "
									+ itemstack.stackSize : "null";
	}

	public static String itemstackArrayToStrings(Object[] input) {
		String concat = "";
		for (int i = 0; i < input.length; i++) {
			concat += i > 0 ? " + " : "";
			if (input[i] instanceof ItemStack) {
				concat += itemstackToString((ItemStack) input[i]);
			}
		}
		return concat;
	}

	public static String itemstackToString(ItemStack itemstack) {
		return itemstack != null ? itemstack.getDisplayName() + " | "
									+ itemstack.stackSize : "null";
	}
}
