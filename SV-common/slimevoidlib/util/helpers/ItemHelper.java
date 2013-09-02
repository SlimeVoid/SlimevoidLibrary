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
			EntityItem item = new EntityItem(
					world,
					(double) x + xx,
					(double) y + yy,
					(double) z + zz,
					itemstack);
			item.age = 10;
			world.spawnEntityInWorld(item);
			return;
		}
	}
}
