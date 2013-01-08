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
package slimevoid.lib.core;

import net.minecraft.item.Item;

/**
 * Used to remove item instances from the game - in order to be replaced
 * 
 * @author Eurymachus
 *
 */
public class ItemRemover {

	/**
	 * Remove a vanilla Item instance
	 * 
	 * @param oldItem The item to be removed
	 * 
	 * @return if the item was removed or not
	 */
	public static boolean removeVanillaItem(Item oldItem) {
		// if the the item in itemsList with the shiftedIndex of the oldItem is
		// initialized
		if (Item.itemsList[oldItem.shiftedIndex] != null) {
			// Set the block in the blocksList to null
			Item.itemsList[oldItem.shiftedIndex] = null;
			// Output a success message
			EurysCore.console("EurysCore", "Item ID [" + oldItem.shiftedIndex + "] successfully removed.");
			return true;
		} else {
			// Output a failure message
			EurysCore.console("EurysCore", "Item ID not removed! Either the ID did not exist or was incorrect!");
			return false;
		}
	}
}
