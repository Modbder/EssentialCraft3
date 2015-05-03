package ec3.common.entity;

import ec3.common.item.ItemsCore;
import ec3.utils.common.ShadeUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityShadowKnife extends EntityThrowable{

	public EntityShadowKnife(World w, EntityLivingBase t)
	{
		super(w, t);
	}
	
    public EntityShadowKnife(World w)
    {
    	super(w);
    }


	@Override
	protected void onImpact(MovingObjectPosition mop) 
	{
		if(mop.entityHit != null)
		{
			if(mop.entityHit instanceof EntityLivingBase)
			{
				EntityLivingBase.class.cast(mop.entityHit).attackEntityFrom(DamageSource.causeMobDamage(getThrower()), 12);
				this.teleportRandomly(mop.entityHit);
				this.setDead();
				if(mop.entityHit instanceof EntityPlayer)
				{
					ShadeUtils.attackPlayerWithShade(EntityPlayer.class.cast(mop.entityHit), getThrower(), new ItemStack(ItemsCore.shadeKnife,1,0));
				}
			}
		}
	}
	
    protected boolean teleportRandomly(Entity e)
    {
        double d0 = e.posX + (this.rand.nextDouble() - 0.5D) * 64.0D;
        double d1 = e.posY + (double)(this.rand.nextInt(64) - 32);
        double d2 = e.posZ + (this.rand.nextDouble() - 0.5D) * 64.0D;
        return this.teleport(e,d0, d1, d2);
    }
    
    protected boolean teleport(Entity e,double x, double y, double z)
    {
        double d3 = e.posX;
        double d4 = e.posY;
        double d5 = e.posZ;
        e.posX = x;
        e.posY = y;
        e.posZ = z;
        boolean flag = false;
        int i = MathHelper.floor_double(e.posX);
        int j = MathHelper.floor_double(e.posY);
        int k = MathHelper.floor_double(e.posZ);

        if (this.worldObj.blockExists(i, j, k))
        {
            boolean flag1 = false;

            while (!flag1 && j > 0)
            {
                Block block = e.worldObj.getBlock(i, j - 1, k);

                if (block.getMaterial().blocksMovement())
                {
                    flag1 = true;
                }
                else
                {
                    --e.posY;
                    --j;
                }
            }

            if (flag1)
            {
                e.setPosition(e.posX, e.posY, e.posZ);

                if (e.worldObj.getCollidingBoundingBoxes(e, e.boundingBox).isEmpty() && !e.worldObj.isAnyLiquid(e.boundingBox))
                {
                    flag = true;
                }
            }
        }

        if (!flag)
        {
            e.setPosition(d3, d4, d5);
            return false;
        }
        else
        {
            short short1 = 128;

            for (int l = 0; l < short1; ++l)
            {
                double d6 = (double)l / ((double)short1 - 1.0D);
                float f = (this.rand.nextFloat() - 0.5F) * 0.2F;
                float f1 = (this.rand.nextFloat() - 0.5F) * 0.2F;
                float f2 = (this.rand.nextFloat() - 0.5F) * 0.2F;
                double d7 = d3 + (e.posX - d3) * d6 + (this.rand.nextDouble() - 0.5D) * (double)e.width * 2.0D;
                double d8 = d4 + (e.posY - d4) * d6 + this.rand.nextDouble() * (double)e.height;
                double d9 = d5 + (e.posZ - d5) * d6 + (this.rand.nextDouble() - 0.5D) * (double)e.width * 2.0D;
                e.worldObj.spawnParticle("portal", d7, d8, d9, (double)f, (double)f1, (double)f2);
            }

            e.worldObj.playSoundEffect(d3, d4, d5, "mob.endermen.portal", 1.0F, 1.0F);
            e.playSound("mob.endermen.portal", 1.0F, 1.0F);
            return true;
        }
    }


}
