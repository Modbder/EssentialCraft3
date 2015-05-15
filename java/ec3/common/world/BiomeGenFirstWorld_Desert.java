package ec3.common.world;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenFirstWorld_Desert extends BiomeGenBase
{
	public int grassColor = 16777215;
	public int waterColor = 16777215;
	public int leavesColor = 16777215;
	
	public BiomeGenFirstWorld_Desert setGrassColor(int i)
	{
		grassColor = i;
		return this;
	}
	
	public BiomeGenFirstWorld_Desert setWaterColor(int i)
	{
		waterColor = i;
		return this;
	}
	
	public BiomeGenFirstWorld_Desert setLeavesColor(int i)
	{
		leavesColor = i;
		return this;
	}
	
	public BiomeGenFirstWorld_Desert setName(String s)
	{
		this.biomeName = s;
		return this;
	}
	
    public BiomeGenFirstWorld_Desert(int par1)
    {
        super(par1);
        this.topBlock = Blocks.sand;
        this.fillerBlock = Blocks.sand;
        this.theBiomeDecorator.treesPerChunk = -999;
        this.theBiomeDecorator.deadBushPerChunk = 2;
        this.theBiomeDecorator.reedsPerChunk = 50;
        this.theBiomeDecorator.cactiPerChunk = 10;
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
    
    public void decorate(World p_76728_1_, Random p_76728_2_, int p_76728_3_, int p_76728_4_)
    {
    	this.theBiomeDecorator.cactusGen = new WorldGenDreadCacti();
        this.theBiomeDecorator.decorateChunk(p_76728_1_, p_76728_2_, this, p_76728_3_, p_76728_4_);
    }
}
