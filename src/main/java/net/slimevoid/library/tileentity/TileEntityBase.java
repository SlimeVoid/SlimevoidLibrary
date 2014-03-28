package net.slimevoid.library.tileentity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.Block.SoundType;
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
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.ForgeDirection;
import net.slimevoid.library.blocks.BlockBase;
import net.slimevoid.library.core.lib.NBTLib;
import net.slimevoid.library.util.helpers.BlockHelper;
import net.slimevoid.library.util.helpers.ItemHelper;

public abstract class TileEntityBase extends TileEntity implements IInventory {

    protected long    tickSchedule;
    protected int     rotation;
    protected boolean active;

    public TileEntityBase() {
        this.tickSchedule = -1L;
        this.rotation = 0;
        this.active = false;
    }

    public void onBlockNeighborChange(Block block) {

    }

    public int onBlockPlaced(int side, float hitX, float hitY, float hitZ, int damage) {
        return damage;
    }

    public void onBlockPlacedBy(ItemStack itemstack, EntityLivingBase entity) {
        this.rotation = (int) Math.floor((double) ((entity.rotationYaw * 4F) / 360F) + 0.5D) & 3;
    }

    public int getRotation() {
        return this.rotation;
    }

    public void setRotation(int newRotation) {
        this.rotation = newRotation;
    }

    public void onTileTick() {

    }

    public boolean onBlockActivated(EntityPlayer entityplayer) {
        return false;
    }

    public abstract int getExtendedBlockID();

    public int getExtendedMetadata() {
        return 0;
    }

    public boolean removeBlockByPlayer(EntityPlayer player, BlockBase blockBase) {
        return blockBase.superRemoveBlockByPlayer(this.getWorldObj(),
                                                  player,
                                                  this.xCoord,
                                                  this.yCoord,
                                                  this.zCoord);
    }

    public ItemStack getPickBlock(MovingObjectPosition target, BlockBase blockBase) {
        return blockBase.superGetPickBlock(target,
                                           this.getWorldObj(),
                                           this.xCoord,
                                           this.yCoord,
                                           this.zCoord);
    }

    public float getBlockHardness(BlockBase blockBase) {
        return blockBase.superBlockHardness(this.getWorldObj(),
                                            this.xCoord,
                                            this.yCoord,
                                            this.zCoord);
    }

    public float getPlayerRelativeBlockHardness(EntityPlayer entityplayer, BlockBase blockBase) {
        return ForgeHooks.blockStrength(blockBase,
                                        entityplayer,
                                        this.getWorldObj(),
                                        this.xCoord,
                                        this.yCoord,
                                        this.zCoord);
    }

    public float getExplosionResistance(Entity entity, double explosionX, double explosionY, double explosionZ, BlockBase blockBase) {
        return blockBase.superGetExplosionResistance(entity,
                                                     this.getWorldObj(),
                                                     this.xCoord,
                                                     this.yCoord,
                                                     this.zCoord,
                                                     explosionX,
                                                     explosionY,
                                                     explosionZ);
    }

    public int colorMultiplier(BlockBase blockBase) {
        return blockBase.superColorMultiplier(this.getWorldObj(),
                                              this.xCoord,
                                              this.yCoord,
                                              this.zCoord);
    }

    protected abstract void addHarvestContents(ArrayList<ItemStack> harvestList);

    public void scheduleTick(int time) {
        long worldTime = this.getWorldObj().getWorldTime() + (long) time;
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
        this.getWorldObj().markBlockForUpdate(this.xCoord,
                                              this.yCoord,
                                              this.zCoord);
        this.getWorldObj()./* updateAllLightTypes */func_147451_t(this.xCoord,
                                                                  this.yCoord,
                                                                  this.zCoord);
    }

    public void updateBlockAndNeighbours() {
        BlockHelper.updateIndirectNeighbors(this.getWorldObj(),
                                            this.xCoord,
                                            this.yCoord,
                                            this.zCoord,
                                            this.getBlockType());
        this.updateBlock();
    }

    public void markBlockDirty() {
        BlockHelper.markBlockDirty(this.getWorldObj(),
                                   this.xCoord,
                                   this.yCoord,
                                   this.zCoord);
    }

