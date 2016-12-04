package ec3.integration.minetweaker;

import java.util.ArrayList;
import java.util.List;

import DummyCore.Utils.UnformedItemStack;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.data.DataFloat;
import minetweaker.api.data.DataInt;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import ec3.api.RadiatingChamberRecipe;
import ec3.api.RadiatingChamberRecipes;
import scala.actors.threadpool.Arrays;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * 
 * @author Artem226
 * 
 * @Description Adding/removing RadiatingChamber recipes
 * 
 */
@ZenClass("mods.ec3.RadiatingChamber")
public class RadiatingChamber {

	public RadiatingChamber() {
		// TODO Auto-generated constructor stub
	}
	
	@ZenMethod
	 public static void add(IItemStack output, IItemStack[] ingredients, int mru, float upperBalance, float lowerBalance, float modifier, int size)
	 {
		 
		 
		 if(ingredients!=null && ingredients.length > 0 )
		 {
		 MineTweakerAPI.apply(new Add(new RadiatingChamberRecipe(SomeMTUtils.toItem(ingredients), SomeMTUtils.toItem(output), mru, new float[]{upperBalance,lowerBalance}, modifier, size)));
		 }
		 else
		 {
			 MineTweakerAPI.getLogger().logError("Ingredients can't be null!");
		 }
		 
	 }
	 
	@ZenMethod
	 public static void add(IItemStack output, IItemStack[] ingredients, int mru, float upperBalance, float lowerBalance, int size)
	 {
		 
		 
		 if(ingredients!=null && ingredients.length > 0 )
		 {
		 MineTweakerAPI.apply(new Add(new RadiatingChamberRecipe(SomeMTUtils.toItem(ingredients), SomeMTUtils.toItem(output), mru, new float[]{upperBalance,lowerBalance}, 1, size)));
		 }
		 else
		 {
			 MineTweakerAPI.getLogger().logError("Ingredients can't be null!");
		 }
		 
	 }
	
	@ZenMethod
	 public static void add(IItemStack output, IItemStack[] ingredients, int mru, float upperBalance, float lowerBalance)
	 {
		 
		 
		 if(ingredients!=null && ingredients.length > 0 )
		 {
		 MineTweakerAPI.apply(new Add(new RadiatingChamberRecipe(SomeMTUtils.toItem(ingredients), SomeMTUtils.toItem(output), mru, new float[]{upperBalance,lowerBalance}, 1, 1)));
		 }
		 else
		 {
			 MineTweakerAPI.getLogger().logError("Ingredients can't be null!");
		 }
		 
	 }
	
	@ZenMethod
	 public static void add(IItemStack output, IItemStack[] ingredients, int mru, float modifier, int size)
	 {
		 
		 
		 if(ingredients!=null && ingredients.length > 0 )
		 {
		 MineTweakerAPI.apply(new Add(new RadiatingChamberRecipe(SomeMTUtils.toItem(ingredients), SomeMTUtils.toItem(output), mru, new float[]{Float.MAX_VALUE,Float.MIN_VALUE}, modifier, size)));
		 }
		 else
		 {
			 MineTweakerAPI.getLogger().logError("Ingredients can't be null!");
		 }
		 
	 }
	
	@ZenMethod
	 public static void add(IItemStack output, IItemStack[] ingredients, int mru, float modifier)
	 {
		 
		 
		 if(ingredients!=null && ingredients.length > 0 )
		 {
		 MineTweakerAPI.apply(new Add(new RadiatingChamberRecipe(SomeMTUtils.toItem(ingredients), SomeMTUtils.toItem(output), mru, new float[]{Float.MAX_VALUE,Float.MIN_VALUE}, modifier, 1)));
		 }
		 else
		 {
			 MineTweakerAPI.getLogger().logError("Ingredients can't be null!");
		 }
		 
	 }
	
