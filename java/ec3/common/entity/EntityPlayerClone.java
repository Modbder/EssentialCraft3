package ec3.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class EntityPlayerClone extends EntityZombie{

	public EntityPlayer playerToAttack = null;
	
	public EntityPlayerClone(World w) 
	{
		super(w);
		this.equipmentDropChances[0] = 0;
		this.equipmentDropChances[1] = 0;
		this.equipmentDropChances[2] = 0;
		this.equipmentDropChances[3] = 0;
		this.equipmentDropChances[4] = 0;
	}
	
    public Entity findPlayerToAttack()
    {
    	playerToAttack = this.worldObj.getClosestVulnerablePlayerToEntity(this, 16);
        return playerToAttack;
    }
	
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(2.0D);
    }
    
    protected void dropEquipment(boolean p_82160_1_, int p_82160_2_)
    {
    	//...
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

