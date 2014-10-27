package ec3.utils.cfg;

import net.minecraftforge.common.config.Configuration;
import DummyCore.Utils.IDummyConfig;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class Config implements IDummyConfig{
	
	public static Config instance;
	
	public Config()
	{
		instance = this;
	}
	
	public void loatEnchants()
	{
	}
	
	//TODO Blocks
	public void loadBlocks()
	{
	}
	
	//TODO Items
	public void loadItems()
	{
	}
	
	//TODO GUIs
	public void loadGUIs()
	{
		guiID[0] = config.get("GUI","Generic GUI ID", 7321).getInt();
	}
	
	
	//TODO Mobs
	public void loadMobs()
	{
		mobID[0] = config.get("Entities","MRUPresenceID", 71).getInt();
		mobID[1] = config.get("Entities","MRUArrowID", 72).getInt();
		mobID[2] = config.get("Entities","SolarBeamID", 73).getInt();
		mobID[3] = config.get("Entities","WindMageID", 74).getInt();
	}
	
	public void loadMisc()
	{
		biomeID[0] = config.get("Biomes", "ChaosCorruptionID", 134).getInt();
		biomeID[1] = config.get("Biomes", "FrozenCorruptionID", 135).getInt();
		biomeID[2] = config.get("Biomes", "ShadowCorruptionID", 136).getInt();
		biomeID[3] = config.get("Biomes", "MagicCorruptionID", 137).getInt();
		biomeID[4] = config.get("Biomes", "DesertID", 138).getInt();
		biomeID[5] = config.get("Biomes", "DreadLandsID", 139).getInt();
		enablePersonalityShatter = config.getBoolean("EnablePersonalityShatter", "Misc", true, "");
	}
	
	public static int getIdForBlock(String name)
	{
		config.load();
		int cfgBlockID = config.get("Blocks", name, genericBlockIDS+blocksCount).getInt();
		++blocksCount;
		config.save();
		return cfgBlockID;
	}
	
	public static int getIdForItem(String name)
	{
		config.load();
		int cfgItemID = config.get("Items",name, genericItemIDS+itemsCount).getInt();
		++itemsCount;
		config.save();
		return cfgItemID;
	}
	
	
	public static int genericBlockIDS = 1200;
	public static int blocksCount = 0;
	public static int genericItemIDS = 13200;
	public static int itemsCount = 0;
	public static int[] guiID = new int[48];
	public static int[] mobID = new int[16];
	public static int[] enchantID = new int[4];
	public static int[] biomeID = new int[8];
	public static boolean isCorruptionAllowed;
	public static boolean renderMRUPresenceWithoutMonocle;
	public static boolean enableHardcoreCrafts;
	public static boolean renderAdvancedBlockFX;
	public static int magicianID;
	public static boolean enablePersonalityShatter = true;
	public static net.minecraftforge.common.config.Configuration config;

	@Override
	public void load(Configuration config) {
		this.config = config;
		config.load();
		this.loadBlocks();
		this.loadItems();
		this.loadGUIs();
		this.loadMobs();
		this.loadMisc();
		this.loatEnchants();
		config.save();
	}


}
