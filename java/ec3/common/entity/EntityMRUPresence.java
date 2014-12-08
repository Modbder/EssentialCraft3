package ec3.common.entity;

import java.util.List;

import DummyCore.Utils.DummyData;
import DummyCore.Utils.MathUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ec3.api.IMRUPressence;
import ec3.common.block.BlockCorruption_Light;
import ec3.common.block.BlocksCore;
import ec3.utils.common.ECUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

public class EntityMRUPresence extends Entity implements IMRUPressence
{
	
	public int mruAmount;
	public float balance = 1.0F;
	public float renderIndex;
	public boolean canStay = true;
	public boolean flag = false;
	public int tickTimer;
    public EntityMRUPresence(World par1World)
    {
        super(par1World);
        this.setBalance(1.0F);
    }

	@Override
	protected void entityInit(){
		this.dataWatcher.addObject(15, 1000000);
		this.dataWatcher.addObject(16, 1);
		this.dataWatcher.addObject(17, 0);
		this.dataWatcher.addObject(18, 1);
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound var1)
	{
		if(!this.worldObj.isRemote)
		this.setMRU(var1.getInteger("mru"));
		this.updateMRU();
		if(!this.worldObj.isRemote)
		this.setBalance(var1.getFloat("Balance"));
		this.updateBalance();
		if(!this.worldObj.isRemote)
		this.setAlwaysStay(var1.getBoolean("stay"));
		this.updateStay();
		if(!this.worldObj.isRemote)
		this.setFlag(var1.getBoolean("flag"));
		this.updateFlag();
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound var1)
	{
		var1.setInteger("mru", this.getMRU());
		var1.setFloat("Balance", this.getBalance());
		var1.setBoolean("stay", this.canAlwaysStay());
		var1.setBoolean("flag", this.getFlag());
	}
	
	@Override
    protected boolean canTriggerWalking(){return false;}
	
	@Override
	protected void updateFallState(double par1, boolean par3){}
	
	@Override
	public void setFire(int par1){}
	
	@Override
	protected void setOnFireFromLava(){}
	
