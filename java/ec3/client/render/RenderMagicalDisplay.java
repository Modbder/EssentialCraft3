package ec3.client.render;

import java.util.ArrayList;
import java.util.List;

import DummyCore.Utils.MiscUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;

import org.lwjgl.opengl.GL11;

import ec3.common.tile.TileMagicalDisplay;

@SideOnly(Side.CLIENT)
public class RenderMagicalDisplay extends TileEntitySpecialRenderer
{

    public RenderMagicalDisplay()
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
        
        TileMagicalDisplay display = (TileMagicalDisplay) p_76986_1_;
        int metadata = display.getWorldObj().getBlockMetadata(display.xCoord, display.yCoord, display.zCoord);
        ItemStack displayed = display.getStackInSlot(0);
        float rotationX = metadata == 3 ? 0 : metadata == 2 ? 180 : metadata == 4 ? 90 : 270;
        float offsetX = metadata == 1 || metadata == 0 ? 0.7F : metadata == 3 || metadata == 2 ? 0.5F : metadata == 4 ? 0.96F : 0.04F;
        float offsetY = metadata == 1 ? 0.04F : metadata == 0 ? 0.96F : 0.4F;
        float offsetZ = metadata == 1 || metadata == 0 ? 0.5F : metadata == 4 || metadata == 5 ? 0.5F : metadata == 2 ? 0.96F : 0.04F;
        float rotationZ = metadata == 1 || metadata == 0 ? 90 : 0;
        float scaleIndex = display.type == 0 ? 1.5F : display.type == 1 ? 1F : 0.75F;
        
