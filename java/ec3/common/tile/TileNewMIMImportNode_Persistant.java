package ec3.common.tile;

import ec3.common.inventory.InventoryMagicFilter;
import ec3.common.item.ItemFilter;
import ec3.utils.common.ECUtils;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;

public class TileNewMIMImportNode_Persistant extends TileNewMIMImportNode{

	public TileNewMIMImportNode_Persistant()
	{
		super();
	}
	
	@Override
	public void importAllPossibleItems(TileNewMIM parent)
	{
		if(this.worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord))
			return;
		
		ISidedInventory inv = getConnectedInventory();
		if(inv != null)
		{
			int[] slots = getAccessibleSlots();
			
			if(slots.length <= 0)
				return;
			
			for(int j = 0; j < slots.length; ++j)
			{
				ItemStack stk = inv.getStackInSlot(slots[j]);
				if(stk != null)
				{
					if(this.getStackInSlot(0) == null || !(this.getStackInSlot(0).getItem() instanceof ItemFilter))
					{
						ItemStack current = stk.copy();
						current.stackSize -= 1;
						
						if(current.stackSize > 0)
							if(parent.addItemStackToSystem(current))
								stk.stackSize = 1;
					}else
					{
						if(ECUtils.canFilterAcceptItem(new InventoryMagicFilter(this.getStackInSlot(0)), stk, this.getStackInSlot(0)))	
						{
							ItemStack current = stk.copy();
							current.stackSize -= 1;
							
							if(current.stackSize > 0)
								if(parent.addItemStackToSystem(current))
									stk.stackSize = 1;
						}
					}
				}
			}
		}else
		{
			IInventory iinv = getConnectedInventoryInefficent();
			
			if(iinv.getSizeInventory() <= 0)
				return;
			
			for(int j = 0; j < iinv.getSizeInventory(); ++j)
			{
				ItemStack stk = iinv.getStackInSlot(j);
				if(stk != null)
				{
					if(this.getStackInSlot(0) == null || !(this.getStackInSlot(0).getItem() instanceof ItemFilter))
					{
						if(parent.addItemStackToSystem(stk))
							iinv.setInventorySlotContents(j, null);
					}else
					{
						if(ECUtils.canFilterAcceptItem(new InventoryMagicFilter(this.getStackInSlot(0)), stk, this.getStackInSlot(0)))	
						{
							if(parent.addItemStackToSystem(stk))
								iinv.setInventorySlotContents(j, null);
						}
					}
				}
			}
		}
	}
	
	

}
