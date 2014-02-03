package slimevoidlib.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import slimevoidlib.items.ItemBlockBase;
import slimevoidlib.sounds.SlimevoidStepSound;
import slimevoidlib.tileentity.TileEntityBase;
import slimevoidlib.util.helpers.BlockHelper;
import cpw.mods.fml.common.registry.GameRegistry;

public abstract class BlockBase extends BlockContainer {

    protected Class[] tileEntityMap;

    protected BlockBase(int blockID, Material material, int maxTiles) {
        super(blockID, material);
        this.tileEntityMap = new Class[maxTiles];
        this.setCreativeTab(this.getCreativeTab());
        this.setStepSound(new SlimevoidStepSound("blockbase", 1.0F, 1.0F));
        this.setHardness(1.0F);
    }

    public abstract CreativeTabs getCreativeTab();

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    public boolean isCube() {
        return false;
    }

    @Override
    public int damageDropped(int metadata) {
        return metadata;
    }

    public void harvestBlock(World world, EntityPlayer entityplayer, int x, int y, int z, int metadata) {
        super.harvestBlock(world,
                           entityplayer,
                           x,
                           y,
                           z,
                           metadata);
    }

    public boolean removeBlockByPlayer(World world, EntityPlayer player, int x, int y, int z) {
        int metadata = world.getBlockMetadata(x,
                                              y,
                                              z);
        TileEntityBase tileentitybase = (TileEntityBase) BlockHelper.getTileEntity(world,
                                                                                   x,
                                                                                   y,
                                                                                   z,
                                                                                   this.getTileMapData(metadata));
        if (tileentitybase != null) {
            return tileentitybase.removeBlockByPlayer(player,
                                                      this);
        } else {
            return super.removeBlockByPlayer(world,
                                             player,
                                             x,
                                             y,
                                             z);
        }
    }

    public boolean superRemoveBlockByPlayer(World world, EntityPlayer player, int x, int y, int z) {
        return super.removeBlockByPlayer(world,
                                         player,
                                         x,
                                         y,
                                         z);
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
        int metadata = world.getBlockMetadata(x,
                                              y,
                                              z);
        TileEntityBase tileentitybase = (TileEntityBase) BlockHelper.getTileEntity(world,
                                                                                   x,
                                                                                   y,
                                                                                   z,
                                                                                   this.getTileMapData(metadata));
        if (tileentitybase != null) {
            return tileentitybase.getPickBlock(target,
                                               this);
        } else {
            return super.getPickBlock(target,
                                      world,
                                      x,
                                      y,
                                      z);
        }
    }

