package ec3.common.tile;

import java.util.UUID;

import DummyCore.Utils.MathUtils;
import DummyCore.Utils.MiscUtils;
import DummyCore.Utils.Notifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import ec3.api.ITERequiresMRU;
import ec3.utils.common.ECUtils;

public abstract class TileMRUGeneric extends TileEntity implements ITERequiresMRU, IInventory, ISidedInventory{

	public TileMRUGeneric()
	{
		super();
		tracker = new TileStatTracker(this);
	}
	
	public int syncTick;
	int mru;
	int maxMRU = 100;
	UUID uuid = UUID.randomUUID();
	float balance;
	public int innerRotation;
	private ItemStack[] items = new ItemStack[1];
	private TileStatTracker tracker;
	
	public static class TileStatTracker
	{
		public TileEntity trackedTile;
		public NBTTagCompound trackedTag;
		
		public TileStatTracker(TileEntity tracked)
		{
			trackedTile = tracked;
		}
		
		public boolean tileNeedsSyncing()
		{
			if(trackedTile == null) return false;
			NBTTagCompound currentTag = new NBTTagCompound();
			if(trackedTag == null)
			{
				trackedTag = new NBTTagCompound();
				trackedTile.writeToNBT(trackedTag);
				return true;
			}else
			{
				trackedTile.writeToNBT(currentTag);
				if(currentTag.equals(trackedTag))
				{
					trackedTile.writeToNBT(trackedTag);
					return false;
				}else
				{
					trackedTile.writeToNBT(trackedTag);
					return true;
				}
			}
		}
		
	}
	
	public abstract int[] getOutputSlots();
	
	public void setSlotsNum(int i)
	{
		items = new ItemStack[i];
	}
	
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
	
	public void updateEntity() 
	{
		++this.innerRotation;
		//Sending the sync packets to the CLIENT. 
		if(syncTick == 0)
		{
			if(this.tracker == null)
				Notifier.notifyCustomMod("EssentialCraft", "[WARNING][SEVERE]TileEntity "+this+" at pos "+this.xCoord+","+this.yCoord+","+this.zCoord+" tries to sync itself, but has no TileTracker attached to it! SEND THIS MESSAGE TO THE DEVELOPER OF THE MOD!");
			else
				if(!this.worldObj.isRemote && this.tracker.tileNeedsSyncing())
				{
					MiscUtils.sendPacketToAllAround(worldObj, getDescriptionPacket(), xCoord, yCoord, zCoord, this.worldObj.provider.dimensionId, 32);
				}
			syncTick = 60;
		}else
			--this.syncTick;
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
		return "ec3.container.generic";
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
			return true;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		if(this.getSizeInventory() > 0)
		{
			if(side == 1)
				return new int[]{0};
			if(side == 0)
				return getOutputSlots();
			else
			{
				int[] retInt;
				if(this.getSizeInventory()-(getOutputSlots().length + 1) > 0)
					retInt = new int[this.getSizeInventory()-(getOutputSlots().length + 1)];
				else
					retInt = new int[0];
				int cnt = 0;
				if(retInt.length > 0)
					for(int i = 0; i < this.getSizeInventory(); ++i)
					{
						if(i != 0 && !MathUtils.arrayContains(getOutputSlots(), i))
						{
							retInt[cnt] = i;
							++cnt;
						}
					}
				return retInt;
			}
		}
		else
			return new int[]{};
	}

	@Override
	public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_,
			int p_102007_3_) {
		return this.isItemValidForSlot(p_102007_1_, p_102007_2_);
	}

	@Override
	public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_,
			int p_102008_3_) {
		return true;
	}
}
