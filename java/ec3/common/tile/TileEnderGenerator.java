package ec3.common.tile;

import java.util.List;

import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import ec3.api.ApiCore;
import ec3.common.entity.EntitySolarBeam;

public class TileEnderGenerator extends TileMRUGeneric{
	
	public TileEnderGenerator()
	{
		this.maxMRU = (int) ApiCore.GENERATOR_MAX_MRU_GENERIC;
		this.balance = -1F;
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
		List<EntityEnderman> l = this.worldObj.getEntitiesWithinAABB(EntityEnderman.class, AxisAlignedBB.getBoundingBox(xCoord-8, yCoord-8, zCoord-8, xCoord+8, yCoord+8, zCoord+8));
		if(!l.isEmpty())
		{
				for(int i = 0; i < l.size(); ++i)
				{
					l.get(i).setPositionAndRotation(xCoord+0.5D, yCoord+1D, zCoord+0.5D, 0, 0);
				}
		}
		List<EntityEnderman> l1 = this.worldObj.getEntitiesWithinAABB(EntityEnderman.class, AxisAlignedBB.getBoundingBox(xCoord-2, yCoord-2, zCoord-2, xCoord+2, yCoord+2, zCoord+2));
		if(!l1.isEmpty())
		{
			if(!this.worldObj.isRemote)
			{
				for(int i = 0; i < l1.size(); ++i)
				{
					if(l1.get(i).attackEntityFrom(DamageSource.magic, 1))
					{
						int mruGenerated = 500;
						this.setMRU((int) (this.getMRU()+mruGenerated));
						if(this.getMRU() > this.getMaxMRU())
							this.setMRU(this.getMaxMRU());
					}
				}
			}
		}
	}

}
