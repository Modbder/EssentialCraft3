package ec3.client.regular;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import DummyCore.Utils.MathUtils;
import DummyCore.Utils.MiscUtils;
import ec3.api.IItemAllowsSeeingMRUCU;
import ec3.common.entity.EntityMRUPresence;
import ec3.common.mod.EssentialCraftCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class EntityCSpellFX extends EntityFX{

	private float scale;
    private double mruPosX;
    private double mruPosY;
    private double mruPosZ;
	public EntityCSpellFX(World w, double x, double y,double z, double i, double j,double k) 
	{
		super(w, x, y, z, i, j,k);
        this.motionX = i;
        this.motionY = j;
        this.motionZ = k;
        this.mruPosX = this.posX = x;
        this.mruPosY = this.posY = y;
        this.mruPosZ = this.posZ = z;
        float f = this.rand.nextFloat() * 0.6F + 0.4F;
        this.scale = this.particleScale = 1F;
        this.particleRed = 0;
        this.particleGreen = 0F;
        this.particleBlue = 0.0F;
        this.particleMaxAge = (int)(Math.random() * 10.0D) + 40;
        this.noClip = false;
        this.setParticleTextureIndex((int)(Math.random() * 8.0D));
	}

	public void renderParticle(Tessellator var3, float par2, float par3, float par4, float par5, float par6, float par7)
    {
	    this.scale = this.particleScale = 1F;
        this.particleRed = 0;
        this.particleGreen = 0F;
        this.particleBlue = 0.0F;
    	super.renderParticle(var3, par2, par3, par4, par5, par6, par7);
        this.particleRed = 1;
        this.particleGreen = 1F;
        this.particleBlue = 1F;
        this.scale = this.particleScale = 0.4F;
    	super.renderParticle(var3, par2, par3, par4, par5, par6, par7);
    }

    public int getBrightnessForRender(float p_70070_1_)
    {
        int i = super.getBrightnessForRender(p_70070_1_);
        float f1 = (float)this.particleAge / (float)this.particleMaxAge;
        f1 *= f1;
        f1 *= f1;
        int j = i & 255;
        int k = i >> 16 & 255;
        k += (int)(f1 * 15.0F * 16.0F);

        if (k > 240)
        {
            k = 240;
        }

        return j | k << 16;
    }

    /**
     * Gets how bright this entity is.
     */
    public float getBrightness(float p_70013_1_)
    {
        float f1 = super.getBrightness(p_70013_1_);
        float f2 = (float)this.particleAge / (float)this.particleMaxAge;
        f2 = f2 * f2 * f2 * f2;
        return f1 * (1.0F - f2) + f2;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
    	this.setParticleTextureIndex(7 - this.particleAge * 8 / this.particleMaxAge);
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        float f = (float)this.particleAge / (float)this.particleMaxAge;
        this.posX = this.mruPosX + this.motionX * (double)f;
        this.posY = this.mruPosY + this.motionY * (double)f;
        this.posZ = this.mruPosZ + this.motionZ * (double)f;

        if (this.particleAge++ >= this.particleMaxAge)
        {
            this.setDead();
            for(int t = 0; t < 10; ++t)
            {
            	//this.worldObj.spawnParticle("smoke", posX, posY, posZ, MathUtils.randomFloat(rand)/6, MathUtils.randomFloat(rand)/6, MathUtils.randomFloat(rand)/6);
            	//if(this.worldObj.rand.nextFloat() < 0.01F)
            	//this.worldObj.spawnParticle("explode", posX, posY, posZ, MathUtils.randomFloat(rand)/6, MathUtils.randomFloat(rand)/6, MathUtils.randomFloat(rand)/6);
            }
        }
       // if(this.worldObj.rand.nextFloat() < 0.01F)
        	//this.worldObj.spawnParticle("reddust", posX, posY, posZ, -1, 0, 0);
    }
}
