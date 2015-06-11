package ec3.client.render;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.lwjgl.opengl.GL11;

import baubles.api.BaublesApi;
import static org.lwjgl.opengl.GL11.*;
import DummyCore.Utils.Coord3D;
import DummyCore.Utils.MathUtils;
import DummyCore.Utils.MiscUtils;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.RenderTickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ec3.api.WindImbueRecipe;
import ec3.common.block.BlockWindRune;
import ec3.common.block.BlocksCore;
import ec3.common.item.ItemComputerArmor;
import ec3.common.item.ItemComputerBoard;
import ec3.common.item.ItemGenericArmor;
import ec3.common.item.ItemMagicalBuilder;
import ec3.common.item.ItemOrbitalRemote;
import ec3.common.item.ItemsCore;
import ec3.common.mod.EssentialCraftCore;
import ec3.common.registry.PotionRegistry;
import ec3.common.tile.TileWindRune;
import ec3.network.proxy.ClientProxy;
import ec3.utils.common.ECUtils;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderPlayerEvent.SetArmorModel;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.common.util.ForgeDirection;

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
	public static boolean isSprintKeyDown;
	
	public static double renderPartialTicksCheck = 0;
	
	public static boolean isNightVisionKeyDown;
	public static boolean isNightVisionActive;
	
	public static final IModelCustom board = AdvancedModelLoader.loadModel(new ResourceLocation("essentialcraft","textures/special/models/board.obj"));
	public static final ResourceLocation boardTextures = new ResourceLocation("essentialcraft","textures/special/models/board.png");
	
	public static Hashtable<IInventory, Hashtable<Integer,List<ForgeDirection>>> slotsTable = new Hashtable<IInventory, Hashtable<Integer, List<ForgeDirection>>>();
	
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
	
	public static IInventory getInventoryFromContainer(GuiContainer gc)
	{
		for(int i = 0; i < gc.inventorySlots.inventorySlots.size(); ++i)
		{
	        Slot slt = (Slot) gc.inventorySlots.inventorySlots.get(i);
	        if(slt != null)
	        	return slt.inventory;
		}
		return null;
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void renderPlayerModel(SetArmorModel event)
	{
		int slot = event.slot;
		ItemStack stk = event.entityPlayer.inventory.armorItemInSlot(3-event.slot);
		if(stk != null)
		{
            Item item = stk.getItem();

            if (item instanceof ItemGenericArmor)
            {
            	event.result = 0;
            	ItemGenericArmor itemarmor = (ItemGenericArmor)item;
            	Minecraft.getMinecraft().renderEngine.bindTexture(RenderBiped.getArmorResource(event.entityPlayer, stk, slot, null));
            	RenderPlayer rp = (RenderPlayer) RenderManager.instance.getEntityRenderObject(Minecraft.getMinecraft().thePlayer);
                ModelBiped modelbiped = slot == 2 ? rp.modelArmor : rp.modelArmorChestplate;
                modelbiped.bipedHead.showModel = slot == 0;
                modelbiped.bipedHeadwear.showModel = slot == 0;
                modelbiped.bipedBody.showModel = slot == 1 || slot == 2;
                modelbiped.bipedRightArm.showModel = slot == 1;
                modelbiped.bipedLeftArm.showModel = slot == 1;
                modelbiped.bipedRightLeg.showModel = slot == 2 || slot == 3;
                modelbiped.bipedLeftLeg.showModel = slot == 2 || slot == 3;
                modelbiped = net.minecraftforge.client.ForgeHooksClient.getArmorModel(event.entityPlayer, stk, slot, modelbiped);
                rp.setRenderPassModel(modelbiped);
                
                modelbiped.onGround = event.entityPlayer.swingProgress;
                modelbiped.isRiding = event.entityPlayer.isRiding();
                modelbiped.isChild = event.entityPlayer.isChild();
                
                int j = itemarmor.getColor(stk);
                if (j != -1)
                {
                    float f1 = (float)(j >> 16 & 255) / 255.0F;
                    float f2 = (float)(j >> 8 & 255) / 255.0F;
                    float f3 = (float)(j & 255) / 255.0F;
                    GL11.glColor3f(f1, f2, f3);

                    if (stk.isItemEnchanted())
                    {
                    	event.result = 31;
                    	return;
                    }

                    event.result = 16;
                    return;
                }
                
                GL11.glColor3f(1.0F, 1.0F, 1.0F);

                if (stk.isItemEnchanted())
                {
                    event.result = 15;
                    return;
                }

                event.result = 11;
                return;
            }

		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void renderPlayerSpecials(RenderPlayerEvent.Specials.Post event)
	{
		
		EntityPlayer p = event.entityPlayer;
		if(BaublesApi.getBaubles(p) != null)
		{
			if(BaublesApi.getBaubles(p).getStackInSlot(3) == null || !(BaublesApi.getBaubles(p).getStackInSlot(3).getItem() instanceof ItemComputerBoard))
				return;
			
			if(!p.capabilities.isFlying)
				return;
			
			GL11.glPushMatrix();
			
			GL11.glRotatef(180, 1, 0, 0);
			GL11.glRotatef(90, 0, 1, 0);
			
			GL11.glTranslatef(0, -1.5F, 0);
			
			GL11.glScalef(0.8F, 0.8F, 0.8F);
			
			Minecraft.getMinecraft().renderEngine.bindTexture(boardTextures);
			board.renderAll();
			
			GL11.glPopMatrix();
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void renderFogEvent(EntityViewRenderEvent.RenderFogEvent event)
	{
		EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
		ItemStack is = player.getCurrentEquippedItem();
		if(is != null)
		{
			if(is.getItem() instanceof ItemMagicalBuilder)
			{
				ItemMagicalBuilder builder = ItemMagicalBuilder.class.cast(is.getItem());
				if(builder.hasFirstPoint(is) && !builder.hasSecondPoint(is))
				{
					Coord3D c = builder.getFirstPoint(is);
					
					GL11.glPushMatrix();
					
					double d = 2.8D;
					
					GL11.glTranslated(c.x-TileEntityRendererDispatcher.staticPlayerX-player.motionX/d, c.y-TileEntityRendererDispatcher.staticPlayerY-((player.motionY+0.07D)/d), c.z-TileEntityRendererDispatcher.staticPlayerZ-player.motionZ/d);
					
					RenderGlobal.drawOutlinedBoundingBox(AxisAlignedBB.getBoundingBox(0, 0, 0, 1, 1, 1), 0xff00ff);
					
					GL11.glPopMatrix();
				}else if(builder.hasFirstPoint(is) && builder.hasSecondPoint(is))
				{
					Coord3D c = builder.getFirstPoint(is);
					Coord3D c1 = builder.getSecondPoint(is);
					
					GL11.glPushMatrix();
					
					double d = 2.8D;
					
					GL11.glTranslated(c.x-TileEntityRendererDispatcher.staticPlayerX-player.motionX/d, c.y-TileEntityRendererDispatcher.staticPlayerY-((player.motionY+0.07D)/d), c.z-TileEntityRendererDispatcher.staticPlayerZ-player.motionZ/d);
					
					double minX = 0;
					double maxX = c1.x-c.x + 1;
					double minY = 0;
					double maxY = c1.y-c.y + 1;
					double minZ = 0;
					double maxZ = c1.z-c.z + 1;
					
					if(c1.x < c.x)
					{
						minX = c1.x-c.x;
						maxX = 1;
					}
					
					if(c1.y < c.y)
					{
						minY = c1.y-c.y;
						maxY = 1;
					}
					
					if(c1.z < c.z)
					{
						minZ = c1.z-c.z;
						maxZ = 1;
					}
					
					RenderGlobal.drawOutlinedBoundingBox(AxisAlignedBB.getBoundingBox(minX,minY,minZ, maxX, maxY, maxZ), 0xff00ff);
					
					GL11.glPopMatrix();
				}
			}
			if(is.getItem() instanceof ItemOrbitalRemote)
			{
				//ItemOrbitalRemote remote = ItemOrbitalRemote.class.cast(is.getItem());
				float f = 1;
		        double d0 = player.prevPosX + (player.posX - player.prevPosX) * (double)f;
		        double d1 = player.prevPosY + (player.posY - player.prevPosY) * (double)f + (double)(player.getEyeHeight() - player.getDefaultEyeHeight()); // isRemote check to revert changes to ray trace position due to adding the eye height clientside and player yOffset differences
		        double d2 = player.prevPosZ + (player.posZ - player.prevPosZ) * (double)f;
		        
		        Vec3 lookVec = Vec3.createVectorHelper(d0, d1, d2);
		        float f1 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch);
		        float f2 = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw);
		        float f3 = MathHelper.cos(-f2 * 0.017453292F - (float)Math.PI);
		        float f4 = MathHelper.sin(-f2 * 0.017453292F - (float)Math.PI);
		        float f5 = -MathHelper.cos(-f1 * 0.017453292F);
		        float f6 = MathHelper.sin(-f1 * 0.017453292F);
		        float f7 = f4 * f5;
		        float f8 = f3 * f5;
		        double d3 = 32.0D;
		        Vec3 distanced = lookVec.addVector((double)f7 * d3, (double)f6 * d3, (double)f8 * d3);
				MovingObjectPosition mop = player.worldObj.func_147447_a(lookVec, distanced, true, false, false);
				
				if(mop != null && mop.typeOfHit == MovingObjectType.BLOCK)
				{
					int screenX = 0;
					int screenY = 0;
					int screenZ = 0;
					
					GL11.glPushMatrix();
					
					f1 = 1.0F;
			        GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
			        
			        GL11.glTranslated((double)mop.blockX-TileEntityRendererDispatcher.staticPlayerX, (double)mop.blockY-TileEntityRendererDispatcher.staticPlayerY, (double)mop.blockZ-TileEntityRendererDispatcher.staticPlayerZ);
			        if (f1 > 0.0F)
			        {
			            Tessellator tessellator = Tessellator.instance;
			            MiscUtils.bindTexture("minecraft", "textures/entity/beacon_beam.png");
			            GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, 10497.0F);
			            GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, 10497.0F);
			            GL11.glDisable(GL11.GL_LIGHTING);
			            GL11.glDisable(GL11.GL_CULL_FACE);
			            GL11.glDisable(GL11.GL_BLEND);
			            GL11.glDepthMask(true);
			            OpenGlHelper.glBlendFunc(770, 1, 1, 0);
			            f2 = (float) ((float)player.worldObj.getTotalWorldTime() + event.renderPartialTicks);
			            f3 = -f2 * 0.2F - (float)MathHelper.floor_float(-f2 * 0.1F);
			            byte b0 = 1;
			            d3 = (double)f2 * 0.025D * (1.0D - (double)(b0 & 1) * 2.5D);
			            tessellator.startDrawingQuads();
			            tessellator.setColorRGBA(0, 255, 255, 11);
			            double d5 = (double)b0 * 0.2D;
			            double d7 = 0.5D + Math.cos(d3 + 2.356194490192345D) * d5;
			            double d9 = 0.5D + Math.sin(d3 + 2.356194490192345D) * d5;
			            double d11 = 0.5D + Math.cos(d3 + (Math.PI / 4D)) * d5;
			            double d13 = 0.5D + Math.sin(d3 + (Math.PI / 4D)) * d5;
			            double d15 = 0.5D + Math.cos(d3 + 3.9269908169872414D) * d5;
			            double d17 = 0.5D + Math.sin(d3 + 3.9269908169872414D) * d5;
			            double d19 = 0.5D + Math.cos(d3 + 5.497787143782138D) * d5;
			            double d21 = 0.5D + Math.sin(d3 + 5.497787143782138D) * d5;
			            double d23 = (double)(256.0F * f1);
			            double d25 = 0.0D;
			            double d27 = 1.0D;
			            double d28 = (double)(-1.0F + f3);
			            double d29 = (double)(256.0F * f1) * (0.5D / d5) + d28;
			            tessellator.addVertexWithUV(screenX + d7, screenY + d23, screenZ + d9, d27, d29);
			            tessellator.addVertexWithUV(screenX + d7, screenY, screenZ + d9, d27, d28);
			            tessellator.addVertexWithUV(screenX + d11, screenY, screenZ + d13, d25, d28);
			            tessellator.addVertexWithUV(screenX + d11, screenY + d23, screenZ + d13, d25, d29);
			            tessellator.addVertexWithUV(screenX + d19, screenY + d23, screenZ + d21, d27, d29);
			            tessellator.addVertexWithUV(screenX + d19, screenY, screenZ + d21, d27, d28);
			            tessellator.addVertexWithUV(screenX + d15, screenY, screenZ + d17, d25, d28);
			            tessellator.addVertexWithUV(screenX + d15, screenY + d23, screenZ + d17, d25, d29);
			            tessellator.addVertexWithUV(screenX + d11, screenY + d23, screenZ + d13, d27, d29);
			            tessellator.addVertexWithUV(screenX + d11, screenY, screenZ + d13, d27, d28);
			            tessellator.addVertexWithUV(screenX + d19, screenY, screenZ + d21, d25, d28);
			            tessellator.addVertexWithUV(screenX + d19, screenY + d23, screenZ + d21, d25, d29);
			            tessellator.addVertexWithUV(screenX + d15, screenY + d23, screenZ + d17, d27, d29);
			            tessellator.addVertexWithUV(screenX + d15, screenY, screenZ + d17, d27, d28);
			            tessellator.addVertexWithUV(screenX + d7, screenY, screenZ + d9, d25, d28);
			            tessellator.addVertexWithUV(screenX + d7, screenY + d23, screenZ + d9, d25, d29);
			            tessellator.draw();
			            GL11.glEnable(GL11.GL_BLEND);
			            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			            GL11.glDepthMask(false);
			            tessellator.startDrawingQuads();
			            tessellator.setColorRGBA(100, 0, 255, 64);
			            double d30 = 0D;
			            double d4 = 0D;
			            double d6 = 1;
			            double d8 = 0D;
			            double d10 = 0D;
			            double d12 = 1;
			            double d14 = 1;
			            double d16 = 1;
			            double d18 = (double)(256.0F * f1);
			            double d20 = 0.0D;
			            double d22 = 1.0D;
			            double d24 = (double)(-1.0F + f3);
			            double d26 = (double)(256.0F * f1 * f1);
			            tessellator.addVertexWithUV(screenX + d30, screenY + d18, screenZ + d4, d22, d26);
			            tessellator.addVertexWithUV(screenX + d30, screenY, screenZ + d4, d22, d24);
			            tessellator.addVertexWithUV(screenX + d6, screenY, screenZ + d8, d20, d24);
			            tessellator.addVertexWithUV(screenX + d6, screenY + d18, screenZ + d8, d20, d26);
			            tessellator.addVertexWithUV(screenX + d14, screenY + d18, screenZ + d16, d22, d26);
			            tessellator.addVertexWithUV(screenX + d14, screenY, screenZ + d16, d22, d24);
			            tessellator.addVertexWithUV(screenX + d10, screenY, screenZ + d12, d20, d24);
			            tessellator.addVertexWithUV(screenX + d10, screenY + d18, screenZ + d12, d20, d26);
			            tessellator.addVertexWithUV(screenX + d6, screenY + d18, screenZ + d8, d22, d26);
			            tessellator.addVertexWithUV(screenX + d6, screenY, screenZ + d8, d22, d24);
			            tessellator.addVertexWithUV(screenX + d14, screenY, screenZ + d16, d20, d24);
			            tessellator.addVertexWithUV(screenX + d14, screenY + d18, screenZ + d16, d20, d26);
			            tessellator.addVertexWithUV(screenX + d10, screenY + d18, screenZ + d12, d22, d26);
			            tessellator.addVertexWithUV(screenX + d10, screenY, screenZ + d12, d22, d24);
			            tessellator.addVertexWithUV(screenX + d30, screenY, screenZ + d4, d20, d24);
			            tessellator.addVertexWithUV(screenX + d30, screenY + d18, screenZ + d4, d20, d26);
			            tessellator.draw();
			            GL11.glEnable(GL11.GL_LIGHTING);
			            GL11.glEnable(GL11.GL_TEXTURE_2D);
			            GL11.glDepthMask(true);
			        }
			        
			        GL11.glPopMatrix();
				}
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void clientGUIRenderTickEvent(RenderTickEvent event)
	{
		GL11.glDisable(GL11.GL_LIGHTING);
		
		EntityPlayer player = EssentialCraftCore.proxy.getClientPlayer();
		if(player != null)
		{
			World w = player.worldObj;
			if(w != null)
			{
				GuiScreen currentScreen = Minecraft.getMinecraft().currentScreen;
				if(currentScreen != null && currentScreen instanceof GuiContainer && GuiScreen.isCtrlKeyDown())
				{
					GuiContainer gc = (GuiContainer) currentScreen;
					try
					{
						Class<GuiContainer> gcClass = GuiContainer.class;
						Field FguiLeft = gcClass.getDeclaredFields()[4];
						Field FguiTop = gcClass.getDeclaredFields()[5];
						FguiLeft.setAccessible(true);
						FguiTop.setAccessible(true);
						int guiLeft = FguiLeft.getInt(gc);
						int guiTop = FguiTop.getInt(gc);
				        int k = guiLeft;
				        int l = guiTop;
				        
				        IInventory inv = getInventoryFromContainer(gc);
				        if(inv != null && inv instanceof ISidedInventory)
				        {
				        	ISidedInventory sided = (ISidedInventory) inv;
				        	if(RenderHandlerEC3.slotsTable.isEmpty() || !RenderHandlerEC3.slotsTable.containsKey(inv))
				        	{
				        		RenderHandlerEC3.slotsTable.clear();
								Hashtable<Integer, List<ForgeDirection>> accessibleSlots = new Hashtable<Integer, List<ForgeDirection>>();
								for(int j = 0; j < 6; ++j)
								{
									ForgeDirection d = ForgeDirection.VALID_DIRECTIONS[j];
									int[] slots = sided.getAccessibleSlotsFromSide(d.ordinal());
									if(slots != null)
									{
										for(int i1 = 0; i1 < slots.length; ++i1)
										{
											int slotN = slots[i1];
											if(accessibleSlots.containsKey(slotN))
											{
												List<ForgeDirection> lst = accessibleSlots.get(slotN);
												if(!lst.contains(d))
													lst.add(d);
												accessibleSlots.put(slotN, lst);
											}else
											{
												List<ForgeDirection> lst = new ArrayList<ForgeDirection>();
												lst.add(d);
												accessibleSlots.put(slotN, lst);
											}
										}
									}
								}
								RenderHandlerEC3.slotsTable.put(inv, accessibleSlots);
				        	}
				        }
				       
						for(int i = 0; i < gc.inventorySlots.inventorySlots.size(); ++i)
						{
					        Slot slt = (Slot) gc.inventorySlots.inventorySlots.get(i);
							if((slt.inventory instanceof TileEntity) || (slt.inventory instanceof InventoryBasic))
							{
								
								GL11.glPushMatrix();
									GL11.glScalef(0.5F, 0.5F, 0.5F);
									Minecraft.getMinecraft().fontRenderer.drawString(""+slt.slotNumber, (k+slt.xDisplayPosition)*2, (l+slt.yDisplayPosition)*2, 0x000000);
								GL11.glPopMatrix();
								if(slt.inventory instanceof ISidedInventory)
								{
									if(RenderHandlerEC3.slotsTable.containsKey(inv))
									{
										Hashtable<Integer, List<ForgeDirection>> accessibleSlots = RenderHandlerEC3.slotsTable.get(inv);
										if(accessibleSlots.containsKey(slt.slotNumber))
										{
											List<ForgeDirection> lst = accessibleSlots.get(slt.slotNumber);
											if(lst != null && !lst.isEmpty())
											{
												ForgeDirection d = lst.get((int) (w.getWorldTime()/20%lst.size()));
												GL11.glPushMatrix();
													GL11.glScalef(0.5F, 0.5F, 0.5F);
													Minecraft.getMinecraft().fontRenderer.drawString(""+d, (k+slt.xDisplayPosition)*2, (l+slt.yDisplayPosition+12)*2, 0x000000);
												GL11.glPopMatrix();
											}
										}
									}
								}
							}
							GL11.glColor3f(1, 1, 1);
						}
					}catch(Exception e)
					{
						e.printStackTrace();
					}
				}else if(currentScreen == null)
				{

				}
			}
		}
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onPlayerTick(TickEvent.PlayerTickEvent event)
	{
		for(int i = 0; i < ClientProxy.playingMusic.size(); ++i)
		{
			ISound snd = ClientProxy.playingMusic.get(i).getSecond();
			if(!Minecraft.getMinecraft().getSoundHandler().isSoundPlaying(snd))
				ClientProxy.playingMusic.remove(i);
		}
		if(event.player.worldObj.isRemote && event.phase == Phase.START)
			if(event.player instanceof EntityClientPlayerMP)
			{
				EntityClientPlayerMP player = (EntityClientPlayerMP) event.player;
				
			    if(PotionRegistry.paradox != null && mc.thePlayer.getActivePotionEffect(PotionRegistry.paradox) != null)
			    {
			    	int duration = mc.thePlayer.getActivePotionEffect(PotionRegistry.paradox).getDuration();
			    	if(duration > 100)
				    {
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
			    
			    if(renderPartialTicksCheck > 0)
			    {
			    	--renderPartialTicksCheck;
			    	if(renderPartialTicksCheck <= 0)
			    		Minecraft.getMinecraft().theWorld.playSound(player.posX, player.posY, player.posZ, "random.orb", 1F, 2F, false);
			    }
			    
			    if(!ClientProxy.kbArmorVision.getIsKeyPressed() && isNightVisionKeyDown)
			    {
			    	isNightVisionKeyDown = false;
			    }
			    
			    if(isNightVisionActive && (player.inventory.armorInventory[3]==null || !(player.inventory.armorInventory[3].getItem() instanceof ItemComputerArmor)))
			    {
			    	isNightVisionActive = false;
			    	Minecraft.getMinecraft().thePlayer.removePotionEffectClient(Potion.nightVision.id);
			    }
			    
			    if(ClientProxy.kbArmorVision.getIsKeyPressed() && !isNightVisionKeyDown && (player.inventory.armorInventory[3]!=null && (player.inventory.armorInventory[3].getItem() instanceof ItemComputerArmor)))
			    {
			    	isNightVisionKeyDown = true;
			    	isNightVisionActive = !isNightVisionActive;
			    	if(isNightVisionActive)
			    	{
			    		PotionEffect effect = new PotionEffect(Potion.nightVision.id,Integer.MAX_VALUE,0,false);
			    		effect.setPotionDurationMax(true);
			    		Minecraft.getMinecraft().thePlayer.addPotionEffect(effect);
			    	}else
			    	{
			    		Minecraft.getMinecraft().thePlayer.removePotionEffectClient(Potion.nightVision.id);
			    	}
			    	
			    }
			    
			    if(!ClientProxy.kbArmorBoost.getIsKeyPressed() && isSprintKeyDown)
			    {
			    	isSprintKeyDown = false;
			    }
			    
			    if(ClientProxy.kbArmorBoost.getIsKeyPressed() && !isSprintKeyDown)
			    {
			    	isSprintKeyDown = true;
			    	if(ItemComputerArmor.class.cast(ItemsCore.computer_helmet).hasFullset(player) && renderPartialTicksCheck <= 0)
			    	{
			    		Vec3 lookVec = player.getLookVec();
			    		player.motionX += lookVec.xCoord*3;
			    		player.motionY += lookVec.yCoord*3;
			    		player.motionZ += lookVec.zCoord*3;
			    		for(int i = 0; i < 10; ++i)
			    			player.worldObj.playSound(player.posX, player.posY, player.posZ, "fireworks.largeBlast", 1, 0.01F+player.worldObj.rand.nextFloat(), false);
			    		renderPartialTicksCheck = 20;
			    	}
			    }
			    
			    if(!player.capabilities.isFlying && player.isInWater() && ItemComputerArmor.class.cast(ItemsCore.computer_helmet).hasFullset(player) && Minecraft.getMinecraft().gameSettings.keyBindForward.getIsKeyPressed())
			    {
			    	player.motionX *= 1.2D;
			    	if(player.motionY > 0)
			    		player.motionY *= 1.2D;
			    	player.motionZ *= 1.2D;
			    	
			    	double d8, d9, d2, d4;
		            d2 = Math.cos((double)player.rotationYaw * Math.PI / 180.0D);
		            d4 = Math.sin((double)player.rotationYaw * Math.PI / 180.0D);
		            double d5 = (double)(player.worldObj.rand.nextFloat() * 2.0F - 1.0F);
		            double d6 = (double)(player.worldObj.rand.nextInt(2) * 2 - 1) * 0.7D;
		            
                    d8 = player.posX - d2 * d5 * 0.8D + d4 * d6;
                    d9 = player.posZ - d4 * d5 * 0.8D - d2 * d6;
                    for(int i = 0; i < 10; ++i)
                    	player.worldObj.spawnParticle("bubble", d8, player.posY - 0.125D, d9, player.motionX, player.motionY, player.motionZ);
			    }
			}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void renderWindRuneOverlay(DrawBlockHighlightEvent event)
	{
		
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void pre(RenderGameOverlayEvent.Pre event)
	{
		try {
			
			if(event.type == ElementType.CROSSHAIRS)
			{
				if(ItemComputerArmor.class.cast(ItemsCore.computer_helmet).hasFullset(Minecraft.getMinecraft().thePlayer))
				{
					ScaledResolution res = new ScaledResolution(mc,mc.displayWidth,mc.displayHeight);
					float r = 10;
					float k = res.getScaledWidth()/2 + 105;
					float h = 11;
				    
				    int circle_points = 100;
				    float angle = 2.0f * 3.1416f / circle_points;
				    
				    GL11.glPushMatrix();
				    
			        GL11.glEnable(2929);
			        GL11.glEnable(3042);
			        GL11.glBlendFunc(770, 771);
			        GL11.glDisable(3553);
			        GL11.glDisable(2896);
			        
					GL11.glEnable(GL11.GL_BLEND);
			        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			        GL11.glDisable(GL11.GL_ALPHA_TEST);
				    
					glBegin(GL_TRIANGLE_FAN); 
					
					float renderTime = Minecraft.getMinecraft().thePlayer.ticksExisted % 40;
					if(renderTime > 20)
						renderTime = 40 - renderTime;
					
					glColor4f(0, 1, 1, MathHelper.clamp_float(renderTime/20,0.1F,0.8F));
					
					glVertex2f(k, h);
					
					for (angle=2.0f;angle<8.3F-renderPartialTicksCheck/20*8.3F;angle+=0.01)
					{
					    float x2 = (float) (k+Math.sin(angle)*r);
					    float y2 = (float) (h+Math.cos(angle)*r);
					    
					    glVertex2f(x2,y2);
					}
					
					glColor3f(1, 1, 1);
					
					glEnd(); 
					
			        GL11.glDisable(GL11.GL_BLEND);
			        GL11.glEnable(GL11.GL_ALPHA_TEST);
					
			        GL11.glEnable(3553);
					
					GL11.glPopMatrix();
					
					GL11.glEnable(GL11.GL_BLEND);
			        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			        GL11.glDisable(GL11.GL_ALPHA_TEST);
					
					GL11.glColor4f(1, 1, 1, MathHelper.clamp_float(renderTime/20,0.1F,0.8F));
					
					MiscUtils.drawTexture_Items((int)k-8, (int)h-8, Items.fireworks.getIconFromDamage(0), 16, 16, 100);
			       
					GL11.glDisable(GL11.GL_BLEND);
			        GL11.glEnable(GL11.GL_ALPHA_TEST);
				}
				
				if(Minecraft.getMinecraft().thePlayer.inventory.armorInventory[3] != null && Minecraft.getMinecraft().thePlayer.inventory.armorInventory[3].getItem() instanceof ItemComputerArmor)
				{
					ScaledResolution res = new ScaledResolution(mc,mc.displayWidth,mc.displayHeight);
					float r = 10;
					float k = res.getScaledWidth()/2-108;
					float h = 11;
				    
				    int circle_points = 100;
				    float angle = 2.0f * 3.1416f / circle_points;
				    
				    GL11.glPushMatrix();
				    
			        GL11.glEnable(2929);
			        GL11.glEnable(3042);
			        GL11.glBlendFunc(770, 771);
			        GL11.glDisable(3553);
			        GL11.glDisable(2896);
			        
					GL11.glEnable(GL11.GL_BLEND);
			        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			        GL11.glDisable(GL11.GL_ALPHA_TEST);
				    
					glBegin(GL_TRIANGLE_FAN); 
					
					float renderTime = Minecraft.getMinecraft().thePlayer.ticksExisted % 40;
					if(renderTime > 20)
						renderTime = 40 - renderTime;
					
					float cB = 0.2F;
					
					if(isNightVisionActive)
						cB = 1F;
					
					glColor4f(0, 0, cB, MathHelper.clamp_float(renderTime/20,0.1F,0.8F));
					
					glVertex2f(k, h);
					
					for (angle=2.0f;angle<8.3F;angle+=0.01)
					{
					    float x2 = (float) (k+Math.sin(angle)*r);
					    float y2 = (float) (h+Math.cos(angle)*r);
					    
					    glVertex2f(x2,y2);
					}
					
					glColor3f(1, 1, 1);
					
					glEnd(); 
					
			        GL11.glDisable(GL11.GL_BLEND);
			        GL11.glEnable(GL11.GL_ALPHA_TEST);
					
			        GL11.glEnable(3553);
					
					GL11.glPopMatrix();
					
					GL11.glEnable(GL11.GL_BLEND);
			        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			        GL11.glDisable(GL11.GL_ALPHA_TEST);
					
					GL11.glColor4f(1, 1, 1, MathHelper.clamp_float(renderTime/20,0.1F,0.8F));
					
					MiscUtils.drawTexture_Items((int)k-8, (int)h-8, ItemsCore.computer_helmet.getIconFromDamage(0), 16, 16, 100);
			       
					GL11.glDisable(GL11.GL_BLEND);
			        GL11.glEnable(GL11.GL_ALPHA_TEST);
				}
				
			}
			
			if(event.type == RenderGameOverlayEvent.ElementType.HEALTH)
			{
				if(Minecraft.getMinecraft().thePlayer.getActivePotionEffect(PotionRegistry.mruCorruptionPotion)!=null)
				{
					Minecraft.getMinecraft().renderEngine.bindTexture(iconsEC);
					
				}
			}
			if(event.type == RenderGameOverlayEvent.ElementType.HOTBAR)
			{
				EntityPlayer p = Minecraft.getMinecraft().thePlayer;
				MovingObjectPosition target = p.rayTrace(mc.playerController.getBlockReachDistance(), event.partialTicks);
				
				if(target != null && target.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
				{
					int x = target.blockX;
					int y = target.blockY;
					int z = target.blockZ;
					Block b = p.worldObj.getBlock(x, y, z);
					if(b != null && b instanceof BlockWindRune)
					{
						TileWindRune rune = (TileWindRune) p.worldObj.getTileEntity(x, y, z);
						if(rune != null)
						{
							if(p.getCurrentEquippedItem() != null)
							{
								WindImbueRecipe rec = WindImbueRecipe.findRecipeByComponent(p.getCurrentEquippedItem());
								if(rec != null)
								{
									int energyReq = rec.enderEnergy;
									int energy = rune.energy;
									
									int color = 0xffffff;
									boolean creative = p.capabilities.isCreativeMode;
									
									if(energy < energyReq && !creative)
										color = 0xff0000;
									else
										color = 0x00ff66;
									
									ScaledResolution res = new ScaledResolution(mc,mc.displayWidth,mc.displayHeight);
									String displayed = energyReq+" ESPE";
									if(creative)
										displayed += " [Creative]";
									Minecraft.getMinecraft().fontRenderer.drawString(displayed, res.getScaledWidth()/2-displayed.length()*3, res.getScaledHeight()/2+5, color);
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			return;
		}
	}
	
	@SideOnly(Side.CLIENT)
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
			        GL11.glDisable(GL11.GL_DEPTH_TEST);
			        GL11.glDepthMask(false);
			    	GL11.glEnable(GL11.GL_BLEND);
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
			        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			    }
			    
			    if(ECUtils.inPortalTime.containsKey(Minecraft.getMinecraft().thePlayer))
			    {
			    	int i = ECUtils.inPortalTime.get(Minecraft.getMinecraft().thePlayer);
			        GL11.glDisable(GL11.GL_DEPTH_TEST);
			        GL11.glDepthMask(false);
			    	GL11.glEnable(GL11.GL_BLEND);
			        GL11.glColor4f(0F, 10F, 0F, ((float)i)/200);
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
			        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			    }
			    
			    if(mc.thePlayer.getActivePotionEffect(PotionRegistry.frozenMind) != null)
			    {
			        GL11.glDisable(GL11.GL_DEPTH_TEST);
			        GL11.glDepthMask(false);
			    	GL11.glEnable(GL11.GL_BLEND);
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
			        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			    }
			    
			    if(mc.thePlayer.getActivePotionEffect(PotionRegistry.paradox) != null)
			    {
			    	/*
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
			    	}*/
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
