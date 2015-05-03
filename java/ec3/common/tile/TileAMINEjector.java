package ec3.common.tile;

import ec3.common.inventory.InventoryMagicFilter;
import ec3.utils.common.ECUtils;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public class TileAMINEjector extends TileMINEjector
{
	public int slotnum = 0;
	
	public boolean ejectItem(IInventory inv, int slotNum)
	{
		if(inv == null) return false;
		ItemStack ejected = inv.getStackInSlot(slotNum);
		if(ejected != null)
		{
			if(this.getStackInSlot(slotNum+64) == null)
			{
				ItemStack inserted = ejected.copy();inserted.stackSize = 1;
				this.setInventorySlotContents(slotNum+64, inserted);
				inv.decrStackSize(slotNum, 1);
				return true;
			}else
			{
				if(this.getStackInSlot(slotNum+64).stackSize + 1 <= this.getStackInSlot(slotNum+64).getMaxStackSize())
				{
					++this.getStackInSlot(slotNum+64).stackSize;
					inv.decrStackSize(slotNum, 1);
					return true;
				}
			}
		}
		return false;
	}
	
	public int findItemToEject(IInventory inv, int prevSlotnum)
	{
		if(inv != null)
			for(int i = prevSlotnum; i < inv.getSizeInventory() && i < 64; ++i)
			{
				ItemStack filter = this.getStackInSlot(i);
				ItemStack stk = inv.getStackInSlot(i);
				if(stk == null || filter == null)
					continue;
				if(ECUtils.canFilterAcceptItem(new InventoryMagicFilter(filter), stk, filter))
				{
					return i;
				}
			}
		return -1;
	}

	 public TileAMINEjector()
	 {
		 super();
		 this.setSlotsNum(128);
		 this.setMaxMRU(0);
	 }
	 
	@Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
    	super.readFromNBT(par1NBTTagCompound);
    	this.slotnum = par1NBTTagCompound.getInteger("slot");
    }
    
    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
    	super.writeToNBT(par1NBTTagCompound);
    	par1NBTTagCompound.setInteger("slot", slotnum);
    }
    
	public int[] getAccessibleSlotsFromSide(ForgeDirection side)
	{
		return null;
	}
    
    @Override
    public void updateEntity() 
    {
    	if(this.slotnum < 0) this.slotnum = 0;
    	super.updateEntity();
		if(!this.worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord))
			{
			
				IInventory inv = (IInventory) this.worldObj.getTileEntity(xCoord+this.getRotation().offsetX, yCoord+this.getRotation().offsetY, zCoord+this.getRotation().offsetZ);
				if(inv != null)
					for(int i = 0; i < inv.getSizeInventory(); ++i)
						if(this.findItemToEject(inv,i) != -1)
							this.ejectItem(inv, this.findItemToEject(inv,i));
			}
    }

	
}
