package ec3.common.tile;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import DummyCore.Utils.Coord3D;
import DummyCore.Utils.DummyDistance;
import DummyCore.Utils.MathUtils;
import DummyCore.Utils.MiscUtils;
import ec3.api.ApiCore;
import ec3.api.ITEHasMRU;
import ec3.common.block.BlocksCore;
import ec3.common.item.ItemBoundGem;
import ec3.utils.common.ECUtils;

public class TileCrystalFormer extends TileMRUGeneric{
	
	public int progressLevel;
	
	public TileCrystalFormer()
	{
		this.maxMRU = (int) ApiCore.DEVICE_MAX_MRU_GENERIC;
		this.setSlotsNum(8);
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		ECUtils.mruIn(this, 0);
		ECUtils.spawnMRUParticles(this, 0);
    	this.doWork();
    	this.spawnParticles();
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
    				ECUtils.increaseCorruptionAt(worldObj, xCoord, yCoord, zCoord, this.worldObj.rand.nextInt(2));
        			if(this.progressLevel >= 1000)
        			{
        				this.progressLevel = 0;
        				this.createItem();
        			}	
    			}
	
    		}
    	}
    }
    
    public void createItem()
    {
    	ItemStack b = new ItemStack(Items.bucket,1,0);
    	this.setInventorySlotContents(2, b);
    	this.setInventorySlotContents(3, b);
    	this.setInventorySlotContents(4, b);
    	this.decrStackSize(5, 1);
    	this.decrStackSize(6, 1);
    	this.decrStackSize(7, 1);
    	ItemStack crystal = new ItemStack(BlocksCore.elementalCrystal,1,0);
    	MiscUtils.getStackTag(crystal).setFloat("size", 1);
    	MiscUtils.getStackTag(crystal).setFloat("fire", 0);
    	MiscUtils.getStackTag(crystal).setFloat("water", 0);
    	MiscUtils.getStackTag(crystal).setFloat("earth", 0);
    	MiscUtils.getStackTag(crystal).setFloat("air", 0);
    	this.setInventorySlotContents(1, crystal);
    }
    
    public boolean canWork()
    {
    	ItemStack[] s = new ItemStack[7];
    	for(int i = 1; i < 8; ++i)
    	{
    		s[i-1] = this.getStackInSlot(i);
    	}
    	if(s[0] == null)
    	{
    		if(s[1] != null && s[2] != null && s[3] != null && s[4] != null && s[5] != null && s[6] != null)
    		{
    			if(s[1].getItem() == Items.water_bucket && s[2].getItem() == Items.water_bucket &&s[3].getItem() == Items.water_bucket && s[4].getItem() == Item.getItemFromBlock(Blocks.glass) && s[5].getItem() == Item.getItemFromBlock(Blocks.glass) &&s[6].getItem() == Items.diamond)
    			{
    				return true;
    			}
    		}
    	}
    	return false;
    }
    
    public void spawnParticles()
    {
    	if(this.canWork() && this.getMRU() > 0)
    	{
    		for(int o = 0; o < 10; ++o)
    		{
    			this.worldObj.spawnParticle("reddust", xCoord+0.1D+this.worldObj.rand.nextDouble()/1.1D, yCoord+((float)o/10), zCoord+0.1D+this.worldObj.rand.nextDouble()/1.1D, -1.0D, 1.0D, 1.0D);
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
