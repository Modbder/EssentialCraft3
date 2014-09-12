package ec3.common.tile;

import java.util.Arrays;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import DummyCore.Utils.Coord3D;
import DummyCore.Utils.DataStorage;
import DummyCore.Utils.DummyData;
import DummyCore.Utils.MathUtils;
import DummyCore.Utils.MiscUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.oredict.OreDictionary;
import ec3.api.ApiCore;
import ec3.api.ITEHasMRU;
import ec3.api.MagicianTableRecipe;
import ec3.api.MagicianTableRecipes;
import ec3.common.entity.EntitySolarBeam;
import ec3.common.item.ItemBoundGem;
import ec3.common.item.ItemGenericEC3;
import ec3.common.mod.EssentialCraftCore;
import ec3.utils.common.ECUtils;

public class TileMagicalQuarry extends TileMRUGeneric{
	
	public int progressLevel;
	 public int miningX;
	 public int miningY;
	 public int miningZ;
	 public boolean flag;
	
	public TileMagicalQuarry()
	{
		this.maxMRU = (int) ApiCore.DEVICE_MAX_MRU_GENERIC*10;
		this.setSlotsNum(4);
	}
	
	public boolean canGenerateMRU()
	{
		return false;
	}
	
	@Override
	public void updateEntity()
	{
		if(this.syncTick == 10)
			this.syncTick = 0;
		super.updateEntity();
    	mine();
    	if(!this.worldObj.isRemote)
    		collectItems();
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
	}
	
	@Override
    public void readFromNBT(NBTTagCompound i)
    {
		super.readFromNBT(i);
		progressLevel = i.getInteger("progressLevel");
		miningX = i.getInteger("miningX");
		miningY = i.getInteger("miningY");
		miningZ = i.getInteger("miningZ");
		flag = i.getBoolean("localFlag");
    }
	
	@Override
    public void writeToNBT(NBTTagCompound i)
    {
    	super.writeToNBT(i);
    	i.setInteger("progressLevel", progressLevel);
    	i.setInteger("miningX", miningX);
    	i.setInteger("miningY", miningY);
    	i.setInteger("miningZ", miningZ);
    	i.setBoolean("localFlag", flag);
    }
	
    @SideOnly(Side.CLIENT)
    @Override
    public AxisAlignedBB getRenderBoundingBox()
    {
    	AxisAlignedBB bb = INFINITE_EXTENT_AABB;
    	return bb;
    }
    
    public boolean hasInventoryUpgrade()
    {
    	ItemStack s = ItemGenericEC3.getStkByName("inventoryUpgrade");
    	for(int i = 0; i < this.getSizeInventory(); ++i)
    	{
    		if(this.getStackInSlot(i) != null && this.getStackInSlot(i).isItemEqual(s))
    			return true;
    	}
    	return false;
    }
    
    public boolean hasMiningUpgrade()
    {
    	ItemStack s = ItemGenericEC3.getStkByName("diamondUpgrade");
    	for(int i = 0; i < this.getSizeInventory(); ++i)
    	{
    		if(this.getStackInSlot(i) != null && this.getStackInSlot(i).isItemEqual(s))
    			return true;
    	}
    	return false;
    }
    
    public boolean hasInventory()
    {
    	TileEntity t = this.worldObj.getTileEntity(xCoord, yCoord+1, zCoord);
    	if(t != null && t instanceof IInventory)
    	{
    		return hasSpaceInInv((IInventory) t);
    	}
    	return false;
    }
    
    public IInventory getInventory()
    {
    	TileEntity t = this.worldObj.getTileEntity(xCoord, yCoord+1, zCoord);
    	if(t != null && t instanceof IInventory)
    	{
    		return (IInventory) t;
    	}
    	return null;
    }
    
    public boolean hasSpaceInInv(IInventory inv)
    {
    	for(int i = 0; i < inv.getSizeInventory(); ++i)
    	{
    		if(inv.getStackInSlot(i) == null)
    			return true;
    	}
    	return false;
    }
    
    public float getEfficency()
    {
    	float f = 0.5F;
    	ItemStack s = ItemGenericEC3.getStkByName("efficencyUpgrade");
    	for(int i = 0; i < this.getSizeInventory(); ++i)
    	{
    		if(this.getStackInSlot(i) != null && this.getStackInSlot(i).isItemEqual(s))
    		{
    			f += (float)this.getStackInSlot(i).stackSize*0.5F;
    		}
    	}
    	return f;
    }
    
    public int getMiningRange()
    {
    	int f = 5;
    	ItemStack s = ItemGenericEC3.getStkByName("diamondUpgrade");
    	for(int i = 0; i < this.getSizeInventory(); ++i)
    	{
    		if(this.getStackInSlot(i) != null && this.getStackInSlot(i).isItemEqual(s))
    		{
    			f += this.getStackInSlot(i).stackSize;
    		}
    	}
    	return f;
    }
    
    public boolean canMineBlock(Block b)
    {
    	if(b == Blocks.bedrock)
    		return false;
    	if(b == Blocks.obsidian && !this.hasMiningUpgrade())
    		return false;
    	return true;
    }
    
    public boolean shouldInstaMine(Block b)
    {
    	return b instanceof BlockLiquid || b == null || b == Blocks.air;
    }
    
