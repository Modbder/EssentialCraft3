package ec3.common.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import codechicken.lib.math.MathHelper;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import ec3.api.DemonTrade;

public class ItemSoulScriber extends ItemSword_Mod{

	public ItemSoulScriber() {
		super(ToolMaterial.WOOD);
	}
	
	@Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Multimap getAttributeModifiers(ItemStack stack)
    {
    	Multimap mp = HashMultimap.create();
    	mp.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", 1, 0));
    	return mp;
    }

    public boolean hitEntity(ItemStack weapon, EntityLivingBase attacked, EntityLivingBase attacker)
    {

    	if(MathHelper.floor_double(attacked.getHealth()) <= 2)
    	{
    		boolean createSoul = DemonTrade.allMobs.contains(attacked.getClass());
    		if(createSoul && attacker instanceof EntityPlayer)
    		{
    			ItemStack soul = new ItemStack(ItemsCore.soul,1,DemonTrade.allMobs.indexOf(attacked.getClass()));
    			EntityItem ei = new EntityItem(attacked.worldObj,attacked.posX,attacked.posY,attacked.posZ,soul);
    			if(!attacked.worldObj.isRemote)
    				attacked.worldObj.spawnEntityInWorld(ei);
    			
    			attacked.setDead();
    		}
    	}
    	return false;
    }

}
