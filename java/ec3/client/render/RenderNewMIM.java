package ec3.client.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

import ec3.common.tile.TileNewMIM;

@SideOnly(Side.CLIENT)
public class RenderNewMIM extends TileEntitySpecialRenderer
{
    public static final ResourceLocation textures = new ResourceLocation("essentialcraft:textures/blocks/mimCube.png");
    public static final ResourceLocation vtextures = new ResourceLocation("essentialcraft:textures/blocks/voidStone.png");
    public static final IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("essentialcraft:textures/special/models/MIM.obj"));

    public RenderNewMIM()
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
    	RenderHelper.disableStandardItemLighting();
        GL11.glPushMatrix();
        GL11.glTranslatef((float)p_76986_2_+0.5F, (float)p_76986_4_, (float)p_76986_6_+0.5F);
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        this.bindTexture(vtextures);
        model.renderPart("Cube.001_Cube.002");
        model.renderPart("Cube_Cube.001");
        
        TileNewMIM t = TileNewMIM.class.cast(p_76986_1_);
        
        GL11.glRotatef(t.innerRotation, 0, 1, 0);
        
        this.bindTexture(textures);
        model.renderPart("Cube.002_Cube.003");
        
        GL11.glPopMatrix();
        RenderHelper.enableStandardItemLighting();
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(TileEntity p_110775_1_)
    {
        return textures;
    }

	@Override
	public void renderTileEntityAt(TileEntity p_147500_1_, double p_147500_2_,
			double p_147500_4_, double p_147500_6_, float p_147500_8_) {
		this.doRender((TileEntity) p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_, 0);
	}
}