    public boolean mineBlock(Block b)
    {
    	if(canMineBlock(b))
    	{
    		if(shouldInstaMine(b))
    		{
    			this.worldObj.setBlock(miningX, miningY, miningZ, Blocks.air, 0, 3);
    			return true;
    		}else
    		{
    			float required = b.getBlockHardness(worldObj, miningX, miningY, miningZ)*9F;
    			if(this.setMRU((int)(this.getMRU()-(2*this.getEfficency()))))
    				this.progressLevel += this.getEfficency();
    			if(this.progressLevel >= required)
    			{
    				progressLevel = 0;
    				b.breakBlock(worldObj, miningX, miningY, miningY, b, this.worldObj.getBlockMetadata(miningX, miningY, miningZ));
    				b.onBlockDestroyedByPlayer(worldObj, miningX, miningY, miningZ, 0);
    				b.dropBlockAsItem(worldObj, miningX, miningY, miningZ, this.worldObj.getBlockMetadata(miningX, miningY, miningZ), 0);
    				this.worldObj.setBlock(miningX, miningY, miningZ, Blocks.air, 0, 3);
    				ECUtils.increaseCorruptionAt(worldObj, xCoord, yCoord, zCoord, this.worldObj.rand.nextInt(2));
    			}
    		}
    		
    	}
    	return false;
    }
    
    public boolean isMainColomnMined()
    {
    	int r = this.yCoord-2;
    	while(--r >= 0)
    	{
    		Block b = worldObj.getBlock(xCoord, r, zCoord);
    		if((b != null && b != Blocks.air && !(b instanceof BlockLiquid) && this.canMineBlock(b)) && this.canMineBlock(b))
    		{
    			return false;
    		}
    	}
    	return true;
    }
    
    public int genMiningColomnY(int current)
    {
    	int r = this.yCoord-2;
    	while(--r >= 0)
    	{
    		Block b = worldObj.getBlock(xCoord, r, zCoord);
    		if((b != null && b != Blocks.air && !(b instanceof BlockLiquid) && this.canMineBlock(b)) && this.canMineBlock(b))
    		{
    			return r;
    		}
    	}
    	return current;
    }
    
    public boolean isRowMined()
    {
    	int rad = this.getMiningRange();
    	for(int x = -rad; x <= rad; ++x)
    	{
        	for(int z = -rad; z <= rad; ++z)
        	{
        		Block b = worldObj.getBlock(xCoord+x, miningY, zCoord+z);
        		if(b != null && b != Blocks.air && !(b instanceof BlockLiquid) && this.canMineBlock(b))
        		{
        			return false;
        		}
        	}
    	}
    	return true;
    }
    
    public boolean canWork()
    {
    	return this.getEfficency() > 0 && this.getMRU()>8;
    }
    
    public void mine()
    {
    	if(canWork() && !this.worldObj.isRemote)
    	{
    		if(isMainColomnMined())
    		{
    			if(!flag)
    			{
    				flag = true;
    				this.miningY = this.yCoord-3;
    			}
    			flag = isMainColomnMined();
    			
    			if(this.isRowMined())
    			{
    				--this.miningY;
    			}else
    			{
    				int rad = this.getMiningRange();
    			    Fort:for(int x = -rad; x <= rad; ++x)
    			    {
    			        for(int z = -rad; z <= rad; ++z)
    			        {
    			        	if(worldObj.getBlock(xCoord+x, miningY, zCoord+z) != null && worldObj.getBlock(xCoord+x, miningY, zCoord+z) != Blocks.air && !(worldObj.getBlock(xCoord+x, miningY, zCoord+z) instanceof BlockLiquid))
    			        	{
    			        		this.miningX = this.xCoord+x;
    			        		this.miningZ = this.zCoord+z;
    			        		this.mineBlock(worldObj.getBlock(xCoord+x, miningY, zCoord+z));
    			        		break Fort;
    			        	}
    			        }
    			    }
    			}
    		}else
    		{
    			this.flag = false;
    			this.miningY = this.genMiningColomnY(miningY);
    			this.miningX = this.xCoord;
    			this.miningZ = this.zCoord;
    			if(worldObj.getBlock(miningX, miningY, miningZ) != null && worldObj.getBlock(miningX, miningY, miningZ) != Blocks.air && !(worldObj.getBlock(miningX, miningY, miningZ) instanceof BlockLiquid))
    			{
    				if(this.mineBlock(worldObj.getBlock(miningX, miningY, miningZ)))
    				{
    					--this.miningY;
    				}
    			}else
    			{
    				--this.miningY;
    			}
    		}
    	}
    }
    
    public void collectItems()
    {
    	List<EntityItem> l = this.worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(miningX, miningY, miningZ, miningX+1, miningY+1, miningZ+1).expand(4D, 2D, 4D));
    	if(!l.isEmpty())
    	{
    		for(int i = 0; i < l.size(); ++i)
    		{
    			EntityItem item = l.get(i);
    			ItemStack s = item.getEntityItem();
    			item.setPositionAndRotation(0, 0, 0, 0, 0);
    			item.setDead();
    			if(this.hasInventoryUpgrade() && this.hasInventory())
    			{
    				this.insertItem(s);
    			}else
    			{
    				this.splitItem(s);
    			}
    		}
    	}
    }
    
    public void splitItem(ItemStack s)
    {
    	EntityItem item = new EntityItem(worldObj, xCoord, yCoord, zCoord, s);
    	item.setPositionAndRotation(xCoord+0.5D, yCoord+2, zCoord+0.5D, 0, 0);
    	this.worldObj.spawnEntityInWorld(item);
    }
    
    public void insertItem(ItemStack s)
    {
    	
    	if(this.hasSpaceInInv(this.getInventory()))
    	{
    		MiscUtils.addItemToInventory(s, getInventory(), this.worldObj.isRemote);
    	}else
    	{
    		this.splitItem(s);
    	}
    }

}
