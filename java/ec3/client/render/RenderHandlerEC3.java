package ec3.client.render;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.lwjgl.opengl.GL11;

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
import ec3.common.item.ItemGenericArmor;
import ec3.common.mod.EssentialCraftCore;
import ec3.common.registry.PotionRegistry;
import ec3.common.tile.TileWindRune;
import ec3.utils.common.ECUtils;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderPlayerEvent.SetArmorModel;
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
	public void clientGUIRenderTickEvent(RenderTickEvent event)
	{
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
				}
			}
		}
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onPlayerTick(TickEvent.PlayerTickEvent event)
	{
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
			}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void renderWindRuneOverlay(DrawBlockHighlightEvent event)
	{

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
