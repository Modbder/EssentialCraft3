package ec3.common.tile;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.OreDictionary;
import DummyCore.Utils.DataStorage;
import DummyCore.Utils.DummyData;
import DummyCore.Utils.MathUtils;
import DummyCore.Utils.MiscUtils;
import ec3.api.ApiCore;
import ec3.api.ITEHasMRU;
import ec3.common.block.BlocksCore;
import ec3.common.item.ItemBoundGem;
import ec3.utils.common.ECUtils;

public class TileCrystalFormer extends TileMRUGeneric{
	
	public int progressLevel;
	public static float cfgMaxMRU = ApiCore.DEVICE_MAX_MRU_GENERIC;
	public static int mruUsage = 100;
	public static int requiredTime = 1000;
	public static boolean generatesCorruption = true;
	public static int genCorruption = 2;
	
	public TileCrystalFormer()
	{
		 super();
		this.maxMRU = (int)cfgMaxMRU;
		this.setSlotsNum(8);
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		ECUtils.mruIn(this, 0);
		ECUtils.spawnMRUParticles(this, 0);
		if(!this.worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord))
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
    		if(this.getMRU() > mruUsage)
    		{
    			if(!this.worldObj.isRemote)
    			{
    				if(this.setMRU(this.getMRU()-mruUsage))
    				++this.progressLevel;
    				if(generatesCorruption)
    					ECUtils.increaseCorruptionAt(worldObj, xCoord, yCoord, zCoord, this.worldObj.rand.nextInt(genCorruption));
        			if(this.progressLevel >= requiredTime)
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
    			if(
    					s[1].getItem() == Items.water_bucket 
    					&& s[2].getItem() == Items.water_bucket 
    					&& s[3].getItem() == Items.water_bucket 
    					&& (s[4].getItem() == Item.getItemFromBlock(Blocks.glass) || OreDictionary.getOreName(OreDictionary.getOreIDs(s[4])[0]).contains("glass") || OreDictionary.getOreName(OreDictionary.getOreIDs(s[4])[0]).contains("Glass")) 
    					&& (s[5].getItem() == Item.getItemFromBlock(Blocks.glass) || OreDictionary.getOreName(OreDictionary.getOreIDs(s[5])[0]).contains("glass") || OreDictionary.getOreName(OreDictionary.getOreIDs(s[5])[0]).contains("Glass")) 
    					&& s[6].getItem() == Items.diamond)
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
    
    public static void setupConfig(Configuration cfg)
    {
    	try
    	{
	    	cfg.load();
	    	String[] cfgArrayString = cfg.getStringList("CrystalFormerSettings", "tileentities", new String[]{
	    			"Max MRU:"+ApiCore.DEVICE_MAX_MRU_GENERIC,
	    			"MRU Usage:100",
	    			"Ticks required to create a crystal:1000",
	    			"Can this device actually generate corruption:true",
	    			"The amount of corruption generated each tick(do not set to 0!):2"
	    			},"");
	    	String dataString="";
	    	
	    	for(int i = 0; i < cfgArrayString.length; ++i)
	    		dataString+="||"+cfgArrayString[i];
	    	
	    	DummyData[] data = DataStorage.parseData(dataString);
	    	
	    	mruUsage = Integer.parseInt(data[1].fieldValue);
	    	requiredTime = Integer.parseInt(data[2].fieldValue);
	    	cfgMaxMRU = Float.parseFloat(data[0].fieldValue);
	    	generatesCorruption = Boolean.parseBoolean(data[3].fieldValue);
	    	genCorruption = Integer.parseInt(data[4].fieldValue);
	    	
	    	cfg.save();
    	}catch(Exception e)
    	{
    		return;
    	}
    }

	@Override
	public int[] getOutputSlots() {
		return new int[]{1};
	}
}
