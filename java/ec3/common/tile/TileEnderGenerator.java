package ec3.common.tile;

import java.util.List;

import DummyCore.Utils.DataStorage;
import DummyCore.Utils.DummyData;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.config.Configuration;
import ec3.api.ApiCore;
import ec3.common.entity.EntitySolarBeam;

public class TileEnderGenerator extends TileMRUGeneric{
	
	public static float cfgMaxMRU = ApiCore.GENERATOR_MAX_MRU_GENERIC;
	public static float cfgBalance = -1F;
	public static float mruGenerated = 500;
	public static int endermenCatchRadius = 8;
	
	public TileEnderGenerator()
	{
		 super();
		this.maxMRU = (int)cfgMaxMRU;
		this.balance = cfgBalance;
	}
	
	public boolean canGenerateMRU()
	{
		return false;
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		if(this.balance == -1)
		{
			this.balance = this.worldObj.rand.nextFloat()*2;
		}
		if(!this.worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord))
		{
			List<EntityEnderman> l = this.worldObj.getEntitiesWithinAABB(EntityEnderman.class, AxisAlignedBB.getBoundingBox(xCoord-endermenCatchRadius, yCoord-endermenCatchRadius, zCoord-endermenCatchRadius, xCoord+endermenCatchRadius, yCoord+endermenCatchRadius, zCoord+endermenCatchRadius));
			if(!l.isEmpty())
			{
					for(int i = 0; i < l.size(); ++i)
					{
						l.get(i).setPositionAndRotation(xCoord+0.5D, yCoord+1D, zCoord+0.5D, 0, 0);
					}
			}
			List<EntityEnderman> l1 = this.worldObj.getEntitiesWithinAABB(EntityEnderman.class, AxisAlignedBB.getBoundingBox(xCoord-2, yCoord-2, zCoord-2, xCoord+2, yCoord+2, zCoord+2));
			if(!l1.isEmpty())
			{
				if(!this.worldObj.isRemote)
				{
					for(int i = 0; i < l1.size(); ++i)
					{
						if(l1.get(i).attackEntityFrom(DamageSource.magic, 1))
						{
							this.setMRU((int) (this.getMRU()+mruGenerated));
							if(this.getMRU() > this.getMaxMRU())
								this.setMRU(this.getMaxMRU());
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
	    	String[] cfgArrayString = cfg.getStringList("EnderGeneratorSettings", "tileentities", new String[]{
	    			"Max MRU:"+ApiCore.GENERATOR_MAX_MRU_GENERIC,
	    			"Default balance(-1 is random):-1.0",
	    			"MRU generated per hit:500",
	    			"Radius of Endermen detection:8"
	    			},"");
	    	String dataString="";
	    	
	    	for(int i = 0; i < cfgArrayString.length; ++i)
	    		dataString+="||"+cfgArrayString[i];
	    	
	    	DummyData[] data = DataStorage.parseData(dataString);
	    	
	    	cfgMaxMRU = Float.parseFloat(data[0].fieldValue);
	    	cfgBalance = Float.parseFloat(data[1].fieldValue);
	    	mruGenerated = Float.parseFloat(data[2].fieldValue);
	    	endermenCatchRadius = Integer.parseInt(data[3].fieldValue);
	    	
	    	cfg.save();
    	}catch(Exception e)
    	{
    		return;
    	}
    }

}
