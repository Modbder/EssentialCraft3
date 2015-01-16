package ec3.common.tile;

import java.util.Arrays;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import DummyCore.Utils.Coord3D;
import DummyCore.Utils.DataStorage;
import DummyCore.Utils.DummyData;
import DummyCore.Utils.MathUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.BlockTallGrass;
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
import ec3.common.mod.EssentialCraftCore;
import ec3.utils.common.ECUtils;

public class TileMagicianTable extends TileMRUGeneric{
	
	public int progressLevel, progressRequired, speedFactor = 1, mruConsume = 1;
	public MagicianTableRecipe currentRecipe;
	
	public TileMagicianTable()
	{
		this.maxMRU = (int) ApiCore.DEVICE_MAX_MRU_GENERIC;
		this.setSlotsNum(7);
	}
	
	public boolean canGenerateMRU()
	{
		return false;
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		ECUtils.mruIn(this, 0);
		ECUtils.spawnMRUParticles(this, 0);
		ItemStack[] craftMatrix = new ItemStack[5];
		craftMatrix[0] = this.getStackInSlot(1);
		craftMatrix[1] = this.getStackInSlot(2);
		craftMatrix[2] = this.getStackInSlot(3);
		craftMatrix[3] = this.getStackInSlot(4);
		craftMatrix[4] = this.getStackInSlot(5);
		MagicianTableRecipe rec = MagicianTableRecipes.getRecipeByCP(craftMatrix);
		if(currentRecipe == null && rec != null && this.progressRequired == rec.mruRequired && this.progressLevel != 0)
		{
			if(canFunction(rec))
			{
				this.progressRequired = rec.mruRequired;
				this.currentRecipe = rec;
			}
		}
		if(currentRecipe == null && rec != null && this.progressRequired == 0 && this.progressLevel == 0)
		{
			if(canFunction(rec))
			{
				this.progressRequired = rec.mruRequired;
				this.currentRecipe = rec;
			}
		}
		if(currentRecipe != null && rec == null)
		{
			progressRequired = 0;
			progressLevel = 0;
			currentRecipe = null;
			return;
		}
		if(currentRecipe != null && rec != null && progressRequired != 0)
		{
			if(!canFunction(rec))
			{
				progressRequired = 0;
				progressLevel = 0;
				currentRecipe = null;
				return;
			}
			int mruReq = mruConsume;
			if(this.getMRU() >= mruReq && this.progressLevel < this.progressRequired)
			{
				this.progressLevel += this.speedFactor;
				ECUtils.increaseCorruptionAt(worldObj, xCoord, yCoord, zCoord, this.worldObj.rand.nextInt(2));
				this.setMRU(this.getMRU()-mruReq);
				if(this.progressLevel >= this.progressRequired)
				{
					progressRequired = 0;
					progressLevel = 0;
					craft();
					currentRecipe = null;
				}
			}
		}
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
		progressRequired = i.getInteger("progressRequired");
		speedFactor = i.getInteger("speedFactor");
		mruConsume = i.getInteger("mruConsume");
    }
	
	@Override
    public void writeToNBT(NBTTagCompound i)
    {
    	super.writeToNBT(i);
    	i.setInteger("progressLevel", progressLevel);
    	i.setInteger("progressRequired", progressRequired);
    	i.setInteger("speedFactor", speedFactor);
    	i.setInteger("mruConsume", mruConsume);
    }
	
    @SideOnly(Side.CLIENT)
    @Override
    public AxisAlignedBB getRenderBoundingBox()
    {
    	AxisAlignedBB bb = INFINITE_EXTENT_AABB;
    	return bb;
    }
    
    public boolean canFunction(MagicianTableRecipe rec)
    {
		ItemStack result = rec.result;
		if(result != null)
		{
			if(this.getStackInSlot(6) == null)
			{
				return true;
			}else
			{
				if(this.getStackInSlot(6).isItemEqual(result))
				{
					if(this.getStackInSlot(6).stackSize+result.stackSize <= this.getInventoryStackLimit() && this.getStackInSlot(6).stackSize+result.stackSize <= this.getStackInSlot(6).getMaxStackSize())
					{
						return true;
					}
				}
			}
		}
		return false;
    }

    public void craft()
    {
    	if(this.canFunction(this.currentRecipe))
    	{
    		ItemStack stk = this.currentRecipe.result;
            if (this.getStackInSlot(6) == null)
            {
            	ItemStack copied = stk.copy();
            	if(copied.stackSize == 0)
            		copied.stackSize = 1;
                this.setInventorySlotContents(6, copied);
            }
            else if (getStackInSlot(6).getItem() == stk.getItem())
            {
                this.setInventorySlotContents(6, new ItemStack(stk.getItem(),stk.stackSize+this.getStackInSlot(6).stackSize,stk.getItemDamage()));
            }
            for(int i = 1; i < 6; ++i)
            {
            	this.decrStackSize(i, 1);
            }
    	}
    }

}
