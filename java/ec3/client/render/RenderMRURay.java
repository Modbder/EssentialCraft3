package ec3.client.render;

import org.lwjgl.opengl.GL11;

import DummyCore.Utils.MiscUtils;
import ec3.common.entity.EntityMRURay;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class RenderMRURay extends Render{

	  private static final ResourceLocation field_147523_b = new ResourceLocation("textures/entity/beacon_beam.png");
	  
	@Override
	public void doRender(Entity p_76986_1_, double p_147500_2_,
			double p_147500_4_, double p_147500_6_, float p_147500_8_,
			float p_76986_9_) {
		EntityMRURay ray = (EntityMRURay) p_76986_1_;
		
		if(ray.pX == 0 && ray.pY == 0 && ray.pZ == 0)
			return;
		
		float r = 0;
		float g = 1;
		float b = 1;
		if(ray.balance == 1)
		{
			r = 1;
			g = 0;
			b = 0;
		}
		if(ray.balance == 2)
		{
			r = 0;
			g = 0;
			b = 1;
		}
		if(ray.balance == 3)
		{
			r = 1;
			g = 0;
			b = 1;
		}
		if(ray.balance == 4)
		{
			r = 0.3F;
			g = 0.3F;
			b = 0.3F;
		}
		renderBeam(p_147500_8_,p_147500_2_,p_147500_4_,p_147500_6_,1D-(double)ray.ticksExisted/60D,0,0,ray.pX-ray.posX,ray.pY-ray.posY,ray.pZ-ray.posZ,r,g,b,r,g,b,(float) (0.1F * (1 + (double)ray.ticksExisted/60)));
		
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		// TODO Auto-generated method stub
		return field_147523_b;
	}
	
	public static void renderBeam(float partialTicks,double x, double y, double z, double posX, double posY, double posZ, double offsetX, double offsetY, double offsetZ, float colorR, float colorG, float colorB, float colorRB, float colorGB, float colorBB, float size)
	{
		GL11.glPushMatrix(); 
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		float f21 = (float)0 + partialTicks;
	        float f31 = MathHelper.sin(f21 * 0.2F) / 2.0F + 0.5F;
	        f31 = (f31 * f31 + f31) * 0.2F;
	        float f4;
	        float f5;
	        float f6;
			f4 = (float) offsetX;
		    f5 = (float) offsetY;
			f6 = (float) offsetZ;
	        GL11.glTranslated(x, y + posY, z+posZ);
	        float f7 = MathHelper.sqrt_float(f4 * f4 + f6 * f6);
	        float f8 = MathHelper.sqrt_float(f4 * f4 + f5 * f5 + f6 * f6);
	        GL11.glRotatef((float)(-Math.atan2((double)f6, (double)f4)) * 180.0F / (float)Math.PI - 90.0F, 0.0F, 1.0F, 0.0F);
	        GL11.glRotatef((float)(-Math.atan2((double)f7, (double)f5)) * 180.0F / (float)Math.PI - 90.0F, 1.0F, 0.0F, 0.0F);
	        Tessellator tessellator = Tessellator.instance;
	        RenderHelper.disableStandardItemLighting();
	        MiscUtils.bindTexture("essentialcraft","textures/special/mru_beam.png");
	        GL11.glShadeModel(GL11.GL_SMOOTH);
	        float f9 = 1;
	        float f10 = MathHelper.sqrt_float(f4 * f4 + f5 * f5 + f6 * f6) / 32.0F - 1 * 0.0001F;
	        tessellator.startDrawing(5);
	        byte b0 = 8;
	        for (int i1 = 0; i1 <= b0; ++i1)
	        {
	            float f11 = MathHelper.sin((float)(i1 % b0) * (float)Math.PI * 2.0F / (float)b0) * 0.75F * size;
	            float f12 = MathHelper.cos((float)(i1 % b0) * (float)Math.PI * 2.0F / (float)b0) * 0.75F * size;
	            float f13 = (float)(i1 % b0) * 1.0F / (float)b0;
	            tessellator.setColorRGBA_F(colorRB, colorGB, colorBB, (float) posX);
	            tessellator.addVertexWithUV((double)(f11), (double)(f12), 0.0D, (double)f13, (double)f10);
	            tessellator.setColorRGBA_F(colorR, colorG, colorB, (float) posX);
	            tessellator.addVertexWithUV((double)f11, (double)f12, (double)f8, (double)f13, (double)f9);
	        }
	
	        tessellator.draw();
	        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	        GL11.glDisable(GL11.GL_BLEND);
	        GL11.glShadeModel(GL11.GL_FLAT);
	        RenderHelper.enableStandardItemLighting();
	    GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}

}
