package ec3.client.render;

import DummyCore.Utils.MiscUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

import ec3.common.tile.TileMagicalAssembler;
import ec3.utils.common.PlayerTickHandler;

@SideOnly(Side.CLIENT)
public class RenderMagicalAssembler extends TileEntitySpecialRenderer
{
	public static final ResourceLocation textures = new ResourceLocation("essentialcraft:textures/special/models/assembler/itemPedestal.png");
	public static final ResourceLocation texturesMRU = new ResourceLocation("essentialcraft:textures/special/models/assembler/armTextures.png");
    public static final IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation("essentialcraft:textures/special/models/assembler/Assembler.obj"));

    public RenderMagicalAssembler()
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
    	TileMagicalAssembler tile = (TileMagicalAssembler) p_76986_1_;
    	RenderHelper.disableStandardItemLighting();
    	
        GL11.glPushMatrix();
        GL11.glTranslatef((float)p_76986_2_+0.5F, (float)p_76986_4_, (float)p_76986_6_+0.5F);
        this.bindTexture(textures);
        model.renderPart("pCube1");
        this.bindTexture(texturesMRU);
        model.renderPart("pCube2");
        model.renderPart("pCube3");
        
        float posIndexX = Minecraft.getMinecraft().theWorld.getWorldTime()%120/100F;
        float posIndexZ = Minecraft.getMinecraft().theWorld.getWorldTime()%70/(70F/1.2F);
        
        float indexX = 0.0F;
        
        if(posIndexX < 0.6F)indexX = posIndexX - 0.3F;
        else indexX = 0.6F-posIndexX + 0.3F;
        	
        
        float indexZ = 0.0F;
        
        if(posIndexZ < 0.6F)indexZ = posIndexZ - 0.3F;
        else indexZ = 0.6F-posIndexZ + 0.3F;
        
        if(!tile.isWorking)
        {
        	indexX = 0;
        	indexZ = 0;
        }
        
        float offsetX = indexX;
        float offsetZ = indexZ;
        
        GL11.glPushMatrix();
        GL11.glTranslatef(indexX, 0, 0);
        model.renderPart("pCylinder1");
        model.renderPart("pCylinder2");
        model.renderPart("pCylinder5");
        GL11.glPopMatrix();
        
        
        GL11.glPushMatrix();
        GL11.glTranslatef(0, 0, indexZ);
        model.renderPart("pCylinder3");
        model.renderPart("pCylinder4");
        model.renderPart("pCylinder6");
        GL11.glPopMatrix();
        
        GL11.glPushMatrix();
        GL11.glTranslatef(indexX, 0, indexZ);
        model.renderPart("pCube4");
        GL11.glPopMatrix();
        
        
      

        GL11.glPopMatrix();
        RenderHelper.enableStandardItemLighting();
        if(tile.isWorking)
        	renderBeam(p_76986_8_, p_76986_2_, p_76986_4_, p_76986_6_, 0.5F+indexX, 0.8F, 0.5F+indexZ, -indexX+offsetX, -0.7F, -indexZ+offsetZ, 0, 1, 1, 0, 1, 1, 0.03F);
        
        
        
        if(tile.currentCraft != null)
        {
        	MiscUtils.renderItemStack_Full(tile.currentCraft, tile.xCoord, tile.yCoord, tile.zCoord, p_76986_2_, p_76986_4_, p_76986_6_, 90, 90, 1, 1, 1, 0.5F, 0.2F, 0.5F);
        }
        
    }

	public static void renderBeam(float partialTicks,double x, double y, double z, double posX, double posY, double posZ, double offsetX, double offsetY, double offsetZ, float colorR, float colorG, float colorB, float colorRB, float colorGB, float colorBB, float size)
	{
		GL11.glPushMatrix(); 
		float f21 = (float)0 + partialTicks;
	        float f31 = MathHelper.sin(f21 * 0.2F) / 2.0F + 0.5F;
	        f31 = (f31 * f31 + f31) * 0.2F;
	        float f4;
	        float f5;
	        float f6;
			f4 = (float) offsetX;
		    f5 = (float) offsetY;
			f6 = (float) offsetZ;
	        GL11.glTranslated(x+posX, y + posY, z+posZ);
	        float f7 = MathHelper.sqrt_float(f4 * f4 + f6 * f6);
	        float f8 = MathHelper.sqrt_float(f4 * f4 + f5 * f5 + f6 * f6);
	        GL11.glRotatef((float)(-Math.atan2((double)f6, (double)f4)) * 180.0F / (float)Math.PI - 90.0F, 0.0F, 1.0F, 0.0F);
	        GL11.glRotatef((float)(-Math.atan2((double)f7, (double)f5)) * 180.0F / (float)Math.PI - 90.0F, 1.0F, 0.0F, 0.0F);
	        Tessellator tessellator = Tessellator.instance;
	        RenderHelper.disableStandardItemLighting();
	        MiscUtils.bindTexture("essentialcraft","textures/special/whitebox.png");
	        GL11.glShadeModel(GL11.GL_SMOOTH);
	        GL11.glEnable(GL11.GL_BLEND);
	        float f9 = 1;
	        float f10 = MathHelper.sqrt_float(f4 * f4 + f5 * f5 + f6 * f6) / 32.0F - (PlayerTickHandler.tickAmount + partialTicks) * 0.1F;
	        tessellator.startDrawing(5);
	        byte b0 = 8;
	
	        for (int i1 = 0; i1 <= b0; ++i1)
	        {
	            float f11 = MathHelper.sin((float)(i1 % b0) * (float)Math.PI * 2.0F / (float)b0) * 0.75F * size;
	            float f12 = MathHelper.cos((float)(i1 % b0) * (float)Math.PI * 2.0F / (float)b0) * 0.75F * size;
	            float f13 = (float)(i1 % b0) * 1.0F / (float)b0;
	            tessellator.setColorRGBA_F(colorRB, colorGB, colorBB, 1F);
	            tessellator.addVertexWithUV((double)(f11), (double)(f12), 0.0D, (double)f13, (double)f10);
	            tessellator.setColorRGBA_F(colorR, colorG, colorB, 0F);
	            tessellator.addVertexWithUV((double)f11, (double)f12, (double)f8, (double)f13, (double)f9);
	        }
	
	        tessellator.draw();
	        GL11.glDisable(GL11.GL_BLEND);
	        GL11.glShadeModel(GL11.GL_FLAT);
	        RenderHelper.enableStandardItemLighting();
		GL11.glPopMatrix();
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
		if(p_147500_1_.getWorldObj().getBlockMetadata(p_147500_1_.xCoord, p_147500_1_.yCoord, p_147500_1_.zCoord) == 0)
		this.doRender((TileEntity) p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_, 0);
	}
}