package ec3.common.tile;

import java.util.ArrayList;

import ec3.common.inventory.InventoryCraftingFrame;
import ec3.common.item.ItemCraftingFrame;
import net.minecraft.item.ItemStack;

public class TileNewMIMCraftingManager extends TileMRUGeneric{

	public TileNewMIM parent;
	int tickTime;
	public ArrayList<CraftingPattern> allCrafts = new ArrayList<CraftingPattern>();
	
	public static class CraftingPattern
	{
		public ItemStack result;
		public ItemStack[] input;
		public boolean isOreDict;
		public ItemStack crafter;
		
		public CraftingPattern(ItemStack par1, ItemStack par2, ItemStack[] par3, boolean par4)
		{
			result = par1;
			crafter = par2;
			input = par3;
			isOreDict = par4;
		}
		
		public CraftingPattern(ItemStack crafting)
		{
			if(crafting != null && crafting.getItem() instanceof ItemCraftingFrame && crafting.hasTagCompound())
			{
				InventoryCraftingFrame frame = new InventoryCraftingFrame(crafting);
				if(frame != null && frame.getStackInSlot(9) != null && (frame.getStackInSlot(0) != null || frame.getStackInSlot(1) != null || frame.getStackInSlot(2) != null || frame.getStackInSlot(3) != null || frame.getStackInSlot(4) != null || frame.getStackInSlot(5) != null || frame.getStackInSlot(6) != null || frame.getStackInSlot(7) != null || frame.getStackInSlot(8) != null))
				{
					ItemStack[] is = new ItemStack[]{frame.getStackInSlot(0),frame.getStackInSlot(1),frame.getStackInSlot(2),frame.getStackInSlot(3),frame.getStackInSlot(4),frame.getStackInSlot(5),frame.getStackInSlot(6),frame.getStackInSlot(7),frame.getStackInSlot(8)};
					result = frame.getStackInSlot(9);
					crafter = crafting;
					input = is;
					isOreDict = !crafting.getTagCompound().getBoolean("ignoreOreDict");
				}
			}
		}
		
		public boolean isValidRecipe()
		{
			return result != null && crafter != null && input.length == 9 && (input[0] != null || input[1] != null || input[2] != null || input[3] != null || input[4] != null || input[5] != null || input[6] != null || input[7] != null || input[8] != null);
		}
		
		public boolean isResultTheSame(ItemStack is)
		{
			if(is == null || !isValidRecipe())
				return false;
			
			return is.isItemEqual(result) && ItemStack.areItemStackTagsEqual(is, result);
		}
		
	}
	
    @Override
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
    {
    	super.setInventorySlotContents(par1, par2ItemStack);
    	this.syncTick = 0;
    }
	
	public TileNewMIMCraftingManager()
	{
		this.setSlotsNum(54);
	}
	
	public void updateEntity() 
	{
		if(this.syncTick == 60)
		{
			rebuildRecipes();
		}
		
		super.updateEntity();
		
		if(tickTime == 0)
		{
			tickTime = 20;
			if(parent != null)
				if(!parent.isParent(this))
					parent = null;
		}else
			--tickTime;
	}
	
	@Override
	public int[] getOutputSlots() {
		return new int[0];
	}
	
	public void rebuildRecipes()
	{
		allCrafts.clear();
		for(int i = 0; i < this.getSizeInventory(); ++i)
		{
			ItemStack is = this.getStackInSlot(i);
			if(is != null)
			{
				CraftingPattern p = new CraftingPattern(is);
				if(p.isValidRecipe())
				{
					allCrafts.add(p);
					continue;
				}else
					p = null;
			}
		}
	}
	
	public ItemStack findCraftingFrameByRecipe(ItemStack result)
	{
		for(int i = 0; i < this.allCrafts.size(); ++i)
		{
			if(allCrafts.get(i).isResultTheSame(result))
				return allCrafts.get(i).crafter;
		}
		
		return null;
	}
	
	public ItemStack[] findCraftingComponentsByRecipe(ItemStack result)
	{
		for(int i = 0; i < this.allCrafts.size(); ++i)
		{
			if(allCrafts.get(i).isResultTheSame(result))
			{
				return allCrafts.get(i).input;
			}
		}
		
		return null;
	}
	
	public ItemStack findResultByCraftingFrame(ItemStack frame)
	{
		for(int i = 0; i < this.allCrafts.size(); ++i)
		{
			if(ItemStack.areItemStacksEqual(allCrafts.get(i).crafter, frame) && ItemStack.areItemStackTagsEqual(allCrafts.get(i).crafter, frame))
			{
				return allCrafts.get(i).result;
			}
		}
		
		return null;
	}
	
	public int craft(ItemStack result, int times)
	{
		ItemStack stk = findCraftingFrameByRecipe(result);
		
		if(stk == null)
			return 0;
		
		int crafted = 0;
		
		for(int i = 0; i < times; ++i)
		{
			ItemStack[] required = findCraftingComponentsByRecipe(result);
			if(canCraft(required,stk))
			{
				if(craft(required,stk))
				{
					parent.addItemStackToSystem(result.copy());
					++crafted;
				}
			}
		}
		
		return crafted;
	}
	
	public boolean canCraft(ItemStack[] components, ItemStack crafter)
	{
		if(crafter == null)
			return false;
		
		if(!crafter.hasTagCompound())
			return false;
		
		for(int i = 0; i < components.length; ++i)
		{
			if(parent.retrieveItemStackFromSystem(components[i], !crafter.getTagCompound().getBoolean("ignoreOreDict"), false) > 0)
				return false;
		}
		
		return true;
	}
	
	public boolean craft(ItemStack[] components, ItemStack crafter)
	{
		if(crafter == null)
			return false;
		
		if(!crafter.hasTagCompound())
			return false;
		
		for(int i = 0; i < components.length; ++i)
		{
			if(parent.retrieveItemStackFromSystem(components[i], !crafter.getTagCompound().getBoolean("ignoreOreDict"), true) > 0)
			{
				for(int j = 0; j < i; ++j)
				{
					parent.addItemStackToSystem(components[j]);
				}
				return false;
			}
		}
		
		return true;
	}
	
	public ArrayList<CraftingPattern> getAllRecipes()
	{
		return allCrafts;
	}
	
	public ArrayList<ItemStack> getAllResults()
	{
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		
		for(int i = 0; i < allCrafts.size(); ++i)
		{
			CraftingPattern par = allCrafts.get(i);
			if(par.isValidRecipe())
				ret.add(par.result);
		}
		
		return ret;
	}
	
	public boolean hasRecipe(ItemStack is)
	{
		if(is == null)
			return false;
		
		for(int i = 0; i < this.getSizeInventory(); ++i)
		{
			if(this.getStackInSlot(i) != null && this.getStackInSlot(i).getItem() instanceof ItemCraftingFrame)
			{
				InventoryCraftingFrame inv = new InventoryCraftingFrame(this.getStackInSlot(i));
				if(inv != null)
				{
					if(inv.getStackInSlot(9) != null && inv.getStackInSlot(9).isItemEqual(is) && ItemStack.areItemStackTagsEqual(is, inv.getStackInSlot(9)))
					{
						return true;
					}
				}
			}
		}
		
		return false;
	}

}
