package ec3.common.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenDesertWells;

public class BiomeGenCorruption_Chaos extends BiomeGenBase
{
    public BiomeGenCorruption_Chaos(int par1)
    {
        super(par1);
        this.biomeName = "Corrupted Land";
    }
    
    public int getBiomeGrassColor()
    {
    	return 0x770000;
    }
    
    public int getBiomeFoliageColor()
    {
    	return 0x770000;
    }
    
    public int getWaterColorMultiplier()
    {
    	return 0x770000;
    }
    
    public int getModdedBiomeGrassColor(int original)
    {
    	return 0x770000;
    }
    
    public int getModdedBiomeFoliageColor(int original)
    {
    	return 0x770000;
    }
}
