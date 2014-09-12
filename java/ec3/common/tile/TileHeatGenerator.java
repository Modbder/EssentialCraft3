package ec3.common.tile;

import java.util.List;

import DummyCore.Utils.Coord3D;
import DummyCore.Utils.DataStorage;
import DummyCore.Utils.DummyData;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import ec3.api.ApiCore;
import ec3.api.IColdBlock;
import ec3.api.IHotBlock;
import ec3.common.entity.EntitySolarBeam;
import ec3.common.item.ItemsCore;

public class TileHeatGenerator extends TileMRUGeneric{
	
	public int currentBurnTime, currentMaxBurnTime;
	
	public TileHeatGenerator()
	{
		this.balance = -1;
		this.maxMRU = (int) ApiCore.GENERATOR_MAX_MRU_GENERIC;
		this.setSlotsNum(2);
	}
	
	public boolean canGenerateMRU()
	{
		return false;
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		if(this.balance == -1)
		{
			this.balance = this.worldObj.rand.nextFloat()*2;
		}
        if (this.currentBurnTime > 0)
        {
            --this.currentBurnTime;
            if(!this.worldObj.isRemote)
            {
            	float mruGenerated = 20;
            	float mruFactor = 1.0F;
            	Block[] b = new Block[4];
            	b[0] = this.worldObj.getBlock(xCoord+2, yCoord, zCoord);
            	b[1] = this.worldObj.getBlock(xCoord-2, yCoord, zCoord);
            	b[2] = this.worldObj.getBlock(xCoord, yCoord, zCoord+2);
            	b[3] = this.worldObj.getBlock(xCoord, yCoord, zCoord-2);
            	for(int i = 0; i < 4; ++i)
            	{
            		if(b[i] == Blocks.air)
            		{
            			mruFactor*=0;
            		}else if(b[i] == Blocks.netherrack)
            		{
            			mruFactor*=0.75F;
            		}else if(b[i] == Blocks.lava)
            		{
            			mruFactor*=0.95F;
            		}else if(b[i] == Blocks.fire)
            		{
            			mruFactor*=0.7F;
            		}else if(b[i] instanceof IHotBlock)
            		{
            			mruFactor*=(((IHotBlock)b[i]).getHeatModifier());
            		}else
            		{
            			mruFactor*=0.5F;
            		}
            		
            	}
            	
            	mruGenerated*=mruFactor;
            	if(mruGenerated >= 1)
            	{
	    			this.setMRU((int) (this.getMRU()+mruGenerated));
	    			if(this.getMRU() > this.getMaxMRU())
	    				this.setMRU(this.getMaxMRU());
            	}
            }
        }

        if (!this.worldObj.isRemote)
        {
            if (this.getStackInSlot(0) != null)
            {
                if (this.currentBurnTime == 0 && this.getMRU() < this.getMaxMRU())
                {
                    this.currentMaxBurnTime = this.currentBurnTime = TileEntityFurnace.getItemBurnTime(this.getStackInSlot(0));

                    if (this.currentBurnTime > 0)
                    {
                        if (this.getStackInSlot(0) != null)
                        {
                           this.decrStackSize(0, 1);
                           if(this.getStackInSlot(1) == null || this.getStackInSlot(1).stackSize < this.getInventoryStackLimit())
                           {
                        	   if(this.getStackInSlot(1) != null && this.getStackInSlot(1).getItem() == ItemsCore.magicalSlag)
                        	   {
                        		   ItemStack stk = this.getStackInSlot(1);
                        		   ++stk.stackSize;
                        		   this.setInventorySlotContents(1, stk);
                        	   }
                        	   if(this.getStackInSlot(1) == null)
                        	   {
                           		   ItemStack stk = new ItemStack(ItemsCore.magicalSlag,1,0);
                        		   this.setInventorySlotContents(1, stk);
                        	   }
                           }
                            if(this.getStackInSlot(0).stackSize == 0)
                            {
                                this.setInventorySlotContents(0, this.getStackInSlot(0).getItem().getContainerItem(this.getStackInSlot(0)));
                            }
                        }
                    }
                }
            }
        }
	}
	
	@Override
    public void readFromNBT(NBTTagCompound i)
    {
		currentBurnTime = i.getInteger("burn");
		currentMaxBurnTime = i.getInteger("burnMax");
		super.readFromNBT(i);
    }
	
	@Override
    public void writeToNBT(NBTTagCompound i)
    {
		i.setInteger("burn", currentBurnTime);
		i.setInteger("burnMax", currentMaxBurnTime);
    	super.writeToNBT(i);
    }

}
