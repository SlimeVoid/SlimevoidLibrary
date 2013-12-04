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
package slimevoidlib.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import slimevoidlib.core.SlimevoidCore;
import slimevoidlib.core.lib.CoreLib;

/**
 * Used to remove blocks instances from the game - in order to be replaced
 * 
 * @author Eurymachus
 * 
 */
public class BlockRemover {

	/**
	 * Remove a vanilla Block instance and it's associated ItemBlock instance
	 * 
	 * @param oldBlock
	 *            The block to be removed
	 * @param shouldRemoveItem
	 *            Whether the associated ItemBlock should attempt to be removed
	 * 
	 * @return if the block was removed or not
	 */
	public static boolean removeVanillaBlock(Block oldBlock, boolean shouldRemoveItem) {
		// if the the block in blocksList with the blockID of the oldBlock is
		// initialized
		if (Block.blocksList[oldBlock.blockID] != null) {
			// Checks if the block is also has an ItemBlock associated with it
			if (shouldRemoveItem) {
				if (Item.itemsList[oldBlock.blockID] != null) {
					Item.itemsList[oldBlock.blockID] = null;
				} else {
					SlimevoidCore.console(	CoreLib.MOD_ID,
											"ItemBlock ID [" + oldBlock.blockID
													+ "] could not be removed.");
				}
			}
			// Set the block in the blocksList to null
			Block.blocksList[oldBlock.blockID] = null;
			// Output a success message
			SlimevoidCore.console(	CoreLib.MOD_ID,
									"Block ID [" + oldBlock.blockID
											+ "] successfully removed.");
			return true;
		} else {
			// Output a failure message
			SlimevoidCore.console(	CoreLib.MOD_ID,
									"Block ID not removed! Either the ID did not exist or was incorrect!");
			return false;
		}
	}
}
