package ec3.common.tile;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.Iterator;
import java.util.List;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import ec3.api.ApiCore;
import ec3.api.ITEHasMRU;
import ec3.common.block.BlocksCore;
import ec3.common.item.ItemBoundGem;
import ec3.common.mod.EssentialCraftCore;
import ec3.utils.common.ECUtils;
import DummyCore.Utils.Coord3D;
import DummyCore.Utils.DataStorage;
import DummyCore.Utils.DummyData;
import DummyCore.Utils.ITEHasGameData;
import DummyCore.Utils.MathUtils;
import DummyCore.Utils.MiscUtils;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.network.play.server.S1DPacketEntityEffect;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.config.Configuration;

public class TileMagicalTeleporter extends TileMRUGeneric
{
	 public int progressLevel;
		public static float cfgMaxMRU = ApiCore.DEVICE_MAX_MRU_GENERIC*10;
		public static boolean generatesCorruption = false;
		public static int genCorruption = 5;
		public static int mruUsage = 500;
		public static int teleportTime = 250;
		public static boolean allowDimensionalTeleportation = true;
	 
	 public TileMagicalTeleporter()
	 {
		 super();
		 this.setSlotsNum(2);
	 }
	 
	@Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
    	super.readFromNBT(par1NBTTagCompound);
    	progressLevel = par1NBTTagCompound.getInteger("progress");
    }
    
    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
    	super.writeToNBT(par1NBTTagCompound);
    	par1NBTTagCompound.setInteger("progress", progressLevel);
    }
    
    @Override
    public void updateEntity() 
    {
    	this.maxMRU = (int) cfgMaxMRU;
    	super.updateEntity();
    	this.spawnParticles();
    	if(!this.worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord))
    	this.tryTeleport();
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
    }
    
    public boolean isStructureCorrect()
    {
    	boolean flag = true;
    	for(int x = -2; x <= 2; ++x)
    	{
        	for(int z = -2; z <= 2; ++z)
        	{
        		if(((x == 2 || x == -2) && z == 0) || ((z == 2 || z == -2) && x == 0))
        		{
        			ItemStack s = new ItemStack(this.worldObj.getBlock(xCoord+x, yCoord, zCoord+z),this.worldObj.getBlockMetadata(xCoord+x, yCoord, zCoord+z),1);
        			ItemStack c = new ItemStack(BlocksCore.magicPlating,0,1);
        			flag = s.areItemStacksEqual(s, c);
        			if(!flag)
        			{
        				return false;
        			}
        		}else
        		{
        			if(x == 0 && z == 0)
        			{
        				
        			}else
        			{
        				flag = this.worldObj.getBlock(xCoord+x, yCoord, zCoord+z) == BlocksCore.voidStone;
        				if(!flag)
        				{
        					return false;
        				}
        			}
        		}
        	}
    	}
    	flag = this.worldObj.getBlock(xCoord+1, yCoord+1, zCoord+2) == BlocksCore.voidStone && this.worldObj.getBlock(xCoord+1, yCoord+2, zCoord+2) == BlocksCore.voidStone &&
    			this.worldObj.getBlock(xCoord+2, yCoord+1, zCoord+1) == BlocksCore.voidStone && this.worldObj.getBlock(xCoord+2, yCoord+2, zCoord+1) == BlocksCore.voidStone &&  
    			this.worldObj.getBlock(xCoord-2, yCoord+1, zCoord+1) == BlocksCore.voidStone && this.worldObj.getBlock(xCoord-2, yCoord+2, zCoord+1) == BlocksCore.voidStone &&
    			this.worldObj.getBlock(xCoord-1, yCoord+1, zCoord+2) == BlocksCore.voidStone && this.worldObj.getBlock(xCoord-1, yCoord+2, zCoord+2) == BlocksCore.voidStone &&
    			this.worldObj.getBlock(xCoord+2, yCoord+1, zCoord-1) == BlocksCore.voidStone && this.worldObj.getBlock(xCoord+2, yCoord+2, zCoord-1) == BlocksCore.voidStone &&
    			this.worldObj.getBlock(xCoord+1, yCoord+1, zCoord-2) == BlocksCore.voidStone && this.worldObj.getBlock(xCoord+1, yCoord+2, zCoord-2) == BlocksCore.voidStone &&
    			this.worldObj.getBlock(xCoord-1, yCoord+1, zCoord-2) == BlocksCore.voidStone && this.worldObj.getBlock(xCoord-1, yCoord+2, zCoord-2) == BlocksCore.voidStone &&
    			this.worldObj.getBlock(xCoord-2, yCoord+1, zCoord-1) == BlocksCore.voidStone && this.worldObj.getBlock(xCoord-2, yCoord+2, zCoord-1) == BlocksCore.voidStone;
    	return flag;
    }
    
    public void tryTeleport()
    {
    	if(this.hasRequiredItemToTeleport() && this.hasPlayer() && this.getMRU() >= this.getTPCost() && this.isStructureCorrect())
    	{
    		EntityPlayer player = this.getPlayer();
    		++this.progressLevel;
    		this.worldObj.playSoundAtEntity(player, "minecart.base", 0.1F, 0.8F+((float)this.progressLevel/teleportTime));
    		if(this.progressLevel >= teleportTime)
    		{
				if(generatesCorruption)
					ECUtils.increaseCorruptionAt(worldObj, xCoord, yCoord, zCoord, this.worldObj.rand.nextInt(genCorruption));
    			int[] tpCoords = this.getCoordsToTP();
    			if(!this.worldObj.isRemote)
    			{
    				this.setMRU(this.getMRU()-getTPCost());
    				for(int i = 0; i < 20; ++i)
    				{
    					this.worldObj.playSoundAtEntity(player, "fireworks.largeBlast_far", 1.0F, 0.5F+MathUtils.randomFloat(this.worldObj.rand));
    				}
    				int currentPlayerDim = player.dimension;
    				int newDim = this.getDimensionToTP();
    				if(currentPlayerDim != newDim && !player.worldObj.isRemote && allowDimensionalTeleportation)
    				{
    					MinecraftServer mcServer = MinecraftServer.getServer();
    					EntityPlayerMP p_72356_1_ = (EntityPlayerMP) player;
    					int j = currentPlayerDim;
    			        WorldServer worldserver = mcServer.worldServerForDimension(currentPlayerDim);
    			        p_72356_1_.dimension = newDim;
    			        WorldServer worldserver1 = mcServer.worldServerForDimension(newDim);
    			        p_72356_1_.playerNetServerHandler.sendPacket(new S07PacketRespawn(p_72356_1_.dimension, p_72356_1_.worldObj.difficultySetting, p_72356_1_.worldObj.getWorldInfo().getTerrainType(), p_72356_1_.theItemInWorldManager.getGameType()));
    			        worldserver.removePlayerEntityDangerously(p_72356_1_);
    			        p_72356_1_.isDead = false;
    			        this.transferEntityToWorld(p_72356_1_, j, worldserver, worldserver1);
    			        mcServer.getConfigurationManager().func_72375_a(p_72356_1_, worldserver);
    			        p_72356_1_.playerNetServerHandler.setPlayerLocation(p_72356_1_.posX, p_72356_1_.posY, p_72356_1_.posZ, p_72356_1_.rotationYaw, p_72356_1_.rotationPitch);
    			        p_72356_1_.theItemInWorldManager.setWorld(worldserver1);
    			        mcServer.getConfigurationManager().updateTimeAndWeatherForPlayer(p_72356_1_, worldserver1);
    			        mcServer.getConfigurationManager().syncPlayerInventory(p_72356_1_);
    			        Iterator iterator = p_72356_1_.getActivePotionEffects().iterator();

    			        while (iterator.hasNext())
    			        {
    			            PotionEffect potioneffect = (PotionEffect)iterator.next();
    			            p_72356_1_.playerNetServerHandler.sendPacket(new S1DPacketEntityEffect(p_72356_1_.getEntityId(), potioneffect));
    			        }
    			        FMLCommonHandler.instance().firePlayerChangedDimensionEvent(p_72356_1_, j, newDim);
    				}
    				player.setPositionAndUpdate(tpCoords[0]+0.5, tpCoords[1]+1.5, tpCoords[2]+0.5);
    				for(int i = 0; i < 20; ++i)
    				{
    					player.worldObj.playSoundAtEntity(player, "fireworks.largeBlast", 1.0F, 0.5F+MathUtils.randomFloat(this.worldObj.rand));
    				}
    				
    			}
    			this.progressLevel = 0;
    		}
    	}else
    	{
    		this.progressLevel = 0;
    	}
    }
    
    public void transferEntityToWorld(Entity p_82448_1_, int p_82448_2_, WorldServer p_82448_3_, WorldServer p_82448_4_)
    {
        WorldProvider pOld = p_82448_3_.provider;
        WorldProvider pNew = p_82448_4_.provider;
        double moveFactor = pOld.getMovementFactor() / pNew.getMovementFactor();
        double d0 = p_82448_1_.posX * moveFactor;
        double d1 = p_82448_1_.posZ * moveFactor;
        double d3 = p_82448_1_.posX;
        double d4 = p_82448_1_.posY;
        double d5 = p_82448_1_.posZ;
        float f = p_82448_1_.rotationYaw;
        p_82448_3_.theProfiler.startSection("moving");
        if (p_82448_1_.dimension == 1)
        {
            ChunkCoordinates chunkcoordinates;

            if (p_82448_2_ == 1)
            {
                chunkcoordinates = p_82448_4_.getSpawnPoint();
            }
            else
            {
                chunkcoordinates = p_82448_4_.getEntrancePortalLocation();
            }

            d0 = (double)chunkcoordinates.posX;
            p_82448_1_.posY = (double)chunkcoordinates.posY;
            d1 = (double)chunkcoordinates.posZ;
            p_82448_1_.setLocationAndAngles(d0, p_82448_1_.posY, d1, 90.0F, 0.0F);

            if (p_82448_1_.isEntityAlive())
            {
                p_82448_3_.updateEntityWithOptionalForce(p_82448_1_, false);
            }
        }

        p_82448_3_.theProfiler.endSection();

        if (p_82448_2_ != 1)
        {
            p_82448_3_.theProfiler.startSection("placing");
            d0 = (double)MathHelper.clamp_int((int)d0, -29999872, 29999872);
            d1 = (double)MathHelper.clamp_int((int)d1, -29999872, 29999872);

            if (p_82448_1_.isEntityAlive())
            {
                p_82448_1_.setLocationAndAngles(d0, p_82448_1_.posY, d1, p_82448_1_.rotationYaw, p_82448_1_.rotationPitch);
                p_82448_4_.spawnEntityInWorld(p_82448_1_);
                p_82448_4_.updateEntityWithOptionalForce(p_82448_1_, false);
            }

            p_82448_3_.theProfiler.endSection();
        }

        p_82448_1_.setWorld(p_82448_4_);
    }
    
    public int getTPCost()
    {
    	int[] tpCoords = this.getCoordsToTP();
    	int dim = this.getDimensionToTP();
    	int diffX = (int) MathUtils.getDifference(xCoord, tpCoords[0]);
    	int diffY = (int) MathUtils.getDifference(yCoord, tpCoords[1]);
    	int diffZ = (int) MathUtils.getDifference(zCoord, tpCoords[2]);
    	float mainDiff = (diffX+diffY+diffZ)/3;
    	int ret = (int)(mruUsage*mainDiff);
    	if(ret > cfgMaxMRU) ret = (int) cfgMaxMRU;
    	if(this.worldObj.provider.dimensionId != dim)
    		ret = (int) cfgMaxMRU;
    	return ret;
    }
    
    public EntityPlayer getPlayer()
    {
    	List<EntityPlayer> l = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord+1, yCoord+2, zCoord+1));
    	if(!l.isEmpty())
    	{
    		return l.get(0);
    	}
    	return null;
    }
    
    public boolean hasPlayer()
    {
    	List<EntityPlayer> l = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord+1, yCoord+2, zCoord+1));
    	if(!l.isEmpty())
    	{
    		return true;
    	}
    	return false;
    }
    
    public int getDimensionToTP()
    {
    	ItemStack s = this.getStackInSlot(1);
    	return MiscUtils.getStackTag(s).getInteger("dim");
    }
    
    public int[] getCoordsToTP()
    {
    	ItemStack s = this.getStackInSlot(1);
    	return MiscUtils.getStackTag(s).getIntArray("pos");
    }

    
    public boolean hasRequiredItemToTeleport()
    {
    	return this.getStackInSlot(1) != null && this.getStackInSlot(1).getItem() instanceof ItemBoundGem && this.getStackInSlot(1).getTagCompound() != null;
    }
    
    public void spawnParticles()
    {
    	if(this.hasPlayer())
    	{
    		EntityPlayer p = this.getPlayer();
    		for(int i = 0; i < (this.progressLevel/5); ++i)
    		{
    			this.worldObj.spawnParticle("reddust", p.posX+MathUtils.randomFloat(this.worldObj.rand)/2, p.posY+MathUtils.randomFloat(this.worldObj.rand)*2-1, p.posZ+MathUtils.randomFloat(this.worldObj.rand)/2, 0, 0, 1);
    			this.worldObj.spawnParticle("reddust", this.xCoord+0.5+MathUtils.randomFloat(this.worldObj.rand)*2, this.yCoord+3,this.zCoord+0.5+MathUtils.randomFloat(this.worldObj.rand)*2, 0, 0, 1);
    			this.worldObj.spawnParticle("reddust", this.xCoord+0.5+2, this.yCoord+2+MathUtils.randomFloat(this.worldObj.rand),this.zCoord+0.5+MathUtils.randomFloat(this.worldObj.rand)*2, 0, 0, 1);
    			this.worldObj.spawnParticle("reddust", this.xCoord+0.5-2, this.yCoord+2+MathUtils.randomFloat(this.worldObj.rand),this.zCoord+0.5+MathUtils.randomFloat(this.worldObj.rand)*2, 0, 0, 1);
    			this.worldObj.spawnParticle("reddust", this.xCoord+0.5+MathUtils.randomFloat(this.worldObj.rand)*2, this.yCoord+2+MathUtils.randomFloat(this.worldObj.rand),this.zCoord+0.5-2, 0, 0, 1);
    			this.worldObj.spawnParticle("reddust", this.xCoord+0.5+MathUtils.randomFloat(this.worldObj.rand)*2, this.yCoord+2+MathUtils.randomFloat(this.worldObj.rand),this.zCoord+0.5+2, 0, 0, 1);
    		}
    		for(int i = 0; i < 4; ++i)
    		{

    		}
    	}
    	if(this.isStructureCorrect())
    	{
    		//for(int i = 0; i < 100; ++i)
    		{
    			EssentialCraftCore.proxy.spawnParticle("cSpellFX", xCoord+0.5F+MathUtils.randomFloat(this.worldObj.rand)*3, yCoord+1, zCoord+0.5F+MathUtils.randomFloat(this.worldObj.rand)*3, 0,2, 0);
    		}
    	}
    }
    
    public static void setupConfig(Configuration cfg)
    {
    	try
    	{
	    	cfg.load();
	    	String[] cfgArrayString = cfg.getStringList("MagicalTeleporterSettings", "tileentities", new String[]{
	    			"Max MRU:"+ApiCore.DEVICE_MAX_MRU_GENERIC*10,
	    			"MRU Usage per block:500",
	    			"Can this device actually generate corruption:false",
	    			"The amount of corruption generated each tick(do not set to 0!):5",
	    			"Ticks required for the teleporter to teleport an entity:250",
	    			"Allow teleportation between dimensions:true"
	    			},"");
	    	String dataString="";
	    	
	    	for(int i = 0; i < cfgArrayString.length; ++i)
	    		dataString+="||"+cfgArrayString[i];
	    	
	    	DummyData[] data = DataStorage.parseData(dataString);
	    	
	    	mruUsage = Integer.parseInt(data[1].fieldValue);
	    	cfgMaxMRU = Float.parseFloat(data[0].fieldValue);
	    	generatesCorruption = Boolean.parseBoolean(data[2].fieldValue);
	    	genCorruption = Integer.parseInt(data[3].fieldValue);
	    	teleportTime = Integer.parseInt(data[4].fieldValue);
	    	allowDimensionalTeleportation = Boolean.parseBoolean(data[5].fieldValue);
	    	
	    	cfg.save();
    	}catch(Exception e)
    	{
    		return;
    	}
    }
	
}
