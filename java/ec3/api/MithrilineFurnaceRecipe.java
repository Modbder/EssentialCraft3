package ec3.api;

import DummyCore.Utils.UnformedItemStack;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class MithrilineFurnaceRecipe implements IRecipe{
	
	public final UnformedItemStack smelted;
	public final ItemStack result;
	public final float enderStarPulsesRequired;
	public final int requiredRecipeSize;
	
	public MithrilineFurnaceRecipe(UnformedItemStack is, ItemStack is_1, float f1, int reqStack)
	{
		smelted = is;
		result = is_1;
		enderStarPulsesRequired = f1;
		requiredRecipeSize = reqStack;
	}

	@Override
	public boolean matches(InventoryCrafting p_77569_1_, World p_77569_2_) {
		return smelted.itemStackMatches(p_77569_1_.getStackInSlot(0));
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting p_77572_1_) {
		return result;
	}

	@Override
	public int getRecipeSize() {
		return 2;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return result;
	}

}
