package ec3.api;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import net.minecraft.item.Item;

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
	
	public static void registerBlockInAStructure()
	{
		
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
	
	

}
