package ec3.client.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import DummyCore.Utils.MathUtils;
import DummyCore.Utils.MiscUtils;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import ec3.api.ApiCore;
import ec3.api.CategoryEntry;
import ec3.api.DiscoveryEntry;
import ec3.api.MagicianTableRecipe;
import ec3.api.PageEntry;
import ec3.api.RadiatingChamberRecipe;
import ec3.api.ShapedAssemblerRecipe;
import ec3.api.StructureBlock;
import ec3.api.StructureRecipe;
import ec3.common.mod.EssentialCraftCore;
import ec3.utils.cfg.Config;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class GuiResearchBook extends GuiScreen{
	
	 protected static RenderItem itemRender = new RenderItem();
	 
	 public int currentDepth;
	 public static int currentPage;
	 public static CategoryEntry currentCategory;
	 public static DiscoveryEntry currentDiscovery;
	 public static int currentPage_discovery;
	 
	 public static List<Object> hoveringText = new ArrayList<Object>();
	 
	 public static List<Object[]> prevState = new ArrayList<Object[]>();
	 
	 public static final int discoveries_per_page = 48;
	 
	 public NBTTagCompound bookTag;
	 
	 public boolean firstOpened = false;
	 
	 public boolean isLeftMouseKeyPressed = false;
	 
	 public boolean isRightMouseKeyPressed = false;
	 
	 public static final ResourceLocation gui = new ResourceLocation("essentialcraft","textures/gui/research_book_generic.png");
	 
	 public static float ticksOpened;
	 
	 public GuiResearchBook()
	 {
		 super();
	 }
	 
	 public void updateScreen() 
	 {
		 ++ticksOpened;
	 }
	 
	 public void initGui() 
	 {
		 isLeftMouseKeyPressed = Mouse.isButtonDown(0);
		 isRightMouseKeyPressed = Mouse.isButtonDown(1);
		 firstOpened = false;
		
		 this.buttonList.clear();
		 this.labelList.clear();
		 bookTag = this.mc.thePlayer.getCurrentEquippedItem().getTagCompound();
	     if(currentCategory == null)
	    	 initCategories();
	     if(currentCategory != null && currentDiscovery == null)
	    	 initDiscoveries();
	     if(currentCategory != null && currentDiscovery != null)
	    	 initPage();
	 }
	 
	 public void drawBackground(int p_146278_1_)
	 {
	     int k = (this.width - 256) / 2;
	     int l = (this.height - 168) / 2;
	     if(currentCategory == null)
	     {
	    	 this.mc.renderEngine.bindTexture(gui);
	     }
	     if(currentCategory != null && currentDiscovery == null && currentCategory.specificBookTextures != null)
	     {
	    	 this.mc.renderEngine.bindTexture(currentCategory.specificBookTextures);
	     }
	     if(currentCategory != null && currentDiscovery == null && currentCategory.specificBookTextures == null)
	     {
	    	 this.mc.renderEngine.bindTexture(gui);
	     }
	     if(currentCategory != null && currentDiscovery != null)
	     {
    		 if(currentCategory == null || currentCategory.specificBookTextures == null)
    			 this.mc.renderEngine.bindTexture(gui);
    		 else
    			 this.mc.renderEngine.bindTexture(currentCategory.specificBookTextures);
	     }
	     this.drawTexturedModalRect(k, l, 0, 0, 256, 180);
	 }
	 
	 @Override
	 public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_)
	 {
		 try
		 {
		 }catch(Exception e)
		 {
			 e.printStackTrace();return;
		 }
		 if(isRightMouseKeyPressed && !Mouse.isButtonDown(1))
		 {
			 isRightMouseKeyPressed = false;
		 }
		 if(!isRightMouseKeyPressed && Mouse.isButtonDown(1))
		 {
			 isRightMouseKeyPressed = true;
			 if(!prevState.isEmpty())
			 {
				  Object[] tryArray = prevState.get(prevState.size()-1);
				  currentPage = Integer.parseInt(tryArray[1].toString());
				  currentPage_discovery = Integer.parseInt(tryArray[2].toString());
				  currentDiscovery = (DiscoveryEntry) tryArray[0];
				  prevState.remove(prevState.size()-1);
				  this.initGui();
			 }
		 }
		 if(isLeftMouseKeyPressed && !Mouse.isButtonDown(0))
			 isLeftMouseKeyPressed = false;
	     if(FMLClientHandler.instance().getCurrentLanguage().equalsIgnoreCase("en_gb") && !firstOpened)
	     {
	    	 firstOpened = true;
	    	 this.fontRendererObj = new FontRenderer(mc.gameSettings, new ResourceLocation("essentialcraft","textures/special/research_font.png"), mc.renderEngine, false);
	    	 fontRendererObj.setUnicodeFlag(false);
	    	 fontRendererObj.setBidiFlag(true);
	    	 ((IReloadableResourceManager)this.mc.getResourceManager()).registerReloadListener(fontRendererObj);
	     }
		 hoveringText.clear();
	     drawBackground(0);
		 if(currentCategory == null)
		 {
			 drawCategories(p_73863_1_, p_73863_2_);
		 }
		 if(currentCategory != null && currentDiscovery == null)
		 {
			 drawDiscoveries(p_73863_1_,p_73863_2_);
		 }
		 if(currentCategory != null && currentDiscovery != null)
		 {
			 drawPage(p_73863_1_,p_73863_2_);
		 }
		 drawAllText();
	 }
	 
    @SuppressWarnings("unchecked")
	public void drawAllText()
	 {
		 //TODO drawText
		 for(int i = 0; i < hoveringText.size(); ++i)
		 {
			 Object obj = hoveringText.get(i);
			 if(obj instanceof Object[])
			 {
				 Object[] drawable = (Object[]) obj;
				 List<String> listToDraw = (List<String>) drawable[0];
				 int x = Integer.parseInt(drawable[1].toString());
				 int y = Integer.parseInt(drawable[2].toString());
				 FontRenderer renderer = (FontRenderer) drawable[3];
				 this.drawHoveringText(listToDraw, x, y, renderer);
			 }
		 }
	 }
	 
	 @SuppressWarnings("unchecked")
	public void initDiscoveries()
	 {
		 currentPage = 0;
	     int k = (this.width - 256) / 2;
	     int l = (this.height - 168) / 2;
	     GuiButtonNoSound back = new GuiButtonNoSound(0, k+236, l+7, 14, 18, "");
	     this.buttonList.add(back);
	     GuiButtonNoSound page_left = new GuiButtonNoSound(1,k+7,l+158,24,13,"");
	     GuiButtonNoSound page_right = new GuiButtonNoSound(2,k+227,l+158,24,13,"");
	     int discAmount = currentCategory.discoveries.size();
	     if(discAmount - 48*(currentPage_discovery+1) <= 0)
	     {
	    	 page_left.enabled = false;
	     }
	     if(discAmount - 48*(currentPage_discovery+1) > 0)
	     {
	    	 page_right.enabled = true;
	     }else
	    	 page_right.enabled = false;
	     for(int i = 48*(currentPage_discovery); i < discAmount - 48*(currentPage_discovery); ++i)
	     {
	    	 int dx = k + (22*(i/6)) + 12;
	    	 if(i >= 24) dx += 40;
	    	 int dy = l + (22*(i%6)) + 22;
	    	 GuiButtonNoSound btnAdd = new GuiButtonNoSound(i + 3, dx, dy, 20, 20, "");
	    	 this.buttonList.add(btnAdd);
	     }
	     this.buttonList.add(page_left);
	     this.buttonList.add(page_right);
	 }
	 
	 @SuppressWarnings("unchecked")
	public void initCategories()
	 {
		 if(bookTag == null)
		 {
			 bookTag = new NBTTagCompound();
			 bookTag.setInteger("tier", 3);
		 }
		 currentPage_discovery = 0;
	     int k = (this.width - 256) / 2 + 128;
	     int l = (this.height - 168) / 2;
	     if(ApiCore.categories != null)
		     for(int i = 0; i < ApiCore.categories.size(); ++i)
		     {
		    	 CategoryEntry cat = ApiCore.categories.get(i);
		    	 if(cat != null)
		    	 {
			    	 GuiButtonNoSound added = new GuiButtonNoSound(i, k + (30*(i/5)) + 8, l + (30*(i%5)) + 28, 20, 20, "");
			    	 added.enabled = false;
			    	 int reqTier = cat.reqTier;
			    	 int tier = bookTag.getInteger("tier");
			    	 if(tier >= reqTier)
			    		 added.enabled = true;
			    	 this.buttonList.add(added);
		    	 }
		     }
	 }
	 
	 @SuppressWarnings("unchecked")
	public void initPage()
	 {
	     int k = (this.width - 256) / 2;
	     int l = (this.height - 168) / 2;
	     GuiButtonNoSound back = new GuiButtonNoSound(0, k+236, l+7, 14, 18, "");
	     this.buttonList.add(back);
	     GuiButtonNoSound page_left = new GuiButtonNoSound(1,k+7,l+158,24,13,"");
	     GuiButtonNoSound page_right = new GuiButtonNoSound(2,k+227,l+158,24,13,"");
	     int pagesMax = currentDiscovery.pages.size();
	     if(currentPage <= 0)
	     {
	    	 page_left.enabled = false;
	     }
	     if(currentPage + 2 >= pagesMax)
	     {
	    	 page_right.enabled = false;
	     }
	     this.buttonList.add(page_left);
	     this.buttonList.add(page_right);
	 }
	 
	 public void drawPage(int mouseX, int mouseZ)
	 {
	     int pagesMax = currentDiscovery.pages.size();
	     for (int ik = 0; ik < this.buttonList.size(); ++ik)
	     {
	    	 GL11.glColor3f(1, 1, 1);
	    	 GuiButton btn  = (GuiButton) this.buttonList.get(ik);
	    	 boolean hover = mouseX >= btn.xPosition && mouseZ >= btn.yPosition && mouseX < btn.xPosition + btn.width && mouseZ < btn.yPosition + btn.height;
	    	 int id = btn.id;
	    	 if(id == 0)
	    	 {
	    		 if(currentCategory == null || currentCategory.specificBookTextures == null)
	    			 this.mc.renderEngine.bindTexture(gui);
	    		 else
	    			 this.mc.renderEngine.bindTexture(currentCategory.specificBookTextures);
	    		 if(hover)
	    			 GL11.glColor3f(1, 0.8F, 1);
	    		 this.drawTexturedModalRect(btn.xPosition, btn.yPosition, 49, 238, 14, 18);
	    	 }
	    	 if(id == 1)
	    	 {
	    		 if(currentCategory == null || currentCategory.specificBookTextures == null)
	    			 this.mc.renderEngine.bindTexture(gui);
	    		 else
	    			 this.mc.renderEngine.bindTexture(currentCategory.specificBookTextures);
	    		 if(hover && btn.enabled)
	    			 GL11.glColor3f(1, 0.8F, 1);
	    		 if(!btn.enabled)
	    			 GL11.glColor3f(0.3F, 0.3F, 0.3F);
	    		 this.drawTexturedModalRect(btn.xPosition, btn.yPosition, 0, 243, 24, 13);
	    	 }
	    	 if(id == 2)
	    	 {
	    		 if(currentCategory == null || currentCategory.specificBookTextures == null)
	    			 this.mc.renderEngine.bindTexture(gui);
	    		 else
	    			 this.mc.renderEngine.bindTexture(currentCategory.specificBookTextures);
	    		 if(hover && btn.enabled)
	    			 GL11.glColor3f(1, 0.8F, 1);
	    		 if(!btn.enabled)
	    			 GL11.glColor3f(0.3F, 0.3F, 0.3F);
	    		 this.drawTexturedModalRect(btn.xPosition, btn.yPosition, 25, 243, 24, 13);
	    	 }
	     }
	     //Text Draw
	     for (int ik = 0; ik < this.buttonList.size(); ++ik)
	     {
	    	 GL11.glColor3f(1, 1, 1);
	    	 GuiButton btn  = (GuiButton) this.buttonList.get(ik);
	    	 boolean hover = mouseX >= btn.xPosition && mouseZ >= btn.yPosition && mouseX < btn.xPosition + btn.width && mouseZ < btn.yPosition + btn.height;
	    	 int id = btn.id;
	    	 if(id == 0)
	    	 {
	    		 if(hover)
	    		 {
	    			 List<String> catStr = new ArrayList<String>();
	    			 catStr.add(StatCollector.translateToLocal("ec3.text.button.back"));
	    			 this.func_146283_a(catStr, mouseX, mouseZ);
	    		 }
	    	 }
	     }
	     
	     this.drawPage_0(mouseX, mouseZ);
	     if(currentPage+1 < pagesMax)
	    	 this.drawPage_1(mouseX, mouseZ);
	 }
	 
	 public void drawPage_0(int mouseX, int mouseY)
	 {
		 PageEntry page = currentDiscovery.pages.get(currentPage);
	     int k = (this.width - 256) / 2;
	     int l = (this.height - 168) / 2;
	     if(currentPage == 0)
	     {
	    	 String added = "";
	    	 if(page.pageTitle == null || page.pageTitle.isEmpty())
	    	 {
	    		 if(currentDiscovery.name == null || currentDiscovery.name.isEmpty())
	    		 {
	    			 added = "\u00a7l"+StatCollector.translateToLocal("ec3book.discovery_"+currentDiscovery.id+".name");
	    		 }
	    		 else
	    			 added = currentDiscovery.name;
	    	 }else
	    	 {
	    		 added = page.pageTitle;
	    	 }
	    	 this.fontRendererObj.drawStringWithShadow(added, k+6, l+10, 0xaa88ff);
	     }else
	     {
	    	 if(page.pageTitle != null && !page.pageTitle.isEmpty())
	    	 {
	    		 this.fontRendererObj.drawStringWithShadow(page.pageTitle, k+6, l+10, 0xffffff);
	    	 }
	     }
	     
	     if(page.pageImgLink != null)
	     {
	    	 GL11.glColor3f(1, 1, 1);
	    	 GL11.glDisable(GL11.GL_LIGHTING);
	    	 this.mc.renderEngine.bindTexture(page.pageImgLink);
	    	 func_152125_a(k+16, l+10, 0, 0, 256, 256, 100, 100, 256, 256);
	    	 l += 86;
	     }
	     
	     if(page.displayedItems != null)
	     {
	    	 for(int i = 0; i < page.displayedItems.length; ++i)
	    	 {
	    		 ItemStack is = page.displayedItems[i];
	    		 if(is != null)
	    		 {
	    			 this.drawIS(is, k + 10 + (i%4*20), l + 10 + (i/4 * 20), mouseX, mouseY, 0);
	    		 }
	    	 }
	    	 
	    	 for(int i = 0; i < page.displayedItems.length; ++i)
	    	 {
	    		 ItemStack is = page.displayedItems[i];
	    		 if(is != null)
	    		 {
	    			 this.drawIS(is, k + 10 + (i%4*20), l + 10 + (i/4 * 20), mouseX, mouseY, 1);
	    		 }
	    	 }
	    	 
	    	 l += page.displayedItems.length/4 * 20;
	     }
	     if(page.pageRecipe != null)
	     {
		     if(page.displayedItems != null)
		     {
		    	 l += page.displayedItems.length/4 * 20;
		     }
	    	 l += this.drawRecipe(mouseX, mouseY, k, l, page.pageRecipe);
	     }
	     if(page.pageText != null && !page.pageText.isEmpty())
	     {
	    	 List<String> display = parse(page.pageText);
	    	 for(int i = 0; i < display.size(); ++i)
	    	 {
	    	     GL11.glColor3f(1, 1, 1);
	             GL11.glEnable(GL11.GL_LIGHTING);
	             GL11.glEnable(GL11.GL_DEPTH_TEST);
	             RenderHelper.enableStandardItemLighting();
	             GL11.glEnable(GL12.GL_RESCALE_NORMAL);
	             //TODO f
	             if(!Minecraft.getMinecraft().fontRenderer.getUnicodeFlag())
	             {
		             GL11.glScalef(0.8F, 0.8F, 0.8F);
		    		 this.fontRendererObj.drawString(display.get(i), (int) ((k+18)*1.25F), (int) ((l+25+i*8)*1.25F), 0x222222);
		    		 GL11.glScalef(1.25F, 1.25F, 1.25F);
	             }else
	             {
	            	 this.fontRendererObj.drawString(display.get(i), (int) ((k+18)), (int) ((l+25+i*8)), 0x222222);
	             }
	    	 }
	     }
	 }
	 
	 public void drawPage_1(int mouseX, int mouseY)
	 {
		 if(currentDiscovery.pages.size() > currentPage+1)
		 {
			 PageEntry page = currentDiscovery.pages.get(currentPage+1);
		     int k = (this.width - 256) / 2 + 128;
		     int l = (this.height - 168) / 2;
		     {
		    	 if(page.pageTitle != null && !page.pageTitle.isEmpty())
		    	 {
		    		 this.fontRendererObj.drawStringWithShadow(page.pageTitle, k+6, l+10, 0xffffff);
		    	 }
		     }
		     
		     if(page.pageImgLink != null)
		     {
		    	 GL11.glDisable(GL11.GL_LIGHTING);
		    	 GL11.glColor3f(1, 1, 1);
		    	 this.mc.renderEngine.bindTexture(page.pageImgLink);
		    	 func_152125_a(k+16, l+10, 0, 0, 256, 256, 100, 100, 256, 256);
		    	 l += 86;
		     }
		     
		     if(page.displayedItems != null)
		     {
		    	 for(int i = 0; i < page.displayedItems.length; ++i)
		    	 {
		    		 ItemStack is = page.displayedItems[i];
		    		 if(is != null)
		    		 {
		    			 this.drawIS(is, k + 10 + (i%4*20), l + 10 + (i/4 * 20), mouseX, mouseY, 0);
		    		 }
		    	 }
		    	 
		    	 for(int i = 0; i < page.displayedItems.length; ++i)
		    	 {
		    		 ItemStack is = page.displayedItems[i];
		    		 if(is != null)
		    		 {
		    			 this.drawIS(is, k + 10 + (i%4*20), l + 10 + (i/4 * 20), mouseX, mouseY, 1);
		    		 }
		    	 }
		    	 
		    	 l += page.displayedItems.length/4 * 20;
		     }
		     if(page.pageRecipe != null)
		     {
			     if(page.displayedItems != null)
			     {
			    	 l += page.displayedItems.length/4 * 20;
			     }
		    	 l += this.drawRecipe(mouseX, mouseY, k, l, page.pageRecipe);
		     }
		     if(page.pageText != null && !page.pageText.isEmpty())
		     {
		    	 List<String> display = parse(page.pageText);
		    	 for(int i = 0; i < display.size(); ++i)
		    	 {
		    	     GL11.glColor3f(1, 1, 1);
		             GL11.glEnable(GL11.GL_LIGHTING);
		             GL11.glEnable(GL11.GL_DEPTH_TEST);
		             RenderHelper.enableStandardItemLighting();
		             GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		             if(!Minecraft.getMinecraft().fontRenderer.getUnicodeFlag())
		             {
			             GL11.glScalef(0.8F, 0.8F, 0.8F);
			    		 this.fontRendererObj.drawString(display.get(i), (int) ((k+18)*1.25F), (int) ((l+25+i*8)*1.25F), 0x222222);
			    		 GL11.glScalef(1.25F, 1.25F, 1.25F);
		             }else
		             {
		            	 this.fontRendererObj.drawString(display.get(i), (int) ((k+18)), (int) ((l+25+i*8)), 0x222222);
		             }
		    	 }
		     }

		 }
	 }
	 
	 public int drawRecipe(int mouseX, int mouseZ, int k, int l, IRecipe toDraw)
	 {
		 
		 //2
		 if(toDraw instanceof ShapedOreRecipe)
		 {
			 return drawShapedOreRecipe(mouseX,mouseZ,k,l,(ShapedOreRecipe) toDraw);
		 }
		 //3
		 if(toDraw instanceof ShapelessOreRecipe)
		 {
			 return drawShapelessOreRecipe(mouseX,mouseZ,k,l,(ShapelessOreRecipe) toDraw);
		 }
		 //5
		 if(toDraw instanceof RadiatingChamberRecipe)
		 {
			 return drawRadiatingChamberRecipe(mouseX,mouseZ,k,l,(RadiatingChamberRecipe) toDraw);
		 }
		 //6
		 if(toDraw instanceof MagicianTableRecipe)
		 {
			 return drawMagicianTableRecipe(mouseX,mouseZ,k,l,(MagicianTableRecipe) toDraw);
		 }
		 //?7?
		 if(toDraw instanceof StructureRecipe)
		 {
			 return drawStructureRecipe(mouseX,mouseZ,k,l,(StructureRecipe) toDraw);
		 }
		 //8
		 if(toDraw instanceof ShapedAssemblerRecipe)
		 {
			 return drawShapedAssemblerRecipe(mouseX,mouseZ,k,l,(ShapedAssemblerRecipe) toDraw);
		 }
		 return 0;
	 }

	 public int drawMagicianTableRecipe(int mouseX, int mouseZ, int k, int l, MagicianTableRecipe toDraw)
	 {
		 this.fontRendererObj.drawString(StatCollector.translateToLocal("ec3.txt.magicianRecipe"), k+24, l+12, 0x222222);
		 this.fontRendererObj.drawString(StatCollector.translateToLocal("MRU Required: "+toDraw.mruRequired), k+26, l+83, 0x222222);
		
		 GL11.glDisable(GL11.GL_LIGHTING);
		 RenderHelper.disableStandardItemLighting();
		 RenderHelper.enableGUIStandardItemLighting();
		 GL11.glColor3f(1, 1, 1);
		 MiscUtils.bindTexture("essentialcraft", "textures/gui/mruStorage.png");
		 MiscUtils.drawTexturedModalRect(k+7, l+20, 0, 0, 18, 72,1);
		 int percentageScaled = MathUtils.pixelatedTextureSize((int) (toDraw.mruRequired), 5000, 72);
		 IIcon icon = (IIcon) EssentialCraftCore.proxy.getClientIcon("mru");
		 MiscUtils.drawTexture(k+8, l-1+(74-percentageScaled)+20, icon, 16, percentageScaled-2, 2);
		 
		 this.drawSlotInRecipe(k, l, 13, 8);
		 this.drawSlotInRecipe(k, l, 13+36, 8);
		 this.drawSlotInRecipe(k, l, 13, 8+36);
		 this.drawSlotInRecipe(k, l, 13+36, 8+36);
		 this.drawSlotInRecipe(k, l, 13+18, 8+18);
		 this.drawSlotInRecipe(k, l, 13+74, 8+18);
		 
		 if(toDraw.requiredItems[0]!=null)
			 this.drawIS(toDraw.requiredItems[0].getISToDraw(Minecraft.getMinecraft().theWorld.getWorldTime()), k+26+18, l+25+18, mouseX, mouseZ, 0);
		 if(toDraw.requiredItems[1]!=null)
			 this.drawIS(toDraw.requiredItems[1].getISToDraw(Minecraft.getMinecraft().theWorld.getWorldTime()), k+26, l+25, mouseX, mouseZ, 0);
		 if(toDraw.requiredItems[2]!=null)
		 	this.drawIS(toDraw.requiredItems[2].getISToDraw(Minecraft.getMinecraft().theWorld.getWorldTime()), k+26+36, l+25, mouseX, mouseZ, 0);
		 if(toDraw.requiredItems[3]!=null)
		 	this.drawIS(toDraw.requiredItems[3].getISToDraw(Minecraft.getMinecraft().theWorld.getWorldTime()), k+26, l+25+36, mouseX, mouseZ, 0);
		 if(toDraw.requiredItems[4]!=null)
			 this.drawIS(toDraw.requiredItems[4].getISToDraw(Minecraft.getMinecraft().theWorld.getWorldTime()), k+26+36, l+25+36, mouseX, mouseZ, 0);
		 
		 this.drawIS(toDraw.result, k+26+74, l+25+18, mouseX, mouseZ, 0);
		 
		 if(toDraw.requiredItems[0]!=null)
			 this.drawIS(toDraw.requiredItems[0].getISToDraw(Minecraft.getMinecraft().theWorld.getWorldTime()), k+26+18, l+25+18, mouseX, mouseZ, 1);
		 if(toDraw.requiredItems[1]!=null) 
			 this.drawIS(toDraw.requiredItems[1].getISToDraw(Minecraft.getMinecraft().theWorld.getWorldTime()), k+26, l+25, mouseX, mouseZ, 1);
		 if(toDraw.requiredItems[2]!=null)
			 this.drawIS(toDraw.requiredItems[2].getISToDraw(Minecraft.getMinecraft().theWorld.getWorldTime()), k+26+36, l+25, mouseX, mouseZ, 1);
		 if(toDraw.requiredItems[3]!=null)
			 this.drawIS(toDraw.requiredItems[3].getISToDraw(Minecraft.getMinecraft().theWorld.getWorldTime()), k+26, l+25+36, mouseX, mouseZ, 1);
		 if(toDraw.requiredItems[4]!=null)
			 this.drawIS(toDraw.requiredItems[4].getISToDraw(Minecraft.getMinecraft().theWorld.getWorldTime()), k+26+36, l+25+36, mouseX, mouseZ, 1);
		 
		 this.drawIS(toDraw.result, k+26+74, l+25+18, mouseX, mouseZ, 1);
		 return 80;
	 }
	 
	 public int drawRadiatingChamberRecipe(int mouseX, int mouseZ, int k, int l, RadiatingChamberRecipe toDraw)
	 {
		 this.fontRendererObj.drawString(StatCollector.translateToLocal("ec3.txt.radiatingRecipe"), k+8, l+12, 0x222222);
		 this.fontRendererObj.drawString(StatCollector.translateToLocal("MRU Required: "+toDraw.mruRequired), k+26, l+83, 0x222222);
		 EnumChatFormatting addeddCF = EnumChatFormatting.RESET;
		 float upperBalance = toDraw.upperBalanceLine;
		 if(upperBalance > 2.0F)upperBalance = 2.0F;
		 if(upperBalance > 1.0F)
			 addeddCF = EnumChatFormatting.RED;
		 else if(upperBalance < 1.0F)
			 addeddCF = EnumChatFormatting.BLUE;
		 else
			 addeddCF = EnumChatFormatting.AQUA;
		 String balanceUpper = Float.toString(upperBalance);
		 if(balanceUpper.length() > 4)
			 balanceUpper = balanceUpper.substring(0, 4);
		 this.fontRendererObj.drawString(StatCollector.translateToLocal(StatCollector.translateToLocal("ec3.txt.format.upperBalance")+addeddCF+balanceUpper), k+44, l+32, 0x222222);

		 float lowerBalance = toDraw.lowerBalanceLine;
		 if(lowerBalance < 0.001F)lowerBalance = 0.001F;
		 if(lowerBalance > 1.0F)
			 addeddCF = EnumChatFormatting.RED;
		 else if(lowerBalance < 1.0F)
			 addeddCF = EnumChatFormatting.BLUE;
		 else
			 addeddCF = EnumChatFormatting.AQUA;
		 String balanceLower = Float.toString(lowerBalance);
		 if(balanceLower.length() > 4)
			 balanceLower = balanceLower.substring(0, 4);
		 this.fontRendererObj.drawString(StatCollector.translateToLocal(StatCollector.translateToLocal("ec3.txt.format.lowerBalance")+addeddCF+balanceLower), k+44, l+32+36, 0x222222);

		 this.fontRendererObj.drawString("MRU/t "+(int)toDraw.costModifier, k+44+18, l+32+18, 0x222222);
		 
		 GL11.glDisable(GL11.GL_LIGHTING);
		 RenderHelper.disableStandardItemLighting();
		 RenderHelper.enableGUIStandardItemLighting();
		 GL11.glColor3f(1, 1, 1);
		 MiscUtils.bindTexture("essentialcraft", "textures/gui/mruStorage.png");
		 MiscUtils.drawTexturedModalRect(k+7, l+20, 0, 0, 18, 72,1);
		 int percentageScaled = MathUtils.pixelatedTextureSize((int) (toDraw.mruRequired*toDraw.costModifier), 5000, 72);
		 IIcon icon = (IIcon) EssentialCraftCore.proxy.getClientIcon("mru");
		 MiscUtils.drawTexture(k+8, l-1+(74-percentageScaled)+20, icon, 16, percentageScaled-2, 2);
		 int positionY = 8;
		 this.drawSlotInRecipe(k, l, 13, 4+positionY);
		 this.drawSlotInRecipe(k, l, 13+18, 22+positionY);
		 this.drawSlotInRecipe(k, l, 13, 40+positionY);
		 
		 this.drawIS(toDraw.recipeItems[0], k+26, l+21+positionY, mouseX, mouseZ, 0);
		 this.drawIS(toDraw.recipeItems[1], k+26, l+21+36+positionY, mouseX, mouseZ, 0);
		 this.drawIS(toDraw.result, k+26+18, l+21+18+positionY, mouseX, mouseZ, 0);
		 
		 this.drawIS(toDraw.recipeItems[0], k+26, l+21+positionY, mouseX, mouseZ, 1);
		 this.drawIS(toDraw.recipeItems[1], k+26, l+21+36+positionY, mouseX, mouseZ, 1);
		 this.drawIS(toDraw.result, k+26+18, l+21+18+positionY, mouseX, mouseZ, 1);
		 return 90;
	 }
	 
	 public int drawStructureRecipe(int mouseX, int mouseZ, int k, int l, StructureRecipe toDraw)
	 {
		 try
		 {
		 this.fontRendererObj.drawString(StatCollector.translateToLocal("ec3.txt.structure"), k+8, l+12, 0x222222);
		 l += 5;
		 StructureRecipe recipe = (StructureRecipe) toDraw;
		 int highestStructureBlk = 0;
		 for(StructureBlock blk : recipe.structure)
		 {
			 if(blk.y > highestStructureBlk)
				 highestStructureBlk = blk.y;
		 }
		 for(StructureBlock blk : recipe.structure)
		 {
			 if(!Config.renderStructuresFromAbove)
				 this.drawSB(blk, k+52+blk.x*10-blk.z*10, l+32+highestStructureBlk*20-blk.y*20+blk.z*10+blk.x*10, mouseX, mouseZ, 0);
			 else
				 this.drawSB(blk, k+52+blk.x*10, l+32+highestStructureBlk*20+blk.z*10, mouseX, mouseZ, 0);
		 }
		 this.drawIS(recipe.referal, k+52, l+144, mouseX, mouseZ, 0);
		 
		 for(StructureBlock blk : recipe.structure)
		 {
			 if(!Config.renderStructuresFromAbove)
				 this.drawSB(blk, k+52+blk.x*10-blk.z*10, l+32+highestStructureBlk*20-blk.y*20+blk.z*10+blk.x*10, mouseX, mouseZ, 1);
			 else
				 this.drawSB(blk, k+52+blk.x*10, l+32+highestStructureBlk*20+blk.z*10, mouseX, mouseZ, 1);
		 }
		 
		 this.drawIS(recipe.referal, k+52, l+144, mouseX, mouseZ, 1);
		 
		 return 60+highestStructureBlk*20;
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
			 return 0;
		 }
	 }
	 
	 @SuppressWarnings("unchecked")
	public int drawShapedAssemblerRecipe(int mouseX, int mouseZ, int k, int l, ShapedAssemblerRecipe toDraw)
	 {
		 this.fontRendererObj.drawString(StatCollector.translateToLocal("ec3.txt.assemblerRecipe"), k+8, l+12, 0x222222);
		 ShapedAssemblerRecipe recipe = (ShapedAssemblerRecipe) toDraw;
		 
		 RenderHelper.disableStandardItemLighting();
		 RenderHelper.enableGUIStandardItemLighting();
		 GL11.glColor3f(1, 1, 1);
		 this.fontRendererObj.drawString(StatCollector.translateToLocal("MRU Required: "+toDraw.mruRequired), k+16, l+83, 0x222222);
		 
		 for(int i = 0; i < 9; ++i)
		 {
			 drawSlotInRecipe(k,l+6,(i%3)*18,(i/3)*18);
		 }
		 drawSlotInRecipe(k,l+6,80,(1)*18);
		 MiscUtils.bindTexture("minecraft", "textures/gui/container/crafting_table.png");
		 
		 GL11.glColor3f(1, 1, 1);
		 this.drawTexturedModalRect(k+78-10, l+23+18, 90, 35, 22, 15);
		 
		 Random rnd = new Random(mc.theWorld.getWorldTime()/20);
		 
		 int[] drawingID = new int[9];
		 
		 for(int i = 0; i < 9; ++i)
		 {
			 Object drawable = recipe.getInput()[i];
			 ItemStack needToDraw = null;
			 if(drawable instanceof ItemStack)
				 needToDraw = (ItemStack) drawable;
			 if(drawable instanceof Item)
				 needToDraw = new ItemStack((Item)drawable);
			 if(drawable instanceof Block)
				 needToDraw = new ItemStack((Block)drawable);
			 if(drawable instanceof ItemStack[])
			 {
				 drawingID[i] = rnd.nextInt(((ItemStack[])drawable).length);
				 needToDraw = ((ItemStack[])drawable)[drawingID[i]];
			 }
			 if(drawable instanceof String)
			 {
				 List<ItemStack> oreStacks = OreDictionary.getOres((String) drawable);
				 drawingID[i] = rnd.nextInt(oreStacks.size());
				 needToDraw = oreStacks.get(drawingID[i]);
			 }
			 if(drawable instanceof List)
			 {
				 List<ItemStack> oreStacks = (List<ItemStack>) drawable;
				 drawingID[i] = rnd.nextInt(oreStacks.size());
				 needToDraw = oreStacks.get(drawingID[i]);
			 }
			 if(needToDraw != null)
			 {
				 this.drawIS(needToDraw, k + 13 + (i%3*18), l + 23 + (i/3 * 18), mouseX, mouseZ, 0);
			 }
		 }
		 
		 this.drawIS(recipe.getRecipeOutput(), k + 93, l + 41, mouseX, mouseZ, 0);
		 
		 for(int i = 0; i < 9; ++i)
		 {
			 Object drawable = recipe.getInput()[i];
			 ItemStack needToDraw = null;
			 if(drawable instanceof ItemStack)
				 needToDraw = (ItemStack) drawable;
			 if(drawable instanceof Item)
				 needToDraw = new ItemStack((Item)drawable);
			 if(drawable instanceof Block)
				 needToDraw = new ItemStack((Block)drawable);
			 if(drawable instanceof ItemStack[])
				 needToDraw = ((ItemStack[])drawable)[drawingID[i]];
			 if(drawable instanceof String)
			 {
				 List<ItemStack> oreStacks = OreDictionary.getOres((String) drawable);
				 needToDraw = oreStacks.get(drawingID[i]);
			 }
			 if(drawable instanceof List)
			 {
				 List<ItemStack> oreStacks = (List<ItemStack>) drawable;
				 needToDraw = oreStacks.get(drawingID[i]);
			 }
			 if(needToDraw != null)
			 {
				 this.drawIS(needToDraw, k + 13 + (i%3*18), l + 23 + (i/3 * 18), mouseX, mouseZ, 1);
			 }
		 }
		 
		 this.drawIS(recipe.getRecipeOutput(), k + 93, l + 41, mouseX, mouseZ, 1);
		 
		 
		 rnd = null;
		 
		 return 80;
	 }
	 
	 @SuppressWarnings("unchecked")
	public int drawShapedOreRecipe(int mouseX, int mouseZ, int k, int l, ShapedOreRecipe toDraw)
	 {
		 this.fontRendererObj.drawString(StatCollector.translateToLocal("ec3.txt.shapedRecipe"), k+8, l+12, 0x222222);
		 ShapedOreRecipe recipe = (ShapedOreRecipe) toDraw;
		 for(int i = 0; i < 9; ++i)
		 {
			 drawSlotInRecipe(k,l+6,(i%3)*18,(i/3)*18);
		 }
		 drawSlotInRecipe(k,l+6,80,(1)*18);
		 MiscUtils.bindTexture("minecraft", "textures/gui/container/crafting_table.png");
		 
		 GL11.glColor3f(1, 1, 1);
		 this.drawTexturedModalRect(k+78-10, l+23+18, 90, 35, 22, 15);
		 
		 Random rnd = new Random(mc.theWorld.getWorldTime()/20);
		 
		 int[] drawingID = new int[9];
		 
		 for(int i = 0; i < recipe.getRecipeSize(); ++i)
		 {
			 Object drawable = recipe.getInput()[i];
			 ItemStack needToDraw = null;
			 if(drawable instanceof ItemStack)
				 needToDraw = (ItemStack) drawable;
			 if(drawable instanceof Item)
				 needToDraw = new ItemStack((Item)drawable);
			 if(drawable instanceof Block)
				 needToDraw = new ItemStack((Block)drawable);
			 if(drawable instanceof ItemStack[])
			 {
				 drawingID[i] = rnd.nextInt(((ItemStack[])drawable).length);
				 needToDraw = ((ItemStack[])drawable)[drawingID[i]];
			 }
			 if(drawable instanceof String)
			 {
				 List<ItemStack> oreStacks = OreDictionary.getOres((String) drawable);
				 drawingID[i] = rnd.nextInt(oreStacks.size());
				 needToDraw = oreStacks.get(drawingID[i]);
			 }
			 if(drawable instanceof List)
			 {
				 List<ItemStack> oreStacks = (List<ItemStack>) drawable;
				 drawingID[i] = rnd.nextInt(oreStacks.size());
				 needToDraw = oreStacks.get(drawingID[i]);
			 }
			 if(needToDraw != null)
			 {
				 this.drawIS(needToDraw, k + 13 + (i%3*18), l + 23 + (i/3 * 18), mouseX, mouseZ, 0);
			 }
		 }
		 
		 this.drawIS(recipe.getRecipeOutput(), k + 93, l + 41, mouseX, mouseZ, 0);
		 
		 for(int i = 0; i < recipe.getRecipeSize(); ++i)
		 {
			 Object drawable = recipe.getInput()[i];
			 ItemStack needToDraw = null;
			 if(drawable instanceof ItemStack)
				 needToDraw = (ItemStack) drawable;
			 if(drawable instanceof Item)
				 needToDraw = new ItemStack((Item)drawable);
			 if(drawable instanceof Block)
				 needToDraw = new ItemStack((Block)drawable);
			 if(drawable instanceof ItemStack[])
				 needToDraw = ((ItemStack[])drawable)[drawingID[i]];
			 if(drawable instanceof String)
			 {
				 List<ItemStack> oreStacks = OreDictionary.getOres((String) drawable);
				 needToDraw = oreStacks.get(drawingID[i]);
			 }
			 if(drawable instanceof List)
			 {
				 List<ItemStack> oreStacks = (List<ItemStack>) drawable;
				 needToDraw = oreStacks.get(drawingID[i]);
			 }
			 if(needToDraw != null)
			 {
				 this.drawIS(needToDraw, k + 13 + (i%3*18), l + 23 + (i/3 * 18), mouseX, mouseZ, 1);
			 }
		 }
		 
		 this.drawIS(recipe.getRecipeOutput(), k + 93, l + 41, mouseX, mouseZ, 1);
		 
		 
		 rnd = null;
		 
		 return (2)*18 + 20;
	 }
	 
	 @SuppressWarnings("unchecked")
	public int drawShapelessOreRecipe(int mouseX, int mouseZ, int k, int l, ShapelessOreRecipe toDraw)
	 {
		 this.fontRendererObj.drawString(StatCollector.translateToLocal("ec3.txt.shapelessRecipe"), k+8, l+12, 0x222222);
		 l += 5;
		 ArrayList<Object> input = ((ShapelessOreRecipe)toDraw).getInput();
		 for(int i = 0; i < input.size(); ++i)
		 {
			 drawSlotInRecipe(k,l,(i%3)*18,(i/3)*18);
		 }
		 int defaultX = 80;
		 int defaultY = (input.size()/3)*18;
		 
		 int defaultYDraw = defaultY;
		 if(defaultYDraw > 1*18) defaultYDraw = 1*18;

		 drawSlotInRecipe(k,l,defaultX,defaultYDraw);
		 
		 MiscUtils.bindTexture("minecraft", "textures/gui/container/crafting_table.png");
		 
		 GL11.glColor3f(1, 1, 1);
		 this.drawTexturedModalRect(k+defaultX-10, l+defaultYDraw+18, 90, 35, 22, 15);
		 
		 Random rnd = new Random(mc.theWorld.getWorldTime()/20);
		 
		 int[] drawingID = new int[9];
		 
		 for(int i = 0; i < input.size(); ++i)
		 {
			 Object drawable = input.get(i);
			 ItemStack needToDraw = null;
			 if(drawable instanceof ItemStack)
				 needToDraw = (ItemStack) drawable;
			 if(drawable instanceof Item)
				 needToDraw = new ItemStack((Item)drawable);
			 if(drawable instanceof Block)
				 needToDraw = new ItemStack((Block)drawable);
			 if(drawable instanceof ItemStack[])
			 {
				 drawingID[i] = rnd.nextInt(((ItemStack[])drawable).length);
				 needToDraw = ((ItemStack[])drawable)[drawingID[i]];
			 }
			 if(drawable instanceof String)
			 {
				 List<ItemStack> oreStacks = OreDictionary.getOres((String) drawable);
				 drawingID[i] = rnd.nextInt(oreStacks.size());
				 needToDraw = oreStacks.get(drawingID[i]);
			 }
			 if(drawable instanceof List)
			 {
				 List<ItemStack> oreStacks = (List<ItemStack>) drawable;
				 drawingID[i] = rnd.nextInt(oreStacks.size());
				 needToDraw = oreStacks.get(drawingID[i]);
			 }
			 if(needToDraw != null)
			 {
				 this.drawIS(needToDraw, k + 13 + (i%3*18), l + 17 + (i/3 * 18), mouseX, mouseZ, 0);
			 }
		 }
		 
		 this.drawIS(toDraw.getRecipeOutput(), k + defaultX + 13, l + defaultYDraw + 17, mouseX, mouseZ, 0);
		 
		 for(int i = 0; i < input.size(); ++i)
		 {
			 Object drawable = input.get(i);
			 ItemStack needToDraw = null;
			 if(drawable instanceof ItemStack)
				 needToDraw = (ItemStack) drawable;
			 if(drawable instanceof Item)
				 needToDraw = new ItemStack((Item)drawable);
			 if(drawable instanceof Block)
				 needToDraw = new ItemStack((Block)drawable);
			 if(drawable instanceof ItemStack[])
				 needToDraw = ((ItemStack[])drawable)[drawingID[i]];
			 if(drawable instanceof String)
			 {
				 List<ItemStack> oreStacks = OreDictionary.getOres((String) drawable);
				 needToDraw = oreStacks.get(drawingID[i]);
			 }
			 if(drawable instanceof List)
			 {
				 List<ItemStack> oreStacks = (List<ItemStack>) drawable;
				 needToDraw = oreStacks.get(drawingID[i]);
			 }
			 if(needToDraw != null)
			 {
				 this.drawIS(needToDraw, k + 13 + (i%3*18), l + 17 + (i/3 * 18), mouseX, mouseZ, 1);
			 }
			 
			 this.drawIS(toDraw.getRecipeOutput(), k + defaultX + 13, l + defaultYDraw + 17, mouseX, mouseZ, 1);
		 }
		 
		 
		 
		 rnd = null;
		 
		 return (input.size()/3)*18 + 20;
	 }
	 
	 public void drawSlotInRecipe(int k, int l, int defaultX, int defaultY)
	 {
	     GL11.glColor3f(1, 1, 1);
         GL11.glEnable(GL11.GL_LIGHTING);
         GL11.glEnable(GL11.GL_DEPTH_TEST);
         RenderHelper.enableStandardItemLighting();
         GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		 this.drawGradientRect(k+12+defaultX, l+16+defaultY, k+12+18+defaultX, l+16+18+defaultY, 0x88ffaaff, 0x88886688);
		 this.drawGradientRect(k+12+defaultX, l+16+defaultY, k+12+1+defaultX, l+16+18+defaultY, 0xff660066, 0xff330033);
		 this.drawGradientRect(k+12+defaultX, l+16+17+defaultY, k+12+18+defaultX, l+16+18+defaultY, 0xff330033, 0xff110011);
		 this.drawGradientRect(k+12+17+defaultX, l+16+defaultY, k+12+18+defaultX, l+16+18+defaultY, 0xff990099, 0xff110011);
		 this.drawGradientRect(k+12+defaultX, l+16+defaultY, k+12+18+defaultX, l+16+1+defaultY, 0xff660066, 0xff990099);
	 }
	 
	 public List<String> parse(String s)
	 {
		 List<String> rtLst = new ArrayList<String>();
		 int maxSymbols = 24;
		 int cycle = 1;
		 String added = "";
		 for(int i = 0; i < s.length(); ++i)
		 {
			 if(i+1 < s.length())
			 {
				 String substr = s.substring(i,i+1);
				 if(substr.equals("|"))
				 {
					 rtLst.add(added);
					 rtLst.add("");
					 added = "";
					 ++i;
				 }
				 added += s.substring(i, i+1);
			 }else
			 {
				 added += s.substring(s.length()-1);
			 }
			 //System.out.println(added+"|"+added.length()+"|"+(added.length() + (maxSymbols*(cycle-1)))+"|"+s.length()+"|"+i);
			 if(added.length() > maxSymbols || added.length() + (maxSymbols*(cycle-1)) == s.length() || i == s.length()-1)
			 {
				 int index = added.lastIndexOf(" ");
				 if(index == -1) index = 24;
				 if(index >= added.length()) index = added.length()-1;
				 if(i == s.length()-1)index = added.length()-1;
				 String add = added.substring(0, index+1);
				 rtLst.add(add);
				 String nxtCycle = added.substring(index+1);
				 added = ""+nxtCycle;
				 ++cycle;
			 }
		 }
		 return rtLst;
	 }
	 
	 public void drawDiscoveries(int mouseX, int mouseZ)
	 {
	     int k = (this.width - 256) / 2;
	     int l = (this.height - 168) / 2;
		 this.fontRendererObj.drawStringWithShadow("\u00a76 \u00a7l" + StatCollector.translateToLocal("ec3.txt.book.startup"), k+6, l+10, 0xffffff);
		 CategoryEntry cat = currentCategory;
		 if(cat.name != null && !cat.name.isEmpty())
			 this.fontRendererObj.drawStringWithShadow("\u00a76 " + cat.name, k+6+128, l+10, 0xffffff);
		 else
			 this.fontRendererObj.drawStringWithShadow("\u00a76 " + StatCollector.translateToLocal("ec3book.category_"+currentCategory.id+".name"), k+6+128, l+10, 0xffffff);
	     for (int ik = 0; ik < this.buttonList.size(); ++ik)
	     {
	    	 GL11.glColor3f(1, 1, 1);
	    	 GuiButton btn  = (GuiButton) this.buttonList.get(ik);
	    	 boolean hover = mouseX >= btn.xPosition && mouseZ >= btn.yPosition && mouseX < btn.xPosition + btn.width && mouseZ < btn.yPosition + btn.height;
	    	 int id = btn.id;
	    	 if(id == 0)
	    	 {
	    		 if(currentCategory == null || currentCategory.specificBookTextures == null)
	    			 this.mc.renderEngine.bindTexture(gui);
	    		 else
	    			 this.mc.renderEngine.bindTexture(currentCategory.specificBookTextures);
	    		 if(hover)
	    			 GL11.glColor3f(1, 0.8F, 1);
	    		 this.drawTexturedModalRect(btn.xPosition, btn.yPosition, 49, 238, 14, 18);
	    	 }
	    	 if(id == 1)
	    	 {
	    		 if(currentCategory == null || currentCategory.specificBookTextures == null)
	    			 this.mc.renderEngine.bindTexture(gui);
	    		 else
	    			 this.mc.renderEngine.bindTexture(currentCategory.specificBookTextures);
	    		 if(hover && btn.enabled)
	    			 GL11.glColor3f(1, 0.8F, 1);
	    		 if(!btn.enabled)
	    			 GL11.glColor3f(0.3F, 0.3F, 0.3F);
	    		 this.drawTexturedModalRect(btn.xPosition, btn.yPosition, 0, 243, 24, 13);
	    	 }
	    	 if(id == 2)
	    	 {
	    		 if(currentCategory == null || currentCategory.specificBookTextures == null)
	    			 this.mc.renderEngine.bindTexture(gui);
	    		 else
	    			 this.mc.renderEngine.bindTexture(currentCategory.specificBookTextures);
	    		 if(hover && btn.enabled)
	    			 GL11.glColor3f(1, 0.8F, 1);
	    		 if(!btn.enabled)
	    			 GL11.glColor3f(0.3F, 0.3F, 0.3F);
	    		 this.drawTexturedModalRect(btn.xPosition, btn.yPosition, 25, 243, 24, 13);
	    	 }
	    	 if(id > 2)
	    	 {
	    		 DiscoveryEntry disc = cat.discoveries.get((48*currentPage_discovery) + id - 3);
	    	     GL11.glColor3f(1, 1, 1);
	    		 RenderHelper.disableStandardItemLighting();
	    		 RenderHelper.enableGUIStandardItemLighting();
	    		 
	    		 if(currentCategory == null || currentCategory.specificBookTextures == null)
	    			 this.mc.renderEngine.bindTexture(gui);
	    		 else
	    			 this.mc.renderEngine.bindTexture(currentCategory.specificBookTextures);
	    		 if(disc.isNew)
	    			 GL11.glColor3f(1, 1, 0);
	    		 if(!hover)
	    			 this.drawTexturedModalRect(btn.xPosition, btn.yPosition, 0, 222, 20, 20);
	    		 else
	    			 this.drawTexturedModalRect(btn.xPosition, btn.yPosition, 28, 222, 20, 20); 
	    		 GL11.glColor3f(1, 1, 1);
	    		 if(disc.displayStack != null)
	    		 {
	    			 GL11.glPushMatrix();
	    			 GL11.glDisable(GL11.GL_LIGHTING);

	    			 itemRender.renderItemAndEffectIntoGUI(fontRendererObj, this.mc.renderEngine, disc.displayStack, btn.xPosition+2, btn.yPosition+2);
	    			 
	    			 GL11.glPopMatrix();
	    		 }
	    		 else if(disc.displayTexture != null)
	    		 {
	    			 RenderHelper.enableStandardItemLighting();
	    			 
	    			 GL11.glEnable(GL11.GL_BLEND);
	    			 
	    			 this.mc.renderEngine.bindTexture(disc.displayTexture);
	    		     Tessellator tec = Tessellator.instance;
	    		     tec.startDrawingQuads();
	    		     
	    		     tec.addVertexWithUV(btn.xPosition+2, btn.yPosition+2, 0, 0, 0);
	    		     tec.addVertexWithUV(btn.xPosition+2, btn.yPosition+2+16, 0, 0, 1);
	    		     tec.addVertexWithUV(btn.xPosition+2+16, btn.yPosition+2+16, 0, 1, 1);
	    		     tec.addVertexWithUV(btn.xPosition+2+16, btn.yPosition+2, 0, 1, 0);
	    		     
	    		     tec.draw();
	    		     
	    		     GL11.glDisable(GL11.GL_BLEND);
	    			
		    		 RenderHelper.disableStandardItemLighting();
		    		 RenderHelper.enableGUIStandardItemLighting();
	    		 }
	    		 
	    		 RenderHelper.enableStandardItemLighting();
	    	 }
	     }
	     //Text Draw
	     for (int ik = 0; ik < this.buttonList.size(); ++ik)
	     {
	    	 GL11.glColor3f(1, 1, 1);
	    	 GuiButton btn  = (GuiButton) this.buttonList.get(ik);
	    	 boolean hover = mouseX >= btn.xPosition && mouseZ >= btn.yPosition && mouseX < btn.xPosition + btn.width && mouseZ < btn.yPosition + btn.height;
	    	 int id = btn.id;
	    	 if(id == 0)
	    	 {
	    		 if(hover)
	    		 {
	    			 List<String> catStr = new ArrayList<String>();
	    			 catStr.add(StatCollector.translateToLocal("ec3.text.button.back"));
	    			 this.func_146283_a(catStr, mouseX, mouseZ);
	    		 }
	    	 }
	    	 if(id > 2)
	    	 {
	    		 if(hover)
	    		 {
		    		 DiscoveryEntry disc = cat.discoveries.get((48*currentPage_discovery) + id - 3);
		    	     GL11.glColor3f(1, 1, 1);
	    			 List<String> discStr = new ArrayList<String>();
	    			 if(disc.name == null || disc.name.isEmpty())
	    				 discStr.add("\u00a7l"+StatCollector.translateToLocal("ec3book.discovery_"+disc.id+".name"));
	    			 else
	    				 discStr.add(disc.name);
	    			 if(disc.shortDescription == null || disc.shortDescription.isEmpty())
	    				 discStr.add("\u00a7o"+StatCollector.translateToLocal("ec3book.discovery_"+disc.id+".desc"));
	    			 else
	    				 discStr.add(disc.shortDescription);
	    			 if(disc.isNew)
	    				 discStr.add("\u00a76"+"New");
	    			 discStr.add(StatCollector.translateToLocal("ec3.txt.contains")+disc.pages.size()+StatCollector.translateToLocal("ec3.txt.pages"));
	    			 this.func_146283_a(discStr, mouseX, mouseZ);
	    		 }
	    	 }
	     }
	 }
	 
	 public void drawCategories(int mouseX, int mouseZ)
	 {
	     int k = (this.width - 256) / 2;
	     int l = (this.height - 168) / 2;
		 this.fontRendererObj.drawStringWithShadow("\u00a76 \u00a7l" + StatCollector.translateToLocal("ec3.txt.book.startup"), k+6, l+10, 0xffffff);
		 this.fontRendererObj.drawStringWithShadow("\u00a76 \u00a7l" + StatCollector.translateToLocal("ec3.txt.book.categories"), k+134, l+10, 0xffffff);
		 this.fontRendererObj.drawStringWithShadow("\u00a76" + StatCollector.translateToLocal("ec3.txt.book.containedKnowledge"), k+16, l+25, 0xffffff);
		 int tier = bookTag.getInteger("tier");
		 for(int i = 0; i <= tier; ++i)
		 {
			 this.fontRendererObj.drawStringWithShadow("\u00a77-\u00a7o" + StatCollector.translateToLocal("ec3.txt.book.tier_"+i), k+16, l+35+(i*10), 0xffffff);
		 }
		 this.fontRendererObj.drawStringWithShadow("\u00a76" + StatCollector.translateToLocal("ec3.txt.book.edition"), k+16, l+90, 0xffffff);
		 this.fontRendererObj.drawString("\u00a72" + FMLCommonHandler.instance().findContainerFor(EssentialCraftCore.core).getDisplayVersion()+"r", k+16, l+100, 0xffffff);
		 k += 128;
	     this.mc.renderEngine.bindTexture(gui);
	     GL11.glColor3f(1, 1, 1);
		 RenderHelper.disableStandardItemLighting();
		 RenderHelper.enableGUIStandardItemLighting();
	     for (int ik = 0; ik < this.buttonList.size(); ++ik)
	     {
	    	 GuiButton btn  = (GuiButton) this.buttonList.get(ik);
	    	 boolean hover = mouseX >= btn.xPosition && mouseZ >= btn.yPosition && mouseX < btn.xPosition + btn.width && mouseZ < btn.yPosition + btn.height;
	    	 CategoryEntry cat = ApiCore.categories.get(ik);
	    	 int reqTier = cat.reqTier;
	    	 if(tier >= reqTier)
	    	 {
	    		 this.mc.renderEngine.bindTexture(gui);
	    		 if(!hover)
	    			 this.drawTexturedModalRect(btn.xPosition, btn.yPosition, 0, 222, 20, 20);
	    		 else
	    			 this.drawTexturedModalRect(btn.xPosition, btn.yPosition, 28, 222, 20, 20); 
	    		 if(cat.displayStack != null)
	    		 {
	    			 GL11.glPushMatrix();
	    			 GL11.glDisable(GL11.GL_LIGHTING);

	    			 itemRender.renderItemAndEffectIntoGUI(fontRendererObj, this.mc.renderEngine, cat.displayStack, btn.xPosition+2, btn.yPosition+2);
	    			 
	    			 GL11.glPopMatrix();
	    		 }
	    		 else if(cat.displayTexture != null)
	    		 {
	    			 this.mc.renderEngine.bindTexture(cat.displayTexture);
	    			 func_146110_a(btn.xPosition+2, btn.yPosition+2, 0, 0, 16, 16, 16, 16);
	    		 }
	    	 }
	     }
	     RenderHelper.enableStandardItemLighting();
	     //Text Overlay
	     for (int ik = 0; ik < this.buttonList.size(); ++ik)
	     {
	    	 GuiButton btn  = (GuiButton) this.buttonList.get(ik);
	    	 boolean hover = mouseX >= btn.xPosition && mouseZ >= btn.yPosition && mouseX < btn.xPosition + btn.width && mouseZ < btn.yPosition + btn.height;
	    	 CategoryEntry cat = ApiCore.categories.get(ik);
	    	 int reqTier = cat.reqTier;
	    	 if(tier >= reqTier)
	    	 {
	    		 if(hover)
	    		 {
	    			 List<String> catStr = new ArrayList<String>();
	    			 if(cat.name == null || cat.name.isEmpty())
	    				 catStr.add("\u00a7l"+StatCollector.translateToLocal("ec3book.category_"+cat.id+".name"));
	    			 else
	    				 catStr.add(cat.name);
	    			 if(cat.shortDescription == null || cat.shortDescription.isEmpty())
	    				 catStr.add("\u00a7o"+StatCollector.translateToLocal("ec3book.category_"+cat.id+".desc"));
	    			 else
	    				 catStr.add(cat.shortDescription);
	    			 catStr.add(StatCollector.translateToLocal("ec3.txt.contains")+cat.discoveries.size()+StatCollector.translateToLocal("ec3.txt.entries"));
	    			 this.func_146283_a(catStr, mouseX, mouseZ);
	    		 }
	    	 }
	     }
	 }
	 
	 @SuppressWarnings("unchecked")
	public void drawIS(ItemStack toDraw, int pX, int pZ, int mX, int mZ, int phase)
	 {
		 if(toDraw != null)
		 {
			 if(phase == 0)
			 {
				 itemRender.renderItemAndEffectIntoGUI(fontRendererObj, this.mc.renderEngine, toDraw, pX, pZ);
				 if(toDraw.stackSize > 1)
				 {
					 GL11.glTranslatef(0, 0, 500);
					 fontRendererObj.drawString(toDraw.stackSize+"", pX+10, pZ+10, 0x000000);
					 GL11.glTranslatef(0, 0, -500);
				 }
			 }else
			 {
				 boolean hover = mX >= pX && mZ >= pZ && mX < pX + 16 && mZ < pZ + 16;
	    		 if(hover)
	    		 {
	    			  List<String> catStr = toDraw.getTooltip(this.mc.thePlayer, false);
	    			  if(ApiCore.findDiscoveryByIS(toDraw) != null)
	    			  {
	    				  catStr.add(EnumChatFormatting.ITALIC + StatCollector.translateToLocal("ec3.txt.is.press"));
	    				  if(Mouse.isButtonDown(0) && !isLeftMouseKeyPressed)
	    				  {
	    					  prevState.add(new Object[]{currentDiscovery,currentPage,currentPage_discovery});
	    					  isLeftMouseKeyPressed = true;
	    					  DiscoveryEntry switchTo = ApiCore.findDiscoveryByIS(toDraw);
	    					  currentPage = 0;
	    					  currentPage_discovery = 0;
	    					  currentDiscovery = switchTo;
	    					  if(switchTo != null)
	    					  {
	    						  f:for(int i = 0; i < switchTo.pages.size(); ++i)
	    						  {
	    							  PageEntry entry = switchTo.pages.get(i);
	    							  if(entry != null)
	    							  {
	    								  if(entry.displayedItems != null && entry.displayedItems.length > 0)
	    								  {
	    									  for(ItemStack is : entry.displayedItems)
	    									  {
	    										  if(toDraw.isItemEqual(is))
	    										  {
	    											  currentPage = i - i%2;
	    											  break f;
	    										  }
	    									  }
	    								  }
	    								  if(entry.pageRecipe != null)
	    								  {
	    									  ItemStack result = entry.pageRecipe.getRecipeOutput();
	    									  if(result.isItemEqual(toDraw))
	    									  {
	   											  currentPage = i - i%2;
												  break f;
	    									  }
	    								  }
	    							  }
	    						  }
	    					  }
	    					  initGui();
	    				  }
	    			  }
	    			  this.func_146283_a(catStr, mX, mZ);
	    		 }
			 }
		 }
	 }
	 
	 @SuppressWarnings("unchecked")
	public void drawSB(StructureBlock drawable, int pX, int pZ, int mX, int mZ, int phase)
	 {
		 ItemStack toDraw = new ItemStack(drawable.blk,0,drawable.metadata);
		 if(phase == 0)
		 {
			 itemRender.renderItemAndEffectIntoGUI(fontRendererObj, this.mc.renderEngine, toDraw, pX, pZ);
		 }else
		 {
			 boolean hover = mX >= pX && mZ >= pZ && mX < pX + 16 && mZ < pZ + 16;
    		 if(hover)
    		 {
    			  List<String> catStr = toDraw.getTooltip(this.mc.thePlayer, false);
    			  catStr.add(StatCollector.translateToLocal("ec3.txt.relativePosition"));
    			  catStr.add("x: "+drawable.x);
    			  catStr.add("y: "+drawable.y);
    			  catStr.add("z: "+drawable.z);
    			  if(ApiCore.findDiscoveryByIS(toDraw) != null)
    			  {
    				  catStr.add(EnumChatFormatting.ITALIC + StatCollector.translateToLocal("ec3.txt.is.press"));
    				  if(Mouse.isButtonDown(0) && !this.isLeftMouseKeyPressed)
    				  {
    					  prevState.add(new Object[]{currentDiscovery,currentPage,currentPage_discovery});
    					  isLeftMouseKeyPressed = true;
    					  DiscoveryEntry switchTo = ApiCore.findDiscoveryByIS(toDraw);
    					  currentPage = 0;
    					  currentPage_discovery = 0;
    					  currentDiscovery = switchTo;
    					  if(switchTo != null)
    					  {
    						  f:for(int i = 0; i < switchTo.pages.size(); ++i)
    						  {
    							  PageEntry entry = switchTo.pages.get(i);
    							  if(entry != null)
    							  {
    								  if(entry.displayedItems != null && entry.displayedItems.length > 0)
    								  {
    									  for(ItemStack is : entry.displayedItems)
    									  {
    										  if(toDraw.isItemEqual(is))
    										  {
    											  currentPage = i - i%2;
    											  break f;
    										  }
    									  }
    								  }
    								  if(entry.pageRecipe != null)
    								  {
    									  ItemStack result = entry.pageRecipe.getRecipeOutput();
    									  if(result.isItemEqual(toDraw))
    									  {
   											  currentPage = i - i%2;
											  break f;
    									  }
    								  }
    							  }
    						  }
    					  }
    				  }
    			  }
    			  this.func_146283_a(catStr, mX, mZ);
    		 }
		 }
	 }
	 
	 @Override
	 protected void mouseClicked(int p_73864_1_, int p_73864_2_, int p_73864_3_)
	 {
		 super.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
	 }
	 
	 protected void actionPerformed(GuiButton b) 
	 {
		 if(currentCategory == null)
		 {
			 CategoryEntry cat = ApiCore.categories.get(b.id);
			 currentCategory = cat;
			 initGui();
			 return;
		 }
		 if(currentCategory != null && currentDiscovery == null)
		 {
			 if(b.id == 0)
			 {
				 currentCategory = null;
				 initGui();
				 return;
			 }
			 if(b.id == 1)
			 {
				 ++currentPage_discovery;
				 initGui();
				 return;
			 }
			 if(b.id == 2)
			 {
				 --currentPage_discovery;
				 initGui();
				 return;
			 }
			 if(b.id > 2)
			 {
				 DiscoveryEntry disc = currentCategory.discoveries.get((48*currentPage_discovery) + b.id - 3);
				 currentDiscovery = disc;
				 initGui();
				 return;
			 }
		 }
		 if(currentCategory != null && currentDiscovery != null)
		 {
			 if(b.id == 0)
			 {
				 currentDiscovery = null;
				 initGui();
				 return;
			 }
			 if(b.id == 1)
			 {
				 currentPage -= 2;
				 initGui();
				 return;
			 }
			 if(b.id == 2)
			 {
				 currentPage += 2;
				 initGui();
				 return;
			 }
		 }
	 }
	 
	 @SuppressWarnings("unchecked")
	protected void renderToolTip(ItemStack p_146285_1_, int p_146285_2_, int p_146285_3_)
	 {
	     List<String> list = p_146285_1_.getTooltip(this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips);

	     for (int k = 0; k < list.size(); ++k)
	     {
	         if (k == 0)
	         {
	             list.set(k, p_146285_1_.getRarity().rarityColor + (String)list.get(k));
	         }
	         else
	         {
	             list.set(k, EnumChatFormatting.GRAY + (String)list.get(k));
	         }
	     }

	     FontRenderer font = p_146285_1_.getItem().getFontRenderer(p_146285_1_);
	     drawHoveringText(list, p_146285_2_, p_146285_3_, (font == null ? fontRendererObj : font));
	 }
	 
	 @SuppressWarnings("rawtypes")
	protected void func_146283_a(List p_146283_1_, int p_146283_2_, int p_146283_3_)
	    {
		 //TODO listAdditions
		 	hoveringText.add(new Object[]{p_146283_1_,p_146283_2_,p_146283_3_,fontRendererObj});
	        //drawHoveringText(p_146283_1_, p_146283_2_, p_146283_3_, fontRendererObj);   
	    }

	    @SuppressWarnings({ "unchecked", "rawtypes" })
		protected void drawHoveringText(List p_146283_1_, int p_146283_2_, int p_146283_3_, FontRenderer font)
	    {
	    	GL11.glDisable(GL11.GL_LIGHTING);
	        if (!p_146283_1_.isEmpty())
	        {
	            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
	            RenderHelper.disableStandardItemLighting();
	            GL11.glDisable(GL11.GL_LIGHTING);
	            GL11.glDisable(GL11.GL_DEPTH_TEST);
	            int k = 0;
	            Iterator<String> iterator = p_146283_1_.iterator();

	            while (iterator.hasNext())
	            {
	                String s = (String)iterator.next();
	                int l = font.getStringWidth(s);

	                if (l > k)
	                {
	                    k = l;
	                }
	            }

	            int j2 = p_146283_2_ + 12;
	            int k2 = p_146283_3_ - 12;
	            int i1 = 8;

	            if (p_146283_1_.size() > 1)
	            {
	                i1 += 2 + (p_146283_1_.size() - 1) * 10;
	            }

	            if (j2 + k > this.width)
	            {
	                j2 -= 28 + k;
	            }

	            if (k2 + i1 + 6 > this.height)
	            {
	                k2 = this.height - i1 - 6;
	            }

	            this.zLevel = 600.0F;
	            itemRender.zLevel = 600.0F;
	            int j1 = -267386872;
	            this.drawGradientRect(j2 - 3, k2 - 4, j2 + k + 3, k2 - 3, j1, j1);
	            this.drawGradientRect(j2 - 3, k2 + i1 + 3, j2 + k + 3, k2 + i1 + 4, j1, j1);
	            this.drawGradientRect(j2 - 3, k2 - 3, j2 + k + 3, k2 + i1 + 3, j1, j1);
	            this.drawGradientRect(j2 - 4, k2 - 3, j2 - 3, k2 + i1 + 3, j1, j1);
	            this.drawGradientRect(j2 + k + 3, k2 - 3, j2 + k + 4, k2 + i1 + 3, j1, j1);
	            int k1 = 1347420415;
	            int l1 = (k1 & 16711422) >> 1 | k1 & -16777216;
	            this.drawGradientRect(j2 - 3, k2 - 3 + 1, j2 - 3 + 1, k2 + i1 + 3 - 1, k1, l1);
	            this.drawGradientRect(j2 + k + 2, k2 - 3 + 1, j2 + k + 3, k2 + i1 + 3 - 1, k1, l1);
	            this.drawGradientRect(j2 - 3, k2 - 3, j2 + k + 3, k2 - 3 + 1, k1, k1);
	            this.drawGradientRect(j2 - 3, k2 + i1 + 2, j2 + k + 3, k2 + i1 + 3, l1, l1);

	            for (int i2 = 0; i2 < p_146283_1_.size(); ++i2)
	            {
	                String s1 = (String)p_146283_1_.get(i2);
	                font.drawStringWithShadow(s1, j2, k2, -1);

	                if (i2 == 0)
	                {
	                    k2 += 2;
	                }

	                k2 += 10;
	            }

	            this.zLevel = 0.0F;
	            itemRender.zLevel = 0.0F;
	            GL11.glEnable(GL11.GL_LIGHTING);
	            GL11.glEnable(GL11.GL_DEPTH_TEST);
	            RenderHelper.enableStandardItemLighting();
	            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
	        }
	        GL11.glEnable(GL11.GL_LIGHTING);
	        GL11.glColor3f(1, 1, 1);
	    }

	    public boolean doesGuiPauseGame()
	    {
	        return false;
	    }
}
