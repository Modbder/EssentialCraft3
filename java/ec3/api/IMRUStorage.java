package ec3.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

/**
 * 
 * @author Modbder
 * @Description This interface is used to check for the items like soul shards in the player's inventory, before using MRU in the actual item. Do not use this when you are creating item, that wastes MRU, use this only to create MRU storage items.
 */
public interface IMRUStorage {
	/**
	 * This is called so you can use different ways of storing MRU in your items. You can find some examples below
	 * @param stack - ItemStack of your item. 
	 * @param amount - the amount of MRU to add or remove. Use negative numbers to decrease MRU.
	 * @return true, if this operation was successful, false if not
	 */
	public abstract boolean setMRU(ItemStack stack, int amount);
	
	/**
	 * This is called so you can use different ways of storing MRU in your items. You can find some examples below
	 * @param stack - ItemStack of your item. 
	 * @return the amount of MRU in your stack
	 */
	public abstract int getMRU(ItemStack stack);
	
	/**
	 * This is called so you can use different ways of storing MRU in your items. You can find some examples below
	 * @param stack - ItemStack of your item. 
	 * @return the amount of MRU your item can store
	 */
	public abstract int getMaxMRU(ItemStack stack);
}

//*======================================EXAMPLES=========================================*//
	/**
	 * here is an example of how I use this to first try to decrease amount of MRU in the MRUStorages, and only then in the actual item. I use this every time I need to decrease the MRU. This is actually contained in ECUtils.class
	 */
	
	/*
	public static boolean tryToDecreaseMRUInStorage(EntityPlayer player, int amount)
	{
		if(player.capabilities.isCreativeMode)
		{
			return true;
		}
		InventoryPlayer _gen_var_0 = player.inventory;
		for(int _gen_int_0 = 0; _gen_int_0 < _gen_var_0.mainInventory.length; ++_gen_int_0)
		{
			ItemStack _gen_var_1 = _gen_var_0.mainInventory[_gen_int_0];
			if(_gen_var_1 != null)
			{
				if(_gen_var_1.getItem() instanceof IMRUStorage)
				{
					if(((IMRUStorage)(_gen_var_1.getItem())).setMRU(_gen_var_1, amount))
					{
						return true;
					}
				}
			}
		}
		
		return false;
		
	}
	*/
