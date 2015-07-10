package ec3.common.world;

import java.util.Random;

import ec3.common.block.BlocksCore;
import ec3.utils.cfg.Config;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenElementalDrops extends WorldGenerator{

	public Block minableReplacable;
	public boolean isEnd;
	public boolean isNether;
	public int generationSteps;
	public Random rnd;
	public int x,y,z;
	public World w;
	
	public WorldGenElementalDrops selectValidAABBGen()
	{
		int startX = x;
		int startZ = z;
		if(!w.blockExists(x+generationSteps/2, y, z))
		{
			while(!w.blockExists(x+generationSteps/2-1, y, z) && x+generationSteps/2-1 >= startX)
			{
				--x;
			}
		}
		if(!w.blockExists(x-generationSteps/2, y, z))
		{
			while(!w.blockExists(x-generationSteps/2+1, y, z) && x-generationSteps/2+1 <= startX)
			{
				++x;
			}
		}
		if(!w.blockExists(x, y, z+generationSteps/2))
		{
			while(!w.blockExists(x, y, z+generationSteps/2-1) && z+generationSteps/2-1 >= startZ)
			{
				--z;
			}
		}
		if(!w.blockExists(x, y, z-generationSteps/2))
		{
			while(!w.blockExists(x, y, z-generationSteps/2+1) && z-generationSteps/2+1 <= startZ)
			{
				++z;
			}
		}
		return this;
	}
	
	public WorldGenElementalDrops(World w, int type, int x, int y, int z)
	{
		rnd = w.rand;
		if(type == 0)
		{
			isEnd = false;
			isNether = false;
			minableReplacable = Blocks.stone;
		}
		if(type == 1)
		{
			isEnd = true;
			isNether = false;
			minableReplacable = Blocks.end_stone;
		}
		if(type == -1)
		{
			isEnd = false;
			isNether = true;
			minableReplacable = Blocks.netherrack;
		}
		generationSteps = 16;
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	@Override
	public boolean generate(World w, Random rnd,int x, int y, int z)
	{
		

		int meta = 0;
		
		if(isNether)
			meta += 5;
		if(isEnd)
			meta += 10;
		
		new WorldGenMinable(BlocksCore.oreDrops,meta,16,minableReplacable).generate(w, rnd, x, y, z);
		
		new WorldGenMinable(BlocksCore.oreDrops,meta+1,8,minableReplacable).generate(w, rnd, x+rnd.nextInt(6)-rnd.nextInt(6), y+rnd.nextInt(6)-rnd.nextInt(6), z+rnd.nextInt(6)-rnd.nextInt(6));
		new WorldGenMinable(BlocksCore.oreDrops,meta+2,8,minableReplacable).generate(w, rnd, x+rnd.nextInt(6)-rnd.nextInt(6), y+rnd.nextInt(6)-rnd.nextInt(6), z+rnd.nextInt(6)-rnd.nextInt(6));
		new WorldGenMinable(BlocksCore.oreDrops,meta+3,8,minableReplacable).generate(w, rnd, x+rnd.nextInt(6)-rnd.nextInt(6), y+rnd.nextInt(6)-rnd.nextInt(6), z+rnd.nextInt(6)-rnd.nextInt(6));
		new WorldGenMinable(BlocksCore.oreDrops,meta+4,8,minableReplacable).generate(w, rnd, x+rnd.nextInt(6)-rnd.nextInt(6), y+rnd.nextInt(6)-rnd.nextInt(6), z+rnd.nextInt(6)-rnd.nextInt(6));
		
		return false;
	}
	
	public static void handleGeneration(Random random, int chunkX, int chunkZ, World world,IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
		for(int i = 0; i < Config.oreGenAttempts; ++i)
		{
			int x = chunkX*16 + random.nextInt(16);
			int y = random.nextInt(world.getHeight());
			int z = chunkZ*16 + random.nextInt(16);
			int type = world.provider.isSurfaceWorld() ? 0 : world.provider.isHellWorld ? -1 : 1;
			new WorldGenElementalDrops(world,type,x,y,z).selectValidAABBGen().generate(world, random, x, y, z);
		}
	}

}
