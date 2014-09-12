package ec3.common.registry;

import ec3.common.world.BiomeGenCorruption_Chaos;
import ec3.common.world.BiomeGenCorruption_Frozen;
import ec3.common.world.BiomeGenCorruption_Magic;
import ec3.common.world.BiomeGenCorruption_Shadow;
import ec3.utils.cfg.Config;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeRegistry {
	public static BiomeRegistry core;
	
	public void register()
	{
		chaosCorruption = new BiomeGenCorruption_Chaos(Config.biomeID[0]);
		frozenCorruption = new BiomeGenCorruption_Frozen(Config.biomeID[1]);
		shadowCorruption = new BiomeGenCorruption_Shadow(Config.biomeID[2]);
		magicCorruption = new BiomeGenCorruption_Magic(Config.biomeID[3]);
	}
	public static BiomeGenCorruption_Chaos chaosCorruption;
	public static BiomeGenCorruption_Frozen frozenCorruption;
	public static BiomeGenCorruption_Shadow shadowCorruption;
	public static BiomeGenCorruption_Magic magicCorruption;
}
