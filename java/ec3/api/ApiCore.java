package ec3.api;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import DummyCore.Utils.DummyDistance;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;

/**
 * 
 * @author Modbder
 * @Description Just some nifty functions to help you out.
 */
public class ApiCore {
	
	public static final float GENERATOR_MAX_MRU_GENERIC = 10000F;
	
	public static final float DEVICE_MAX_MRU_GENERIC = 5000F;
	
	public static List<Item> allowsSeeingMRU = new ArrayList<Item>();
	
	public static Hashtable<Item, ArrayList<Float>> reductionsTable = new Hashtable<Item, ArrayList<Float>>();
	
	public static List<CategoryEntry> categories = new ArrayList<CategoryEntry>();
	
	public static Hashtable<String, DiscoveryEntry> discoveriesByIS = new Hashtable<String, DiscoveryEntry>();
	
	public static IPlayerData getPlayerData(EntityPlayer p)
	{
		try
		{
			Class<?> ecUtilsClass = Class.forName("ec3.utils.common.ECUtils");
			Method getData = ecUtilsClass.getMethod("getData", EntityPlayer.class);
			return IPlayerData.class.cast(getData.invoke(null, p));
			
		}catch(Exception e)
		{
			return null;
		}
	}
	
	@Deprecated
	public static int getubmru(EntityPlayer p)
	{
		return getPlayerData(p).getPlayerUBMRU();
	}
	
	@Deprecated
	public static void setubmru(EntityPlayer p, int amount)
	{
		if(!(p instanceof FakePlayer))
			getPlayerData(p).modifyUBMRU(amount);
	}
	
	
	
	@SuppressWarnings("unchecked")
	public static void registerBlockInAStructure(EnumStructureType structure, Block registered)
	{
		try
		{
			Class<?> ecUtilsClass = Class.forName("ec3.utils.common.ECUtils");
			Field hashTableFld = ecUtilsClass.getDeclaredField("allowedBlocks");
			hashTableFld.setAccessible(true);
			Hashtable<EnumStructureType,List<Block>> hashMap = (Hashtable<EnumStructureType, List<Block>>) hashTableFld.get(null);
			List<Block> allowedBlocksLst = hashMap.get(structure);
			allowedBlocksLst.add(registered);
			hashMap.put(structure, allowedBlocksLst);
			hashTableFld.set(null, hashMap);
		}catch(Exception e)
		{
			return;
		}
	}
	
	public static void registerBlockMRUResistance(Block registered, int metadata, float resistance)
	{
		try
		{
			Class<?> ecUtilsClass = Class.forName("ec3.utils.common.ECUtils");
			Method regBlk = ecUtilsClass.getMethod("registerBlockResistance", Block.class,int.class,float.class);
			regBlk.setAccessible(true);
			regBlk.invoke(null,registered,metadata,resistance);
		}catch(Exception e)
		{
			return;
		}
	}
	
	public static DiscoveryEntry findDiscoveryByIS(ItemStack referal)
	{
		if(referal == null)return null;
		referal.stackSize = 0;
		return ApiCore.discoveriesByIS.get(referal.toString());
	}
	
	public static void allowItemToSeeMRU(Item i)
	{
		allowsSeeingMRU.add(i);
	}
	
	public static void setArmorProperties(Item item_0, float i, float j, float k)
	{
		ArrayList<Float> red = new ArrayList<Float>();
		red.add(i);
		red.add(j);
		red.add(k);
		reductionsTable.put(item_0, red);
	}
	
	public static boolean tryToDecreaseMRUInStorage(EntityPlayer player, int amount)
	{
		try
		{
			Class<?> ecUtilsClass = Class.forName("ec3.utils.common.ECUtils");
			Method tryToDecreaseMRUInStorage = ecUtilsClass.getMethod("tryToDecreaseMRUInStorage", EntityPlayer.class,int.class);
			return Boolean.parseBoolean(tryToDecreaseMRUInStorage.invoke(null, player,-amount).toString());
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	public static void increaseCorruptionAt(World w, float x, float y, float z, int amount)
	{
		try
		{
			Class<?> ecUtilsClass = Class.forName("ec3.utils.common.ECUtils");
			Method increaseCorruptionAt = ecUtilsClass.getMethod("increaseCorruptionAt", World.class,float.class,float.class,float.class,int.class);
			increaseCorruptionAt.setAccessible(true);
			increaseCorruptionAt.invoke(null, w,x,y,z,amount);
		}catch(Exception e)
		{
			return;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static IMRUPressence getClosestMRUCU(World w, DummyCore.Utils.Coord3D c, int radius)
	{
		List<IMRUPressence> l = w.getEntitiesWithinAABB(IMRUPressence.class, AxisAlignedBB.getBoundingBox(c.x-0.5, c.y-0.5, c.z-0.5, c.x+0.5, c.y+0.5, c.z+0.5).expand(radius, radius/2, radius));
		IMRUPressence ret = null;
		if(!l.isEmpty())
		{
			if(!(l.get(0) instanceof IMRUPressence))
			{
				ret = (IMRUPressence) l.get(0);
			}else
			{
				List<IMRUPressence> actualList = l;
				double currentDistance = 0;
				double dominatingDistance = 0;
				int dominatingIndex = 0;
				DummyCore.Utils.Coord3D main = new DummyCore.Utils.Coord3D(c.x,c.y,c.z);
				for(int i = 0; i < actualList.size(); ++i)
				{
					Entity pressence = (Entity) actualList.get(i);
					DummyCore.Utils.Coord3D current = new DummyCore.Utils.Coord3D(pressence.posX,pressence.posY,pressence.posZ);
					DummyDistance dist = new DummyDistance(main,current);
					if(i == 0)
					{
						dominatingIndex = i;
						dominatingDistance = dist.getDistance();
					}else
					{
						currentDistance = dist.getDistance();
						if(currentDistance < dominatingDistance)
						{
							dominatingIndex = i;
							dominatingDistance = dist.getDistance();
						}
					}
				}
				ret = actualList.get(dominatingIndex);
			}
		}
		return ret;
	}

}
