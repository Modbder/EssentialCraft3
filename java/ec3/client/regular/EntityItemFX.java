package ec3.client.regular;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import ec3.common.entity.EntityMRUPresence;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;

public class EntityItemFX extends EntityFX{
	
	public double red,green,blue;

	public EntityItemFX(World w, double x, double y,double z, double r, double g, double b, double mX, double mY, double mZ)
	{
		super(w, x, y, z, 0, 0, 0);
		red = r;
		green = g;
		blue = b;
		this.motionX = mX/20;
		this.motionY = mY/20;
		this.motionZ = mZ/20;
		this.particleMaxAge = 25;
	}
	
    public void renderParticle(Tessellator var3, float x, float y, float z, float u1, float u2, float u3)
    {
    	this.noClip = true;
    	prevPosX = posX;
    	prevPosY = posY;
    	prevPosZ = posZ;
        float f11 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)x - interpPosX);
        float f12 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)x - interpPosY);
        float f13 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)x - interpPosZ);
    	var3.addVertex(0.0D, 0.0D, 0.0D);
    	var3.addVertex(0.0D, 0.0D, 0.0D);
    	var3.addVertex(0.0D, 0.0D, 0.0D);
    	var3.addVertex(0.0D, 0.0D, 0.0D);
    	var3.draw();
    	Random var6 = new Random((long) (this.posX*100+this.posY*100+this.posZ*100));
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glDepthMask(false);
        GL11.glPushMatrix();
        GL11.glTranslated(f11, f12, f13);
        int mru = 200;
        GL11.glScalef(0.0000075F*mru, 0.0000075F*mru, 0.0000075F*mru);
        for (int var7 = 0; var7 < 100; ++var7)
        {
            GL11.glRotatef(var6.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(var6.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(var6.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(var6.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(var6.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(var6.nextFloat() * 360.0F + 1 * 90.0F, 0.0F, 0.0F, 1.0F);
            var3.startDrawing(6);
            float var8 = var6.nextFloat() * 20.0F + 5.0F + 1 * 10.0F;
            float var9 = var6.nextFloat() * 2.0F + 1.0F + 1 * 2.0F;
            var3.setBrightness(Integer.MAX_VALUE);
            var3.setColorOpaque_F((float)red, (float)green, (float)blue);
            var3.addVertex(0.0D, 0.0D, 0.0D);
            var3.setColorOpaque_F((float)red, (float)green, (float)blue);
            var3.addVertex(-0.866D * (double)var9, (double)var8, (double)(-0.5F * var9));
            var3.addVertex(0.866D * (double)var9, (double)var8, (double)(-0.5F * var9));
            var3.addVertex(0.0D, (double)var8, (double)(1.0F * var9));
            var3.addVertex(-0.866D * (double)var9, (double)var8, (double)(-0.5F * var9));
            var3.draw();
        }

        GL11.glPopMatrix();
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        var3.startDrawingQuads();
    }
	
	

}
