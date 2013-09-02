package slimevoidlib.blocks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import slimevoidlib.items.ItemBlockBase;
import slimevoidlib.tileentity.TileEntityBase;
import slimevoidlib.util.helpers.BlockHelper;
import slimevoidlib.util.helpers.ItemHelper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
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

public abstract class BlockBase extends BlockContainer {
	
	protected Class[] tileEntityMap;

	protected BlockBase(int blockID, Material material, int maxTiles) {
		super(blockID, material);
		this.tileEntityMap = new Class[maxTiles];
		this.setCreativeTab(this.getCreativeTab());
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

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, int x, int y, int z) {
		int metadata = iblockaccess.getBlockMetadata(x, y, z);
		TileEntityBase tileentitybase = (TileEntityBase) BlockHelper.getTileEntity(iblockaccess, x, y, z, this.getTileMapData(metadata));
		if (tileentitybase == null) {
			super.setBlockBoundsBasedOnState(iblockaccess, x, y, z);
			return;
		} else {
			tileentitybase.setBlockBoundsBasedOnState(this);
			return;
		}
	}
	
	@Override
	public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 startVec, Vec3 endVec) {
		int metadata = world.getBlockMetadata(x, y, z);
		TileEntityBase tileentitybase = (TileEntityBase) BlockHelper.getTileEntity(world, x, y, z, this.getTileMapData(metadata));
		if (tileentitybase == null) {
			return super.collisionRayTrace(world, x, y, z, startVec, endVec);
		} else {
			return tileentitybase.collisionRayTrace(this, startVec, endVec);
		}
	}
	
	@Override
	public void addCollisionBoxesToList(World world, int x, int y,
			int z, AxisAlignedBB axisAlignedBB, List aList,
			Entity anEntity) {
		int metadata = world.getBlockMetadata(x, y, z);
		TileEntityBase tileentitybase = (TileEntityBase) BlockHelper.getTileEntity(world, x, y, z, this.getTileMapData(metadata));
		if (tileentitybase == null) {
			super.addCollisionBoxesToList(world, x, y, z, axisAlignedBB, aList, anEntity);
			return;
		} else {
			tileentitybase.addCollisionBoxesToList(this, axisAlignedBB, aList, anEntity);
			return;
		}
	}

	public final void superSetBlockBoundsBasedOnState(World worldObj, int x, int y, int z) {
		super.setBlockBoundsBasedOnState(worldObj, x, y, z);
	}

	public final MovingObjectPosition superCollisionRayTrace(World world, int x, int y, int z, Vec3 startVec, Vec3 endVec) {
		return super.collisionRayTrace(world, x, y, z, startVec, endVec);
	}
	
	public final void superAddCollisionBoxesToList(World world, int x, int y,
			int z, AxisAlignedBB axisAlignedBB, List aList,
			Entity anEntity) {
		super.addCollisionBoxesToList(world, x, y, z, axisAlignedBB, aList, anEntity);
	}

	@Override
	public boolean isBlockSolidOnSide(World world, int x, int y, int z, ForgeDirection side) {
		int metadata = world.getBlockMetadata(x, y, z);
		TileEntityBase tileentitybase = (TileEntityBase) BlockHelper.getTileEntity(world, x, y, z, this.getTileMapData(metadata));
		if (tileentitybase == null) {
			return super.isBlockSolidOnSide(world, x, y, z, side);
		} else {
			return tileentitybase.isBlockSolidOnSide(this, side);
		}
	}

	public boolean superIsBlockSolidOnSide(World world, int x, int y, int z, ForgeDirection side) {
		return super.isBlockSolidOnSide(world, x, y, z, side);
	}
	
	@Override
    public float getBlockHardness(World world, int x, int y, int z) {
		int metadata = world.getBlockMetadata(x, y, z);
		TileEntityBase tileentitybase = (TileEntityBase) BlockHelper.getTileEntity(world, x, y, z, this.getTileMapData(metadata));
		if (tileentitybase == null) {
			return super.getBlockHardness(world, x, y, z);
		} else {
			return tileentitybase.getBlockHardness(world, x, y, z);
		}
	}
	
	@Override
	public ArrayList getBlockDropped(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> harvestList = new ArrayList<ItemStack>();
		TileEntityBase tileentitybase = (TileEntityBase) BlockHelper.getTileEntity(world, x, y, z, this.getTileMapData(metadata));
		if (tileentitybase == null) {
			return harvestList;
		} else {
			tileentitybase.addHarvestContents(harvestList);
			return harvestList;
		}
	}
	
	@Override
	public int quantityDroppedWithBonus(int i, Random random) {
		return 0;
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int x,
			int y, int z, int damage) {
	}

	@Override
	public boolean removeBlockByPlayer(World world, EntityPlayer entityplayer,
			int x, int y, int z) {
		if (world.isRemote) {
			return true;
		}
		int blockId = world.getBlockId(x, y, z);
		int metadata = world.getBlockMetadata(x, y, z);
		Block block = Block.blocksList[blockId];
		if (block == null) {
			return false;
		}
		if (block.canHarvestBlock(entityplayer, metadata)
				&& !entityplayer.capabilities.isCreativeMode) {
			ArrayList blockDropped = getBlockDropped(world, x, y, z, metadata,
					EnchantmentHelper.getFortuneModifier(entityplayer));
			ItemStack itemstack;
			for (Iterator stack = blockDropped.iterator(); stack.hasNext(); ItemHelper
					.dropItem(world, x, y, z, itemstack))
				itemstack = (ItemStack) stack.next();

		}
		world.setBlock(x, y, z, 0);
		return true;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int blockID) {
		int metadata = world.getBlockMetadata(x, y, z);
		TileEntityBase tileentitybase = (TileEntityBase) BlockHelper.getTileEntity(world, x, y, z, this.getTileMapData(metadata));
		if (tileentitybase == null) {
			world.setBlock(x, y, z, 0);
			return;
		} else {
			tileentitybase.onBlockNeighborChange(blockID);
			return;
		}
	}

	public void onBlockPlaced(World world, int x, int y, int z, int side, EntityPlayer entityplayer, ItemStack itemstack) {
		int metadata = world.getBlockMetadata(x, y, z);
		TileEntityBase tileentitybase = (TileEntityBase) BlockHelper.getTileEntity(world, x, y, z, this.getTileMapData(metadata));
		if (tileentitybase == null) {
			return;
		} else {
			tileentitybase.onBlockPlaced(itemstack, side, entityplayer);
			return;
		}
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int side, int metadata) {
		TileEntityBase tileentity = (TileEntityBase) BlockHelper.getTileEntity(world, x, y, z, this.getTileMapData(metadata));
		if (tileentity == null) {
			return;
		} else {
			tileentity.onBlockRemoval();
			super.breakBlock(world, x, y, z, side, metadata);
			return;
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int side, float xHit, float yHit, float zHit) {
		int metadata = world.getBlockMetadata(x, y, z);
		TileEntityBase tileentity = (TileEntityBase) BlockHelper.getTileEntity(world, x, y, z, this.getTileMapData(metadata));
		if (tileentity == null) {
			return false;
		} else {
			return tileentity.onBlockActivated(entityplayer);
		}
	}
	
	@Override
	public Icon getBlockTexture(IBlockAccess iblockaccess, int x, int y, int z, int side) {
		int metadata = iblockaccess.getBlockMetadata(x, y, z);
		TileEntityBase tileentity = (TileEntityBase) BlockHelper.getTileEntity(iblockaccess, x, y, z, this.getTileMapData(metadata));
		if (tileentity != null) {
			return tileentity.getBlockTexture(x, y, z, metadata, side);
		} else {
			return this.getIcon(side, metadata);
		}
	}

	@Override
	public int getLightValue(IBlockAccess iblockaccess, int x, int y, int z) {
		int metadata = iblockaccess.getBlockMetadata(x, y, z);
		TileEntityBase tileentity = (TileEntityBase) BlockHelper.getTileEntity(iblockaccess, x, y, z, this.getTileMapData(metadata));
		if (tileentity == null) {
			return super.getLightValue(iblockaccess, x, y, z);
		} else {
			return tileentity.getLightValue();
		}
	}
	
	private Class getTileMapData(int metadata) {
		if (metadata < this.tileEntityMap.length) {
			return this.tileEntityMap[metadata];
		} else {
			return null;
		}
	}

	@Override
	public abstract int getRenderType();
	
	public void addTileEntityMapping(int metadata, Class tileEntityClass) {
		this.tileEntityMap[metadata] = tileEntityClass;
	}

	public void setItemName(int metadata, String name) {
		Item item = Item.itemsList[this.blockID];
		if (item != null) {
			((ItemBlockBase) item).setMetaName(metadata, (new StringBuilder()).append("tile.").append(name).toString());
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
