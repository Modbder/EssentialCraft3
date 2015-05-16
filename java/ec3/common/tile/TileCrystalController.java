package ec3.common.tile;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.config.Configuration;
import DummyCore.Utils.DataStorage;
import DummyCore.Utils.DummyData;
import DummyCore.Utils.MathUtils;
import ec3.api.ApiCore;
import ec3.api.ITEHasMRU;
import ec3.common.item.ItemBoundGem;
import ec3.common.item.ItemEssence;
import ec3.utils.common.ECUtils;

public class TileCrystalController extends TileMRUGeneric{
	
	public static float cfgMaxMRU = ApiCore.DEVICE_MAX_MRU_GENERIC;
	public static int mruUsage = 100;
	public static int chanceToUseMRU = 20;
	public static float mutateModifier = .001F;
	
	public TileCrystalController()
	{
		 super();
		this.maxMRU = (int)cfgMaxMRU;
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
		if(!this.worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord))
	    	if(!this.worldObj.isRemote && this.worldObj.rand.nextInt(chanceToUseMRU) == 0 && this.getMRU() >= mruUsage)
	    	{
	    		this.setMRU(this.getMRU()-mruUsage);
	    	}
    	this.spawnParticles();
    	if(!this.worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord))
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
    		float chance = (float) (mutateModifier*(rarity+1));
    		if(this.worldObj.rand.nextFloat() <= chance)
    		{
    			int type = ItemEssence.convertDamageToIntBefore4(e.getItemDamage());
    			c.mutate(type, this.worldObj.rand.nextInt((rarity+1)*2));
    			this.decrStackSize(1, 1);
    			this.setMRU(this.getMRU()-mruUsage*10);
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
    
    public static void setupConfig(Configuration cfg)
    {
    	try
    	{
	    	cfg.load();
	    	String[] cfgArrayString = cfg.getStringList("CrystalControllerSettings", "tileentities", new String[]{
	    			"Max MRU:"+ApiCore.DEVICE_MAX_MRU_GENERIC,
	    			"MRU Usage:100",
	    			"Chance to NOT use MRU(do not set to 0!):20",
	    			"Crystal mutation chance modifier:0.001"
	    			},"");
	    	String dataString="";
	    	
	    	for(int i = 0; i < cfgArrayString.length; ++i)
	    		dataString+="||"+cfgArrayString[i];
	    	
	    	DummyData[] data = DataStorage.parseData(dataString);
	    	
	    	mutateModifier = Float.parseFloat(data[3].fieldValue);
	    	mruUsage = Integer.parseInt(data[1].fieldValue);
	    	chanceToUseMRU = Integer.parseInt(data[2].fieldValue);
	    	cfgMaxMRU = Float.parseFloat(data[0].fieldValue);
	    	
	    	cfg.save();
    	}catch(Exception e)
    	{
    		return;
    	}
    }
}