	@ZenMethod
	 public static void add(IItemStack output, IItemStack[] ingredients, int mru)
	 {
		 
		 
		 if(ingredients!=null && ingredients.length > 0 )
		 {
		 MineTweakerAPI.apply(new Add(new RadiatingChamberRecipe(SomeMTUtils.toItem(ingredients), SomeMTUtils.toItem(output), mru, new float[]{Float.MAX_VALUE,Float.MIN_VALUE}, 1, 1)));
		 }
		 else
		 {
			 MineTweakerAPI.getLogger().logError("Ingredients can't be null!");
		 }
		 
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
		RadiatingChamberRecipe recipe;

        public Add(RadiatingChamberRecipe add){
            recipe = add;
        }

        @Override
        public void apply(){

        	RadiatingChamberRecipes.addRecipe(recipe);
        }

        @Override
        public boolean canUndo(){
            return true;
        }

        @Override
        public void undo(){
        	
        	ItemStack[] req = new ItemStack[recipe.recipeItems.length];
			for(int i = 0; i < req.length;++i)
			{
				if(recipe.recipeItems[i] != null)
					req[i] = recipe.recipeItems[i].copy();
				else
					req[i] = null;
			}
			for(int i = 0; i < req.length; ++i)
			{
				if(req[i] != null)
					req[i].stackSize = 0;
			}
			if(RadiatingChamberRecipes.recipes.containsKey(Arrays.toString(req)))
        	   RadiatingChamberRecipes.recipes.remove(Arrays.toString(req));
			
        	ItemStack search = recipe.result.copy();
    		search.stackSize = 0;
    		String searchStr = search.toString();
    		search = null;
    		
    		RadiatingChamberRecipes.recipesByIS.remove(searchStr);
    		RadiatingChamberRecipes.craftMatrixByID.remove(Arrays.toString(req));
        	req = null;
        }

        @Override
        public String describe(){
            return "Adding Radiating Chamber recipe for " + recipe.getRecipeOutput().getDisplayName();
        }

        @Override
        public String describeUndo(){
            return "UnAdding Radiating Chamber recipe for " + recipe.getRecipeOutput().getDisplayName();
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }

    }
	
	 private static class Remove implements IUndoableAction {
		 RadiatingChamberRecipe recipe = null;
	        ItemStack ToRemove;

	        public Remove(ItemStack rem){
	            ToRemove = rem;
	        }

	        @Override
	        public void apply(){
	        	RadiatingChamberRecipe rec = RadiatingChamberRecipes.getRecipeByResult(ToRemove);
	        	
	        	if(rec != null)
	        	{
	        		ItemStack[] req = new ItemStack[rec.recipeItems.length];
	    			for(int i = 0; i < req.length;++i)
	    			{
	    				if(rec.recipeItems[i] != null)
	    					req[i] = rec.recipeItems[i].copy();
	    				else
	    					req[i] = null;
	    			}
	    			for(int i = 0; i < req.length; ++i)
	    			{
	    				if(req[i] != null)
	    					req[i].stackSize = 0;
	    			}
	    			if(RadiatingChamberRecipes.recipes.containsKey(Arrays.toString(req)))
	            	   RadiatingChamberRecipes.recipes.remove(Arrays.toString(req));
	    			
	            	ItemStack search = recipe.result.copy();
	        		search.stackSize = 0;
	        		String searchStr = search.toString();
	        		search = null;
	        		
	        		RadiatingChamberRecipes.recipesByIS.remove(searchStr);
	        		RadiatingChamberRecipes.craftMatrixByID.remove(Arrays.toString(req));
	            	req = null;
	        	
	        	recipe = rec;
	        	}
	        	else
	        		MineTweakerAPI.getLogger().logError("Radiating Chamber Recipe from "+ToRemove.getDisplayName()+" not found!");
	        }

	        @Override
	        public boolean canUndo(){
	            return recipe != null;
	        }

	        @Override
	        public void undo(){
	        	RadiatingChamberRecipes.addRecipe(recipe);
	        }

	        @Override
	        public String describe(){
	            return "Removing Radiating Chamber recipe for " + recipe.getRecipeOutput().getDisplayName();
	        }

	        @Override
	        public String describeUndo(){
	            return "UnRemoving Radiating Chamber recipe for " + recipe.getRecipeOutput().getDisplayName();
	        }

	        @Override
	        public Object getOverrideKey() {
	            return null;
	        }

	    }
}
