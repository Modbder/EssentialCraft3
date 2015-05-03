package ec3.common.item;

import java.util.List;

import org.lwjgl.opengl.GL11;

import DummyCore.Utils.DCASMCheck;
import DummyCore.Utils.ExistanceCheck;
import DummyCore.Utils.MiscUtils;
import thaumcraft.api.IGoggles;
import thaumcraft.api.IRepairable;
import thaumcraft.api.IVisDiscountGear;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.nodes.IRevealer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ec3.api.IItemRequiresMRU;
import ec3.common.mod.EssentialCraftCore;
import ec3.utils.common.ECUtils;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.ISpecialArmor;

@DCASMCheck
@ExistanceCheck(classPath = {"thaumcraft.api.IRepairable","thaumcraft.api.IVisDiscountGear","thaumcraft.api.nodes.IRevealer","thaumcraft.api.IGoggles"})
public class ItemArmorMod extends ItemArmor implements IRepairable, IVisDiscountGear, IRevealer, IGoggles, ISpecialArmor, IItemRequiresMRU{
	public String armorTexture;
	public int aType;
	public ArmorMaterial mat;
	public ItemArmorMod(ArmorMaterial p_i45325_1_, int p_i45325_2_,
			int p_i45325_3_, int it) {
		super(p_i45325_1_, p_i45325_2_, p_i45325_3_);
		aType = it;
		mat = p_i45325_1_;
	}
	
