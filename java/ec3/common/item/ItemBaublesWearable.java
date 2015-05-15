package ec3.common.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import DummyCore.Utils.MiscUtils;
import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemBaublesWearable extends Item implements IBauble{
	
	public static IIcon[] baubleGenIcon = new IIcon[18];
	public static IIcon[] baubleGenIcon_Top = new IIcon[18];
	public static String[] baubleType = new String[]{"amulet","belt","ring"};
	
    public IIcon getIcon(ItemStack stack, int pass)
    {
    	try
    	{
			NBTTagCompound bTag = MiscUtils.getStackTag(stack);
			if(bTag.hasKey("type"))
			{
				int type = bTag.getInteger("type");
				int bottomInt = bTag.getInteger("b");
				int topInt = bTag.getInteger("t");
				if(pass == 0)
					return baubleGenIcon[bottomInt+(type*6)];
				else
					return baubleGenIcon_Top[topInt+(type*6)];
			}
	        return baubleGenIcon[0];
    	}catch(Exception e)
    	{
    		  return baubleGenIcon[0];
    	}
    }
	
    public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining)
    {
    	try
    	{
			NBTTagCompound bTag = MiscUtils.getStackTag(stack);
			if(bTag.hasKey("type"))
			{
				int type = bTag.getInteger("type");
				int bottomInt = bTag.getInteger("b");
				int topInt = bTag.getInteger("t");
				if(renderPass == 0)
					return baubleGenIcon[bottomInt+(type*6)];
				else
					return baubleGenIcon_Top[topInt+(type*6)];
			}
	        return baubleGenIcon[0];
    	}catch(Exception e)
    	{
    		  return baubleGenIcon[0];
    	}
    }
    
    public ItemStack onItemRightClick(ItemStack stack, World w, EntityPlayer p)
    {
		NBTTagCompound bTag = MiscUtils.getStackTag(stack);
		if(!bTag.hasKey("type"))
		{
			initRandomTag(stack,w.rand);
		}
		return stack;
    }
    
    public static void initRandomTag(ItemStack stk, Random rand)
    {
    	NBTTagCompound bTag = MiscUtils.getStackTag(stk);
    	int type = rand.nextInt(3);
    	bTag.setInteger("type", type);
    	bTag.setInteger("b", rand.nextInt(6));
    	bTag.setInteger("t", rand.nextInt(6));
    	bTag.setFloat("mrucr", rand.nextFloat()/10);
    	bTag.setFloat("mrurr", rand.nextFloat()/10);
    	bTag.setFloat("car", rand.nextFloat()/10);
    	stk.setTagCompound(bTag);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void addInformation(ItemStack stack, EntityPlayer p_77624_2_, List p_77624_3_, boolean p_77624_4_) 
    {
		NBTTagCompound bTag = MiscUtils.getStackTag(stack);
		if(bTag.hasKey("type"))
		{
			ArrayList<Float> fltLst = new ArrayList();
			fltLst.add(bTag.getFloat("mrucr"));
			fltLst.add(bTag.getFloat("mrurr"));
			fltLst.add(bTag.getFloat("car"));
			p_77624_3_.add(EnumChatFormatting.GOLD+"+"+((int)(fltLst.get(0)*100))+"% "+EnumChatFormatting.DARK_PURPLE+"to MRUCorruption resistance");
			p_77624_3_.add(EnumChatFormatting.GOLD+"+"+((int)(fltLst.get(1)*100))+"% "+EnumChatFormatting.DARK_PURPLE+"to MRURadiation resistance");
			p_77624_3_.add(EnumChatFormatting.GOLD+"-"+((int)(fltLst.get(2)*100))+"% "+EnumChatFormatting.DARK_PURPLE+"to Corruption affection");
		}
    }
	
    public void registerIcons(IIconRegister p_94581_1_)
    {
        super.registerIcons(p_94581_1_);
        for(int i = 0; i < 6; ++i)
        {
        	for(int j = 0; j < 3; ++j)
        	{
        		baubleGenIcon[i+(j*6)] = p_94581_1_.registerIcon("essentialcraft:baubles/bauble_"+baubleType[j]+"_"+i);
        		baubleGenIcon_Top[i+(j*6)] = p_94581_1_.registerIcon("essentialcraft:baubles/bauble_"+baubleType[j]+"_"+i+"_t");
        	}
        }
    }
    
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }
    
    

	@Override
	public BaubleType getBaubleType(ItemStack itemstack) {
		NBTTagCompound bTag = MiscUtils.getStackTag(itemstack);
		if(bTag.hasKey("type"))
		{
			int type = bTag.getInteger("type");
			switch(type)
			{
				case 0:
					return BaubleType.AMULET;
				case 1:
					return BaubleType.BELT;
				case 2:
					return BaubleType.RING;
			}
		}
		return null;
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) {
		// TODO Auto-generated method stub
		return true;
	}

}
