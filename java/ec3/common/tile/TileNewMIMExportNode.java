package ec3.common.tile;

import java.util.ArrayList;

import ec3.common.inventory.InventoryMagicFilter;
import ec3.common.item.ItemFilter;
import ec3.utils.common.ECUtils;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileNewMIMExportNode extends TileMRUGeneric{

	public TileNewMIMExportNode()
	{
		this.setMaxMRU(0);
		this.setSlotsNum(1);
	}
	
	public ForgeDirection getRotation()
	{
		int metadata = this.worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
		if(metadata > 5) metadata -= 6;
		return ForgeDirection.getOrientation(metadata);
	}
	
	@Override
	public int[] getOutputSlots() {
		return new int[]{0};
	}
	
	public ISidedInventory getConnectedInventory()
	{
		ForgeDirection side = getRotation();
		if(this.worldObj.getTileEntity(xCoord+side.offsetX, yCoord+side.offsetY, zCoord+side.offsetZ) != null)
		{
			TileEntity tile = this.worldObj.getTileEntity(xCoord+side.offsetX, yCoord+side.offsetY, zCoord+side.offsetZ);
			if(tile instanceof ISidedInventory)
				return (ISidedInventory) tile;
		}
		
		return null;
	}
	
	public IInventory getConnectedInventoryInefficent()
	{
		ForgeDirection side = getRotation();
		if(this.worldObj.getTileEntity(xCoord+side.offsetX, yCoord+side.offsetY, zCoord+side.offsetZ) != null)
		{
			TileEntity tile = this.worldObj.getTileEntity(xCoord+side.offsetX, yCoord+side.offsetY, zCoord+side.offsetZ);
			if(tile instanceof IInventory)
				return (IInventory) tile;
		}
		
		return null;
	}
	
	public int[] getAccessibleSlots()
	{
		return getConnectedInventory().getAccessibleSlotsFromSide(getRotation().getOpposite().ordinal());
	}
	
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
									if(copied.stackSize >= inv.getInventoryStackLimit())
										copied.stackSize = inv.getInventoryStackLimit();
									
									inv.setInventorySlotContents(slots[j], copied);
									parent.retrieveItemStackFromSystem(copied, false, true);
								}else
								{
									ItemStack copied = itemsToExport.get(i).copy();
									if(copied.stackSize >= inv.getInventoryStackLimit())
										copied.stackSize = inv.getInventoryStackLimit();
									
									if(inv.getStackInSlot(slots[j]).stackSize + copied.stackSize <= inv.getInventoryStackLimit() && inv.getStackInSlot(slots[j]).stackSize + copied.stackSize <= copied.getMaxStackSize())
									{
										inv.getStackInSlot(slots[j]).stackSize += copied.stackSize;
										parent.retrieveItemStackFromSystem(copied, false, true);
									}else
									{
										int reduceBy = copied.stackSize - inv.getStackInSlot(slots[j]).stackSize;
										if(reduceBy > 0)
										{
											copied.stackSize = reduceBy;
											inv.getStackInSlot(slots[j]).stackSize += reduceBy;
											parent.retrieveItemStackFromSystem(copied, false, true);
										}
									}
								}
							}else
							{
								ItemStack copied = itemsToExport.get(i).copy();
								if(ECUtils.canFilterAcceptItem(new InventoryMagicFilter(this.getStackInSlot(0)), copied, this.getStackInSlot(0)))
								{
									if(copied.stackSize >= inv.getInventoryStackLimit())
										copied.stackSize = inv.getInventoryStackLimit();
									
									if(inv.getStackInSlot(slots[j]) == null)
									{
										inv.setInventorySlotContents(slots[j], copied);
										parent.retrieveItemStackFromSystem(copied, false, true);
									}else
									{
										if(inv.getStackInSlot(slots[j]).stackSize + copied.stackSize <= inv.getInventoryStackLimit() && inv.getStackInSlot(slots[j]).stackSize + copied.stackSize <= copied.getMaxStackSize())
										{
											inv.getStackInSlot(slots[j]).stackSize += copied.stackSize;
											parent.retrieveItemStackFromSystem(copied, false, true);
										}else
										{
											int reduceBy = copied.stackSize - inv.getStackInSlot(slots[j]).stackSize;
											if(reduceBy > 0)
											{
												copied.stackSize = reduceBy;
												inv.getStackInSlot(slots[j]).stackSize += reduceBy;
												parent.retrieveItemStackFromSystem(copied, false, true);
											}
										}
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
									
									iinv.setInventorySlotContents(j, copied);
									parent.retrieveItemStackFromSystem(copied, false, true);
								}else
								{
									ItemStack copied = itemsToExport.get(i).copy();
									if(copied.stackSize >= iinv.getInventoryStackLimit())
										copied.stackSize = iinv.getInventoryStackLimit();
									
									if(iinv.getStackInSlot(j).stackSize + copied.stackSize <= iinv.getInventoryStackLimit() && iinv.getStackInSlot(j).stackSize + copied.stackSize <= copied.getMaxStackSize())
									{
										iinv.getStackInSlot(j).stackSize += copied.stackSize;
										parent.retrieveItemStackFromSystem(copied, false, true);
									}else
									{
										int reduceBy = copied.stackSize - iinv.getStackInSlot(j).stackSize;
										if(reduceBy > 0)
										{
											copied.stackSize = reduceBy;
											iinv.getStackInSlot(j).stackSize += reduceBy;
											parent.retrieveItemStackFromSystem(copied, false, true);
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
										iinv.setInventorySlotContents(j, copied);
										parent.retrieveItemStackFromSystem(copied, false, true);
									}else
									{
										if(iinv.getStackInSlot(j).stackSize + copied.stackSize <= iinv.getInventoryStackLimit() && iinv.getStackInSlot(j).stackSize + copied.stackSize <= copied.getMaxStackSize())
										{
											iinv.getStackInSlot(j).stackSize += copied.stackSize;
											parent.retrieveItemStackFromSystem(copied, false, true);
										}else
										{
											int reduceBy = copied.stackSize - iinv.getStackInSlot(j).stackSize;
											if(reduceBy > 0)
											{
												copied.stackSize = reduceBy;
												iinv.getStackInSlot(j).stackSize += reduceBy;
												parent.retrieveItemStackFromSystem(copied, false, true);
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
