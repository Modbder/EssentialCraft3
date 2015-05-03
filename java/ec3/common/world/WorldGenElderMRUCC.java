package ec3.common.world;

import static net.minecraftforge.common.ChestGenHooks.DUNGEON_CHEST;

import java.util.Random;

import DummyCore.Utils.Coord3D;
import DummyCore.Utils.MathUtils;
import ec3.utils.cfg.Config;
import ec3.utils.common.ECUtils;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.DungeonHooks;

public class WorldGenElderMRUCC extends WorldGenerator{
	public int type;
	
	public static void handleGeneration(Random random, int chunkX, int chunkZ, World world,IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
		for(int i = 0; i < Config.eMRUCUGenAttempts; ++i)
		{
			int rndX = chunkX*16 + random.nextInt(16);
			int rndY = random.nextInt(128);
			int rndZ = chunkZ*16 + random.nextInt(16);
			new WorldGenElderMRUCC(random.nextInt(4)).generate(world, random, rndX, rndY, rndZ);
		}
	}
	
	public WorldGenElderMRUCC(int i)
	{
		type = i;
	}
	@Override
	public boolean generate(World var1, Random var2, int var3, int var4,
			int var5) {
		if(type == 0 && canGenerateAt(var3-2,var5-2,var3+2,var5+2,var4,true,var1))
		{
			for(int x = -2; x<= 2; ++x)
			{
				for(int z = -2; z<= 2; ++z)
				{
					var1.setBlock(var3+x, var4, var5+z, Block.getBlockFromName("minecraft:stonebrick"), 0, 3);
					if((x == -2 || x == 2)||(z == -2 || z == 2))
					{
						var1.setBlock(var3+x, var4+1, var5+z, Block.getBlockFromName("minecraft:cobblestone_wall"), 0, 3);
					}
					if(x == 0 && z == 0)
					{
						var1.setBlock(var3+x, var4+1, var5+z, Block.getBlockFromName("minecraft:stonebrick"), 3, 3);
						ECUtils.createMRUCUAt(var1, new Coord3D(var3+x+0.5F,var4+1.5F,var5+z+0.5F), 5000+var2.nextInt(5000), MathUtils.randomFloat(var2)+1.0F, false, false);
					}
				}
			}
		}
		if(type == 1 && canGenerateAt(var3-4,var5-4,var3+4,var5+4,var4,true,var1))
		{
			for(int x = -4; x<= 4; ++x)
			{
				for(int z = -4; z<= 4; ++z)
				{
					var1.setBlock(var3+x, var4, var5+z, Block.getBlockFromName("minecraft:stonebrick"), var2.nextInt(3), 3);
					if((x == -4 || x == 4)||(z == -4 || z == 4))
					{
						var1.setBlock(var3+x, var4+1, var5+z, Block.getBlockFromName("minecraft:cobblestone_wall"), 0, 3);
					}
					if(x == 0 && z == 0)
					{
						var1.setBlock(var3+x, var4+1, var5+z, Block.getBlockFromName("minecraft:stonebrick"), 3, 3);
						ECUtils.createMRUCUAt(var1, new Coord3D(var3+x+0.5F,var4+1.5F,var5+z+0.5F), 5000+var2.nextInt(5000), MathUtils.randomFloat(var2)+1.0F, false, false);
					}
					if((x == -3 && z == -3) || (x == 3 && z == -3) || (x == 3 && z == 3) || (x == -3 && z == 3))
					{
						var1.setBlock(var3+x, var4+1, var5+z, Block.getBlockFromName("minecraft:chest"), var2.nextInt(4), 3);
                        TileEntityChest tileentitychest = (TileEntityChest)var1.getTileEntity(var3+x, var4+1, var5+z);

                        if (tileentitychest != null)
                        {
                            WeightedRandomChestContent.generateChestContents(var2, ChestGenHooks.getItems(DUNGEON_CHEST, var2), tileentitychest, ChestGenHooks.getCount(DUNGEON_CHEST, var2));
                        }
					}
				}
			}
		}
		if(type == 2 && canGenerateAt(var3-4,var5-4,var3+4,var5+4,var4,true,var1))
		{
			for(int x = -4; x<= 4; ++x)
			{
				for(int z = -4; z<= 4; ++z)
				{
					var1.setBlock(var3+x, var4, var5+z, Block.getBlockFromName("minecraft:stonebrick"), var2.nextInt(3), 3);
					var1.setBlock(var3+x, var4+1, var5+z, Block.getBlockFromName("minecraft:stonebrick"), var2.nextInt(3), 3);
					if((x == -4 || x == 4)||(z == -4 || z == 4))
					{
						var1.setBlock(var3+x, var4+2, var5+z, Block.getBlockFromName("minecraft:cobblestone_wall"), 0, 3);
					}
					if(x == 0 && z == 0)
					{
						var1.setBlock(var3+x, var4+2, var5+z, Block.getBlockFromName("minecraft:stonebrick"), 3, 3);
						ECUtils.createMRUCUAt(var1, new Coord3D(var3+x+0.5F,var4+1.5F,var5+z+0.5F), 5000+var2.nextInt(5000), MathUtils.randomFloat(var2)+1.0F, false, false);
					}
					if(x != 3 && x != -3 && z != 3 && z != -3 && ((x == -2 || x == 2)||(z == -2 || z == 2)))
					{
						var1.setBlock(var3+x, var4+2, var5+z, Block.getBlockFromName("minecraft:cobblestone_wall"), 0, 3);
					}
					if(x != 4 && x != -4 && z != 4 && z != -4 && ((x == -3 || x == 3)||(z == -3 || z == 3)))
					{
						var1.setBlock(var3+x, var4+1, var5+z, Block.getBlockFromName("minecraft:lava"), 0, 3);
					}
				}
			}
		}
		if(type == 3 && canGenerateAt(var3-4,var5-4,var3+4,var5+4,var4,false,var1))
		{
			for(int x = -4; x<= 4; ++x)
			{
				for(int z = -4; z<= 4; ++z)
				{
					var1.setBlock(var3+x, var4, var5+z, Block.getBlockFromName("minecraft:stonebrick"), var2.nextInt(3), 3);
					var1.setBlock(var3+x, var4+4, var5+z, Block.getBlockFromName("minecraft:stonebrick"), var2.nextInt(3), 3);
					if((x == -4 || x == 4)||(z == -4 || z == 4))
					{
						var1.setBlock(var3+x, var4+1, var5+z, Block.getBlockFromName("minecraft:iron_bars"), 0, 3);
						var1.setBlock(var3+x, var4+2, var5+z, Block.getBlockFromName("minecraft:stonebrick"), var2.nextInt(3), 3);
						var1.setBlock(var3+x, var4+3, var5+z, Block.getBlockFromName("minecraft:iron_bars"), 0, 3);
					}
					if((x == -4 && z == -4) || (x == 4 && z == -4) || (x == 4 && z == 4) || (x == -4 && z == 4))
					{
						var1.setBlock(var3+x, var4+1, var5+z, Block.getBlockFromName("minecraft:stonebrick"), var2.nextInt(3), 3);
						var1.setBlock(var3+x, var4+3, var5+z, Block.getBlockFromName("minecraft:stonebrick"), var2.nextInt(3), 3);
					}
					if(x == 0 && z == 0)
					{
						var1.setBlock(var3+x, var4+1, var5+z, Block.getBlockFromName("minecraft:stonebrick"), 3, 3);
						ECUtils.createMRUCUAt(var1, new Coord3D(var3+x+0.5F,var4+1.5F,var5+z+0.5F), 5000+var2.nextInt(5000), MathUtils.randomFloat(var2)+1.0F, false, false);
					}
					if((x == -3 && z == -3) || (x == 3 && z == -3) || (x == 3 && z == 3) || (x == -3 && z == 3))
					{
						var1.setBlock(var3+x, var4+1, var5+z, Block.getBlockFromName("minecraft:chest"), var2.nextInt(4), 3);
                        TileEntityChest tileentitychest = (TileEntityChest)var1.getTileEntity(var3+x, var4+1, var5+z);

                        if (tileentitychest != null)
                        {
                            WeightedRandomChestContent.generateChestContents(var2, ChestGenHooks.getItems(DUNGEON_CHEST, var2), tileentitychest, ChestGenHooks.getCount(DUNGEON_CHEST, var2));
                        }
					}
					if((x == -1 && z == 0) || (x == 1 && z == 0) || (x == 0 && z == 1) || (x == 0 && z == -1))
					{
						var1.setBlock(var3+x, var4+1, var5+z, Block.getBlockFromName("minecraft:mob_spawner"), 0, 3);
                        TileEntityMobSpawner tileentitymobspawner = (TileEntityMobSpawner)var1.getTileEntity(var3+x, var4+1, var5+z);

                        if (tileentitymobspawner != null)
                        {
                        	tileentitymobspawner.func_145881_a().setEntityName(DungeonHooks.getRandomDungeonMob(var2));
                        }
					}
				}
			}
		}
		
		return false;
	}

	public boolean canGenerateAt(int minX, int minZ, int maxX, int maxZ, int y, boolean requiresSolidBlocks, World w)
	{
		for(int dx = minX; dx <= maxX; ++dx)
		{
			for(int dz = minZ; dz <= maxZ; ++dz)
			{
				Block b = w.getBlock(dx, y, dz);
				if(!b.isAir(w, dx, y, dz) && b.isNormalCube(w, dx, y, dz))
				{
					if(requiresSolidBlocks)
					{
						if(!w.getBlock(dx, y+1, dz).isNormalCube(w, dx, y, dz) || w.getBlock(dx, y+1, dz).isAir(w, dx, y+1, dz)){}
						else
							return false;
					}else
					{
						if(w.getBlock(dx, y, dz).isReplaceableOreGen(w, dx, y, dz, Blocks.stone)){}
						else
							return false;
					}
						
				}else
					return false;
			}
		}
		return true;
	}
}
