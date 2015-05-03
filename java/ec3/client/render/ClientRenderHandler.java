package ec3.client.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import ec3.common.item.ItemGun;

public class ClientRenderHandler {
	
	ResourceLocation loc = new ResourceLocation("essentialcraft","textures/hud/sniper_scope.png");

	
	@SubscribeEvent
	public void onClientRenderTick(RenderGameOverlayEvent.Pre event)
	{
		if(event.type != ElementType.ALL)
		{
	    	EntityPlayer p = Minecraft.getMinecraft().thePlayer;
	    	if(p.getCurrentEquippedItem() != null && p.getCurrentEquippedItem().getItem() instanceof ItemGun && p.isSneaking() && p.getCurrentEquippedItem().getTagCompound() != null && p.getCurrentEquippedItem().getTagCompound().hasKey("scope") && ItemGun.class.cast(p.getCurrentEquippedItem().getItem()).gunType.equalsIgnoreCase("sniper"))
	    	{
	    		if(event.type == ElementType.CROSSHAIRS)
	    		{
		    		Minecraft mc = Minecraft.getMinecraft();
				    ScaledResolution scaledresolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
				    int k = scaledresolution.getScaledWidth();
				    int l = scaledresolution.getScaledHeight();
				    if(k < l) k = l;
				    if(k > l) k = l;
				    
				    Minecraft.getMinecraft().getTextureManager().bindTexture(loc);
				    GL11.glDepthMask(false);
				    
			        Tessellator tessellator = Tessellator.instance;
			        tessellator.startDrawingQuads();
			        tessellator.addVertexWithUV(scaledresolution.getScaledWidth()/2-scaledresolution.getScaledWidth()/4, (double)l, -90.0D, 0.0D, 1.0D);
			        tessellator.addVertexWithUV((double)k+scaledresolution.getScaledWidth()/2-scaledresolution.getScaledWidth()/4, (double)l, -90.0D, 1.0D, 1.0D);
			        tessellator.addVertexWithUV((double)k+scaledresolution.getScaledWidth()/2-scaledresolution.getScaledWidth()/4, 0.0D, -90.0D, 1.0D, 0.0D);
			        tessellator.addVertexWithUV(scaledresolution.getScaledWidth()/2-scaledresolution.getScaledWidth()/4, 0.0D, -90.0D, 0.0D, 0.0D);
			        tessellator.draw();
			        Minecraft.getMinecraft().getTextureManager().bindTexture(RenderHandlerEC3.whitebox);
			        
			        GL11.glColor3f(0, 0, 0);
			        
			        tessellator.startDrawingQuads();
			        tessellator.addVertexWithUV(0, (double)l, -90.0D, 0.0D, 1.0D);
			        tessellator.addVertexWithUV(scaledresolution.getScaledWidth()/2-scaledresolution.getScaledWidth()/4, (double)l, -90.0D, 1.0D, 1.0D);
			        tessellator.addVertexWithUV(scaledresolution.getScaledWidth()/2-scaledresolution.getScaledWidth()/4, 0.0D, -90.0D, 1.0D, 0.0D);
			        tessellator.addVertexWithUV(0, 0.0D, -90.0D, 0.0D, 0.0D);
			        tessellator.draw();
			        tessellator.startDrawingQuads();
			        tessellator.addVertexWithUV((double)k+scaledresolution.getScaledWidth()/2-scaledresolution.getScaledWidth()/4, (double)l, -90.0D, 0.0D, 1.0D);
			        tessellator.addVertexWithUV(scaledresolution.getScaledWidth(), (double)l, -90.0D, 1.0D, 1.0D);
			        tessellator.addVertexWithUV(scaledresolution.getScaledWidth(), 0.0D, -90.0D, 1.0D, 0.0D);
			        tessellator.addVertexWithUV((double)k+scaledresolution.getScaledWidth()/2-scaledresolution.getScaledWidth()/4, 0.0D, -90.0D, 0.0D, 0.0D);
			        tessellator.draw();
			        
			        GL11.glColor3f(1, 1, 1);
			        
			        GL11.glDepthMask(true);
			        
	    		}
	    		event.setCanceled(true);
	    	}
		}
	}
	
	public static void renderImage(ResourceLocation image, int scaledResX, int scaledResY, float opacity, float r, float g, float b)
	{
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(r, g, b, opacity);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        Minecraft.getMinecraft().getTextureManager().bindTexture(image);

        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}
	

}
 