package ec3.common.item;

import ec3.api.ApiCore;
import ec3.api.MagicianTableUpgrades;
import ec3.common.mod.EssentialCraftCore;
import DummyCore.Items.ItemRegistry;
import DummyCore.Utils.MiscUtils;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;
import static ec3.utils.cfg.Config.getIdForItem;

public class ItemsCore {

	public static ItemsCore instance;

	@SuppressWarnings("deprecation")
	public void loadItems() {
		//LogManager.getLogger().error("The reason you are about to see TONS of errors in the console is purely because I do not want you to loose your worlds upon updating to 3.4 version. Thanks!");
		elemental = EnumHelper.addToolMaterial("elemental", 6, 3568, 15.0F, 5.0F, 36);
		weakElemental = EnumHelper.addToolMaterial("weakElemental", 3, 754, 7.0F, 2.5F, 36);
		windElemental = EnumHelper.addToolMaterial("windElemental", 11, 15684, 15.0F, 6F, 42);
		shade = EnumHelper.addToolMaterial("shade", 32, 0, 1.0F, 17.0F, 12);
		//System.out.println("registry");
		testingItem = new TestItem_EC().setUnlocalizedName("essentialcraft:testItem").setTextureName("minecraft:blaze_rod").setMaxStackSize(1);
		ItemRegistry.registerItem(testingItem,"essentialcraft.testItem", EssentialCraftCore.class);
		mruMover1 = new ItemMRUMover().setUnlocalizedName("essentialcraft:mruMover1").setTextureName("essentialcraft:tools/mru_mover_t1").setMaxStackSize(1).setFull3D();
		ItemRegistry.registerItem(mruMover1,"essentialcraft.mruMover1", EssentialCraftCore.class);
		drops = new ItemDrop().setUnlocalizedName("essentialcraft:drops_").setTextureName("minecraft:blaze_rod").setMaxStackSize(64);
		Item.itemRegistry.addObject(getIdForItem("drops"), "essentialcraft:drops", drops);
		ItemRegistry.registerItem(drops, EssentialCraftCore.class);
		elemental_pick = new ItemPickaxe_Mod(elemental).setUnlocalizedName("essentialcraft:elemental_pick").setTextureName("essentialcraft:tools/elemental_pickaxe").setMaxStackSize(1).setFull3D();
		Item.itemRegistry.addObject(getIdForItem("elemental_pick"), "essentialcraft:elemental_pick", elemental_pick);
		ItemRegistry.registerItem(elemental_pick, EssentialCraftCore.class);
		elemental_axe = new ItemAxe_Mod(elemental).setUnlocalizedName("essentialcraft:elemental_axe").setTextureName("essentialcraft:tools/elemental_axe").setMaxStackSize(1).setFull3D();
		Item.itemRegistry.addObject(getIdForItem("elemental_axe"), "essentialcraft:elemental_axe", elemental_axe);
		ItemRegistry.registerItem(elemental_axe, EssentialCraftCore.class);
		elemental_hoe = new ItemHoe_Mod(elemental).setUnlocalizedName("essentialcraft:elemental_hoe").setTextureName("essentialcraft:tools/elemental_hoe").setMaxStackSize(1).setFull3D();
		Item.itemRegistry.addObject(getIdForItem("elemental_axe"), "essentialcraft:elemental_hoe", elemental_hoe);
		ItemRegistry.registerItem(elemental_hoe, EssentialCraftCore.class);
		elemental_shovel = new ItemShovel_Mod(elemental).setUnlocalizedName("essentialcraft:elemental_shovel").setTextureName("essentialcraft:tools/elemental_shovel").setMaxStackSize(1).setFull3D();
		Item.itemRegistry.addObject(getIdForItem("elemental_shovel"), "essentialcraft:elemental_shovel", elemental_shovel);
		ItemRegistry.registerItem(elemental_shovel, EssentialCraftCore.class);
		elemental_sword = new ItemSword_Mod(elemental).setUnlocalizedName("essentialcraft:elemental_sword").setTextureName("essentialcraft:tools/elemental_sword").setMaxStackSize(1).setFull3D();
		Item.itemRegistry.addObject(getIdForItem("elemental_sword"), "essentialcraft:elemental_sword", elemental_sword);
		ItemRegistry.registerItem(elemental_sword, EssentialCraftCore.class);
		bound_gem = new ItemBoundGem().setUnlocalizedName("essentialcraft:bound_gem").setTextureName("essentialcraft:gem_bound").setFull3D();
		Item.itemRegistry.addObject(getIdForItem("bound_gem"), "essentialcraft:bound_gem", bound_gem);
		ItemRegistry.registerItem(bound_gem, EssentialCraftCore.class);
		magicMonocle=registerItemSimple(magicMonocle,ItemMonocle.class,"magicMonocle","tools/magicMonocle",16,true,1);
		
		record_everlastingSummer = new ItemRecord_Mod("letsbefriends").setUnlocalizedName("essentialcraft:record").setTextureName("record_blocks");
		Item.itemRegistry.addObject(getIdForItem("record"), "essentialcraft:record", record_everlastingSummer);
		ItemRegistry.registerItem(record_everlastingSummer, EssentialCraftCore.class);
		
		record_papersPlease = new ItemRecord_Mod("arstotzkan").setUnlocalizedName("essentialcraft:record_a").setTextureName("record_far");
		Item.itemRegistry.addObject(getIdForItem("record_a"), "essentialcraft:record_a", record_papersPlease);
		ItemRegistry.registerItem(record_papersPlease, EssentialCraftCore.class);
		
		record_secret = new ItemRecord_Mod("secret").setUnlocalizedName("essentialcraft:record_secret").setTextureName("record_13");
		Item.itemRegistry.addObject(getIdForItem("record_secret"), "essentialcraft:record_secret", record_secret);
		ItemRegistry.registerItem(record_secret, EssentialCraftCore.class);
		
		soulStone = registerItemSimple(soulStone,ItemSoulStone.class,"soulStone","matrix/soulStone",0,false,1);
		matrixProj = registerItemSimple(matrixProj,ItemMRUMatrixProjection.class,"mruMatrixProjection","mruMatrix_empty",0,false,1);
		titanite = registerItemSimple(titanite,Item.class,"titanite","misc/titanite",0,false,64);
		twinkling_titanite = registerItemSimple(twinkling_titanite,Item.class,"twinkling_titanite","misc/twinkling_titanite",0,false,64);
		secret = registerItemSimple(secret,ItemSecret.class,"secret","soulStone",0,false,1);
		magicalSlag = registerItemSimple(magicalSlag,Item.class,"magicalSlag","misc/magicalSlag",0,false,64);
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
		
		fFocus = registerItemSimple(fFocus,ItemElementalFocus.class,"fFocus","elemental/eFocusFire",3000,false,1);
		wFocus = registerItemSimple(wFocus,ItemElementalFocus.class,"wFocus","elemental/eFocusWater",3000,false,1);
		eFocus = registerItemSimple(eFocus,ItemElementalFocus.class,"eFocus","elemental/eFocusEarth",3000,false,1);
		aFocus = registerItemSimple(aFocus,ItemElementalFocus.class,"aFocus","elemental/eFocusAir",3000,false,1);
		
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
		
		elementalSword = registerItemSimple(elementalSword,ItemElementalSword.class,"elementalSword","tools/elementalSword",0,false,1);
		ember = registerItemSimple(ember,ItemEmber.class,"ember","windTablet",0,false,16);
		
		
		
		magicArmorItems[0] = new ItemArmorMod(magicArmorMaterial, 4, 0, 0).setArmorTexture("armorEC3Magic").setUnlocalizedName("essentialcraft:helm_magic").setMaxDamage(512);
		Item.itemRegistry.addObject(getIdForItem("helm_magic"), "essentialcraft:helm_magic", magicArmorItems[0]);
		ItemRegistry.registerItem(magicArmorItems[0], EssentialCraftCore.class);
		ApiCore.allowItemToSeeMRU(magicArmorItems[0]);
		ApiCore.setArmorProperties(magicArmorItems[0], 0.25F, 0.125F, 0.10F);
		
		magicArmorItems[1] = new ItemArmorMod(magicArmorMaterial, 4, 1, 0).setArmorTexture("armorEC3Magic").setUnlocalizedName("essentialcraft:chest_magic").setMaxDamage(1536);
		Item.itemRegistry.addObject(getIdForItem("chest_magic"), "essentialcraft:chest_magic", magicArmorItems[1]);
		ItemRegistry.registerItem(magicArmorItems[1], EssentialCraftCore.class);
		ApiCore.setArmorProperties(magicArmorItems[1], 0.25F, 0.125F, 0.10F);
		
		magicArmorItems[2] = new ItemArmorMod(magicArmorMaterial, 4, 2, 0).setArmorTexture("armorEC3Magic").setUnlocalizedName("essentialcraft:legs_magic").setMaxDamage(1024);
		Item.itemRegistry.addObject(getIdForItem("legs_magic"), "essentialcraft:legs_magic", magicArmorItems[2]);
		ItemRegistry.registerItem(magicArmorItems[2], EssentialCraftCore.class);
		ApiCore.setArmorProperties(magicArmorItems[2], 0.25F, 0.125F, 0.10F);
		
		magicArmorItems[3] = new ItemArmorMod(magicArmorMaterial, 4, 3, 0).setArmorTexture("armorEC3Magic").setUnlocalizedName("essentialcraft:boots_magic").setMaxDamage(512);
		Item.itemRegistry.addObject(getIdForItem("boots_magic"), "essentialcraft:boots_magic", magicArmorItems[3]);
		ItemRegistry.registerItem(magicArmorItems[3], EssentialCraftCore.class);
		ApiCore.setArmorProperties(magicArmorItems[3], 0.25F, 0.125F, 0.10F);
		
		
		magicArmorItems[4] = new ItemArmorMod(voidArmorMaterial, 4, 0, 1).setArmorTexture("armorEC3Void").setUnlocalizedName("essentialcraft:helm_void").setMaxDamage(512*4);
		Item.itemRegistry.addObject(getIdForItem("helm_void"), "essentialcraft:helm_void", magicArmorItems[4]);
		ItemRegistry.registerItem(magicArmorItems[4], EssentialCraftCore.class);
		ApiCore.allowItemToSeeMRU(magicArmorItems[4]);
		ApiCore.setArmorProperties(magicArmorItems[4], 1F, 0.5F, 0.75F);
		MiscUtils.registerDescriptionFor(magicArmorItems[4].getUnlocalizedName(), "Converts life energy of fallen foes to your hunger", EnumChatFormatting.ITALIC);
		
		magicArmorItems[5] = new ItemArmorMod(voidArmorMaterial, 4, 1, 1).setArmorTexture("armorEC3Void").setUnlocalizedName("essentialcraft:chest_void").setMaxDamage(1536*4);
		Item.itemRegistry.addObject(getIdForItem("chest_void"), "essentialcraft:chest_void", magicArmorItems[5]);
		ItemRegistry.registerItem(magicArmorItems[5], EssentialCraftCore.class);
		ApiCore.setArmorProperties(magicArmorItems[5], 1F, 0.5F, 0.75F);
		MiscUtils.registerDescriptionFor(magicArmorItems[5].getUnlocalizedName(), "Can dodge 20% of attacks", EnumChatFormatting.ITALIC);
		MiscUtils.registerItemModifier(magicArmorItems[5], -1, "armor", "02001", 20D, SharedMonsterAttributes.maxHealth, 0);
		
		magicArmorItems[6] = new ItemArmorMod(voidArmorMaterial, 4, 2, 1).setArmorTexture("armorEC3Void").setUnlocalizedName("essentialcraft:legs_void").setMaxDamage(1024*4);
		Item.itemRegistry.addObject(getIdForItem("legs_void"), "essentialcraft:legs_void", magicArmorItems[6]);
		ItemRegistry.registerItem(magicArmorItems[6], EssentialCraftCore.class);
		ApiCore.setArmorProperties(magicArmorItems[6], 1F, 0.5F, 0.75F);
		MiscUtils.registerDescriptionFor(magicArmorItems[6].getUnlocalizedName(), "Can phase-shift through monsters", EnumChatFormatting.ITALIC);
		
		magicArmorItems[7] = new ItemArmorMod(voidArmorMaterial, 4, 3, 1).setArmorTexture("armorEC3Void").setUnlocalizedName("essentialcraft:boots_void").setMaxDamage(512*4);
		Item.itemRegistry.addObject(getIdForItem("boots_void"), "essentialcraft:boots_void", magicArmorItems[7]);
		ItemRegistry.registerItem(magicArmorItems[7], EssentialCraftCore.class);
		ApiCore.setArmorProperties(magicArmorItems[7], 1F, 0.5F, 0.75F);
		MiscUtils.registerDescriptionFor(magicArmorItems[7].getUnlocalizedName(), "Negates 90% of falldamage", EnumChatFormatting.ITALIC);
		MiscUtils.registerItemModifier(magicArmorItems[7], -1, "armor", "02000", 0.05D, SharedMonsterAttributes.movementSpeed, 0);
	
		
		
		magicArmorItems[8] = new ItemArmorMod(inquisArmorMaterial, 4, 0, 2).setArmorTexture("armorEC3Inquisitorium").setUnlocalizedName("essentialcraft:helm_inq").setMaxDamage(512*10);
		Item.itemRegistry.addObject(getIdForItem("helm_inq"), "essentialcraft:helm_inq", magicArmorItems[8]);
		ItemRegistry.registerItem(magicArmorItems[8], EssentialCraftCore.class);
		ApiCore.allowItemToSeeMRU(magicArmorItems[8]);
		ApiCore.setArmorProperties(magicArmorItems[8], 1F, 1F, 1F);
		MiscUtils.registerDescriptionFor(magicArmorItems[8].getUnlocalizedName(), "Linses created from the moonlight metal to see unpurity", EnumChatFormatting.ITALIC);
		
		magicArmorItems[9] = new ItemArmorMod(inquisArmorMaterial, 4, 1, 2).setArmorTexture("armorEC3Inquisitorium").setUnlocalizedName("essentialcraft:chest_inq").setMaxDamage(1536*10);
		Item.itemRegistry.addObject(getIdForItem("chest_inq"), "essentialcraft:chest_inq", magicArmorItems[9]);
		ItemRegistry.registerItem(magicArmorItems[9], EssentialCraftCore.class);
		ApiCore.setArmorProperties(magicArmorItems[9], 1F, 1F, 1F);
		MiscUtils.registerDescriptionFor(magicArmorItems[9].getUnlocalizedName(), "Be quiet, Dominique!", EnumChatFormatting.ITALIC);
		MiscUtils.registerItemModifier(magicArmorItems[9], -1, "armor", "02001", 30D, SharedMonsterAttributes.maxHealth, 0);
		
		magicArmorItems[10] = new ItemArmorMod(inquisArmorMaterial, 4, 2, 2).setArmorTexture("armorEC3Inquisitorium").setUnlocalizedName("essentialcraft:legs_inq").setMaxDamage(1024*10);
		Item.itemRegistry.addObject(getIdForItem("legs_inq"), "essentialcraft:legs_inq", magicArmorItems[10]);
		ItemRegistry.registerItem(magicArmorItems[10], EssentialCraftCore.class);
		ApiCore.setArmorProperties(magicArmorItems[10], 1F, 1F, 1F);
		MiscUtils.registerDescriptionFor(magicArmorItems[10].getUnlocalizedName(), "Magic is a parasite. It must be wiped out!", EnumChatFormatting.ITALIC);
		
		magicArmorItems[11] = new ItemArmorMod(inquisArmorMaterial, 4, 3, 2).setArmorTexture("armorEC3Inquisitorium").setUnlocalizedName("essentialcraft:boots_inq").setMaxDamage(512*10);
		Item.itemRegistry.addObject(getIdForItem("boots_inq"), "essentialcraft:boots_inq", magicArmorItems[11]);
		ItemRegistry.registerItem(magicArmorItems[11], EssentialCraftCore.class);
		ApiCore.setArmorProperties(magicArmorItems[11], 1F, 1F, 1F);
		MiscUtils.registerDescriptionFor(magicArmorItems[11].getUnlocalizedName(), "Magic shall not prevail!", EnumChatFormatting.ITALIC);
		MiscUtils.registerItemModifier(magicArmorItems[11], -1, "armor", "02000", 0.1D, SharedMonsterAttributes.movementSpeed, 0);
		
		
		
		magicArmorItems[12] = new ItemArmorMod(ArmorMaterial.CLOTH, 4, 0, 3).setArmorTexture("armorEC3Wind").setUnlocalizedName("essentialcraft:helm_wind").setMaxDamage(128);
		Item.itemRegistry.addObject(getIdForItem("helm_wind"), "essentialcraft:helm_wind", magicArmorItems[12]);
		ItemRegistry.registerItem(magicArmorItems[12], EssentialCraftCore.class);
		ApiCore.allowItemToSeeMRU(magicArmorItems[12]);
		
		magicArmorItems[13] = new ItemArmorMod(ArmorMaterial.CLOTH, 4, 1, 3).setArmorTexture("armorEC3Wind").setUnlocalizedName("essentialcraft:chest_wind").setMaxDamage(256+128);
		Item.itemRegistry.addObject(getIdForItem("chest_wind"), "essentialcraft:chest_wind", magicArmorItems[13]);
		ItemRegistry.registerItem(magicArmorItems[13], EssentialCraftCore.class);
		
		magicArmorItems[14] = new ItemArmorMod(ArmorMaterial.CLOTH, 4, 2, 3).setArmorTexture("armorEC3Wind").setUnlocalizedName("essentialcraft:legs_wind").setMaxDamage(256);
		Item.itemRegistry.addObject(getIdForItem("legs_wind"), "essentialcraft:legs_wind", magicArmorItems[14]);
		ItemRegistry.registerItem(magicArmorItems[14], EssentialCraftCore.class);
		
		magicArmorItems[15] = new ItemArmorMod(ArmorMaterial.CLOTH, 4, 3, 3).setArmorTexture("armorEC3Wind").setUnlocalizedName("essentialcraft:boots_wind").setMaxDamage(128);
		Item.itemRegistry.addObject(getIdForItem("boots_wind"), "essentialcraft:boots_wind", magicArmorItems[15]);
		ItemRegistry.registerItem(magicArmorItems[15], EssentialCraftCore.class);
		
		bauble = registerItemSimple(bauble,ItemBaublesWearable.class,"bauble","windTablet",0,true,1);
		
		magmaticStaff = registerItemSimple(magmaticStaff,ItemMagmaticWand.class,"magmaticStaff","tools/magmaticWand",0,true,1);
		magicalLantern = registerItemSimple(magicalLantern,ItemMagicLantern.class,"magicalLantern","tools/elementalPurifier",0,true,1);
		magnetizingStaff = registerItemSimple(magnetizingStaff,ItemMagnetizingStaff.class,"magnetizingStaff","tools/magnetizingStaff",0,true,1);
		
		research_book = registerItemSimple(research_book,ItemKnowledgeBook.class,"research_book","book_knowledge",0,false,1);
	
		air_potion = registerItemSimple(air_potion,ItemLiquidAir.class,"air_potion","fluidAir",0,false,64);
		mruMover_t2 = registerItemSimple(mruMover_t2,ItemMRUMover.class,"mruMover_t2","tools/mru_mover_t2",256*6,true,1);
		playerList = registerItemSimple(playerList,ItemPlayerList.class,"playerList","minecraft:paper",0,false,1);
		magicalChisel = registerItemSimple(magicalChisel,ItemMagicalChisel.class,"magicalChisel","tools/magicalChisel",256,true,1);
		filter = registerItemSimple(filter,ItemFilter.class,"itemFilter","itemFilter",0,false,1);
		controlRod = registerItemSimple(controlRod,ItemControlRod.class,"controlRod","tools/controlRod",0,true,1);
		baublesCore = registerItemSimple(baublesCore,BaublesModifier.class,"baublesCore","???",0,false,1);
		ApiCore.allowsSeeingMRU.add(mruMover_t2);
		
		shadeSword = registerItemSimple(shadeSword,ItemShadeSword.class,"shadeSword","tools/swordOfTheSHADE",0,true,1);
		shadeSlasher = registerItemSimple(shadeSlasher,ItemShadeSlasher.class,"shadeSlasher","tools/slasherOfTheSHADE",0,true,1);
		shadeKnife = registerItemSimple(shadeKnife,ItemShadowKnife.class,"shadeKnife","tools/shadowKnife",0,true,32);
		entityEgg = registerItemSimple(entityEgg,ItemSpawnEGGEC3.class,"entityEgg","fruit",0,false,64);
		
		pistol = new ItemGun("pistol").setUnlocalizedName("ec3.gun.pistol");
		ItemRegistry.registerItem(pistol, "gun.pistol", EssentialCraftCore.class);
		rifle = new ItemGun("rifle").setUnlocalizedName("ec3.gun.rifle");
		ItemRegistry.registerItem(rifle, "gun.rifle", EssentialCraftCore.class);
		sniper = new ItemGun("sniper").setUnlocalizedName("ec3.gun.sniper");
		ItemRegistry.registerItem(sniper, "gun.sniper", EssentialCraftCore.class);
		gatling = new ItemGun("gatling").setUnlocalizedName("ec3.gun.gatling");
		ItemRegistry.registerItem(gatling, "gun.gatling", EssentialCraftCore.class);
		
		weak_elemental_pick = new ItemPickaxe_Mod(weakElemental).setUnlocalizedName("essentialcraft.weak_elemental_pick").setTextureName("essentialcraft:tools/weak_pickaxe").setMaxStackSize(1).setFull3D();
		ItemRegistry.registerItem(weak_elemental_pick, "weak_elemental_pick", EssentialCraftCore.class);
		weak_elemental_axe = new ItemAxe_Mod(weakElemental).setUnlocalizedName("essentialcraft.weak_elemental_axe").setTextureName("essentialcraft:tools/weak_axe").setMaxStackSize(1).setFull3D();
		ItemRegistry.registerItem(weak_elemental_axe,"weak_elemental_axe", EssentialCraftCore.class);
		weak_elemental_hoe = new ItemHoe_Mod(weakElemental).setUnlocalizedName("essentialcraft.weak_elemental_hoe").setTextureName("essentialcraft:tools/weak_hoe").setMaxStackSize(1).setFull3D();
		ItemRegistry.registerItem(weak_elemental_hoe,"weak_elemental_hoe", EssentialCraftCore.class);
		weak_elemental_shovel = new ItemShovel_Mod(weakElemental).setUnlocalizedName("essentialcraft.weak_elemental_shovel").setTextureName("essentialcraft:tools/weak_shovel").setMaxStackSize(1).setFull3D();
		ItemRegistry.registerItem(weak_elemental_shovel,"weak_elemental_shovel", EssentialCraftCore.class);
		weak_elemental_sword = new ItemSword_Mod(weakElemental).setUnlocalizedName("essentialcraft.weak_elemental_sword").setTextureName("essentialcraft:tools/weak_sword").setMaxStackSize(1).setFull3D();
		ItemRegistry.registerItem(weak_elemental_sword,"weak_elemental_sword", EssentialCraftCore.class);
		
		wind_elemental_pick = new ItemWindPickaxe(windElemental).setUnlocalizedName("essentialcraft.wind_elemental_pick").setTextureName("essentialcraft:tools/wind_pickaxe").setMaxStackSize(1).setFull3D();
		ItemRegistry.registerItem(wind_elemental_pick,"wind_elemental_pick", EssentialCraftCore.class);
		wind_elemental_shovel = new ItemWindShovel(windElemental).setUnlocalizedName("essentialcraft.wind_elemental_shovel").setTextureName("essentialcraft:tools/wind_shovel").setMaxStackSize(1).setFull3D();
		ItemRegistry.registerItem(wind_elemental_shovel,"wind_elemental_shovel", EssentialCraftCore.class);
		wind_elemental_axe = new ItemWindAxe(windElemental).setUnlocalizedName("essentialcraft.wind_elemental_axe").setTextureName("essentialcraft:tools/wind_axe").setMaxStackSize(1).setFull3D();
		ItemRegistry.registerItem(wind_elemental_axe,"wind_elemental_axe", EssentialCraftCore.class);
		wind_elemental_hoe = new ItemWindHoe(windElemental).setUnlocalizedName("essentialcraft.wind_elemental_hoe").setTextureName("essentialcraft:tools/wind_hoe").setMaxStackSize(1).setFull3D();
		ItemRegistry.registerItem(wind_elemental_hoe,"wind_elemental_hoe", EssentialCraftCore.class);
		wind_elemental_sword = new ItemWindSword(windElemental).setUnlocalizedName("essentialcraft.wind_elemental_sword").setTextureName("essentialcraft:tools/wind_sword").setMaxStackSize(1).setFull3D();
		ItemRegistry.registerItem(wind_elemental_sword,"wind_elemental_sword", EssentialCraftCore.class);
		
		wind_helmet = new ItemGenericArmor(windArmorMaterial, 0, 0).setUnlocalizedName("essentialcraft.wind_helmet").setTextureName("essentialcraft:armor/wind_helmet").setMaxDamage(312*2);
		ItemRegistry.registerItem(wind_helmet,"wind_helmet", EssentialCraftCore.class);
		ApiCore.allowItemToSeeMRU(wind_helmet);
		ApiCore.setArmorProperties(wind_helmet, 0.5F, 0.75F, 0.5F);
		
		wind_chestplate = new ItemGenericArmor(windArmorMaterial, 0, 1).setUnlocalizedName("essentialcraft.wind_chestplate").setTextureName("essentialcraft:armor/wind_chestplate").setMaxDamage(312*4);
		ItemRegistry.registerItem(wind_chestplate,"wind_chestplate", EssentialCraftCore.class);
		ApiCore.setArmorProperties(wind_chestplate, 0.5F, 0.75F, 0.5F);
		MiscUtils.registerItemModifier(wind_chestplate, -1, "armor", "02000", 0.075D, SharedMonsterAttributes.movementSpeed, 0);

		wind_leggings = new ItemGenericArmor(windArmorMaterial, 0, 2).setUnlocalizedName("essentialcraft.wind_leggings").setTextureName("essentialcraft:armor/wind_leggings").setMaxDamage(312*3);
		ItemRegistry.registerItem(wind_leggings,"wind_leggings", EssentialCraftCore.class);
		ApiCore.setArmorProperties(wind_leggings, 0.5F, 0.75F, 0.5F);
		
		wind_boots = new ItemGenericArmor(windArmorMaterial, 0, 3).setUnlocalizedName("essentialcraft.wind_boots").setTextureName("essentialcraft:armor/wind_boots").setMaxDamage(312*2);
		ItemRegistry.registerItem(wind_boots,"wind_boots", EssentialCraftCore.class);
		ApiCore.setArmorProperties(wind_boots, 0.5F, 0.75F, 0.5F);
		
		soul = registerItemSimple(soul,ItemCapturedSoul.class,"soul","creatureSoul",0,false,64);
		soulScriber = registerItemSimple(soulScriber,ItemSoulScriber.class,"soulScriber","tools/soulScriber",32,true,1);

		magicalBuilder = registerItemSimple(magicalBuilder,ItemMagicalBuilder.class,"magicalBuilder","spells/staff_void",0,true,1);
		
		MagicianTableUpgrades.addUpgrade(new ItemStack(genericItem,1,1), 1.2F, loc("elementalCore"));
		MagicianTableUpgrades.addUpgrade(new ItemStack(genericItem,1,0), 5F, loc("combinedMagicalAlloys"));
		MagicianTableUpgrades.addUpgrade(new ItemStack(genericItem,1,8), 2.5F, loc("enderAlloy"));
		MagicianTableUpgrades.addUpgrade(new ItemStack(genericItem,1,23), 3F, loc("eyeOfAbsorbtion"));
		MagicianTableUpgrades.addUpgrade(new ItemStack(genericItem,1,35), 6F, loc("voidPlating"));
		MagicianTableUpgrades.addUpgrade(new ItemStack(genericItem,1,36), 7.5F, loc("voidCore"));
		MagicianTableUpgrades.addUpgrade(new ItemStack(genericItem,1,37), 10F, loc("voidReactor"));
		MagicianTableUpgrades.addUpgrade(new ItemStack(genericItem,1,44),4F, loc("crystal"));
		MagicianTableUpgrades.addUpgrade(new ItemStack(genericItem,1,53),16F, loc("demonicCore"));
	}
	
