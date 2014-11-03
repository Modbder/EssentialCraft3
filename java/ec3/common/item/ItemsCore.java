package ec3.common.item;

import ec3.api.ApiCore;
import ec3.common.mod.EssentialCraftCore;
import DummyCore.Items.ItemRegistry;
import DummyCore.Utils.MiscUtils;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemFood;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.EnumHelper;
import static ec3.utils.cfg.Config.getIdForItem;

public class ItemsCore {

	public static ItemsCore instance;

	public void loadItems() {
		elemental = EnumHelper.addToolMaterial("elemental", 4, 1842, 9.0F, 3.5F, 21);
		//System.out.println("registry");
		testingItem = new TestItem_EC().setUnlocalizedName("essentialcraft:testItem").setTextureName("minecraft:blaze_rod").setMaxStackSize(1);
		Item.itemRegistry.addObject(getIdForItem("testItem"), "essentialcraft:testItem", testingItem);
		ItemRegistry.registerItem(testingItem, EssentialCraftCore.class);
		mruMover1 = new ItemMRUMover().setUnlocalizedName("essentialcraft:mruMover1").setTextureName("essentialcraft:mru_mover_t1").setMaxStackSize(1).setFull3D();
		Item.itemRegistry.addObject(getIdForItem("mruMover1"), "essentialcraft:mruMover1", mruMover1);
		ItemRegistry.registerItem(mruMover1, EssentialCraftCore.class);
		drops = new ItemDrop().setUnlocalizedName("essentialcraft:drops_").setTextureName("minecraft:blaze_rod").setMaxStackSize(16);
		Item.itemRegistry.addObject(getIdForItem("drops"), "essentialcraft:drops", drops);
		ItemRegistry.registerItem(drops, EssentialCraftCore.class);
		elemental_pick = new ItemPickaxe_Mod(elemental).setUnlocalizedName("essentialcraft:elemental_pick").setTextureName("essentialcraft:elemental_pickaxe").setMaxStackSize(1).setFull3D();
		Item.itemRegistry.addObject(getIdForItem("elemental_pick"), "essentialcraft:elemental_pick", elemental_pick);
		ItemRegistry.registerItem(elemental_pick, EssentialCraftCore.class);
		elemental_axe = new ItemAxe_Mod(elemental).setUnlocalizedName("essentialcraft:elemental_axe").setTextureName("essentialcraft:elemental_axe").setMaxStackSize(1).setFull3D();
		Item.itemRegistry.addObject(getIdForItem("elemental_axe"), "essentialcraft:elemental_axe", elemental_axe);
		ItemRegistry.registerItem(elemental_axe, EssentialCraftCore.class);
		elemental_hoe = new ItemHoe_Mod(elemental).setUnlocalizedName("essentialcraft:elemental_hoe").setTextureName("essentialcraft:elemental_hoe").setMaxStackSize(1).setFull3D();
		Item.itemRegistry.addObject(getIdForItem("elemental_axe"), "essentialcraft:elemental_hoe", elemental_hoe);
		ItemRegistry.registerItem(elemental_hoe, EssentialCraftCore.class);
		elemental_shovel = new ItemShovel_Mod(elemental).setUnlocalizedName("essentialcraft:elemental_shovel").setTextureName("essentialcraft:elemental_shovel").setMaxStackSize(1).setFull3D();
		Item.itemRegistry.addObject(getIdForItem("elemental_shovel"), "essentialcraft:elemental_shovel", elemental_shovel);
		ItemRegistry.registerItem(elemental_shovel, EssentialCraftCore.class);
		elemental_sword = new ItemSword_Mod(elemental).setUnlocalizedName("essentialcraft:elemental_sword").setTextureName("essentialcraft:elemental_sword").setMaxStackSize(1).setFull3D();
		Item.itemRegistry.addObject(getIdForItem("elemental_sword"), "essentialcraft:elemental_sword", elemental_sword);
		ItemRegistry.registerItem(elemental_sword, EssentialCraftCore.class);
		bound_gem = new ItemBoundGem().setUnlocalizedName("essentialcraft:bound_gem").setTextureName("essentialcraft:gem_bound").setFull3D();
		Item.itemRegistry.addObject(getIdForItem("bound_gem"), "essentialcraft:bound_gem", bound_gem);
		ItemRegistry.registerItem(bound_gem, EssentialCraftCore.class);
		magicMonocle=registerItemSimple(magicMonocle,ItemMonocle.class,"magicMonocle","magicMonocle",16,true,1);
		
		record_everlastingSummer = new ItemRecord_Mod("letsbefriends").setUnlocalizedName("essentialcraft:record").setTextureName("record_blocks");
		Item.itemRegistry.addObject(getIdForItem("record"), "essentialcraft:record", record_everlastingSummer);
		ItemRegistry.registerItem(record_everlastingSummer, EssentialCraftCore.class);
		
		record_papersPlease = new ItemRecord_Mod("arstotzkan").setUnlocalizedName("essentialcraft:record_a").setTextureName("record_far");
		Item.itemRegistry.addObject(getIdForItem("record_a"), "essentialcraft:record_a", record_papersPlease);
		ItemRegistry.registerItem(record_papersPlease, EssentialCraftCore.class);
		
		record_secret = new ItemRecord_Mod("secret").setUnlocalizedName("essentialcraft:record_secret").setTextureName("record_13");
		Item.itemRegistry.addObject(getIdForItem("record_secret"), "essentialcraft:record_secret", record_secret);
		ItemRegistry.registerItem(record_secret, EssentialCraftCore.class);
		
		soulStone = registerItemSimple(soulStone,ItemSoulStone.class,"soulStone","soulStone",0,false,1);
		matrixProj = registerItemSimple(matrixProj,ItemMRUMatrixProjection.class,"mruMatrixProjection","mruMatrix_empty",0,false,1);
		spell = registerItemSimple(spell,ItemSpell.class,"spell","soulStone",0,false,1);
		staff = registerItemSimple(staff,ItemStaff.class,"staff","staff_metalic",0,true,1);
		titanite = registerItemSimple(titanite,Item.class,"titanite","titanite",0,false,64);
		twinkling_titanite = registerItemSimple(twinkling_titanite,Item.class,"twinkling_titanite","twinkling_titanite",0,false,64);
		secret = registerItemSimple(secret,ItemSecret.class,"secret","soulStone",0,false,1);
		magicalSlag = registerItemSimple(magicalSlag,Item.class,"magicalSlag","magicalSlag",0,false,64);
		genericItem = registerItemSimple(genericItem,ItemGenericEC3.class,"genItem","magicalSlag",0,false,64);
		magicalAlloy = registerItemSimple(magicalAlloy,ItemMagicalAlloy.class,"magicalAlloy","magicalSlag",0,false,64);
		essence = registerItemSimple(essence,ItemEssence.class,"essence","magicalSlag",0,false,16);
		
		storage = new ItemMRUStorageNBTTag(new int[]{500,5000,30000,120000,1000000}).setUnlocalizedName("essentialcraft:storage").setTextureName("essentialcraft:soulStone");
		Item.itemRegistry.addObject(getIdForItem("storage"), "essentialcraft:storage", storage);
		ItemRegistry.registerItem(storage, EssentialCraftCore.class);
		
		magicalDigger = registerItemSimple(magicalDigger,ItemMagicalDigger.class,"magicalDigger","tools/magicalDigger",0,true,1);
		spawnerCollector = registerItemSimple(spawnerCollector,ItemSpawnerCollector.class,"spawnerCollector","tools/spawnerCollector",0,true,1);
		staffOfLife = registerItemSimple(staffOfLife,ItemLifeStaff.class,"staffOfLife","tools/staffOfLife",0,true,1);
		biomeWand = registerItemSimple(biomeWand,ItemBiomeWand.class,"biomeWand","tools/biomeWand",0,true,1);
		
		emeraldHeart = registerItemSimple(emeraldHeart,ItemEmeraldHeart.class,"emeraldHeart","tools/emeraldHeart",0,true,1);
		magicalShield = registerItemSimple(magicalShield,ItemMagicalShield.class,"magicalShield","tools/magicalShield",0,true,1);
		spikyShield = registerItemSimple(spikyShield,ItemSpikyShield.class,"spikyShield","tools/spikyShield",0,true,1);
		magicWaterBottle = registerItemSimple(magicWaterBottle,ItemMagicalWater.class,"magicWaterBottle","tools/magicWaterBottle",0,true,1);
		magicalPorkchop = registerItemSimple(magicalPorkchop,ItemMagicalPorkchop.class,"magicalPorkchop","tools/magicalPorkchop",0,true,1);
		charm = registerItemSimple(charm,ItemCharm.class,"charm","tools/charmFire",0,true,1);
		magicalWings = registerItemSimple(magicalWings,ItemMagicalWings.class,"magicalWings","tools/magicalWings",0,true,1);
		
		chaosFork = registerItemSimple(chaosFork,ItemChaosFork.class,"chaosFork","tools/chaosFork",0,true,1);
		frozenMace = registerItemSimple(frozenMace,ItemFrostMace.class,"frozenMace","tools/frozenMace",0,true,1);
		holyMace = registerItemSimple(holyMace,ItemHolyMace.class,"holyMace","tools/holyMace",0,true,1);
		
		elementalFuel = registerItemSimple(elementalFuel,ItemEssentialFuel.class,"elementalFuel","tools/emeraldHeart",0,false,16);
		
		fFocus = registerItemSimple(fFocus,ItemElementalFocus.class,"fFocus","eFocusFire",3000,false,1);
		wFocus = registerItemSimple(wFocus,ItemElementalFocus.class,"wFocus","eFocusWater",3000,false,1);
		eFocus = registerItemSimple(eFocus,ItemElementalFocus.class,"eFocus","eFocusEarth",3000,false,1);
		aFocus = registerItemSimple(aFocus,ItemElementalFocus.class,"aFocus","eFocusAir",3000,false,1);
		
		fruit = new ItemFood(8, 10F, false).setUnlocalizedName("essentialcraft:fruit_Item").setTextureName("essentialcraft:fruit");
		Item.itemRegistry.addObject(getIdForItem("fruit_Item"), "essentialcraft:fruit_Item", fruit);
		ItemRegistry.registerItem(fruit, EssentialCraftCore.class);
		
		bottledWind = new ItemWindKeeper(10).setUnlocalizedName("essentialcraft:bottledWind").setTextureName("essentialcraft:bottledWind");
		Item.itemRegistry.addObject(getIdForItem("bottledWind"), "essentialcraft:bottledWind", bottledWind);
		ItemRegistry.registerItem(bottledWind, EssentialCraftCore.class);
		
		imprisonedWind = new ItemWindKeeper(25).setUnlocalizedName("essentialcraft:imprisonedWind").setTextureName("essentialcraft:imprisonedWind");
		Item.itemRegistry.addObject(getIdForItem("imprisonedWind"), "essentialcraft:imprisonedWind", imprisonedWind);
		ItemRegistry.registerItem(imprisonedWind, EssentialCraftCore.class);
		
		windKeeper = new ItemWindKeeper(100).setUnlocalizedName("essentialcraft:windKeeper").setTextureName("essentialcraft:windkeeper");
		Item.itemRegistry.addObject(getIdForItem("windKeeper"), "essentialcraft:windKeeper", windKeeper);
		ItemRegistry.registerItem(windKeeper, EssentialCraftCore.class);
		
		windTablet = registerItemSimple(windTablet,ItemWindTablet.class,"windTablet","windTablet",0,false,1);
		
		elementalSword = registerItemSimple(elementalSword,ItemElementalSword.class,"elementalSword","elementalSword",0,false,1);
		ember = registerItemSimple(ember,ItemEmber.class,"ember","windTablet",0,false,16);
		
		
		
		magicArmorItems[0] = new ItemArmorMod(magicArmorMaterial, 4, 0).setArmorTexture("armorEC3Magic").setUnlocalizedName("essentialcraft:helm_magic").setMaxDamage(512);
		Item.itemRegistry.addObject(getIdForItem("helm_magic"), "essentialcraft:helm_magic", magicArmorItems[0]);
		ItemRegistry.registerItem(magicArmorItems[0], EssentialCraftCore.class);
		ApiCore.allowItemToSeeMRU(magicArmorItems[0]);
		ApiCore.setArmorProperties(magicArmorItems[0], 0.25F, 0.125F, 0.10F);
		
		magicArmorItems[1] = new ItemArmorMod(magicArmorMaterial, 4, 1).setArmorTexture("armorEC3Magic").setUnlocalizedName("essentialcraft:chest_magic").setMaxDamage(1536);
		Item.itemRegistry.addObject(getIdForItem("chest_magic"), "essentialcraft:chest_magic", magicArmorItems[1]);
		ItemRegistry.registerItem(magicArmorItems[1], EssentialCraftCore.class);
		ApiCore.setArmorProperties(magicArmorItems[1], 0.25F, 0.125F, 0.10F);
		
		magicArmorItems[2] = new ItemArmorMod(magicArmorMaterial, 4, 2).setArmorTexture("armorEC3Magic").setUnlocalizedName("essentialcraft:legs_magic").setMaxDamage(1024);
		Item.itemRegistry.addObject(getIdForItem("legs_magic"), "essentialcraft:legs_magic", magicArmorItems[2]);
		ItemRegistry.registerItem(magicArmorItems[2], EssentialCraftCore.class);
		ApiCore.setArmorProperties(magicArmorItems[2], 0.25F, 0.125F, 0.10F);
		
		magicArmorItems[3] = new ItemArmorMod(magicArmorMaterial, 4, 3).setArmorTexture("armorEC3Magic").setUnlocalizedName("essentialcraft:boots_magic").setMaxDamage(512);
		Item.itemRegistry.addObject(getIdForItem("boots_magic"), "essentialcraft:boots_magic", magicArmorItems[3]);
		ItemRegistry.registerItem(magicArmorItems[3], EssentialCraftCore.class);
		ApiCore.setArmorProperties(magicArmorItems[3], 0.25F, 0.125F, 0.10F);
		
		
		magicArmorItems[4] = new ItemArmorMod(voidArmorMaterial, 4, 0).setArmorTexture("armorEC3Void").setUnlocalizedName("essentialcraft:helm_void").setMaxDamage(512*4);
		Item.itemRegistry.addObject(getIdForItem("helm_void"), "essentialcraft:helm_void", magicArmorItems[4]);
		ItemRegistry.registerItem(magicArmorItems[4], EssentialCraftCore.class);
		ApiCore.allowItemToSeeMRU(magicArmorItems[4]);
		ApiCore.setArmorProperties(magicArmorItems[4], 1F, 0.5F, 0.75F);
		MiscUtils.registerDescriptionFor(magicArmorItems[4].getUnlocalizedName(), "Converts life energy of fallen foes to your hunger", EnumChatFormatting.ITALIC);
		
		magicArmorItems[5] = new ItemArmorMod(voidArmorMaterial, 4, 1).setArmorTexture("armorEC3Void").setUnlocalizedName("essentialcraft:chest_void").setMaxDamage(1536*4);
		Item.itemRegistry.addObject(getIdForItem("chest_void"), "essentialcraft:chest_void", magicArmorItems[5]);
		ItemRegistry.registerItem(magicArmorItems[5], EssentialCraftCore.class);
		ApiCore.setArmorProperties(magicArmorItems[5], 1F, 0.5F, 0.75F);
		MiscUtils.registerDescriptionFor(magicArmorItems[5].getUnlocalizedName(), "Can dodge 20% of attacks", EnumChatFormatting.ITALIC);
		MiscUtils.registerItemModifier(magicArmorItems[5], -1, "armor", "02001", 20D, SharedMonsterAttributes.maxHealth, 0);
		
		magicArmorItems[6] = new ItemArmorMod(voidArmorMaterial, 4, 2).setArmorTexture("armorEC3Void").setUnlocalizedName("essentialcraft:legs_void").setMaxDamage(1024*4);
		Item.itemRegistry.addObject(getIdForItem("legs_void"), "essentialcraft:legs_void", magicArmorItems[6]);
		ItemRegistry.registerItem(magicArmorItems[6], EssentialCraftCore.class);
		ApiCore.setArmorProperties(magicArmorItems[6], 1F, 0.5F, 0.75F);
		MiscUtils.registerDescriptionFor(magicArmorItems[6].getUnlocalizedName(), "Can phase-shift through monsters", EnumChatFormatting.ITALIC);
		
		magicArmorItems[7] = new ItemArmorMod(voidArmorMaterial, 4, 3).setArmorTexture("armorEC3Void").setUnlocalizedName("essentialcraft:boots_void").setMaxDamage(512*4);
		Item.itemRegistry.addObject(getIdForItem("boots_void"), "essentialcraft:boots_void", magicArmorItems[7]);
		ItemRegistry.registerItem(magicArmorItems[7], EssentialCraftCore.class);
		ApiCore.setArmorProperties(magicArmorItems[7], 1F, 0.5F, 0.75F);
		MiscUtils.registerDescriptionFor(magicArmorItems[7].getUnlocalizedName(), "Negates 90% of falldamage", EnumChatFormatting.ITALIC);
		MiscUtils.registerItemModifier(magicArmorItems[7], -1, "armor", "02000", 0.05D, SharedMonsterAttributes.movementSpeed, 0);
	
		
		
		magicArmorItems[8] = new ItemArmorMod(inquisArmorMaterial, 4, 0).setArmorTexture("armorEC3Inquisitorium").setUnlocalizedName("essentialcraft:helm_inq").setMaxDamage(512*10);
		Item.itemRegistry.addObject(getIdForItem("helm_inq"), "essentialcraft:helm_inq", magicArmorItems[8]);
		ItemRegistry.registerItem(magicArmorItems[8], EssentialCraftCore.class);
		ApiCore.allowItemToSeeMRU(magicArmorItems[8]);
		ApiCore.setArmorProperties(magicArmorItems[8], 1F, 1F, 1F);
		MiscUtils.registerDescriptionFor(magicArmorItems[8].getUnlocalizedName(), "Linses created from the moonlight metal to see unpurity", EnumChatFormatting.ITALIC);
		
		magicArmorItems[9] = new ItemArmorMod(inquisArmorMaterial, 4, 1).setArmorTexture("armorEC3Inquisitorium").setUnlocalizedName("essentialcraft:chest_inq").setMaxDamage(1536*10);
		Item.itemRegistry.addObject(getIdForItem("chest_inq"), "essentialcraft:chest_inq", magicArmorItems[9]);
		ItemRegistry.registerItem(magicArmorItems[9], EssentialCraftCore.class);
		ApiCore.setArmorProperties(magicArmorItems[9], 1F, 1F, 1F);
		MiscUtils.registerDescriptionFor(magicArmorItems[9].getUnlocalizedName(), "Be quiet, Dominique!", EnumChatFormatting.ITALIC);
		MiscUtils.registerItemModifier(magicArmorItems[9], -1, "armor", "02001", 30D, SharedMonsterAttributes.maxHealth, 0);
		
		magicArmorItems[10] = new ItemArmorMod(inquisArmorMaterial, 4, 2).setArmorTexture("armorEC3Inquisitorium").setUnlocalizedName("essentialcraft:legs_inq").setMaxDamage(1024*10);
		Item.itemRegistry.addObject(getIdForItem("legs_inq"), "essentialcraft:legs_inq", magicArmorItems[10]);
		ItemRegistry.registerItem(magicArmorItems[10], EssentialCraftCore.class);
		ApiCore.setArmorProperties(magicArmorItems[10], 1F, 1F, 1F);
		MiscUtils.registerDescriptionFor(magicArmorItems[10].getUnlocalizedName(), "Magic is a parasite. It must be wiped out!", EnumChatFormatting.ITALIC);
		
		magicArmorItems[11] = new ItemArmorMod(inquisArmorMaterial, 4, 3).setArmorTexture("armorEC3Inquisitorium").setUnlocalizedName("essentialcraft:boots_inq").setMaxDamage(512*10);
		Item.itemRegistry.addObject(getIdForItem("boots_inq"), "essentialcraft:boots_inq", magicArmorItems[11]);
		ItemRegistry.registerItem(magicArmorItems[11], EssentialCraftCore.class);
		ApiCore.setArmorProperties(magicArmorItems[11], 1F, 1F, 1F);
		MiscUtils.registerDescriptionFor(magicArmorItems[11].getUnlocalizedName(), "Magic shall not prevail!", EnumChatFormatting.ITALIC);
		MiscUtils.registerItemModifier(magicArmorItems[11], -1, "armor", "02000", 0.1D, SharedMonsterAttributes.movementSpeed, 0);
		
		
		
		magicArmorItems[12] = new ItemArmorMod(ArmorMaterial.CLOTH, 4, 0).setArmorTexture("armorEC3Wind").setUnlocalizedName("essentialcraft:helm_wind").setMaxDamage(128);
		Item.itemRegistry.addObject(getIdForItem("helm_wind"), "essentialcraft:helm_wind", magicArmorItems[12]);
		ItemRegistry.registerItem(magicArmorItems[12], EssentialCraftCore.class);
		ApiCore.allowItemToSeeMRU(magicArmorItems[12]);
		
		magicArmorItems[13] = new ItemArmorMod(ArmorMaterial.CLOTH, 4, 1).setArmorTexture("armorEC3Wind").setUnlocalizedName("essentialcraft:chest_wind").setMaxDamage(256+128);
		Item.itemRegistry.addObject(getIdForItem("chest_wind"), "essentialcraft:chest_wind", magicArmorItems[13]);
		ItemRegistry.registerItem(magicArmorItems[13], EssentialCraftCore.class);
		
		magicArmorItems[14] = new ItemArmorMod(ArmorMaterial.CLOTH, 4, 2).setArmorTexture("armorEC3Wind").setUnlocalizedName("essentialcraft:legs_wind").setMaxDamage(256);
		Item.itemRegistry.addObject(getIdForItem("legs_wind"), "essentialcraft:legs_wind", magicArmorItems[14]);
		ItemRegistry.registerItem(magicArmorItems[14], EssentialCraftCore.class);
		
		magicArmorItems[15] = new ItemArmorMod(ArmorMaterial.CLOTH, 4, 3).setArmorTexture("armorEC3Wind").setUnlocalizedName("essentialcraft:boots_wind").setMaxDamage(128);
		Item.itemRegistry.addObject(getIdForItem("boots_wind"), "essentialcraft:boots_wind", magicArmorItems[15]);
		ItemRegistry.registerItem(magicArmorItems[15], EssentialCraftCore.class);
		
		bauble = registerItemSimple(bauble,ItemBaublesWearable.class,"bauble","windTablet",0,true,1);
		
		magmaticStaff = registerItemSimple(magmaticStaff,ItemMagmaticWand.class,"magmaticStaff","tools/magmaticWand",0,true,1);
		magicalLantern = registerItemSimple(magicalLantern,ItemMagicLantern.class,"magicalLantern","tools/elementalPurifier",0,true,1);
		magnetizingStaff = registerItemSimple(magnetizingStaff,ItemMagnetizingStaff.class,"magnetizingStaff","tools/magnetizingStaff",0,true,1);
	}

