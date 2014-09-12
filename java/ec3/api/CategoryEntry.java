package ec3.api;

import java.util.ArrayList;
import java.util.List;

import scala.actors.threadpool.Arrays;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CategoryEntry {
	
	public List<DiscoveryEntry> discoveries = new ArrayList<DiscoveryEntry>();
	
	public String id;
	
	public ItemStack displayStack;
	
	public String name;
	
	public String shortDescription;
	
	public CategoryEntry(String i)
	{
		id = i;
	}
	
	public CategoryEntry setName(String s)
	{
		name = s;
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
		return this;
	}
	
	public CategoryEntry setDesc(String s)
	{
		shortDescription = s;
		return this;
	}
	
	public CategoryEntry apendDiscovery(DiscoveryEntry disc)
	{
		discoveries.add(disc);
		return this;
	}

}
