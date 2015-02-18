package ec3.common.tile;

import java.util.UUID;

import DummyCore.Utils.DataStorage;
import DummyCore.Utils.DummyData;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.config.Configuration;
import ec3.api.ApiCore;
import ec3.api.EnumStructureType;
import ec3.api.IStructurePiece;
import ec3.api.ITERequiresMRU;
import ec3.common.entity.EntitySolarBeam;

public class TileSolarPrism extends TileEntity{
	
	public static float solarBeamChance = 0.025F;
	public static boolean requiresUnabstructedSky = true;
	public static boolean requiresMidday = true;
	public static boolean ignoreRain = false;
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		if(!this.worldObj.isRemote)
		{
			if(this.worldObj.rand.nextFloat() <= solarBeamChance && (this.worldObj.canBlockSeeTheSky(xCoord, yCoord, zCoord) || !requiresUnabstructedSky) && ((worldObj.getWorldTime() % 24000 >= 5000 && worldObj.getWorldTime() % 24000 <= 7000) || !requiresMidday) && (!this.worldObj.isRaining() || ignoreRain))
			{
				int y = this.yCoord-1;
				while(y > 0 && this.worldObj.isAirBlock(xCoord, y, zCoord))
				{
					--y;
					if(!this.worldObj.isAirBlock(xCoord, y, zCoord))
					{
						EntitySolarBeam beam = new EntitySolarBeam(this.worldObj,xCoord,y,zCoord);
						this.worldObj.spawnEntityInWorld(beam);
					}
				}
			}
			if(this.worldObj.isAirBlock(xCoord+2, yCoord, zCoord) || this.worldObj.isAirBlock(xCoord-2, yCoord, zCoord) || this.worldObj.isAirBlock(xCoord, yCoord, zCoord-2) || this.worldObj.isAirBlock(xCoord, yCoord, zCoord+2))
			{
				this.worldObj.getBlock(xCoord, yCoord, zCoord).dropBlockAsItem(worldObj, xCoord, yCoord, zCoord, this.getBlockMetadata(), 0);
				this.worldObj.setBlock(xCoord, yCoord, zCoord, Blocks.air, 0, 3);
			}
			if(!this.worldObj.isAirBlock(xCoord+1, yCoord, zCoord) || !this.worldObj.isAirBlock(xCoord-1, yCoord, zCoord) || !this.worldObj.isAirBlock(xCoord, yCoord, zCoord-1) || !this.worldObj.isAirBlock(xCoord, yCoord, zCoord+1))
			{
				this.worldObj.getBlock(xCoord, yCoord, zCoord).dropBlockAsItem(worldObj, xCoord, yCoord, zCoord, this.getBlockMetadata(), 0);
				this.worldObj.setBlock(xCoord, yCoord, zCoord, Blocks.air, 0, 3);
			}
			if(!this.worldObj.isAirBlock(xCoord+1, yCoord, zCoord+1) || !this.worldObj.isAirBlock(xCoord+1, yCoord, zCoord-1) || !this.worldObj.isAirBlock(xCoord-1, yCoord, zCoord-1) || !this.worldObj.isAirBlock(xCoord-1, yCoord, zCoord+1))
			{
				this.worldObj.getBlock(xCoord, yCoord, zCoord).dropBlockAsItem(worldObj, xCoord, yCoord, zCoord, this.getBlockMetadata(), 0);
				this.worldObj.setBlock(xCoord, yCoord, zCoord, Blocks.air, 0, 3);
			}
		}
	}
	
    public static void setupConfig(Configuration cfg)
    {
    	try
    	{
	    	cfg.load();
	    	String[] cfgArrayString = cfg.getStringList("SolarPrismSettings", "tileentities", new String[]{
	    			"Chance each tick to create a solar beam:0.025",
	    			"Requires unobstructed sky:true",
	    			"Requires midday:true",
	    			"Is rain ignored:false"
	    			},"");
	    	String dataString="";
	    	
	    	for(int i = 0; i < cfgArrayString.length; ++i)
	    		dataString+="||"+cfgArrayString[i];
	    	
	    	DummyData[] data = DataStorage.parseData(dataString);
	    	
	    	solarBeamChance = Float.parseFloat(data[0].fieldValue);
	    	requiresUnabstructedSky = Boolean.parseBoolean(data[1].fieldValue);
	    	requiresMidday = Boolean.parseBoolean(data[2].fieldValue);
	    	ignoreRain = Boolean.parseBoolean(data[3].fieldValue);
	    	
	    	cfg.save();
    	}catch(Exception e)
    	{
    		return;
    	}
    }

}
