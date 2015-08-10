package ec3.api;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class CategoryEntry {
	
	public List<DiscoveryEntry> discoveries = new ArrayList<DiscoveryEntry>();
	
	public String id;
	
	public ItemStack displayStack;
	
	public String name;
	
	public String shortDescription;
	
	public ResourceLocation displayTexture;
	
	public int reqTier;
	
	public ResourceLocation specificBookTextures;
	
	public int textColor = 0x222222;
	
	public CategoryEntry(String i)
	{
		id = i;
	}
	
	public CategoryEntry setName(String s)
	{
		name = s;
		return this;
	}
	
	public CategoryEntry setTier(int i)
	{
		reqTier = i;
		return this;
	}
	
	public CategoryEntry setTextColor(int i)
	{
		textColor = i;
		return this;
	}
	
	public CategoryEntry setDisplayStack(Object obj)
	{
		if(obj instanceof ItemStack)
			displayStack = (ItemStack) obj;
		if(obj instanceof Block)
			displayStack = new ItemStack((Block) obj,1,0);
		if(obj instanceof Item)
			displayStack = new ItemStack((Item) obj,1,0);
		if(obj instanceof ResourceLocation)
			displayTexture = (ResourceLocation) obj;
		return this;
	}
	
	public CategoryEntry setDesc(String s)
	{
		shortDescription = s;
		return this;
	}
	
	public CategoryEntry setSpecificTexture(ResourceLocation l)
	{
		specificBookTextures = l;
		return this;
	}
	
	public CategoryEntry apendDiscovery(DiscoveryEntry disc)
	{
		discoveries.add(disc);
		return this;
	}

}