    public void breakBlock(Block block, int metadata) {
        ArrayList<ItemStack> harvestList = new ArrayList<ItemStack>();
        this.addHarvestContents(harvestList);
        for (ItemStack itemstack : harvestList) {
            ItemHelper.dropItem(this.getWorldObj(),
                                this.xCoord,
                                this.yCoord,
                                this.zCoord,
                                itemstack);
        }
    }

    @Override
    public void updateEntity() {
        if (this.getWorldObj().isRemote) return;
        if (this.tickSchedule < 0L) return;
        long worldTime = this.getWorldObj().getWorldTime();
        if (this.tickSchedule > worldTime + 1200L) this.tickSchedule = worldTime + 1200L;
        else if (this.tickSchedule <= worldTime) {
            this.tickSchedule = -1L;
            this.onTileTick();
            this.markBlockDirty();
        }
    }

    public int getRotatedSide(int side) {
        switch (rotation) {
        case 0:
            return side == 3 ? 2 : side == 2 ? 3 : side;
        case 1:
            return side == 3 ? 5 : side == 5 ? 3 : side;
        case 2:
            return side;
        case 3:
            return side == 3 ? 4 : side == 4 ? 3 : side;
        }
        return side;
    }

    public IIcon getBlockTexture(int x, int y, int z, int metadata, int side) {
        return this.getBlockType().getIcon(this.getRotatedSide(side),
                                           metadata);
    }

    public void setBlockBoundsBasedOnState(BlockBase blockBase) {
        blockBase.superSetBlockBoundsBasedOnState(this.getWorldObj(),
                                                  this.xCoord,
                                                  this.yCoord,
                                                  this.zCoord);
    }

    public void setBlockBoundsForItemRender(BlockBase blockBase) {
        blockBase.setBlockBoundsForItemRender();
    }

    public MovingObjectPosition collisionRayTrace(BlockBase blockbase, Vec3 startVec, Vec3 endVec) {
        return blockbase.superCollisionRayTrace(this.getWorldObj(),
                                                this.xCoord,
                                                this.yCoord,
                                                this.zCoord,
                                                startVec,
                                                endVec);
    }

    public void addCollisionBoxesToList(BlockBase blockBase, AxisAlignedBB axisAlignedBB, List aList, Entity anEntity) {
        blockBase.superAddCollisionBoxesToList(this.getWorldObj(),
                                               this.xCoord,
                                               this.yCoord,
                                               this.zCoord,
                                               axisAlignedBB,
                                               aList,
                                               anEntity);
    }

    public boolean isSideSolid(BlockBase blockBase, ForgeDirection side) {
        return blockBase.superSideSolid(this.getWorldObj(),
                                        this.xCoord,
                                        this.yCoord,
                                        this.zCoord,
                                        side);
    }

    public boolean addBlockDestroyEffects(BlockBase blockBase, int meta, EffectRenderer effectRenderer) {
        return blockBase.superDestroyEffects(this.getWorldObj(),
                                             this.xCoord,
                                             this.yCoord,
                                             this.zCoord,
                                             meta,
                                             effectRenderer);
    }

    public boolean addBlockHitEffects(BlockBase blockBase, MovingObjectPosition target, EffectRenderer effectRenderer) {
        return blockBase.superHitEffects(this.getWorldObj(),
                                         target,
                                         effectRenderer);
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
        this.readFromNBT(pkt.func_148857_g());
        this.onInventoryChanged();
        this.updateBlock();
    }

    public void onInventoryChanged() {
        this.markDirty();
        this.onInventoryHasChanged(this.worldObj,
                                   this.xCoord,
                                   this.yCoord,
                                   this.zCoord);
    }

    /**
     * If we need to send information to the client it should be done here
     */
    protected void onInventoryHasChanged(World world, int x, int y, int z) {
        world.markBlockForUpdate(x,
                                 y,
                                 z);
    }

    @Override
    public String getInventoryName() {
        return this.getInvName();
    }

    public abstract String getInvName();

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public void openInventory() {
    }

    @Override
    public void closeInventory() {
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.writeToNBT(nbttagcompound);
        Packet packet = new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, nbttagcompound);
        return packet;
    }
}
