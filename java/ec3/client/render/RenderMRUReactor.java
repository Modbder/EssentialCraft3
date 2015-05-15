package ec3.client.render;

import DummyCore.Utils.Lightning;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

import ec3.common.tile.TileMRUReactor;
import ec3.common.tile.TileRayTower;

@SideOnly(Side.CLIENT)
public class RenderMRUReactor extends TileEntitySpecialRenderer
{
	
    public static final ResourceLocation textures = new ResourceLocation("essentialcraft:textures/special/models/flowerBurner.png");
    public static final IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("essentialcraft:textures/special/models/mruReactor_btm.obj"));
    
    public static final ResourceLocation stextures = new ResourceLocation("essentialcraft:textures/special/models/sphere.png");
    public static final IModelCustom smodel = AdvancedModelLoader.loadModel(new ResourceLocation("essentialcraft:textures/special/models/sphere.obj"));
    

    public RenderMRUReactor()
    {
    	
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(TileMRUReactor p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
    {
    	RenderHelper.disableStandardItemLighting();
        GL11.glPushMatrix();
        GL11.glTranslatef((float)p_76986_2_+0.5F, (float)p_76986_4_, (float)p_76986_6_+0.5F);
        this.bindTexture(textures);
        model.renderAll();
        
        GL11.glPopMatrix();
        
        GL11.glPushMatrix();
        GL11.glTranslatef((float)p_76986_2_+0.5F, (float)p_76986_4_, (float)p_76986_6_+0.5F);
        this.bindTexture(stextures);
        if(!p_76986_1_.isStructureCorrect)
        {
        	GL11.glColor3f(0.4F, 0.4F, 0.4F);
        	GL11.glScalef(0.55F, 0.55F, 0.55F);
        	smodel.renderAll();
        }else
        {
        	float wTime = 0F;
        	GL11.glTranslatef(0, 0.5F+wTime, 0);
        	GL11.glScalef(0.55F, 0.55F, 0.55F);
        	smodel.renderAll();
        }
        
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        if(p_76986_1_.isStructureCorrect())
    	for(int i = 0; i < p_76986_1_.lightnings.size(); ++i)
    	{
    		Lightning l = p_76986_1_.lightnings.get(i);
    		l.render(p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_);
    	}
    	GL11.glPopMatrix();
        
        RenderHelper.enableStandardItemLighting();
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(TileRayTower p_110775_1_)
    {
        return textures;
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(TileEntity p_110775_1_)
    {
        return this.getEntityTexture((TileMRUReactor)p_110775_1_);
    }

	@Override
	public void renderTileEntityAt(TileEntity p_147500_1_, double p_147500_2_,
			double p_147500_4_, double p_147500_6_, float p_147500_8_) {
		this.doRender((TileMRUReactor) p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_, 0);
	}
}