	public Item setArmorTexture(String path)
	{
		armorTexture = path;
		return this;
	}
	
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        super.addInformation(stack, player, list, par4);
        list.add((new StringBuilder()).append(EnumChatFormatting.DARK_PURPLE).append(StatCollector.translateToLocal("tc.visdiscount")).append(": ").append(getVisDiscount(stack, player, null)).append("%").toString());
        if(this.aType == 1)
        {
        	list.add(this.getMRU(stack) + "/" + this.getMaxMRU(stack) + " MRU");
        }	
    }
    
	
	@Override 
	public String getArmorTexture(ItemStack itemstack, Entity entity, int slot, String type)
	{ 
		switch(slot)
		{ 
			case 2: return "essentialcraft:textures/special/models/"+armorTexture+"_1.png"; //2 should be the slot for legs
			default: return "essentialcraft:textures/special/models/"+armorTexture+"_0.png"; 
		}
	}
	
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
    	if(this.aType != 1)
    		super.getSubItems(par1, par2CreativeTabs, par3List);
    	else
	        for (int var4 = 0; var4 < 1; ++var4)
	        {
	        	ItemStack min = new ItemStack(par1, 1, 0);
	        	ECUtils.initMRUTag(min, 5000);
	        	ItemStack max = new ItemStack(par1, 1, 0);
	        	ECUtils.initMRUTag(max, 5000);
	        	ECUtils.getStackTag(max).setInteger("mru", 5000);
	            par3List.add(min);
	            par3List.add(max);
	        }
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) 
    { 
    	{ 
    		ModelBiped armorModel = null;
    		if(itemStack != null)
    		{ 
    			if(itemStack.getItem() instanceof ItemArmorMod)
    			{ 
			        GL11.glEnable(GL11.GL_BLEND);
			        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
    				int type = ((ItemArmor)itemStack.getItem()).armorType;
    				if(type == 3)
    				{
    					armorModel = (ModelBiped)(EssentialCraftCore.proxy.getClientModel(0));
    				}
    				else
    					if(type == 1)
    					{
    						armorModel = (ModelBiped)(EssentialCraftCore.proxy.getClientModel(2));
    					}else
		    				{ 
		    					armorModel = (ModelBiped)(EssentialCraftCore.proxy.getClientModel(1)); 
		    				}
    			}
    			if(armorModel != null)
    			{ 
    				armorModel.bipedHead.showModel = armorSlot == 0;
    				armorModel.bipedHeadwear.showModel = armorSlot == 0;
    				armorModel.bipedBody.showModel = armorSlot == 1 || armorSlot == 2;
    				armorModel.bipedRightArm.showModel = armorSlot == 1;
    				armorModel.bipedLeftArm.showModel = armorSlot == 1;
    				armorModel.bipedRightLeg.showModel = armorSlot == 2 || armorSlot == 3; 
    				armorModel.bipedLeftLeg.showModel = armorSlot == 2 || armorSlot == 3;
    				armorModel.isSneak = entityLiving.isSneaking(); 
    				armorModel.isRiding = entityLiving.isRiding(); 
    				armorModel.isChild = entityLiving.isChild();
    				armorModel.heldItemRight = entityLiving.getEquipmentInSlot(0) != null ? 1 :0; 
    				if(entityLiving instanceof EntityPlayer)
    				{ 
    					armorModel.aimedBow =((EntityPlayer)entityLiving).getItemInUseDuration() > 2;
    				}
    			}
    		}
    		return armorModel;
    	}
    }

	@Override
	public boolean showIngamePopups(ItemStack itemstack, EntityLivingBase player) {
		int type = ((ItemArmor)itemstack.getItem()).armorType;
		return type == 0;
	}

	@Override
	public boolean showNodes(ItemStack itemstack, EntityLivingBase player) {
		int type = ((ItemArmor)itemstack.getItem()).armorType;
		return type == 0;
	}

	@Override
	public int getVisDiscount(ItemStack stack, EntityPlayer player,
			Aspect aspect) {
		int type = ((ItemArmor)stack.getItem()).armorType;
		
		return discount[aType][type];
	}
	
	public static int[][] discount = new int[][]{{5,5,3,2},{8,10,7,5},{10,15,8,7},{2,3,2,1}};

	@Override
	public ArmorProperties getProperties(EntityLivingBase player,
			ItemStack armor, DamageSource source, double damage, int slot) {
		if(this.aType != 1)
		{
			if(!source.isUnblockable())
			{
                ItemArmor aarmor = (ItemArmor)armor.getItem();
                return new ArmorProperties(0, aarmor.damageReduceAmount / 25D, aarmor.getMaxDamage() + 1 - armor.getItemDamage());
			}else
				return new ArmorProperties(0,0,armor.getMaxDamage() + 1 - armor.getItemDamage());
		}else
		{
			int mru = getMRU(armor);
			if(mru > 0)
			{
                ItemArmor aarmor = (ItemArmor)armor.getItem();
                return new ArmorProperties(0, aarmor.damageReduceAmount / 25D, aarmor.getMaxDamage() + 1 - armor.getItemDamage());
			}else
				return new ArmorProperties(0,0,armor.getMaxDamage() + 1 - armor.getItemDamage());
		}
	}

	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		return mat.getDamageReductionAmount(armorType);
	}

	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack,
			DamageSource source, int damage, int slot) {
		
		if(this.aType != 1)
		{
			stack.damageItem(damage, entity);
		}else
		{
			if(entity instanceof EntityPlayer)
			{
				EntityPlayer p = (EntityPlayer) entity;
				if(ECUtils.tryToDecreaseMRUInStorage(p, -damage*1000) || this.setMRU(stack, -damage*1000))
				{
					
				}else
				{
					
				}
			}
		}
	}

	@Override
	public boolean setMRU(ItemStack stack, int amount) {
		if(getMRU(stack)+amount >= 0 && getMRU(stack)+amount<=getMaxMRU(stack))
		{
			MiscUtils.getStackTag(stack).setInteger("mru", MiscUtils.getStackTag(stack).getInteger("mru")+amount);
			return true;
		}
		return false;
	}

	@Override
	public int getMRU(ItemStack stack) {
		return MiscUtils.getStackTag(stack).getInteger("mru");
	}

	@Override
	public int getMaxMRU(ItemStack stack) {
		return this.aType == 1 ? 5000 : 1;
	}

}
