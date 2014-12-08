package ec3.common.registry;

import java.lang.reflect.Field;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import DummyCore.Core.CoreInitialiser;
import cpw.mods.fml.common.registry.GameRegistry;
import ec3.api.MagicianTableRecipes;
import ec3.api.RadiatingChamberRecipes;
import ec3.common.block.BlocksCore;
import ec3.common.item.ItemsCore;

public class RecipeRegistry {
	public static RecipeRegistry instance;
	public boolean hasGregTech = false;
	private Class GT_Class;

	public void main()
	{
		registerDictionary();
		registerRecipes();
		registerMagicianTable();
		registerRadiatingChamber();
	}
	
	public void registerDictionary()
	{
		OreDictionary.registerOre("shardFire", new ItemStack(ItemsCore.drops,1,0));
		OreDictionary.registerOre("shardWater", new ItemStack(ItemsCore.drops,1,1));
		OreDictionary.registerOre("shardEarth", new ItemStack(ItemsCore.drops,1,2));
		OreDictionary.registerOre("shardAir", new ItemStack(ItemsCore.drops,1,3));
		OreDictionary.registerOre("shardElemental", new ItemStack(ItemsCore.drops,1,4));
		OreDictionary.registerOre("gemCoal", new ItemStack(Items.coal,1,0));
		OreDictionary.registerOre("gemNetherStar", new ItemStack(Items.nether_star,1,0));
		OreDictionary.registerOre("obsidian", new ItemStack(Blocks.obsidian,1,0));
		OreDictionary.registerOre("gemEnderPearl", new ItemStack(Items.ender_pearl,1,0));
		OreDictionary.registerOre("gemEnderPearl",new ItemStack(ItemsCore.genericItem,1,38));
		OreDictionary.registerOre("ingotGold",new ItemStack(ItemsCore.genericItem,1,39));
		OreDictionary.registerOre("enderEye", new ItemStack(Items.ender_eye,1,0));
		OreDictionary.registerOre("elementalCore", new ItemStack(ItemsCore.genericItem,1,1));
		OreDictionary.registerOre("elementalCore", new ItemStack(ItemsCore.genericItem,1,42));
		OreDictionary.registerOre("plateDiamond", new ItemStack(ItemsCore.genericItem,1,21));
		OreDictionary.registerOre("plateEmerald", new ItemStack(ItemsCore.genericItem,1,22));
		OreDictionary.registerOre("frameMagic", new ItemStack(ItemsCore.genericItem,1,24));
		OreDictionary.registerOre("frameIron", new ItemStack(ItemsCore.genericItem,1,26));
		OreDictionary.registerOre("magicWater", new ItemStack(ItemsCore.genericItem,1,6));
		OreDictionary.registerOre("waterMagic", new ItemStack(ItemsCore.genericItem,1,6));
		OreDictionary.registerOre("ingotThaumium", new ItemStack(ItemsCore.genericItem,1,5));
		OreDictionary.registerOre("ingotMagic", new ItemStack(ItemsCore.genericItem,1,5));
		OreDictionary.registerOre("plateMagic", new ItemStack(ItemsCore.genericItem,1,34));
		OreDictionary.registerOre("plateMagic", new ItemStack(ItemsCore.genericItem,1,41));
		OreDictionary.registerOre("alloysMagical", new ItemStack(ItemsCore.genericItem,1,0));
		OreDictionary.registerOre("orbGold", new ItemStack(ItemsCore.genericItem,1,4));
		OreDictionary.registerOre("plateFortified", new ItemStack(ItemsCore.genericItem,1,7));
		OreDictionary.registerOre("plateEnder", new ItemStack(ItemsCore.genericItem,1,8));
		OreDictionary.registerOre("plateGlass", new ItemStack(ItemsCore.genericItem,1,9));
		OreDictionary.registerOre("ingotGoldMagical", new ItemStack(ItemsCore.genericItem,1,10));
		OreDictionary.registerOre("plateRedstone", new ItemStack(ItemsCore.genericItem,1,11));
		OreDictionary.registerOre("dustCrystal", new ItemStack(ItemsCore.genericItem,1,20));
		OreDictionary.registerOre("dustMagic", new ItemStack(ItemsCore.genericItem,1,3));
		OreDictionary.registerOre("rodHeat", new ItemStack(ItemsCore.genericItem,1,25));
		OreDictionary.registerOre("screenMagic", new ItemStack(ItemsCore.genericItem,1,27));
		OreDictionary.registerOre("mruLink", new ItemStack(ItemsCore.genericItem,1,28));
		OreDictionary.registerOre("mruCatcher", new ItemStack(ItemsCore.genericItem,1,29));
		OreDictionary.registerOre("conversionMatrix", new ItemStack(ItemsCore.genericItem,1,30));
		OreDictionary.registerOre("plateObsidian", new ItemStack(ItemsCore.genericItem,1,31));
		OreDictionary.registerOre("worldInteractor", new ItemStack(ItemsCore.genericItem,1,33));
		OreDictionary.registerOre("titanite", new ItemStack(ItemsCore.titanite,1,0));
		OreDictionary.registerOre("ttitanite", new ItemStack(ItemsCore.twinkling_titanite,1,0));
		OreDictionary.registerOre("titaniteTwinkling", new ItemStack(ItemsCore.twinkling_titanite,1,0));
		
		OreDictionary.registerOre("gemDiamond",new ItemStack(ItemsCore.genericItem,1,40));
		OreDictionary.registerOre("gemEmerald",new ItemStack(ItemsCore.genericItem,1,40));
		OreDictionary.registerOre("gemRuby",new ItemStack(ItemsCore.genericItem,1,40));
		OreDictionary.registerOre("gemSapphire",new ItemStack(ItemsCore.genericItem,1,40));
		OreDictionary.registerOre("gemPeridot",new ItemStack(ItemsCore.genericItem,1,40));
		
		OreDictionary.registerOre("plateVoid", new ItemStack(ItemsCore.genericItem,1,35));
		OreDictionary.registerOre("voidCore", new ItemStack(ItemsCore.genericItem,1,36));
		OreDictionary.registerOre("voidMRU", new ItemStack(ItemsCore.genericItem,1,37));
		
		OreDictionary.registerOre("focusFire", new ItemStack(ItemsCore.fFocus,1,0));
		OreDictionary.registerOre("focusWater", new ItemStack(ItemsCore.wFocus,1,0));
		OreDictionary.registerOre("focusEarth", new ItemStack(ItemsCore.eFocus,1,0));
		OreDictionary.registerOre("focusAir", new ItemStack(ItemsCore.aFocus,1,0));
		
		OreDictionary.registerOre("soulShard", new ItemStack(ItemsCore.storage,1,0));
		OreDictionary.registerOre("soulStone", new ItemStack(ItemsCore.storage,1,1));
		OreDictionary.registerOre("darkSoulMatter", new ItemStack(ItemsCore.storage,1,2));
		OreDictionary.registerOre("redSoulMatter", new ItemStack(ItemsCore.storage,1,3));
		OreDictionary.registerOre("matterOfEternity", new ItemStack(ItemsCore.storage,1,4));
		
		OreDictionary.registerOre("magnet", new ItemStack(ItemsCore.genericItem,1,43));
		OreDictionary.registerOre("resonatingCrystal", new ItemStack(ItemsCore.genericItem,1,44));
		
	}
	