	@Override
	public void onEntityUpdate(){
		this.ignoreFrustumCheck = true;
		this.updateMRU();
		this.updateBalance();
		this.updateStay();
		this.updateFlag();
		if(!this.isDead)
		{
			List<EntityMRUPresence> l = this.worldObj.getEntitiesWithinAABB(EntityMRUPresence.class, AxisAlignedBB.getBoundingBox(posX-0.5D, posY-0.5D, posZ-0.5D, posX+0.5D, posY+0.5D, posZ+0.5D));
			if(!l.isEmpty())
			{
				for(int i = 0; i < l.size(); ++i)
				{
					EntityMRUPresence presence = l.get(i);
					if(presence != this && !presence.isDead)
					{
						presence.setDead();
						this.setMRU(this.getMRU()/2+presence.getMRU()/2);
						this.setBalance((this.getBalance()+presence.getBalance())/2);
					}
				}
			}
		}
		if(!this.worldObj.isRemote)
		{
			if(tickTimer <= 0)
			{
				tickTimer = 20;
				float diff = 0F;
				Block id = BlocksCore.lightCorruption[3];
				if(getBalance() < 1F)
				{
					id = BlocksCore.lightCorruption[1];
					diff = 1F-getBalance();
				}
				if(getBalance() > 1F)
				{
					id = BlocksCore.lightCorruption[0];
					diff = getBalance()-1F;
				}
				float mainMRUState = (diff*getMRU()/60000F)*100F;
				Vec3 vec = Vec3.createVectorHelper(0, 1, 0);
				vec.rotateAroundX(this.worldObj.rand.nextFloat()*360);
				vec.rotateAroundY(this.worldObj.rand.nextFloat()*360);
				vec.rotateAroundZ(this.worldObj.rand.nextFloat()*360);
				for(int i = 0; i < mainMRUState; ++i)
				{
					Vec3 vc = Vec3.createVectorHelper(vec.xCoord*i, vec.yCoord*i, vec.zCoord*i);
					Vec3 vc1 = Vec3.createVectorHelper(vec.xCoord*(i+1), vec.yCoord*(i+1), vec.zCoord*(i+1));
					Block blk = this.worldObj.getBlock((int)(vc.xCoord+posX),(int)(vc.yCoord+posY),(int)(vc.zCoord+posZ));
					Block blk1 = this.worldObj.getBlock((int)(vc1.xCoord+posX),(int)(vc1.yCoord+posY),(int)(vc1.zCoord+posZ));
					int meta = this.worldObj.getBlockMetadata((int)(vc1.xCoord+posX),(int)(vc1.yCoord+posY),(int)(vc1.zCoord+posZ));
					float resistance = 1F;
					if(ECUtils.ignoreMeta.containsKey(blk1.getUnlocalizedName()) && ECUtils.ignoreMeta.get(blk1.getUnlocalizedName()))
					{
						meta = -1;
					}
					DummyData dt = new DummyData(blk1.getUnlocalizedName(),meta);
					if(ECUtils.mruResistance.containsKey(dt.toString()))
					{
						resistance = ECUtils.mruResistance.get(dt.toString());
					}else
					{
						resistance = 1F;
					}
					dt = null;
					if(!(blk1 instanceof BlockCorruption_Light) && !(blk instanceof BlockCorruption_Light) && blk1 != Blocks.air && blk == Blocks.air)
					{
						if(!this.worldObj.isRemote && this.worldObj.rand.nextInt((int) (1000*resistance)) <= mainMRUState)
						{
							this.worldObj.setBlock((int)(vc.xCoord+posX),(int)(vc.yCoord+posY),(int)(vc.zCoord+posZ), id, 0, 3);
							break;
						}
					}
					if(blk instanceof BlockCorruption_Light)
					{
						int metadata = this.worldObj.getBlockMetadata((int)(vc.xCoord+posX),(int)(vc.yCoord+posY),(int)(vc.zCoord+posZ));
						if(metadata < 7 && this.worldObj.rand.nextInt((int) (1000*resistance)) <= mainMRUState)
						{
							this.worldObj.setBlockMetadataWithNotify((int)(vc.xCoord+posX),(int)(vc.yCoord+posY),(int)(vc.zCoord+posZ), metadata+1, 3);
						}
					}
				}
				vec = null;
			}else
			{
				--tickTimer;
			}
		}else
		{
			if(this.worldObj.rand.nextFloat() < 0.025F)
				this.worldObj.playSound(posX, posY, posZ, "essentialcraft:sound.mrucu_noise", (float)this.getMRU()/60000F, 0.1F+this.worldObj.rand.nextFloat(), false);
		}
		if(this.getMRU() > 60000)
		{
			this.setMRU(60000);
		}
		/*
		if(this.getMRU()>10000&&!this.worldObj.isRemote)
		{
			this.setFlag(false);
		}
		if(!this.canAlwaysStay())
		{
			if(this.getMRU()<=0&&this.getFlag())
			{
				this.setDead();
			}
		}
		
		if(this.getBalance()!=1.0F&&EssentialCraftCore.core.isCorruptionAllowed())
		{
			if(!this.worldObj.isRemote)
			{
				if(this.worldObj.rand.nextFloat()<0.05F)
				{
					if(this.getBalance()>1.0F && this.getBalance()<2.0F)
					{
						this.setBalance(this.getBalance()+this.worldObj.rand.nextFloat()/3000);
					}
					if(this.getBalance()<1.0F && this.getBalance()>0.0F)
					{
						this.setBalance(this.getBalance()-this.worldObj.rand.nextFloat()/3000);
					}
				}
			}
			if(this.getMRU()<=50000)
			{
				if(!this.worldObj.isRemote)
				{
					float diff = 0;
					if(this.getBalance()>1.0F)
					{
						diff = this.getBalance()-1.0F;
					}
					if(this.getBalance()<1.0F)
					{
						diff = 1.0F-this.getBalance();
					}
					if(diff<0.1F)
					{
						//this.setBalance(1.0F);
						//this.setFlag(true);
					}
					if(this.getMRU()>10000)
					{
						this.setFlag(false);
					}
					if(!this.getFlag())
					{
						if((int)(diff*5)>0)
						{
							this.setMRU(this.getMRU()+this.worldObj.rand.nextInt((int)(diff*5)));
						}else
						{
							
						}
					}
				}
			}
			if(!this.worldObj.isRemote && this.getMRU()>=5000)
			{
				List l = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getAABBPool().getAABB((double)this.posX, (double)this.posY, (double)this.posZ, (double)(this.posX + 1), (double)(this.posY + 1), (double)(this.posZ + 1)).expand(12.0D, 6.0D, 12.0D));
				if(!l.isEmpty())
				{
					for(int u = 0; u < l.size(); ++u)
					{
						EntityLivingBase mob = (EntityLivingBase) l.get(u);
						if(!(mob instanceof EntityPlayer))
						{
							if(mob.getAITarget()!=null && mob.getAITarget().isDead)
							{
								mob.setRevengeTarget(null);
							}
							if(mob.getAITarget()==null)
							{
								mob.setRevengeTarget((EntityLivingBase)l.get(this.worldObj.rand.nextInt(l.size())));
								mob.attackEntityAsMob(mob.getAITarget());
							}
						}
					}
				}
			}
			for(int x = -12; x<=12;++x)
			{
				for(int y = -12; y<=12;++y)
				{
					for(int z = -12; z<=12;++z)
					{
						if(this.worldObj.getBlockId(x+(int)this.posX, y+(int)this.posY, z+(int)this.posZ) != BlocksCore.magicCorruption.blockID && this.worldObj.getBlockId(x+(int)this.posX, y+(int)this.posY, z+(int)this.posZ) != 0 && EssentialCraftCore.core.isCorruptionAllowed() && this.getMRU()>27000 && !this.worldObj.isRemote && this.worldObj.rand.nextInt(1000000000)<=this.getMRU())
						{	
							CorruptionUtils.createCorruptionAt(worldObj, new Coord3D(x+(int)this.posX, y+(int)this.posY, z+(int)this.posZ));
						}
					}
				}
			}
			if(this.getMRU() > 9000 && !this.worldObj.isRemote && this.getBalance() != 1.0F)
			{
				DummyCore.Utils.Coord2D pos2d = new DummyCore.Utils.Coord2D((float)posX,(float) posZ);
				DummyCore.Utils.Coord2D offsetpos2d = DummyCore.Utils.MathUtils.polarOffset(pos2d, this.worldObj.rand.nextFloat()*360, this.worldObj.rand.nextInt(32));
				int randY = (int) (this.worldObj.rand.nextInt(32)-16+this.posY);
				int randX = (int) offsetpos2d.x;
				int randZ = (int) offsetpos2d.z;
				int meta = 0;
				if(this.getBalance() < 1.0F)
					meta = 1;
				if(this.getBalance() > 1.0F)
					meta = 0;
				if(this.worldObj.getBlockId(randX, randY, randZ) == 0)
				{
		    		if(this.worldObj.isBlockFullCube(randX, randY+1, randZ) ||
				    	    this.worldObj.isBlockFullCube(randX, randY-1, randZ) ||
				    	    this.worldObj.isBlockFullCube(randX+1, randY, randZ) ||
				    	    this.worldObj.isBlockFullCube(randX-1, randY, randZ) ||
				    	    this.worldObj.isBlockFullCube(randX, randY, randZ+1) ||
				    	    this.worldObj.isBlockFullCube(randX, randY, randZ-1))
					this.worldObj.setBlock(randX, randY, randZ, BlocksCore.lightCorruption.blockID, meta, 2);
				}
			}
			if(this.getMRU()>15000)
			{
				List l = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getAABBPool().getAABB((double)this.posX, (double)this.posY, (double)this.posZ, (double)(this.posX + 1), (double)(this.posY + 1), (double)(this.posZ + 1)).expand(12.0D, 6.0D, 12.0D));
				if(!l.isEmpty())
				{
					for(int u = 0; u < l.size(); ++u)
					{
						EntityLivingBase e = (EntityLivingBase) l.get(u);
						if(e instanceof EntityPlayer && ((EntityPlayer)e).capabilities.isCreativeMode)
						{
						}else if(!(e instanceof EntityBlaze))
						{
							e.addPotionEffect(new PotionEffect(Potion.hunger.id,100,0));
							if(this.getMRU()>20000)
							e.addPotionEffect(new PotionEffect(Potion.poison.id,100,0));
							if(this.getMRU()>25000)
							e.addPotionEffect(new PotionEffect(Potion.blindness.id,100,0));
							if(this.getMRU()>30000)
							e.addPotionEffect(new PotionEffect(Potion.confusion.id,100,0));
						}
					}
				}
			}
			if(this.getMRU()>30000)
			{
				if(!this.worldObj.isRemote && this.worldObj.rand.nextFloat() < 0.2F)
				{
					for(int i = 0; i < this.worldObj.rand.nextInt(20)+5;++i)
					{
						EntityMagicalCorruptor snowball = new EntityMagicalCorruptor(worldObj, posX, posY, posZ);
						float yaw = this.worldObj.rand.nextInt(360);
						float pitch = 360 - this.worldObj.rand.nextInt(360);
		            	snowball.setPositionAndRotation(posX, posY, posZ, yaw, pitch);
		            	float var3 = 0.4F;
		            	snowball.motionX = (double)(-MathHelper.sin(snowball.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(snowball.rotationPitch / 180.0F * (float)Math.PI) * var3);
		            	snowball.motionZ = (double)(MathHelper.cos(snowball.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(snowball.rotationPitch / 180.0F * (float)Math.PI) * var3);
		            	snowball.motionY = (double)(-MathHelper.sin((snowball.rotationPitch + 1.5F) / 180.0F * (float)Math.PI) * var3);
		            	snowball.setThrowableHeading(snowball.motionX, snowball.motionY, snowball.motionZ, 1.5F, 1.0F);
		            	this.worldObj.spawnEntityInWorld(snowball);
					}
				}
			}
			if(this.getMRU()>35000)
			{
				if(!this.worldObj.isRemote && this.worldObj.rand.nextFloat() < 0.005F)
				{
					for(int i = 0; i < this.worldObj.rand.nextInt(2)+1;++i)
					{
						EntityItem blaze = new EntityItem(worldObj,posX, posY+1, posZ,new ItemStack(Item.ghastTear,1,0));
						blaze.setPositionAndRotation(posX, posY+1, posZ, 90, 90);
						
						this.worldObj.spawnEntityInWorld(blaze);
						if(!this.worldObj.isRemote && this.worldObj.rand.nextFloat() < 0.2F)
						{
							for(int s = 0; s < this.worldObj.rand.nextInt(20)+5;++s)
							{
								EntityMagicalCorruptor snowball = new EntityMagicalCorruptor(worldObj, posX, posY, posZ);
								float yaw = this.worldObj.rand.nextInt(360);
								float pitch = this.worldObj.rand.nextInt(180);
				            	snowball.setPositionAndRotation(posX, posY, posZ, yaw, pitch);
				            	float var3 = 0.4F;
				            	snowball.motionX = (double)(-MathHelper.sin(snowball.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(snowball.rotationPitch / 180.0F * (float)Math.PI) * var3);
				            	snowball.motionZ = (double)(MathHelper.cos(snowball.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(snowball.rotationPitch / 180.0F * (float)Math.PI) * var3);
				            	snowball.motionY = (double)(-MathHelper.sin((snowball.rotationPitch + 1.5F) / 180.0F * (float)Math.PI) * var3);
				            	snowball.setThrowableHeading(snowball.motionX, snowball.motionY, snowball.motionZ, 1.5F, 1.0F);
				            	this.worldObj.spawnEntityInWorld(snowball);
							}
						}
					}
				}

			}
			if(this.getMRU()>40000)
			{
				if(!this.worldObj.isRemote && this.worldObj.rand.nextFloat()<0.05F)
				{
					this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, this.worldObj.rand.nextFloat()*4, true);
				}
			}
			if(this.getMRU()>45000)
			{
				if(!this.worldObj.isRemote && this.worldObj.rand.nextFloat()<0.01F)
				{
    				EntityLightningBolt el = new EntityLightningBolt(this.worldObj, this.posX+this.worldObj.rand.nextInt(8)-this.worldObj.rand.nextInt(8), this.posY, this.posZ+this.worldObj.rand.nextInt(8)-this.worldObj.rand.nextInt(8));
    				//if()
    				this.worldObj.spawnEntityInWorld(el);
    				this.worldObj.addWeatherEffect(el);
				}
			}
			if(this.getMRU()>=49600)
			{
				if(!this.worldObj.isRemote)
				{
					EntityLightningBolt el = new EntityLightningBolt(this.worldObj, this.posX, this.posY, this.posZ);
    				this.worldObj.spawnEntityInWorld(el);
    				this.worldObj.addWeatherEffect(el);
    				this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, this.worldObj.rand.nextFloat()*4, true);
				}
			}
			if(this.getMRU()>=50000)
			{
				this.renderIndex += 0.01F;
				if(this.getBalance()>1.0F && !this.worldObj.isRemote)
				{
					this.setBalance(this.getBalance()-this.worldObj.rand.nextFloat()/5);
				}
				if(this.getBalance()<1.0F && !this.worldObj.isRemote)
				{
					this.setBalance(this.getBalance()+this.worldObj.rand.nextFloat()/5);
				}
				float diff = 0;
				if(this.getBalance()>1.0F)
				{
					diff = this.getBalance()-1.0F;
				}
				if(this.getBalance()<1.0F)
				{
					diff = 1.0F-this.getBalance();
				}
				if(diff <= 0.1F && !this.worldObj.isRemote)
				{
					this.setBalance(1.0F);
					this.setMRU(1000);
					for(int i = 0; i < 1000;++i)
					{
						EntityMagicalCorruptor snowball = new EntityMagicalCorruptor(worldObj, posX, posY, posZ);
						float yaw = this.worldObj.rand.nextInt(360);
						float pitch = this.worldObj.rand.nextInt(180);
		            	snowball.setPositionAndRotation(posX, posY, posZ, yaw, pitch);
		            	float var3 = 0.4F;
		            	snowball.motionX = (double)(-MathHelper.sin(snowball.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(snowball.rotationPitch / 180.0F * (float)Math.PI) * var3);
		            	snowball.motionZ = (double)(MathHelper.cos(snowball.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(snowball.rotationPitch / 180.0F * (float)Math.PI) * var3);
		            	snowball.motionY = (double)(-MathHelper.sin((snowball.rotationPitch + 1.5F) / 180.0F * (float)Math.PI) * var3);
		            	snowball.setThrowableHeading(snowball.motionX, snowball.motionY, snowball.motionZ, 1.5F, 1.0F);
		            	this.worldObj.spawnEntityInWorld(snowball);
					}
					this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 250, true);
					for(int x = -12; x<=12;++x)
					{
						for(int y = -12; y<=12;++y)
						{
							for(int z = -12; z<=12;++z)
							{
								if(this.worldObj.getBlockId(x+(int)this.posX, y+(int)this.posY, z+(int)this.posZ) != BlocksCore.magicCorruption.blockID && this.worldObj.getBlockId(x+(int)this.posX, y+(int)this.posY, z+(int)this.posZ) != 0 && EssentialCraftCore.core.isCorruptionAllowed() && this.getMRU()>9000 && !this.worldObj.isRemote && this.worldObj.rand.nextInt(1000000000)<=this.getMRU())
								{	
									CorruptionUtils.createCorruptionAt(worldObj, new Coord3D(x+(int)this.posX, y+(int)this.posY, z+(int)this.posZ));
								}
							}
						}
					}
				}
			}
		}
		*/
    	renderIndex += 0.001F*this.balance;
    	if(renderIndex>=1F)renderIndex=0F;
    	
		}
	
