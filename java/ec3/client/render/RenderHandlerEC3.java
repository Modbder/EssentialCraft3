package ec3.client.render;

import org.lwjgl.opengl.GL11;

import DummyCore.Utils.Coord3D;
import DummyCore.Utils.MathUtils;
import DummyCore.Utils.MiscUtils;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ec3.common.block.BlocksCore;
import ec3.common.registry.PotionRegistry;
import ec3.network.proxy.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

public class RenderHandlerEC3 {
	
	public static final ResourceLocation iconsEC = new ResourceLocation("essentialcraft","textures/special/icons.png");
	public static final ResourceLocation whitebox = new ResourceLocation("essentialcraft","textures/special/whitebox.png");
	public static final Minecraft mc = Minecraft.getMinecraft();
	
	public static boolean isParadoxActive;
	public static int currentParadoxTicks;
	public static int paradoxID;
	
	public static Coord3D explosion;
	
	public static IRenderHandler skyRenderer;
	
	public static boolean isMouseInverted;
	
	public void renderParadox()
	{
		Minecraft mc = Minecraft.getMinecraft();
	    ScaledResolution scaledresolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
	    int k = scaledresolution.getScaledWidth();
	    int l = scaledresolution.getScaledHeight();
		if(currentParadoxTicks >= 190)
		{
			renderImage(whitebox, k, l, 1,0,0,0);

		}
		if(paradoxID == 0)
		{
			if(currentParadoxTicks == 199)
			{
				mc.thePlayer.worldObj.playSound(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, "ambient.cave.cave", 100, 0.01F, false);
			}
			if(currentParadoxTicks == 199)
			{
				MiscUtils.setShaders(5);
			}
			if(currentParadoxTicks <= 10)
			{
				MiscUtils.setShaders(-1);
				renderImage(whitebox, k, l, 1,1,1,1);
			}
			if(currentParadoxTicks >= 10)
			for(int i = 0; i < 20; ++i)
			{
				mc.theWorld.spawnParticle("reddust", mc.thePlayer.posX+MathUtils.randomDouble(mc.theWorld.rand)*16, mc.thePlayer.posY+MathUtils.randomDouble(mc.theWorld.rand)*16, mc.thePlayer.posZ+MathUtils.randomDouble(mc.theWorld.rand)*16, -1, 0, 0);
			}
		}
		if(paradoxID == 1)
		{
			if(currentParadoxTicks >= 190)
			{
				renderImage(whitebox, k, l, 1,0,0,0);
			}
			if(currentParadoxTicks == 190)
			{
				MiscUtils.setShaders(16);
				World w = mc.theWorld;
				WorldProvider prov = w.provider;
				if(!(prov.getSkyRenderer() instanceof RenderSkyParadox_1))
				{
					skyRenderer = prov.getSkyRenderer();
					prov.setSkyRenderer(new RenderSkyParadox_1());
				}
			}
			if(currentParadoxTicks <= 10)
			{
				MiscUtils.setShaders(-1);
				renderImage(whitebox, k, l, 1,1,1,1);
				World w = mc.theWorld;
				WorldProvider prov = w.provider;
				prov.setSkyRenderer(skyRenderer);
			}
		}
		if(paradoxID == 2)
		{
			if(currentParadoxTicks >= 190)
			{
				renderImage(whitebox, k, l, 1,0,0,0);
			}
			if(currentParadoxTicks == 190)
			{
				MiscUtils.setShaders(12);
			}
			if(currentParadoxTicks < 190 && currentParadoxTicks > 10)
			{
				if(explosion == null)
				{
					explosion = new Coord3D(mc.thePlayer.posX+MathUtils.randomDouble(mc.theWorld.rand)*32, mc.thePlayer.posY+32, mc.thePlayer.posZ+MathUtils.randomDouble(mc.theWorld.rand)*32);
					
				}
				else
				{
					mc.theWorld.playSound(explosion.x, explosion.y, explosion.z, "random.explode", 3, 0.1F, true);
					mc.theWorld.createExplosion(null, explosion.x, explosion.y, explosion.z, 5, false);
					explosion.y -= 0.5F;
					if(explosion.y < mc.thePlayer.posY-10)
					{
						explosion = null;
					}
				}
			}
			if(currentParadoxTicks <= 10)
			{
				MiscUtils.setShaders(-1);
				renderImage(whitebox, k, l, 1,1,1,1);
			}
		}
		if(paradoxID == 3)
		{
			if(currentParadoxTicks >= 190)
			{
				renderImage(whitebox, k, l, 1,0,0,0);
			}
			if(currentParadoxTicks == 190)
			{
				MiscUtils.setShaders(8);
			}
			mc.thePlayer.motionY += 0.04F;
			if(currentParadoxTicks <= 10)
			{
				MiscUtils.setShaders(-1);
				renderImage(whitebox, k, l, 1,1,1,1);
			}
		}
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onPlayerTick(LivingUpdateEvent event)
	{
		if(event.entity.worldObj.isRemote)
			if(event.entityLiving instanceof EntityClientPlayerMP)
			{
				EntityClientPlayerMP player = (EntityClientPlayerMP) event.entityLiving;
			    if(mc.thePlayer.getActivePotionEffect(PotionRegistry.paradox) != null)
			    {
			    	int duration = mc.thePlayer.getActivePotionEffect(PotionRegistry.paradox).getDuration();
			    	if(duration % 100 == 0)
			    		mc.theWorld.playSound(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, "essentialcraft:sound.heartbeat", 100, 1, true);
			    	if(currentParadoxTicks > 0)
			    		--currentParadoxTicks;
			    	if(currentParadoxTicks == 0 && duration < 1600)
			    	{
			    		paradoxID = -1;
			    		isParadoxActive = false;
			    		MiscUtils.setShaders(-1);
			    	}
			    	if(duration < 1700 && !isParadoxActive && player.worldObj.rand.nextFloat() < 0.005F)
			    	{
			    		//paradoxID = player.worldObj.rand.nextInt(6);
			    		paradoxID = mc.theWorld.rand.nextInt(4);
			    		currentParadoxTicks = 200;
			    		isParadoxActive = true;
			    	}
			    }else
			    {
			    	currentParadoxTicks = 0;
		    		paradoxID = -1;
		    		isParadoxActive = false;
			    }
			}
	}
	
	@SubscribeEvent
	public void pre(RenderGameOverlayEvent.Pre event)
	{
		try {
			if(event.type == RenderGameOverlayEvent.ElementType.HEALTH)
			{
				if(Minecraft.getMinecraft().thePlayer.getActivePotionEffect(PotionRegistry.mruCorruptionPotion)!=null)
				{
					Minecraft.getMinecraft().renderEngine.bindTexture(iconsEC);
					
				}
			}
		} catch (Exception e) {
			return;
		}
	}
	
	
	@SubscribeEvent
	public void post(RenderGameOverlayEvent.Post event)
	{
		try {
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
			    
			    if(mc.thePlayer.getActivePotionEffect(PotionRegistry.windTouch) != null && mc.thePlayer.getActivePotionEffect(PotionRegistry.windTouch).getAmplifier() > 1)
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
			    
			    if(mc.thePlayer.getActivePotionEffect(PotionRegistry.paradox) != null)
			    {
			    	int duration = mc.thePlayer.getActivePotionEffect(PotionRegistry.paradox).getDuration();
			    	renderImage(whitebox, k, l, (float)(duration-1800)/200,1,1,1);
			    	if(duration == 1700)MiscUtils.setShaders(-1);
			    	if(duration <= 1600)
			    	{
			    		int rDur = duration % 200;
			    		if((rDur < 18 && rDur > 15) || (rDur > 20 && rDur < 25))
			    			renderImage(whitebox, k, l, 1,0,0,0);
			    	}
			    	renderParadox();
			    	if(duration == 1)
			    	{
			    		MiscUtils.setShaders(-1);
						World w = mc.theWorld;
						WorldProvider prov = w.provider;
						if(prov.getSkyRenderer() instanceof RenderSkyParadox_1)
							prov.setSkyRenderer(skyRenderer);
			    	}
			    }else
			    
			    try
			    {
				World w = mc.theWorld;
				WorldProvider prov = w.provider;
				if(prov.getSkyRenderer() instanceof RenderSkyParadox_1)
					prov.setSkyRenderer(skyRenderer);
			    }catch(Exception e)
			    {
			    	return;
			    }
			}
			if(event.type == RenderGameOverlayEvent.ElementType.HEALTH)
			{
				if(Minecraft.getMinecraft().thePlayer.getActivePotionEffect(PotionRegistry.mruCorruptionPotion)!=null)
				{
					Minecraft.getMinecraft().renderEngine.bindTexture(Gui.icons);
				}
			}
		} catch (Exception e) {
			return;
		}
	}
	
	public static void renderImage(ResourceLocation image, int scaledResX, int scaledResY, float opacity, float r, float g, float b)
	{
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(r, g, b, opacity);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        mc.getTextureManager().bindTexture(image);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(0.0D, (double)scaledResY, -90.0D, 0.0D, 1.0D);
        tessellator.addVertexWithUV((double)scaledResX, (double)scaledResY, -90.0D, 1.0D, 1.0D);
        tessellator.addVertexWithUV((double)scaledResX, 0.0D, -90.0D, 1.0D, 0.0D);
        tessellator.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
        tessellator.draw();
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}
	

}
