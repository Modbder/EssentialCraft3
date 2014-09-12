package ec3.api;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;

public abstract class PageEntry {
	
	public String pageTitle;
	
	public String pageText;
	
	public String pageImgLink;
	
	public String pageID;
	
	public IRecipe pageRecipe;
	
	public PageEntry setTitle(String title)
	{
		pageTitle = title;
		return this;
	}
	
	public PageEntry setText(String txt)
	{
		pageText = txt;
		return this;
	}
	
	public PageEntry setImg(String img)
	{
		pageImgLink = img;
		return this;
	}
	
	public PageEntry setRecipe(IRecipe rec)
	{
		pageRecipe = rec;
		return this;
	}
	
	public PageEntry(String id)
	{
		pageID = id;
	}
	
	@SideOnly(Side.CLIENT)
	public abstract ResourceLocation getGuiResouceLocation();
	
	@SideOnly(Side.CLIENT)
	public abstract FontRenderer getFontRenderer();
	
	@SideOnly(Side.CLIENT)
	public abstract String[] getParseSpecialRules();

}
