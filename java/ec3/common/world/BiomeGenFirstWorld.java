package ec3.common.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenDesertWells;

public class BiomeGenFirstWorld extends BiomeGenBase
{
	public int grassColor = 16777215;
	public int waterColor = 16777215;
	public int leavesColor = 16777215;
	
	public BiomeGenFirstWorld setGrassColor(int i)
	{
		grassColor = i;
		return this;
	}
	
	public BiomeGenFirstWorld setWaterColor(int i)
	{
		waterColor = i;
		return this;
	}
	
	public BiomeGenFirstWorld setLeavesColor(int i)
	{
		leavesColor = i;
		return this;
	}
	
	public BiomeGenFirstWorld setName(String s)
	{
		this.biomeName = s;
		return this;
	}
	
    public BiomeGenFirstWorld(int par1)
    {
        super(par1);
    }
    
    public int getBiomeGrassColor()
    {
    	return grassColor;
    }
    
    public int getBiomeFoliageColor()
    {
    	return leavesColor;
    }
    
    public int getWaterColorMultiplier()
    {
    	return waterColor;
    }
    
    public int getModdedBiomeGrassColor(int original)
    {
    	return grassColor;
    }
    
    public int getModdedBiomeFoliageColor(int original)
    {
    	return leavesColor;
    }
}
