package net.slimevoid.library.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.world.World;

public abstract class ContainerBase extends Container {

    protected InventoryPlayer playerInventory;
    protected IInventory      customInventory;
    protected World           world;

    public ContainerBase(InventoryPlayer playerInventory, IInventory customInventory, World world, int playerColOffset, int playerRowOffset) {
        super();
        this.playerInventory = playerInventory;
        this.customInventory = customInventory;
        this.world = world;
        this.bindLocalInventory();
        if (this.shouldBindPlayerInventory()) {
            this.bindPlayerInventory(playerColOffset,
                                     playerRowOffset);
        }
    }

    protected boolean shouldBindPlayerInventory() {
        return true;
    }

    protected abstract void bindLocalInventory();

    protected void bindUpperInventory(int playerColOffset, int playerRowOffset) {
        // Player inventory
        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 9; ++column) {
                int slotIndex = column + (row * 9);
                this.addSlotToContainer(new Slot(new InventorySubUpdate(ContainerBase.this, playerInventory, 9, 27), slotIndex, (8 + column * 18 + playerColOffset), (row * 18 + playerRowOffset)));
            }
        }
    }

    protected void bindHotBarInventory(int playerColOffset, int playerRowOffset) {
        // Hotbar inventory
        for (int row = 0; row < 9; ++row) {
            int slotIndex = row;
            this.addSlotToContainer(new Slot(playerInventory, slotIndex, (8 + row * 18 + playerColOffset), 58 + playerRowOffset));
        }
    }

    protected void bindPlayerInventory(int playerColOffset, int playerRowOffset) {
        this.bindUpperInventory(playerColOffset,
                                playerRowOffset);
        this.bindHotBarInventory(playerColOffset,
                                 playerRowOffset);
    }

    public InventoryPlayer getPlayerInventory() {
        return this.playerInventory;
    }

    public IInventory getInventoryData() {
        return this.customInventory;
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer) {
        return this.customInventory.isUseableByPlayer(entityplayer);
    }

    /**
     * Fake Container Class
     * 
     */

    protected static class ContainerNull extends Container {

        @Override
        public boolean canInteractWith(EntityPlayer entityplayer) {
            return false;
        }

        @Override
        public void onCraftMatrixChanged(IInventory inventory) {
        }

        public ContainerNull() {
        }
    }

}
