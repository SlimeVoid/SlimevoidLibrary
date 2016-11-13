package net.slimevoid.library.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public abstract class BlockTransientLight extends Block {

    public BlockTransientLight() {
        super(Material.air);
    }

    public boolean isAirBlock(World world, int x, int y, int z) {
        return true;
    }

    @Override
    public int quantityDropped(IBlockState blockState, int fortune, Random random) {
        return 0;
    }

    @Override
    public void addCollisionBoxesToList(World world, BlockPos pos, IBlockState blockState, AxisAlignedBB axisalignedbb, List arraylist, Entity entity) {
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBox(World world, BlockPos pos) {
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

    //@Override
    //public boolean renderAsNormalBlock() {
    //    return false;
    //}

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState blockState) {
        if (!world.isRemote) {
            world.scheduleUpdate(pos,
                    this,
                    this.tickRate(world));
        }
    }

    @Override
    public void /*onBlockPreDestroy*/onBlockDestroyedByPlayer(World world, BlockPos pos, IBlockState blockState) {

    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState blockState, Random random) {
        if (!world.isRemote) {
            if (!this.handleLightingConditions(world,
                    pos,
                    random)) {
                world.setBlockToAir(pos);
            }
            world.scheduleUpdate(pos,
                    this,
                    this.tickRate(world));
        }
    }

    /**
     * This method should return true if you handled lighting updates otherwise
     * the block will remove itself automatically on this tick
     */
    protected abstract boolean handleLightingConditions(World world, BlockPos pos, Random random);

    @Override
    public int getRenderType() {
        return 0;//RenderingRegistry.getNextAvailableRenderId();
    }

    public static void setBlock(World world, IBlockState blockState, BlockPos pos) {
        if (!world.isRemote) {
            if ((world.getBlockState(pos).getBlock() == Blocks.air || world.getBlockState(pos).getBlock() == blockState.getBlock())) {
                world.setBlockState(pos,
                        blockState,
                        0);
            }
        }
    }

}