        if(displayed != null)
        {
        	
        	if(!(displayed.getItem() instanceof ItemBlock))
        	{
        		GL11.glScalef(scaleIndex, scaleIndex, scaleIndex);
            	if(display.type == 0 || metadata == 0 || metadata == 1)
    		    {
    	        	MiscUtils.renderItemStack_Full(displayed, display.xCoord, display.yCoord, display.zCoord, p_76986_2_/scaleIndex, p_76986_4_/scaleIndex, p_76986_6_/scaleIndex, rotationX, rotationZ, 1, 1, 1, offsetX/scaleIndex, offsetY/scaleIndex, offsetZ/scaleIndex);
    		    }
            	if(display.type == 1 && metadata != 0 && metadata != 1)
    		    {
            	    offsetY = metadata == 1 ? 0.04F : metadata == 0 ? 0.96F : 0.6F;
    	        	MiscUtils.renderItemStack_Full(displayed, display.xCoord, display.yCoord, display.zCoord, p_76986_2_/scaleIndex, p_76986_4_/scaleIndex, p_76986_6_/scaleIndex, rotationX, rotationZ, 1, 1, 1, offsetX/scaleIndex, offsetY/scaleIndex, offsetZ/scaleIndex);
    	        	FontRenderer renderer = Minecraft.getMinecraft().fontRenderer;
    	        	GL11.glTranslated(p_76986_2_, p_76986_4_, p_76986_6_);
    	        	String drawedName = displayed.getDisplayName();
    	        	GL11.glRotatef(180, 1, 0, 0);
    	        	//GL11.glRotatef(180, 0, 1, 0);
    	        	switch(metadata)
    	        	{
	    	        	case 2:
	    	        	{
	    	        		GL11.glTranslatef(1-((float)drawedName.length()/160F), -0.4F, -0.949F);
	    	        		break;
	    	        	}
	    	        	case 3:
	    	        	{
	    	        		GL11.glTranslatef(((float)drawedName.length()/160F), -0.4F, -0.051F);
	    	        		break;
	    	        	}
	    	        	case 4:
	    	        	{
	    	        		GL11.glTranslatef(0.949F, -0.4F, -((float)drawedName.length()/160F));
	    	        		break;
	    	        	}
	    	        	case 5:
	    	        	{
	    	        		GL11.glTranslatef(0.051F, -0.4F, -1+((float)drawedName.length()/160F));
	    	        		break;
	    	        	}
    	        	}
    	        	
    	        	GL11.glRotatef(rotationX, 0, 1, 0);
    	        	
    	        	float s = 0.06F / (drawedName.length()/2);
    	        	
    	        	GL11.glScalef(s, s, s);
    	        	renderer.drawString(drawedName, 0, 0, 0xffffff);
    		    }
            	if(display.type == 2 && metadata != 0 && metadata != 1)
    		    {
                    offsetX = metadata == 3 ? 0.25F : metadata == 2 ? 0.75F : metadata == 4 ? 0.94F : 0.05F;
                    offsetY = 0.7F;
                    offsetZ = metadata == 4 ? 0.25F : metadata == 5 ? 0.75F : metadata == 2 ? 0.95F : 0.04F;
    	        	MiscUtils.renderItemStack_Full(displayed, display.xCoord, display.yCoord, display.zCoord, p_76986_2_/scaleIndex, p_76986_4_/scaleIndex, p_76986_6_/scaleIndex, rotationX, rotationZ, 1, 1, 1, offsetX/scaleIndex, offsetY/scaleIndex, offsetZ/scaleIndex);
    	        	FontRenderer renderer = Minecraft.getMinecraft().fontRenderer;
    	        	GL11.glTranslated(p_76986_2_, p_76986_4_, p_76986_6_);
    	        	String drawedName = displayed.getDisplayName();
    	        	GL11.glRotatef(180, 1, 0, 0);
    	        	//GL11.glRotatef(180, 0, 1, 0);
    	        	switch(metadata)
    	        	{
	    	        	case 2:
	    	        	{
	    	        		GL11.glTranslatef(-0.4F+1-((float)drawedName.length()/160F), -0.8F, -0.949F);
	    	        		break;
	    	        	}
	    	        	case 3:
	    	        	{
	    	        		GL11.glTranslatef(0.4F+((float)drawedName.length()/160F), -0.8F, -0.051F);
	    	        		break;
	    	        	}
	    	        	case 4:
	    	        	{
	    	        		GL11.glTranslatef(0.949F, -0.8F, -0.4F-((float)drawedName.length()/160F));
	    	        		break;
	    	        	}
	    	        	case 5:
	    	        	{
	    	        		GL11.glTranslatef(0.051F, -0.8F, 0.4F-1+((float)drawedName.length()/160F));
	    	        		break;
	    	        	}
    	        	}
    	        	
    	        	GL11.glRotatef(rotationX, 0, 1, 0);
    	        	
    	        	float s = 0.03F / (drawedName.length()/2);
    	        	GL11.glPushMatrix();
    	        	GL11.glScalef(s, s, s);
    	        	renderer.drawString(drawedName, 0, 0, 0xffffff);
    	        	GL11.glPopMatrix();
    	        	List<String> displaySt = new ArrayList<String>();
    	        	displayed.getItem().addInformation(displayed, Minecraft.getMinecraft().thePlayer, displaySt, false);
    	        	
    	        	int longestStr = 1;
    	        	
    	        	for(int i = 0; i < displaySt.size(); ++i)
    	        	{
    	        		String st = displaySt.get(i);
    	        		if(longestStr < st.length())
    	        			longestStr = st.length();
    	        	}
    	        	GL11.glPushMatrix();
    	        	GL11.glTranslatef(-0.4F, 0.15F, 0);
    	        
    	        	s = 0.08F / (longestStr/2F);
    	        	GL11.glScalef(s, s, s);
    	        	
    	        	for(int i = 0; i < displaySt.size(); ++i)
    	        	{
    	        		GL11.glTranslatef(0, 10, 0);
    	        		renderer.drawString(displaySt.get(i), 0, 0, 0xffffff);
    	        	}
    	        	
    	        	GL11.glPopMatrix();
    		    }
        	}
        	else
        	{
        		Block displayedBlock = ((ItemBlock)displayed.getItem()).field_150939_a;
        		IIcon icon = displayedBlock.getIcon(metadata, displayed.getItemDamage());
        		Tessellator tec = Tessellator.instance;
        		tec.startDrawingQuads();
        		
            	MiscUtils.bindTexture("minecraft", "textures/atlas/blocks.png");
                double minU = icon.getMinU();
                double maxU = icon.getMaxU();
                double minV = icon.getMinV();
                double maxV = icon.getMaxV();
                
                GL11.glTranslated(p_76986_2_, p_76986_4_, p_76986_6_);
                int color = displayedBlock.getRenderColor(displayed.getItemDamage());
                
                switch(metadata)
                {
	                case 0:
	                {
	                	GL11.glTranslatef(0, 0.949F, 0);
	                	GL11.glRotatef(90, 1, 0, 0);
	                	break;
	                }
	                case 1:
	                {
	                	GL11.glTranslatef(0, 0.051F, 1);
	                	GL11.glRotatef(-90, 1, 0, 0);
	                	break;
	                }
	                case 2:
	                {
	                	GL11.glTranslatef(1, 0, 0.949F);
	                	GL11.glRotatef(180, 0, 1, 0);
	                	break;
	                }
	                case 3:
	                {
	                	GL11.glTranslatef(0, 0, 0.051F);
	                	GL11.glRotatef(0, 0, 1, 0);
	                	break;
	                }
	                case 4:
	                {
	                	GL11.glTranslatef(0.949F, 0, 0);
	                	GL11.glRotatef(270, 0, 1, 0);
	                	break;
	                }
	                case 5:
	                {
	                	GL11.glTranslatef(0.051F, 0, 1);
	                	GL11.glRotatef(90, 0, 1, 0);
	                	break;
	                }
                }
                tec.setColorOpaque_I(color);
                
                tec.addVertexWithUV(0, 0, 0, minU, minV);
                tec.addVertexWithUV(1, 0, 0, maxU, minV);
                tec.addVertexWithUV(1, 1, 0, maxU, maxV);
                tec.addVertexWithUV(0, 1, 0, minU, maxV);
        		
        		tec.draw();
        	}  

        }
	        
        
        GL11.glPopMatrix();
        RenderHelper.enableStandardItemLighting();
    }

	@Override
	public void renderTileEntityAt(TileEntity p_147500_1_, double p_147500_2_,
			double p_147500_4_, double p_147500_6_, float p_147500_8_) {
		this.doRender((TileEntity) p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_, 0);
	}
}