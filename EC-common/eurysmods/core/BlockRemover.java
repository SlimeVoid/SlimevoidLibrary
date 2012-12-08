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
package eurysmods.core;

import net.minecraft.src.Block;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.ModLoader;

public class BlockRemover {

	public static boolean removeVanillaBlock(Block oldBlock) {
		// if the the block in blocksList with the blockID of the oldBlock is
		// initialized
		if (Block.blocksList[oldBlock.blockID] != null) {
			// Checks if the block is also has an ItemBlock associated with it
			if (ItemBlock.itemsList[oldBlock.blockID] != null) {
				ItemBlock.itemsList[oldBlock.blockID] = null;
			}
			// Set the block in the blocksList to null
			Block.blocksList[oldBlock.blockID] = null;
			// Output a success message
			ModLoader
					.getLogger()
						.info(
								"Block ID [" + oldBlock.blockID + "] successfully removed.");
			return true;
		} else {
			// Output a failure message
			ModLoader
					.getLogger()
						.severe(
								"Block ID not removed! Either the ID did not exist or was incorrect!");
			return false;
		}
	}
}
