package ec3.common.registry;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import ec3.api.EnumSpellType;
import ec3.common.item.ItemsCore;
import ec3.common.spell.SpellMRUArrow;
import ec3.common.spell.SpellMRURelease;
import ec3.common.spell.SpellPersonalityShatter;
import ec3.common.spell.SpellTransform;
import ec3.common.spell.SpellWorldShatter;

public class SpellRegistry {
	
	public static void register()
	{
		new SpellTransform().setTransformed(new ItemStack(ItemsCore.soulStone,1,0)).setTransforming(new ItemStack(Blocks.stone,1,0)).setAttuneReq(-1).setDesc("Transforms the regular stone into Soul Stone").setName("Transform - Soul Stone").setIconName("spells/tr_soulStone").setTierReq(0).setType(EnumSpellType.CONSUMING).setubmruReq(1000).register();
		new SpellTransform().setTransformed(new ItemStack(ItemsCore.titanite,1,0)).setTransforming(new ItemStack(Items.emerald,1,0)).setAttuneReq(-1).setDesc("Transforms the regular emerald into Titanite").setName("Transform - Titanite").setIconName("spells/tr_soulStone").setTierReq(0).setType(EnumSpellType.CONSUMING).setubmruReq(5000).register();
		new SpellTransform().setTransformed(new ItemStack(ItemsCore.twinkling_titanite,1,0)).setTransforming(new ItemStack(Items.diamond,1,0)).setAttuneReq(-1).setDesc("Transforms the regular diamond into Twinkling Titanite").setName("Transform - Twinkling Titanite").setIconName("spells/tr_soulStone").setTierReq(0).setType(EnumSpellType.CONSUMING).setubmruReq(10000).register();
		new SpellTransform().setTransformed(new ItemStack(ItemsCore.secret,1,4)).setTransforming(new ItemStack(Items.nether_star,1,0)).setAttuneReq(-1).setDesc("Transforms the regular nether star into something...").setName("Transform - Unknown").setIconName("spells/tr_soulStone").setTierReq(2).setType(EnumSpellType.CONSUMING).setubmruReq(500000).register();
		new SpellMRUArrow().setDamage(0).setAttuneReq(-1).setDesc("Deals little damage").setName("Pale MRU Arrow").setIconName("spells/mruArrow").setTierReq(0).setType(EnumSpellType.CONSUMING).setubmruReq(1000).register();
		new SpellMRUArrow().setDamage(2).setAttuneReq(-1).setDesc("Deals normal damage").setName("MRU Arrow").setIconName("spells/mruArrow").setTierReq(1).setType(EnumSpellType.CONSUMING).setubmruReq(5000).register();
		new SpellMRUArrow().setDamage(4).setAttuneReq(3).setDesc("Deals lots of damage").setName("Soundfull MRU Arrow").setIconName("spells/mruArrow").setTierReq(2).setType(EnumSpellType.CONSUMING).setubmruReq(10000).register();
		new SpellMRURelease().setDamage(16).setAttuneReq(-1).setDesc("Deals damage in AOE").setName("MRU Release").setIconName("spells/mruRelease").setTierReq(1).setType(EnumSpellType.CONSUMING).setubmruReq(20000).register();
		new SpellMRURelease().setDamage(48).setAttuneReq(3).setDesc("Deals huge damage in AOE").setName("Huge MRU Release").setIconName("spells/mruRelease").setTierReq(2).setType(EnumSpellType.CONSUMING).setubmruReq(100000).register();
		new SpellWorldShatter().setAttuneReq(3).setDesc("Kills all monsters").setName("World Shatter").setIconName("spells/worldShatter").setTierReq(2).setType(EnumSpellType.CONSUMING).setubmruReq(5000000).register();
		new SpellPersonalityShatter().setAttuneReq(3).setDesc("Kills all players").setName("Personality Shatter").setIconName("spells/personalityShatter").setTierReq(2).setType(EnumSpellType.CONSUMING).setubmruReq(2147483647).register();
	}

}
