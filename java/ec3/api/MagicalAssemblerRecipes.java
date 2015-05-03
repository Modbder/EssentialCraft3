package ec3.api;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class MagicalAssemblerRecipes {
	
	public static List<ShapedAssemblerRecipe> recipes = new ArrayList<ShapedAssemblerRecipe>();
	
	public static List<ShapedAssemblerRecipe> findRecipes(ItemStack result)
	{
		List<ShapedAssemblerRecipe> retLst = new ArrayList<ShapedAssemblerRecipe>();
		for(ShapedAssemblerRecipe rec : recipes)
		{
			if(rec.getRecipeOutput().isItemEqual(result))
				retLst.add(rec);
		}
		return retLst;
	}
	
	@SuppressWarnings("unchecked")
	public static List<ShapedAssemblerRecipe> findUsageRecipes(ItemStack result)
	{
		List<ShapedAssemblerRecipe> retLst = new ArrayList<ShapedAssemblerRecipe>();
		List<ShapedAssemblerRecipe> genLst = new ArrayList<ShapedAssemblerRecipe>();
		for(ShapedAssemblerRecipe rec : recipes)
		{
			for(Object obj : rec.getInput())
			{
	    		ItemStack needToFind = null;
	    		 if(obj instanceof ItemStack)
					 needToFind = (ItemStack) obj;
				 if(obj instanceof Item)
					 needToFind = new ItemStack((Item)obj);
				 if(obj instanceof Block)
					 needToFind = new ItemStack((Block)obj);
				 if(obj instanceof ItemStack[])
				 {
					 f:for(ItemStack is : (ItemStack[])obj)
					 {
						 if(is.isItemEqual(result))
							 genLst.add(rec);
						 break f;
					 }
				 }
				 if(obj instanceof String)
				 {
					 List<ItemStack> oreStacks = OreDictionary.getOres((String) obj);
					 f:for(ItemStack is : oreStacks)
					 {
						 if(is.isItemEqual(result))
							 genLst.add(rec);
						 break f;
					 }
				 }
				 if(obj instanceof List)
				 {
					 List<ItemStack> oreStacks = (List<ItemStack>) obj;
					 f:for(ItemStack is : oreStacks)
					 {
						 if(is.isItemEqual(result))
							 genLst.add(rec);
						 break f;
					 }
				 }
				 
				 if(needToFind != null)
				 {
					 if(needToFind.isItemEqual(result))
						 genLst.add(rec);
				 }
			}
		}
		for(ShapedAssemblerRecipe rec : genLst)
		{
			if(!retLst.contains(rec))
				retLst.add(rec);
		}
		return retLst;
	}
	
	

}
