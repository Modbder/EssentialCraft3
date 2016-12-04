package ec3.integration.minetweaker;

import net.minecraft.item.ItemStack;
import ec3.api.MagicianTableRecipe;
import ec3.api.MithrilineFurnaceRecipe;
import ec3.api.MithrilineFurnaceRecipes;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.data.DataFloat;
import minetweaker.api.data.DataInt;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.ec3.MithrilineFurnace")
/**
 * 
 * @author Artem226
 * 
 * @Description Adding/removing MithrilineFurnace recipes
 * 
 */
public class MithrilineFurnace {

	public MithrilineFurnace() {
		// TODO Auto-generated constructor stub
	}
	
	@ZenMethod
	 public static void add(IItemStack output, IIngredient ingredient, int stackSizeOfIngr, int enderpower)
	 {
		MineTweakerAPI.apply(new Add(new MithrilineFurnaceRecipe(SomeMTUtils.toUnformedIS(ingredient), SomeMTUtils.toItem(output), enderpower, stackSizeOfIngr)));
	 }
	
	@ZenMethod
	 public static void remove(IItemStack recipeOutput)
	 {
		 if(recipeOutput != null)
		 {
			 MineTweakerAPI.apply(new Remove(SomeMTUtils.toItem(recipeOutput)));
		 }
		 else
			 MineTweakerAPI.getLogger().logError("Target item can't be null!");
	 }

	private static class Add implements IUndoableAction {
		MithrilineFurnaceRecipe recipe;

		public Add(MithrilineFurnaceRecipe rec)
		{
			recipe = rec;
		}
		
		@Override
		public void apply() {
			MithrilineFurnaceRecipes.addRecipe(recipe);
			
		}

		@Override
		public boolean canUndo() {
			
			return recipe !=null;
		}

		@Override
		public void undo() {
			MithrilineFurnaceRecipes.allRegisteredRecipes.remove(recipe);
			
		}

		@Override
		public String describe() {
			
			return "Adding Mithriline Furnace recipe for " + recipe.getRecipeOutput().getDisplayName();
		}

		@Override
		public String describeUndo() {
		
			return "UnAdding Mithriline Furnace recipe for " + recipe.getRecipeOutput().getDisplayName();
		}

		@Override
		public Object getOverrideKey() {
			
			return null;
		}
	}
	
	private static class Remove implements IUndoableAction {
		MithrilineFurnaceRecipe recipe = null;
        ItemStack ToRemove;
        
		public Remove(ItemStack rem)
		{
			ToRemove = rem;
		}
		
		@Override
		public void apply() {
			MithrilineFurnaceRecipe rec = MithrilineFurnaceRecipes.findRecipeByResult(ToRemove);
			
			if(rec != null)
			{
				MithrilineFurnaceRecipes.allRegisteredRecipes.remove(rec);
				recipe = rec;
			}
			
		}

		@Override
		public boolean canUndo() {
			
			return recipe != null;
		}

		@Override
		public void undo() {
			MithrilineFurnaceRecipes.addRecipe(recipe);
			
		}

		@Override
		public String describe() {
			
			return "Removing Mithriline Furnace recipe for " + recipe.getRecipeOutput().getDisplayName();
		}

		@Override
		public String describeUndo() {
		
			return "UnRemoving Mithriline Furnace recipe for " + recipe.getRecipeOutput().getDisplayName();
		}

		@Override
		public Object getOverrideKey() {
			
			return null;
		}
	}
}
