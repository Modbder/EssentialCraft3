package ec3.common.item;

import java.util.UUID;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemGenericArmor extends ItemArmor{

	public ItemGenericArmor(ArmorMaterial material, int renderIndex,int type)
	{
		super(material, renderIndex, type);
	}
	
	@Override 
	public String getArmorTexture(ItemStack itemstack, Entity entity, int slot, String type)
	{ 
		switch(slot)
		{ 
			case 2: return "essentialcraft:textures/special/armor/wind_layer_2.png"; //2 should be the slot for legs
			default: return "essentialcraft:textures/special/armor/wind_layer_1.png"; 
		}
	}
	
	
	
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int renderPass)
    {
        int j = this.getColor(stack);

        if (j < 0)
        {
            j = 0xffffff;
        }

        return j;
    }
    
    public boolean hasColor(ItemStack stk)
    {
    	return (!stk.hasTagCompound() ? false : (!stk.getTagCompound().hasKey("display", 10) ? false : stk.getTagCompound().getCompoundTag("display").hasKey("color", 3)));
    }
    
    public int getColor(ItemStack p_82814_1_)
    {
        NBTTagCompound nbttagcompound = p_82814_1_.getTagCompound();
        if (nbttagcompound == null)
        {
            return 0xffffff;
        }
        else
        {
            NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");
            return nbttagcompound1 == null ? 0xffffff : (nbttagcompound1.hasKey("color", 3) ? nbttagcompound1.getInteger("color") : 0xffffff);
        }
    }
    
    public void removeColor(ItemStack stk)
    {
        NBTTagCompound nbttagcompound = stk.getTagCompound();

        if (nbttagcompound != null)
        {
            NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");

            if (nbttagcompound1.hasKey("color"))
            {
                nbttagcompound1.removeTag("color");
            }
        }
    }
    
    //setArmorColor? dyeArmor? applyArmorColor?
    @Override
    public void func_82813_b(ItemStack stk, int newColor)
    {
        NBTTagCompound nbttagcompound = stk.getTagCompound();

        if (nbttagcompound == null)
        {
            nbttagcompound = new NBTTagCompound();
            stk.setTagCompound(nbttagcompound);
        }

        NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");

        if (!nbttagcompound.hasKey("display", 10))
        {
            nbttagcompound.setTag("display", nbttagcompound1);
        }

        nbttagcompound1.setInteger("color", newColor);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Multimap getAttributeModifiers(ItemStack stack)
    {
    	Multimap mods = HashMultimap.create();
    	
    	if(this == ItemsCore.wind_chestplate)
    		mods.put(SharedMonsterAttributes.movementSpeed.getAttributeUnlocalizedName(), new AttributeModifier(UUID.fromString("1bca943c-3cf5-42cc-a3df-2ed994ae0000"), "mSpeed", 0.075D, 0));
    	
    	return mods;
    }

}
