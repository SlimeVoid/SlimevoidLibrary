package slimevoidlib.materials.api;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IPlacementHandler {

	public abstract boolean onPlaceBlock(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int i);

	public abstract void addCreativeItems(int i, CreativeTabs tabs, List tabslist, int matIndex);

	public abstract boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata);

	public abstract String getUnlocalizedName();

	public abstract String getLocalizedName();

}
