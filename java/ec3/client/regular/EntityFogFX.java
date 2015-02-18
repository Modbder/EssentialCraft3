package ec3.client.regular;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import DummyCore.Utils.MathUtils;
import DummyCore.Utils.MiscUtils;
import ec3.api.IItemAllowsSeeingMRUCU;
import ec3.common.entity.EntityMRUPresence;
import ec3.common.mod.EssentialCraftCore;
import ec3.utils.common.ECUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityFogFX extends EntityFX{

	private float scale;
    private double mruPosX;
    private double mruPosY;
    private double mruPosZ;
    public double red, green, blue;
    public ResourceLocation rec = new ResourceLocation("essentialcraft","textures/items/particles/fog.png");
    private static final ResourceLocation particleTextures = new ResourceLocation("textures/particle/particles.png");
	public EntityFogFX(World w, double x, double y,double z, double i, double j,double k) 
	{
		super(w, x, y, z, i, j,k);
		if(w != null && w.rand != null && this != null) //wat? this!=null? what code is this sh*t? o_O
		{
			this.motionX = MathUtils.randomDouble(w.rand);
			this.motionY = MathUtils.randomDouble(w.rand);
			this.motionZ = MathUtils.randomDouble(w.rand);
	        this.red = i;
	        this.green = j;
	        this.blue = k;
	        this.mruPosX = this.posX = x;
	        this.mruPosY = this.posY = y;
	        this.mruPosZ = this.posZ = z;
	        float f = this.rand.nextFloat() * 0.6F + 0.4F;
	        this.scale = this.particleScale = 1F;
	        this.particleRed = (float) red;
	        this.particleGreen = (float) green;
	        this.particleBlue = (float) blue;
	        this.particleMaxAge = (int)(Math.random() * 10.0D) + 100;
	        this.noClip = true;
	        this.setParticleTextureIndex((int)(Math.random() * 8.0D));
		}
	}


    public void renderParticle(Tessellator p_70539_1_, float p_70539_2_, float p_70539_3_, float p_70539_4_, float p_70539_5_, float p_70539_6_, float p_70539_7_)
    {
    	p_70539_1_.draw();
    	Minecraft.getMinecraft().renderEngine.bindTexture(rec);
        float f6 = (float)2 / 16.0F;
        float f7 = f6 + 0.0624375F;
        float f8 = (float)2 / 16.0F;
        float f9 = f8 + 0.0624375F;
        float f10 = 1F * this.particleScale;

        if (this.particleIcon != null)
        {
            f6 = this.particleIcon.getMinU();
            f7 = this.particleIcon.getMaxU();
            f8 = this.particleIcon.getMinV();
            f9 = this.particleIcon.getMaxV();
        }

        GL11.glEnable(GL11.GL_BLEND);
        float f11 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)p_70539_2_ - interpPosX);
        float f12 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)p_70539_2_ - interpPosY);
        float f13 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)p_70539_2_ - interpPosZ);
        p_70539_1_.startDrawing(6);
        p_70539_1_.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);
        p_70539_1_.addVertexWithUV((double)(f11 - p_70539_3_ * f10 - p_70539_6_ * f10), (double)(f12 - p_70539_4_ * f10), (double)(f13 - p_70539_5_ * f10 - p_70539_7_ * f10), (double)f7, (double)f9);
        p_70539_1_.addVertexWithUV((double)(f11 - p_70539_3_ * f10 + p_70539_6_ * f10), (double)(f12 + p_70539_4_ * f10), (double)(f13 - p_70539_5_ * f10 + p_70539_7_ * f10), (double)f7, (double)f8);
        p_70539_1_.addVertexWithUV((double)(f11 + p_70539_3_ * f10 + p_70539_6_ * f10), (double)(f12 + p_70539_4_ * f10), (double)(f13 + p_70539_5_ * f10 + p_70539_7_ * f10), (double)f6, (double)f8);
        p_70539_1_.addVertexWithUV((double)(f11 + p_70539_3_ * f10 - p_70539_6_ * f10), (double)(f12 - p_70539_4_ * f10), (double)(f13 + p_70539_5_ * f10 - p_70539_7_ * f10), (double)f6, (double)f9);
        p_70539_1_.draw();
        Minecraft.getMinecraft().renderEngine.bindTexture(particleTextures);
        p_70539_1_.startDrawingQuads();
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
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        float f = (float)this.particleAge / (float)this.particleMaxAge;
        this.posX = this.mruPosX + this.motionX * (double)f;
        this.posY = this.mruPosY + this.motionY * (double)f;
        this.posZ = this.mruPosZ + this.motionZ * (double)f;
        this.particleScale *= 1.01F;
        if (this.particleAge++ >= this.particleMaxAge)
        {
            this.setDead();
        }
    }
}
