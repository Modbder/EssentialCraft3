package ec3.common.inventory;

import ec3.common.tile.TileCrafter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ContainerCrafter extends Container{
	
	TileCrafter tile;
	
    public ContainerCrafter(InventoryPlayer inventoryPlayer, TileCrafter crft)
    {
        this.tile = crft;
        
        final TileCrafter tc = crft;
        
		for(int o = 0; o < 9; ++o)
		{
			final int p = o;
			this.addSlotToContainer(new Slot(tc,o, 30+(o%3*18),17+(o/3*18)){
				
			    public boolean isItemValid(ItemStack stk)
			    {
			        return tc.isItemValidForSlot(p, stk);
			    }

			});
		}
		
		this.addSlotToContainer(new Slot(tc,9, 124,35){
			
		    public boolean isItemValid(ItemStack stk)
		    {
		        return false;
		    }

		});
		this.addSlotToContainer(new Slot(tc,10, 94,17)
		{
		    public boolean isItemValid(ItemStack stk)
		    {
		        return tc.isFrame(stk);
		    }
		});

        for (int inventoryRowIndex = 0; inventoryRowIndex < 3; ++inventoryRowIndex)
        {
            for (int inventoryColumnIndex = 0; inventoryColumnIndex < 9; ++inventoryColumnIndex)
            {
                this.addSlotToContainer(new Slot(inventoryPlayer, inventoryColumnIndex + inventoryRowIndex * 9 + 9, 8 + inventoryColumnIndex * 18, 84 + inventoryRowIndex * 18));
            }
        }

        for (int actionBarSlotIndex = 0; actionBarSlotIndex < 9; ++actionBarSlotIndex)
        {
            this.addSlotToContainer(new Slot(inventoryPlayer, actionBarSlotIndex, 8 + actionBarSlotIndex * 18, 142));
        }
    }
    
    @Override
    public void onContainerClosed(EntityPlayer entityPlayer)
    {
        super.onContainerClosed(entityPlayer);
        tile.closeInventory();
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityPlayer)
    {
        return this.tile.isUseableByPlayer(entityPlayer);
    }
    
    @Override
    public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int slotIndex)
    {
        ItemStack newItemStack = null;
        Slot slot = (Slot) inventorySlots.get(slotIndex);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemStack = slot.getStack();
            newItemStack = itemStack.copy();

            if (slotIndex < 11)
            {
                if (!this.mergeItemStack(itemStack, 11, inventorySlots.size(), false))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemStack, 0, 11, false))
            {
                return null;
            }

            if (itemStack.stackSize == 0)
            {
                slot.putStack(null);
            }
            else
            {
                slot.onSlotChanged();
            }
        }

        return newItemStack;
    }    

	@Override
	protected boolean mergeItemStack(ItemStack itemStack, int slotMin, int slotMax, boolean ascending)
	{
	    boolean slotFound = false;
	    int currentSlotIndex = ascending ? slotMax - 1 : slotMin;
        Slot slot;
        ItemStack stackInSlot;
	    if (itemStack.isStackable())
        {
	    	while (itemStack.stackSize > 0 && (!ascending && currentSlotIndex < slotMax || ascending && currentSlotIndex >= slotMin))
	    	{
               slot = (Slot) this.inventorySlots.get(currentSlotIndex);
                stackInSlot = slot.getStack();
                if (slot.isItemValid(itemStack) && equalsIgnoreStackSize(itemStack, stackInSlot))
                {
                    int combinedStackSize = stackInSlot.stackSize + itemStack.stackSize;
                    int slotStackSizeLimit = Math.min(stackInSlot.getMaxStackSize(), slot.getSlotStackLimit());
                    if (combinedStackSize <= slotStackSizeLimit)
                    {
                        itemStack.stackSize = 0;
                        stackInSlot.stackSize = combinedStackSize;
                        slot.onSlotChanged();
                        slotFound = true;
                    }
                    else if (stackInSlot.stackSize < slotStackSizeLimit)
                    {
                        itemStack.stackSize -= slotStackSizeLimit - stackInSlot.stackSize;
                        stackInSlot.stackSize = slotStackSizeLimit;
                        slot.onSlotChanged();
                        slotFound = true;
                    }
               }
               currentSlotIndex += ascending ? -1 : 1;
            }
        }

        if (itemStack.stackSize > 0)
        {
            currentSlotIndex = ascending ? slotMax - 1 : slotMin;
            while (!ascending && currentSlotIndex < slotMax || ascending && currentSlotIndex >= slotMin)
            {
                slot = (Slot) this.inventorySlots.get(currentSlotIndex);
                stackInSlot = slot.getStack();
                if (slot.isItemValid(itemStack) && stackInSlot == null)
                {
                    slot.putStack(cloneItemStack(itemStack, Math.min(itemStack.stackSize, slot.getSlotStackLimit())));
                    slot.onSlotChanged();
                    if (slot.getStack() != null)
                    {
                        itemStack.stackSize -= slot.getStack().stackSize;
                        slotFound = true;
                    }
                    break;
                }
	            currentSlotIndex += ascending ? -1 : 1;
            }
        }
	    return slotFound;
    }
	
    public static ItemStack cloneItemStack(ItemStack itemStack, int stackSize)
    {
        ItemStack clonedItemStack = itemStack.copy();
        clonedItemStack.stackSize = stackSize;
        return clonedItemStack;
    }
    
    public static boolean equalsIgnoreStackSize(ItemStack itemStack1, ItemStack itemStack2)
    {
        if (itemStack1 != null && itemStack2 != null)
        {
            if (Item.getIdFromItem(itemStack1.getItem()) - Item.getIdFromItem(itemStack2.getItem()) == 0)
            {
                if (itemStack1.getItem() == itemStack2.getItem())
                {
                    if (itemStack1.getItemDamage() == itemStack2.getItemDamage())
                    {
                        if (itemStack1.hasTagCompound() && itemStack2.hasTagCompound())
                        {
                            if (ItemStack.areItemStackTagsEqual(itemStack1, itemStack2))
                            {
                                return true;
                            }
                        }
                        else if (!itemStack1.hasTagCompound() && !itemStack2.hasTagCompound())
                        {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }
}
