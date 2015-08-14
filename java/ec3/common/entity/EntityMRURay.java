package ec3.common.entity;

import java.util.ArrayList;
import java.util.List;

import ec3.utils.common.ECUtils;
import ec3.utils.common.ShadeUtils;
import DummyCore.Utils.DataStorage;
import DummyCore.Utils.DummyData;
import DummyCore.Utils.MathUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityMRURay extends Entity
{

	public float balance;
	public float damage;
	public EntityLivingBase shootingEntity;
	public double pX,pY,pZ;
	List<EntityLivingBase> hitEntities = new ArrayList<EntityLivingBase>();
	
	public EntityMRURay(World w)
	{
		super(w);
	}
	
    public void onEntityUpdate()
    {
    	if(this.ticksExisted >= 60)
    	{
    		this.setDead();
    	}
    	
    	if(!this.worldObj.isRemote)
    	{
    		this.getDataWatcher().updateObject(12, "||x:"+pX+"||y:"+pY+"||z:"+pZ+"||b:"+(double)balance);
    	}
    	
    	if(this.worldObj.isRemote)
    	{
    		String dataStr = this.getDataWatcher().getWatchableObjectString(12);
    		if(dataStr != null && !dataStr.isEmpty())
    		{
    			DummyData[] posData = DataStorage.parseData(dataStr);
    			pX = Double.parseDouble(posData[0].fieldValue);
    			pY = Double.parseDouble(posData[1].fieldValue);
    			pZ = Double.parseDouble(posData[2].fieldValue);
    			balance = (float) Double.parseDouble(posData[3].fieldValue);
    		}
    	}
    	
    }
	
	public EntityMRURay(World w, EntityLivingBase base)
	{
		super(w);
		this.rotationYaw = base.rotationYawHead;
		this.rotationPitch = base.rotationPitch;
		this.shootingEntity = base;
		pX = this.shootingEntity.posX;
		pY = this.shootingEntity.posY+this.shootingEntity.getEyeHeight();
		pZ = this.shootingEntity.posZ;
		this.posX = base.posX;
		this.posY = base.posY+this.shootingEntity.getEyeHeight();
		this.posZ = base.posZ;
	}
	
	public EntityMRURay(World w, EntityLivingBase base, float damage, float offset, float balance)
	{
		
		super(w);
		this.posX = base.posX;
		this.posY = base.posY+base.getEyeHeight();
		this.posZ = base.posZ;
		float rY = base.rotationYaw;
		float rP = base.rotationPitch;
		if(!w.isRemote)
		{
			base.rotationYaw = (float) (base.rotationYawHead + MathUtils.randomDouble(w.rand)*offset);
			base.rotationPitch = (float) (base.rotationPitch + MathUtils.randomDouble(w.rand)*offset);
		}
		this.damage = damage;
		this.balance = balance;
		this.shootingEntity = base;
		pX = this.shootingEntity.posX;
		pY = this.shootingEntity.posY+this.shootingEntity.getEyeHeight();
		pZ = this.shootingEntity.posZ;
		if(!this.worldObj.isRemote)
		{
			this.shoot(rY,rP);
		}
	}
	
	public DamageSource causeMRUDamage(final EntityLivingBase attacker, EntityLivingBase attacked)
	{
		if(attacked instanceof EntityPlayer)
		{
			EntityPlayer player = EntityPlayer.class.cast(attacked);
			if(!player.worldObj.isRemote && MinecraftServer.getServer().isPVPEnabled())
			{
				ECUtils.getData(player).modifyOverhaulDamage(ECUtils.getData(player).getOverhaulDamage() + MathHelper.floor_double(this.damage*100));
			}
			if(this.balance == 4)
			{
				ShadeUtils.attackPlayerWithShade(player, attacker, null);
			}
		}
		return new DamageSource("mru")
		{
			
		    public Entity getEntity()
		    {
		        return attacker;
		    }
		}
		.setProjectile();
	}
	
	@SuppressWarnings("unchecked")
	public void shoot(float f, float f1)
	{
		Vec3 vec = this.shootingEntity.getLookVec();
		for(int i = 0; i < 128; ++i)
		{
			float vX = (float) ((vec.xCoord*(float)i/2F)+this.posX);
			float vY = (float) ((vec.yCoord*(float)i/2F)+this.posY);
			float vZ = (float) ((vec.zCoord*(float)i/2F)+this.posZ);
			int bVX = MathHelper.floor_float(vX);
			int bVY = MathHelper.floor_float(vY);
			int bVZ = MathHelper.floor_float(vZ);
			double d = 0.5D;
			AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(vX-d, vY-d, vZ-d, vX+d, vY+d, vZ+d);
			List<EntityLivingBase> entities = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, aabb);
			for(int j = 0; j < entities.size(); ++j)
			{
				EntityLivingBase base = entities.get(j);
				if(base != shootingEntity && !base.isDead && !hitEntities.contains(base))
				{
					base.attackEntityFrom(causeMRUDamage(this.shootingEntity, base), this.damage);
					this.hitEntities.add(base);
				}
			}
			Block b = this.worldObj.getBlock(bVX, bVY, bVZ);
			if(b.isNormalCube(worldObj, bVX, bVY, bVZ) || i == 127)
			{
				this.setPositionAndRotation(vX, vY, vZ, 0, 0);
				break;
			}
		}
		this.shootingEntity.rotationYaw = f;
		this.shootingEntity.rotationPitch = f1;
	}

	@Override
	protected void entityInit()
	{
		this.getDataWatcher().addObject(12, "");
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound tag) 
	{
		pX = tag.getDouble("pX");
		pY = tag.getDouble("pY");
		pZ = tag.getDouble("pZ");
		balance = tag.getFloat("balance");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound tag) 
	{
		tag.setDouble("pX", pX);
		tag.setDouble("pY", pY);
		tag.setDouble("pZ", pZ);
		tag.setFloat("balance", balance);
	}

}
