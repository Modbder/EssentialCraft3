package ec3.client.render;

import org.lwjgl.opengl.GL11;

import ec3.common.entity.EntityOrbitalStrike;
import DummyCore.Utils.MiscUtils;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class RenderOrbitalStrike extends Render{

	@Override
	public void doRender(Entity e, double screenX,double screenY, double screenZ, float rotationYaw,float rotationPitch) {
		
		GL11.glPushMatrix();
		
		EntityOrbitalStrike eos = (EntityOrbitalStrike) e;
		
		//GL11.glScaled(eos.delay/3, 1, eos.delay/3);
		
		RenderHelper.disableStandardItemLighting();
		float f1 = 1.0F;
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);

        if (f1 > 0.0F)
        {
            Tessellator tessellator = Tessellator.instance;
            MiscUtils.bindTexture("minecraft", "textures/entity/beacon_beam.png");
            GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, 10497.0F);
            GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, 10497.0F);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_CULL_FACE);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glDepthMask(true);
            OpenGlHelper.glBlendFunc(770, 1, 1, 0);
            float f2 = (float)e.worldObj.getTotalWorldTime() + rotationYaw;
            float f3 = -f2 * 0.2F - (float)MathHelper.floor_float(-f2 * 0.1F);
            byte b0 = 1;
            double d3 = (double)f2 * 0.025D * (1.0D - (double)(b0 & 1) * 2.5D);
            tessellator.startDrawingQuads();
            tessellator.setColorRGBA(0, 255, 255, 11);
            double d5 = (double)b0 * 0.2D;
            double d7 = 0.5D + Math.cos(d3 + 2.356194490192345D) * d5;
            double d9 = 0.5D + Math.sin(d3 + 2.356194490192345D) * d5;
            double d11 = 0.5D + Math.cos(d3 + (Math.PI / 4D)) * d5;
            double d13 = 0.5D + Math.sin(d3 + (Math.PI / 4D)) * d5;
            double d15 = 0.5D + Math.cos(d3 + 3.9269908169872414D) * d5;
            double d17 = 0.5D + Math.sin(d3 + 3.9269908169872414D) * d5;
            double d19 = 0.5D + Math.cos(d3 + 5.497787143782138D) * d5;
            double d21 = 0.5D + Math.sin(d3 + 5.497787143782138D) * d5;
            double d23 = (double)(256.0F * f1);
            double d25 = 0.0D;
            double d27 = 1.0D;
            double d28 = (double)(-1.0F + f3);
            double d29 = (double)(256.0F * f1) * (0.5D / d5) + d28;
            tessellator.addVertexWithUV(screenX + d7, screenY + d23, screenZ + d9, d27, d29);
            tessellator.addVertexWithUV(screenX + d7, screenY, screenZ + d9, d27, d28);
            tessellator.addVertexWithUV(screenX + d11, screenY, screenZ + d13, d25, d28);
            tessellator.addVertexWithUV(screenX + d11, screenY + d23, screenZ + d13, d25, d29);
            tessellator.addVertexWithUV(screenX + d19, screenY + d23, screenZ + d21, d27, d29);
            tessellator.addVertexWithUV(screenX + d19, screenY, screenZ + d21, d27, d28);
            tessellator.addVertexWithUV(screenX + d15, screenY, screenZ + d17, d25, d28);
            tessellator.addVertexWithUV(screenX + d15, screenY + d23, screenZ + d17, d25, d29);
            tessellator.addVertexWithUV(screenX + d11, screenY + d23, screenZ + d13, d27, d29);
            tessellator.addVertexWithUV(screenX + d11, screenY, screenZ + d13, d27, d28);
            tessellator.addVertexWithUV(screenX + d19, screenY, screenZ + d21, d25, d28);
            tessellator.addVertexWithUV(screenX + d19, screenY + d23, screenZ + d21, d25, d29);
            tessellator.addVertexWithUV(screenX + d15, screenY + d23, screenZ + d17, d27, d29);
            tessellator.addVertexWithUV(screenX + d15, screenY, screenZ + d17, d27, d28);
            tessellator.addVertexWithUV(screenX + d7, screenY, screenZ + d9, d25, d28);
            tessellator.addVertexWithUV(screenX + d7, screenY + d23, screenZ + d9, d25, d29);
            tessellator.draw();
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glDepthMask(false);
            tessellator.startDrawingQuads();
            tessellator.setColorRGBA(100, 0, 255, 64);
            double d30 = 0D;
            double d4 = 0D;
            double d6 = eos.delay/3;
            double d8 = 0D;
            double d10 = 0D;
            double d12 = eos.delay/3;
            double d14 = eos.delay/3;
            double d16 = eos.delay/3;
            double d18 = (double)(256.0F * f1);
            double d20 = 0.0D;
            double d22 = 1.0D;
            double d24 = (double)(-1.0F + f3);
            double d26 = (double)(256.0F * f1 * f1);
            tessellator.addVertexWithUV(screenX + d30, screenY + d18, screenZ + d4, d22, d26);
            tessellator.addVertexWithUV(screenX + d30, screenY, screenZ + d4, d22, d24);
            tessellator.addVertexWithUV(screenX + d6, screenY, screenZ + d8, d20, d24);
            tessellator.addVertexWithUV(screenX + d6, screenY + d18, screenZ + d8, d20, d26);
            tessellator.addVertexWithUV(screenX + d14, screenY + d18, screenZ + d16, d22, d26);
            tessellator.addVertexWithUV(screenX + d14, screenY, screenZ + d16, d22, d24);
            tessellator.addVertexWithUV(screenX + d10, screenY, screenZ + d12, d20, d24);
            tessellator.addVertexWithUV(screenX + d10, screenY + d18, screenZ + d12, d20, d26);
            tessellator.addVertexWithUV(screenX + d6, screenY + d18, screenZ + d8, d22, d26);
            tessellator.addVertexWithUV(screenX + d6, screenY, screenZ + d8, d22, d24);
            tessellator.addVertexWithUV(screenX + d14, screenY, screenZ + d16, d20, d24);
            tessellator.addVertexWithUV(screenX + d14, screenY + d18, screenZ + d16, d20, d26);
            tessellator.addVertexWithUV(screenX + d10, screenY + d18, screenZ + d12, d22, d26);
            tessellator.addVertexWithUV(screenX + d10, screenY, screenZ + d12, d22, d24);
            tessellator.addVertexWithUV(screenX + d30, screenY, screenZ + d4, d20, d24);
            tessellator.addVertexWithUV(screenX + d30, screenY + d18, screenZ + d4, d20, d26);
            tessellator.draw();
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glDepthMask(true);
        }
        RenderHelper.enableStandardItemLighting();
        
        GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity e) {
		return null;
	}

}
