package ec3.common.tile;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.config.Configuration;
import DummyCore.Utils.DataStorage;
import DummyCore.Utils.DummyData;
import DummyCore.Utils.MathUtils;
import ec3.api.ApiCore;
import ec3.api.IItemRequiresMRU;
import ec3.api.ITEHasMRU;
import ec3.common.item.ItemBoundGem;
import ec3.utils.common.ECUtils;

public class TileChargingChamber extends TileMRUGeneric{
	
	public static float cfgMaxMRU = (int) ApiCore.DEVICE_MAX_MRU_GENERIC;
	public static float reqMRUModifier = 1.0F;
	
	public TileChargingChamber()
	{
		 super();
		this.maxMRU = (int) cfgMaxMRU;
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
			tryChargeTools();
	}
	
    public void tryChargeTools()
    {
    	ItemStack _gen_var_0 = this.getStackInSlot(1);
    	if(_gen_var_0 != null && !this.worldObj.isRemote)
    	{
    		if(_gen_var_0.getItem() instanceof IItemRequiresMRU)
    		{
    			IItemRequiresMRU item = (IItemRequiresMRU) _gen_var_0.getItem();
    			int mru = item.getMRU(_gen_var_0);
    			int maxMRU = item.getMaxMRU(_gen_var_0);
    			int p = (int)((float)maxMRU/100*5);
    			if(mru < maxMRU)
    			{
    				if(mru+p < maxMRU)
    				{
    					if(this.getMRU() > p*reqMRUModifier)
    					{
    						System.out.println(item.setMRU(_gen_var_0, p));
    						if(item.setMRU(_gen_var_0,p))
    							this.setMRU((int) (this.getMRU()-p*reqMRUModifier));
    					}else if(this.getMRU() > 0)
    					{
    						if(item.setMRU(_gen_var_0,this.getMRU()))
    							this.setMRU(0);
    					}
    				}else
    				{
    					int k = maxMRU - mru;
    					if(this.getMRU() > k*reqMRUModifier)
    					{
    						if(item.setMRU(_gen_var_0,k))
    							this.setMRU((int) (this.getMRU()-k*reqMRUModifier));
    					}else if(this.getMRU() > 0)
    					{
    						if(item.setMRU(_gen_var_0,this.getMRU()))
    							this.setMRU(0);
    					}
    				}
    			}
    		}
    	}
    }
    
    public static void setupConfig(Configuration cfg)
    {
    	try
    	{
	    	cfg.load();
	    	String[] cfgArrayString = cfg.getStringList("ChargingChamberSettings", "tileentities", new String[]{"Max MRU:"+ApiCore.DEVICE_MAX_MRU_GENERIC,"Charge cost Modifier:1.0"}, "Settings of the given Device.");
	    	String dataString="";
	    	
	    	for(int i = 0; i < cfgArrayString.length; ++i)
	    		dataString+="||"+cfgArrayString[i];
	    	
	    	DummyData[] data = DataStorage.parseData(dataString);
	    	cfgMaxMRU = (int)Float.parseFloat(data[0].fieldValue);
	    	reqMRUModifier=Float.parseFloat(data[1].fieldValue);
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
