package ec3.common.item;

import java.util.List;

import DummyCore.Utils.DummyDataUtils;

import com.google.common.collect.Multimap;

import ec3.utils.common.ECUtils;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemFrostMace extends ItemStoresMRUInNBT {

	public ItemFrostMace() {
		super();	
		this.setMaxMRU(5000);
		this.maxStackSize = 1;
		this.bFull3D = true;
	}
	
	@Override
    public ItemStack onEaten(ItemStack p_77654_1_, World p_77654_2_, EntityPlayer p_77654_3_)
    {
		Vec3 playerLookVec = p_77654_3_.getLookVec();
		p_77654_3_.motionX += playerLookVec.xCoord;
		p_77654_3_.motionY += playerLookVec.yCoord;
		p_77654_3_.motionZ += playerLookVec.zCoord;
		return p_77654_1_;
    }

    /**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack p_77626_1_)
    {
        return 32;
    }
    
    public boolean hitEntity(ItemStack p_77644_1_, EntityLivingBase p_77644_2_, EntityLivingBase p_77644_3_)
    {
    	if(p_77644_3_ instanceof EntityPlayer)
    	{
	        if((ECUtils.tryToDecreaseMRUInStorage((EntityPlayer) p_77644_3_, -250) || this.setMRU(p_77644_1_, -250)))
	        {
	        	String username = ((EntityPlayer) p_77644_3_).getDisplayName();
	        	String attunement = DummyDataUtils.getDataForPlayer(username, "essentialcraft", "attunement");
				if(attunement != null && !attunement.isEmpty() && !attunement.equals("no data") && !attunement.equals("empty string") && !attunement.equals("empty"))
				{
					int att = Integer.parseInt(attunement);
					if(att == 2)
					{
						PotionEffect eff = p_77644_2_.getActivePotionEffect(Potion.moveSlowdown);
						if(p_77644_2_.hurtResistantTime == 0 || p_77644_2_.hurtResistantTime >= 15 && eff != null)
						{
							p_77644_2_.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id,1000,eff.getAmplifier()+1));
							return true;
						}
						else
						{
							if(p_77644_2_.hurtResistantTime == 0 || p_77644_2_.hurtResistantTime >= 15)
							{
								p_77644_2_.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id,1000,0));
								return true;
							}
						}
						
					}
				}
	        }
    	}
        return false;
    }
    
    public Multimap getItemAttributeModifiers()
    {
        Multimap multimap = super.getItemAttributeModifiers();
        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", 16, 0));
        return multimap;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack p_77661_1_)
    {
        return EnumAction.bow;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_)
    {
        p_77659_3_.setItemInUse(p_77659_1_, this.getMaxItemUseDuration(p_77659_1_));
        return p_77659_1_;
    }

}
