package ec3.common.item;

import java.util.List;

import ec3.utils.common.ECUtils;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class ItemLifeStaff extends ItemStoresMRUInNBT {

	public ItemLifeStaff() {
		super();	
		this.setMaxMRU(5000);
		this.maxStackSize = 1;
		this.bFull3D = true;
	}
    
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
    	if(ECUtils.tryToDecreaseMRUInStorage(player, -100) || this.setMRU(stack, -100))
    	{
	    	if(ItemDye.applyBonemeal(new ItemStack(stack.getItem(),stack.getItemDamage(),stack.stackSize+1), world, x, y, z, player))
	    	{
	    		for(int px = -5; px <= 5; ++px)
	    		{
	        		for(int pz = -5; pz <= 5; ++pz)
	        		{
	        			if(this.getMRU(stack) > 10)
	        				
	        			if(ItemDye.applyBonemeal(new ItemStack(stack.getItem(),stack.getItemDamage(),stack.stackSize+1), world, x+px, y, z+pz, player))
	        			{
	        				if(!ECUtils.tryToDecreaseMRUInStorage(player, -100))this.setMRU(stack, -100);
	        			}
	        		}
	    		}
	    	}
    	}
        return false;
    }
    
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
    {
    	if(entity instanceof EntityZombie)
    	{
    		EntityZombie e = (EntityZombie) entity;
    		if(e.isVillager() && (ECUtils.tryToDecreaseMRUInStorage(player, -500) || this.setMRU(stack, -500)) && !e.worldObj.isRemote)
    		{
    	        EntityVillager entityvillager = new EntityVillager(e.worldObj);
    	        entityvillager.copyLocationAndAnglesFrom(e);
    	        entityvillager.onSpawnWithEgg((IEntityLivingData)null);
    	        if (e.isChild())
    	        {
    	            entityvillager.setGrowingAge(-24000);
    	        }

    	        e.worldObj.removeEntity(e);
    	        e.worldObj.spawnEntityInWorld(entityvillager);
    	        entityvillager.addPotionEffect(new PotionEffect(Potion.confusion.id, 200, 0));
    	        e.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1017, (int)e.posX, (int)e.posY, (int)e.posZ, 0);
    		}
    		return true;
    	}
    	if(entity instanceof EntityAgeable)
    	{
    		EntityAgeable e = (EntityAgeable) entity;
    		if(e.isChild() && !e.worldObj.isRemote && (ECUtils.tryToDecreaseMRUInStorage(player, -100) || this.setMRU(stack, -100)))
    		{
    			e.setGrowingAge(0);
    		}
    	}
    	return true;
    	
    }
}
