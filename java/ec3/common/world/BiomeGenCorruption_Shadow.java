package ec3.common.world;

import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenCorruption_Shadow extends BiomeGenBase
{
    public BiomeGenCorruption_Shadow(int par1)
    {
        super(par1);
        this.biomeName = "Corrupted Land";
    }
    
    public int getBiomeGrassColor()
    {
    	return 0x222222;
    }
    
    public int getBiomeFoliageColor()
    {
    	return 0x222222;
    }
    
    public int getWaterColorMultiplier()
    {
    	return 0x222222;
    }
    
    public int getModdedBiomeGrassColor(int original)
    {
    	return 0x222222;
    }
    
    public int getModdedBiomeFoliageColor(int original)
    {
    	return 0x222222;
    }
}
