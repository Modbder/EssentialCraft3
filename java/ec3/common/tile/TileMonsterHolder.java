package ec3.common.tile;

import java.util.Arrays;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import DummyCore.Utils.Coord3D;
import DummyCore.Utils.DummyDistance;
import DummyCore.Utils.MathUtils;
import ec3.api.ApiCore;
import ec3.api.ITEHasMRU;
import ec3.common.item.ItemBoundGem;
import ec3.utils.common.ECUtils;

public class TileMonsterHolder extends TileMRUGeneric{
	public float rad = 12F;
	
	public TileMonsterHolder()
	{
		this.maxMRU = (int) ApiCore.DEVICE_MAX_MRU_GENERIC;
		this.setSlotsNum(1);
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		ECUtils.mruIn(this, 0);
		ECUtils.spawnMRUParticles(this, 0);
		IInventory inv = this;
		int slotNum = 0;
		TileEntity tile = this;
		if(inv.getStackInSlot(slotNum) != null && inv.getStackInSlot(slotNum).getItem() instanceof ItemBoundGem && inv.getStackInSlot(slotNum).getTagCompound() != null)
		{
			ItemStack s = inv.getStackInSlot(slotNum);
			int[] o = ItemBoundGem.getCoords(s);
			if(MathUtils.getDifference(tile.xCoord, o[0]) <= 16 && MathUtils.getDifference(tile.yCoord, o[1]) <= 16 && MathUtils.getDifference(tile.zCoord, o[2]) <= 16)
			{
    			if(tile.getWorldObj().getTileEntity(o[0], o[1], o[2]) != null && tile.getWorldObj().getTileEntity(o[0], o[1], o[2]) instanceof ITEHasMRU)
    			{
    				this.setBalance(((ITEHasMRU) tile.getWorldObj().getTileEntity(o[0], o[1], o[2])).getBalance());
    			}
    		}
		}
    	List<EntityLivingBase> lst = tile.getWorldObj().getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(tile.xCoord-32, tile.yCoord-32, tile.zCoord-32, tile.xCoord+32, tile.yCoord+32, tile.zCoord+32));
    	if(!lst.isEmpty())
    	{
    		for(int i = 0; i < lst.size(); ++i)
    		{
    			EntityLivingBase e = lst.get(i);
    			if(!(e instanceof EntityPlayer))
    			{
    				if(getMRU() > 1)
    				{
	    				Coord3D tilePos = new Coord3D(tile.xCoord+0.5D,tile.yCoord+0.5D,tile.zCoord+0.5D);
	    				Coord3D mobPosition = new Coord3D(e.posX,e.posY,e.posZ);
	    				DummyDistance dist = new DummyDistance(tilePos,mobPosition);
	    				if(dist.getDistance() < 10 && dist.getDistance() >= 7)
	    				{
	    					Vec3 posVector = Vec3.createVectorHelper(tilePos.x-mobPosition.x,tilePos.y-mobPosition.y ,tilePos.z-mobPosition.z);
	    					e.setPositionAndRotation(tilePos.x-posVector.xCoord/1.1D, tilePos.y-posVector.yCoord/1.1D, tilePos.z-posVector.zCoord/1.1D, e.rotationYaw, e.rotationPitch);
	    				}
	    				if(!this.worldObj.isRemote)
	    					this.setMRU(this.getMRU()-1);
    				}
    			}
    		}
    	}
	}
	
    @SideOnly(Side.CLIENT)
    @Override
    public AxisAlignedBB getRenderBoundingBox()
    {
    	AxisAlignedBB bb = INFINITE_EXTENT_AABB;
    	return bb;
    }
}
