package ec3.common.entity;

import java.util.List;

import baubles.api.BaublesApi;
import DummyCore.Utils.MathUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ec3.common.item.BaublesModifier;
import ec3.common.mod.EssentialCraftCore;
import ec3.utils.common.ECUtils;
import ec3.utils.common.RadiationManager;
import ec3.utils.common.WindRelations;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityPoisonFume extends EntityMob
{
    /** Random offset used in floating behaviour */
    private float heightOffset = 0.5F;
    /** ticks until heightOffset is randomized */
    private int heightOffsetUpdateTime;
    private int field_70846_g;
    
    public double mX, mY, mZ;

    public EntityPoisonFume(World p_i1731_1_)
    {
        super(p_i1731_1_);
        this.isImmuneToFire = true;
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
    }
    
    public boolean attackEntityFrom(DamageSource p_70097_1_, float p_70097_2_)
    {
    	return false;
    }

    protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(16, new Byte((byte)0));
    }

    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender(float p_70070_1_)
    {
        return 15728880;
    }

    /**
     * Gets how bright this entity is.
     */
    public float getBrightness(float p_70013_1_)
    {
        return 1.0F;
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        if (!this.worldObj.isRemote)
        {
            --this.heightOffsetUpdateTime;

            if (this.heightOffsetUpdateTime <= 0)
            {
                this.heightOffsetUpdateTime = 100;
                this.mX = MathUtils.randomDouble(this.worldObj.rand);
                this.mY = MathUtils.randomDouble(this.worldObj.rand);
                this.mZ = MathUtils.randomDouble(this.worldObj.rand);
                this.heightOffset = 0.5F + (float)this.rand.nextGaussian() * 3.0F;
            }
            this.motionX = mX/10;
            this.motionY = mY/10;
            this.motionZ = mZ/10;
            if(this.ticksExisted > 1000)
            	this.setDead();
        }
        EssentialCraftCore.proxy.spawnParticle("fogFX", (float)posX, (float)posY+2, (float)posZ, 0.0F, 1.0F, 0.0F);
        List<EntityPlayer> players = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(posX-1, posY-1, posZ-1, posX+1, posY+1, posZ+1).expand(6, 3, 6));
        for(int i = 0; i < players.size(); ++i)
        {
        	EntityPlayer p = players.get(i);
    		boolean ignorePoison = false;
        	IInventory b = BaublesApi.getBaubles(p);
        	if(b != null)
        	{
        		for(int i1 = 0; i1 < b.getSizeInventory(); ++i1)
        		{
        			ItemStack is = b.getStackInSlot(i1);
        			if(is != null && is.getItem() != null && is.getItem() instanceof BaublesModifier && is.getItemDamage() == 19)
        				ignorePoison = true;
        		}
        	}
        	if(!p.worldObj.isRemote && !ignorePoison)
        	{
        		RadiationManager.increasePlayerRadiation(p, 10);
	        	p.addPotionEffect(new PotionEffect(Potion.poison.id,200,1));
        	}
        }
        super.onLivingUpdate();
    }

    /**
     * Basic mob attack. Default to touch of death in EntityCreature. Overridden by each mob to define their attack.
     */
    protected void attackEntity(Entity p_70785_1_, float p_70785_2_)
    {
    }

    /**
     * Called when the mob is falling. Calculates and applies fall damage.
     */
    protected void fall(float p_70069_1_) {}

    /**
     * Returns true if the entity is on fire. Used by render to add the fire effect on rendering.
     */
    public boolean isBurning()
    {
        return false;
    }

    public boolean func_70845_n()
    {
        return (this.dataWatcher.getWatchableObjectByte(16) & 1) != 0;
    }

    public void func_70844_e(boolean p_70844_1_)
    {
        byte b0 = this.dataWatcher.getWatchableObjectByte(16);

        if (p_70844_1_)
        {
            b0 = (byte)(b0 | 1);
        }
        else
        {
            b0 &= -2;
        }

        this.dataWatcher.updateObject(16, Byte.valueOf(b0));
    }

    /**
     * Checks to make sure the light is not too bright where the mob is spawning
     */
    protected boolean isValidLightLevel()
    {
        return true;
    }
    
    public boolean getCanSpawnHere()
    {
    	return this.dimension == 53 && ECUtils.isEventActive("ec3.event.fumes");
    }
}