package ec3.api;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class WindImbueRecipe implements IRecipe{
	
	public ItemStack result;
	public ItemStack transforming;
	public int enderEnergy;
	
	public static final List<WindImbueRecipe> recipes = new ArrayList<WindImbueRecipe>();
	
	public static WindImbueRecipe findRecipeByComponent(ItemStack c)
	{
		if(c == null)
			return null;
		
		for(int i = 0; i < recipes.size(); ++i)
		{
			if(c.isItemEqual(recipes.get(i).transforming))
				return recipes.get(i);
		}
		
		return null;
	}
	
	public static WindImbueRecipe findRecipeByResult(ItemStack r)
	{
		if(r == null)
			return null;
		
		for(int i = 0; i < recipes.size(); ++i)
		{
			if(r.isItemEqual(recipes.get(i).result))
				return recipes.get(i);
		}
		
		return null;
	}
	
	public WindImbueRecipe(ItemStack s1, ItemStack s2, int i)
	{
		transforming = s1;
		result = s2;
		enderEnergy = i;
		recipes.add(this);
	}

	@Override
	public boolean matches(InventoryCrafting c, World w) 
	{
		return c.getSizeInventory() > 0 && c.getSizeInventory() < 3 && c.getStackInSlot(0) != null && c.getStackInSlot(0).isItemEqual(transforming);
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting c) {
		return result;
	}

	@Override
	public int getRecipeSize() 
	{
		return 1;
	}

	@Override
	public ItemStack getRecipeOutput() 
	{
		return result;
	}

}
