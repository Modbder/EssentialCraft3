package ec3.common.tile;

import ec3.api.ApiCore;

public class TileMoonWell extends TileMRUGeneric{
	
	public TileMoonWell()
	{
		this.maxMRU = (int) ApiCore.GENERATOR_MAX_MRU_GENERIC;
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
		this.balance = 1.0F;
		float mruGenerated = 60F;
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
