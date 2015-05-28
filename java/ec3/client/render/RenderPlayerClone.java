package ec3.client.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderPlayerClone extends RenderBiped
{
    private static final ResourceLocation textures = new ResourceLocation("textures/entity/steve.png");
    /** The model of the enderman */
    private ModelBiped model;
    public RenderPlayerClone()
    {
        super(new ModelBiped(1,0,64,32), 0.5F);
        this.model = (ModelBiped)super.mainModel;
        this.setRenderPassModel(this.model);
    }

    protected void preRenderCallback(EntityLivingBase p_77041_1_, float p_77041_2_) 
    {
    	float s = 1.0F;
    	GL11.glScalef(s, s, s);
    	
    	GL11.glDisable(GL11.GL_ALPHA_TEST);
    	GL11.glEnable(GL11.GL_BLEND);
    	GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA);
    	
    	GL11.glColor4f(1, 1, 1, 0.2F);
    	
    }

    protected ResourceLocation getEntityTexture(Entity p_110775_1_)
    {
        return textures;
    }

    public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
    {
        super.doRender(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
}