package ec3.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class StructureRecipe implements IRecipe{
	
	public List<StructureBlock> structure = new ArrayList<StructureBlock>();
	public ItemStack referal;
	
	public StructureRecipe(ItemStack ref, StructureBlock... positions)
	{
		referal = ref;
		structure = Arrays.asList(positions);
	}

	@Override
	public boolean matches(InventoryCrafting p_77569_1_, World p_77569_2_) {
		return false;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting p_77572_1_) {
		return referal;
	}

	@Override
	public int getRecipeSize() {
		// TODO Auto-generated method stub
		return structure.size();
	}

	@Override
	public ItemStack getRecipeOutput() {
		// TODO Auto-generated method stub
		return referal;
	}
	
	

}
