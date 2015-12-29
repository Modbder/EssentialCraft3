package ec3.integration.minetweaker;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.data.DataInt;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import ec3.api.MagicianTableRecipe;
import ec3.api.MagicianTableRecipes;
import ec3.api.WindImbueRecipe;
import scala.actors.threadpool.Arrays;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.ec3.WindImbue")
public class WindImbue {

	public WindImbue() {
		// TODO Auto-generated constructor stub
	}
	
	 @ZenMethod
	 public static void add(IItemStack output, IItemStack input, int energy)
	 {
		MineTweakerAPI.apply(new Add(SomeMTUtils.toItem(input), SomeMTUtils.toItem(output), energy));
	 }
	  
	 @ZenMethod
	 public static void remove(IItemStack output)
	 {
		 MineTweakerAPI.apply(new Remove(SomeMTUtils.toItem(output)));
	 }
	 
	 

	private static class Add implements IUndoableAction {
		WindImbueRecipe recipe;
		ItemStack input;
		ItemStack output;
		int energy;

        public Add(ItemStack in, ItemStack out, int energy){
            input = in;
            output = out;
            this.energy = energy;
        }

        @Override
        public void apply(){

        	recipe = new WindImbueRecipe(input, output, energy);
        }

        @Override
        public boolean canUndo(){
            return recipe != null;
        }

        @Override
        public void undo(){
        	WindImbueRecipe.recipes.remove(recipe);
        }

        @Override
        public String describe(){
            return "Adding wind imbue recipe for " + output.getDisplayName();
        }

        @Override
        public String describeUndo(){
            return "UnAdding wind imbue recipe for " + output.getDisplayName();
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }

    }
	
	 private static class Remove implements IUndoableAction {
		 WindImbueRecipe recipe = null;
	        ItemStack ToRemove;

	        public Remove(ItemStack rem){
	            ToRemove = rem;
	        }

	        @Override
	        public void apply(){
	        	WindImbueRecipe rec = WindImbueRecipe.findRecipeByResult(ToRemove);
	        	
	        	if(rec != null)
	        	{
	        	WindImbueRecipe.recipes.remove(rec);
	        	recipe = rec;
	        	}
	        	else
	        		MineTweakerAPI.getLogger().logError("Wind Imbue recipe from "+ToRemove.getDisplayName()+" not found!");
	        }

	        @Override
	        public boolean canUndo(){
	            return recipe != null;
	        }

	        @Override
	        public void undo(){
	        	WindImbueRecipe.recipes.add(recipe);
	        }

	        @Override
	        public String describe(){
	            return "Removing wind imbue recipe for " + recipe.getRecipeOutput().getDisplayName();
	        }

	        @Override
	        public String describeUndo(){
	            return "UnRemoving wind imbue recipe for " + recipe.getRecipeOutput().getDisplayName();
	        }

	        @Override
	        public Object getOverrideKey() {
	            return null;
	        }

	    }
}
