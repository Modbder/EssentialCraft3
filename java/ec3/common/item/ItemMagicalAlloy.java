package ec3.common.item;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ec3.utils.common.EnumOreColoring;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.OreDictionary;

public class ItemMagicalAlloy extends Item{
	public static IIcon[] layerIcon = new IIcon[2];
	
	public ItemMagicalAlloy()
	{
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}
    
    public void registerIcons(IIconRegister p_94581_1_)
    {
        super.registerIcons(p_94581_1_);
        layerIcon[0] = p_94581_1_.registerIcon("essentialcraft:modular/alloy_layer_0");
        layerIcon[1] = p_94581_1_.registerIcon("essentialcraft:modular/alloy_layer_1");
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_)
    {
        for(int i = 0; i < EnumOreColoring.values().length; ++i)
        {
        	p_150895_3_.add(new ItemStack(p_150895_1_,1,i));
        }
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List p_77624_3_, boolean p_77624_4_)
    {
    	EnumOreColoring ore = EnumOreColoring.values()[p_77624_1_.getItemDamage()];
    	ArrayList<ItemStack> oreLst = OreDictionary.getOres(ore.oreName);
    	if(oreLst != null && !oreLst.isEmpty())
    		p_77624_3_.add(oreLst.get(0).getDisplayName());
    	else	
    		p_77624_3_.add(StatCollector.translateToLocal("tile."+EnumOreColoring.values()[p_77624_1_.getItemDamage()].oreName+".name"));
    }
    
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(int i, int j)
    {
    	return layerIcon[j];
    }
    
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack p_82790_1_, int p_82790_2_)
    {
    	if(p_82790_2_ == 0)
    		return 16777215;
    	return EnumOreColoring.values()[p_82790_1_.getItemDamage()].color;
    }


}
