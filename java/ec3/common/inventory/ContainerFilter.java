package ec3.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ContainerFilter extends Container{
	
	public InventoryMagicFilter inventory;
	
	public EntityPlayer player;
	
	public ContainerFilter(EntityPlayer p, InventoryMagicFilter inv)
	{
		player = p;
		inventory = inv;
		
		for(int o = 0; o < 9; ++o)
		{
			this.addSlotToContainer(new Slot(inv,o, 62+(o%3*18),17+(o/3*18)));
		}
		
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
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(p_82846_2_);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (p_82846_2_ < inventory.getSizeInventory())
            {
                if (!this.mergeItemStack(itemstack1, inventory.getSizeInventory(), 36+inventory.getSizeInventory(), true))
                {
                    if (itemstack1.stackSize == 0)
                    {
                        slot.putStack((ItemStack)null);
                    }
                    slot.onSlotChange(itemstack1, itemstack);
                    return null;
                }

                
            }
            else if (p_82846_2_ > inventory.getSizeInventory())
            {
            	for(int i = 0; i < inventory.getSizeInventory(); ++i)
            	{
                    if (this.mergeItemStack(itemstack1, i, i+1, false))
                    {
                        if (itemstack1.stackSize == 0)
                        {
                            slot.putStack((ItemStack)null);
                        }
                        return null;
                    }
            	}
            }
            if (p_82846_2_ > inventory.getSizeInventory() && p_82846_2_ < 27+inventory.getSizeInventory())
            {
                if (!this.mergeItemStack(itemstack1, 27+inventory.getSizeInventory(), 36+inventory.getSizeInventory(), false))
                {
                    if (itemstack1.stackSize == 0)
                    {
                        slot.putStack((ItemStack)null);
                    }
                    return null;
                }
            }
            else if (p_82846_2_ > 27+inventory.getSizeInventory() && p_82846_2_ < 36+inventory.getSizeInventory() && !this.mergeItemStack(itemstack1, inventory.getSizeInventory(), 27+inventory.getSizeInventory(), false))
            {
                if (itemstack1.stackSize == 0)
                {
                    slot.putStack((ItemStack)null);
                }
                return null;
            }
            if (itemstack.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(p_82846_1_, itemstack1);
        }

        return itemstack;
    }
	
}
