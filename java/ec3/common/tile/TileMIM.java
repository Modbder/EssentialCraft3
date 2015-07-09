package ec3.common.tile;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import DummyCore.Utils.MiscUtils;
import ec3.api.ApiCore;
import ec3.common.inventory.InventoryMagicFilter;
import ec3.common.item.ItemBoundGem;
import ec3.common.item.ItemFilter;
import ec3.utils.common.ECUtils;

public class TileMIM extends TileMRUGeneric{
	
	public ItemStack[] innerStorage = new ItemStack[256];
	
    public void setInventorySlotContentsInner(int par1, ItemStack par2ItemStack)
    {
        this.innerStorage[par1] = par2ItemStack;

        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
        {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }
    }
    
	public ItemStack getStackInSlotInner(int par1) {
		// TODO Auto-generated method stub
		return this.innerStorage[par1];
	}

	public ItemStack decrStackSizeInner(int par1, int par2) {
        if (this.innerStorage[par1] != null)
        {
            ItemStack itemstack;

            if (this.innerStorage[par1].stackSize <= par2)
            {
                itemstack = this.innerStorage[par1];
                this.innerStorage[par1] = null;
                return itemstack;
            }
            else
            {
                itemstack = this.innerStorage[par1].splitStack(par2);

                if (this.innerStorage[par1].stackSize == 0)
                {
                    this.innerStorage[par1] = null;
                }

                return itemstack;
            }
        }
        else
        {
            return null;
        }
	}
	
