package ec3.common.registry;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import ec3.common.item.ItemsCore;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraftforge.common.AchievementPage;

public class AchievementRegistry {
	
	public static void register()
	{
		registerNewAchievement(10,0,new ItemStack(ItemsCore.record_everlastingSummer,1,0),null,"theEndlessSummer",true);
		registerNewAchievement(10,-2,new ItemStack(ItemsCore.record_papersPlease,1,0),null,"papersPlease",true);
		registerNewAchievement(0,0,new ItemStack(ItemsCore.soulStone,1,0),null,"soulStone",false);
		registerNewAchievement(0,1,new ItemStack(ItemsCore.soulStone,1,0),null,"darkSouls",true);
		
		Achievement[] achievements = new Achievement[achievementList.size()];
		for(int i = 0; i < achievementList.size(); ++i)
		{
			achievements[i] = achievementList.get(achievementNames.get(i));
		}
		AchievementPage ecAchievements = new AchievementPage("EssentialCraft3", achievements);
		AchievementPage.registerAchievementPage(ecAchievements);
	}
	
	public static void registerNewAchievement(int x, int y,ItemStack display, Achievement parent, String name, boolean isSpecial)
	{
		Achievement beeingRegistered = new Achievement(name, name, x, y, display, parent);
		if(isSpecial)
			beeingRegistered.setSpecial();
		if(parent == null)
			beeingRegistered.initIndependentStat();
		beeingRegistered.registerStat();
		achievementNames.add(name);
		achievementList.put(name, beeingRegistered);
	}
	
	public static void registerAchievementStat(Achievement ach)
	{
		AchievementList.achievementList.remove(ach);
	}
	
	public static List<String> achievementNames = new ArrayList<String>();
	public static Hashtable<String, Achievement> achievementList = new Hashtable<String, Achievement>();

}
