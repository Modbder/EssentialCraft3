package ec3.api;

import DummyCore.Utils.UnformedItemStack;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class MagicianTableRecipe implements IRecipe{

	public UnformedItemStack[] requiredItems = new UnformedItemStack[5];
	public ItemStack result;
	public int mruRequired;
	
	public MagicianTableRecipe(UnformedItemStack[] i, ItemStack j, int k)
	{
		requiredItems = i;
		result = j;
		mruRequired = k;
	}
	
	public MagicianTableRecipe(MagicianTableRecipe recipeByResult) {
		requiredItems = recipeByResult.requiredItems;
		result = recipeByResult.result;
		mruRequired = recipeByResult.mruRequired;
	}

	@Override
	public boolean matches(InventoryCrafting p_77569_1_, World p_77569_2_) {
		if(p_77569_1_.getSizeInventory() >= 5)
		{
			boolean ret = true;
			if(!requiredItems[0].itemStackMatches(p_77569_1_.getStackInSlot(0)))
				ret = false;
			for(int i = 1; i < 5; ++i)
			{
				if(!requiredItems[i].itemStackMatches(p_77569_1_.getStackInSlot(i)))
				{
					ret = false;
				}
			}
			return ret;
		}
		return false;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting p_77572_1_) {
		// TODO Auto-generated method stub
		return result;
	}

	@Override
	public int getRecipeSize() {
		// TODO Auto-generated method stub
		return 5;
	}

	@Override
	public ItemStack getRecipeOutput() {
		// TODO Auto-generated method stub
		return result;
	}
	
	public String toString()
	{
		String retStr = super.toString();
		for(int i = 0; i < this.requiredItems.length; ++i)
		{
			retStr+="||item_"+i+":"+requiredItems[i];
		}
		retStr+="||output:"+result;
		retStr+="||mru:"+mruRequired;
		return retStr;
	}

}
