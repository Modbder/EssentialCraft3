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
import ec3.utils.common.ECUtils;

public class TileCrystalController extends TileMRUGeneric{
	
	public TileCrystalController()
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
    	if(!this.worldObj.isRemote && this.worldObj.rand.nextInt(20) == 0 && this.getMRU() >= 100)
    	{
    		this.setMRU(this.getMRU()-100);
    	}
    	this.spawnParticles();
    	this.mutateToElement();
	}
	
    public void spawnParticles()
    {
    	if(this.getMRU() > 0 && this.getCrystal() != null)
    	{
    		for(int o = 0; o < 2; ++o)
    		{
    			this.worldObj.spawnParticle("reddust", xCoord+0.3D+this.worldObj.rand.nextDouble()/2, yCoord+0.3F+((float)o/2), zCoord+0.3D+this.worldObj.rand.nextDouble()/2D, -1.0D, 1.0D, 0.0D);
    		}
    	}
    }
    
    public void mutateToElement()
    {
    	if(this.getStackInSlot(1) != null && this.getStackInSlot(1).getItem() instanceof ItemEssence && this.getMRU() > 500 && !this.worldObj.isRemote && this.getCrystal() != null && this.getCrystal().size < 100)
    	{
    		ItemStack e = this.getStackInSlot(1);
    		TileElementalCrystal c = this.getCrystal();
    		int rarity = (int)((float)e.getItemDamage()/4);
    		float chance = (float) (.001*(rarity+1));
    		if(this.worldObj.rand.nextFloat() <= chance)
    		{
    			int type = ItemEssence.convertDamageToIntBefore4(e.getItemDamage());
    			c.mutate(type, this.worldObj.rand.nextInt((rarity+1)*2));
    			this.decrStackSize(1, 1);
    			this.setMRU(this.getMRU()-20);
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
