package ec3.common.item;

import ec3.common.mod.EssentialCraftCore;
import DummyCore.Items.ItemRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
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
		
		fFocus = registerItemSimple(fFocus,Item.class,"fFocus","eFocusFire",3000,false,1);
		wFocus = registerItemSimple(wFocus,Item.class,"wFocus","eFocusWater",3000,false,1);
		eFocus = registerItemSimple(eFocus,Item.class,"eFocus","eFocusEarth",3000,false,1);
		aFocus = registerItemSimple(aFocus,Item.class,"aFocus","eFocusAir",3000,false,1);
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
	
	public static Item chaosFork;
	public static Item frozenMace;
	public static Item holyMace;
	
	public static Item elementalFuel;
	
	public static Item fFocus;
	public static Item wFocus;
	public static Item eFocus;
	public static Item aFocus;
	
	public static ToolMaterial elemental;
}
