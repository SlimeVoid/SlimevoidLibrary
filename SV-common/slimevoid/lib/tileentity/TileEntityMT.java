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
package slimevoid.lib.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import slimevoid.lib.ITileEntityMT;
import slimevoid.lib.network.PacketTileEntityMT;
import slimevoid.lib.network.PacketUpdate;

/**
 * Abstract TileEntity class for generation of Multi-Textured blocks contains the data required
 * 
 * @author Eurymachus
 *
 */
public abstract class TileEntityMT extends TileEntity implements ITileEntityMT {

	/**
	 * The value of the items damage (Usually stored in metadata however due to limitation we store it in tileentity)
	 */
	private int textureValue;

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setInteger("textureValue", textureValue);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		textureValue = nbttagcompound.getInteger("textureValue");
	}

	/**
	 * Sets the texture value
	 *
	 * @param texture the texture value to set (usually itemdamage)
	 */
	@Override
	public void setTextureValue(int texture) {
		this.textureValue = texture;
	}

	/**
	 * Gets the texture value for the Multi-Textured block
	 * 
	 * @return the texture value
	 */
	@Override
	public int getTextureValue() {
		return this.textureValue;
	}

	@Override
	public Packet getDescriptionPacket() {
		return getUpdatePacket();
	}

	/**
	 * Overriden by child classes to return an instance of PacketUpdate
	 * 
	 * @return the associated PacketUpdate.getPacket()
	 */
	public abstract Packet getUpdatePacket();

	/**
	 * Default Packet Handling for the tileentity
	 */
	@Override
	public void handleUpdatePacket(World world, PacketUpdate packet) {
		this.setTextureValue(((PacketTileEntityMT) packet).getTextureValue());
		System.out.println("Texture: " + this.getTextureValue());
		this.onInventoryChanged();
		world.markBlockForUpdate(
				packet.xPosition,
				packet.yPosition,
				packet.zPosition);
	}
}