	public static ResourceLocation loc(String name)
	{
		return new ResourceLocation("essentialcraft","textures/special/models/"+name+".png");
	}

	@SuppressWarnings("deprecation")
	public static Item registerItemSimple(Item i, Class<?extends Item> itemClass, String name, String textureName, int damage, boolean full3D, int stackSize)
	{
		try
		{
			if(textureName.indexOf(":") != -1)
				i = itemClass.newInstance().setUnlocalizedName("essentialcraft:"+name).setTextureName(textureName).setMaxDamage(damage).setMaxStackSize(stackSize);
			else
				i = itemClass.newInstance().setUnlocalizedName("essentialcraft:"+name).setTextureName("essentialcraft:"+textureName).setMaxDamage(damage).setMaxStackSize(stackSize);
			if(full3D)
				i.setFull3D();
			Item.itemRegistry.addObject(getIdForItem(name), "essentialcraft:"+name, i);
			ItemRegistry.registerItem(i, EssentialCraftCore.class);
			return i;
		}
		catch (Exception e) {
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
	
	public static Item research_book;
	
	public static Item air_potion;
	
	public static Item mruMover_t2;
	
	public static Item playerList;
	
	public static Item magicalChisel;
	
	public static Item filter;
	public static Item controlRod;
	public static Item baublesCore;
	
	public static Item shadeSword;
	public static Item shadeSlasher;
	public static Item shadeKnife;
	public static Item entityEgg;
	
	public static Item pistol;
	public static Item rifle;
	public static Item sniper;
	public static Item gatling;
	
	public static Item weak_elemental_pick;
	public static Item weak_elemental_axe;
	public static Item weak_elemental_hoe;
	public static Item weak_elemental_shovel;
	public static Item weak_elemental_sword;
	
	public static Item wind_elemental_pick;
	public static Item wind_elemental_axe;
	public static Item wind_elemental_hoe;
	public static Item wind_elemental_shovel;
	public static Item wind_elemental_sword;
	
	public static Item wind_helmet;
	public static Item wind_chestplate;
	public static Item wind_leggings;
	public static Item wind_boots;
	
	public static Item soul;
	public static Item soulScriber;
	
	public static Item magicalBuilder;
	
	public static ArmorMaterial magicArmorMaterial = EnumHelper.addArmorMaterial("MRUFortified", 33, new int[]{3, 8, 6, 3}, 25);
	public static ArmorMaterial voidArmorMaterial = EnumHelper.addArmorMaterial("VoidFortified", 52, new int[]{3, 8, 6, 3}, 40);
	public static ArmorMaterial inquisArmorMaterial = EnumHelper.addArmorMaterial("Inquisitorium", 64, new int[]{10, 10, 10, 10}, 127);
	public static ArmorMaterial windArmorMaterial = EnumHelper.addArmorMaterial("WindElemental", 42, new int[]{4, 10, 7, 4}, 40);
	
	
	public static ToolMaterial elemental;
	public static ToolMaterial weakElemental;
	public static ToolMaterial windElemental;
	public static ToolMaterial shade;
}
