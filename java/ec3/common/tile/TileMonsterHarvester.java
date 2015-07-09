package ec3.common.tile;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.FakePlayer;
import DummyCore.Utils.DataStorage;
import DummyCore.Utils.DummyData;
import DummyCore.Utils.MiscUtils;
import ec3.api.ApiCore;
import ec3.utils.common.ECUtils;

public class TileMonsterHarvester extends TileMRUGeneric{
	public static float rad = 12F;
	public float rotation = 0F;
	public int destrTick;
	public static float cfgMaxMRU = ApiCore.DEVICE_MAX_MRU_GENERIC;
	public static boolean generatesCorruption = false;
	public static int genCorruption = 10;
	public static int mruUsage = 100;
	public static int mobDestructionTimer = 1440;
	public static boolean allowBossDuplication = false;
	public static boolean clearCopyInventory = true;
	
	public TileMonsterHarvester()
	{
		 super();
		this.maxMRU = (int)cfgMaxMRU;
		this.setSlotsNum(6);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		ECUtils.manage(this, 0);
		if(!this.worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord))
		{
			++destrTick;
			if(destrTick >= mobDestructionTimer)
			{
				destrTick = 0;
		    	List<EntityLivingBase> lst = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(xCoord-16, yCoord-16, zCoord-16, xCoord+16, yCoord+16, zCoord+16));
		    	if(!lst.isEmpty() && !this.worldObj.isRemote)
		    	{
		    		for(int i = 0; i < lst.size(); ++i)
		    		{
		    			EntityLivingBase e = lst.get(i);
		    			if(!(e instanceof EntityPlayer))
		    			{
		    				if(getMRU() > mruUsage)
		    				{
		    					if(e instanceof IBossDisplayData && !allowBossDuplication)
		    						return;
		    					
		    					EntityLivingBase copy = (EntityLivingBase) MiscUtils.cloneEntity(e);
		    					this.worldObj.spawnEntityInWorld(copy);
		    					if(clearCopyInventory)
		    					{
		    						copy.setCurrentItemOrArmor(0, null);
		    						copy.setCurrentItemOrArmor(1, null);
		    						copy.setCurrentItemOrArmor(2, null);
		    						copy.setCurrentItemOrArmor(3, null);
		    						copy.setCurrentItemOrArmor(4, null);
		    					}
		    					FakePlayer player = new FakePlayer((WorldServer) e.worldObj, ECUtils.EC3FakePlayerProfile);
		    					ItemStack stk = this.getStackInSlot(2);
		    					if(stk != null)
		    						player.inventory.setInventorySlotContents(player.inventory.currentItem, stk.copy());
		    					copy.setHealth(0.1F);
		    					player.attackTargetEntityWithCurrentItem(copy);
		    					player.setDead();
		    					this.setMRU(this.getMRU()-mruUsage);
		    					if(copy.getHealth() > 0)
		    						copy.setDead();
		    	    			if(generatesCorruption)
		    	    				ECUtils.increaseCorruptionAt(worldObj, xCoord, yCoord, zCoord, this.worldObj.rand.nextInt(genCorruption));
		    				}
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
	    	String[] cfgArrayString = cfg.getStringList("MonsterDuplicatorSettings", "tileentities", new String[]{
	    			"Max MRU:"+ApiCore.DEVICE_MAX_MRU_GENERIC,
	    			"MRU Usage Per Mob:100",
	    			"Can this device actually generate corruption:false",
	    			"The amount of corruption generated each tick(do not set to 0!):10",
	    			"Radius to duplicate mobs within:12.0",
	    			"Ticks required to duplicate mobs:1440",
	    			"Allow duplication of bosses:false",
	    			"Remove inventory of a duplicate before killing it:true"
	    			},"");
	    	String dataString="";
	    	
	    	for(int i = 0; i < cfgArrayString.length; ++i)
	    		dataString+="||"+cfgArrayString[i];
	    	
	    	DummyData[] data = DataStorage.parseData(dataString);
	    	
	    	mruUsage = Integer.parseInt(data[1].fieldValue);
	    	cfgMaxMRU = Float.parseFloat(data[0].fieldValue);
	    	generatesCorruption = Boolean.parseBoolean(data[2].fieldValue);
	    	genCorruption = Integer.parseInt(data[3].fieldValue);
	    	rad = Float.parseFloat(data[4].fieldValue);
	    	mobDestructionTimer = Integer.parseInt(data[5].fieldValue);
	    	allowBossDuplication = Boolean.parseBoolean(data[6].fieldValue);
	    	clearCopyInventory = Boolean.parseBoolean(data[7].fieldValue);
	    	
	    	cfg.save();
    	}catch(Exception e)
    	{
    		return;
    	}
    }

	@Override
	public int[] getOutputSlots() {
		return new int[]{1,2,3,4};
	}
}
