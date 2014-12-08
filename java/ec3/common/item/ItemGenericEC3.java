package ec3.common.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import DummyCore.Utils.DummyDataUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ec3.common.registry.PotionRegistry;
import ec3.common.world.WorldGenElderMRUCC;
import ec3.network.proxy.ClientProxy;
import ec3.utils.common.ECUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemGenericEC3 extends Item{
	public static String[] names = new String[]{
		"combinedMagicalAlloys", //0
		"elementalCore", //1
		"enderScalePlating", //2
		"magicalEssence", //3
		"magicalGoldenOrb", //4
		"magicalIngot", //5
		"magicalWater", //6
		"magicFortifiedPlating", //7
		"magicPurifyedEnderScaleAlloy", //8
		"magicPurifyedGlassAlloy", //9
		"magicPurifyedGoldAlloy", //10
		"magicPurifyedRedstoneAlloy", //11
		"mruShard", //12
		"mruCrystal", //13
		"mruGem", //14
		"mruChunk", //15
		"mruLargeChunk", //16
		"inventoryUpgrade", //17
		"efficencyUpgrade", //18
		"diamondUpgrade", //19
		"crystalDust", //20
		"diamondPlate", //21
		"emeraldPlate", //22
		"eyeOfAbsorbtion", //23
		"fortifiedFrame", //24
		"heatingRod", //25
		"ironSupport", //26
		"magicalScreen", //27
		"matrixLink", //28
		"mruCatcher", //29
		"mruConversionMatrix", //30
		"obsidianPlate", //31
		"sunImbuedGlass", //32
		"worldInteractor", //33
		"magicPlate", //34
		"voidPlating", //35
		"voidCore", //36
		"voidMruReactor", //37
		"palePearl", //38
		"paleIngot", //39
		"gemPale", //40
		"palePlate", //41
		"paleCore", //42
		"mruMagnet", //43
		"mruResonatingCrystal" //44
		};
	public static IIcon[] itemIcons = new IIcon[128];
	
	public ItemGenericEC3()
	{
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}
	
    public ItemStack onEaten(ItemStack p_77654_1_, World p_77654_2_, EntityPlayer base)
    {
        if (!base.capabilities.isCreativeMode)
        {
            --p_77654_1_.stackSize;
        }

        if (!p_77654_2_.isRemote)
        {
        	String currentEnergy = DummyDataUtils.getDataForPlayer(((EntityPlayer) base).getDisplayName(),"essentialcraft", "ubmruEnergy");
        	int addedEnergy = 500;
			if(currentEnergy != null && !currentEnergy.isEmpty() && !currentEnergy.equals("no data") && !currentEnergy.equals("empty string") && !currentEnergy.equals("empty"))
			{
				int currentEnergy_int = Integer.parseInt(currentEnergy);
				addedEnergy += currentEnergy_int;
			}
			DummyDataUtils.setDataForPlayer(base.getDisplayName(), "essentialcraft", "ubmruEnergy", Integer.toString(addedEnergy));
        }else
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
