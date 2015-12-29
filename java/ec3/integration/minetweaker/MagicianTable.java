package ec3.integration.minetweaker;

import java.util.ArrayList;
import java.util.List;

import DummyCore.Utils.UnformedItemStack;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.data.DataInt;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import ec3.api.MagicianTableRecipe;
import ec3.api.MagicianTableRecipes;
import scala.actors.threadpool.Arrays;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * 
 * @author Artem226
 * 
 * @Description Adding/removing MagicianTable recipes
 * 
 */
@ZenClass("mods.ec3.MagicianTable")
public class MagicianTable {

	public MagicianTable() {
		// TODO Auto-generated constructor stub
	}
	
	 @ZenMethod
	 public static void add(IItemStack output, IIngredient[] ingredients, int mru)
	 {
		 List<UnformedItemStack> items = new ArrayList<UnformedItemStack>();
		 
		 if(ingredients!=null && ingredients.length > 0)
		 {
		 for(IIngredient in : ingredients)
		 {
			 items.add(SomeMTUtils.toUnformedIS(in));
		 }
		 
		 UnformedItemStack[] stack = new UnformedItemStack[items.size()];
		 
		 for(int i = 0; i < items.size(); i++)
		 {
		     stack[i] = items.get(i);
		 }
		 
		  
		 
		 MineTweakerAPI.apply(new Add(new MagicianTableRecipe(stack, SomeMTUtils.toItem(output).copy(), mru)));
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
		MagicianTableRecipe recipe;

        public Add(MagicianTableRecipe add){
            recipe = add;
        }

        @Override
        public void apply(){

        	MagicianTableRecipes.addRecipe(recipe);
        }

        @Override
        public boolean canUndo(){
            return true;
        }

        @Override
        public void undo(){
        	MagicianTableRecipes.recipes.remove(Arrays.asList(recipe.requiredItems));
        	ItemStack search = recipe.result.copy();
    		search.stackSize = 0;
    		String searchStr = search.toString();
    		search = null;
        	MagicianTableRecipes.recipesByIS.remove(searchStr);
        	MagicianTableRecipes.craftMatrixByID.remove(Arrays.asList(recipe.requiredItems));
        }

        @Override
        public String describe(){
            return "Adding magician table recipe: "+recipe.getRecipeOutput().getDisplayName();
            
        }

        @Override
        public String describeUndo(){
            return "UnAdding magician table recipe: " + recipe.getRecipeOutput().getDisplayName();
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }

    }
	
	 private static class Remove implements IUndoableAction {
		 MagicianTableRecipe recipe = null;
	        ItemStack ToRemove;

	        public Remove(ItemStack rem){
	            ToRemove = rem;
	        }

	        @Override
	        public void apply(){
	        	MagicianTableRecipe rec = MagicianTableRecipes.getRecipeByResult(ToRemove);
	        	
	        	if(rec != null)
	        	{
	        	MagicianTableRecipes.recipes.remove(Arrays.asList(rec.requiredItems));
	        	ItemStack search = rec.result.copy();
	    		search.stackSize = 0;
	    		String searchStr = search.toString();
	    		search = null;
	        	MagicianTableRecipes.recipesByIS.remove(searchStr);
	        	MagicianTableRecipes.craftMatrixByID.remove(Arrays.asList(rec.requiredItems));
	        	
	        	recipe = rec;
	        	}
	        	else
	        		MineTweakerAPI.getLogger().logError("Magician table recipe from "+ToRemove.getDisplayName()+" not found!");
	        }

	        @Override
	        public boolean canUndo(){
	            return recipe != null;
	        }

	        @Override
	        public void undo(){
	        	MagicianTableRecipes.addRecipe(recipe);
	        }

	        @Override
	        public String describe(){
	            return "Removing magician table recipe for " + recipe.getRecipeOutput().getDisplayName();
	        }

	        @Override
	        public String describeUndo(){
	            return "UnRemoving magician table recipe for " + recipe.getRecipeOutput().getDisplayName();
	        }

	        @Override
	        public Object getOverrideKey() {
	            return null;
	        }

	    }
}
