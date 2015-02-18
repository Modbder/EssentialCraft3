package ec3.common.tile;

import java.util.List;

import DummyCore.Utils.Coord3D;
import DummyCore.Utils.DataStorage;
import DummyCore.Utils.DummyData;
import DummyCore.Utils.MathUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.OreDictionary;
import ec3.api.ApiCore;
import ec3.common.entity.EntitySolarBeam;
import ec3.common.mod.EssentialCraftCore;
import ec3.utils.common.ECUtils;

public class TileFlowerBurner extends TileMRUGeneric{
	
	public Coord3D burnedFlower;
	public int burnTime = 0;
	
	public static float cfgMaxMRU =  ApiCore.GENERATOR_MAX_MRU_GENERIC;
	public static float cfgBalance = -1F;
	public static float mruGenerated = 10;
	public static int flowerBurnTime = 600;
	public static int saplingBurnTime = 900;
	public static int tallgrassBurnTime = 150;
	public static boolean createDeadBush = true;
	
	public TileFlowerBurner()
	{
		 super();
		this.balance = cfgBalance;
		this.maxMRU = (int)cfgMaxMRU;
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
			if(!this.worldObj.isRemote)
			{
				if(this.burnedFlower == null && this.getMRU()<this.getMaxMRU())
				{
					int offsetX = (int) (MathUtils.randomDouble(this.worldObj.rand)*8);
					int offsetZ = (int) (MathUtils.randomDouble(this.worldObj.rand)*8);
					Block b = this.worldObj.getBlock(xCoord+offsetX, yCoord, zCoord+offsetZ);
					int[] ids = OreDictionary.getOreIDs(new ItemStack(b,1,0));
					String name = "";
					if(ids != null && ids.length > 0)
					{
						for(int i = 0; i < ids.length; ++i)
						{
							int oreDictID = ids[i];
							String n = OreDictionary.getOreName(oreDictID);
							if(n != null && !n.isEmpty())
							{
								name = n;
								break;
							}
						}
					}
					if(name.toLowerCase().contains("flower") || b instanceof BlockFlower)
					{
						burnedFlower = new Coord3D(xCoord+offsetX, yCoord, zCoord+offsetZ);
						burnTime = flowerBurnTime;
					}
					if(name.toLowerCase().contains("sapling") || b instanceof BlockSapling)
					{
						burnedFlower = new Coord3D(xCoord+offsetX, yCoord, zCoord+offsetZ);
						burnTime = saplingBurnTime;
					}
					if(name.toLowerCase().contains("tallgrass") || b instanceof BlockTallGrass)
					{
						burnedFlower = new Coord3D(xCoord+offsetX, yCoord, zCoord+offsetZ);
						burnTime = tallgrassBurnTime;
					}
				}else
				{
					if(this.burnedFlower != null)
					{
						--burnTime;
						this.setMRU((int) (this.getMRU()+mruGenerated));
						if(this.getMRU() > this.getMaxMRU())
							this.setMRU(this.getMaxMRU());
						if(burnTime <= 0)
						{
							if(createDeadBush)
								this.worldObj.setBlock((int)burnedFlower.x, (int)burnedFlower.y, (int)burnedFlower.z, Blocks.deadbush, 0, 3);
							else
								this.worldObj.setBlock((int)burnedFlower.x, (int)burnedFlower.y, (int)burnedFlower.z, Blocks.air, 0, 3);
							burnedFlower = null;
						}
					}
				}
			}
			if(this.burnedFlower != null && burnTime > 0)
			{
				this.worldObj.spawnParticle("flame", burnedFlower.x+0.5F+MathUtils.randomFloat(this.worldObj.rand)*0.3F, burnedFlower.y+0.1F+this.worldObj.rand.nextFloat()/2, burnedFlower.z+0.5F+MathUtils.randomFloat(this.worldObj.rand)*0.3F, 0, 0, 0);
				--burnTime;
				if(burnTime <= 0)
				{
					for(int t = 0; t < 200; ++t)
						EssentialCraftCore.proxy.SmokeFX(burnedFlower.x+0.5F+MathUtils.randomFloat(this.worldObj.rand)*0.3F, burnedFlower.y+0.1F+this.worldObj.rand.nextFloat()/2, burnedFlower.z+0.5F+MathUtils.randomFloat(this.worldObj.rand)*0.3F, 0, 0, 0, 1);
				}
			}
		}
		
		EssentialCraftCore.proxy.FlameFX(xCoord+0.5F+MathUtils.randomFloat(this.worldObj.rand)*0.4F, yCoord+0.1F, zCoord+0.5F+MathUtils.randomFloat(this.worldObj.rand)*0.4F, 0, 0.01F, 0, 1D, 0.5D, 1, 1);
		EssentialCraftCore.proxy.FlameFX(xCoord+0.5F+MathUtils.randomFloat(this.worldObj.rand)*0.2F, yCoord+0.2F, zCoord+0.5F+MathUtils.randomFloat(this.worldObj.rand)*0.2F, 0, 0.01F, 0, 1D, 0.5D, 1, 1);
		for(int i = 0; i < 10; ++i)
			EssentialCraftCore.proxy.SmokeFX(xCoord+0.5F+MathUtils.randomFloat(this.worldObj.rand)*0.1F, yCoord+0.6F, zCoord+0.5F+MathUtils.randomFloat(this.worldObj.rand)*0.1F, 0, 0, 0,1);
	}
	
	@Override
    public void readFromNBT(NBTTagCompound i)
    {
		if(i.hasKey("coord"))
		{
			DummyData[] coordData = DataStorage.parseData(i.getString("coord"));
			burnedFlower = new Coord3D(Double.parseDouble(coordData[0].fieldValue),Double.parseDouble(coordData[1].fieldValue),Double.parseDouble(coordData[2].fieldValue));
		}
		burnTime = i.getInteger("burn");
		super.readFromNBT(i);
    }
	
	@Override
    public void writeToNBT(NBTTagCompound i)
    {
		if(burnedFlower != null)
		{
			i.setString("coord", burnedFlower.toString());
		}
		i.setInteger("burn", burnTime);
    	super.writeToNBT(i);
    }

    public static void setupConfig(Configuration cfg)
    {
    	try
    	{
	    	cfg.load();
	    	String[] cfgArrayString = cfg.getStringList("NatureFurnaceSettings", "tileentities", new String[]{
	    			"Max MRU:"+ApiCore.GENERATOR_MAX_MRU_GENERIC,
	    			"Default balance(-1 is random):-1.0",
	    			"MRU generated per tick:10",
	    			"Time required to burn a flower:600",
	    			"Time required to burn a sapling:900",
	    			"Time required to burn grass:150",
	    			"Should leave Dead Bushes:true"
	    			},"");
	    	String dataString="";
	    	
	    	for(int i = 0; i < cfgArrayString.length; ++i)
	    		dataString+="||"+cfgArrayString[i];
	    	
	    	DummyData[] data = DataStorage.parseData(dataString);
	    	
	    	cfgMaxMRU = Float.parseFloat(data[0].fieldValue);
	    	cfgBalance = Float.parseFloat(data[1].fieldValue);
	    	mruGenerated = Float.parseFloat(data[2].fieldValue);
	    	flowerBurnTime = Integer.parseInt(data[3].fieldValue);
	    	saplingBurnTime = Integer.parseInt(data[4].fieldValue);
	    	tallgrassBurnTime = Integer.parseInt(data[5].fieldValue);
	    	createDeadBush = Boolean.parseBoolean(data[6].fieldValue);
	    	
	    	cfg.save();
    	}catch(Exception e)
    	{
    		return;
    	}
    }
}
