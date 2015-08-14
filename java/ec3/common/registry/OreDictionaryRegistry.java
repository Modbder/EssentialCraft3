package ec3.common.registry;

import static ec3.utils.cfg.Config.allowPaleItemsInOtherRecipes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import ec3.common.block.BlocksCore;
import ec3.common.item.ItemsCore;

public class OreDictionaryRegistry {
	
	public static void register()
	{
		OreDictionary.registerOre("blockDropsFire", new ItemStack(BlocksCore.drops,1,0));
		OreDictionary.registerOre("blockDropsWater", new ItemStack(BlocksCore.drops,1,1));
		OreDictionary.registerOre("blockDropsEarth", new ItemStack(BlocksCore.drops,1,2));
		OreDictionary.registerOre("blockDropsAir", new ItemStack(BlocksCore.drops,1,3));
		OreDictionary.registerOre("blockMagicalPlating", new ItemStack(BlocksCore.magicPlating,1,OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("glass", new ItemStack(BlocksCore.fortifiedGlass,1,OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("stone", new ItemStack(BlocksCore.fortifiedStone,1,OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("iceCompressed", new ItemStack(BlocksCore.coldStone,1,OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("blockVoid", new ItemStack(BlocksCore.voidStone,1,OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("blockVoidStone", new ItemStack(BlocksCore.voidStone,1,OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("voidStone", new ItemStack(BlocksCore.voidStone,1,OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("glassVoid", new ItemStack(BlocksCore.voidGlass,1,OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("blockGlassVoid", new ItemStack(BlocksCore.voidGlass,1,OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("voidGlass", new ItemStack(BlocksCore.voidGlass,1,OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("blockVoidGlass", new ItemStack(BlocksCore.voidGlass,1,OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("blockConcrete", new ItemStack(BlocksCore.concrete,1,OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("concrete", new ItemStack(BlocksCore.concrete,1,OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("cacti", new ItemStack(BlocksCore.cacti,1,OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("cactus", new ItemStack(BlocksCore.cacti,1,OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("blockCacti", new ItemStack(BlocksCore.cacti,1,OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("blockCactus", new ItemStack(BlocksCore.cacti,1,OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("dirt", new ItemStack(BlocksCore.dreadDirt,1,OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("blockDirt", new ItemStack(BlocksCore.dreadDirt,1,OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("log", new ItemStack(BlocksCore.root,1,OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("blockLog", new ItemStack(BlocksCore.root,1,OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("fence", new ItemStack(BlocksCore.fence[0],1,OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("fence", new ItemStack(BlocksCore.fence[1],1,OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("fence", new ItemStack(BlocksCore.fence[2],1,OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("blockPalePlating", new ItemStack(BlocksCore.platingPale,1,OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("blockMitrilinePlating", new ItemStack(BlocksCore.invertedBlock,1,OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("chest", new ItemStack(BlocksCore.chest,1,OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("blockChest", new ItemStack(BlocksCore.chest,1,OreDictionary.WILDCARD_VALUE));
		
		OreDictionary.registerOre("blockElemental", new ItemStack(BlocksCore.compressed,1,4));
		OreDictionary.registerOre("compressedShardFire", new ItemStack(BlocksCore.compressed,1,0));
		OreDictionary.registerOre("compressedShardWater", new ItemStack(BlocksCore.compressed,1,1));
		OreDictionary.registerOre("compressedShardEarth", new ItemStack(BlocksCore.compressed,1,2));
		OreDictionary.registerOre("compressedShardWater", new ItemStack(BlocksCore.compressed,1,3));
		
		OreDictionary.registerOre("oreShardFire", new ItemStack(BlocksCore.oreDrops,1,1));
		OreDictionary.registerOre("oreShardWater", new ItemStack(BlocksCore.oreDrops,1,2));
		OreDictionary.registerOre("oreShardEarth", new ItemStack(BlocksCore.oreDrops,1,3));
		OreDictionary.registerOre("oreShardAir", new ItemStack(BlocksCore.oreDrops,1,4));
		OreDictionary.registerOre("oreShardElemental", new ItemStack(BlocksCore.oreDrops,1,0));
		OreDictionary.registerOre("oreShardFire", new ItemStack(BlocksCore.oreDrops,1,6));
		OreDictionary.registerOre("oreShardWater", new ItemStack(BlocksCore.oreDrops,1,7));
		OreDictionary.registerOre("oreShardEarth", new ItemStack(BlocksCore.oreDrops,1,8));
		OreDictionary.registerOre("oreShardAir", new ItemStack(BlocksCore.oreDrops,1,9));
		OreDictionary.registerOre("oreShardElemental", new ItemStack(BlocksCore.oreDrops,1,5));
		OreDictionary.registerOre("oreShardFire", new ItemStack(BlocksCore.oreDrops,1,11));
		OreDictionary.registerOre("oreShardWater", new ItemStack(BlocksCore.oreDrops,1,12));
		OreDictionary.registerOre("oreShardEarth", new ItemStack(BlocksCore.oreDrops,1,13));
		OreDictionary.registerOre("oreShardAir", new ItemStack(BlocksCore.oreDrops,1,14));
		OreDictionary.registerOre("oreShardElemental", new ItemStack(BlocksCore.oreDrops,1,10));
		
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
		
		OreDictionary.registerOre("record", new ItemStack(ItemsCore.record_everlastingSummer,1,0));
		OreDictionary.registerOre("record", new ItemStack(ItemsCore.record_papersPlease,1,0));
		OreDictionary.registerOre("record", new ItemStack(ItemsCore.record_robocalypse,1,0));
		OreDictionary.registerOre("record", new ItemStack(ItemsCore.record_secret,1,0));
	}
}
