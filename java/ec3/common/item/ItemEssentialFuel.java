package ec3.common.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemEssentialFuel extends Item {

	public IIcon[] icon = new IIcon[4];
	public String[] name = new String[]{"Fiery","Watery","Earthen","Windy"};
	public ItemEssentialFuel() {
		super();
		this.setMaxDamage(0);
		this.maxStackSize = 16;
        this.bFull3D = false;
	}
	
    public IIcon getIconFromDamage(int par1)
    {
    	
    	return icon[par1];
    }
    
    @Override
    public void registerIcons(IIconRegister par1IconRegister)
    {
    	for(int i = 0; i < 4; ++i)
    	{
    		this.icon[i] = par1IconRegister.registerIcon("essentialcraft:elemental/eFuel_"+name[i]);
    	}
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int var4 = 0; var4 < 4; ++var4)
        {
        	ItemStack min = new ItemStack(par1, 1, var4);
            par3List.add(min);
        }
    }
    
    public String getUnlocalizedName(ItemStack p_77667_1_)
    {
        return this.getUnlocalizedName()+name[p_77667_1_.getItemDamage()];
    }
    
    public EnumRarity getRarity(ItemStack par1ItemStack)
    {
    	return EnumRarity.rare;
    }
    
    public boolean hasEffect(ItemStack par1ItemStack)
    {
    	return false;
    }
}
