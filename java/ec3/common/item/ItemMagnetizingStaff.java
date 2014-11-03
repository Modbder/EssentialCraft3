package ec3.common.item;

import java.util.List;

import ec3.utils.common.ECUtils;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
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
import net.minecraft.world.World;

public class ItemMagnetizingStaff extends ItemStoresMRUInNBT {

	public ItemMagnetizingStaff() {
		super();	
		this.setMaxMRU(5000);
		this.maxStackSize = 1;
		this.bFull3D = true;
	}
    /**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack p_77626_1_)
    {
        return Integer.MAX_VALUE;
    }
    
    public void onUsingTick(ItemStack stack, EntityPlayer player, int count)
    {
    	if(count % 8 == 0 && (ECUtils.tryToDecreaseMRUInStorage(player, -50) || this.setMRU(stack, -50)))
    	{
    		player.swingItem();
    		List<EntityItem> items = player.worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(player.posX-0.5D, player.posY, player.posZ-0.5D, player.posX+0.5D, player.posY+1, player.posZ+0.5D).expand(12D, 6D, 12D));
    		for(EntityItem item : items)
    		{
    			if(player.posX < item.posX)
    				item.motionX -= 0.1F;
    			else
    				item.motionX += 0.1F;
    			
    			if(player.posY < item.posY)
    				item.motionY -= 0.1F;
    			else
    				item.motionY += 0.5F;
    			
    			if(player.posZ < item.posZ)
    				item.motionZ -= 0.1F;
    			else
    				item.motionZ += 0.1F;
    		}
    		
    		//Split//
    		
    		List<EntityXPOrb> orbs = player.worldObj.getEntitiesWithinAABB(EntityXPOrb.class, AxisAlignedBB.getBoundingBox(player.posX-0.5D, player.posY, player.posZ-0.5D, player.posX+0.5D, player.posY+1, player.posZ+0.5D).expand(12D, 6D, 12D));
    		for(EntityXPOrb item : orbs)
    		{
    			if(player.posX < item.posX)
    				item.motionX -= 0.1F;
    			else
    				item.motionX += 0.1F;
    			
    			if(player.posY < item.posY)
    				item.motionY -= 0.1F;
    			else
    				item.motionY += 0.5F;
    			
    			if(player.posZ < item.posZ)
    				item.motionZ -= 0.1F;
    			else
    				item.motionZ += 0.1F;
    		}
    	}
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
