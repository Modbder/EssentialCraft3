package ec3.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;

public class DiscoveryEntry {
	
	public List<PageEntry> pages = new ArrayList<PageEntry>();
	
	public String id;
	
	public ItemStack displayStack;
	
	public List<ItemStack> referalItemStackLst = new ArrayList<ItemStack>();
	
	public String name;
	
	public String shortDescription;
	
	public ResourceLocation displayTexture;
	
	public boolean isNew = false;
	
	public DiscoveryEntry(String i)
	{
		id = i;
	}
	
	public DiscoveryEntry setName(String s)
	{
		name = s;
		return this;
	}
	
	public DiscoveryEntry setNew()
	{
		isNew = true;
		return this;
	}
	
	public DiscoveryEntry setDisplayStack(Object obj)
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
	
	public DiscoveryEntry setDesc(String s)
	{
		shortDescription = s;
		return this;
	}
	
	public DiscoveryEntry apendPage(PageEntry page)
	{
		pages.add(page);
		return this;
	}
	
	public DiscoveryEntry setReferal(ItemStack... stk)
	{
		referalItemStackLst.addAll(Arrays.asList(stk));
		for(int i = 0;i < stk.length; ++i)
		{
			ItemStack is = stk[i];
			is.stackSize = 0;
			ApiCore.discoveriesByIS.put(is.toString(), this);
		}
		return this;
	}

}
