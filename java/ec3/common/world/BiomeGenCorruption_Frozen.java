package ec3.common.world;

import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenCorruption_Frozen extends BiomeGenBase
{
    public BiomeGenCorruption_Frozen(int par1)
    {
        super(par1);
        this.biomeName = "Corrupted Land";
    }
    
    public int getBiomeGrassColor()
    {
    	return 0x0077ff;
    }
    
    public int getBiomeFoliageColor()
    {
    	return 0x0077ff;
    }
    
    public int getWaterColorMultiplier()
    {
    	return 0x0077ff;
    }
    
    public int getModdedBiomeGrassColor(int original)
    {
    	return 0x0077ff;
    }
    
    public int getModdedBiomeFoliageColor(int original)
    {
    	return 0x0077ff;
    }
}
