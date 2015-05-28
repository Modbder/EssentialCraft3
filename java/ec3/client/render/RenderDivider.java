package ec3.client.render;

import org.lwjgl.opengl.GL11;

import DummyCore.Utils.MiscUtils;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class RenderDivider extends Render {
	
	public static final IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("essentialcraft","textures/special/models/divideSphere.obj"));

	@Override
	public void doRender(Entity e, double x,
			double y, double z, float partial,
			float zero) {
		
		GL11.glPushMatrix();
		
		GL11.glTranslated(x, y, z);
		GL11.glScalef(3, 3, 3);
		
		RenderHelper.disableStandardItemLighting();
		
    	GL11.glDisable(GL11.GL_ALPHA_TEST);
    	GL11.glEnable(GL11.GL_BLEND);
    	GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA);
    	
    	MiscUtils.bindTexture("minecraft", "textures/entity/beacon_beam.png");
    	
    	GL11.glColor4f(1, 0, 1,0.2F);
		
		model.renderAll();
		
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		
		RenderHelper.enableStandardItemLighting();
		
		GL11.glPopMatrix();
		
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity e) {
		return null;
	}

}
