package ec3.common.tile;

import java.util.List;

import DummyCore.Utils.Coord3D;
import DummyCore.Utils.DataStorage;
import DummyCore.Utils.DummyData;
import DummyCore.Utils.MathUtils;
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
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.ForgeDirection;
import ec3.api.ApiCore;
import ec3.api.IColdBlock;
import ec3.api.IHotBlock;
import ec3.common.entity.EntitySolarBeam;
import ec3.common.item.ItemsCore;
import ec3.common.mod.EssentialCraftCore;

public class TileHeatGenerator extends TileMRUGeneric{
	
	public int currentBurnTime, currentMaxBurnTime;
	public static float cfgMaxMRU =  ApiCore.GENERATOR_MAX_MRU_GENERIC;
	public static float cfgBalance = -1F;
	public static float mruGenerated = 20;
	
	public TileHeatGenerator()
	{
		 super();
		this.balance = cfgBalance;
		this.maxMRU = (int)cfgMaxMRU;
		this.setSlotsNum(2);
	}
	
	public boolean canGenerateMRU()
	{
		return false;
	}
	
	@Override
	public void updateEntity()
	{
		float mruGen = mruGenerated;
		super.updateEntity();
		if(this.balance == -1)
		{
			this.balance = this.worldObj.rand.nextFloat()*2;
		}
		if(!this.worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord))
		{
	        if (this.currentBurnTime > 0)
	        {
	            --this.currentBurnTime;
	            if(!this.worldObj.isRemote)
	            {
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
	            	
	            	mruGen*=mruFactor;
	            	if(mruGen >= 1)
	            	{
		    			this.setMRU((int) (this.getMRU()+mruGen));
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
		EssentialCraftCore.proxy.SmokeFX(xCoord+0.5F+MathUtils.randomFloat(this.worldObj.rand)*0.05F, yCoord+0.8F, zCoord+0.5F+MathUtils.randomFloat(this.worldObj.rand)*0.05F, 0, 0, 0, 1);
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
	
    public static void setupConfig(Configuration cfg)
    {
    	try
    	{
	    	cfg.load();
	    	String[] cfgArrayString = cfg.getStringList("HeatGeneratorSettings", "tileentities", new String[]{
	    			"Max MRU:"+ApiCore.GENERATOR_MAX_MRU_GENERIC,
	    			"Default balance(-1 is random):-1.0",
	    			"Max MRU generated per tick:20"
	    			},"");
	    	String dataString="";
	    	
	    	for(int i = 0; i < cfgArrayString.length; ++i)
	    		dataString+="||"+cfgArrayString[i];
	    	
	    	DummyData[] data = DataStorage.parseData(dataString);
	    	
	    	cfgMaxMRU = Float.parseFloat(data[0].fieldValue);
	    	cfgBalance = Float.parseFloat(data[1].fieldValue);
	    	mruGenerated = Float.parseFloat(data[2].fieldValue);
	    	
	    	cfg.save();
    	}catch(Exception e)
    	{
    		return;
    	}
    }

}
