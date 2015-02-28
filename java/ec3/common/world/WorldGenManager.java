package ec3.common.world;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenManager implements IWorldGenerator{

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
		if(world != null)
		{
			if(world.provider != null)
			{
				int dim = world.provider.dimensionId;
				if(dim == -1) this.handleNetherGen(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
				else if(dim == 1) this.handleEndGen(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
				else this.handlePossibleOverworldGen(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
			}
		}
	}
	
	public void handleNetherGen(Random random, int chunkX, int chunkZ, World world,IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
		WorldGenElementalDrops.handleGeneration(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
	}
	
	public void handlePossibleOverworldGen(Random random, int chunkX, int chunkZ, World world,IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
		WorldGenElementalDrops.handleGeneration(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
		WorldGenElderMRUCC.handleGeneration(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
	}
	
	public void handleEndGen(Random random, int chunkX, int chunkZ, World world,IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
		WorldGenElementalDrops.handleGeneration(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
	}

}
