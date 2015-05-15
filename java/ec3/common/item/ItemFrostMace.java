package ec3.common.item;

import java.util.List;

import DummyCore.Utils.MiscUtils;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import ec3.api.IItemRequiresMRU;
import ec3.utils.common.ECUtils;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemFrostMace extends ItemSword implements IItemRequiresMRU {

	public ItemFrostMace() {
		super(ItemsCore.elemental);	
		this.setMaxMRU(5000);
		this.maxStackSize = 1;
		this.bFull3D = true;
		this.setMaxDamage(0);
	}
	
	int maxMRU = 5000;
	
	public Item setMaxMRU(int max)
	{
		maxMRU = max;
		return this;
	}
	
	@Override
	public boolean setMRU(ItemStack stack, int amount) {
		if(MiscUtils.getStackTag(stack).getInteger("mru")+amount >= 0 && MiscUtils.getStackTag(stack).getInteger("mru")+amount<=MiscUtils.getStackTag(stack).getInteger("maxMRU"))
		{
			MiscUtils.getStackTag(stack).setInteger("mru", MiscUtils.getStackTag(stack).getInteger("mru")+amount);
			return true;
		}
		return false;
	}
	
	@Override
	public int getMRU(ItemStack stack) {
		// TODO Auto-generated method stub
		return MiscUtils.getStackTag(stack).getInteger("mru");
	}
	
    public boolean isItemTool(ItemStack p_77616_1_)
    {
    	return true;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) 
    {
    	super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
    	par3List.add(ECUtils.getStackTag(par1ItemStack).getInteger("mru") + "/" + ECUtils.getStackTag(par1ItemStack).getInteger("maxMRU") + " MRU");
    }
	
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int var4 = 0; var4 < 1; ++var4)
        {
        	ItemStack min = new ItemStack(par1, 1, 0);
        	ECUtils.initMRUTag(min, maxMRU);
        	ItemStack max = new ItemStack(par1, 1, 0);
        	ECUtils.initMRUTag(max, maxMRU);
        	ECUtils.getStackTag(max).setInteger("mru", ECUtils.getStackTag(max).getInteger("maxMRU"));
            par3List.add(min);
            par3List.add(max);
        }
    }
    
	@Override
	public int getMaxMRU(ItemStack stack) {
		// TODO Auto-generated method stub
		return this.maxMRU;
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
    	try {
			if(p_77644_3_ instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer) p_77644_3_;
			    if((ECUtils.tryToDecreaseMRUInStorage((EntityPlayer) p_77644_3_, -250) || this.setMRU(p_77644_1_, -250)))
			    {
					int att = ECUtils.getData(player).getMatrixTypeID();
					if(att == 2)
					{
						PotionEffect eff = p_77644_2_.getActivePotionEffect(Potion.moveSlowdown);
						if(eff != null && p_77644_2_.hurtResistantTime == 0 || p_77644_2_.hurtResistantTime >= 15 && eff != null)
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
		} catch (Exception e) {
			return false;
		}
        return false;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Multimap getItemAttributeModifiers()
    {
        Multimap multimap = HashMultimap.create();
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
