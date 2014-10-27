package ec3.common.registry;

import org.apache.logging.log4j.LogManager;

import ec3.common.world.BiomeGenCorruption_Chaos;
import ec3.common.world.BiomeGenCorruption_Frozen;
import ec3.common.world.BiomeGenCorruption_Magic;
import ec3.common.world.BiomeGenCorruption_Shadow;
import ec3.common.world.BiomeGenFirstWorld;
import ec3.common.world.BiomeGenFirstWorld_Desert;
import ec3.common.world.BiomeGenFirstWorld_Dreadlands;
import ec3.utils.cfg.Config;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase.Height;

public class BiomeRegistry {
	public static BiomeRegistry core;
	
	public void register()
	{
		chaosCorruption = new BiomeGenCorruption_Chaos(Config.biomeID[0]);
		frozenCorruption = new BiomeGenCorruption_Frozen(Config.biomeID[1]);
		shadowCorruption = new BiomeGenCorruption_Shadow(Config.biomeID[2]);
		magicCorruption = new BiomeGenCorruption_Magic(Config.biomeID[3]);
		registerFirstWorldBiomes();
	}
	
	public void registerFirstWorldBiomes()
	{
		firstWorldBiomeArray[0] = BiomeGenBase.ocean;
		firstWorldBiomeArray[1] = magicCorruption;
		firstWorldBiomeArray[2] = BiomeGenBase.beach;
		firstWorldBiomeArray[3] = BiomeGenBase.river;
		firstWorldBiomeArray[4] = new BiomeGenFirstWorld_Dreadlands(Config.biomeID[5]).setGrassColor(0x889688).setWaterColor(0x889688).setLeavesColor(0x889688).setBiomeName("dreadlands").setDisableRain().setTemperatureRainfall(2.0F, 0.0F).setHeight(new Height(0.125F, 0.126F));
		firstWorldBiomeArray[5] = BiomeGenBase.forest;
		firstWorldBiomeArray[6] = BiomeGenBase.extremeHills;
		firstWorldBiomeArray[7] = chaosCorruption;
		firstWorldBiomeArray[8] = new BiomeGenFirstWorld_Desert(Config.biomeID[4]).setBiomeName("desert").setColor(16421912).setDisableRain().setTemperatureRainfall(2.0F, 0.0F).setHeight(new Height(0.125F, 0.05F));
		firstWorldBiomeArray[9] = frozenCorruption;
		//firstWorldBiomeArray[3] = new BiomeGenFirstWorld(Config.biomeID[4]);
		//firstWorldBiomeArray[3] = new BiomeGenFirstWorld(Config.biomeID[4]);
		//firstWorldBiomeArray[3] = new BiomeGenFirstWorld(Config.biomeID[4]);
		//firstWorldBiomeArray[3] = new BiomeGenFirstWorld(Config.biomeID[4]);
		//firstWorldBiomeArray[3] = new BiomeGenFirstWorld(Config.biomeID[4]);
		
	}
	
    public static BiomeGenBase getBiome(int p_150568_0_)
    {
        if (p_150568_0_ >= 0 && p_150568_0_ <= firstWorldBiomeArray.length)
        {
            return firstWorldBiomeArray[p_150568_0_];
        }
        else
        {
            LogManager.getLogger().warn("Biome ID is out of bounds: " + p_150568_0_ + ", defaulting to 0 (Ocean)");
            return firstWorldBiomeArray[0];
        }
    }
	
	public static BiomeGenCorruption_Chaos chaosCorruption;
	public static BiomeGenCorruption_Frozen frozenCorruption;
	public static BiomeGenCorruption_Shadow shadowCorruption;
	public static BiomeGenCorruption_Magic magicCorruption;
	
	public static BiomeGenBase[] firstWorldBiomeArray = new BiomeGenBase[10];
}
