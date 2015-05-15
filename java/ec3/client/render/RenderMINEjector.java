package ec3.client.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

import ec3.common.tile.TileMINEjector;

@SideOnly(Side.CLIENT)
public class RenderMINEjector extends TileEntitySpecialRenderer
{
    public static final ResourceLocation glass = new ResourceLocation("essentialcraft:textures/special/models/minEjector_glass.png");
    public static final ResourceLocation overlay = new ResourceLocation("essentialcraft:textures/special/models/minEjector_overlay.png");
    public static final ResourceLocation rings = new ResourceLocation("essentialcraft:textures/special/models/minEjector_rings.png");
    public static final ResourceLocation overlayA = new ResourceLocation("essentialcraft:textures/special/models/minEjector_overlayA.png");
    public static final ResourceLocation ringsA = new ResourceLocation("essentialcraft:textures/special/models/minEjector_ringsA.png");
    public static final IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("essentialcraft:textures/special/models/MIN_Ejector.obj"));

    public RenderMINEjector()
    {
    	
    }


	@Override
	public void renderTileEntityAt(TileEntity tile, double x,
			double y, double z, float partialTicks) 
	{
		boolean advanced = tile.getWorldObj().getBlockMetadata(tile.xCoord, tile.yCoord, tile.zCoord) >= 6;
		TileMINEjector ejector = (TileMINEjector) tile;
		TextureManager render = Minecraft.getMinecraft().renderEngine;
		float scaleindex = 0.5F;
		if(ejector.getRotation() == ForgeDirection.DOWN)
		{
			GL11.glPushMatrix();
			RenderHelper.disableStandardItemLighting();
			GL11.glEnable(GL11.GL_BLEND);
	        GL11.glDisable(GL11.GL_LIGHTING);
	        GL11.glDisable(GL11.GL_CULL_FACE);
	        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			
			GL11.glTranslated(x+0.5F, y, z+0.5F);
			GL11.glScalef(scaleindex, scaleindex, scaleindex);
			render.bindTexture(glass);
			model.renderPart("pPipe1");
			if(!advanced)
				render.bindTexture(overlay);
			else
				render.bindTexture(overlayA);
			model.renderPart("pCube2");
			if(!advanced)
				render.bindTexture(rings);
			else
				render.bindTexture(ringsA);
			model.renderPart("pTorus1");
			model.renderPart("pTorus2");
			model.renderPart("pTorus3");
			model.renderPart("pTorus4");
			
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_BLEND);
			RenderHelper.enableStandardItemLighting();
			GL11.glPopMatrix();
		}
		if(ejector.getRotation() == ForgeDirection.UP)
		{
			GL11.glPushMatrix();
			RenderHelper.disableStandardItemLighting();
			GL11.glEnable(GL11.GL_BLEND);
	        GL11.glDisable(GL11.GL_LIGHTING);
	        GL11.glDisable(GL11.GL_CULL_FACE);
	        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			
			GL11.glTranslated(x+0.5F, y+1, z+0.5F);
			GL11.glRotatef(180, 1, 0, 0);
			GL11.glScalef(scaleindex, scaleindex, scaleindex);
			render.bindTexture(glass);
			model.renderPart("pPipe1");
			if(!advanced)
				render.bindTexture(overlay);
			else
				render.bindTexture(overlayA);
			model.renderPart("pCube2");
			if(!advanced)
				render.bindTexture(rings);
			else
				render.bindTexture(ringsA);
			model.renderPart("pTorus1");
			model.renderPart("pTorus2");
			model.renderPart("pTorus3");
			model.renderPart("pTorus4");
			
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_BLEND);
			RenderHelper.enableStandardItemLighting();
			GL11.glPopMatrix();
		}
		if(ejector.getRotation() == ForgeDirection.SOUTH)
		{
			GL11.glPushMatrix();
			RenderHelper.disableStandardItemLighting();
			GL11.glEnable(GL11.GL_BLEND);
	        GL11.glDisable(GL11.GL_LIGHTING);
	        GL11.glDisable(GL11.GL_CULL_FACE);
	        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			
			GL11.glTranslated(x+0.5F, y+0.5F, z+1);
			GL11.glRotatef(-90, 1, 0, 0);
			GL11.glScalef(scaleindex, scaleindex, scaleindex);
			render.bindTexture(glass);
			model.renderPart("pPipe1");
			if(!advanced)
				render.bindTexture(overlay);
			else
				render.bindTexture(overlayA);
			model.renderPart("pCube2");
			if(!advanced)
				render.bindTexture(rings);
			else
				render.bindTexture(ringsA);
			model.renderPart("pTorus1");
			model.renderPart("pTorus2");
			model.renderPart("pTorus3");
			model.renderPart("pTorus4");
			
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_BLEND);
			RenderHelper.enableStandardItemLighting();
			GL11.glPopMatrix();
		}
		if(ejector.getRotation() == ForgeDirection.NORTH)
		{
			GL11.glPushMatrix();
			RenderHelper.disableStandardItemLighting();
			GL11.glEnable(GL11.GL_BLEND);
	        GL11.glDisable(GL11.GL_LIGHTING);
	        GL11.glDisable(GL11.GL_CULL_FACE);
	        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			
			GL11.glTranslated(x+0.5F, y+0.5F, z);
			GL11.glRotatef(90, 1, 0, 0);
			GL11.glScalef(scaleindex, scaleindex, scaleindex);
			render.bindTexture(glass);
			model.renderPart("pPipe1");
			if(!advanced)
				render.bindTexture(overlay);
			else
				render.bindTexture(overlayA);
			model.renderPart("pCube2");
			if(!advanced)
				render.bindTexture(rings);
			else
				render.bindTexture(ringsA);
			model.renderPart("pTorus1");
			model.renderPart("pTorus2");
			model.renderPart("pTorus3");
			model.renderPart("pTorus4");
			
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_BLEND);
			RenderHelper.enableStandardItemLighting();
			GL11.glPopMatrix();
		}
		if(ejector.getRotation() == ForgeDirection.EAST)
		{
			GL11.glPushMatrix();
			RenderHelper.disableStandardItemLighting();
			GL11.glEnable(GL11.GL_BLEND);
	        GL11.glDisable(GL11.GL_LIGHTING);
	        GL11.glDisable(GL11.GL_CULL_FACE);
	        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			
			GL11.glTranslated(x+1, y+0.5F, z+0.5F);
			GL11.glRotatef(90, 0, 0, 1);
			GL11.glScalef(scaleindex, scaleindex, scaleindex);
			render.bindTexture(glass);
			model.renderPart("pPipe1");
			if(!advanced)
				render.bindTexture(overlay);
			else
				render.bindTexture(overlayA);
			model.renderPart("pCube2");
			if(!advanced)
				render.bindTexture(rings);
			else
				render.bindTexture(ringsA);
			model.renderPart("pTorus1");
			model.renderPart("pTorus2");
			model.renderPart("pTorus3");
			model.renderPart("pTorus4");
			
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_BLEND);
			RenderHelper.enableStandardItemLighting();
			GL11.glPopMatrix();
		}
		if(ejector.getRotation() == ForgeDirection.WEST)
		{
			GL11.glPushMatrix();
			RenderHelper.disableStandardItemLighting();
			GL11.glEnable(GL11.GL_BLEND);
	        GL11.glDisable(GL11.GL_LIGHTING);
	        GL11.glDisable(GL11.GL_CULL_FACE);
	        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			
			GL11.glTranslated(x, y+0.5F, z+0.5F);
			GL11.glRotatef(-90, 0, 0, 1);
			GL11.glScalef(scaleindex, scaleindex, scaleindex);
			render.bindTexture(glass);
			model.renderPart("pPipe1");
			if(!advanced)
				render.bindTexture(overlay);
			else
				render.bindTexture(overlayA);
			model.renderPart("pCube2");
			if(!advanced)
				render.bindTexture(rings);
			else
				render.bindTexture(ringsA);
			model.renderPart("pTorus1");
			model.renderPart("pTorus2");
			model.renderPart("pTorus3");
			model.renderPart("pTorus4");
			
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_BLEND);
			RenderHelper.enableStandardItemLighting();
			GL11.glPopMatrix();
		}
	}
}