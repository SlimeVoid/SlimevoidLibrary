package slimevoidlib.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.world.World;

public abstract class ContainerBase extends Container {

	protected InventoryPlayer playerInventory;
	protected IInventory customInventory;
	protected World world;
	
	public ContainerBase(InventoryPlayer playerInventory, IInventory customInventory, World world, int playerColOffset, int playerRowOffset) {
		super();
		this.playerInventory = playerInventory;
		this.customInventory = customInventory;
		this.world = world;
		this.bindPlayerInventory(playerColOffset, playerRowOffset);
		this.bindLocalInventory();
	}
	
	protected abstract void bindLocalInventory();

	protected void bindPlayerInventory(int playerColOffset, int playerRowOffset) {
		// Player inventory
		for (int row = 0; row < 3; ++row) {
			for (int column = 0; column < 9; ++column) {
				int slotIndex = 9 + column + (row * 9);
				this.addSlotToContainer(new Slot(this.playerInventory, slotIndex, (8 + column * 18 + playerColOffset), (row * 18 + playerRowOffset)));
			}
		}

		// Hotbar inventory
		for (int row = 0; row < 9; ++row) {
			int slotIndex = row;
			this.addSlotToContainer(new Slot(this.playerInventory, slotIndex, (8 + row * 18 + playerColOffset), 58 + playerRowOffset));
		}
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

}
