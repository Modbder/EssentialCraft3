package ec3.common.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemMagicalChisel extends Item{
	
    public ItemStack getContainerItem(ItemStack itemStack)
    {
        if (!hasContainerItem(itemStack))
        {
            return null;
        }
        return new ItemStack(itemStack.getItem(),itemStack.stackSize,itemStack.getItemDamage()+1);
    }
    
    public boolean hasContainerItem(ItemStack stack)
    {
    	return stack.getItemDamage() < stack.getMaxDamage();
    }

    public boolean doesContainerItemLeaveCraftingGrid(ItemStack p_77630_1_)
    {
        return false;
    }
}
