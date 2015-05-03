package ec3.common.tile;

import java.util.List;

import cpw.mods.fml.common.eventhandler.Event.Result;
import net.minecraft.block.Block;
import net.minecraft.block.BlockMobSpawner;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.SpawnerAnimals;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.ForgeEventFactory;
import DummyCore.Utils.DataStorage;
import DummyCore.Utils.DummyData;
import DummyCore.Utils.MathUtils;
import ec3.api.ApiCore;
import ec3.api.ITEHasMRU;
import ec3.common.item.ItemBoundGem;
import ec3.utils.common.ECUtils;



public class TileDarknessObelisk extends TileMRUGeneric
{
	public static float cfgMaxMRU = ApiCore.DEVICE_MAX_MRU_GENERIC;
	public static boolean generatesCorruption = false;
	public static int genCorruption = 30;
	public static int mruUsage = 500;
	public static float worldSpawnChance = 0.1F;
	public static int mobSpanwerDelay = 100;
	public static boolean enableMobSpawners = true;
	public static int mobSpawnerRadius = 3,
					  worldSpawnerRadius = 8;
	
	public TileDarknessObelisk()
	{
		 super();
		this.maxMRU = (int)cfgMaxMRU;
		this.setSlotsNum(2);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void updateEntity()
	{
		if(!this.worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord))
		{
			if(this.getStackInSlot(1) == null || !(Block.getBlockFromItem(this.getStackInSlot(1).getItem()) instanceof BlockMobSpawner))
			{
				BiomeGenBase biome = this.worldObj.getBiomeGenForCoords(xCoord, zCoord);
				List l = biome.getSpawnableList(EnumCreatureType.monster);
				if(l != null && !l.isEmpty() && !this.worldObj.isRemote && this.worldObj.rand.nextFloat() < worldSpawnChance && this.getMRU() - mruUsage > 0)
				{
	                BiomeGenBase.SpawnListEntry spawnlistentry = null;
	                IEntityLivingData ientitylivingdata = null;
	                int rndOffsetX = (int) (xCoord+MathUtils.randomDouble(this.worldObj.rand)*worldSpawnerRadius);
	                int rndOffsetY = (int) (yCoord+MathUtils.randomDouble(this.worldObj.rand)*worldSpawnerRadius);
	                int rndOffsetZ = (int) (zCoord+MathUtils.randomDouble(this.worldObj.rand)*worldSpawnerRadius);
	                if(SpawnerAnimals.canCreatureTypeSpawnAtLocation(EnumCreatureType.monster, this.worldObj, rndOffsetX, rndOffsetY, rndOffsetZ))
	                {
	                	WorldServer wrld = (WorldServer) this.worldObj;
	                	spawnlistentry = wrld.spawnRandomCreature(EnumCreatureType.monster, rndOffsetX, rndOffsetY, rndOffsetZ);
	                	if(spawnlistentry != null)
	                	{
	                        EntityLiving entityliving;
	
	                        try
	                        {
	                            entityliving = (EntityLiving)spawnlistentry.entityClass.getConstructor(new Class[] {World.class}).newInstance(new Object[] {wrld});
	                            entityliving.setLocationAndAngles((double)rndOffsetX+0.5F, (double)rndOffsetY, (double)rndOffsetZ+0.5D, wrld.rand.nextFloat() * 360.0F, 0.0F);
	                            Result canSpawn = ForgeEventFactory.canEntitySpawn(entityliving, wrld, (float)rndOffsetX+0.5F, (float)rndOffsetY, (float)rndOffsetZ+0.5F);
	                            if (canSpawn == Result.ALLOW || (canSpawn == Result.DEFAULT && entityliving.getCanSpawnHere()))
	                            {
	                                wrld.spawnEntityInWorld(entityliving);
	                                this.setMRU(this.getMRU() - mruUsage);
			        				if(generatesCorruption)
			        					ECUtils.increaseCorruptionAt(worldObj, xCoord, yCoord, zCoord, this.worldObj.rand.nextInt(genCorruption));
	                                if (!ForgeEventFactory.doSpecialSpawn(entityliving, wrld, (float)rndOffsetX+0.5F, (float)rndOffsetY, (float)rndOffsetZ+0.5F))
	                                {
	                                    ientitylivingdata = entityliving.onSpawnWithEgg(ientitylivingdata);
	                                }
	                            }
	                        }
	                        catch (Exception exception)
	                        {
	                            exception.printStackTrace();
	                            return;
	                        }
	                	}
	                }
				}
			}else
			{
				if(Block.getBlockFromItem(this.getStackInSlot(1).getItem()) instanceof BlockMobSpawner && enableMobSpawners)
				{
					if(this.innerRotation >= mobSpanwerDelay && this.getMRU() - mruUsage > 0)
					{
						this.innerRotation = 0;
						int metadata = this.getStackInSlot(1).getItemDamage();
						Entity base = EntityList.createEntityByID(metadata, worldObj);
						if(base instanceof EntityLiving)
						{
							EntityLiving entityliving = (EntityLiving) base;
			                int rndOffsetX = (int) (xCoord+MathUtils.randomDouble(this.worldObj.rand)*mobSpawnerRadius);
			                int rndOffsetY = (int) (yCoord+MathUtils.randomDouble(this.worldObj.rand));
			                int rndOffsetZ = (int) (zCoord+MathUtils.randomDouble(this.worldObj.rand)*mobSpawnerRadius);
			                entityliving.setLocationAndAngles(rndOffsetX+0.5D, rndOffsetY, rndOffsetZ+0.5D, this.worldObj.rand.nextFloat() * 360.0F, 0.0F);
		                    if (entityliving.getCanSpawnHere())
		                    {
		                    	if(!this.worldObj.isRemote)
		                    		this.worldObj.spawnEntityInWorld(entityliving);
		                    	
		                        this.worldObj.playAuxSFX(2004, this.xCoord, this.yCoord, this.zCoord, 0);
	
		                        if (entityliving != null)
		                        {
		                            entityliving.spawnExplosionParticle();
		                        }
		                        this.setMRU(this.getMRU()-mruUsage);
		        				if(generatesCorruption)
		        					ECUtils.increaseCorruptionAt(worldObj, xCoord, yCoord, zCoord, this.worldObj.rand.nextInt(genCorruption));
		                    }
						}
					}
				}
			}
		}
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
		super.updateEntity();
	}
	
