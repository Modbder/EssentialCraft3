package ec3.common.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ec3.common.world.WorldGenElderMRUCC;
import ec3.network.proxy.ClientProxy;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemGenericEC3 extends Item{
	public static String[] names = new String[]{"combinedMagicalAlloys","elementalCore","enderScalePlating","magicalEssence","magicalGoldenOrb","magicalIngot","magicalWater","magicFortifiedPlating","magicPurifyedEnderScaleAlloy","magicPurifyedGlassAlloy","magicPurifyedGoldAlloy","magicPurifyedRedstoneAlloy","mruShard","mruCrystal","mruGem","mruChunk","mruLargeChunk","inventoryUpgrade","efficencyUpgrade","diamondUpgrade","crystalDust","diamondPlate","emeraldPlate","eyeOfAbsorbtion","fortifiedFrame","heatingRod","ironSupport","magicalScreen","matrixLink","mruCatcher","mruConversionMatrix","obsidianPlate","sunImbuedGlass","worldInteractor","magicPlate"};
	public static IIcon[] itemIcons = new IIcon[128];
	
	public ItemGenericEC3()
	{
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}
	
    public ItemStack onEaten(ItemStack p_77654_1_, World p_77654_2_, EntityPlayer p_77654_3_)
    {
        if (!p_77654_3_.capabilities.isCreativeMode)
        {
            --p_77654_1_.stackSize;
        }

        if (!p_77654_2_.isRemote)
        {
            
        }
        return p_77654_1_.stackSize <= 0 ? new ItemStack(Items.glass_bottle) : p_77654_1_;
    }
    
    public int getMaxItemUseDuration(ItemStack p_77626_1_)
    {
        return 32;
    }

    public EnumAction getItemUseAction(ItemStack p_77661_1_)
    {
        return EnumAction.drink;
    }
    
    public static ItemStack getStkByName(String name)
    {
    	List lst = Arrays.asList(names);
    	if(lst.contains(name))
    	{
    		ItemStack stk = new ItemStack(ItemsCore.genericItem,1,lst.indexOf(name));
    		return stk;
    	}
    	return null;
    }

    public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_)
    {
    	if(p_77659_1_.getItemDamage() == 6)
    		p_77659_3_.setItemInUse(p_77659_1_, this.getMaxItemUseDuration(p_77659_1_));
        return p_77659_1_;
    }
	
    public String getUnlocalizedName(ItemStack p_77667_1_)
    {
        return this.getUnlocalizedName()+names[p_77667_1_.getItemDamage()];
    }
    
    public void registerIcons(IIconRegister p_94581_1_)
    {
        super.registerIcons(p_94581_1_);
        for(int i = 0; i < names.length; ++i)
        	itemIcons[i] = p_94581_1_.registerIcon("essentialcraft:"+names[i]);
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int i)
    {
        return this.itemIcons[i];
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_)
    {
        for(int i = 0; i < names.length; ++i)
        {
        	p_150895_3_.add(new ItemStack(p_150895_1_,1,i));
        }
    }

}
