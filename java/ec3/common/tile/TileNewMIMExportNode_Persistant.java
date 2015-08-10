package ec3.common.tile;

import java.util.ArrayList;

import ec3.common.inventory.InventoryMagicFilter;
import ec3.common.item.ItemFilter;
import ec3.utils.common.ECUtils;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;

public class TileNewMIMExportNode_Persistant extends TileNewMIMExportNode{

	public TileNewMIMExportNode_Persistant()
	{
		super();
	}
	
	@Override
	public void exportAllPossibleItems(TileNewMIM parent)
	{
		if(this.worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord))
			return;
		
		ISidedInventory inv = getConnectedInventory();
		if(inv != null)
		{
			ArrayList<ItemStack> itemsToExport = parent.getAllItems();
			int[] slots = getAccessibleSlots();
			
			if(slots.length <= 0)
				return;
			
			for(int i = 0; i < itemsToExport.size(); ++i)
			{
				for(int j = 0; j < slots.length; ++j)
				{
					if(inv.canInsertItem(slots[j], itemsToExport.get(i), getRotation().getOpposite().ordinal()))
					{
						if(inv.getStackInSlot(slots[j]) == null || (inv.getStackInSlot(slots[j]).isItemEqual(itemsToExport.get(i)) && ItemStack.areItemStackTagsEqual(inv.getStackInSlot(slots[j]), itemsToExport.get(i))))
						{
							if(this.getStackInSlot(0) == null || !(this.getStackInSlot(0).getItem() instanceof ItemFilter))
							{
								if(inv.getStackInSlot(slots[j]) == null)
								{
									ItemStack copied = itemsToExport.get(i).copy();
									if(copied.stackSize >= 2)
										copied.stackSize = 1;
									
									if(parent.retrieveItemStackFromSystem(copied, false, true) == 0)
										inv.setInventorySlotContents(slots[j], copied);
								}
							}else
							{
								ItemStack copied = itemsToExport.get(i).copy();
								if(ECUtils.canFilterAcceptItem(new InventoryMagicFilter(this.getStackInSlot(0)), copied, this.getStackInSlot(0)))
								{
									if(copied.stackSize >= 2)
										copied.stackSize = 2;
									
									if(inv.getStackInSlot(slots[j]) == null)
									{
										if(parent.retrieveItemStackFromSystem(copied, false, true) == 0)
											inv.setInventorySlotContents(slots[j], copied);
									}
								}
							}
						}
					}
				}
			}
		}else
		{
			IInventory iinv = getConnectedInventoryInefficent();
			
			ArrayList<ItemStack> itemsToExport = parent.getAllItems();
			
			if(iinv.getSizeInventory() <= 0)
				return;
			
			for(int i = 0; i < itemsToExport.size(); ++i)
			{
				for(int j = 0; j < iinv.getSizeInventory(); ++j)
				{
					if(iinv.isItemValidForSlot(j, itemsToExport.get(i)))
					{
						if(iinv.getStackInSlot(j) == null || (iinv.getStackInSlot(j).isItemEqual(itemsToExport.get(i)) && ItemStack.areItemStackTagsEqual(iinv.getStackInSlot(j), itemsToExport.get(i))))
						{
							if(this.getStackInSlot(0) == null || !(this.getStackInSlot(0).getItem() instanceof ItemFilter))
							{
								if(iinv.getStackInSlot(j) == null)
								{
									ItemStack copied = itemsToExport.get(i).copy();
									if(copied.stackSize >= iinv.getInventoryStackLimit())
										copied.stackSize = iinv.getInventoryStackLimit();
									
									if(parent.retrieveItemStackFromSystem(copied, false, true) == 0)
										iinv.setInventorySlotContents(j, copied);
								}else
								{
									ItemStack copied = itemsToExport.get(i).copy();
									if(copied.stackSize >= iinv.getInventoryStackLimit())
										copied.stackSize = iinv.getInventoryStackLimit();
									
									if(iinv.getStackInSlot(j).stackSize + copied.stackSize <= iinv.getInventoryStackLimit() && iinv.getStackInSlot(j).stackSize + copied.stackSize <= copied.getMaxStackSize())
									{
										if(parent.retrieveItemStackFromSystem(copied, false, true) == 0)
											iinv.getStackInSlot(j).stackSize += copied.stackSize;
									}else
									{
										int reduceBy = copied.stackSize - iinv.getStackInSlot(j).stackSize;
										if(reduceBy > 0)
										{
											copied.stackSize = reduceBy;
											if(parent.retrieveItemStackFromSystem(copied, false, true) == 0)
												iinv.getStackInSlot(j).stackSize += reduceBy;
										}
									}
								}
							}else
							{
								ItemStack copied = itemsToExport.get(i).copy();
								if(ECUtils.canFilterAcceptItem(new InventoryMagicFilter(this.getStackInSlot(0)), copied, this.getStackInSlot(0)))
								{
									if(copied.stackSize >= iinv.getInventoryStackLimit())
										copied.stackSize = iinv.getInventoryStackLimit();
									
									if(iinv.getStackInSlot(j) == null)
									{
										if(parent.retrieveItemStackFromSystem(copied, false, true) == 0)
											iinv.setInventorySlotContents(j, copied);
									}else
									{
										if(iinv.getStackInSlot(j).stackSize + copied.stackSize <= iinv.getInventoryStackLimit() && iinv.getStackInSlot(j).stackSize + copied.stackSize <= copied.getMaxStackSize())
										{
											if(parent.retrieveItemStackFromSystem(copied, false, true) == 0)
												iinv.getStackInSlot(j).stackSize += copied.stackSize;
										}else
										{
											int reduceBy = copied.stackSize - iinv.getStackInSlot(j).stackSize;
											if(reduceBy > 0)
											{
												copied.stackSize = reduceBy;
												if(parent.retrieveItemStackFromSystem(copied, false, true) == 0)
													iinv.getStackInSlot(j).stackSize += reduceBy;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	

}
