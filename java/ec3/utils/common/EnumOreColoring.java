package ec3.utils.common;

public enum EnumOreColoring {
	
	COAL("oreCoal","gemCoal",0x343434),
	IRON("oreIron",0xe2c0aa),
	GOLD("oreGold",0xf8af2b),
	DIAMOND("oreDiamond","gemDiamond",0x5decf5),
	EMERALD("oreEmerald","gemEmerald",0x17dd62),
	QUARTZ("oreQuartz","gemQuartz",0xd1beb1),
	REDSTONE("oreRedstone","dustRedstone",0x8f0303,8),
	LAPIS("oreLapis","gemLapis",0x1c40a9,16),
	COPPER("oreCopper",0xbc4800),
	TIN("oreTin",0xc3e9ff),
	LEAD("oreLead",0x7c8cc7),
	SILVER("oreSilver",0xf0fdfe),
	COBALT("oreCobalt",0x002568),
	ARDITE("oreArdite",0xc9a537),
	NICKEL("oreFerrous",0xe5e4bd),
	ALUMINUM("oreAluminum",0xc5c5c5),
	URANIUM("oreUranium",0x41b200),
	IRIDIUM("oreIridium",0xebffff),
	ALCHEMITE("oreAlchemite","gemAlchemite",0xff0e27,5),
	FIRE("oreInfusedFire","shardFire",0xff0000,3),
	WATER("oreInfusedWater","shardWater",0x0000ff,3),
	EARTH("oreInfusedEarth","shardEarth",0x00ff00,3),
	AIR("oreInfusedAir","shardAir",0xffff00,3),
	ORDER("oreInfusedOrder","shardOrder",0xcccccc,3),
	ENTROPY("oreInfusedEntropy","shardEntropy",0x333333,3),
	;
	
	EnumOreColoring(String i, int j)
	{
		oreName = i;
		color = j;
		dropAmount = 1;
		outputName = "";
	}
	
	EnumOreColoring(String i, int j, int k)
	{
		oreName = i;
		color = j;
		dropAmount = k;
		outputName = "";
	}
	
	EnumOreColoring(String i,String s, int j, int k)
	{
		oreName = i;
		color = j;
		dropAmount = k;
		outputName = s;
	}
	
	EnumOreColoring(String i,String s, int j)
	{
		oreName = i;
		color = j;
		dropAmount = 1;
		outputName = s;
	}
	
	public int color, dropAmount;
	public String oreName;
	public String outputName;

}
