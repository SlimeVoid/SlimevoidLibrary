package net.slimevoid.library.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IChatComponent;

public class InventorySubUpdate implements IInventory {

    int size;
    int start;
    IInventory parent;
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
    public String getCommandSenderName() {
        return parent.getCommandSenderName();
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
    public void openInventory(EntityPlayer entityplayer) {
    }

    @Override
    public void closeInventory(EntityPlayer entityplayer) {
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        return true;
    }

    @Override
    public IChatComponent getDisplayName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getField(int id) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setField(int id, int value) {
        // TODO Auto-generated method stub

    }

    @Override
    public int getFieldCount() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void clear() {
        // TODO Auto-generated method stub

    }
}
