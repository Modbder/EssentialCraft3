package ec3.common.world;

import java.util.Random;

import ec3.common.block.BlocksCore;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenDesertWells;

public class BiomeGenFirstWorld_Dreadlands extends BiomeGenBase
{
	public int grassColor = 16777215;
	public int waterColor = 16777215;
	public int leavesColor = 16777215;
	
	public BiomeGenFirstWorld_Dreadlands setGrassColor(int i)
	{
		grassColor = i;
		return this;
	}
	
	public BiomeGenFirstWorld_Dreadlands setWaterColor(int i)
	{
		waterColor = i;
		return this;
	}
	
	public BiomeGenFirstWorld_Dreadlands setLeavesColor(int i)
	{
		leavesColor = i;
		return this;
	}
	
	public BiomeGenFirstWorld_Dreadlands setName(String s)
	{
		this.biomeName = s;
		return this;
	}
	
    public BiomeGenFirstWorld_Dreadlands(int par1)
    {
        super(par1);
        this.topBlock = BlocksCore.dreadDirt;
        this.fillerBlock = BlocksCore.dreadDirt;
        this.theBiomeDecorator.treesPerChunk = -999;
        this.theBiomeDecorator.deadBushPerChunk = 2;
        this.theBiomeDecorator.reedsPerChunk = -999;
        this.theBiomeDecorator.cactiPerChunk = -999;
        this.theBiomeDecorator.cactusGen = new WorldGenDreadCacti();
        
        this.spawnableCreatureList.clear();
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
