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
import net.minecraftforge.oredict.OreDictionary;
import ec3.api.ApiCore;
import ec3.common.entity.EntitySolarBeam;
import ec3.common.mod.EssentialCraftCore;
import ec3.utils.common.ECUtils;

public class TileFlowerBurner extends TileMRUGeneric{
	
	public Coord3D burnedFlower;
	public int burnTime = 0;
	
	public TileFlowerBurner()
	{
		this.balance = -1;
		this.maxMRU = (int) ApiCore.GENERATOR_MAX_MRU_GENERIC;
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
					burnTime = 20*30;
				}
				if(name.toLowerCase().contains("sapling") || b instanceof BlockSapling)
				{
					burnedFlower = new Coord3D(xCoord+offsetX, yCoord, zCoord+offsetZ);
					burnTime = 30*30;
				}
				if(name.toLowerCase().contains("tallgrass") || b instanceof BlockTallGrass)
				{
					burnedFlower = new Coord3D(xCoord+offsetX, yCoord, zCoord+offsetZ);
					burnTime = 5*30;
				}
			}else
			{
				if(this.burnedFlower != null)
				{
					--burnTime;
					int mruGenerated = 10;
					this.setMRU((int) (this.getMRU()+mruGenerated));
					if(this.getMRU() > this.getMaxMRU())
						this.setMRU(this.getMaxMRU());
					if(burnTime <= 0)
					{
						this.worldObj.setBlock((int)burnedFlower.x, (int)burnedFlower.y, (int)burnedFlower.z, Blocks.deadbush, 0, 3);
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
					this.worldObj.spawnParticle("smoke", burnedFlower.x+0.5F+MathUtils.randomFloat(this.worldObj.rand)*0.3F, burnedFlower.y+0.1F+this.worldObj.rand.nextFloat()/2, burnedFlower.z+0.5F+MathUtils.randomFloat(this.worldObj.rand)*0.3F, 0, 0, 0);
			}
		}
		this.worldObj.spawnParticle("flame", xCoord+0.5F+MathUtils.randomFloat(this.worldObj.rand)*0.3F, yCoord+0.1F+this.worldObj.rand.nextFloat()/2, zCoord+0.5F+MathUtils.randomFloat(this.worldObj.rand)*0.3F, 0, 0, 0);
		this.worldObj.spawnParticle("smoke", xCoord+0.5F+MathUtils.randomFloat(this.worldObj.rand)*0.3F, yCoord+0.1F+this.worldObj.rand.nextFloat()/2, zCoord+0.5F+MathUtils.randomFloat(this.worldObj.rand)*0.3F, 0, 0, 0);
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

}
