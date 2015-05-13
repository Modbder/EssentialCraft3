package ec3.api;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class RadiatingChamberRecipe implements IRecipe{

	public ItemStack[] recipeItems = new ItemStack[2];
	public ItemStack result;
	public int mruRequired;
	public float upperBalanceLine,lowerBalanceLine;
	public float costModifier;
	public int recipeSize = 1;
	
	public RadiatingChamberRecipe(ItemStack[] ingred, ItemStack res, int mruReq, float[] balancePoints)
	{
		recipeItems = ingred;
		result = res;
		mruRequired = mruReq;
		upperBalanceLine = balancePoints[0];
		lowerBalanceLine = balancePoints[1];
		costModifier = 1.0F;
	}
	
	public RadiatingChamberRecipe(ItemStack[] ingred, ItemStack res, int mruReq, float[] balancePoints, int size)
	{
		recipeItems = ingred;
		result = res;
		mruRequired = mruReq;
		upperBalanceLine = balancePoints[0];
		lowerBalanceLine = balancePoints[1];
		costModifier = 1.0F;
		recipeSize = size;
		result.stackSize = size;
	}
	
	public RadiatingChamberRecipe(ItemStack[] ingred, ItemStack res, int mruReq, float[] balancePoints, float modifier)
	{
		recipeItems = ingred;
		result = res;
		mruRequired = mruReq;
		upperBalanceLine = balancePoints[0];
		lowerBalanceLine = balancePoints[1];
		costModifier = modifier;
	}
	
	public RadiatingChamberRecipe(ItemStack[] ingred, ItemStack res, int mruReq, float[] balancePoints, float modifier, int size)
	{
		recipeItems = ingred;
		result = res;
		mruRequired = mruReq;
		upperBalanceLine = balancePoints[0];
		lowerBalanceLine = balancePoints[1];
		costModifier = modifier;
		recipeSize = size;
		result.stackSize = size;
	}
	
	public RadiatingChamberRecipe(RadiatingChamberRecipe recipeByResult) {
		recipeItems = recipeByResult.recipeItems;
		result = recipeByResult.result;
		mruRequired = recipeByResult.mruRequired;
		upperBalanceLine = recipeByResult.upperBalanceLine;
		lowerBalanceLine = recipeByResult.lowerBalanceLine;
		costModifier = recipeByResult.costModifier;
	}

	@Override
	public boolean matches(InventoryCrafting p_77569_1_, World p_77569_2_) {
		if(p_77569_1_.getSizeInventory() >= 5)
		{
			boolean ret = true;
			for(int i = 1; i < 2; ++i)
			{
				if(!p_77569_1_.getStackInSlot(i).isItemEqual(recipeItems[i]))
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
		return 2;
	}

	@Override
	public ItemStack getRecipeOutput() {
		// TODO Auto-generated method stub
		return result;
	}
	
	public String toString()
	{
		String retStr = super.toString();
		for(int i = 0; i < this.recipeItems.length; ++i)
		{
			retStr+="||item_"+i+":"+recipeItems[i];
		}
		retStr+="||output:"+result;
		retStr+="||mru:"+mruRequired;
		retStr+="||upperBalance:"+upperBalanceLine;
		retStr+="||lowerBalance:"+lowerBalanceLine;
		return retStr;
	}

}
