package ec3.common.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemEssence extends Item{
	public static String[] dropNames = new String[]{"Fire","Water","Earth","Air"};
	public static IIcon[] itemIcons = new IIcon[4];
	
	public ItemEssence()
	{
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}
	
    public String getUnlocalizedName(ItemStack p_77667_1_)
    {
        return this.getUnlocalizedName()+dropNames[convertDamageToIntBefore4(p_77667_1_.getItemDamage())];
    }
    
    public void registerIcons(IIconRegister p_94581_1_)
    {
        super.registerIcons(p_94581_1_);
        for(int i = 0; i < 4; ++i)
        	itemIcons[i] = p_94581_1_.registerIcon("essentialcraft:elemental/essence"+dropNames[i]);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int i)
    {
        return itemIcons[convertDamageToIntBefore4(i)];
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_)
    {
        for (int var4 = 0; var4 < 16; ++var4)
        {
        	ItemStack min = new ItemStack(p_150895_1_, 1, var4);
        	p_150895_3_.add(min);
        }
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) 
    {
    	int t = (int)((float)par1ItemStack.getItemDamage()/4);
    	if(t == 0)
    		par3List.add("Rarity: \247f"+"Common");
    	if(t == 1)
    		par3List.add("Rarity: \247e"+"Uncommon");
    	if(t == 2)
    		par3List.add("Rarity: \247b"+"Rare");
    	if(t == 3)
    		par3List.add("Rarity: \247d"+"Exceptional");
    }
    
    public static int convertDamageToIntBefore4(int d)
    {
    	if(d == 0 || d == 4 || d == 8 || d == 12)
    	return 0;
    	if(d == 1 || d == 5 || d == 9 || d == 13)
    		return 1;
    	if(d == 2 || d == 6 || d == 10 || d == 14)
    		return 2;
    	if(d == 3 || d == 7 || d == 11 || d == 15)
    		return 3;
    	return 0;
    }
    
    public EnumRarity getRarity(ItemStack par1ItemStack)
    {
    	int t = (int)((float)par1ItemStack.getItemDamage()/4);
    	if(t == 1)
    		return EnumRarity.uncommon;
    	if(t == 2)
    		return EnumRarity.rare;
    	if(t == 3)
    		return EnumRarity.epic;
    	return EnumRarity.common;
    }

}
