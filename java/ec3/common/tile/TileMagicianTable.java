package ec3.common.tile;

import java.util.ArrayList;
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
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.OreDictionary;
import ec3.api.ApiCore;
import ec3.api.ITEHasMRU;
import ec3.api.MagicianTableRecipe;
import ec3.api.MagicianTableRecipes;
import ec3.api.MagicianTableUpgrades;
import ec3.common.entity.EntitySolarBeam;
import ec3.common.item.ItemBoundGem;
import ec3.common.mod.EssentialCraftCore;
import ec3.utils.common.ECUtils;

public class TileMagicianTable extends TileMRUGeneric{
	
	public float progressLevel, progressRequired, speedFactor = 1, mruConsume = 1;
	public int upgrade = -1;
	public MagicianTableRecipe currentRecipe;
	
	public static float cfgMaxMRU = ApiCore.DEVICE_MAX_MRU_GENERIC;
	public static boolean generatesCorruption = true;
	public static int genCorruption = 1;
	public static float mruUsage = 1;
	
	public TileMagicianTable()
	{
		 super();
		this.maxMRU = (int)cfgMaxMRU;
		this.setSlotsNum(7);
	}
	
	public boolean canGenerateMRU()
	{
		return false;
	}
	
	@Override
	public void updateEntity()
	{
		if(this.upgrade == -1)
			this.speedFactor = 1F;
		else
			this.speedFactor = MagicianTableUpgrades.upgradeEfficency.get(upgrade);
		if(this.speedFactor != 1)
			this.mruConsume = this.speedFactor*2*mruUsage;
		else
			this.mruConsume = 1*mruUsage;
		super.updateEntity();
		ECUtils.mruIn(this, 0);
		ECUtils.spawnMRUParticles(this, 0);
		if(!this.worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord))
		{
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
				float mruReq = mruConsume;
				if(this.getMRU() >= mruReq && this.progressLevel < this.progressRequired)
				{
					this.progressLevel += this.speedFactor;
					if(generatesCorruption)
						ECUtils.increaseCorruptionAt(worldObj, xCoord, yCoord, zCoord, this.worldObj.rand.nextInt(genCorruption));
					this.setMRU((int) (this.getMRU()-mruReq));
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
	}
	
	@Override
    public void readFromNBT(NBTTagCompound i)
    {
		super.readFromNBT(i);
		progressLevel = i.getFloat("progressLevel");
		progressRequired = i.getFloat("progressRequired");
		speedFactor = i.getFloat("speedFactor");
		mruConsume = i.getFloat("mruConsume");
		upgrade = i.getInteger("upgrade");
    }
	
	@Override
    public void writeToNBT(NBTTagCompound i)
    {
    	super.writeToNBT(i);
    	i.setFloat("progressLevel", progressLevel);
    	i.setFloat("progressRequired", progressRequired);
    	i.setFloat("speedFactor", speedFactor);
    	i.setFloat("mruConsume", mruConsume);
    	i.setInteger("upgrade", upgrade);
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
    
    public static void setupConfig(Configuration cfg)
    {
    	try
    	{
	    	cfg.load();
	    	String[] cfgArrayString = cfg.getStringList("MagicianTableSettings", "tileentities", new String[]{
	    			"Max MRU:"+ApiCore.DEVICE_MAX_MRU_GENERIC,
	    			"MRU Usage Modifier:1.0",
	    			"Can this device actually generate corruption:true",
	    			"The amount of corruption generated each tick(do not set to 0!):1"
	    			},"");
	    	String dataString="";
	    	
	    	for(int i = 0; i < cfgArrayString.length; ++i)
	    		dataString+="||"+cfgArrayString[i];
	    	
	    	DummyData[] data = DataStorage.parseData(dataString);
	    	
	    	mruUsage = Float.parseFloat(data[1].fieldValue);
	    	cfgMaxMRU = Float.parseFloat(data[0].fieldValue);
	    	generatesCorruption = Boolean.parseBoolean(data[2].fieldValue);
	    	genCorruption = Integer.parseInt(data[3].fieldValue);
	    	
	    	cfg.save();
    	}catch(Exception e)
    	{
    		return;
    	}
    }

}
