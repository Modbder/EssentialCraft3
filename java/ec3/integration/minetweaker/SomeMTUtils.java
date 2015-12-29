package ec3.integration.minetweaker;

import java.util.ArrayList;
import java.util.List;

import DummyCore.Utils.UnformedItemStack;
import net.minecraft.item.ItemStack;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.oredict.IOreDictEntry;

public class SomeMTUtils {

	public SomeMTUtils() {
		// TODO Auto-generated constructor stub
	}

	
	public static ItemStack toItem(IItemStack item)
	{
		
		if (item == null)
			return null;
        else
        {
            Object internal = item.getInternal();
            if (internal == null || !(internal instanceof ItemStack))
            {
                MineTweakerAPI.getLogger().logError("Not a valid item stack: " + item);
            }
            return ((ItemStack)internal).copy();
        }
		
		
	}
	
	public static ItemStack[] toItem(IItemStack[] item)
	{
		
		if (item == null)
			return null;
        else
        {
            List<Object> internal = new ArrayList<Object>();
            for(IItemStack it : item)
            {
            	internal.add(it.getInternal());
            }
            
            for(Object o : internal)
            if (o == null || !(o instanceof ItemStack))
            {
                MineTweakerAPI.getLogger().logError("Not a valid item stack: " + item);
            }
            
            ItemStack[] stack = new ItemStack[internal.size()];
            
            for(int i = 0; i < internal.size(); i++)
   		    {
   		     stack[i] = (ItemStack) internal.get(i);
   		    }
            
            
            return stack;
        }
		
		
	}
	
	public static String OreToString(IOreDictEntry entry)
	{
        return ((IOreDictEntry) entry).getName();
    }
	
	public static UnformedItemStack toUnformedIS(IIngredient ingredient){
        if (ingredient == null)
        	return null;
        else {
            if (ingredient instanceof IOreDictEntry)
            {
                return new UnformedItemStack((String)OreToString((IOreDictEntry) ingredient));
            }
            else if (ingredient instanceof IItemStack)
            {
                return new UnformedItemStack(((ItemStack)toItem((IItemStack)ingredient)).copy());
            }
            else return null;
        }
    }
	
	public static ItemStack ingrToItem(IIngredient ing)
	{
		 if (ing == null)
	        	return null;
	        else {
	            if (ing instanceof IItemStack)
	            {
	                return toItem((IItemStack) ing).copy();
	            }
	            else return null;
	        }
	}
}
