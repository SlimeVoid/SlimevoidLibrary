package slimevoidlib.tileentity;

import java.util.ArrayList;
import java.util.Iterator;

import slimevoidlib.core.lib.BlockLib;
import slimevoidlib.core.lib.ItemLib;
import slimevoidlib.core.lib.NBTLib;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public abstract class TileEntityBase extends TileEntity {
	
	protected long tickSchedule;
	
	public abstract int getLightValue();
	
	public abstract int getBlockTexture(int x, int y, int z, int side);
	
	public TileEntityBase () {
		this.tickSchedule = -1L;
	}
	
	public void onBlockNeighborChange(int direction) {
		
	}
	
	public void onBlockPlaced(ItemStack itemstack, int side, EntityLivingBase entity) {
		
	}
	
	public void onBlockRemoval() {
		
	}

	public void onTileTick() {
		
	}

	public boolean onBlockActivated(EntityPlayer entityplayer) {
		System.out.println("Base Activated");
		return false;
	}
	
	public int getDamageValue() {
		return 0;
	}
	
	public abstract int getBlockID();
	
	public int getExtendedMetadata() {
		return 0;
	}
	
	public void setExtendedMetadata(int metadata) {
		
	}

	public void addHarvestContents(ArrayList<ItemStack> harvestList) {
		harvestList.add(
			new ItemStack(
				this.getBlockID(),
				1, 
				this.getDamageValue()));
	}

	public void scheduleTick(int time) {
		long worldTime = this.worldObj.getWorldTime() + (long) time;
		if (this.tickSchedule > 0L && this.tickSchedule < worldTime) {
			return;
		} else {
			this.tickSchedule = worldTime;
			this.dirtyBlock();
			return;
		}
	}

	
	public void updateBlockChange() {
		BlockLib.updateIndirectNeighbors(this.worldObj, this.xCoord, this.yCoord, this.zCoord, this.getBlockID());
		this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
	}
	
	public void updateBlock() {
		this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		BlockLib.markBlockDirty(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
	}
	
	public void dirtyBlock() {
		BlockLib.markBlockDirty(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
	}
	
	public void breakBlock() {
		ArrayList<ItemStack> harvestList = new ArrayList<ItemStack>();
		this.addHarvestContents(harvestList);
		ItemStack itemstack;
		for (Iterator<ItemStack> stack = harvestList.iterator(); stack.hasNext();
				ItemLib.dropItem(this.worldObj, this.xCoord, this.yCoord, this.zCoord, itemstack)
			) {
			itemstack = (ItemStack) stack.next();
		}
	}

	@Override
	public void updateEntity() {
		if (this.worldObj.isRemote)
			return;
		if (this.tickSchedule < 0L)
			return;
		long worldTime = this.worldObj.getWorldTime();
		if (this.tickSchedule > worldTime + 1200L)
			this.tickSchedule = worldTime + 1200L;
		else if (this.tickSchedule <= worldTime) {
			this.tickSchedule = -1L;
			this.onTileTick();
			this.dirtyBlock();
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		this.tickSchedule = nbttagcompound.getLong(NBTLib.TILE_TICK_SCHEDULE);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setLong(NBTLib.TILE_TICK_SCHEDULE, this.tickSchedule);
	}
}
