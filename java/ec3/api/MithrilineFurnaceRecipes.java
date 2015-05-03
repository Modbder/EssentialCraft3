package ec3.api;

import java.util.ArrayList;
import java.util.List;

import DummyCore.Utils.UnformedItemStack;
import net.minecraft.item.ItemStack;

public class MithrilineFurnaceRecipes {
	
	public static final List<MithrilineFurnaceRecipe> allRegisteredRecipes = new ArrayList<MithrilineFurnaceRecipe>();
	
	public static void addRecipe(ItemStack component, ItemStack result, float cost, int req)
	{
		UnformedItemStack is = new UnformedItemStack(component);
		addRecipe(new MithrilineFurnaceRecipe(is,result,cost,req));
	}
	
	public static void addRecipe(String oreDictName, ItemStack result, float cost,int req)
	{
		UnformedItemStack is = new UnformedItemStack(oreDictName);
		if(!is.possibleStacks.isEmpty())
			addRecipe(new MithrilineFurnaceRecipe(is,result,cost,req));
	}
	
	public static void addRecipe(MithrilineFurnaceRecipe rec)
	{
		if(allRegisteredRecipes.contains(rec))
			return;
		else 
			allRegisteredRecipes.add(rec);
	}
	
	public static MithrilineFurnaceRecipe findRecipeByComponent(ItemStack is)
	{
		for(int i = 0; i < allRegisteredRecipes.size(); ++i)
		{
			MithrilineFurnaceRecipe recipe = allRegisteredRecipes.get(i);
			if(recipe != null && recipe.smelted.itemStackMatches(is))
				return recipe;
		}
		
		return null;
	}
	
	public static MithrilineFurnaceRecipe findRecipeByResult(ItemStack is)
	{
		for(int i = 0; i < allRegisteredRecipes.size(); ++i)
		{
			MithrilineFurnaceRecipe recipe = allRegisteredRecipes.get(i);
			if(recipe != null && recipe.result.isItemEqual(is))
				return recipe;
		}
		
		return null;
	}

}