	public static Item registerItemSimple(Item i, Class<?extends Item> itemClass, String name, String textureName, int damage, boolean full3D, int stackSize)
	{
		try
		{
			i = itemClass.newInstance().setUnlocalizedName("essentialcraft:"+name).setTextureName("essentialcraft:"+textureName).setMaxDamage(damage).setMaxStackSize(stackSize);
			if(full3D)
				i.setFull3D();
			Item.itemRegistry.addObject(getIdForItem(name), "essentialcraft:"+name, i);
			ItemRegistry.registerItem(i, EssentialCraftCore.class);
			return i;
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static Item testingItem;
	public static Item mruMover1;
	public static Item fancyHat;
	public static Item drops;
	public static Item elemental_pick;
	public static Item elemental_axe;
	public static Item elemental_hoe;
	public static Item elemental_shovel;
	public static Item elemental_sword;
	public static Item bound_gem;
	public static Item magicMonocle;
	public static Item record_everlastingSummer;
	public static Item record_papersPlease;
	public static Item soulStone;
	public static Item matrixProj;
	public static Item spell;
	public static Item bell;
	public static Item staff;
	public static Item titanite;
	public static Item twinkling_titanite;
	public static Item secret;
	public static Item magicalSlag;
	public static Item genericItem;
	public static Item magicalAlloy;
	public static Item record_secret;
	public static Item essence;
	public static Item storage;
	
	public static Item magicalDigger;
	public static Item spawnerCollector;
	public static Item staffOfLife;
	public static Item biomeWand;
	
	public static Item emeraldHeart;
	public static Item magicalShield;
	public static Item spikyShield;
	public static Item magicWaterBottle;
	public static Item magicalPorkchop;
	public static Item charm;
	public static Item magicalWings;
	public static Item magmaticStaff;
	public static Item magicalLantern;
	public static Item magnetizingStaff;
	
	public static Item chaosFork;
	public static Item frozenMace;
	public static Item holyMace;
	
	public static Item elementalFuel;
	
	public static Item fFocus;
	public static Item wFocus;
	public static Item eFocus;
	public static Item aFocus;
	
	public static Item fruit;
	
	public static Item bottledWind;
	public static Item windKeeper;
	public static Item imprisonedWind;
	
	public static Item windTablet;
	
	public static Item elementalSword;
	public static Item ember;
	
	public static Item[] magicArmorItems = new Item[16];
	
	public static Item bauble;
	
	public static ArmorMaterial magicArmorMaterial = EnumHelper.addArmorMaterial("MRUFortified", 33, new int[]{3, 8, 6, 3}, 25);
	public static ArmorMaterial voidArmorMaterial = EnumHelper.addArmorMaterial("VoidFortified", 52, new int[]{3, 8, 6, 3}, 40);
	public static ArmorMaterial inquisArmorMaterial = EnumHelper.addArmorMaterial("Inquisitorium", 64, new int[]{10, 10, 10, 10}, 127);
	
	public static ToolMaterial elemental;
}
