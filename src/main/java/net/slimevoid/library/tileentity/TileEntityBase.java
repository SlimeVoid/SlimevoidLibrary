package net.slimevoid.library.tileentity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.Block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.slimevoid.library.blocks.BlockBase;
import net.slimevoid.library.core.lib.NBTLib;
import net.slimevoid.library.util.helpers.BlockHelper;
import net.slimevoid.library.util.helpers.ItemHelper;

public abstract class TileEntityBase extends TileEntity implements IUpdatePlayerListBox, IInventory {

    protected long    tickSchedule;
    protected int     rotation;
    protected boolean active;

    public TileEntityBase() {
        this.tickSchedule = -1L;
        this.rotation = 0;
        this.active = false;
    }

    public void onNeighborChange(BlockPos pos) {

    }

    public int onBlockPlaced(int side, float hitX, float hitY, float hitZ, int damage) {
        return damage;
    }

    public void onBlockPlacedBy(ItemStack itemstack, EntityLivingBase entity) {
        this.setRotation((int) Math.floor((double) ((entity.rotationYaw * 4F) / 360F) + 0.5D) & 3);
    }

    public int getRotation() {
        return this.rotation;
    }

    public void setRotation(int newRotation) {
        this.rotation = newRotation;
    }

    public void onTileTick() {

    }

    public boolean onBlockActivated(IBlockState blockState, EntityPlayer entityplayer, EnumFacing side, float xHit, float yHit, float zHit) {
        return false;
    }

    public abstract int getExtendedBlockID();

    public int getExtendedMetadata() {
        return 0;
    }

    public boolean removeBlockByPlayer(EntityPlayer player, BlockBase blockBase, boolean willHarvest) {
        return blockBase.superRemoveBlockByPlayer(this.getWorld(),
        										  pos,
                                                  player,
                                                  willHarvest);
    }

    public ItemStack getPickBlock(MovingObjectPosition target, BlockBase blockBase) {
        return blockBase.superGetPickBlock(target,
                                           this.getWorld(),
                                           this.pos);
    }

    public float getBlockHardness(BlockBase blockBase) {
        return blockBase.superBlockHardness(this.getWorld(),
                                            this.pos);
    }

    public float getPlayerRelativeBlockHardness(EntityPlayer entityplayer, IBlockState blockState) {
        return ForgeHooks.blockStrength(blockState,
                                        entityplayer,
                                        this.getWorld(),
                                        this.pos);
    }

    public float getExplosionResistance(BlockBase blockBase, Entity exploder, Explosion explosion) {
        return blockBase.superGetExplosionResistance(this.getWorld(),
                                                     this.pos,
                                                     exploder,
                                                     explosion);
    }

    public int colorMultiplier(BlockBase blockBase, int renderPass) {
        return blockBase.superColorMultiplier(this.getWorld(),
                                              this.pos,
                                              renderPass);
    }

    protected abstract void addHarvestContents(ArrayList<ItemStack> harvestList);

    public void scheduleTick(int time) {
        long worldTime = this.getWorld().getWorldTime() + (long) time;
        if (this.tickSchedule > 0L && this.tickSchedule < worldTime) {
            return;
        } else {
            this.tickSchedule = worldTime;
            this.markBlockDirty();
            return;
        }
    }

    public int getLightValue() {
        return 0;
    }

    public void updateBlock() {
        this.getWorld().markBlockForUpdate(this.pos);
        this.getWorld().notifyLightSet(this.pos);
    }

    public void updateBlockAndNeighbours() {
        BlockHelper.updateIndirectNeighbors(this.getWorld(),
                                            this.pos,
                                            this.getBlockType());
        this.updateBlock();
    }

    public void markBlockDirty() {
        //BlockHelper.markBlockDirty(this.getWorld(),
        //                           this.pos);
    }

    public void breakBlock(IBlockState blockState) {
        ArrayList<ItemStack> harvestList = new ArrayList<ItemStack>();
        this.addHarvestContents(harvestList);
        for (ItemStack itemstack : harvestList) {
            ItemHelper.dropItem(this.getWorld(),
                                this.pos,
                                itemstack);
        }
    }

