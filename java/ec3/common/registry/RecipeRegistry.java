package ec3.common.registry;

import java.lang.reflect.Field;

import net.minecraft.block.Block;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import DummyCore.Utils.MiscUtils;
import DummyCore.Utils.UnformedItemStack;
import cpw.mods.fml.common.registry.GameRegistry;
import ec3.api.DemonTrade;
import ec3.api.MagicianTableRecipes;
import ec3.api.MithrilineFurnaceRecipes;
import ec3.api.RadiatingChamberRecipes;
import ec3.api.WindImbueRecipe;
import ec3.common.block.BlocksCore;
import ec3.common.entity.EntityWindMage;
import ec3.common.item.ItemsCore;
import ec3.utils.common.RecipeArmorDyesHandler;
import static ec3.utils.cfg.Config.allowPaleItemsInOtherRecipes;

public class RecipeRegistry {
	public static RecipeRegistry instance;
	public boolean hasGregTech = false;
	@SuppressWarnings("unused")
	private Class<?> GT_Class;

	@SuppressWarnings("unchecked")
	public void main()
	{
		registerDictionary();
		registerRecipes();
		registerMagicianTable();
		registerRadiatingChamber();
		registerMithrilineFurnace();
		registerWindRecipes();
		registerDemonTrades();
		CraftingManager.getInstance().getRecipeList().add(new RecipeArmorDyesHandler());
	}
	
	public void registerDictionary()
	{
		//TODO OreDict
		OreDictionary.registerOre("shardFire", new ItemStack(ItemsCore.drops,1,0));
		OreDictionary.registerOre("shardWater", new ItemStack(ItemsCore.drops,1,1));
		OreDictionary.registerOre("shardEarth", new ItemStack(ItemsCore.drops,1,2));
		OreDictionary.registerOre("shardAir", new ItemStack(ItemsCore.drops,1,3));
		OreDictionary.registerOre("shardElemental", new ItemStack(ItemsCore.drops,1,4));
		OreDictionary.registerOre("gemCoal", new ItemStack(Items.coal,1,0));
		OreDictionary.registerOre("gemNetherStar", new ItemStack(Items.nether_star,1,0));
		OreDictionary.registerOre("obsidian", new ItemStack(Blocks.obsidian,1,0));
		OreDictionary.registerOre("gemEnderPearl", new ItemStack(Items.ender_pearl,1,0));
		OreDictionary.registerOre("itemFeather", new ItemStack(Items.feather,1,0));
		OreDictionary.registerOre("itemBook", new ItemStack(Items.book,1,0));
		
		if(allowPaleItemsInOtherRecipes)
		{
			OreDictionary.registerOre("gemEnderPearl",new ItemStack(ItemsCore.genericItem,1,38));
			OreDictionary.registerOre("enderPearl",new ItemStack(ItemsCore.genericItem,1,38));
			OreDictionary.registerOre("itemEnderPearl",new ItemStack(ItemsCore.genericItem,1,38));
			OreDictionary.registerOre("pearlEnder",new ItemStack(ItemsCore.genericItem,1,38));
		}
		
		OreDictionary.registerOre("ec3:gemEnderPearl", new ItemStack(Items.ender_pearl,1,0));
		OreDictionary.registerOre("ec3:gemEnderPearl", new ItemStack(ItemsCore.genericItem,1,38));
		
		if(allowPaleItemsInOtherRecipes)
			OreDictionary.registerOre("ingotGold",new ItemStack(ItemsCore.genericItem,1,39));
		
		OreDictionary.registerOre("ec3:ingotGold",new ItemStack(ItemsCore.genericItem,1,39));
		OreDictionary.registerOre("ec3:ingotGold",new ItemStack(Items.gold_ingot));
		
		OreDictionary.registerOre("enderEye", new ItemStack(Items.ender_eye,1,0));
		OreDictionary.registerOre("elementalCore", new ItemStack(ItemsCore.genericItem,1,1));
		OreDictionary.registerOre("elementalCore", new ItemStack(ItemsCore.genericItem,1,42));
		OreDictionary.registerOre("elementalCore", new ItemStack(ItemsCore.genericItem,1,53));
		OreDictionary.registerOre("demonicCore", new ItemStack(ItemsCore.genericItem,1,53));
		
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
		OreDictionary.registerOre("platePale", new ItemStack(ItemsCore.genericItem,1,41));
		OreDictionary.registerOre("plateMagic", new ItemStack(ItemsCore.genericItem,1,49));
		OreDictionary.registerOre("plateMagic", new ItemStack(ItemsCore.genericItem,1,54));
		OreDictionary.registerOre("plateDemonic", new ItemStack(ItemsCore.genericItem,1,54));
		OreDictionary.registerOre("ingotDemonic", new ItemStack(ItemsCore.genericItem,1,52));
		
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
		
		OreDictionary.registerOre("gemPale",new ItemStack(ItemsCore.genericItem,1,40));
		
		if(allowPaleItemsInOtherRecipes)
		{
			OreDictionary.registerOre("gemDiamond",new ItemStack(ItemsCore.genericItem,1,40));
			OreDictionary.registerOre("gemEmerald",new ItemStack(ItemsCore.genericItem,1,40));
			OreDictionary.registerOre("gemRuby",new ItemStack(ItemsCore.genericItem,1,40));
			OreDictionary.registerOre("gemSapphire",new ItemStack(ItemsCore.genericItem,1,40));
			OreDictionary.registerOre("gemPeridot",new ItemStack(ItemsCore.genericItem,1,40));
		}
		
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
		OreDictionary.registerOre("gemResonant", new ItemStack(ItemsCore.genericItem,1,44));
		OreDictionary.registerOre("crystalResonant", new ItemStack(ItemsCore.genericItem,1,44));
		
		OreDictionary.registerOre("coreLapis", new ItemStack(ItemsCore.genericItem,1,45));
		OreDictionary.registerOre("dustFading", new ItemStack(ItemsCore.genericItem,1,46));
		OreDictionary.registerOre("gemFading", new ItemStack(ItemsCore.genericItem,1,47));
		OreDictionary.registerOre("gemMithriline", new ItemStack(ItemsCore.genericItem,1,48));
		OreDictionary.registerOre("plateMithriline", new ItemStack(ItemsCore.genericItem,1,49));
		OreDictionary.registerOre("ingotMithriline", new ItemStack(ItemsCore.genericItem,1,50));
		OreDictionary.registerOre("dustMithriline", new ItemStack(ItemsCore.genericItem,1,51));
		OreDictionary.registerOre("gemWind", new ItemStack(ItemsCore.genericItem,1,55));
		
		OreDictionary.registerOre("blockElemental", new ItemStack(BlocksCore.compressed,1,4));
		
	}
	
