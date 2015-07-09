package ec3.common.tile;

import ec3.common.inventory.InventoryMagicFilter;
import ec3.common.item.ItemFilter;
import ec3.utils.common.ECUtils;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileNewMIMImportNode extends TileMRUGeneric{

	public TileNewMIMImportNode()
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
						if(parent.addItemStackToSystem(stk))
							inv.setInventorySlotContents(slots[j], null);
					}else
					{
						if(ECUtils.canFilterAcceptItem(new InventoryMagicFilter(this.getStackInSlot(0)), stk, this.getStackInSlot(0)))	
						{
							if(parent.addItemStackToSystem(stk))
								inv.setInventorySlotContents(slots[j], null);
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