	public void registerRecipes()
	{
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemsCore.drops,1,4), new Object[]{
			"shardFire","shardWater","shardEarth","shardAir"
		}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemsCore.mruMover1,1,0), new Object[]{
			"shardElemental","stickWood"
		}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemsCore.bound_gem,1,0), new Object[]{
			"shardElemental","gemQuartz"
		}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemsCore.genericItem,1,33), new Object[]{
			"ingotThaumium","ingotThaumium","ingotThaumium","ingotThaumium","ingotThaumium","ingotThaumium","ingotThaumium","ingotThaumium",new ItemStack(ItemsCore.genericItem,1,23)
		}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemsCore.air_potion,1,0), new Object[]{
			Items.potionitem,new ItemStack(ItemsCore.elementalFuel,1,3)
		}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemsCore.playerList,1,0), new Object[]{
			Items.paper,new ItemStack(ItemsCore.bound_gem,1,0)
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlocksCore.blockPale,1,0), new Object[]{
			"ISI",
			"SES",
			"ISI",
			'I',getItemByNameEC3("genericItem",39),
			'S',Blocks.lapis_block,
			'E',getItemByNameEC3("genericItem",38)
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.genericItem,1,1), new Object[]{
			"ISI",
			"SES",
			"ISI",
			'I',"ingotIron",
			'S',"stone",
			'E',"shardElemental"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.mruMover_t2,1,0), new Object[]{
			" SI",
			" ES",
			"E  ",
			'I',"gemDiamond",
			'S',"plateGlass",
			'E',"ingotMagic"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.genericItem,1,21), new Object[]{
			"ISI",
			" E ",
			"ISI",
			'I',"ingotIron",
			'S',"gemDiamond",
			'E',"shardElemental"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.genericItem,1,21), new Object[]{
			"I I",
			"SES",
			"I I",
			'I',"ingotIron",
			'S',"gemDiamond",
			'E',"shardElemental"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.genericItem,1,22), new Object[]{
			"ISI",
			" E ",
			"ISI",
			'I',"ingotIron",
			'S',"gemEmerald",
			'E',"shardElemental"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.genericItem,1,22), new Object[]{
			"I I",
			"SES",
			"I I",
			'I',"ingotIron",
			'S',"gemEmerald",
			'E',"shardElemental"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.genericItem,1,23), new Object[]{
			"ISI",
			"SES",
			"ISI",
			'I',"gemQuartz",
			'S',"shardElemental",
			'E',"enderEye"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.genericItem,3,24), new Object[]{
			"ISI",
			"S S",
			"ISI",
			'I',getItemByNameEC3("fortifiedGlass",0),
			'S',getItemByNameEC3("fortifiedStone",0)
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.genericItem,1,25), new Object[]{
			"ISI",
			"ISI",
			"ISI",
			'I',"shardFire",
			'S',Items.blaze_rod
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.genericItem,1,26), new Object[]{
			"ISI",
			"SIS",
			"ISI",
			'I',"ingotIron",
			'S',"shardElemental"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.genericItem,1,27), new Object[]{
			"I I",
			" S ",
			"I I",
			'I',"frameMagic",
			'S',"magicWater"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.genericItem,1,28), new Object[]{
			"SPS",
			"EGE",
			"GGG",
			'G',"ingotGold",
			'E',"gemEmerald",
			'P',"gemEnderPearl",
			'S',"shardElemental"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.genericItem,1,29), new Object[]{
			"GGG",
			"EGE",
			"SPS",
			'G',"ingotGold",
			'E',"enderEye",
			'P',"gemEnderPearl",
			'S',"shardElemental"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.genericItem,1,30), new Object[]{
			" I ",
			" S ",
			" I ",
			'I',"ingotThaumium",
			'S',"elementalCore"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.genericItem,1,31), new Object[]{
			" O ",
			"OMO",
			" O ",
			'O',"obsidian",
			'M',"frameMagic"
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlocksCore.matrixAbsorber,1,0), new Object[]{
			"SAS",
			"HEH",
			"SLS",
			'S',"frameIron",
			'A',"mruCatcher",
			'L',"mruLink",
			'E',"elementalCore",
			'H',"rodHeat"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlocksCore.radiatingChamber,1,0), new Object[]{
			"DAD",
			"ECE",
			"DHD",
			'D',"plateDiamond",
			'A',"mruCatcher",
			'H',"rodHeat",
			'E',getItemByNameEC3("genericItem",23),
			'C',"elementalCore"
		}));
		
		addRecipe(new ItemStack(BlocksCore.ecStateChecker,1,0),new Object[]{
			"frameMagic","screenMagic","frameMagic",
			"mruCatcher","elementalCore","conversionMatrix",
			"plateMagic","mruLink","plateMagic",
		});
		addRecipe(new ItemStack(BlocksCore.ecHoldingChamber,1,0),new Object[]{
			"frameMagic","mruCatcher","frameMagic",
			getItemByNameEC3("storage",2),"plateGlass",getItemByNameEC3("storage",2),
			"plateMagic","mruLink","plateMagic",
		});
		addRecipe(new ItemStack(BlocksCore.rayTower,1,0),new Object[]{
			"magicWater","conversionMatrix","magicWater",
			"plateFortified","mruCatcher","plateFortified",
			new ItemStack(BlocksCore.fortifiedStone,1,0),"screenMagic",new ItemStack(BlocksCore.fortifiedStone,1,0),
		});
		
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(BlocksCore.magicPlating,16,0), new Object[]{
			getItemByNameEC3("genericItem",34),getItemByNameEC3("genericItem",34),getItemByNameEC3("genericItem",34),getItemByNameEC3("genericItem",34)
		}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(BlocksCore.platingPale,16,0), new Object[]{
			getItemByNameEC3("genericItem",41),getItemByNameEC3("genericItem",41),getItemByNameEC3("genericItem",41),getItemByNameEC3("genericItem",41)
		}));
		addRecipe(new ItemStack(BlocksCore.potionSpreader,1,0),new Object[]{
			"worldInteractor","frameMagic","worldInteractor",
			"mruCatcher","elementalCore","screenMagic",
			"plateMagic","conversionMatrix","plateMagic",
		});
		addRecipe(new ItemStack(BlocksCore.magicalEnchanter,1,0),new Object[]{
			"screenMagic",new ItemStack(Items.enchanted_book,1,0),"mruCatcher",
			"worldInteractor",new ItemStack(Blocks.enchanting_table,1,0),"conversionMatrix",
			"plateMagic","elementalCore","plateMagic",
		});
		addRecipe(new ItemStack(BlocksCore.monsterHarvester,1,0),new Object[]{
			"plateMagic","screenMagic","mruCatcher",
			new ItemStack(ItemsCore.staffOfLife,1,0),"elementalCore","conversionMatrix",
			"worldInteractor","frameMagic","worldInteractor",
		});
		addRecipe(new ItemStack(BlocksCore.magicalRepairer,1,0),new Object[]{
			"frameMagic","screenMagic","mruCatcher",
			"conversionMatrix","elementalCore","conversionMatrix",
			"plateMagic","mruLink","worldInteractor",
		});
		addRecipe(new ItemStack(BlocksCore.ecAcceptor,1,0),new Object[]{
			"frameMagic","mruCatcher","frameMagic",
			"screenMagic","elementalCore","conversionMatrix",
			"plateMagic","mruLink","plateMagic",
		});
		addRecipe(new ItemStack(BlocksCore.solarPrism,1,0),new Object[]{
			"alloysMagical",getItemByNameEC3("genericItem",32),"alloysMagical",
			getItemByNameEC3("genericItem",32),getItemByNameEC3("genericItem",32),getItemByNameEC3("genericItem",32),
			"alloysMagical",getItemByNameEC3("genericItem",32),"alloysMagical",
		});
		addRecipe(new ItemStack(BlocksCore.ecController,1,0),new Object[]{
			"frameMagic","plateMagic","frameMagic",
			"conversionMatrix","elementalCore","worldInteractor",
			"mruLink","mruLink","mruLink",
		});
		addRecipe(new ItemStack(BlocksCore.ecEjector,1,0),new Object[]{
			"frameMagic","mruLink","frameMagic",
			"conversionMatrix","elementalCore","conversionMatrix",
			"plateMagic","mruLink","plateMagic",
		});
		addRecipe(new ItemStack(BlocksCore.coldStone,1,0),new Object[]{
			new ItemStack(Blocks.ice,1,0),new ItemStack(Blocks.snow,1,0),new ItemStack(Blocks.ice,1,0),
			new ItemStack(Blocks.snow,1,0),new ItemStack(Blocks.glowstone,1,0),new ItemStack(Blocks.snow,1,0),
			new ItemStack(Blocks.ice,1,0),new ItemStack(Blocks.snow,1,0),new ItemStack(Blocks.ice,1,0),
		});
		addRecipe(new ItemStack(BlocksCore.coldDistillator,1,0),new Object[]{
			"screenMagic","mruCatcher","conversionMatrix",
			new ItemStack(BlocksCore.coldStone),"elementalCore",new ItemStack(BlocksCore.coldStone),
			"plateMagic",new ItemStack(ItemsCore.matrixProj,1,2),"worldInteractor",
		});
		addRecipe(new ItemStack(BlocksCore.magmaticSmeltery,1,0),new Object[]{
			"mruCatcher","screenMagic","alloysMagical",
			"conversionMatrix","elementalCore","conversionMatrix",
			"rodHeat","worldInteractor","rodHeat",
		});
		addRecipe(new ItemStack(BlocksCore.magicalJukebox,1,0),new Object[]{
			"plateMagic","mruCatcher","worldInteractor",
			"elementalCore",new ItemStack(Blocks.jukebox,1,0),"shardElemental",
			"plateRedstone","screenMagic","plateRedstone",
		});
		addRecipe(new ItemStack(BlocksCore.crystalFormer,1,0),new Object[]{
			"screenMagic","mruCatcher","plateMagic",
			"dustCrystal","elementalCore","dustCrystal",
			"plateObsidian","conversionMatrix","plateObsidian",
		});
		addRecipe(new ItemStack(BlocksCore.crystalController,1,0),new Object[]{
			"screenMagic","mruCatcher","plateMagic",
			"dustCrystal","elementalCore","dustCrystal",
			"plateFortified","conversionMatrix","plateFortified",
		});
		addRecipe(new ItemStack(BlocksCore.naturalFurnace,1,0),new Object[]{
			"frameMagic","screenMagic","mruCatcher",
			"rodHeat","elementalCore","rodHeat",
			"plateMagic","worldInteractor","conversionMatrix",
		});
		addRecipe(new ItemStack(BlocksCore.heatGenerator,1,0),new Object[]{
			"plateMagic","screenMagic","conversionMatrix",
			"rodHeat","elementalCore","rodHeat",
			"frameMagic","worldInteractor","mruCatcher",
		});
		addRecipe(new ItemStack(BlocksCore.enderGenerator,1,0),new Object[]{
			"frameMagic","screenMagic","mruCatcher",
			"conversionMatrix","elementalCore","conversionMatrix",
			"plateMagic","worldInteractor","plateEnder",
		});
		addRecipe(new ItemStack(BlocksCore.magicianTable,1,0),new Object[]{
			"frameIron","mruCatcher","frameIron",
			"plateEmerald","elementalCore","plateEmerald",
			"plateObsidian","frameIron","plateObsidian",
		});
		addRecipe(new ItemStack(BlocksCore.magicalQuarry,1,0),new Object[]{
			"plateFortified","frameMagic","plateFortified",
			"screenMagic",new ItemStack(ItemsCore.magicalDigger,1,0),"mruCatcher",
			"worldInteractor","elementalCore","worldInteractor",
		});
		addRecipe(new ItemStack(BlocksCore.monsterClinger,1,0),new Object[]{
			"worldInteractor","elementalCore","worldInteractor",
			"screenMagic","conversionMatrix","mruCatcher",
			"frameMagic","plateFortified","frameMagic",
		});
		addRecipe(new ItemStack(BlocksCore.crystalExtractor,1,0),new Object[]{
			"screenMagic","dustCrystal","alloysMagical",
			"conversionMatrix","elementalCore","conversionMatrix",
			"plateFortified","dustCrystal","plateFortified",
		});
		addRecipe(new ItemStack(BlocksCore.chargingChamber,1,0),new Object[]{
			"elementalCore","mruCatcher","frameMagic",
			"screenMagic",new ItemStack(ItemsCore.storage,1,3),"conversionMatrix",
			"plateMagic","mruLink","plateMagic",
		});
		addRecipe(new ItemStack(BlocksCore.voidGlass,32,0),new Object[]{
			"titanite","frameMagic","titanite",
			"frameMagic","ttitanite","frameMagic",
			"titanite","frameMagic","titanite",
		});
		addRecipe(new ItemStack(BlocksCore.voidStone,16,0),new Object[]{
			"titanite","plateObsidian","titanite",
			"plateObsidian","ttitanite","plateObsidian",
			"titanite","plateObsidian","titanite",
		});
		addRecipe(new ItemStack(BlocksCore.sunRayAbsorber,1,0),new Object[]{
			"screenMagic",new ItemStack(ItemsCore.genericItem,1,32),"mruCatcher",
			"worldInteractor","elementalCore","conversionMatrix",
			"plateMagic",new ItemStack(ItemsCore.matrixProj,1,1),"plateMagic",
		});
		addRecipe(new ItemStack(BlocksCore.moonWell,1,0),new Object[]{
			"screenMagic",new ItemStack(BlocksCore.elementalCrystal,1,0),"mruCatcher",
			"worldInteractor","elementalCore","conversionMatrix",
			"plateMagic",new ItemStack(ItemsCore.matrixProj,1,3),"plateMagic",
		});
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.elemental_hoe,1,0), new Object[]{
			"DD ",
			" S ",
			" S ",
			'D',"shardElemental",
			'S',"stickWood"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.elemental_axe,1,0), new Object[]{
			"DD ",
			"DS ",
			" S ",
			'D',"shardElemental",
			'S',"stickWood"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.elemental_pick,1,0), new Object[]{
			"DDD",
			" S ",
			" S ",
			'D',"shardElemental",
			'S',"stickWood"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.elemental_shovel,1,0), new Object[]{
			" D ",
			" S ",
			" S ",
			'D',"shardElemental",
			'S',"stickWood"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.elemental_sword,1,0), new Object[]{
			" D ",
			" D ",
			" S ",
			'D',"shardElemental",
			'S',"stickWood"
		}));
		
		addRecipe(new ItemStack(ItemsCore.biomeWand,1,0),new Object[]{
			"redSoulMatter","focusFire","focusAir",
			"plateEmerald","ingotThaumium","focusWater",
			"focusEarth","plateEmerald","redSoulMatter",
		});
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.magicMonocle,1,0), new Object[]{
			" DS",
			" D ",
			" D ",
			'D',new ItemStack(ItemsCore.genericItem,1,10),
			'S',"plateGlass"
		}));
		
		addRecipe(new ItemStack(ItemsCore.spell,1,0),new Object[]{
			"plateFortified",getItemByNameEC3("genericItem",15),"plateFortified",
			getItemByNameEC3("genericItem",15),getItemByNameEC3("soulStone",0),getItemByNameEC3("genericItem",15),
			"plateFortified",getItemByNameEC3("genericItem",15),"plateFortified",
		});
		
		addRecipe(new ItemStack(ItemsCore.spell,1,1),new Object[]{
			"gemEmerald","dustMagic","gemEmerald",
			"dustMagic",getItemByNameEC3("spell",0),"dustMagic",
			"gemEmerald","dustMagic","gemEmerald",
		});
		
		addRecipe(new ItemStack(ItemsCore.spell,1,2),new Object[]{
			"plateDiamond","dustCrystal","plateDiamond",
			"dustCrystal",getItemByNameEC3("spell",1),"dustCrystal",
			"plateDiamond","dustCrystal","plateDiamond",
		});
		
		addRecipe(new ItemStack(ItemsCore.spell,1,3),new Object[]{
			"alloysMagical",getItemByNameEC3("spell",2),"alloysMagical",
			getItemByNameEC3("spell",2),"gemNetherStar",getItemByNameEC3("spell",2),
			"alloysMagical",getItemByNameEC3("spell",2),"alloysMagical",
		});
		
		addRecipe(new ItemStack(ItemsCore.spell,1,4),new Object[]{
			"alloysMagical",getItemByNameEC3("genericItem",16),"alloysMagical",
			getItemByNameEC3("genericItem",16),new ItemStack(BlocksCore.elementalCrystal,1,0),getItemByNameEC3("genericItem",16),
			"alloysMagical",getItemByNameEC3("genericItem",16),"alloysMagical",
		});
		
		addRecipe(new ItemStack(ItemsCore.spell,1,5),new Object[]{
			getItemByNameEC3("genericItem",23),"dustCrystal",getItemByNameEC3("genericItem",23),
			"dustCrystal",getItemByNameEC3("spell",4),"dustCrystal",
			getItemByNameEC3("genericItem",23),"dustCrystal",getItemByNameEC3("genericItem",23),
		});
		
		addRecipe(new ItemStack(ItemsCore.spell,1,6),new Object[]{
			getItemByNameEC3("matrixProj",3),getItemByNameEC3("spell",5),getItemByNameEC3("matrixProj",3),
			getItemByNameEC3("spell",5),"gemNetherStar",getItemByNameEC3("spell",5),
			getItemByNameEC3("matrixProj",3),getItemByNameEC3("spell",5),getItemByNameEC3("matrixProj",3),
		});
		
		addRecipe(new ItemStack(ItemsCore.spell,1,7),new Object[]{
			"alloysMagical","focusAir","alloysMagical",
			"focusFire","soulShard","focusFire",
			"alloysMagical",getItemByNameEC3("genericItem",23),"alloysMagical",
		});
		
		addRecipe(new ItemStack(ItemsCore.spell,1,8),new Object[]{
			getItemByNameEC3("matrixProj",3),getItemByNameEC3("spell",7),getItemByNameEC3("matrixProj",3),
			getItemByNameEC3("spell",7),"gemNetherStar",getItemByNameEC3("spell",7),
			getItemByNameEC3("matrixProj",3),getItemByNameEC3("spell",7),getItemByNameEC3("matrixProj",3),
		});
		
		addRecipe(new ItemStack(ItemsCore.staff,1,0),new Object[]{
			"ingotMagic",getItemByNameEC3("genericItem",16),getItemByNameEC3("genericItem",4),
			"plateMagic","ingotMagic",getItemByNameEC3("genericItem",16),
			"ingotMagic","plateMagic","ingotMagic",
		});
		
		addRecipe(new ItemStack(ItemsCore.staff,1,1),new Object[]{
			new ItemStack(ItemsCore.chaosFork,1,0),"titanite","ttitanite",
			"plateMagic",new ItemStack(ItemsCore.staff,1,0),"titanite",
			"matterOfEternity","plateMagic",new ItemStack(ItemsCore.frozenMace,1,0),
		});
		
		addRecipe(new ItemStack(ItemsCore.genericItem,1,17),new Object[]{
			"alloysMagical","plateGlass","alloysMagical",
			"plateEnder",Blocks.chest,"plateEnder",
			"alloysMagical","plateGlass","alloysMagical",
		});
		
		addRecipe(new ItemStack(ItemsCore.genericItem,1,18),new Object[]{
			"alloysMagical","plateRedstone","alloysMagical",
			"plateRedstone",Items.cake,"plateRedstone",
			"alloysMagical","plateRedstone","alloysMagical",
		});
		
		addRecipe(new ItemStack(ItemsCore.genericItem,1,19),new Object[]{
			"alloysMagical","plateEnder","alloysMagical",
			"plateEnder","dustCrystal","plateEnder",
			"alloysMagical","plateEnder","alloysMagical",
		});
		
		addRecipe(new ItemStack(ItemsCore.spawnerCollector,1,0),new Object[]{
			"dustCrystal","focusWater","focusWater",
			"ingotMagic","redSoulMatter","focusWater",
			"plateMagic","ingotMagic","dustCrystal",
		});
		
		addRecipe(new ItemStack(ItemsCore.magicalDigger,1,0),new Object[]{
			Blocks.tnt,"focusEarth","redSoulMatter",
			"focusFire",ItemsCore.elemental_pick,"focusEarth",
			"plateMagic","focusFire",Blocks.tnt,
		});
		
		addRecipe(new ItemStack(ItemsCore.staffOfLife,1,0),new Object[]{
			ItemsCore.elemental_hoe,"focusEarth",new ItemStack(ItemsCore.genericItem,1,4),
			"focusEarth","redSoulMatter","focusEarth",
			"plateMagic","focusEarth",ItemsCore.elemental_hoe,
		});
		
		addRecipe(new ItemStack(ItemsCore.emeraldHeart,1,0),new Object[]{
			"focusEarth","gemEmerald","focusEarth",
			"magicWater","redSoulMatter","magicWater",
			"focusWater",Items.apple,"focusWater",
		});
		addRecipe(new ItemStack(ItemsCore.magicalShield,1,0),new Object[]{
			"plateObsidian","alloysMagical","plateObsidian",
			"focusEarth","redSoulMatter","focusEarth",
			"dustCrystal","plateObsidian","dustCrystal",
		});
		addRecipe(new ItemStack(ItemsCore.spikyShield,1,0),new Object[]{
			ItemsCore.elemental_sword,"gemNetherStar",ItemsCore.elemental_sword,
			"alloysMagical",ItemsCore.magicalShield,"alloysMagical",
			"alloysMagical","matterOfEternity","alloysMagical",
		});
		addRecipe(new ItemStack(ItemsCore.magicWaterBottle,1,0),new Object[]{
			"focusAir","magicWater","focusEarth",
			"plateDiamond","redSoulMatter","plateDiamond",
			"focusWater","magicWater","focusWater",
		});
		addRecipe(new ItemStack(ItemsCore.magicalPorkchop,1,0),new Object[]{
			"focusEarth",Items.porkchop,"focusEarth",
			"dustCrystal","redSoulMatter","dustCrystal",
			"focusWater","dustCrystal","focusWater",
		});
		addRecipe(new ItemStack(ItemsCore.magicalWings,1,0),new Object[]{
			"plateMagic","focusAir","focusAir",
			"plateMagic",Items.feather,Items.feather,
			"redSoulMatter",Items.feather,Items.feather,
		});
		addRecipe(new ItemStack(ItemsCore.holyMace,1,0),new Object[]{
			"redSoulMatter","focusAir",new ItemStack(ItemsCore.genericItem,1,4),
			"ingotMagic",ItemsCore.elemental_sword,"focusAir",
			"focusEarth","ingotMagic","redSoulMatter",
		});
		addRecipe(new ItemStack(ItemsCore.chaosFork,1,0),new Object[]{
			"ingotMagic","focusFire","matterOfEternity",
			"ingotMagic",ItemsCore.elemental_sword,"focusFire",
			new ItemStack(ItemsCore.matrixProj,1,1),"ingotMagic","ingotMagic",
		});
		addRecipe(new ItemStack(ItemsCore.frozenMace,1,0),new Object[]{
			"ingotMagic","focusWater","matterOfEternity",
			"ingotMagic",ItemsCore.elemental_sword,"focusWater",
			new ItemStack(ItemsCore.matrixProj,1,2),"ingotMagic","ingotMagic",
		});
		
		addRecipe(new ItemStack(ItemsCore.windTablet,1,0),new Object[]{
			"focusAir","plateFortified","focusAir",
			"plateFortified",ItemsCore.windKeeper,"plateFortified",
			"focusAir","plateFortified","focusAir",
		});
		
		addRecipe(new ItemStack(BlocksCore.magicalTeleporter,1,0),new Object[]{
			"screenMagic","voidMRU","worldInteractor",
			"plateMagic","voidCore","plateMagic",
			"plateVoid","matterOfEternity","plateVoid",
		});
		
		addRecipe(new ItemStack(BlocksCore.magicalFurnace,1,0),new Object[]{
			"screenMagic","voidMRU","worldInteractor",
			"plateFortified","voidCore","plateFortified",
			"plateVoid","matterOfEternity","plateVoid",
		});
		
		addRecipe(new ItemStack(BlocksCore.emberForge,1,0),new Object[]{
			"screenMagic","voidMRU","plateEnder",
			"plateFortified","voidCore","plateFortified",
			"plateVoid","matterOfEternity","plateVoid",
		});
		
		addRecipe(new ItemStack(ItemsCore.magicArmorItems[0],1,0),new Object[]{
			"alloysMagical","worldInteractor","alloysMagical",
			"plateMagic","plateGlass","plateMagic",
			"dustCrystal","dustCrystal","dustCrystal",
		});
		
		addRecipe(new ItemStack(ItemsCore.magicArmorItems[1],1,0),new Object[]{
			"worldInteractor","dustCrystal","worldInteractor",
			"plateMagic","alloysMagical","plateMagic",
			"plateMagic","alloysMagical","plateMagic",
		});
		
		addRecipe(new ItemStack(ItemsCore.magicArmorItems[2],1,0),new Object[]{
			"alloysMagical","worldInteractor","alloysMagical",
			"plateMagic","dustCrystal","plateMagic",
			"plateMagic","dustCrystal","plateMagic",
		});
		
		addRecipe(new ItemStack(ItemsCore.magicArmorItems[3],1,0),new Object[]{
			"dustCrystal","worldInteractor","dustCrystal",
			"plateMagic","dustCrystal","plateMagic",
			"alloysMagical","dustCrystal","alloysMagical",
		});
		
		addRecipe(new ItemStack(ItemsCore.magicArmorItems[4],1,0),new Object[]{
			"gemNetherStar","voidMRU","gemNetherStar",
			"voidCore",new ItemStack(ItemsCore.magicArmorItems[0],1,0),"voidCore",
			"plateVoid","matterOfEternity","plateVoid",
		});
		
		addRecipe(new ItemStack(ItemsCore.magicArmorItems[5],1,0),new Object[]{
			"voidMRU","gemNetherStar","voidMRU",
			"voidCore",new ItemStack(ItemsCore.magicArmorItems[1],1,0),"voidCore",
			"plateVoid","matterOfEternity","plateVoid",
		});
		
		addRecipe(new ItemStack(ItemsCore.magicArmorItems[6],1,0),new Object[]{
			"gemNetherStar","voidMRU","gemNetherStar",
			"voidCore",new ItemStack(ItemsCore.magicArmorItems[2],1,0),"voidCore",
			"plateVoid","matterOfEternity","plateVoid",
		});
		
		addRecipe(new ItemStack(ItemsCore.magicArmorItems[7],1,0),new Object[]{
			"gemNetherStar","voidMRU","gemNetherStar",
			"voidCore",new ItemStack(ItemsCore.magicArmorItems[3],1,0),"voidCore",
			"plateVoid","matterOfEternity","plateVoid",
		});
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlocksCore.fence[0],16,0), new Object[]{
			"   ",
			"DDD",
			"DDD",
			'D',BlocksCore.voidStone
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlocksCore.fence[1],16,0), new Object[]{
			"   ",
			"DDD",
			"DDD",
			'D',BlocksCore.magicPlating
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlocksCore.fence[2],16,0), new Object[]{
			"   ",
			"DDD",
			"DDD",
			'D',BlocksCore.fortifiedStone
		}));
		
		addRecipe(new ItemStack(ItemsCore.magmaticStaff,1,0),new Object[]{
			"focusFire","focusFire",BlocksCore.magmaticSmeltery,
			"dustCrystal","matterOfEternity","focusFire",
			"plateMagic","dustCrystal","focusFire",
		});
		
		addRecipe(new ItemStack(ItemsCore.magicalLantern,1,0),new Object[]{
			"focusAir","focusFire","redSoulMatter",
			ItemsCore.magicalSlag,"plateMagic","focusFire",
			"plateMagic",ItemsCore.magicalSlag,"focusAir",
		});
		
		addRecipe(new ItemStack(ItemsCore.magnetizingStaff,1,0),new Object[]{
			"focusAir","orbGold","darkSoulMatter",
			ItemsCore.magicalSlag,"plateMagic","orbGold",
			"plateMagic",ItemsCore.magicalSlag,"focusAir",
		});
		
		addRecipe(new ItemStack(BlocksCore.mruCoilHardener,1,0),new Object[]{
			"plateMagic","elementalCore","plateMagic",
			"plateMagic","magnet","plateMagic",
			"plateMagic","mruLink","plateMagic",
		});
		
		addRecipe(new ItemStack(BlocksCore.mruCoil,1,0),new Object[]{
			"worldInteractor","resonatingCrystal","screenMagic",
			"plateMagic","magnet","plateMagic",
			"mruLink",new ItemStack(ItemsCore.matrixProj,1,3),"mruLink",
		});
		
		addRecipe(new ItemStack(BlocksCore.corruptionCleaner,1,0),new Object[]{
			"screenMagic","elementalCore","plateFortified",
			"plateRedstone","resonatingCrystal","plateEnder",
			"plateFortified","worldInteractor","plateFortified",
		});
		
		addRecipe(new ItemStack(BlocksCore.reactorSupport,2,0),new Object[]{
			"magnet","resonatingCrystal","magnet",
			"plateMagic","elementalCore","plateMagic",
			"plateMagic","plateEnder","plateMagic",
		});
		
		addRecipe(new ItemStack(BlocksCore.reactor,1,0),new Object[]{
			new ItemStack(ItemsCore.matrixProj,1,3),"gemNetherStar",new ItemStack(ItemsCore.matrixProj,1,3),
			"resonatingCrystal","magnet","resonatingCrystal",
			"plateMagic","plateMagic","plateMagic",
		});
		
		registerEFuelCrafts();
		registerCharmsCraft();
	}
	
	public void addRecipe(ItemStack output, Object... recipe)
	{
		GameRegistry.addRecipe(new ShapedOreRecipe(output, new Object[]{
			"123",
			"456",
			"789",
			'1',recipe[0],
			'2',recipe[1],
			'3',recipe[2],
			'4',recipe[3],
			'5',recipe[4],
			'6',recipe[5],
			'7',recipe[6],
			'8',recipe[7],
			'9',recipe[8],
		}));
	}
	
	public void registerRadiatingChamber()
	{
		RadiatingChamberRecipes.addRecipeIS(new ItemStack[]{new ItemStack(Blocks.stone),new ItemStack(Items.iron_ingot)}, getItemByNameEC3("fortifiedStone",0), 10, new float[]{Float.MAX_VALUE,Float.MIN_VALUE});
		RadiatingChamberRecipes.addRecipeIS(new ItemStack[]{new ItemStack(Blocks.glass),new ItemStack(Items.iron_ingot)}, getItemByNameEC3("fortifiedGlass",0), 10, new float[]{Float.MAX_VALUE,Float.MIN_VALUE});
		RadiatingChamberRecipes.addRecipeIS(new ItemStack[]{new ItemStack(Items.redstone),new ItemStack(Items.blaze_powder)}, getItemByNameEC3("genericItem",3), 100, new float[]{Float.MAX_VALUE,Float.MIN_VALUE});
		RadiatingChamberRecipes.addRecipeIS(new ItemStack[]{getItemByNameEC3("soulStone",0),null}, getItemByNameEC3("matrixProj",0), 1000, new float[]{Float.MAX_VALUE,Float.MIN_VALUE});
		RadiatingChamberRecipes.addRecipeIS(new ItemStack[]{getItemByNameEC3("matrixProj",0),null}, getItemByNameEC3("matrixProj",1), 10000, new float[]{Float.MAX_VALUE,1.5F});
		RadiatingChamberRecipes.addRecipeIS(new ItemStack[]{getItemByNameEC3("matrixProj",0),null}, getItemByNameEC3("matrixProj",2), 10000, new float[]{0.5F,Float.MIN_VALUE});
		RadiatingChamberRecipes.addRecipeIS(new ItemStack[]{getItemByNameEC3("matrixProj",0),null}, getItemByNameEC3("matrixProj",3), 20000, new float[]{1.49F,0.69F});
		RadiatingChamberRecipes.addRecipeIS(new ItemStack[]{new ItemStack(Items.dye,1,4),new ItemStack(Items.glowstone_dust)}, getItemByNameEC3("genericItem",38), 250, new float[]{Float.MAX_VALUE,Float.MIN_VALUE},20);
		RadiatingChamberRecipes.addRecipeIS(new ItemStack[]{new ItemStack(Blocks.lapis_block,1,0),new ItemStack(Items.gold_ingot)}, getItemByNameEC3("genericItem",39), 100, new float[]{Float.MAX_VALUE,Float.MIN_VALUE},80);
		RadiatingChamberRecipes.addRecipeIS(new ItemStack[]{new ItemStack(BlocksCore.blockPale,1,0),new ItemStack(Items.diamond)}, getItemByNameEC3("genericItem",40), 250, new float[]{Float.MAX_VALUE,Float.MIN_VALUE},120);
		RadiatingChamberRecipes.addRecipeIS(new ItemStack[]{new ItemStack(BlocksCore.blockPale,1,0),new ItemStack(Items.emerald)}, getItemByNameEC3("genericItem",40), 300, new float[]{Float.MAX_VALUE,Float.MIN_VALUE},100);
		RadiatingChamberRecipes.addRecipeIS(new ItemStack[]{new ItemStack(Blocks.stone),new ItemStack(ItemsCore.drops,1,4)}, getItemByNameEC3("genericItem",42), 100, new float[]{Float.MAX_VALUE,Float.MIN_VALUE},10);
		RadiatingChamberRecipes.addRecipeIS(new ItemStack[]{new ItemStack(Items.iron_ingot),new ItemStack(ItemsCore.genericItem,1,3)}, getItemByNameEC3("genericItem",43), 1000, new float[]{Float.MAX_VALUE,Float.MIN_VALUE},1);
		RadiatingChamberRecipes.addRecipeIS(new ItemStack[]{new ItemStack(Items.diamond),new ItemStack(Items.emerald)}, getItemByNameEC3("genericItem",44), 100, new float[]{Float.MAX_VALUE,Float.MIN_VALUE},50);
	}
	
	public void registerMagicianTable()
	{
		MagicianTableRecipes.addRecipeIS(new ItemStack[]{getItemByNameEC3("genericItem",7),getItemByNameEC3("genericItem",11),getItemByNameEC3("genericItem",8),getItemByNameEC3("genericItem",8),getItemByNameEC3("genericItem",11)},getItemByNameEC3("genericItem",0), 10000);
		MagicianTableRecipes.addRecipeIS(new ItemStack[]{getItemByNameEC3("genericItem",7),getItemByNameEC3("genericItem",8),getItemByNameEC3("genericItem",11),getItemByNameEC3("genericItem",11),getItemByNameEC3("genericItem",8)},getItemByNameEC3("genericItem",0), 10000);
		MagicianTableRecipes.addRecipeIS(new ItemStack[]{new ItemStack(Items.ender_pearl),getItemByNameEC3("genericItem",10),getItemByNameEC3("genericItem",10),getItemByNameEC3("genericItem",10),getItemByNameEC3("genericItem",10)},getItemByNameEC3("genericItem",4), 5000);
		MagicianTableRecipes.addRecipeIS(new ItemStack[]{new ItemStack(Items.iron_ingot),null,null,null,null},getItemByNameEC3("genericItem",5), 50);
		MagicianTableRecipes.addRecipeIS(new ItemStack[]{new ItemStack(Items.potionitem),null,null,null,null},getItemByNameEC3("genericItem",6), 250);
		for(int i = 0; i < 16; ++i)
			MagicianTableRecipes.addRecipeIS(new ItemStack[]{getItemByNameEC3("fortifiedStone",i),null,null,null,null},getItemByNameEC3("genericItem",7), 10);
		MagicianTableRecipes.addRecipeIS(new ItemStack[]{getItemByNameEC3("genericItem",7),new ItemStack(Items.ender_pearl),new ItemStack(Items.ender_pearl),new ItemStack(Items.ender_pearl),new ItemStack(Items.ender_pearl)},getItemByNameEC3("genericItem",8), 1000);
		MagicianTableRecipes.addRecipeIS(new ItemStack[]{getItemByNameEC3("genericItem",7),new ItemStack(Blocks.glass),new ItemStack(Blocks.glass),new ItemStack(Blocks.glass),new ItemStack(Blocks.glass)},getItemByNameEC3("genericItem",9), 1000);
		MagicianTableRecipes.addRecipeIS(new ItemStack[]{new ItemStack(Items.gold_ingot),new ItemStack(Items.gold_nugget),new ItemStack(Items.gold_nugget),new ItemStack(Items.gold_nugget),new ItemStack(Items.gold_nugget)},getItemByNameEC3("genericItem",10), 250);
		MagicianTableRecipes.addRecipeIS(new ItemStack[]{getItemByNameEC3("genericItem",7),new ItemStack(Items.redstone),new ItemStack(Items.redstone),new ItemStack(Items.redstone),new ItemStack(Items.redstone)},getItemByNameEC3("genericItem",11), 1000);
		MagicianTableRecipes.addRecipeIS(new ItemStack[]{new ItemStack(Items.quartz),null,null,null,null},getItemByNameEC3("genericItem",12), 10);
		MagicianTableRecipes.addRecipeIS(new ItemStack[]{getItemByNameEC3("genericItem",12),null,null,null,null},getItemByNameEC3("genericItem",13), 100);
		MagicianTableRecipes.addRecipeIS(new ItemStack[]{getItemByNameEC3("genericItem",13),null,null,null,null},getItemByNameEC3("genericItem",14), 200);
		MagicianTableRecipes.addRecipeIS(new ItemStack[]{getItemByNameEC3("genericItem",14),null,null,null,null},getItemByNameEC3("genericItem",15), 500);
		MagicianTableRecipes.addRecipeIS(new ItemStack[]{getItemByNameEC3("genericItem",15),null,null,null,null},getItemByNameEC3("genericItem",16), 1000);
		MagicianTableRecipes.addRecipeIS(new ItemStack[]{getItemByNameEC3("genericItem",3),null,null,null,null},getItemByNameEC3("genericItem",20), 3000);
		MagicianTableRecipes.addRecipeIS(new ItemStack[]{getItemByNameEC3("genericItem",9),getItemByNameEC3("genericItem",21),getItemByNameEC3("genericItem",22),getItemByNameEC3("genericItem",22),getItemByNameEC3("genericItem",21)},getItemByNameEC3("genericItem",32), 10000);
		MagicianTableRecipes.addRecipeIS(new ItemStack[]{getItemByNameEC3("genericItem",9),getItemByNameEC3("genericItem",22),getItemByNameEC3("genericItem",21),getItemByNameEC3("genericItem",21),getItemByNameEC3("genericItem",22)},getItemByNameEC3("genericItem",32), 10000);
		MagicianTableRecipes.addRecipeIS(new ItemStack[]{getItemByNameEC3("genericItem",7),getItemByNameEC3("genericItem",5),getItemByNameEC3("genericItem",5),getItemByNameEC3("genericItem",5),getItemByNameEC3("genericItem",5)},getItemByNameEC3("genericItem",34), 100);
		MagicianTableRecipes.addRecipeIS(new ItemStack[]{getItemByNameEC3("genericItem",7),getItemByNameEC3("genericItem",39),getItemByNameEC3("genericItem",39),getItemByNameEC3("genericItem",39),getItemByNameEC3("genericItem",39)},getItemByNameEC3("genericItem",41), 100);
		MagicianTableRecipes.addRecipeIS(new ItemStack[]{new ItemStack(Items.quartz),getItemByNameEC3("genericItem",12),getItemByNameEC3("genericItem",12),getItemByNameEC3("genericItem",12),getItemByNameEC3("genericItem",12)},getItemByNameEC3("storage",0), 100);
		MagicianTableRecipes.addRecipeIS(new ItemStack[]{new ItemStack(Items.emerald),getItemByNameEC3("genericItem",13),getItemByNameEC3("genericItem",13),getItemByNameEC3("genericItem",13),getItemByNameEC3("genericItem",13)},getItemByNameEC3("storage",1), 500);
		MagicianTableRecipes.addRecipeIS(new ItemStack[]{new ItemStack(Items.ender_pearl),getItemByNameEC3("genericItem",14),getItemByNameEC3("genericItem",14),getItemByNameEC3("genericItem",14),getItemByNameEC3("genericItem",14)},getItemByNameEC3("storage",2), 100);
		MagicianTableRecipes.addRecipeIS(new ItemStack[]{new ItemStack(Items.diamond),getItemByNameEC3("genericItem",15),getItemByNameEC3("genericItem",15),getItemByNameEC3("genericItem",15),getItemByNameEC3("genericItem",15)},getItemByNameEC3("storage",3), 250);
		MagicianTableRecipes.addRecipeIS(new ItemStack[]{new ItemStack(Items.nether_star),getItemByNameEC3("genericItem",16),getItemByNameEC3("genericItem",16),getItemByNameEC3("genericItem",16),getItemByNameEC3("genericItem",16)},getItemByNameEC3("storage",4), 500);
	}
	
	public static ItemStack getItemByNameEC3(String itemName, int metadata)
	{
		Class blocks = BlocksCore.class;
		try {
			Field block = blocks.getDeclaredField(itemName);
			ItemStack is = new ItemStack((Block) block.get(null),1,metadata);
			return is;
		} catch (Exception e) {
			try {
				Class items = ItemsCore.class;
				Field item = items.getDeclaredField(itemName);
				ItemStack is = new ItemStack((Item) item.get(null),1,metadata);
				return is;
			} catch (Exception e1) {
				e.printStackTrace();
				e1.printStackTrace();
				return null;
			}
		}
	}
	
	public void registerEFuelCrafts()
	{
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.elementalFuel,1,0), new Object[]{
		" E ","ECE"," E ", 'E', new ItemStack(ItemsCore.essence,1,0),'C',Items.coal
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.elementalFuel,4,0), new Object[]{
		" E ","ECE"," E ", 'E', new ItemStack(ItemsCore.essence,1,4),'C',Items.coal
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.elementalFuel,8,0), new Object[]{
		" E ","ECE"," E ", 'E', new ItemStack(ItemsCore.essence,1,8),'C',Items.coal
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.elementalFuel,16,0), new Object[]{
		" E ","ECE"," E ", 'E', new ItemStack(ItemsCore.essence,1,12),'C',Items.coal
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.elementalFuel,1,1), new Object[]{
		" E ","ECE"," E ", 'E', new ItemStack(ItemsCore.essence,1,1),'C',Items.snowball
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.elementalFuel,4,1), new Object[]{
		" E ","ECE"," E ", 'E', new ItemStack(ItemsCore.essence,1,5),'C',Items.snowball
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.elementalFuel,8,1), new Object[]{
		" E ","ECE"," E ", 'E', new ItemStack(ItemsCore.essence,1,9),'C',Items.snowball
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.elementalFuel,16,1), new Object[]{
		" E ","ECE"," E ", 'E', new ItemStack(ItemsCore.essence,1,13),'C',Items.snowball
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.elementalFuel,1,2), new Object[]{
		" E ","ECE"," E ", 'E', new ItemStack(ItemsCore.essence,1,2),'C',Items.wheat_seeds
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.elementalFuel,4,2), new Object[]{
		" E ","ECE"," E ", 'E', new ItemStack(ItemsCore.essence,1,6),'C',Items.wheat_seeds
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.elementalFuel,8,2), new Object[]{
		" E ","ECE"," E ", 'E', new ItemStack(ItemsCore.essence,1,10),'C',Items.wheat_seeds
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.elementalFuel,16,2), new Object[]{
		" E ","ECE"," E ", 'E', new ItemStack(ItemsCore.essence,1,14),'C',Items.wheat_seeds
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.elementalFuel,1,3), new Object[]{
		" E ","ECE"," E ", 'E', new ItemStack(ItemsCore.essence,1,3)
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.elementalFuel,4,3), new Object[]{
		" E ","ECE"," E ", 'E', new ItemStack(ItemsCore.essence,1,7)
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.elementalFuel,8,3), new Object[]{
		" E ","ECE"," E ", 'E', new ItemStack(ItemsCore.essence,1,11)
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.elementalFuel,16,3), new Object[]{
		" E ","ECE"," E ", 'E', new ItemStack(ItemsCore.essence,1,15)
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.fFocus,1,0), new Object[]{
		"GIG","IEI","GIG", 'E', new ItemStack(ItemsCore.elementalFuel,1,0),'G',Items.gold_ingot,'I',Items.iron_ingot
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.wFocus,1,0), new Object[]{
		"GIG","IEI","GIG", 'E', new ItemStack(ItemsCore.elementalFuel,1,1),'G',Items.gold_ingot,'I',Items.iron_ingot
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.eFocus,1,0), new Object[]{
		"GIG","IEI","GIG", 'E', new ItemStack(ItemsCore.elementalFuel,1,2),'G',Items.gold_ingot,'I',Items.iron_ingot
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.aFocus,1,0), new Object[]{
		"GIG","IEI","GIG", 'E', new ItemStack(ItemsCore.elementalFuel,1,3),'G',Items.gold_ingot,'I',Items.iron_ingot
		}));
	}
	
	public void registerCharmsCraft()
	{
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.charm,1,0), new Object[]{
		"SGS","FRF","@G@",
		'F',ItemsCore.fFocus,'W',ItemsCore.wFocus,'E',ItemsCore.eFocus,'A',ItemsCore.aFocus,
		'S',Items.string,'G',getItemByNameEC3("genericItem",10),'R',new ItemStack(ItemsCore.storage,1,3),'@',"magicWater"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.charm,1,1), new Object[]{
		"SGS","WRW","@G@",
		'F',ItemsCore.fFocus,'W',ItemsCore.wFocus,'E',ItemsCore.eFocus,'A',ItemsCore.aFocus,
		'S',Items.string,'G',getItemByNameEC3("genericItem",10),'R',new ItemStack(ItemsCore.storage,1,3),'@',"magicWater"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.charm,1,2), new Object[]{
		"SGS","ERE","@G@",
		'F',ItemsCore.fFocus,'W',ItemsCore.wFocus,'E',ItemsCore.eFocus,'A',ItemsCore.aFocus,
		'S',Items.string,'G',getItemByNameEC3("genericItem",10),'R',new ItemStack(ItemsCore.storage,1,3),'@',"magicWater"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.charm,1,3), new Object[]{
		"SGS","ARA","@G@",
		'F',ItemsCore.fFocus,'W',ItemsCore.wFocus,'E',ItemsCore.eFocus,'A',ItemsCore.aFocus,
		'S',Items.string,'G',getItemByNameEC3("genericItem",10),'R',new ItemStack(ItemsCore.storage,1,3),'@',"magicWater"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.charm,1,4), new Object[]{
		"SGS","FRA","@G@",
		'F',ItemsCore.fFocus,'W',ItemsCore.wFocus,'E',ItemsCore.eFocus,'A',ItemsCore.aFocus,
		'S',Items.string,'G',getItemByNameEC3("genericItem",10),'R',new ItemStack(ItemsCore.storage,1,3),'@',"magicWater"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.charm,1,4), new Object[]{
		"SGS","ARF","@G@",
		'F',ItemsCore.fFocus,'W',ItemsCore.wFocus,'E',ItemsCore.eFocus,'A',ItemsCore.aFocus,
		'S',Items.string,'G',getItemByNameEC3("genericItem",10),'R',new ItemStack(ItemsCore.storage,1,3),'@',"magicWater"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.charm,1,5), new Object[]{
		"SGS","FRE","@G@",
		'F',ItemsCore.fFocus,'W',ItemsCore.wFocus,'E',ItemsCore.eFocus,'A',ItemsCore.aFocus,
		'S',Items.string,'G',getItemByNameEC3("genericItem",10),'R',new ItemStack(ItemsCore.storage,1,3),'@',"magicWater"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.charm,1,5), new Object[]{
		"SGS","ERF","@G@",
		'F',ItemsCore.fFocus,'W',ItemsCore.wFocus,'E',ItemsCore.eFocus,'A',ItemsCore.aFocus,
		'S',Items.string,'G',getItemByNameEC3("genericItem",10),'R',new ItemStack(ItemsCore.storage,1,3),'@',"magicWater"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.charm,1,6), new Object[]{
		"SGS","FRW","@G@",
		'F',ItemsCore.fFocus,'W',ItemsCore.wFocus,'E',ItemsCore.eFocus,'A',ItemsCore.aFocus,
		'S',Items.string,'G',getItemByNameEC3("genericItem",10),'R',new ItemStack(ItemsCore.storage,1,3),'@',"magicWater"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.charm,1,6), new Object[]{
		"SGS","WRF","@G@",
		'F',ItemsCore.fFocus,'W',ItemsCore.wFocus,'E',ItemsCore.eFocus,'A',ItemsCore.aFocus,
		'S',Items.string,'G',getItemByNameEC3("genericItem",10),'R',new ItemStack(ItemsCore.storage,1,3),'@',"magicWater"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.charm,1,7), new Object[]{
		"SGS","ERW","@G@",
		'F',ItemsCore.fFocus,'W',ItemsCore.wFocus,'E',ItemsCore.eFocus,'A',ItemsCore.aFocus,
		'S',Items.string,'G',getItemByNameEC3("genericItem",10),'R',new ItemStack(ItemsCore.storage,1,3),'@',"magicWater"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.charm,1,7), new Object[]{
		"SGS","WRE","@G@",
		'F',ItemsCore.fFocus,'W',ItemsCore.wFocus,'E',ItemsCore.eFocus,'A',ItemsCore.aFocus,
		'S',Items.string,'G',getItemByNameEC3("genericItem",10),'R',new ItemStack(ItemsCore.storage,1,3),'@',"magicWater"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.charm,1,8), new Object[]{
		"SGS","WRA","@G@",
		'F',ItemsCore.fFocus,'W',ItemsCore.wFocus,'E',ItemsCore.eFocus,'A',ItemsCore.aFocus,
		'S',Items.string,'G',getItemByNameEC3("genericItem",10),'R',new ItemStack(ItemsCore.storage,1,3),'@',"magicWater"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.charm,1,8), new Object[]{
		"SGS","ARW","@G@",
		'F',ItemsCore.fFocus,'W',ItemsCore.wFocus,'E',ItemsCore.eFocus,'A',ItemsCore.aFocus,
		'S',Items.string,'G',getItemByNameEC3("genericItem",10),'R',new ItemStack(ItemsCore.storage,1,3),'@',"magicWater"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.charm,1,9), new Object[]{
		"SGS","ERA","@G@",
		'F',ItemsCore.fFocus,'W',ItemsCore.wFocus,'E',ItemsCore.eFocus,'A',ItemsCore.aFocus,
		'S',Items.string,'G',getItemByNameEC3("genericItem",10),'R',new ItemStack(ItemsCore.storage,1,3),'@',"magicWater"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.charm,1,9), new Object[]{
		"SGS","ARE","@G@",
		'F',ItemsCore.fFocus,'W',ItemsCore.wFocus,'E',ItemsCore.eFocus,'A',ItemsCore.aFocus,
		'S',Items.string,'G',getItemByNameEC3("genericItem",10),'R',new ItemStack(ItemsCore.storage,1,3),'@',"magicWater"
		}));
	}
}

