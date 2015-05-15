package ec3.common.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ec3.common.entity.EntityMRUPresence;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemMRUMover extends Item{
	public IIcon theIcon;
    
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 72000;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.bow;
    }
    
    public void onUsingTick(ItemStack stack, EntityPlayer player, int count)
    {
    	Vec3 mainLookVec = player.getLookVec();
    	for(int i = 0; i < 20; ++i)
		{
			Vec3 additionalVec = mainLookVec.addVector(mainLookVec.xCoord*i, mainLookVec.yCoord*i, mainLookVec.zCoord*i);
			List<?> entityList = player.worldObj.getEntitiesWithinAABB(EntityMRUPresence.class, AxisAlignedBB.getBoundingBox(player.posX+additionalVec.xCoord-1, player.posY+additionalVec.yCoord-2, player.posZ+additionalVec.zCoord-1, player.posX+additionalVec.xCoord+1, player.posY+additionalVec.yCoord+2, player.posZ+additionalVec.zCoord+1));
			if(!entityList.isEmpty())
			{
				EntityMRUPresence presence = (EntityMRUPresence) entityList.get(player.worldObj.rand.nextInt(entityList.size()));
				player.worldObj.spawnParticle("portal", player.posX, player.posY, player.posZ, presence.posX-player.posX, presence.posY-player.posY-1, presence.posZ-player.posZ);
				float moveX = 0;
				float moveY = 0;
				float moveZ = 0;
				moveX = (float) -(mainLookVec.xCoord/10);
				moveY = (float) -(mainLookVec.yCoord/10);
				moveZ = (float) -(mainLookVec.zCoord/10);
				//if(!presence.worldObj.isRemote)
				{
					if(!player.isSneaking())
						presence.setPositionAndRotation(presence.posX+moveX, presence.posY+moveY, presence.posZ+moveZ, 0, 0);
					else
						presence.setPositionAndRotation(presence.posX-moveX, presence.posY-moveY, presence.posZ-moveZ, 0, 0);
					if(count % 20 == 0)
						stack.damageItem(1, player);
				}
					
				break;
			}
		}
    }
    
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        return par1ItemStack;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon(this.getIconString());
        this.theIcon = par1IconRegister.registerIcon(this.getIconString() + "_active");
    }
    
    public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining)
    {
    	if(useRemaining == 0) return this.itemIcon;
    	else
    		return this.theIcon;
    }

}
