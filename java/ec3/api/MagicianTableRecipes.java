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

public class MagicianTableRecipes {
	
	public static final Hashtable<String,MagicianTableRecipe> recipes = new Hashtable<String,MagicianTableRecipe>();
	public static final Hashtable<String,MagicianTableRecipe> recipesByIS = new Hashtable<String,MagicianTableRecipe>();
	public static final List<String> craftMatrixByID = new ArrayList<String>();
	
	public static MagicianTableRecipe getRecipeByResult(ItemStack result)
	{
		ItemStack req = result.copy();
		req.stackSize = 0;
		String searchStr = req.toString();
		req = null;
		return recipesByIS.get(searchStr);
	}
	
	public static MagicianTableRecipe getRecipeByCP(ItemStack[] craftingPair)
	{
		ItemStack[] req = new ItemStack[craftingPair.length];
		for(int i = 0; i < req.length;++i)
		{
			if(craftingPair[i] != null)
				req[i] = craftingPair[i].copy();
			else
				req[i] = null;
		}
		for(int i = 0; i < req.length; ++i)
		{
			if(req[i] != null)
				req[i].stackSize = 0;
		}
		String searchStr = Arrays.toString(req);
		req = null;
		return recipes.get(searchStr);
	}
	
	public static boolean addRecipe(MagicianTableRecipe rec)
	{
		try
		{
			ItemStack[] req = new ItemStack[rec.requiredItems.length];
			for(int i = 0; i < req.length;++i)
			{
				if(rec.requiredItems[i] != null)
					req[i] = rec.requiredItems[i].copy();
				else
					req[i] = null;
			}
			for(int i = 0; i < req.length; ++i)
			{
				if(req[i] != null)
					req[i].stackSize = 0;
			}
			recipes.put(Arrays.toString(req), rec);
			ItemStack result = rec.result.copy();
			result.stackSize = 0;
			String searchStr = result.toString();
			result = null;
			recipesByIS.put(searchStr, rec);
			craftMatrixByID.add(Arrays.toString(req));
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
			MagicianTableRecipe addedRecipe = new MagicianTableRecipe(craftingPair, result, mruRequired);
			return addRecipe(addedRecipe);
		}catch(Exception e)
		{
			Side side = FMLCommonHandler.instance().getEffectiveSide();
			Notifier.notifyCustomMod("EssentialCraftAPI","Unable to add ore recipe "+Arrays.toString(craftingPair)+" with the result "+result+" on side "+side);
			return false;
		}
	}
	
	public static boolean addRecipeOreDict(String[] craftingPair, String result, int mruRequired)
	{
		try
		{
			List<ItemStack>[] stkLists = new List[6];
			for(int i = 0; i < 5; ++i)
			{
				List<ItemStack> stkHoldList = OreDictionary.getOres(craftingPair[i]);
				stkLists[i].addAll(stkHoldList);
			}
			List<ItemStack> stkHoldList = OreDictionary.getOres(result);
			stkLists[5].addAll(stkHoldList);
			for(int i = 0; i < stkLists[0].size(); ++i)
			{
				for(int i1 = 0; i1 < stkLists[1].size(); ++i1)
				{
					for(int i2 = 0; i2 < stkLists[2].size(); ++i2)
					{
						for(int i3 = 0; i3 < stkLists[3].size(); ++i3)
						{
							for(int i4 = 0; i4 < stkLists[4].size(); ++i4)
							{
								for(int i5 = 0; i5 < stkLists[5].size(); ++i5)
								{
									ItemStack[] c = new ItemStack[5];
									c[0] = stkLists[0].get(i);
									c[1] = stkLists[1].get(i1);
									c[2] = stkLists[2].get(i2);
									c[3] = stkLists[3].get(i3);
									c[4] = stkLists[4].get(i4);
									addRecipeIS(c,stkLists[5].get(i),mruRequired);
								}
							}
						}
					}
				}
			}
			return true;
		}catch(Exception e)
		{
			Side side = FMLCommonHandler.instance().getEffectiveSide();
			Notifier.notifyCustomMod("EssentialCraftAPI","Unable to add ore dict recipe "+Arrays.toString(craftingPair)+" with the result "+result+" on side "+side);
			return false;
		}
	}

}
