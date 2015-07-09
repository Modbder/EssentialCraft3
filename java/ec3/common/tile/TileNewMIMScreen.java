package ec3.common.tile;

import net.minecraftforge.common.config.Configuration;
import DummyCore.Utils.DataStorage;
import DummyCore.Utils.DummyData;
import ec3.api.ApiCore;
import ec3.utils.common.ECUtils;

public class TileNewMIMScreen extends TileMRUGeneric{

	public TileNewMIM parent;
	int tickTime;
	
	public static float cfgMaxMRU = ApiCore.DEVICE_MAX_MRU_GENERIC;
	public static int mruForIns = 500;
	public static int mruForOut = 10;
	
	public TileNewMIMScreen()
	{
		this.setMaxMRU(cfgMaxMRU);
		this.setSlotsNum(2);
	}
	
	@Override
	public int[] getOutputSlots() {
		return new int[]{1};
	}
	
	public void updateEntity() 
	{
		super.updateEntity();
		ECUtils.manage(this, 0);
		if(tickTime == 0)
		{
			tickTime = 20;
			if(parent != null)
				if(!parent.isParent(this))
					parent = null;
		}else
			--tickTime;
		
		if(parent != null)
		{
			if(this.getStackInSlot(1) != null)
			{
				if(this.getMRU() >= mruForIns)
				{
					if(this.setMRU(this.getMRU()-mruForIns))
					{
						if(parent.addItemStackToSystem(this.getStackInSlot(1)))
							this.setInventorySlotContents(1, null);
						
						syncTick = 0;
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
	    	String[] cfgArrayString = cfg.getStringList("NewMagicalInventoryManagerScreenSettings", "tileentities", new String[]{
	    			"Max MRU:"+ApiCore.DEVICE_MAX_MRU_GENERIC,
	    			"MRU Per Inserted Item:500",
	    			"MRU Per Requested Item(for 1):10"
	    			},"");
	    	String dataString="";
	    	
	    	for(int i = 0; i < cfgArrayString.length; ++i)
	    		dataString+="||"+cfgArrayString[i];
	    	
	    	DummyData[] data = DataStorage.parseData(dataString);
	    	
	    	mruForIns = Integer.parseInt(data[1].fieldValue);
	    	mruForOut = Integer.parseInt(data[2].fieldValue);
	    	cfgMaxMRU = Float.parseFloat(data[0].fieldValue);
	    	
	    	cfg.save();
    	}catch(Exception e)
    	{
    		return;
    	}
    }

}