	public void updateMRU()
	{
		if(!this.worldObj.isRemote)
		{
			this.setMRU(this.getMRU());
		}
		if(this.worldObj.isRemote)
		{
			this.mruAmount = this.getMRU();
		}
	}
	
	public void setMRU(int i)
	{
		this.dataWatcher.updateObject(16, i);
	}
	
	public int getMRU()
	{
		return this.dataWatcher.getWatchableObjectInt(16);
	}
	
	public void updateBalance()
	{
		if(!this.worldObj.isRemote)
		{
			this.setBalance(this.getBalance());
		}
		if(this.worldObj.isRemote)
		{
			this.balance = this.getBalance();
		}
	}
	
	public float getBalance()
	{
		return ((float)this.dataWatcher.getWatchableObjectInt(15))/1000000;
	}
	
	public void setBalance(float f)
	{
		this.dataWatcher.updateObject(15, (int)(f*1000000));
	}
	
	public boolean canAlwaysStay()
	{
		return this.dataWatcher.getWatchableObjectInt(17)==0;
	}
	
	public void setAlwaysStay(boolean b)
	{
		if(b)
		{
			this.dataWatcher.updateObject(17, 0);
		}else
		{
			this.dataWatcher.updateObject(17, 1);
		}
	}
	
	public void updateStay()
	{
		if(!this.worldObj.isRemote)
		{
			this.setAlwaysStay(this.canAlwaysStay());
		}
		if(this.worldObj.isRemote)
		{
			this.canStay = this.canAlwaysStay();
		}
	}
	
