package ec3.common.tile;

import net.minecraftforge.common.config.Configuration;
import DummyCore.Utils.DataStorage;
import DummyCore.Utils.DummyData;
import ec3.api.ApiCore;

public class TileMoonWell extends TileMRUGeneric{
	
	public static float cfgMaxMRU =  ApiCore.GENERATOR_MAX_MRU_GENERIC;
	public static float cfgBalance = 1F;
	public static float mruGenerated = 60;
	
	public TileMoonWell()
	{
		 super();
		this.maxMRU = (int)cfgMaxMRU;
	}
	
	public boolean canGenerateMRU()
	{
		int moonPhase = this.worldObj.provider.getMoonPhase(this.worldObj.getWorldTime());
		boolean night = !this.worldObj.isDaytime();
		return moonPhase != 4 && night && this.worldObj.canBlockSeeTheSky(xCoord, yCoord+1, zCoord);
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		this.balance = cfgBalance;
		if(!this.worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord))
		{
			int moonPhase = this.worldObj.provider.getMoonPhase(this.worldObj.getWorldTime());
			float moonFactor = 1.0F;
			switch(moonPhase)
			{
				case 0:
				{
					moonFactor = 1.0F;
					break;
				}
				case 1:
				{
					moonFactor = 0.75F;
					break;
				}
				case 7:
				{
					moonFactor = 0.75F;
					break;
				}
				case 2:
				{
					moonFactor = 0.5F;
					break;
				}
				case 6:
				{
					moonFactor = 0.5F;
					break;
				}
				case 3:
				{
					moonFactor = 0.25F;
					break;
				}
				case 5:
				{
					moonFactor = 0.25F;
					break;
				}
				case 4:
				{
					moonFactor = 0.0F;
					break;
				}
			}
			mruGenerated *= moonFactor;
			float heightFactor = 1.0F;
			if(yCoord > 80)
				heightFactor = 0F;
			else
			{
				heightFactor = 1.0F - (float)((float)yCoord/80F);
				mruGenerated *= heightFactor;
			}
			if(mruGenerated > 0 && canGenerateMRU() && !this.worldObj.isRemote)
			{
				this.setMRU((int) (this.getMRU()+mruGenerated));
				if(this.getMRU() > this.getMaxMRU())
					this.setMRU(this.getMaxMRU());
			}	
		}
	}
	
    public static void setupConfig(Configuration cfg)
    {
    	try
    	{
	    	cfg.load();
	    	String[] cfgArrayString = cfg.getStringList("MoonWellSettings", "tileentities", new String[]{
	    			"Max MRU:"+ApiCore.GENERATOR_MAX_MRU_GENERIC,
	    			"Default balance:1.0",
	    			"Max MRU generated per tick:60"
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
