package ec3.common.tile;

import ec3.common.inventory.InventoryMagicFilter;
import ec3.utils.common.ECUtils;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileMINInjector extends TileMRUGeneric
{
	
	public ForgeDirection getRotation()
	{
		int metadata = this.worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
		if(metadata > 5) metadata -= 6;
		return ForgeDirection.getOrientation(metadata);
	}
	
	public boolean injectItem(IInventory inv, int slotNum)
	{
		ItemStack injected = inv.getStackInSlot(slotNum);
		if(this.getStackInSlot(1) != null)
		{
			if(injected != null)
			{
				if(injected.stackSize+1 <= injected.getMaxStackSize())
				{
					injected.stackSize += 1;
					this.decrStackSize(1, 1);
					return true;
				}
			}else
			{
				ItemStack inserted = this.getStackInSlot(1).copy();inserted.stackSize = 1;
				inv.setInventorySlotContents(slotNum, inserted);
				this.decrStackSize(1, 1);
				return true;
			}
		}
		return false;
	}
	
	public int[] getAccessibleSlotsFromSide(ForgeDirection side)
	{
		if(this.worldObj.getTileEntity(xCoord+side.offsetX, yCoord+side.offsetY, zCoord+side.offsetZ) != null)
		{
			TileEntity tile = this.worldObj.getTileEntity(xCoord+side.offsetX, yCoord+side.offsetY, zCoord+side.offsetZ);
			if(tile instanceof ISidedInventory)
			{
				return ((ISidedInventory)tile).getAccessibleSlotsFromSide(side.getOpposite().ordinal());
			}else
				if(tile instanceof IInventory)
				{
					int[] ret = new int[((IInventory)tile).getSizeInventory()];
					for(int i = 0; i < ret.length; ++i)
						ret[i]= i;
					return ret;
				}
			
		}
		return null;
	}
	
	public int findItemToInject(IInventory inv)
	{
		if(this.getAccessibleSlotsFromSide(this.getRotation()) != null && this.getAccessibleSlotsFromSide(this.getRotation()).length > 0)
		{
			ItemStack filter = this.getStackInSlot(0);
			int[] slots = this.getAccessibleSlotsFromSide(this.getRotation());
			for(int i = 0; i < slots.length; ++i)
			{
				ItemStack stk = this.getStackInSlot(1);
				ItemStack in = inv.getStackInSlot(slots[i]);
				
				if(stk == null)
					continue;
				if(filter == null)
				{
					
					if(in == null || (in.isItemEqual(stk) && ItemStack.areItemStackTagsEqual(stk, in) && in.stackSize+1 <= in.getMaxStackSize()))
						return slots[i];
				}else
				{
					if(ECUtils.canFilterAcceptItem(new InventoryMagicFilter(filter), stk, filter) && (in==null || (in.stackSize+1 <= in.getMaxStackSize() && in.isItemEqual(stk) && ItemStack.areItemStackTagsEqual(in, stk))))
					{
						return slots[i];
					}
				}
			}
		}
		return -1;
	}

	 public TileMINInjector()
	 {
		 super();
		 this.setSlotsNum(2);
		 this.setMaxMRU(0);

	 }
	 
	@Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
    	super.readFromNBT(par1NBTTagCompound);
    }
    
    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
    	super.writeToNBT(par1NBTTagCompound);
    }
    
    @Override
    public void updateEntity() 
    {
    	super.updateEntity();
		if(!this.worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord))
			if(this.getAccessibleSlotsFromSide(this.getRotation()) != null)
			{
				IInventory inv = (IInventory) this.worldObj.getTileEntity(xCoord+this.getRotation().offsetX, yCoord+this.getRotation().offsetY, zCoord+this.getRotation().offsetZ);
				if(this.findItemToInject(inv) != -1)
					this.injectItem(inv, this.findItemToInject(inv));
			}
    }

	
}