	public void setFlag(boolean b)
	{
		if(b)
		{
			this.dataWatcher.updateObject(18, 0);
		}else
		{
			this.dataWatcher.updateObject(18, 1);
		}
	}
	
	public boolean getFlag()
	{
		return this.dataWatcher.getWatchableObjectInt(18)==0;
	}
	
	public void updateFlag()
	{
		if(!this.worldObj.isRemote)
		{
			this.setFlag(this.getFlag());
		}
		if(this.worldObj.isRemote)
		{
			this.flag = this.getFlag();
		}
	}
	
	@Override
    protected void fall(float par1){}
	
	@Override
    protected void dealFireDamage(int par1){}
	
	@Override
	public boolean handleWaterMovement(){return false;}
	
	@Override
	public boolean isInsideOfMaterial(Material par1Material){return false;}
	
	@Override
	public boolean handleLavaMovement(){return false;}
	
	@Override
	public void moveFlying(float par1, float par2, float par3){}
	
	@Override
	public int getBrightnessForRender(float par1){return 1;}
	
	@Override
	public float getBrightness(float par1){return 1.0F;}
	
	@Override
	public void applyEntityCollision(Entity par1Entity){}
	
	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2){return false;}
	
	@Override
	public boolean isEntityInsideOpaqueBlock(){return false;}
	
	@Override
	public float getCollisionBorderSize(){return 0.0F;}
	
	@Override
	public void onStruckByLightning(EntityLightningBolt par1EntityLightningBolt){}
	
	@Override
	public ItemStack getPickedResult(MovingObjectPosition target){return null;}
    
}
