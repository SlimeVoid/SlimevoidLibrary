package net.slimevoid.library.util.helpers;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

public class BlockHelper {

    public static void notifyBlock(World world, int x, int y, int z, Block source) {
        Block block = world.getBlock(x,
                                     y,
                                     z);
        if (block != null) {
            block.onNeighborBlockChange(world,
                                        x,
                                        y,
                                        z,
                                        source);
        }
    }

    public static void updateIndirectNeighbors(World world, int x, int y, int z, Block block) {
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
                                    x + inDirX,
                                    y + inDirY,
                                    z + inDirZ,
                                    block);
                    }
                }

            }

        }

    }

    public static void markBlockDirty(World world, int x, int y, int z) {
        if (world.blockExists(x,
                              y,
                              z)) {
            world.getChunkFromBlockCoords(x,
                                          z).setChunkModified();
        }
    }

    public static Object getTileEntity(IBlockAccess world, int x, int y, int z, Class tileEntityClass) {
        if (tileEntityClass == null) {
            return null;
        }
        TileEntity tileentity = SlimevoidHelper.getBlockTileEntity(world,
                                                                   x,
                                                                   y,
                                                                   z);
        if (!tileEntityClass.isInstance(tileentity)) {
            return null;
        } else {
            return tileentity;
        }
    }

    public static TileEntity getTileEntityAtBase(Entity entity) {
        int x = MathHelper.floor_double(entity.posX);
        int y = MathHelper.floor_double(entity.posY - 0.20000000298023224D
                                        - (double) entity.yOffset);
        int z = MathHelper.floor_double(entity.posZ);
        return SlimevoidHelper.getBlockTileEntity(entity.worldObj,
                                                  x,
                                                  y,
                                                  z);
    }

    public static void playBlockPlaceNoise(World world, int x, int y, int z, Block block) {
        world.playSoundEffect((float) x + 0.5F,
                              (float) y + 0.5F,
                              (float) z + 0.5F,
                              "step.stone",
                              (block.stepSound.getPitch() + 1.0F) / 2.0F,
                              block.stepSound.getVolume() * 0.8F);
    }
}
