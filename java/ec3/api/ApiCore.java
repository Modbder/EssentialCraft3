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

/**
 * 
 * @author Modbder
 * @Description Just some nifty functions to help you out.
 */
public class ApiCore {
	
	/**
	 * A usual amount of MRU all generators would have
	 */
	public static final float GENERATOR_MAX_MRU_GENERIC = 10000F;
	
	/**
	 * A usual amount of MRU all devices would have
	 */
	public static final float DEVICE_MAX_MRU_GENERIC = 5000F;
	
	/**
	 * A list of all items, which allow the player to see MRUCUs and MRU particles
	 */
	public static List<Item> allowsSeeingMRU = new ArrayList<Item>();
	
	/**
	 * A list of reductions the armor can have
	 */
	public static Hashtable<Item, ArrayList<Float>> reductionsTable = new Hashtable<Item, ArrayList<Float>>();
	
	/**
	 * All categories the Book Of Knowledge can have
	 */
	public static List<CategoryEntry> categories = new ArrayList<CategoryEntry>();
	
	/**
	 * A list of all discoveries bound to generic ItemStack
	 */
	public static Hashtable<String, DiscoveryEntry> discoveriesByIS = new Hashtable<String, DiscoveryEntry>();
	
	/**
	 * Use this to get a full information on the player - it's UBMRU, Balance and Corruption status
	 * @param p - the player to get the data of. Please check, that it is not null and is not a FakePlayer!
	 * @return The corresponding player data, or null if something went wrong
	 */
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
	
	/**
	 * Allows a specified block to be a part of a specified structure.
	 * @param structure - the structure the block can be a part of
	 * @param registered - the block that is registered. Not metadata sensitive!
	 */
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
	
	/**
	 * Allows a block to 'resist' MRUCU effects on the player. Also that block will be tougher for the corruption no grow on
	 * @param registered - the block to register
	 * @param metadata - the block's metadata. Use -1 or OreDictionary.WILDCARD_VALUE to make the check ignore metadata.
	 * @param resistance - the resistance the block will have. All non-registered have 1.
	 */
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
	
	/**
	 * Finds a DiscoveryEntry by the given ItemStack. The ItemStack would either be in the list of items at one of the pages, or will be a crafting result.
	 * @param referal - the ItemStack to lookup.
	 * @return A valid DiscoveryEntry if was found, null otherwise
	 */
	public static DiscoveryEntry findDiscoveryByIS(ItemStack referal)
	{
		if(referal == null)return null;
		int size = referal.stackSize;
		referal.stackSize = 0;
		DiscoveryEntry de = ApiCore.discoveriesByIS.get(referal.toString());
		referal.stackSize = size;
		return de;
	}
	
	/**
	 * Registers an item as one allowed to grant the player MRUCU and MRU vision
	 * @param i - the item to register
	 */
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
