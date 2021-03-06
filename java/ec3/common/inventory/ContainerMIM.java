package ec3.common.inventory;

import ec3.common.item.ItemBoundGem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class ContainerMIM extends Container{
	
    private IInventory tile;
    public ContainerMIM(InventoryPlayer par1InventoryPlayer, TileEntity par2)
    {
        this.tile = (IInventory) par2;
        this.addSlotToContainer(new SlotBoundEssence(tile, 0, -17, -17));
        
        for(int i = 0; i < 6*9; ++i)
        {
        	this.addSlotToContainer(new Slot(tile, 1+i, 8+(i%9)*18, 8 + (i/9)*18));
        }
        
        int i;

        for (i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(par1InventoryPlayer, j + i * 9 + 9, 8 + j * 18,7*18+ i * 18));
            }
        }

        for (i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(par1InventoryPlayer, i, 8 + i * 18, 8+10*18));
        }
    }

	@Override
	public boolean canInteractWith(EntityPlayer p_75145_1_) {
		// TODO Auto-generated method stub
		return tile.isUseableByPlayer(p_75145_1_);
	}

	@Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(par2);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (par2 != 1 && par2 != 0)
            {
                if (itemstack1.getItem() instanceof ItemBoundGem)
                {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false))
                    {
                        return null;
                    }
                }
                else if (par2 >= 1 && par2 < 28)
                {
                    if (!this.mergeItemStack(itemstack1, 28, 37, false))
                    {
                        return null;
                    }
                }
                else if (par2 >= 28 && par2 < 37 && !this.mergeItemStack(itemstack1, 1, 28, false))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 1, 37, false))
            {
                return null;
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

            slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
        }

        return itemstack;
    }
}
