package ec3.client.render;

import DummyCore.Utils.Coord3D;
import DummyCore.Utils.Lightning;
import DummyCore.Utils.MathUtils;
import DummyCore.Utils.MiscUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
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
import org.lwjgl.util.Color;

import ec3.api.ITEHasMRU;
import ec3.client.model.ModelFloatingCube;
import ec3.client.model.ModelSolarPrism;
import ec3.common.item.ItemBoundGem;
import ec3.common.tile.TileMRUCoil_Hardener;
import ec3.common.tile.TileRayTower;
import ec3.utils.common.ECUtils;

@SideOnly(Side.CLIENT)
public class RenderDarknessObelisk extends TileEntitySpecialRenderer
{
    public static final ResourceLocation obelisk = new ResourceLocation("essentialcraft:textures/special/models/darknessObelisk.png");
    public static final ResourceLocation rune = new ResourceLocation("essentialcraft:textures/special/models/darknessRune.png");
    public static final IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("essentialcraft:textures/special/models/DarknessObelisk.obj"));
    
    public RenderDarknessObelisk()
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
    	
        GL11.glPushMatrix();
        	ECUtils.renderMRUBeam(tile, 0, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
        GL11.glPopMatrix();
        
        GL11.glPushMatrix();
        	Minecraft.getMinecraft().renderEngine.bindTexture(obelisk);
        	GL11.glTranslated(p_76986_2_+0.5F, p_76986_4_, p_76986_6_+0.5F);
        	model.renderPart("pCube1");
        GL11.glPopMatrix();
        
        GL11.glPushMatrix();
        	float lightLevel = tile.getWorldObj().getBlockLightValue(tile.xCoord, tile.yCoord, tile.zCoord);
        	GL11.glEnable(GL11.GL_BLEND);
        	GL11.glColor4f(1, 1, 1, 1-lightLevel/15F);
	    	Minecraft.getMinecraft().renderEngine.bindTexture(rune);
	    	GL11.glTranslated(p_76986_2_+0.5F, p_76986_4_, p_76986_6_+0.5F);
	    	
	    	float upperRotationIndex = tile.getWorldObj().getWorldTime()%100;
	    	if(upperRotationIndex > 50)upperRotationIndex = 50-upperRotationIndex+50;
	    	
	    	GL11.glTranslatef(0, upperRotationIndex/200-0.1F, 0);
	    	GL11.glRotatef(tile.getWorldObj().getWorldTime()%360, 0, 1, 0);
	    	model.renderPart("pPlane1");
	    	GL11.glDisable(GL11.GL_BLEND);
    	GL11.glPopMatrix();
    }

	@Override
	public void renderTileEntityAt(TileEntity p_147500_1_, double p_147500_2_,
			double p_147500_4_, double p_147500_6_, float p_147500_8_) {
		if(p_147500_1_.getWorldObj().getBlockMetadata(p_147500_1_.xCoord, p_147500_1_.yCoord, p_147500_1_.zCoord) == 0)
		this.doRender((TileEntity) p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_, 0);
	}
}