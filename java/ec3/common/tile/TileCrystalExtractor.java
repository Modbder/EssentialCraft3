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
import ec3.common.item.ItemEssence;
import ec3.common.item.ItemGenericEC3;
import ec3.common.item.ItemsCore;
import ec3.utils.common.ECUtils;

public class TileCrystalExtractor extends TileMRUGeneric{
	
	public int progressLevel;
	
	public TileCrystalExtractor()
	{
		this.maxMRU = (int) ApiCore.DEVICE_MAX_MRU_GENERIC;
		this.setSlotsNum(13);
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
		this.doWork();
    	this.spawnParticles();
    	
	}
	
    public void doWork()
    {
    	if(canWork())
    	{
    		if(this.getMRU() > 100)
    		{
    			if(!this.worldObj.isRemote)
    			{
    				if(this.setMRU(this.getMRU()-100))
    				++this.progressLevel;
        			if(this.progressLevel >= 1000)
        			{
        				this.progressLevel = 0;
        				this.createItems();
        			}	
    			}
	
    		}
    	}
    }
    
    public void createItems()
    {
    	TileElementalCrystal t = this.getCrystal();
    	float f = t.fire;
    	float w = t.water;
    	float e = t.earth;
    	float a = t.air;
    	float s = t.size*3000;
    	float[] baseChance = new float[]{f,w,e,a};
    	int[] essenceChance = new int[]{300000/8,300000/6,300000/4,300000/2};
    	int[] getChance = new int[16];
    	for(int i = 0; i < 16; ++i)
    	{
    		getChance[i] = (int) ((s*baseChance[ItemEssence.convertDamageToIntBefore4(i)])/essenceChance[i/4]);
    	}
    	if(!this.worldObj.isRemote)
    	{
	    	for(int i = 1; i < 13; ++i)
	    	{
	    		ItemStack st = new ItemStack(ItemsCore.essence,1,this.worldObj.rand.nextInt(16));
	    		if(this.worldObj.rand.nextInt(100) < getChance[st.getItemDamage()])
	    		{
	    			int sts = this.worldObj.rand.nextInt(1+getChance[st.getItemDamage()]/4);
	    			st.stackSize = sts;
	    			if(st.stackSize <= 0)
	    			{
	    				st.stackSize = 1;
	    			}
	    			this.setInventorySlotContents(i, st);
	    		}
	    	}
    	}
    }
	
    public boolean canWork()
    {
    	for(int i = 1; i < 13; ++i)
    	{
    		if(this.getStackInSlot(i) != null)
    		{
    			return false;
    		}
    	}
    	if(this.getCrystal() == null)
    		return false;
    	return true;
    }
    
    public boolean hasItemInSlots()
    {
    	for(int i = 1; i < 13; ++i)
    	{
    		if(this.getStackInSlot(i) != null)
    		{
    			return true;
    		}
    	}
    	return false;
    }
    
    public void spawnParticles()
    {
    	if(this.canWork() && this.getMRU() > 0)
    	{
    		TileElementalCrystal t = this.getCrystal();
    		if(t != null)
    		for(int o = 0; o < 10; ++o)
    		{
    			this.worldObj.spawnParticle("portal", xCoord+this.worldObj.rand.nextDouble(), t.yCoord+this.worldObj.rand.nextDouble(), zCoord+this.worldObj.rand.nextDouble(), (t.xCoord-xCoord), 0.0D, (t.zCoord-zCoord));
    		}
    	}
    }
    
    public TileElementalCrystal getCrystal()
    {
    	TileElementalCrystal t = null;
    	if(hasCrystalOnFront())
    	{
    		t = (TileElementalCrystal) this.worldObj.getTileEntity(xCoord+1, yCoord, zCoord);
    	}
    	if(hasCrystalOnBack())
    	{
    		t = (TileElementalCrystal) this.worldObj.getTileEntity(xCoord-1, yCoord, zCoord);
    	}
    	if(hasCrystalOnLeft())
    	{
    		t = (TileElementalCrystal) this.worldObj.getTileEntity(xCoord, yCoord, zCoord+1);
    	}
    	if(hasCrystalOnRight())
    	{
    		t = (TileElementalCrystal) this.worldObj.getTileEntity(xCoord, yCoord, zCoord-1);
    	}
    	return t;
    }

    public boolean hasCrystalOnFront()
    {
    	TileEntity t = this.worldObj.getTileEntity(xCoord+1, yCoord, zCoord);
    	return t != null && t instanceof TileElementalCrystal;
    }
    
    public boolean hasCrystalOnBack()
    {
    	TileEntity t = this.worldObj.getTileEntity(xCoord-1, yCoord, zCoord);
    	return t != null && t instanceof TileElementalCrystal;
    }
    
    public boolean hasCrystalOnLeft()
    {
    	TileEntity t = this.worldObj.getTileEntity(xCoord, yCoord, zCoord+1);
    	return t != null && t instanceof TileElementalCrystal;
    }
    
    public boolean hasCrystalOnRight()
    {
    	TileEntity t = this.worldObj.getTileEntity(xCoord, yCoord, zCoord-1);
    	return t != null && t instanceof TileElementalCrystal;
    }

	
    @SideOnly(Side.CLIENT)
    @Override
    public AxisAlignedBB getRenderBoundingBox()
    {
    	AxisAlignedBB bb = INFINITE_EXTENT_AABB;
    	return bb;
    }
}
