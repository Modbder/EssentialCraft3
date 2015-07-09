package ec3.common.tile;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.config.Configuration;
import DummyCore.Utils.Coord3D;
import DummyCore.Utils.DataStorage;
import DummyCore.Utils.DummyData;
import DummyCore.Utils.MathUtils;
import ec3.api.ApiCore;
import ec3.common.block.BlockCorruption_Light;
import ec3.utils.common.ECUtils;

public class TileCorruptionCleaner extends TileMRUGeneric{
	
	public Coord3D cleared;
	public int clearTime = 0;
	
	public static int maxRadius = 8;
	public static boolean removeBlock = false;
	public static int mruUsage = 20;
	public static int ticksRequired = 200;
	public static float cfgMaxMRU = ApiCore.DEVICE_MAX_MRU_GENERIC;
	
	public TileCorruptionCleaner()
	{
		 super();
		this.maxMRU = (int) cfgMaxMRU;
		this.setSlotsNum(1);
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		ECUtils.manage(this, 0);
		
		if(!this.worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord))
		{
			if(cleared == null)
			{
				if(!this.worldObj.isRemote)
				{
					int offsetX = (int) (MathUtils.randomDouble(this.worldObj.rand)*maxRadius);
					int offsetY = (int) (MathUtils.randomDouble(this.worldObj.rand)*maxRadius);
					int offsetZ = (int) (MathUtils.randomDouble(this.worldObj.rand)*maxRadius);
					Block b = this.worldObj.getBlock(xCoord+offsetX, yCoord+offsetY, zCoord+offsetZ);
					if(b instanceof BlockCorruption_Light)
					{
						cleared = new Coord3D(xCoord+offsetX, yCoord+offsetY, zCoord+offsetZ);
						clearTime = ticksRequired;
					}
				}
			}else
			{
				if(!this.worldObj.isRemote)
				{
					Block b = this.worldObj.getBlock((int)cleared.x, (int)cleared.y, (int)cleared.z);
					if(!(b instanceof BlockCorruption_Light))
					{
						cleared = null;
						clearTime = 0;
						return;
					}
					if(this.getMRU() - mruUsage > 0)
					{
						--clearTime;
						this.setMRU(this.getMRU()-mruUsage);
						if(clearTime <= 0)
						{
							int metadata = this.worldObj.getBlockMetadata((int)cleared.x, (int)cleared.y, (int)cleared.z);
							if(metadata == 0 || removeBlock)
								this.worldObj.setBlockToAir((int)cleared.x, (int)cleared.y, (int)cleared.z);
							else
								this.worldObj.setBlockMetadataWithNotify((int)cleared.x, (int)cleared.y, (int)cleared.z, metadata-1, 3);
							this.cleared = null;
						}
					}
				}
			}
		}
	}
    
	@Override
    public void readFromNBT(NBTTagCompound i)
    {
		if(i.hasKey("coord"))
		{
			String str = i.getString("coord");
			if(!str.equals("null"))
			{
				DummyData[] coordData = DataStorage.parseData(i.getString("coord"));
				cleared = new Coord3D(Double.parseDouble(coordData[0].fieldValue),Double.parseDouble(coordData[1].fieldValue),Double.parseDouble(coordData[2].fieldValue));
			}else
			{
				cleared = null;
			}
		}
		clearTime = i.getInteger("clear");
		super.readFromNBT(i);
    }
	
	@Override
    public void writeToNBT(NBTTagCompound i)
    {
		if(cleared != null)
		{
			i.setString("coord", cleared.toString());
		}else
		{
			i.setString("coord", "null");
		}
		i.setInteger("clear", clearTime);
    	super.writeToNBT(i);
    }
	
    public static void setupConfig(Configuration cfg)
    {
    	try
    	{
	    	cfg.load();
	    	String[] cfgArrayString = cfg.getStringList("CorruptionCleanerSettings", "tileentities", new String[]{
	    			"Max MRU:"+ApiCore.DEVICE_MAX_MRU_GENERIC,
	    			"MRU Usage per Tick:20",
	    			"Required time to destroy corruption:200",
	    			"Should remove corruption blocks instead of reducing their growth:false",
	    			"A radius in which the device will detect corruption blocks:8"
	    			},"");
	    	String dataString="";
	    	
	    	for(int i = 0; i < cfgArrayString.length; ++i)
	    		dataString+="||"+cfgArrayString[i];
	    	
	    	DummyData[] data = DataStorage.parseData(dataString);
	    	
	    	maxRadius = Integer.parseInt(data[4].fieldValue);
	    	removeBlock = Boolean.parseBoolean(data[3].fieldValue);
	    	mruUsage = Integer.parseInt(data[1].fieldValue);
	    	ticksRequired = Integer.parseInt(data[2].fieldValue);
	    	cfgMaxMRU = Float.parseFloat(data[0].fieldValue);
	    	
	    	cfg.save();
    	}catch(Exception e)
    	{
    		return;
    	}
    }

	@Override
	public int[] getOutputSlots() {
		return new int[]{0};
	}
}
