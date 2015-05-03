package ec3.common.tile;

import java.util.Random;

import DummyCore.Utils.DataStorage;
import DummyCore.Utils.DummyData;
import DummyCore.Utils.MiscUtils;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.config.Configuration;

public class TileElementalCrystal extends TileEntity{
	public int syncTick;
	public float size,fire,water,earth,air;
	
	public static float mutatuinChance = 0.001F;
	public static float growthModifier = 1.0F;
	
	@Override
    public void readFromNBT(NBTTagCompound i)
    {
		super.readFromNBT(i);
		size = i.getFloat("size");
		fire = i.getFloat("fire");
		water = i.getFloat("water");
		earth = i.getFloat("earth");
		air = i.getFloat("air");
    }
	
	@Override
    public void writeToNBT(NBTTagCompound i)
    {
    	super.writeToNBT(i);
    	i.setFloat("size", size);
    	i.setFloat("fire", fire);
    	i.setFloat("water", water);
    	i.setFloat("earth", earth);
    	i.setFloat("air", air);
    }
	
    
    public float getElementByNum(int num)
    {
    	if(num == 0)
    		return this.fire;
    	if(num == 1)
    		return this.water;
    	if(num == 2)
    		return this.earth;
    	if(num == 3)
    		return this.air;
    	return -1;
    }
    
    public void setElementByNum(int num, float amount)
    {
    	if(num == 0)
    		this.fire += amount;
    	if(num == 1)
    		this.water += amount;
    	if(num == 2)
    		this.earth += amount;
    	if(num == 3)
    		this.air += amount;
    }
    
    public void randomlyMutate()
    {
    	Random r = this.worldObj.rand;
    	if(r.nextFloat() <= mutatuinChance)
    	{
    		this.mutate(r.nextInt(4), r.nextInt(3)-r.nextInt(3));
    	}
    }
    
    public boolean mutate(int element, int amount)
    {
    	if(this.getElementByNum(element)+amount <= 100 && this.getElementByNum(element)+amount >= 0)
    	{
    		this.setElementByNum(element, amount);
    	}
    	return false;
    }
    
    public int getDominant()
    {
    	if(this.fire > this.water && this.fire > this.earth && this.fire > this.air)
    	{
    		return 0;
    	}
    	if(this.water > this.fire && this.water > this.earth && this.water > this.air)
    	{
    		return 1;
    	}
    	if(this.earth > this.water && this.earth > this.fire && this.earth > this.air)
    	{
    		return 2;
    	}
    	if(this.air > this.fire && this.air > this.earth && this.air > this.water)
    	{
    		return 3;
    	}
    	return -1;
    }
	
	public void updateEntity() 
	{
		int metadata = this.worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
		
		if(metadata == 1)
		{
			Block b = this.worldObj.getBlock(xCoord, yCoord-1, zCoord);
			if(!b.isBlockSolid(worldObj, xCoord, yCoord-1, zCoord, 0))
			{
				this.worldObj.getBlock(xCoord, yCoord, zCoord).dropBlockAsItem(getWorldObj(), xCoord, yCoord, zCoord, metadata, 0);
				this.worldObj.setBlockToAir(xCoord, yCoord, zCoord);
			}
		}
		
		if(metadata == 0)
		{
			Block b = this.worldObj.getBlock(xCoord, yCoord+1, zCoord);
			if(!b.isBlockSolid(worldObj, xCoord, yCoord+1, zCoord, 1))
			{
				this.worldObj.getBlock(xCoord, yCoord, zCoord).dropBlockAsItem(getWorldObj(), xCoord, yCoord, zCoord, metadata, 0);
				this.worldObj.setBlockToAir(xCoord, yCoord, zCoord);
			}
		}
		
		if(metadata == 2)
		{
			Block b = this.worldObj.getBlock(xCoord, yCoord, zCoord+1);
			if(!b.isBlockSolid(worldObj, xCoord, yCoord, zCoord+1, 3))
			{
				this.worldObj.getBlock(xCoord, yCoord, zCoord).dropBlockAsItem(getWorldObj(), xCoord, yCoord, zCoord, metadata, 0);
				this.worldObj.setBlockToAir(xCoord, yCoord, zCoord);
			}
		}
		
		if(metadata == 3)
		{
			Block b = this.worldObj.getBlock(xCoord, yCoord, zCoord-1);
			if(!b.isBlockSolid(worldObj, xCoord, yCoord, zCoord-1, 2))
			{
				this.worldObj.getBlock(xCoord, yCoord, zCoord).dropBlockAsItem(getWorldObj(), xCoord, yCoord, zCoord, metadata, 0);
				this.worldObj.setBlockToAir(xCoord, yCoord, zCoord);
			}
		}
		
		if(metadata == 4)
		{
			Block b = this.worldObj.getBlock(xCoord+1, yCoord, zCoord);
			if(!b.isBlockSolid(worldObj, xCoord+1, yCoord, zCoord, 5))
			{
				this.worldObj.getBlock(xCoord, yCoord, zCoord).dropBlockAsItem(getWorldObj(), xCoord, yCoord, zCoord, metadata, 0);
				this.worldObj.setBlockToAir(xCoord, yCoord, zCoord);
			}
		}
		
		if(metadata == 5)
		{
			Block b = this.worldObj.getBlock(xCoord-1, yCoord, zCoord);
			if(!b.isBlockSolid(worldObj, xCoord-1, yCoord, zCoord, 4))
			{
				this.worldObj.getBlock(xCoord, yCoord, zCoord).dropBlockAsItem(getWorldObj(), xCoord, yCoord, zCoord, metadata, 0);
				this.worldObj.setBlockToAir(xCoord, yCoord, zCoord);
			}
		}
		
		if(this.size < 100)
		{
			this.worldObj.spawnParticle("enchantmenttable", this.xCoord+this.worldObj.rand.nextFloat(),this.yCoord+1,this.zCoord+this.worldObj.rand.nextFloat(), 0, 0, 0);
			if(!this.worldObj.isRemote)
			{
	    		this.size += 0.002F*growthModifier;
	    			randomlyMutate();
			}
		}
		//Sending the sync packets to the CLIENT. 
		if(syncTick == 0)
		{
			if(!this.worldObj.isRemote)
				MiscUtils.sendPacketToAllAround(worldObj, getDescriptionPacket(), xCoord, yCoord, zCoord, this.worldObj.provider.dimensionId, 128);
			syncTick = 10;
		}else
			--this.syncTick;
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
	    	String[] cfgArrayString = cfg.getStringList("ElementalCrystalSettings", "tileentities", new String[]{
	    			"Chance to mutate per tick:0.001",
	    			"Growth per tick modifier(crystal grows at 0.2% per tick):1.0"
	    			},"");
	    	String dataString="";
	    	
	    	for(int i = 0; i < cfgArrayString.length; ++i)
	    		dataString+="||"+cfgArrayString[i];
	    	
	    	DummyData[] data = DataStorage.parseData(dataString);
	    	
	    	mutatuinChance = Float.parseFloat(data[0].fieldValue);
	    	growthModifier = Float.parseFloat(data[1].fieldValue);
	    	
	    	cfg.save();
    	}catch(Exception e)
    	{
    		return;
    	}
    }
}
