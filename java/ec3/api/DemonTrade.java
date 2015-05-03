package ec3.api;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public class DemonTrade
{
	public static List<DemonTrade> trades = new ArrayList<DemonTrade>();
	public static List<Class<? extends Entity>> allMobs = new ArrayList<Class<? extends Entity>>();
	public ItemStack desiredItem;
	public Class<? extends Entity> entityType;
	public final int id;
	
	public DemonTrade(ItemStack is)
	{
		id = trades.size();
		desiredItem = is;
		trades.add(this);
	}
	
	public DemonTrade(Class<? extends Entity> e)
	{
		id = trades.size();
		entityType = e;
		allMobs.add(e);
		trades.add(this);
	}

}
