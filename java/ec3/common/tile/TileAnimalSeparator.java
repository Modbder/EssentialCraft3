package ec3.common.tile;

import java.util.List;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.monster.IMob;
import net.minecraft.util.AxisAlignedBB;
import ec3.api.ApiCore;
import ec3.utils.common.ECUtils;

public class TileAnimalSeparator extends TileMRUGeneric{

	public TileAnimalSeparator()
	{
		this.setSlotsNum(1);
		this.setMaxMRU(ApiCore.DEVICE_MAX_MRU_GENERIC);
	}
	
	@Override
	public int[] getOutputSlots() {
		return new int[0];
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		ECUtils.manage(this, 0);
	}
	
	@SuppressWarnings("unchecked")
	public void separate(boolean b)
	{
		AxisAlignedBB toTeleport = AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord+1, yCoord+1, zCoord+1).expand(24, 24, 24);
		AxisAlignedBB noTeleport = AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord+1, yCoord+1, zCoord+1).expand(5, 5, 5);
		List<EntityAgeable> tp = this.worldObj.getEntitiesWithinAABB(EntityAgeable.class, toTeleport);
		List<EntityAgeable> ntp = this.worldObj.getEntitiesWithinAABB(EntityAgeable.class, noTeleport);
		for(EntityAgeable e : tp)
		{
			if(!e.isDead && !(e instanceof IMob) && ((b && e.isChild()) || (!b && !e.isChild())) && this.getMRU() >= 100 && !ntp.contains(e) && this.setMRU(this.getMRU()-100))
			{
				e.setPositionAndRotation(xCoord+0.5D, yCoord+1.5D, zCoord+0.5D, 0, 0);
			}
		}
	}

}
