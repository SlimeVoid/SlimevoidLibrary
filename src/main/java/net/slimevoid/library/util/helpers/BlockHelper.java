package net.slimevoid.library.util.helpers;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

public class BlockHelper {

    public static void notifyBlock(World world, BlockPos pos, Block source) {
        IBlockState blockState = world.getBlockState(pos);
        Block block = blockState.getBlock();
        if (block != null) {
            block.onNeighborBlockChange(world,
                                        pos,
                                        blockState,
                                        source);
        }
    }

    public static void updateIndirectNeighbors(World world, BlockPos pos, Block neighbor) {
        if (world.isRemote
            || FMLCommonHandler.instance().getSide() == Side.CLIENT) return;
        for (int inDirX = -3; inDirX <= 3; inDirX++) {
            for (int inDirY = -3; inDirY <= 3; inDirY++) {
                for (int inDirZ = -3; inDirZ <= 3; inDirZ++) {
                    int updateDirection = inDirX >= 0 ? inDirX : -inDirX;
                    updateDirection += inDirY >= 0 ? inDirY : -inDirY;
                    updateDirection += inDirZ >= 0 ? inDirZ : -inDirZ;
                    if (updateDirection <= 3) {
                        notifyBlock(world,
                        			pos,
                                    neighbor);
                    }
                }

            }

        }

    }

    public static Object getTileEntity(IBlockAccess world, BlockPos pos, Class tileEntityClass) {
        if (tileEntityClass == null) {
            return null;
        }
        TileEntity tileentity = SlimevoidHelper.getBlockTileEntity(world,
                                                                   pos);
        if (!tileEntityClass.isInstance(tileentity)) {
            return null;
        } else {
            return tileentity;
        }
    }

    public static TileEntity getTileEntityAtBase(Entity entity) {
        int x = MathHelper.floor_double(entity.posX);
        int y = MathHelper.floor_double(entity.posY - 0.20000000298023224D
                                        - (double) entity.getYOffset());
        int z = MathHelper.floor_double(entity.posZ);
        return SlimevoidHelper.getBlockTileEntity(entity.worldObj,
                                                  new BlockPos(x, y, z));
    }

    public static void playBlockPlaceNoise(World world, int x, int y, int z, Block block) {
        world.playSoundEffect((float) x + 0.5F,
                              (float) y + 0.5F,
                              (float) z + 0.5F,
                              block.stepSound.getPlaceSound(),
                              (block.stepSound.getFrequency() + 1.0F) / 2.0F,
                              block.stepSound.getVolume() * 0.8F);
    }
}
