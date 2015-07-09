package ec3.common.tile;

import ec3.common.inventory.InventoryCraftingFrame;
import ec3.common.item.ItemCraftingFrame;
import ec3.utils.common.ECUtils;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;

public class TileCrafter extends TileMRUGeneric{

	public TileCrafter()
	{
		super();
		this.setMaxMRU(0);
		this.setSlotsNum(11);
		this.slot0IsBoundGem = false;
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int side) 
	{
		return super.getAccessibleSlotsFromSide(side);
	}
	
	@Override
	public int getInventoryStackLimit() {
		return 64;
	}
	
	@Override
	public int[] getOutputSlots() {
		// TODO Auto-generated method stub
		return new int[]{9};
	}
	
	@Override
	public void updateEntity()
    {
		if(this.worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord) || this.worldObj.getBlockPowerInput(xCoord, yCoord, yCoord) > 0)
		{
			if(!this.hasFrame())
			{
				this.makeRecipe();
			}else
			{
				if(this.hasSufficientForCraftWithFrame())
					this.makeRecipe();
			}
		}
	}
	
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) 
	{
		if(stack == null)
			return false;
		
		return slot == 10 ? isFrame(stack) : slot == 9 ? false : isItemFineForSlot(stack,slot);
	}
	
	public boolean isFrame(ItemStack is)
	{
		return is != null && is.getItem() instanceof ItemCraftingFrame && new InventoryCraftingFrame(is).inventory[9] != null;
	}
	
	
	public boolean isItemFineForSlot(ItemStack compared, int slotNum)
	{
		if(hasFrame())
		{
			return areStacksTheSame(getRecipeFromFrame()[slotNum],compared,hasOreDict());
		}else
		{
			return true;
		}
	}
	
	public boolean hasOreDict()
	{
		return this.getStackInSlot(10) != null && this.getStackInSlot(10).getItem() instanceof ItemCraftingFrame && this.getStackInSlot(10).stackTagCompound != null && !this.getStackInSlot(10).stackTagCompound.getBoolean("ignoreOreDict");
	}
	
	public boolean hasFrame()
	{
		return this.getStackInSlot(10) != null && this.getStackInSlot(10).getItem() instanceof ItemCraftingFrame && new InventoryCraftingFrame(this.getStackInSlot(10)).inventory[9] != null;
	}
	
	public boolean areStacksTheSame(ItemStack stk1, ItemStack stk2, boolean oreDict)
	{
		if(stk1 == null && stk2 == null)
			return true;
		
		if(stk1 == null || stk2 == null)
			return false;
		
		if(!oreDict)
		{
			if(!stk1.isItemEqual(stk2))
				return false;
			
			if(!ItemStack.areItemStacksEqual(stk1, stk2))
				return false;
		}else
		{
			if(!ECUtils.oreDictionaryCompare(stk1, stk2))
				return false;
		}
		
		return true;
	}
	
	public boolean hasSufficientForCraftWithFrame()
	{
		ItemStack[] frame = this.getRecipeFromFrame();
		for(int i = 0; i < 9; ++i)
		{
			ItemStack stk = this.getStackInSlot(i);
			if(!areStacksTheSame(frame[i],stk,hasOreDict()))
				return false;
		}
		
		return true;
	}
	
	public ItemStack[] getRecipeFromFrame()
	{
		if(this.getStackInSlot(10) != null && this.getStackInSlot(10).getItem() instanceof ItemCraftingFrame)
		{
			InventoryCraftingFrame cInv = new InventoryCraftingFrame(this.getStackInSlot(10));
			if(cInv.inventory[9] != null)
			{
				ItemStack[] arrayStk = new ItemStack[9];
				
				arrayStk[0] = cInv.inventory[0];
				arrayStk[1] = cInv.inventory[3];
				arrayStk[2] = cInv.inventory[6];
				arrayStk[3] = cInv.inventory[1];
				arrayStk[4] = cInv.inventory[4];
				arrayStk[5] = cInv.inventory[7];
				arrayStk[6] = cInv.inventory[2];
				arrayStk[7] = cInv.inventory[5];
				arrayStk[8] = cInv.inventory[8];
				
				return arrayStk;
			}
		}
		return null;
	}
	
	public static class InventoryCraftingNoContainer extends InventoryCrafting
	{
		
	    private ItemStack[] stackList;
	    private int inventoryWidth;
	    
	    public InventoryCraftingNoContainer(int p_i1807_2_, int p_i1807_3_)
	    {
	    	super(null,p_i1807_2_,p_i1807_3_);
	        int k = p_i1807_2_ * p_i1807_3_;
	        this.stackList = new ItemStack[k];
	        this.inventoryWidth = p_i1807_2_;
	    }
	    
	    public int getSizeInventory()
	    {
	        return this.stackList.length;
	    }
	    
	    public ItemStack getStackInSlot(int p_70301_1_)
	    {
	        return p_70301_1_ >= this.getSizeInventory() ? null : this.stackList[p_70301_1_];
	    }

	    public ItemStack getStackInRowAndColumn(int p_70463_1_, int p_70463_2_)
	    {
	        if (p_70463_1_ >= 0 && p_70463_1_ < this.inventoryWidth)
	        {
	            int k = p_70463_1_ + p_70463_2_ * this.inventoryWidth;
	            return this.getStackInSlot(k);
	        }
	        else
	        {
	            return null;
	        }
	    }
	    
	    public ItemStack getStackInSlotOnClosing(int p_70304_1_)
	    {
	        if (this.stackList[p_70304_1_] != null)
	        {
	            ItemStack itemstack = this.stackList[p_70304_1_];
	            this.stackList[p_70304_1_] = null;
	            return itemstack;
	        }
	        else
	        {
	            return null;
	        }
	    }
	    
	    public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_)
	    {
	        if (this.stackList[p_70298_1_] != null)
	        {
	            ItemStack itemstack;

	            if (this.stackList[p_70298_1_].stackSize <= p_70298_2_)
	            {
	                itemstack = this.stackList[p_70298_1_];
	                this.stackList[p_70298_1_] = null;
	                return itemstack;
	            }
	            else
	            {
	                itemstack = this.stackList[p_70298_1_].splitStack(p_70298_2_);

	                if (this.stackList[p_70298_1_].stackSize == 0)
	                {
	                    this.stackList[p_70298_1_] = null;
	                }

	                return itemstack;
	            }
	        }
	        else
	        {
	            return null;
	        }
	    }

	    public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_)
	    {
	        this.stackList[p_70299_1_] = p_70299_2_;
	    }
		
	}
	
	public void makeRecipe()
	{
		InventoryCraftingNoContainer craftingInv = new InventoryCraftingNoContainer(3, 3);
		
		for(int i = 0; i < 9; ++i)
		{
			craftingInv.setInventorySlotContents(i, this.getStackInSlot(i));
		}
		
		ItemStack result = CraftingManager.getInstance().findMatchingRecipe(craftingInv, this.worldObj);
		
		
		if(result != null)
		{
			if(this.getStackInSlot(9) == null)
			{
				this.setInventorySlotContents(9, result.copy());
				decreaseStacks();
			}else
			{
				if(this.getStackInSlot(9).isItemEqual(result) && ItemStack.areItemStackTagsEqual(result, this.getStackInSlot(9)))
				{
					if(this.getStackInSlot(9).stackSize + result.stackSize <= this.getInventoryStackLimit() && this.getStackInSlot(9).stackSize + result.stackSize <= result.getMaxStackSize())
					{
						this.getStackInSlot(9).stackSize += result.stackSize;
						decreaseStacks();
					}
				}
			}
		}
	}
	
	public void decreaseStacks()
	{
		for(int i = 0; i < 9; ++i)
		{
			ItemStack is = this.getStackInSlot(i);
			if(is != null)
			{
				ItemStack container = is.getItem().getContainerItem(is);
				this.decrStackSize(i, 1);
				if((this.getStackInSlot(i) == null || this.getStackInSlot(i).stackSize <= 0) && container != null)
				{
					this.setInventorySlotContents(i, container.copy());
				}
			}
		}
	}

}
