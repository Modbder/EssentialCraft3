package ec3.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IWindModifier {
	
	public float getModifier(ItemStack stk, EntityPlayer p);

}
