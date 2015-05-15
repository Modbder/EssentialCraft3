package ec3.client.regular;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityColoredFlameFX extends EntityFX{
	
	private float flameScale;

    private static final ResourceLocation particleTextures = new ResourceLocation("textures/particle/particles.png");
    private static final ResourceLocation ecparticleTextures = new ResourceLocation("essentialcraft","textures/special/particles.png");
    
	public EntityColoredFlameFX(World w, double x,double y, double z, double mX,double mY, double mZ) 
	{
		super(w, x, y, z, mX, mY,mZ);
        this.motionX = this.motionX * 0.009999999776482582D + mX;
        this.motionY = this.motionY * 0.009999999776482582D + mY;
        this.motionZ = this.motionZ * 0.009999999776482582D + mZ;
        this.flameScale = this.particleScale;
        this.particleRed = this.particleGreen = this.particleBlue = 1.0F;
        this.particleMaxAge = (int)(8.0D / (Math.random() * 0.8D + 0.2D)) + 4;
        this.noClip = true;
        this.setParticleTextureIndex(48);
	}
	
	public EntityColoredFlameFX(World w, double x,double y, double z, double mX,double mY, double mZ, double r, double g, double b, double scale) 
	{
		super(w, x, y, z, mX, mY,mZ);
        this.motionX = this.motionX * 0.009999999776482582D + mX;
        this.motionY = this.motionY * 0.009999999776482582D + mY;
        this.motionZ = this.motionZ * 0.009999999776482582D + mZ;
        this.flameScale = (float) scale;
        this.particleRed = (float) r;
        this.particleGreen = (float) g;
        this.particleBlue = (float) b;
        this.particleMaxAge = (int)(8.0D / (Math.random() * 0.8D + 0.2D)) + 4;
        this.noClip = true;
        this.setParticleTextureIndex(48);
	}
	
    public void renderParticle(Tessellator var3, float p_70539_2_, float p_70539_3_, float p_70539_4_, float p_70539_5_, float p_70539_6_, float p_70539_7_)
    {
		var3.draw();
		Minecraft.getMinecraft().renderEngine.bindTexture(ecparticleTextures);
		
        float f6 = ((float)this.particleAge + p_70539_2_) / (float)this.particleMaxAge;
        this.particleScale = this.flameScale * (1.0F - f6 * f6 * 0.5F);
        var3.startDrawingQuads();
        	super.renderParticle(var3, p_70539_2_, p_70539_3_, p_70539_4_, p_70539_5_, p_70539_6_, p_70539_7_);
    	var3.draw();
        
		Minecraft.getMinecraft().renderEngine.bindTexture(particleTextures);
		var3.startDrawingQuads();
    }
    
    public int getBrightnessForRender(float p_70070_1_)
    {
        float f1 = ((float)this.particleAge + p_70070_1_) / (float)this.particleMaxAge;

        if (f1 < 0.0F)
        {
            f1 = 0.0F;
        }

        if (f1 > 1.0F)
        {
            f1 = 1.0F;
        }

        int i = super.getBrightnessForRender(p_70070_1_);
        int j = i & 255;
        int k = i >> 16 & 255;
        j += (int)(f1 * 15.0F * 16.0F);

        if (j > 240)
        {
            j = 240;
        }

        return j | k << 16;
    }

    /**
     * Gets how bright this entity is.
     */
    public float getBrightness(float p_70013_1_)
    {
        float f1 = ((float)this.particleAge + p_70013_1_) / (float)this.particleMaxAge;

        if (f1 < 0.0F)
        {
            f1 = 0.0F;
        }

        if (f1 > 1.0F)
        {
            f1 = 1.0F;
        }

        float f2 = super.getBrightness(p_70013_1_);
        return f2 * f1 + (1.0F - f1);
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (this.particleAge++ >= this.particleMaxAge)
        {
            this.setDead();
        }

        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9599999785423279D;
        this.motionY *= 0.9599999785423279D;
        this.motionZ *= 0.9599999785423279D;

        if (this.onGround)
        {
            this.motionX *= 0.699999988079071D;
            this.motionZ *= 0.699999988079071D;
        }
    }

}
