package net.slimevoid.library.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import cpw.mods.fml.client.registry.RenderingRegistry;

public abstract class BlockTransientLight extends Block {

    public BlockTransientLight() {
        super(Material.air);
    }

    public boolean isAirBlock(World world, int x, int y, int z) {
        return true;
    }

    @Override
    public int quantityDropped(int meta, int fortune, Random random) {
        return 0;
    }

    @Override
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB axisalignedbb, List arraylist, Entity entity) {
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
        return null;
    }

    @Override
    public boolean isCollidable() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        if (!world.isRemote) {
            world.scheduleBlockUpdate(x,
                                      y,
                                      z,
                                      this,
                                      tickRate(world));
        }
    }

    @Override
    public void onBlockPreDestroy(World par1World, int par2, int par3, int par4, int par5) {

    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random random) {
        if (!world.isRemote) {
            if (!this.handleLightingConditions(world,
                                               x,
                                               y,
                                               z,
                                               random)) {
                world.setBlockToAir(x,
                                    y,
                                    z);
            }
            world.scheduleBlockUpdate(x,
                                      y,
                                      z,
                                      this,
                                      tickRate(world));
        }
    }

    /**
     * This method should return true if you handled lighting updates otherwise
     * the block will remove itself automatically on this tick
     * 
     */
    protected abstract boolean handleLightingConditions(World world, int x, int y, int z, Random random);

    @Override
    public int getRenderType() {
        return RenderingRegistry.getNextAvailableRenderId();
    }

    public static void setBlock(Block block, int x, int y, int z, World world) {
        if (!world.isRemote) {
            if ((world.getBlock(x,
                                y,
                                z) == Blocks.air || world.getBlock(x,
                                                                   y,
                                                                   z) == block)) {
                world.setBlock(x,
                               y,
                               z,
                               block,
                               0,
                               2);
            }
        }
    }

}