	public void registerRecipes()
	{
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemsCore.drops,4,4), new Object[]{
			"shardFire","shardWater","shardEarth","shardAir"
		}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemsCore.mruMover1,1,0), new Object[]{
			"shardElemental","stickWood"
		}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemsCore.magicalChisel,1,0), new Object[]{
			"shardElemental","shardElemental","shardElemental","shardElemental","stickWood"
		}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemsCore.bound_gem,1,0), new Object[]{
			"shardElemental","gemQuartz"
		}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemsCore.genericItem,1,33), new Object[]{
			"ingotThaumium","ingotThaumium","ingotThaumium","ingotThaumium","ingotThaumium","ingotThaumium","ingotThaumium","ingotThaumium",new ItemStack(ItemsCore.genericItem,1,23)
		}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemsCore.playerList,1,0), new Object[]{
			Items.paper,new ItemStack(ItemsCore.bound_gem,1,0)
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.genericItem,1,50), new Object[]{
			" I ",
			"ISI",
			" I ",
			'I',"dustMithriline",
			'S',"ingotThaumium"
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.genericItem,1,48), new Object[]{
			" I ",
			"ISI",
			" I ",
			'S',"dustMithriline",
			'I',"ingotMithriline"
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlocksCore.holopad), new Object[]{
			"TVT",
			"TET",
			"THT",
			'T',ItemsCore.twinkling_titanite,
			'V',gen(36),
			'H',gen(59),
			'E',new ItemStack(ItemsCore.storage,1,4)
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlocksCore.blockPale,1,0), new Object[]{
			"ISI",
			"SES",
			"ISI",
			'I',getItemByNameEC3("genericItem",39),
			'S',Blocks.lapis_block,
			'E',getItemByNameEC3("genericItem",38)
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlocksCore.furnaceMagic,1,0), new Object[]{
			"III",
			"IEI",
			"III",
			'I',new ItemStack(ItemsCore.genericItem,1,7),
			'E',"rodHeat"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlocksCore.furnaceMagic,1,4), new Object[]{
			"III",
			"IEI",
			"III",
			'I',new ItemStack(ItemsCore.genericItem,1,34),
			'E',new ItemStack(BlocksCore.furnaceMagic,1,0)
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlocksCore.furnaceMagic,1,8), new Object[]{
			"III",
			"IEI",
			"III",
			'I',new ItemStack(ItemsCore.genericItem,1,41),
			'E',new ItemStack(BlocksCore.furnaceMagic,1,4)
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlocksCore.furnaceMagic,1,12), new Object[]{
			"III",
			"IEI",
			"III",
			'I',new ItemStack(ItemsCore.genericItem,1,35),
			'E',new ItemStack(BlocksCore.furnaceMagic,1,8)
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
			'I',"ingotIron",
			'S',"shardElemental",
			'E',"ec3:gemEnderPearl"
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
			'S',Items.iron_ingot
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.genericItem,4,26), new Object[]{
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
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.genericItem,8,28), new Object[]{
			"SPS",
			"EGE",
			"GGG",
			'G',"ec3:ingotGold",
			'E',"gemEmerald",
			'P',"ec3:gemEnderPearl",
			'S',"shardElemental"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.genericItem,8,29), new Object[]{
			"GGG",
			"EGE",
			"SPS",
			'G',"ec3:ingotGold",
			'E',"ec3:gemEnderPearl",
			'P',"ec3:gemEnderPearl",
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
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.controlRod,1,0), new Object[]{
			"DII",
			" CI",
			"I D",
			'I',"ingotIron",
			'C',"elementalCore",
			'D',"waterMagic"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlocksCore.magicalMirror,3,0), new Object[]{
			" P ",
			"PGP",
			" P ",
			'P',"plateMagic",
			'G',"plateGlass"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.filter,1,0), new Object[]{
			" P ",
			"PGP",
			" P ",
			'P',"plateFortified",
			'G',Blocks.crafting_table
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.filter,1,1), new Object[]{
			" P ",
			"PGP",
			" P ",
			'P',"plateVoid",
			'G',ItemsCore.filter
		}));
		addRecipe(new ItemStack(ItemsCore.magicalBuilder,1,0),new Object[]{
			new ItemStack(BlocksCore.rightClicker,1,OreDictionary.WILDCARD_VALUE),"plateVoid","voidCore",
			new ItemStack(ItemsCore.filter,1,0),new ItemStack(ItemsCore.magicalDigger,1,0),"plateVoid",
			"plateMagic",new ItemStack(ItemsCore.filter,1,0),new ItemStack(BlocksCore.rightClicker,1,OreDictionary.WILDCARD_VALUE)
		});
		GameRegistry.addShapelessRecipe(new ItemStack(ItemsCore.magicalBuilder,1,1), new ItemStack(ItemsCore.magicalBuilder,1,0));
		GameRegistry.addShapelessRecipe(new ItemStack(ItemsCore.magicalBuilder,1,2), new ItemStack(ItemsCore.magicalBuilder,1,1));
		GameRegistry.addShapelessRecipe(new ItemStack(ItemsCore.magicalBuilder,1,3), new ItemStack(ItemsCore.magicalBuilder,1,2));
		GameRegistry.addShapelessRecipe(new ItemStack(ItemsCore.magicalBuilder,1,4), new ItemStack(ItemsCore.magicalBuilder,1,3));
		GameRegistry.addShapelessRecipe(new ItemStack(ItemsCore.magicalBuilder,1,0), new ItemStack(ItemsCore.magicalBuilder,1,4));
		
		addRecipe(new ItemStack(BlocksCore.ecStateChecker,1,0),new Object[]{
			"frameMagic","screenMagic","frameMagic",
			"mruCatcher","elementalCore","conversionMatrix",
			"plateMagic","mruLink","plateMagic",
		});
		addRecipe(new ItemStack(BlocksCore.assembler,1,0),new Object[]{
			"plateMagic","screenMagic","plateMagic",
			"conversionMatrix","elementalCore","conversionMatrix",
			"plateMagic","mruLink","plateMagic",
		});
		addRecipe(new ItemStack(BlocksCore.mithrilineCrystal,1,0),new Object[]{
			"dustMithriline","enderEye","dustMithriline",
			"dustMithriline","gemMithriline","dustMithriline",
			"dustMithriline","ec3:gemEnderPearl","dustMithriline",
		});
		addRecipe(new ItemStack(BlocksCore.mithrilineCrystal,1,3),new Object[]{
			"dustFading","gemFading","dustFading",
			"dustFading",new ItemStack(BlocksCore.mithrilineCrystal,1,0),"dustFading",
			"dustFading","gemFading","dustFading",
		});
		addRecipe(new ItemStack(BlocksCore.mithrilineCrystal,1,6),new Object[]{
			"plateVoid","voidCore","plateVoid",
			"plateVoid",new ItemStack(BlocksCore.mithrilineCrystal,1,3),"plateVoid",
			"plateVoid","voidMRU","plateVoid",
		});
		addRecipe(new ItemStack(BlocksCore.mithrilineCrystal,1,9),new Object[]{
			"ingotDemonic","demonicCore","ingotDemonic",
			"ingotDemonic",new ItemStack(BlocksCore.mithrilineCrystal,1,6),"ingotDemonic",
			"ingotDemonic","demonicCore","ingotDemonic",
		});
		addRecipe(new ItemStack(BlocksCore.ecBalancer,1,0),new Object[]{
			"plateMagic","alloysMagical","plateMagic",
			"conversionMatrix","elementalCore","conversionMatrix",
			"plateMagic","mruCatcher","plateMagic",
		});
		addRecipe(new ItemStack(BlocksCore.ecRedstoneController,1,0),new Object[]{
			"plateMagic","plateRedstone","plateMagic",
			"conversionMatrix","elementalCore","conversionMatrix",
			"plateMagic","plateRedstone","plateMagic",
		});
		addRecipe(new ItemStack(ItemsCore.genericItem,1,53),new Object[]{
			"plateDemonic","shardElemental","plateDemonic",
			"shardElemental","resonatingCrystal","shardElemental",
			"plateDemonic","shardElemental","plateDemonic",
		});
		addRecipe(new ItemStack(BlocksCore.ecHoldingChamber,1,0),new Object[]{
			"frameMagic","mruCatcher","frameMagic",
			getItemByNameEC3("storage",2),"plateGlass",getItemByNameEC3("storage",2),
			"plateMagic","mruLink","plateMagic",
		});
		addRecipe(new ItemStack(BlocksCore.rayTower,4,0),new Object[]{
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
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(BlocksCore.invertedBlock,16,0), new Object[]{
			getItemByNameEC3("genericItem",49),getItemByNameEC3("genericItem",49),getItemByNameEC3("genericItem",49),getItemByNameEC3("genericItem",49)
		}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(BlocksCore.demonicPlating,16,0), new Object[]{
			getItemByNameEC3("genericItem",54),getItemByNameEC3("genericItem",54),getItemByNameEC3("genericItem",54),getItemByNameEC3("genericItem",54)
		}));
		addRecipe(new ItemStack(BlocksCore.potionSpreader,1,0),1000,new Object[]{
			"worldInteractor","frameMagic","worldInteractor",
			"mruCatcher","elementalCore","screenMagic",
			"plateMagic","conversionMatrix","plateMagic",
		});
		addRecipe(new ItemStack(BlocksCore.magicalEnchanter,1,0),5000,new Object[]{
			"screenMagic",new ItemStack(Items.enchanted_book,1,0),"mruCatcher",
			"worldInteractor",new ItemStack(Blocks.enchanting_table,1,0),"conversionMatrix",
			"plateMagic","elementalCore","plateMagic",
		});
		addRecipe(new ItemStack(BlocksCore.monsterHarvester,1,0),10000,new Object[]{
			"plateMagic","screenMagic","mruCatcher",
			new ItemStack(ItemsCore.staffOfLife,1,0),"elementalCore","conversionMatrix",
			"worldInteractor","frameMagic","worldInteractor",
		});
		addRecipe(new ItemStack(BlocksCore.magicalRepairer,1,0),2000,new Object[]{
			"frameMagic","screenMagic","mruCatcher",
			"conversionMatrix","elementalCore","conversionMatrix",
			"plateMagic","mruLink","worldInteractor",
		});
		/*
		addRecipe(new ItemStack(BlocksCore.minEjector,2,0),2000,new Object[]{
			"frameMagic","screenMagic","frameMagic",
			"frameMagic","elementalCore","frameMagic",
			getItemByNameEC3("genericItem",34),"conversionMatrix",getItemByNameEC3("genericItem",34),
		});
		addRecipe(new ItemStack(BlocksCore.minEjector,1,6),10000,new Object[]{
			"frameMagic","screenMagic","frameMagic",
			"frameMagic","elementalCore","frameMagic",
			getItemByNameEC3("genericItem",41),"conversionMatrix",getItemByNameEC3("genericItem",41),
		});
		addRecipe(new ItemStack(BlocksCore.minInjector,2,0),2000,new Object[]{
			"screenMagic","screenMagic","screenMagic",
			"frameMagic","elementalCore","frameMagic",
			getItemByNameEC3("genericItem",34),"conversionMatrix",getItemByNameEC3("genericItem",34),
		});
		addRecipe(new ItemStack(BlocksCore.minInjector,1,6),10000,new Object[]{
			"screenMagic","screenMagic","screenMagic",
			"frameMagic","elementalCore","frameMagic",
			getItemByNameEC3("genericItem",41),"conversionMatrix",getItemByNameEC3("genericItem",41),
		});
		*/
		addRecipe(new ItemStack(BlocksCore.ecAcceptor,1,0),new Object[]{
			"frameMagic","mruCatcher","frameMagic",
			"screenMagic","elementalCore","conversionMatrix",
			"plateMagic","mruLink","plateMagic",
		});
		addRecipe(new ItemStack(BlocksCore.solarPrism,1,0),1200,new Object[]{
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
		addRecipe(new ItemStack(BlocksCore.coldDistillator,1,0),1000,new Object[]{
			"screenMagic","mruCatcher","conversionMatrix",
			new ItemStack(BlocksCore.coldStone),"elementalCore",new ItemStack(BlocksCore.coldStone),
			"plateMagic",new ItemStack(ItemsCore.matrixProj,1,2),"worldInteractor",
		});
		addRecipe(new ItemStack(BlocksCore.magmaticSmeltery,1,0),8000,new Object[]{
			"mruCatcher","screenMagic","alloysMagical",
			"conversionMatrix","elementalCore","conversionMatrix",
			"rodHeat","worldInteractor","rodHeat",
		});
		addRecipe(new ItemStack(BlocksCore.magicalJukebox,1,0),404,new Object[]{
			"plateMagic","mruCatcher","worldInteractor",
			"elementalCore",new ItemStack(Blocks.jukebox,1,0),"shardElemental",
			"plateRedstone","screenMagic","plateRedstone",
		});
		addRecipe(new ItemStack(BlocksCore.crystalFormer,1,0),1200,new Object[]{
			"screenMagic","mruCatcher","plateMagic",
			"dustCrystal","elementalCore","dustCrystal",
			"plateObsidian","conversionMatrix","plateObsidian",
		});
		addRecipe(new ItemStack(BlocksCore.crystalController,1,0),3000,new Object[]{
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
		addRecipe(new ItemStack(BlocksCore.magicalQuarry,1,0),5000,new Object[]{
			"plateFortified","frameMagic","plateFortified",
			"screenMagic",new ItemStack(ItemsCore.magicalDigger,1,0),"mruCatcher",
			"worldInteractor","elementalCore","worldInteractor",
		});
		addRecipe(new ItemStack(BlocksCore.monsterClinger,1,0),8000,new Object[]{
			"worldInteractor","elementalCore","worldInteractor",
			"screenMagic","conversionMatrix","mruCatcher",
			"frameMagic","plateFortified","frameMagic",
		});
		addRecipe(new ItemStack(BlocksCore.crystalExtractor,1,0),1000,new Object[]{
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
		//TODO Computer Guy's recipes
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.genericItem,1,69),new Object[]{
			"#R#",
			"LQL",
			"#R#",
			'#',"plateDemonic",
			'R',"dustRedstone",
			'L',"gemLapis",
			'Q',"gemQuartz"
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.genericItem,1,68),new Object[]{
			"#@#",
			"@C@",
			"#@#",
			'#',"plateDemonic",
			'@',gen(47),
			'C',gen(55)
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.genericItem,1,74),new Object[]{
			"#@#",
			"@C@",
			"#@#",
			'#',"plateDemonic",
			'@',gen(48),
			'C',gen(55)
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.genericItem,1,67),new Object[]{
			"#@#",
			"@C@",
			"#@#",
			'#',"plateDemonic",
			'@',gen(44),
			'C',gen(68)
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.genericItem,1,66),new Object[]{
			"#@#",
			"@C@",
			"#@#",
			'#',"plateDemonic",
			'@',gen(47),
			'C',gen(74)
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.genericItem,1,59),new Object[]{
			"#C#",
			"@#@",
			"#C#",
			'#',"plateDemonic",
			'@',gen(0),
			'C',gen(55)
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.genericItem,1,65),new Object[]{
			"#@#",
			"@C@",
			"#@#",
			'#',gen(41),
			'@',"demonicCore",
			'C',gen(37)
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.genericItem,1,63),new Object[]{
			"#@#",
			"ACA",
			"#@#",
			'#',gen(59),
			'@',gen(74),
			'C',gen(9),
			'A',gen(32)
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.genericItem,1,64),new Object[]{
			"#@#",
			"@C@",
			"#@#",
			'#',gen(74),
			'@',gen(59),
			'C',gen(75)
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.genericItem,1,60),new Object[]{
			"#@#",
			"@@@",
			"#@#",
			'#',gen(74),
			'@',gen(59)
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.genericItem,1,62),new Object[]{
			"#@#",
			"$#$",
			"P#P",
			'#',gen(68),
			'@',gen(67),
			'$',gen(60),
			'P',gen(59)
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.genericItem,1,56),new Object[]{
			"#+#",
			"/C*",
			"#-#",
			'#',gen(59),
			'C',gen(69),
			'+',gen(70),
			'-',gen(73),
			'*',gen(72),
			'/',gen(71)
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.genericItem,1,61),new Object[]{
			"#@#",
			"@C@",
			"#@#",
			'#',gen(67),
			'@',gen(68),
			'C',gen(59)
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.genericItem,1,58),new Object[]{
			"#@#",
			"@C@",
			"#@#",
			'#',gen(66),
			'@',gen(67),
			'C',gen(65)
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.genericItem,1,57),new Object[]{
			"#@#",
			"@C@",
			"#@#",
			'#',gen(68),
			'@',gen(65),
			'C',new ItemStack(ItemsCore.storage,1,4)
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.genericItem,1,75),new Object[]{
			"#C#",
			"767",
			"#C#",
			'#',"plateDemonic",
			'C',"demonicCore",
			'7',gen(74),
			'6',gen(60)
		}));
		
		//Armor
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.computer_helmet,1,0),new Object[]{
			"FCF",
			"SLS",
			"EOE",
			'F',gen(58),
			'C',gen(57),
			'S',gen(64),
			'L',gen(63),
			'E',gen(59),
			'O',gen(60)
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.computer_chestplate,1,0),new Object[]{
			"R*S",
			"FCF",
			"ETE",
			'R',gen(61),
			'*',gen(56),
			'S',gen(60),
			'F',gen(58),
			'C',gen(57),
			'E',gen(59),
			'T',gen(62)
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.computer_leggings,1,0),new Object[]{
			"FRF",
			"TCT",
			"ESE",
			'R',gen(61),
			'S',gen(60),
			'F',gen(58),
			'C',gen(57),
			'E',gen(59),
			'T',gen(62)
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.computer_boots,1,0),new Object[]{
			"FSF",
			"ECE",
			"SFS",
			'S',gen(60),
			'F',gen(58),
			'C',gen(57),
			'E',gen(59)
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.computerBoard,1,0),new Object[]{
			"SCS",
			"EEE",
			"RTR",
			'R',gen(61),
			'S',gen(60),
			'C',gen(57),
			'E',gen(59),
			'T',gen(62)
		}));
		//TODO 4.5 recipes
		addRecipe(new ItemStack(BlocksCore.mithrilineFurnace,1,0),new Object[]{
			"dustMithriline","gemMithriline","dustMithriline",
			"plateEmerald",Blocks.furnace,"plateEmerald",
			"ingotMithriline","rodHeat","ingotMithriline",
		});
		addRecipe(new ItemStack(BlocksCore.playerPentacle,1,0),new Object[]{
			"dustMithriline",ItemsCore.matrixProj,"dustMithriline",
			ItemsCore.matrixProj,ItemsCore.soulStone,ItemsCore.matrixProj,
			"dustMithriline",ItemsCore.matrixProj,"dustMithriline",
		});
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlocksCore.redstoneTransmitter,2,0), new Object[]{
			"   ",
			"RTR",
			"SSS",
			'S',BlocksCore.fortifiedStone,
			'R',"dustRedstone",
			'T',Blocks.redstone_torch
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlocksCore.magicalHopper,1,0), new Object[]{
			"SHS",
			"SCS",
			"SDS",
			'S',BlocksCore.fortifiedStone,
			'H',Blocks.hopper,
			'C',Blocks.chest,
			'D',Blocks.dropper
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlocksCore.rightClicker,1,0), new Object[]{
			"SSS",
			"DID",
			"SPS",
			'S',BlocksCore.fortifiedStone,
			'P',Blocks.piston,
			'I',Blocks.dispenser,
			'D',Blocks.dropper
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlocksCore.windRune,1,0), new Object[]{
			"MPM",
			"PEP",
			"MPM",
			'M',BlocksCore.invertedBlock,
			'E',"elementalCore",
			'P',"plateMithriline"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlocksCore.metadataManager,1,0), new Object[]{
			"SSS",
			"SBR",
			"SSS",
			'S',BlocksCore.fortifiedStone,
			'R',"dustRedstone",
			'B',ItemsCore.bound_gem
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlocksCore.blockBreaker,1,0), new Object[]{
			"SSS",
			"SRP",
			"SSS",
			'S',BlocksCore.fortifiedStone,
			'R',"dustRedstone",
			'P',ItemsCore.weak_elemental_pick
		}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(BlocksCore.rightClicker,1,1), new Object[]{
			new ItemStack(BlocksCore.rightClicker,1,0),new ItemStack(Items.slime_ball,1,0)
		}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(BlocksCore.rightClicker,1,2), new Object[]{
			new ItemStack(BlocksCore.rightClicker,1,0),new ItemStack(Blocks.chest,1,0)
		}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(BlocksCore.rightClicker,1,3), new Object[]{
			new ItemStack(BlocksCore.rightClicker,1,2),new ItemStack(Items.slime_ball,1,0)
		}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(BlocksCore.rightClicker,1,4), new Object[]{
			new ItemStack(BlocksCore.rightClicker,1,2),new ItemStack(Items.redstone,1,0)
		}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(BlocksCore.rightClicker,1,5), new Object[]{
			new ItemStack(BlocksCore.rightClicker,1,4),new ItemStack(Items.slime_ball,1,0)
		}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(BlocksCore.compressed,1,0), new Object[]{
			new ItemStack(ItemsCore.drops,1,0),
			new ItemStack(ItemsCore.drops,1,0),
			new ItemStack(ItemsCore.drops,1,0),
			new ItemStack(ItemsCore.drops,1,0),
			new ItemStack(ItemsCore.drops,1,0),
			new ItemStack(ItemsCore.drops,1,0),
			new ItemStack(ItemsCore.drops,1,0),
			new ItemStack(ItemsCore.drops,1,0),
			new ItemStack(ItemsCore.drops,1,0),
		}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(BlocksCore.compressed,1,1), new Object[]{
			new ItemStack(ItemsCore.drops,1,1),
			new ItemStack(ItemsCore.drops,1,1),
			new ItemStack(ItemsCore.drops,1,1),
			new ItemStack(ItemsCore.drops,1,1),
			new ItemStack(ItemsCore.drops,1,1),
			new ItemStack(ItemsCore.drops,1,1),
			new ItemStack(ItemsCore.drops,1,1),
			new ItemStack(ItemsCore.drops,1,1),
			new ItemStack(ItemsCore.drops,1,1),
		}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(BlocksCore.compressed,1,2), new Object[]{
			new ItemStack(ItemsCore.drops,1,2),
			new ItemStack(ItemsCore.drops,1,2),
			new ItemStack(ItemsCore.drops,1,2),
			new ItemStack(ItemsCore.drops,1,2),
			new ItemStack(ItemsCore.drops,1,2),
			new ItemStack(ItemsCore.drops,1,2),
			new ItemStack(ItemsCore.drops,1,2),
			new ItemStack(ItemsCore.drops,1,2),
			new ItemStack(ItemsCore.drops,1,2),
		}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(BlocksCore.compressed,1,3), new Object[]{
			new ItemStack(ItemsCore.drops,1,3),
			new ItemStack(ItemsCore.drops,1,3),
			new ItemStack(ItemsCore.drops,1,3),
			new ItemStack(ItemsCore.drops,1,3),
			new ItemStack(ItemsCore.drops,1,3),
			new ItemStack(ItemsCore.drops,1,3),
			new ItemStack(ItemsCore.drops,1,3),
			new ItemStack(ItemsCore.drops,1,3),
			new ItemStack(ItemsCore.drops,1,3),
		}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(BlocksCore.compressed,1,4), new Object[]{
			new ItemStack(ItemsCore.drops,1,4),
			new ItemStack(ItemsCore.drops,1,4),
			new ItemStack(ItemsCore.drops,1,4),
			new ItemStack(ItemsCore.drops,1,4),
			new ItemStack(ItemsCore.drops,1,4),
			new ItemStack(ItemsCore.drops,1,4),
			new ItemStack(ItemsCore.drops,1,4),
			new ItemStack(ItemsCore.drops,1,4),
			new ItemStack(ItemsCore.drops,1,4),
		}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemsCore.drops,9,0), new Object[]{
			new ItemStack(BlocksCore.compressed,1,0)
		}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemsCore.drops,9,1), new Object[]{
			new ItemStack(BlocksCore.compressed,1,1)
		}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemsCore.drops,9,2), new Object[]{
			new ItemStack(BlocksCore.compressed,1,2)
		}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemsCore.drops,9,3), new Object[]{
			new ItemStack(BlocksCore.compressed,1,3)
		}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemsCore.drops,9,4), new Object[]{
			new ItemStack(BlocksCore.compressed,1,4)
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlocksCore.demonicPentacle,1,0), new Object[]{
			"FVF",
			"VCV",
			"FVF",
			'F',"focusFire",
			'V',"plateVoid",
			'C',"voidCore"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlocksCore.weaponMaker,1,0), new Object[]{
			"SIS",
			"SCS",
			"SSS",
			'S',BlocksCore.fortifiedStone,
			'I',Blocks.heavy_weighted_pressure_plate,
			'C',Blocks.crafting_table
		}));
		GameRegistry.addShapelessRecipe(new ItemStack(BlocksCore.weaponMaker,1,1), new ItemStack(BlocksCore.weaponMaker,1,0));
		GameRegistry.addShapelessRecipe(new ItemStack(BlocksCore.weaponMaker,1,2), new ItemStack(BlocksCore.weaponMaker,1,1));
		GameRegistry.addShapelessRecipe(new ItemStack(BlocksCore.weaponMaker,1,3), new ItemStack(BlocksCore.weaponMaker,1,2));
		GameRegistry.addShapelessRecipe(new ItemStack(BlocksCore.weaponMaker,1,0), new ItemStack(BlocksCore.weaponMaker,1,3));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.wind_elemental_hoe,1,0), new Object[]{
			"DD ",
			" S ",
			" S ",
			'D',"gemWind",
			'S',"stickWood"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.wind_elemental_axe,1,0), new Object[]{
			"DD ",
			"DS ",
			" S ",
			'D',"gemWind",
			'S',"stickWood"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.wind_elemental_pick,1,0), new Object[]{
			"DDD",
			" S ",
			" S ",
			'D',"gemWind",
			'S',"stickWood"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.wind_elemental_shovel,1,0), new Object[]{
			" D ",
			" S ",
			" S ",
			'D',"gemWind",
			'S',"stickWood"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.wind_elemental_sword,1,0), new Object[]{
			" D ",
			" D ",
			" S ",
			'D',"gemWind",
			'S',"stickWood"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.soulScriber,1,0), new Object[]{
			"N",
			"Q",
			"S",
			'N',Items.netherbrick,
			'S',"stickWood",
			'Q',Items.quartz
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.weak_elemental_hoe,1,0), new Object[]{
			"DD ",
			" S ",
			" S ",
			'D',"blockElemental",
			'S',"stickWood"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.weak_elemental_axe,1,0), new Object[]{
			"DD ",
			"DS ",
			" S ",
			'D',"blockElemental",
			'S',"stickWood"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.weak_elemental_pick,1,0), new Object[]{
			"DDD",
			" S ",
			" S ",
			'D',"blockElemental",
			'S',"stickWood"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.weak_elemental_shovel,1,0), new Object[]{
			" D ",
			" S ",
			" S ",
			'D',"blockElemental",
			'S',"stickWood"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.weak_elemental_sword,1,0), new Object[]{
			" D ",
			" D ",
			" S ",
			'D',"blockElemental",
			'S',"stickWood"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.wind_helmet,1,0), new Object[]{
			"DDD",
			"D D",
			'D',"gemWind"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.wind_chestplate,1,0), new Object[]{
			"D D",
			"DDD",
			"DDD",
			'D',"gemWind"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.wind_leggings,1,0), new Object[]{
			"DDD",
			"DDD",
			"D D",
			'D',"gemWind"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.wind_boots,1,0), new Object[]{
			"D D",
			"D D",
			'D',"gemWind"
		}));
		
		//TODO 4.6 Recipes
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlocksCore.chest,1,0), new Object[]{
			"###",
			"# #",
			"###",
			'#',new ItemStack(BlocksCore.magicPlating,1,OreDictionary.WILDCARD_VALUE)
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlocksCore.chest,1,1), new Object[]{
			"###",
			"# #",
			"###",
			'#',new ItemStack(BlocksCore.voidStone,1,OreDictionary.WILDCARD_VALUE)
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlocksCore.device,1,0), new Object[]{
			"#@#",
			"#R#",
			"###",
			'#',new ItemStack(BlocksCore.fortifiedStone,1,OreDictionary.WILDCARD_VALUE),
			'R',"dustRedstone",
			'@',ItemsCore.weak_elemental_hoe
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlocksCore.device,1,1), new Object[]{
			"#@#",
			"#R#",
			"###",
			'#',new ItemStack(BlocksCore.fortifiedStone,1,OreDictionary.WILDCARD_VALUE),
			'R',"dustRedstone",
			'@',Items.water_bucket
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlocksCore.device,1,2), new Object[]{
			"#@#",
			"#R#",
			"#@#",
			'#',new ItemStack(BlocksCore.fortifiedStone,1,OreDictionary.WILDCARD_VALUE),
			'R',"dustRedstone",
			'@',Blocks.crafting_table
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlocksCore.device,1,3), new Object[]{
			"#@#",
			"#R#",
			"###",
			'#',new ItemStack(BlocksCore.fortifiedStone,1,OreDictionary.WILDCARD_VALUE),
			'R',"dustRedstone",
			'@',Blocks.hay_block
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlocksCore.device,1,5), new Object[]{
			"#@#",
			"#R#",
			"###",
			'#',new ItemStack(BlocksCore.fortifiedStone,1,OreDictionary.WILDCARD_VALUE),
			'R',"dustRedstone",
			'@',Items.shears
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlocksCore.device,1,6), new Object[]{
			"#@#",
			"#R#",
			"###",
			'#',new ItemStack(BlocksCore.fortifiedStone,1,OreDictionary.WILDCARD_VALUE),
			'R',"dustRedstone",
			'@',Items.golden_apple
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlocksCore.device,1,7), new Object[]{
			"#@#",
			"#R#",
			"###",
			'#',new ItemStack(BlocksCore.fortifiedStone,1,OreDictionary.WILDCARD_VALUE),
			'R',"dustRedstone",
			'@',Items.apple
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlocksCore.advBreaker,1,0), new Object[]{
			" R ",
			"RBR",
			" C ",
			'B',BlocksCore.blockBreaker,
			'R',"dustRedstone",
			'C',Blocks.chest
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.craftingFrame,1,0), new Object[]{
			"#@#",
			"@#@",
			"#@#",
			'#',Blocks.crafting_table,
			'@',"plateFortified"
		}));
		
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemsCore.inventoryGem,1,0),new Object[]{
			"shardElemental",gen(12)
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.filter,1,2), new Object[]{
			" @ ",
			"@#@",
			" @ ",
			'#',Blocks.crafting_table,
			'@',"plateObsidian"
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.filter,1,3), new Object[]{
			" @ ",
			"@#@",
			" @ ",
			'#',new ItemStack(ItemsCore.filter,1,2),
			'@',"plateVoid"
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlocksCore.mimEjector), new Object[]{
			" @ ",
			"#C#",
			"#E#",
			'#',gen(34),
			'@',"ec3:gemEnderPearl",
			'C',"elementalCore",
			'E',"plateEnder"
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlocksCore.mimInjector), new Object[]{
			" @ ",
			"#C#",
			"#E#",
			'#',gen(34),
			'@',gen(23),
			'C',"elementalCore",
			'E',"plateEnder"
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlocksCore.mimEjectorP), new Object[]{
			" @ ",
			"#C#",
			"#E#",
			'#',"plateMithriline",
			'@',"ec3:gemEnderPearl",
			'C',"elementalCore",
			'E',"plateEnder"
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlocksCore.mimInjectorP), new Object[]{
			" @ ",
			"#C#",
			"#E#",
			'#',"plateMithriline",
			'@',gen(23),
			'C',"elementalCore",
			'E',"plateEnder"
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlocksCore.newMim), new Object[]{
			"VPV",
			"CRC",
			"PMP",
			'P',"plateVoid",
			'V',new ItemStack(BlocksCore.chest,1,1),
			'C',"voidCore",
			'R',"voidMRU",
			'M',"redSoulMatter"
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlocksCore.mimScreen), new Object[]{
			"DDD",
			"PCP",
			"PMP",
			'P',"plateMagic",
			'D',"screenMagic",
			'C',"elementalCore",
			'M',"mruCatcher"
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlocksCore.mimInvStorage), new Object[]{
			"PPP",
			"SCS",
			"PPP",
			'P',"plateMagic",
			'C',"elementalCore",
			'S',BlocksCore.chest
		}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlocksCore.mimCrafter), new Object[]{
			"PPP",
			"SCS",
			"PPP",
			'P',"plateMagic",
			'C',"elementalCore",
			'S',Blocks.crafting_table
		}));
		
		
		addRecipe(new ItemStack(BlocksCore.voidStone,16,0),new Object[]{
			"titanite","plateObsidian","titanite",
			"plateObsidian","ttitanite","plateObsidian",
			"titanite","plateObsidian","titanite",
		});
		addRecipe(new ItemStack(BlocksCore.sunRayAbsorber,1,0),10000,new Object[]{
			"screenMagic",new ItemStack(ItemsCore.genericItem,1,32),"mruCatcher",
			"worldInteractor","elementalCore","conversionMatrix",
			"plateMagic",new ItemStack(ItemsCore.matrixProj,1,1),"plateMagic",
		});
		addRecipe(new ItemStack(BlocksCore.moonWell,1,0),10000,new Object[]{
			"screenMagic",new ItemStack(BlocksCore.elementalCrystal,1,0),"mruCatcher",
			"worldInteractor","elementalCore","conversionMatrix",
			"plateMagic",new ItemStack(ItemsCore.matrixProj,1,3),"plateMagic",
		});
		//TODO assembler Recipes
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.elemental_hoe,1,0), new Object[]{
			"DD ",
			" S ",
			" S ",
			'D',"resonatingCrystal",
			'S',"stickWood"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.elemental_axe,1,0), new Object[]{
			"DD ",
			"DS ",
			" S ",
			'D',"resonatingCrystal",
			'S',"stickWood"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.elemental_pick,1,0), new Object[]{
			"DDD",
			" S ",
			" S ",
			'D',"resonatingCrystal",
			'S',"stickWood"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.elemental_shovel,1,0), new Object[]{
			" D ",
			" S ",
			" S ",
			'D',"resonatingCrystal",
			'S',"stickWood"
		}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemsCore.elemental_sword,1,0), new Object[]{
			" D ",
			" D ",
			" S ",
			'D',"resonatingCrystal",
			'S',"stickWood"
		}));
		
		addRecipe(new ItemStack(ItemsCore.biomeWand,1,0),1200,new Object[]{
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
		
		addRecipe(new ItemStack(ItemsCore.genericItem,1,17),new Object[]{
			"alloysMagical","plateGlass","alloysMagical",
			"plateEnder",Blocks.chest,"plateEnder",
			"alloysMagical","plateGlass","alloysMagical",
		});
		
		addRecipe(new ItemStack(ItemsCore.genericItem,3,18),new Object[]{
			"alloysMagical","plateRedstone","alloysMagical",
			"plateRedstone",Items.cake,"plateRedstone",
			"alloysMagical","plateRedstone","alloysMagical",
		});
		
		addRecipe(new ItemStack(ItemsCore.genericItem,1,80),new Object[]{
			"alloysMagical","plateDiamond","alloysMagical",
			"plateEmerald",Items.golden_apple,"plateEmerald",
			"alloysMagical","plateDiamond","alloysMagical",
		});
		
		addRecipe(new ItemStack(ItemsCore.genericItem,1,81),new Object[]{
			"alloysMagical","plateObsidian","alloysMagical",
			"plateObsidian",Items.ender_eye,"plateObsidian",
			"alloysMagical","plateObsidian","alloysMagical",
		});
		
		addRecipe(new ItemStack(ItemsCore.genericItem,3,78),new Object[]{
			"alloysMagical","platePale","alloysMagical",
			"platePale","gemPale","platePale",
			"alloysMagical","platePale","alloysMagical",
		});
		
		addRecipe(new ItemStack(ItemsCore.genericItem,1,77),new Object[]{
			"alloysMagical",gen(79),"alloysMagical",
			gen(79),Items.lava_bucket,gen(79),
			"alloysMagical",gen(79),"alloysMagical",
		});
		
		addRecipe(new ItemStack(ItemsCore.genericItem,3,19),new Object[]{
			"alloysMagical","plateEnder","alloysMagical",
			"plateEnder","dustCrystal","plateEnder",
			"alloysMagical","plateEnder","alloysMagical",
		});
		
		addRecipe(new ItemStack(ItemsCore.spawnerCollector,1,0),10000,new Object[]{
			"dustCrystal","focusWater","focusWater",
			"ingotMagic","redSoulMatter","focusWater",
			"plateMagic","ingotMagic","dustCrystal",
		});
		
		addRecipe(new ItemStack(ItemsCore.magicalDigger,1,0),10000,new Object[]{
			Blocks.tnt,"focusEarth","redSoulMatter",
			"focusFire",ItemsCore.elemental_pick,"focusEarth",
			"plateMagic","focusFire",Blocks.tnt,
		});
		
		addRecipe(new ItemStack(ItemsCore.staffOfLife,1,0),1000,new Object[]{
			ItemsCore.elemental_hoe,"focusEarth",new ItemStack(ItemsCore.genericItem,1,4),
			"focusEarth","redSoulMatter","focusEarth",
			"plateMagic","focusEarth",ItemsCore.elemental_hoe,
		});
		
		addRecipe(new ItemStack(ItemsCore.emeraldHeart,1,0),5000,new Object[]{
			"focusEarth","gemEmerald","focusEarth",
			"magicWater","redSoulMatter","magicWater",
			"focusWater",Items.apple,"focusWater",
		});
		addRecipe(new ItemStack(ItemsCore.magicalShield,1,0),5000,new Object[]{
			"plateObsidian","alloysMagical","plateObsidian",
			"focusEarth","redSoulMatter","focusEarth",
			"dustCrystal","plateObsidian","dustCrystal",
		});
		addRecipe(new ItemStack(ItemsCore.spikyShield,1,0),10000,new Object[]{
			ItemsCore.elemental_sword,"gemNetherStar",ItemsCore.elemental_sword,
			"alloysMagical",ItemsCore.magicalShield,"alloysMagical",
			"alloysMagical","matterOfEternity","alloysMagical",
		});
		addRecipe(new ItemStack(ItemsCore.magicWaterBottle,1,0),5000,new Object[]{
			"focusAir","magicWater","focusEarth",
			"plateDiamond","redSoulMatter","plateDiamond",
			"focusWater","magicWater","focusWater",
		});
		addRecipe(new ItemStack(ItemsCore.magicalPorkchop,1,0),5000,new Object[]{
			"focusEarth",Items.porkchop,"focusEarth",
			"dustCrystal","redSoulMatter","dustCrystal",
			"focusWater","dustCrystal","focusWater",
		});
		addRecipe(new ItemStack(ItemsCore.magicalWings,1,0),5000,new Object[]{
			"plateMagic","focusAir","focusAir",
			"plateMagic",Items.feather,Items.feather,
			"redSoulMatter",Items.feather,Items.feather,
		});
		addRecipe(new ItemStack(ItemsCore.holyMace,1,0),1000,new Object[]{
			"redSoulMatter","focusAir",new ItemStack(ItemsCore.genericItem,1,4),
			"ingotMagic",ItemsCore.elemental_sword,"focusAir",
			"focusEarth","ingotMagic","redSoulMatter",
		});
		addRecipe(new ItemStack(ItemsCore.chaosFork,1,0),10000,new Object[]{
			"ingotMagic","focusFire","matterOfEternity",
			"ingotMagic",ItemsCore.elemental_sword,"focusFire",
			new ItemStack(ItemsCore.matrixProj,1,1),"ingotMagic","ingotMagic",
		});
		addRecipe(new ItemStack(ItemsCore.frozenMace,1,0),10000,new Object[]{
			"ingotMagic","focusWater","matterOfEternity",
			"ingotMagic",ItemsCore.elemental_sword,"focusWater",
			new ItemStack(ItemsCore.matrixProj,1,2),"ingotMagic","ingotMagic",
		});
		
		addRecipe(new ItemStack(ItemsCore.windTablet,1,0),10000,new Object[]{
			"focusAir","plateFortified","focusAir",
			"plateFortified",ItemsCore.windKeeper,"plateFortified",
			"focusAir","plateFortified","focusAir",
		});
		
		addRecipe(new ItemStack(BlocksCore.magicalTeleporter,1,0),10000,new Object[]{
			"screenMagic","voidMRU","worldInteractor",
			"plateMagic","voidCore","plateMagic",
			"plateVoid","matterOfEternity","plateVoid",
		});
		/*
		addRecipe(new ItemStack(BlocksCore.mim,1,0),10000,new Object[]{
			"screenMagic","voidMRU","screenMagic",
			"plateVoid","voidCore","plateVoid",
			"plateVoid","plateVoid","plateVoid",
		});
		addRecipe(new ItemStack(BlocksCore.darknessObelisk,1,0),1000,new Object[]{
			"plateVoid","voidMRU","plateVoid",
			"plateVoid","matterOfEternity","plateVoid",
			"plateVoid","voidMRU","plateVoid",
		});
		*/
		addRecipe(new ItemStack(BlocksCore.ultraHeatGen,1,0),5000,new Object[]{
			"plateVoid","voidMRU","plateVoid",
			"voidCore",new ItemStack(BlocksCore.heatGenerator),"voidCore",
			"plateVoid","voidMRU","plateVoid",
		});
		addRecipe(new ItemStack(BlocksCore.ultraFlowerBurner,1,0),5000,new Object[]{
			"plateVoid","voidMRU","plateVoid",
			"voidCore",new ItemStack(BlocksCore.naturalFurnace),"voidCore",
			"plateVoid","voidMRU","plateVoid",
		});
		
		addRecipe(new ItemStack(BlocksCore.magicalFurnace,1,0),10000,new Object[]{
			"screenMagic","voidMRU","worldInteractor",
			"plateFortified","voidCore","plateFortified",
			"plateVoid","matterOfEternity","plateVoid",
		});
		
		addRecipe(new ItemStack(BlocksCore.emberForge,1,0),10000,new Object[]{
			"screenMagic","voidMRU","plateEnder",
			"plateFortified","voidCore","plateFortified",
			"plateVoid","matterOfEternity","plateVoid",
		});
		
		addRecipe(new ItemStack(ItemsCore.magicArmorItems[0],1,0),2000,new Object[]{
			"alloysMagical","worldInteractor","alloysMagical",
			"plateMagic","plateGlass","plateMagic",
			"dustCrystal","dustCrystal","dustCrystal",
		});
		
		addRecipe(new ItemStack(ItemsCore.magicArmorItems[1],1,0),5000,new Object[]{
			"worldInteractor","dustCrystal","worldInteractor",
			"plateMagic","alloysMagical","plateMagic",
			"plateMagic","alloysMagical","plateMagic",
		});
		
		addRecipe(new ItemStack(ItemsCore.magicArmorItems[2],1,0),3500,new Object[]{
			"alloysMagical","worldInteractor","alloysMagical",
			"plateMagic","dustCrystal","plateMagic",
			"plateMagic","dustCrystal","plateMagic",
		});
		
		addRecipe(new ItemStack(ItemsCore.magicArmorItems[3],1,0),2000,new Object[]{
			"dustCrystal","worldInteractor","dustCrystal",
			"plateMagic","dustCrystal","plateMagic",
			"alloysMagical","dustCrystal","alloysMagical",
		});
		
		addRecipe(new ItemStack(ItemsCore.magicArmorItems[4],1,0),10000,new Object[]{
			"gemNetherStar","voidMRU","gemNetherStar",
			"voidCore",new ItemStack(ItemsCore.magicArmorItems[0],1,0),"voidCore",
			"plateVoid","matterOfEternity","plateVoid",
		});
		
		addRecipe(new ItemStack(ItemsCore.magicArmorItems[5],1,0),10000,new Object[]{
			"voidMRU","gemNetherStar","voidMRU",
			"voidCore",new ItemStack(ItemsCore.magicArmorItems[1],1,0),"voidCore",
			"plateVoid","matterOfEternity","plateVoid",
		});
		
		addRecipe(new ItemStack(ItemsCore.magicArmorItems[6],1,0),10000,new Object[]{
			"gemNetherStar","voidMRU","gemNetherStar",
			"voidCore",new ItemStack(ItemsCore.magicArmorItems[2],1,0),"voidCore",
			"plateVoid","matterOfEternity","plateVoid",
		});
		
		addRecipe(new ItemStack(ItemsCore.magicArmorItems[7],1,0),10000,new Object[]{
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
		
		addRecipe(new ItemStack(ItemsCore.magmaticStaff,1,0),1000,new Object[]{
			"focusFire","focusFire",BlocksCore.magmaticSmeltery,
			"dustCrystal","matterOfEternity","focusFire",
			"plateMagic","dustCrystal","focusFire",
		});
		
		addRecipe(new ItemStack(ItemsCore.magicalLantern,1,0),1000,new Object[]{
			"focusAir","focusFire","redSoulMatter",
			ItemsCore.magicalSlag,"plateMagic","focusFire",
			"plateMagic",ItemsCore.magicalSlag,"focusAir",
		});
		
		addRecipe(new ItemStack(ItemsCore.magnetizingStaff,1,0),1000,new Object[]{
			"focusAir","orbGold","darkSoulMatter",
			ItemsCore.magicalSlag,"plateMagic","orbGold",
			"plateMagic",ItemsCore.magicalSlag,"focusAir",
		});
		
		addRecipe(new ItemStack(BlocksCore.mruCoilHardener,3,0),1000,new Object[]{
			"plateMagic","elementalCore","plateMagic",
			"plateMagic","magnet","plateMagic",
			"plateMagic","mruLink","plateMagic",
		});
		
		addRecipe(new ItemStack(BlocksCore.mruCoil,1,0),10000,new Object[]{
			"worldInteractor","resonatingCrystal","screenMagic",
			"plateMagic","magnet","plateMagic",
			"mruLink",new ItemStack(ItemsCore.matrixProj,1,3),"mruLink",
		});
		
		addRecipe(new ItemStack(BlocksCore.corruptionCleaner,1,0),1000,new Object[]{
			"screenMagic","elementalCore","plateFortified",
			"plateRedstone","resonatingCrystal","plateEnder",
			"plateFortified","worldInteractor","plateFortified",
		});
		
		addRecipe(new ItemStack(BlocksCore.reactorSupport,2,0),10000,new Object[]{
			"magnet","resonatingCrystal","magnet",
			"plateMagic","elementalCore","plateMagic",
			"plateMagic","plateEnder","plateMagic",
		});
		
		addRecipe(new ItemStack(BlocksCore.reactor,1,0),10000,new Object[]{
			new ItemStack(ItemsCore.matrixProj,1,3),"gemNetherStar",new ItemStack(ItemsCore.matrixProj,1,3),
			"resonatingCrystal","magnet","resonatingCrystal",
			"plateMagic","plateMagic","plateMagic",
		});
		
		ItemStack book = new ItemStack(ItemsCore.research_book);
		MiscUtils.getStackTag(book).setInteger("tier", 0);
		
		addRecipe(book,new Object[]{
			"dyeRed","itemBook","itemFeather",
			"dyeGreen","itemBook","dyeBlack",
			"dyeBlue","itemBook","dyeBlack",
		});
		
		ItemStack book_t1 = new ItemStack(ItemsCore.research_book);
		MiscUtils.getStackTag(book_t1).setInteger("tier", 1);
		
		addRecipe(book_t1,new Object[]{
				"elementalCore","shardElemental","elementalCore",
				"shardElemental",book,"shardElemental",
				"elementalCore","shardElemental","elementalCore",
			});

		ItemStack book_t2 = new ItemStack(ItemsCore.research_book);
		MiscUtils.getStackTag(book_t2).setInteger("tier", 2);
		
		ItemStack book_t3 = new ItemStack(ItemsCore.research_book);
		MiscUtils.getStackTag(book_t3).setInteger("tier", 3);

		addRecipe(book_t3,new Object[]{
				"plateVoid","resonatingCrystal","plateVoid",
				"resonatingCrystal",book_t2,"resonatingCrystal",
				"plateVoid","resonatingCrystal","plateVoid",
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
	
	public void addRecipe(ItemStack output,int mruReq, Object... recipe)
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
		//TODO RadiatingChamberRecipes
		RadiatingChamberRecipes.addRecipeIS(new ItemStack[]{new ItemStack(Blocks.stone),new ItemStack(Items.iron_ingot)}, getItemByNameEC3("fortifiedStone",0), 10, new float[]{Float.MAX_VALUE,Float.MIN_VALUE},4);
		RadiatingChamberRecipes.addRecipeIS(new ItemStack[]{new ItemStack(Blocks.glass),new ItemStack(Items.iron_ingot)}, getItemByNameEC3("fortifiedGlass",0), 10, new float[]{Float.MAX_VALUE,Float.MIN_VALUE},4);
		RadiatingChamberRecipes.addRecipeIS(new ItemStack[]{new ItemStack(Items.redstone),new ItemStack(Items.blaze_powder)}, getItemByNameEC3("genericItem",3), 100, new float[]{Float.MAX_VALUE,Float.MIN_VALUE},1);
		RadiatingChamberRecipes.addRecipeIS(new ItemStack[]{getItemByNameEC3("soulStone",0),null}, getItemByNameEC3("matrixProj",0), 1000, new float[]{Float.MAX_VALUE,Float.MIN_VALUE},1);
		RadiatingChamberRecipes.addRecipeIS(new ItemStack[]{getItemByNameEC3("matrixProj",0),null}, getItemByNameEC3("matrixProj",1), 10000, new float[]{Float.MAX_VALUE,1.5F},1);
		RadiatingChamberRecipes.addRecipeIS(new ItemStack[]{getItemByNameEC3("matrixProj",0),null}, getItemByNameEC3("matrixProj",2), 10000, new float[]{0.5F,Float.MIN_VALUE},1);
		RadiatingChamberRecipes.addRecipeIS(new ItemStack[]{getItemByNameEC3("matrixProj",0),null}, getItemByNameEC3("matrixProj",3), 20000, new float[]{1.49F,0.69F},1);
		RadiatingChamberRecipes.addRecipeIS(new ItemStack[]{new ItemStack(Items.dye,1,4),new ItemStack(Items.glowstone_dust)}, getItemByNameEC3("genericItem",38), 250, new float[]{Float.MAX_VALUE,Float.MIN_VALUE},20,1);
		RadiatingChamberRecipes.addRecipeIS(new ItemStack[]{new ItemStack(Blocks.lapis_block,1,0),new ItemStack(Items.gold_ingot)}, getItemByNameEC3("genericItem",39), 100, new float[]{Float.MAX_VALUE,Float.MIN_VALUE},80,4);
		RadiatingChamberRecipes.addRecipeIS(new ItemStack[]{new ItemStack(BlocksCore.blockPale,1,0),new ItemStack(Items.diamond)}, getItemByNameEC3("genericItem",40), 250, new float[]{Float.MAX_VALUE,Float.MIN_VALUE},120,4);
		RadiatingChamberRecipes.addRecipeIS(new ItemStack[]{new ItemStack(BlocksCore.blockPale,1,0),new ItemStack(Items.emerald)}, getItemByNameEC3("genericItem",40), 300, new float[]{Float.MAX_VALUE,Float.MIN_VALUE},100,4);
		RadiatingChamberRecipes.addRecipeIS(new ItemStack[]{new ItemStack(Blocks.stone),new ItemStack(ItemsCore.drops,1,4)}, getItemByNameEC3("genericItem",42), 100, new float[]{Float.MAX_VALUE,Float.MIN_VALUE},10,1);
		RadiatingChamberRecipes.addRecipeIS(new ItemStack[]{new ItemStack(Items.iron_ingot),new ItemStack(ItemsCore.genericItem,1,3)}, getItemByNameEC3("genericItem",43), 1000, new float[]{Float.MAX_VALUE,Float.MIN_VALUE},1,1);
		RadiatingChamberRecipes.addRecipeIS(new ItemStack[]{new ItemStack(Items.diamond),new ItemStack(Items.emerald)}, getItemByNameEC3("genericItem",44), 100, new float[]{Float.MAX_VALUE,Float.MIN_VALUE},50,1);
	}
	
	public void registerMagicianTable()
	{
		//TODO MagicianTableRecipes
		MagicianTableRecipes.addRecipeIS(new ItemStack[]{getItemByNameEC3("genericItem",7),getItemByNameEC3("genericItem",79),getItemByNameEC3("genericItem",8),getItemByNameEC3("genericItem",8),getItemByNameEC3("genericItem",79)},getItemByNameEC3("genericItem",0), 10000);
		MagicianTableRecipes.addRecipeIS(new ItemStack[]{getItemByNameEC3("genericItem",7),getItemByNameEC3("genericItem",8),getItemByNameEC3("genericItem",79),getItemByNameEC3("genericItem",79),getItemByNameEC3("genericItem",8)},getItemByNameEC3("genericItem",0), 10000);
		MagicianTableRecipes.addRecipeIS(new ItemStack[]{new ItemStack(Items.ender_pearl),getItemByNameEC3("genericItem",10),getItemByNameEC3("genericItem",10),getItemByNameEC3("genericItem",10),getItemByNameEC3("genericItem",10)},getItemByNameEC3("genericItem",4), 5000);
		MagicianTableRecipes.addRecipeIS(new ItemStack[]{new ItemStack(Items.iron_ingot),null,null,null,null},getItemByNameEC3("genericItem",5), 50);
		MagicianTableRecipes.addRecipeIS(new ItemStack[]{new ItemStack(Items.potionitem),null,null,null,null},getItemByNameEC3("genericItem",6), 250);
		MagicianTableRecipes.addRecipeIS(new ItemStack[]{getItemByNameEC3("fortifiedStone",OreDictionary.WILDCARD_VALUE),null,null,null,null},getItemByNameEC3("genericItem",7), 10);
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
		MagicianTableRecipes.addRecipeIS(new UnformedItemStack[]{new UnformedItemStack(getItemByNameEC3("genericItem",7)),new UnformedItemStack("ingotThaumium"),new UnformedItemStack("ingotThaumium"),new UnformedItemStack("ingotThaumium"),new UnformedItemStack("ingotThaumium")},getItemByNameEC3("genericItem",34), 100);
		MagicianTableRecipes.addRecipeIS(new ItemStack[]{getItemByNameEC3("genericItem",7),getItemByNameEC3("genericItem",39),getItemByNameEC3("genericItem",39),getItemByNameEC3("genericItem",39),getItemByNameEC3("genericItem",39)},getItemByNameEC3("genericItem",41), 100);
		MagicianTableRecipes.addRecipeIS(new ItemStack[]{new ItemStack(Items.quartz),getItemByNameEC3("genericItem",12),getItemByNameEC3("genericItem",12),getItemByNameEC3("genericItem",12),getItemByNameEC3("genericItem",12)},getItemByNameEC3("storage",0), 100);
		MagicianTableRecipes.addRecipeIS(new ItemStack[]{new ItemStack(Items.emerald),getItemByNameEC3("genericItem",13),getItemByNameEC3("genericItem",13),getItemByNameEC3("genericItem",13),getItemByNameEC3("genericItem",13)},getItemByNameEC3("storage",1), 500);
		MagicianTableRecipes.addRecipeIS(new ItemStack[]{new ItemStack(Items.ender_pearl),getItemByNameEC3("genericItem",14),getItemByNameEC3("genericItem",14),getItemByNameEC3("genericItem",14),getItemByNameEC3("genericItem",14)},getItemByNameEC3("storage",2), 100);
		MagicianTableRecipes.addRecipeIS(new ItemStack[]{new ItemStack(Items.diamond),getItemByNameEC3("genericItem",15),getItemByNameEC3("genericItem",15),getItemByNameEC3("genericItem",15),getItemByNameEC3("genericItem",15)},getItemByNameEC3("storage",3), 250);
		MagicianTableRecipes.addRecipeIS(new ItemStack[]{new ItemStack(Items.nether_star),getItemByNameEC3("genericItem",16),getItemByNameEC3("genericItem",16),getItemByNameEC3("genericItem",16),getItemByNameEC3("genericItem",16)},getItemByNameEC3("storage",4), 500);
		MagicianTableRecipes.addRecipeIS(new UnformedItemStack[]{new UnformedItemStack(getItemByNameEC3("genericItem",7)),new UnformedItemStack("ingotMithriline"),new UnformedItemStack("ingotMithriline"),new UnformedItemStack("ingotMithriline"),new UnformedItemStack("ingotMithriline")},getItemByNameEC3("genericItem",49), 400);
		MagicianTableRecipes.addRecipeIS(new UnformedItemStack[]{new UnformedItemStack(getItemByNameEC3("genericItem",7)),new UnformedItemStack("ingotDemonic"),new UnformedItemStack("ingotDemonic"),new UnformedItemStack("ingotDemonic"),new UnformedItemStack("ingotDemonic")},getItemByNameEC3("genericItem",54), 2000);
		MagicianTableRecipes.addRecipeIS(new ItemStack[]{getItemByNameEC3("genericItem",7),new ItemStack(Items.blaze_powder),new ItemStack(Items.blaze_powder),new ItemStack(Items.blaze_powder),new ItemStack(Items.blaze_powder)},getItemByNameEC3("genericItem",79), 500);
		ItemStack book_t1 = new ItemStack(ItemsCore.research_book);
		MiscUtils.getStackTag(book_t1).setInteger("tier", 1);
		ItemStack book_t2 = new ItemStack(ItemsCore.research_book);
		MiscUtils.getStackTag(book_t2).setInteger("tier", 2);
		MagicianTableRecipes.addRecipeIS(new ItemStack[]{book_t1,null,null,null,null},book_t2, 100);
		MagicianTableRecipes.addRecipeIS(new ItemStack[]{new ItemStack(BlocksCore.voidStone),null,null,null,null},getItemByNameEC3("genericItem",35), 1000);
	}
	
	public void registerMithrilineFurnace()
	{
		//TODO MithrilineFurnaceRecipes
		MithrilineFurnaceRecipes.addRecipe("dustMithriline", getItemByNameEC3("genericItem",50), 60,1);
		MithrilineFurnaceRecipes.addRecipe("gemResonant", getItemByNameEC3("genericItem",48), 120,1);
		MithrilineFurnaceRecipes.addRecipe("gemMithriline", getItemByNameEC3("genericItem",47), 240,1);
		MithrilineFurnaceRecipes.addRecipe(getItemByNameEC3("genericItem",47), new ItemStack(ItemsCore.genericItem,8,46), 480,1);
		MithrilineFurnaceRecipes.addRecipe("dustGlowstone", getItemByNameEC3("genericItem",51), 32,1);
		MithrilineFurnaceRecipes.addRecipe("ingotIron", new ItemStack(Items.gold_ingot), 64,8);
		MithrilineFurnaceRecipes.addRecipe("ingotGold", new ItemStack(Items.iron_ingot,8,0), 64,1);
		MithrilineFurnaceRecipes.addRecipe("gemDiamond", new ItemStack(Items.emerald), 512,2);
		MithrilineFurnaceRecipes.addRecipe("gemEmerald", new ItemStack(Items.diamond,2,0), 512,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Blocks.planks,1,0), new ItemStack(Blocks.planks,1,1), 1,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Blocks.planks,1,1), new ItemStack(Blocks.planks,1,2), 1,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Blocks.planks,1,2), new ItemStack(Blocks.planks,1,3), 1,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Blocks.planks,1,3), new ItemStack(Blocks.planks,1,4), 1,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Blocks.planks,1,4), new ItemStack(Blocks.planks,1,5), 1,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Blocks.planks,1,5), new ItemStack(Blocks.planks,1,0), 1,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Blocks.sapling,1,0), new ItemStack(Blocks.sapling,1,1), 1,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Blocks.sapling,1,1), new ItemStack(Blocks.sapling,1,2), 1,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Blocks.sapling,1,2), new ItemStack(Blocks.sapling,1,3), 1,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Blocks.sapling,1,3), new ItemStack(Blocks.sapling,1,4), 1,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Blocks.sapling,1,4), new ItemStack(Blocks.sapling,1,5), 1,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Blocks.sapling,1,5), new ItemStack(Blocks.sapling,1,0), 1,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Blocks.log,1,0), new ItemStack(Blocks.log,1,1), 1,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Blocks.log,1,1), new ItemStack(Blocks.log,1,2), 1,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Blocks.log,1,2), new ItemStack(Blocks.log,1,3), 1,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Blocks.log,1,3), new ItemStack(Blocks.log2,1,0), 1,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Blocks.log2,1,0), new ItemStack(Blocks.log2,1,1), 1,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Blocks.log2,1,1), new ItemStack(Blocks.log,1,0), 1,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Blocks.leaves,1,0), new ItemStack(Blocks.leaves,1,1), 1,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Blocks.leaves,1,1), new ItemStack(Blocks.leaves,1,2), 1,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Blocks.leaves,1,2), new ItemStack(Blocks.leaves,1,3), 1,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Blocks.leaves,1,3), new ItemStack(Blocks.leaves2,1,0), 1,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Blocks.leaves2,1,0), new ItemStack(Blocks.leaves2,1,1), 1,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Blocks.leaves2,1,1), new ItemStack(Blocks.leaves,1,0), 1,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Blocks.melon_block,1,0), new ItemStack(Blocks.pumpkin,1,0), 1,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Blocks.pumpkin,1,0), new ItemStack(Blocks.melon_block,1,0), 1,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Blocks.yellow_flower,1,0), new ItemStack(Blocks.red_flower,1,0), 1,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Blocks.red_flower,1,0), new ItemStack(Blocks.red_flower,1,1), 1,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Blocks.red_flower,1,1), new ItemStack(Blocks.red_flower,1,2), 1,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Blocks.red_flower,1,2), new ItemStack(Blocks.red_flower,1,3), 1,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Blocks.red_flower,1,3), new ItemStack(Blocks.red_flower,1,4), 1,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Blocks.red_flower,1,4), new ItemStack(Blocks.red_flower,1,5), 1,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Blocks.red_flower,1,5), new ItemStack(Blocks.red_flower,1,6), 1,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Blocks.red_flower,1,6), new ItemStack(Blocks.red_flower,1,7), 1,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Blocks.red_flower,1,7), new ItemStack(Blocks.red_flower,1,8), 1,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Blocks.red_flower,1,8), new ItemStack(Blocks.yellow_flower,1,0), 1,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Blocks.red_mushroom,1,0), new ItemStack(Blocks.brown_mushroom,1,0), 1,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Blocks.brown_mushroom,1,0), new ItemStack(Blocks.red_mushroom,1,0), 1,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Items.ender_pearl,1,0), new ItemStack(Items.blaze_rod,2,0), 128,3);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Items.blaze_rod,1,0), new ItemStack(Items.ender_pearl,3,0), 128,2);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Items.redstone,1,0), new ItemStack(Items.ghast_tear,1,0), 1024,64);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Items.ghast_tear,1,0), new ItemStack(Items.redstone,64,0), 1024,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Items.clay_ball,1,0), new ItemStack(Items.gunpowder,1,0), 32,12);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Items.gunpowder,1,0), new ItemStack(Items.clay_ball,12,0), 32,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Items.porkchop,1,0), new ItemStack(Items.beef,1,0), 16,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Items.beef,1,0), new ItemStack(Items.chicken,1,0), 16,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Items.chicken,1,0), new ItemStack(Items.fish,1,0), 16,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Items.fish,1,0), new ItemStack(Items.rotten_flesh,2,0), 16,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Items.rotten_flesh,1,0), new ItemStack(Items.porkchop,1,0), 16,2);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Items.cooked_porkchop,1,0), new ItemStack(Items.cooked_beef,1,0), 16,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Items.cooked_beef,1,0), new ItemStack(Items.cooked_chicken,1,0), 16,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Items.cooked_chicken,1,0), new ItemStack(Items.cooked_fished,1,0), 16,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Items.cooked_fished,1,0), new ItemStack(Items.cooked_fished,1,1), 16,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Items.cooked_fished,1,1), new ItemStack(Items.cooked_porkchop,1,0), 16,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Items.record_13,1,0), new ItemStack(Items.record_cat,1,0), 256,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Items.record_cat,1,0), new ItemStack(Items.record_blocks,1,0), 256,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Items.record_blocks,1,0), new ItemStack(Items.record_chirp,1,0), 256,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Items.record_chirp,1,0), new ItemStack(Items.record_far,1,0), 256,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Items.record_far,1,0), new ItemStack(Items.record_mall,1,0), 256,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Items.record_mall,1,0), new ItemStack(Items.record_mellohi,1,0), 256,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Items.record_mellohi,1,0), new ItemStack(Items.record_stal,1,0), 256,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Items.record_stal,1,0), new ItemStack(Items.record_strad,1,0), 256,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Items.record_strad,1,0), new ItemStack(Items.record_ward,1,0), 256,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Items.record_ward,1,0), new ItemStack(Items.record_11,1,0), 256,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Items.record_11,1,0), new ItemStack(Items.record_wait,1,0), 256,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Items.record_wait,1,0), new ItemStack(Items.record_13,1,0), 256,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Items.sugar,1,0), new ItemStack(Items.slime_ball,4,0), 16,3);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Items.slime_ball,1,0), new ItemStack(Items.sugar,3,0), 16,4);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Items.egg,1,0), new ItemStack(Items.bone,2,0), 48,9);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Items.bone,1,0), new ItemStack(Items.egg,9,0), 48,2);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Items.skull,1,0), new ItemStack(Items.skull,1,1), 64,3);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Items.skull,1,1), new ItemStack(Items.skull,3,2), 64,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Items.skull,1,2), new ItemStack(Items.skull,1,3), 64,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Items.skull,1,3), new ItemStack(Items.skull,1,4), 64,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Items.skull,1,4), new ItemStack(Items.skull,1,0), 64,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Items.wheat,1,0), new ItemStack(Items.leather,1,0), 128,3);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Items.leather,1,0), new ItemStack(Items.wheat,3,0), 128,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Items.fish,1,1), new ItemStack(Items.fish,1,2), 24,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Items.fish,1,2), new ItemStack(Items.fish,1,3), 24,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Items.fish,1,3), new ItemStack(Items.fish,1,1), 24,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Items.reeds,1,0), new ItemStack(Items.feather,2,0), 64,3);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Items.feather,1,0), new ItemStack(Items.reeds,3,0), 64,2);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Items.coal,1,0), new ItemStack(Items.coal,1,1), 1,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Items.coal,1,1), new ItemStack(Items.coal,1,0), 1,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Blocks.fire,1,0), new ItemStack(Blocks.coal_block,1,0), 512,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Blocks.coal_block,1,0), new ItemStack(Blocks.fire,1,0), 512,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Blocks.ice,1,0), new ItemStack(Blocks.grass,1,0), 1,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Blocks.grass,1,0), new ItemStack(Blocks.dirt,1,0), 1,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Blocks.dirt,1,0), new ItemStack(Blocks.dirt,1,2), 1,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Blocks.dirt,1,2), new ItemStack(Blocks.glass,1,0), 1,1);
		MithrilineFurnaceRecipes.addRecipe(new ItemStack(Blocks.glass,1,0), new ItemStack(Blocks.ice,1,0), 1,1);
	}
	
	@SuppressWarnings("unused")
	public static void registerWindRecipes()
	{
		new WindImbueRecipe(new ItemStack(ItemsCore.soulStone,1,0),new ItemStack(ItemsCore.soulStone,1,0),10000*4);
		new WindImbueRecipe(new ItemStack(Items.diamond,1,0),new ItemStack(ItemsCore.genericItem,1,55),10000);
		new WindImbueRecipe(new ItemStack(Items.potionitem,1,0),new ItemStack(ItemsCore.air_potion,1,0),250);
		//TODO wind recipes
	}
	
	@SuppressWarnings("unused")
	public static void registerDemonTrades()
	{
		//TODO demon trades
		new DemonTrade(EntityVillager.class);
		new DemonTrade(EntityEnderman.class);
		new DemonTrade(EntityWindMage.class);
		new DemonTrade(new ItemStack(Blocks.diamond_block,2,0));
		new DemonTrade(new ItemStack(Blocks.bedrock,1,0));
		new DemonTrade(new ItemStack(Blocks.iron_block,8,0));
		new DemonTrade(new ItemStack(Blocks.tnt,64,0));
		new DemonTrade(new ItemStack(Blocks.enchanting_table,16,0));
		new DemonTrade(new ItemStack(Blocks.bookshelf,64,0));
		new DemonTrade(new ItemStack(Blocks.end_portal_frame,3,0));
		new DemonTrade(new ItemStack(Blocks.dragon_egg,1,0));
		new DemonTrade(new ItemStack(Blocks.beacon,1,0));
		new DemonTrade(new ItemStack(Blocks.command_block,1,0));
		new DemonTrade(new ItemStack(Items.golden_apple,1,1));
		new DemonTrade(new ItemStack(Items.map,1,OreDictionary.WILDCARD_VALUE));
		new DemonTrade(new ItemStack(Items.ender_eye,32,0));
		new DemonTrade(new ItemStack(Items.skull,1,3));
		new DemonTrade(new ItemStack(Items.nether_star,1,0));
		new DemonTrade(new ItemStack(Items.record_11,1,0));
		new DemonTrade(new ItemStack(BlocksCore.magicalEnchanter,1,0));
		new DemonTrade(new ItemStack(BlocksCore.magicalRepairer,1,0));
		new DemonTrade(new ItemStack(BlocksCore.heatGenerator,3,0));
		new DemonTrade(new ItemStack(BlocksCore.magicalTeleporter,2,0));
		new DemonTrade(new ItemStack(BlocksCore.mruCoil,1,0));
		new DemonTrade(new ItemStack(BlocksCore.ultraHeatGen,1,0));
		new DemonTrade(new ItemStack(BlocksCore.darknessObelisk,1,0));
		new DemonTrade(new ItemStack(BlocksCore.mithrilineCrystal,12,0));
		new DemonTrade(new ItemStack(BlocksCore.mithrilineCrystal,6,3));
		new DemonTrade(new ItemStack(BlocksCore.mithrilineCrystal,3,6));
		new DemonTrade(new ItemStack(BlocksCore.compressed,32,4));
		new DemonTrade(new ItemStack(ItemsCore.emeraldHeart,1,0));
		new DemonTrade(new ItemStack(ItemsCore.spikyShield,1,0));
		new DemonTrade(new ItemStack(ItemsCore.magicalShield,1,0));
		new DemonTrade(new ItemStack(ItemsCore.magicWaterBottle,1,0));
		new DemonTrade(new ItemStack(ItemsCore.wind_elemental_pick,1,0));
		new DemonTrade(new ItemStack(ItemsCore.wind_elemental_axe,1,0));
		new DemonTrade(new ItemStack(ItemsCore.wind_elemental_hoe,1,0));
		new DemonTrade(new ItemStack(ItemsCore.wind_elemental_shovel,1,0));
		new DemonTrade(new ItemStack(ItemsCore.wind_elemental_sword,1,0));
		new DemonTrade(new ItemStack(ItemsCore.wind_chestplate,1,0));
		new DemonTrade(new ItemStack(ItemsCore.wind_helmet,1,0));
		new DemonTrade(new ItemStack(ItemsCore.wind_boots,1,0));
		new DemonTrade(new ItemStack(ItemsCore.wind_leggings,1,0));
		new DemonTrade(new ItemStack(ItemsCore.magicalPorkchop,1,0));
		new DemonTrade(new ItemStack(ItemsCore.chaosFork,1,0));
		new DemonTrade(new ItemStack(ItemsCore.research_book,1,0));
		new DemonTrade(new ItemStack(ItemsCore.record_everlastingSummer,1,0));
		new DemonTrade(new ItemStack(ItemsCore.pistol,1,0));
		new DemonTrade(new ItemStack(ItemsCore.rifle,1,0));
		new DemonTrade(new ItemStack(ItemsCore.sniper,1,0));
		new DemonTrade(new ItemStack(ItemsCore.gatling,1,0));
	}
	
	public static ItemStack getItemByNameEC3(String itemName, int metadata)
	{
		Class<BlocksCore> blocks = BlocksCore.class;
		try {
			Field block = blocks.getDeclaredField(itemName);
			ItemStack is = new ItemStack((Block) block.get(null),1,metadata);
			return is;
		} catch (Exception e) {
			try {
				Class<ItemsCore> items = ItemsCore.class;
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
	
	public static ItemStack gen(int meta)
	{
		return new ItemStack(ItemsCore.genericItem,1,meta);
	}
}

