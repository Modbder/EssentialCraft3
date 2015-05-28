package ec3.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class EntityPlayerClone extends EntityZombie{

	public EntityPlayerClone(World w) 
	{
		super(w);
	}
	
    protected Entity findPlayerToAttack()
    {
        return this.worldObj.getClosestVulnerablePlayerToEntity(this, 16);
    }
	
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(2.0D);
    }
    
	@Override
	public void onUpdate()
	{
		if(!this.isPotionActive(Potion.moveSpeed.id))
			this.addPotionEffect(new PotionEffect(Potion.moveSpeed.id,200,3,true));
		
		if(this.deathTime > 0)
			this.setDead();
		
		super.onUpdate();
		if(this.ticksExisted % 200 == 0)
			this.setDead();
		
	}
    
    protected String getLivingSound()
    {
        return null;
    }

    protected String getHurtSound()
    {
        return null;
    }

    protected String getDeathSound()
    {
        return null;
    }

}
