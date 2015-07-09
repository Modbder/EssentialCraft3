package ec3.common.inventory;

import DummyCore.Utils.UnformedItemStack;
import ec3.utils.common.ECUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ContainerCraftingFrame extends Container{
	
	public InventoryCraftingFrame inventory;
	public ItemStack currentStack;
	public EntityPlayer player;
	
	public static class SlotFake extends Slot
	{
		ContainerCraftingFrame parent;
		EntityPlayer player;
		public SlotFake(EntityPlayer p,ContainerCraftingFrame c,IInventory inv, int id, int x,	int y) {
			super(inv, id, x, y);
			parent = c;
			player = p;
		}
		
	    public void onPickupFromSlot(EntityPlayer pickuper, ItemStack stk)
	    {
	    	this.inventory.setInventorySlotContents(slotNumber, null);
	    	this.onSlotChanged();
	    }
		
	    public boolean canTakeStack(EntityPlayer player)
	    {
    		InventoryCrafting crafting = new InventoryCrafting(this.parent, 3, 3);
    		
    		for(int i = 0; i < 9; ++i)
    		{
    			if(i == 0)
    				crafting.setInventorySlotContents(i, parent.inventory.getStackInSlot(0));
    			if(i == 1)
    				crafting.setInventorySlotContents(i, parent.inventory.getStackInSlot(3));
    			if(i == 2)
    				crafting.setInventorySlotContents(i, parent.inventory.getStackInSlot(6));
    			if(i == 3)
    				crafting.setInventorySlotContents(i, parent.inventory.getStackInSlot(1));
    			if(i == 4)
    				crafting.setInventorySlotContents(i, parent.inventory.getStackInSlot(4));
    			if(i == 5)
    				crafting.setInventorySlotContents(i, parent.inventory.getStackInSlot(7));
    			if(i == 6)
    				crafting.setInventorySlotContents(i, parent.inventory.getStackInSlot(2));
    			if(i == 7)
    				crafting.setInventorySlotContents(i, parent.inventory.getStackInSlot(5));
    			if(i == 8)
    				crafting.setInventorySlotContents(i, parent.inventory.getStackInSlot(8));
    		}
    		
    		ItemStack result = CraftingManager.getInstance().findMatchingRecipe(crafting, player.worldObj);

    		parent.inventory.setInventorySlotContents(9, result != null ? result.copy() : null);
    		
    		crafting = null;
    		
    		if(this.slotNumber == 9 && result == null)
    			this.inventory.setInventorySlotContents(slotNumber, null);
    		
    		if(this.slotNumber != 9)
    		{
    			this.inventory.setInventorySlotContents(slotNumber, null);
    			
        		crafting = new InventoryCrafting(this.parent, 3, 3);
        		for(int i = 0; i < 9; ++i)
        			crafting.setInventorySlotContents(i, parent.inventory.getStackInSlot(i));
        		
        		result = CraftingManager.getInstance().findMatchingRecipe(crafting, player.worldObj);

        		parent.inventory.setInventorySlotContents(9, result != null ? result.copy() : null);
        		
        		crafting = null;
    		}
    		
    		this.onSlotChanged();
    		
	        return false;
	    }
	    
	    public boolean isItemValid(ItemStack stk)
	    {
	    	if(stk != null)
	    	{
	    		if(this.slotNumber != 9)
	    		{
	    			ItemStack setTo = stk.copy();
	    			setTo.stackSize = 0;
	    			this.inventory.setInventorySlotContents(slotNumber, setTo);

		    		InventoryCrafting crafting = new InventoryCrafting(this.parent, 3, 3);
		    		
		    		for(int i = 0; i < 9; ++i)
		    		{
		    			if(i == 0)
		    				crafting.setInventorySlotContents(i, parent.inventory.getStackInSlot(0));
		    			if(i == 1)
		    				crafting.setInventorySlotContents(i, parent.inventory.getStackInSlot(3));
		    			if(i == 2)
		    				crafting.setInventorySlotContents(i, parent.inventory.getStackInSlot(6));
		    			if(i == 3)
		    				crafting.setInventorySlotContents(i, parent.inventory.getStackInSlot(1));
		    			if(i == 4)
		    				crafting.setInventorySlotContents(i, parent.inventory.getStackInSlot(4));
		    			if(i == 5)
		    				crafting.setInventorySlotContents(i, parent.inventory.getStackInSlot(7));
		    			if(i == 6)
		    				crafting.setInventorySlotContents(i, parent.inventory.getStackInSlot(2));
		    			if(i == 7)
		    				crafting.setInventorySlotContents(i, parent.inventory.getStackInSlot(5));
		    			if(i == 8)
		    				crafting.setInventorySlotContents(i, parent.inventory.getStackInSlot(8));
		    		}
		    		
		    		ItemStack result = CraftingManager.getInstance().findMatchingRecipe(crafting, player.worldObj);
		    		
		    		parent.inventory.setInventorySlotContents(9, result != null ? result.copy() : null);
		    		
		    		crafting = null;
		    		
		    		this.onSlotChanged();
		    		
		    		Slot.class.cast(this.parent.inventorySlots.get(9)).onSlotChanged();
	    		}else
	    		{
	    			IRecipe settedRec = null;
	    			
	    			if(!this.getHasStack())
	    			{
	    				IRecipe rec = ECUtils.findRecipeByIS(stk, 0);
	    				if(rec == null && !parent.player.getCurrentEquippedItem().getTagCompound().getBoolean("ignoreOreDict"))
	    					rec = ECUtils.findRecipeByIS(stk, 2);
	    				
	    				settedRec = rec;
	    				
	    				if(rec != null)
	    				{
	    					for(int i = 0; i < 9; ++i)
	    					{
	    						this.parent.inventory.setInventorySlotContents(i, null);
	    						if(rec instanceof ShapelessRecipes)
	    						{
	    							ShapelessRecipes srec = (ShapelessRecipes) rec;
	    							if(srec.recipeItems.size() > i)
	    								this.parent.inventory.setInventorySlotContents(i, (ItemStack) srec.recipeItems.get(i));
	    						}
	    						if(rec instanceof ShapedRecipes)
	    						{
	    							ShapedRecipes srec = (ShapedRecipes) rec;
	    							if(srec.recipeItems.length > i)
	    								this.parent.inventory.setInventorySlotContents(i, (ItemStack) srec.recipeItems[i]);
	    						}
	    						if(rec instanceof ShapelessOreRecipe)
	    						{
	    							ShapelessOreRecipe srec = (ShapelessOreRecipe) rec;
	    							if(srec.getInput().size() > i)
	    							{
	    								if(srec.getInput().get(i) != null)
	    								{
	    									UnformedItemStack ust = new UnformedItemStack(srec.getInput().get(i));
	    									this.parent.inventory.setInventorySlotContents(i, (ItemStack)ust.possibleStacks.get(player.worldObj.rand.nextInt(ust.possibleStacks.size())));
	    								}else
	    									this.parent.inventory.setInventorySlotContents(i, null);
	    							}
	    						}
	    						if(rec instanceof ShapedOreRecipe)
	    						{
	    							ShapedOreRecipe srec = (ShapedOreRecipe) rec;
	    							if(srec.getInput().length > i)
	    							{
	    								if(srec.getInput()[i] != null)
	    								{
	    									UnformedItemStack ust = new UnformedItemStack(srec.getInput()[i]);
	    									this.parent.inventory.setInventorySlotContents(i, (ItemStack)ust.possibleStacks.get(player.worldObj.rand.nextInt(ust.possibleStacks.size())));
	    								}else
	    									this.parent.inventory.setInventorySlotContents(i, null);
	    							}
	    						}
	    						
	    						Slot.class.cast(this.parent.inventorySlots.get(i)).onSlotChanged();
	    					}
	    				}
	    			}
	    			
	    			this.inventory.setInventorySlotContents(slotNumber, settedRec == null || settedRec.getRecipeOutput() == null ? null : settedRec.getRecipeOutput().copy());
	    		}
	    	}
	        return false;
	    }
		
	}
	
	public ContainerCraftingFrame(EntityPlayer p, InventoryCraftingFrame inv)
	{
		player = p;
		inventory = inv;
		
		for(int o = 0; o < 9; ++o)
		{
			this.addSlotToContainer(new SlotFake(p,this,inv,o, 30+(o/3*18),17+(o%3*18)));
		}
		
		this.addSlotToContainer(new SlotFake(p,this,inv,9, 124,17+18));
		
        int i;

        
        for (i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(p.inventory, j + i * 9 + 9, 8 + j * 18,84+ i * 18));
            }
        }

        for (i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(p.inventory, i, 8 + i * 18, 142));
        }
	}

	@Override
	public boolean canInteractWith(EntityPlayer p) {
		return true;
	}
	
	public void saveToNBT(ItemStack itemStack)
	{
		if (!itemStack.hasTagCompound())
		{
			itemStack.setTagCompound(new NBTTagCompound());
		}
		inventory.writeToNBT(itemStack.getTagCompound());
	}
	
	@Override
	public ItemStack slotClick(int slotID, int buttonPressed, int flag, EntityPlayer player)
    {
		Slot tmpSlot;
		if (slotID >= 0 && slotID < inventorySlots.size())
		{
			tmpSlot = (Slot) inventorySlots.get(slotID);
		}
		else
		{
			tmpSlot = null;
		}

		if (tmpSlot != null && tmpSlot.isSlotInInventory(player.inventory, player.inventory.currentItem))
		{
			return tmpSlot.getStack();
		}
		
		return super.slotClick(slotID, buttonPressed, flag, player);
    }

    public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_)
    {
        return null;
    }
	
}
