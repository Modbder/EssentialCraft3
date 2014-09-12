package ec3.common.item;

import java.util.List;

import com.google.common.collect.Multimap;

import DummyCore.Utils.DummyDataUtils;
import DummyCore.Utils.MathUtils;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import ec3.utils.common.ECUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class ItemHolyMace extends ItemStoresMRUInNBT {

	public ItemHolyMace() {
		super();
		this.setMaxMRU(5000);
		this.maxStackSize = 1;
        this.bFull3D = true;
	}
	
    public boolean hitEntity(ItemStack p_77644_1_, EntityLivingBase p_77644_2_, EntityLivingBase p_77644_3_)
    {
    	if(p_77644_3_ instanceof EntityPlayer)
    	{
	        if((ECUtils.tryToDecreaseMRUInStorage((EntityPlayer) p_77644_3_, -250) || this.setMRU(p_77644_1_, -250)))
	        {
	        	return true;
	        }
    	}
        return false;
    }
    
	
	@Override
	public void onUpdate(ItemStack s, World world, Entity entity, int indexInInventory, boolean isCurrentItem)
	{
		super.onUpdate(s, world, entity, indexInInventory, isCurrentItem);
		if(entity instanceof EntityPlayer)
		{
			int level = EnchantmentHelper.getEnchantmentLevel(Enchantment.smite.effectId, s);
			if(level == 0)
			{
				s.addEnchantment(Enchantment.smite, 6);
			}
		}
	}
	
    public Multimap getItemAttributeModifiers()
    {
        Multimap multimap = super.getItemAttributeModifiers();
        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", 12, 0));
        return multimap;
    }
}
