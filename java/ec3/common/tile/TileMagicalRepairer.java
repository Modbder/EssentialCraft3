package ec3.common.tile;

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

public class TileMagicalRepairer extends TileMRUGeneric{
	
	public TileMagicalRepairer()
	{
		this.maxMRU = (int) ApiCore.DEVICE_MAX_MRU_GENERIC;
		this.setSlotsNum(2);
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
    	repare();
    	spawnParticles();
	}
	
    public void repare()
    {
    	ItemStack repareItem = this.getStackInSlot(1);
    	if(canRepare(repareItem))
    	{
    		if(this.setMRU(this.getMRU()-70))
    		{
    			repareItem.setItemDamage(repareItem.getItemDamage()-1);
    			ECUtils.increaseCorruptionAt(worldObj, xCoord, yCoord, zCoord, this.worldObj.rand.nextInt(3));
    		}
    	}
    }
    
    public boolean canRepare(ItemStack s)
    {
    	return s != null && s.getItemDamage() != 0 && s.getItem().isRepairable()&& this.getMRU() >= 70;
    }
    
    public void spawnParticles()
    {
    	if(this.canRepare(this.getStackInSlot(1)) && this.getMRU() > 0)
    	{
    		for(int o = 0; o < 10; ++o)
    		{
    			this.worldObj.spawnParticle("reddust", xCoord+0.25D+this.worldObj.rand.nextDouble()/2.2D, yCoord+0.25D+((float)o/20), zCoord+0.25D+this.worldObj.rand.nextDouble()/2.2D, 1.0D, 0.0D, 1.0D);
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
