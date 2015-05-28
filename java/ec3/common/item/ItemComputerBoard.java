package ec3.common.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import baubles.api.BaubleType;
import baubles.api.IBauble;

public class ItemComputerBoard extends Item implements IBauble{

	@Override
	public BaubleType getBaubleType(ItemStack itemstack) 
	{
		return BaubleType.BELT;
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player) 
	{
		
	}

	@Override
	public void onEquipped(ItemStack itemstack, EntityLivingBase player) 
	{
		
	}

	@Override
	public void onUnequipped(ItemStack itemstack, EntityLivingBase player)
	{
		
	}

	@Override
	public boolean canEquip(ItemStack itemstack, EntityLivingBase player) 
	{
		return true;
	}

	@Override
	public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) 
	{
		return true;
	}

}
