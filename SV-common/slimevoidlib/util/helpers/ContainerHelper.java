package slimevoidlib.util.helpers;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerHelper {
	public static boolean moveSingleItem(Container container, ItemStack stackInSlot, int i) {
		Slot slot = (Slot) container.inventorySlots.get(i);
		ItemStack itemstack1 = slot.getStack();
		if (itemstack1 == null) {
			slot.putStack(stackInSlot.copy());
			slot.getStack().stackSize = 1;
			slot.onSlotChanged();
			--stackInSlot.stackSize;
			return true;
		}
		return false;
	}

	public static boolean mergeItemStack(Container container, ItemStack par1ItemStack, int par2, int par3, boolean par4) {
		boolean flag1 = false;
		int k = par2;

		if (par4) {
			k = par3 - 1;
		}

		Slot slot;
		ItemStack itemstack1;

		if (par1ItemStack.isStackable()) {
			while (par1ItemStack.stackSize > 0
					&& (!par4 && k < par3 || par4 && k >= par2)) {
				slot = (Slot) container.inventorySlots.get(k);
				itemstack1 = slot.getStack();

				if (itemstack1 != null
					&& itemstack1.itemID == par1ItemStack.itemID
					&& (!par1ItemStack.getHasSubtypes() || par1ItemStack.getItemDamage() == itemstack1.getItemDamage())
					&& ItemStack.areItemStackTagsEqual(	par1ItemStack,
														itemstack1)) {
					int l = itemstack1.stackSize + par1ItemStack.stackSize;

					if (l <= par1ItemStack.getMaxStackSize()
						&& l <= slot.getSlotStackLimit()) {
						par1ItemStack.stackSize = 0;
						itemstack1.stackSize = l;
						slot.onSlotChanged();
						flag1 = true;
					} else if (itemstack1.stackSize < par1ItemStack.getMaxStackSize()
								&& itemstack1.stackSize < slot.getSlotStackLimit()) {
						par1ItemStack.stackSize -= par1ItemStack.getMaxStackSize()
													- itemstack1.stackSize;
						itemstack1.stackSize = par1ItemStack.getMaxStackSize();
						slot.onSlotChanged();
						flag1 = true;
					}
				}

				if (par4) {
					--k;
				} else {
					++k;
				}
			}
		}

		if (par1ItemStack.stackSize > 0) {
			if (par4) {
				k = par3 - 1;
			} else {
				k = par2;
			}

			while (!par4 && k < par3 || par4 && k >= par2) {
				slot = (Slot) container.inventorySlots.get(k);
				itemstack1 = slot.getStack();

				if (itemstack1 == null) {
					slot.putStack(par1ItemStack.copy());
					if (par1ItemStack.stackSize > slot.getSlotStackLimit()) {
						slot.getStack().stackSize = slot.getSlotStackLimit();
						par1ItemStack.stackSize -= slot.getSlotStackLimit();
					} else {
						par1ItemStack.stackSize = 0;
						flag1 = true;
					}
					slot.onSlotChanged();
					break;
				}

				if (par4) {
					--k;
				} else {
					++k;
				}
			}
		}

		return flag1;
	}
}
