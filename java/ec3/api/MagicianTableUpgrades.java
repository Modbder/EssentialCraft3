package ec3.api;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

/**
 * 
 * @author Modbder
 * @Description Use this to add new Upgrades to Magician Table. You should do this after initializing your Items.
 */
public class MagicianTableUpgrades {
	
	public static final List<ItemStack> upgradeStacks = new ArrayList<ItemStack>();
	
	public static final List<Float> upgradeEfficency = new ArrayList<Float>();
	
	public static final List<ResourceLocation> upgradeTextures = new ArrayList<ResourceLocation>();
	
	public static void addUpgrade(ItemStack is, float f,ResourceLocation rl)
	{
		upgradeStacks.add(is.copy());
		upgradeEfficency.add(f);
		upgradeTextures.add(rl);
	}

	public static boolean isItemUpgrade(ItemStack is)
	{
		for(ItemStack s : upgradeStacks)
			if(s.isItemEqual(is))
				return true;
		
		return false;
	}
	
	public static ItemStack createStackByUpgradeID(int uid)
	{
		if(upgradeStacks.size() > uid)
			return upgradeStacks.get(uid);
		return null;
	}
	
	public static int getUpgradeIDByItemStack(ItemStack is)
	{
		for(int i = 0; i < upgradeStacks.size(); ++i)
		{
			if(is.isItemEqual(upgradeStacks.get(i)))
				return i;
		}
		
		return -1;
	}
}
