package ec3.common.entity;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityDivider extends Entity
{
	
	public EntityDivider(World w) {
		super(w);
	}
	
	public EntityDivider(World w, double x, double y, double z) {
		this(w);
		this.setPositionAndRotation(x, y, z, 0, 0);
	}
	
	public EntityDivider(World w, double x, double y, double z, double damage, double delay, EntityLivingBase base) {
		this(w,x,y,z);
		this.damage = damage;
		this.delay = delay;
	}
	
	public EntityLivingBase attacker;
	public double delay = 2;
	public double damage = 1;
	public static final double RANGE = 3;
	@Override
	protected void entityInit() 
	{
		this.getDataWatcher().addObject(12, "||null:null");
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound tag) {
		delay = tag.getDouble("delay");
		damage = tag.getDouble("damage");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound tag) {
		tag.setDouble("delay", delay);
		tag.setDouble("damage", damage);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void onUpdate()
	{
		delay -= 0.05D;
		
		if(this.ticksExisted % 10 == 0)
			this.playSound("creeper.primed", 1.0F, 0.5F);
		
		this.worldObj.spawnParticle("reddust", posX, posY, posZ, 1, 0, 1);
		
		if(delay <= 0 && !this.isDead)
		{
			List<EntityLivingBase> allEntities = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(posX-0.5D, posY-0.5D, posZ-0.5D, posX+0.5D, posY+0.5D, posZ+0.5D).expand(3, 3, 3));
			for(int i = 0; i < allEntities.size(); ++i)
			{
				
				EntityLivingBase elb = allEntities.get(i);
				
				if(elb == null)
					continue;
				
				if(elb.isDead)
					continue;
				
				if(elb == this.attacker)
					continue;
				
				if(elb instanceof EntityHologram)
					continue;
				
				if(elb.getDistanceToEntity(this) > 3)
					continue;
				
				if(this.worldObj.isRemote)
					return;
				
				elb.setHealth(elb.getHealth()/2);
				elb.addPotionEffect(new PotionEffect(Potion.weakness.id,200,4,true));
				elb.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id,200,4,true));
				elb.addPotionEffect(new PotionEffect(Potion.digSlowdown.id,200,4,true));
				elb.addPotionEffect(new PotionEffect(Potion.blindness.id,100,0,true));
				
			}
			this.setDead();

		}
	}
	
	

}