    public final ItemStack superGetPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
        return super.getPickBlock(target,
                                  world,
                                  x,
                                  y,
                                  z);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, int x, int y, int z) {
        int metadata = iblockaccess.getBlockMetadata(x,
                                                     y,
                                                     z);
        TileEntityBase tileentitybase = (TileEntityBase) BlockHelper.getTileEntity(iblockaccess,
                                                                                   x,
                                                                                   y,
                                                                                   z,
                                                                                   this.getTileMapData(metadata));
        if (tileentitybase != null) {
            tileentitybase.setBlockBoundsBasedOnState(this);
        } else {
            super.setBlockBoundsBasedOnState(iblockaccess,
                                             x,
                                             y,
                                             z);
        }
    }

    public void setBlockBoundsForItemRender(int metadata) {
        TileEntityBase tileentitybase = (TileEntityBase) this.createTileEntity(null,
                                                                               metadata);
        if (tileentitybase != null) {
            tileentitybase.setBlockBoundsForItemRender(this);
        }
    }

    @Override
    public void setBlockBoundsForItemRender() {
        this.setBlockBounds(0.0F,
                            0.0F,
                            0.0F,
                            1.0F,
                            1.0F,
                            1.0F);
    }

    @Override
    public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 startVec, Vec3 endVec) {
        int metadata = world.getBlockMetadata(x,
                                              y,
                                              z);
        TileEntityBase tileentitybase = (TileEntityBase) BlockHelper.getTileEntity(world,
                                                                                   x,
                                                                                   y,
                                                                                   z,
                                                                                   this.getTileMapData(metadata));
        if (tileentitybase != null) {
            return tileentitybase.collisionRayTrace(this,
                                                    startVec,
                                                    endVec);
        } else {
            return super.collisionRayTrace(world,
                                           x,
                                           y,
                                           z,
                                           startVec,
                                           endVec);
        }
    }

    @Override
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB axisAlignedBB, List aList, Entity anEntity) {
        int metadata = world.getBlockMetadata(x,
                                              y,
                                              z);
        TileEntityBase tileentitybase = (TileEntityBase) BlockHelper.getTileEntity(world,
                                                                                   x,
                                                                                   y,
                                                                                   z,
                                                                                   this.getTileMapData(metadata));
        if (tileentitybase != null) {
            tileentitybase.addCollisionBoxesToList(this,
                                                   axisAlignedBB,
                                                   aList,
                                                   anEntity);
        } else {
            super.addCollisionBoxesToList(world,
                                          x,
                                          y,
                                          z,
                                          axisAlignedBB,
                                          aList,
                                          anEntity);
        }
    }

    public final void superSetBlockBoundsBasedOnState(World worldObj, int x, int y, int z) {
        this.setBlockBounds(0.0F,
                            0.0F,
                            0.0F,
                            1.0F,
                            1.0F,
                            1.0F);
    }

    public final MovingObjectPosition superCollisionRayTrace(World world, int x, int y, int z, Vec3 startVec, Vec3 endVec) {
        return super.collisionRayTrace(world,
                                       x,
                                       y,
                                       z,
                                       startVec,
                                       endVec);
    }

    public final void superAddCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB axisAlignedBB, List aList, Entity anEntity) {
        super.addCollisionBoxesToList(world,
                                      x,
                                      y,
                                      z,
                                      axisAlignedBB,
                                      aList,
                                      anEntity);
    }

    @Override
    public boolean isBlockSolidOnSide(World world, int x, int y, int z, ForgeDirection side) {
        int metadata = world.getBlockMetadata(x,
                                              y,
                                              z);
        TileEntityBase tileentitybase = (TileEntityBase) BlockHelper.getTileEntity(world,
                                                                                   x,
                                                                                   y,
                                                                                   z,
                                                                                   this.getTileMapData(metadata));
        if (tileentitybase != null) {
            return tileentitybase.isBlockSolidOnSide(this,
                                                     side);
        } else {
            return super.isBlockSolidOnSide(world,
                                            x,
                                            y,
                                            z,
                                            side);
        }
    }

    public boolean superIsBlockSolidOnSide(World world, int x, int y, int z, ForgeDirection side) {
        return super.isBlockSolidOnSide(world,
                                        x,
                                        y,
                                        z,
                                        side);
    }

    @Override
    public float getExplosionResistance(Entity entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ) {
        int metadata = world.getBlockMetadata(x,
                                              y,
                                              z);
        TileEntityBase tileentitybase = (TileEntityBase) BlockHelper.getTileEntity(world,
                                                                                   x,
                                                                                   y,
                                                                                   z,
                                                                                   this.getTileMapData(metadata));
        if (tileentitybase != null) {
            return tileentitybase.getExplosionResistance(entity,
                                                         explosionX,
                                                         explosionY,
                                                         explosionZ,
                                                         this);
        } else {
            return super.getExplosionResistance(entity,
                                                world,
                                                x,
                                                y,
                                                z,
                                                explosionX,
                                                explosionY,
                                                explosionZ);
        }
    }

    public final float superGetExplosionResistance(Entity par1Entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ) {
        return super.getExplosionResistance(par1Entity,
                                            world,
                                            x,
                                            y,
                                            z,
                                            explosionX,
                                            explosionY,
                                            explosionZ);
    }

    @Override
    public float getBlockHardness(World world, int x, int y, int z) {
        int metadata = world.getBlockMetadata(x,
                                              y,
                                              z);
        TileEntityBase tileentitybase = (TileEntityBase) BlockHelper.getTileEntity(world,
                                                                                   x,
                                                                                   y,
                                                                                   z,
                                                                                   this.getTileMapData(metadata));
        if (tileentitybase != null) {
            return tileentitybase.getBlockHardness(this);
        } else {
            return super.getBlockHardness(world,
                                          x,
                                          y,
                                          z);
        }
    }

    public float superBlockHardness(World world, int x, int y, int z) {
        return super.getBlockHardness(world,
                                      x,
                                      y,
                                      z);
    }

    @Override
    public float getPlayerRelativeBlockHardness(EntityPlayer entityplayer, World world, int x, int y, int z) {
        int metadata = world.getBlockMetadata(x,
                                              y,
                                              z);
        TileEntityBase tileentitybase = (TileEntityBase) BlockHelper.getTileEntity(world,
                                                                                   x,
                                                                                   y,
                                                                                   z,
                                                                                   this.getTileMapData(metadata));
        if (tileentitybase != null) {
            return tileentitybase.getPlayerRelativeBlockHardness(entityplayer,
                                                                 this);
        } else {
            return super.getPlayerRelativeBlockHardness(entityplayer,
                                                        world,
                                                        x,
                                                        y,
                                                        z);
        }
    }

    @Override
    public int colorMultiplier(IBlockAccess iblockaccess, int x, int y, int z) {
        int metadata = iblockaccess.getBlockMetadata(x,
                                                     y,
                                                     z);
        TileEntityBase tileentitybase = (TileEntityBase) BlockHelper.getTileEntity(iblockaccess,
                                                                                   x,
                                                                                   y,
                                                                                   z,
                                                                                   this.getTileMapData(metadata));
        if (tileentitybase != null) {
            return tileentitybase.colorMultiplier(this);
        } else {
            return super.colorMultiplier(iblockaccess,
                                         x,
                                         y,
                                         z);
        }
    }

    public int superColorMultiplier(World world, int x, int y, int z) {
        return super.colorMultiplier(world,
                                     x,
                                     y,
                                     z);
    }

    @Override
    public ArrayList getBlockDropped(World world, int x, int y, int z, int metadata, int fortune) {
        ArrayList<ItemStack> harvestList = new ArrayList<ItemStack>();
        harvestList.add(new ItemStack(this.blockID, 1, metadata));
        return harvestList;
    }

    @Override
    public int quantityDroppedWithBonus(int i, Random random) {
        return 0;
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int blockID) {
        int metadata = world.getBlockMetadata(x,
                                              y,
                                              z);
        TileEntityBase tileentitybase = (TileEntityBase) BlockHelper.getTileEntity(world,
                                                                                   x,
                                                                                   y,
                                                                                   z,
                                                                                   this.getTileMapData(metadata));
        if (tileentitybase != null) {
            tileentitybase.onBlockNeighborChange(blockID);
        } else {
            world.setBlock(x,
                           y,
                           z,
                           0);
        }
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityplayer, ItemStack itemstack) {
        int metadata = world.getBlockMetadata(x,
                                              y,
                                              z);
        TileEntityBase tileentitybase = (TileEntityBase) BlockHelper.getTileEntity(world,
                                                                                   x,
                                                                                   y,
                                                                                   z,
                                                                                   this.getTileMapData(metadata));
        if (tileentitybase != null) {
            tileentitybase.onBlockPlacedBy(itemstack,
                                           entityplayer);
        }
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, int side, int metadata) {
        TileEntityBase tileentity = (TileEntityBase) BlockHelper.getTileEntity(world,
                                                                               x,
                                                                               y,
                                                                               z,
                                                                               this.getTileMapData(metadata));
        if (tileentity != null) {
            tileentity.breakBlock(side,
                                  metadata);
        }
        super.breakBlock(world,
                         x,
                         y,
                         z,
                         side,
                         metadata);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int side, float xHit, float yHit, float zHit) {
        int metadata = world.getBlockMetadata(x,
                                              y,
                                              z);
        TileEntityBase tileentity = (TileEntityBase) BlockHelper.getTileEntity(world,
                                                                               x,
                                                                               y,
                                                                               z,
                                                                               this.getTileMapData(metadata));
        if (tileentity != null) {
            return tileentity.onBlockActivated(entityplayer);
        } else {
            return false;
        }
    }

    @Override
    public Icon getBlockTexture(IBlockAccess iblockaccess, int x, int y, int z, int side) {
        int metadata = iblockaccess.getBlockMetadata(x,
                                                     y,
                                                     z);
        TileEntityBase tileentity = (TileEntityBase) BlockHelper.getTileEntity(iblockaccess,
                                                                               x,
                                                                               y,
                                                                               z,
                                                                               this.getTileMapData(metadata));
        if (tileentity != null) {
            return tileentity.getBlockTexture(x,
                                              y,
                                              z,
                                              metadata,
                                              side);
        } else {
            return this.getIcon(side,
                                metadata);
        }
    }

    @Override
    public int getLightValue(IBlockAccess iblockaccess, int x, int y, int z) {
        int metadata = iblockaccess.getBlockMetadata(x,
                                                     y,
                                                     z);
        TileEntityBase tileentity = (TileEntityBase) BlockHelper.getTileEntity(iblockaccess,
                                                                               x,
                                                                               y,
                                                                               z,
                                                                               this.getTileMapData(metadata));
        if (tileentity != null) {
            return tileentity.getLightValue();
        } else {
            return super.getLightValue(iblockaccess,
                                       x,
                                       y,
                                       z);
        }
    }

    public boolean superBlockDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer effectRenderer) {
        return super.addBlockDestroyEffects(world,
                                            x,
                                            y,
                                            z,
                                            meta,
                                            effectRenderer);
    }

    @Override
    public boolean addBlockDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer effectRenderer) {
        int metadata = world.getBlockMetadata(x,
                                              y,
                                              z);
        TileEntityBase tileentity = (TileEntityBase) BlockHelper.getTileEntity(world,
                                                                               x,
                                                                               y,
                                                                               z,
                                                                               this.getTileMapData(metadata));
        if (tileentity != null) {
            return tileentity.addBlockDestroyEffects(this,
                                                     meta,
                                                     effectRenderer);
        } else {
            return super.addBlockDestroyEffects(world,
                                                x,
                                                y,
                                                z,
                                                meta,
                                                effectRenderer);
        }
    }

    public boolean superBlockHitEffects(World world, MovingObjectPosition target, EffectRenderer effectRenderer) {
        return super.addBlockHitEffects(world,
                                        target,
                                        effectRenderer);
    }

    @Override
    public boolean addBlockHitEffects(World world, MovingObjectPosition target, EffectRenderer effectRenderer) {
        int x = target.blockX, y = target.blockY, z = target.blockZ;
        int metadata = world.getBlockMetadata(x,
                                              y,
                                              z);
        TileEntityBase tileentity = (TileEntityBase) BlockHelper.getTileEntity(world,
                                                                               x,
                                                                               y,
                                                                               z,
                                                                               this.getTileMapData(metadata));
        if (tileentity != null) {
            return tileentity.addBlockHitEffects(this,
                                                 target,
                                                 effectRenderer);
        } else {
            return super.addBlockHitEffects(world,
                                            target,
                                            effectRenderer);
        }
    }

    public Class getTileMapData(int metadata) {
        if (metadata < this.tileEntityMap.length) {
            return this.tileEntityMap[metadata];
        } else {
            return null;
        }
    }

    public void addMapping(int metadata, Class<? extends TileEntity> tileEntityClass, String unlocalizedName) {
        this.tileEntityMap[metadata] = tileEntityClass;
        GameRegistry.registerTileEntity(tileEntityClass,
                                        unlocalizedName);
        this.setItemName(metadata,
                         unlocalizedName);
    }

    public void setItemName(int metadata, String name) {
        Item item = Item.itemsList[this.blockID];
        if (item != null) {
            ((ItemBlockBase) item).setMetaName(metadata,
                                               (new StringBuilder()).append("tile.").append(name).toString());
        }
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        try {
            return (TileEntity) this.getTileMapData(metadata).newInstance();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return null;
    }

}
