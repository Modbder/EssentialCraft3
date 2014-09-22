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
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import DummyCore.Utils.Coord3D;
import DummyCore.Utils.DummyDistance;
import DummyCore.Utils.MathUtils;
import DummyCore.Utils.MiscUtils;
import ec3.api.ApiCore;
import ec3.api.ITEHasMRU;
import ec3.common.item.ItemBoundGem;
import ec3.common.item.ItemGenericEC3;
import ec3.utils.common.ECUtils;

public class TileMonsterHarvester extends TileMRUGeneric{
	public float rad = 12F;
	public float rotation = 0F;
	
	public TileMonsterHarvester()
	{
		this.maxMRU = (int) ApiCore.DEVICE_MAX_MRU_GENERIC;
		this.setSlotsNum(6);
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
    	List<EntityLivingBase> lst = tile.getWorldObj().getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(tile.xCoord-16, tile.yCoord-16, tile.zCoord-16, tile.xCoord+16, tile.yCoord+16, tile.zCoord+16));
    	if(!lst.isEmpty() && !this.worldObj.isRemote)
    	{
    		for(int i = 0; i < lst.size(); ++i)
    		{
    			EntityLivingBase e = lst.get(i);
    			if(!(e instanceof EntityPlayer))
    			{
    				if(getMRU() > 100 && this.worldObj.getWorldTime() % 1440 == 0)
    				{
    					EntityLivingBase copy = (EntityLivingBase) MiscUtils.cloneEntity(e);
    					this.worldObj.spawnEntityInWorld(copy);
    					FakePlayer player = new FakePlayer((WorldServer) e.worldObj, ECUtils.EC3FakePlayerProfile);
    					ItemStack stk = this.getStackInSlot(2);
    					if(stk != null)
    						player.inventory.setInventorySlotContents(player.inventory.currentItem, stk.copy());
    					copy.setHealth(0.1F);
    					player.attackTargetEntityWithCurrentItem(copy);
    					player.setDead();
    					this.setMRU(this.getMRU()-100);
    					if(copy.getHealth() > 0)
    						copy.setDead();
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
