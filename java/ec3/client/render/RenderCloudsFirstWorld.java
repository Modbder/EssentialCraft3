package ec3.client.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.IRenderHandler;

public class RenderCloudsFirstWorld extends IRenderHandler{
	
	private static final ResourceLocation locationCloudsPng = new ResourceLocation("textures/environment/clouds.png");
	 private int cloudTickCounter;

	@Override
	public void render(float partialTicks, WorldClient world, Minecraft mc) {
		if(world.rand.nextInt(10)==0)
		++cloudTickCounter;
		for(int layer = 0; layer < 3; ++layer)
		{
			GL11.glPushMatrix();
			// TODO Auto-generated method stub
	        GL11.glDisable(GL11.GL_CULL_FACE);
	        float f1 = (float)(mc.renderViewEntity.lastTickPosY + (mc.renderViewEntity.posY - mc.renderViewEntity.lastTickPosY) * (double)partialTicks);
	        Tessellator tessellator = Tessellator.instance;
	        float f2 = 12.0F;
	        float f3 = 4.0F;
	        double d0 = (double)((float)this.cloudTickCounter + partialTicks);
	        double d1 = (mc.renderViewEntity.prevPosX + (mc.renderViewEntity.posX - mc.renderViewEntity.prevPosX) * (double)partialTicks + d0* layer*layer * 0.029999999329447746D) / (double)f2 ;
	        double d2 = (mc.renderViewEntity.prevPosZ + (mc.renderViewEntity.posZ - mc.renderViewEntity.prevPosZ) * (double)partialTicks) / (double)f2* layer*layer + 0.33000001311302185D ;
	        float f4 = world.provider.getCloudHeight() - f1 + 0.33F + layer*36;
	        int i = MathHelper.floor_double(d1 / 2048.0D);
	        int j = MathHelper.floor_double(d2 / 2048.0D);
	        d1 -= (double)(i * 2048);
	        d2 -= (double)(j * 2048);
	        mc.renderEngine.bindTexture(locationCloudsPng);
	        GL11.glEnable(GL11.GL_BLEND);
	        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
	        Vec3 vec3 = Vec3.createVectorHelper((1F*(layer+1)/3F), (1F*(layer+1)/3F), (1F*(layer+1)/3F));
	        if(world.isRaining())
	        	vec3 = Vec3.createVectorHelper(0.3D, 0.3D, 0.3D);
	        if(world.isRaining() && world.isThundering())
	        	vec3 = Vec3.createVectorHelper(0.1D, 0.1D, 0.1D);
	        float f5 = (float)vec3.xCoord;
	        float f6 = (float)vec3.yCoord;
	        float f7 = (float)vec3.zCoord;
	        float f8;
	        float f9;
	        float f10;
	
	        if (mc.gameSettings.anaglyph)
	        {
	            f8 = (f5 * 30.0F + f6 * 59.0F + f7 * 11.0F) / 100.0F;
	            f9 = (f5 * 30.0F + f6 * 70.0F) / 100.0F;
	            f10 = (f5 * 30.0F + f7 * 70.0F) / 100.0F;
	            f5 = f8;
	            f6 = f9;
	            f7 = f10;
	        }
	
	        f8 = (float)(d1 * 0.0D);
	        f9 = (float)(d2 * 0.0D);
	        f10 = 0.00390625F;
	        f8 = (float)MathHelper.floor_double(d1) * f10;
	        f9 = (float)MathHelper.floor_double(d2) * f10;
	        float f11 = (float)(d1 - (double)MathHelper.floor_double(d1));
	        float f12 = (float)(d2 - (double)MathHelper.floor_double(d2));
	        byte b0 = 8;
	        byte b1 = 4;
	        float f13 = 9.765625E-4F;
	        GL11.glScalef(f2, 1.0F, f2);
	
	        for (int k = 0; k < 2; ++k)
	        {
	            if (k == 0)
	            {
	                GL11.glColorMask(false, false, false, false);
	            }
	            else if (mc.gameSettings.anaglyph)
	            {
	                if (EntityRenderer.anaglyphField == 0)
	                {
	                    GL11.glColorMask(false, true, true, true);
	                }
	                else
	                {
	                    GL11.glColorMask(true, false, false, true);
	                }
	            }
	            else
	            {
	                GL11.glColorMask(true, true, true, true);
	            }
	
	            for (int l = -b1 + 1; l <= b1; ++l)
	            {
	                for (int i1 = -b1 + 1; i1 <= b1; ++i1)
	                {
	                    tessellator.startDrawingQuads();
	                    float f14 = (float)(l * b0);
	                    float f15 = (float)(i1 * b0);
	                    float f16 = f14 - f11;
	                    float f17 = f15 - f12;
	
	                    if (f4 > -f3 - 1.0F)
	                    {
	                        tessellator.setColorRGBA_F(f5 * 0.7F, f6 * 0.7F, f7 * 0.7F, 0.8F);
	                        tessellator.setNormal(0.0F, -1.0F, 0.0F);
	                        tessellator.addVertexWithUV((double)(f16 + 0.0F), (double)(f4 + 0.0F), (double)(f17 + (float)b0), (double)((f14 + 0.0F) * f10 + f8), (double)((f15 + (float)b0) * f10 + f9));
	                        tessellator.addVertexWithUV((double)(f16 + (float)b0), (double)(f4 + 0.0F), (double)(f17 + (float)b0), (double)((f14 + (float)b0) * f10 + f8), (double)((f15 + (float)b0) * f10 + f9));
	                        tessellator.addVertexWithUV((double)(f16 + (float)b0), (double)(f4 + 0.0F), (double)(f17 + 0.0F), (double)((f14 + (float)b0) * f10 + f8), (double)((f15 + 0.0F) * f10 + f9));
	                        tessellator.addVertexWithUV((double)(f16 + 0.0F), (double)(f4 + 0.0F), (double)(f17 + 0.0F), (double)((f14 + 0.0F) * f10 + f8), (double)((f15 + 0.0F) * f10 + f9));
	                    }
	
	                    if (f4 <= f3 + 1.0F)
	                    {
	                        tessellator.setColorRGBA_F(f5, f6, f7, 0.8F);
	                        tessellator.setNormal(0.0F, 1.0F, 0.0F);
	                        tessellator.addVertexWithUV((double)(f16 + 0.0F), (double)(f4 + f3 - f13), (double)(f17 + (float)b0), (double)((f14 + 0.0F) * f10 + f8), (double)((f15 + (float)b0) * f10 + f9));
	                        tessellator.addVertexWithUV((double)(f16 + (float)b0), (double)(f4 + f3 - f13), (double)(f17 + (float)b0), (double)((f14 + (float)b0) * f10 + f8), (double)((f15 + (float)b0) * f10 + f9));
	                        tessellator.addVertexWithUV((double)(f16 + (float)b0), (double)(f4 + f3 - f13), (double)(f17 + 0.0F), (double)((f14 + (float)b0) * f10 + f8), (double)((f15 + 0.0F) * f10 + f9));
	                        tessellator.addVertexWithUV((double)(f16 + 0.0F), (double)(f4 + f3 - f13), (double)(f17 + 0.0F), (double)((f14 + 0.0F) * f10 + f8), (double)((f15 + 0.0F) * f10 + f9));
	                    }
	
	                    tessellator.setColorRGBA_F(f5 * 0.9F, f6 * 0.9F, f7 * 0.9F, 0.8F);
	                    int j1;
	
	                    if (l > -1)
	                    {
	                        tessellator.setNormal(-1.0F, 0.0F, 0.0F);
	
	                        for (j1 = 0; j1 < b0; ++j1)
	                        {
	                            tessellator.addVertexWithUV((double)(f16 + (float)j1 + 0.0F), (double)(f4 + 0.0F), (double)(f17 + (float)b0), (double)((f14 + (float)j1 + 0.5F) * f10 + f8), (double)((f15 + (float)b0) * f10 + f9));
	                            tessellator.addVertexWithUV((double)(f16 + (float)j1 + 0.0F), (double)(f4 + f3), (double)(f17 + (float)b0), (double)((f14 + (float)j1 + 0.5F) * f10 + f8), (double)((f15 + (float)b0) * f10 + f9));
	                            tessellator.addVertexWithUV((double)(f16 + (float)j1 + 0.0F), (double)(f4 + f3), (double)(f17 + 0.0F), (double)((f14 + (float)j1 + 0.5F) * f10 + f8), (double)((f15 + 0.0F) * f10 + f9));
	                            tessellator.addVertexWithUV((double)(f16 + (float)j1 + 0.0F), (double)(f4 + 0.0F), (double)(f17 + 0.0F), (double)((f14 + (float)j1 + 0.5F) * f10 + f8), (double)((f15 + 0.0F) * f10 + f9));
	                        }
	                    }
	
	                    if (l <= 1)
	                    {
	                        tessellator.setNormal(1.0F, 0.0F, 0.0F);
	
	                        for (j1 = 0; j1 < b0; ++j1)
	                        {
	                            tessellator.addVertexWithUV((double)(f16 + (float)j1 + 1.0F - f13), (double)(f4 + 0.0F), (double)(f17 + (float)b0), (double)((f14 + (float)j1 + 0.5F) * f10 + f8), (double)((f15 + (float)b0) * f10 + f9));
	                            tessellator.addVertexWithUV((double)(f16 + (float)j1 + 1.0F - f13), (double)(f4 + f3), (double)(f17 + (float)b0), (double)((f14 + (float)j1 + 0.5F) * f10 + f8), (double)((f15 + (float)b0) * f10 + f9));
	                            tessellator.addVertexWithUV((double)(f16 + (float)j1 + 1.0F - f13), (double)(f4 + f3), (double)(f17 + 0.0F), (double)((f14 + (float)j1 + 0.5F) * f10 + f8), (double)((f15 + 0.0F) * f10 + f9));
	                            tessellator.addVertexWithUV((double)(f16 + (float)j1 + 1.0F - f13), (double)(f4 + 0.0F), (double)(f17 + 0.0F), (double)((f14 + (float)j1 + 0.5F) * f10 + f8), (double)((f15 + 0.0F) * f10 + f9));
	                        }
	                    }
	
	                    tessellator.setColorRGBA_F(f5 * 0.8F, f6 * 0.8F, f7 * 0.8F, 0.8F);
	
	                    if (i1 > -1)
	                    {
	                        tessellator.setNormal(0.0F, 0.0F, -1.0F);
	
	                        for (j1 = 0; j1 < b0; ++j1)
	                        {
	                            tessellator.addVertexWithUV((double)(f16 + 0.0F), (double)(f4 + f3), (double)(f17 + (float)j1 + 0.0F), (double)((f14 + 0.0F) * f10 + f8), (double)((f15 + (float)j1 + 0.5F) * f10 + f9));
	                            tessellator.addVertexWithUV((double)(f16 + (float)b0), (double)(f4 + f3), (double)(f17 + (float)j1 + 0.0F), (double)((f14 + (float)b0) * f10 + f8), (double)((f15 + (float)j1 + 0.5F) * f10 + f9));
	                            tessellator.addVertexWithUV((double)(f16 + (float)b0), (double)(f4 + 0.0F), (double)(f17 + (float)j1 + 0.0F), (double)((f14 + (float)b0) * f10 + f8), (double)((f15 + (float)j1 + 0.5F) * f10 + f9));
	                            tessellator.addVertexWithUV((double)(f16 + 0.0F), (double)(f4 + 0.0F), (double)(f17 + (float)j1 + 0.0F), (double)((f14 + 0.0F) * f10 + f8), (double)((f15 + (float)j1 + 0.5F) * f10 + f9));
	                        }
	                    }
	
	                    if (i1 <= 1)
	                    {
	                        tessellator.setNormal(0.0F, 0.0F, 1.0F);
	
	                        for (j1 = 0; j1 < b0; ++j1)
	                        {
	                            tessellator.addVertexWithUV((double)(f16 + 0.0F), (double)(f4 + f3), (double)(f17 + (float)j1 + 1.0F - f13), (double)((f14 + 0.0F) * f10 + f8), (double)((f15 + (float)j1 + 0.5F) * f10 + f9));
	                            tessellator.addVertexWithUV((double)(f16 + (float)b0), (double)(f4 + f3), (double)(f17 + (float)j1 + 1.0F - f13), (double)((f14 + (float)b0) * f10 + f8), (double)((f15 + (float)j1 + 0.5F) * f10 + f9));
	                            tessellator.addVertexWithUV((double)(f16 + (float)b0), (double)(f4 + 0.0F), (double)(f17 + (float)j1 + 1.0F - f13), (double)((f14 + (float)b0) * f10 + f8), (double)((f15 + (float)j1 + 0.5F) * f10 + f9));
	                            tessellator.addVertexWithUV((double)(f16 + 0.0F), (double)(f4 + 0.0F), (double)(f17 + (float)j1 + 1.0F - f13), (double)((f14 + 0.0F) * f10 + f8), (double)((f15 + (float)j1 + 0.5F) * f10 + f9));
	                        }
	                    }
	
	                    tessellator.draw();
	                }
	            }
	        }
	
	        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	        GL11.glDisable(GL11.GL_BLEND);
	        GL11.glEnable(GL11.GL_CULL_FACE);
	        GL11.glPopMatrix();
		}
	}

}