    @Override
    public void update() {
        if (this.getWorld().isRemote) return;
        if (this.tickSchedule < 0L) return;
        long worldTime = this.getWorld().getWorldTime();
        if (this.tickSchedule > worldTime + 1200L) this.tickSchedule = worldTime + 1200L;
        else if (this.tickSchedule <= worldTime) {
            this.tickSchedule = -1L;
            this.onTileTick();
            this.markBlockDirty();
        }
    }

    public EnumFacing getFacing() {
        switch (this.rotation) {
        case 0:
            return EnumFacing.NORTH;
        case 1:
            return EnumFacing.EAST;
        case 2:
            return EnumFacing.SOUTH;
        case 3:
            return EnumFacing.WEST;
        }
        return EnumFacing.NORTH;
    }

    //public IIcon getBlockTexture(int x, int y, int z, int metadata, int side) {
    //    return this.getBlockType().getIcon(this.getRotatedSide(side),
    //                                       metadata);
    //}

    public void setBlockBoundsBasedOnState(BlockBase blockBase) {
        blockBase.superSetBlockBoundsBasedOnState(this.getWorld(),
                                                  this.pos);
    }

    public void setBlockBoundsForItemRender(BlockBase blockBase) {
        blockBase.setBlockBoundsForItemRender();
    }

    public MovingObjectPosition collisionRayTrace(BlockBase blockbase, Vec3 startVec, Vec3 endVec) {
        return blockbase.superCollisionRayTrace(this.getWorld(),
                                                this.pos,
                                                startVec,
                                                endVec);
    }

    public void addCollisionBoxesToList(BlockBase blockBase, IBlockState blockState, AxisAlignedBB axisAlignedBB, List aList, Entity anEntity) {
        blockBase.superAddCollisionBoxesToList(this.getWorld(),
                							   this.pos,
        									   blockState,
                                               axisAlignedBB,
                                               aList,
                                               anEntity);
    }

    public boolean isSideSolid(BlockBase blockBase, EnumFacing side) {
        return blockBase.superSideSolid(this.getWorld(),
                                        this.pos,
                                        side);
    }

    public boolean addBlockDestroyEffects(BlockBase blockBase, EffectRenderer effectRenderer) {
        return blockBase.superDestroyEffects(this.getWorld(),
                this.pos,
                effectRenderer);
    }

    public boolean addBlockHitEffects(BlockBase blockBase, MovingObjectPosition target, EffectRenderer effectRenderer) {
        return blockBase.superHitEffects(this.getWorld(),
                target,
                effectRenderer);
    }

    public IBlockState getActualState(IBlockState state, BlockBase blockBase) {
        System.out.println(this.rotation);
        return state.withProperty(BlockBase.FACING, this.getFacing());
    }

    /**
     * This can be overriden and used to retrieve the step sound based on
     * TileEntity information
     * 
     * @return a Step Sound
     */
    public SoundType getStepSound() {
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
        nbttagcompound.setLong(NBTLib.TILE_TICK_SCHEDULE,
                this.tickSchedule);
        nbttagcompound.setByte(NBTLib.TILE_ROTATION,
                (byte) this.rotation);
        nbttagcompound.setByte(NBTLib.TILE_ACTIVE,
                (byte) (this.active ? 1 : 0));
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.getNbtCompound());
        this.onInventoryChanged();
        this.updateBlock();
    }

    public void onInventoryChanged() {
        this.markDirty();
        this.onInventoryHasChanged(this.worldObj,
                this.pos);
    }

    /**
     * If we need to send information to the client it should be done here
     */
    protected void onInventoryHasChanged(World world, BlockPos pos) {
        world.markBlockForUpdate(pos);
    }

    @Override
    public String getCommandSenderName() {
        return this.getInvName();
    }

    public abstract String getInvName();

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public void openInventory(EntityPlayer entityplayer) {
    }

    @Override
    public void closeInventory(EntityPlayer entityplayer) {
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.writeToNBT(nbttagcompound);
        Packet packet = new S35PacketUpdateTileEntity(pos, 0, nbttagcompound);
        return packet;
    }
}
