package ec3.client.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

import ec3.common.tile.TileDemonicPentacle;

@SideOnly(Side.CLIENT)
public class RenderDemonicPentacle extends TileEntitySpecialRenderer
{
    public static final ResourceLocation rune = new ResourceLocation("essentialcraft:textures/special/models/demonicPentacle.png");
    public static final IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("essentialcraft:textures/special/models/DarknessObelisk.obj"));
    
    public RenderDemonicPentacle()
    {
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(TileEntity tile, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
    {
    	RenderHelper.disableStandardItemLighting();
    	TileDemonicPentacle p = (TileDemonicPentacle) tile;
    	
        GL11.glPushMatrix();
	    	Minecraft.getMinecraft().renderEngine.bindTexture(rune);
	    	GL11.glTranslated(p_76986_2_+0.5F, p_76986_4_-0.2F, p_76986_6_+0.5F);

	    	if(p.tier == -1)
	    	{
	    		GL11.glPopMatrix();
	    		return;
	    	}
	    	
	        float movement = Minecraft.getMinecraft().theWorld.getWorldTime()%60+p_76986_8_;
	        
	        if(movement > 30)movement = 30 - movement+30F;
	        
	        float c = movement/30F;
	        if(c < 0.02F)c = 0.02F;
	        if(c > 0.8F)c= 0.8F;
	        
	    	if(p.tier == 0)
	    	{
	    		GL11.glColor3f(c, 0, 0);
	    	}
	    	
	    	//GL11.glRotatef(45, 0, 1, 0);
	    	
	    	model.renderPart("pPlane1");
    	GL11.glPopMatrix();
    }

	@Override
	public void renderTileEntityAt(TileEntity p_147500_1_, double p_147500_2_,
			double p_147500_4_, double p_147500_6_, float p_147500_8_) {
		if(p_147500_1_.getWorldObj().getBlockMetadata(p_147500_1_.xCoord, p_147500_1_.yCoord, p_147500_1_.zCoord) == 0)
		this.doRender((TileEntity) p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_, 0);
	}
}