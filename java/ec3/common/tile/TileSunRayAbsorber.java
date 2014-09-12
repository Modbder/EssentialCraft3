package ec3.common.tile;

import java.util.List;

import net.minecraft.util.AxisAlignedBB;
import ec3.api.ApiCore;
import ec3.common.entity.EntitySolarBeam;

public class TileSunRayAbsorber extends TileMRUGeneric{
	
	public TileSunRayAbsorber()
	{
		this.maxMRU = (int) ApiCore.GENERATOR_MAX_MRU_GENERIC*10;
	}
	
	public boolean canGenerateMRU()
	{
		return false;
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		this.balance = 2.0F;
		List<EntitySolarBeam> l = this.worldObj.getEntitiesWithinAABB(EntitySolarBeam.class, AxisAlignedBB.getBoundingBox(xCoord-1, yCoord-1, zCoord-1, xCoord+1, yCoord+1, zCoord+1));
		if(!l.isEmpty())
		{
			if(!this.worldObj.isRemote)
			{
				int mruGenerated = 500;
				this.setMRU((int) (this.getMRU()+mruGenerated));
				if(this.getMRU() > this.getMaxMRU())
					this.setMRU(this.getMaxMRU());
			}
		}
	}

}
