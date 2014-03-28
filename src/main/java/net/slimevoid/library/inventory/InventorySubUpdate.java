package net.slimevoid.library.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class InventorySubUpdate implements IInventory {

    int           size;
    int           start;
    IInventory    parent;
    ContainerBase container;

    public InventorySubUpdate(ContainerBase container, IInventory parentInventory, int startSlot, int inventorySize) {
        super();
        this.parent = parentInventory;
        this.start = startSlot;
        this.size = inventorySize;
        this.container = container;
    }

    @Override
    public int getSizeInventory() {
        return size;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return parent.getStackInSlot(slot + start);
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        ItemStack itemstack = parent.decrStackSize(slot + start,
                                                   amount);
        if (itemstack != null) {
        }
        return itemstack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        return parent.getStackInSlotOnClosing(slot + start);
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack ist) {
        parent.setInventorySlotContents(slot + start,
                                        ist);
    }

    @Override
    public String getInventoryName() {
        return parent.getInventoryName();
    }

    @Override
    public int getInventoryStackLimit() {
        return parent.getInventoryStackLimit();
    }

    @Override
    public void markDirty() {
        parent.markDirty();
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer) {
        return false;
    }

    @Override
    public void openInventory() {
    }

    @Override
    public void closeInventory() {
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        return true;
    }
}
