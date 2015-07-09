package ec3.common.inventory;

import ec3.common.tile.TileNewMIMScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class ContainerNewMIMScreen extends Container{
	
    private IInventory inv;
    public ContainerNewMIMScreen(InventoryPlayer par1InventoryPlayer, TileEntity par2)
    {
        this.inv = (IInventory) par2;
        if(inv != null)
        {
	        TileNewMIMScreen screen = (TileNewMIMScreen) inv;
	        if(screen.parent != null)
	        {
	        	screen.parent.openAllStorages(par1InventoryPlayer.player);
	        }
	        
	        this.addSlotToContainer(new SlotBoundEssence(inv, 0, 8, 134));
	        this.addSlotToContainer(new Slot(inv, 1, 8, 26));
        }
        
        int i;

        for (i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(par1InventoryPlayer, j + i * 9 + 9, 48 + j * 18,174+ i * 18));
            }
        }

        for (i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(par1InventoryPlayer, i, 48 + i * 18, 232));
        }
    }

    @Override
    public void onContainerClosed(EntityPlayer entityPlayer)
    {
        super.onContainerClosed(entityPlayer);
        if(inv != null)
        {
	        TileNewMIMScreen screen = (TileNewMIMScreen) inv;
	        if(screen.parent != null)
	        {
	        	screen.parent.closeAllStorages(entityPlayer);
	        }
        }
    }
    
	@Override
	public boolean canInteractWith(EntityPlayer p_75145_1_) {
		// TODO Auto-generated method stub
		return inv.isUseableByPlayer(p_75145_1_);
	}

    public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(p_82846_2_);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (p_82846_2_ < inv.getSizeInventory())
            {
                if (!this.mergeItemStack(itemstack1, inv.getSizeInventory(), 36+inv.getSizeInventory(), true))
                {
                    if (itemstack1.stackSize == 0)
                    {
                        slot.putStack((ItemStack)null);
                    }
                    slot.onSlotChange(itemstack1, itemstack);
                    return null;
                }

                
            }
            else if (p_82846_2_ > inv.getSizeInventory())
            {
            	for(int i = 0; i < inv.getSizeInventory(); ++i)
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
            if (p_82846_2_ > inv.getSizeInventory() && p_82846_2_ < 27+inv.getSizeInventory())
            {
                if (!this.mergeItemStack(itemstack1, 27+inv.getSizeInventory(), 36+inv.getSizeInventory(), false))
                {
                    if (itemstack1.stackSize == 0)
                    {
                        slot.putStack((ItemStack)null);
                    }
                    return null;
                }
            }
            else if (p_82846_2_ > 27+inv.getSizeInventory() && p_82846_2_ < 36+inv.getSizeInventory() && !this.mergeItemStack(itemstack1, inv.getSizeInventory(), 27+inv.getSizeInventory(), false))
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