    public static void setupConfig(Configuration cfg)
    {
    	try
    	{
	    	cfg.load();
	    	String[] cfgArrayString = cfg.getStringList("DarknessObeliskSettings", "tileentities", new String[]{
	    			"Max MRU:"+ApiCore.DEVICE_MAX_MRU_GENERIC,
	    			"MRU Usage:500",
	    			"Ticks required to try to create an entity:100",
	    			"Can this device actually generate corruption:false",
	    			"The amount of corruption generated each tick(do not set to 0!):30",
	    			"A chance per tick to try spawning a mob without a spawner:0.1",
	    			"Enable mob spawning using spawner:true",
	    			"Radius for a spawner:3",
	    			"Radius for no spawner:8"
	    			},"");
	    	String dataString="";
	    	
	    	for(int i = 0; i < cfgArrayString.length; ++i)
	    		dataString+="||"+cfgArrayString[i];
	    	
	    	DummyData[] data = DataStorage.parseData(dataString);
	    	
	    	mruUsage = Integer.parseInt(data[1].fieldValue);
	    	mobSpanwerDelay = Integer.parseInt(data[2].fieldValue);
	    	cfgMaxMRU = Float.parseFloat(data[0].fieldValue);
	    	generatesCorruption = Boolean.parseBoolean(data[3].fieldValue);
	    	genCorruption = Integer.parseInt(data[4].fieldValue);
	    	worldSpawnChance = Float.parseFloat(data[5].fieldValue);
	    	enableMobSpawners = Boolean.parseBoolean(data[6].fieldValue);
	    	mobSpawnerRadius = Integer.parseInt(data[7].fieldValue);
	    	worldSpawnerRadius = Integer.parseInt(data[8].fieldValue);
	    	
	    	cfg.save();
    	}catch(Exception e)
    	{
    		return;
    	}
    }
}