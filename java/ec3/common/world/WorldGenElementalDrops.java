package ec3.common.world;

import java.util.Random;

import ec3.common.block.BlocksCore;
import ec3.utils.cfg.Config;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.util.ForgeDirection;

public class WorldGenElementalDrops extends WorldGenerator{

	public Block minableReplaceable;
	public int dimID;
	public int generationStep;
	WorldGenElementalDrops parent;
	
	public static void handleGeneration(Random random, int chunkX, int chunkZ, World world,IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
		for(int i = 0; i < Config.oreGenAttempts; ++i)
		{
			int rndX = chunkX*16 + random.nextInt(16);
			int rndY = random.nextInt(256);
			int rndZ = chunkZ*16 + random.nextInt(16);
			Block minableReplaceable = world.provider.dimensionId == -1 ? Blocks.netherrack : world.provider.dimensionId == 1 ? Blocks.end_stone : Blocks.stone;
			WorldGenElementalDrops.staticGenerate(world, random, rndX, rndY, rndZ, world.provider.dimensionId, 0, minableReplaceable);
		}
	}
	
	public WorldGenElementalDrops(int dim)
	{
		dimID = dim;
		generationStep = 0;
		minableReplaceable = dim == -1 ? Blocks.netherrack : dim == 1 ? Blocks.end_stone : Blocks.stone;
		parent = this;
	}
	
	protected WorldGenElementalDrops(int i, WorldGenElementalDrops j, Block k)
	{
		dimID = i;
		parent = j;
		minableReplaceable = k;
	}
	
	@Override
	public boolean generate(World w, Random r,int x, int y, int z) 
	{
		int metadata = 0;
		if(parent.generationStep < 16)
		{
			metadata = dimID == 1 ? 10 : dimID == -1 ? 5 : 0;
		}else
		{
			metadata = dimID == 1 ? 11+r.nextInt(4) : dimID == -1 ? 6+r.nextInt(4) : 1+r.nextInt(4);
		}
		if(!w.blockExists(x, y, z))
			return false;
		 
		Block b = w.getBlock(x, y, z);
		if(b.isReplaceableOreGen(w, x, y, z, minableReplaceable))
		{
			if(parent.generationStep < 16)
			{
				w.setBlock(x, y, z, BlocksCore.oreDrops,metadata,0);
			}else
			{
				if(r.nextInt(parent.generationStep) <= 16)
					w.setBlock(x, y, z, BlocksCore.oreDrops,metadata,0);
				else
				{
					 w.setBlock(x, y, z, minableReplaceable,metadata,0);
					return false;
				}
			}
			++parent.generationStep;
		}else
			return false;
		int tries = 0;
		int i = r.nextInt(6);
		ForgeDirection dir = ForgeDirection.VALID_DIRECTIONS[i];
		while(!new WorldGenElementalDrops(dimID,parent,minableReplaceable).generate(w, r, x+dir.offsetX, y+dir.offsetY, z+dir.offsetZ) && tries < 8)
		{
			i = r.nextInt(6);
			dir = ForgeDirection.VALID_DIRECTIONS[i];
			++tries;
		}
		return true;
	}
	
	public static int staticGenerate(World w, Random r,int x, int y, int z, int dimID, int generationStep, Block minableReplaceable) 
	{
		if(generationStep > 24)
			return 24;
		
		int metadata = 0;
		if(generationStep < 16)
		{
			metadata = dimID == 1 ? 10 : dimID == -1 ? 5 : 0;
		}else
		{
			metadata = dimID == 1 ? 11+r.nextInt(4) : dimID == -1 ? 6+r.nextInt(4) : 1+r.nextInt(4);
		}
		if(!w.blockExists(x, y, z))
			return generationStep;
		 
		Block b = w.getBlock(x, y, z);
		if(b.isReplaceableOreGen(w, x, y, z, minableReplaceable))
		{
			if(generationStep < 16)
			{
				w.setBlock(x, y, z, BlocksCore.oreDrops,metadata,0);
			}else
			{
				if(r.nextInt(generationStep) <= 16)
					w.setBlock(x, y, z, BlocksCore.oreDrops,metadata,0);
				else
				{
					 w.setBlock(x, y, z, minableReplaceable,metadata,0);
					return ++generationStep;
				}
			}
		}else
			return generationStep;
		int i = r.nextInt(6);
		ForgeDirection dir = ForgeDirection.VALID_DIRECTIONS[i];
		WorldGenElementalDrops.staticGenerate(w, r, x+dir.offsetX, y+dir.offsetY, z+dir.offsetZ, dimID, generationStep+1, minableReplaceable);
		return generationStep;
	}

}
