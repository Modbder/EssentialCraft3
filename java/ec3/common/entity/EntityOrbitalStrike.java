package ec3.common.entity;

import java.util.List;

import ec3.utils.common.ECUtils;
import DummyCore.Utils.MathUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityOrbitalStrike extends Entity
{
	
	public EntityOrbitalStrike(World w) {
		super(w);
	}
	
	public EntityOrbitalStrike(World w, double x, double y, double z) {
		this(w);
		this.setPositionAndRotation(x, y, z, 0, 0);
	}
	
	public EntityOrbitalStrike(World w, double x, double y, double z, double damage, double delay, EntityLivingBase base) {
		this(w,x,y,z);
		this.damage = damage;
		this.delay = delay;
	}
	
	public EntityLivingBase attacker;
	public double delay = 3;
	public double damage = 1;
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
		
		if(!this.worldObj.isRemote)
			this.getDataWatcher().updateObject(12, String.valueOf(delay));
		
		if(this.ticksExisted == 3)
			ECUtils.playSoundToAllNearby(posX, posY, posZ, "essentialcraft:sound.orbital_strike", 1, 1F, 16,this.dimension);
		
		if(delay <= 0 && !this.isDead)
		{
			List<EntityLivingBase> allEntities = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(posX-0.5D, posY-0.5D, posZ-0.5D, posX+0.5D, posY+0.5D, posZ+0.5D).expand(2, 2, 2));
			for(int i = 0; i < allEntities.size(); ++i)
			{
				
				EntityLivingBase elb = allEntities.get(i);
				
				if(elb == null)
					continue;
				
				if(elb.isDead)
					continue;
				
				if(elb == this.attacker)
					continue;
				
				elb.attackEntityFrom(new DamageSource("orbitalStrike"){
					
				    public Entity getEntity()
				    {
				        return attacker;
				    }
					
				}
				.setDamageIsAbsolute(), (float) damage);
			}
			this.setDead();
			
			for(int i = 0; i < 3; ++i)
				this.worldObj.playSound(posX, posY, posZ, "random.explode", 1, this.rand.nextFloat()*2, false);
			
			for(int i = 0; i < 20; ++i)
				this.worldObj.spawnParticle("hugeexplosion", posX+MathUtils.randomDouble(rand), posY+MathUtils.randomDouble(rand), posZ+MathUtils.randomDouble(rand), 0, 0, 0);
		
			if(this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"))
				for(int dx = -2; dx <= 2; ++dx)
				{
					int x = MathHelper.floor_double(posX) + dx;
					for(int dy = -2; dy <= 2; ++dy)
					{
						int y = MathHelper.floor_double(posY) + dy;
						for(int dz = -2; dz <= 2; ++dz)
						{
							int z = MathHelper.floor_double(posZ) + dz;
							Block b = this.worldObj.getBlock(x, y, z);
							if(!b.isAir(worldObj, x, y, z))
							{
								if(b.getMaterial() == Material.water || b.getMaterial() == Material.ice || b.getMaterial() == Material.snow)
								{
									if(!this.worldObj.isRemote)
										this.worldObj.setBlock(x, y, z, Blocks.air,0,2);
									
									worldObj.playSoundEffect((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), "random.fizz", 0.5F, 2.6F + (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.8F);
				                   
									for (int l = 0; l < 8; ++l)
				                    {
				                    	worldObj.spawnParticle("largesmoke", (double)x + Math.random(), (double)y + Math.random(), (double)z + Math.random(), 0.0D, 0.0D, 0.0D);
				                    }
									
									continue;
								}
								ItemStack is = new ItemStack(b,1,worldObj.getBlockMetadata(x, y, z));
								ItemStack result = FurnaceRecipes.smelting().getSmeltingResult(is);
								if(result != null)
								{
									if(result.getItem() instanceof ItemBlock)
									{
										Block setTo = ItemBlock.class.cast(result.getItem()).field_150939_a;
										if(setTo != null && !this.worldObj.isRemote)
											this.worldObj.setBlock(x, y, z, setTo,result.getItemDamage(),2);
									}else
									{
										if(!this.worldObj.isRemote)
											this.worldObj.setBlock(x, y, z, Blocks.air,0,2);
										
										EntityItem itm = new EntityItem(this.worldObj,x,y,z,result.copy());
										
										if(!this.worldObj.isRemote)
											this.worldObj.spawnEntityInWorld(itm);
									}
								}
								
								is = null;
								result = null;
							}
						}	
					}
				}
		}
		
		if(this.worldObj.isRemote)
		{
			try
			{
				this.delay = Double.parseDouble(this.getDataWatcher().getWatchableObjectString(12));
			}catch(Exception e)
			{
				
			}
			
		}
	}
	
	

}
