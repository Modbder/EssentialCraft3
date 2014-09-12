package ec3.common.item;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ItemArmorMod extends ItemArmor{
	public String armorTexture;
	
	public ItemArmorMod(ArmorMaterial p_i45325_1_, int p_i45325_2_,
			int p_i45325_3_) {
		super(p_i45325_1_, p_i45325_2_, p_i45325_3_);
		// TODO Auto-generated constructor stub
	}
	
	public Item setArmorTexture(String path)
	{
		armorTexture = path;
		return this;
	}
	
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
    {
        return armorTexture;
    }

}
