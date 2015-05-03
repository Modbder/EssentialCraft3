package ec3.api;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;

public class PageEntry {
	
	public String pageTitle;
	
	public String pageText;
	
	public ResourceLocation pageImgLink;
	
	public String pageID;
	
	public IRecipe pageRecipe;
	
	public ItemStack[] displayedItems;
	
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
	
	public PageEntry setImg(ResourceLocation img)
	{
		pageImgLink = img;
		return this;
	}
	
	public PageEntry setRecipe(IRecipe rec)
	{
		pageRecipe = rec;
		return this;
	}
	
	public PageEntry setDisplayStacks(ItemStack... rec)
	{
		displayedItems = rec;
		return this;
	}
	
	public PageEntry(String id)
	{
		pageID = id;
	}

}
