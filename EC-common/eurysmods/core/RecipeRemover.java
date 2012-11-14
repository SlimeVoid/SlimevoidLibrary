package eurysmods.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.minecraft.src.Block;
import net.minecraft.src.CraftingManager;
import net.minecraft.src.IRecipe;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;

public class RecipeRemover {

	// set of recipes to remove
	static Set<Integer> itemSet = new HashSet<Integer>();

	public static void registerItemRecipeToRemove(Object object) {
		if (object instanceof Block) {
			itemSet.add(((Block) object).blockID);
		}
		if (object instanceof Item) {
			itemSet.add(((Item) object).shiftedIndex);
		}
	}

	public static void removeCrafting() {

		// remove everything from the list
		List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
		Iterator<IRecipe> it = recipes.iterator();
		List<IRecipe> matches = new ArrayList<IRecipe>(); // recipes to remove
		while (it.hasNext()) {
			IRecipe recipe = it.next();
			ItemStack output = recipe.getRecipeOutput();
			if (itemSet.contains(output.itemID)) {
				matches.add(recipe);
			}
		}
		for (IRecipe recipe : matches) {
			ModLoader.getLogger().info("Removing recipe for " + recipe);
			recipes.remove(recipe);
		}
	}
}
