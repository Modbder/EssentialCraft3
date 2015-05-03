package ec3.common.tile;

import DummyCore.Utils.MathUtils;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.common.util.ForgeDirection;
import ec3.api.ApiCore;
import ec3.api.IHotBlock;
import ec3.common.item.ItemsCore;
import ec3.common.mod.EssentialCraftCore;

public class TileUltraHeatGenerator extends TileMRUGeneric{
	
	public int currentBurnTime, currentMaxBurnTime;
	public float heat;
	
	public TileUltraHeatGenerator()
	{
		 super();
		this.balance = -1;
		this.maxMRU = (int) ApiCore.GENERATOR_MAX_MRU_GENERIC*10;
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
		if(!this.worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord))
		{
	        if (this.currentBurnTime > 0)
	        {
	           
	            if(!this.worldObj.isRemote)
	            {
	            	float mruGenerated = 20;
	            	float mruFactor = 1.0F;
	            	Block[] b = new Block[4];
	            	b[0] = this.worldObj.getBlock(xCoord+2, yCoord, zCoord);
	            	b[1] = this.worldObj.getBlock(xCoord-2, yCoord, zCoord);
	            	b[2] = this.worldObj.getBlock(xCoord, yCoord, zCoord+2);
	            	b[3] = this.worldObj.getBlock(xCoord, yCoord, zCoord-2);
	            	int[] ox = new int[]{2,-2,0,0};
	            	int[] oz = new int[]{0,0,2,-2};
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
	            			mruFactor*=(((IHotBlock)b[i]).getHeatModifier(getWorldObj(), xCoord+ox[i], yCoord, zCoord+oz[i]));
	            		}else
	            		{
	            			mruFactor*=0.5F;
	            		}
	            		
	            	}
	            	
	            	float scaledHeatFactor = 0F;
	            	if(this.heat < 1000)
	            	{
	            		scaledHeatFactor = 0.1F + this.heat/1000F;
	            		 this.currentBurnTime -= 2.5F/scaledHeatFactor;
		            }else
		            	if(this.heat > 10000)
		            	{
		            		scaledHeatFactor = 0.001F + 10000/this.heat;
		            		this.currentBurnTime -= 1F*scaledHeatFactor;
		            	}else
		            	{
		            		scaledHeatFactor = 1F;
		            		 --this.currentBurnTime;
		            	}
	            	this.heat += (mruFactor*scaledHeatFactor);
	            	if(heat < 1000)
	            		mruGenerated = heat/100;
	            	else
	            		if(heat > 10000)
	            			mruGenerated = 80+heat/1000;
	            		else
	            			mruGenerated = heat/124;
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
	                if (this.currentBurnTime <= 0 && this.getMRU() < this.getMaxMRU())
	                {
	                    this.currentMaxBurnTime = this.currentBurnTime = TileEntityFurnace.getItemBurnTime(this.getStackInSlot(0));
	
	                    if (this.currentBurnTime > 0)
	                    {
	                        if (this.getStackInSlot(0) != null)
	                        {
	                           
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
	                            this.decrStackSize(0, 1);
	                        }
	                    }
	                }
	            }
	        }else
	        {
	        	if(this.heat > 0)
	        	{
	        		this.worldObj.spawnParticle("flame", xCoord+0.5F, yCoord+0.5F, zCoord+0.5F, 0, 0.1f, 0);
	        		for(int i = 0; i < 4; ++i)
	        		{
	        			if(i == 0)
	        				this.worldObj.spawnParticle("flame", xCoord+0.05D, yCoord+1.2F, zCoord+0.05D, 0, 0.01f, 0);
	        			if(i == 1)
	        				this.worldObj.spawnParticle("flame", xCoord+0.95D, yCoord+1.2F, zCoord+0.05D, 0, 0.01f, 0);
	        			if(i == 2)
	        				this.worldObj.spawnParticle("flame", xCoord+0.05D, yCoord+1.2F, zCoord+0.95D, 0, 0.01f, 0);
	        			if(i == 3)
	        				this.worldObj.spawnParticle("flame", xCoord+0.95D, yCoord+1.2F, zCoord+0.95D, 0, 0.01f, 0);
	        		}
	        	}
	        }
		}
		for(int i = 2; i < 6; ++i)
		{
			ForgeDirection rotation = ForgeDirection.VALID_DIRECTIONS[i];
			float rotXAdv = rotation.offsetX-0.5F;
			float rotZAdv = rotation.offsetZ-0.5F;
			EssentialCraftCore.proxy.FlameFX(xCoord+0.725F+rotXAdv/2.2F, yCoord+0.4F, zCoord+0.725F+rotZAdv/2.2F, 0, 0F, 0, 0.8D, 0.5D, 0.5F, 0.5F);
			EssentialCraftCore.proxy.FlameFX(xCoord+0.5F+MathUtils.randomFloat(this.worldObj.rand)*0.2F, yCoord+0.65F, zCoord+0.5F+MathUtils.randomFloat(this.worldObj.rand)*0.2F, 0, 0.01F, 0, 0.8D, 0.5D, 0.5F, 1F);
		}
	}
	
	@Override
    public void readFromNBT(NBTTagCompound i)
    {
		currentBurnTime = i.getInteger("burn");
		currentMaxBurnTime = i.getInteger("burnMax");
		heat = i.getFloat("heat");
		super.readFromNBT(i);
    }
	
	@Override
    public void writeToNBT(NBTTagCompound i)
    {
		i.setInteger("burn", currentBurnTime);
		i.setInteger("burnMax", currentMaxBurnTime);
		i.setFloat("heat", heat);
    	super.writeToNBT(i);
    }

}
