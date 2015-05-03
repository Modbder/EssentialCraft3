package ec3.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IWindResistance {
	
	public boolean resistWind(EntityPlayer p, ItemStack stk);

}
