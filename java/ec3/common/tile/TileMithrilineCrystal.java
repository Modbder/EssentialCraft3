package ec3.common.tile;

import DummyCore.Utils.DataStorage;
import DummyCore.Utils.DummyData;
import DummyCore.Utils.MiscUtils;
import DummyCore.Utils.Notifier;
import DummyCore.Utils.TileStatTracker;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.config.Configuration;

public class TileMithrilineCrystal extends TileEntity{
	
	public static float energyEachTick = 0.025F;
	public static float energyEachTick_End = 0.1F;
	public static float maxEnergy = 10000F;
	public static boolean requiresUnobstructedSky = true;
	public float energy;
	private TileStatTracker tracker;
	public int syncTick;

	public TileMithrilineCrystal()
	{
		super();
		tracker = new TileStatTracker(this);
	}
	
	@Override
    public void readFromNBT(NBTTagCompound i)
    {
		super.readFromNBT(i);
		energy = i.getFloat("energy");
    }
	
	@Override
    public void writeToNBT(NBTTagCompound i)
    {
    	super.writeToNBT(i);
    	i.setFloat("energy", energy);
    }
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		if(syncTick == 0)
		{
			if(this.tracker == null)
				Notifier.notifyCustomMod("EssentialCraft", "[WARNING][SEVERE]TileEntity "+this+" at pos "+this.xCoord+","+this.yCoord+","+this.zCoord+" tries to sync itself, but has no TileTracker attached to it! SEND THIS MESSAGE TO THE DEVELOPER OF THE MOD!");
			else
				if(!this.worldObj.isRemote && this.tracker.tileNeedsSyncing())
				{
					MiscUtils.sendPacketToAllAround(worldObj, getDescriptionPacket(), xCoord, yCoord, zCoord, this.worldObj.provider.dimensionId, 32);
				}
			syncTick = 60;
		}else
			--this.syncTick;
		boolean hasSky = this.worldObj.canBlockSeeTheSky(xCoord, yCoord+3, zCoord) || !requiresUnobstructedSky;
		if(hasSky)
		{
			float energyGenerated = this.worldObj.provider != null && this.worldObj.provider.dimensionId == 1 ? energyEachTick_End : energyEachTick;
			if(energy < maxEnergy)
				energy += energyGenerated;
		}
	}
	
	@Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.writeToNBT(nbttagcompound);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, -10, nbttagcompound);
    }
	
	@Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
		if(net.getNetHandler() instanceof INetHandlerPlayClient)
			if(pkt.func_148853_f() == -10)
				this.readFromNBT(pkt.func_148857_g());
    }
	
    public static void setupConfig(Configuration cfg)
    {
    	try
    	{
	    	cfg.load();
	    	String[] cfgArrayString = cfg.getStringList("MithrilineCrystalSettings", "tileentities", new String[]{
	    			"Enderstar pulse generated each tick:0.025",
	    			"Enderstar pulse generated each tick in the end:0.1",
	    			"Requires a direct view to the sky:true",
	    			"Maximum enderstar pulse stored:10000"
	    			},"");
	    	String dataString="";
	    	
	    	for(int i = 0; i < cfgArrayString.length; ++i)
	    		dataString+="||"+cfgArrayString[i];
	    	
	    	DummyData[] data = DataStorage.parseData(dataString);
	    	
	    	energyEachTick = Float.parseFloat(data[0].fieldValue);
	    	energyEachTick_End = Float.parseFloat(data[1].fieldValue);
	    	requiresUnobstructedSky = Boolean.parseBoolean(data[2].fieldValue);
	    	maxEnergy = Float.parseFloat(data[3].fieldValue);
	    	
	    	cfg.save();
    	}catch(Exception e)
    	{
    		return;
    	}
    }

}
