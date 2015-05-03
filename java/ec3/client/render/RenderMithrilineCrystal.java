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

@SideOnly(Side.CLIENT)
public class RenderMithrilineCrystal extends TileEntitySpecialRenderer
{
    public static final ResourceLocation textures_mithriline = new ResourceLocation("essentialcraft:textures/blocks/invertedPlatingBlock.png");
    public static final ResourceLocation textures_pale = new ResourceLocation("essentialcraft:textures/blocks/ct/palePlating/tblr.png");
    public static final ResourceLocation textures_void = new ResourceLocation("essentialcraft:textures/blocks/ct/voidStone/tblr.png");
    public static final ResourceLocation textures_demonic = new ResourceLocation("essentialcraft:textures/blocks/ct/demonicPlating/tblr.png");
    public static final ResourceLocation textures_shade = new ResourceLocation("essentialcraft:textures/blocks/corruption/shade/7.png");
    public static final IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("essentialcraft:textures/special/models/MithrilineCrystal.obj"));

    public RenderMithrilineCrystal()
    {
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(TileEntity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
    {
    	int meta = p_76986_1_.getBlockMetadata();
    	RenderHelper.disableStandardItemLighting();
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1, 1, 1, 0.9F);
        float time = (Minecraft.getMinecraft().theWorld.getWorldTime()%45)*8 + p_76986_8_;
        float movement = Minecraft.getMinecraft().theWorld.getWorldTime()%60;
        
        if(movement > 30)movement = 30 - movement+30F;
        
        GL11.glTranslatef((float)p_76986_2_+0.5F, (float)p_76986_4_+movement/30, (float)p_76986_6_+0.5F);
        GL11.glRotatef(time, 0, 1, 0);
        GL11.glScalef(2, 2, 2);
        this.bindTexture(meta == 0 ? textures_mithriline : meta == 3 ? textures_pale : meta == 6 ? textures_void : meta == 9 ? textures_demonic : textures_shade);
        model.renderAll();
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
        
        RenderHelper.enableStandardItemLighting();
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(TileEntity p_110775_1_)
    {
        return textures_mithriline;
    }

	@Override
	public void renderTileEntityAt(TileEntity p_147500_1_, double p_147500_2_,
			double p_147500_4_, double p_147500_6_, float p_147500_8_) {
		if(p_147500_1_.getWorldObj().getBlockMetadata(p_147500_1_.xCoord, p_147500_1_.yCoord, p_147500_1_.zCoord)%3 == 0)
		this.doRender((TileEntity) p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_, 0);
	}
}