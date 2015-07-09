package ec3.common.tile;

import java.util.UUID;

import DummyCore.Utils.MiscUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import ec3.api.ITETransfersMRU;
import ec3.common.item.ItemBoundGem;
import ec3.utils.common.ECUtils;

public class TileRayTower extends TileEntity implements IInventory, ITETransfersMRU{
	public int syncTick;
	int mru;
	int maxMRU = 100;
	UUID uuid = UUID.randomUUID();
	float balance;
	public int innerRotation;
	public ItemStack[] items = new ItemStack[1];
	
	@Override
    public void readFromNBT(NBTTagCompound i)
    {
		super.readFromNBT(i);
		ECUtils.loadMRUState(this, i);
		MiscUtils.loadInventory(this, i);
    }
	
	@Override
    public void writeToNBT(NBTTagCompound i)
    {
    	super.writeToNBT(i);
    	ECUtils.saveMRUState(this, i);
    	MiscUtils.saveInventory(this, i);
    }
	
	@Override
	public void updateEntity() 
	{
		++this.innerRotation;
		//Sending the sync packets to the CLIENT. 
		if(syncTick == 0)
		{
			if(!this.worldObj.isRemote)
				MiscUtils.sendPacketToAllAround(worldObj, getDescriptionPacket(), xCoord, yCoord, zCoord, this.worldObj.provider.dimensionId, 16);
			syncTick = 10;
		}else
			--this.syncTick;
		ECUtils.manage(this, 0);
	}
	
	@Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.writeToNBT(nbttagcompound);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, -10, nbttagcompound);
    }
	
	@Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
		if(net.getNetHandler() instanceof INetHandlerPlayClient)
			if(pkt.func_148853_f() == -10)
				this.readFromNBT(pkt.func_148857_g());
    }
	
	@Override
	public int getMRU() {
		return mru;
	}

	@Override
	public int getMaxMRU() {
		return maxMRU;
	}

	@Override
	public boolean setMRU(int i) {
		mru = i;
		return true;
	}

	@Override
	public float getBalance() {
		return balance;
	}

	@Override
	public boolean setBalance(float f) {
		balance = f;
		return true;
	}

	@Override
	public boolean setMaxMRU(float f) {
		maxMRU = (int) f;
		return true;
	}

	@Override
	public UUID getUUID() {
		return uuid;
	}

	@Override
	public int getSizeInventory() {
		return this.items.length;
	}

	@Override
	public ItemStack getStackInSlot(int par1) {
		return this.items[par1];
	}

	@Override
	public ItemStack decrStackSize(int par1, int par2) {
        if (this.items[par1] != null)
        {
            ItemStack itemstack;

            if (this.items[par1].stackSize <= par2)
            {
                itemstack = this.items[par1];
                this.items[par1] = null;
                return itemstack;
            }
            else
            {
                itemstack = this.items[par1].splitStack(par2);

                if (this.items[par1].stackSize == 0)
                {
                    this.items[par1] = null;
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
    public ItemStack getStackInSlotOnClosing(int par1)
    {
        if (this.items[par1] != null)
        {
            ItemStack itemstack = this.items[par1];
            this.items[par1] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
    }


    @Override
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
    {
        this.items[par1] = par2ItemStack;

        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
        {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }
    }
    

	@Override
	public String getInventoryName() {
		return "ec3.container.rayTower";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.worldObj.getTileEntity(xCoord, yCoord, zCoord) == this && player.dimension == this.worldObj.provider.dimensionId;
	}

	@Override
	public void openInventory() {
		
	}

	@Override
	public void closeInventory() {
		
	}

	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
		if(p_94041_2_.getItem() instanceof ItemBoundGem)
			return true;
		return false;
	}

}
