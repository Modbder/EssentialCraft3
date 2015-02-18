package ec3.common.tile;

import java.util.List;

import DummyCore.Utils.Coord3D;
import DummyCore.Utils.DataStorage;
import DummyCore.Utils.DummyData;
import DummyCore.Utils.DummyDataUtils;
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
import ec3.api.ApiCore;
import ec3.api.IColdBlock;
import ec3.api.IHotBlock;
import ec3.common.entity.EntitySolarBeam;
import ec3.common.item.ItemSoulStone;
import ec3.common.item.ItemsCore;

public class TileMatrixAbsorber extends TileMRUGeneric{
	
	public int sndTime;
	public static float cfgMaxMRU =  ApiCore.GENERATOR_MAX_MRU_GENERIC/10;
	public static float cfgBalance = 1F;
	public static float mruGenerated = 1;
	public static float mruUsage = 10;
	
	
	public TileMatrixAbsorber()
	{
		 super();
		this.balance = cfgBalance;
		this.maxMRU = (int)cfgMaxMRU;
		this.setSlotsNum(1);
	}
	
	public boolean canGenerateMRU()
	{
		return false;
	}
	
	@Override
	public void updateEntity()
	{
		this.balance = cfgBalance;
		super.updateEntity();
		boolean t = false;
		if(!this.worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord))
		{
			ItemStack stk = this.getStackInSlot(0);
			if(stk != null && stk.getItem() instanceof ItemSoulStone)
			{
		    	if(stk.getTagCompound() != null)
		    	{
		    		String username = stk.getTagCompound().getString("playerName");
		    		String currentEnergy = DummyDataUtils.getDataForPlayer(username, "essentialcraft", "ubmruEnergy");
					if(currentEnergy != null && !currentEnergy.isEmpty() && !currentEnergy.equals("no data") && !currentEnergy.equals("empty string") && !currentEnergy.equals("empty"))
					{
						if(this.getMRU() + mruGenerated <= this.getMaxMRU())
						{
							int current = Integer.parseInt(currentEnergy);
							if(current - mruUsage >= 0)
							{
								DummyDataUtils.setDataForPlayer(username, "essentialcraft", "ubmruEnergy", Integer.toString((int) (current-mruUsage)));
								this.setMRU((int) (this.getMRU()+mruGenerated));
					    		for(int o = 0; o < 10; ++o)
					    		{
					    			this.worldObj.spawnParticle("reddust", xCoord+0.25D+this.worldObj.rand.nextDouble()/2.2D, yCoord+0.25D+((float)o/20), zCoord+0.25D+this.worldObj.rand.nextDouble()/2.2D, 1.0D, 0.0D, 1.0D);
					    		}
					    		t = true;
							}
						}
					}
		    	}
			}
			--sndTime;
			if(t && sndTime <= 0)
			{
				sndTime = 20*20;
				this.worldObj.playSound(xCoord+0.5D, yCoord+0.5D, zCoord+0.5D, "essentialcraft:sound.deepnoise", 0.01F, 2F, false);
			}
			if(!t)
				sndTime = 0;
		}
	}
	
	@Override
    public void readFromNBT(NBTTagCompound i)
    {
		super.readFromNBT(i);
    }
	
	@Override
    public void writeToNBT(NBTTagCompound i)
    {
    	super.writeToNBT(i);
    }

    public static void setupConfig(Configuration cfg)
    {
    	try
    	{
	    	cfg.load();
	    	String[] cfgArrayString = cfg.getStringList("MatrixAbsorberSettings", "tileentities", new String[]{
	    			"Max MRU:"+ApiCore.GENERATOR_MAX_MRU_GENERIC/10,
	    			"Default balance:1.0",
	    			"MRU generated per tick:1",
	    			"UBMRU Used per tick:10"
	    			},"");
	    	String dataString="";
	    	
	    	for(int i = 0; i < cfgArrayString.length; ++i)
	    		dataString+="||"+cfgArrayString[i];
	    	
	    	DummyData[] data = DataStorage.parseData(dataString);
	    	
	    	cfgMaxMRU = Float.parseFloat(data[0].fieldValue);
	    	cfgBalance = Float.parseFloat(data[1].fieldValue);
	    	mruGenerated = Float.parseFloat(data[2].fieldValue);
	    	mruUsage = Float.parseFloat(data[3].fieldValue);
	    	
	    	cfg.save();
    	}catch(Exception e)
    	{
    		return;
    	}
    }
}
