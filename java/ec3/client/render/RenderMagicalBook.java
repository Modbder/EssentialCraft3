package ec3.client.render;

import org.lwjgl.opengl.GL11;

import DummyCore.Utils.MiscUtils;
import cpw.mods.fml.common.FMLCommonHandler;
import ec3.api.DiscoveryEntry;
import ec3.api.PageEntry;
import ec3.client.gui.GuiResearchBook;
import ec3.common.mod.EssentialCraftCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class RenderMagicalBook implements IItemRenderer{
	protected static RenderItem itemRender = new RenderItem();

	public static final ResourceLocation bookTextures = new ResourceLocation("essentialcraft","textures/special/models/bookUV.png");
	public static final ResourceLocation pageTextures = new ResourceLocation("essentialcraft","textures/special/models/pageUV.png");
	public static final IModelCustom book = AdvancedModelLoader.loadModel(new ResourceLocation("essentialcraft","textures/special/models/book.obj"));
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return type == ItemRenderType.EQUIPPED_FIRST_PERSON;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		return type == ItemRenderType.EQUIPPED_FIRST_PERSON;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		GL11.glPushMatrix();
		
		GL11.glRotated(45, 0, 0, 1);
		GL11.glRotated(25, 0, 1, 0);
		GL11.glRotated(25, 1, 0, 0);
		GL11.glTranslated(0.3D, 0, 0);
		Minecraft.getMinecraft().renderEngine.bindTexture(bookTextures);
		book.renderPart("Cube_Cube.001");
		Minecraft.getMinecraft().renderEngine.bindTexture(pageTextures);
		book.renderPart("Cube.005_Cube.006");
		book.renderPart("Cube.004_Cube.005");
		book.renderPart("Cube.003_Cube.004");
		book.renderPart("Cube.002_Cube.003");
		book.renderPart("Cube.001_Cube.002");
		book.renderPart("Cube_Cube.002");
		
		GL11.glPopMatrix();
		
		if(GuiResearchBook.currentDiscovery == null && GuiResearchBook.currentCategory == null)
		{
			GL11.glPushMatrix();
			
			GL11.glRotated(45, 0, 0, 1);
			GL11.glRotated(25, 0, 1, 0);
			GL11.glRotated(25, 1, 0, 0);
			GL11.glTranslated(0.3D, 0, 0);
			
			GL11.glRotated(90, 1, 0, 0);
			GL11.glRotated(90, 0, 0, 1);
			GL11.glTranslated(-0.92D, -0.92D, -0.35D);
			GL11.glRotated(-15, 0, 1, 0);
			double d1 = 0.007D;
			GL11.glScaled(d1, d1, 1);
			
			Minecraft.getMinecraft().fontRenderer.drawString("\u00a7l" + StatCollector.translateToLocal("ec3.txt.book.startup"), 0, 0, 0x7e760a);
			Minecraft.getMinecraft().fontRenderer.drawString(StatCollector.translateToLocal("ec3.txt.book.containedKnowledge"), 16, 15, 0x7e760a);
			int tier = MiscUtils.getStackTag(item).getInteger("tier");
			for(int i = 0; i <= tier; ++i)
			{
				Minecraft.getMinecraft().fontRenderer.drawString("-\u00a7o" + StatCollector.translateToLocal("ec3.txt.book.tier_"+i), 16, 25+(i*10), 0x222222);
			}
			Minecraft.getMinecraft().fontRenderer.drawString(StatCollector.translateToLocal("ec3.txt.book.edition"), 16, 80, 0x7e760a);
			Minecraft.getMinecraft().fontRenderer.drawString("\u00a72" + FMLCommonHandler.instance().findContainerFor(EssentialCraftCore.core).getDisplayVersion()+"r", 16, 90, 0xffffff);
			
			GL11.glPopMatrix();
		}
		
		if(GuiResearchBook.currentDiscovery != null)
		{
			DiscoveryEntry currentDisc = GuiResearchBook.currentDiscovery;
			GL11.glPushMatrix();
			
			GL11.glRotated(45, 0, 0, 1);
			GL11.glRotated(25, 0, 1, 0);
			GL11.glRotated(25, 1, 0, 0);
			GL11.glTranslated(0.3D, 0, 0);
			
			GL11.glPushMatrix();
			GL11.glRotated(90, 1, 0, 0);
			GL11.glRotated(90, 0, 0, 1);
			GL11.glTranslated(-0.92D, -0.92D, -0.35D);
			GL11.glRotated(-15, 0, 1, 0);
			double d1 = 0.012D;
			GL11.glScaled(d1, d1, 1);
			
			String added = "null";
   		 	if(currentDisc.name == null || currentDisc.name.isEmpty())
   		 	{
   		 		added = "\u00a7l"+StatCollector.translateToLocal("ec3book.discovery_"+currentDisc.id+".name");
   		 	}
   		 	else
   		 		added = currentDisc.name;
   		 	
   			GL11.glTranslated(0, 0D, -0.01D);
			Minecraft.getMinecraft().fontRenderer.drawString(added, 0, 0, 0x000000);
			GL11.glTranslated(0, 0D, 0.01D);
			
			if(currentDisc.pages.size() > GuiResearchBook.currentPage)
			{
				PageEntry page = currentDisc.pages.get(GuiResearchBook.currentPage);
			    if(page.pageImgLink != null)
			    {
			    	 GL11.glColor3f(1, 1, 1);
			    	 GL11.glDisable(GL11.GL_LIGHTING);
			    	 Minecraft.getMinecraft().renderEngine.bindTexture(page.pageImgLink);
			    	 GL11.glPushMatrix();
			    	 GL11.glScaled(0.5D,0.5D,1);
			    	 Gui.func_152125_a(16, 0, 0, 0, 256, 256, 100, 100, 256, 256);
			    	 GL11.glPopMatrix();
			    	 GL11.glTranslated(0, 30D, 0);
			    }
				if(page.pageText != null && !page.pageText.isEmpty())
				{
					GL11.glTranslated(0, 0D, -0.01D);
					GL11.glScaled(0.75D, 0.5D, 1);
					Minecraft.getMinecraft().fontRenderer.setUnicodeFlag(true);
					Minecraft.getMinecraft().fontRenderer.drawSplitString(page.pageText, 0, 19, 110, 0x000000);
					Minecraft.getMinecraft().fontRenderer.setUnicodeFlag(false);
				}
			}
			
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			GL11.glRotated(90, 1, 0, 0);
			GL11.glRotated(90, 0, 0, 1);
			GL11.glTranslated(-0.04D, -0.92D, -0.35D);
			GL11.glRotated(15, 0, 1, 0);
			d1 = 0.012D;
			GL11.glScaled(d1, d1, 1);
			
			if(currentDisc.pages.size() > GuiResearchBook.currentPage+1)
			{
				PageEntry page = currentDisc.pages.get(GuiResearchBook.currentPage+1);
			    if(page.pageImgLink != null)
			    {
			    	 GL11.glColor3f(1, 1, 1);
			    	 GL11.glDisable(GL11.GL_LIGHTING);
			    	 Minecraft.getMinecraft().renderEngine.bindTexture(page.pageImgLink);
			    	 GL11.glPushMatrix();
			    	 GL11.glScaled(0.5D,0.5D,1);
			    	 Gui.func_152125_a(16, 0, 0, 0, 256, 256, 100, 100, 256, 256);
			    	 GL11.glPopMatrix();
			    	 GL11.glTranslated(0, 30D, 0);
			    }
				if(page.pageText != null && !page.pageText.isEmpty())
				{
					GL11.glTranslated(0, 0D, -0.01D);
					GL11.glScaled(0.75D, 0.5D, 1);
					Minecraft.getMinecraft().fontRenderer.setUnicodeFlag(true);
					Minecraft.getMinecraft().fontRenderer.drawSplitString(page.pageText, 0, 19, 100, 0x000000);
					Minecraft.getMinecraft().fontRenderer.setUnicodeFlag(false);
				}
			}
			
			GL11.glPopMatrix();
			GL11.glPopMatrix();
		}
	}

}
