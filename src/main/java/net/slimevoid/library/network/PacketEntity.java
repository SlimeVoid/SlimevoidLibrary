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

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

/**
 * Packet for sending Entity information
 * 
 * entityId The ID of the entity to send
 * 
 * @author Eurymachus
 * 
 */
public abstract class PacketEntity extends PacketUpdate {
    private int entityId;

    public PacketEntity() {
        super(PacketIds.ENTITY);
    }

    /**
     * Get the entityId for this packet
     * 
     * @return The entity ID
     */
    public int getEntityId() {
        return this.entityId;
    }

    /**
     * Set the entityId for this packet
     * 
     * @param entityId
     *            The entity ID
     */
    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    @Override
    public void writeData(ChannelHandlerContext ctx, ByteBuf data) {
        super.writeData(ctx,
                        data);
        data.writeInt(this.entityId);
    }

    @Override
    public void readData(ChannelHandlerContext ctx, ByteBuf data) {
        super.readData(ctx,
                       data);
        this.entityId = data.readInt();
    }

    @Override
    public boolean targetExists(World world) {
        // Get the loaded entities for the world
        @SuppressWarnings("unchecked")
        List<? extends Entity> entities = world.loadedEntityList;
        // For each entity within the world
        for (int i = 0; i < entities.size(); i++) {
            // Get the current entity
            Entity entity = entities.get(i);
            // Is entity id of current loaded entity equal to this entity Id
            if (entity != null && entity.getEntityId() == this.getEntityId()) {
                // Entity is loaded and exists
                return true;
            }
        }
        // Entity does not exist or is not loaded
        return false;
    }
}
