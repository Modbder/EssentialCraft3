package ec3.client.regular;

import org.lwjgl.opengl.GL11;

import ec3.utils.common.ECUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntitySmokeFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class SmokeFX extends EntitySmokeFX{
    private static final ResourceLocation particleTextures = new ResourceLocation("textures/particle/particles.png");
    private static final ResourceLocation ecparticleTextures = new ResourceLocation("essentialcraft","textures/special/particles.png");
    
	public SmokeFX(World p_i1226_1_, double p_i1226_2_, double p_i1226_4_,
			double p_i1226_6_, double p_i1226_8_, double p_i1226_10_,
			double p_i1226_12_, float p_i1226_14_) {
		super(p_i1226_1_, p_i1226_2_, p_i1226_4_, p_i1226_6_, p_i1226_8_, p_i1226_10_,
				p_i1226_12_, p_i1226_14_);
		// TODO Auto-generated constructor stub
	}
	
	public void renderParticle(Tessellator var3, float par2, float par3, float par4, float par5, float par6, float par7)
    {
		var3.draw();
		Minecraft.getMinecraft().renderEngine.bindTexture(ecparticleTextures);
			var3.startDrawingQuads();
    	super.renderParticle(var3, par2, par3, par4, par5, par6, par7);
    		var3.draw();
		Minecraft.getMinecraft().renderEngine.bindTexture(particleTextures);
		var3.startDrawingQuads();
    }

}
