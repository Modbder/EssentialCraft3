package ec3.client.gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import DummyCore.Utils.MathUtils;
import cpw.mods.fml.common.FMLCommonHandler;
import ec3.api.ApiCore;
import ec3.api.CategoryEntry;
import ec3.api.DiscoveryEntry;
import ec3.api.PageEntry;
import ec3.common.mod.EssentialCraftCore;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class GuiResearchBook extends GuiScreen{
	
	 protected static RenderItem itemRender = new RenderItem();
	 
	 public int currentDepth;
	 public static int currentPage;
	 public static CategoryEntry currentCategory;
	 public static DiscoveryEntry currentDiscovery;
	 public static int currentPage_discovery;
	 
	 public static final int discoveries_per_page = 48;
	 
	 public NBTTagCompound bookTag;
	 
	 public static final ResourceLocation gui = new ResourceLocation("essentialcraft","textures/gui/research_book_generic.png");
	 
	 public void initGui() 
	 {
		 this.mc.theWorld.playSound(this.mc.thePlayer.posX,this.mc.thePlayer.posY,this.mc.thePlayer.posZ, "essentialcraft:sound.pageturn", 1, 1+MathUtils.randomFloat(this.mc.theWorld.rand)/4, false);
		 this.buttonList.clear();
		 this.labelList.clear();
		 bookTag = this.mc.thePlayer.getCurrentEquippedItem().getTagCompound();
	     int k = (this.width - 256) / 2 + 128;
	     int l = (this.height - 168) / 2;
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
	    	 this.mc.renderEngine.bindTexture(gui);
	     }
	     this.drawTexturedModalRect(k, l, 0, 0, 256, 180);
	 }
	 
	 @Override
	 public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_)
	 {
	     int k = (this.width - 256) / 2;
	     int l = (this.height - 168) / 2;
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
	 }
	 
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
	    	 if(i >= 24) dx += 128;
	    	 int dy = l + (22*(i%6)) + 22;
	    	 GuiButtonNoSound btnAdd = new GuiButtonNoSound(i + 3, dx, dy, 20, 20, "");
	    	 this.buttonList.add(btnAdd);
	     }
	     this.buttonList.add(page_left);
	     this.buttonList.add(page_right);
	 }
	 
	 public void initCategories()
	 {
		 currentPage_discovery = 0;
	     int k = (this.width - 256) / 2 + 128;
	     int l = (this.height - 168) / 2;
	     for(int i = 0; i < ApiCore.categories.size(); ++i)
	     {
	    	 CategoryEntry cat = ApiCore.categories.get(i);
	    	 GuiButtonNoSound added = new GuiButtonNoSound(i, k + (30*(i/5)) + 8, l + (30*(i%5)) + 28, 20, 20, "");
	    	 added.enabled = false;
	    	 int reqTier = cat.reqTier;
	    	 int tier = bookTag.getInteger("tier");
	    	 if(tier >= reqTier)
	    		 added.enabled = true;
	    	 this.buttonList.add(added);
	     }
	 }
	 
	 public void initPage()
	 {
	     int k = (this.width - 256) / 2;
	     int l = (this.height - 168) / 2;
	     GuiButtonNoSound back = new GuiButtonNoSound(0, k+236, l+7, 14, 18, "");
	     this.buttonList.add(back);
	     GuiButtonNoSound page_left = new GuiButtonNoSound(1,k+7,l+158,24,13,"");
	     GuiButtonNoSound page_right = new GuiButtonNoSound(2,k+227,l+158,24,13,"");
	     int pagesMax = this.currentDiscovery.pages.size();
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
	     int k = (this.width - 256) / 2;
	     int l = (this.height - 168) / 2;
	     int pagesMax = this.currentDiscovery.pages.size();
	     for (int ik = 0; ik < this.buttonList.size(); ++ik)
	     {
	    	 GL11.glColor3f(1, 1, 1);
	    	 GuiButton btn  = (GuiButton) this.buttonList.get(ik);
	    	 boolean hover = mouseX >= btn.xPosition && mouseZ >= btn.yPosition && mouseX < btn.xPosition + btn.width && mouseZ < btn.yPosition + btn.height;
	    	 int id = btn.id;
	    	 if(id == 0)
	    	 {
	    		 this.mc.renderEngine.bindTexture(gui);
	    		 this.mc.renderEngine.bindTexture(gui);
	    		 if(hover)
	    			 GL11.glColor3f(1, 0.8F, 1);
	    		 this.drawTexturedModalRect(btn.xPosition, btn.yPosition, 49, 238, 14, 18);
	    	 }
	    	 if(id == 1)
	    	 {
	    		 this.mc.renderEngine.bindTexture(gui);
	    		 if(hover && btn.enabled)
	    			 GL11.glColor3f(1, 0.8F, 1);
	    		 if(!btn.enabled)
	    			 GL11.glColor3f(0.3F, 0.3F, 0.3F);
	    		 this.drawTexturedModalRect(btn.xPosition, btn.yPosition, 0, 243, 24, 13);
	    	 }
	    	 if(id == 2)
	    	 {
	    		 this.mc.renderEngine.bindTexture(gui);
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
		 PageEntry page = this.currentDiscovery.pages.get(currentPage);
	     int k = (this.width - 256) / 2;
	     int l = (this.height - 168) / 2;
	     if(currentPage == 0)
	     {
	    	 String added = "";
	    	 if(page.pageTitle == null || page.pageTitle.isEmpty())
	    	 {
	    		 if(this.currentDiscovery.name == null || this.currentDiscovery.name.isEmpty())
	    		 {
	    			 added = "\u00a7l"+StatCollector.translateToLocal("ec3book.discovery_"+this.currentDiscovery.id+".name");
	    		 }
	    		 else
	    			 added = this.currentDiscovery.name;
	    	 }else
	    	 {
	    		 added = page.pageTitle;
	    	 }
	    	 this.fontRendererObj.drawStringWithShadow(added, k+6, l+10, 0xffffff);
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
	    	 this.func_152125_a(k+16, l+10, 0, 0, 256, 256, 100, 100, 256, 256);
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
	     
	     if(page.pageText != null && !page.pageText.isEmpty())
	     {
	    	 List<String> display = parse(page.pageText);
	    	 for(int i = 0; i < display.size(); ++i)
	    	 {
	    		 this.fontRendererObj.drawString(display.get(i), k+8, l+25+i*10, 0x222222);
	    	 }
	     }
	 }
	 
	 public void drawPage_1(int mouseX, int mouseY)
	 {
		 PageEntry page = this.currentDiscovery.pages.get(currentPage+1);
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
	    	 this.func_152125_a(k+16, l+10, 0, 0, 256, 256, 100, 100, 256, 256);
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
	     
	     if(page.pageText != null && !page.pageText.isEmpty())
	     {
	    	 List<String> display = parse(page.pageText);
	    	 for(int i = 0; i < display.size(); ++i)
	    	 {
	    		 this.fontRendererObj.drawString(display.get(i), k+8, l+25+i*10, 0x222222);
	    	 }
	     }
	     
	     
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
	    		 this.mc.renderEngine.bindTexture(gui);
	    		 this.mc.renderEngine.bindTexture(gui);
	    		 if(hover)
	    			 GL11.glColor3f(1, 0.8F, 1);
	    		 this.drawTexturedModalRect(btn.xPosition, btn.yPosition, 49, 238, 14, 18);
	    	 }
	    	 if(id == 1)
	    	 {
	    		 this.mc.renderEngine.bindTexture(gui);
	    		 if(hover && btn.enabled)
	    			 GL11.glColor3f(1, 0.8F, 1);
	    		 if(!btn.enabled)
	    			 GL11.glColor3f(0.3F, 0.3F, 0.3F);
	    		 this.drawTexturedModalRect(btn.xPosition, btn.yPosition, 0, 243, 24, 13);
	    	 }
	    	 if(id == 2)
	    	 {
	    		 this.mc.renderEngine.bindTexture(gui);
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
	    		 
	    		 this.mc.renderEngine.bindTexture(gui);
	    		 if(!hover)
	    			 this.drawTexturedModalRect(btn.xPosition, btn.yPosition, 0, 222, 20, 20);
	    		 else
	    			 this.drawTexturedModalRect(btn.xPosition, btn.yPosition, 28, 222, 20, 20); 
	    		 if(disc.displayStack != null)
	    		 {
	    			 GL11.glPushMatrix();
	    			 GL11.glDisable(GL11.GL_LIGHTING);

	    			 this.itemRender.renderItemAndEffectIntoGUI(fontRendererObj, this.mc.renderEngine, disc.displayStack, btn.xPosition+2, btn.yPosition+2);
	    			 
	    			 GL11.glPopMatrix();
	    		 }
	    		 else if(disc.displayTexture != null)
	    		 {
	    			 this.mc.renderEngine.bindTexture(disc.displayTexture);
	    			 this.func_146110_a(btn.xPosition+2, btn.yPosition+2, 0, 0, 16, 16, 16, 16);
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
		 this.fontRendererObj.drawString("\u00a72  " + FMLCommonHandler.instance().findContainerFor(EssentialCraftCore.core).getDisplayVersion(), k+16, l+100, 0xffffff);
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

	    			 this.itemRender.renderItemAndEffectIntoGUI(fontRendererObj, this.mc.renderEngine, cat.displayStack, btn.xPosition+2, btn.yPosition+2);
	    			 
	    			 GL11.glPopMatrix();
	    		 }
	    		 else if(cat.displayTexture != null)
	    		 {
	    			 this.mc.renderEngine.bindTexture(cat.displayTexture);
	    			 this.func_146110_a(btn.xPosition+2, btn.yPosition+2, 0, 0, 16, 16, 16, 16);
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
	    			 this.func_146283_a(catStr, mouseX, mouseZ);
	    		 }
	    	 }
	     }
	 }
	 
	 public void drawIS(ItemStack toDraw, int pX, int pZ, int mX, int mZ, int phase)
	 {
		 if(phase == 0)
		 {
			 this.itemRender.renderItemAndEffectIntoGUI(fontRendererObj, this.mc.renderEngine, toDraw, pX, pZ);
		 }else
		 {
			 boolean hover = mX >= pX && mZ >= pZ && mX < pX + 16 && mZ < pZ + 16;
    		 if(hover)
    		 {
    			  List<String> catStr = toDraw.getTooltip(this.mc.thePlayer, false);
    			  if(ApiCore.findDiscoveryByIS(toDraw) != null)
    			  {
    				  catStr.add(EnumChatFormatting.ITALIC + StatCollector.translateToLocal("ec3.txt.is.press"));
    				  if(Mouse.isButtonDown(0))
    				  {
    					  DiscoveryEntry switchTo = ApiCore.findDiscoveryByIS(toDraw);
    					  currentPage = 0;
    					  currentPage_discovery = 0;
    					  currentDiscovery = switchTo;
    					  initGui();
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
		 if(this.currentCategory == null)
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
				 ++this.currentPage_discovery;
				 initGui();
				 return;
			 }
			 if(b.id == 2)
			 {
				 --this.currentPage_discovery;
				 initGui();
				 return;
			 }
			 if(b.id > 2)
			 {
				 DiscoveryEntry disc = currentCategory.discoveries.get((48*currentPage_discovery) + b.id - 3);
				 this.currentDiscovery = disc;
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
				 this.currentPage -= 2;
				 initGui();
				 return;
			 }
			 if(b.id == 2)
			 {
				 this.currentPage += 2;
				 initGui();
				 return;
			 }
		 }
	 }
	 
	 protected void renderToolTip(ItemStack p_146285_1_, int p_146285_2_, int p_146285_3_)
	 {
	     List list = p_146285_1_.getTooltip(this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips);

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
	 
	 protected void func_146283_a(List p_146283_1_, int p_146283_2_, int p_146283_3_)
	    {
	        drawHoveringText(p_146283_1_, p_146283_2_, p_146283_3_, fontRendererObj);   
	    }

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
	            Iterator iterator = p_146283_1_.iterator();

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

	            this.zLevel = 300.0F;
	            itemRender.zLevel = 300.0F;
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
