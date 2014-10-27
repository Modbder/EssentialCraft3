package ec3.common.item;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ec3.common.mod.EssentialCraftCore;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
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
	
	@Override 
	public String getArmorTexture(ItemStack itemstack, Entity entity, int slot, String type)
	{ 
		switch(slot)
		{ 
			case 2: return "essentialcraft:textures/special/models/"+armorTexture+"_1.png"; //2 should be the slot for legs
			default: return "essentialcraft:textures/special/models/"+armorTexture+"_0.png"; 
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

}
