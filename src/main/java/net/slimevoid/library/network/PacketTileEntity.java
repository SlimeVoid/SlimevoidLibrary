/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version. This program is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details. You should have received a copy of the GNU
 * Lesser General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>
 */
package net.slimevoid.library.network;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.slimevoid.library.util.helpers.SlimevoidHelper;

/**
 * Packet for sending TileEntity information
 * 
 * @author Eurymachus
 * 
 */
public abstract class PacketTileEntity extends PacketUpdate {

    public PacketTileEntity() {
        super(PacketIds.TILE);
    }

    /**
     * Get the tileentity instance for this packet
     * 
     * @param world
     *            The world in which the tileentity resides
     * 
     * @return The tileentity
     */
    public TileEntity getTileEntity(World world) {
        if (this.targetExists(world)) {
            return SlimevoidHelper.getBlockTileEntity(world,
                                                      new BlockPos(this.xPosition,
                                                      this.yPosition,
                                                      this.zPosition));
        }
        return null;
    }

    @Override
    public boolean targetExists(World world) {
    	IBlockState blockState = SlimevoidHelper.getBlockState(world,
                new BlockPos(this.xPosition,
                this.yPosition,
                this.zPosition));
        if (/*SlimevoidHelper.targetExists(world,
                                         this.xPosition,
                                         this.yPosition,
                                         this.zPosition)
            && */blockState.getBlock().hasTileEntity(blockState)) {
            return true;
        }
        return false;
    }
}
