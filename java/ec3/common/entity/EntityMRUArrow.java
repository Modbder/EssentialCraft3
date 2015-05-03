package ec3.common.entity;

import DummyCore.Utils.MathUtils;
import ec3.common.mod.EssentialCraftCore;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.world.World;

public class EntityMRUArrow extends EntityArrow{
	
    public EntityMRUArrow(World p_i1753_1_)
    {
        super(p_i1753_1_);
    }


	public EntityMRUArrow(World p_i1756_1_, EntityLivingBase p_i1756_2_,float p_i1756_3_) 
	{
		super(p_i1756_1_, p_i1756_2_, p_i1756_3_);
		this.canBePickedUp = 0;
	}
	
    public void onUpdate()
    {
        super.onUpdate();
        if(this.ticksExisted > 60)
        	this.setDead();
        this.motionX *= 0.8F;
        //this.motionY *= 0.8F;
        this.motionZ *= 0.8F;
        for(int i = 0; i < 2; ++i)
        {
        	EssentialCraftCore.proxy.spawnParticle("cSpellFX", (float)posX+MathUtils.randomFloat(rand)/10, (float)posY+MathUtils.randomFloat(rand)/10, (float)posZ+MathUtils.randomFloat(rand)/10, motionX*10, motionY*10, motionZ*10);
        }
    }

}
