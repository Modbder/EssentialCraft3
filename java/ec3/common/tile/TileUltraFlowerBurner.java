package ec3.common.tile;

import java.util.List;

import DummyCore.Utils.Coord3D;
import DummyCore.Utils.DataStorage;
import DummyCore.Utils.DummyData;
import DummyCore.Utils.MathUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraftforge.oredict.OreDictionary;
import ec3.api.ApiCore;
import ec3.common.mod.EssentialCraftCore;

public class TileUltraFlowerBurner extends TileMRUGeneric{
	
	public Coord3D burnedFlower;
	public int burnTime = 0, mruProduced = 0;
	
	public TileUltraFlowerBurner()
	{
		 super();
		this.balance = -1;
		this.maxMRU = (int) ApiCore.GENERATOR_MAX_MRU_GENERIC*10;
	}
	
	public boolean canGenerateMRU()
	{
		return false;
	}
	
	@SuppressWarnings("unchecked")
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
				if(this.worldObj.getWorldTime()%80 == 0)
				{
					List<EntityItem> sapplings = this.worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(xCoord-16, yCoord-0.5D, zCoord-16, xCoord+16, yCoord+1.5D, zCoord+16));
					if(!sapplings.isEmpty())
					{
						for(int i = 0; i < sapplings.size(); ++i)
						{
							EntityItem sappling = sapplings.get(i);
							if(!sappling.isDead)
							{
								ItemStack sStk = sappling.getEntityItem();
								if(sStk != null)
								{
									int[] ids = OreDictionary.getOreIDs(sStk);
									String name = "";
									if(ids != null && ids.length > 0)
									{
										for(int i1 = 0; i1 < ids.length; ++i1)
										{
											int oreDictID = ids[i1];
											String n = OreDictionary.getOreName(oreDictID);
											if(n != null && !n.isEmpty())
											{
												name = n;
												break;
											}
										}
									}
									if(name.contains("treeSapling"))
									{
										int pBIDX = MathHelper.floor_double(sappling.posX);
										int pBIDY = MathHelper.floor_double(sappling.posY);
										int pBIDZ = MathHelper.floor_double(sappling.posZ);
										Block b = this.worldObj.getBlock(pBIDX, pBIDY, pBIDZ);
										if(b.isAir(worldObj, pBIDX, pBIDY, pBIDZ))
										{
											Block sBlk = Block.getBlockFromItem(sStk.getItem());
											if(sBlk != null)
											{
												this.worldObj.setBlock(pBIDX, pBIDY, pBIDZ, sBlk, sStk.getItemDamage(), 3);
												sappling.setDead();
											}
										}
									}
								}
							}
						}
					}
				}
				if(this.burnedFlower == null && this.getMRU()<this.getMaxMRU())
				{
					int offsetX = (int) (MathUtils.randomDouble(this.worldObj.rand)*16);
					int offsetZ = (int) (MathUtils.randomDouble(this.worldObj.rand)*16);
					y:for(int y = 32; y >= 0; --y)
					{
						Block b = this.worldObj.getBlock(xCoord+offsetX, yCoord+y, zCoord+offsetZ);
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
							burnedFlower = new Coord3D(xCoord+offsetX, yCoord+y, zCoord+offsetZ);
							burnTime = 30*20;
							mruProduced = 60;
						}
						if(name.toLowerCase().contains("leaves") || b.isLeaves(worldObj, xCoord+offsetX, yCoord, zCoord+offsetZ))
						{
							burnedFlower = new Coord3D(xCoord+offsetX, yCoord+y, zCoord+offsetZ);
							burnTime = 15*20;
							mruProduced = 70;
						}
						if(name.toLowerCase().contains("tallgrass") || b instanceof BlockTallGrass || b instanceof BlockDoublePlant)
						{
							burnedFlower = new Coord3D(xCoord+offsetX, yCoord+y, zCoord+offsetZ);
							burnTime = 5*20;
							mruProduced = 20;
						}
						if(name.toLowerCase().contains("logWood") || b instanceof BlockLog)
						{
							burnedFlower = new Coord3D(xCoord+offsetX, yCoord+y, zCoord+offsetZ);
							burnTime = 120*20;
							mruProduced = 100;
						}
						if(mruProduced > 0)
							break y;
					}
				}else
				{
					if(this.burnedFlower != null)
					{
						--burnTime;
						int mruGenerated = mruProduced;
						this.setMRU((int) (this.getMRU()+mruGenerated));
						if(this.getMRU() > this.getMaxMRU())
							this.setMRU(this.getMaxMRU());
						if(burnTime <= 0)
						{
							if(this.worldObj.getBlock((int)burnedFlower.x, (int)burnedFlower.y, (int)burnedFlower.z).isLeaves(worldObj, (int)burnedFlower.x, (int)burnedFlower.y, (int)burnedFlower.z))
							{
								Item droppedSapling = this.worldObj.getBlock((int)burnedFlower.x, (int)burnedFlower.y, (int)burnedFlower.z).getItemDropped(this.worldObj.getBlockMetadata((int)burnedFlower.x, (int)burnedFlower.y, (int)burnedFlower.z), this.worldObj.rand, 0);
								if(droppedSapling != null)
								{
									if(this.worldObj.rand.nextFloat() < 0.05F)
									{
										ItemStack saplingStk = new ItemStack(droppedSapling,1,this.worldObj.getBlock((int)burnedFlower.x, (int)burnedFlower.y, (int)burnedFlower.z).damageDropped(this.worldObj.getBlockMetadata((int)burnedFlower.x, (int)burnedFlower.y, (int)burnedFlower.z)));
										EntityItem sapling = new EntityItem(this.worldObj, (int)burnedFlower.x+0.5D, (int)burnedFlower.y+0.5D, (int)burnedFlower.z+0.5D, saplingStk);
										this.worldObj.spawnEntityInWorld(sapling);
									}
								}
							}
							this.worldObj.setBlockToAir((int)burnedFlower.x, (int)burnedFlower.y, (int)burnedFlower.z);
							burnedFlower = null;
							mruProduced = 0;
						}
					}
				}
			}
			if(this.burnedFlower != null && burnTime > 0)
			{
				EssentialCraftCore.proxy.FlameFX(burnedFlower.x+0.5F+MathUtils.randomFloat(this.worldObj.rand)*0.3F, burnedFlower.y+0.1F+this.worldObj.rand.nextFloat()/2, burnedFlower.z+0.5F+MathUtils.randomFloat(this.worldObj.rand)*0.3F, 0, 0, 0, 1, 1, 1, 1);
				EssentialCraftCore.proxy.FlameFX(burnedFlower.x+0.5F+MathUtils.randomFloat(this.worldObj.rand)*0.3F, burnedFlower.y+0.1F+this.worldObj.rand.nextFloat()/2, burnedFlower.z+0.5F+MathUtils.randomFloat(this.worldObj.rand)*0.3F, ((xCoord-0.5D)-burnedFlower.x+0.5F+MathUtils.randomFloat(this.worldObj.rand)*0.3F)/20, (yCoord-burnedFlower.y+0.5F+MathUtils.randomFloat(this.worldObj.rand)*0.3F)/20, ((zCoord-0.5D)-burnedFlower.z+0.5F+MathUtils.randomFloat(this.worldObj.rand)*0.3F)/20, 1, 1, 1, 1);
				--burnTime;
				if(burnTime <= 0)
				{
					for(int t = 0; t < 600; ++t)
						EssentialCraftCore.proxy.SmokeFX(burnedFlower.x+0.5F+MathUtils.randomFloat(this.worldObj.rand)*0.3F, burnedFlower.y+0.1F+this.worldObj.rand.nextFloat()/2, burnedFlower.z+0.5F+MathUtils.randomFloat(this.worldObj.rand)*0.3F, 0, 0, 0,1);
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
		mruProduced = i.getInteger("genMRU");
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
		i.setInteger("genMRU", mruProduced);
    	super.writeToNBT(i);
    }

	@Override
	public int[] getOutputSlots() {
		return new int[0];
	}

}
