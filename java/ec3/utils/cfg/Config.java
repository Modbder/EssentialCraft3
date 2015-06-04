package ec3.utils.cfg;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.EnumHelper;
import DummyCore.Utils.IDummyConfig;
import cpw.mods.fml.common.registry.GameRegistry;
import ec3.common.block.BlocksCore;
import ec3.common.entity.EntitiesCore;
import ec3.common.registry.TileRegistry;
import ec3.utils.common.EnumOreColoring;

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
		guiID[1] = config.get("GUI","Demon GUI ID", 7322).getInt();
	}
	
	
	//TODO Mobs
	public void loadMobs()
	{
		autoFindEID = config.getBoolean("autoFindEntityIDs", "Entities", false, "Enable if you can't set the entity ID's on your own.");
		mobID[0] = EntitiesCore.nextEntityID(config.get("Entities","MRUPresenceID", 71).getInt());
		mobID[1] = EntitiesCore.nextEntityID(config.get("Entities","MRUArrowID", 72).getInt());
		mobID[2] = EntitiesCore.nextEntityID(config.get("Entities","SolarBeamID", 73).getInt());
		mobID[3] = EntitiesCore.nextEntityID(config.get("Entities","WindMageID", 74).getInt());
		mobID[4] = EntitiesCore.nextEntityID(config.get("Entities","PosionFumeID", 75).getInt());
	}
	
	public void loadMisc()
	{
		biomeID[0] = config.get("Biomes", "ChaosCorruptionID", 91).getInt();
		biomeID[1] = config.get("Biomes", "FrozenCorruptionID", 92).getInt();
		biomeID[2] = config.get("Biomes", "ShadowCorruptionID", 93).getInt();
		biomeID[3] = config.get("Biomes", "MagicCorruptionID", 94).getInt();
		biomeID[4] = config.get("Biomes", "DesertID", 95).getInt();
		biomeID[5] = config.get("Biomes", "DreadLandsID", 96).getInt();
		enablePersonalityShatter = config.getBoolean("EnablePersonalityShatter", "Misc", true, "");
		renderStructuresFromAbove = config.getBoolean("renderStructuresFromAbove", "Misc", true, "");
		dimensionID = config.getInt("Hoanna ID", "Misc", 53, Integer.MIN_VALUE, Integer.MAX_VALUE, "");
		String[] cfgCustomOreParsing = config.getStringList("CustomMagmaticAlloys", "Misc", new String[]{"oreSaltpeter:10065301|5?dustSaltpeter","oreSulfur:16777113|5?dustSulfur"}, "Allows to add custom ores to Magmatic Alloys, where this is an array list, where first part is the ore name in OreDictionary, int after : is the color, int after | is the amount of drops you get from the ore and String after ? is the OreDictionary name of the result.");
		for(String s : cfgCustomOreParsing)
		{
			int index_0 = s.indexOf(":");
			int index_1 = s.indexOf("|");
			int index_2 = s.indexOf("?");
			if(index_0 == -1 || index_1 == -1 || index_2 == -1)continue;
			String oredOreName = s.substring(0, index_0);
			int oreColor = Integer.parseInt(s.substring(index_0+1, index_1));
			int oreOutput = Integer.parseInt(s.substring(index_1+1, index_2));
			String oredResultName = s.substring(index_2+1, s.length());
			
			EnumHelper.addEnum(EnumOreColoring.class, oredOreName.toUpperCase(), new Class[]{String.class,String.class,int.class,int.class}, new Object[]{oredOreName,oredResultName,oreColor,oreOutput});
		}
		oreGenAttempts = config.getInt("oreGenAttempts", "Misc", 4, 0, Integer.MAX_VALUE, "The amount of tries to generate the elemental ore cluster in a chunk. Set to 0 to disable worldgen.");
		eMRUCUGenAttempts = config.getInt("eMRUCUGenAttempts", "Misc", 1, 0, Integer.MAX_VALUE, "The amount of tries to generate the Elder MRUCU Structure in a chunk. Set to 0 to disable worldgen.");
		allowPaleItemsInOtherRecipes = config.getBoolean("AllowPaleItemsInOtherRecipes", "Misc", true, "");
	}
	
	public static int getIdForBlock(String name)
	{
		return ++blocksCount;
	}
	
	public static int getIdForItem(String name)
	{
		return ++itemsCount;
	}
	
	
	public static int genericBlockIDS = 1200;
	public static int blocksCount = 0;
	public static int genericItemIDS = 13200;
	public static int itemsCount = 0;
	public static int dimensionID = 53;
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
	public static boolean autoFindEID = false;
	public static net.minecraftforge.common.config.Configuration config;
	
	public static String[] data_addedOresNames;
	public static int[] data_addedOreColors;
	public static int[] data_addedOreAmount;
	public static int oreGenAttempts;
	public static int eMRUCUGenAttempts;
	
	public static boolean allowPaleItemsInOtherRecipes;
	
	public static boolean renderStructuresFromAbove;

	@Override
	public void load(Configuration config) {
		Config.config = config;
		config.load();
		this.loadBlocks();
		this.loadItems();
		this.loadGUIs();
		this.loadMobs();
		this.loadMisc();
		this.loatEnchants();
		config.save();
		this.loadTiles();
	}
	
	public void loadTiles()
	{

		for(Class<? extends TileEntity> tile : TileRegistry.cfgDependant)
		{
			try
			{
				if(tile.getMethod("setupConfig", Configuration.class) != null)
				{
					tile.getMethod("setupConfig", Configuration.class).invoke(null, Config.config);
				}
			}catch(Exception e)
			{
				e.printStackTrace();
				continue;
			}
		}

	}
	
	public void postInitParseDecorativeBlocks()
	{
		config.load();
		String[] cfgCustomFancy = config.getStringList("Custom Fancy Blocks", "Misc", new String[]{"Thaumcraft:blockCosmeticOpaque?0","Thaumcraft:blockCosmeticSolid?1"}, "Allows to add custom Fancy Blocks to the game, where string before : is the modname, String after :, but before ? is the block name, and int after ? is metadata.");
		for(String s : cfgCustomFancy)
		{
			int index_0 = s.indexOf(":");
			int index_1 = s.indexOf("?");
			if(index_0 == -1 || index_1 == -1)continue;
			String modname = s.substring(0,index_0);
			String blockname = s.substring(index_0+1,index_1);
			int metadata = Integer.parseInt(s.substring(index_1+1));
			Block added = GameRegistry.findBlock(modname, blockname);
			if(added != null)
			{
				BlocksCore.createFancyBlock(added, blockname, metadata);
			}
		}
		config.save();
	}


}
