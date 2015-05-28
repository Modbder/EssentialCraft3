package ec3.common.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

public class EntityDividerProjectile extends EntitySnowball{
	
	public EntityDividerProjectile(World w)
	{
		super(w);
	}

	public EntityDividerProjectile(World p_i1774_1_, EntityLivingBase p_i1774_2_) {
		super(p_i1774_1_, p_i1774_2_);
		// TODO Auto-generated constructor stub
	}
	
    protected void onImpact(MovingObjectPosition p_70184_1_)
    {
        if (p_70184_1_.typeOfHit == MovingObjectType.BLOCK)
        {
        	EntityDivider div = new EntityDivider(this.worldObj,this.posX,this.posY,this.posZ,0,2,this.getThrower());
        	if(!this.worldObj.isRemote)
        		this.worldObj.spawnEntityInWorld(div);
        	
        	this.setDead();
        	
        }
    }

}
