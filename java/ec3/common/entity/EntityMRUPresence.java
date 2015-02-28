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
	
	public void merge()
	{
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
	}
	
	
	@Override
	public void onEntityUpdate(){
		this.noClip = true;
		this.ignoreFrustumCheck = true;

		this.merge();
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
				float mainMRUState = (diff*getMRU()/20000F)*10F;
				Vec3 vec = Vec3.createVectorHelper(1, 0, 0);
				vec.rotateAroundX(this.worldObj.rand.nextFloat()*360);
				vec.rotateAroundY(this.worldObj.rand.nextFloat()*360);
				vec.rotateAroundZ(this.worldObj.rand.nextFloat()*360);
				if(!this.getFlag())
				{
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
				}else
				{
					this.setFlag(false);
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
		if(this.getMRU() > 20000)
		{
			this.setMRU(20000);
		}

    	renderIndex += 0.001F*this.balance;
    	if(renderIndex>=1F)renderIndex=0F;
		this.updateMRU();
		this.updateBalance();
		this.updateStay();
		this.updateFlag();
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
		if(this != null && !this.isDead)
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
		if(this != null && !this.isDead)
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
			if(this != null && !this.isDead)
				this.dataWatcher.updateObject(17, 0);
		}else
		{
			if(this != null && !this.isDead)
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
			if(this != null && !this.isDead)
				this.dataWatcher.updateObject(18, 0);
		}else
		{
			if(this != null && !this.isDead)
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
