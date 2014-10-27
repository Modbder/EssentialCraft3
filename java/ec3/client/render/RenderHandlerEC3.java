package ec3.client.render;

import org.lwjgl.opengl.GL11;

import DummyCore.Utils.MiscUtils;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import ec3.common.block.BlocksCore;
import ec3.common.registry.PotionRegistry;
import ec3.network.proxy.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public class RenderHandlerEC3 {
	
	public static final ResourceLocation iconsEC = new ResourceLocation("essentialcraft","textures/special/icons.png");
	
	@SubscribeEvent
	public void pre(RenderGameOverlayEvent.Pre event)
	{
		if(event.type == RenderGameOverlayEvent.ElementType.HEALTH)
		{
			if(Minecraft.getMinecraft().thePlayer.getActivePotionEffect(PotionRegistry.mruCorruptionPotion)!=null)
			{
				Minecraft.getMinecraft().renderEngine.bindTexture(iconsEC);
			}
		}
	}
	
	@SubscribeEvent
	public void post(RenderGameOverlayEvent.Post event)
	{
		if(event.type == RenderGameOverlayEvent.ElementType.ALL)
		{
			Minecraft mc = Minecraft.getMinecraft();
	        ScaledResolution scaledresolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
	        int k = scaledresolution.getScaledWidth();
	        int l = scaledresolution.getScaledHeight();
	        
	        if(mc.thePlayer.getActivePotionEffect(PotionRegistry.chaosInfluence) != null)
	        {
		        GL11.glDisable(GL11.GL_ALPHA_TEST);
		        GL11.glDisable(GL11.GL_DEPTH_TEST);
		        GL11.glDepthMask(false);
		        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		        GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.3F);
		        IIcon iicon = BlocksCore.lightCorruption[0].getIcon(0, 7);
		        mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
		        float f1 = iicon.getMinU();
		        float f2 = iicon.getMinV();
		        float f3 = iicon.getMaxU();
		        float f4 = iicon.getMaxV();
		        Tessellator tessellator = Tessellator.instance;
		        tessellator.startDrawingQuads();
		        tessellator.addVertexWithUV(0.0D, (double)l, -90.0D, (double)f1, (double)f4);
		        tessellator.addVertexWithUV((double)k, (double)l, -90.0D, (double)f3, (double)f4);
		        tessellator.addVertexWithUV((double)k, 0.0D, -90.0D, (double)f3, (double)f2);
		        tessellator.addVertexWithUV(0.0D, 0.0D, -90.0D, (double)f1, (double)f2);
		        tessellator.draw();
		        GL11.glDepthMask(true);
		        GL11.glEnable(GL11.GL_DEPTH_TEST);
		        GL11.glEnable(GL11.GL_ALPHA_TEST);
		        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	        }
	        
	        if(mc.thePlayer.getActivePotionEffect(PotionRegistry.windTouch) != null)
	        {
		        GL11.glDisable(GL11.GL_ALPHA_TEST);
		        GL11.glDisable(GL11.GL_DEPTH_TEST);
		        GL11.glDepthMask(false);
		        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		        GL11.glColor4f(0F, 10F, 0F, ((float)mc.thePlayer.getActivePotionEffect(PotionRegistry.windTouch).getAmplifier()/23F));
		        IIcon iicon = Blocks.lava.getBlockTextureFromSide(0);
		        mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
		        float f1 = iicon.getMinU();
		        float f2 = iicon.getMinV();
		        float f3 = iicon.getMaxU();
		        float f4 = iicon.getMaxV();
		        Tessellator tessellator = Tessellator.instance;
		        tessellator.startDrawingQuads();
		        tessellator.addVertexWithUV(0.0D, (double)l, -90.0D, (double)f1, (double)f4);
		        tessellator.addVertexWithUV((double)k, (double)l, -90.0D, (double)f3, (double)f4);
		        tessellator.addVertexWithUV((double)k, 0.0D, -90.0D, (double)f3, (double)f2);
		        tessellator.addVertexWithUV(0.0D, 0.0D, -90.0D, (double)f1, (double)f2);
		        tessellator.draw();
		        GL11.glDepthMask(true);
		        GL11.glEnable(GL11.GL_DEPTH_TEST);
		        GL11.glEnable(GL11.GL_ALPHA_TEST);
		        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	        }
	        
	        if(mc.thePlayer.getActivePotionEffect(PotionRegistry.frozenMind) != null)
	        {
		        GL11.glDisable(GL11.GL_ALPHA_TEST);
		        GL11.glDisable(GL11.GL_DEPTH_TEST);
		        GL11.glDepthMask(false);
		        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		        GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.3F);
		        IIcon iicon = BlocksCore.lightCorruption[1].getIcon(0, 7);
		        mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
		        float f1 = iicon.getMinU();
		        float f2 = iicon.getMinV();
		        float f3 = iicon.getMaxU();
		        float f4 = iicon.getMaxV();
		        Tessellator tessellator = Tessellator.instance;
		        tessellator.startDrawingQuads();
		        tessellator.addVertexWithUV(0.0D, (double)l, -90.0D, (double)f1, (double)f4);
		        tessellator.addVertexWithUV((double)k, (double)l, -90.0D, (double)f3, (double)f4);
		        tessellator.addVertexWithUV((double)k, 0.0D, -90.0D, (double)f3, (double)f2);
		        tessellator.addVertexWithUV(0.0D, 0.0D, -90.0D, (double)f1, (double)f2);
		        tessellator.draw();
		        GL11.glDepthMask(true);
		        GL11.glEnable(GL11.GL_DEPTH_TEST);
		        GL11.glEnable(GL11.GL_ALPHA_TEST);
		        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	        }
		}
		if(event.type == RenderGameOverlayEvent.ElementType.HEALTH)
		{
			if(Minecraft.getMinecraft().thePlayer.getActivePotionEffect(PotionRegistry.mruCorruptionPotion)!=null)
			{
				Minecraft.getMinecraft().renderEngine.bindTexture(Gui.icons);
			}
		}
	}
	

}
