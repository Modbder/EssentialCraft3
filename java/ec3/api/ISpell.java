package ec3.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface ISpell {
	
	public abstract EnumSpellType getSpellType(ItemStack stk);
	
	public abstract int getUBMRURequired(ItemStack stk);
	
	public abstract void onSpellUse(int currentUBMRU, int attunement, EntityPlayer player, ItemStack spell, ItemStack holder);
	
	public abstract int getRequiredContainerTier(ItemStack stk);
	
	public abstract int getAttunementRequired(ItemStack stk);
	
	public abstract boolean requiresSpecificAttunement(ItemStack stk);
	
	public abstract boolean canUseSpell(ItemStack spell, EntityPlayer user);

}
