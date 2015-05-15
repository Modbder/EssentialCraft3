package ec3.common.entity;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityWeatherEffect;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntitySolarBeam extends EntityWeatherEffect {
	
	public int beamLiveTime = 20;

	public EntitySolarBeam(World p_i1702_1_) {
		super(p_i1702_1_);
		this.ignoreFrustumCheck = true;
	}
	
	public EntitySolarBeam(World p_i1703_1_, double p_i1703_2_, double p_i1703_4_, double p_i1703_6_)
    {
        super(p_i1703_1_);
        this.setLocationAndAngles(p_i1703_2_, p_i1703_4_, p_i1703_6_, 0.0F, 0.0F);
        this.beamLiveTime = 20;
        if (!p_i1703_1_.isRemote && p_i1703_1_.getGameRules().getGameRuleBooleanValue("doFireTick") && p_i1703_1_.doChunksNearChunkExist(MathHelper.floor_double(p_i1703_2_), MathHelper.floor_double(p_i1703_4_), MathHelper.floor_double(p_i1703_6_), 10))
        {
            int i = MathHelper.floor_double(p_i1703_2_);
            int j = MathHelper.floor_double(p_i1703_4_);
            int k = MathHelper.floor_double(p_i1703_6_);

            if (p_i1703_1_.getBlock(i, j, k).getMaterial() == Material.air && Blocks.fire.canPlaceBlockAt(p_i1703_1_, i, j, k))
            {
                p_i1703_1_.setBlock(i, j, k, Blocks.fire);
            }

            for (i = 0; i < 32; ++i)
            {
                j = MathHelper.floor_double(p_i1703_2_) + this.rand.nextInt(13) - 1;
                k = MathHelper.floor_double(p_i1703_4_) + this.rand.nextInt(13) - 1;
                int l = MathHelper.floor_double(p_i1703_6_) + this.rand.nextInt(13) - 1;

                if (p_i1703_1_.getBlock(j, k, l).getMaterial() == Material.air && Blocks.fire.canPlaceBlockAt(p_i1703_1_, j, k, l))
                {
                    p_i1703_1_.setBlock(j, k, l, Blocks.fire);
                }
            }
        }
    }

    public void onUpdate()
    {
        super.onUpdate();
        if(--beamLiveTime <= 0)
        	this.setDead();
        if((beamLiveTime%5) == 0)
        	this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "fire.fire", 10.0F, 2F);
        double d0 = 6.0D;
        List<?> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, AxisAlignedBB.getBoundingBox(this.posX - d0, this.posY - d0, this.posZ - d0, this.posX + d0, this.posY + 128.0D + d0, this.posZ + d0));

        for (int l = 0; l < list.size(); ++l)
        {
            Entity entity = (Entity)list.get(l);
            entity.setFire(5);
            entity.attackEntityFrom(DamageSource.onFire, 3.0F);
        }
    }
	
	@Override
	protected void entityInit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {
		// TODO Auto-generated method stub
		
	}

}
