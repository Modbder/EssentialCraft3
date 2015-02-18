package ec3.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import DummyCore.Utils.DataStorage;
import DummyCore.Utils.DummyData;
import DummyCore.Utils.Notifier;
import DummyCore.Utils.UnformedItemStack;

public class MagicianTableRecipes {
	
	public static final Hashtable<List<UnformedItemStack>,MagicianTableRecipe> recipes = new Hashtable<List<UnformedItemStack>,MagicianTableRecipe>();
	public static final Hashtable<String,MagicianTableRecipe> recipesByIS = new Hashtable<String,MagicianTableRecipe>();
	public static final List<List<UnformedItemStack>> craftMatrixByID = new ArrayList<List<UnformedItemStack>>();
	
	public static List<MagicianTableRecipe> getRecipiesByComponent(ItemStack component)
	{
		List<MagicianTableRecipe> retLst = new ArrayList();
		for(List<UnformedItemStack> lst : craftMatrixByID)
		{
			for(UnformedItemStack stk : lst)
			{
				if(stk.itemStackMatches(component))
					retLst.add(recipes.get(lst));
			}
		}
		return retLst;
	}
	
	public static MagicianTableRecipe getRecipeByResult(ItemStack result)
	{
		ItemStack search = result.copy();
		search.stackSize = 0;
		String searchStr = search.toString();
		search = null;
		return recipesByIS.get(searchStr);
	}
	
	public static List<UnformedItemStack> getUnformedStkByItemStacks(ItemStack[] pair)
	{
		boolean allNull = true;
		for(int i = 0; i < pair.length; ++i)
		{
			if(pair[i] != null)
				allNull = false;
		}
		if(allNull)return new ArrayList();
		ForLST:for(List<UnformedItemStack> lst : craftMatrixByID)
		{
			ForSize:for(int i = 0; i < lst.size(); ++i)
			{
				UnformedItemStack stack = lst.get(i);
				{
					if((stack == null || stack.possibleStacks.isEmpty()) && pair[i] == null)
					{
						continue ForSize;
					}
					if(!stack.itemStackMatches(pair[i]))
						continue ForLST;
				}
			}
			return lst;
		}
		return new ArrayList();
	}

	
	public static MagicianTableRecipe getRecipeByCP(ItemStack[] craftingPair)
	{
		List<UnformedItemStack> lst = getUnformedStkByItemStacks(craftingPair);
		return recipes.get(lst);
	}
	
	public static boolean addRecipe(MagicianTableRecipe rec)
	{
		try
		{
			UnformedItemStack[] req = new UnformedItemStack[rec.requiredItems.length];
			for(int i = 0; i < req.length;++i)
			{
				if(rec.requiredItems[i] != null)
					req[i] = rec.requiredItems[i].copy();
				else
					req[i] = null;
			}
			recipes.put(Arrays.asList(req), rec);
			ItemStack result = rec.result.copy();
			result.stackSize = 0;
			String searchStr = result.toString();
			result = null;
			recipesByIS.put(searchStr, rec);
			craftMatrixByID.add(Arrays.asList(req));
			req = null;
			return true;
		}catch(Exception e)
		{
			Side side = FMLCommonHandler.instance().getEffectiveSide();
			Notifier.notifyCustomMod("EssentialCraftAPI","Unable to add recipe "+rec+" on side "+side);
			return false;
		}
	}
	
	public static boolean addRecipeIS(ItemStack[] craftingPair, ItemStack result, int mruRequired)
	{
		try
		{
			UnformedItemStack[] unformedStacks = new UnformedItemStack[craftingPair.length];
			for(int i = 0; i < craftingPair.length; ++i)
			{
				unformedStacks[i] = new UnformedItemStack(craftingPair[i]);
			}
			MagicianTableRecipe addedRecipe = new MagicianTableRecipe(unformedStacks, result, mruRequired);
			return addRecipe(addedRecipe);
		}catch(Exception e)
		{
			Side side = FMLCommonHandler.instance().getEffectiveSide();
			Notifier.notifyCustomMod("EssentialCraftAPI","Unable to add ore recipe "+Arrays.toString(craftingPair)+" with the result "+result+" on side "+side);
			return false;
		}
	}
	
	public static boolean addRecipeIS(UnformedItemStack[] craftingPair, ItemStack result, int mruRequired)
	{
		try
		{
			MagicianTableRecipe addedRecipe = new MagicianTableRecipe(craftingPair, result, mruRequired);
			return addRecipe(addedRecipe);
		}catch(Exception e)
		{
			Side side = FMLCommonHandler.instance().getEffectiveSide();
			Notifier.notifyCustomMod("EssentialCraftAPI","Unable to add ore recipe "+Arrays.toString(craftingPair)+" with the result "+result+" on side "+side);
			return false;
		}
	}
	
	public static boolean addRecipeOreDict(String[] craftingPair, ItemStack result, int mruRequired)
	{
		try
		{
			UnformedItemStack[] unformedStack = new UnformedItemStack[craftingPair.length];
			for(int i = 0; i < unformedStack.length; ++i)
			{
				unformedStack[i] = new UnformedItemStack(craftingPair[i]);
			}
			MagicianTableRecipe addedRecipe = new MagicianTableRecipe(unformedStack, result, mruRequired);
			return addRecipe(addedRecipe);
		}catch(Exception e)
		{
			Side side = FMLCommonHandler.instance().getEffectiveSide();
			Notifier.notifyCustomMod("EssentialCraftAPI","Unable to add ore dict recipe "+Arrays.toString(craftingPair)+" with the result "+result+" on side "+side);
			return false;
		}
	}

}
