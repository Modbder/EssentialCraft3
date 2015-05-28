package ec3.common.item;

import ec3.common.entity.EntityOrbitalStrike;
import ec3.common.registry.AchievementRegistry;
import ec3.utils.common.ECUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

public class ItemOrbitalRemote extends ItemStoresMRUInNBT{

	@Override
	public void onUpdate(ItemStack itemStack, World world, Entity entity, int indexInInventory, boolean isCurrentItem)
	{
		super.onUpdate(itemStack, world, entity, indexInInventory, isCurrentItem);
		if(entity instanceof EntityPlayer && entity.ticksExisted % 20 == 0)
			EntityPlayer.class.cast(entity).triggerAchievement(AchievementRegistry.achievementList.get("hologramRemote"));
	}
	
	@Override
    public ItemStack onItemRightClick(ItemStack stk, World w, EntityPlayer p)
    {
		float f = 1;
        double d0 = p.prevPosX + (p.posX - p.prevPosX) * (double)f;
        double d1 = p.prevPosY + (p.posY - p.prevPosY) * (double)f + (double)(p.worldObj.isRemote ? p.getEyeHeight() - p.getDefaultEyeHeight() : p.getEyeHeight()); // isRemote check to revert changes to ray trace position due to adding the eye height clientside and player yOffset differences
        double d2 = p.prevPosZ + (p.posZ - p.prevPosZ) * (double)f;
        
        Vec3 lookVec = Vec3.createVectorHelper(d0, d1, d2);
        float f1 = p.prevRotationPitch + (p.rotationPitch - p.prevRotationPitch);
        float f2 = p.prevRotationYaw + (p.rotationYaw - p.prevRotationYaw);
        float f3 = MathHelper.cos(-f2 * 0.017453292F - (float)Math.PI);
        float f4 = MathHelper.sin(-f2 * 0.017453292F - (float)Math.PI);
        float f5 = -MathHelper.cos(-f1 * 0.017453292F);
        float f6 = MathHelper.sin(-f1 * 0.017453292F);
        float f7 = f4 * f5;
        float f8 = f3 * f5;
        double d3 = 32.0D;
        Vec3 distanced = lookVec.addVector((double)f7 * d3, (double)f6 * d3, (double)f8 * d3);
		MovingObjectPosition mop = p.worldObj.func_147447_a(lookVec, distanced, true, false, false);
		if(mop != null && mop.typeOfHit == MovingObjectType.BLOCK)
		{
			if((ECUtils.tryToDecreaseMRUInStorage(p, -10000) || this.setMRU(stk, -10000)))
			{
				EntityOrbitalStrike eos = new EntityOrbitalStrike(w,mop.blockX+0.5D,mop.blockY+1,mop.blockZ+0.5D,128,3,p);
				
				if(!w.isRemote)
					w.spawnEntityInWorld(eos);
			}
		}
		
        return stk;
    }
	
}
