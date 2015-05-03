package ec3.client.render;

import DummyCore.Utils.MathUtils;
import DummyCore.Utils.MiscUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelEnderCrystal;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

import ec3.api.ITEHasMRU;
import ec3.client.model.ModelFloatingCube;
import ec3.client.model.ModelSolarPrism;
import ec3.common.item.ItemBoundGem;
import ec3.common.tile.TileCorruptionCleaner;
import ec3.common.tile.TileMagicalQuarry;
import ec3.common.tile.TileRayTower;
import ec3.utils.common.ECUtils;
import ec3.utils.common.PlayerTickHandler;

@SideOnly(Side.CLIENT)
public class RenderCorruptionCleaner extends TileEntitySpecialRenderer
{
    public static final ResourceLocation textures = new ResourceLocation("essentialcraft:textures/special/models/magicianTable.png");

    public RenderCorruptionCleaner()
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
        if(p_76986_1_ instanceof TileCorruptionCleaner)
        {
        	TileCorruptionCleaner tile = (TileCorruptionCleaner) p_76986_1_;
        	if(tile.cleared == null)
        	{}else
        	{
        		GL11.glPushMatrix();        		
        		float[] o = new float[]{tile.cleared.x,tile.cleared.y+1.45F,tile.cleared.z};
		        GL11.glPopMatrix();
		        ECUtils.renderMRUBeam(p_76986_1_, 0, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
		        float f21 = (float)0 + p_76986_9_;
		        float f31 = MathHelper.sin(f21 * 0.2F) / 2.0F + 0.5F;
		        f31 = (f31 * f31 + f31) * 0.2F;
		        float f4;
		        float f5;
		        float f6;
		        GL11.glPushMatrix();
				f4 = (float)(o[0] - p_76986_1_.xCoord);
			    f5 = (float)(o[1] - (double)(f31 + p_76986_1_.yCoord+1.3F));
				f6 = (float)(o[2] - p_76986_1_.zCoord);
		        GL11.glTranslatef((float)p_76986_2_+0.5F, (float)p_76986_4_ + 0.5F, (float)p_76986_6_+0.5F);
		        float f7 = MathHelper.sqrt_float(f4 * f4 + f6 * f6);
		        float f8 = MathHelper.sqrt_float(f4 * f4 + f5 * f5 + f6 * f6);
		        GL11.glRotatef((float)(-Math.atan2((double)f6, (double)f4)) * 180.0F / (float)Math.PI - 90.0F, 0.0F, 1.0F, 0.0F);
		        GL11.glRotatef((float)(-Math.atan2((double)f7, (double)f5)) * 180.0F / (float)Math.PI - 90.0F, 1.0F, 0.0F, 0.0F);
		        Tessellator tessellator = Tessellator.instance;
		        RenderHelper.disableStandardItemLighting();
		        GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
		        GL11.glDisable(GL11.GL_CULL_FACE);
		        MiscUtils.bindTexture("essentialcraft","textures/special/mru_beam.png");
		        GL11.glShadeModel(GL11.GL_SMOOTH);
		        GL11.glEnable(GL11.GL_BLEND);
		        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		        GL11.glDisable(GL11.GL_ALPHA_TEST);
		        float f9 = 1;
		        float f10 = MathHelper.sqrt_float(f4 * f4 + f5 * f5 + f6 * f6) / 32.0F - (PlayerTickHandler.tickAmount + p_76986_9_) * 0.1F;
		        tessellator.startDrawing(5);
		        byte b0 = 8;
		
		        for (int i = 0; i <= b0; ++i)
		        {
		            float f11 = MathHelper.sin((float)(i % b0) * (float)Math.PI * 2.0F / (float)b0) * 0.75F * 0.1F;
		            float f12 = MathHelper.cos((float)(i % b0) * (float)Math.PI * 2.0F / (float)b0) * 0.75F * 0.1F;
		            float f13 = (float)(i % b0) * 1.0F / (float)b0;
		            tessellator.setColorRGBA_F(0.0F, 1.0F, 1.0F, 10F);
		            tessellator.addVertexWithUV((double)(f11), (double)(f12), 0.0D, (double)f13, (double)f10);
		            tessellator.setColorRGBA_F(0.0F, 1.0F, 1.0F, 10F);
		            tessellator.addVertexWithUV((double)f11, (double)f12, (double)f8, (double)f13, (double)f9);
		        }
		
		        tessellator.draw();
		        GL11.glEnable(GL11.GL_CULL_FACE);
		        GL11.glDisable(GL11.GL_BLEND);
		        GL11.glShadeModel(GL11.GL_FLAT);
		        GL11.glEnable(GL11.GL_ALPHA_TEST);
		        RenderHelper.enableStandardItemLighting();
		        GL11.glPopMatrix();
	        }
        }
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