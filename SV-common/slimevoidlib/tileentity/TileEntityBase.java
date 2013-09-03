package slimevoidlib.tileentity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.StepSound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import slimevoidlib.blocks.BlockBase;
import slimevoidlib.core.lib.NBTLib;
import slimevoidlib.util.helpers.BlockHelper;
import slimevoidlib.util.helpers.ItemHelper;

public abstract class TileEntityBase extends TileEntity {

	protected long tickSchedule;
	protected int rotation;
	protected boolean active;
	
	public TileEntityBase() {
		this.tickSchedule = -1L;
		this.rotation = 0;
		this.active = false;
	}
	
	public void onBlockNeighborChange(int direction) {
		
	}

	public int onBlockPlaced(int side, float hitX, float hitY, float hitZ, int damage) {
		return damage;
	}
	
	public void onBlockPlacedBy(ItemStack itemstack, EntityLivingBase entity) {
		this.rotation = (int) Math.floor((double)((entity.rotationYaw * 4F) / 360F) + 0.5D) & 3;
	}
	
	public int getRotation() {
		return this.rotation;
	}
	
	public void setRotation(int newRotation) {
		this.rotation = newRotation;
	}
	
	public void onBlockRemoval(int side, int metadata) {
		
	}

	public void onTileTick() {
		
	}

	public boolean onBlockActivated(EntityPlayer entityplayer) {
		return false;
	}
	
	public int getExtendedBlockID() {
		return 0;
	}
	
	public abstract int getBlockID();
	
	public int getExtendedMetadata() {
		return 0;
	}
	
    public float getBlockHardness(World par1World, int par2, int par3, int par4) {
    	return 0.0F;
    }

	public void addHarvestContents(ArrayList<ItemStack> harvestList) {
		harvestList.add(
			new ItemStack(
				this.getBlockID(),
				1, 
				this.getExtendedBlockID()));
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
	
	public int getLightValue() {
		return 0;
	}

	
	public void updateBlockChange() {
		BlockHelper.updateIndirectNeighbors(this.worldObj, this.xCoord, this.yCoord, this.zCoord, this.getBlockID());
		this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
	}
	
	public void updateBlock() {
		this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		BlockHelper.markBlockDirty(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
	}
	
	public void dirtyBlock() {
		BlockHelper.markBlockDirty(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
	}
	
	public void breakBlock() {
		ArrayList<ItemStack> harvestList = new ArrayList<ItemStack>();
		this.addHarvestContents(harvestList);
		ItemStack itemstack;
		for (Iterator<ItemStack> stack = harvestList.iterator(); stack.hasNext();
				ItemHelper.dropItem(this.worldObj, this.xCoord, this.yCoord, this.zCoord, itemstack)
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

	public int getRotatedSide(int side) {
		switch (rotation) {
		case 0:
			return side;
		case 1:
			return side == 2 ? 5 : side == 5 ? 2 : side;
		case 2:
			return side == 2 ? 3 : side == 3 ? 2 : side;
		case 3:
			return side == 2 ? 4 : side == 4 ? 2 : side;
		}
		return side;
	}

	public Icon getBlockTexture(int x, int y, int z, int metadata, int side) {
		return Block.blocksList[this.getBlockID()].getIcon(this.getRotatedSide(side), metadata);
	}
	
	public void setBlockBoundsBasedOnState(BlockBase blockBase) {
		blockBase.superSetBlockBoundsBasedOnState(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
	}
	
	public MovingObjectPosition collisionRayTrace(BlockBase blockbase, Vec3 startVec, Vec3 endVec) {
		return blockbase.superCollisionRayTrace(this.worldObj, this.xCoord, this.yCoord, this.zCoord, startVec, endVec);
	}

	public void addCollisionBoxesToList(BlockBase blockBase, AxisAlignedBB axisAlignedBB, List aList, Entity anEntity) {
		blockBase.superAddCollisionBoxesToList(this.worldObj, this.xCoord, this.yCoord, this.zCoord, axisAlignedBB, aList, anEntity);
	}

	public boolean isBlockSolidOnSide(BlockBase blockBase, ForgeDirection side) {
		return blockBase.superIsBlockSolidOnSide(this.worldObj, this.xCoord, this.yCoord, this.zCoord, side);
	}
	
	/**
	 * This can be overriden and used to retrieve the step sound based on TileEntity information
	 * 
	 * @return a Step Sound
	 */
	public StepSound getStepSound() {
		return null;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		this.tickSchedule = nbttagcompound.getLong(NBTLib.TILE_TICK_SCHEDULE);
		this.rotation = nbttagcompound.getByte(NBTLib.TILE_ROTATION);
		int status = nbttagcompound.getByte(NBTLib.TILE_ACTIVE);
		this.active = status > 0;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setLong(NBTLib.TILE_TICK_SCHEDULE, this.tickSchedule);
		nbttagcompound.setByte(NBTLib.TILE_ROTATION, (byte) this.rotation);
		nbttagcompound.setByte(NBTLib.TILE_ACTIVE, (byte) (this.active ? 1 : 0));
	}

	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) {
		this.readFromNBT(pkt.customParam1);
		this.onInventoryChanged();
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		this.writeToNBT(nbttagcompound);
		Packet packet = new Packet132TileEntityData(xCoord, yCoord, zCoord, 0, nbttagcompound);
		return packet;
	}
}