	@Override
    public void readFromNBT(NBTTagCompound loadTag)
    {
		super.readFromNBT(loadTag);
		for(int i = 0; i < 256; ++i)
		{
			this.setInventorySlotContentsInner(i, null);
		}
        NBTTagList nbttaglist = loadTag.getTagList("ItemsInner", 10);
        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");

            if (b0 >= 0 && b0 < 256)
            {
            	this.setInventorySlotContentsInner(b0, ItemStack.loadItemStackFromNBT(nbttagcompound1));
            }
        }
    }
	
	@Override
    public void writeToNBT(NBTTagCompound tag)
    {
    	super.writeToNBT(tag);
        NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < 256; ++i)
        {
            if (this.getStackInSlotInner(i) != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                this.getStackInSlotInner(i).writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }
        tag.setTag("ItemsInner", nbttaglist);
    }
	
	
	public TileMIM()
	{
		 super();
		this.maxMRU = (int) ApiCore.DEVICE_MAX_MRU_GENERIC;
		this.setSlotsNum(1+(6*9));
	}
	
	@Override
	public void updateEntity()
	{
		if(!this.worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord))
		{
			if(!this.worldObj.isRemote)
			for(int i = 1; i < this.getSlots(); ++i)
			{
				ItemStack boundGem = this.getStackInSlot(i);
				if(boundGem != null)
				{
					if(boundGem.getItem() instanceof ItemBoundGem)
					{
						int[] coords = ItemBoundGem.getCoords(boundGem);
						if(coords != null && coords.length == 3)
						{
							TileEntity tile = this.worldObj.getTileEntity(coords[0], coords[1], coords[2]);
							//System.out.println(tile);
							if(tile != null && this.getMRU() - 3 > 0 && !this.worldObj.isBlockIndirectlyGettingPowered(coords[0], coords[1], coords[2]))
							{
								this.setMRU(this.getMRU()-3);
								this.action(tile);
							}
						}
					}
				}
			}
		}
		++this.innerRotation;
		//Sending the sync packets to the CLIENT. 
		if(syncTick == 0)
		{
			if(!this.worldObj.isRemote)
			{
				MiscUtils.sendPacketToAllAround(worldObj, getDescriptionPacket(), xCoord, yCoord, zCoord, this.worldObj.provider.dimensionId, 128);
			}
			syncTick = 50;
		}else
			--this.syncTick;
		
		ECUtils.manage(this, 0);
		
	}
	
	public void action(TileEntity tile)
	{
		if(tile instanceof TileAMINEjector)
		{
			TileAMINEjector e = (TileAMINEjector) tile;
			for(int j = 64; j < 128; ++j)
			{
				ItemStack is = e.getStackInSlot(j);
				if(is != null && this.findSlotForItemStack(is) != -1)
				{
					int i = this.findSlotForItemStack(is);
					if(this.getStackInSlotInner(i) == null)
					{
						ItemStack set = is.copy();set.stackSize=1;
						this.setInventorySlotContentsInner(i, set);
						e.decrStackSize(j, 1);
						ECUtils.spawnItemFX(e, this);
					}else
					{
						if(this.getStackInSlotInner(i).stackSize + 1 <= this.getStackInSlotInner(i).getMaxStackSize())
						{
							++this.getStackInSlotInner(i).stackSize;
							e.decrStackSize(j, 1);
							ECUtils.spawnItemFX(e, this);
						}
					}
				}
			}
		}else
		{
			if(tile instanceof TileMINEjector)
			{
				TileMINEjector e = (TileMINEjector) tile;
				ItemStack is = e.getStackInSlot(1);
				if(is != null && this.findSlotForItemStack(is) != -1)
				{
					int i = this.findSlotForItemStack(is);
					if(this.getStackInSlotInner(i) == null)
					{
						ItemStack set = is.copy();set.stackSize=1;
						this.setInventorySlotContentsInner(i, set);
						e.decrStackSize(1, 1);
						ECUtils.spawnItemFX(e, this);
					}else
					{
						if(this.getStackInSlotInner(i).stackSize + 1 <= this.getStackInSlotInner(i).getMaxStackSize())
						{
							++this.getStackInSlotInner(i).stackSize;
							e.decrStackSize(1, 1);
							ECUtils.spawnItemFX(e, this);
						}
					}
				}
			}
		}
		if(tile instanceof TileAMINInjector)
		{
			TileAMINInjector e = (TileAMINInjector) tile;
			Filter:for(int i = 0; i < e.getAvaiableSlotsNum(); ++i)
			{
				ItemStack filter = e.getStackInSlot(i);
				if(filter != null)
				{
					InventoryMagicFilter filterInventory = new InventoryMagicFilter(filter);
					for(int j = 0; j < getSlotsInner(); ++j)
					{
						ItemStack is = this.getStackInSlotInner(j);
						if(is != null)
						{
							if(ECUtils.canFilterAcceptItem(filterInventory, is, filter))
							{
								if(e.getStackInSlot(i+64) == null)
								{
									ItemStack set = is.copy();set.stackSize=1;
									e.setInventorySlotContents(i+64, set);
									this.decrStackSizeInner(j, 1);
									ECUtils.spawnItemFX(this, e);
								}else
								{
									if(e.getStackInSlot(i+64).stackSize+1 <= e.getStackInSlot(i+64).getMaxStackSize())
									{
										++e.getStackInSlot(i+64).stackSize;
										this.decrStackSizeInner(j, 1);
										ECUtils.spawnItemFX(this, e);
									}
								}
								continue Filter;
							}
						}
					}
				}
			}
		}else
		{
			if(tile instanceof TileMINInjector)
			{
				TileMINInjector e = (TileMINInjector) tile;
				for(int j = 0; j < 256; ++j)
				{
					ItemStack is = this.getStackInSlotInner(j);
					if(is != null)
					{
						if(e.getStackInSlot(0) == null || (e.getStackInSlot(0).getItem() instanceof ItemFilter && ECUtils.canFilterAcceptItem(new InventoryMagicFilter(e.getStackInSlot(0)), is, e.getStackInSlot(0))))
						{
							if(e.getStackInSlot(1) == null)
							{
								ItemStack set = is.copy();set.stackSize=1;
								e.setInventorySlotContents(1, set);
								this.decrStackSizeInner(j, 1);
								ECUtils.spawnItemFX(this, e);
							}else
							{
								if(e.getStackInSlot(1).stackSize+1 <= e.getStackInSlot(1).getMaxStackSize())
								{
									++e.getStackInSlot(1).stackSize;
									this.decrStackSizeInner(j, 1);
									ECUtils.spawnItemFX(this, e);
								}
							}
							break;
						}
					}
				}
			}
		}
	}
	
	public int getSlotsInner()
	{
		int retInt = 0;
		for(int i = 0; i < 256; ++i)
		{
			if(this.getStackInSlotInner(i) != null)
				++retInt;
			else
			{
				if(i < 255)
				{
					if(this.getStackInSlot(i+1) == null)
						return retInt;
				}else
					return retInt;
			}
		}
		return retInt;
	}
	
	public int getSlots()
	{
		int retInt = 1;
		for(int i = 1; i < 9*6+1; ++i)
		{
			if(this.getStackInSlot(i) != null)
				++retInt;
			else
			{
				return retInt;
			}
		}
		return retInt;
	}
	
	public int findSlotForItemStack(ItemStack stk)
	{
		for(int i = 0; i < 256; ++i)
		{
			ItemStack is = this.getStackInSlotInner(i);
			if(is != null && stk.isItemEqual(is) && ItemStack.areItemStackTagsEqual(stk, is) && is.stackSize + 1 <= is.getMaxStackSize())return i;
		}
		for(int i = 0; i < 256; ++i)
		{
			ItemStack is = this.getStackInSlotInner(i);
			if(is == null) return i;
		}
		return -1;
	}

	@Override
	public int[] getOutputSlots() {
		return new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54};
	}
}
