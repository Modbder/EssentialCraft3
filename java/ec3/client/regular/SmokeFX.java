package ec3.client.regular;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntitySmokeFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class SmokeFX extends EntitySmokeFX{
    private static final ResourceLocation particleTextures = new ResourceLocation("textures/particle/particles.png");
    private static final ResourceLocation ecparticleTextures = new ResourceLocation("essentialcraft","textures/special/particles.png");
    
	public SmokeFX(World w, double x, double y,	double z, double mX, double mY,	double mZ, float scale)
	{
		super(w, x, y, z, mX, mY,mZ, scale);
	}
	
	public SmokeFX(World w, double x, double y,	double z, double mX, double mY,	double mZ, float scale, double r, double g, double b)
	{
		super(w, x, y, z, mX, mY,mZ, scale);
		this.particleRed = (float) r;
		this.particleGreen = (float) g;
		this.particleBlue = (float) b;
	}
	
	public void renderParticle(Tessellator var3, float par2, float par3, float par4, float par5, float par6, float par7)
    {
		var3.draw();
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		boolean enabled = GL11.glIsEnabled(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_BLEND);
		Minecraft.getMinecraft().renderEngine.bindTexture(ecparticleTextures);
			var3.startDrawingQuads();
    	super.renderParticle(var3, par2, par3, par4, par5, par6, par7);
    		var3.draw();
		Minecraft.getMinecraft().renderEngine.bindTexture(particleTextures);
		if(!enabled)
			GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glPopMatrix();
		var3.startDrawingQuads();
    }

}
